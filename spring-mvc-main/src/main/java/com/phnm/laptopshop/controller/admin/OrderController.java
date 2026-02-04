package com.phnm.laptopshop.controller.admin;

import com.phnm.laptopshop.domain.Order;
import com.phnm.laptopshop.domain.OrderDetail;
import com.phnm.laptopshop.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {

    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/admin/order")
    public String getOrder(
            Model model,
            @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (Exception e) {
        }
        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<Order> ordersPage = orderService.getAllOrders(pageable);
        List<Order> orders = ordersPage.getContent();
        model.addAttribute("orders", orders);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", ordersPage.getTotalPages());
        return "admin/order/index";
    }

    @GetMapping("/admin/order/{id}")
    public String getOrderDetail(Model model, @PathVariable long id) {
        Optional<Order> orderOptional = orderService.getOrderById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            List<OrderDetail> orderDetails = order.getOrderDetails();
            model.addAttribute("id", id);
            model.addAttribute("order", order);
            model.addAttribute("orderDetails", orderDetails);
        }
        return "admin/order/detail";
    }

    @GetMapping("/admin/order/update/{id}")
    public String getUpdateOrder(Model model, @PathVariable long id) {
        Optional<Order> orderOptional = orderService.getOrderById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            model.addAttribute("order", order);
        }
        return "admin/order/update";
    }

    @PostMapping("/admin/order/update")
    public String updateOrder(@ModelAttribute("order") Order order) {
        orderService.updateOrderStatus(order);
        return "redirect:/admin/order";
    }

    @GetMapping("/admin/order/delete/{id}")
    public String getDeleteOrderPage(Model model, @PathVariable long id) {
        Optional<Order> orderOptional = orderService.getOrderById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            model.addAttribute("order", order);
        }
        return "admin/order/delete";
    }

    @PostMapping("/admin/order/delete")
    public String deleteOrder(@ModelAttribute("order") Order order) {
        orderService.deleteOrderById(order.getId());
        return "redirect:/admin/order";
    }
}

