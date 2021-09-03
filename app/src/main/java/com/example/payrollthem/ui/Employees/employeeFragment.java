package com.example.payrollthem.ui.Employees;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.payrollthem.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class employeeFragment extends Fragment {
    private static final int PERMISSION_FILE=23;
    private static final int ACCESS_FILE=20;
    private CircleImageView currentdp;
    private ImageView backbtn;
    private TextView Age;
    private TextView EmpName;
    private TextView Email;
    private TextView Gender;
    private TextView fund;
    private TextView Loan;
    private TextView jobTitle;
    private TextView Department;
    private TextView Phone;
    private TextView accountNO;
    private TextView employeeID;
    private TextView Addresses;
    private TextView Salary;
    private ImageView changedp;
    private TextView pfund;
    private TextView prevpay;
    private TextView JoiningDate;
    String key;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    Uri picUri;
    Uri croppedUri;
    private ImageView upload;
    private ProgressBar pbar3;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private String currentUserId;
    private String userid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_employee, container, false);
        backbtn=view.findViewById(R.id.imageView);
        EmpName=view.findViewById(R.id.nnname);
        storageReference= FirebaseStorage.getInstance().getReference();
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        currentUserId=user.getUid();
        userid=currentUserId.substring(0,7);
        Email=view.findViewById(R.id.corpggts);
        Gender=view.findViewById(R.id.Genderrrr);
        fund=view.findViewById(R.id.providentfund);
        Loan=view.findViewById(R.id.employeeloan);
        jobTitle=view.findViewById(R.id.titleofjob);
        pfund=view.findViewById(R.id.pfset);
        Department=view.findViewById(R.id.deptofemo);
        JoiningDate=view.findViewById(R.id.joinindate);
        Phone=view.findViewById(R.id.cont);
        prevpay=view.findViewById(R.id.last_payment);
        pbar3=view.findViewById(R.id.progressbar3);
        upload=view.findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadProfilepic();
            }
        });
        currentdp=view.findViewById(R.id.dpc);
        Salary=view.findViewById(R.id.salaryset);
        changedp=view.findViewById(R.id.changepic);
        accountNO=view.findViewById(R.id.accountbank);
        employeeID=view.findViewById(R.id.empidwork);
        Addresses=view.findViewById(R.id.addressss);
        Age=view.findViewById(R.id.admttts);
        changedp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_FILE);
                }else{
                    Intent intent=new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_PICK);
                    startActivityForResult(Intent.createChooser(intent,"choose image"),ACCESS_FILE);
                }

            }
        });
        key=getArguments().getString("employeeno");
        DocumentReference documentReference=db.collection("Users").document(userid).collection("Employee").document(key);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot value) {
                if (value.exists()) {
                    EmpName.setText(value.getString("Employee Name"));
                    Age.setText(value.getString("Date of Birth"));
                    Email.setText(value.getString("Email id"));
                    Gender.setText(value.getString("Gender"));
                    fund.setText(value.getString("Provident Fund"));
                    Loan.setText(value.getString("Loan"));
                    pfund.setText(value.getString("PF Rate"));
                    jobTitle.setText(value.getString("Job Title"));
                    Department.setText(value.getString("Department"));
                    Phone.setText(value.getString("Contact No"));
                    accountNO.setText(value.getString("Account no"));
                    employeeID.setText(value.getString("Employee Id"));
                    Addresses.setText(value.getString("Address"));
                    Salary.setText(value.getString("Salary"));
                    JoiningDate.setText(value.getString("Joining Date"));
                    prevpay.setText("Last Payment: "+value.getString("Last Payment"));
                    Picasso.get().load(value.getString("image_url")).placeholder(R.drawable.dpc).
                            fit().into(currentdp);
                }
            }
        });


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Employee emp=new Employee();
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.replace(R.id.backtolist,emp);
                ft.commit();
            }
        });
        return view;
    }

    private void uploadProfilepic() {
        pbar3.setVisibility(View.VISIBLE);
        final StorageReference filepath=storageReference.child("Employee_pic_"+ Timestamp.now().getSeconds());
        filepath.putFile(croppedUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String ImageUrl=uri.toString();
                        Map<String,Object> obj=new HashMap<>();
                        obj.put("image_url",ImageUrl);
                        DocumentReference documentReference=db.collection("Users").document(userid).collection("Employee").document(key);
                        documentReference.update(obj).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                pbar3.setVisibility(View.INVISIBLE);
                                upload.setVisibility(View.INVISIBLE);
                                Toast.makeText(getActivity(),"UPLOADED",Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ACCESS_FILE && resultCode==RESULT_OK && data!=null){
            picUri=data.getData();
            CropImage.activity(picUri).setGuidelines(CropImageView.Guidelines.ON).
                                       setCropShape(CropImageView.CropShape.OVAL).setFixAspectRatio(true).
                                       setActivityTitle("crop image").start(getContext(),this);
        }
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK){
                croppedUri=result.getUri();
                currentdp.setImageURI(croppedUri);
                upload.setVisibility(View.VISIBLE);
            }
        }
    }
}