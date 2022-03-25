package com.example.teachagram.ui.classInfo.list2;



import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachagram.data.Student;
import com.example.teachagram.databinding.FragmentClassInfoListItem2Binding;

public class ViewHolder extends RecyclerView.ViewHolder {
    public final TextView mText1;
    public final TextView mText2;
    public final ConstraintLayout container;
    public final ConstraintLayout container2;
    public Student mItem;

    public ViewHolder(FragmentClassInfoListItem2Binding binding) {
        super(binding.getRoot());
        mText1 = binding.textView1;
        mText2 = binding.textView2;
        container = binding.container;
        container2 = binding.container2;

    }

    @Override
    public String toString() {
        return super.toString() + " '" + mText2.getText() + "'";
    }
}
