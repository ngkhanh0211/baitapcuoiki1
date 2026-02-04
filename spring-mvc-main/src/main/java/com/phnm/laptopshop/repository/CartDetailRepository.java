package com.phnm.laptopshop.repository;

import com.phnm.laptopshop.domain.Cart;
import com.phnm.laptopshop.domain.CartDetail;
import com.phnm.laptopshop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    boolean existsByCartAndProduct(Cart cart, Product product);

    CartDetail findByCartAndProduct(Cart cart, Product product);
}
