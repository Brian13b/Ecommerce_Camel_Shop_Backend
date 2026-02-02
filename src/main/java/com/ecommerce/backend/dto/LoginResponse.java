package com.ecommerce.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String token;

    @Builder.Default
    private String tipo = "Bearer";
    
    private Long id;
    private String username;
    private String email;
    private String nombreCompleto;
    private String rol;
}
