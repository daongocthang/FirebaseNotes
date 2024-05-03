package com.standalone.firebasenotes.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.standalone.firebasenotes.R;
import com.standalone.firebasenotes.databinding.ActivityResetPasswordBinding;
import com.standalone.firebasenotes.utils.MsgBox;
import com.standalone.firebasenotes.utils.ProgressDialog;
import com.standalone.firebasenotes.utils.ValidationManager;

import java.util.Objects;

public class ResetPasswordActivity extends AppCompatActivity {
    final String TAG = getClass().getCanonicalName();

    ActivityResetPasswordBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;

        actionBar.setTitle(R.string.reset_password);

        auth = FirebaseAuth.getInstance();
        auth.setLanguageCode("vi");
        final ValidationManager validator = ValidationManager.getInstance();
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.refresh();
                validator.doValidation(binding.tilEmail).checkEmpty().checkEmail();
                if (validator.isAllValid()) {
                    onSubmit();
                }
            }
        });
    }

    private void onSubmit() {
        final String email = Objects.requireNonNull(binding.edtEmail.getText()).toString().trim();
        ProgressDialog progress = new ProgressDialog(this);
        progress.show();

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progress.dismiss();
                if (task.isSuccessful()) {
                    MsgBox.alert(ResetPasswordActivity.this, String.format(MsgBox.MSG_SEND_MAIL_SUCCESS, email));
                } else {
                    Log.w(TAG, "Something wrong" + task.getException());
                }
            }
        });
    }
}