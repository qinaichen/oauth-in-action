package com.ylkj.cloud;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class AppTest {

    /**
     * 接口平台token地址
     */
    private String tokenUrl= "http://192.168.120.57:19001/oauth/token";

    private String clientId = "a3b4e06d-6a91-4e74-8c2d-78a03a198807";

    private String clientSecret = "5f7d344a-d7e7-4673-9438-3d3cb8328e47";

    public String getToken(){
        HttpURLConnection connection = null;
        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(tokenUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Authorization", "Basic "+new String(Base64.getEncoder().encode(String.format("%s:%s",clientId,clientSecret).getBytes())));
            connection.setDoOutput(true);
            connection.setDoInput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(),"UTF-8");
            writer.write(String.format("grant_type=client_credentials&scope=all"));
            writer.flush();
            writer.close();

            int responseCode = connection.getResponseCode();
            if(responseCode == 200){
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = reader.readLine();
                while (line!=null){
                    buffer.append(line);
                    line = reader.readLine();
                }
                reader.close();
            }else{
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String line = reader.readLine();
                while (line!=null){
                    buffer.append(line);
                    line = reader.readLine();
                }
                reader.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }


    @Test
    public void test01(){
        String token = this.getToken();
        System.out.println(token);
    }
}
