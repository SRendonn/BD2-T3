package com.db2_t3.models;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;


public class ConexionMongoDB {
    public static final String puerto = "27017";

    public static MongoDatabase conectarMongoDB() {
        MongoDatabase database = null;
        try {
            System.out.println("Conectando a la instancia de MongoDB...");
            MongoClient client = MongoClients.create("mongodb://localhost:" + puerto);
            database = client.getDatabase("db2t3");
            System.out.println("Conexión exitosa a la instancia de MongoDB");
        } catch (Exception e) {
            System.out.println("No se pudo conectar a la instancia de MongoDB");
        }
        return database;
    }

    public static MongoDatabase conectarMongoDB(String databaseName) {
        MongoDatabase database = null;
        try {
            System.out.println("Conectando a la instancia de MongoDB...");
            MongoClient client = MongoClients.create("mongodb://localhost:" + puerto);
            database = client.getDatabase(databaseName);
            System.out.println("Conexión exitosa a la instancia de MongoDB");
        } catch (Exception e) {
            System.out.println("No se pudo conectar a la instancia de MongoDB");
        }
        return database;
    }

    public static MongoDatabase conectarMongoDB(String databaseName, String port) {
        MongoDatabase database = null;
        try {
            System.out.println("Conectando a la instancia de MongoDB...");
            MongoClient client = MongoClients.create("mongodb://localhost:" + port);
            database = client.getDatabase(databaseName);
            System.out.println("Conexión exitosa a la instancia de MongoDB");
        } catch (Exception e) {
            System.out.println("No se pudo conectar a la instancia de MongoDB");
        }
        return database;
    }
}
