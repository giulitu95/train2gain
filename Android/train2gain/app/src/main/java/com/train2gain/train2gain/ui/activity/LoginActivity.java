package com.train2gain.train2gain.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.train2gain.train2gain.R;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth authManager;
    private GoogleSignInClient mGoogleSignInClient;
    public static final int RC_SIGN_IN = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        EditText emailText = findViewById(R.id.email_text);
        EditText passwordText = findViewById(R.id.password_text);

        Button loginButton = findViewById(R.id.login_button);
        Button registrationButton = findViewById(R.id.registration_button);
        Button googleSignIn = findViewById(R.id.google_sign_in_button);

        authManager = FirebaseAuth.getInstance();

        registrationButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent startRegistrationActivityIntent = new Intent(LoginActivity.this, Registration.class);
                startActivity(startRegistrationActivityIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();
                if(!email.equals("") || !password.equals("")){
                    authManager.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                long userId = authManager.getCurrentUser().getUid().hashCode();
                                Intent startMainActityIntent = new Intent(LoginActivity.this, SyncDataSplashActivity.class);
                                startMainActityIntent.putExtra(SyncDataSplashActivity.USER_ID_PARAM, userId);
                                startActivity(startMainActityIntent);
                            }else{
                                TextView loginError = findViewById(R.id.login_error);
                                loginError.setText("Incorrect email or password");
                            }
                        }
                    });
                }else{

                }
            }
        });
        googleSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                googleSignIn();
            }
        });


    }
    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

}
