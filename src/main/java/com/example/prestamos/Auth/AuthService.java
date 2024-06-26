package com.example.prestamos.Auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.prestamos.repositories.UserRepository;
import com.example.prestamos.Jwt.JwtService;
import com.example.prestamos.utils.Role;
import com.example.prestamos.models.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
//Esta clase se encarga de la lógica de negocio relacionada con la autenticación y el registro de usuarios
//La clase AuthService proporciona servicios de autenticación y registro para la aplicación. El método login valida las credenciales
// del usuario y devuelve un token JWT si el inicio de sesión es exitoso. El método register crea un nuevo usuario en la base de datos
// y también devuelve un token JWT. Estos tokens JWT se pueden utilizar para la autorización en otras partes de la aplicación.

    private final UserRepository userRepository;
    //Este repositorio se utiliza para acceder a los datos del usuario en la base de datos.
    private final JwtService jwtService;
    // Este servicio se utiliza para generar y manejar tokens JWT.
    private final PasswordEncoder passwordEncoder;
    //Este codificador se utiliza para codificar las contraseñas de los usuarios de forma segura antes de almacenarlas en la base de datos.
    private final AuthenticationManager authenticationManager;
    // Este gestor de autenticación se utiliza para validar las credenciales del usuario durante el inicio de sesión.

    //Se encarga del procesod e inicio de sesion
    public AuthResponse login(LoginRequest request) {
    //Toma un objeto LoginRequest como argumento, que contiene el nombre de usuario y la contraseña del usuario

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user=userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token=jwtService.getToken(user);
        return AuthResponse.builder()
            .token(token)
            .build();

    }
//Primero, usa el authenticationManager para autenticar al usuario. Esto verifica si el nombre de usuario y la contraseña proporcionados
// son válidos.
//Si la autenticación es exitosa, recupera los detalles del usuario de la base de datos utilizando el nombre de usuario proporcionado en
// la solicitud.
//Luego, utiliza el jwtService para generar un token JWT basado en los detalles del usuario recuperados.
//Finalmente, devuelve un objeto AuthResponse que contiene el token JWT generado.



    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode( request.getPassword()))
            .firstname(request.getFirstname())
            .lastname(request.lastname)
            .country(request.getCountry())
            .role(Role.USER)
            .build();

        userRepository.save(user);

        return AuthResponse.builder()
            .token(jwtService.getToken(user))
            .build();
        
    }
//Este método público se encarga del proceso de registro de usuarios.
//Toma un objeto RegisterRequest como argumento, que contiene los detalles del nuevo usuario (nombre de usuario, contraseña, nombre,
//apellido, país y rol).
//Primero, crea un objeto User utilizando el builder pattern.
//Codifica la contraseña del usuario utilizando el passwordEncoder antes de establecerla en el objeto User.
//Guarda el nuevo usuario en la base de datos utilizando el userRepository.
//Una vez que se guarda el usuario, utiliza el jwtService para generar un token JWT basado en los detalles del usuario creado.
//Finalmente, devuelve un objeto AuthResponse que contiene el token JWT generado.
}
