package com.standalone.firebasenotes.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.standalone.firebasenotes.R;

public class MsgBox {
    public static final String ERR_MSG_CHECK_EMPTY = "Vui lòng không để trống.";
    public static final String ERR_MSG_CHECK_EMAIL = "Email không hợp lệ.";
    public static final String ERR_MSG_MATCH_PASSWORD = "Mật khẩu nhập lại không khớp";
    public static final String ERR_MSG_CHECK_PASSWORD = "Mật khẩu có độ dài từ 6-20 ký tự bao gồm chữ cái và số";
    public static final String MSG_SIGNUP_SUCCESS = "Bạn đã đăng ký thành công.";
    public static final String MSG_SIGNUP_FAILURE = "Email đã được sử dụng.";
    public static final String MSG_LOGIN_FAILURE = "Email hoặc mật khẩu không đúng.";

    public static void alert(Context context, String msg) {
        new AlertDialog.Builder(context).setMessage(msg).setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();
    }
}
