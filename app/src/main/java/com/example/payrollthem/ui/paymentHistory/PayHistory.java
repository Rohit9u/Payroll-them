package com.example.payrollthem.ui.paymentHistory;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.payrollthem.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import Recycler.payHistoryList;
import model.nameNid;

public class PayHistory extends Fragment {
    private EditText year;
    private Spinner month;
    private Button getList;
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
        View v=inflater.inflate(R.layout.pay_history_fragment, container, false);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        year=v.findViewById(R.id.year);
        month=v.findViewById(R.id.month);
        getList=v.findViewById(R.id.getlistBtn);
        userid=user.getUid();
        currentUserId=userid.substring(0,7);
        list=v.findViewById(R.id.listview);
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
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                nameNid xyz=(nameNid)adapterView.getItemAtPosition(i);
                employeeId=xyz.getEmployeeId();
                employeeIntoFrag(employeeId,getYear,selectedMonth);

            }
        });
        getList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllList();
            }
        });
        return v;
    }

    private void employeeIntoFrag(String employeeId, String getYear, String selectedMonth) {
        paySlip_history slip_history=new paySlip_history();
        Bundle args=new Bundle();
        args.putString("key1",employeeId);
        args.putString("key2",getYear);
        args.putString("key3",selectedMonth);
        slip_history.setArguments(args);
        FragmentManager fm=getActivity().getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.payhistory,slip_history);
        ft.commit();
    }

    private void getAllList() {
        getYear=year.getText().toString().trim();
        if(!TextUtils.isEmpty(selectedMonth) && !TextUtils.isEmpty(getYear)){
            CollectionReference collectionReference=db.collection("Users").document(currentUserId).collection("Payment Parameter").document(getYear).collection(selectedMonth);
            collectionReference.whereEqualTo("year",getYear).whereEqualTo("month",selectedMonth).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if(!queryDocumentSnapshots.isEmpty()){
                        for (QueryDocumentSnapshot snapshot:queryDocumentSnapshots){
                            nameNid instance=new nameNid();
                            instance.setEmployeeId(snapshot.getString("employeeId"));
                            instance.setEmployeeName(snapshot.getString("employeeName"));
                            arrayList.add(instance);
                        }
                        payList=new payHistoryList(getActivity(),arrayList);
                        list.setAdapter(payList);
                    }
                    else{
                        Toast.makeText(getActivity(),"No data found",Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }else{
            Toast.makeText(getActivity(),"Credentials empty",Toast.LENGTH_SHORT).show();
        }
    }
}