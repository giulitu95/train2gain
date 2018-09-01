package com.train2gain.train2gain.adapter.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.train2gain.train2gain.R;
import com.train2gain.train2gain.adapter.model.RecyclerViewItem;

public class HeaderHolder extends RecyclerView.ViewHolder {

    private final TextView exerciseTypeTextView;

    public HeaderHolder(View itemView) {
        super(itemView);
        this.exerciseTypeTextView = (TextView) itemView.findViewById(R.id.schedule_step_exercise_type);
    }

    public void bindData(@NonNull final RecyclerViewItem<String> exerciseTypeRecyclerViewItem) {
        this.exerciseTypeTextView.setText(exerciseTypeRecyclerViewItem.getObject());
    }

}
