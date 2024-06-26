package com.example.prestamos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.prestamos.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;
//Este repositorio se utiliza para acceder a los datos del usuario en la base de datos.

    @Bean //Esta anotación se utiliza para definir métodos que crean beans de Spring. Estos beans son administrados por Spring y se
    // pueden inyectar en otras clases.
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
    {
        return config.getAuthenticationManager();
    }
//Este método crea un bean de tipo AuthenticationManager.
//El AuthenticationManager es responsable de autenticar a los usuarios.
//El método utiliza el parámetro config de tipo AuthenticationConfiguration para obtener el AuthenticationManager configurado por Spring Security.



// es responsable de verificar las credenciales del usuario (típicamente el nombre de usuario y la contraseña).
    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider authenticationProvider= new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
//El método crea una instancia de DaoAuthenticationProvider.
//Configura el DaoAuthenticationProvider para usar el UserDetailsService y el PasswordEncoder creados por otros métodos de esta clase
// (veremos esos métodos a continuación).


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//El PasswordEncoder se utiliza para codificar las contraseñas de los usuarios de forma segura antes de almacenarlas en la base de datos.
//El método devuelve una instancia de BCryptPasswordEncoder, que es un algoritmo popular para codificar contraseñas.


    @Bean
    public UserDetailsService userDetailService() {
        return username -> userRepository.findByUsername(username)
        .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }
//El UserDetailsService es responsable de cargar información de usuario específica por nombre de usuario.
//El método implementa una expresión lambda que toma un nombre de usuario como argumento.
//Busca un usuario en el repositorio utilizando el nombre de usuario proporcionado.
//Si se encuentra el usuario, lo devuelve como un objeto UserDetails.
//Si no se encuentra el usuario, lanza una excepción UsernameNotFoundException con un mensaje informativo.
}

//La clase ApplicationConfig es una clase de configuración de Spring que define varios beans importantes para la autenticación de usuarios:
//
//AuthenticationManager: Responsable de autenticar a los usuarios.
//AuthenticationProvider: Responsable de verificar las credenciales del usuario.
//PasswordEncoder: Codifica las contraseñas de los usuarios de forma segura.
//UserDetailsService: Carga información de usuario específica por nombre de usuario.
//Estos beans trabajan en conjunto para proporcionar un mecanismo de autenticación seguro y basado en base de datos para la aplicación
// Spring Security.