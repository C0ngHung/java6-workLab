package org.conghung.lab01.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class MyController {
    @RequestMapping("/")
    public String home(Model model) {
        logAuthentication();
        model.addAttribute("message", "@/ => home()");
        return "page";
    }

    @RequestMapping("/poly/url0")
    public String method0(Model model) {
        logAuthentication();
        model.addAttribute("message", "@/poly/url0 => method1()");
        return "page";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/poly/url1")
    public String method1(Model model) {
        logAuthentication();
        model.addAttribute("message", "@/poly/url1 => method1()");
        return "page";
    }
    @PreAuthorize("hasRole('USER')")
    @RequestMapping("/poly/url2")
    public String method2(Model model) {
        logAuthentication();
        model.addAttribute("message", "@/poly/url2 => method2()");
        return "page";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/poly/url3")
    public String method3(Model model) {
        logAuthentication();
        model.addAttribute("message", "@/poly/url3 => method3()");
        return "page";
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @RequestMapping("/poly/url4")
    public String method4(Model model) {
        logAuthentication();
        model.addAttribute("message", "@/poly/url4 => method4()");
        return "page";
    }

    private void logAuthentication() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            log.info("No authentication present");
            return;
        }
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(java.util.stream.Collectors.joining(", "));
        log.info("username: {}, authorities: {}", authentication.getName(), authorities);
    }
}
