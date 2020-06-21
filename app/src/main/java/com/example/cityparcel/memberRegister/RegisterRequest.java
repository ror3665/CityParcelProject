package com.example.cityparcel.memberRegister;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    final static private String URL = "http://thecityparcel.com/Register.php";
    private Map<String, String> parameters;

    public RegisterRequest(String mem_email, String mem_password, String mem_username, String mem_phone, String mem_zipcode, String mem_address1, String mem_address2, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("mem_email", mem_email);
        parameters.put("mem_password", mem_password);
        parameters.put("mem_username", mem_username);
        parameters.put("mem_phone", mem_phone);
        parameters.put("mem_zipcode", mem_zipcode);
        parameters.put("mem_address1", mem_address1);
        parameters.put("mem_address2", mem_address2);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}