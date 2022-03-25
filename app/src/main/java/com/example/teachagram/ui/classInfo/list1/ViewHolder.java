package com.example.teachagram.ui.classInfo.list1;


import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachagram.data.Standard;
import com.example.teachagram.databinding.FragmentClassInfoListItem1Binding;

public class ViewHolder extends RecyclerView.ViewHolder {
    public final TextView mText1;
    public final TextView mText2;
    public final TextView mText3;
    public final ImageView imageView;
    public final ConstraintLayout container;
    public final ConstraintLayout containerSmall;
    public Standard mItem;

    public ViewHolder(FragmentClassInfoListItem1Binding binding) {
        super(binding.getRoot());
        mText1 = binding.textView1;
        mText2 = binding.textView2;
        mText3 = binding.textView3 ;
        imageView = binding.imageView1;
        container = binding.container;
        containerSmall = binding.containerSamall;
    }

    @Override
    public String toString() {
        return super.toString() + " '" + mText2.getText() + "'";
    }
}
