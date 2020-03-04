package ru.mirea.dimedrol.lab4.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Map;

@Controller
@EnableWebMvc
public class MainController {
    @GetMapping
    public String main(Map<String, Object> model) {
//        Iterable<User> users = userRepo.findAll();
//        model.put("users", users);
        return "static/mainpage";

    }
}
