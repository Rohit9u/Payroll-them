package com.example.payrollthem.ui.Department;

import Recycler.DepartmentRecyclerView;
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
import android.widget.TextView;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import model.ListDepartment;

public class Department extends Fragment {
    private EditText DepartmentId;
    private EditText DepartmentName;
    private ImageView addPopup;
    private FirebaseAuth authfb;
    private AlertDialog.Builder builder;
    private String curruserid;
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public DepartmentRecyclerView departmentRecyclerView;
    public AlertDialog dialog;
    private FirebaseUser firebuser;
    public List<ListDepartment> lists;
    public TextView nolist;
    public ProgressBar pbar;
    public RecyclerView recyclerView;
    private Button saveButton;
    public String userid;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.department_fragment, container, false);
        FirebaseAuth instance = FirebaseAuth.getInstance();
        authfb = instance;
        FirebaseUser currentUser = instance.getCurrentUser();
        firebuser = currentUser;
        String uid = currentUser.getUid();
        curruserid = uid;
        userid = uid.substring(0, 7);
        lists = new ArrayList();
        //this.nolist = (TextView) view.findViewById(R.id.nolist);
        RecyclerView recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView = recyclerView2;
        recyclerView2.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ImageView imageView = (ImageView) view.findViewById(R.id.depAdd);
        addPopup = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                popupAdd();
            }
        });
        return view;
    }

    /* access modifiers changed from: private */
    public void popupAdd() {
        builder = new AlertDialog.Builder(getView().getContext());
        View views = getLayoutInflater().inflate(R.layout.departmentpopup, (ViewGroup) null);
        DepartmentName = (EditText) views.findViewById(R.id.department_name);
        DepartmentId = (EditText) views.findViewById(R.id.department_id);
        pbar =views.findViewById(R.id.departmentProgress);
        Button button =views.findViewById(R.id.save_depart);
        saveButton = button;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                addDeparatments();
            }
        });
        builder.setView(views);
        AlertDialog create =builder.create();
        dialog = create;
        create.show();
    }

    public void addDeparatments() {
        pbar.setVisibility(View.INVISIBLE);
        String nameDepartment = DepartmentName.getText().toString().trim();
        String idDepartment = DepartmentId.getText().toString().trim();
        if (!TextUtils.isEmpty(nameDepartment) && !TextUtils.isEmpty(idDepartment)) {
            Map<String, String> deptobj = new HashMap<>();
            deptobj.put("Department Name", nameDepartment);
            deptobj.put("Department Id", idDepartment);
            deptobj.put("UserId", this.userid);
            ListDepartment ld = new ListDepartment();
            ld.setDepartmentName(nameDepartment);
            ld.setDepartmentId(idDepartment);
            ld.setUserid(this.userid);
            db.collection("Users").document(this.userid).collection("Department").document(idDepartment).set(ld).addOnSuccessListener(new OnSuccessListener<Void>() {
                public void onSuccess(Void aVoid) {
                    Department.this.pbar.setVisibility(View.INVISIBLE);
                    Department.this.dialog.dismiss();
                    Toast.makeText(Department.this.getActivity(), "Department added", Toast.LENGTH_LONG).show();
                    db.collection("Users").document(Department.this.userid).collection("Department").whereEqualTo("userid", (Object) Department.this.userid).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                lists.clear();
                                Iterator<QueryDocumentSnapshot> it = queryDocumentSnapshots.iterator();
                                while (it.hasNext()) {
                                    lists.add(it.next().toObject(ListDepartment.class));
                                }
                                departmentRecyclerView = new DepartmentRecyclerView(getActivity(),lists);
                                recyclerView.setAdapter(departmentRecyclerView);
                                return;
                            }
                            Department.this.nolist.setVisibility(View.VISIBLE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        public void onFailure(Exception e) {
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(Exception e) {
                    Toast.makeText(Department.this.getActivity(), "Unable to add at the moment, Try Later", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void onStart() {
        super.onStart();
        this.db.collection("Users").document(this.userid).collection("Department").whereEqualTo("userid", (Object) this.userid).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    Iterator<QueryDocumentSnapshot> it = queryDocumentSnapshots.iterator();
                    while (it.hasNext()) {
                        lists.add( it.next().toObject(ListDepartment.class));
                    }
                    departmentRecyclerView = new DepartmentRecyclerView(getActivity(),lists);
                    recyclerView.setAdapter(departmentRecyclerView);
                    return;
                }
                nolist.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(Exception e) {
            }
        });
    }
}
