package com.standalone.firebasenotes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.standalone.firebasenotes.interfaces.OnDataChangedListener;
import com.standalone.firebasenotes.adapters.NoteAdapter;
import com.standalone.firebasenotes.controllers.FireStoreHelper;
import com.standalone.firebasenotes.databinding.ActivityDashboardBinding;
import com.standalone.firebasenotes.fragments.NoteDialogFragment;
import com.standalone.firebasenotes.models.Note;
import com.standalone.firebasenotes.utils.ProgressDialog;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity implements OnDataChangedListener {

    final String TAG = this.getClass().getSimpleName();
    ActivityDashboardBinding binding;
    FirebaseAuth auth;
    FireStoreHelper<Note> helper;
    NoteAdapter adapter;

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

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NoteDialogFragment().show(getSupportFragmentManager(), NoteDialogFragment.TAG);
            }
        });

        adapter = new NoteAdapter();
        binding.recycler.setAdapter(adapter);

        onDataChangedListener();
    }

    @Override
    public void onDataChangedListener() {
        ProgressDialog progressDlg = new ProgressDialog(this);
        progressDlg.show();

        helper = new FireStoreHelper<>();
        helper.fetch(Note.class, new FireStoreHelper.OnFetchCompleteListener<Note>() {
            @Override
            public void onFetchComplete(ArrayList<Note> data) {
                adapter.setItemList(data);
                progressDlg.dismiss();
            }
        });
    }
}