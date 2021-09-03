//package com.example.payrollthem.ui.Employees;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;
//
//import com.example.payrollthem.R;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.EventListener;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.FirebaseFirestoreException;
//
//public class jbjbjb extends AppCompatActivity {
//    private ImageView backbtn;
//    private TextView Age;
//    private TextView EmpName;
//    private TextView Email;
//    private TextView Gender;
//    private TextView fund;
//    private TextView Loan;
//    private TextView jobTitle;
//    private TextView Department;
//    private TextView Phone;
//    private TextView accountNO;
//    private TextView employeeID;
//    private TextView Addresses;
//    private FirebaseFirestore db;
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_employee);
//        backbtn=findViewById(R.id.imageView);
//        EmpName=findViewById(R.id.nnname);
//        Email=findViewById(R.id.corpggts);
//        Gender=findViewById(R.id.Genderrrr);
//        fund=findViewById(R.id.providentfund);
//        Loan=findViewById(R.id.employeeloan);
//        jobTitle=findViewById(R.id.titleofjob);
//        Department=findViewById(R.id.deptofemo);
//        Phone=findViewById(R.id.cont);
//        accountNO=findViewById(R.id.accountbank);
//        employeeID=findViewById(R.id.empidwork);
//        Addresses=findViewById(R.id.addressss);
//        Age=findViewById(R.id.admttts);
////        String id;
////        Intent intent=getIntent();
////        id=intent.getStringExtra("id");
////
////        DocumentReference documentReference=db.collection("Employee").document(id);
////        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
////            @Override
////            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
////                EmpName.setText(value.getString("name"));
////                Age.setText(value.getString("age"));
////                Email.setText(value.getString("email"));
////                Gender.setText(value.getString("gender"));
////                fund.setText(value.getString("fund"));
////                Loan.setText(value.getString("loan"));
////                jobTitle.setText(value.getString("JobTitle"));
////                Department.setText(value.getString("Department"));
////                Phone.setText(value.getString("Phoneno"));
////                accountNO.setText(value.getString("accountno"));
////                employeeID.setText(value.getString("employeesId"));
////                Addresses.setText(value.getString("address"));
////            }
////        });
//        backbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//    }
//
//}
