package com.micasa.holamundo.model;

public class Respuesta {
    private Long id;
    private Long id_user;
    private int respuesta;
    private Long id_nivel;
    private Long id_pregunta;


    public Respuesta(Long id, Long id_user, int respuesta, Long id_nivel, Long id_pregunta) {
        this.id = id;
        this.id_user = id_user;
        this.respuesta = respuesta;
        this.id_nivel = id_nivel;
        this.id_pregunta = id_pregunta;
    }

    public Respuesta(Long id_user, int respuesta, Long id_nivel, Long id_pregunta) {
        this.id_user = id_user;
        this.respuesta = respuesta;
        this.id_nivel = id_nivel;
        this.id_pregunta = id_pregunta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    public int getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(int respuesta) {
        this.respuesta = respuesta;
    }

    public Long getId_nivel() {
        return id_nivel;
    }

    public void setId_nivel(Long id_nivel) {
        this.id_nivel = id_nivel;
    }

    public Long getId_pregunta() {
        return id_pregunta;
    }

    public void setId_pregunta(Long id_pregunta) {
        this.id_pregunta = id_pregunta;
    }

    @Override
    public String toString() {
        return "Respuesta{" +
                "id=" + id +
                ", id_user=" + id_user +
                ", respuesta=" + respuesta +
                ", id_nivel=" + id_nivel +
                ", id_pregunta=" + id_pregunta +
                '}';
    }
}
