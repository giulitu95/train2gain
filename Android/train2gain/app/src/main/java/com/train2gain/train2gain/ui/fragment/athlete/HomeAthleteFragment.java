package com.train2gain.train2gain.ui.fragment.athlete;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.train2gain.train2gain.R;

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
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getString(R.string.home_title));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO do things here..
    }
}
