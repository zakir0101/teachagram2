package com.example.teachagram.ui.createObjective;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.example.teachagram.R;
import com.example.teachagram.daos.AppDatabase;
import com.example.teachagram.data.MyViewModel;
import com.example.teachagram.data.Objective;
import com.example.teachagram.databinding.FragmentCreateObjectiveBinding;
import com.example.teachagram.databinding.Popup1Binding;
import com.example.teachagram.ui.createObjective.list.Adapater;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class CreateObjectiveFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public FragmentCreateObjectiveBinding binding;
    public MyViewModel model;
    public NavController controller;
    public List<Objective> selected;
    public AppDatabase db ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =
                FragmentCreateObjectiveBinding.inflate(inflater, container, false);
        model = new ViewModelProvider(getActivity()).get(MyViewModel.class);
        db = model.getDb(this);
        controller = Navigation.findNavController(container);
        selected = new ArrayList<>();

        setUpTitle();
        binding.list1.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        binding.list1.setAdapter(new Adapater(this));

        binding.appBarMain.imageView2.setOnClickListener(onBack);
        binding.appBarMain.imageView3.setOnClickListener(onEdit);
        binding.appBarMain.imageView.setOnClickListener(v -> {onCheck();});
        binding.fab.setOnClickListener(onAdd);

        return binding.getRoot();
    }

    void setUpTitle(){
        String name = model.selUnit.name;
        if (name.toCharArray().length > 20)
            name = name.substring(0, 20) + "...";
        binding.appBarMain.textView1.setText(name);
    }


    private void onCheck() {
        Popup1Binding popup1Binding = Popup1Binding.inflate(getLayoutInflater());
        int width = ConstraintLayout.LayoutParams.MATCH_PARENT ;
        int height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popup1Binding.getRoot() ,width
                , height ,focusable);
        popupWindow.showAtLocation(getView() , Gravity.CENTER, 0, 0);
        popup1Binding.button.setOnClickListener( v -> {popupWindow.dismiss();});
        popup1Binding.button2.setOnClickListener(v -> {
            popupWindow.dismiss();
            onDelete.onClick(v);
        });
    }
    View.OnClickListener onEdit =v -> {
        model.editMood = true;
        model.selObjective = selected.get(0);
        controller.navigate(R.id.action_navigation_create_objective_to_createObjectiveFragment);
        reset();

    } ;
    View.OnClickListener onAdd =view -> {
        model.editMood = false;
        controller.navigate(R.id.action_navigation_create_objective_to_createObjectiveFragment);
        reset();

    };

    View.OnClickListener onDelete =v -> {
        db.deleteObjectiveAsync(selected).observe(this, a ->{
            reset();
            binding.list1.setAdapter(new Adapater(this));
        } );

    };

    private void reset() {
        selected.clear();
        binding.appBarMain.imageView.setVisibility(View.GONE);
        binding.appBarMain.imageView3.setVisibility(View.GONE);
        binding.appBarMain.imageView2.setVisibility(View.VISIBLE);
    }

    View.OnClickListener onBack =v -> {
        controller.navigateUp();
    };
}