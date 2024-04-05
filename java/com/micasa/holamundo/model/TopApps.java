package com.micasa.holamundo.model;

import java.util.List;

public class TopApps {
    private List<App> resultados;

    public List<App> getResultados() {
        return resultados;
    }

    public void setResultados(List<App> resultados) {
        this.resultados = resultados;
    }

    @Override
    public String toString() {
        return "TopApps{" +
                "resultados=" + resultados +
                '}';
    }
}
