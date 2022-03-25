package com.example.teachagram.ui.createUnit.list;


import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachagram.data.Unit;
import com.example.teachagram.databinding.FragmentCreateUnitListItemBinding;

public class ViewHolder extends RecyclerView.ViewHolder {
    public final TextView mText1;
    public final TextView mText2;
    public final TextView mText3;
    public final ConstraintLayout container;
    public Unit mItem;

    public ViewHolder(FragmentCreateUnitListItemBinding binding) {
        super(binding.getRoot());
        mText1 = binding.textView1;
        mText2 = binding.textView2;
        mText3 = binding.textView3 ;
        container = binding.container;
    }

    @Override
    public String toString() {
        return super.toString() + " '" + mText2.getText() + "'";
    }
}
