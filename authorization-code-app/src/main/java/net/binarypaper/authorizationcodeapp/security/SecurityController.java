package net.binarypaper.authorizationcodeapp.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("security")
@PreAuthorize("isAuthenticated()")
@Slf4j
public class SecurityController {

    private WebClient webClient;

    public SecurityController(
            WebClient webClient,
            @Value("${application.employee-service.url}") String employeeServiceUrl) {
        this.webClient = webClient.mutate().baseUrl(employeeServiceUrl + "/security").build();
        ;
    }

    @GetMapping("jwt")
    public JsonNode getJWT() {
        log.info("getJWT() called");
        try {
            JsonNode jwt = webClient
                    .get()
                    .uri("/jwt")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();
            return jwt;
        } catch (WebClientResponseException ex) {
            log.error(ex.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

}
