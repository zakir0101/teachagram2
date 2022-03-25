package com.example.teachagram.ui.classInfo.list1;

import androidx.annotation.RequiresApi;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teachagram.R;
import com.example.teachagram.data.Classes;
import com.example.teachagram.data.MyViewModel;
import com.example.teachagram.data.Standard;
import com.example.teachagram.data.SubjectName;
import com.example.teachagram.databinding.FragmentClassInfoListItem1Binding;
import com.example.teachagram.ui.classInfo.ClassInfoFragment;

import java.util.ArrayList;
import java.util.List;


public class Adapter1 extends RecyclerView.Adapter<ViewHolder> {

    private List<Standard> mValues= new ArrayList<>();;
    private final NavController controller;
    private final MyViewModel model;
    private final ClassInfoFragment fragment;


    public Adapter1(ClassInfoFragment classInfoFragment) {
        fragment = classInfoFragment;
        controller = fragment.controller;
        model = fragment.model;
        fragment.db.getStandardForClassAsync(model.selClass).observe(fragment.getViewLifecycleOwner(),
                standards -> {mValues = standards; notifyDataSetChanged();});
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentClassInfoListItem1Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Standard item = holder.mItem;
        holder.mText1.setText(item.name);
        setText23image(holder);
        holder.itemView.setOnClickListener(onClick(holder));
        holder.itemView.setOnLongClickListener(onLongClick(holder));
        holder.containerSmall.setBackground(getCornerShape(R.color.surface3));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public View.OnClickListener onClick(ViewHolder holder) {
        return v -> {
            if (fragment.selected1.isEmpty()) {
                model.selStandard = holder.mItem;
                controller.navigate(R.id.to_navigation_edit_unit);
            } else {
                onLongClick(holder).onLongClick(v);
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setText23image(ViewHolder holder) {
        fragment.db.getSubjectAsync(holder.mItem.subjectName).observe(fragment.getViewLifecycleOwner(),
                s -> {
                    holder.mText2.setText(s.name);
                    Drawable unwrappedDrawable = getContext().getDrawable( s.res);
                    Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
                    DrawableCompat.setTint(wrappedDrawable, getContext().getColor(R.color.greenAppbarlight));
                    holder.imageView.setImageDrawable(wrappedDrawable);
                });
        fragment.db.getPercentForStandardAsync(holder.mItem, model.selClass).observe(
                fragment.getViewLifecycleOwner(),integer -> {
                    holder.mText3.setText(String.format("%d%%", integer));
                }
        );



    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public RippleDrawable getRipple(Integer color) {
        ColorStateList c = getContext().getColorStateList(R.color.lightgreen);
        RippleDrawable drawable = new RippleDrawable(c, getCornerShape(color), null);
        return drawable;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
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

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.M)
    private View.OnLongClickListener onLongClick(ViewHolder holder) {
        List<Standard> selected = fragment.selected1;
        return v -> {
            if (!selected.contains(holder.mItem)) {
                selected.add(holder.mItem);
                holder.container.setBackground(getRipple(R.color.surface2));

            } else {
                selected.remove(holder.mItem);
                holder.container.setBackground(getRipple(R.color.surface1));
            }
            if (selected.isEmpty()) {
                fragment.binding.textView2.setText("ADD");
                fragment.binding.textView2.setTextColor(getContext().getColor(R.color.greenAppbar));

            } else {
                fragment.binding.textView2.setText("remove");
                fragment.binding.textView2.setTextColor(getContext().getColor(R.color.lightRed));
            }
            return true;
        };
    }
}