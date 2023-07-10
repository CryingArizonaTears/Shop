package com.andersenlab.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCredentialsDto {
    Long id;
    String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;
}
