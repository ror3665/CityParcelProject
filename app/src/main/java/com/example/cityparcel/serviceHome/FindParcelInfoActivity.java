package com.example.cityparcel.serviceHome;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cityparcel.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FindParcelInfoActivity extends AppCompatActivity {

    private static String URL = "http://thecityparcel.com/ShowParcelInfo.php";
    private static String URL2 = "http://thecityparcel.com/GetUserName.php";
    private static String URL3 = "http://thecityparcel.com/CommentList.php";
    private TextView usernameTextView, startingPointTextView, titleTextView, destinationTextView, parcelInfoTextView, priceTextView;
    private EditText commentEditText;
    private String writer, getWriterUsername, getStartingPoint, getTitle, getDestination, getParcelInfo, getPrice, memEmail, getUsername, getCommentWriter, getCommentContent;
    private int parcelIdx;
    private ImageButton addCommentBtn;
    private RecyclerView recyclerView;
    private ParcelInfoAdapter parcelInfoAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcel_info);

        Intent intent = getIntent();
        writer = intent.getStringExtra("member");
        parcelIdx = intent.getIntExtra("parcelIdx", 0); // 0 is error
        memEmail = intent.getStringExtra("memEmail");

        usernameTextView = (TextView) findViewById(R.id.textView_parcel_info_username);
        startingPointTextView = (TextView) findViewById(R.id.textView_parcel_info_startingPoint);
        titleTextView = (TextView) findViewById(R.id.textView_parcel_info_title);
        destinationTextView = (TextView) findViewById(R.id.textView_parcel_info_destination);
        parcelInfoTextView = (TextView) findViewById(R.id.textView_parcel_info_parcelInfo);
        priceTextView = (TextView) findViewById(R.id.textView_parcel_info_price);
        commentEditText = (EditText)findViewById(R.id.editText_parcel_info_comment);
        addCommentBtn = (ImageButton)findViewById(R.id.button_parcel_info_send);

        init();
        getWriterUsernamePHP();
        getUsernamePHP();
        getParcelListPHP();

        addCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCommentPHP();
            }
        });
    }

    private void init() {
        recyclerView = findViewById(R.id.parcel_info_recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        parcelInfoAdapter = new ParcelInfoAdapter();
        getCommentListPHP();
    }


    private void getParcelListPHP() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonobject = new JSONObject(response);
                    JSONArray jsonarray = jsonobject.getJSONArray("response");
                    for (int i = 0; i < jsonarray.length(); ++i) {
                        JSONObject jData = jsonarray.getJSONObject(i);
                        getStartingPoint = jData.getString("parcel_location");
                        getTitle = jData.getString("parcel_title");
                        getDestination = jData.getString("parcel_destination");
                        getParcelInfo = jData.getString("parcel_form");
                        getPrice = jData.getString("parcel_price");
                    }
                    setTextView();
                } catch (JSONException e) {
                    Log.e("JSON Parser", "Error parsing data [" + e.getMessage()+"]");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FindParcelInfoActivity.this, "DB CONNECTION FAIL", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("idx", Integer.toString(parcelIdx));
                return parameters;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void setTextView() {
        String price = NumberFormat.getCurrencyInstance(Locale.KOREA).format(Integer.parseInt(getPrice));
        startingPointTextView.setText(getStartingPoint + "(출발지)");
        titleTextView.setText(getTitle);
        destinationTextView.setText(getDestination  + "(목적지)");
        parcelInfoTextView.setText(getParcelInfo);
        priceTextView.setText("운송비 " + price);
    }

    private void getWriterUsernamePHP() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonobject = new JSONObject(response);
                    JSONArray jsonarray = jsonobject.getJSONArray("response");
                    for (int i = 0; i < jsonarray.length(); ++i) {
                        JSONObject jData = jsonarray.getJSONObject(i);
                        getWriterUsername = jData.getString("mem_username");
                    }
                    setUsernameTextView();
                } catch (JSONException e) {
                    Log.e("JSON Parser", "Error parsing data [" + e.getMessage()+"]");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FindParcelInfoActivity.this, "DB CONNECTION FAIL", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("writer", writer);
                return parameters;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getUsernamePHP() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonobject = new JSONObject(response);
                    JSONArray jsonarray = jsonobject.getJSONArray("response");
                    for (int i = 0; i < jsonarray.length(); ++i) {
                        JSONObject jData = jsonarray.getJSONObject(i);
                        getUsername = jData.getString("mem_username");
                    }
                    setUsernameTextView();
                } catch (JSONException e) {
                    Log.e("JSON Parser", "Error parsing data [" + e.getMessage()+"]");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FindParcelInfoActivity.this, "DB CONNECTION FAIL", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("writer", memEmail);
                return parameters;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void setUsernameTextView() {
        usernameTextView.setText(getWriterUsername);
    }

    private void addCommentPHP() {
        String index = Integer.toString(parcelIdx);
        String comment = commentEditText.getText().toString();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Toast.makeText(FindParcelInfoActivity.this, "덧글 등록완료", Toast.LENGTH_LONG).show();
                        commentEditText.setText(null);

                    } else {
                        Toast.makeText(FindParcelInfoActivity.this, "DB CONNECTION FAIL", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        AddCommentRequest addCommentRequest = new AddCommentRequest(index, getUsername, comment, responseListener);
        RequestQueue queue = Volley.newRequestQueue(FindParcelInfoActivity.this);
        queue.add(addCommentRequest);
    }

    private void getCommentListPHP() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonobject = new JSONObject(response);
                    JSONArray jsonarray = jsonobject.getJSONArray("response");
                    for (int i = 0; i < jsonarray.length(); ++i) {
                        JSONObject jData = jsonarray.getJSONObject(i);
                        getCommentWriter = jData.getString("comment_member");
                        getCommentContent = jData.getString("comment_comment");
                        parcelInfoAdapter.addItem(new ParcelInfoNode(getCommentWriter, getCommentContent));
                    }
                    recyclerView.setAdapter(parcelInfoAdapter); //show
                } catch (JSONException e) {
                    Log.e("JSON Parser", "Error parsing data [" + e.getMessage()+"]");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FindParcelInfoActivity.this, "DB CONNECTION FAIL", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("idx", Integer.toString(parcelIdx));
                return parameters;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

