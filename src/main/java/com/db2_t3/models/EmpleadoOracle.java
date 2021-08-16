package com.db2_t3.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmpleadoOracle {
    private int cedula;
    private int ventas;
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

    EmpleadoOracle(int cedula, int ventas){
        setVentas(ventas);
        setCedula(cedula);
    }
}
