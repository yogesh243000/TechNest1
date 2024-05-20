package com.example.technest;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.github.muddz.styleabletoast.StyleableToast;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsernameOrEmail, editTextPassword;
    TextView forgetPassword, register;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        editTextUsernameOrEmail = findViewById(R.id.etUsername);
        editTextPassword = findViewById(R.id.etPassword);
        Button buttonLogin = findViewById(R.id.btnLogin);
        forgetPassword = findViewById(R.id.textPassword);
        register = findViewById(R.id.textRegister);

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.button_color);
        buttonLogin.setBackground(drawable);
        TextView textRegister = findViewById(R.id.textRegister);

        // Create a SpannableString to make the text clickable
        SpannableString spannableString = new SpannableString("Don't have an account? Register now");

        // Create a ClickableSpan for the register action
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                // Start the RegistrationActivity
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        };

        // Set the ClickableSpan to the spannable string and specify the range of text to make clickable
        spannableString.setSpan(clickableSpan, 23, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set the spannable string to the TextView and enable the movement method to handle clicks
        textRegister.setText(spannableString);
        textRegister.setMovementMethod(LinkMovementMethod.getInstance());


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameOrEmail = editTextUsernameOrEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (usernameOrEmail.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Call login function
                    login(usernameOrEmail, password);
                }
            }
        });
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle forget password click
                Toast.makeText(LoginActivity.this, "Forget button clicked! ", Toast.LENGTH_SHORT).show();
            }

        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle register click
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login(String usernameOrEmail, String password) {
        mAuth.signInWithEmailAndPassword(usernameOrEmail, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // User is signed in
                            StyleableToast.makeText(LoginActivity.this, "Login Successful", R.style.mytoast).show();
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        StyleableToast.makeText(LoginActivity.this, "Authentication failed.", R.style.mytoast).show();

                    }
                });
    }
}
