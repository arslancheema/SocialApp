package com.example.aarshad.socialappstart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class Login extends AppCompatActivity {
EditText etName;
    EditText etEmail;
    EditText etPassword;
    ImageView ivUserImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         etName=(EditText)findViewById(R.id.etName);
          etEmail=(EditText)findViewById(R.id.etEmail);
          etPassword=(EditText)findViewById(R.id.etPassword);
         ivUserImage=(ImageView) findViewById(R.id.ivUserImage);
        ivUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    public void buLogin(View view) {

    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }


}
