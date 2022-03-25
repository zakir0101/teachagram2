package com.example.teachagram.ui.editObjective.list;


import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachagram.data.Objective;
import com.example.teachagram.databinding.FragmentEditObjectiveListItemBinding;

public class ViewHolder extends RecyclerView.ViewHolder {
    public final TextView textView1;
    public final ConstraintLayout conDone;
    public final ConstraintLayout conNot;
    public final ConstraintLayout container ;
    public final ImageView imageView;
    public Objective mItem;

    public ViewHolder(FragmentEditObjectiveListItemBinding binding) {
        super(binding.getRoot());
        textView1 = binding.textView1;
        conDone = binding.conDone ;
        conNot = binding.conNot ;
        container = binding.container;
        imageView = binding.imageView4;
    }

    @Override
    public String toString() {
        return super.toString() + " '" + textView1.getText() + "'";
    }
}