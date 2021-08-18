package com.db2_t3.models;

import com.mongodb.BasicDBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;

public class EstadisticaDeptoMongoDB {

    private String departamento;
    private int totalVentas;
    private CiudadMongoDB mejorCiudad;
    private EmpleadoMongoDB peorVendedor;
    private EmpleadoMongoDB mejorVendedor;

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public int getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(int totalVentas) {
        this.totalVentas = totalVentas;
    }

    public CiudadMongoDB getMejorCiudad() {
        return mejorCiudad;
    }

    public void setCiudad(CiudadMongoDB mejorCiudad) {
        this.mejorCiudad = mejorCiudad;
    }

    public EmpleadoMongoDB getPeorVendedor() {
        return peorVendedor;
    }

    public void setPeorVendedor(EmpleadoMongoDB peorVendedor) {
        this.peorVendedor = peorVendedor;
    }

    public EmpleadoMongoDB getMejorVendedor() {
        return mejorVendedor;
    }

    public void setMejorVendedor(EmpleadoMongoDB mejorVendedor) {
        this.mejorVendedor = mejorVendedor;
    }

    public EstadisticaDeptoMongoDB(String departamento, int totalVentas, CiudadMongoDB ciudad, EmpleadoMongoDB mejorVendedor, EmpleadoMongoDB peorVendedor) {
        setDepartamento(departamento);
        setTotalVentas(totalVentas);
        setCiudad(ciudad);
        setMejorVendedor(mejorVendedor);
        setPeorVendedor(peorVendedor);
    }

    /**
     * Añade la información de ventas de cada departamento a la colección estadísticas de la base de datos de MongoDB.
     * La estructura final del documento es la siguiente:
     * {@code
     * {
     * departamento: String,
     * misventas: [{
     * ciudad: String,
     * totalVentas: Int,
     * vendedor: {
     * cedula: Int,
     * ventas: Int
     * }
     * }]
     * }
     * }
     *
     * @param departamentos   Lista de departamentos
     * @param deleteAllBefore Especifica si debe limpiar la colección antes de insertar los nuevos documentos (false)
     */
    public static void addEstadisticas(ArrayList<DepartamentoOracle> departamentos, Boolean deleteAllBefore) {
        // Se realiza la conexión a la base de datos y se selecciona la colección "estadísticas".
        MongoDatabase db = ConexionMongoDB.conectarMongoDB();
        try {
            db.createCollection("estadisticas");
        } catch (Exception e) {
        }
        MongoCollection<Document> statsCollection = db.getCollection("estadisticas");
        if (deleteAllBefore) statsCollection.deleteMany(new BasicDBObject());
        for (int i = 0; i < departamentos.size(); i++) {
            // Se inicializa el documento que será agregado a la colección con el nombre del departamento.
            Document estadistica = new Document("departamento", departamentos.get(i).getNombre());
            ArrayList<CiudadOracle> cities = departamentos.get(i).getVentasPorCiudad();
            // Se inicializa la lista misventas, que contendrá la ciudad, el total de ventas y el mejor vendedor.
            // Esta es una lista de BasicDBObject, una implementación de BSON específica de MongoDB, para Java.
            ArrayList<BasicDBObject> misventas = new ArrayList<>();
            for (int j = 0; j < cities.size(); j++) {
                // Se agrega la ciudad al DBObject
                BasicDBObject venta = new BasicDBObject("ciudad", cities.get(j).getNombre());
                // Se agrega el totalVentas al DBObject
                venta.append("totalVentas", cities.get(j).getTotalVentas());
                try {
                    // Se agrega el vendedor como un DBObject compuesto por la cédula y las ventas de este
                    BasicDBObject vendedor = new BasicDBObject("cedula", cities.get(j).getMejorVendedor().getCedula());
                    vendedor.append("ventas", cities.get(j).getMejorVendedor().getVentas());
                    // Se agrega el vendedor al DBObject
                    venta.append("vendedor", vendedor);
                } catch (Exception e) {
                    // Se agrega el vendedor al DBObject
                    venta.append("vendedor", null);
                }
                // Se agrega el DBObject venta a la lista misventas
                misventas.add(venta);
            }
            // Se agrega la lista misventas al documento
            estadistica.append("misventas", misventas);
            // Se inserta el documento en la base de datos
            InsertOneResult res = statsCollection.insertOne(estadistica);
            System.out.println(res);
        }
    }

    public static void addEstadisticas(ArrayList<DepartamentoOracle> departamentos) {
        addEstadisticas(departamentos, false);
    }

    public static ArrayList<EstadisticaDeptoMongoDB> getEstadisticasDepartamento() {
        MongoDatabase db = ConexionMongoDB.conectarMongoDB();
        MongoCollection<Document> statsCollection = db.getCollection("estadisticas");

        Bson unwind = Aggregates.unwind("$misventas"); // Esparcir el array misventas

        // Parte de la query usada para encontrar la mejor ciudad del departamento
        Bson sortCiudad = Aggregates.sort(Sorts.descending("misventas.totalVentas")); // Sort por ciudad con mejores ventas
        BsonField totalVentasAux = Accumulators.sum("totalVentas", "$misventas.totalVentas"); // Suma ventas de cada ciudad del depto
        BsonField mejorCiudadNombreAux = Accumulators.first("mejorCiudadNombre", "$misventas.ciudad"); // Selecciona nombre de la primera ciudad
        BsonField mejorCiudadVentasAux = Accumulators.first("mejorCiudadVentas", "$misventas.totalVentas"); // Selecciona ventas de la primera ciudad
        BsonField misventas = Accumulators.push("misventas", "$misventas"); // Rehacer el array misventas para usarlo más adelante
        Bson groupCiudad = Aggregates.group("$departamento", totalVentasAux, mejorCiudadNombreAux, mejorCiudadVentasAux, misventas);

        // Parte de la query usada para encontrar el mejor y peor vendedor del departamento
        Bson sortVendedor = Aggregates.sort(Sorts.descending("misventas.vendedor.ventas")); // Sort por vendedor con más ventas
        Bson filterVendedor = Aggregates.match(Filters.ne("misventas.vendedor", null));
        BsonField totalVentasAcc = Accumulators.first("totalVentas", "$totalVentas"); // Sumar totalVentas
        BsonField mejorVendedorAcc = Accumulators.first("mejorVendedor", "$misventas.vendedor"); // Seleccionar mejor vendedor
        BsonField peorVendedorAcc = Accumulators.last("peorVendedor", "$misventas.vendedor"); // Seleccionar peor vendedor
        BsonField mejorCiudadNombre = Accumulators.first("mejorCiudadNombre", "$mejorCiudadNombre");
        BsonField mejorCiudadVentas = Accumulators.first("mejorCiudadVentas", "$mejorCiudadVentas");
        Bson groupVendedor = Aggregates.group(
                "$_id",
                totalVentasAcc,
                mejorVendedorAcc,
                peorVendedorAcc,
                mejorCiudadNombre,
                mejorCiudadVentas
        ); // Agrupar y aplicar los acumuladores

        AggregateIterable<Document> query = statsCollection.aggregate(Arrays.asList(
                unwind,
                sortCiudad,
                groupCiudad,
                unwind,
                sortVendedor,
                filterVendedor,
                groupVendedor
        ));

        ArrayList<EstadisticaDeptoMongoDB> result = new ArrayList<>();

        for (Document document : query) {
            String departamento = (String) document.get("_id");
            int totalVentas = (int) document.get("totalVentas");
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
            // EmpleadoMongoDB peorVendedor = document.get("peorVendedor") == null
            //        ? mejorVendedor : new EmpleadoMongoDB(
            //        (int) ((Document) document.get("peorVendedor")).get("cedula"),
            //        (int) ((Document) document.get("peorVendedor")).get("ventas")
            // );

            EstadisticaDeptoMongoDB stat = new EstadisticaDeptoMongoDB(departamento, totalVentas, ciudad, mejorVendedor, peorVendedor);
            System.out.println(stat);

            result.add(stat);
        }

        return result;
    }

    @Override
    public String toString() {
        return "EstadisticaMongoDB{" +
                "departamento='" + departamento + '\'' +
                ", totalVentas=" + totalVentas +
                ", ciudad=" + mejorCiudad +
                ", mejorVendedor=" + mejorVendedor +
                ", peorVendedor=" + peorVendedor +
                '}';
    }
}
