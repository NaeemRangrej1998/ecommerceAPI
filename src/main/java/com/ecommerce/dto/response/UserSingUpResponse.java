package com.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSingUpResponse {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;
}
