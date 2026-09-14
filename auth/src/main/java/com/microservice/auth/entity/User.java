package com.microservice.auth.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.annotations.CreationTimestamp;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.microservice.auth.dto.request.CreateUserDto;
import com.microservice.auth.enums.UserEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Users")
@AllArgsConstructor 
@NoArgsConstructor 
@Getter 
@Setter 
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   
    private Long id;
    
    @Column(length = 255, unique = true, nullable = false)
    private String name;

    @Column(length = 100, unique = true, nullable = false)
    private String email;

    @Column(length = 64, unique = true, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserEnum roles;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false) 
    private LocalDateTime createdAt;

    public User(CreateUserDto dto, String password) {
        setName(dto.name());
        setEmail(dto.email());
        setPassword(password);
        setRoles(UserEnum.ROLE_USER);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(this.roles)
            .stream()
            .map(role -> new SimpleGrantedAuthority(role.name()))
        .collect(Collectors.toList());
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
