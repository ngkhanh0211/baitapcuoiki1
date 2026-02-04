package com.phnm.laptopshop.repository;

import com.phnm.laptopshop.domain.Cart;
import com.phnm.laptopshop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
}
