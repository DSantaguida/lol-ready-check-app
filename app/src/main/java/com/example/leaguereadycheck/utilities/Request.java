package com.example.leaguereadycheck.utilities;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class Request extends AsyncTask<String, Void, String> {

    public String get(String address) {
        try {
            return execute("GET", address).get();
        } catch (ExecutionException | InterruptedException e){
            return e.getMessage();
        }
    }

    public String post(String address) {
        try {
            return execute("POST", address).get();
        } catch (ExecutionException | InterruptedException e){
            return e.getMessage();
        }
    }

    private static String request(String type, String address) {
        String result = "error";
        try {
            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(type);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            result =  content.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected String doInBackground(String... strings) {
        String result = "error";
        try {
            URL url = new URL(strings[1]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(strings[0]);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            result =  content.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
