package brokeerp_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private String tipo = "Bearer";
    private String email;
    private String nombreCompleto;
    private String rol;

    public LoginResponse(String token, String email, String nombreCompleto, String rol) {
        this.token = token;
        this.email = email;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
    }
}