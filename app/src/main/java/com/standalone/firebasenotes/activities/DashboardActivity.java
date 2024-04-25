package com.standalone.firebasenotes.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.standalone.firebasenotes.adapters.NoteAdapter;
import com.standalone.firebasenotes.databinding.ActivityDashboardBinding;
import com.standalone.firebasenotes.fragments.NoteDialogFragment;
import com.standalone.firebasenotes.interfaces.OnDialogDismissListener;
import com.standalone.firebasenotes.models.Note;

public class DashboardActivity extends AppCompatActivity implements NoteAdapter.OnItemClickListener, NoteDialogFragment.OnDialogDismissListener {

    final String TAG = this.getClass().getSimpleName();
    ActivityDashboardBinding binding;
    NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        }
        adapter = new NoteAdapter(this);
        binding.recycler.setAdapter(adapter);
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NoteDialogFragment().show(getSupportFragmentManager(), NoteDialogFragment.TAG);
            }
        });
        adapter.fetchData();
    }

    @Override
    public void onItemClick(int position) {
        adapter.updateNote(position);
    }

    @Override
    public void onDialogDismiss(DialogInterface dialog) {
        adapter.fetchData();
    }
}