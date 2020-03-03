package com.kratos47mhs.ganjoor.fragment;

import android.util.Base64;
import android.view.View;

import com.kratos47mhs.ganjoor.CanvasView;

import com.kratos47mhs.ganjoor.R;
import com.kratos47mhs.ganjoor.fragment.template.NoteFragment;
import com.kratos47mhs.ganjoor.model.DatabaseModel;

public class DrawingNoteFragment extends NoteFragment {
    private CanvasView canvas;

    public DrawingNoteFragment() {
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_drawing_note;
    }

    @Override
    public void saveNote(final SaveListener listener) {
        super.saveNote(listener);

        new Thread() {
            @Override
            public void run() {
                note.body = Base64.encodeToString(canvas.getBitmapAsByteArray(), Base64.NO_WRAP);

                long id = note.save();
                if (note.id == DatabaseModel.NEW_MODEL_ID) {
                    note.id = id;
                }

                listener.onSave();
                interrupt();
            }
        }.start();
    }

    @Override
    public void init(View view) {
        canvas = view.findViewById(R.id.canvas);

        view.findViewById(R.id.pen_tool).setOnClickListener(view1 -> {
            canvas.setMode(CanvasView.Mode.DRAW);
            canvas.setPaintStrokeWidth(3F);
        });

        view.findViewById(R.id.eraser_tool).setOnClickListener(view12 -> {
            canvas.setMode(CanvasView.Mode.ERASER);
            canvas.setPaintStrokeWidth(40F);
        });

        if (!note.body.isEmpty()) {
            canvas.drawBitmap(Base64.decode(note.body, Base64.NO_WRAP));
        }
    }
}
