package com.study.wechatjumphelper;

import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.study.wechatjumphelper.service.JumpService;

public class MainActivity extends AppCompatActivity {
    private int result;
    private Intent intent = null;
    private int REQUEST_MEDIA_PROJECTION = 1;
    MediaProjectionManager mMediaProjectionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMediaProjectionManager = (MediaProjectionManager)getApplication().getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        startProjectionIntent();
    }
    public void startProjectionIntent(){
        startActivityForResult(mMediaProjectionManager.createScreenCaptureIntent(), REQUEST_MEDIA_PROJECTION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_MEDIA_PROJECTION){
            if(resultCode==RESULT_OK){
                result=resultCode;
                intent=data;
                ((MyApplication)getApplication()).setMediaProjectionManager(mMediaProjectionManager);
                ((MyApplication)getApplication()).setIntent(intent);
                ((MyApplication)getApplication()).setResult(result);
                Intent serIntent=new Intent(getApplicationContext(), JumpService.class);
                startService(serIntent);
                finish();
            }
        }
    }
}
