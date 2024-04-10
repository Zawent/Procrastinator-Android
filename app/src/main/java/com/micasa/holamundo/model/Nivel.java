package com.micasa.holamundo.model;

public class Nivel {

    private Long id;
    private String descripcion;

    public Nivel(Long id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Nivel(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Nivel{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
