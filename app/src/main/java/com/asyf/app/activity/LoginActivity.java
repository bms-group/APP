package com.asyf.app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.asyf.app.R;
import com.asyf.app.application.Myapplication;
import com.asyf.app.common.Logger;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Myapplication application = (Myapplication) getApplication();
        Logger.d(TAG, "程序加载" + application.getName());
        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.e(TAG, "点击登录按钮");
                Toast.makeText(LoginActivity.this, "点击了登录", Toast.LENGTH_SHORT).show();
                //getTest();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getTest() {
        //https://www.apiopen.top/weatherApi?city=成都
        RequestParams params = new RequestParams("https://www.apiopen.top/weatherApi");
        params.addQueryStringParameter("city", "成都");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Logger.e(TAG, result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Logger.e(TAG, "失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }
}
