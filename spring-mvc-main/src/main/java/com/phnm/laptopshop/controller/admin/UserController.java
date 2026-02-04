package com.phnm.laptopshop.controller.admin;

import com.phnm.laptopshop.domain.User;
import com.phnm.laptopshop.service.UploadService;
import com.phnm.laptopshop.service.UserService;
import com.phnm.laptopshop.validation.CreateGroup;
import com.phnm.laptopshop.validation.UpdateGroup;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    private final UserService userService;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;


    public UserController(
            UserService userService,
            UploadService uploadService,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/admin/user")
    public String getUserPage(
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
        Page<User> usersPage = userService.getAllUsers(pageable);
        List<User> users = usersPage.getContent();
        model.addAttribute("users", users);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", usersPage.getTotalPages());
        return "admin/user/index";
    }

    @GetMapping("/admin/user/{id}")
    public String getDetailUserPage(Model model, @PathVariable long id) {
        User user = userService.getUserById(id);
        model.addAttribute("id", id);
        model.addAttribute("user", user);
        return "admin/user/detail";
    }

    @GetMapping("/admin/user/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping(value = "/admin/user/create")
    public String createUser(
            @ModelAttribute("newUser") @Validated(CreateGroup.class) User newUser,
            BindingResult newUserBindingResult,
            @RequestParam("userAvatar") MultipartFile file
    ) {
        List<FieldError> errors = newUserBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>" + error.getField() + " - " + error.getDefaultMessage());
        }

        if (newUserBindingResult.hasErrors()) {
            return "admin/user/create";
        }

        String fileName = uploadService.handleSaveUploadFile(file, "avatar");
        String hashPassword = passwordEncoder.encode(newUser.getPassword());

        newUser.setAvatar(fileName);
        newUser.setPassword(hashPassword);
        newUser.setRole(userService.getRoleByName(newUser.getRole().getName()));

        userService.saveUser(newUser);
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable long id) {
        User currentUser = userService.getUserById(id);
        model.addAttribute("newUser", currentUser);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String updateUser(
            @ModelAttribute("newUser") @Validated(UpdateGroup.class) User user,
            BindingResult newUserBindingResult,
            @RequestParam("userAvatar") MultipartFile file) {
        List<FieldError> errors = newUserBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>" + error.getField() + " - " + error.getDefaultMessage());
        }

        if (newUserBindingResult.hasErrors()) {
            return "admin/user/update";
        }

        User currentUser = userService.getUserById(user.getId());
        if (currentUser != null) {
            currentUser.setPhone(user.getPhone());
            currentUser.setFullName(user.getFullName());
            currentUser.setAddress(user.getAddress());
            currentUser.setRole(userService.getRoleByName(user.getRole().getName()));

            String fileName = uploadService.handleSaveUploadFile(file, "avatar");

            if (!file.isEmpty()) {
                currentUser.setAvatar(fileName);
            }

            userService.saveUser(currentUser);
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("newUser", new User());
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String deleteUser(@ModelAttribute("newUser") User user) {
        userService.deleteUserById(user.getId());
        return "redirect:/admin/user";
    }

}
