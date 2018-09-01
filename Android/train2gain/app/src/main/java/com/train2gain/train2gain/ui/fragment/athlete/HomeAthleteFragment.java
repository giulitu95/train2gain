package com.train2gain.train2gain.ui.fragment.athlete;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.train2gain.train2gain.R;
import com.train2gain.train2gain.repository.common.Resource;
import com.train2gain.train2gain.ui.activity.MainActivity;
import com.train2gain.train2gain.viewmodel.ScheduleDailyWorkoutViewModel;

public class HomeAthleteFragment extends Fragment {

    private final static String ATHLETE_USER_ID_KEY = "ATHLETE_USER_ID_KEY";
    private long athleteUserId = -1;

    public static HomeAthleteFragment newInstance(final long athleteUserId){
        HomeAthleteFragment homeAthleteFragment = new HomeAthleteFragment();
        Bundle fragmentArgs = new Bundle();
        fragmentArgs.putLong(HomeAthleteFragment.ATHLETE_USER_ID_KEY, athleteUserId);
        homeAthleteFragment.setArguments(fragmentArgs);
        return homeAthleteFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get fragment argument (athlete user id)
        Bundle fragmentParams = getArguments();
        if(fragmentParams != null && fragmentParams.containsKey(HomeAthleteFragment.ATHLETE_USER_ID_KEY)){
            this.athleteUserId = (Long) fragmentParams.getLong(HomeAthleteFragment.ATHLETE_USER_ID_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_athlete, parent, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        ScheduleDailyWorkoutViewModel scheduleDailyWorkoutViewModel = ViewModelProviders.of(getActivity()).get(ScheduleDailyWorkoutViewModel.class);
        scheduleDailyWorkoutViewModel.getScheduleDailyWorkoutOfTheDayMinimal(this.athleteUserId).observe(this, scheduleDailyWorkoutResource -> {
            if(scheduleDailyWorkoutResource != null && scheduleDailyWorkoutResource.getStatus() == Resource.Status.SUCCESS && scheduleDailyWorkoutResource.getData() != null){
                showAthleteHomeAvailableScheduleLayout();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getString(R.string.home_title));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Nothing to do
    }

    private void showAthleteHomeAvailableScheduleLayout(){
        // Hide "no schedule available"
        LinearLayout noScheduleLayout = getView().findViewById(R.id.home_athlete_no_schedule_layout);
        noScheduleLayout.setVisibility(View.GONE);

        // Show "go to daily workout and set listener"
        CardView goToScheduleDailyWorkoutCardView = getView().findViewById(R.id.home_athlete_btn_daily_workout_layout);
        goToScheduleDailyWorkoutCardView.setVisibility(View.VISIBLE);
        Button goToScheduleDailyWorkoutButton = getView().findViewById(R.id.home_athlete_btn_daily_workout);
        goToScheduleDailyWorkoutButton.setOnClickListener((onClickView) -> {
            if(((MainActivity) getActivity()) != null){
                ((MainActivity) getActivity()).replaceContentFrame(DailyWorkoutFragment.newInstance(this.athleteUserId));
            }
        });
    }

}
