package com.login.api.model;

import java.util.Collection;
import java.util.List;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.login.api.enums.UserRole;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Entity
@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Table(name = "signup")
public class UserModel implements UserDetails {
    public UserModel(String name, String email, String password, UserRole role, String cpf) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.cpf = cpf;
        this.role = role;
    }

    @Id()
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank
    @Column(name = "name",length = 100, nullable = false, updatable = false, unique = true)
    private String name;

    @Email
    @NotBlank
    @Column(name ="email", length = 50, nullable = false, updatable = false, unique = true)
    private String email;

    @Nullable
    @Column(name ="password", nullable = false, updatable = false)
    private String password;

    @CPF()
    @Column(name ="cpf", length = 11, nullable = false, updatable = false, unique = true)
    private String cpf;

    @Enumerated(EnumType.STRING)
    @Nullable
    @Column(name = "role")
    private UserRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN)
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return name;
    }

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
}
