package com.example.payrollthem;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {
    FirebaseAuth auth;
    EditText email;
    ProgressBar pbar;
    Button send;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        this.email = findViewById(R.id.emailidreset);
        this.send = findViewById(R.id.sendbtn);
        this.auth = FirebaseAuth.getInstance();
        this.pbar = findViewById(R.id.pbarreset);
        this.send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ResetPassword.this.pbar.setVisibility(View.VISIBLE);
                String emailID = ResetPassword.this.email.getText().toString().trim();
                if (!TextUtils.isEmpty(emailID)) {
                    ResetPassword.this.auth.sendPasswordResetEmail(emailID).addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(Task<Void> task) {
                            Toast.makeText(ResetPassword.this, "Link Sent",Toast.LENGTH_SHORT).show();
                            pbar.setVisibility(View.INVISIBLE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        public void onFailure(Exception e) {
                            Toast.makeText(ResetPassword.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            pbar.setVisibility(View.INVISIBLE);
                        }
                    });
                } else {
                    Toast.makeText(ResetPassword.this, "empty credentials", Toast.LENGTH_LONG).show();
                }
                pbar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
