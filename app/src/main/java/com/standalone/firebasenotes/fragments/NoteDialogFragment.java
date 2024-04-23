package com.standalone.firebasenotes.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.standalone.firebasenotes.R;
import com.standalone.firebasenotes.controllers.FireStoreHelper;
import com.standalone.firebasenotes.databinding.DialogNoteBinding;
import com.standalone.firebasenotes.models.Note;

import java.util.Objects;

public class NoteDialogFragment extends BottomSheetDialogFragment {
    public static final String TAG = NoteDialogFragment.class.getSimpleName();

    DialogNoteBinding binding;

    FireStoreHelper<Note> helper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.AppTheme_Dialog);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogNoteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        Objects.requireNonNull(getDialog()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Declaring Components
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmit();
            }
        });

        helper = new FireStoreHelper<>("Notes");
    }

    private void onSubmit() {
        // create a new value
        Note note = new Note();

        note.setTitle(Objects.requireNonNull(binding.edtTitle.getText()).toString());
        note.setContent(Objects.requireNonNull(binding.edtContent.getText()).toString());
        helper.create(note);
        dismiss();
    }
}
