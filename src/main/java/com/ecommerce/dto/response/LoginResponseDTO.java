package com.ecommerce.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class LoginResponseDTO {
    private String token;

    private long expiresIn;
}
