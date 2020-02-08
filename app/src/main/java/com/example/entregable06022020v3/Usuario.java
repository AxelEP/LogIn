package com.example.entregable06022020v3;

import java.io.Serializable;
import java.util.ArrayList;

public class Usuario implements Serializable {
    public String nombre;
    public String contraseña;
    public String correo;
    public static ArrayList<Usuario> listaUsuarios = new ArrayList<>();

    public Usuario(String nombre, String contraseña, String correo){
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.correo = correo;
        listaUsuarios.add(this);
    }
}
