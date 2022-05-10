package com.pe.app.sqlite_registroagenda;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.pe.app.sqlite_registroagenda.models.User;

import java.util.List;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.MyViewHolder> {

    private List<User> userList;

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public AdapterUser(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View list_user = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_user, viewGroup, false);
        return new MyViewHolder(list_user);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        // Obtener el usuario de nuestra lista gracias al índice i
        User usuario = userList.get(i);

        // Obtener los datos de la lista
        String nombreUsuario = usuario.getNombre();
        int numeroUsuario = usuario.getNumero();
        String correoUsuario = usuario.getCorreo();
        // Y poner a los TextView los datos con setText
        myViewHolder.nombre.setText(nombreUsuario);
        myViewHolder.numero.setText(String.valueOf(numeroUsuario));
        myViewHolder.correo.setText((correoUsuario));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, numero, correo;

        MyViewHolder(View itemView) {
            super(itemView);
            this.nombre = itemView.findViewById(R.id.NameContact);
            this.numero = itemView.findViewById(R.id.NumberPhone);
            this.correo = itemView.findViewById(R.id.CorreoElectronic);
        }
    }
}
