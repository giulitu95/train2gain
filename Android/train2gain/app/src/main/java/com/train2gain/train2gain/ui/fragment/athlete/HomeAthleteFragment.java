package com.train2gain.train2gain.ui.fragment.athlete;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.train2gain.train2gain.R;

public class HomeAthleteFragment extends Fragment {

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
