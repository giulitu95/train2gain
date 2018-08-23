package com.train2gain.train2gain.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.train2gain.train2gain.R;
import com.train2gain.train2gain.service.SyncDataTask;

public class SyncDataSplashActivity extends AppCompatActivity implements SyncDataTask.CallbackInterface {

    public static final String USER_ID_PARAM = "USER_ID_PARAM";
    private long userId = 6;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_data_splash);

        // Init user for which we want info
        // TODO fix this, get from parameters
        userId = getIntent().getLongExtra(this.USER_ID_PARAM, 1);
        // Init Sync thread task
        Button tryAgainButton = (Button) findViewById(R.id.sync_data_splash_btn_try_again);
        tryAgainButton.setOnClickListener((View listenerView) -> {
            showSyncDataProgressInfo();
            SyncDataTask syncDataTask = new SyncDataTask(SyncDataSplashActivity.this, SyncDataSplashActivity.this);
            syncDataTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, userId);
        });

        // Execute first update
        SyncDataTask syncDataTask = new SyncDataTask(SyncDataSplashActivity.this, SyncDataSplashActivity.this);
        syncDataTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, userId);
    }

    @Override
    public void onSyncDataFailed(){
        hideSyncDataProgressInfo();
    }

    @Override
    public void onSyncDataSuccessful(){
        Intent startMainActivityIntent = new Intent(this, MainActivity.class);
        startMainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startMainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startMainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startMainActivityIntent.putExtra(USER_ID_PARAM, this.userId);
        startActivity(startMainActivityIntent);
    }

    private void showSyncDataProgressInfo(){
        ((TextView) findViewById(R.id.sync_data_splash_error)).setVisibility(View.GONE);
        ((Button) findViewById(R.id.sync_data_splash_btn_try_again)).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.sync_data_splash_info)).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.sync_data_splash_info_2)).setVisibility(View.VISIBLE);
        ((ProgressBar) findViewById(R.id.sync_data_splash_progress)).setVisibility(View.VISIBLE);
    }

    private void hideSyncDataProgressInfo(){
        ((TextView) findViewById(R.id.sync_data_splash_info)).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.sync_data_splash_info_2)).setVisibility(View.GONE);
        ((ProgressBar) findViewById(R.id.sync_data_splash_progress)).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.sync_data_splash_error)).setVisibility(View.VISIBLE);
        ((Button) findViewById(R.id.sync_data_splash_btn_try_again)).setVisibility(View.VISIBLE);
    }

}
