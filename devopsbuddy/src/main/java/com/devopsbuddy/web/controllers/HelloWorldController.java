package com.devopsbuddy.web.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorldController {

    @Value("GARYBANNEMAN")
    String variableName;

    @RequestMapping("/")
    public String sayHello(Model model) {
        variableName = "SOMETHING_ELSE";
        model.addAttribute("nameForHTML", variableName);
        return "index";
    }

}
