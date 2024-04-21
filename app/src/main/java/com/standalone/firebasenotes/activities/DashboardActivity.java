package com.standalone.firebasenotes.activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.standalone.firebasenotes.R;
import com.standalone.firebasenotes.adapters.NoteAdapter;
import com.standalone.firebasenotes.controllers.FirebaseHelper;
import com.standalone.firebasenotes.fragments.NoteDialogFragment;
import com.standalone.firebasenotes.models.Note;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        final FloatingActionButton fab = findViewById(R.id.fab);
        final RecyclerView recyclerView = findViewById(R.id.recycler);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NoteDialogFragment().show(getSupportFragmentManager(), NoteDialogFragment.TAG);
            }
        });

        NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

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