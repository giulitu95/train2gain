package com.train2gain.train2gain.ui.fragment.trainer;

import android.app.Fragment;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.train2gain.train2gain.R;
import com.train2gain.train2gain.model.entity.User;
import com.train2gain.train2gain.repository.UserRepository;
import com.train2gain.train2gain.viewmodel.UserViewModel;

import java.util.List;

public class SelectUserFragment extends Fragment {

    static final String TRAINERID_PARAM = "TRAINERID_PARAM";
    private long trainerId;
    private ViewModel userListViewModel;
    public SelectUserFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        this.trainerId = getArguments().getLong(TRAINERID_PARAM);
        UserRepository userRepository = UserRepository.getInstance(parent.getContext());
        return inflater.inflate(R.layout.fragment_selectuser, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO do things here..
    }
}
