package com.rafaelalves.testejava.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/***
 * Classe de teste do webhook;
 */
public class WebhookTest {

    public static void main(String[] args) {
        String url = "http://localhost:8080/webhook"; 

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        String json = "{ \"clienteId\": 1, \"valor\": 100.0, \"deposito\": true }";
        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            String responseBody = response.getBody();
            HttpStatus statusCode = (HttpStatus) response.getStatusCode();

            if (statusCode.is2xxSuccessful()) {
                System.out.println("Sucesso: " + responseBody);
            } else {
                System.out.println("Falha: " + responseBody);
            }
        } catch (Exception e) {
            System.out.println("Erro ao enviar solicitação: " + e.getMessage());
        }
    }
}
