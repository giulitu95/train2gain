package com.train2gain.train2gain.ui.fragment.athlete;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.train2gain.train2gain.R;

public class DailyWorkoutFragment extends Fragment {

    private final static String ATHLETE_USER_ID_KEY = "ATHLETE_USER_ID_KEY";
    private long athleteUserId = -1;

    public static DailyWorkoutFragment newInstance(final long athleteUserId){
        DailyWorkoutFragment dailyWorkoutFragment = new DailyWorkoutFragment();
        Bundle fragmentArgs = new Bundle();
        fragmentArgs.putLong(DailyWorkoutFragment.ATHLETE_USER_ID_KEY, athleteUserId);
        dailyWorkoutFragment.setArguments(fragmentArgs);
        return dailyWorkoutFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get fragment argument (athlete user id)
        Bundle fragmentParams = getArguments();
        if(fragmentParams != null && fragmentParams.containsKey(DailyWorkoutFragment.ATHLETE_USER_ID_KEY)){
            this.athleteUserId = (Long) fragmentParams.getLong(DailyWorkoutFragment.ATHLETE_USER_ID_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_daily_workout_athlete, parent, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        // TODO add view model managing
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getString(R.string.daily_workout_title));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Nothing to do
    }

}
