package com.aditya.project.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employee_id;

    private String username;

    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    private String title;

    @ManyToOne
    @JoinColumn(name= "department", referencedColumnName = "department_id")
    private Department department;

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        // Return a list of roles/authorities for the employee
//        // For simplicity, assuming 'ROLE_USER'. You can add roles based on your requirements.
//        return java.util.Collections.singletonList(() -> "ROLE_USER");
//    }

//    @Override
//    public String getPassword() {
//        return this.password;
//    }
//
//    @Override
//    public String getUsername() {
//        return this.username;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true; // Can be modified based on business logic
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true; // Can be modified based on business logic
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true; // Can be modified based on business logic
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true; // Can be modified based on business logic
//    }

}
