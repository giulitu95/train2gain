package com.train2gain.train2gain.ui.fragment.trainer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.train2gain.train2gain.R;

public class HomeTrainerFragment extends Fragment {

    private final static String TRAINER_USER_ID_KEY = "TRAINER_USER_ID_KEY";
    private long trainerUserId = -1;

    public static HomeTrainerFragment newInstance(final long trainerUserId){
        HomeTrainerFragment homeTrainerFragment = new HomeTrainerFragment();
        Bundle fragmentArgs = new Bundle();
        fragmentArgs.putLong(HomeTrainerFragment.TRAINER_USER_ID_KEY, trainerUserId);
        homeTrainerFragment.setArguments(fragmentArgs);
        return homeTrainerFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get fragment argument (trainer user id)
        Bundle fragmentParams = getArguments();
        if(fragmentParams != null && fragmentParams.containsKey(HomeTrainerFragment.TRAINER_USER_ID_KEY)){
            this.trainerUserId = (Long) fragmentParams.getLong(HomeTrainerFragment.TRAINER_USER_ID_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_trainer, parent, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getString(R.string.home_title));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Set listener
        Button goToCreateNewScheduleButton = (Button) view.findViewById(R.id.home_trainer_btn_new_schedule);
        goToCreateNewScheduleButton.setOnClickListener(onClickView -> {
            // TODO open / switch to 'CreateNewSchedule' activity here
        });
        Button goToManageUserButton = (Button) view.findViewById(R.id.home_trainer_btn_manage_users);
        goToManageUserButton.setOnClickListener(onClickView -> {
            // TODO open / switch to 'ManageUsers' activity here
        });
    }

}
