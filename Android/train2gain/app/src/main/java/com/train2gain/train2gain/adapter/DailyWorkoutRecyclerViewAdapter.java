package com.train2gain.train2gain.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.train2gain.train2gain.R;
import com.train2gain.train2gain.adapter.holder.HeaderHolder;
import com.train2gain.train2gain.adapter.holder.StandardSetHolder;
import com.train2gain.train2gain.adapter.model.RecyclerViewItem;
import com.train2gain.train2gain.model.entity.ScheduleStep;

import java.util.ArrayList;
import java.util.List;

public class DailyWorkoutRecyclerViewAdapter extends RecyclerView.Adapter {

    @NonNull
    private List<RecyclerViewItem> recyclerViewItemList;

    public DailyWorkoutRecyclerViewAdapter(){
        this.recyclerViewItemList = new ArrayList<RecyclerViewItem>();
    }

    public void setNewRecyclerViewItemList(@NonNull final List<RecyclerViewItem> recyclerViewItemList){
        this.recyclerViewItemList = recyclerViewItemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        if(viewType == RecyclerViewItem.ItemType.HEADER.ordinal()){
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_daily_workout_item_header, parent, false);
            return new HeaderHolder(view);
        }else if(viewType == RecyclerViewItem.ItemType.STANDARD_SET.ordinal()){
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_daily_workout_item_schedule_step, parent, false);
            return new StandardSetHolder(view);
        }else{
            // This point should never be reached
            return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        if(viewType == RecyclerViewItem.ItemType.HEADER.ordinal()){
            ((HeaderHolder) holder).bindData((RecyclerViewItem<String>) recyclerViewItemList.get(position));
        }else if(viewType == RecyclerViewItem.ItemType.STANDARD_SET.ordinal()){
            ((StandardSetHolder) holder).bindData((RecyclerViewItem<ScheduleStep>) recyclerViewItemList.get(position));
        }
    }

    @Override
    public int getItemViewType(final int position) {
        RecyclerViewItem recyclerViewRecyclerViewItem = this.recyclerViewItemList.get(position);
        switch (recyclerViewRecyclerViewItem.getType()){
            case HEADER:
                return RecyclerViewItem.ItemType.HEADER.ordinal();
            default:
                return RecyclerViewItem.ItemType.STANDARD_SET.ordinal();
        }
    }

    @Override
    public int getItemCount() {
        return this.recyclerViewItemList.size();
    }

}
