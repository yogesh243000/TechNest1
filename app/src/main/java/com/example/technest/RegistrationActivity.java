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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import io.github.muddz.styleabletoast.StyleableToast;

public class RegistrationActivity extends AppCompatActivity {

    private EditText editTextName1, editTextName2, editTextEmail, editTextPassword;
    private ProgressBar progressBar;
    private TextView textViewLogin;
    private Button buttonRegister;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initializeFirebase();

        editTextName1 = findViewById(R.id.etFirstName);
        editTextName2 = findViewById(R.id.etLastName);
        editTextEmail = findViewById(R.id.etEmail);
        editTextPassword = findViewById(R.id.etPassword);
        progressBar = findViewById(R.id.progressBar);
        textViewLogin = findViewById(R.id.textView);
        buttonRegister = findViewById(R.id.btnRegister);

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.button_color);
        buttonRegister.setBackground(drawable);

        SpannableString spannableString = new SpannableString("Already have an account? Login");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        };
        spannableString.setSpan(clickableSpan, 24, 29, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textViewLogin.setText(spannableString);
        textViewLogin.setMovementMethod(LinkMovementMethod.getInstance());

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonRegister.setEnabled(false);

                String firstName = editTextName1.getText().toString().trim();
                String lastName = editTextName2.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    StyleableToast.makeText(RegistrationActivity.this,"Please fill in all fields", R.style.mytoast).show();
                    buttonRegister.setEnabled(true);
                } else if (password.length() < 6) {
                    StyleableToast.makeText(RegistrationActivity.this,"Please fill in all fields", R.style.mytoast).show();
                    buttonRegister.setEnabled(true);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    customRegisterUser(email, password);
                }
            }
        });
    }

    private void initializeFirebase() {
        FirebaseApp.initializeApp(this);
    }

    private void customRegisterUser(String email, String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {

                        StyleableToast.makeText(RegistrationActivity.this, "Registration successful", R.style.mytoast).show();
                        Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                        startActivity(intent);
                    } else {
                        StyleableToast.makeText(RegistrationActivity.this, "Already registered", R.style.mytoast).show();
                        buttonRegister.setEnabled(true);
                    }
                });
    }

}
