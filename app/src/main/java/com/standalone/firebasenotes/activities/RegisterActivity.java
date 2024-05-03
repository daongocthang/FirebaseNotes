package com.standalone.firebasenotes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.standalone.firebasenotes.R;
import com.standalone.firebasenotes.databinding.ActivityRegisterBinding;
import com.standalone.firebasenotes.utils.MsgBox;
import com.standalone.firebasenotes.utils.ValidationManager;

import java.util.Objects;


public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    ValidationManager manager;
    FirebaseAuth auth;

    final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;

        actionBar.setTitle(R.string.register);


        manager = ValidationManager.getInstance();
        auth = FirebaseAuth.getInstance();

        binding.btnLoginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, SignInActivity.class));
                finish();
            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.refresh();

                manager.doValidation(binding.tilEmail).checkEmpty().checkEmail();
                manager.doValidation(binding.tilPassword).checkEmpty().checkPassword();
                manager.doValidation(binding.tilConfirmPassword).checkEmpty().matchPassword(binding.tilPassword);

                if (manager.isAllValid()) {
                    onSubmit();
                }
            }
        });
    }

    void onSubmit() {
        String email = Objects.requireNonNull(binding.edtEmail.getText()).toString().trim();
        String password = Objects.requireNonNull(binding.edtPassword.getText()).toString().trim();

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "createUserWithEMail:success");
                    MsgBox.alert(RegisterActivity.this, MsgBox.MSG_SIGNUP_SUCCESS);
                } else {
                    Log.w(TAG, "createUserWithEMail:failure", task.getException());
                    MsgBox.alert(RegisterActivity.this, MsgBox.MSG_SIGNUP_FAILURE);
                }
            }
        });
    }


}
