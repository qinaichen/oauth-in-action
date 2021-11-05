package com.ylkj.cloud.model;


import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Set;

/**
 * @author Administrator
 */
@Data
@Accessors(chain = true)
public class Client {
    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 客户端凭证
     */
    private String clientSecret;
    /**
     * 授权节点
     */
    private List<String> redirectUris;
    /**
     * 授权范围
     */
    private Set<String> scope;

    private String clientName;

    private String clientUri;

    private String logoUri;
}
