package com.example.payrollthem.ui.Department;

import Recycler.JobTItleRecyclerView;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.payrollthem.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.ListJobTitle;

public class job_title extends Fragment {
    private ImageView addJob;
    private AlertDialog.Builder builder;
    private String currenentdeptname;
    private String currentDepartmentid;
    private String currentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    AlertDialog dialog;
    private FirebaseAuth firebaseAuth;
    JobTItleRecyclerView jobTItleRecyclerView;
    private EditText jobtitle;
    private EditText jobtitleSalary;
    private EditText jobtitleid;
    List<ListJobTitle> listJobTitles;
    ProgressBar progressBar;
    private Button saveButton;
    RecyclerView titleRecyclerview;
    private FirebaseUser user;
    private String userid;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_title, container, false);
        addJob = (ImageView) view.findViewById(R.id.popupopen);
        FirebaseAuth instance = FirebaseAuth.getInstance();
        firebaseAuth = instance;
        FirebaseUser currentUser2 = instance.getCurrentUser();
        user = currentUser2;
        String uid = currentUser2.getUid();
        currentUser = uid;
        userid = uid.substring(0, 7);
        currentDepartmentid = getArguments().getString("Department id");
        currenentdeptname = getArguments().getString("Department Name");
        listJobTitles = new ArrayList();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_jobtitle);
        titleRecyclerview = recyclerView;
        recyclerView.setHasFixedSize(true);
        titleRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        addJob.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                job_title.this.jobpopupOpen();
            }
        });
        return view;
    }

    public void jobpopupOpen() {
        builder = new AlertDialog.Builder(getView().getContext());
        View v1 = getLayoutInflater().inflate(R.layout.job_title_popup, (ViewGroup) null);
        progressBar = (ProgressBar) v1.findViewById(R.id.progressjob);
        jobtitle = (EditText) v1.findViewById(R.id.job_titlename);
        jobtitleid = (EditText) v1.findViewById(R.id.jobid);
        jobtitleSalary = (EditText) v1.findViewById(R.id.set_amount);
        Button button = (Button) v1.findViewById(R.id.saveJobTitle);
        saveButton = button;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                saveinfo();
            }
        });
        builder.setView(v1);
        AlertDialog create = this.builder.create();
        dialog = create;
        create.show();
    }

    public void saveinfo() {
        progressBar.setVisibility(View.VISIBLE);
        String retTitle = jobtitle.getText().toString().trim();
        String retid = jobtitleid.getText().toString().trim();
        String Salary = jobtitleSalary.getText().toString().trim();
        if (!TextUtils.isEmpty(retTitle) && !TextUtils.isEmpty(retid) && !TextUtils.isEmpty(Salary)) {
            ListJobTitle listJobTitle = new ListJobTitle();
            listJobTitle.setDepartment_Id(currentDepartmentid);
            listJobTitle.setDepartment_Name(currenentdeptname);
            listJobTitle.setJob_Id(retid);
            listJobTitle.setJob_Salary(Salary);
            listJobTitle.setJob_Title(retTitle);
            listJobTitle.setUser_id(userid);
            db.collection("Users").document(this.userid).collection("Department").document(this.currentDepartmentid).collection("Job Title").document(retid).set(listJobTitle).addOnSuccessListener(new OnSuccessListener<Void>() {
                public void onSuccess(Void aVoid) {
                    progressBar.setVisibility(View.INVISIBLE);
                    dialog.dismiss();
                    Toast.makeText(job_title.this.getActivity(), "Added successfully", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(Exception e) {
                    Toast.makeText(job_title.this.getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            db.collection("Users").document(this.userid).collection("Department").document(this.currentDepartmentid).collection("Job Title").whereEqualTo("user_id", (Object) this.userid).whereEqualTo("department_Id", (Object) this.currentDepartmentid).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    job_title.this.listJobTitles.clear();
                    if (!queryDocumentSnapshots.isEmpty()) {
                        Iterator<QueryDocumentSnapshot> it = queryDocumentSnapshots.iterator();
                        while (it.hasNext()) {
                            listJobTitles.add(it.next().toObject(ListJobTitle.class));
                        }
                        jobTItleRecyclerView = new JobTItleRecyclerView(job_title.this.getActivity(), job_title.this.listJobTitles);
                        titleRecyclerview.setAdapter(job_title.this.jobTItleRecyclerView);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(Exception e) {
                    Toast.makeText(job_title.this.getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void onStart() {
        super.onStart();
        db.collection("Users").document(userid).collection("Department").document(currentDepartmentid).collection("Job Title").whereEqualTo("user_id", (Object) this.userid).whereEqualTo("department_Id", (Object) this.currentDepartmentid).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    Iterator<QueryDocumentSnapshot> it = queryDocumentSnapshots.iterator();
                    while (it.hasNext()) {
                        job_title.this.listJobTitles.add(it.next().toObject(ListJobTitle.class));
                    }
                    jobTItleRecyclerView = new JobTItleRecyclerView(getActivity(), job_title.this.listJobTitles);
                    titleRecyclerview.setAdapter(job_title.this.jobTItleRecyclerView);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(Exception e) {
                Toast.makeText(job_title.this.getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
