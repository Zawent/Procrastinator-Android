package com.micasa.holamundo.model;

import java.sql.Time;
import java.time.LocalDateTime;

public class Bloqueo {
    private long id;
    private String hora_inicio;
    private String duracion;
    private String estado;
    private long id_app;

    public Bloqueo(long id, String hora_inicio, String duracion, String estado, long id_app) {
        this.id = id;
        this.hora_inicio = hora_inicio;
        this.duracion = duracion;
        this.estado = estado;
        this.id_app = id_app;
    }

    public Bloqueo(String hora_inicio, String duracion, String estado, long id_app) {
        this.hora_inicio = hora_inicio;
        this.duracion = duracion;
        this.estado = estado;
        this.id_app = id_app;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public long getId_app() {
        return id_app;
    }

    public void setId_app(long id_app) {
        this.id_app = id_app;
    }

    @Override
    public String toString() {
        return "Bloqueo{" +
                "id=" + id +
                ", hora_inicio=" + hora_inicio +
                ", duracion=" + duracion +
                ", estado='" + estado + '\'' +
                ", id_app=" + id_app +
                '}';
    }
}
