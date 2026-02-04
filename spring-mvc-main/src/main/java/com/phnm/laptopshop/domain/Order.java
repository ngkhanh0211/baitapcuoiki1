package com.phnm.laptopshop.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double totalPrice;
    private String receiverName;
    private String receiverAddress;
    private String receiverPhone;
    private String status;
    
    // Các trường cho thanh toán VNPay
    private String paymentMethod; // "COD" hoặc "VNPAY"
    private String vnpTxnRef; // Mã giao dịch VNPay
    private String vnpAmount; // Số tiền thanh toán
    private String vnpBankCode; // Mã ngân hàng
    private String vnpPayDate; // Ngày thanh toán
    private String vnpTransactionStatus; // Trạng thái giao dịch
    private String vnpOrderInfo; // Thông tin đơn hàng

    // userId
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
    
    // Getter và Setter cho các trường VNPay
    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getVnpTxnRef() {
        return vnpTxnRef;
    }

    public void setVnpTxnRef(String vnpTxnRef) {
        this.vnpTxnRef = vnpTxnRef;
    }

    public String getVnpAmount() {
        return vnpAmount;
    }

    public void setVnpAmount(String vnpAmount) {
        this.vnpAmount = vnpAmount;
    }

    public String getVnpBankCode() {
        return vnpBankCode;
    }

    public void setVnpBankCode(String vnpBankCode) {
        this.vnpBankCode = vnpBankCode;
    }

    public String getVnpPayDate() {
        return vnpPayDate;
    }

    public void setVnpPayDate(String vnpPayDate) {
        this.vnpPayDate = vnpPayDate;
    }

    public String getVnpTransactionStatus() {
        return vnpTransactionStatus;
    }

    public void setVnpTransactionStatus(String vnpTransactionStatus) {
        this.vnpTransactionStatus = vnpTransactionStatus;
    }

    public String getVnpOrderInfo() {
        return vnpOrderInfo;
    }

    public void setVnpOrderInfo(String vnpOrderInfo) {
        this.vnpOrderInfo = vnpOrderInfo;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", totalPrice=" + totalPrice + "]";
    }

}
