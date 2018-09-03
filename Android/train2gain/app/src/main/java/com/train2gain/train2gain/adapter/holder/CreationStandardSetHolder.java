package com.train2gain.train2gain.adapter.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.train2gain.train2gain.App;
import com.train2gain.train2gain.R;
import com.train2gain.train2gain.adapter.model.RecyclerViewItem;
import com.train2gain.train2gain.model.entity.Exercise;
import com.train2gain.train2gain.model.entity.ScheduleSet;
import com.train2gain.train2gain.model.entity.ScheduleStep;

import java.util.List;

public class CreationStandardSetHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final ImageView exerciseImageImageView;
    private final TextView exerciseNameTextView;
    private final TextView scheduleSetsTextView;
    private final TextView scheduleStepRestTimeTextView;

    public CreationStandardSetHolder(View itemView) {
        super(itemView);

        // Init layout elements
        this.exerciseImageImageView = (ImageView) itemView.findViewById(R.id.creation_schedule_step_exercise_image);
        this.exerciseNameTextView = (TextView) itemView.findViewById(R.id.creation_schedule_step_exercise_title_txt);
        this.scheduleSetsTextView = (TextView) itemView.findViewById(R.id.creation_schedule_step_sets_txt);
        this.scheduleStepRestTimeTextView = (TextView) itemView.findViewById(R.id.creation_schedule_step_rest_time_txt);
    }

    public void bindData(@NonNull final RecyclerViewItem<ScheduleStep> scheduleStepRecyclerViewItem) {
        // Init objects
        ScheduleStep scheduleStep = scheduleStepRecyclerViewItem.getObject();
        List<ScheduleSet> scheduleSetList = scheduleStep.getScheduleSetList();
        Exercise exercise = scheduleSetList.get(0).getScheduleSetItemList().get(0).getExercise();
        StringBuilder scheduleSetsBuilder = new StringBuilder();
        for(ScheduleSet scheduleSet : scheduleSetList){
            int tmpReps = scheduleSet.getScheduleSetItemList().get(0).getReps();
            scheduleSetsBuilder.append(App.getContext().getResources()
                    .getString(R.string.schedule_step_set_reps_format_string, tmpReps));
            scheduleSetsBuilder.append(" ");
        }

        // Sets layout objects
        this.exerciseNameTextView.setText(exercise.getName());
        this.scheduleSetsTextView.setText(scheduleSetsBuilder.toString());
        this.scheduleStepRestTimeTextView.setText(App.getContext().getResources()
                .getString(R.string.schedule_step_rest_time_format_string, scheduleStep.getRestTime()));
        Picasso.get()
                .load(exercise.getImageUrl())
                .placeholder(R.drawable.image_recycler_view_daily_workout_default)
                .error(R.drawable.image_recycler_view_daily_workout_default)
                .into(this.exerciseImageImageView);

    }

    @Override
    public void onClick(View view) {
        // TODO implementation of InfoButton OnClick listener
    }
}
