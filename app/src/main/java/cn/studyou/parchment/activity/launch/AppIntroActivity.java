package cn.studyou.parchment.activity.launch;

import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;

import cn.studyou.parchment.activity.main.MainActivity;
import cn.studyou.parchment.activity.login.LoginActivity;
import cn.studyou.parchment.engine.ParchmentApplication;
import cn.studyou.parchment.R;
import cn.studyou.parchment.fragment.SlideFragment;
import cn.studyou.baselibrary.utils.IntentUtil;

public class AppIntroActivity extends AppIntro {

    @Override
    public void init(Bundle savedInstanceState) {

        addSlide(SlideFragment.newInstance(R.layout.intro1));
        addSlide(SlideFragment.newInstance(R.layout.intro2));
        addSlide(SlideFragment.newInstance(R.layout.intro3));
        addSlide(SlideFragment.newInstance(R.layout.intro4));
        setBarColor(getResources().getColor(R.color.green));
        setSeparatorColor(getResources().getColor(R.color.colorAccent));
        setVibrateIntensity(30);
        setSkipText(getString(R.string.skip));
        setDoneText(getString(R.string.enter));
    }

    private void startMain(){
        startActivity(IntentUtil.getLauncherIntent().setClass(getApplicationContext(), MainActivity.class));
        finish();
    }

    private void startLogin(){
        startActivity(IntentUtil.getLauncherIntent().setClass(getApplicationContext(),LoginActivity.class));
        finish();
    }

    private void startActivity(){
        boolean isLogin = false;
        if (ParchmentApplication.user!=null){
            isLogin = true;
        }
        if (isLogin){
            startMain();
        }else {
            startLogin();
        }
    }
    @Override
    public void onSkipPressed() {
        startActivity();
    }

    @Override
    public void onDonePressed() {
        startActivity();
    }

    @Override
    public void onSlideChanged() {
    }

    @Override
    public void onNextPressed() {
    }
}
