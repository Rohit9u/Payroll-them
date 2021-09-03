package com.example.payrollthem.ui.Payslip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.payrollthem.R;
import com.example.payrollthem.ui.paymentHistory.paySlip_history;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import Recycler.payHistoryList;
import model.nameNid;

public class paySlip extends Fragment {
    private EditText year;
    private Spinner month;
    private EditText empid;
    private Button getSlip;
    private ListView list;
    private ArrayList<nameNid> arrayList;
    private ArrayAdapter<String> arrayAdapter;
    private String selectedMonth;
    private String getYear;
    private String currentUserId;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private FirebaseAuth auth;
    private payHistoryList payList;
    private FirebaseUser user;
    private String employeeId;
    private String yearargs;
    private String userid;

    private String[] allMonth={"January","February","March","April","May","June","July","August","September","October","November","December"};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       View v=inflater.inflate(R.layout.pay_slip_fragment, container, false);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        empid=v.findViewById(R.id.employeidrr);
        year=v.findViewById(R.id.yearrr);
        month=v.findViewById(R.id.monthrr);
        userid=user.getUid();
        getSlip=v.findViewById(R.id.getslipBtn);
        currentUserId=userid.substring(0,7);
        arrayList=new ArrayList<>();
        arrayAdapter=new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,allMonth);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month.setAdapter(arrayAdapter);
        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedMonth=allMonth[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getSlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                employeeId=empid.getText().toString().trim();
                getYear=year.getText().toString().trim();
                employeeIntofrag(employeeId,getYear,selectedMonth);
            }
        });
        return v;
    }

    private void employeeIntofrag(String employeeId, String getYear, String selectedMonth) {
        paySlip_history slip_history=new paySlip_history();
        Bundle args=new Bundle();
        args.putString("key1",employeeId);
        args.putString("key2",getYear);
        args.putString("key3",selectedMonth);
        slip_history.setArguments(args);
        FragmentManager fm=getActivity().getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.payslipfrag,slip_history);
        ft.commit();
    }


}