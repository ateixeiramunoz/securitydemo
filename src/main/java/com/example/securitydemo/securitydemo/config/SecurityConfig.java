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

            // Configuración del formulario de inicio de sesión
            http.formLogin(form -> form
                    // Establece la página de inicio de sesión y permite que cualquier persona la visite sin necesidad de autenticación
                    .loginPage("/login").permitAll()
                    // Establece la ruta para procesar el formulario de inicio de sesión cuando se envía
                    .loginProcessingUrl("/procesarLogin")
                    // Establece la ruta a la que se redirige al usuario después de iniciar sesión correctamente
                    .defaultSuccessUrl("/")
            );

            // Configuración del cierre de sesión
            http.logout(logout -> logout
                    // Establece la ruta para procesar la petición de cierre de sesión
                    //AntPathRequestMatcher es una clase de Spring Framework que se utiliza para mapear patrones de URL y
                    // determinar si una URL coincide con un patrón determinado.
                    //En este caso, new AntPathRequestMatcher("/logout") crea un objeto AntPathRequestMatcher que se
                    // utiliza para mapear la URL "/logout". Esto se utiliza en la configuración de la sesión para determinar
                    // la ruta de la petición de cierre de sesión. En otras palabras, esta línea de código le dice a Spring
                    // que cuando reciba una solicitud para "/logout", debe procesarla como una solicitud de cierre de sesión.
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    // Permite que cualquier persona cierre sesión
                    .permitAll()
            );

            /**
             * El método "build()" de HttpSecurityBuilder es el último método que se llama para
             * construir el objeto HttpSecurity después de haber configurado todas las opciones de seguridad
             * necesarias en la aplicación. Devuelve un objeto HttpSecurity configurado y listo para ser utilizado
             * en la aplicación.
             *
             * Este método recoge todas las configuraciones realizadas anteriormente en la instancia 'http' de la clase
             * WebSecurityConfigurerAdapter y las combina para construir un objeto HttpSecurity completo y coherente
             * que se utiliza para establecer la seguridad en la aplicación.
             *
             * Una vez que se ha llamado al método "build()", se considera que la configuración de seguridad ha terminado
             * y el objeto HttpSecurity generado se utilizará para proteger las rutas y recursos de la aplicación.
             *
             * @return Un objeto HttpSecurity completamente configurado y listo para ser utilizado en la aplicación.
             */
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
