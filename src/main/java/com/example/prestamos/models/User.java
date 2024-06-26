package com.example.prestamos.models;

import java.util.Collection;
import java.util.List;

import com.example.prestamos.utils.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User implements UserDetails {
// Esto indica que la clase Usuario implementa la interfaz UserDetails. Esta interfaz define métodos requeridos por Spring Security para administrar la autenticación del usuario.


    @Id
    @GeneratedValue //Esta anotación (posiblemente de JPA) indica que el valor del campo id se generará automáticamente, generalmente por la base de datos (por ejemplo, autoincremento).
    Integer id;
    @Basic
    @Column(nullable = false)
    String username;
    @Column(nullable = false)
    String lastname;
    String firstname;
    String country;
    String password;
    @Enumerated(EnumType.STRING) // Esta anotación (posiblemente de JPA) define cómo se almacena el enum Rol en la base de datos. Aquí, EnumType.STRING especifica que el valor del enum se almacenará como una cadena (por ejemplo, "ADMIN" o "USUARIO").
    Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return List.of(new SimpleGrantedAuthority((role.name())));
    }
 //Este método devuelve una colección de objetos `GrantedAuthority` que representan las autoridades del usuario (permisos o niveles de acceso). Aquí, crea una lista que contiene un `SimpleGrantedAuthority` basado en el nombre del rol del usuario obtenido del enum `rol`.


    @Override
    public boolean isAccountNonExpired() {
       return true;
    }
    @Override
    public boolean isAccountNonLocked() {
       return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

  //Todos estos métodos devuelven `true` por defecto en este ejemplo. Spring Security los usa para verificar el estado de la cuenta del usuario para controles de seguridad adicionales. Puede personalizar su lógica según sus requisitos específicos.
}
