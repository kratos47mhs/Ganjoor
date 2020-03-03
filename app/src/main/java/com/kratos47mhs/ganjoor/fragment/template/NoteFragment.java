package com.kratos47mhs.ganjoor.fragment.template;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.kratos47mhs.ganjoor.R;
import com.kratos47mhs.ganjoor.activity.NoteActivity;
import com.kratos47mhs.ganjoor.db.OpenHelper;
import com.kratos47mhs.ganjoor.model.DatabaseModel;
import com.kratos47mhs.ganjoor.model.Note;

import java.util.Objects;

abstract public class NoteFragment extends Fragment {
    public Note note = null;
    public Callbacks activity;
    public EditText title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayout(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View deleteBtn = Objects.requireNonNull(getActivity()).findViewById(R.id.delete_btn);
        title = view.findViewById(R.id.title_txt);

        Intent data = getActivity().getIntent();
        long noteId = data.getLongExtra(OpenHelper.COLUMN_ID, DatabaseModel.NEW_MODEL_ID);
        final long categoryId = data.getLongExtra(OpenHelper.COLUMN_PARENT_ID, DatabaseModel.NEW_MODEL_ID);

        if (noteId != DatabaseModel.NEW_MODEL_ID) {
            note = Note.find(noteId);
        }

        if (note == null) {
            note = new Note();
            activity.setNoteResult(NoteActivity.RESULT_NEW, false);
            deleteBtn.setVisibility(View.GONE);
            note.categoryId = categoryId;
            note.title = "";
            note.body = "";
            note.isArchived = false;
            note.type = data.getIntExtra(OpenHelper.COLUMN_TYPE, DatabaseModel.TYPE_NOTE_SIMPLE);
        } else {
            activity.setNoteResult(NoteActivity.RESULT_EDIT, false);
            deleteBtn.setVisibility(View.VISIBLE);
            deleteBtn.setOnClickListener(view1 -> activity.setNoteResult(NoteActivity.RESULT_DELETE, true));
        }

        title.setText(note.title);

        init(view);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (Callbacks) context;
    }

    public void saveNote(SaveListener listener) {
        String inputTitle = title.getText().toString();
        if (inputTitle.isEmpty()) inputTitle = "Untitled";
        note.title = inputTitle;
        if (note.id == DatabaseModel.NEW_MODEL_ID) {
            note.createdAt = System.currentTimeMillis();
        }
    }

    abstract public int getLayout();

    abstract public void init(View view);

    public interface SaveListener {
        void onSave();
    }

    public interface Callbacks {
        void setNoteResult(int result, boolean closeActivity);
    }
}
