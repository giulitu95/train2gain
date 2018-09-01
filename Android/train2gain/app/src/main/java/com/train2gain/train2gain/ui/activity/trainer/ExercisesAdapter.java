package com.train2gain.train2gain.ui.activity.trainer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.train2gain.train2gain.R;
import com.train2gain.train2gain.model.entity.Exercise;

import java.util.List;

public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.ExercisesHolder> {
    private List<Exercise> exerciseList;
    private Context context;
    private View.OnClickListener exerciseListener;
    public static class ExercisesHolder extends RecyclerView.ViewHolder{
        public LinearLayout itemContainer;

        public ExercisesHolder(LinearLayout itemContainer) {
            super(itemContainer);
            this.itemContainer = itemContainer;

        }

    }

    public ExercisesAdapter(List<Exercise> exerciseList, View.OnClickListener exerciseListener) {
        this.exerciseList = exerciseList;
        this.exerciseListener = exerciseListener;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ExercisesAdapter.ExercisesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LinearLayout itemContainer = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_exercises_item, parent, false);
        itemContainer.setOnClickListener(this.exerciseListener);
        ExercisesHolder eh = new ExercisesHolder(itemContainer);
        return eh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ExercisesHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TextView exerciseName = (TextView)holder.itemContainer.findViewById(R.id.exerciselist_exercise_title);
        TextView exerciseCategory = (TextView)holder.itemContainer.findViewById(R.id.exerciselist_category_text);
        ImageView exerciseImage = (ImageView)holder.itemContainer.findViewById(R.id.exerciselist_image);
        Picasso.get().load(exerciseList.get(position).getImageUrl()).error(R.drawable.image_app_logo).into(exerciseImage);
        exerciseCategory.setText(exerciseList.get(position).getMuscleGroup().toString());
        exerciseName.setText(exerciseList.get(position).getName());

    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return exerciseList.size();
    }
}
