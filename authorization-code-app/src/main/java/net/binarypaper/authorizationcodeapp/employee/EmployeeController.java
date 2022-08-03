package net.binarypaper.authorizationcodeapp.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("employees")
@PreAuthorize("isAuthenticated()")
@Slf4j
public class EmployeeController {

    private WebClient webClient;

    public EmployeeController(WebClient webClient,
            @Value("${application.employee-service.url}") String employeeServiceUrl) {
        this.webClient = webClient.mutate().baseUrl(employeeServiceUrl + "/employees").build();
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        log.info("getAllEmployees() called");
        try {
            return webClient
                    .get()
                    .uri("/")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<Employee>>() {
                    })
                    .block();
        } catch (WebClientResponseException ex) {
            log.error(ex.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }
}