package com.andersenlab.shop.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileDto {
    Long id;
    UserCredentialsDto userCredentialsDto;
    BucketDto bucketDto;
    RoleDto roleDto;
    String fullName;
    String address;
    String email;
    String phone;
}
