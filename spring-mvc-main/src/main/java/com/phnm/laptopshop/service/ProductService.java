package com.phnm.laptopshop.service;

import com.phnm.laptopshop.domain.Cart;
import com.phnm.laptopshop.domain.CartDetail;
import com.phnm.laptopshop.domain.Product;
import com.phnm.laptopshop.domain.User;
import com.phnm.laptopshop.domain.dto.ProductCriteriaDTO;
import com.phnm.laptopshop.repository.CartDetailRepository;
import com.phnm.laptopshop.repository.CartRepository;
import com.phnm.laptopshop.repository.ProductRepository;
import com.phnm.laptopshop.repository.UserRepository;
import com.phnm.laptopshop.service.specification.ProductSpecs;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;

    public ProductService(
            ProductRepository productRepository,
            UserRepository userRepository,
            CartRepository cartRepository,
            CartDetailRepository cartDetailRepository,
            UserService userService) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
    }

    public Page<Product> getAllProductsWithSpec(Pageable pageable, ProductCriteriaDTO productCriteriaDTO) {
        if (productCriteriaDTO.getTarget() == null
                && productCriteriaDTO.getFactory() == null
                && productCriteriaDTO.getPrice() == null) {
            return productRepository.findAll(pageable);
        }

        Specification<Product> combinedSpec = Specification.where(null);

        if (productCriteriaDTO.getTarget() != null && productCriteriaDTO.getTarget().isPresent()) {
            Specification<Product> currentSpecs = ProductSpecs.matchListTarget(productCriteriaDTO.getTarget().get());
            combinedSpec = combinedSpec.and(currentSpecs);
        }
        if (productCriteriaDTO.getFactory()!= null && productCriteriaDTO.getFactory().isPresent()) {
            Specification<Product> currentSpecs = ProductSpecs.matchListFactory(productCriteriaDTO.getFactory().get());
            combinedSpec = combinedSpec.and(currentSpecs);
        }
        if (productCriteriaDTO.getPrice() != null && productCriteriaDTO.getPrice().isPresent()) {
            Specification<Product> currentSpecs = buildPriceSpecification(productCriteriaDTO.getPrice().get());
            combinedSpec = combinedSpec.and(currentSpecs);
        }
        return productRepository.findAll(combinedSpec, pageable);
    }

//    public Page<Product> getAllProductsWithSpec(Pageable pageable, double min) {
//        return productRepository.findAll(ProductSpecs.minPrice(min), pageable);
//    }

//    public Page<Product> getAllProductsWithSpec(Pageable pageable, double max) {
//        return productRepository.findAll(ProductSpecs.maxPrice(max), pageable);
//    }

//    public Page<Product> getAllProductsWithSpec(Pageable pageable, String factory) {
//        return productRepository.findAll(ProductSpecs.matchFactory(factory), pageable);
//    }

//    public Page<Product> getAllProductsWithSpec(Pageable pageable, List<String> factory) {
//        return productRepository.findAll(ProductSpecs.matchListFactory(factory), pageable);
//    }

//    public Page<Product> getAllProductsWithSpec(Pageable pageable, String price) {
//        if (price.equals("10-toi-15-trieu")) {
//            double min = 10000000;
//            double max = 15000000;
//
//            return productRepository.findAll(ProductSpecs.matchPrice(min, max), pageable);
//        } else if (price.equals("15-toi-20-trieu")) {
//            double min = 15000000;
//            double max = 20000000;
//
//            return productRepository.findAll(ProductSpecs.matchPrice(min, max), pageable);
//        } else if (price.equals("tren-20-trieu")) {
//            double min = 20000000;
//
//            return productRepository.findAll(ProductSpecs.minPrice(min), pageable);
//        } else {
//            return productRepository.findAll(pageable);
//        }
//    }

    public Specification<Product> buildPriceSpecification(List<String> price) {
        Specification<Product> combinedSpec = Specification.where(null);
        for (String p : price) {
            double min = 0;
            double max = 0;

            switch (p) {
                case "duoi-10-trieu":
                    min = 0;
                    max = 10000000;
                    break;
                case "10-15-trieu":
                    min = 10000000;
                    max = 15000000;
                    break;
                case "15-20-trieu":
                    min = 15000000;
                    max = 20000000;
                    break;
                case "tren-20-trieu":
                    min = 20000000;
                    max = 200000000;
                    break;
            }

            if (min != 0 && max != 0) {
                Specification<Product> spec = ProductSpecs.matchMultiplePrice(min, max);
                combinedSpec = combinedSpec.or(spec);
            }
        }

        return combinedSpec;
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public void saveProduct(Product newProduct) {
        productRepository.save(newProduct);
    }

    public Product getProductById(long id) {
        return productRepository.findProductById(id);
    }

    public void deleteProductById(long id) {
        productRepository.deleteById(id);
    }

    public void addProductToCart(
            String email,
            long productId,
            HttpSession session,
            long quantity) {
        User user = userService.getUserByEmail(email);
        if (user != null) {
            Cart cart = cartRepository.findByUser(user);

            if (cart == null) {
                Cart newCart = new Cart();
                newCart.setUser(user);
                newCart.setSum(0);
                cart = cartRepository.save(newCart);
                user.setCart(cart); // Đồng bộ quan hệ ngược
                userRepository.save(user); // Lưu User để cập nhật
            }

            Optional<Product> productOptional = productRepository.findById(productId);
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                CartDetail existCartDetail = cartDetailRepository.findByCartAndProduct(cart, product);

                if (existCartDetail == null) {
                    CartDetail cartDetail = new CartDetail();
                    cartDetail.setProduct(product);
                    cartDetail.setCart(cart);
                    cartDetail.setQuantity(quantity);
                    cartDetail.setPrice(product.getPrice());
                    cartDetailRepository.save(cartDetail);

                    int sum = cart.getSum() + 1;
                    cart.setSum(sum);
                    cartRepository.save(cart);
                    session.setAttribute("sum", sum);
                } else {
                    existCartDetail.setQuantity(existCartDetail.getQuantity() + quantity);
                    cartDetailRepository.save(existCartDetail);
                }
            }
        }
    }

    public Cart findCartByUser(User user) {
        return cartRepository.findByUser(user);
    }

    public void deleteCartDetail(long id, HttpSession session) {
        Optional<CartDetail> cartDetailOptional = cartDetailRepository.findById(id);
        if (cartDetailOptional.isPresent()) {
            CartDetail cartDetail = cartDetailOptional.get();
            Cart currentCart = cartRepository.findById(cartDetail.getCart().getId())
                    .orElseThrow(() -> new RuntimeException("Cart not found"));

            cartDetailRepository.deleteById(id);

            User user = currentCart.getUser();
            if (currentCart.getSum() > 1) {
                int sum = currentCart.getSum() - 1;
                currentCart.setSum(sum);
                session.setAttribute("sum", sum);
                cartRepository.save(currentCart);
            } else {
                if (user != null) {
                    user.setCart(null); // Ngắt liên kết
                    userRepository.save(user); // Lưu User để cập nhật
                }
                cartRepository.delete(currentCart); // Xóa Cart
                session.setAttribute("sum", 0);
            }
        }
    }

    public void updateCartBeforeCheckout(List<CartDetail> cartDetails) {
        for (CartDetail cartDetail : cartDetails) {
            Optional<CartDetail> cartDetailOptional = cartDetailRepository.findById(cartDetail.getId());
            if (cartDetailOptional.isPresent()) {
                CartDetail existingCartDetail = cartDetailOptional.get();
                existingCartDetail.setQuantity(cartDetail.getQuantity());
                cartDetailRepository.save(existingCartDetail);
            }
        }
    }

    public Page<Product> searchProductsByName(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return productRepository.findAll(pageable);
        }
        
        return productRepository.findAll(ProductSpecs.nameLike(keyword), pageable);
    }
}
