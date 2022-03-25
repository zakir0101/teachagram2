package com.example.teachagram.ui.create;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


import com.example.teachagram.R;
import com.example.teachagram.daos.AppDatabase;
import com.example.teachagram.data.MyViewModel;
import com.example.teachagram.data.Objective;
import com.example.teachagram.databinding.CreateClassBinding;
import com.example.teachagram.databinding.CreateObjectiveBinding;
import com.example.teachagram.ui.createObjective.CreateObjectiveFragment;

public class AddObjectiveFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public CreateObjectiveBinding binding;
    public NavController controller;
    public MyViewModel model;
    public AppDatabase db;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = CreateObjectiveBinding.inflate(inflater, container, false);
        controller = Navigation.findNavController(container);
        model = new ViewModelProvider(getActivity()).get(MyViewModel.class);
        db = model.getDb(this);

        binding.editTextTextMultiLine.setOnFocusChangeListener(((v, hasFocus) -> {
            if (!hasFocus)
                hideKeyboard();
        }));
        if (model.editMood == true) {
            binding.imageView2.setOnClickListener(onEdit());

        } else {
            binding.imageView2.setOnClickListener(onAdd());
        }

        binding.imageView1.setOnClickListener(v -> {
            controller.navigateUp();
        });
        return binding.getRoot();
    }

    private View.OnClickListener onAdd() {
        binding.editTextTextMultiLine.setText("");
        return view -> {
            String text = binding.editTextTextMultiLine.getText().toString();
            db.addObjectiveAsync(text, model.selUnit.name, model.selStandard.name).observe(getViewLifecycleOwner(),
                    add -> {
                        if (!add)
                            Toast.makeText(getContext(), "Objective already exist !", Toast.LENGTH_SHORT).show();
                        else
                            controller.navigateUp();
                    });

        };
    }

    private View.OnClickListener onEdit() {
        binding.editTextTextMultiLine.setText(model.selObjective.text);
        return view -> {
            String text = binding.editTextTextMultiLine.getText().toString();
            db.editObjectiveAsync(model.selObjective, text,
                    model.selStandard.name, model.selUnit.name).observe(getViewLifecycleOwner(),
                    edited -> {
                        if (!edited)
                            Toast.makeText(getContext(), "Objective already exist !", Toast.LENGTH_SHORT).show();
                        else {
                            controller.navigateUp();
                        }
                    });

        };
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.editTextTextMultiLine.getWindowToken(), 0);
    }
}
