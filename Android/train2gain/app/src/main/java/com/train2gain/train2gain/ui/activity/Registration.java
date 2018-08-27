package com.train2gain.train2gain.ui.activity;

import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
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
import java.util.Map;

public class Registration extends AppCompatActivity {

    static final int ACTIVITY_SELECT_IMAGE = 1;

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
    private Trainer trainer = null;
    private User trainerUser = null;
    private TextView trainerLabel;
    private UserType userType = null;
    private CircleImageView addProfileImage;
    private Uri selectedImageUri = null;
    private LinearLayout tokenContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        MediaManager.init(Registration.this);
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
        this.trainerLabel = findViewById(R.id.registration_trainerName_label);
        this.addProfileImage = findViewById(R.id.registration_addProfileImage);
        this.tokenContainer = findViewById(R.id.registration_insertToken_container);
        //initialize firebase authentication
        this.authManager = FirebaseAuth.getInstance();

        //Initializa Volley http request queue
        this.queue = Volley.newRequestQueue(this);

        //Set ButtonListener
        this.registerButton.setOnClickListener(new RegistrationButtonListener());
        userTypeRadio.setOnCheckedChangeListener((RadioGroup group, int checkedId) -> {
                if(checkedId == R.id.athlete_radio){
                    registerButton.setEnabled(false);
                    tokenContainer.setVisibility(View.VISIBLE);
                    userType = UserType.ATHLETE;
                } else {
                    registerButton.setEnabled(true);
                    userType = UserType.TRAINER;
                    tokenContainer.setVisibility(View.GONE);
                }
            }
        );

        this.addProfileImage.setOnClickListener((View v) -> openGallery());


        //when the text in token editText change send an api request to the server to check if exist a trainer qwith that token
        tokenText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                findViewById(R.id.registration_trainerName_label).setVisibility(View.GONE);
                registerButton.setEnabled(false);
                //if token is empty hide the label
                if(!tokenText.getText().toString().equals("")){
                    findViewById(R.id.registration_loadingTrainer_container).setVisibility(View.VISIBLE);
                } else{
                    findViewById(R.id.registration_loadingTrainer_container).setVisibility(View.GONE);
                }
                //For every key pressed start a thread with a timeout, if after the timeout any outher key is pressed the client start the request to the server
                Thread thread = new TokenTrainerThread();
                thread.start();

            }
        });

    }

    private void openGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, ACTIVITY_SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        if(requestCode == ACTIVITY_SELECT_IMAGE && resultCode == RESULT_OK){
            selectedImageUri = imageReturnedIntent.getData();
            addProfileImage.setImageURI(selectedImageUri);
        }
    }

    /*
    *   Thread in wich for every key of keyboard touched I require a Trainer with that token.
    *   The requests have a delay after the key pressed
     */
    class TokenTrainerThread extends Thread{
        @Override
        public void run(){
            try {
                String beforeToken = tokenText.getText().toString();
                sleep(800);
                String updatedToken = tokenText.getText().toString();
                if(beforeToken.equals(updatedToken)){

                    String url ="https://train2gainrest.herokuapp.com/api/trainer/token/" + tokenText.getText().toString();
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, (String response) -> {

                            try {
                                JSONObject json = new JSONObject(response);
                                //check if exixt a trainer with this token
                                if(json.get("content") instanceof  JSONObject){
                                    JSONObject content = (JSONObject) json.get("content");
                                    trainerLabel.setTextColor(Color.GREEN);
                                    trainerLabel.setText((String)content.get("displayName"));
                                    findViewById(R.id.registration_loadingTrainer_container).setVisibility(View.GONE);
                                    trainerLabel.setVisibility(View.VISIBLE);


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

                                    registerButton.setEnabled(true);
                                } else {
                                    trainer = null;
                                    trainerUser = null;
                                    trainerLabel.setTextColor(Color.RED);
                                    trainerLabel.setText(R.string.registration_no_trainer_token);
                                    findViewById(R.id.registration_loadingTrainer_container).setVisibility(View.GONE);
                                    trainerLabel.setVisibility(View.VISIBLE);

                                    registerButton.setEnabled(false);
                                }
                            } catch (JSONException e) {
                                registerButton.setEnabled(false);
                                trainerLabel.setTextColor(Color.RED);
                                trainerLabel.setText(R.string.registration_trainer_token_error);
                                findViewById(R.id.registration_loadingTrainer_container).setVisibility(View.GONE);
                                trainerLabel.setVisibility(View.VISIBLE);
                                e.printStackTrace();
                            }
                        }, (VolleyError error) -> {
                            registerButton.setEnabled(false);
                            trainerLabel.setTextColor(Color.RED);
                            trainerLabel.setText(R.string.registration_trainer_token_error);
                            findViewById(R.id.registration_loadingTrainer_container).setVisibility(View.GONE);
                            trainerLabel.setVisibility(View.VISIBLE);
                        }
                    );
                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);
                }

            } catch (InterruptedException e) {
                trainerLabel.setTextColor(Color.RED);
                trainerLabel.setText(R.string.registration_trainer_token_error);
                findViewById(R.id.registration_loadingTrainer_container).setVisibility(View.GONE);
                trainerLabel.setVisibility(View.VISIBLE);
                e.printStackTrace();
            }
        }
    }

    private void authenticate(String email, String password, String name, String lastName, String profileImageUrl){
        authManager.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Registration.this, (@NonNull Task<AuthResult> task) -> {
            if (task.isSuccessful()) {
                String firebaseUid = authManager.getCurrentUser().getUid();
                //convert firebase string id to remoote database int id
                int databaseUid = firebaseUid.hashCode();

                //prepare user
                Date currentTime = Calendar.getInstance().getTime();
                User user = new User(databaseUid, userType, name.concat(" ").concat(lastName), email, currentTime);
                if(profileImageUrl != null){
                    user.setPhotoUrl(profileImageUrl);
                }
                //send and save user
                UserRepository userRepository = UserRepository.getInstance(Registration.this);
                userRepository.uploadUser(user);

                Intent startActityIntent = null;

                findViewById(R.id.registration_loadingInfo_container).setVisibility(View.GONE);
                if(userType == UserType.ATHLETE) {
                    userRepository.localSaveUser(trainerUser);
                    TrainerRepository trainerRepository = TrainerRepository.getInstance(Registration.this);
                    trainerRepository.localSaveTrainer(trainer);
                    startActityIntent = new Intent(Registration.this, AddAthleteInfoActivity.class);
                    startActityIntent.putExtra("trainerId", trainerUser.getId());
                    startActityIntent.putExtra("athleteId", user.getId());
                } else {
                    startActityIntent = new Intent(Registration.this, SyncDataSplashActivity.class);
                    startActityIntent.putExtra(SyncDataSplashActivity.USER_ID_PARAM, user.getId());
                }
                startActivity(startActityIntent);

            } else {
                Toast err;
                findViewById(R.id.registration_loadingInfo_container).setVisibility(View.GONE);
                registerButton.setEnabled(true);
                try {
                    throw task.getException();
                } catch(FirebaseAuthWeakPasswordException e) {
                    err = Toast.makeText(getApplicationContext(), "password troppo corta", Toast.LENGTH_SHORT);
                    err.show();
                } catch(FirebaseAuthInvalidCredentialsException e) {
                    err = Toast.makeText(getApplicationContext(), "email non valida", Toast.LENGTH_SHORT);
                    err.show();
                } catch(FirebaseAuthUserCollisionException e) {
                    err = Toast.makeText(getApplicationContext(), "utente già registrato", Toast.LENGTH_SHORT);
                    err.show();
                } catch(Exception e) {
                    err = Toast.makeText(getApplicationContext(), "errore di connessione", Toast.LENGTH_SHORT);
                    err.show();
                }
            }
        });
    }
    /*
    *   Listener of Button Registration in which I send all needed requests
     */
    class RegistrationButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            String email = emailText.getText().toString();
            String password = passwordText.getText().toString();
            String name = nameText.getText().toString();
            String lastName = lastNameText.getText().toString();
            String confirmPassword = confirmPasswordText.getText().toString();
            Toast toastError;
            //check if all field are correctly filled
            if(name.equals("")){
                toastError = Toast.makeText(getApplicationContext(), R.string.registration_emptyName_error, Toast.LENGTH_SHORT);
                toastError.show();;
                registerButton.setEnabled(true);
            } else if(lastName.equals("")){
                toastError = Toast.makeText(getApplicationContext(), R.string.registration_emptyLastName_error, Toast.LENGTH_SHORT);
                toastError.show();
                registerButton.setEnabled(true);
            } else if(email.equals("")){
                toastError = Toast.makeText(getApplicationContext(), R.string.registration_emptyLastName_error, Toast.LENGTH_SHORT);
                toastError.show();
                registerButton.setEnabled(true);
            } else if(password.equals("")){
                toastError = Toast.makeText(getApplicationContext(), R.string.registration_emptyPassword_error, Toast.LENGTH_SHORT);
                toastError.show();
                registerButton.setEnabled(true);
            } else if(confirmPassword.equals("")){
                toastError = Toast.makeText(getApplicationContext(), R.string.registration_emptyConfirmPassword_error, Toast.LENGTH_SHORT);
                toastError.show();
                registerButton.setEnabled(true);
            } else if(!confirmPassword.equals(password)){
                toastError = Toast.makeText(getApplicationContext(), R.string.registration_confirmPassword_error, Toast.LENGTH_SHORT);
                toastError.show();
                registerButton.setEnabled(true);
            } else if(userType == null){
                toastError = Toast.makeText(getApplicationContext(), R.string.registration_emptyUserType, Toast.LENGTH_SHORT);
                toastError.show();
                registerButton.setEnabled(true);
            } else if(userType == UserType.ATHLETE && (trainer == null || trainerUser == null)){
                toastError = Toast.makeText(getApplicationContext(), R.string.registration_invalid_token, Toast.LENGTH_SHORT);
                toastError.show();
                registerButton.setEnabled(true);
            } else {

                //show loading container
                findViewById(R.id.registration_loadingInfo_container).setVisibility(View.VISIBLE);
                TextView loadingLabel =  findViewById(R.id.registration_loadingInfo_label);
                loadingLabel.setText(R.string.registration_dbUploading);
                registerButton.setEnabled(false);
                if(selectedImageUri == null){
                    authenticate(email, password, name, lastName, null);
                } else {
                    MediaManager.get().upload(selectedImageUri).option("folder", "profileImages").callback(new UploadCallback() {
                        @Override
                        public void onStart(String requestId) {
                            findViewById(R.id.registration_loadingInfo_container).setVisibility(View.VISIBLE);
                            TextView loadingLabel = findViewById(R.id.registration_loadingInfo_label);
                            loadingLabel.setText(R.string.registration_start_image_upload);
                            registerButton.setEnabled(false);
                        }

                        @Override
                        public void onProgress(String requestId, long bytes, long totalBytes) {
                            findViewById(R.id.registration_loadingInfo_container).setVisibility(View.VISIBLE);
                            TextView loadingLabel = findViewById(R.id.registration_loadingInfo_label);
                            loadingLabel.setText(R.string.registration_upload_image);
                        }

                        @Override
                        public void onSuccess(String requestId, Map resultData) {
                            String profileImageUrl = (String) resultData.get("url");
                            authenticate(email, password, name, lastName, profileImageUrl);
                        }

                        @Override
                        public void onError(String requestId, ErrorInfo error) {
                            registerButton.setEnabled(true);
                        }

                        @Override
                        public void onReschedule(String requestId, ErrorInfo error) {
                            // your code here
                        }
                    }).dispatch();
                }
                //send registration request to firebase

            }
        }
    }
}
