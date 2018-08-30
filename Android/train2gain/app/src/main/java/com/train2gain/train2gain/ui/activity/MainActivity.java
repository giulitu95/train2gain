package com.train2gain.train2gain.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
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
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.train2gain.train2gain.R;
import com.train2gain.train2gain.model.entity.User;
import com.train2gain.train2gain.model.enums.UserType;
import com.train2gain.train2gain.ui.fragment.trainer.HomeFragment;
import com.train2gain.train2gain.viewmodel.UserViewModel;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    // UI elements
    private DrawerLayout drawerLayout = null;
    private NavigationView navigationDrawerView = null;
    private View navDrawerHeaderView = null;
    private Fragment fragmentToInsert = null;

    // Activity User details
    private UserType currentSelectedUserType = null;
    private UserViewModel profileViewModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // User details (minimal)
        long userId = -1;
        UserType userType = null;

        // Get activity param like userId and userType
        Bundle activityParams = getIntent().getExtras();
        if(activityParams != null && activityParams.containsKey(SyncDataSplashActivity.USER_ID_PARAM) && activityParams.containsKey(SyncDataSplashActivity.USER_TYPE_PARAM)){
            userId = activityParams.getLong(SyncDataSplashActivity.USER_ID_PARAM);
            userType = (UserType) activityParams.getSerializable(SyncDataSplashActivity.USER_TYPE_PARAM);
        }else{
            return;
        }

        // Init current user type
        if(userType != null){
            if(userType == UserType.ATHLETE || userType == UserType.BOTH){
                this.currentSelectedUserType = UserType.ATHLETE;
            }else{
                this.currentSelectedUserType = UserType.TRAINER;
            }
        }else{
            return;
        }

        // Init activity top bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.frame_toolbar);
        setSupportActionBar(toolbar);

        // Sets menu open button on toolbar
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        // Init activity drawer and listener
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                if(MainActivity.this.fragmentToInsert != null){
                    replaceContentFrame(MainActivity.this.fragmentToInsert);
                    MainActivity.this.fragmentToInsert = null;
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        // Init navigation drawer
        this.navigationDrawerView = (NavigationView) findViewById(R.id.nav_view);
        this.navigationDrawerView.setNavigationItemSelectedListener(menuItem -> {
            menuItem.setChecked(true);
            onNavigationDrawerItemSelected(menuItem, MainActivity.this.currentSelectedUserType);
            this.drawerLayout.closeDrawer(Gravity.START);
            return true;
        });

        // Init navigation drawer header
        this.navDrawerHeaderView = (View) this.navigationDrawerView.getHeaderView(0);

        // Init spinner (user type chooser)
        Spinner menuHeaderSpinner = (Spinner) this.navDrawerHeaderView.findViewById(R.id.header_user_type);
        menuHeaderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                UserType userType = (UserType) parent.getItemAtPosition(pos);
                MainActivity.this.currentSelectedUserType = userType;
                MainActivity.this.fragmentToInsert = new com.train2gain.train2gain.ui.fragment.athlete.HomeFragment();
                if(userType == UserType.TRAINER) MainActivity.this.fragmentToInsert = new HomeFragment();
                drawerLayout.closeDrawer(Gravity.START);
                updateNavDrawerMenu(userType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nothing to do
            }
        });

        // Init view models
        this.profileViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        this.profileViewModel.getUser(userId).observe(this, userResource -> {
            if(userResource != null && userResource.getData() != null){
                updateNavDrawerHeaderInfo(userResource.getData());
            }
        });

        // Init specific things base on userType
        if(this.currentSelectedUserType == UserType.TRAINER){
            replaceContentFrame(new HomeFragment());
        }else{
            replaceContentFrame(new com.train2gain.train2gain.ui.fragment.athlete.HomeFragment());

        }
        updateNavDrawerMenu(this.currentSelectedUserType);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(this.drawerLayout != null && item.getItemId() == android.R.id.home){
            this.drawerLayout.openDrawer(Gravity.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(this.drawerLayout != null && this.drawerLayout.isDrawerOpen(Gravity.START)){
            this.drawerLayout.closeDrawer(Gravity.START);
        }else{
            super.onBackPressed();
        }
    }

    /**
     * Called when an item in the Navigation Menu Drawer is tapped
     * @param menuItem the menu entry of the navigation drawer that has been tapped
     * @param currentUserType the current selected user type in the navigation drawer
     */
    private void onNavigationDrawerItemSelected(MenuItem menuItem, UserType currentUserType){
        // TODO add code to change 'fragmentToInsert' base on selected menu item
    }

    /**
     * Replaces the main activity content frame with a new fragment, given as input object
     * @param fragmentToBeInserted the fragment that will be inserted in the main activity frame
     */
    private void replaceContentFrame(@NonNull final Fragment fragmentToBeInserted){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragmentToBeInserted);
        fragmentTransaction.commit();
    }

    /**
     * Updates navigation drawer menu's items
     * @param currentUserType the current selected user type in the navigation drawer
     */
    private void updateNavDrawerMenu(@NonNull UserType currentUserType){
        this.navigationDrawerView.getMenu().clear();
        switch(currentUserType){
            case ATHLETE:
                navigationDrawerView.inflateMenu(R.menu.activity_main_menu_athlete);
                break;
            case TRAINER:
                navigationDrawerView.inflateMenu(R.menu.activity_main_menu_trainer);
                break;
        }
    }

    /**
     * Updates the Navigation drawer header with User info
     * @param user the user object that contains the info we want to display
     */
    private void updateNavDrawerHeaderInfo(@NonNull User user){
        CircleImageView userImage = (CircleImageView) this.navDrawerHeaderView.findViewById(R.id.header_user_profile_image);
        TextView userName = (TextView) this.navDrawerHeaderView.findViewById(R.id.header_user_name);
        Spinner userType = (Spinner) this.navDrawerHeaderView.findViewById(R.id.header_user_type);

        // Prepare user types
        List<UserType> userTypesList = new ArrayList<>();
        if(user.getType() == UserType.BOTH){
            userTypesList.add(UserType.ATHLETE);
            userTypesList.add(UserType.TRAINER);
        }else{
            userTypesList.add(user.getType());
        }

        // Prepare spinner Adapter
        ArrayAdapter<UserType> spinnerArrayAdapter = new ArrayAdapter<UserType>(this, R.layout.nav_drawer_header_spinner_item, userTypesList);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.nav_drawer_header_spinner_item_dropdown);
        userType.setAdapter(spinnerArrayAdapter);

        // Set user info..
        userName.setText(user.getDisplayName());
        userType.setAdapter(spinnerArrayAdapter);
        Picasso.get()
               .load(user.getPhotoUrl())
               .placeholder(R.drawable.image_default_profile_picture)
               .error(R.drawable.image_default_profile_picture)
               .into(userImage);

        // If user type is only one, spinner become a TextView
        if(userTypesList.size() == 1){
            userType.setEnabled(false);
            userType.setBackground(null);
        }
    }

}
