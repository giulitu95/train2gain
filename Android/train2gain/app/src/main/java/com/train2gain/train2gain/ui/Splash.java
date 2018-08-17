package com.train2gain.train2gain.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        checkUser(user);

        finish();
    }

    private void checkUser(FirebaseUser user){
        if(user == null){
            // Login o Reg
            Intent startActityIntent = new Intent(this, LoginActivity.class);
            startActivity(startActityIntent);
        }else{
        }
    }

}
