package com.rafaelalves.testejava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.rafaelalves.testejava.entities.Cliente;
import com.rafaelalves.testejava.exception.DomainException;
import com.rafaelalves.testejava.repository.ClienteRepository;
import com.rafaelalves.testejava.repository.EmpresaRepository;


/***
 * Tentativa de implementar o webhook na aplicação, no entanto não possível corrigir os erros para que 
 * funcione perfeitamente
 */
@RestController
@RequestMapping("/webhook")
public class WebhookController {

    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/")
    public ResponseEntity<String> handleWebhook(@RequestBody WebhookPayload payload) {
        try {
            Cliente cliente = clienteRepository.findById(payload.getClienteId())
                    .orElseThrow(() -> new DomainException("Cliente não encontrado"));

            cliente.depositar(payload.getValor());
            clienteRepository.save(cliente);

            String webhookUrl = "https://webhook.site/75eb04bc-812a-4767-b100-b287e261cced";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            HttpEntity<WebhookPayload> requestEntity = new HttpEntity<>(payload, headers);
            
            ResponseEntity<String> response = restTemplate.exchange(
                    webhookUrl,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            System.out.println("Webhook response: " + response.getBody());

            return ResponseEntity.ok("Webhook processado com sucesso");

        } catch (DomainException e) {
            System.err.println("Erro ao processar o webhook: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao processar o webhook: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado: " + e.getMessage());
        }
    }
}
