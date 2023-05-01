package com.example.securitydemo.securitydemo.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.securitydemo.securitydemo.entity.Rol;
import com.example.securitydemo.securitydemo.entity.Usuario;
import com.example.securitydemo.securitydemo.repositories.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.List;

/**
 * The type Custom user details service.
 */
public class CustomUserDetailsService implements UserDetailsService {


    /**
     * The Usuario repository.
     */
    @Autowired
    UsuarioRepository usuarioRepository;
    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if(usuario != null )
        {
            //Es un User de la clase principal de SpringSecurity (UserDetails-User)
            return new User(
                    usuario.getUsername(),
                    usuario.getPassword(),
                    mapRolesToAuthorities(usuario.getRoles())
            );
        }
        else
        {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }
    /*La función mapRolesToAuthorities recibe una lista de roles y los mapea a instancias de GrantedAuthority.
    En este caso, se utiliza la clase SimpleGrantedAuthority para representar cada rol.
    La función utiliza el método stream() de la lista para convertirla en un stream de elementos, lo
    que permite aplicar operaciones en paralelo y de manera más eficiente.
    El método map se utiliza para convertir cada elemento del stream (un objeto Rol) en una instancia
    de SimpleGrantedAuthority.
    Para ello, se utiliza la expresión lambda role -> new SimpleGrantedAuthority(role.getName()),
    que crea una nueva instancia de SimpleGrantedAuthority con el nombre del rol.
    Finalmente, se utiliza el método toList() para convertir el stream de elementos
    en una lista de instancias de SimpleGrantedAuthority, que es el tipo de objeto que la función debe devolver.
*/
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(List<Rol> roles) {
        // Mapear los roles a instancias de SimpleGrantedAuthority
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList(); // Convertir el stream en una lista
    }
}
