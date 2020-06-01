package com.example.cityparcel.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cityparcel.MainActivity;
import com.example.cityparcel.R;

public class RegisterAddressFormActivity extends AppCompatActivity {

    private static final int DaumWebViewActivityForResult = 10000;
    private EditText registerAddressForm, registerPostalCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_form);

        final Button daumWebViewButton = (Button)findViewById(R.id.button_searchAddress);
        final Button registerFormNextButton = (Button)findViewById(R.id.button_registerAddressFormDone);

        registerAddressForm = (EditText)findViewById(R.id.editText_registerAddressForm);
        registerPostalCode = (EditText)findViewById(R.id.editText_registerPostalCode);

        daumWebViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent daumWebViewIntent = new Intent(RegisterAddressFormActivity.this, DaumWebViewActivity.class);
                RegisterAddressFormActivity.this.startActivityForResult(daumWebViewIntent, DaumWebViewActivityForResult);
            }
        });

        registerFormNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(RegisterAddressFormActivity.this, MainActivity.class);
                registerIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                RegisterAddressFormActivity.this.startActivity(registerIntent);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {

            case DaumWebViewActivityForResult:

                if (resultCode == RESULT_OK) {
                    String address = intent.getExtras().getString("address");
                    String postalCode = intent.getExtras().getString("postalCode");
                    if (address != null)
                        registerPostalCode.setText(postalCode);
                        registerAddressForm.setText(address);

                }
                break;

        }
    }
}
