package com.agendexa.entity;

import com.agendexa.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table (name = "users")
@Data
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Define as "permissões/roles" do usuário.
        // O prefixo "ROLE_" é uma convenção do Spring Security.
        if (this.role == UserRole.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getUsername() {
        // Spring Security usará o email como "username" para login.
        return this.email;
    }

    @Override
    public String getPassword() {
        // Retorna a senha (já criptografada) do usuário.
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Para este projeto, as contas não expiram.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // As contas não serão bloqueadas.
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // As credenciais (senha) não expiram.
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Todas as contas estarão habilitadas por padrão.
        return true;
    }
}
