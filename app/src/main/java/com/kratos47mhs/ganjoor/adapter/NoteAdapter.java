package com.kratos47mhs.ganjoor.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kratos47mhs.ganjoor.adapter.template.ModelAdapter;
import com.kratos47mhs.ganjoor.model.Note;
import com.kratos47mhs.ganjoor.widget.NoteViewHolder;

import java.util.ArrayList;

import com.kratos47mhs.ganjoor.R;

public class NoteAdapter extends ModelAdapter<Note, NoteViewHolder> {
    public NoteAdapter(ArrayList<Note> items, ArrayList<Note> selected, ClickListener<Note> listener) {
        super(items, selected, listener);
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false));
    }
}