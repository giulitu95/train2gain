package com.train2gain.train2gain.ui.activity.trainer;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.train2gain.train2gain.R;
import com.train2gain.train2gain.model.entity.Exercise;
import com.train2gain.train2gain.model.entity.ScheduleSet;
import com.train2gain.train2gain.model.entity.ScheduleSetItem;
import com.train2gain.train2gain.model.entity.ScheduleStep;
import com.train2gain.train2gain.model.enums.ScheduleStepType;
import com.train2gain.train2gain.repository.common.Resource;
import com.train2gain.train2gain.viewmodel.ExerciseViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ScheduleStepActivity extends AppCompatActivity{


    public static final String SCHEDULESTEP_PARAM = "SCHEDULESTEP_PARRAM";
    private RelativeLayout addExerciseButton;
    private Exercise selectedExercise = null;
    private static final int SEARCH_EXERCISE_REQUEST_CODE = 1;
    private ArrayList<Item> addSetItemLIst;
    LinearLayout setListContainer;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //initialize setList
        addSetItemLIst = new ArrayList<Item>();

        //import item from res
        setContentView(R.layout.activity_schedulestep);
        setListContainer = findViewById(R.id.addexercise_setList);
        addExerciseButton = (RelativeLayout)findViewById(R.id.schedule_step_addexercise_button);
        ImageView clearScheduleStep = findViewById(R.id.schedule_step_clear_button);
        ImageView addSetButton = (ImageView)findViewById(R.id.addexercise_addset_button);
        ImageView confirmButton = (ImageView)findViewById(R.id.schedule_step_confirm);
        ImageView cancelButton = (ImageView)findViewById(R.id.schedule_step_cancel);
        //add listener to elemnts
        ExerciseViewModel exercisevm = ViewModelProviders.of(this).get(ExerciseViewModel.class);
        addExerciseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ScheduleStepActivity.this, SearchExerciseActivity.class);
                startActivityForResult(intent, SEARCH_EXERCISE_REQUEST_CODE);
            }
        });
        clearScheduleStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearScheduleStep();
            }
        });

        addSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = new Item(ScheduleStepActivity.this, addSetItemLIst.size() + 1);
                addSetItemLIst.add(item);
                setListContainer.addView(item);
                updateLastAddSet();

            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText restTime = findViewById(R.id.schedule_step_rest_time_edittext);
                Intent intent = new Intent();
                ScheduleStep scheduleStep = createScheduleStep();
                if(restTime.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Specifica un tempo di recupero", Toast.LENGTH_SHORT).show();
                } else if(selectedExercise == null){
                    Toast.makeText(getApplicationContext(), "Devi scegliere un esercizio", Toast.LENGTH_SHORT).show();
                } else if (scheduleStep == null){
                    Toast.makeText(getApplicationContext(), "Non hai inserito tutte le ripetizioni", Toast.LENGTH_SHORT).show();
                } else {
                    scheduleStep.setRestTime(Integer.parseInt(restTime.getText().toString()));
                    intent.putExtra(SCHEDULESTEP_PARAM, scheduleStep);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == SEARCH_EXERCISE_REQUEST_CODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                selectedExercise = (Exercise) data.getExtras().getSerializable(SearchExerciseActivity.SELECTED_EXERCISE_PARAM);
                addExerciseButton.setVisibility(View.GONE);
                findViewById(R.id.schedule_step_exercise_item).setVisibility(View.VISIBLE);
                TextView category = findViewById(R.id.schedule_step_category_text);
                TextView exerciseName = findViewById(R.id.schedule_step_exercise_title);
                Picasso.get().load(selectedExercise.getImageUrl()).error(R.drawable.image_app_logo).into((ImageView)findViewById(R.id.schedule_step_exercise_image));
                category.setText(selectedExercise.getMuscleGroup().toString());
                exerciseName.setText(selectedExercise.getName());
            }
        }
    }
    private void clearScheduleStep(){
        selectedExercise = null;
        findViewById(R.id.schedule_step_exercise_item).setVisibility(View.GONE);
        addExerciseButton.setVisibility(View.VISIBLE);
    }
    private void updateLastAddSet(){
        TextView lastSetNumber = findViewById(R.id.schedule_step_last__setnumber);
        lastSetNumber.setText(addSetItemLIst.size()+1+"");
    }

    private ScheduleStep createScheduleStep(){

        if(addSetItemLIst.size() == 0)
            return null;
        ScheduleStep scheduleStep =  new ScheduleStep();
        ArrayList<ScheduleSet> scheduleSetList = new ArrayList<ScheduleSet>();
        Iterator<Item> it = addSetItemLIst.iterator();
        while (it.hasNext()){
            Item rep = it.next();
            int numberOfReps = rep.getReps();
            if(numberOfReps == -1){
                return null;
            } else {
                ScheduleSetItem setItem = new ScheduleSetItem();
                setItem.setOrder(0);
                setItem.setExerciseId(selectedExercise.getId());
                setItem.setExercise(selectedExercise);
                setItem.setReps(numberOfReps);

                ScheduleSet set = new ScheduleSet();
                set.setOrder(rep.getSetNumber() - 1);

                List<ScheduleSetItem> scheduleSetItemlist = new ArrayList<ScheduleSetItem>();
                scheduleSetItemlist.add(setItem);
                set.setScheduleSetItemList(scheduleSetItemlist);

                scheduleSetList.add(set);

            }

        }
        scheduleStep.setScheduleSetList(scheduleSetList);
        scheduleStep.setType(ScheduleStepType.STANDARD_SET);
        return scheduleStep;
    }






    class Item extends LinearLayout{

        private int number;
        private int setNumber;
        private TextView setIndexText;
        public Item(Context context, int setNumberParam){
            super(context);
            this.setNumber = setNumberParam;
            this.number = -1;
            LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mInflater.inflate(R.layout.element_set_insert, this, true);
            EditText setNumberText =  findViewById(R.id.elemente_set_insert_setnumber);
            setIndexText = findViewById(R.id.element_set_insert_setnumber);
            setIndexText.setText(setNumber+ "");
            ImageView removeSetButton = findViewById(R.id.element_set_remove);
            removeSetButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addSetItemLIst.remove(Item.this);
                    for(int i = setNumber - 1; i < addSetItemLIst.size(); i++){
                        addSetItemLIst.get(i).setSetNumber(i +1);
                    }
                    setListContainer.removeView(Item.this);
                    updateLastAddSet();
                }
            });
            setNumberText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(setNumberText.getText().toString().equals("")){
                        number = -1;
                    } else {
                        number = Integer.parseInt(setNumberText.getText().toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }

        public void setSetNumber(int setNumber) {
            this.setNumber = setNumber;
            setIndexText.setText(setNumber+"");
        }
        public int getReps(){
            return this.number;
        }
        public int getSetNumber(){
            return this.setNumber;
        }

    }


}
