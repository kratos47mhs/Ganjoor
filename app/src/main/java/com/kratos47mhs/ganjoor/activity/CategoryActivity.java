package com.kratos47mhs.ganjoor.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.kratos47mhs.ganjoor.R;
import com.kratos47mhs.ganjoor.db.OpenHelper;
import com.kratos47mhs.ganjoor.fragment.CategoryFragment;
import com.kratos47mhs.ganjoor.fragment.template.RecyclerFragment;
import com.kratos47mhs.ganjoor.inner.Animator;
import com.kratos47mhs.ganjoor.model.Category;

public class CategoryActivity extends AppCompatActivity implements RecyclerFragment.Callbacks {
    public static final int REQUEST_CODE = 1;
    public static final int RESULT_CHANGE = 101;
    private Toolbar toolbar;
    private CategoryFragment fragment;

    public CategoryActivity(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(Category.getStyle(getIntent().getIntExtra(OpenHelper.COLUMN_THEME, Category.THEME_GREEN)));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(findViewById(R.id.toolbar)); //TODO edited

        try {
            //noinspection ConstantConditions
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } catch (Exception ignored) {
        }

        toolbar.findViewById(R.id.back_btn).setOnClickListener(view -> onBackPressed());

        if (savedInstanceState == null) {
            fragment = new CategoryFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onChangeSelection(boolean state) {
        if (state) {
            Animator.create(getApplicationContext())
                    .on(toolbar)
                    .setEndVisibility(View.INVISIBLE)
                    .animate(R.anim.fade_out);
        } else {
            Animator.create(getApplicationContext())
                    .on(toolbar)
                    .setStartVisibility(View.VISIBLE)
                    .animate(R.anim.fade_in);
        }
    }

    @Override
    public void toggleOneSelection(boolean state) {
    }

    @Override
    public void onBackPressed() {
        if (fragment.isFabOpen) {
            fragment.toggleFab(true);
            return;
        }

        if (fragment.selectionState) {
            fragment.toggleSelection(false);
            return;
        }

        Intent data = new Intent();
        data.putExtra("position", fragment.categoryPosition);
        data.putExtra(OpenHelper.COLUMN_COUNTER, fragment.items.size());
        setResult(RESULT_CHANGE, data);
        finish();
    }
}