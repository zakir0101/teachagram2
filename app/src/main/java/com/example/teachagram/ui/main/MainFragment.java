package com.example.teachagram.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teachagram.R;
import com.example.teachagram.daos.AppDatabase;
import com.example.teachagram.data.Backup;
import com.example.teachagram.data.Classes;
import com.example.teachagram.data.MyViewModel;
import com.example.teachagram.data.Standard;
import com.example.teachagram.databinding.FragmentMainBinding;
import com.example.teachagram.databinding.Popup1Binding;
import com.example.teachagram.load.AdditionalMathematics1Sudan;
import com.example.teachagram.load.AddittionalMathematics2Sudan;
import com.example.teachagram.load.CambridgeAdditionalMathematics;
import com.example.teachagram.ui.main.list.Adapter1;
import com.example.teachagram.ui.main.list.Adapter2;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A fragment representing a list of Items.
 */
public class MainFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public MyViewModel model;
    public AppDatabase db;
    public List<Classes> selected1 = new ArrayList<>();
    public List<Standard> selected2 = new ArrayList<>();
    public NavController controller;
    public FragmentMainBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(
                inflater, container, false);
        RecyclerView r = binding.list1;
        model = new ViewModelProvider(getActivity()).get(MyViewModel.class);
        controller = Navigation.findNavController(container);
        db = model.getDb(this);
        NavigationUI.setupWithNavController(binding.navView, controller);
        r.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        getActivity().getWindow().setStatusBarColor(getContext().getColor(R.color.white));
        binding.appBarMain.imageView3.setOnClickListener(v -> {
            onMenu(v);
        });
        TextView text = binding.textView1;

        View.OnClickListener onClickListener;
        int id = controller.getCurrentDestination().getId();

        if (id == R.id.navigation_class) {
            text.setText(R.string.title_class);
            binding.fab.setOnClickListener(onAdd1);
            binding.appBarMain.imageView2.setOnClickListener(onEdit1);
            binding.appBarMain.imageView.setOnClickListener(v -> {
                onCheck(1);
            });
            r.setAdapter(new Adapter1(this));


        } else {

            text.setText(R.string.title_standard);
            binding.fab.setOnClickListener(onAdd2);
            binding.appBarMain.imageView2.setOnClickListener(onEdit2);
            binding.appBarMain.imageView.setOnClickListener(v -> {
                onCheck(2);
            });

            r.setAdapter(new Adapter2(this));
        }


        return binding.getRoot();
    }

    private void onMenu(View view) {
        PopupMenu menu = new PopupMenu(getContext(), view);
        menu.getMenu().add("load default Standards");
        menu.getMenu().add("save Backup");
        menu.getMenu().add("load Backup");

        PopupMenu.OnMenuItemClickListener listener = item -> {
            if (item.getTitle().equals("load default Standards"))
                OnLoadStandard();
            if (item.getTitle().equals("save Backup"))
                saveBackupFile();
            if (item.getTitle().equals("load Backup"))
                loadBackupFile();
            return true;
        };
        menu.setOnMenuItemClickListener(listener);
        menu.show();
    }


    private void OnLoadStandard() {
        AsyncTask.execute(() -> {
            db.loadDefaultStandard(new CambridgeAdditionalMathematics());
            db.loadDefaultStandard(new AdditionalMathematics1Sudan());
            db.loadDefaultStandard(new AddittionalMathematics2Sudan());
        });
        Toast.makeText(getContext(), "Standard added ", Toast.LENGTH_SHORT).show();

    }

    public void saveBackupFile() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/txt");
        intent.putExtra(Intent.EXTRA_TITLE, "standard-backup.txt");

        // Optionally, specify a URI for the directory that should be opened in
        // the system file picker when your app creates the document.

        startActivityForResult(intent, 12);
    }

    private void loadBackupFile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/*");

        startActivityForResult(intent,11 );
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 12 && resultCode == Activity.RESULT_OK) {
            onSaveBackup(data);
        }
        if (requestCode == 11 && resultCode == Activity.RESULT_OK) {
            onLoadBackup(data);
        }
    }

    private void onLoadBackup(Intent data) {
        Uri uri = null;
        if (data != null) {
            uri = data.getData();
            StringBuilder stringBuilder = new StringBuilder();
            try (InputStream inputStream =
                         getActivity().getContentResolver().openInputStream(uri);
                 BufferedReader reader = new BufferedReader(
                         new InputStreamReader(Objects.requireNonNull(inputStream)))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                Toast.makeText(getContext(), "File read successfully", Toast.LENGTH_SHORT).show();

                try {
                    Backup backup = Backup.fromJson(stringBuilder.toString());
                    db.loadFromBackupAsync(backup);
                    Toast.makeText(getContext(), "backup load successfully", Toast.LENGTH_SHORT).show();

                }catch (JsonSyntaxException exception)
                {
                    Toast.makeText(getContext(),"File Format illegal !", Toast.LENGTH_SHORT).show();
                }


            } catch (FileNotFoundException e) {
                Toast.makeText(getContext(), "File not found exception", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (IOException e) {
                Toast.makeText(getContext(), "IO exception", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }

    private void onSaveBackup(Intent data) {
        Uri uri = null;
        if (data != null) {
            uri = data.getData();
            // Perform operations on the document using its URI.
            Uri finalUri = uri;
            db.getBackUpAsync().observe(getViewLifecycleOwner(), s -> {
                try {
                    ParcelFileDescriptor pfd = getActivity().getContentResolver().
                            openFileDescriptor(finalUri, "w");
                    FileOutputStream fileOutputStream =
                            new FileOutputStream(pfd.getFileDescriptor());

                    fileOutputStream.write(s.getBytes());
                    // Let the document provider know you're done by closing the stream.
                    fileOutputStream.close();
                    pfd.close();
                    Toast.makeText(getContext(), "Backup saved successfully", Toast.LENGTH_LONG).show();

                } catch (FileNotFoundException e) {
                    Toast.makeText(getContext(), "File not found exception", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                } catch (IOException e) {
                    Toast.makeText(getContext(), "IO exception", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            });

        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public View.OnClickListener onDelete1 = v -> {
        db.deleteClassAsync(selected1).observe(this, d -> {
            binding.list1.setAdapter(new Adapter1(this));
            selected1.clear();
            changeIcon();
        });
    };
    public View.OnClickListener onDelete2 = v -> {

        db.deleteStandardAsync(selected2).observe(this, d -> {
            binding.list1.setAdapter(new Adapter2(this));
            selected2.clear();
            changeIcon();
        });


    };

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
                onDelete1.onClick(v);
            else
                onDelete2.onClick(v);
        });
    }

    private void changeIcon() {
        binding.appBarMain.imageView.setVisibility(View.GONE);
        binding.appBarMain.imageView2.setVisibility(View.GONE);
        binding.appBarMain.imageView3.setVisibility(View.VISIBLE);

    }

    public View.OnClickListener onAdd1 = view -> {
        model.editMood = false;
        controller.navigate(R.id.to_createClassFragment);
        selected1.clear();
        changeIcon();

    };
    public View.OnClickListener onAdd2 = view -> {
        model.editMood = false;
        controller.navigate(R.id.to_createStandardFragment);
        selected2.clear();
        changeIcon();
    };
    public View.OnClickListener onEdit1 = v -> {
        model.editMood = true;
        model.selClass = selected1.get(0);
        controller.navigate(R.id.to_createClassFragment);
        selected1.clear();
        changeIcon();
    };
    public View.OnClickListener onEdit2 = v -> {
        model.editMood = true;
        model.selStandard = selected2.get(0);
        controller.navigate(R.id.to_createStandardFragment);
        selected2.clear();
        changeIcon();
    };


}