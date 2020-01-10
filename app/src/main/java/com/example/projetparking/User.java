package com.example.projetparking;

import com.example.projetparking.shared.Message;

import java.util.List;

public class User {
    public String email,role,name;
    private Boolean allow;

    public User(String email, String role,String name,Boolean allow) {
        this.email = email;
        this.role = role;
        this.name = name;
        this.allow = allow;
    }

    public Boolean getAllow() {
        return allow;
    }

    public void setAllow(Boolean allow) {
        this.allow = allow;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
