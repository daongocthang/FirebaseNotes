package com.standalone.firebasenotes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.standalone.firebasenotes.adapters.NoteAdapter;
import com.standalone.firebasenotes.controllers.FireStoreHelper;
import com.standalone.firebasenotes.controllers.FirebaseHelper;
import com.standalone.firebasenotes.databinding.ActivityDashboardBinding;
import com.standalone.firebasenotes.fragments.NoteDialogFragment;
import com.standalone.firebasenotes.models.Note;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    final String TAG = this.getClass().getSimpleName();
    ActivityDashboardBinding binding;
    FirebaseAuth auth;
    FireStoreHelper<Note> helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        }

        helper = new FireStoreHelper<>("Notes");
        helper.fetch(new FireStoreHelper.OnFetchCompleteListener<Note>() {
            @Override
            public void onFetchComplete(ArrayList<Note> data) {
                for (Note note : data) {
                    Log.d(TAG, note.toMap().toString());
                }
            }
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NoteDialogFragment().show(getSupportFragmentManager(), NoteDialogFragment.TAG);
            }
        });

        NoteAdapter adapter = new NoteAdapter();
        binding.recycler.setAdapter(adapter);

        FirebaseHelper<Note> helper = new FirebaseHelper<>("notes");
        helper.addDataChangListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Note> itemList = helper.fetchAll(snapshot, Note.class);
                adapter.setItemList(itemList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}