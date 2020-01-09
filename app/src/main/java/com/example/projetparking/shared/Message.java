package com.example.projetparking.shared;

public class Message {
    String emailSender,message;
    Boolean lu = false;

    public Boolean getLu() {
        return lu;
    }

    public void setLu(Boolean lu) {
        this.lu = lu;
    }

    public String getEmailSender() {
        return emailSender;
    }

    public void setEmailSender(String emailSender) {
        this.emailSender = emailSender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Message() {
    }

    public Message(String emailSender, String message) {
        this.emailSender = emailSender;
        this.message = message;
    }
}
