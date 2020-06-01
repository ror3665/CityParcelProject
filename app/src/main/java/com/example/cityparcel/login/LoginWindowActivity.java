package com.example.cityparcel.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cityparcel.R;
import com.example.cityparcel.serviceHome.ServiceMainActivity;

public class LoginWindowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button loginButton = (Button)findViewById(R.id.button_loginWindow);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(LoginWindowActivity.this, ServiceMainActivity.class);
                LoginWindowActivity.this.startActivity(loginIntent);
            }
        });
    }
}
