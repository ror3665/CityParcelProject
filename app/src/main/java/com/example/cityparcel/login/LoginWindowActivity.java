package com.example.cityparcel.login;

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
import com.example.cityparcel.serviceHome.ServiceMainActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginWindowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText memEmailEditText = (EditText)findViewById(R.id.editTextLoginEmailAddress);
        final EditText memPasswordEditText = (EditText)findViewById(R.id.editTextLoginPassword);

        final Button loginButton = (Button)findViewById(R.id.button_loginWindow);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 아이디 입력 확인
                if (memEmailEditText.getText().toString().length() == 0) {
                    Toast.makeText(LoginWindowActivity.this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
                    memEmailEditText.requestFocus();
                    return;
                }

                // 비밀번호 입력 확인
                if (memPasswordEditText.getText().toString().length() == 0) {
                    Toast.makeText(LoginWindowActivity.this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    memPasswordEditText.requestFocus();
                    return;
                }

                final String mem_email = memEmailEditText.getText().toString();
                final String mem_password = memPasswordEditText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success)
                            {
                                String memEmail = jsonResponse.getString("mem_email");
                                Intent loginIntent = new Intent(LoginWindowActivity.this, ServiceMainActivity.class);
                                loginIntent.putExtra("mem_email", mem_email);
                                //LoginWindowActivity.this.startActivity(loginIntent);
                                startActivity(loginIntent);
                                finish();
                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginWindowActivity.this);
                                builder.setMessage("로그인에 실패하였습니다")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(mem_email, mem_password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginWindowActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}
