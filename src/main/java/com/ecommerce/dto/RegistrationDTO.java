package com.ecommerce.dto;

import lombok.*;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;
}
