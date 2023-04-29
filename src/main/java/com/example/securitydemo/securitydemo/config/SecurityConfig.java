package com.example.securitydemo.securitydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.securitydemo.securitydemo.service.CustomUserDetailsService;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

            http.authorizeHttpRequests( authorize -> authorize

                    //Permitimos todas las visitas a la pagina principal
                    .requestMatchers("","/").permitAll()
                    //Permitimos todas las visitas a la pagina principal
                    .requestMatchers("/registro","/registrarusuario").permitAll()
                    //Permitimos todas las visitas a /public
                    .requestMatchers("/public").permitAll()
                    //Solo permitimos a usuarios registrados visitar "/private"
                    .requestMatchers("/private").authenticated() //Permitimos únicamente las visitas de usuarios registrados a  /private
                    // Todas las request no filtradas hasta ahora, se rechazarán
                    .anyRequest().denyAll()
            );

            http
                    .formLogin(form -> form
                            .loginPage("/login").permitAll() //Permitimos todas las visitas a la página de login
                            .loginProcessingUrl("/procesarLogin") // Establece la ruta de procesamiento del formulario de inicio de sesión
                            .defaultSuccessUrl("/")// Establece la ruta de redirección después de que el usuario inicia sesión correctamente
                    );

            http.
                    logout(logout -> logout
                    // Establece la ruta para procesar la petición de cierre de sesión
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .permitAll()
            );

            return http.build();

        }


    /**
     * Método que crea un bean BCryptPasswordEncoder.
     *
     * @return BCryptPasswordEncoder Bean de codificación de contraseñas con BCrypt.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Método que crea un bean CustomUserDetailsService.
     *
     * @return
     */
    @Bean
    CustomUserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService();
    }

    /**
     * Método que crea un proveedor de autenticación de DAO y lo configura con el
     * userDetailsService y el passwordEncoder que hemos creado como Beans.
     * Primero se crea una instancia de DaoAuthenticationProvider con new DaoAuthenticationProvider().
     * Luego, se configura el servicio que se utilizará para obtener los detalles del usuario
     * mediante setUserDetailsService(userDetailsService), donde userDetailsService
     * es una instancia de la interfaz UserDetailsService que proporciona
     * los detalles del usuario en función del nombre de usuario.
     * Finalmente, se configura el codificador de contraseñas que se utilizará
     * para verificar las contraseñas de los usuarios al momento de la autenticación
     * mediante setPasswordEncoder(passwordEncoder()),
     * donde passwordEncoder()
     * es una función que devuelve una instancia del codificador de contraseñas
     * que se ha definido en la aplicación.
     * El objeto DaoAuthenticationProvider se devuelve para que se pueda utilizar
     * en la configuración de la autenticación en la aplicación.
     *
     * @return DaoAuthenticationProvider Proveedor de autenticación de DAO.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

}
