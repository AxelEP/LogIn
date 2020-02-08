package com.example.entregable06022020v3;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StandarLoginActivity extends AppCompatActivity {

    TextView tvNombreStandar, tvCorreoStandar;
    Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standar_login);

        tvNombreStandar = findViewById(R.id.tvNombreStandar);
        tvCorreoStandar = findViewById(R.id.tvCorreoStandar);
        Bundle currentUser = getIntent().getExtras();
        if(currentUser!=null){
            usuario = (Usuario) currentUser.getSerializable("usuario");
            tvNombreStandar.setText(usuario.nombre);
            tvCorreoStandar.setText(usuario.correo);
        }
        else{
            Toast.makeText(this, "Error al cargar información del usuario", Toast.LENGTH_SHORT).show();
        }
     }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if(resultCode==111){
            tvNombreStandar.setText(data.getStringExtra("nombre"));
            tvCorreoStandar.setText(data.getStringExtra("correo"));
        }*/
    }



    public void logoutStandar(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
