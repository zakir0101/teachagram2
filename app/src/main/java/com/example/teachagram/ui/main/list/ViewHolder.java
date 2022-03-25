package com.example.teachagram.ui.main.list;


import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachagram.data.Classes;
import com.example.teachagram.data.Standard;
import com.example.teachagram.databinding.FragmentMainListItemBinding;

public class ViewHolder extends RecyclerView.ViewHolder {
    public final TextView mText1;
    public final TextView mText2;
    public final ImageView mImageView;
    public final ConstraintLayout layout;
    public final ConstraintLayout container;

    public Standard mItem2;
    public Classes mItem1;

    public ViewHolder(FragmentMainListItemBinding binding) {
        super(binding.getRoot());
        mText1 = binding.textView1;
        mText2 = binding.textView2;
        mImageView = binding.imageIcon1;
        layout = binding.imageContainer;
        container = binding.container ;
    }

    @Override
    public String toString() {
        return super.toString() + " '" + mText2.getText() + "'";
    }
}