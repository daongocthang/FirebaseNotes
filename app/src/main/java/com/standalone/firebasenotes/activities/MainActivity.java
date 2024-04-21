package com.standalone.firebasenotes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.standalone.firebasenotes.databinding.ActivityLoginBinding;
import com.standalone.firebasenotes.utils.ValidationManager;

public class MainActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    ValidationManager manager = ValidationManager.getInstance();
    ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);

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
                        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                        startActivity(intent);
                    }
                }
        );

    }

    void onSubmit() {
        Toast.makeText(this, "All valid", Toast.LENGTH_SHORT).show();
    }
}
