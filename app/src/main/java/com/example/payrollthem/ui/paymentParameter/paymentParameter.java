package com.example.payrollthem.ui.paymentParameter;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.health.TimerStat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.payrollthem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import model.payParameter;

public class paymentParameter extends Fragment {
    private EditText year;
    private EditText Absence;
    private EditText overTime;
    private EditText SeasonalBonus;
    private EditText otherBonus;
    private EditText employeeId;
    private TextView update;
    private Spinner month;
    private String currentMonth;
    private ArrayAdapter<String> arrayAdapter;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private String currentuserId;
    private EditText deduction;
    private ProgressBar updateprogress;
    private double fund;
    private String sal;
    private String name;
    private String imageUrl;
    private String deductedAmount;
    private String taxDeduct;
    private int taxCut;
    private String totalDeduct;
    private int totDeduct;
    private String time;
    private int deducteAmount;
    private double fundint;
    private String userid;
    private String[] monthname={"January","February","March","April","May","June","July","August","September","October","November","December"};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.payment_parameter_fragment, container, false);
        auth=FirebaseAuth.getInstance();
        currentUser=auth.getCurrentUser();
        userid=currentUser.getUid();
        currentuserId=userid.substring(0,7);
        year=v.findViewById(R.id.empyearupdate);
        Absence=v.findViewById(R.id.empabsent);
        overTime=v.findViewById(R.id.overtime);
        deduction=v.findViewById(R.id.otherDeduction);
        SeasonalBonus=v.findViewById(R.id.seasonalBonus);
        otherBonus=v.findViewById(R.id.otherBonus);
        updateprogress=v.findViewById(R.id.progressbar5);
        employeeId=v.findViewById(R.id.empidupdate);
        update=v.findViewById(R.id.update);
        month=v.findViewById(R.id.empMonthupdate);
        arrayAdapter=new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,monthname);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month.setAdapter(arrayAdapter);
        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentMonth=monthname[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatepayment();
            }
        });
        return v;
    }

    private void updatepayment() {
        String year1=year.getText().toString().trim();
        final String empid1=employeeId.getText().toString().trim();
        String OverTime=overTime.getText().toString().trim();
        final String bonusseasonal=SeasonalBonus.getText().toString().trim();
        final String otherbonus=otherBonus.getText().toString().trim();
        String absent1=Absence.getText().toString().trim();
        final String deductionAbsent=deduction.getText().toString().trim();
        updateprogress.setVisibility(View.VISIBLE);
        if(!TextUtils.isEmpty(year1) &&
        !TextUtils.isEmpty(empid1) &&
        !TextUtils.isEmpty(OverTime) &&
        !TextUtils.isEmpty(bonusseasonal) &&
        !TextUtils.isEmpty(otherbonus) &&
        !TextUtils.isEmpty(absent1) &&
        !TextUtils.isEmpty(deductionAbsent) &&
        !TextUtils.isEmpty(currentMonth)) {
            payParameter parameter = new payParameter();
            parameter.setEmployeeId(empid1);
            parameter.setYear(year1);
            parameter.setOverTime(OverTime);
            parameter.setSeasonalBonus(bonusseasonal);
            parameter.setMonth(currentMonth);
            parameter.setAbsent(absent1);
            parameter.setOtherBonus(otherbonus);
            parameter.setUserid(currentuserId);
            parameter.setOtherDeduction(deductionAbsent);
            final DocumentReference documentReference=db.collection("Users").document(currentuserId).collection("Payment Parameter").document(year1).collection(currentMonth).document(empid1);
            documentReference.set(parameter).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    final DocumentReference docRef=db.collection("Users").document(currentuserId).collection("Employee").document(empid1);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                final DocumentSnapshot document=task.getResult();
                                if(document.exists()){
                                    String Salary = document.getString("Salary");
                                    String pfundrate = document.getString("PF Rate");
                                    String fundPresent = document.getString("Provident Fund");
                                    name=document.getString("Employee Name");
                                    imageUrl=document.getString("image_url");
                                    fundint = Double.parseDouble(fundPresent);
                                    int retrivedSalary = Integer.parseInt(Salary);
                                    int annualSalary=(retrivedSalary*12);
                                    if(annualSalary>250000 && annualSalary<=500000) {
                                        taxCut=retrivedSalary*5/100;
                                    }
                                    else if(annualSalary>500000 && annualSalary<=1000000) {
                                        taxCut=retrivedSalary*20/100;
                                    }else{
                                        taxCut=retrivedSalary*30/100;
                                    }
                                    taxDeduct=Integer.toString(taxCut);
                                    int retrivedpfrate = Integer.parseInt(pfundrate);
                                    deducteAmount = retrivedSalary * retrivedpfrate / 100;
                                    int finalSalary = retrivedSalary - deducteAmount;
                                    int sesBonus = Integer.parseInt(bonusseasonal);
                                    int otbonus = Integer.parseInt(otherbonus);
                                    int abdeduct=Integer.parseInt(deductionAbsent);
                                    totDeduct=(deducteAmount+abdeduct+taxCut);
                                    totalDeduct=Integer.toString(totDeduct);
                                    int paidSalary = (finalSalary + sesBonus + otbonus-abdeduct-taxCut);
                                    sal = Integer.toString(paidSalary);
                                    deductedAmount=Integer.toString(deducteAmount);
                                    Date date=new Date();
                                    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
                                    time=sdf.format(date);
                                    Map<String,String> obj=new HashMap<>();
                                    obj.put("employeeName",name);
                                    obj.put("datePaid",time);
                                    obj.put("taxDeduction",taxDeduct);
                                    obj.put("salaryPaid",sal);
                                    obj.put("totalDeduction",totalDeduct);
                                    obj.put("pfCut",deductedAmount);
                                    obj.put("image_Url",imageUrl);
                                    documentReference.set(obj,SetOptions.merge());
                                    DocumentReference docrefx=db.collection("Users").document(currentuserId);
                                    docrefx.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if(task.isSuccessful()){
                                                DocumentSnapshot documentsnap=task.getResult();
                                                if(documentsnap.exists()){
                                                    String fundinterest=documentsnap.getString("EPF rate");
                                                    double fundintz=Double.parseDouble(fundinterest);
                                                    double currFund=deducteAmount*fundintz/100;
                                                    double totalcurrfund=(currFund+deducteAmount);
                                                    if(fundint==0){
                                                        fund=totalcurrfund;
                                                    }else {
                                                        fund=(totalcurrfund+fundint);
                                                    }
                                                    String fundP=Double.toString(fund);
                                                    Map<String,Object> objectMap=new HashMap<>();
                                                    objectMap.put("Last Payment",time);
                                                    objectMap.put("Provident Fund",fundP);
                                                    docRef.update(objectMap);
                                                }
                                            }
                                        }
                                    });
                                    updateprogress.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getActivity(),"updated",Toast.LENGTH_SHORT).show();
                                    year.getText().clear();
                                    Absence.getText().clear();
                                    overTime.getText().clear();
                                    SeasonalBonus.getText().clear();
                                    otherBonus.getText().clear();
                                    employeeId.getText().clear();
                                    deduction.getText().clear();
                                }
                            }
                        }
                    });

                }


            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(),"payment parameter update failed due to "+e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }

    }

}