package com.example.springboot.controller;

import com.example.springboot.model.User;
import com.example.springboot.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String findAll(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("/admin/create")
    public String createUserForm(User user){
        return "user-create";
    }

    @PostMapping("/admin/create")
    public String createUser(User user){
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("admin/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model){
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user-update";
    }

    @PostMapping("/admin/update")
    public String updateUser(User user){
        userService.updateUser(user);
        return "redirect:/admin";
    }


/*    @GetMapping("/user")
    public String userPageForm(User user){
        return "user";
    }*/

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
