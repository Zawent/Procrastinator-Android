package com.micasa.holamundo.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    private Long id;
    private String name;
    private String fecha_nacimiento;
    private String ocupacion;
    private String email;
    private int id_rol;
    private int nivel_id;

    public User(Long id, String name, String fecha_nacimiento, String ocupacion, String email, int id_rol) {
        this.id = id;
        this.name = name;
        this.fecha_nacimiento = fecha_nacimiento;
        this.ocupacion = ocupacion;
        this.email = email;
        this.id_rol = id_rol;
    }

    public User(String name, String fecha_nacimiento, String ocupacion, String email, int id_rol) {
        this.name = name;
        this.fecha_nacimiento = fecha_nacimiento;
        this.ocupacion = ocupacion;
        this.email = email;
        this.id_rol = id_rol;
    }

    public User(Long id, String name, String fecha_nacimiento, String ocupacion, String email, int id_rol, int nivel_id) {
        this.id = id;
        this.name = name;
        this.fecha_nacimiento = fecha_nacimiento;
        this.ocupacion = ocupacion;
        this.email = email;
        this.id_rol = id_rol;
        this.nivel_id = nivel_id;
    }

    public User(String name, String fecha_nacimiento, String ocupacion, String email, int id_rol, int nivel_id) {
        this.name = name;
        this.fecha_nacimiento = fecha_nacimiento;
        this.ocupacion = ocupacion;
        this.email = email;
        this.id_rol = id_rol;
        this.nivel_id = nivel_id;
    }

    public User(Long id, String name, String fecha_nacimiento, String ocupacion) {
        this.id = id;
        this.name = name;
        this.fecha_nacimiento = fecha_nacimiento;
        this.ocupacion = ocupacion;
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

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String edad) {
        this.fecha_nacimiento = fecha_nacimiento;
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

    public int getNivel_id() {
        return nivel_id;
    }

    public void setNivel_id(int nivel_id) {
        this.nivel_id = nivel_id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fecha_nacimiento='" + fecha_nacimiento + '\'' +
                ", ocupacion='" + ocupacion + '\'' +
                ", email='" + email + '\'' +
                ", id_rol=" + id_rol +
                ", nivel_id=" + nivel_id +
                '}';
    }
}
