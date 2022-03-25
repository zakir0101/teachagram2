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
import com.example.teachagram.data.Standard;
import com.example.teachagram.data.SubjectName;
import com.example.teachagram.databinding.FragmentMainListItemBinding;
import com.example.teachagram.ui.main.MainFragment;

import java.util.ArrayList;
import java.util.List;


public class Adapter2 extends RecyclerView.Adapter<ViewHolder> {

    private List<Standard> mValues= new ArrayList<>();;
    private final MainFragment fragment;

    public Adapter2(MainFragment mainFragment) {
        fragment = mainFragment;
        fragment.db.getAllStandardAsync().observe(fragment.getViewLifecycleOwner(),
                standards -> {
                    mValues = standards;
                    notifyDataSetChanged();
                });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentMainListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem2 = mValues.get(position);
        holder.itemView.setOnClickListener(onClick(holder));
        holder.itemView.setOnLongClickListener(onLongClick(holder));
        holder.mText1.setText(mValues.get(position).name);
        fragment.db.getSubjectAsync(mValues.get(position).subjectName).observe(
                fragment.getViewLifecycleOwner(), s -> {
                    holder.mText2.setText(s.name);
                    holder.mImageView.setImageResource(s.res);
                }
        );

        holder.layout.setBackground(getCornerShape(holder.mItem2.color));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public View.OnClickListener onClick(ViewHolder holder) {
        return v -> {
            if (fragment.selected2.isEmpty()) {
                fragment.model.selStandard = holder.mItem2;
                fragment.controller.navigate(R.id.to_navigation_create_unit);
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    public View.OnLongClickListener onLongClick(ViewHolder holder) {

        List<Standard> selected = fragment.selected2;
        return v -> {
            if (!selected.contains(holder.mItem2)) {
                selected.add(holder.mItem2);
                holder.container.setBackground(getRipple(R.color.surface2));

            } else {
                selected.remove(holder.mItem2);
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