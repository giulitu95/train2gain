package com.train2gain.train2gain.adapter.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.train2gain.train2gain.R;
import com.train2gain.train2gain.adapter.model.RecyclerViewItem;
import com.train2gain.train2gain.model.entity.ScheduleDailyWorkout;

public class ScheduleHolder extends RecyclerView.ViewHolder {

    private final TextView dayTextView;

    public ScheduleHolder(View itemView) {
        super(itemView);
        this.dayTextView = (TextView) itemView.findViewById(R.id.daily_workout_day_name);
    }

    public void bindData(@NonNull ScheduleDailyWorkout currentDailyWorkout) {
        this.dayTextView.setText("Day ".concat(String.valueOf(currentDailyWorkout.getOrder() +1)));
    }

}
