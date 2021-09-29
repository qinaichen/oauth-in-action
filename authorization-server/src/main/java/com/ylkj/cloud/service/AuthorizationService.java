package com.ylkj.cloud.service;


import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorizationService {




    private List<JSONObject> clients = Lists.newArrayList();

    private JSONObject authServer = new JSONObject();


    public AuthorizationService(){
        JSONObject client = new JSONObject();
        client.put("client_id", "oauth-client-1");
        client.put("client_secret","oauth-client-secret-1");
        client.put("redirect_uris", Lists.newArrayList("http://localhost:9000/callback"));
        client.put("scope", "foo bar");
        clients.add(client);
        authServer.put("authorizationEndpoint", "http://localhost:9001/authorize");
        authServer.put("tokenEndpoint", "http://localhost:9001/token");
    }

    public  JSONObject getClient(String clientId){
        Optional<JSONObject> first = this.clients.stream().filter(item -> item.getString("client_id").equals(clientId)).findFirst();
        return first.isPresent()?first.get():null;
    }


    public List<JSONObject> getClients() {
        return clients;
    }

    public JSONObject getAuthServer() {
        return authServer;
    }
}
