package com.example.springboot.controller;

import com.example.springboot.model.Role;
import com.example.springboot.model.User;
import com.example.springboot.repository.RoleRepository;
import com.example.springboot.service.UserServiceImpl;
import com.sun.org.apache.xpath.internal.operations.Mod;
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
    public String findAll(Model model, Principal principal){
        List<User> users = userService.findAll();
        User currentUser = userService.findUserByFirstName(principal.getName());
        model.addAttribute("users", users);
        model.addAttribute("user", new User());
        model.addAttribute("currentUser", currentUser);
        return "user-list";
    }

/*    @GetMapping("/admin/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model){
        User upUser = userService.findById(id);
        model.addAttribute("user", upUser);
        return "user-list";
    }*/

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

    @PostMapping("/admin/update")
    public String updateUser(User user){
        userService.saveUser(user);
        return "redirect:/admin";
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
