package com.example.prestamos.Jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
//En resumen, la clase JwtService proporciona funcionalidades para generar, extraer información y validar tokens JWT en una aplicación Spring. Utiliza la librería Jwts para el procesamiento JWT y se basa en una clave secreta para la firma y verificación.



    private static final String SECRET_KEY="586E3272357538782F413F4428472B4B6250655368566B597033733676397924";
//Esto declara una variable privada estática final de tipo cadena llamada SECRET_KEY. Almacena la clave secreta utilizada para firmar y verificar JWTs. Importante: Esta clave debe ser una cadena larga y aleatoria que se mantenga confidencial y no se almacene directamente en el código por razones de seguridad. Se recomienda utilizar variables de entorno o un archivo de configuración seguro para almacenar la clave secreta.


    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }
// Este método público toma un objeto UserDetails (que representa a un usuario) como entrada y devuelve una cadena de token JWT.

    //Generando Token JWT:
    private String getToken(Map<String,Object> extraClaims, UserDetails user) {
        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(user.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
            .signWith(getKey(), SignatureAlgorithm.HS256)
            .compact();
    }
//Este método auxiliar privado toma dos argumentos: un Map de reclamos adicionales (información adicional opcional para incluir en el token) y un objeto UserDetails. Construye el token JWT usando la librería Jwts:
//Establece cualquier reclamo adicional proporcionado en el token.
//Establece el sujeto del token como el nombre de usuario.
//Establece la hora de emisión a la marca de tiempo actual.
//Establece la hora de vencimiento 24 horas después de la hora de emisión (se puede ajustar).
//Firma el token con la clave secreta usando el algoritmo HS256.
//Comprime el objeto JWT en una cadena.



//Generación de la Clave:
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
//Este método privado recupera el objeto clave utilizado para firmar y verificar JWTs.
//Decodifica la cadena de clave secreta codificada en base64.
//Convierte los bytes decodificados en un objeto clave usando el método Keys.hmacShaKeyFor.


//Nombre de Usuario a partir del Token:
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }
//Este método público toma una cadena de token JWT como entrada y devuelve el nombre de usuario extraído del reclamo de sujeto del token.
//getClaim(String token, Function<Claims,T> claimsResolver): Este método auxiliar privado se utiliza para extraer varios reclamos del token JWT. Toma la cadena del token y una función que opera en el objeto Claims.
//Analiza el token usando la clave secreta.
//Extrae el cuerpo (objeto Claims) del token analizado.
//Aplica la función proporcionada (en este caso, recupera el reclamo del sujeto que contiene el nombre de usuario) al objeto Claims y devuelve el valor extraído.



//Validación del Token:
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username=getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }
//Este método público toma una cadena de token JWT y un objeto UserDetails como entrada y devuelve verdadero si el token es válido, falso en caso contrario.
//Extrae el nombre de usuario del token.
//Comprueba si el nombre de usuario extraído coincide con el nombre de usuario de los detalles del usuario proporcionado.
//Llama a isTokenExpired para verificar que el token no haya expirado.
//Devuelve verdadero si tanto el nombre de usuario coincide como el token no está vencido



//Metodos Auxiliares
    private Claims getAllClaims(String token)
    {
        return Jwts
            .parserBuilder()
            .setSigningKey(getKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
//Este método auxiliar privado analiza el token JWT y recupera el objeto Claims que contiene toda la información codificada dentro del token.


//para extraer la fecha de vencimiento de los reclamos del token
    public <T> T getClaim(String token, Function<Claims,T> claimsResolver)
    {
        final Claims claims=getAllClaims(token);
        return claimsResolver.apply(claims);
    }


    private Date getExpiration(String token)
    {
        return getClaim(token, Claims::getExpiration);
    }



    private boolean isTokenExpired(String token)
    {
        return getExpiration(token).before(new Date());
    }
 //Este método auxiliar privado compara la fecha de vencimiento del token con la fecha y hora actuales. Devuelve verdadero si el token está vencido, falso en caso contrario.
}
