package com.db2_t3.models;

import java.sql.*;

public class ConexionOracle {

    public static String puerto = "1521";
    public static String usuario = "";
    public static String contra = "";
    private static Connection conn;

    /*
    * Realiza una conexión con la base de datos de Oracle
    * */
    public static void conectarOracle(){

        try{ // Se carga el driver JDBC-ODBC
            Class.forName ("oracle.jdbc.driver.OracleDriver");
            System.out.println("Driver cargado");
        } catch( Exception e ) {
            System.out.println("No se pudo cargar el driver JDBC");
        }

        try{ // Se establece la conexión con la base de datos
            System.out.println("Conectando...");
            conn = DriverManager.getConnection
                    ("jdbc:oracle:thin:@localhost:"+puerto+":xe",usuario, contra);
            System.out.println("Conexión realizada");
        } catch( SQLException e ) {
            System.out.println( "No hay conexión con la base de datos." );
        }
    }

    /*
    * Desconecta la base de Datos de Oracle
    * */
    public static void desconectarOracle(){
        try{
            conn.close();
        } catch (SQLException e) {
            System.out.println("No se pudo cerrar la base de datos por el siguiente error:");
            System.out.println("Código de error: "+e.getErrorCode());
            System.out.println(e.getMessage());
        }

    }

    /*
    * Retorna el resultado de una consulta en la base de datos de Oracle a partir de un
    * String con la consulta
    * */
    public static ResultSet consultarOracle(String consulta){
        Statement sentencia;
        ResultSet resultado;
        try{
            conectarOracle();
            sentencia = conn.createStatement();
            resultado = sentencia.executeQuery(consulta);
            return resultado;
        } catch( SQLException e ) {
            System.out.println("No se pudo hacer la consulta a la base de datos por el siguiente error:");
            System.out.println(consulta);
            System.out.println("Código de error: "+e.getErrorCode());
            System.out.println(e.getMessage());
            return null;
        }


    }


}
