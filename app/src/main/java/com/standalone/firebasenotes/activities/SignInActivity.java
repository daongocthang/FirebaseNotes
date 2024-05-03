package com.standalone.firebasenotes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.standalone.firebasenotes.databinding.ActivitySignInBinding;
import com.standalone.firebasenotes.utils.MsgBox;
import com.standalone.firebasenotes.utils.ValidationManager;

import java.util.Objects;

public class SignInActivity extends AppCompatActivity {
    ActivitySignInBinding binding;
    ValidationManager manager = ValidationManager.getInstance();
    FirebaseAuth auth;

    final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                manager.refresh();
                manager.doValidation(binding.tilEmail).checkEmpty().checkEmail();
                manager.doValidation(binding.tilPassword).checkEmpty();

                if (manager.isAllValid()) {
                    onSubmit();
                }
            }
        });

        binding.btnRegisterHere.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SignInActivity.this, RegisterActivity.class);
                        startActivity(intent);
                    }
                }
        );

        binding.btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, ResetPasswordActivity.class));
            }
        });
    }

    void onSubmit() {
        String email = Objects.requireNonNull(binding.edtEmail.getText()).toString().trim();
        String password = Objects.requireNonNull(binding.edtPassword.getText()).toString().trim();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "signInWithEmail:success");
                    startActivity(new Intent(SignInActivity.this, DashboardActivity.class));
                    finish();
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    MsgBox.alert(SignInActivity.this, MsgBox.MSG_LOGIN_FAILURE);
                }
            }
        });
    }
}
