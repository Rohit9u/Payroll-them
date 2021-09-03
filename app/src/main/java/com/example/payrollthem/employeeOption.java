package com.example.payrollthem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class employeeOption extends AppCompatActivity {
    private ImageView profile;
    private ImageView paySlip;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private Toolbar toolbar;
    private String employid;
    private String corporateid;
    private String userid;
    private EditText year;
    private Spinner month;
    private Button get;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;
    private ArrayAdapter<String> adapter;
    private String selectedMonth;
    private String[] allMonth={"January","February","March","April","May","June","July","August","September","October","November","December"};
    private FirebaseFirestore ff=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_option);
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        userid=user.getUid();
        DocumentReference documentReference=ff.collection("Employee").document(userid);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    employid=documentSnapshot.getString("Employee Id");
                    corporateid=documentSnapshot.getString("Corporate Id");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        profile=findViewById(R.id.profile);
        paySlip=findViewById(R.id.payslipimg);
        paySlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogue();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(employeeOption.this,profileEmployee.class);
                intent.putExtra("key1",corporateid);
                intent.putExtra("key2",employid);
                startActivity(intent);
            }
        });

    }

    private void openDialogue() {
        builder=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.popupforslip,null);
        year=view.findViewById(R.id.yearselects);
        month=view.findViewById(R.id.months);
        get=view.findViewById(R.id.getSlip);
        adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,allMonth);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month.setAdapter(adapter);
        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedMonth=allMonth[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String yearx=year.getText().toString().trim();
                Intent intent=new Intent(employeeOption.this,payslipEmployee.class);
                intent.putExtra("year",yearx);
                intent.putExtra("month",selectedMonth);
                intent.putExtra("key1",corporateid);
                intent.putExtra("key2",employid);
                startActivity(intent);
            }
        });
        builder.setView(view);
        alertDialog=builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.employee,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                signout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signout() {
        auth.signOut();
        user=auth.getCurrentUser();
        if(user==null){
            Toast.makeText(getApplicationContext(),"logout successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(employeeOption.this,Employee_login.class));
            finish();
        }
    }


}