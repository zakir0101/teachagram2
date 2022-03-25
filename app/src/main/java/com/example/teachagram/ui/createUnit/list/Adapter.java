package com.example.teachagram.ui.createUnit.list;

import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teachagram.R;
import com.example.teachagram.data.MyViewModel;
import com.example.teachagram.data.Objective;
import com.example.teachagram.data.Unit;
import com.example.teachagram.databinding.FragmentCreateUnitListItemBinding;
import com.example.teachagram.ui.createUnit.CreateUnitFragment;

import java.util.ArrayList;
import java.util.List;


public class Adapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Unit> mValues = new ArrayList<>();;
    private final List<Unit> selected;
    private final MyViewModel model;
    private final CreateUnitFragment fragment;

    public Adapter(CreateUnitFragment createUnitFragment) {
        fragment = createUnitFragment;
        model = fragment.model;
        selected = fragment.selected;
        fragment.db.getUnitsAsync(model.selStandard.name).observe(fragment.getViewLifecycleOwner(),
                units -> {
                    mValues = units;
                    notifyDataSetChanged();
                });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentCreateUnitListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Unit item = holder.mItem;
        holder.mText1.setText(item.name);
        setMiddleText(holder);
        fragment.db.countObjectiveAsync(holder.mItem).observe(fragment.getViewLifecycleOwner(),
                integer -> {
                    holder.mText3.setText(String.format("+%d Objective", integer));
                });
        holder.itemView.setOnClickListener(onClick(holder));
        holder.itemView.setOnLongClickListener(onLongClick(holder));

    }

    public void setMiddleText(ViewHolder holder) {
        Unit item = holder.mItem;
        fragment.db.getObjectivesAsync(item.name, item.standardName).observe(fragment.getViewLifecycleOwner(),
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    public View.OnLongClickListener onLongClick(ViewHolder holder) {
        return v -> {
            if (!selected.contains(holder.mItem)) {
                selected.add(holder.mItem);
                ;
                holder.container.setBackground(getRipple(R.color.surface2));
            } else {
                selected.remove(holder.mItem);
                holder.container.setBackground(getRipple(R.color.surface1));
            }
            if (selected.isEmpty()) {
                fragment.binding.appBarMain.imageView.setVisibility(View.GONE);
                fragment.binding.appBarMain.imageView3.setVisibility(View.GONE);
                fragment.binding.appBarMain.imageView2.setVisibility(View.VISIBLE);
            } else {
                fragment.binding.appBarMain.imageView.setVisibility(View.VISIBLE);
                fragment.binding.appBarMain.imageView3.setVisibility(View.VISIBLE);
                fragment.binding.appBarMain.imageView2.setVisibility(View.GONE);
            }
            return true;
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    View.OnClickListener onClick(ViewHolder holder) {
        return v -> {
            if (selected.isEmpty()) {
                model.selUnit = holder.mItem;
                fragment.controller.navigate(R.id.to_navigation_create_objective);
            } else
                onLongClick(holder).onLongClick(v);
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public RippleDrawable getRipple(Integer color) {
        ColorStateList c = getContext().getColorStateList(R.color.lightgreen);
        RippleDrawable drawable = new RippleDrawable(c, getCornerShape(color), null);
        return drawable;
    }


    public Context getContext() {
        return fragment.getContext();
    }

    public GradientDrawable getCornerShape(Integer color) {

        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadius(24);

        shape.setColor(getContext().getResources().getColor(color));
        return shape;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

}