package com.projectSTS.ecommercerest.dto.auth;

import com.projectSTS.ecommercerest.model.RolEnum;
import java.util.Set;

public class RegisterRequestDTO {
    private String username;
    private String password;
    private Set<RolEnum> roles;

    public RegisterRequestDTO() {}

    public RegisterRequestDTO(String username, String password, Set<RolEnum> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Set<RolEnum> getRoles() { return roles; }
    public void setRoles(Set<RolEnum> roles) { this.roles = roles; }
}