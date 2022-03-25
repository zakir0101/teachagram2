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
import com.example.teachagram.databinding.CreateClassBinding;
import com.example.teachagram.databinding.CreateUnitBinding;

public class AddUnitFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    CreateUnitBinding binding;
    NavController controller;
    MyViewModel model;
    AppDatabase db;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CreateUnitBinding.inflate(inflater, container, false);
        controller = Navigation.findNavController(container);
        model = new ViewModelProvider(getActivity()).get(MyViewModel.class);
        db = model.getDb(this);

        binding.editText1.setOnFocusChangeListener(((v, hasFocus) -> {
            if (!hasFocus)
                hideKeyboard();
        }));
        binding.editText2.setOnFocusChangeListener(((v, hasFocus) -> {
            if (!hasFocus)
                hideKeyboard();
        }));

        if (model.editMood) {
            preLoad();
            binding.imageView2.setOnClickListener(onEdit());
        }else
            binding.imageView2.setOnClickListener(onAdd());

        binding.imageView1.setOnClickListener(v -> {
            controller.navigateUp();
        });

        return binding.getRoot();
    }

    private View.OnClickListener onAdd() {
        return v -> {
            String unit = binding.editText1.getText().toString();
            db.addUnitAsync(unit ,model.selStandard.name).observe(getViewLifecycleOwner(),
                    add ->{
                if(!add)
                    Toast.makeText(getContext(), "Unit already exist !", Toast.LENGTH_SHORT).show();
                else
                    controller.navigateUp();
                    } );
        };
    }

    private View.OnClickListener onEdit() {
        return v -> {
            String unit = binding.editText1.getText().toString();
            db.editUnitAsync(model.selUnit, unit, model.selUnit.standardName).observe(getViewLifecycleOwner(),
                    edited ->{
                if(!edited)
                    Toast.makeText(getContext(), "Unit already exist !", Toast.LENGTH_SHORT).show();
                else
                    controller.navigateUp();
                    } );

        };
    }

    private void preLoad() {
        binding.editText1.setText(model.selUnit.name);
        binding.editText2.setText(model.selUnit.index.toString());
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.editText1.getWindowToken(), 0);
    }
}
