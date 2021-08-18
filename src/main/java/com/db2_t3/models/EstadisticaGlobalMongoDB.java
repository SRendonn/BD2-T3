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

    public static ArrayList<EstadisticaGlobalMongoDB> getEstadisticasGlobales() {
        MongoDatabase db = ConexionMongoDB.conectarMongoDB();
        MongoCollection<Document> statsCollection = db.getCollection("estadisticas");

        Bson unwind = Aggregates.unwind("$misventas");
        Bson sortCiudad = Aggregates.sort(Sorts.descending("misventas.totalVentas"));
        BsonField totalDeptoAux = Accumulators.sum("totalDepto", "$misventas.totalVentas");
        BsonField deptoAux = Accumulators.first("departamento", "$departamento");
        BsonField misventas = Accumulators.push("misventas", "$misventas");
        Bson groupCiudad = Aggregates.group("$departamento", totalDeptoAux, deptoAux, misventas);
        Bson sortDepto = Aggregates.sort(Sorts.descending("totalDepto"));
        BsonField totalDepto = Accumulators.first("totalDepto", "$totalDepto");
        BsonField depto = Accumulators.first("departamento", "$departamento");
        BsonField mejorCiudadNombreAux = Accumulators.first("mejorCiudadNombre", "$misventas.ciudad");
        BsonField mejorCiudadVentasAux = Accumulators.first("mejorCiudadVentas", "$misventas.totalVentas");
        Bson groupAux = Aggregates.group("", totalDepto, depto, mejorCiudadNombreAux, mejorCiudadVentasAux, misventas);
        Bson sortVendedor = Aggregates.sort(Sorts.descending("misventas.vendedor.ventas"));
        Bson filterVendedor = Aggregates.match(Filters.ne("misventas.vendedor", null));
        BsonField mejorCiudadNombre = Accumulators.first("mejorCiudadNombre", "$mejorCiudadNombre");
        BsonField mejorCiudadVentas = Accumulators.first("mejorCiudadVentas", "$mejorCiudadVentas");
        BsonField mejorVendedorAcc = Accumulators.first("mejorVendedor", "$misventas.vendedor");
        BsonField peorVendedorAcc = Accumulators.last("peorVendedor", "$misventas.vendedor");
        Bson groupAux2 = Aggregates.group("", totalDepto, depto, mejorCiudadNombre, mejorCiudadVentas, mejorVendedorAcc, peorVendedorAcc);

        AggregateIterable<Document> query = statsCollection.aggregate(Arrays.asList(
                unwind,
                sortCiudad,
                groupCiudad,
                unwind,
                sortDepto,
                groupAux,
                unwind,
                sortVendedor,
                filterVendedor,
                groupAux2
        ));

        ArrayList<EstadisticaGlobalMongoDB> result = new ArrayList<>();

        for (Document document : query) {
            String nombreDepartamento = (String) document.get("departamento");
            int totalDepartamento = (int) document.get("totalDepto");
            DepartamentoMongoDB departamento = new DepartamentoMongoDB(nombreDepartamento, totalDepartamento);
            String ciudadNombre = (String) document.get("mejorCiudadNombre");
            int ciudadVentas = (int) document.get("mejorCiudadVentas");
            CiudadMongoDB ciudad = new CiudadMongoDB(ciudadNombre, ciudadVentas);
            EmpleadoMongoDB mejorVendedor = document.get("mejorVendedor") == null
                    ? null : new EmpleadoMongoDB(
                    (int) ((Document) document.get("mejorVendedor")).get("cedula"),
                    (int) ((Document) document.get("mejorVendedor")).get("ventas")
            );
            EmpleadoMongoDB peorVendedor = document.get("peorVendedor") == null
                    ? null : new EmpleadoMongoDB(
                    (int) ((Document) document.get("peorVendedor")).get("cedula"),
                    (int) ((Document) document.get("peorVendedor")).get("ventas")
            );

            EstadisticaGlobalMongoDB stat = new EstadisticaGlobalMongoDB(departamento, ciudad, mejorVendedor, peorVendedor);
            System.out.println(stat);

            result.add(stat);
        }

        return result;
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
