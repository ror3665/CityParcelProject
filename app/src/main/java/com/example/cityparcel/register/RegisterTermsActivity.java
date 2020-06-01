package com.example.cityparcel.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import com.example.cityparcel.R;

public class RegisterTermsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_terms);

        final CheckBox acceptCheckBox = (CheckBox)findViewById(R.id.checkBox_registerTermsCheckbox);
        final Button registerTermsAcceptButton = (Button)findViewById(R.id.button_registerAccept);

        registerTermsAcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(acceptCheckBox.isChecked()) {
                    Intent registerIntent = new Intent(RegisterTermsActivity.this, SmsValidityActivity.class);
                    RegisterTermsActivity.this.startActivity(registerIntent);
                }
            }
        });

    }
}
