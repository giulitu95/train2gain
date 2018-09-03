package com.train2gain.train2gain.ui.fragment.trainer;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.train2gain.train2gain.R;
import com.train2gain.train2gain.model.entity.Trainer;
import com.train2gain.train2gain.model.entity.User;
import com.train2gain.train2gain.viewmodel.TrainerViewModel;
import com.train2gain.train2gain.viewmodel.UserViewModel;

import java.text.SimpleDateFormat;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileTrainerFragment extends Fragment {

    private final static String TRAINER_USER_ID_KEY = "TRAINER_USER_ID_KEY";
    private long trainerUserId = -1;

    // UI elements
    private TextView displayNameTextView = null;
    private TextView emailTextView = null;
    private TextView userTypeTextView = null;
    private TextView registrationDateTextView = null;
    private TextView tokenTextView = null;
    private CircleImageView profileImageView = null;

    public static ProfileTrainerFragment newInstance(final long trainerUserId){
        ProfileTrainerFragment profileTrainerFragment = new ProfileTrainerFragment();
        Bundle fragmentArgs = new Bundle();
        fragmentArgs.putLong(ProfileTrainerFragment.TRAINER_USER_ID_KEY, trainerUserId);
        profileTrainerFragment.setArguments(fragmentArgs);
        return profileTrainerFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get fragment argument (trainer user id)
        Bundle fragmentParams = getArguments();
        if(fragmentParams != null && fragmentParams.containsKey(ProfileTrainerFragment.TRAINER_USER_ID_KEY)){
            this.trainerUserId = (Long) fragmentParams.getLong(ProfileTrainerFragment.TRAINER_USER_ID_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_trainer, parent, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        // Load user data
        UserViewModel userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        userViewModel.getUser(this.trainerUserId).observe(this, userResource -> {
            if(userResource != null && userResource.getData() != null){
                User userData = userResource.getData();

                // Format date
                String datePattern = "dd / MM / yyyy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern, Locale.getDefault());
                String userRegistrationDate = dateFormat.format(userData.getRegistrationDate());

                // Set data to layout
                this.displayNameTextView.setText(userData.getDisplayName());
                this.emailTextView.setText(userData.getEmail());
                this.userTypeTextView.setText(userData.getType().toString());
                this.registrationDateTextView.setText(userRegistrationDate);
                Picasso.get()
                       .load(userData.getPhotoUrl())
                       .placeholder(R.drawable.image_default_profile_picture)
                       .error(R.drawable.image_default_profile_picture)
                       .into(this.profileImageView);
            }
        });

        // Load trainer data
        TrainerViewModel trainerViewModel = ViewModelProviders.of(getActivity()).get(TrainerViewModel.class);
        trainerViewModel.getTrainer(this.trainerUserId).observe(this, trainerResource -> {
            if(trainerResource != null && trainerResource.getData() != null) {
                Trainer trainerData = trainerResource.getData();
                if(!trainerData.getToken().trim().isEmpty()){
                    this.tokenTextView.setText(trainerResource.getData().getToken());
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getString(R.string.profile_title));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Init layout things
        this.displayNameTextView = (TextView) view.findViewById(R.id.profile_trainer_display_name);
        this.emailTextView = (TextView) view.findViewById(R.id.profile_trainer_email);
        this.userTypeTextView = (TextView)  view.findViewById(R.id.profile_trainer_user_type);
        this.registrationDateTextView = (TextView)  view.findViewById(R.id.profile_trainer_registration_date);
        this.tokenTextView = (TextView) view.findViewById(R.id.profile_trainer_token);
        this.profileImageView = (CircleImageView) view.findViewById(R.id.profile_trainer_user_profile_image);
    }

}
