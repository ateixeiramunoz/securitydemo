package com.example.securitydemo.securitydemo.service;

import com.example.securitydemo.securitydemo.dto.UserDto;
import com.example.securitydemo.securitydemo.entity.Usuario;
import com.example.securitydemo.securitydemo.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;



    public UserDto mapToUserDto(Usuario user){
        UserDto userDto = new UserDto();
        String[] str = user.getNombre().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public Usuario guardarUsuarioDTO (UserDto userDto)
    {
        Usuario usuario = new Usuario();
        usuario.setNombre(userDto.getFirstName() + userDto.getLastName());
        usuario.setEmail(userDto.getEmail());
        usuario.setUsername(userDto.getUsername());
        usuario.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return usuarioRepository.save(usuario);
    }

}
