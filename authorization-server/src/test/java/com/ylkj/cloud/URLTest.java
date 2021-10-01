package com.ylkj.cloud;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class URLTest {

    @Test
    public void test01() throws MalformedURLException {
        String path ="http://www.baidu.com/a/b/c?a=1&b=2";
        URL url = new URL(path);
        String path1 = url.getPath();
        System.out.println(path1);
    }
}
