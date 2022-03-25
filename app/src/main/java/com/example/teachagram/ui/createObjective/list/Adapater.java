package com.example.teachagram.ui.createObjective.list;

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
import com.example.teachagram.data.Objective;
import com.example.teachagram.databinding.FragmentCreateObjectieveListItemBinding;
import com.example.teachagram.ui.createObjective.CreateObjectiveFragment;

import java.util.ArrayList;
import java.util.List;


public class Adapater extends RecyclerView.Adapter<ViewHolder> {

    private List<Objective> mValues= new ArrayList<>();;
    private final List<Objective> selected;
    private final CreateObjectiveFragment fragment;


    public Adapater(CreateObjectiveFragment createObjectiveFragment) {
        fragment = createObjectiveFragment;
        selected = fragment.selected;
        fragment.db.getObjectivesAsync(fragment.model.selUnit.name,fragment.model.selStandard.name).
                observe(fragment.getViewLifecycleOwner(),objectives -> {mValues = objectives;
                notifyDataSetChanged();});
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentCreateObjectieveListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.textView1.setText(mValues.get(position).text);

        holder.itemView.setOnLongClickListener(onLongClick(holder));
        holder.itemView.setOnClickListener(onClick(holder));

    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    View.OnClickListener onClick (ViewHolder holder) {
        return v -> {
          if (!selected.isEmpty())
              onLongClick(holder).onLongClick(v);
          else
          {
              fragment.model.editMood= true;
              fragment.model.selObjective = holder.mItem;
              fragment. controller.navigate(R.id.action_navigation_create_objective_to_createObjectiveFragment);
          }
        };
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    View.OnLongClickListener onLongClick(ViewHolder holder){
        return v -> {
            if( ! selected.contains(holder.mItem)) {
                selected.add(holder.mItem) ;
                holder.container.setBackground(getRipple(R.color.surface2));
            } else{
                selected.remove(holder.mItem);
                holder.container.setBackground(getRipple(R.color.surface1));
            }
            if (selected.isEmpty()) {
                fragment.binding.appBarMain.imageView.setVisibility(View.GONE);
                fragment.binding.appBarMain.imageView3.setVisibility(View.GONE);
                fragment.binding.appBarMain.imageView2.setVisibility(View.VISIBLE);
            } else {
                fragment.binding.appBarMain.imageView.setVisibility(View.VISIBLE);
                fragment.binding.appBarMain.imageView3.setVisibility(View.VISIBLE);
                fragment.binding.appBarMain.imageView2.setVisibility(View.GONE);
            }
            return true;
        };

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public RippleDrawable getRipple(Integer color){
        ColorStateList c = getContext().getColorStateList(R.color.lightgreen);
        RippleDrawable drawable = new RippleDrawable(c,getCornerShape(color),null);
        return drawable;
    }
    public Context getContext(){
        return fragment.getContext();
    }
    public GradientDrawable getCornerShape(Integer color){

        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadius(24);

        shape.setColor( getContext().getResources().getColor(color));
        return shape;
    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }

}