package com.micasa.holamundo.model;

public class Pregunta {
    private Long id;
    private String descripcion_pregunta;


    public Pregunta(Long id, String descripcion_pregunta) {
        this.id = id;
        this.descripcion_pregunta = descripcion_pregunta;
    }

    public Pregunta(String descripcion_pregunta) {
        this.descripcion_pregunta = descripcion_pregunta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion_pregunta() {
        return descripcion_pregunta;
    }

    public void setDescripcion_pregunta(String descripcion_pregunta) {
        this.descripcion_pregunta = descripcion_pregunta;
    }

    @Override
    public String toString() {
        return "Pregunta{" +
                "id=" + id +
                ", descripcion_pregunta='" + descripcion_pregunta + '\'' +
                '}';
    }


}
