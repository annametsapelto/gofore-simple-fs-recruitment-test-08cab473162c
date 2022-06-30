package com.gofore.recruiment.fullstack.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Owner {
    @JsonProperty("user_id")
    public final String userId;

    @JsonProperty("display_name")
    public final String displayName;

    public final String location;
    
    @JsonProperty("profile_image")
    public final String avatar;

    @JsonCreator
    public Owner(
            @JsonProperty ("profile_image") String avatar,
            @JsonProperty("user_id") String userId,
            @JsonProperty("display_name") String displayName,
            String location
    ) {
        this.userId = userId;
        this.displayName = displayName;
        this.location = location;
        this.avatar = avatar;
    }
}
