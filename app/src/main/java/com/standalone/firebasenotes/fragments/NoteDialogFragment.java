package com.standalone.firebasenotes.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.standalone.firebasenotes.R;
import com.standalone.firebasenotes.controllers.FireStoreHelper;
import com.standalone.firebasenotes.databinding.DialogNoteBinding;
import com.standalone.firebasenotes.models.Note;
import com.standalone.firebasenotes.utils.ProgressDialog;

import java.util.Objects;

public class NoteDialogFragment extends BottomSheetDialogFragment {
    public static final String TAG = NoteDialogFragment.class.getSimpleName();
    String keyReference;

    DialogNoteBinding binding;

    public NoteDialogFragment() {
    }

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

        Bundle args = getArguments();
        if (args != null && args.containsKey("note")) {
            Note note = (Note) args.getSerializable("note");
            binding.edtTitle.setText(note.getTitle());
            binding.edtContent.setText(note.getContent());
            keyReference = note.getKey();
        }
    }


    private void onSubmit() {
        // create a new value
        Note note = new Note();
        note.setTitle(Objects.requireNonNull(binding.edtTitle.getText()).toString());
        note.setContent(Objects.requireNonNull(binding.edtContent.getText()).toString());
        ProgressDialog progress = new ProgressDialog(binding.getRoot().getContext());
        progress.show();
        FireStoreHelper<Note> helper = new FireStoreHelper<>();
        Task<Void> task;
        if (keyReference == null) {
            task = helper.create(note);
        } else {
            task = helper.update(keyReference, note);
        }

        task.addOnCompleteListener(t -> {
            progress.dismiss();
        });


        dismiss();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        Context context=binding.getRoot().getContext();
        if(context instanceof  OnDialogDismissListener){
            ((OnDialogDismissListener) context).onDialogDismiss(dialog);
        }

        super.onDismiss(dialog);
    }

    public interface OnDialogDismissListener {
        void onDialogDismiss(DialogInterface dialog);
    }
}
