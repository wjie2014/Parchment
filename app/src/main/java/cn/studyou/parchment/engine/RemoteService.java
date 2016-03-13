package cn.studyou.parchment.engine;

import java.util.List;

import com.alibaba.fastjson.JSON;

import cn.studyou.baselibrary.activity.BaseActivity;
import cn.studyou.baselibrary.net.DefaultThreadPool;
import cn.studyou.baselibrary.net.HttpRequest;
import cn.studyou.baselibrary.net.RequestCallback;
import cn.studyou.baselibrary.net.RequestParameter;
import cn.studyou.baselibrary.net.Response;
import cn.studyou.baselibrary.net.URLData;
import cn.studyou.baselibrary.net.UrlConfigManager;
import cn.studyou.parchment.service.MockService;

public class RemoteService {
	private static RemoteService service = null;

	private RemoteService() {

	}

	public static synchronized RemoteService getInstance() {
		if (RemoteService.service == null) {
			RemoteService.service = new RemoteService();
		}
		return RemoteService.service;
	}

	public void invoke(final BaseActivity activity,
			final String apiKey,
			final List<RequestParameter> params,
			final RequestCallback callBack) {

		final URLData urlData = UrlConfigManager.findURL(activity, apiKey);
		if (urlData.getMockClass() != null) {
			try {
				MockService mockService = (MockService) Class.forName(
						urlData.getMockClass()).newInstance();
				String strResponse = mockService.getJsonData();

				final Response responseInJson = JSON.parseObject(strResponse,
						Response.class);
				if (callBack != null) {
					if (responseInJson.hasError()) {
						callBack.onFail(responseInJson.getErrorMessage());
					} else {
						callBack.onSuccess(responseInJson.getResult());
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else {
			HttpRequest request = activity.getRequestManager().createRequest(
					urlData, params, callBack);
			DefaultThreadPool.getInstance().execute(request);
		}
	}
}