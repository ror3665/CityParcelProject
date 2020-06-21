package com.example.cityparcel.memberRegister;

import android.app.AlertDialog;
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
import com.example.cityparcel.R;

import org.json.JSONObject;

public class RegisterFormActivity extends AppCompatActivity {
    private boolean validate = false;
    private AlertDialog dialog;
    private String memPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);

        Intent registerFormIntent = getIntent();
        memPhone = registerFormIntent.getStringExtra("memPhone");

        final EditText memEmailEditText = (EditText) findViewById(R.id.editText_registerFormEmailAddress);
        final EditText memPasswordEditText = (EditText) findViewById(R.id.editText_registerFormPassword);
        final EditText memPasswordDoubleCheckEditText = (EditText) findViewById(R.id.editText_editText_registerFormPasswordDoubleCheck);
        final EditText memUsernameEditText = (EditText) findViewById(R.id.editText_registerFormName);

        final Button validateButton = (Button) findViewById(R.id.button_UserValidate);
        final Button registerFormNextButton = (Button)findViewById(R.id.button_registerFormNext);

        //이메일 중복 검사
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mem_email = memEmailEditText.getText().toString();
                if (validate) {
                    return;
                }

                //이메일 값을 입력하지 않았다면
                if (mem_email.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterFormActivity.this);
                    dialog = builder.setMessage("아이디를 입력해주세요")
                            .setPositiveButton("OK", null)
                            .create();
                    dialog.show();
                    return;
                }


                //검증시작
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            //Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_LONG).show();

                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {//사용할 수 있는 이메일이라면
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterFormActivity.this);
                                dialog = builder.setMessage("사용 가능한 이메일 입니다.")
                                        .setPositiveButton("OK", null)
                                        .create();
                                dialog.show();
                                memEmailEditText.setEnabled(false);//이메일 값을 바꿀 수 없도록 함
                                validate = true;//검증완료
                            } else {//사용할 수 없는 이메일라면
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterFormActivity.this);
                                dialog = builder.setMessage("사용할 수 없는 이메일 입니다.")
                                        .setNegativeButton("OK", null)
                                        .create();
                                dialog.show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                ValidateRequest validateRequest = new ValidateRequest(mem_email, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterFormActivity.this);
                queue.add(validateRequest);
            }
        });

        registerFormNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이메일 중복체크를 했는지 확인함
                if (!validate) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterFormActivity.this);
                    dialog = builder.setMessage("이메일 중복을 확인해주세요")
                            .setNegativeButton("OK", null)
                            .create();
                    dialog.show();
                    return;
                }

                // 비밀번호 입력 확인
                if (memPasswordEditText.getText().toString().length() == 0) {
                    Toast.makeText(RegisterFormActivity.this, "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                    memPasswordEditText.requestFocus();
                    return;
                }

                // 이름 입력 확인
                if (memUsernameEditText.getText().toString().length() == 0) {
                    Toast.makeText(RegisterFormActivity.this, "이름을 입력하세요!", Toast.LENGTH_SHORT).show();
                    memUsernameEditText.requestFocus();
                    return;
                }

                // 비밀번호 일치 확인
                if (!memPasswordEditText.getText().toString().equals(memPasswordDoubleCheckEditText.getText().toString())) {
                    Toast.makeText(RegisterFormActivity.this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
                    memPasswordEditText.setText("");
                    memPasswordDoubleCheckEditText.setText("");
                    memPasswordEditText.requestFocus();
                    return;
                }

                Intent registerIntent = new Intent(RegisterFormActivity.this, RegisterAddressFormActivity.class);
                registerIntent.putExtra("memEmail", memEmailEditText.getText().toString());
                registerIntent.putExtra("memPassword", memPasswordEditText.getText().toString());
                registerIntent.putExtra("memUsername", memUsernameEditText.getText().toString());
                registerIntent.putExtra("memPhone", memPhone);
                RegisterFormActivity.this.startActivity(registerIntent);
            }
        });
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
