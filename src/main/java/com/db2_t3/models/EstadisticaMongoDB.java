package com.db2_t3.models;

import java.util.ArrayList;

public class EstadisticaMongoDB {

    private String departamento;
    private ArrayList<CiudadOracle> misventas;

    public EstadisticaMongoDB(String departamento, ArrayList<CiudadOracle> misventas) {
        this.departamento = departamento;
        this.misventas = misventas;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public ArrayList<CiudadOracle> getMisventas() {
        return misventas;
    }

    public void setMisventas(ArrayList<CiudadOracle> misventas) {
        this.misventas = misventas;
    }
}
