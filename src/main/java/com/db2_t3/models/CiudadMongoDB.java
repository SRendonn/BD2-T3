package com.db2_t3.models;

public class CiudadMongoDB {
    private String nombre;
    private int totalVentas;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(int totalVentas) {
        this.totalVentas = totalVentas;
    }

    public CiudadMongoDB(String nombre, int totalVentas) {
        setNombre(nombre);
        setTotalVentas(totalVentas);
    }

    public CiudadMongoDB(String nombre) {
        this(nombre, 0);
    }
}
