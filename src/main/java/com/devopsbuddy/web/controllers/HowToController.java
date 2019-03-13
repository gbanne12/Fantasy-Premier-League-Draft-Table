package com.devopsbuddy.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HowToController {

    @GetMapping("how-to")
    public String helpPage(Model model) {
        return "howto";
    }


}
