package com.db2_t3.models;

import java.sql.ResultSet;
import java.sql.SQLException;

/*
* El objeto almacena la cédula y las ventas de un empleado. Incluye un método estático para
* encontrar el mejor empleado por ventas de cada ciudad desde la base de datos de Oracle
* */
public class EmpleadoOracle {
    // Atributos
    private int cedula;
    private int ventas;

    // Constructor
    EmpleadoOracle(int cedula, int ventas){
        setVentas(ventas);
        setCedula(cedula);
    }

    //Getters y Setters
    public int getVentas() {
        return ventas;
    }

    private void setVentas(int ventas) {
        this.ventas = ventas;
    }

    public int getCedula() {
        return cedula;
    }

    private void setCedula(int cedula) {
        this.cedula = cedula;
    }

    /*
    * Consulta en la base de datos de Oracle el mejor vendedor de una ciudad
    * y lo retorna con sus respectivas ventas
    * */
    public static EmpleadoOracle obtenerMejorVendedorPorCiudad(String ciudad){
        String consulta = "SELECT cedula, total FROM(SELECT e.cc as cedula, SUM(v.nro_unidades*v.miprod.precio_unitario) AS total FROM empleado e, TABLE(e.ventas) v WHERE e.miciu.nom = '"+ciudad+"' GROUP BY e.cc ORDER BY total DESC) WHERE rownum=1";
        ResultSet resultado = ConexionOracle.consultarOracle(consulta);
        EmpleadoOracle nuevoEmpleado = null;
        try{
            while (resultado.next()){
                int cedulaEmpleado = resultado.getInt("cedula");
                int ventasEmpleado = resultado.getInt("total");
                nuevoEmpleado =  new EmpleadoOracle(cedulaEmpleado, ventasEmpleado);
            }
        } catch (SQLException e){
            System.out.println("No se pudo hacer la consulta del mejor vendedorpor ciudad por el siguiente error:");
            System.out.println("Código de error: "+e.getErrorCode());
            System.out.println(e.getMessage());
        }
        return nuevoEmpleado;
    }
}
