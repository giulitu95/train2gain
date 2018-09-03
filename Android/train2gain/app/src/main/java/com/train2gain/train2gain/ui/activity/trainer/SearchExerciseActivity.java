package com.train2gain.train2gain.ui.activity.trainer;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.train2gain.train2gain.R;
import com.train2gain.train2gain.model.entity.Exercise;
import com.train2gain.train2gain.model.enums.MuscleGroup;
import com.train2gain.train2gain.repository.common.Resource;
import com.train2gain.train2gain.viewmodel.ExerciseViewModel;
import com.train2gain.train2gain.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.Iterator;
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
        exerciseRecyclerView = (RecyclerView) findViewById(R.id.exerxises_reciclerview);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView

        exerciseRecyclerView.setHasFixedSize(true);
        SearchView exerciseSearchView = (SearchView) findViewById(R.id.searchexercise_searchview);
        exerciseSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Iterator<Exercise> exerciseListIterator = exerciseList.iterator();
                ArrayList<Exercise> filteredExerciseList = new ArrayList<Exercise>();
                while (exerciseListIterator.hasNext()){
                    Exercise currentExercise = exerciseListIterator.next();
                    if(currentExercise.getName().toLowerCase().startsWith(newText.toLowerCase())){
                        filteredExerciseList.add(currentExercise);
                    }
                }
                exercisesAdapter.updateExerciseList(filteredExerciseList);
                exercisesAdapter.notifyDataSetChanged();
                return false;
            }
        });
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        exerciseRecyclerView.setLayoutManager(layoutManager);
        //get list of athletes of trainer
        ExerciseViewModel exercisevm = ViewModelProviders.of(this).get(ExerciseViewModel.class);
        exercisevm.getExercises(true).observe(this, exerciseListResource ->{
            //ERROR: resolve, the second
            if(exerciseListResource != null &&exerciseListResource.getData() != null ){
                exerciseList = exerciseListResource.getData();
                View.OnClickListener itemContainer = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra(SELECTED_EXERCISE_PARAM, exerciseList.get(exerciseRecyclerView.getChildAdapterPosition(view)));
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                };
                if(exerciseList != null) {
                    exercisesAdapter = new ExercisesAdapter(exerciseList, itemContainer);
                    exerciseRecyclerView.setAdapter(exercisesAdapter);
                }
            }

        });
        // specify an adapter (see also next example)



    }
}
