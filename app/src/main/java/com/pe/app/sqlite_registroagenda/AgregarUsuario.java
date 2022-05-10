package com.pe.app.sqlite_registroagenda;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pe.app.sqlite_registroagenda.controllers.UserController;
import com.pe.app.sqlite_registroagenda.models.User;

public class AgregarUsuario extends AppCompatActivity {
    private Button btnSave;
    private EditText nombreE, numero, correoE ;
    private UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Instanciar vistas
        nombreE = findViewById(R.id.nombre);
        numero = findViewById(R.id.telefono);
        correoE = findViewById(R.id.correo);
        btnSave = findViewById(R.id.btnSave);

        // Crear el controlador
        userController = new UserController(AgregarUsuario.this);

        // Agregar listener del botón de guardar
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Resetear errores a ambos
                nombreE.setError(null);
                numero.setError(null);
                correoE.setError(null);
                String nombre = nombreE.getText().toString(),
                        numeroComoCadena = numero.getText().toString(),
                correo = correoE.getText().toString();

                if ("".equals(nombre)) {
                    nombreE.setError("Escribe el nombre del usuario");
                    nombreE.requestFocus();
                    return;
                }
                if ("".equals(numeroComoCadena)) {
                    numero.setError("Escribe el numero del usuario");
                    numero.requestFocus();
                    return;
                }
                if ("".equals(correo)) {
                    numero.setError("Escribe el correo del usuario");
                    numero.requestFocus();
                    return;
                }

                // Ver si es un entero
                int numeroE;
                try {
                    numeroE = Integer.parseInt(numero.getText().toString());
                } catch (NumberFormatException e) {
                    numero.setError("Escribe un número");
                    numero.requestFocus();
                    return;
                }
                // Ya pasó la validación
                User nuevaUsuario = new User(nombre, correo, numeroE);
                long id = userController.nuevaUser(nuevaUsuario);
                if (id == -1) {
                    // De alguna manera ocurrió un error
                    Toast.makeText(AgregarUsuario.this, "Error al guardar. Intenta de nuevo", Toast.LENGTH_SHORT).show();
                } else {
                    // Terminar
                    finish();
                }
            }
        });


    }
}
