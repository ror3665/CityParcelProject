package com.example.cityparcel.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.cityparcel.R;

public class SmsValidityActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_validity);

        final Button smsValidityNextButton = (Button)findViewById(R.id.button_smsValidityNext);

        smsValidityNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(SmsValidityActivity.this, RegisterFormActivity.class);
                SmsValidityActivity.this.startActivity(registerIntent);
            }
        });
    }
}
