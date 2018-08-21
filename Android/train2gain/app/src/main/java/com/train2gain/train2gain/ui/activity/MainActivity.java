package com.train2gain.train2gain.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.train2gain.train2gain.R;
import com.train2gain.train2gain.model.entity.User;
import com.train2gain.train2gain.model.enums.UserType;
import com.train2gain.train2gain.ui.fragment.HomeAthleteFragment;
import com.train2gain.train2gain.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private View navDrawerHeaderView = null;
    private DrawerLayout drawerLayout = null;
    private NavigationView navigationDrawerView = null;
    private UserViewModel profileViewModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get activity param like UserId
        Bundle activityParams = getIntent().getExtras();
        long userId = -1;
        if(activityParams != null){
            userId = activityParams.getLong(SyncDataSplashActivity.USER_ID_PARAM);
        }

        // Init activity drawer
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Init activity top bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.frame_toolbar);
        setSupportActionBar(toolbar);

        // Sets menu open button on toolbar
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        // Init navigation drawer
        this.navigationDrawerView = (NavigationView) findViewById(R.id.nav_view);
        this.navigationDrawerView.setNavigationItemSelectedListener(menuItem -> {
            menuItem.setChecked(true);
            drawerLayout.closeDrawers();
            // TODO add method to change activity frame
            return true;
        });

        // Init profile view objects
        this.navDrawerHeaderView = (View) this.navigationDrawerView.getHeaderView(0);

        // Set menuHeaderSpinner onClick
        Spinner menuHeaderSpinner = (Spinner) this.navDrawerHeaderView.findViewById(R.id.header_user_type);
        menuHeaderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String userType = (String) parent.getItemAtPosition(pos);
                updateNavDrawerMenu(userType);
                // TODO add method to change activity frame
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Init view model
        this.profileViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        this.profileViewModel.init(this, userId);
        this.profileViewModel.getUser().observe(this, userResource -> {
            if(userResource != null && userResource.getData() != null){
                updateNavDrawerHeaderInfo(userResource.getData());
            }
        });

        // View fragment TODO to fix these lines
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, new HomeAthleteFragment());
        ft.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.drawerLayout.openDrawer(Gravity.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(this.drawerLayout.isDrawerOpen(Gravity.START)){
            this.drawerLayout.closeDrawer(Gravity.START);
        }else{
            super.onBackPressed();
        }
    }

    /**
     * Updates the Navigation drawer header with User info
     * @param user the user object that contains the info we want to display
     */
    private void updateNavDrawerHeaderInfo(@NonNull User user){
        ImageView userImage = (ImageView) this.navDrawerHeaderView.findViewById(R.id.header_user_profile_image);
        TextView userName = (TextView) this.navDrawerHeaderView.findViewById(R.id.header_user_name);
        Spinner userType = (Spinner) this.navDrawerHeaderView.findViewById(R.id.header_user_type);

        // Prepare user types
        String[] userTypes = getString(user.getType().getResourceId()).replaceAll("\\s", "").split(",");
        List<String> userTypesList = new ArrayList<String>(Arrays.asList(userTypes));

        // Set user info..
        userName.setText(user.getDisplayName());
        Picasso.get().load(user.getPhotoUrl()).into(userImage);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.nav_drawer_header_spinner_item, userTypesList);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.nav_drawer_header_spinner_item_dropdown);
        userType.setAdapter(spinnerArrayAdapter);
    }

    /**
     * Updates the Navigation drawer menu items
     * @param userType the type of user (athlete or trainer) that uses the app
     */
    private void updateNavDrawerMenu(String userType){
        navigationDrawerView.getMenu().clear();
        if(userType.equals(getString(UserType.TRAINER.getResourceId()))){
            navigationDrawerView.inflateMenu(R.menu.activity_main_menu_trainer);
        }else{
            navigationDrawerView.inflateMenu(R.menu.activity_main_menu_athlete);
        }
    }

}
