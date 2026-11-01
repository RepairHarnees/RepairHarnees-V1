package com.repairharnees.infrastructure.persistence;

import com.repairharnees.domain.entities.Usuario;
import com.repairharnees.domain.repositories.IUsuarioRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UsuarioRepositoryAdapter implements IUsuarioRepository {

    private final SpringDataUsuarioRepository springRepo;

    public UsuarioRepositoryAdapter(SpringDataUsuarioRepository springRepo) {
        this.springRepo = springRepo;
    }

    @Override
    public Usuario save(Usuario usuario) { return springRepo.save(usuario); }

    @Override
    public Optional<Usuario> findById(UUID id) { return springRepo.findById(id); }

    @Override
    public Optional<Usuario> findByUsername(String username) { return springRepo.findByUsername(username); }

    @Override
    public List<Usuario> findAll() { return springRepo.findAll(); }

    @Override
    public void deleteById(UUID id) { springRepo.deleteById(id); }

    @Override
    public boolean existsByUsername(String username) { return springRepo.existsByUsername(username); }
}
