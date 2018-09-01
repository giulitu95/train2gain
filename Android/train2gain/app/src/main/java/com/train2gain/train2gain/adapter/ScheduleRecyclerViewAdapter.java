package com.train2gain.train2gain.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.train2gain.train2gain.R;
import com.train2gain.train2gain.adapter.holder.CreationStandardSetHolder;
import com.train2gain.train2gain.adapter.holder.HeaderHolder;
import com.train2gain.train2gain.adapter.holder.ScheduleHolder;
import com.train2gain.train2gain.adapter.holder.StandardSetHolder;
import com.train2gain.train2gain.adapter.model.RecyclerViewItem;
import com.train2gain.train2gain.model.entity.ScheduleDailyWorkout;
import com.train2gain.train2gain.model.entity.ScheduleStep;

import java.util.ArrayList;
import java.util.List;

public class ScheduleRecyclerViewAdapter extends RecyclerView.Adapter {

    @NonNull
    private List<ScheduleDailyWorkout> recyclerViewItemList;

    public ScheduleRecyclerViewAdapter(){
        this.recyclerViewItemList = new ArrayList<ScheduleDailyWorkout>();
    }

    public void setNewRecyclerViewItemList(@NonNull final List<ScheduleDailyWorkout> recyclerViewItemList){
        this.recyclerViewItemList = recyclerViewItemList;
    }
    public void setNewRecyclerViewItem(@NonNull final ScheduleDailyWorkout recyclerViewItem){
        this.recyclerViewItemList.add(recyclerViewItem);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_schedule_item, parent, false);
        return new ScheduleHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((ScheduleHolder) holder).bindData((ScheduleDailyWorkout) recyclerViewItemList.get(position));
    }


    @Override
    public int getItemCount() {
        return this.recyclerViewItemList.size();
    }

}
