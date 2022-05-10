package com.pe.app.sqlite_registroagenda;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pe.app.sqlite_registroagenda.controllers.UserController;
import com.pe.app.sqlite_registroagenda.models.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<User> userList;
    private RecyclerView recyclerView;
    private AdapterUser adaptadorUsuario;
    private UserController usuarioController;
    private FloatingActionButton AddUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Ojo: este código es generado automáticamente, pone la vista y ya, pero
        // no tiene nada que ver con el código que vamos a escribir
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Lo siguiente sí es nuestro ;)
        // Definir nuestro controlador
        usuarioController = new UserController(MainActivity.this);

        // Instanciar vistas
        recyclerView = findViewById(R.id.recyclerViewUser);
        AddUser = findViewById(R.id.btnAddUser);


        // Por defecto es una lista vacía,
        // se la ponemos al adaptador y configuramos el recyclerView
        userList = new ArrayList<>();
        adaptadorUsuario = new AdapterUser(userList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adaptadorUsuario);

        // Una vez que ya configuramos el RecyclerView le ponemos los datos de la BD
        refrescarListaDeUsuarios();

        // Listener de los clicks en la lista, o sea el RecyclerView
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override // Un toque sencillo
            public void onClick(View view, int position) {
                // Pasar a la actividad EditarUsuario.java
                User usuarioSeleccionada = userList.get(position);
                Intent intent = new Intent(MainActivity.this, EditarUsuario.class);
                intent.putExtra("idUsuario", usuarioSeleccionada.getId());
                intent.putExtra("nombreUsuario",  usuarioSeleccionada.getNombre());
                intent.putExtra("numeroUsuario", usuarioSeleccionada.getNumero());
                intent.putExtra("correoUsuario",usuarioSeleccionada.getCorreo() );
                startActivity(intent);
            }

            @Override // Un toque largo
            public void onLongClick(View view, int position) {
                final User usuarioParaEliminar = userList.get(position);
                AlertDialog dialog = new AlertDialog
                        .Builder(MainActivity.this)
                        .setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                usuarioController.eliminarUser(usuarioParaEliminar);
                                refrescarListaDeUsuarios();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle("Confirmar")
                        .setMessage("¿Eliminar a la Usuario " + usuarioParaEliminar.getNombre() + "?")
                        .create();
                dialog.show();

            }
        }));

        // Listener del FAB
        AddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Simplemente cambiamos de actividad
                Intent intent = new Intent(MainActivity.this, AgregarUsuario.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        refrescarListaDeUsuarios();
    }

    public void refrescarListaDeUsuarios() {
        /*
         * ==========
         * Justo aquí obtenemos la lista de la BD
         * y se la ponemos al RecyclerView
         * ============
         *
         * */
        if (adaptadorUsuario == null) return;
        userList = usuarioController.obtenerUsers();
        adaptadorUsuario.setUserList(userList);
        adaptadorUsuario.notifyDataSetChanged();
    }
}
