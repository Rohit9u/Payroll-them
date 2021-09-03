//package com.example.payrollthem;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.ActionBarDrawerToggle;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.drawerlayout.widget.DrawerLayout;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//import com.google.android.material.navigation.NavigationView;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//
//public class postLogin extends AppCompatActivity {
//    private ImageView imview;
//    private FirebaseAuth auth;
//    private ProgressBar prgBar;
//    private DrawerLayout drawerLayout;
//    private Toolbar toolbars;
//    private NavigationView navigationView;
//    FirebaseAuth.AuthStateListener authStateListener;
//    FirebaseUser user;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_post_login);
//        auth = FirebaseAuth.getInstance();
//        drawerLayout = findViewById(R.id.drawerlayout);
//        toolbars = findViewById(R.id.toolbar);
//        navigationView = findViewById(R.id.navigationvbar);
//        setSupportActionBar(toolbars);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
////        imview.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                signout();
////            }
////        });
//    }
//
////    private void signout() {
////        prgBar.setVisibility(View.VISIBLE);
////        auth.signOut();
////        user=auth.getCurrentUser();
////                if(user==null){
////                    Toast.makeText(getApplicationContext(),"logout successfully",Toast.LENGTH_LONG).show();
////                    prgBar.setVisibility(View.GONE);
////                    startActivity(new Intent(postLogin.this,Employer_login.class));
////                    finish();
////                }
////    }
//}