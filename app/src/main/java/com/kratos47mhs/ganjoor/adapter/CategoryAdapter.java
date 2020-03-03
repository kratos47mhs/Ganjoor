package com.kratos47mhs.ganjoor.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kratos47mhs.ganjoor.adapter.template.ModelAdapter;
import com.kratos47mhs.ganjoor.model.Category;
import com.kratos47mhs.ganjoor.widget.CategoryViewHolder;

import java.util.ArrayList;

import com.kratos47mhs.ganjoor.R;

public class CategoryAdapter extends ModelAdapter<Category, CategoryViewHolder> {
    public CategoryAdapter(ArrayList<Category> items, ArrayList<Category> selected, ClickListener<Category> listener) {
        super(items, selected, listener);
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false));
    }
}