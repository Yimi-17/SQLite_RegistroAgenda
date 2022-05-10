package com.pe.app.sqlite_registroagenda.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pe.app.sqlite_registroagenda.DBAyudante;
import com.pe.app.sqlite_registroagenda.models.User;

import java.util.ArrayList;

public class UserController {
    private DBAyudante ayudanteDB;
    private String Nombre_Tabla = "usuarios";


    public UserController(Context contexto) {
        ayudanteDB = new DBAyudante(contexto);
    }

    public int eliminarUser(User usuario) {

        SQLiteDatabase baseDeDatos = ayudanteDB.getWritableDatabase();
        String[] argumentos = {String.valueOf(usuario.getId())};
        return baseDeDatos.delete(Nombre_Tabla, "id = ?", argumentos);
    }

    public long nuevaUser(User usuario) {
        // writable porque vamos a insertar
        SQLiteDatabase baseDeDatos = ayudanteDB.getWritableDatabase();
        ContentValues valoresParaInsertar = new ContentValues();
        valoresParaInsertar.put("nombre", usuario.getNombre());
        valoresParaInsertar.put("telefono", usuario.getNumero());
        valoresParaInsertar.put("correo", usuario.getCorreo());
        return baseDeDatos.insert(Nombre_Tabla, null, valoresParaInsertar);
    }

    public int guardarCambios(User usuarioEditada) {
        SQLiteDatabase baseDeDatos = ayudanteDB.getWritableDatabase();
        ContentValues valoresParaActualizar = new ContentValues();
        valoresParaActualizar.put("nombre", usuarioEditada.getNombre());
        valoresParaActualizar.put("telefono", usuarioEditada.getNumero());
        valoresParaActualizar.put("correo", usuarioEditada.getCorreo());
        // where id...
        String campoParaActualizar = "id = ?";
        // ... = idUsuario
        String[] argumentosParaActualizar = {String.valueOf(usuarioEditada.getId())};
        return baseDeDatos.update(Nombre_Tabla, valoresParaActualizar, campoParaActualizar, argumentosParaActualizar);
    }

    public ArrayList<User> obtenerUsers() {
        ArrayList<User> usuarios = new ArrayList<>();
        // readable porque no vamos a modificar, solamente leer
        SQLiteDatabase baseDeDatos = ayudanteDB.getReadableDatabase();
        // SELECT nombre, edad, id
        String[] columnasAConsultar = {"nombre", "telefono", "correo", "id"};
        Cursor cursor = baseDeDatos.query(
                Nombre_Tabla,//from usuarios
                columnasAConsultar,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor == null) {
            /*
                Salimos aquí porque hubo un error, regresar
                lista vacía
             */
            return usuarios;

        }
        // Si no hay datos, igualmente regresamos la lista vacía
        if (!cursor.moveToFirst()) return usuarios;

        // En caso de que sí haya, iteramos y vamos agregando los
        // datos a la lista de mascotas
        do {
            // El 0 es el número de la columna, como seleccionamos
            // nombre, edad,id entonces el nombre es 0, edad 1 e id es 2
            String nombreObtenidoDeBD = cursor.getString(0);
            int numeroObtenidaDeBD = cursor.getInt(1);
            String correoObtenidoDeDB = cursor.getString(2);
            int idUser = cursor.getInt(3);

            User usuarioObtenidaDeBD = new User(nombreObtenidoDeBD, correoObtenidoDeDB, numeroObtenidaDeBD, idUser);
            usuarios.add(usuarioObtenidaDeBD);
        } while (cursor.moveToNext());

        // Fin del ciclo. Cerramos cursor y regresamos la lista de mascotas :)
        cursor.close();
        return usuarios;

    }
}
