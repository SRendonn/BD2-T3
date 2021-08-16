package com.db2_t3.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CiudadOracle {
    private String nombre;
    private int totalVentas = 0;
    private EmpleadoOracle mejorVendedor;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(int totalVentas) {
        this.totalVentas = totalVentas;
    }

    public EmpleadoOracle getMejorVendedor() {
        return mejorVendedor;
    }

    public void setMejorVendedor(EmpleadoOracle mejorVendedor) {
        this.mejorVendedor = mejorVendedor;
    }

    public static int ventasPorCiudad(String ciudad){
        String consultaVentas = "SELECT SUM(v.nro_unidades*v.miprod.precio_unitario) AS ventas FROM empleado e, TABLE(e.ventas) v WHERE e.miciu.nom = '"+ciudad+"' GROUP BY e.miciu.nom";
        int ventasCiudad = 0;
        ResultSet resultadoVentas = ConexionOracle.consultarOracle(consultaVentas);
        try{
            while (resultadoVentas.next()) {
                ventasCiudad = resultadoVentas.getInt("ventas");
            }
            return ventasCiudad;
        } catch (SQLException e){
            System.out.println("No se pudo hacer la consulta de ventas por ciudad por el siguiente error:");
            System.out.println("Código de error: "+e.getErrorCode());
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public static ArrayList<CiudadOracle> obtenerCiudadesPorDepartamento(String departamento){
        ArrayList<CiudadOracle> listaCiudades = new ArrayList<CiudadOracle>();
        String consultaNombre = "SELECT c.nom AS nombre FROM ciudad c WHERE c.midep.nom= '"+departamento+"' ORDER BY c.nom";
        ResultSet resultadoNombre = ConexionOracle.consultarOracle(consultaNombre);
        try{
            while (resultadoNombre.next()){
                String nombreCiudad = resultadoNombre.getString("nombre");
                int ventasCiudad = ventasPorCiudad(nombreCiudad);
                EmpleadoOracle mejorVendedorCiudad = EmpleadoOracle.obtenerMejorVendedorPorCiudad(nombreCiudad);
                listaCiudades.add(new CiudadOracle(nombreCiudad, ventasCiudad, mejorVendedorCiudad));
            }
        } catch (SQLException e){
            System.out.println("No se pudo hacer la consulta de ciudades por departamento por el siguiente error:");
            System.out.println("Código de error: "+e.getErrorCode());
            System.out.println(e.getMessage());
        }

        return listaCiudades;
    }


    CiudadOracle(String nombre, int totalVentas, EmpleadoOracle mejorVendedor){
        setNombre(nombre);
        setTotalVentas(totalVentas);
        setMejorVendedor(mejorVendedor);
    }

}
