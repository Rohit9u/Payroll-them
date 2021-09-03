package com.example.payrollthem.ui.Profile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;

import api.documentApi;
import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends Fragment {
    private ImageView editInfo;
    private TextView setCorporate;
    private TextView setAdmin;
    private TextView setPhone;
    private TextView setEmail;
    private TextView setGender;
    private TextView setEPF;
    private CircleImageView profilepic;
    private String Imageurl;
    private String setcop,setadm,setph,setem;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private String doced;
    private FirebaseFirestore fstore=FirebaseFirestore.getInstance();
    private String uniqueId;
    private TextView Unique;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v1=inflater.inflate(R.layout.profile_fragment, container, false);
        editInfo=v1.findViewById(R.id.editpr);
        setCorporate=v1.findViewById(R.id.copN);
        setAdmin=v1.findViewById(R.id.admN);
        profilepic=v1.findViewById(R.id.dpc);
        setPhone=v1.findViewById(R.id.contN);
        setEmail=v1.findViewById(R.id.emailN);
        setEPF=v1.findViewById(R.id.rateset);
        setGender=v1.findViewById(R.id.GenderN);
        Unique=v1.findViewById(R.id.uniquIde);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        doced=user.getUid();
        uniqueId=doced.substring(0,7);
        DocumentReference documentReference=fstore.collection("Users").document(uniqueId);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    setCorporate.setText(documentSnapshot.getString("Corporate name"));
                    setAdmin.setText(documentSnapshot.getString("Admin name"));
                    setPhone.setText(documentSnapshot.getString("Phone no"));
                    setEmail.setText(documentSnapshot.getString("Email"));
                    setEPF.setText(documentSnapshot.getString("EPF rate")+"%");
                    setGender.setText(documentSnapshot.getString("Gender"));
                    Unique.setText(documentSnapshot.getString("Company Unique Id"));
                    Picasso.get().load(documentSnapshot.getString("Image_Url")).placeholder(R.drawable.dpc).
                            fit().into(profilepic);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("error: ",e.getMessage());
            }
        });


        editInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProfInfo addprof=new addProfInfo();
                FragmentManager fagmag=getActivity().getSupportFragmentManager();
                FragmentTransaction fagtr=fagmag.beginTransaction();
                fagtr.replace(R.id.firstLayout,addprof);
                fagtr.commit();
            }
        });
        return v1;
    }



}