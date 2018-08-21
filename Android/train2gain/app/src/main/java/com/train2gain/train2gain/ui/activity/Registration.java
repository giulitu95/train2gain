package com.train2gain.train2gain.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.train2gain.train2gain.R;
import com.train2gain.train2gain.model.entity.Gym;
import com.train2gain.train2gain.model.entity.Trainer;
import com.train2gain.train2gain.model.entity.User;
import com.train2gain.train2gain.model.enums.UserType;
import com.train2gain.train2gain.repository.TrainerRepository;
import com.train2gain.train2gain.repository.UserRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

public class Registration extends AppCompatActivity {

    private EditText emailText;
    private EditText passwordText;
    private EditText nameText;
    private EditText lastNameText;
    private EditText confirmPasswordText;
    private RadioGroup userTypeRadio;
    private Button cancelButton;
    private Button registerButton;
    private EditText tokenText;
    private RequestQueue queue;
    private int trainerId;
    private FirebaseAuth authManager;
    private Trainer trainer;
    private User trainerUser;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        // Import View element from res
        this.emailText = findViewById(R.id.registration_email_txt);
        this.passwordText = findViewById(R.id.registration_password_txt);
        this.nameText = findViewById(R.id.registration_name_txt);
        this.lastNameText = findViewById(R.id.registration_lastName_txt);
        this.confirmPasswordText = findViewById(R.id.registration_confirmPassword_txt);
        this.userTypeRadio = findViewById(R.id.registration_userType_txt);
        this.cancelButton = findViewById(R.id.registration_cancel_btn);
        this.registerButton = findViewById(R.id.registration_register_btn);
        this.tokenText = findViewById(R.id.token_txt);
        this.authManager = FirebaseAuth.getInstance();
        registerButton.setOnClickListener(new RegistrationButtonListener());
        this.queue = Volley.newRequestQueue(this);
        userTypeRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.athlete_radio){
                    tokenText.setVisibility(View.VISIBLE);
                } else {
                    tokenText.setVisibility(View.GONE);
                }
            }
        });
        tokenText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!tokenText.getText().toString().equals("")){
                    findViewById(R.id.registration_ladingTrainer_container).setVisibility(View.VISIBLE);
                    findViewById(R.id.registration_trainerName_container).setVisibility(View.GONE);
                } else{
                    findViewById(R.id.registration_ladingTrainer_container).setVisibility(View.GONE);
                    findViewById(R.id.registration_trainerName_container).setVisibility(View.GONE);
                }
                //For every key pressed start a thread with a timeout, if after the timeout any outher key is pressed the client start the request to the server
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            String before = tokenText.getText().toString();
                            sleep(800);
                            if(before.equals(tokenText.getText().toString())){
                                // Instantiate the RequestQueue.
                                String url ="https://train2gainrest.herokuapp.com/api/trainer/token/" + tokenText.getText();
                                // Request a string response from the provided URL.
                                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        findViewById(R.id.registration_ladingTrainer_container).setVisibility(View.GONE);
                                        findViewById(R.id.registration_trainerName_container).setVisibility(View.VISIBLE);
                                        TextView mTextView = findViewById(R.id.registration_trainarName_label);
                                        try {
                                            JSONObject json = new JSONObject(response);
                                            if(json.get("content") instanceof  JSONObject){
                                                JSONObject content = (JSONObject) json.get("content");
                                                mTextView.setTextColor(Color.GREEN);
                                                mTextView.setText((String)content.get("displayName"));
                                                trainerId = content.getInt("id");
                                                Gym gym = new Gym();
                                                JSONObject trainerJSON = (JSONObject) content.get("trainer");
                                                JSONObject gymJSON = (JSONObject) trainerJSON.get("gym");
                                                gym.setId(gymJSON.getInt("id"));
                                                gym.setLogoUrl(gymJSON.getString("logoUrl"));
                                                gym.setName(gymJSON.getString("gymName"));
                                                trainer = new Trainer();
                                                trainer.setGym(gym);
                                                trainer.setUserId(trainerJSON.getInt("id"));
                                                trainer.setGymId(trainerJSON.getInt("gymId"));
                                                trainerUser = new User();
                                                trainerUser.setDisplayName(content.getString("displayName"));
                                                trainerUser.setEmail(content.getString("email"));
                                                trainerUser.setId(content.getInt("id"));
                                                trainerUser.setPhotoUrl(content.getString("profileImageUrl"));
                                                trainerUser.setRegistrationDate(null); //////////////////////////////////////////////////TODO
                                                trainerUser.setType(UserType.getFromKey(content.getInt("userType")));
                                            } else {
                                                mTextView.setTextColor(Color.RED);
                                                mTextView.setText("non ho trovato trainer con questo nome");
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("res", "error");
                                    }
                                });
                                // Add the request to the RequestQueue.
                                queue.add(stringRequest);
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };

                thread.start();

            }
        });

    }


    class RegistrationButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            String email = emailText.getText().toString();
            String password = passwordText.getText().toString();
            String name = nameText.getText().toString();
            String lastName = lastNameText.getText().toString();
            String confirmPassword = confirmPasswordText.getText().toString();
            UserType  userType;

            TextView error = findViewById(R.id.registration_error_txt);
            if(userTypeRadio.getCheckedRadioButtonId() == R.id.athlete_radio){
                userType = UserType.ATHLETE;
            } else if(userTypeRadio.getCheckedRadioButtonId() == R.id.coach_radio){
                userType = UserType.TRAINER;
            } else{
                userType = null;
            }

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
                findViewById(R.id.registration_loadingInfo_container).setVisibility(View.VISIBLE);
                TextView loadingLabel =  findViewById(R.id.registration_loadingInfo_label);
                loadingLabel.setText("Upload delle informazioni");
                authManager.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        TextView error = findViewById(R.id.registration_error_txt);
                        if (task.isSuccessful()) {
                            String firebaseUid = authManager.getCurrentUser().getUid();
                            int databaseUid = firebaseUid.hashCode();
                            Date currentTime = Calendar.getInstance().getTime();
                            User athleteUser = new User(databaseUid, userType, name.concat(" ").concat(lastName), email, currentTime);
                            UserRepository userRepository = UserRepository.getInstance(Registration.this);
                            userRepository.uploadUser(athleteUser);
                            userRepository.localSaveUser(trainerUser);
                            TrainerRepository trainerRepository = TrainerRepository.getInstance(Registration.this);
                            trainerRepository.localSaveTrainer(trainer);
                            Intent startActityIntent = new Intent(Registration.this, AddAthleteInfoActivity.class);
                            startActityIntent.putExtra("trainerId", trainerId);
                            startActityIntent.putExtra("athleteId", databaseUid);
                            startActivity(startActityIntent);
                        } else {
                            error.setText("qualcosa è andato storto");
                        }
                    }
                });
            }
        }
    }
}
