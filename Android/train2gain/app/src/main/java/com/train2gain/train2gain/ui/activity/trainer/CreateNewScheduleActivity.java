package com.train2gain.train2gain.ui.activity.trainer;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.train2gain.train2gain.R;
import com.train2gain.train2gain.model.entity.User;
import com.train2gain.train2gain.viewmodel.UserViewModel;

import java.util.List;

public class CreateNewScheduleActivity extends AppCompatActivity{
    public static final String USER_PARAM_ID = "USER_PARAM_ID";
    private long trainerId;
    private User selectedAthlete = null;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createschedule);
        trainerId = getIntent().getLongExtra(USER_PARAM_ID, 1);

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
        Button addUser = (Button) findViewById(R.id.createschedule_selectuser_button);
        Button addWorkout = (Button) findViewById(R.id.createschedule_adddailyworkout_button);
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
                startActivity(start);
            }
        });

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
            }
        });
        builderSingle.show();
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
            // Populate the data into the template view using the data object
            name.setText(user.getDisplayName());
            registrationDate.setText("Registration date: " + (String) DateFormat.format("dd", user.getRegistrationDate()) + "/" +
                                                             (String) DateFormat.format("MM", user.getRegistrationDate()) + "/" +
                                                             (String) DateFormat.format("YYYY", user.getRegistrationDate()) + "/");
            // Return the completed view to render on screen
            return convertView;

        }
    }
}
