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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class EmployeeREg extends AppCompatActivity {
    public FirebaseAuth auth;
    public String corpid;
    public EditText corporateid;
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public String email;
    public EditText emailid;
    public String employId;
    public EditText employeeid;
    public String fsemail;
    public String fsemid;
    LinearLayout layout;
    public String passwd;
    public EditText password;
    public EditText passwordConfirm;
    public ProgressBar pbar;
    String recheckpswd;
    private Button register;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_r_eg);
        corporateid = findViewById(R.id.CorporateIdreg);
        employeeid = findViewById(R.id.employeeIDreg);
        emailid = findViewById(R.id.emailidempReg);
        layout = findViewById(R.id.linearLayouttwo);
        password = findViewById(R.id.passwordempReg);
        auth = FirebaseAuth.getInstance();
        register = findViewById(R.id.regid);
        passwordConfirm =  findViewById(R.id.passwordempRegcheck);
        pbar = findViewById(R.id.pbarsignup);
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String unused = corporateid.getText().toString().trim();
                String unused2 =employeeid.getText().toString().trim();
                String unused3 =emailid.getText().toString().trim();
                String unused4 =password.getText().toString().trim();
                passwordConfirm.getText().toString().trim();
                pbar.setVisibility(View.INVISIBLE);
                SignupEmployee(corpid, employId, email, passwd);
            }
        });
    }

    /* access modifiers changed from: private */
    public void SignupEmployee(String corpid2, String employId2, String email2, String passwd2) {
        if (!TextUtils.isEmpty(corpid2) && !TextUtils.isEmpty(employId2) && !TextUtils.isEmpty(email2) && !TextUtils.isEmpty(passwd2) && !TextUtils.isEmpty(this.recheckpswd)) {
            this.layout.setVisibility(View.INVISIBLE);
            if (this.recheckpswd.equals(passwd2)) {
                final String str = email2;
                final String str2 = employId2;
                final String str3 = passwd2;
                final String str4 = corpid2;
                this.db.collection("Users").document(corpid2).collection("Employee").document(employId2).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String unused = EmployeeREg.this.fsemid = documentSnapshot.getString("Employee Id");
                            String unused2 = EmployeeREg.this.fsemail = documentSnapshot.getString("Email id");
                            if (!str.equals(EmployeeREg.this.fsemail) || !str2.equals(EmployeeREg.this.fsemid)) {
                                Toast.makeText(EmployeeREg.this, "Data not found", Toast.LENGTH_LONG).show();
                            } else {
                                EmployeeREg.this.auth.createUserWithEmailAndPassword(str, str3).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    public void onSuccess(AuthResult authResult) {
                                        DocumentReference documentReference1 = EmployeeREg.this.db.collection("Employee").document(EmployeeREg.this.auth.getCurrentUser().getUid());
                                        Map<String, String> map = new HashMap<>();
                                        map.put("Employee Id", str2);
                                        map.put("Corporate Id", str4);
                                        map.put("Email Id", str);
                                        documentReference1.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            public void onSuccess(Void aVoid) {
                                                EmployeeREg.this.pbar.setVisibility(View.VISIBLE);
                                                EmployeeREg.this.startActivity(new Intent(EmployeeREg.this, employeeOption.class));
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            public void onFailure(Exception e) {
                                                Toast.makeText(EmployeeREg.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                });
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    public void onFailure(Exception e) {
                        Toast.makeText(EmployeeREg.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                return;
            }
            this.layout.setVisibility(View.VISIBLE);
        }
    }
}
