package com.example.teachagram.ui.create;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.teachagram.daos.AppDatabase;
import com.example.teachagram.data.MyViewModel;
import com.example.teachagram.data.Standard;
import com.example.teachagram.data.SubjectName;
import com.example.teachagram.databinding.CreateStandardBinding;

import java.util.List;

public class AddStandardFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public NavController controller;
    public AppDatabase db;
    public MyViewModel model;
    public CreateStandardBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CreateStandardBinding
                .inflate(inflater, container, false);
        controller = Navigation.findNavController(container);
        model = new ViewModelProvider(getActivity()).get(MyViewModel.class);
        db = model.getDb(this);

        binding.autoCompleteTextView.setFocusable(false);
        binding.autoCompleteTextView.setOnClickListener(v -> {
            onMenu(v);
        });
        if (model.editMood == false) {
            binding.imageView2.setOnClickListener(onAdd());
        } else {
            Standard oldStandard = model.selStandard;
            String oldSubject = model.selStandard.subjectName;
            binding.editText1.setText(oldStandard.name);
            binding.autoCompleteTextView.setText(oldSubject);

            binding.imageView2.setOnClickListener(onEdit());
        }
        binding.imageView1.setOnClickListener(v -> {
            controller.navigateUp();
        });
        return binding.getRoot();
    }


    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void onMenu(View view) {
        MenuBuilder builder = new MenuBuilder(getContext());

        PopupMenu menu = new PopupMenu(getContext(), view);
        db.getAllSubjectAsync().observe(getViewLifecycleOwner(),subjectNames -> {
            int index = 0;
            for (SubjectName s : subjectNames) {
                menu.getMenu().add(s.name);
                menu.getMenu().getItem(index).setIcon(getContext().getDrawable(s.res));
                index++;
            }
            PopupMenu.OnMenuItemClickListener listener = item -> {
                binding.autoCompleteTextView.setText(item.getTitle());
                return true;
            };
            menu.setOnMenuItemClickListener(listener);
            MenuPopupHelper helper = new MenuPopupHelper(
                    getContext(), (MenuBuilder) menu.getMenu(), view);
            helper.setForceShowIcon(true);
            helper.show();

        });
    }


    public View.OnClickListener onAdd() {
        return view -> {
            String sub = binding.autoCompleteTextView.getText().toString();
            if(sub.equals("")){
                Toast.makeText(getContext(), "you have to select a subject !", Toast.LENGTH_SHORT).show();
                return;
            }


            String standard = binding.editText1.getText().toString();
            db.addStandardAsync(standard,sub).observe(getViewLifecycleOwner(),added->
            {
                if(added)
                    controller.navigateUp();
                else
                    Toast.makeText(getContext(), "Name already exist !", Toast.LENGTH_SHORT).show();
            });
        };
    }


    public View.OnClickListener onEdit() {
        Standard oldStandard = model.selStandard;
        String oldSubject = model.selStandard.subjectName;
        binding.editText1.setText(oldStandard.name);
        binding.autoCompleteTextView.setText(oldSubject);
        return view -> {
            String newSubject = binding.autoCompleteTextView.getText().toString();
            String newName = binding.editText1.getText().toString();
            db.editStandardAsync(oldStandard ,newName,newSubject,oldStandard.color).observe(getViewLifecycleOwner(),
                    edited ->{
                    if(edited)
                        controller.navigateUp();
                    else
                        Toast.makeText(getContext(), "Name already exist !", Toast.LENGTH_SHORT).show();
                    } );

        };

    }


}
