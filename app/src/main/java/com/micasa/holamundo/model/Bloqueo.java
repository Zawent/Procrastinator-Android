package com.micasa.holamundo.model;

import java.sql.Time;
import java.time.LocalDateTime;

public class Bloqueo {
    private long id;
    private LocalDateTime hora_inicio;
    private Time duracion;
    private String estado;
    private long id_app;

    public Bloqueo(long id, LocalDateTime hora_inicio, Time duracion, String estado, long id_app) {
        this.id = id;
        this.hora_inicio = hora_inicio;
        this.duracion = duracion;
        this.estado = estado;
        this.id_app = id_app;
    }

    public Bloqueo(LocalDateTime hora_inicio, Time duracion, String estado, long id_app) {
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

    public LocalDateTime getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(LocalDateTime hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public Time getDuracion() {
        return duracion;
    }

    public void setDuracion(Time duracion) {
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
