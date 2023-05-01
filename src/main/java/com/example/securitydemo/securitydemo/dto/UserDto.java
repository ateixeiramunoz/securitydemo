package com.example.securitydemo.securitydemo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto
{
    private Long id; // Identificador del usuario
    @NotEmpty // Anotación para validar que el campo no esté vacío
    private String username; // Nombre de usuario
    @NotEmpty // Anotación para validar que el campo no esté vacío
    private String firstName; // Primer nombre del usuario
    @NotEmpty // Anotación para validar que el campo no esté vacío
    private String lastName; // Apellido del usuario
    @NotEmpty(message = "Email should not be empty") // Anotación para validar que el campo no esté vacío y mensaje de error personalizado
    @Email // Anotación para validar que el campo sea un correo electrónico válido
    private String email; // Correo electrónico del usuario
    @NotEmpty(message = "Password should not be empty") // Anotación para validar que el campo no esté vacío y mensaje de error personalizado
    private String password; // Contraseña del usuario
}
