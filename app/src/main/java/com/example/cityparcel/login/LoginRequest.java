package com.example.cityparcel.login;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    final static private String URL = "http://thecityparcel.com/Login.php";
    private Map<String, String> parameters;

    public LoginRequest(String mem_email, String mem_password, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        Log.d("heedong", mem_email + " " + mem_password);
        parameters.put("mem_email", mem_email);
        parameters.put("mem_password", mem_password);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
