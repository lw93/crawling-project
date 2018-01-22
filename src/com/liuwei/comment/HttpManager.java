package com.liuwei.comment;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import com.liuwei.api.ApiService;

public class HttpManager {

	public static OkHttpClient.Builder okHttpClient;
	public static Retrofit retrofit;
	public static ApiService apiService;

	public static ApiService getClient() {
		if (null == okHttpClient) {
			okHttpClient = new OkHttpClient.Builder();
		}
		okHttpClient.cookieJar(new CookieJar() {
			List<Cookie> cookies;

			@Override
			public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
				this.cookies = list;
			}

			@Override
			public List<Cookie> loadForRequest(HttpUrl httpUrl) {
				if (cookies != null){
					System.out.println(cookies.toString());
					return cookies;
				}
				return new ArrayList<Cookie>();
			}
		});
		if (null == retrofit) {
			retrofit = new Retrofit.Builder().client(okHttpClient.build())
					.baseUrl(Constant.BASE_URL)
					.addConverterFactory(ScalarsConverterFactory.create())
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.build();
			if (null == apiService) {
				apiService = retrofit.create(ApiService.class);
			}
		}
		return apiService;
	}

}
