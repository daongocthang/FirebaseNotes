package com.standalone.firebasenotes.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.standalone.firebasenotes.R;
import com.standalone.firebasenotes.fragments.NoteDialogFragment;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NoteDialogFragment().show(getSupportFragmentManager(), NoteDialogFragment.TAG);
            }
        });
    }

}