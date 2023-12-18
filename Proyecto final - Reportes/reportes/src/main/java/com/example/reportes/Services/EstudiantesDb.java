package com.example.reportes.Services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import com.example.reportes.Models.Estudiantes;

public class EstudiantesDb {
    Connection _cn;
    int ola;

    public EstudiantesDb() {
        _cn = new Conexion().openDb();
    }

    public List<Estudiantes> ObtenerEstudiantes(Estudiantes es) {
        List<Estudiantes> Estud = new ArrayList<>();
        try {
            Statement stmt = _cn.createStatement();
            String query = "SELECT e.pri_nom + ' ' + e.pri_apellido AS Nombre, e.cedula, p.tipo_proyecto, p.titulo_proyecto, "
                    + "CASE WHEN p.Fecha_entrega IS NOT NULL AND p.Fecha_verificacion IS NULL THEN 'Entrega' " 
                    + "WHEN p.Fecha_verificacion IS NOT NULL AND p.Fecha_evaluacion IS NULL THEN 'Verificacion'"
			        + "WHEN p.Fecha_evaluacion IS NOT NULL AND p.Fecha_aprobacion IS NULL THEN 'Evaluacion' "
			        + "WHEN p.Fecha_aprobacion IS NOT NULL AND p.Fecha_sustentacion IS NULL THEN 'Aprobacion'"
			        + "WHEN p.Fecha_sustentacion IS NOT NULL AND p.Fecha_aprobacion IS NOT NULL THEN 'Aprobacion'"
                    + "END AS etapa, c.Nombre_carrera, e.Semestre, em.Nombre_empresa, e.Año_cursa, "
                    + "CASE WHEN p.fecha_sustentacion IS NULL THEN 'S/A' ELSE CAST(YEAR(p.Fecha_sustentacion) AS VARCHAR(4)) END AS sustentacion "
                    + "FROM Estudiante e JOIN Proyecto p ON e.Cod_proyecto = p.Cod_proyecto "
                    + "JOIN Carrera c ON e.Cod_carrera = c.Cod_carrera "
                    + "LEFT JOIN Practica_profesional pp ON e.Cod_proyecto = pp.Cod_proyecto "
                    + "LEFT JOIN Supervisor s ON pp.Cod_supervisor = s.Cod_supervisor "
                    + "LEFT JOIN Empresa em ON s.Cod_empresa = em.Cod_empresa ";

            // Construir la parte WHERE de la consulta basada en los filtros proporcionados
            List<String> conditions = new ArrayList<>();
            List<String> values = new ArrayList<>();
            System.out.println(es.getAño() + "" + es.getCarrera() + "" + es.getEtapa());
            if (es.getAño() != null && !es.getAño().isEmpty()) {
                conditions.add("e.Año_cursa = ?");
                values.add(es.getAño());
            }

            if (es.getCarrera() != null && !es.getCarrera().isEmpty()) {
                conditions.add("c.Nombre_carrera = ?");
                values.add(es.getCarrera());
            }

            if (es.getSemestre() != null && !es.getSemestre().isEmpty()) {
                conditions.add("e.Semestre = ?");
                values.add(es.getSemestre());
            }

            if (es.getTipo() != null && !es.getTipo().isEmpty()) {
                conditions.add("p.Tipo_proyecto = ?");
                values.add(es.getTipo());
            }

            if (es.getEtapa() != null && !es.getEtapa().isEmpty()) {
                conditions.add("(CASE WHEN p.Fecha_entrega IS NOT NULL AND p.Fecha_verificacion IS NULL THEN 'Entrega' "
                        + "WHEN p.Fecha_verificacion IS NOT NULL AND p.Fecha_evaluacion IS NULL THEN 'Verificacion' "
                        + "WHEN p.Fecha_evaluacion IS NOT NULL AND p.Fecha_aprobacion IS NULL THEN 'Evaluacion' "
                        + "WHEN p.Fecha_aprobacion IS NOT NULL AND p.Fecha_sustentacion IS NULL THEN 'Aprobacion' "
                        + "WHEN p.Fecha_sustentacion IS NOT NULL AND p.Fecha_aprobacion IS NOT NULL THEN 'Aprobacion' END) = ?");
                values.add(es.getEtapa());
            }

            if (!conditions.isEmpty()) {
                query += "WHERE " + String.join(" AND ", conditions);
            }

            PreparedStatement pstmt = _cn.prepareStatement(query);

            // Establecer valores de parámetros si hay filtros presentes
            for (int i = 0; i < values.size(); i++) {
                pstmt.setString(i + 1, values.get(i));
            }

            ResultSet result = pstmt.executeQuery();

            while (result.next()) {
                Estudiantes estudiante = new Estudiantes(
                        result.getString("Nombre"),
                        result.getString("cedula"),
                        result.getString("tipo_proyecto"),
                        result.getString("titulo_proyecto"),
                        result.getString("etapa"),
                        result.getString("Nombre_carrera"),
                        result.getString("Semestre"),
                        result.getString("Nombre_empresa"),
                        result.getString("Año_cursa"),
                        result.getString("sustentacion"));

                Estud.add(estudiante);
            }

            result.close();
            stmt.close();
            return Estud;
        } catch (Exception e) {
            e.printStackTrace();
            int x = 1;
        }
        return null;
    }

    public List<Estudiantes> TodoLosEstudiantes() {
        List<Estudiantes> Estud = new ArrayList<>();
        try {
            Statement stmt = _cn.createStatement();
            String query = "SELECT e.pri_nom + ' ' + e.pri_apellido AS Nombre, e.cedula, p.tipo_proyecto, p.titulo_proyecto, "
                    + "CASE WHEN p.Fecha_entrega IS NOT NULL AND p.Fecha_verificacion IS NULL THEN 'Entrega' "
                    + "WHEN p.Fecha_verificacion IS NOT NULL AND p.Fecha_evaluacion IS NULL THEN 'Verificacion' "
                    + "WHEN p.Fecha_evaluacion IS NOT NULL AND p.Fecha_aprobacion IS NULL THEN 'Evaluacion' "
                    + "WHEN p.Fecha_aprobacion IS NOT NULL AND p.Fecha_sustentacion IS NULL THEN 'Aprobacion' "
                    + "WHEN p.Fecha_sustentacion IS NOT NULL AND p.Fecha_aprobacion IS NOT NULL THEN 'Aprobacion' END AS etapa, "
                    + "c.Nombre_carrera, e.Semestre, em.Nombre_empresa, e.Año_cursa, "
                    + "CASE WHEN p.fecha_sustentacion IS NULL THEN 'S/A' ELSE CAST(YEAR(p.Fecha_sustentacion) AS VARCHAR(4)) END AS sustentacion "
                    + "FROM Estudiante e JOIN Proyecto p ON e.Cod_proyecto = p.Cod_proyecto "
                    + "JOIN Carrera c ON e.Cod_carrera = c.Cod_carrera "
                    + "LEFT JOIN Practica_profesional pp ON e.Cod_proyecto = pp.Cod_proyecto "
                    + "LEFT JOIN Supervisor s ON pp.Cod_supervisor = s.Cod_supervisor "
                    + "LEFT JOIN Empresa em ON s.Cod_empresa = em.Cod_empresa ";
            ResultSet result = stmt.executeQuery(query);

            while (result.next()) {
                Estudiantes estudiante = new Estudiantes(
                        result.getString("Nombre"),
                        result.getString("cedula"),
                        result.getString("tipo_proyecto"),
                        result.getString("titulo_proyecto"),
                        result.getString("etapa"),
                        result.getString("Nombre_carrera"),
                        result.getString("Semestre"),
                        result.getString("Nombre_empresa"),
                        result.getString("Año_cursa"),
                        result.getString("sustentacion"));

                Estud.add(estudiante);
            }

            result.close();
            stmt.close();
            return Estud;
        } catch (Exception e) {
            e.printStackTrace();
            int x = 1;
        }
        return null;
    }

}
