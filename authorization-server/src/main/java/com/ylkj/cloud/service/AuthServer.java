package com.ylkj.cloud.service;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthServer {


    private String authorizationEndpoint;

    private String tokenEndpoint;

}
