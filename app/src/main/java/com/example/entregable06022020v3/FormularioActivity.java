package com.example.entregable06022020v3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class FormularioActivity extends AppCompatActivity {

    EditText etNombre, etContraseña, etCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        etNombre = findViewById(R.id.etNombre);
        etContraseña = findViewById(R.id.etPassword);
        etCorreo = findViewById(R.id.etEmail);
    }


    public void submit(View view) {
        String nombre = etNombre.getText().toString();
        String correo = etCorreo.getText().toString();
        String contraseña = etContraseña.getText().toString();
        new Usuario(nombre, contraseña, correo);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
