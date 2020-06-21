package com.example.cityparcel.memberRegister;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ValidateRequest extends StringRequest {

    final static private String URL = "http://thecityparcel.com/UserValidate.php";
    private Map<String, String> parameters;

    public ValidateRequest(String mem_email, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("mem_email", mem_email);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}

