package com.example.teachagram.ui.editUnit.list;

import static java.lang.String.format;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachagram.R;
import com.example.teachagram.daos.AppDatabase;
import com.example.teachagram.data.MyViewModel;
import com.example.teachagram.data.Objective;
import com.example.teachagram.data.Unit;
import com.example.teachagram.databinding.FragmentEditUnitListItemBinding;
import com.example.teachagram.ui.editUnit.EditUnitFragment;

import java.util.ArrayList;
import java.util.List;


public class Adapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Unit> mValues = new ArrayList<>();;
    private final MyViewModel model;
    private final NavController controller;
    private final EditUnitFragment fragment;
    private final AppDatabase db;


    public Adapter(EditUnitFragment editUnitFragment) {
        fragment = editUnitFragment;
        controller = fragment.controller;
        model = fragment.model;
        db = model.getDb(fragment);
        db.getUnitsAsync(model.selStandard.name).observe(fragment.getViewLifecycleOwner(), units -> {
            mValues = units;
            notifyDataSetChanged();
        });

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentEditUnitListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

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

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Unit item = holder.mItem;
        holder.mText1.setText(item.name);
        setText2(holder);
        setText34(holder);
        // fragment.binding.progressBar.setProgress(db.getPercentForStandard(
        //       model.selStandard, model.selClass));
        holder.con1.setBackground(getCornerShape(R.color.surface2));
        holder.itemView.setOnClickListener(onClick(holder));


    }

    private void setText34(ViewHolder holder) {


        db.getPercentTextForUnitAsync(holder.mItem, model.selClass).observe(fragment.getViewLifecycleOwner(),
                s -> {holder.mText3.setText(s);});


        db.getPercentForUnitAsync(holder.mItem, model.selClass).observe(fragment.getViewLifecycleOwner(),
                integer -> {holder.mText4.setText(String.format("%d%%", integer));
        if(integer.equals(100))
            holder.imageView.setVisibility(View.VISIBLE);
        else
            holder.imageView.setVisibility(View.GONE);

        });

    }

    public View.OnClickListener onClick(ViewHolder holder) {
        return v -> {
            model.selUnit = holder.mItem;
            controller.navigate(R.id.to_navigation_eidt_objective);
        };
    }

    public void setText2(ViewHolder holder) {
        Unit item = holder.mItem;
        db.getObjectivesAsync(item.name, item.standardName).observe(fragment.getViewLifecycleOwner(),
                objectives -> {
                    if (!objectives.isEmpty()) {
                        String text = objectives.get(0).text;
                        if (text.toCharArray().length > 80)
                            text = text.substring(0, 80) + "...";
                        holder.mText2.setText(text);
                    } else
                        holder.mText2.setText("");
                });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

}