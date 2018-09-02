package com.train2gain.train2gain.ui.activity.trainer;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.train2gain.train2gain.R;
import com.train2gain.train2gain.model.entity.Exercise;
import com.train2gain.train2gain.model.enums.MuscleGroup;
import com.train2gain.train2gain.viewmodel.ExerciseViewModel;
import com.train2gain.train2gain.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchExerciseActivity extends AppCompatActivity{
    public static final String SELECTED_EXERCISE_PARAM = "SELECTED_EXERCISE_PARAM";
    private RecyclerView exerciseRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ExercisesAdapter exercisesAdapter;
    private List<Exercise> exerciseList;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchexercise);

        exerciseList = new ArrayList<Exercise>();
        Exercise a = new Exercise();
        a.setId(1); a.setName("esercizio1"); a.setDescription("description"); a.setMuscleGroup(MuscleGroup.CHEST);

        exerciseList.add(a);
        Exercise b = new Exercise();
        b.setId(2); b.setName("exercizio2"); a.setDescription("description"); a.setMuscleGroup(MuscleGroup.CALF);
        exerciseList.add(b);
        exerciseRecyclerView = (RecyclerView) findViewById(R.id.exerxises_reciclerview);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        exerciseRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        exerciseRecyclerView.setLayoutManager(layoutManager);
        //get list of athletes of trainer
        ExerciseViewModel exercisevm = ViewModelProviders.of(this).get(ExerciseViewModel.class);
        exercisevm.getExercises(true).observe(this, exerciseListResource ->{
            //ERROR: resolve, the second
            exerciseList = exerciseListResource.getData();
            View.OnClickListener itemContainer = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int itemPosition = exerciseRecyclerView.getChildAdapterPosition(view);
                    Intent intent = new Intent();
                    intent.putExtra(SELECTED_EXERCISE_PARAM, itemPosition);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            };
            if(exerciseList != null) {
                exercisesAdapter = new ExercisesAdapter(exerciseList, itemContainer);
                exerciseRecyclerView.setAdapter(exercisesAdapter);
            }

        });
        // specify an adapter (see also next example)



    }
}
