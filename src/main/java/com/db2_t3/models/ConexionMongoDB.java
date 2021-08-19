package com.db2_t3.models;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;


public class ConexionMongoDB {
    public static String puerto = "27017";
    public static String dbName = "db2t3";
    public static MongoClient client = null;

    public static MongoDatabase conectarMongoDB(String databaseName, String port) {
        MongoDatabase database = null;
        try {
            // Conectando a la instancia de MongoDB...
            if (client == null) client = MongoClients.create("mongodb://localhost:" + port);
            database = client.getDatabase(databaseName);
            // Conexión exitosa a la instancia de MongoDB
        } catch (Exception e) {
            System.out.println("No se pudo conectar a la instancia de MongoDB");
        }
        return database;
    }

    public static MongoDatabase conectarMongoDB(String databaseName) {
        return conectarMongoDB(databaseName, puerto);
    }

    public static MongoDatabase conectarMongoDB() {
        return conectarMongoDB(dbName, puerto);
    }

}
