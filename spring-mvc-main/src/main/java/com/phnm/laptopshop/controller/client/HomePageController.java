package com.phnm.laptopshop.controller.client;

import com.phnm.laptopshop.domain.*;
import com.phnm.laptopshop.domain.dto.ProductCriteriaDTO;
import com.phnm.laptopshop.domain.dto.RegisterDTO;
import com.phnm.laptopshop.repository.CartRepository;
import com.phnm.laptopshop.service.OrderService;
import com.phnm.laptopshop.service.ProductService;
import com.phnm.laptopshop.service.UserService;
import com.phnm.laptopshop.service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class HomePageController {
    private final ProductService productService;
    private final OrderService orderService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final VNPayService vnPayService;

    public HomePageController(
            ProductService productService,
            OrderService orderService,
            UserService userService,
            PasswordEncoder passwordEncoder,
            VNPayService vnPayService) {
        this.productService = productService;
        this.orderService = orderService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.vnPayService = vnPayService;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        Pageable pageable = PageRequest.of(0, 8);
        Page<Product> products = productService.getAllProducts(pageable);
        List<Product> productList = products.getContent();
        model.addAttribute("products", productList);
        return "client/homepage/index";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerUser", new RegisterDTO());
        return "client/auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("registerUser") @Valid RegisterDTO registerDTO,
                               BindingResult bindingResult) {
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>" + error.getField() + " - " + error.getDefaultMessage());
        }

        if (bindingResult.hasErrors()) {
            return "client/auth/register";
        }
        User user = userService.registerDTOtoUser(registerDTO);
        String hashPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(hashPassword);
        user.setRole(userService.getRoleByName("USER"));

        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "client/auth/login";
    }

    @GetMapping("/access-deny")
    public String getDenyPage() {
        return "client/auth/deny";
    }

    @GetMapping("/cart")
    public String getCartPage(Model model, HttpServletRequest request) {
        User currentUser = new User();
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        Cart cart = productService.findCartByUser(currentUser);

        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

        double totalPrice = 0;

        for (CartDetail cartDetail : cartDetails) {
            totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
        }

        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);

        model.addAttribute("cart", cart);
        return "client/cart/detail";
    }

    @GetMapping("/checkout")
    public String getCheckoutPage(Model model, HttpServletRequest request) {
        User currentUser = new User();
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        Cart cart = productService.findCartByUser(currentUser);

        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

        double totalPrice = 0;

        for (CartDetail cartDetail : cartDetails) {
            totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
        }

        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);

        return "client/cart/checkout";
    }

    @PostMapping("/confirm-checkout")
    public String getCheckoutPage(@ModelAttribute("cart") Cart cart) {
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
        productService.updateCartBeforeCheckout(cartDetails);
        return "redirect:/checkout";
    }

    @PostMapping("/place-order")
    public String placeOrder(
            HttpServletRequest request,
            @RequestParam("receiverName") String receiverName,
            @RequestParam("receiverAddress") String receiverAddress,
            @RequestParam("receiverPhone") String receiverPhone,
            @RequestParam(value = "paymentMethod", required = false, defaultValue = "COD") String paymentMethod
    ) {
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        User currentUser = userService.getUserById(id);
        
        // Nếu thanh toán qua VNPay
        if ("VNPAY".equals(paymentMethod)) {
            // Tạo đơn hàng mới với trạng thái "Chờ thanh toán"
            Order order = orderService.createOrderForVNPay(currentUser, session, receiverName, receiverAddress, receiverPhone);
            
            if (order != null) {
                // Tạo URL thanh toán VNPay và chuyển hướng
                String paymentUrl = vnPayService.createPaymentUrl(request, order);
                return "redirect:" + paymentUrl;
            }
            
            // Nếu không tạo được đơn hàng, quay lại trang checkout
            return "redirect:/checkout";
        }
        
        // Thanh toán COD (mặc định)
        orderService.placeOrder(currentUser, session, receiverName, receiverAddress, receiverPhone);
        return "redirect:/order-success";
    }
    
    /**
     * Xử lý callback từ VNPay sau khi thanh toán
     */
    @GetMapping("/payment-callback")
    public String paymentCallback(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        Map<String, String> response = vnPayService.processPaymentCallback(request);
        
        if ("SUCCESS".equals(response.get("status"))) {
            // Thanh toán thành công
            redirectAttributes.addFlashAttribute("paymentStatus", "success");
            redirectAttributes.addFlashAttribute("paymentMessage", "Thanh toán thành công");
            
            // Xóa giỏ hàng
            HttpSession session = request.getSession(false);
            long userId = (long) session.getAttribute("id");
            User currentUser = userService.getUserById(userId);
            orderService.clearCartAfterOrder(currentUser, session);
            
            return "redirect:/order-success";
        } else if ("FAILED".equals(response.get("status"))) {
            // Thanh toán thất bại
            redirectAttributes.addFlashAttribute("paymentStatus", "failed");
            redirectAttributes.addFlashAttribute("paymentMessage", "Thanh toán thất bại hoặc bị hủy bỏ");
            return "redirect:/order-failed";
        } else {
            // Lỗi xử lý
            redirectAttributes.addFlashAttribute("paymentStatus", "error");
            redirectAttributes.addFlashAttribute("paymentMessage", response.get("message"));
            return "redirect:/order-failed";
        }
    }
    
    /**
     * Trang thông báo thanh toán thất bại
     */
    @GetMapping("/order-failed")
    public String getOrderFailedPage(Model model) {
        return "client/cart/orderFailed";
    }

    @GetMapping("/order-success")
    public String getOrderSuccessPage() {
        return "client/cart/orderSuccess";
    }

    @GetMapping("/order-history")
    public String getOrderHistoryPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        User currentUser = userService.getUserById(id);
        List<Order> orders = orderService.getOrdersByUser(currentUser);

        model.addAttribute("orders", orders);
        return "client/cart/orderHistory";
    }

    @GetMapping("/product")
    public String getProductPage(
            Model model,
            ProductCriteriaDTO productCriteriaDTO,
            HttpServletRequest request) {
        int page = 1;
        try {
            if (productCriteriaDTO.getPage().isPresent()) {
                page = Integer.parseInt(productCriteriaDTO.getPage().get());
            }
        } catch (Exception e) {}

        Pageable pageable = PageRequest.of(page - 1, 3);
        if (productCriteriaDTO.getSort() != null && productCriteriaDTO.getSort().isPresent()) {
            String sort = productCriteriaDTO.getSort().get();
            if (sort.equals("gia-tang-dan")) {
                pageable = PageRequest.of(page - 1, 3, Sort.by(Product_.PRICE).ascending());

            } else if (sort.equals("gia-giam-dan")) {
                pageable = PageRequest.of(page - 1, 3, Sort.by(Product_.PRICE).descending());


            } else {
                pageable = PageRequest.of(page - 1, 3);

            }
        }
        Page<Product> productsPage = productService.getAllProductsWithSpec(pageable, productCriteriaDTO);

        List<Product> products = !productsPage.getContent().isEmpty() ? productsPage.getContent() : new ArrayList<>();

        String qs = request.getQueryString();
        if (qs != null && !qs.isBlank()) {
            qs = qs.replace("page=" + page, "");
        }

        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productsPage.getTotalPages());
        model.addAttribute("queryString", qs);
        return "client/product/list";
    }
    
    @GetMapping("/search")
    public String searchProducts(
            @RequestParam("keyword") String keyword, 
            @RequestParam(value = "page", defaultValue = "1") int page,
            Model model, 
            HttpServletRequest request) {
        
        // Page is 1-indexed in the view but 0-indexed in Spring Pageable
        int pageIndex = Math.max(0, page - 1);
        Pageable pageable = PageRequest.of(pageIndex, 9); // Show 9 products per page
        
        Page<Product> productsPage = productService.searchProductsByName(keyword, pageable);
        
        List<Product> products = !productsPage.getContent().isEmpty() 
                ? productsPage.getContent() 
                : new ArrayList<>();
        
        String qs = "keyword=" + keyword;
        
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productsPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("queryString", "&" + qs);
        
        return "client/product/list";
    }
}
