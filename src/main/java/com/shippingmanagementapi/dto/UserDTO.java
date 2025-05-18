package com.shippingmanagementapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shippingmanagementapi.model.User;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordHash;

    private String fullName;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // UserDetails'den gelen alanlar
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private List<SimpleGrantedAuthority> authorities;

    // Opsiyonel: User entity'sinden DTO'ya dönüşüm için builder metodu
    public static UserDTO fromUser(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setPhone(user.getPhone());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());

        // UserDetails'den gelen alanları set et
        dto.setAccountNonExpired(user.isAccountNonExpired());
        dto.setAccountNonLocked(user.isAccountNonLocked());
        dto.setCredentialsNonExpired(user.isCredentialsNonExpired());
        dto.setEnabled(user.isEnabled());
        dto.setAuthorities(user.getAuthorities().stream()
                .map(auth -> new SimpleGrantedAuthority(auth.getAuthority()))
                .toList());

        return dto;
    }
}