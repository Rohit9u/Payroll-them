package com.example.payrollthem;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.auth.User;

public class NavdrawerActivity extends AppCompatActivity  {
    private TextView email,corporatename;
    private DrawerLayout drawerLayout;
    String documentid;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore fstore;
    private String userid;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navdrawer);
        auth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        user=auth.getCurrentUser();
        documentid=user.getUid();
        userid=documentid.substring(0,7);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView=navigationView.getHeaderView(0);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawerLayout=findViewById(R.id.drawer_layout);
        email=headerView.findViewById(R.id.copId);
        corporatename=headerView.findViewById(R.id.copname);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
            DocumentReference documentReference=fstore.collection("Users").document(userid);
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot value) {
                    corporatename.setText(value.getString("Corporate name"));
                    email.setText(value.getString("Email"));

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                 Toast.makeText(NavdrawerActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.profile, R.id.employee, R.id.pay_history,R.id.departments,R.id.pay_slip,R.id.payment_parameter)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.getMenu().findItem(R.id.logoutid).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                auth.signOut();
                user=auth.getCurrentUser();
                if(user==null){
                    Toast.makeText(getApplicationContext(),"logout successfully",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(NavdrawerActivity.this,Employer_login.class));
                    finish();
                }
                return true;
            }
        });
        navigationView.getMenu().findItem(R.id.quit_app).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent=new Intent(NavdrawerActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT",true);
                startActivity(intent);
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navdrawer, menu);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}