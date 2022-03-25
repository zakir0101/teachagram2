package com.example.teachagram.ui.main.list;


import androidx.annotation.RequiresApi;
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
import com.example.teachagram.daos.AppDatabase;
import com.example.teachagram.data.Classes;
import com.example.teachagram.data.Standard;
import com.example.teachagram.databinding.FragmentMainListItemBinding;
import com.example.teachagram.ui.main.MainFragment;

import java.util.ArrayList;
import java.util.List;


public class Adapter1 extends RecyclerView.Adapter<ViewHolder> {

    private List<Classes> mValues = new ArrayList<>();
    private final MainFragment fragment;
    private final AppDatabase db;

    public Adapter1(MainFragment mainFragment) {
        fragment = mainFragment;
        mainFragment.model.getDb(mainFragment).getAllClassAsync().observe(
                fragment.getViewLifecycleOwner(), classes -> {
                    mValues = classes;
                    notifyDataSetChanged();
                }
        );

        db = mainFragment.model.getDb(fragment);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentMainListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem1 = mValues.get(position);
        holder.itemView.setOnClickListener(onClick(holder));


        holder.itemView.setOnLongClickListener(onLongClick(holder));

        holder.mText1.setText(mValues.get(position).name);

        db.getFirstStandardForClassAsync(holder.mItem1).observe(fragment.getViewLifecycleOwner(),
                s -> {
                    if (s != null)
                        holder.mText2.setText(s.name);
                    else
                        holder.mText2.setText("");
                });

        holder.mImageView.setImageResource(R.drawable.ic_action_name);

        holder.layout.setBackground(getCornerShape(holder.mItem1.color));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
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
    public View.OnClickListener onClick(ViewHolder holder) {
        return v -> {
            if (fragment.selected1.isEmpty()) {
                fragment.model.selClass = holder.mItem1;
                fragment.controller.navigate(R.id.to_navigation_class_info);

            } else
                onLongClick(holder).onLongClick(v);
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private View.OnLongClickListener onLongClick(ViewHolder holder) {

        List<Classes> selected = fragment.selected1;
        return v -> {
            if (!selected.contains(holder.mItem1)) {
                selected.add(holder.mItem1);
                holder.container.setBackground(getRipple(R.color.surface2));
                ;

            } else {
                selected.remove(holder.mItem1);
                holder.container.setBackground(getRipple(R.color.surface1));
            }
            if (selected.isEmpty()) {
                fragment.binding.appBarMain.imageView.setVisibility(View.GONE);
                fragment.binding.appBarMain.imageView2.setVisibility(View.GONE);
                fragment.binding.appBarMain.imageView3.setVisibility(View.VISIBLE);
            } else {
                fragment.binding.appBarMain.imageView.setVisibility(View.VISIBLE);
                fragment.binding.appBarMain.imageView2.setVisibility(View.VISIBLE);
                fragment.binding.appBarMain.imageView3.setVisibility(View.GONE);
            }
            return true;
        };
    }
}