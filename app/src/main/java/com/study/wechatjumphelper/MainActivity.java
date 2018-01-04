package com.study.wechatjumphelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private String[] da = {"092000.png","091856.png","092055.png",
            "092243.png","092422.png","092618.png","092749.png"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < da.length; i++) {
                    String testData = Constants.getPath("testData/", da[i]);
                    JumpUtils.jumpJump(testData);
                }

            }
        }).start();
    }
}
