package br.com.atividade.barbearia.Model;

import br.com.atividade.barbearia.Enum.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name= "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name ="id", nullable = false, unique = true, updatable = false)
    private UUID id;

    @NotBlank(message = "The username must be declared")
    @Size(min = 3, max = 100)
    @Column(name = "username",nullable = false, unique = true, length = 100)
    private String username;

    @NotBlank(message ="The email must be declared")
    @Email
    @Column(name = "email",nullable = false, unique = true, length = 250)
    private String email;

    @NotBlank(message = "The Password must be declared")
    @Column(name = "password", nullable = false, length = 100)
    @JsonIgnore
    private String password;

    @NotNull(message = "The role must be declared")
    @Enumerated(EnumType.STRING)
    @Column(name= "role", nullable = false, length = 10)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Appointment> appointments;

    @CreationTimestamp
    @Column(name = "created_Time",updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_Time")
    private LocalDateTime updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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

    public String getTypeCountDisplay(){
        return this.role == Role.OWNER  ? "Proprietário" : "Cliente";
    }



}
