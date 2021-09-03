package com.example.payrollthem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;

public class payslipEmployee extends AppCompatActivity {
    TextView absent;
    FirebaseAuth auth;
    ImageView back;
    String currentUserid;
    TextView date;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CircleImageView dp;
    String empid;
    TextView id;
    TextView month;
    String monthxt;
    TextView name;
    TextView otherBonus;
    TextView otherDeduction;
    TextView pfcut;
    TextView salaryPaid;
    TextView seasonalBonus;
    TextView texation;
    TextView totaldeduction;
    FirebaseUser user;
    String userid;
    TextView year;
    private String yeartext;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_payslip_employee);
        Bundle bundle = getIntent().getExtras();
        FirebaseAuth instance = FirebaseAuth.getInstance();
        auth = instance;
        user = instance.getCurrentUser();
        if (bundle != null) {
            userid = bundle.getString("key1");
            empid = bundle.getString("key2");
            yeartext = bundle.getString("year");
            monthxt = bundle.getString("month");
        }
        name = findViewById(R.id.nnnamer);
        id = findViewById(R.id.empidworkar);
        year = findViewById(R.id.yearEr);
        month =findViewById(R.id.montheEr);
        absent = findViewById(R.id.absentr);
        otherBonus =findViewById(R.id.otherEr);
        seasonalBonus = findViewById(R.id.seasonalEr);
        pfcut =findViewById(R.id.pfcytEr);
        texation = findViewById(R.id.taxcutr);
        totaldeduction = findViewById(R.id.tDeductEr);
        date = findViewById(R.id.last_paymentszr);
        dp =  findViewById(R.id.dpcxr);
        ImageView imageView = findViewById(R.id.imageViewszr);
        back = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(payslipEmployee.this, employeeOption.class));
            }
        });
        salaryPaid = (TextView) findViewById(R.id.paidSalaryEr);
        otherDeduction = (TextView) findViewById(R.id.oCutEr);
        db.collection("Users").document(userid).collection("Payment Parameter").document(yeartext).collection(monthxt).document(this.empid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            public void onSuccess(DocumentSnapshot docu) {
                name.setText(docu.getString("employeeName"));
                id.setText(docu.getString("employeeId"));
                year.setText(docu.getString("year"));
                month.setText(docu.getString("month"));
                absent.setText(docu.getString("absent"));
                otherBonus.setText(docu.getString("otherBonus"));
                seasonalBonus.setText(docu.getString("seasonalBonus"));
                pfcut.setText(docu.getString("pfCut"));
                texation.setText(docu.getString("taxDeduction"));
                totaldeduction.setText(docu.getString("totalDeduction"));
                date.setText("Paid on: " + docu.getString("datePaid"));
                salaryPaid.setText(docu.getString("salaryPaid"));
                otherDeduction.setText(docu.getString("otherDeduction"));
                Picasso.get().load(docu.getString("image_Url")).placeholder(R.drawable.dpc).fit().into((ImageView) payslipEmployee.this.dp);
            }
        });
    }
}
