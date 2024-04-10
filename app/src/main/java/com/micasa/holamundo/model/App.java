package com.micasa.holamundo.model;

public class App {
    private long id;
    private String nombre;
    private long id_user;

    private int contador;

    public App(long id, String nombre, long id_user) {
        this.id = id;
        this.nombre = nombre;
        this.id_user = id_user;
    }

    public App(String nombre, long id_user) {
        this.nombre = nombre;
        this.id_user = id_user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    @Override
    public String toString() {
        return "App{" +
                ", nombre='" + nombre +
                '}';
    }
}
