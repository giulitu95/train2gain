package com.train2gain.train2gain.ui.activity.athlete;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.train2gain.train2gain.R;
import com.train2gain.train2gain.model.entity.Athlete;
import com.train2gain.train2gain.repository.AthleteRepository;
import com.train2gain.train2gain.ui.activity.SyncDataSplashActivity;

import java.util.Calendar;

public class AddAthleteInfoActivity extends AppCompatActivity {
    private long trainerId;
    private long athleteId;
    private EditText weightText;
    private EditText heightText;
    private EditText firstWorkoutDateText;
    private Calendar firstWorkoutCalendar;
    private DatePickerDialog.OnDateSetListener firstWorkoutDateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.trainerId =  getIntent().getLongExtra("trainerId", 1);
        this.athleteId = getIntent().getLongExtra("athleteId", 1);
        setContentView(R.layout.activity_addathleteinfo);
        weightText = findViewById(R.id.addathleteinfo_weight_text);
        heightText = findViewById(R.id.addathleteinfo_height_text);
        this.firstWorkoutDateText = findViewById(R.id.addathleteinfo_firstworkoutdate_text);
        firstWorkoutDateText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                firstWorkoutCalendar = Calendar.getInstance();
                int year = firstWorkoutCalendar.get(Calendar.YEAR);
                int month = firstWorkoutCalendar.get(Calendar.MONTH);
                int day = firstWorkoutCalendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog= new DatePickerDialog(
                        AddAthleteInfoActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        firstWorkoutDateListener,
                        year,
                        month,
                        day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });
        firstWorkoutDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month++;
                String date = dayOfMonth + "/" + month + "/" + year;
                firstWorkoutDateText.setText(date);
            }
        };
        Button confirm = findViewById(R.id.addathleteinfo_confirm_button);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Athlete athlete = new Athlete();
                athlete.setTrainerUserId(trainerId);
                athlete.setHeight(Integer.parseInt(heightText.getText().toString()));
                athlete.setUserId(athleteId);
                athlete.setFirstWorkoutDate(firstWorkoutCalendar.getTime());
                athlete.setWeight(Integer.parseInt(weightText.getText().toString()));
                AthleteRepository athleteRepository = AthleteRepository.getInstance(AddAthleteInfoActivity.this);
                athleteRepository.uploadAthlete(athlete);
                Intent startMainActivityIntent = new Intent(AddAthleteInfoActivity.this, SyncDataSplashActivity.class);
                startMainActivityIntent.putExtra(SyncDataSplashActivity.USER_ID_PARAM, athleteId);
                startActivity(startMainActivityIntent);
            }
        });
    }
}
