package com.projectSTS.ecommercerest.service.impl;

import com.projectSTS.ecommercerest.dto.auth.AuthResponseDTO;
import com.projectSTS.ecommercerest.dto.auth.LoginRequestDTO;
import com.projectSTS.ecommercerest.dto.auth.RegisterRequestDTO;
import com.projectSTS.ecommercerest.exception.GlobalExceptionHandler.ResourceNotFoundException;
import com.projectSTS.ecommercerest.model.RolEnum;
import com.projectSTS.ecommercerest.model.Usuario;
import com.projectSTS.ecommercerest.repository.UsuarioRepository;
import com.projectSTS.ecommercerest.security.JwtUtils;
import com.projectSTS.ecommercerest.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthServiceImpl(UsuarioRepository usuarioRepository,
                           PasswordEncoder passwordEncoder,
                           JwtUtils jwtUtils) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("El usuario '" + request.getUsername() + "' ya existe.");
        }

        Set<RolEnum> roles = (request.getRoles() == null || request.getRoles().isEmpty())
                ? Set.of(RolEnum.ROLE_USER)
                : request.getRoles();

        Usuario usuario = new Usuario(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()), // Encriptar contraseña con BCrypt
                roles
        );

        usuarioRepository.save(usuario);

        List<String> rolesString = usuario.getRoles().stream()
                .map(Enum::name)
                .collect(Collectors.toList());

        String token = jwtUtils.generarToken(usuario.getUsername(), rolesString);

        return new AuthResponseDTO(token, usuario.getUsername(), rolesString);
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado."));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new IllegalArgumentException("Contraseña incorrecta.");
        }

        List<String> rolesString = usuario.getRoles().stream()
                .map(Enum::name)
                .collect(Collectors.toList());

        String token = jwtUtils.generarToken(usuario.getUsername(), rolesString);

        return new AuthResponseDTO(token, usuario.getUsername(), rolesString);
    }
}