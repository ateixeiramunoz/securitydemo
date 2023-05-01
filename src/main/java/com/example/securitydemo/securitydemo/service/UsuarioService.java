package com.example.securitydemo.securitydemo.service;

import com.example.securitydemo.securitydemo.dto.UserDto;
import com.example.securitydemo.securitydemo.entity.Usuario;
import com.example.securitydemo.securitydemo.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * Servicio que proporciona métodos para manipular objetos Usuario y UserDto en una aplicación Spring Boot con seguridad integrada.
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * Convierte un objeto Usuario en un objeto UserDto.
     *
     * @param user El objeto Usuario a convertir.
     * @return Un objeto UserDto que contiene información del usuario.
     */
    public UserDto mapToUserDto(Usuario user){
        // Crear un nuevo objeto UserDto
        UserDto userDto = new UserDto();

        // Dividir el nombre del usuario en dos partes (primer nombre y apellido)
        String[] str = user.getNombre().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());

        // Devolver el objeto UserDto
        return userDto;
    }

    /**
     * Guarda un objeto UserDto en la base de datos como un nuevo objeto Usuario.
     *
     * @param userDto El objeto UserDto a guardar.
     * @return Un objeto Usuario recién guardado en la base de datos.
     */
    public Usuario guardarUsuarioDTO(UserDto userDto) {
        // Crear un nuevo objeto Usuario
        Usuario usuario = new Usuario();

        // Configurar los campos del objeto Usuario
        usuario.setNombre(userDto.getFirstName() + userDto.getLastName());
        usuario.setEmail(userDto.getEmail());
        usuario.setUsername(userDto.getUsername());

        // Codificar la contraseña del usuario utilizando el algoritmo de hash BCrypt
        usuario.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Guardar el objeto Usuario en la base de datos y devolverlo
        return usuarioRepository.save(usuario);
    }
}
