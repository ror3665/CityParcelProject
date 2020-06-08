package com.example.cityparcel.register;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cityparcel.R;

public class DaumWebViewActivity extends AppCompatActivity {

    private WebView webView;
    private TextView txt_address, txt_postalCode;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daum_web_view);

        txt_postalCode = findViewById(R.id.textview_daumPostalCodeResult);
        txt_address = findViewById(R.id.textview_daumResult);
        final Button daumWebViewDone = (Button) findViewById(R.id.button_daumWebViewDone);

        // WebView 초기화
        init_webView();

        // 핸들러를 통한 JavaScript 이벤트 반응
        handler = new Handler();

        daumWebViewDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProcessData processData = new ProcessData();
                processData.processData(txt_address.getText().toString(), txt_postalCode.getText().toString());
            }
        });
    }
    public void init_webView() {
        // WebView 설정
        webView = (WebView) findViewById(R.id.webView_daum);

        // JavaScript 허용
        webView.getSettings().setJavaScriptEnabled(true);

        // JavaScript의 window.open 허용
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);


        // JavaScript이벤트에 대응할 함수를 정의 한 클래스를 붙여줌
        webView.addJavascriptInterface(new AndroidBridge(), "CityParcel");

        // web client 를 chrome 으로 설정
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        // webview url load. php 파일 주소
        webView.loadUrl("http://thecityparcel.com/searchaddress.php");

    }

    private class AndroidBridge {
        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    txt_postalCode.setText(arg1);
                    txt_address.setText(String.format("%s %s", arg2, arg3));
                    // WebView를 초기화 하지않으면 재사용할 수 없음
                    init_webView();
                }
            });
        }
    }

    public class ProcessData {
        public void processData(String address, String postalCode) {
            Bundle extra = new Bundle();
            Intent intent = new Intent();
            extra.putString("address", address);
            extra.putString("postalCode", postalCode);
            intent.putExtras(extra);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

}