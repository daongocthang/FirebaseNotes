package com.standalone.firebasenotes.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.standalone.firebasenotes.adapters.NoteAdapter;
import com.standalone.firebasenotes.adapters.RecyclerItemTouchHelper;
import com.standalone.firebasenotes.databinding.ActivityDashboardBinding;
import com.standalone.firebasenotes.fragments.NoteDialogFragment;
import com.standalone.firebasenotes.models.Note;

public class DashboardActivity extends AppCompatActivity implements NoteDialogFragment.DialogEventListener {

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

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(binding.recycler);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NoteDialogFragment().show(getSupportFragmentManager(), NoteDialogFragment.TAG);
            }
        });
        adapter.fetchData();
    }

    @Override
    public void onDialogSubmit(DialogInterface dialog, Note note) {
        adapter.addItem(note);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onDialogCancel(DialogInterface dialog) {
        adapter.notifyDataSetChanged();
    }
}