package com.ylkj.cloud.service;


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ylkj.cloud.model.Client;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorizationService {




    private List<Client> clients = Lists.newArrayList();

    private AuthServer authServer = new AuthServer();


    public AuthorizationService(){
        Client client = new Client().setClientId("oauth-client-1")
                .setClientSecret("oauth-client-secret-1")
                .setRedirectUris(Lists.newArrayList("http://localhost:9000/callback"))
                .setScope(Sets.newHashSet("foo", "bar"));
        clients.add(client);
        authServer.setAuthorizationEndpoint("http://localhost:9001/authorize")
                .setTokenEndpoint("http://localhost:9001/token");
    }

    public  Client getClient(String clientId){
        Optional<Client> first = this.clients.stream().filter(item -> item.getClientId().equals(clientId)).findFirst();
        return first.isPresent()?first.get():null;
    }


    public List<Client> getClients() {
        return clients;
    }

    public AuthServer getAuthServer() {
        return authServer;
    }
}
