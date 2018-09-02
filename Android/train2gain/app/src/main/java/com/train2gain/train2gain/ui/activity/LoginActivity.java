package com.train2gain.train2gain.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.train2gain.train2gain.R;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth authManager;
    private EditText emailText;
    private EditText passwordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MediaManager.init(getApplicationContext());
        emailText = findViewById(R.id.email_text);
        passwordText = findViewById(R.id.password_text);

        Button loginButton = findViewById(R.id.login_button);
        Button registrationButton = findViewById(R.id.registration_button);

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
                if(!email.equals("") && !password.equals("")){
                    authManager.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                long userId = authManager.getCurrentUser().getUid().hashCode();
                                Intent startMainActityIntent = new Intent(LoginActivity.this, SyncDataSplashActivity.class);
                                startMainActityIntent.putExtra(SyncDataSplashActivity.USER_ID_PARAM, userId);
                                startActivity(startMainActityIntent);
                            }else{
                                Toast.makeText(getApplicationContext(), "Email o password errati", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(), "Specifica email e password", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
