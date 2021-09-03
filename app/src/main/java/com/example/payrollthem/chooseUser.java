package com.example.payrollthem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class chooseUser extends AppCompatActivity implements View.OnClickListener {
    private ImageView employer_login;
    private ImageView employee_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user);
        employer_login=findViewById(R.id.corporate);
        employee_login=findViewById(R.id.user);
        employer_login.setOnClickListener(this);
        employee_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.corporate:
                Intent intent=new Intent(chooseUser.this,Employer_login.class);
                startActivity(intent);
                break;
            case R.id.user:
                Intent intent1=new Intent(chooseUser.this,Employee_login.class);
                startActivity(intent1);
                break;

        }

    }
}