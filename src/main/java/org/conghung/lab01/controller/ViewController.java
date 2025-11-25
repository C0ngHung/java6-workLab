package org.conghung.lab01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/axios-demo")
    public String axiosDemo() {
        return "axios-demo";
    }
}
