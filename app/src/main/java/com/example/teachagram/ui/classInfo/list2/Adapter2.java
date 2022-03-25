package com.example.teachagram.ui.classInfo.list2;


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
import com.example.teachagram.data.Student;
import com.example.teachagram.databinding.FragmentClassInfoListItem2Binding;
import com.example.teachagram.ui.classInfo.ClassInfoFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Adapter2 extends RecyclerView.Adapter<ViewHolder> {

    private List<Student> mValues= new ArrayList<>();;
    private final NavController controller;
    private final ClassInfoFragment fragment;


    public Adapter2(ClassInfoFragment classInfoFragment) {
        fragment = classInfoFragment;
        controller = fragment.controller;
        fragment.db.getStudentsAsync(fragment.model.selClass.name).observe(
                fragment.getViewLifecycleOwner(), students -> {
                    mValues = students;
                    notifyDataSetChanged();
                }
        );

    }

    @Override
    public com.example.teachagram.ui.classInfo.list2.ViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentClassInfoListItem2Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Student item = holder.mItem;
        holder.mText1.setText(Character.toString(item.name.toUpperCase(Locale.ROOT).charAt(0)));
        holder.mText2.setText(item.name);
        holder.itemView.setOnLongClickListener(onLongClick(holder));
        holder.itemView.setOnClickListener(v -> {
            if (!fragment.selected2.isEmpty())
                onLongClick(holder).onLongClick(v);
        });

        holder.container.setBackground(getCornerShape(holder.mItem.color));

    }

    @Override
    public int getItemCount() {
        return mValues.size();
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
    private View.OnLongClickListener onLongClick(ViewHolder holder) {

        List<Student> selected = fragment.selected2;
        return v -> {
            if (!selected.contains(holder.mItem)) {
                selected.add(holder.mItem);
                holder.container2.setBackground(getRipple(R.color.surface2));
            } else {
                selected.remove(holder.mItem);
                holder.container2.setBackground(null);
                holder.container2.setBackground(getRipple(R.color.white));

            }
            if (selected.isEmpty()) {
                fragment.binding.textView4.setText("ADD");
                fragment.binding.textView4.setTextColor(getContext().getColor(R.color.greenAppbar));

            } else {
                fragment.binding.textView4.setText("remove");
                fragment.binding.textView4.setTextColor(getContext().getColor(R.color.lightRed));
            }
            return true;
        };
    }

}