package com.repairharnees.domain.repositories;

import com.repairharnees.domain.entities.Usuario;

import java.util.Optional;
import java.util.UUID;

public interface IUsuarioRepository {
    Usuario save(Usuario usuario);
    Optional<Usuario> findById(UUID id);
    Optional<Usuario> findByUsername(String username);
    java.util.List<Usuario> findAll();
    void deleteById(UUID id);
    boolean existsByUsername(String username);
}
