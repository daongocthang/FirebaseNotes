package com.standalone.firebasenotes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.standalone.firebasenotes.databinding.ActivityRegisterBinding;
import com.standalone.firebasenotes.utils.ValidationManager;


public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    ValidationManager manager = ValidationManager.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnLoginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
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
                    Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
