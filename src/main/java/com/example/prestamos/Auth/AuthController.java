package com.example.prestamos.Auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
// Este campo final privado contiene una referencia al bean AuthService inyectado a través del constructor. Este servicio contiene la
// lógica de negocio para el inicio de sesión y el registro.
    
    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(authService.login(request));
    }
//Este método público está mapeado a la ruta POST "/auth/login".
//Toma un objeto LoginRequest como argumento del cuerpo de la solicitud HTTP (indicado por @RequestBody). Este objeto contiene el nombre
// de usuario y la contraseña del usuario.
//Utiliza el servicio inyectado authService para realizar el proceso de inicio de sesión. Le pasa el objeto LoginRequest recibido como
// argumento.
//El método authService.login valida las credenciales del usuario y devuelve un objeto AuthResponse que contiene el token JWT generado
// (si el inicio de sesión es exitoso).
//Envuelve el objeto AuthResponse devuelto por el servicio en un objeto ResponseEntity y lo devuelve con un código de estado HTTP 200 (OK).




    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request)
    {
        return ResponseEntity.ok(authService.register(request));
    }
//Este método público está mapeado a la ruta POST "/auth/register".
//Toma un objeto RegisterRequest como argumento del cuerpo de la solicitud HTTP (indicado por @RequestBody). Este objeto contiene los detalles
// del nuevo usuario (nombre de usuario, contraseña, nombre, apellido, país y rol).
//Utiliza el servicio inyectado authService para realizar el proceso de registro de usuarios. Le pasa el objeto RegisterRequest recibido como
// argumento.
//El método authService.register crea un nuevo usuario en la base de datos y devuelve un objeto AuthResponse que contiene el token JWT generado.
//Envuelve el objeto AuthResponse devuelto por el servicio en un objeto ResponseEntity y lo devuelve con un código de estado HTTP 200 (OK).

}
//La clase AuthController actúa como un punto de entrada para las solicitudes HTTP relacionadas con la autenticación y el registro de usuarios.
// Mapea las rutas "/auth/login" y "/auth/register" a los métodos login y register respectivamente. Estos métodos delegan la lógica de negocio
// real al servicio inyectado AuthService y devuelven las respuestas en formato JSON.