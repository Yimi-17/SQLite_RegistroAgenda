package com.pe.app.sqlite_registroagenda;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pe.app.sqlite_registroagenda.controllers.UserController;
import com.pe.app.sqlite_registroagenda.models.User;

public class EditarUsuario extends AppCompatActivity {
    private EditText editNombre, editTelefono, editCorreo;
    private Button btnSaveChange;
    private User usuario;//El usuario que vamos a estar editando
    private UserController usuariosController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Recuperar datos que enviaron
        Bundle extras = getIntent().getExtras();
        // Si no hay datos (cosa rara) salimos
        if (extras == null) {
            finish();
            return;
        }
        // Instanciar el controlador de las mascotas
        usuariosController = new UserController(EditarUsuario.this);

        // Rearmar usuario
        // Nota: igualmente solamente podríamos mandar el id y recuperar el usuario de la BD
        long idUsuario = extras.getLong("idUsuario");
        String nombreUsuario = extras.getString("nombreUsuario");
        int telefonoUsuario = extras.getInt("telefonoUsuario");
        String correoUsuario = extras.getString("correoUsuario");
        usuario = new User(nombreUsuario, correoUsuario, telefonoUsuario, (int) idUsuario);

        // Ahora declaramos las vistas
        editTelefono = findViewById(R.id.editTelefono);
        editNombre = findViewById(R.id.editNombre);
        editCorreo = findViewById(R.id.editCorreo);
        btnSaveChange = findViewById(R.id.btnSaveChange);


        // Rellenar los EditText con los datos de los usuarios
        editTelefono.setText(String.valueOf(usuario.getNumero()));
        editNombre.setText(usuario.getNombre());
        editCorreo.setText(usuario.getCorreo());

        // Listener del click del botón para salir, simplemente cierra la actividad

        // Listener del click del botón que guarda cambios
        btnSaveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remover previos errores si existen
                editNombre.setError(null);
                editTelefono.setError(null);
                editCorreo.setError(null);
                // Crear la mascota con los nuevos cambios pero ponerle
                // el id de la anterior
                String nuevoNombre = editNombre.getText().toString();
                String posibleNuevoTelefono = editTelefono.getText().toString();
                String nuevoCorreo = editCorreo.getText().toString();
                if (nuevoNombre.isEmpty()) {
                    editNombre.setError("Escribe el nombre");
                    editNombre.requestFocus();
                    return;
                }
                if (posibleNuevoTelefono.isEmpty()) {
                    editTelefono.setError("Escribe el telefono");
                    editTelefono.requestFocus();
                    return;
                }
                if (nuevoCorreo.isEmpty()) {
                    editCorreo.setError("Escribe el nuevo correo");
                    editCorreo.requestFocus();
                    return;
                }

                // Si no es entero, igualmente marcar error
                int nuevoTelefono;
                try {
                    nuevoTelefono = Integer.parseInt(posibleNuevoTelefono);
                } catch (NumberFormatException e) {
                    editTelefono.setError("Escribe un número");
                    editTelefono.requestFocus();
                    return;
                }
                // Si llegamos hasta aquí es porque los datos ya están validados
                User usuarioConNuevosCambios = new User(nuevoNombre, nuevoCorreo, nuevoTelefono, usuario.getId());
                int filasModificadas = usuariosController.guardarCambios(usuarioConNuevosCambios);
                if (filasModificadas != 1) {
                    // De alguna forma ocurrió un error porque se debió modificar únicamente una fila
                    Toast.makeText(EditarUsuario.this, "Error guardando cambios. Intente de nuevo.", Toast.LENGTH_SHORT).show();
                } else {
                    // Si las cosas van bien, volvemos a la principal
                    // cerrando esta actividad
                    finish();
                }
            }
        });
    }
}
