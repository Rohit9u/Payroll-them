package com.example.payrollthem;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.Iterator;

public class profileEmployee extends AppCompatActivity {
    public TextView Addresses;
    public TextView Age;
    public TextView Department;
    public TextView Email;
    public TextView EmpName;
    public TextView Gender;
    public TextView JoiningDate;
    public TextView Loan;
    public TextView Phone;
    public TextView Salary;
    public TextView accountNO;
    private String corporateid;
    public CircleImageView currentdp;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public TextView employeeID;
    private String employeeids;
    public TextView fund;
    public TextView jobTitle;
    public TextView pfund;
    public TextView prevpay;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_employee);
        this.currentdp = findViewById(R.id.dpcxx);
        this.Age =findViewById(R.id.admtttspro);
        this.EmpName =findViewById(R.id.nnnamepro);
        this.Email = findViewById(R.id.corpggtsPro);
        this.Gender = findViewById(R.id.GenderrrrPro);
        this.fund = findViewById(R.id.providentfundPro);
        this.Loan = findViewById(R.id.employeeloanPro);
        this.jobTitle =findViewById(R.id.titleofjobPro);
        this.Department =findViewById(R.id.deptofemoPro);
        this.Phone =findViewById(R.id.contPro);
        this.accountNO =findViewById(R.id.accountbankPro);
        this.employeeID =findViewById(R.id.empidworkpro);
        this.Addresses = findViewById(R.id.addressssPro);
        this.Salary =findViewById(R.id.salarysetPro);
        this.pfund = findViewById(R.id.pfsetPro);
        this.prevpay =findViewById(R.id.last_paymentpro);
        this.JoiningDate =findViewById(R.id.joinindatePro);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.corporateid = bundle.getString("key1");
            this.employeeids = bundle.getString("key2");
        }
        this.db.collection("Users").document(this.corporateid).collection("Employee").whereEqualTo("Employee Id", (Object) this.employeeids).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    Iterator<QueryDocumentSnapshot> it = queryDocumentSnapshots.iterator();
                    while (it.hasNext()) {
                        QueryDocumentSnapshot value = it.next();
                        profileEmployee.this.EmpName.setText(value.getString("Employee Name"));
                        profileEmployee.this.Age.setText(value.getString("Date of Birth"));
                        profileEmployee.this.Email.setText(value.getString("Email id"));
                        profileEmployee.this.Gender.setText(value.getString("Gender"));
                        profileEmployee.this.fund.setText(value.getString("Provident Fund"));
                        profileEmployee.this.Loan.setText(value.getString("Loan"));
                        profileEmployee.this.pfund.setText(value.getString("PF Rate"));
                        profileEmployee.this.jobTitle.setText(value.getString("Job Title"));
                        profileEmployee.this.Department.setText(value.getString("Department"));
                        profileEmployee.this.Phone.setText(value.getString("Contact No"));
                        profileEmployee.this.accountNO.setText(value.getString("Account no"));
                        profileEmployee.this.employeeID.setText(value.getString("Employee Id"));
                        profileEmployee.this.Addresses.setText(value.getString("Address"));
                        profileEmployee.this.Salary.setText(value.getString("Salary"));
                        profileEmployee.this.JoiningDate.setText(value.getString("Joining Date"));
                        profileEmployee.this.prevpay.setText("Last Payment: " + value.getString("Last Payment"));
                        Picasso.get().load(value.getString("image_url")).placeholder((int) R.drawable.dpc).fit().into((ImageView) profileEmployee.this.currentdp);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(Exception e) {
                Toast.makeText(profileEmployee.this, "hght " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
