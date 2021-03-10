package com.ylkj.cloud.controller;

import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class AuthController {

    private Map<String,String> authServer = ImmutableMap.of("authorizationEndpoint","http://localhost:9001/authorize","tokenEndpoint","http://localhost:9001/token");


    private Map<String,String> clients = ImmutableMap.of("client_id","oauth-client-1",
            "client_secret","oauth-client-secret-1",
            "redirect_uris","http://localhost:9000/callback","scope","foo bar");

    /**
     * 首页信息介绍
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("clients",clients);
        model.addAttribute("authServer",authServer);
        return "index";
    }


    /**
     * 授权
     * @return
     */
    @GetMapping("/authorize")
    public String authorize(HttpServletRequest request, HttpServletResponse response){
        String clientId = request.getParameter("client_id");
        String redrectUri = request.getParameter("redirect_uri");
        if(StringUtils.isEmpty(clientId)){
            request.setAttribute("error","Unknown client");
            return "error";
        }else if(!clients.get("client_id").equals(clientId)){
            request.setAttribute("error","Unknown client");
            return "error";
        }else if(!clients.get("redirect_uris").equals(redrectUri)){
            request.setAttribute("error","Invalid redirect URI");
            return "error";
        }

        return null;
    }
}
