package com.db2_t3.models;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;

public class EstadisticaGlobalMongoDB {
    private DepartamentoMongoDB mejorDepartamento;
    private CiudadMongoDB mejorCiudad;
    private EmpleadoMongoDB mejorVendedor;
    private EmpleadoMongoDB peorVendedor;

    public DepartamentoMongoDB getMejorDepartamento() {
        return mejorDepartamento;
    }

    public void setMejorDepartamento(DepartamentoMongoDB mejorDepartamento) {
        this.mejorDepartamento = mejorDepartamento;
    }

    public CiudadMongoDB getMejorCiudad() {
        return mejorCiudad;
    }

    public void setMejorCiudad(CiudadMongoDB mejorCiudad) {
        this.mejorCiudad = mejorCiudad;
    }

    public EmpleadoMongoDB getMejorVendedor() {
        return mejorVendedor;
    }

    public void setMejorVendedor(EmpleadoMongoDB mejorVendedor) {
        this.mejorVendedor = mejorVendedor;
    }

    public EmpleadoMongoDB getPeorVendedor() {
        return peorVendedor;
    }

    public void setPeorVendedor(EmpleadoMongoDB peorVendedor) {
        this.peorVendedor = peorVendedor;
    }

    public EstadisticaGlobalMongoDB(DepartamentoMongoDB mejorDepartamento, CiudadMongoDB mejorCiudad, EmpleadoMongoDB mejorVendedor, EmpleadoMongoDB peorVendedor) {
        setMejorDepartamento(mejorDepartamento);
        setMejorCiudad(mejorCiudad);
        setMejorVendedor(mejorVendedor);
        setPeorVendedor(peorVendedor);
    }

    @Override
    public String toString() {
        return "EstadisticaGlobalMongoDB{" +
                "mejorDepartamento=" + mejorDepartamento +
                ", mejorCiudad=" + mejorCiudad +
                ", mejorVendedor=" + mejorVendedor +
                ", peorVendedor=" + peorVendedor +
                '}';
    }
}
