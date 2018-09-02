package com.train2gain.train2gain.ui.activity.trainer;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.train2gain.train2gain.R;
import com.train2gain.train2gain.adapter.ScheduleRecyclerViewAdapter;
import com.train2gain.train2gain.model.entity.Schedule;
import com.train2gain.train2gain.model.entity.ScheduleDailyWorkout;
import com.train2gain.train2gain.model.entity.User;
import com.train2gain.train2gain.repository.ScheduleRepository;
import com.train2gain.train2gain.viewmodel.ExerciseViewModel;
import com.train2gain.train2gain.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class CreateNewScheduleActivity extends AppCompatActivity{
    public static final String USER_PARAM_ID = "USER_PARAM_ID";
    public static final int WORKOUT_ACTIVITY_REQUEST_CODE = 1;
    private long trainerId;
    private User selectedAthlete = null;
    private List<User> userList;
    private ArrayList<ScheduleDailyWorkout> dailyWorkoutArrayList;
    private Schedule currentSchedule;
    private ScheduleRecyclerViewAdapter scheduleRecyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ExerciseViewModel exercisevm = ViewModelProviders.of(this).get(ExerciseViewModel.class);
        exercisevm.getExercises(true).observe(this, exerciseListResource ->{

            exerciseListResource.getData();

        });
        setContentView(R.layout.activity_createschedule);
        trainerId = getIntent().getLongExtra(USER_PARAM_ID, 1);
        currentSchedule = new Schedule();
        dailyWorkoutArrayList = new ArrayList<ScheduleDailyWorkout>();
        //get list of athletes of trainer
        UserViewModel uservm = ViewModelProviders.of(this).get(UserViewModel.class);
        uservm.getTrainerUserList(trainerId, 0).observe(this, userListResource ->{

            userList = userListResource.getData();

            if(userList == null || userList.size() == 0){
                showNoUserMessage();
            } else {

                showChooseAthleteButton();
            }

        });
        ImageView removeUserButton = (ImageView) findViewById(R.id.createschedule_removeuser_button);
        removeUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedAthlete = null;
                findViewById(R.id.createschedule_user_container).setVisibility(View.GONE);
                findViewById(R.id.createschedule_selectuser_button).setVisibility(View.VISIBLE);
            }
        });
        RelativeLayout addUser = (RelativeLayout) findViewById(R.id.createschedule_selectuser_button);
        RelativeLayout addWorkout = (RelativeLayout) findViewById(R.id.createschedule_adddailyworkout_button);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        addWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start = new Intent(CreateNewScheduleActivity.this, WorkoutActivity.class);
                startActivityForResult(start, WORKOUT_ACTIVITY_REQUEST_CODE);
            }
        });
        scheduleRecyclerViewAdapter = new ScheduleRecyclerViewAdapter();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.createSchedule_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(this.scheduleRecyclerViewAdapter);

        ImageView confirm = (ImageView) findViewById(R.id.createschedule_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedAthlete == null){
                    Toast.makeText(getApplicationContext(), "Non hai selezionato un atleta", Toast.LENGTH_SHORT).show();
                } else if(dailyWorkoutArrayList.size() == 0){
                    Toast.makeText(getApplicationContext(), "Devi prima aggiungere almeno un workout", Toast.LENGTH_SHORT).show();
                } else {
                    currentSchedule.setAthleteUserId(selectedAthlete.getId());
                    currentSchedule.setTrainerUserId(trainerId);
                    currentSchedule.setStartDate(new Date());
                    ScheduleRepository scheduleRepository = ScheduleRepository.getInstance(getApplicationContext());
                    //send schedule
                    scheduleRepository.uploadSchedule(currentSchedule);

                    finish();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == WORKOUT_ACTIVITY_REQUEST_CODE){
            if(resultCode == RESULT_OK){
               ScheduleDailyWorkout currentDailyWorkout = (ScheduleDailyWorkout) data.getExtras().getSerializable(WorkoutActivity.DAILY_WORKOUT_PARAM);
               HashSet<String> categorySet = (HashSet<String>) data.getExtras().getSerializable(WorkoutActivity.CATEGORY_SET_PARAM);
               currentDailyWorkout.setOrder(dailyWorkoutArrayList.size());
               dailyWorkoutArrayList.add(currentDailyWorkout);
               currentSchedule.setScheduleDailyWorkoutList(dailyWorkoutArrayList);
               this.scheduleRecyclerViewAdapter.setNewRecyclerViewItemList(dailyWorkoutArrayList);
               this.scheduleRecyclerViewAdapter.notifyDataSetChanged();
            }
        }
    }
    private void showNoUserMessage(){
        findViewById(R.id.createschedule_selectuser_button).setVisibility(View.GONE);
        TextView noUserMessage = findViewById(R.id.createschedule_addusermessage_text);
        noUserMessage.setText("Non hai nominato ancora nessun atleta");
        noUserMessage.setVisibility(View.VISIBLE);
    }
    private void showChooseAthleteButton(){
        findViewById(R.id.createschedule_selectuser_button).setVisibility(View.VISIBLE);
        findViewById(R.id.createschedule_addusermessage_text).setVisibility(View.GONE);
    }

    private void showDialog(){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle("Select One Name:-");

        final UserListAdapter arrayAdapter = new UserListAdapter(this, userList);
        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedAthlete = arrayAdapter.getItem(which);
                findViewById(R.id.createschedule_user_container).setVisibility(View.VISIBLE);
                ((TextView)findViewById(R.id.createschedule_user_displayname)).setText(selectedAthlete.getDisplayName());
                ((TextView)findViewById(R.id.createschedule_user_registrationdate)).setText(getStringDate(selectedAthlete.getRegistrationDate()));
                Picasso.get().load(selectedAthlete.getPhotoUrl()).error(R.drawable.image_app_logo).into((ImageView)findViewById(R.id.createschedule_user_image));
                findViewById(R.id.createschedule_addusermessage_text).setVisibility(View.GONE);
                findViewById(R.id.createschedule_selectuser_button).setVisibility(View.GONE);
            }
        });
        builderSingle.show();
    }

    private String getStringDate(Date date){
        return ((String) DateFormat.format("dd", date)).concat( "/")
                .concat((String) DateFormat.format("MM", date)).concat( "/")
                .concat((String) DateFormat.format("yyyy", date));
    }
    class UserListAdapter extends ArrayAdapter<User>{
        public UserListAdapter(Context context, List<User> items) {
            super(context, 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            User user = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_user, parent, false);
            }
            // Lookup view for data population
            TextView name = (TextView) convertView.findViewById(R.id.userlistitem_displayname);
            TextView registrationDate = (TextView) convertView.findViewById(R.id.userlistitem_registrationdate);
            ImageView userImage = (ImageView) convertView.findViewById(R.id.user_image);
            Picasso.get().load(user.getPhotoUrl()).error(R.drawable.image_app_logo).into(userImage);
            // Populate the data into the template view using the data object
            name.setText(user.getDisplayName());
            registrationDate.setText(getStringDate(user.getRegistrationDate()));
            // Return the completed view to render on screen
            return convertView;

        }
    }
    //TODO: resolve problem that when open the second time the list of exercise there aren't exercises
}
