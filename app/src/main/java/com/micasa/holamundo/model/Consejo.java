package com.micasa.holamundo.model;

public class Consejo {
    private long id;
    private long id_nivel;
    private String consejo;

    public Consejo(long id, long id_nivel, String consejo) {
        this.id = id;
        this.id_nivel = id_nivel;
        this.consejo = consejo;
    }

    public Consejo(long id_nivel, String consejo) {
        this.id_nivel = id_nivel;
        this.consejo = consejo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_nivel() {
        return id_nivel;
    }

    public void setId_nivel(long id_nivel) {
        this.id_nivel = id_nivel;
    }

    public String getConsejo() {
        return consejo;
    }

    public void setConsejo(String consejo) {
        this.consejo = consejo;
    }

    @Override
    public String toString() {
        return "Consejo{" +
                "id=" + id +
                ", id_nivel=" + id_nivel +
                ", consejo='" + consejo + '\'' +
                '}';
    }
}
