package com.uniquindio.software.clinica.modelo;

public enum TipoSangre {
    O_POSITIVO("O+"),
    O_NEGATIVO("O-"),
    A_POSITIVO("A+"),
    A_NEGATIVO("A-"),
    B_POSITIVO("B+"),
    B_NEGATIVO("B-"),
    AB_POSITIVO("AB+"),
    AB_NEGATIVO("AB-");

    private final String nomenclatura;

    TipoSangre(String nomenclatura) {
        this.nomenclatura = nomenclatura;
    }

    public String getNomenclatura() {
        return nomenclatura;
    }

}
