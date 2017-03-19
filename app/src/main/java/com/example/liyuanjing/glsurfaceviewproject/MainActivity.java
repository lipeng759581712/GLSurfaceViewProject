package com.example.liyuanjing.glsurfaceviewproject;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.widget.Toast;


public class MainActivity extends Activity {
    private GLSurfaceView glSurfaceView;
    private boolean rendererSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.glSurfaceView = new GLSurfaceView(this);
        final ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo=activityManager.getDeviceConfigurationInfo();
        final boolean supportsEs2=configurationInfo.reqGlEsVersion>=0x20000;
        if(supportsEs2){
            this.glSurfaceView.setEGLContextClientVersion(2);
            this.glSurfaceView.setRenderer(new LYJRenderer(this));
            this.rendererSet=true;
        }else{
            Toast.makeText(this,"bu zhi chi gai banben ",Toast.LENGTH_SHORT).show();
            return;
        }
        setContentView(this.glSurfaceView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(this.rendererSet){
            this.glSurfaceView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(this.rendererSet){
            this.glSurfaceView.onPause();
        }
    }
}
