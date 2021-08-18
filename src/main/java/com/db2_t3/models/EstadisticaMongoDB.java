package com.db2_t3.models;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;


import java.util.ArrayList;

public class EstadisticaMongoDB {

    private String departamento;
    private Long totalVentas;
    private CiudadMongoDB ciudad;
    private EmpleadoMongoDB peorVendedor;
    private EmpleadoMongoDB mejorVendedor;

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public Long getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(Long totalVentas) {
        this.totalVentas = totalVentas;
    }

    public CiudadMongoDB getCiudad() {
        return ciudad;
    }

    public void setCiudad(CiudadMongoDB ciudad) {
        this.ciudad = ciudad;
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

    public EstadisticaMongoDB(String departamento, Long totalVentas, CiudadMongoDB ciudad, EmpleadoMongoDB peorVendedor, EmpleadoMongoDB mejorVendedor) {
        setDepartamento(departamento);
        setTotalVentas(totalVentas);
        setCiudad(ciudad);
        setPeorVendedor(peorVendedor);
        setMejorVendedor(mejorVendedor);
    }

    /**
     * Añade la información de ventas de cada departamento a la colección estadísticas de la base de datos de MongoDB.
     * La estructura final del documento es la siguiente:
     * {@code
     * {
     *     departamento: String,
     *     misventas: [{
     *         ciudad: String,
     *         totalVentas: Int,
     *         vendedor: {
     *             cedula: Int,
     *             ventas: Int
     *         }
     *     }]
     * }
     * }
     * @param departamentos Lista de departamentos
     * @param deleteAllBefore Especifica si debe limpiar la colección antes de insertar los nuevos documentos (false)
     * */
    public static void addEstadisticas(ArrayList<DepartamentoOracle> departamentos, Boolean deleteAllBefore) {
        // Se realiza la conexión a la base de datos y se selecciona la colección "estadísticas".
        MongoDatabase db = ConexionMongoDB.conectarMongoDB();
        try {
            db.createCollection("estadisticas");
        } catch (Exception e) {}
        MongoCollection<Document> statsCollection = db.getCollection("estadisticas");
        if (deleteAllBefore) statsCollection.deleteMany(new BasicDBObject());
        for(int i = 0; i<departamentos.size(); i++){
            // Se inicializa el documento que será agregado a la colección con el nombre del departamento.
            Document estadistica = new Document("departamento", departamentos.get(i).getNombre());
            ArrayList<CiudadOracle> cities = departamentos.get(i).getVentasPorCiudad();
            // Se inicializa la lista misventas, que contendrá la ciudad, el total de ventas y el mejor vendedor.
            // Esta es una lista de BasicDBObject, una implementación de BSON específica de MongoDB, para Java.
            ArrayList<BasicDBObject> misventas = new ArrayList<>();
            for(int j = 0; j<cities.size(); j++){
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
                }
                catch (Exception e){
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

    public static void getEstadisticas() {
        MongoDatabase db = ConexionMongoDB.conectarMongoDB();
        MongoCollection<Document> statsCollection = db.getCollection("estadisticas");
        FindIterable<Document> deptos = statsCollection.find();
        deptos.forEach(document -> System.out.println(document));
    }
}
