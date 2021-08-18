package com.db2_t3.models;

public class EmpleadoMongoDB {
    private int cedula;
    private int ventas;

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public int getVentas() {
        return ventas;
    }

    public void setVentas(int ventas) {
        this.ventas = ventas;
    }

    public EmpleadoMongoDB(int cedula, int ventas) {
        setCedula(cedula);
        setVentas(ventas);
    }
}
