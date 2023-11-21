package com.devsu.prueba.model.enums;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
public enum TipoMovimiento {
    D("Débito"),
    C("Crédito");

    private final String descripcion;

    TipoMovimiento(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
