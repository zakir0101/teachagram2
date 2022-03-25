package com.example.teachagram.ui.editUnit.list;


import android.media.Image;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachagram.data.Unit;

import com.example.teachagram.databinding.FragmentEditUnitListItemBinding;

public class ViewHolder extends RecyclerView.ViewHolder {
    public final TextView mText1;
    public final TextView mText2;
    public final TextView mText3;
    public final TextView mText4;
    public final ConstraintLayout con1;
    public final ImageView imageView;
    public Unit mItem;

    public ViewHolder(FragmentEditUnitListItemBinding binding) {
        super(binding.getRoot());
        mText1 = binding.textView1;
        mText2 = binding.textView2;
        mText3 = binding.textView3;
        mText4 = binding.textView4;
        imageView = binding.imageView5;
        con1 = binding.con1;
    }

    @Override
    public String toString() {
        return super.toString() + " '" + mText2.getText() + "'";
    }
}
