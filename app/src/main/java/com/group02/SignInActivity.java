package com.group02;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class SignInActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    Button btnSignIn;
    Button btnSignUp;
    HashMap<String, String> accounts = new HashMap<>();
    String username;
    String password;
    Toast toast;
    private final int RESULT_OK = 1;
    private ActivityResultLauncher<Intent> signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        signUp = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK){
                Intent data = result.getData();
                accounts = (HashMap<String, String>) data.getSerializableExtra("accounts");
            }
        });
        etUsername = findViewById(R.id.usernameTxt);
        etPassword = findViewById(R.id.passwordTxt);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn.setOnClickListener(signIn);
        btnSignUp.setOnClickListener(goToSignUp);
    }

    private View.OnClickListener goToSignUp = v -> {
        Intent intent = new Intent();
        intent.setClass(v.getContext(), SignUpActivity.class);
        intent.putExtra("accounts", accounts);
        signUp.launch(intent);
    };
    private View.OnClickListener signIn = v -> {
        username = etUsername.getText().toString().trim();
        password = etPassword.getText().toString();
        // Start the game after login successfully
        if (IsNotEmpty() && IsSuccessLogin()) {
            Intent mainIntent = new Intent(this, MainActivity.class);
            mainIntent.putExtra("username", username);
            startActivity(mainIntent);
            finish();
        }
    };

    private boolean IsNotEmpty() {
        if (username.isEmpty() || password.isEmpty()) {
            if (toast != null) toast.cancel();
            toast = Toast.makeText(this, "All field must be filled", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        return true;
    }

    private boolean IsSuccessLogin() {
        String pwd = accounts.get(username);
        if (password.equals(pwd)) return true;
        if (toast != null) toast.cancel();
        toast = Toast.makeText(this, "Username or password incorrect", Toast.LENGTH_SHORT);
        toast.show();
        return false;
    }
}