package com.train2gain.train2gain.ui.activity.trainer;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.train2gain.train2gain.R;
import com.train2gain.train2gain.model.entity.User;
import com.train2gain.train2gain.ui.fragment.trainer.SelectUserFragment;
import com.train2gain.train2gain.viewmodel.UserViewModel;

import java.util.List;

public class ManageWorkoutActivity extends AppCompatActivity {

    private int schedule;
    public static final String USERID_PARAM = "USERID_PARAM";
    private long userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sync_data_splash);
        userId = getIntent().getLongExtra(USERID_PARAM, 1);
        UserViewModel uservm = ViewModelProviders.of(this).get(UserViewModel.class);
        uservm.getTrainerUserList(userId, 0).observe(this, userListResource ->{

            List<User> userList = userListResource.getData();
            if(userList == null || userList.size() == 0){
                //if trainer don't have users
            } else {
                SelectUserFragment
            }

        });

    }

}
