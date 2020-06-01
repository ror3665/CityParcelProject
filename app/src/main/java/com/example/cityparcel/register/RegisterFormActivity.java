package com.example.cityparcel.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.example.cityparcel.R;

public class RegisterFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);

        final Button registerFormNextButton = (Button)findViewById(R.id.button_registerFormNext);

        registerFormNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(RegisterFormActivity.this, RegisterAddressFormActivity.class);
                RegisterFormActivity.this.startActivity(registerIntent);
            }
        });
    }
}
