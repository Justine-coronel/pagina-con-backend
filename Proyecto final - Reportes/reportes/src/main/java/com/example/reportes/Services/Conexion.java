package com.example.reportes.Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//ESTABLECER CONEXION A LA BASE DE DATOS
public class Conexion {
    public Connection openDb() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(
                    "jdbc:sqlserver://localhost:1433;databaseName=Trabajo_Graduación;user=sa;password=123456789;encrypt=true;trustServerCertificate=true");
        } catch (SQLException e) {
            int x = 1;
            System.out.println("Error de conexion- No se puede conectar a la Base de datos");
        } catch (ClassNotFoundException cnfex) {
            int x = 1;
            System.out.println("Error de Clase");
        }
        return null;
    }
}