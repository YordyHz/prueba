package com.devsu.prueba.model.enums;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
public enum Genero {
    M("Masculino"),
    F("Femenino");

    private final String descripcion;

    Genero(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
