package com.projectSTS.ecommercerest.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Autowired
    public JwtAuthorizationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. Extraer el encabezado "Authorization" de la petición HTTP
        String header = request.getHeader("Authorization");

        // 2. Verificar que el encabezado no esté vacío y comience por "Bearer "
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7); // Extraer solo el JWT omitiendo "Bearer "

            // 3. Validar la firma y la fecha de expiración del token
            if (jwtUtils.validarToken(token)) {
                String username = jwtUtils.obtenerUsernameDelToken(token);
                List<String> roles = jwtUtils.obtenerRolesDelToken(token);

                // Convertir la lista de roles en autoridades que entiende Spring Security
                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                // 4. Crear el objeto de autenticación con el usuario y sus roles
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);

                // 5. Registrar la autenticación en el contexto global de seguridad de Spring
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // Continuar con la cadena de filtros hacia el Controller
        filterChain.doFilter(request, response);
    }
}