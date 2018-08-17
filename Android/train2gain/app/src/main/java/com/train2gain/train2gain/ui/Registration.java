package com.train2gain.train2gain.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.train2gain.train2gain.R;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        EditText emailText = findViewById(R.id.registration_email_txt);
        EditText passwordText = findViewById(R.id.registration_password_txt);
        EditText nameText = findViewById(R.id.registration_name_txt);
        EditText lastNameText = findViewById(R.id.registration_lastName_txt);
        EditText confirmPasswordText = findViewById(R.id.registration_confirmPassword_txt);
        RadioGroup userTypeRadio = findViewById(R.id.registration_userType_txt);
        Button cancelButton = findViewById(R.id.registration_cancel_btn);
        Button registerButton = findViewById(R.id.registration_register_btn);

        FirebaseAuth firebase = FirebaseAuth.getInstance();


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();
                String name = nameText.getText().toString();
                String lastName = lastNameText.getText().toString();
                String confirmPassword = confirmPasswordText.getText().toString();

                TextView error = findViewById(R.id.registration_error_txt);
                if(name.equals("")){
                    error.setText("Inserisci il tuo nome");
                } else if(lastName.equals("")){
                    error.setText("Inserisci il tuo cognome");
                } else if(email.equals("")){
                    error.setText("Inserisci la tua email");
                } else if(password.equals("")){
                    error.setText("Inserisci una password");
                } else if(confirmPassword.equals("")){
                    error.setText("Devi confermare la tua password");
                } else if(!confirmPassword.equals(password)){
                    error.setText("password diverse");
                } else {
                    firebase.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            TextView error = findViewById(R.id.registration_error_txt);
                            if (task.isSuccessful()) {
                                Log.d("reg", "Ok");
                            } else {
                                error.setText("qualcosa è andato storto");
                            }
                        }
                    });
                }
            }
        });

    }
}
