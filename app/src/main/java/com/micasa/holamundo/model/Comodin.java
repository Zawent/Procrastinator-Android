package com.micasa.holamundo.model;

public class Comodin {

    private Long id;
    private String tiempo_generacion;
    private int id_user;

    @Override
    public String toString() {
        return "Comodin{" +
                "id=" + id +
                ", tiempo_generacion=" + tiempo_generacion +
                ", id_user=" + id_user +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTiempo_generacion() {
        return tiempo_generacion;
    }

    public void setTiempo_generacion(String tiempo_generacion) {
        this.tiempo_generacion = tiempo_generacion;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public Comodin(Long id, String tiempo_generacion, int id_user) {
        this.id = id;
        this.tiempo_generacion = tiempo_generacion;
        this.id_user = id_user;
    }
}

