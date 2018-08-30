package com.effisoft.kata.calculator.domain.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SubtractionService extends OperationService<Integer> {

    public SubtractionService() {
        super("([0-9]*)\\-([0-9]*)");
    }

    @Override
    public Integer compute(Integer val1, Integer val2) throws OperationException {
        Integer result;
        try {
            URL obj = new URL("http://api.mathjs.org/v4/?expr="+val1+"-"+val2);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))){
                    String inputLine = in.readLine();
                    result = Integer.valueOf(inputLine);
                }
            } else {
                throw new OperationException("GET request not worked");
            }
        } catch (IOException e) {
            throw new OperationException(e);
        }
        return result;
    }
}
