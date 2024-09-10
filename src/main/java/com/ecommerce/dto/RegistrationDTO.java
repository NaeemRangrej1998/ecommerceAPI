package com.ecommerce.dto;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;
}
