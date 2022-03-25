package com.example.teachagram.ui.createObjective.list;


import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachagram.data.Objective;
import com.example.teachagram.databinding.FragmentCreateObjectieveListItemBinding;

public class ViewHolder extends RecyclerView.ViewHolder {
    public final TextView textView1;
    public final ConstraintLayout container ;
    public Objective mItem;

    public ViewHolder(FragmentCreateObjectieveListItemBinding binding) {
        super(binding.getRoot());
        textView1 = binding.textView1;
        container = binding.container;
    }

    @Override
    public String toString() {
        return super.toString() + " '" + textView1.getText() + "'";
    }
}