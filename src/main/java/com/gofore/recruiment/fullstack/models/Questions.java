package com.gofore.recruiment.fullstack.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Questions {
    @JsonProperty("items")
    public final List<Question> questions;

    @JsonCreator
    public Questions(List<Question> items) {
        questions = items;
    }
}
