package com.example.securitydemo.securitydemo.controller;

import com.example.securitydemo.securitydemo.dto.UserDto;
import com.example.securitydemo.securitydemo.entity.Usuario;
import com.example.securitydemo.securitydemo.repositories.UsuarioRepository;
import com.example.securitydemo.securitydemo.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Log4j2
public class RegisterController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registro")
    public String mostrarFormularioDeRegistro(Model model)
    {
        UserDto userDto = new UserDto();

        model.addAttribute("usuario",userDto);
        return "formularioRegistro";
    }

    /**
     * Metodo que registra un nuevo usuario en el sistema, utilizando un objeto UserDto
     *
     * @param userDto   Objeto UserDto con la información del nuevo usuario a registrar
     * @param result    Objeto BindingResult que contiene los errores de validación del formulario
     * @param model     Objeto Model para enviar datos a la vista
     * @return          Cadena con la ruta de redirección después de registrar el usuario
     */
    @PostMapping("/registrarusuario")
    public String registarUsuario(@Valid @ModelAttribute("usuario") UserDto userDto, BindingResult result, Model model)
    {
        // Verifica si ya existe un usuario con el mismo nombre de usuario
        Usuario usuarioExistente = usuarioRepository.findByUsername(userDto.getUsername());
        // Se busca si el usuario ya está registrado en la base de datos
        Usuario usuarioExistente = usuarioRepository.findByUsername(userDto.getUsername());

        // Si el usuario ya existe, se rechaza el valor del campo 'username' del formulario
        // y se agrega un mensaje de error personalizado a 'result' para mostrar en la vista
        if(usuarioExistente != null && usuarioExistente.getUsername() != null && !usuarioExistente.getUsername().isEmpty()){
            result.rejectValue("username", null,
                    "There is already an account registered with the same username");
        }

        // Si hay errores de validación, se vuelve a la vista de registro para mostrar los errores
        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }

        // Se guarda el usuario en la base de datos
        usuarioService.guardarUsuarioDTO(userDto);

        // Se redirige al usuario a la página principal después de registrar el usuario
        return "redirect:/";
    }






}
