package com.example.cityparcel.memberRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cityparcel.R;

public class SmsValidityActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_validity);

        final Button smsValidityNextButton = (Button)findViewById(R.id.button_smsValidityNext);
        final EditText memPhoneEditText = (EditText)findViewById(R.id.editText_cellphoneNumber);

        smsValidityNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //전화번호 입력 확인
                if (memPhoneEditText.getText().toString().length() == 0) {
                    Toast.makeText(SmsValidityActivity.this, "전화번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    memPhoneEditText.requestFocus();
                    return;
                }


                Intent registerIntent = new Intent(SmsValidityActivity.this, RegisterFormActivity.class);
                registerIntent.putExtra("memPhone", memPhoneEditText.getText().toString());
                SmsValidityActivity.this.startActivity(registerIntent);
            }
        });
    }
}
