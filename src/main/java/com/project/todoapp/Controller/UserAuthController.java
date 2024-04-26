package com.project.todoapp.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.todoapp.Models.User;
import com.project.todoapp.Service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserAuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password,HttpServletRequest request) {
        User user = userService.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("isLoggedIn", true);
            // Redirect to /projects if login is successful
            return "redirect:/projects";
        } else {
            return "redirect:/login";
        }
    }
    @GetMapping("/login") 
    public String showLoginPage() {
        return "login"; 
    }
    @GetMapping("/register") 
    public String showRegisterPage() {
        return "register"; 
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password,HttpServletRequest request) {
        User user = new User(username, password);
        User registeredUser = userService.registerUser(user);
        if (registeredUser != null) {
            HttpSession session = request.getSession();
            session.setAttribute("isLoggedIn", true);
            // If registration is successful, redirect to /projects
            return "redirect:/projects";
        } else {
            return "redirect:/register";
        }
    }
    @PostMapping("/logout")
    public String logoutUser(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("isLoggedIn") != null && (boolean) session.getAttribute("isLoggedIn")){
            session.invalidate();
        }
        return "redirect:/login";

    }
    
    
}

