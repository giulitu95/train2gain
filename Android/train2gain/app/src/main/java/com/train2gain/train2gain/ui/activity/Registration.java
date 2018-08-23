package com.train2gain.train2gain.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import de.hdodenhof.circleimageview.CircleImageView;
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
import com.squareup.picasso.Picasso;
import com.train2gain.train2gain.R;
import com.train2gain.train2gain.model.entity.Gym;
import com.train2gain.train2gain.model.entity.Trainer;
import com.train2gain.train2gain.model.entity.User;
import com.train2gain.train2gain.model.enums.UserType;
import com.train2gain.train2gain.repository.TrainerRepository;
import com.train2gain.train2gain.repository.UserRepository;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
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
    private FirebaseAuth authManager;
    private Trainer trainer;
    private User trainerUser;
    private TextView trainerLabel;
    private UserType userType;
    private CircleImageView addProfileImage;
    static final int ACTIVITY_SELECT_IMAGE = 1;
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
        this.registerButton.setOnClickListener(new RegistrationButtonListener());
        this.queue = Volley.newRequestQueue(this);
        this.trainerLabel = findViewById(R.id.registration_trainarName_label);
        this.addProfileImage = findViewById(R.id.registration_addProfileImage);
        this.userType = null;
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

        this.addProfileImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openGallery();
            }
        });

        //whent the text in token editText change send an api request to the server to check if exist a trainer qwith that token
        tokenText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //if token is empty hide the label
                if(!tokenText.getText().toString().equals("")){
                    findViewById(R.id.registration_ladingTrainer_container).setVisibility(View.VISIBLE);
                    findViewById(R.id.registration_trainerName_container).setVisibility(View.GONE);
                } else{
                    findViewById(R.id.registration_ladingTrainer_container).setVisibility(View.GONE);
                    findViewById(R.id.registration_trainerName_container).setVisibility(View.GONE);
                }
                //For every key pressed start a thread with a timeout, if after the timeout any outher key is pressed the client start the request to the server
                Thread thread = new TokenTrainerThread();
                thread.start();

            }
        });

    }
    private void openGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, this.ACTIVITY_SELECT_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        if(requestCode == this.ACTIVITY_SELECT_IMAGE && resultCode == RESULT_OK){
            Uri selectedImageUri = imageReturnedIntent.getData();
            addProfileImage.setImageURI(selectedImageUri);
        }
    }


    class TokenTrainerThread extends Thread{
        @Override
        public void run(){
            try {
                String beforeToken = tokenText.getText().toString();
                sleep(800);
                String updatedToken = tokenText.getText().toString();
                if(beforeToken.equals(updatedToken)){

                    String url ="https://train2gainrest.herokuapp.com/api/trainer/token/" + tokenText.getText().toString();
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            findViewById(R.id.registration_ladingTrainer_container).setVisibility(View.GONE);
                            findViewById(R.id.registration_trainerName_container).setVisibility(View.VISIBLE);

                            try {
                                JSONObject json = new JSONObject(response);
                                //check if exixt a trainer with this token
                                if(json.get("content") instanceof  JSONObject){
                                    JSONObject content = (JSONObject) json.get("content");
                                    trainerLabel.setTextColor(Color.GREEN);
                                    trainerLabel.setText((String)content.get("displayName"));


                                    //******** Fill java objects with JSON relatives JSON fields +++++++++++++
                                    // require values from json
                                    JSONObject trainerJSON = (JSONObject) content.get("trainer");
                                    JSONObject gymJSON = (JSONObject) trainerJSON.get("gym");

                                    //instantiation of a  gym
                                    Gym gym = new Gym();
                                    gym.setId(gymJSON.getInt("id"));
                                    gym.setLogoUrl(gymJSON.getString("logoUrl"));
                                    gym.setName(gymJSON.getString("gymName"));

                                    //instantiation of a trainer
                                    trainer = new Trainer();
                                    trainer.setGym(gym);
                                    trainer.setUserId(trainerJSON.getInt("id"));
                                    trainer.setGymId(trainerJSON.getInt("gymId"));

                                    //instantiation of a user
                                    trainerUser = new User();
                                    trainerUser.setDisplayName(content.getString("displayName"));
                                    trainerUser.setEmail(content.getString("email"));
                                    trainerUser.setId(content.getInt("id"));
                                    trainerUser.setPhotoUrl(content.getString("profileImageUrl"));
                                    trainerUser.setRegistrationDate(new Date(content.getInt("registrationDate")));
                                    trainerUser.setType(UserType.getFromKey(content.getInt("userType")));
                                } else {
                                    trainerLabel.setTextColor(Color.RED);
                                    trainerLabel.setText("non ho trovato trainer con questo nome");
                                }
                            } catch (JSONException e) {
                                trainerLabel.setTextColor(Color.RED);
                                trainerLabel.setText("mmh c'è qualcosa che non va");
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            trainerLabel.setTextColor(Color.RED);
                            trainerLabel.setText("mmh c'è qualcosa che non va");
                        }
                    });
                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);
                }

            } catch (InterruptedException e) {
                trainerLabel.setTextColor(Color.RED);
                trainerLabel.setText("mmh c'è qualcosa che non va");
                e.printStackTrace();
            }
        }
    }

    class RegistrationButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            String email = emailText.getText().toString();
            String password = passwordText.getText().toString();
            String name = nameText.getText().toString();
            String lastName = lastNameText.getText().toString();
            String confirmPassword = confirmPasswordText.getText().toString();
            TextView error = findViewById(R.id.registration_error_txt);

            //check userType choice
            if(userTypeRadio.getCheckedRadioButtonId() == R.id.athlete_radio){
                userType = UserType.ATHLETE;
            } else if(userTypeRadio.getCheckedRadioButtonId() == R.id.coach_radio){
                userType = UserType.TRAINER;
            }

            //check if all field are correctly filled
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
            } else if(userType == null){
                error.setText("seleziona la tipologia di utente");
            } else {
                //show loading container
                findViewById(R.id.registration_loadingInfo_container).setVisibility(View.VISIBLE);
                TextView loadingLabel =  findViewById(R.id.registration_loadingInfo_label);
                loadingLabel.setText("Upload delle informazioni");

                //send registration request to firebase
                authManager.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        TextView error = findViewById(R.id.registration_error_txt);
                        if (task.isSuccessful()) {
                            String firebaseUid = authManager.getCurrentUser().getUid();
                            //convert firebase string id to remoote database int id
                            int databaseUid = firebaseUid.hashCode();
                            Date currentTime = Calendar.getInstance().getTime();
                            User athleteUser = new User(databaseUid, userType, name.concat(" ").concat(lastName), email, currentTime);
                            //TODO: upload profile image
                            UserRepository userRepository = UserRepository.getInstance(Registration.this);
                            userRepository.uploadUser(athleteUser);
                            userRepository.localSaveUser(trainerUser);
                            TrainerRepository trainerRepository = TrainerRepository.getInstance(Registration.this);
                            trainerRepository.localSaveTrainer(trainer);
                            Intent startActityIntent = new Intent(Registration.this, AddAthleteInfoActivity.class);
                            startActityIntent.putExtra("trainerId", trainerUser.getId());
                            startActityIntent.putExtra("athleteId", athleteUser.getId());
                            startActivity(startActityIntent);
                        } else {
                            error.setText("oops qualcosa è andato storto");
                        }
                    }
                });
            }
        }
    }
}
