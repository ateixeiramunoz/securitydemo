package com.example.securitydemo.securitydemo.repositories;

import com.example.securitydemo.securitydemo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface User repository.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByEmail(String email);

    Usuario findByUsername(String username);


}