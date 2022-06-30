package com.gofore.recruiment.fullstack.controllers;

import com.gofore.recruiment.fullstack.models.HealthCheck;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HealthCheckControllerTest {
    @LocalServerPort
    private int serverPort;

    @Test
    public void getHealth() {
        webClient().get().uri("/api/v1/healthcheck").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(HealthCheck.class)
                .consumeWith(res -> assertThat(res.getResponseBody().status).isEqualTo("OK"));
    }

    private WebTestClient webClient() {
        return WebTestClient.bindToServer().baseUrl("http://localhost:" + serverPort).build();
    }
}
