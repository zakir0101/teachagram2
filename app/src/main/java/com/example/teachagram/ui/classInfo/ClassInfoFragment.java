package com.example.teachagram.ui.classInfo;

import android.media.midi.MidiOutputPort;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachagram.R;
import com.example.teachagram.daos.AppDatabase;
import com.example.teachagram.data.ClassStandard;
import com.example.teachagram.data.MyViewModel;
import com.example.teachagram.data.Standard;
import com.example.teachagram.data.Student;
import com.example.teachagram.databinding.FragmentClassInfoBinding;
import com.example.teachagram.databinding.Popup1Binding;
import com.example.teachagram.databinding.PopupStandardBinding;
import com.example.teachagram.databinding.PopupStudentBinding;
import com.example.teachagram.ui.classInfo.list1.Adapter1;
import com.example.teachagram.ui.classInfo.list2.Adapter2;

import java.util.ArrayList;
import java.util.List;

public class ClassInfoFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public FragmentClassInfoBinding binding;
    public NavController controller;
    public MyViewModel model;
    public AppDatabase db;
    public List<Standard> selected1 = new ArrayList<>();
    public List<Student> selected2 = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =
                FragmentClassInfoBinding.inflate(inflater, container, false);
        controller = Navigation.findNavController(container);
        model = new ViewModelProvider(getActivity()).get(MyViewModel.class);
        db = model.getDb(this);

        getActivity().getWindow().setStatusBarColor(getContext().getColor(R.color.darkgreen));
        setUpTitle();
        binding.appBar.imageView1.setOnClickListener(view -> {
            controller.navigateUp();
        });
        binding.textView4.setOnClickListener(v -> {
            if (selected2.isEmpty())
                onAddStudent();
            else
                onCheck(2);
        });
        binding.textView2.setOnClickListener(v -> {
            if (selected1.isEmpty())
                onAddStandard();
            else
                onCheck(1);
        });
        RecyclerView r1 = binding.list1;
        RecyclerView r2 = binding.list2;
        r1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        r2.setLayoutManager(new GridLayoutManager(binding.getRoot().getContext(), 5));
        r1.setAdapter(new Adapter1(this));
        r2.setAdapter(new Adapter2(this));
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void onCheck(Integer x) {
        Popup1Binding popup1Binding = Popup1Binding.inflate(getLayoutInflater());
        int width = ConstraintLayout.LayoutParams.MATCH_PARENT;
        int height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popup1Binding.getRoot(), width
                , height, focusable);
        popupWindow.showAtLocation(getView(), Gravity.CENTER, 0, 0);
        popup1Binding.button.setOnClickListener(v -> {
            popupWindow.dismiss();
        });
        popup1Binding.button2.setOnClickListener(v -> {
            popupWindow.dismiss();
            if (x == 1)
                onDelete1();
            else
                onDelete2();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void onDelete1() {
        db.detachClassToStandardAsync(model.selClass, selected1).observe(getViewLifecycleOwner(),
                aBoolean -> {
                    binding.list1.setAdapter(new Adapter1(this));
                    selected1.clear();
                });

        binding.textView2.setText("ADD");
        binding.textView2.setTextColor(getContext().getColor(R.color.greenAppbar));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void onDelete2() {
        db.deleteStudentAsync(selected2).observe(getViewLifecycleOwner(), aBoolean -> {
            binding.list2.setAdapter(new Adapter2(this));
            selected2.clear();
            binding.textView4.setText("ADD");
            binding.textView4.setTextColor(getContext().getColor(R.color.greenAppbar));
        });

    }


    private void onAddStandard() {
        PopupStandardBinding popupBinding = PopupStandardBinding.inflate(getLayoutInflater());
        int width = ConstraintLayout.LayoutParams.MATCH_PARENT;
        int height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupBinding.getRoot(), width
                , height, focusable);
        popupWindow.showAtLocation(getView(), Gravity.CENTER, 0, 0);

        popupBinding.editTextTextPersonName.setFocusable(false);
        popupBinding.editTextTextPersonName.setOnClickListener(v -> {
            onMenu(v, popupBinding);
        });

        popupBinding.button.setOnClickListener(v -> {
            popupWindow.dismiss();
        });
        popupBinding.button2.setOnClickListener(v -> {
            String name = popupBinding.editTextTextPersonName.getText().toString();
            if (name.equals("")) {
                Toast.makeText(getContext(), "Select a Standard from menu !", Toast.LENGTH_SHORT).show();
                return;
            }
            db.attachClassToStandardAsync(model.selClass, name).observe(getViewLifecycleOwner(),
                    attached -> {
                        if (attached) {
                            binding.list1.setAdapter(new Adapter1(this));
                            popupWindow.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Standard already Exist !", Toast.LENGTH_SHORT).show();
                        }

                    });
        });

    }

    private void onMenu(View view, PopupStandardBinding popupStandardBinding) {
        PopupMenu menu = new PopupMenu(getContext(), view);
        db.getAllStandardAsync().observe(getViewLifecycleOwner(), standards -> {
            for (Standard s : standards)
                menu.getMenu().add(s.name);
            PopupMenu.OnMenuItemClickListener listener = item -> {
                popupStandardBinding.editTextTextPersonName.setText(item.getTitle());
                return true;
            };
            menu.setOnMenuItemClickListener(listener);
            menu.show();
        });

    }

    private void onAddStudent() {
        PopupStudentBinding popupBinding = PopupStudentBinding.inflate(getLayoutInflater());
        int width = ConstraintLayout.LayoutParams.MATCH_PARENT;
        int height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupBinding.getRoot(), width
                , height, focusable);
        popupWindow.showAtLocation(getView(), Gravity.CENTER, 0, 0);
        popupBinding.button.setOnClickListener(v -> {
            popupWindow.dismiss();
        });
        popupBinding.button2.setOnClickListener(v -> {
            String name = popupBinding.editTextTextPersonName.getText().toString();
            db.addStudentAsync(name, model.selClass.name).observe(getViewLifecycleOwner(),
                    aBoolean -> {
                        if (aBoolean) {
                            popupWindow.dismiss();
                            binding.list2.setAdapter(new Adapter2(this));
                        }else{
                            Toast.makeText(getContext(),"Student already exist !",Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    private void setUpTitle() {
        String name = model.selClass.name;
        if (name.toCharArray().length > 20)
            name = name.substring(0, 20) + "...";
        binding.appBar.textView1.setText(name);

    }


}
