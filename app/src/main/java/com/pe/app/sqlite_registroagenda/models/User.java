package com.pe.app.sqlite_registroagenda.models;

public class User {

    private String nombre, correo;
    private int numero;
    private int id; //ID BD


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**Contructor para los objetos*/
    public User(String nombre, String correo, int numero) {
        this.nombre = nombre;
        this.correo = correo;
        this.numero = numero;
    }

    /**Contructor base de datos*/
    public User(String nombre, String correo, int numero, int id) {
        this.nombre = nombre;
        this.correo = correo;
        this.numero = numero;
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", numero=" + numero +
                ", id=" + id +
                '}';
    }
}
