package brokeerp_backend.controller;

import brokeerp_backend.dto.LoginRequest;
import brokeerp_backend.dto.LoginResponse;
import brokeerp_backend.entity.Usuario;
import brokeerp_backend.repository.UsuarioRepository;
import brokeerp_backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            Usuario usuario = usuarioRepository.findByEmail(request.getEmail()).orElseThrow();
            String token = jwtUtil.generarToken(usuario.getEmail(), usuario.getRol().getNombreRol());

            return ResponseEntity.ok(new LoginResponse(
                    token,
                    usuario.getEmail(),
                    usuario.getNombreCompleto(),
                    usuario.getRol().getNombreRol()));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Credenciales inválidas: " + e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> yo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(401).body("No autenticado");
        }
        Usuario usuario = usuarioRepository.findByEmail(auth.getName()).orElseThrow();
        return ResponseEntity.ok(usuario);
    }
}