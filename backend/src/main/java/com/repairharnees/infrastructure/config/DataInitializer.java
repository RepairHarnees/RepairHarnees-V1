package com.repairharnees.infrastructure.config;

import com.repairharnees.domain.entities.Usuario;
import com.repairharnees.domain.repositories.IUsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsers(IUsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (usuarioRepository.findByUsername("admin").isEmpty()) {
                usuarioRepository.save(Usuario.builder()
                        .username("admin").password(passwordEncoder.encode("admin123"))
                        .nombre("Administrador").rol(Usuario.Rol.ADMINISTRADOR).activo(true).build());
            }
            if (usuarioRepository.findByUsername("recepcion").isEmpty()) {
                usuarioRepository.save(Usuario.builder()
                        .username("recepcion").password(passwordEncoder.encode("recepcion123"))
                        .nombre("Recepcionista").rol(Usuario.Rol.RECEPCIONISTA).activo(true).build());
            }
            if (usuarioRepository.findByUsername("reparador").isEmpty()) {
                usuarioRepository.save(Usuario.builder()
                        .username("reparador").password(passwordEncoder.encode("reparador123"))
                        .nombre("Reparador").rol(Usuario.Rol.REPARADOR).activo(true).build());
            }
        };
    }
}
