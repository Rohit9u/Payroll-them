package com.example.payrollthem;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Employer_login extends AppCompatActivity {
    private Button RegnBtn;
    private FirebaseAuth auth;
    String email;
    private EditText emaillogin;
    private Button login;
    private EditText passwordlogin;
    public ProgressBar pbar;
    private Button reset;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_login);
        this.auth = FirebaseAuth.getInstance();
        this.reset =findViewById(R.id.reset);
        if (this.auth.getCurrentUser() != null) {
            startActivity(new Intent(this, NavdrawerActivity.class));
            finish();
        }
        this.reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Employer_login.this.startActivity(new Intent(Employer_login.this, ResetPassword.class));
            }
        });
        this.RegnBtn = findViewById(R.id.notRegistered);
        this.login = findViewById(R.id.login);
        this.emaillogin = findViewById(R.id.emailidlogin);
        this.passwordlogin =findViewById(R.id.passwordlogin);
        this.pbar = findViewById(R.id.progresslogn);
        this.RegnBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Employer_login.this.startActivity(new Intent(Employer_login.this, Employer_Registration.class));
            }
        });
        this.login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Employer_login.this.loginto();
            }
        });
    }

    public void loginto() {
        this.pbar.setVisibility(View.VISIBLE);
        String emaillo = this.emaillogin.getText().toString();
        String passwordlo = this.passwordlogin.getText().toString();
        if (TextUtils.isEmpty(emaillo)) {
            this.pbar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "enter email", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(passwordlo)) {
            this.pbar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Password is empty", Toast.LENGTH_LONG).show();
        } else {
            this.auth.signInWithEmailAndPassword(emaillo, passwordlo).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                public void onComplete(Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Employer_login.this.getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                        Employer_login.this.pbar.setVisibility(View.INVISIBLE);
                        Employer_login.this.startActivity(new Intent(Employer_login.this, NavdrawerActivity.class));
                        return;
                    }
                    Toast.makeText(Employer_login.this.getApplicationContext(), "Login failed", Toast.LENGTH_LONG).show();
                    Employer_login.this.pbar.setVisibility(View.INVISIBLE);
                }
            });
        }
    }
}
