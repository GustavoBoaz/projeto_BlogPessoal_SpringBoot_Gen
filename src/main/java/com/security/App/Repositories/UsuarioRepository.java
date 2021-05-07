package com.security.App.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.App.Models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByUsuario(String userName);

}
