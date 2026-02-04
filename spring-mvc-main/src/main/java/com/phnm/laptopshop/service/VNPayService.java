package com.phnm.laptopshop.service;

import com.phnm.laptopshop.config.VNPayConfig;
import com.phnm.laptopshop.domain.Order;
import com.phnm.laptopshop.repository.OrderRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class VNPayService {

    private final OrderRepository orderRepository;

    @Autowired
    public VNPayService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Tạo URL thanh toán cho VNPay dựa trên đơn hàng
     * @param request HttpServletRequest
     * @param order Đơn hàng
     * @return URL thanh toán
     */
    public String createPaymentUrl(HttpServletRequest request, Order order) {
        String orderInfo = "Thanh toan don hang #" + order.getId();
        String vnp_TxnRef = order.getId() + "-" + System.currentTimeMillis();
        
        // Lưu thông tin giao dịch vào đơn hàng
        order.setVnpTxnRef(vnp_TxnRef);
        order.setVnpOrderInfo(orderInfo);
        order.setPaymentMethod("VNPAY");
        orderRepository.save(order);
        
        return VNPayConfig.getPaymentUrl(request, vnp_TxnRef, order.getTotalPrice(), orderInfo, null);
    }
    
    /**
     * Xử lý callback từ VNPay sau khi thanh toán
     * @param request Dữ liệu callback từ VNPay
     * @return Kết quả xử lý
     */
    public Map<String, String> processPaymentCallback(HttpServletRequest request) {
        Map<String, String> response = new HashMap<>();
        
        try {
            // Lấy thông tin từ VNPay callback
            String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
            String vnp_TxnRef = request.getParameter("vnp_TxnRef");
            String vnp_Amount = request.getParameter("vnp_Amount");
            String vnp_TransactionStatus = request.getParameter("vnp_TransactionStatus");
            String vnp_BankCode = request.getParameter("vnp_BankCode");
            String vnp_PayDate = request.getParameter("vnp_PayDate");
            String vnp_OrderInfo = request.getParameter("vnp_OrderInfo");
            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            
            // Kiểm tra mã giao dịch hợp lệ
            String[] parts = vnp_TxnRef.split("-");
            if (parts.length < 1) {
                response.put("status", "ERROR");
                response.put("message", "Invalid transaction reference");
                return response;
            }
            
            long orderId;
            try {
                orderId = Long.parseLong(parts[0]);
            } catch (NumberFormatException e) {
                response.put("status", "ERROR");
                response.put("message", "Invalid order ID");
                return response;
            }
            
            // Kiểm tra đơn hàng tồn tại
            Optional<Order> orderOpt = orderRepository.findById(orderId);
            if (orderOpt.isEmpty()) {
                response.put("status", "ERROR");
                response.put("message", "Order not found");
                return response;
            }
            
            Order order = orderOpt.get();
            
            // Nếu giao dịch thành công (Code = 00)
            if ("00".equals(vnp_ResponseCode) && "00".equals(vnp_TransactionStatus)) {
                // Cập nhật thông tin thanh toán
                order.setVnpTxnRef(vnp_TxnRef);
                order.setVnpAmount(vnp_Amount);
                order.setVnpBankCode(vnp_BankCode);
                order.setVnpPayDate(vnp_PayDate);
                order.setVnpTransactionStatus(vnp_TransactionStatus);
                order.setVnpOrderInfo(vnp_OrderInfo);
                order.setStatus("Đã thanh toán");
                
                orderRepository.save(order);
                
                response.put("status", "SUCCESS");
                response.put("message", "Payment successful");
                response.put("orderId", String.valueOf(order.getId()));
            } else {
                // Giao dịch thất bại hoặc hủy bỏ
                order.setVnpTxnRef(vnp_TxnRef);
                order.setVnpTransactionStatus(vnp_TransactionStatus);
                order.setStatus("Chờ thanh toán");
                
                orderRepository.save(order);
                
                response.put("status", "FAILED");
                response.put("message", "Payment failed or cancelled");
                response.put("orderId", String.valueOf(order.getId()));
            }
        } catch (Exception e) {
            response.put("status", "ERROR");
            response.put("message", "Error processing payment: " + e.getMessage());
        }
        
        return response;
    }
} 