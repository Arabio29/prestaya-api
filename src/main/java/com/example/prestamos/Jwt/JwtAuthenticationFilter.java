package com.example.prestamos.Jwt;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
//Esto define una clase pública llamada JwtAuthenticationFilter que hereda de OncePerRequestFilter. OncePerRequestFilter es un filtro de Spring
// Security que se ejecuta solo una vez por solicitud.



    private final JwtService jwtService;
//Este campo final privado contiene una referencia al bean JwtService inyectado a través del constructor (gracias a @RequiredArgsConstructor).
// Se utiliza para tareas de procesamiento JWT como extraer el nombre de usuario y validar tokens.

    private final UserDetailsService userDetailsService;
//Este campo final privado contiene una referencia al bean UserDetailsService inyectado a través del constructor. Se utiliza para cargar los
// detalles del usuario por nombre de usuario.


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    //Este es el método central del filtro. Se llama para cada solicitud HTTP.

    //Extraccion del token
        final String token = getTokenFromRequest(request);
    //Esta línea llama al método auxiliar getTokenFromRequest para recuperar el token JWT de los encabezados de la solicitud (si está presente).

        final String username;
    //Esta variable almacenará el nombre de usuario extraído del token (si es válido).

    //Omitir si no hay Token:
        if (token==null)
        {
            filterChain.doFilter(request, response);
            return;
        }
    //Si no hay token en la solicitud, el filtro permite que la solicitud continúe sin procesamiento adicional llamando a
        // filterChain.doFilter(request, response).


    //Extracción del Nombre de Usuario del Token:
        username=jwtService.getUsernameFromToken(token);
    // Esta línea usa el jwtService inyectado para extraer el nombre de usuario del token proporcionado.


    //Lógica de Autenticación:
        if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
    // Esto verifica si se extrajo un nombre de usuario y no hay una autenticación actual en el contexto de seguridad.
        {
            UserDetails userDetails=userDetailsService.loadUserByUsername(username);
// inyectado para cargar los detalles del usuario en función del nombre de usuario extraído.
            if (jwtService.isTokenValid(token, userDetails))
   //Esto verifica si el token es válido usando el jwtService y los detalles del usuario cargados.
            {
                UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
//Si el token es válido, se crea un nuevo objeto UsernamePasswordAuthenticationToken con los detalles del usuario cargado y sus autoridades.
//Los detalles se configuran usando WebAuthenticationDetailsSource (probablemente incluyendo la dirección IP y la información de la sesión).
//El token de autenticación creado se establece en el contexto de seguridad usando
// SecurityContextHolder.getContext().setAuthentication(authToken). Esto hace que la información del usuario sea accesible en toda
// la aplicación una vez autenticada.
        }
        
        filterChain.doFilter(request, response);
    }
//Finalmente, la cadena de filtros se continúa usando filterChain.doFilter(request, response) para permitir que la solicitud continúe
// al siguiente filtro o al manejo real de la solicitud.




    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader=request.getHeader(HttpHeaders.AUTHORIZATION);

        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer "))
        {
            return authHeader.substring(7);
        }
        return null;
    }
//Este método auxiliar privado recupera el token JWT del encabezado de autorización de la solicitud.
//Verifica si el encabezado de autorización existe y comienza con "Bearer ".
//Si es válido, extrae el valor del token después de "Bearer ".
//Si no es válido o no hay ningún encabezado, devuelve null.

}

//En resumen, la clase JwtAuthenticationFilter es un filtro de Spring Security responsable de la autenticación basada en JWT.
// Intercepta las solicitudes entrantes, extrae el token JWT del encabezado de autorización (si está presente), valida el token
// usando el JwtService y, si es válido, crea un objeto de autenticación de Spring Security con los detalles del usuario extraídos
// del token. Este objeto de autenticación luego se almacena en el contexto de seguridad, haciendo que la información del usuario sea