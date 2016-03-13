package cn.studyou.parchment.service;

import com.alibaba.fastjson.JSON;
import cn.studyou.baselibrary.net.Response;
import cn.studyou.parchment.entity.UserInfo;

/**
 * 基本功能：用户登录服务类
 * 创建：王杰
 * 创建时间：16/3/8
 * 邮箱：w489657152@gmail.com
 */
public class MockLoginService extends  MockService {
    @Override
    public String getJsonData() {
        UserInfo userInfo = new UserInfo();
        userInfo.setLoginName("jianqiang.bao");
        userInfo.setUserName("包建强");
        userInfo.setScore(100);

        Response response = getSuccessResponse();
        response.setResult(JSON.toJSONString(userInfo));
        return JSON.toJSONString(response);
    }
}
