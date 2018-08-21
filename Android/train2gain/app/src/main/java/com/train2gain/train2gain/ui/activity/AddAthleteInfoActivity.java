package com.train2gain.train2gain.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.train2gain.train2gain.R;
import com.train2gain.train2gain.model.entity.Athlete;
import com.train2gain.train2gain.repository.AthleteRepository;

public class AddAthleteInfoActivity extends AppCompatActivity {
    private int trainerId;
    private int athleteId;
    private EditText weightText;
    private EditText heightText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.trainerId =  getIntent().getIntExtra("trainerId", 1);
        this.athleteId = getIntent().getIntExtra("athleteId", 1);
        setContentView(R.layout.activity_addathleteinfo);
        weightText = findViewById(R.id.addathleteinfo_weight_text);
        heightText = findViewById(R.id.addathleteinfo_height_text);
        Button confirm = findViewById(R.id.addathleteinfo_confirm_button);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Athlete athlete = new Athlete();
                athlete.setTrainerUserId(trainerId);
                athlete.setHeight(Integer.parseInt(heightText.getText().toString()));
                athlete.setUserId(athleteId);
                AthleteRepository athleteRepository = AthleteRepository.getInstance(AddAthleteInfoActivity.this);
                athleteRepository.uploadAthlete(athlete);

            }
        });
    }
}
