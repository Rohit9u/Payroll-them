package com.example.payrollthem.ui.paymentHistory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.payrollthem.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class paySlip_history extends Fragment {
private TextView name;
private TextView id;
private TextView year;
private TextView month;
private TextView absent;
private TextView otherBonus;
private TextView seasonalBonus;
private TextView pfcut;
private TextView texation;
private TextView totaldeduction;
private TextView date;
private CircleImageView dp;
private ImageView back;
private TextView salaryPaid;
private TextView otherDeduction;
private FirebaseFirestore db=FirebaseFirestore.getInstance();
private FirebaseAuth auth;
private FirebaseUser user;
private String userid;
private String empid;
private String yeartext;
private String monthxt;
private String currentUserid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_pay_slip_history, container, false);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        currentUserid=user.getUid();
        userid=currentUserid.substring(0,7);
        empid=getArguments().getString("key1");
        yeartext=getArguments().getString("key2");
        monthxt=getArguments().getString("key3");
        name=v.findViewById(R.id.nnname);
        id=v.findViewById(R.id.empidworka);
        year=v.findViewById(R.id.yearE);
        month=v.findViewById(R.id.montheE);
        absent=v.findViewById(R.id.absent);
        otherBonus=v.findViewById(R.id.otherE);
        seasonalBonus=v.findViewById(R.id.seasonalE);
        pfcut=v.findViewById(R.id.pfcytE);
        texation=v.findViewById(R.id.taxcut);
        totaldeduction=v.findViewById(R.id.tDeductE);
        date=v.findViewById(R.id.last_paymentsz);
        dp=v.findViewById(R.id.dpcx);
        back=v.findViewById(R.id.imageViewsz);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PayHistory ph=new PayHistory();
//                Bundle args=new Bundle();
//                args.putString("key5",yeartext);
//                ph.setArguments(args);
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.replace(R.id.history,ph);
                ft.commit();
            }
        });
        salaryPaid=v.findViewById(R.id.paidSalaryE);
        otherDeduction=v.findViewById(R.id.oCutE);
        DocumentReference documentReference =db.collection("Users").document(userid).collection("Payment Parameter").
                document(yeartext).collection(monthxt).document(empid);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
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
                date.setText("Paid on: "+docu.getString("datePaid"));
                salaryPaid.setText(docu.getString("salaryPaid"));
                otherDeduction.setText(docu.getString("otherDeduction"));
                Picasso.get().load(docu.getString("image_Url")).placeholder(R.drawable.dpc).fit().into(dp);
            }
        });
        return v;
    }

}