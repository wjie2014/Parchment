package cn.studyou.parchment.activity.base;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import cn.studyou.baselibrary.activity.BaseActivity;
import cn.studyou.baselibrary.net.RequestCallback;
import cn.studyou.baselibrary.utils.IntentUtil;
import cn.studyou.parchment.R;
import cn.studyou.parchment.activity.login.LoginActivity;
import cn.studyou.parchment.engine.AppConstants;

/**
 * 基本功能：基础Activity类
 * 创建：王杰
 * 创建时间：16/3/9
 * 邮箱：w489657152@gmail.com
 */
public abstract class AppBaseActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.green);//通知栏所需颜色

    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    protected boolean needCallback;

    protected ProgressDialog dlg;

    public abstract class AbstractRequestCallback implements RequestCallback {

        public abstract void onSuccess(String content);

        public void onFail(String errorMessage) {
            dlg.dismiss();

            new AlertDialog.Builder(AppBaseActivity.this).setTitle("出错啦")
                    .setMessage(errorMessage).setPositiveButton("确定", null)
                    .show();
        }

        public void onCookieExpired() {
            dlg.dismiss();

            new AlertDialog.Builder(AppBaseActivity.this)
                    .setTitle("出错啦")
                    .setMessage("Cookie过期，请重新登录")
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    startActivity(IntentUtil.getLauncherIntent().setClass(getApplicationContext(), LoginActivity.class).putExtra(AppConstants.NeedCallback,
                                            true));
                                }
                            }).show();
        }
    }

}
