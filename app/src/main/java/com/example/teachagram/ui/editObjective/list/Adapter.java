package com.example.teachagram.ui.editObjective.list;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachagram.R;
import com.example.teachagram.daos.AppDatabase;
import com.example.teachagram.data.MyViewModel;
import com.example.teachagram.data.Objective;
import com.example.teachagram.databinding.FragmentEditObjectiveListItemBinding;
import com.example.teachagram.ui.editObjective.EditObjectiveFragment;

import java.util.ArrayList;
import java.util.List;


public class Adapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Objective> mValues= new ArrayList<>();;
    private final AppDatabase db;
    private final MyViewModel model;
    private final EditObjectiveFragment fragment;


    public Adapter(EditObjectiveFragment items) {
        fragment = items;
        model = fragment.model;
        db = model.getDb(fragment);
        db.getObjectivesAsync(model.selUnit.name,model.selStandard.name).observe(fragment.getViewLifecycleOwner(),
                objectives -> {mValues = objectives ; notifyDataSetChanged();});

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentEditObjectiveListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.textView1.setText(mValues.get(position).text);
        db.getProgressAsync(holder.mItem, model.selClass).observe(fragment.getViewLifecycleOwner(),
                done -> {
                    setDone(holder, done);
                });

        holder.conDone.setOnClickListener(view -> {
            setDone(holder, true);
        });
        holder.conNot.setOnClickListener(view -> {
            setDone(holder, false);
        });

    }

    public Context getContext() {
        return fragment.getContext();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public RippleDrawable getRipple(Integer color) {
        ColorStateList c = getContext().getColorStateList(R.color.lightgreen);
        RippleDrawable drawable = new RippleDrawable(c, getCornerShape(color), null);
        return drawable;
    }

    public GradientDrawable getCornerShape(Integer color) {

        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadius(24);

        shape.setColor(getContext().getResources().getColor(color));
        return shape;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setDone(ViewHolder holder, Boolean b) {
        db.setProgressAsync(holder.mItem, model.selClass, b).observe(fragment.getViewLifecycleOwner(),
                aBoolean -> {
                    db.getPercentForUnitAsync(model.selUnit, model.selClass).observe(fragment.getViewLifecycleOwner(),
                            integer -> {
                                fragment.binding.progressBar.setProgress(integer);
                                fragment. binding.textView7.setText(String.format("%d%%",integer));
                            });
                });
        // warscheinlich funktionier nicht , man soll die open erst fertig machen

        if (b) {
            holder.conDone.setBackground(getRipple(R.color.surface2));
            holder.conNot.setBackground(getRipple(R.color.surface1));
            holder.imageView.setVisibility(View.VISIBLE);
        } else {
            holder.conDone.setBackground(getRipple(R.color.surface1));
            holder.conNot.setBackground(getRipple(R.color.surface2));
            holder.imageView.setVisibility(View.GONE);

        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

}