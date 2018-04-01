package com.asyf.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.asyf.app.R;
import com.asyf.app.common.Logger;

/**
 * Created by Administrator on 2018/4/1.
 */

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fg_home);
        Logger.e("AAA", "创建");
    }
}
