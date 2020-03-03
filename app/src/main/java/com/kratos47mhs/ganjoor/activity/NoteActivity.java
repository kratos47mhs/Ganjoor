package com.kratos47mhs.ganjoor.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


//import androidx.annotation.Nullable;
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;


import com.kratos47mhs.ganjoor.db.OpenHelper;
import com.kratos47mhs.ganjoor.fragment.DrawingNoteFragment;
import com.kratos47mhs.ganjoor.fragment.SimpleNoteFragment;
import com.kratos47mhs.ganjoor.fragment.template.NoteFragment;
import com.kratos47mhs.ganjoor.model.Category;
import com.kratos47mhs.ganjoor.model.DatabaseModel;

import com.kratos47mhs.ganjoor.R;

public class NoteActivity extends AppCompatActivity implements NoteFragment.Callbacks {
    public static final int REQUEST_CODE = 2;
    public static final int RESULT_NEW = 101;
    public static final int RESULT_EDIT = 102;
    public static final int RESULT_DELETE = 103;

    private int noteResult = 0;
    private int position;

    private NoteFragment fragment;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Intent data = getIntent();
        setTheme(Category.getStyle(data.getIntExtra(OpenHelper.COLUMN_THEME, Category.THEME_GREEN)));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);



        position = data.getIntExtra("position", 0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            //noinspection ConstantConditions
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } catch (Exception ignored) {
        }

        toolbar.findViewById(R.id.back_btn).setOnClickListener(view -> onBackPressed());

        if (savedInstanceState == null) {
            if (data.getIntExtra(OpenHelper.COLUMN_TYPE, DatabaseModel.TYPE_NOTE_SIMPLE) == DatabaseModel.TYPE_NOTE_SIMPLE) {
                fragment = new SimpleNoteFragment();
            } else {
                fragment = new DrawingNoteFragment();
            }

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        fragment.saveNote(() -> {
            final Intent data = new Intent();
            data.putExtra("position", position);
            data.putExtra(OpenHelper.COLUMN_ID, fragment.note.id);

            switch (noteResult) {
                case RESULT_NEW:
                    data.putExtra(OpenHelper.COLUMN_TYPE, fragment.note.type);
                    data.putExtra(OpenHelper.COLUMN_DATE, fragment.note.createdAt);
                case RESULT_EDIT:
                    data.putExtra(OpenHelper.COLUMN_TITLE, fragment.note.title);
            }

            runOnUiThread(() -> {
                setResult(noteResult, data);
                finish();
            });
        });
    }

    @Override
    public void setNoteResult(int result, boolean closeActivity) {
        noteResult = result;
        if (closeActivity) {
            Intent data = new Intent();
            data.putExtra("position", position);
            data.putExtra(OpenHelper.COLUMN_ID, fragment.note.id);
            setResult(result, data);
            finish();
        }
    }
}