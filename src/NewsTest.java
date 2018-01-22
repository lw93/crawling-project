import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DefaultSubscriber;

import java.util.List;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liuwei.comment.Constant;
import com.liuwei.comment.HttpManager;
import com.liuwei.comment.ThreadManager;
import com.liuwei.db.DBHelper;
import com.liuwei.pojo.BasePOJO;
import com.liuwei.pojo.DetailPOJO;
import com.liuwei.pojo.NewsPOJO;


/**
 * @Project: news-crawling
 * @Class: NewsTest.java
 * @Description: 
 * @Date: 2017年11月18日
 * @author liuwei5
 */
public class NewsTest {
	public static final Logger logger = LoggerFactory.getLogger(NewsTest.class);
	private static Executor threadManager = ThreadManager.getThreadManager(1);
	public static void main(String[] args) throws InterruptedException {
		
		//getNewsTest(400);
		for (int i = 0; i <= 420;) {
			getNewsTest(i);
			Thread.sleep(1000);
			i += 10;
		}
	}
	
	private static void getFirstNewsTest(){
//		DBHelper db = new DBHelper();
//		NewsPOJO newsPOJO = db.searchFirstNews();
//		System.out.println(newsPOJO.getTitle());
//		System.out.println(newsPOJO.getDocId());
	}
	
	private static void getNewsTest(int i){
		HttpManager.getClient().getNews("T1348649776727", i)
		.subscribeOn(Schedulers.from(threadManager))
		.subscribe(new DefaultSubscriber<String>() {

			@Override
			public void onComplete() {
				logger.info("======onComplete=======");
			}

			@Override
			public void onError(Throwable arg0) {
				logger.info("======onError=======");
				logger.error("{}", arg0.toString());
			}

			@Override
			public void onNext(String arg0) {
				logger.info("======onNext=======");
				if (null != arg0 && !"".equals(arg0)) {
					JSONObject jsonObject = JSON.parseObject(arg0);
					 JSONArray jsonArray = jsonObject.getJSONArray("T1348649776727");
					List<BasePOJO> objDTO = JSON.parseArray(jsonArray.toString(),BasePOJO.class);    
					 logger.debug("{}",objDTO);
					for (BasePOJO basePOJO : objDTO) {
//						BasePOJO basePoJo = JSON.parseObject(arg0, BasePOJO.class);
						logger.debug(basePOJO.getTitle());
					}
					
				} else {
					logger.debug(Constant.NONE_CONTENT);
				}
				logger.info("======onNext=======");
			}

		});
		HttpManager.getClient().getDetailNews("D3HAF4VV001680P9")
		.subscribeOn(Schedulers.from(threadManager))
		.subscribe(new DefaultSubscriber<String>() {

			@Override
			public void onComplete() {
				logger.info("======onComplete=======");
			}

			@Override
			public void onError(Throwable arg0) {
				logger.info("======onError=======");
				logger.error("{}", arg0.toString());
			}

			@Override
			public void onNext(String arg0) {
				logger.info("======onNext=======");
				if (null != arg0 && !"".equals(arg0)) {
					JSONObject jsonObject = JSON.parseObject(arg0);
					JSONObject detailDTO = jsonObject.getJSONObject("D3HAF4VV001680P9");
					DetailPOJO detailPojo = JSON.parseObject(detailDTO.toString(),DetailPOJO.class);
					logger.debug("json:" + detailPojo.getBody());
					
				} else {
					logger.debug(Constant.NONE_CONTENT);
				}
				logger.info("======onNext=======");
			}
		});
	}
	
}
