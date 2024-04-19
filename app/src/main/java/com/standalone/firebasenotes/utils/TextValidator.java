package com.standalone.firebasenotes.utils;

import android.content.Context;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class TextValidator {
    private static TextValidator instance = null;
    private EditText editText;
    private ErrorSetter errorSetter;
    private String emailPattern="[a-zA-Z0-9._]+@[a-z]+\\.+[a-z]+";

    private final String ERR_MSG_CHK_EMPTY = "Field can not be empty.";
    private final String ERR_MSG_CHK_EMAIL = "Invalid email.";
    private final String ERR_MSG_CHK_PASSWORD = "Password does not match.";

    private boolean isAllValid = false;
    private boolean isEmpty = true;
    private boolean isEmptyValid = false;
    private boolean isEmailValid = false;
    private boolean isPasswordValid = false;

    private TextValidator() {
    }

    public static TextValidator getInstance() {
        if (instance == null) instance = new TextValidator();
        return instance;
    }

    public TextValidator doValidation(Context context, EditText editText) {
        errorSetter = (ErrorSetter) context;
        this.editText = editText;

        return instance;
    }

    public TextValidator checkEmpty() {
        if (editText.getText().toString().isEmpty()) {
            errorSetter.setError(editText, ERR_MSG_CHK_EMPTY);
            isEmptyValid = false;
        } else {
            isEmptyValid = true;
        }

        isEmpty = !isEmptyValid;
        return instance;
    }

    public boolean isAllValid() {
        return isEmptyValid;
    }

    interface ErrorSetter {
        void setError(EditText editText, String errorMessenger);
    }
}
