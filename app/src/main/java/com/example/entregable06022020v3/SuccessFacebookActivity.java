package com.example.entregable06022020v3;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;

public class SuccessFacebookActivity extends AppCompatActivity {


    TextView tvUserNameFb;
    ImageView ivUserPictFb;
    ProfileTracker profileTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_facebook);

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged (Profile oldProfile, Profile currentProfile) {
                if (currentProfile != null) {
                    displayProfileInfo(currentProfile);
                }
            }
        };

        tvUserNameFb = (TextView) findViewById(R.id.tvUserNameFb);
        ivUserPictFb = (ImageView) findViewById(R.id.ivUserPictFb);

        if(AccessToken.getCurrentAccessToken() == null){
            goLoginScreen();
        } else {
            Profile profile = Profile.getCurrentProfile();
            if (profile != null) {
                displayProfileInfo(profile);
                Toast.makeText(this, "Desplegando usuario", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void goLoginScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logoutFb(View view) {
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }

    private void displayProfileInfo(Profile profile) {
        String id = profile.getId();
        String name = profile.getName();
        String photoUrl = profile.getProfilePictureUri(100, 100).toString();

        tvUserNameFb.setText(name);

        Glide.with(getApplicationContext())
                .load(photoUrl)
                .into(ivUserPictFb);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        tvUserNameFb.setText(data.getStringExtra("nombre")+" " + data.getStringExtra("apellido"));
        Glide.with(getApplicationContext()).load(data.getStringExtra("uri")).into(ivUserPictFb);
    }
}
