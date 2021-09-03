package com.example.payrollthem.ui.Profile;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
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
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
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
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class addProfInfo extends Fragment   {
    private String[] Gender={"Male","Female"};
    private static final int PERMISSION_FILE=23;
    private static final int ACCESS_FILE=43;
    private static final int PICK_IMAGE=1;
    private TextView saveInfo;
    private EditText Editcorporate;
    private EditText EditAdmin;
    private EditText EditPhone;
    private EditText epf;
    private Spinner spin;
    private CircleImageView profilepic;
    private ImageView choose;
    private ImageView upload;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser firebaseUser;
    private String currUserdocument;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private ProgressBar progressBar;
    private Uri firsturi;
    String sex;
    private Uri resultUri;
    private ImageView back;
    private DocumentReference docurefs;
    private String uniqueId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v2= inflater.inflate(R.layout.fragment_add_prof_info, container, false);
        saveInfo=v2.findViewById(R.id.save_changes);
        storageReference= FirebaseStorage.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        currUserdocument=firebaseUser.getUid();
        uniqueId=currUserdocument.substring(0,7);
        docurefs=db.collection("Users").document(uniqueId);
        Editcorporate=v2.findViewById(R.id.EcopN);
        profilepic=v2.findViewById(R.id.dc);
        choose=v2.findViewById(R.id.changepicup);
        upload=v2.findViewById(R.id.uploadup);
        EditAdmin=v2.findViewById(R.id.EadmN);
        epf=v2.findViewById(R.id.EpfrN);
        EditPhone=v2.findViewById(R.id.EcontN);
        progressBar=v2.findViewById(R.id.prbar);
        back=v2.findViewById(R.id.imageView);
        spin=v2.findViewById(R.id.EGenderN);
        CollectionReference colref=db.collection("Users");
        colref.whereEqualTo("userId",currUserdocument).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                        uniqueId=documentSnapshot.getString("Company Unique Id");
                    }
                }
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDp();
            }
        });
        ArrayAdapter arrayAdapter=new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,Gender);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(arrayAdapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sex=Gender[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        docurefs.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
             if(value.getString("Admin name")!=null){
                 EditAdmin.setText(value.getString("Admin name"));
             }
             if(value.getString("Corporate name")!=null){
                 Editcorporate.setText(value.getString("Corporate name"));
             }
             if(value.getString("Phone no")!=null){
                 EditPhone.setText(value.getString("Phone no"));
             }
             if(value.getString("EPF rate")!=null){
                 epf.setText(value.getString("EPF rate"));
             }
                Picasso.get().load(value.getString("Image_Url")).placeholder(R.drawable.dp).
                        fit().into(profilepic);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Profile profile = new Profile();
                FragmentManager mf = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = mf.beginTransaction();
                ft.replace(R.id.secondlay, profile);
                ft.commit();
            }
        });
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                firebaseUser=firebaseAuth.getCurrentUser();
                if(firebaseUser!=null){

                }else{

                }
            }
        };
        saveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProfle();

            }
        });
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_FILE);
                }else {
                    Intent gallery = new Intent();
                    gallery.setType("image/*");
                    gallery.setAction(Intent.ACTION_PICK);
                    startActivityForResult(Intent.createChooser(gallery, "select picture"), ACCESS_FILE);
                }
                }
        });
        return v2;
    }

    private void saveDp() {
        progressBar.setVisibility(View.VISIBLE);
        final StorageReference filepath = storageReference.child("user profile image").child("profile_pic_" + Timestamp.now().getSeconds());
        filepath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String imageurl = uri.toString();
                        Map<String,Object> object=new HashMap<>();
                        object.put("Image_Url", imageurl);

                       docurefs.update(object).addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void aVoid) {
                               progressBar.setVisibility(View.INVISIBLE);
                               upload.setVisibility(View.INVISIBLE);
                               Toast.makeText(getActivity(),"Image added",Toast.LENGTH_LONG).show();
                           }
                       }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                           }
                       });

                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }


    private void saveProfle() {
        progressBar.setVisibility(View.VISIBLE);
        final String copname = Editcorporate.getText().toString().trim();
        final String admname = EditAdmin.getText().toString().trim();
        final String phno = EditPhone.getText().toString().trim();
        final String epff=epf.getText().toString().trim();
            if(!TextUtils.isEmpty(copname) &&
            !TextUtils.isEmpty(admname) &&
            !TextUtils.isEmpty(phno) &&
            !TextUtils.isEmpty(epff)){
                Map<String, Object> obj = new HashMap<>();
                obj.put("Admin name", admname);
                obj.put("Phone no", phno);
                obj.put("Corporate name",copname);
                obj.put("Gender",sex);
                obj.put("EPF rate",epff);
                docurefs.update(obj).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity(),"UPDATED SUCCESSFULLY",Toast.LENGTH_LONG).show();
                        Profile profile = new Profile();
                        FragmentManager mf = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = mf.beginTransaction();
                        ft.replace(R.id.secondlay, profile);
                        ft.commit();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ACCESS_FILE && resultCode==RESULT_OK && data!=null  ) {
            firsturi = data.getData();
            profilepic.setImageURI(firsturi);
            CropImage.activity(firsturi)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .setActivityTitle("crop image")
                    .setFixAspectRatio(true)
                    .start(getContext(),this);
        }
            if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    resultUri = result.getUri();
                    profilepic.setImageURI(resultUri);
                    upload.setVisibility(View.VISIBLE);
                }

            }
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(firebaseUser!=null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}