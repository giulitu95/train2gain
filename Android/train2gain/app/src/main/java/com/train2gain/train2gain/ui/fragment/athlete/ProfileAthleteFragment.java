package com.train2gain.train2gain.ui.fragment.athlete;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.train2gain.train2gain.R;
import com.train2gain.train2gain.model.entity.Athlete;
import com.train2gain.train2gain.model.entity.User;
import com.train2gain.train2gain.viewmodel.AthleteViewModel;
import com.train2gain.train2gain.viewmodel.UserViewModel;

import java.text.SimpleDateFormat;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileAthleteFragment extends Fragment {

    private final static String ATHLETE_USER_ID_KEY = "ATHLETE_USER_ID_KEY";
    private long athleteUserId = -1;

    // UI elements
    private TextView displayNameTextView = null;
    private TextView heightTextView = null;
    private TextView weightTextView = null;
    private TextView emailTextView = null;
    private TextView userTypeTextView = null;
    private TextView registrationDateTextView = null;
    private TextView firstWorkoutDateTextView = null;
    private CircleImageView profileImageView = null;

    public static ProfileAthleteFragment newInstance(final long athleteUserId){
        ProfileAthleteFragment profileAthleteFragment = new ProfileAthleteFragment();
        Bundle fragmentArgs = new Bundle();
        fragmentArgs.putLong(ProfileAthleteFragment.ATHLETE_USER_ID_KEY, athleteUserId);
        profileAthleteFragment.setArguments(fragmentArgs);
        return profileAthleteFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get fragment argument (athlete user id)
        Bundle fragmentParams = getArguments();
        if(fragmentParams != null && fragmentParams.containsKey(ProfileAthleteFragment.ATHLETE_USER_ID_KEY)){
            this.athleteUserId = (Long) fragmentParams.getLong(ProfileAthleteFragment.ATHLETE_USER_ID_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_athlete, parent, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        // Load user data
        UserViewModel userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        userViewModel.getUser(this.athleteUserId).observe(this, userResource -> {
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

        // Load athlete data
        AthleteViewModel athleteViewModel = ViewModelProviders.of(getActivity()).get(AthleteViewModel.class);
        athleteViewModel.getAthlete(this.athleteUserId).observe(this, athleteResource -> {
            if(athleteResource != null && athleteResource.getData() != null){
                Athlete athlete = athleteResource.getData();

                // Set athlete data
                if(athlete.getHeight() != -1){
                    this.heightTextView.setText(getString(R.string.profile_height_format_string, athlete.getHeight()));
                }
                if(athlete.getHeight() != -1){
                    this.weightTextView.setText(getString(R.string.profile_weight_format_string, athlete.getWeight()));
                }
                if(athlete.getFirstWorkoutDate() != null){
                    // Format date
                    String datePattern = "dd / MM / yyyy";
                    SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern, Locale.getDefault());
                    String firstWorkoutDate = dateFormat.format(athlete.getFirstWorkoutDate());

                    // Set date
                    this.firstWorkoutDateTextView.setText(firstWorkoutDate);
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
        this.displayNameTextView = (TextView) view.findViewById(R.id.profile_athlete_display_name);
        this.heightTextView = (TextView) view.findViewById(R.id.profile_athlete_height);
        this.weightTextView = (TextView) view.findViewById(R.id.profile_athlete_weight);
        this.emailTextView = (TextView) view.findViewById(R.id.profile_athlete_email);
        this.userTypeTextView = (TextView)  view.findViewById(R.id.profile_athlete_user_type);
        this.registrationDateTextView = (TextView)  view.findViewById(R.id.profile_athlete_registration_date);
        this.firstWorkoutDateTextView = (TextView) view.findViewById(R.id.profile_athlete_first_workout);
        this.profileImageView = (CircleImageView) view.findViewById(R.id.profile_athlete_user_profile_image);
    }

}
