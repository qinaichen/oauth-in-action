package com.ylkj.cloud.service;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthServer {

    /**
     * 认证节点
     */
    private String authorizationEndpoint;
    /**
     * token节点
     */
    private String tokenEndpoint;

}
