package cn.studyou.parchment.activity.launch;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.studyou.parchment.activity.main.MainActivity;
import cn.studyou.parchment.activity.login.LoginActivity;
import cn.studyou.parchment.engine.ParchmentApplication;
import cn.studyou.parchment.R;
import cn.studyou.baselibrary.log.L;
import cn.studyou.baselibrary.utils.AppIntroUtil;
import cn.studyou.baselibrary.utils.IntentUtil;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        int launchMode = AppIntroUtil.getThis().getLaunchMode();
        boolean isLogin = false;
        if (ParchmentApplication.user!=null){
            isLogin = true;
        }
        if (launchMode == 3) {
            L.e("Again start!");
            if (isLogin){
                delayedStartMain();
            }else{
                delayedStartLogin();
            }
        } else if (launchMode == 2) {
            L.e("Update start!");
            delayedStartIntro();
        } else if (launchMode == 1) {
            L.e("First start!");
            delayedStartIntro();
        }
    }

    private void delayedStartMain() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(IntentUtil.getLauncherIntent().setClass(getApplicationContext(), MainActivity.class));
                finish();
            }
        }, 3000);
    }

    private void delayedStartLogin() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(IntentUtil.getLauncherIntent().setClass(getApplicationContext(), LoginActivity.class));
                finish();
            }
        }, 3000);
    }

    private void delayedStartIntro() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(IntentUtil.getLauncherIntent().setClass(getApplicationContext(), AppIntroActivity.class));
                finish();
            }
        }, 3000);
    }
}
