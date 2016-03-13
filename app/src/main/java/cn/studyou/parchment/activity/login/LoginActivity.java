package cn.studyou.parchment.activity.login;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.studyou.baselibrary.net.RequestCallback;
import cn.studyou.baselibrary.net.RequestParameter;
import cn.studyou.baselibrary.utils.IntentUtil;
import cn.studyou.parchment.R;
import cn.studyou.parchment.activity.base.AppBaseActivity;
import cn.studyou.parchment.activity.main.MainActivity;
import cn.studyou.parchment.engine.AppConstants;
import cn.studyou.parchment.engine.RemoteService;
import cn.studyou.parchment.engine.User;
import cn.studyou.parchment.entity.UserInfo;
import me.drakeet.materialdialog.MaterialDialog;

public class LoginActivity extends AppBaseActivity {

    @Bind(R.id.activity_login_account_et)
    EditText activityLoginAccountEt;
    @Bind(R.id.activity_login_password_et)
    EditText activityLoginPasswordEt;
    @Bind(R.id.activity_login_login_btn)
    Button activityLoginLoginBtn;
    @Bind(R.id.activity_login_forgot_password_tv)
    TextView activityLoginForgotPasswordTv;
    @Bind(R.id.activity_login_register_tv)
    TextView activityLoginRegisterTv;
    private MaterialDialog mMaterialDialog;
    private MaterialDialog mMaterialProgressDialog;
    private boolean isAccount = false;
    private boolean isPassword = false;

    @Override
    protected void initVariables() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
            return;
        needCallback = bundle.getBoolean(AppConstants.NeedCallback, false);

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mMaterialDialog = new MaterialDialog(this);
        activityLoginAccountEt.addTextChangedListener(activityLoginAccountEtWatcher);
        activityLoginPasswordEt.addTextChangedListener(activityLoginPasswordEtWatcher);
        activityLoginLoginBtn.setClickable(false);
    }

    @Override
    protected void loadData() {

    }

    @OnClick(R.id.activity_login_login_btn)
    void login() {
        RequestCallback loginCallback = new AbstractRequestCallback() {

            @Override
            public void onSuccess(String content) {
                UserInfo userInfo = JSON.parseObject(content,
                        UserInfo.class);
                if (userInfo != null) {
                    User.getInstance().setLoginName(userInfo.getLoginName());
                    User.getInstance().setScore(userInfo.getScore());
                    User.getInstance().setUserName(userInfo.getUserName());
                    User.getInstance().setLoginStatus(true);
                    User.getInstance().save();
                }

                if (needCallback) {
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    startMainActivity();
                }
            }
        };

        ArrayList<RequestParameter> params = new ArrayList<RequestParameter>();
        RequestParameter rp1 = new RequestParameter("loginName", "wangjie");
        RequestParameter rp2 = new RequestParameter("password", "1111");
        params.add(rp1);
        params.add(rp2);

        RemoteService.getInstance().invoke(this, "login", params,
                loginCallback);
    }

    private void showProgressBar() {
        mMaterialProgressDialog = new MaterialDialog(this);
        View view = LayoutInflater.from(this)
                .inflate(R.layout.item_progressbar,
                        null);
        mMaterialProgressDialog.setContentView(view).show();
    }

    private void dissmissProgressBar() {
        if (mMaterialProgressDialog != null) {
            mMaterialProgressDialog.dismiss();
        }
    }

    private void startMainActivity() {
        dissmissProgressBar();
        startActivity(IntentUtil.getLauncherIntent().setClass(getApplicationContext(), MainActivity.class));
        finish();
    }

    private void showDialog(String message) {
        mMaterialDialog.setTitle(R.string.login_error_title)
                .setMessage(message)
                .setPositiveButton(getString(R.string.confirm), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                });
        mMaterialDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    TextWatcher activityLoginAccountEtWatcher = new TextWatcher() {
        private CharSequence temp;

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            temp = s;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            if (temp.length() > 1) {
                isAccount = true;
            } else {
                isAccount = false;
            }
            btnCanClick();
        }

    };
    TextWatcher activityLoginPasswordEtWatcher = new TextWatcher() {
        private CharSequence temp;

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            temp = s;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            if (temp.length() > 1) {
                isPassword = true;
            } else {
                isPassword = false;
            }
            btnCanClick();
        }
    };

    private void btnCanClick() {
        if (isPassword && isAccount) {
            activityLoginLoginBtn.setClickable(true);
            activityLoginLoginBtn.setBackgroundResource(R.drawable.shape_button_bg_dark);
        } else {
            activityLoginLoginBtn.setClickable(false);
            activityLoginLoginBtn.setBackgroundResource(R.drawable.shape_button_bg);
        }
    }
}
