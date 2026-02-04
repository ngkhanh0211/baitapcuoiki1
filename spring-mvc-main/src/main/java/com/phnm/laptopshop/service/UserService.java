package com.phnm.laptopshop.service;

import com.phnm.laptopshop.domain.Role;
import com.phnm.laptopshop.domain.User;
import com.phnm.laptopshop.domain.dto.RegisterDTO;
import com.phnm.laptopshop.repository.OrderRepository;
import com.phnm.laptopshop.repository.ProductRepository;
import com.phnm.laptopshop.repository.RoleRepository;
import com.phnm.laptopshop.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final RoleRepository roleRepository;

    public UserService(
            UserRepository userRepository,
            ProductRepository productRepository,
            OrderRepository orderRepository,
            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.roleRepository = roleRepository;
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User saveUser(User newUser) {
        User savedUser = userRepository.save(newUser);
        System.out.println(savedUser);
        return savedUser;
    }

    public User getUserById(long id) {
        return userRepository.findUserById(id);
    }

    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    public User registerDTOtoUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setFullName(registerDTO.getFirstName() + " " + registerDTO.getLastName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        return user;
    }

    public boolean isEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public long countUsers() {
        return userRepository.count();
    }

    public long countProducts() {
        return productRepository.count();
    }

    public long countOrders() {
        return orderRepository.count();
    }
}
