package com.effisoft.kata.calculator.domain.core;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SubtractionService extends OperationService<Integer> {

    public SubtractionService() {
        super("([0-9]*)\\-([0-9]*)");
    }

    @Override
    public Integer compute(Integer val1, Integer val2) throws OperationException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://api.mathjs.org/v4/?expr=" + val1 + "-" + val2))
                .GET()
                .build();
            HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
            return Integer.valueOf(response.body());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new OperationException(e);
        }
    }
}
