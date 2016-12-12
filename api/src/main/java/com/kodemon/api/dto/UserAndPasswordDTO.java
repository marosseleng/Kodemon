package com.kodemon.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by mseleng on 12/11/16.
 */
public class UserAndPasswordDTO {
    @JsonProperty(required = true)
    private UserDTO user;
    @JsonProperty(required = true)
    private String password;

    public UserDTO getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
