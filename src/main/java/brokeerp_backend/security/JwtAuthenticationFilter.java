package brokeerp_backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        System.out.println(">>> JWT FILTER: " + request.getMethod() + " " + request.getRequestURI());
        if (authHeader != null) {
            String preview = authHeader.length() > 40
                    ? authHeader.substring(0, 40) + "..."
                    : authHeader;
            System.out.println(">>> AUTH HEADER (" + authHeader.length() + " chars): " + preview);
        } else {
            System.out.println(">>> AUTH HEADER: NULL");
        }

        String email = null;
        String jwt = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7).trim();
            try {
                email = jwtUtil.extraerEmail(jwt);
                System.out.println(">>> EMAIL EXTRAIDO DEL TOKEN: " + email);
            } catch (Exception e) {
                System.out.println(
                        ">>> ERROR AL EXTRAER EMAIL: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            }
        } else {
            System.out.println(">>> HEADER NO EMPIEZA CON 'Bearer '");
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                System.out.println(">>> USUARIO CARGADO: " + userDetails.getUsername() + " | Roles: "
                        + userDetails.getAuthorities());

                if (jwtUtil.esTokenValido(jwt, userDetails.getUsername())) {
                    System.out.println(">>> TOKEN VALIDO - Autenticando");
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    System.out.println(">>> TOKEN INVALIDO - No autenticado");
                }
            } catch (Exception e) {
                System.out.println(">>> ERROR AL CARGAR USUARIO: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}