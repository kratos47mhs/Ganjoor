package com.kratos47mhs.ganjoor.dialog;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;


import com.kratos47mhs.ganjoor.R;
import com.kratos47mhs.ganjoor.inner.Animator;

import java.util.Objects;

public class ContentDialog extends DialogFragment {
    private View content;
    private DialogListener listener;
    private boolean isWorking = false;
    private View loading;

    public ContentDialog() {
    }

    static ContentDialog newInstance(DialogListener listener) {
        ContentDialog dialog = new ContentDialog();
        dialog.listener = listener;
        Bundle args = new Bundle();
        args.putInt("title", R.string.new_folder);
        args.putInt("positive", R.string.create);
        args.putInt("negative", R.string.cancel);
        args.putInt("neutral", -1);
        args.putInt("layoutRes", R.layout.dialog_new_folder);
        dialog.setArguments(args);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);

        View view = inflater.inflate(R.layout.dialog_content, container);
        assert getArguments() != null;
        content = inflater.inflate(getArguments().getInt("layoutRes"), view.findViewById(R.id.content_holder));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();

        assert args != null;
        ((TextView) view.findViewById(R.id.title_txt)).setText(args.getInt("title"));

        int positive = args.getInt("positive");
        int negative = args.getInt("negative");
        int neutral = args.getInt("neutral");

        loading = view.findViewById(R.id.loading);

        final ContentDialog dialog = this;

        TextView positive_btn = view.findViewById(R.id.positive_btn);
        if (positive != -1) {
            positive_btn.setVisibility(View.VISIBLE);
            positive_btn.setText(positive);
            positive_btn.setOnClickListener(view1 -> {
                if (isWorking) return;
                listener.onPositive(dialog, content);
            });
        } else {
            positive_btn.setVisibility(View.GONE);
        }

        TextView negative_btn = view.findViewById(R.id.negative_btn);
        if (negative != -1) {
            negative_btn.setVisibility(View.VISIBLE);
            negative_btn.setText(negative);
            negative_btn.setOnClickListener(view12 -> {
                if (isWorking) return;
                listener.onNegative(dialog, content);
            });
        } else {
            negative_btn.setVisibility(View.GONE);
        }

        TextView neutral_btn = view.findViewById(R.id.neutral_btn);
        if (neutral != -1) {
            neutral_btn.setVisibility(View.VISIBLE);
            neutral_btn.setText(neutral);
            neutral_btn.setOnClickListener(view13 -> {
                if (isWorking) return;
                listener.onNeutral(dialog, content);
            });
        } else {
            neutral_btn.setVisibility(View.GONE);
        }

        listener.onInit(content);
    }

    public void setWorking(boolean status) {
        isWorking = status;
        loading.setVisibility(status ? View.VISIBLE : View.GONE);
    }

    public void displayError(EditText target, int text) {
        target.setError(getString(text));

        Animator.create(Objects.requireNonNull(getContext()).getApplicationContext())
                .on(getView())
                .animate(R.anim.shake);
    }

    boolean checkEditText(EditText target) {
        if (target.length() > 0) return true;

        target.setError(getString(R.string.required));

        Animator.create(Objects.requireNonNull(getContext()).getApplicationContext())
                .on(getView())
                .animate(R.anim.shake);

        return false;
    }

    public interface DialogListener {
        void onPositive(ContentDialog dialog, View content);

        void onNegative(ContentDialog dialog, View content);

        void onNeutral(ContentDialog dialog, View content);

        void onInit(View content);
    }
}
