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

    @PostMapping("/registrarusuario")
    public String registarUsuario(@Valid @ModelAttribute("usuario") UserDto userDto, BindingResult result, Model model)
    {
        Usuario usuarioExistente = usuarioRepository.findByUsername(userDto.getUsername());
        if(usuarioExistente != null && usuarioExistente.getUsername() != null && !usuarioExistente.getUsername().isEmpty()){
            result.rejectValue("username", null,
                    "There is already an account registered with the same username");
        }
        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }

        usuarioService.guardarUsuarioDTO(userDto);

        return "redirect:/";
    }





}
