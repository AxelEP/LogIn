package com.example.entregable06022020v3;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.security.MessageDigest;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient googleApiClient;
    SignInButton signInButton;
    public static final int SIGN_IN_CODE = 777, CODIGO_DE_REGISTRO = 888, CODIGO_INGRESO = 111;

    private CallbackManager callbackManager;
    private LoginButton loginButton;

    EditText etUser, etContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_main);

        etUser = findViewById(R.id.etUser);
        etContraseña = findViewById(R.id.etPassword);

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email" , "public_profile", "user_friends");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                goFbActivity();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), R.string.loginCancelado, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        signInButton = (SignInButton) findViewById(R.id.btnLoginGoogle);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_CODE);
            }
        });
    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGN_IN_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

        /*if (requestCode == CODIGO_DE_REGISTRO && resultCode == RESULT_OK){
            String nombre = data.getStringExtra("nombre");
            String contraseña = data.getStringExtra("contraseña");
            String correo = data.getStringExtra("correo");

            listaUsuarios.add(new Usuario(nombre, contraseña, correo));
        }*/

    }

    private void goFbActivity(){
        //if(perfil != null){
            Intent intent = new Intent(this, SuccessFacebookActivity.class);
            //intent.putExtra("nombre", perfil.getFirstName());
            //intent.putExtra("apellido", perfil.getLastName());
            //intent.putExtra("uri", perfil.getProfilePictureUri(100,100).toString());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        //}
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            goMainScreen();
        }else{
            Toast.makeText(this, R.string.errorLogin, Toast.LENGTH_SHORT).show();
        }
    }

    private void goMainScreen() {
        Intent intent = new Intent(this, SuccesGoogleActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public String keyHashes(){
        PackageInfo info;
        String key = null;

        try{
            info = getPackageManager().getPackageInfo("com.example.entregable06022020v3", PackageManager.GET_SIGNATURES);
            for(Signature signature : info.signatures){
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(),0));
            }

        }catch(Exception e){

        }

        return key;
    }

    public void signIn(View view) {

        Intent intent = new Intent(this, FormularioActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        /*Intent intent = new Intent(this, FormularioActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("lista", listaUsuarios);
        intent.putExtras(bundle);
        startActivityForResult(intent, CODIGO_DE_REGISTRO);*/


    }

    public void standarLogin(View view) {
        String nombre = etUser.getText().toString();
        String contraseña = etUser.getText().toString();
        Usuario usuario = null;
        System.out.println("Usuarios registrados:");
        for(Usuario aux : Usuario.listaUsuarios){
            System.out.println("Usuario: " + aux.nombre + " " + aux.correo + " " + aux.contraseña);
            System.out.println(nombre.equals(aux.nombre));
            System.out.println(contraseña.equals(aux.contraseña));
            if(aux.nombre.equals(nombre) && aux.nombre.equals(contraseña)){
                usuario = aux;
                Intent intent = new Intent(this, StandarLoginActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("usuario", usuario);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }
}
