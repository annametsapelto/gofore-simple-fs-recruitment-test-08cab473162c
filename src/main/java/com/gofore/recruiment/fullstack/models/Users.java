package com.gofore.recruiment.fullstack.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Users {
    @JsonProperty("items")
    public List<User> users;

    @JsonCreator
    public Users(List<User> items) {
        this.users = items;
    }
}
