package com.repairharnees.application.usecases;

import com.repairharnees.application.dto.LoginRequest;
import com.repairharnees.application.dto.LoginResponse;
import com.repairharnees.application.ports.IUnitOfWork;
import com.repairharnees.domain.entities.Usuario;
import com.repairharnees.infrastructure.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoginUseCase {

    private final IUnitOfWork unitOfWork;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginUseCase(IUnitOfWork unitOfWork, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.unitOfWork = unitOfWork;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse ejecutar(LoginRequest request) {
        Usuario usuario = unitOfWork.usuarios().findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Credenciales invalidas"));
        if (!usuario.isActivo() || !passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new IllegalArgumentException("Credenciales invalidas");
        }
        String token = jwtService.generateToken(usuario.getId(), usuario.getUsername(), usuario.getRol().name());
        return LoginResponse.builder()
                .token(token)
                .userId(usuario.getId())
                .username(usuario.getUsername())
                .rol(usuario.getRol().name())
                .build();
    }
}
