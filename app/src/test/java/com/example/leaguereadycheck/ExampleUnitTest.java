package com.example.leaguereadycheck;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import static org.junit.Assert.*;

public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void riotGet(){
        try {
            URL url = new URL("http://localhost:8080");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            System.out.println(content.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loadProps() throws IOException {
        Properties props = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream("config.properties");
        props.load(in);

        if (props.getProperty("client.address").equals("")){
            System.out.println("Empty");
        }
        else {
            System.out.println("Filled");
        }

    }

    @Test
    public void riotPost(){
        try {
            URL url = new URL("http://localhost:8080/ready-check/accept");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            System.out.println(content.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void jsonParse() throws JSONException {
        String json = "{\"errorCode\":\"RPC_ERROR\",\"httpStatus\":404,\"implementationDetails\":{},\"message\":\"Not attached to a matchmaking queue.\"}";
        JSONObject jsonObject = new JSONObject(json);
        System.out.println(jsonObject.getString("message"));
    }

}