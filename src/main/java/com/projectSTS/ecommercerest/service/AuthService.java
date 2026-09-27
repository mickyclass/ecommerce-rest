package com.projectSTS.ecommercerest.service;

import com.projectSTS.ecommercerest.dto.auth.AuthResponseDTO;
import com.projectSTS.ecommercerest.dto.auth.LoginRequestDTO;
import com.projectSTS.ecommercerest.dto.auth.RegisterRequestDTO;

public interface AuthService {
    AuthResponseDTO register(RegisterRequestDTO request);
    AuthResponseDTO login(LoginRequestDTO request);
}