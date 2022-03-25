package com.example.teachagram.ui.create;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


import com.example.teachagram.daos.AppDatabase;
import com.example.teachagram.data.MyViewModel;
import com.example.teachagram.data.Standard;
import com.example.teachagram.data.SubjectName;
import com.example.teachagram.databinding.CreateClassBinding;

import java.util.List;

public class AddClassFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    AppDatabase db;
    MyViewModel model;
    CreateClassBinding binding;
    NavController controller;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CreateClassBinding.inflate(inflater, container, false);
        controller = Navigation.findNavController(container);
        model = new ViewModelProvider(getActivity()).get(MyViewModel.class);
        db = model.getDb(this);

        binding.autoCompleteTextView.setFocusable(false);
        binding.autoCompleteTextView.setOnClickListener(v -> {
            onMenu(v);
        });
        if (model.editMood) {
            preLoad();
            binding.imageView2.setOnClickListener(onEdit());
        } else
            binding.imageView2.setOnClickListener(onAdd());

        binding.imageView1.setOnClickListener(onCancel());
        return binding.getRoot();
    }

    private View.OnClickListener onCancel() {
        return v -> {
            controller.navigateUp();
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private View.OnClickListener onEdit() {
        return view -> {
            String sName = binding.autoCompleteTextView.getText().toString();
            String className = binding.editText1.getText().toString();
            if (sName.equals("")) {
                Toast.makeText(getContext(), "You have to select a standard !", Toast.LENGTH_SHORT).show();
                return;
            }

            db.editClassAsync(model.selClass,className,model.selClass.color,sName).observe(getViewLifecycleOwner(),
                    edited ->{
                        if(edited) {
                            hideKeyboard();
                            controller.navigateUp();
                        }
                        else
                            Toast.makeText(getContext(), "Name already exist", Toast.LENGTH_SHORT).show();

                    } );
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private View.OnClickListener onAdd() {
        return view -> {
            String sName = binding.autoCompleteTextView.getText().toString();
            Standard newS;
            if (sName.equals("")) {
                Toast.makeText(getContext(), "You have to select a standard !", Toast.LENGTH_SHORT).show();
                return;
            }
            String cName = binding.editText1.getText().toString();
            db.addClassAsync(cName, sName).observe(getViewLifecycleOwner(), added -> {
                if (added) {
                    hideKeyboard();
                    controller.navigateUp();
                }
                else
                    Toast.makeText(getContext(), "Name already exist !", Toast.LENGTH_SHORT).show();
            });

        };
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void onMenu(View view) {
        hideKeyboard();
        PopupMenu menu = new PopupMenu(getContext(), view);
        db.getAllStandardAsync().observe(getViewLifecycleOwner(), standards -> {
            for (Standard s : standards)
                menu.getMenu().add(s.name);
            PopupMenu.OnMenuItemClickListener listener = item -> {
                binding.autoCompleteTextView.setText(item.getTitle());
                return true;
            };
            menu.setOnMenuItemClickListener(listener);
            menu.show();
        });

    }

    void preLoad() {

        String classN = model.selClass.name;
        binding.editText1.setText(classN);
        db.getStandardForClassAsync(model.selClass).observe(getViewLifecycleOwner(), standards -> {
            if (!standards.isEmpty()) {
                binding.autoCompleteTextView.setText(standards.get(0).name);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public  void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.editText1.getWindowToken(), 0);
    }
}
