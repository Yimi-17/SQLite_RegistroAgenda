package com.pe.app.sqlite_registroagenda;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBAyudante extends SQLiteOpenHelper {
    private static final String NOMBRE_BASE_DE_DATOS = "usuarios",
            NOMBRE_TABLA_USUARIOS = "usuarios";
    private static final int VERSION_BASE_DE_DATOS = 1;

    public DBAyudante(Context context) {
        super(context, NOMBRE_BASE_DE_DATOS, null, VERSION_BASE_DE_DATOS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s(id integer primary key autoincrement, nombre text, telefono int, correo text)", NOMBRE_TABLA_USUARIOS));

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
