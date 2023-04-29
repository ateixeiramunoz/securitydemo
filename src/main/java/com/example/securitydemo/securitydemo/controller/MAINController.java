package com.example.securitydemo.securitydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MAINController {

    @GetMapping("/private")
    public String showHomePrivado (Model model) {
        model.addAttribute("titulo", "Hola que tal");
        return "home-privado";
    }

    @GetMapping("/public")
    public String showHomePublico (Model model) {
        model.addAttribute("titulo", "Hola que tal");
        return "home-publico";
    }

    @GetMapping(value ={"","/"})
    public String showHome (Model model) {
        model.addAttribute("titulo", "Hola que tal");
        return "home";
    }



}
