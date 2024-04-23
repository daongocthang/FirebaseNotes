package com.standalone.firebasenotes.utils;

import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationManager {
    static ValidationManager instance;
    final String EMAIL_PATTERN = "[a-z0-9A-Z._-]+@[a-z]+\\.[a-z]+";
    final String PASSWORD_PATTERN = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=\\S+$).{6,20}$";

    TextInputLayout textInputLayout;
    EditText editText;
    Pattern pattern;
    Matcher matcher;

    boolean isEmpty;

    ArrayList<TextInputLayout> invalidList = new ArrayList<>();

    public static ValidationManager getInstance() {
        if (instance == null) instance = new ValidationManager();
        return instance;
    }

    public ValidationManager doValidation(TextInputLayout textInputLayout) {
        this.textInputLayout = textInputLayout;
        this.editText = textInputLayout.getEditText();
        this.isEmpty = false;
        return instance;
    }

    public ValidationManager checkEmpty() {
        isEmpty = editText.getText().toString().isEmpty();
        boolean isEmptyValid = !isEmpty;

        if (isEmpty) {
            textInputLayout.setError(MsgBox.ERR_MSG_CHECK_EMPTY);
            appendToInvalidListIfNotExists();
        }

        return instance;
    }

    public ValidationManager checkEmail() {
        boolean isEmailValid = editText.getText().toString().matches(EMAIL_PATTERN);
        if (!isEmpty && !isEmailValid) {
            textInputLayout.setError(MsgBox.ERR_MSG_CHECK_EMAIL);
            appendToInvalidListIfNotExists();
        }

        return instance;
    }

    public ValidationManager matchPassword(TextInputLayout password) {
        String passwordString = Objects.requireNonNull(password.getEditText()).getText().toString();
        boolean hasMatched = editText.getText().toString().equals(passwordString) && !passwordString.isEmpty();
        if (!isEmpty && !hasMatched) {
            textInputLayout.setError(MsgBox.ERR_MSG_MATCH_PASSWORD);
            appendToInvalidListIfNotExists();
        }

        return instance;
    }

    public ValidationManager checkPassword() {
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(editText.getText().toString());
        boolean isPasswordValid = matcher.matches();

        if (!isEmpty && !isPasswordValid) {
            textInputLayout.setError(MsgBox.ERR_MSG_CHECK_PASSWORD);
            invalidList.add(textInputLayout);
            appendToInvalidListIfNotExists();
        }

        return instance;
    }

    public boolean isAllValid() {
        return (invalidList.size() == 0);
    }

    public void refresh() {
        if (invalidList.size() == 0) return;

        for (TextInputLayout child : invalidList) {
            child.setError(null);
        }

        invalidList.clear();
    }

    private void appendToInvalidListIfNotExists() {
        if (!invalidList.contains(textInputLayout)) {
            invalidList.add(textInputLayout);
        }
    }
}
