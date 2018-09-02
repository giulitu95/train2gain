package com.train2gain.train2gain.adapter.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.train2gain.train2gain.R;
import com.train2gain.train2gain.adapter.model.RecyclerViewItem;
import com.train2gain.train2gain.model.entity.ScheduleDailyWorkout;
import com.train2gain.train2gain.model.entity.ScheduleSet;
import com.train2gain.train2gain.model.entity.ScheduleSetItem;
import com.train2gain.train2gain.model.entity.ScheduleStep;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class ScheduleHolder extends RecyclerView.ViewHolder {

    private final TextView dayTextView;
    private final TextView categoryListTextView;
    public ScheduleHolder(View itemView) {
        super(itemView);
        this.dayTextView = (TextView) itemView.findViewById(R.id.daily_workout_day_name);
        this.categoryListTextView = (TextView) itemView.findViewById(R.id.scheduleitem_categorylist);
    }

    public void bindData(@NonNull ScheduleDailyWorkout currentDailyWorkout) {
        this.dayTextView.setText("Day ".concat(String.valueOf(currentDailyWorkout.getOrder() +1)));
        this.categoryListTextView.setText(TextUtils.join(", ", getCategoryList(currentDailyWorkout)));
    }

    public HashSet<String> getCategoryList(ScheduleDailyWorkout currentDailyWorkout){
        HashSet<String> categorySet = new HashSet<String>();
        List<ScheduleStep> scheduleStepList = currentDailyWorkout.getScheduleStepList();
        Iterator<ScheduleStep> scheduleStepIt = scheduleStepList.iterator();
        while (scheduleStepIt.hasNext()){
            ScheduleStep currentScheduleStep = scheduleStepIt.next();
            List<ScheduleSet> scheduleSetList = currentScheduleStep.getScheduleSetList();
            Iterator<ScheduleSet> scheduleSetIt = scheduleSetList.iterator();
            while(scheduleSetIt.hasNext()){
                ScheduleSet currentScheduleSet = scheduleSetIt.next();
                List<ScheduleSetItem> scheduleSetItemList = currentScheduleSet.getScheduleSetItemList();
                Iterator<ScheduleSetItem> scheduleSetItemIterator = scheduleSetItemList.iterator();
                while(scheduleSetItemIterator.hasNext()){
                    ScheduleSetItem item = scheduleSetItemIterator.next();
                    categorySet.add(item.getExercise().getMuscleGroup().toString());
                }

            }
        }
        return categorySet;
    }

}
