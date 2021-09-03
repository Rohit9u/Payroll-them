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
import com.google.firebase.auth.FirebaseUser;

public class Employee_login extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText email;
    private String emailstr;
    private EditText password;
    private String passwordstr;
    public ProgressBar pbar;
    private Button register;
    private Button reset;
    private Button signinBtn;
    private FirebaseUser user;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_login);
        FirebaseAuth instance = FirebaseAuth.getInstance();
        auth = instance;
        FirebaseUser currentUser = instance.getCurrentUser();
        user = currentUser;
        if (currentUser != null) {
            startActivity(new Intent(getApplicationContext(), employeeOption.class));
            finish();
        }
        reset = findViewById(R.id.resetemp);
        signinBtn =findViewById(R.id.loginemp);
        register = findViewById(R.id.newuser);
        email = findViewById(R.id.emailidemp);
        pbar = findViewById(R.id.pbarsignin);
        password =findViewById(R.id.passwordemp);
        reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
               startActivity(new Intent(Employee_login.this, ResetPassword.class));
            }
        });
        signinBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Employee_login.this.Login();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EmployeeREg.class));
            }
        });
    }

    public void Login() {
        emailstr = email.getText().toString().trim();
        passwordstr = password.getText().toString().trim();
        pbar.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(emailstr)) {
            pbar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "enter email", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(passwordstr)) {
            pbar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Password is empty", Toast.LENGTH_LONG).show();
        } else {
            auth.signInWithEmailAndPassword(emailstr, passwordstr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                public void onComplete(Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                        pbar.setVisibility(View.INVISIBLE);
                        startActivity(new Intent(Employee_login.this, employeeOption.class));
                        return;
                    }
                    Toast.makeText(Employee_login.this.getApplicationContext(), "Login failed", Toast.LENGTH_LONG).show();
                    pbar.setVisibility(View.INVISIBLE);
                }
            });
        }
    }
}
