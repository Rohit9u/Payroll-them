package com.example.payrollthem;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class Employer_Registration extends AppCompatActivity {
    private Button AlreadyLogin;
    private Button Signup;
    private FirebaseAuth.AuthStateListener authStateListener;
    public EditText confirmPassword;
    public FirebaseUser currentUser;
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public String documentid;
    public EditText email;
    public FirebaseAuth firebaseAuth;
    private LinearLayout layout;
    public EditText password;
    String passwordCfn;
    private ProgressBar progressBar;
    private String randomstring;
    public EditText user;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer__registration);
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.AlreadyLogin = findViewById(R.id.already_login);
        this.Signup =  findViewById(R.id.createacc);
        this.email = findViewById(R.id.emailid);
        this.password =findViewById(R.id.passwordregister);
        this.confirmPassword =  findViewById(R.id.passwordregisterCp);
        this.layout =  findViewById(R.id.linearLayout);
        this.user =  findViewById(R.id.username);
        this.progressBar =  findViewById(R.id.progressBar);
        this.authStateListener = new FirebaseAuth.AuthStateListener() {
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                FirebaseUser unused = Employer_Registration.this.currentUser = firebaseAuth.getCurrentUser();
                FirebaseUser unused2 = Employer_Registration.this.currentUser;
            }
        };
        this.AlreadyLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Employer_Registration.this.startActivity(new Intent(Employer_Registration.this, Employer_login.class));
            }
        });
        this.Signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (TextUtils.isEmpty(Employer_Registration.this.email.getText().toString()) || TextUtils.isEmpty(Employer_Registration.this.password.getText().toString()) || TextUtils.isEmpty(Employer_Registration.this.user.getText().toString())) {
                    Toast.makeText(Employer_Registration.this, "empty credentials not allowed", Toast.LENGTH_LONG).show();
                    return;
                }
                String emailrec = Employer_Registration.this.email.getText().toString().trim();
                String passwordrec = Employer_Registration.this.password.getText().toString().trim();
                String userrec = Employer_Registration.this.user.getText().toString().trim();
                Employer_Registration employer_Registration = Employer_Registration.this;
                employer_Registration.passwordCfn = employer_Registration.confirmPassword.getText().toString().trim();
                Employer_Registration.this.registerNewuser(emailrec, passwordrec, userrec);
            }
        });
    }

    /* access modifiers changed from: private */
    public void registerNewuser(final String emailrec, String passwordrec, final String userrec) {
        if (TextUtils.isEmpty(emailrec) || TextUtils.isEmpty(passwordrec) || TextUtils.isEmpty(userrec) || TextUtils.isEmpty(this.passwordCfn)) {
            Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_LONG).show();
            this.progressBar.setVisibility(View.INVISIBLE);
        } else if (passwordrec.equals(this.passwordCfn)) {
            this.progressBar.setVisibility(View.VISIBLE);
            this.firebaseAuth.createUserWithEmailAndPassword(emailrec, passwordrec).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                static final /* synthetic */ boolean $assertionsDisabled = false;

                public void onComplete(Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Employer_Registration employer_Registration = Employer_Registration.this;
                        FirebaseUser unused = employer_Registration.currentUser = employer_Registration.firebaseAuth.getCurrentUser();
                        if (Employer_Registration.this.currentUser != null) {
                            String unused2 = Employer_Registration.this.documentid = Employer_Registration.this.currentUser.getUid().substring(0, 7);
                            Map<String, String> userObj = new HashMap<>();
                            userObj.put("Corporate name", userrec);
                            userObj.put("Email", emailrec);
                            userObj.put("Company Unique Id", Employer_Registration.this.documentid);
                            Employer_Registration.this.db.collection("Users").document(Employer_Registration.this.documentid).set(userObj).addOnSuccessListener(new OnSuccessListener<Void>() {
                                public void onSuccess(Void aVoid) {
                                    Employer_Registration.this.startActivity(new Intent(Employer_Registration.this, NavdrawerActivity.class));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                public void onFailure(Exception e) {
                                    Toast.makeText(Employer_Registration.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                            return;
                        }
                        throw new AssertionError();
                    }
                }
            });
        } else {
            this.layout.setVisibility(View.VISIBLE);
            this.progressBar.setVisibility(View.VISIBLE);
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        this.currentUser = this.firebaseAuth.getCurrentUser();
        this.firebaseAuth.addAuthStateListener(this.authStateListener);
    }
}
