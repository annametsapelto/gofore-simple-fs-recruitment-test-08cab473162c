package com.gofore.recruiment.fullstack.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Question {
    public final Owner owner;

    public final String title;

    public final String link;

    public final boolean isAnswered;

    @JsonCreator
    public Question(
            Owner owner,
            String title,
            String link,
            @JsonProperty("is_answered") boolean isAnswered
    ) {
        this.owner = owner;
        this.title = title;
        this.link = link;
        this.isAnswered = isAnswered;
    }
}
