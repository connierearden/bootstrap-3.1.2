package com.example.springboot.controller;

import com.example.springboot.model.Role;
import com.example.springboot.model.User;
import com.example.springboot.repository.RoleRepository;
import com.example.springboot.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private RoleRepository roleRepository;
    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String findAll(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("user", new User());
        return "user-list";
    }

    @PostMapping("/admin/create")
    public String createUser(User user){
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @PostMapping("admin/update/")
    public String updateUser(User user){
        userService.saveUser(user);
        return "user-list";
    }





    @GetMapping("/user")
    public String userPage (Model model, Principal principal) {
        User user = userService.findUserByFirstName(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }


    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
