package cn.studyou.baselibrary.net;

public interface RequestCallback
{
	public void onSuccess(String content);

	public void onFail(String errorMessage);

	public void onCookieExpired();
}
