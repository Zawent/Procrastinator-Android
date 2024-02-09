package com.micasa.holamundo.model;

import java.io.Serializable;

public class User implements Serializable {

    private Long id;
    private String name;
    private int edad;
    private String ocupacion;
    private String email;
    private int id_rol;

    public User(Long id, String name, int edad, String ocupacion, String email, int id_rol) {
        this.id = id;
        this.name = name;
        this.edad = edad;
        this.ocupacion = ocupacion;
        this.email = email;
        this.id_rol = id_rol;
    }

    public User(String name, int edad, String ocupacion, String email, int id_rol) {
        this.name = name;
        this.edad = edad;
        this.ocupacion = ocupacion;
        this.email = email;
        this.id_rol = id_rol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", edad=" + edad +
                ", ocupacion='" + ocupacion + '\'' +
                ", email='" + email + '\'' +
                ", id_rol=" + id_rol +
                '}';
    }
}
