package com.db2_t3.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DepartamentoOracle {

    // Atributos
    private String nombre;
    private ArrayList<CiudadOracle> ventasPorCiudad;

    // Constructor
    DepartamentoOracle(String nombre, ArrayList<CiudadOracle>ventasPorCiudad){
        setNombre(nombre);
        setVentas(ventasPorCiudad);
    }

    // Getters y setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<CiudadOracle> getVentasPorCiudad() {
        return ventasPorCiudad;
    }

    public void setVentas(ArrayList<CiudadOracle> ventasPorCiudad) {
        this.ventasPorCiudad = ventasPorCiudad;
    }


    /*
    * Extrae todos los departamentos de la base de datos de Oracle con:
    * - Nombre
    * - Lista de ciudades con:
    *   - Nombre
    *   - Ventas totales
    *   - Mejor vendedor con:
    *     - Cédula
    *     - Ventas totales del vendedor
    * */
    public static ArrayList<DepartamentoOracle> obtenerDepartamentos(){
        ArrayList<DepartamentoOracle> listaDepartamentos = new ArrayList<DepartamentoOracle>();
        String consulta = "SELECT nom AS nombre FROM departamento ORDER BY nom";
        ResultSet resultado = ConexionOracle.consultarOracle(consulta);
        try{
            while (resultado.next()){
                String nombreDepartamento = resultado.getString("nombre");
                ArrayList<CiudadOracle> nuevasCiudades = new ArrayList<CiudadOracle>();
                nuevasCiudades = CiudadOracle.obtenerCiudadesPorDepartamento(nombreDepartamento);
                listaDepartamentos.add(new DepartamentoOracle(nombreDepartamento, nuevasCiudades));
            }
        } catch (SQLException e){
            System.out.println("No se pudo hacer la consulta de departamentos por el siguiente error:");
            System.out.println("Código de error: "+e.getErrorCode());
            System.out.println(e.getMessage());
            return null;
        }
        return listaDepartamentos;
    }
}
