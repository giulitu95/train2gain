package com.train2gain.train2gain.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splash extends AppCompatActivity {

    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        checkUser(user);

        finish();
    }

    private void checkUser(FirebaseUser user){
        if(user == null){
            // Login o Reg
            Intent startActityIntent = new Intent(this, LoginActivity.class);
            startActivity(startActityIntent);
        }else{
            Intent startActivityIntent = new Intent(this, SyncDataSplashActivity.class);
            long userId = user.getUid().hashCode();
            startActivityIntent.putExtra(SyncDataSplashActivity.USER_ID_PARAM, userId);
            startActivity(startActivityIntent);
        }
    }

}
