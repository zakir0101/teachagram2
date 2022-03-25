package com.example.teachagram.ui.createUnit;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.example.teachagram.R;
import com.example.teachagram.daos.AppDatabase;
import com.example.teachagram.data.MyViewModel;
import com.example.teachagram.data.Unit;
import com.example.teachagram.databinding.FragmentCreateUnitBinding;
import com.example.teachagram.databinding.Popup1Binding;
import com.example.teachagram.ui.createUnit.list.Adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class CreateUnitFragment extends Fragment {


    public List<Unit> selected;
    public NavController controller;
    public MyViewModel model;
    public FragmentCreateUnitBinding binding;
    public AppDatabase db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateUnitBinding.inflate(inflater, container, false);
        model = new ViewModelProvider(getActivity()).get(MyViewModel.class);
        selected = new ArrayList<>();
        controller = Navigation.findNavController(container);
        db = model.getDb(this);

        setTitlle();
        binding.appBarMain.imageView2.setOnClickListener(onBack);
        binding.appBarMain.imageView3.setOnClickListener(onEdit);
        binding.appBarMain.imageView.setOnClickListener(v -> {onCheck();});
        binding.fab.setOnClickListener(onAdd);

        binding.list1.setLayoutManager(new GridLayoutManager(binding.getRoot().getContext(), 2));
        binding.list1.setAdapter(new Adapter(this));

        return binding.getRoot();
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

    void setTitlle(){
        String name = model.selStandard.name;
        if (name.toCharArray().length > 20)
            name = name.substring(0, 20) + "...";
        binding.appBarMain.textView1.setText(name);
    }

    View.OnClickListener onEdit = v -> {
        model.editMood = true;
        model.selUnit = selected.get(0);
        controller.navigate(R.id.to_createUnitFragment);
        selected.clear();
        reset();
    };

    View.OnClickListener onAdd =view -> {
        model.editMood = false;
        controller.navigate(R.id.to_createUnitFragment);
        selected.clear();
        reset();
    };

     View.OnClickListener onDelete =v -> {
         db.deleteUnitAsync(selected).observe(this , b ->{
             binding.list1.setAdapter(new Adapter(this));
             reset();
         } );


     } ;
    private void reset() {
        selected.clear();
        binding.appBarMain.imageView.setVisibility(View.GONE);
        binding.appBarMain.imageView3.setVisibility(View.GONE);
        binding.appBarMain.imageView2.setVisibility(View.VISIBLE);
    }
     View.OnClickListener onBack = v -> {
         controller.navigateUp();
     };
}