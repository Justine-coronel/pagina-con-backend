package com.example.reportes.Models;

public class Estudiantes {
    private String nombre;
    private String cedula;
    private String tipo;
    private String titulo;
    private String etapa;
    private String carrera;
    private String semestre;
    private String empresa;
    private String año;
    private String sustentacion;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEtapa() {
        return etapa;
    }

    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getAño() {
        return año;
    }

    public void setAño(String año) {
        this.año = año;
    }

    public String getSustentacion() {
        return sustentacion;
    }

    public void setSustentacion(String sustentacion) {
        this.sustentacion = sustentacion;
    }

    public Estudiantes(String nombre, String cedula, String tipo, String titulo, String etapa, String carrera,
            String semestre, String empresa, String año, String sustentacion) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.tipo = tipo;
        this.titulo = titulo;
        this.etapa = etapa;
        this.carrera = carrera;
        this.semestre = semestre;
        this.empresa = empresa;
        this.año = año;
        this.sustentacion = sustentacion;
    }

    public Estudiantes() {

    }
}
