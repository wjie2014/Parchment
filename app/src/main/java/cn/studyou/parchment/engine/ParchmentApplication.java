package cn.studyou.parchment.engine;

import android.app.Application;

import org.litepal.LitePalApplication;
import org.litepal.crud.DataSupport;

import cn.studyou.baselibrary.log.L;
import cn.studyou.baselibrary.utils.AppIntroUtil;

/**
 * 基本功能：Application
 * 创建：王杰
 * 创建时间：16/3/7
 * 邮箱：w489657152@gmail.com
 */
public class ParchmentApplication extends Application {
    private static ParchmentApplication instance;
    public static final boolean DEVELOPER_MODE = true;
    public static User user;
    public static ParchmentApplication getThis() {
        if (instance == null)
            instance = new ParchmentApplication();
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化日志工具
        L.init(DEVELOPER_MODE);
        AppIntroUtil.getThis().markOpenApp(getApplicationContext());
        //初始化LitePal
        LitePalApplication.initialize(this);
        try {
            user = DataSupport.findLast(User.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(user!=null){
            L.e("用户信息存在！");
        }else{
            L.e("用户信息不存在！");
        }
    }
}
