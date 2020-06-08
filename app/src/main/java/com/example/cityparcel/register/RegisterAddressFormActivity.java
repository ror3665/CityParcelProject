package com.example.cityparcel.register;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.cityparcel.MainActivity;
import com.example.cityparcel.R;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterAddressFormActivity extends AppCompatActivity {
    private AlertDialog dialog;
    private static final int DaumWebViewActivityForResult = 10000;
    private EditText registerAddressForm, registerPostalCode, registerDetailAddressForm;
    private String memEmail, memPassword, memUsername, memPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_form);

        Intent registerAddressIntent = getIntent();
        memEmail = registerAddressIntent.getStringExtra("memEmail");
        memPassword = registerAddressIntent.getStringExtra("memPassword");
        memUsername = registerAddressIntent.getStringExtra("memUsername");
        memPhone = registerAddressIntent.getStringExtra("memPhone");

        final Button daumWebViewButton = (Button)findViewById(R.id.button_searchAddress);
        final Button registerFormNextButton = (Button)findViewById(R.id.button_registerAddressFormDone);

        registerPostalCode = (EditText)findViewById(R.id.editText_registerPostalCode);
        registerAddressForm = (EditText)findViewById(R.id.editText_registerAddressForm);
        registerDetailAddressForm = (EditText)findViewById(R.id.editText_registerDetailAddressForm);


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

                // 우편번호 입력 확인
                if (registerPostalCode.getText().toString().length() == 0) {
                    Toast.makeText(RegisterAddressFormActivity.this, "우편번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                    registerPostalCode.requestFocus();
                    return;
                }

                // 주소 입력 확인
                if (registerAddressForm.getText().toString().length() == 0) {
                    Toast.makeText(RegisterAddressFormActivity.this, "주소를 입력하세요!", Toast.LENGTH_SHORT).show();
                    registerAddressForm.requestFocus();
                    return;
                }

                // 세부 주소 입력 확인
                if (registerDetailAddressForm.getText().toString().length() == 0) {
                    Toast.makeText(RegisterAddressFormActivity.this, "세부 주소를 입력하세요!", Toast.LENGTH_SHORT).show();
                    registerDetailAddressForm.requestFocus();
                    return;
                }

                //회원정보 DB저장
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterAddressFormActivity.this);
                                dialog = builder.setMessage("회원 등록에 성공했습니다!")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent registerIntent = new Intent(RegisterAddressFormActivity.this, MainActivity.class);
                                                registerIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                RegisterAddressFormActivity.this.startActivity(registerIntent);
                                            }
                                        })
                                        .create();

                                dialog.show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterAddressFormActivity.this);
                                dialog = builder.setMessage("회원등록에 실패했습니다!")
                                        .setNegativeButton("다시시도", null)
                                        .create();
                                dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                String mem_zipcode = registerPostalCode.getText().toString();
                String mem_address1 = registerAddressForm.getText().toString();
                String mem_address2 = registerDetailAddressForm.getText().toString();

                RegisterRequest registerRequest = new RegisterRequest(memEmail, memPassword, memUsername, memPhone, mem_zipcode, mem_address1, mem_address2, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterAddressFormActivity.this);
                queue.add(registerRequest);


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
    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
