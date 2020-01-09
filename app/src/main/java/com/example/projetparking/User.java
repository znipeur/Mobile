package com.example.projetparking;

import java.util.List;

public class User {
    public String email,role;
    public List<Message> listMessage;

    public User(String email, String role,List<Message> listMessage) {
        this.email = email;
        this.role = role;
        this.listMessage = listMessage;
    }

    public User() {
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

    public List<Message> getListMessage() {
        return listMessage;
    }

    public void setListMessage(List<Message> listMessage) {
        this.listMessage = listMessage;
    }
}
