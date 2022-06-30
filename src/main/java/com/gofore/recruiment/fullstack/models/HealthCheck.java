package com.gofore.recruiment.fullstack.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HealthCheck {
    public final String status;

    @JsonCreator
    public HealthCheck(@JsonProperty("status") String status) {
        this.status = status;
    }
}
