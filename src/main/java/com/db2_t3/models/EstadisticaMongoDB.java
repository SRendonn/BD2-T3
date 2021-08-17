package com.db2_t3.models;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

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

    public static void addEstadisticas(ArrayList<DepartamentoOracle> departamentos) {
        MongoDatabase db = ConexionMongoDB.conectarMongoDB();
        MongoCollection<Document> stats = db.getCollection("estadisticas");
        for(int i = 0; i<departamentos.size(); i++){
            Document estadistica = new Document("departamento", departamentos.get(i).getNombre());
            ArrayList<CiudadOracle> cities = departamentos.get(i).getVentasPorCiudad();
            ArrayList<BasicDBObject> misventas = new ArrayList<>();
            for(int j = 0; j<cities.size(); j++){
                BasicDBObject venta = new BasicDBObject("ciudad", cities.get(j).getNombre());
                venta.append("totalVentas", cities.get(j).getTotalVentas());
                try {
                    BasicDBObject vendedor = new BasicDBObject("cedula", cities.get(j).getMejorVendedor().getCedula());
                    vendedor.append("ventas", cities.get(j).getMejorVendedor().getVentas());
                    venta.append("vendedor", vendedor);
                }
                catch (Exception e){
                    venta.append("vendedor", null);
                }
                misventas.add(venta);
            }
            estadistica.append("misventas", misventas);
            System.out.println(stats.deleteMany(new Document("departamento", departamentos.get(i).getNombre())));
            InsertOneResult res = stats.insertOne(estadistica);

            System.out.println(res);
        }
    }
}
