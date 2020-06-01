package com.example.cityparcel;
//
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cityparcel.login.LoginWindowActivity;
import com.example.cityparcel.register.RegisterTermsActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button registerButton = (Button)findViewById(R.id.button_register);
        final Button loginButton = (Button)findViewById(R.id.button_login);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this, RegisterTermsActivity.class);
                MainActivity.this.startActivity(registerIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(MainActivity.this, LoginWindowActivity.class);
                MainActivity.this.startActivity(loginIntent);
            }
        });

    }
}