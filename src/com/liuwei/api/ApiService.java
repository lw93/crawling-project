package com.liuwei.api;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiService {
	
	@Headers({
			"Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
			"Accept-Language:zh-CN,zh;q=0.8",
			"Cache-Control:max-age=0",
			"Connection:keep-alive",
			"Host:c.m.163.com",
			"Upgrade-Insecure-Requests:1",
			"User-Agent:Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Mobile Safari/537.36" })
	// 获取主页内容
	@GET("nc/article/list/{news_id}/{offset}-20.html")
	Flowable<String> getNews(@Path("news_id") String news_id,@Path("offset") int page);

	@Headers({
		"Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
		"Accept-Language:zh-CN,zh;q=0.8",
		"Cache-Control:max-age=0",
		"Connection:keep-alive",
		"Host:c.m.163.com",
		"Upgrade-Insecure-Requests:1",
		"User-Agent:Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Mobile Safari/537.36" })
	// 获取详情页内容
	@GET("nc/article/{doc_id}/full.html")
	Flowable<String> getDetailNews(@Path("doc_id") String doc_id);
	
	
	
	//@Streaming
	//下载图片		"Accept-Encoding:gzip, deflate",
	@GET()
	@Headers({
		"Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
		"Accept-Language:zh-CN,zh;q=0.8",
		"Cache-Control:max-age=0",
		"Connection:keep-alive",
		"Host:cms-bucket.nosdn.127.net",
		"If-None-Match:527d6f700817b6aaa7d9d73d5d395e8f",
		"Upgrade-Insecure-Requests:1",
		"User-Agent:Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Mobile Safari/537.36Name"
	})
	Flowable<ResponseBody> getNewsImg(@Url String imgUrl,@HeaderMap Map<String, Object> headerMap);
}
