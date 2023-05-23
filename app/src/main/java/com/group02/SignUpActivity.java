package com.group02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    Button btnSignIn;
    EditText etUsername;
    EditText etPassword;
    EditText etConfirm;
    Button btnSignUp;
    HashMap<String, String> accounts;
    Toast toast;
    String username;
    String password;
    String confirm;
    private final int RESULT_OK = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Intent intent = getIntent();
        accounts = (HashMap<String, String>) intent.getSerializableExtra("accounts");
        etUsername = findViewById(R.id.usernameTxt);
        etPassword = findViewById(R.id.passwordTxt);
        etConfirm = findViewById(R.id.confirmTxt);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(signUp);
        btnSignIn = findViewById(R.id.btnGoToLogin);
        btnSignIn.setOnClickListener(gotoSignIn);

    }

    private View.OnClickListener gotoSignIn = v ->
    {
        StoreData();
        finish();
    };

    private void StoreData() {
        Intent data = new Intent();
        data.putExtra("accounts", accounts);
        setResult(RESULT_OK, data);
    }

    private View.OnClickListener signUp = v -> {
        username = etUsername.getText().toString().trim();
        password = etPassword.getText().toString();
        confirm = etConfirm.getText().toString();
        if (IsEmpty()) return;
        if (IsNotMatch()) return;
        if (IsExisted()) return;
        accounts.put(username, password);
        if (toast != null) toast.cancel();
        toast = Toast.makeText(this, "Sign up successfully", Toast.LENGTH_SHORT);
        toast.show();
    };

    private boolean IsEmpty() {
        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            if (toast != null) toast.cancel();
            toast = Toast.makeText(this, "All field must be filled", Toast.LENGTH_SHORT);
            toast.show();
            return true;
        }
        return false;
    }

    private boolean IsNotMatch() {
        if (password.equals(confirm)) return false;
        if (toast != null) toast.cancel();
        toast = Toast.makeText(this, "Confirm password doesn't match", Toast.LENGTH_SHORT);
        toast.show();
        return true;
    }

    private boolean IsExisted() {
        if (accounts.get(username) == null) return false;
        if (toast != null) toast.cancel();
        toast = Toast.makeText(this, "Account already existed", Toast.LENGTH_SHORT);
        toast.show();
        return true;
    }
}