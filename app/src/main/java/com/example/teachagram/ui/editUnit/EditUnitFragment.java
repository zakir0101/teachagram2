package com.example.teachagram.ui.editUnit;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachagram.R;
import com.example.teachagram.daos.AppDatabase;
import com.example.teachagram.data.MyViewModel;
import com.example.teachagram.data.Unit;
import com.example.teachagram.databinding.FragmentEditUnitBinding;
import com.example.teachagram.ui.editUnit.list.Adapter;

import java.util.List;

public class EditUnitFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public FragmentEditUnitBinding binding;
    public NavController controller;
    public MyViewModel model;
    public AppDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =
                FragmentEditUnitBinding.inflate(inflater, container, false);
        controller = Navigation.findNavController(container);
        model = new ViewModelProvider(getActivity()).get(MyViewModel.class);
        db = model.getDb(this);

        String name = model.selStandard.name;
        if (name.toCharArray().length > 20)
            name = name.substring(0, 20) + "...";
        binding.include.textView1.setText(name);
        binding.include.textView2.setText(model.selClass.name);


        binding.include.imageView1.setOnClickListener(view -> {
            controller.navigateUp();
        });
        binding.list1.setNestedScrollingEnabled(false);
        binding.list1.setLayoutManager(new GridLayoutManager(
                binding.getRoot().getContext(), 2));

        binding.progressBar.setProgress(0);
        binding.list1.setAdapter(new Adapter(this));
        db.getPercentForStandardAsync(model.selStandard, model.selClass).observe(getViewLifecycleOwner(),
                integer -> {
                    binding.progressBar.setProgress(integer);
                    binding.textView6.setText(String.format("%d%%",integer));
                });

        return binding.getRoot();
    }


}
