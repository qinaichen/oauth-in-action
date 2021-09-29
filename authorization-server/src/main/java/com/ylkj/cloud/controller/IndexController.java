package com.ylkj.cloud.controller;

import com.ylkj.cloud.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {


    @Autowired
    private AuthorizationService authorizationService;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("clients",this.authorizationService.getClients());
        model.addAttribute("authServer",this.authorizationService.getAuthServer());
        return "index";
    }
}
