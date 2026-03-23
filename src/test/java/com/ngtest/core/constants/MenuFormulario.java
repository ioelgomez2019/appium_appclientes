package com.ngtest.core.constants;

public enum MenuFormulario {
    LOGIN("Login"),
    CREDITOS("Creditos"),
    AHORROS("Ahorros"),
    CAJA("Caja");

    private final String descripcion;

    MenuFormulario(String descripcion) {
        this.descripcion = descripcion;
    }

    public String descripcion() {
        return descripcion;
    }
}
