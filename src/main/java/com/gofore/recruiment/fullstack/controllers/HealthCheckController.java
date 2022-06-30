package com.gofore.recruiment.fullstack.controllers;

import com.gofore.recruiment.fullstack.models.HealthCheck;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/healthcheck")
public class HealthCheckController {
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public HealthCheck getHealth() {
        return new HealthCheck("OK");
    }
}
