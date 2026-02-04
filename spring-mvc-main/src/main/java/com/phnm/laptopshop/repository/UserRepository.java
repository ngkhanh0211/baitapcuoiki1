package com.phnm.laptopshop.repository;

import com.phnm.laptopshop.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User save(User newUser);

    void deleteById(long id);

    User findByEmail(String email);

    User findUserById(long id);

    boolean existsByEmail(String email);

    Page<User> findAll(Pageable pageable);
}
