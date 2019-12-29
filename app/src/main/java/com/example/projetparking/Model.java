package com.example.projetparking;

public class Model {
    String id,user,adresse;
    Long capacite;

    public Model() {
    }

    public Model(String id, String user, String adresse, Long capacite) {
        this.id = id;
        this.user = user;
        this.adresse = adresse;
        this.capacite = capacite;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Long getCapacite() {
        return capacite;
    }

    public void setCapacite(Long capacite) {
        this.capacite = capacite;
    }
}
