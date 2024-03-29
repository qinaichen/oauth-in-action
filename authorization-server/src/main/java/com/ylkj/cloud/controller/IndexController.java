package com.ylkj.cloud.controller;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import com.ylkj.cloud.model.Client;
import com.ylkj.cloud.service.AuthorizationService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * @author Administrator
 */
@Controller
public class IndexController {


    @Autowired
    private AuthorizationService authorizationService;

    private Set<String> requests = new HashSet<>();

    /**
     * 首页
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("clients",this.authorizationService.getClients());
        model.addAttribute("authServer",this.authorizationService.getAuthServer());
        return "index";
    }

    /**
     * 请求授权
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/authorize")
    public String authorize(HttpServletRequest request,Model model){
        String clientId = request.getParameter("client_id");
        if(StringUtils.isEmpty(clientId)){
            model.addAttribute("error","Unknown client");
            return "error";
        }
        List<Client> clients = this.authorizationService.getClients();
        Optional<Client> first = clients.stream().filter(item -> item.getClientId().equals(clientId)).findFirst();
        if(!first.isPresent()){
            model.addAttribute("error","Unknown client");
            return "error";
        }
        String redirectUri = request.getParameter("redirect_uri");
        List<String> redirectUris = first.get().getRedirectUris();
        if(!redirectUris.contains(redirectUri)){
            model.addAttribute("error","Invalid redirect URI");
            return "error";
        }

        String scope = request.getParameter("scope");
        String[] splits = scope.split(" ");
        ArrayList<String> cScopes = Lists.newArrayList(splits);
        Set<String> serverScopes = first.get().getScope();;
        boolean isSub = CollectionUtils.isSubCollection(cScopes, serverScopes);
        if(!isSub){
            try {
                URL url = new URL(redirectUri);
                String ret = UrlBuilder.create().setScheme(url.getProtocol())
                        .setHost(url.getHost())
                        .addPath(url.getPath())
                        .addQuery("error", "invalid_scope").build();
                return "redirect:"+ret;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        String requestId = RandomUtil.randomString(8);
        requests.add(requestId);
        model.addAttribute("client",first.get());
        model.addAttribute("reqId",requestId);
        model.addAttribute("scope",cScopes);
        return "approve";
    }

    @PostMapping("/approve")
    public String approve(HttpServletRequest request,Model model){
        String reqId = request.getParameter("reqId");
        if(!requests.contains(reqId)){
            model.addAttribute("error","No matching authorization request");
            return "error";
        }
        return null;
    }
}
