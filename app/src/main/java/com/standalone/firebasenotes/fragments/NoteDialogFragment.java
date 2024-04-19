package com.standalone.firebasenotes.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.standalone.firebasenotes.R;

import java.util.Objects;

public class NoteDialogFragment extends BottomSheetDialogFragment {
    public static final String TAG = NoteDialogFragment.class.getSimpleName();
    EditText edTitle, edSubtitle;
    Button btSubmit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.AppTheme_Dialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_note, container, false);
        Objects.requireNonNull(getDialog()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Declaring Components
        edTitle = view.findViewById(R.id.edt_title);
        edSubtitle = view.findViewById(R.id.edt_subtitle);
        btSubmit = view.findViewById(R.id.btn_submit);
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onSubmit();
            }
        });
    }

    private void onSubmit() {
        Toast.makeText(this.getContext(), "Progressing", Toast.LENGTH_SHORT).show();
        dismiss();
    }
}
