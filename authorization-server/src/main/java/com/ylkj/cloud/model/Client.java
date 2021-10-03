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

    private String clientId;


    private String clientSecret;

    private List<String> redirectUris;

    private Set<String> scope;

    private String clientName;

    private String clientUri;

    private String logoUri;
}
