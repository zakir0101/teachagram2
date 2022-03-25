package com.example.teachagram.ui.editObjective;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.teachagram.daos.AppDatabase;
import com.example.teachagram.data.MyViewModel;
import com.example.teachagram.data.Objective;
import com.example.teachagram.databinding.FragmentEditObjectiveBinding;
import com.example.teachagram.ui.editObjective.list.Adapter;

import java.util.List;

public class EditObjectiveFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public FragmentEditObjectiveBinding binding;
    public MyViewModel model;
    public NavController controller;
    public AppDatabase db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =
                FragmentEditObjectiveBinding.inflate(inflater, container, false);
        controller = Navigation.findNavController(container);
        model = new ViewModelProvider(getActivity()).get(MyViewModel.class);
        db = model.getDb(this);


        setTitle();
        binding.include.imageView1.setOnClickListener(view -> {
            controller.navigateUp();
        });
        binding.list1.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        binding.list1.setNestedScrollingEnabled(false);
        binding.list1.setAdapter(new Adapter(this));
        db.getPercentForUnitAsync(model.selUnit , model.selClass).observe(getViewLifecycleOwner(),
                integer -> {binding.progressBar.setProgress( integer);
                    binding.textView7.setText(String.format("%d%%",integer));
        });

        return binding.getRoot();
    }

    private void setTitle() {
        String name = model.selUnit.name;
        if (name.toCharArray().length > 20)
            name = name.substring(0, 20) + "...";
        binding.include.textView1.setText(name);
        binding.include.textView2.setText(model.selClass.name);

    }


}
