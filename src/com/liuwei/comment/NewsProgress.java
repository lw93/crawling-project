package com.liuwei.comment;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DefaultSubscriber;

import java.util.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liuwei.pojo.BasePOJO;
import com.liuwei.pojo.DetailPOJO;
import com.liuwei.pojo.NewsPOJO;
import com.liuwei.util.CompressUtil;
import com.liuwei.util.PropertiesUtil;

/**
 * @Project: news-crawling
 * @Class: NewsProgress.java
 * @Description: 线程爬取实现类
 * @Date: 2017年12月5日
 * @author liuwei5
 */
public class NewsProgress implements Runnable {

	public static final Logger logger = LoggerFactory.getLogger(NewsProgress.class);
	private volatile static String TOUTIAO_NEWS_DOCID = "";// 头条
	private volatile static String SHEHUI_NEWS_DOCID = "";// 社会
	private volatile static String HISTROY_NEWS_DOCID = "";// 历史
	private volatile static String DIANTAI_NEWS_DOCID = "";// 电台
	private volatile static String JUNSHI_NEWS_DOCID = "";// 军事
	private volatile static String HANKONHG_NEWS_DOCID = "";// 航空
	private volatile static String YAOWEN_NEWS_DOCID = "";// 要文
	private volatile static String YULE_NEWS_DOCID = "";// 娱乐
	private volatile static String YINGSHIGE_NEWS_DOCID = "";// 影视歌
	private volatile static String CAIJING_NEWS_DOCID = "";// 财经
	private volatile static String GUPIAO_NEWS_DOCID = "";// 股票
	private volatile static String CAIPIAO_NEWS_DOCID = "";// 彩票
	private volatile static String TIYU_NEWS_DOCID = "";// 体育
	private volatile static String KEJI_NEWS_DOCID = "";// 科技
	private volatile static String PHONE_NEWS_DOCID = "";// 手机
	private volatile static String SHUMA_NEWS_DOCID = "";// 数码
	private volatile static String ZHINENG_NEWS_DOCID = "";// 智能
	private volatile static String HAPPYTIME_NEWS_DOCID = "";// 轻松一刻
	private volatile static String DUJIA_NEWS_DOCID = "";// 独家
	private volatile static String CAR_NEWS_DOCID = "";// 汽车
	private volatile static String HOUSE_NEWS_DOCID = "";// 房产
	private volatile static String JIAJU_NEWS_DOCID = "";// 家居
	private volatile static String GAME_NEWS_DOCID = "";// 游戏
	private volatile static String TRAVEL_NEWS_DOCID = "";// 旅游
	private volatile static String HEALTHY_NEWS_DOCID = "";// 健康
	private volatile static String EDUCATION_NEWS_DOCID = "";// 教育
	private volatile static String FANSHION_NEWS_DOCID = "";// 时尚
	private volatile static String WOMEN_NEWS_DOCID = "";// 女人
	private volatile static String ZHENWU_NEWS_DOCID = "";// 政务
	private volatile static String ART_NEWS_DOCID = "";// 艺术
	private volatile static BasePOJO mBasePOJO;
	private ArrayBlockingQueue<Vector<NewsPOJO>> blockQueue;
	private String newsId;
	public NewsProgress(ArrayBlockingQueue<Vector<NewsPOJO>> queue,String newsID) {
			this.blockQueue = queue;
			this.newsId = newsID;
	}
	
	@Override
	public void run() {
		for (int i = 0; i <= 420;) {
			parseNewsList(newsId, i);
			i+=20;
		}
	}
	/**
	 * 获取新闻列表数据
	 */
	private void parseNewsList(final String newsID, final int page) {
		HttpManager.getClient().getNews(newsID, page)
				 .subscribeOn(Schedulers.newThread())
				.subscribe(new DefaultSubscriber<String>() {
					@Override
					public void onComplete() {
						logger.info("======onComplete=======");
					}

					@Override
					public void onError(Throwable arg0) {
						logger.info("======parseNewsList onError=======");
						logger.error("{}", arg0.toString());
					}

					@Override
					public void onNext(String arg0) {
						logger.info("======onNext Parse NewsList Start =======");
						if (null != arg0 && !"".equals(arg0)) {
							int temp = page;
							//logger.debug("第temp:"+temp +"/page:"+page+"页:{}",arg0);
							JSONObject jsonObject = JSON.parseObject(arg0);
							JSONArray jsonArray = jsonObject.getJSONArray(newsID);
							List<BasePOJO> objDTO = JSON.parseArray(jsonArray.toString(), BasePOJO.class);
							logger.debug("第temp:"+temp +"/page:"+page+"页--"+newsID+"--数量:{}",objDTO.size());
							if (objDTO != null && objDTO.size() > 0) {
								validateFirst(newsID,objDTO,temp);
								Vector<NewsPOJO> vector = new Vector<NewsPOJO>(26);
								for (BasePOJO basePOJO : objDTO) {
									NewsPOJO newsPOJO = new NewsPOJO();
									newsPOJO.setNewsId(newsID);
									if (basePOJO.getImgextra() != null 
											&& basePOJO.getImgextra().size() > 0) {
										newsPOJO.setImgExtra(JSON.toJSONString(basePOJO.getImgextra()));
									}
									newsPOJO.setTitle(basePOJO.getTitle());
									newsPOJO.setDocId(basePOJO.getDocid());
									newsPOJO.setShortCentent(basePOJO.getDigest());
									newsPOJO.setNewsResouce(basePOJO.getSource());
									newsPOJO.setImgUrl(basePOJO.getImgsrc());
									newsPOJO.setNewsType(PropertiesUtil.getNewsType(newsID));
									newsPOJO.setProductTime(Timestamp.valueOf(basePOJO.getPtime()));
									newsPOJO.setCreateTime(new Timestamp(System.currentTimeMillis()));
									parseDetail(basePOJO.getDocid(), newsPOJO);
									saveNewsImg(basePOJO.getImgsrc(), newsPOJO);
									vector.add(newsPOJO);
								}
								try {
									blockQueue.put(vector);
								} catch (InterruptedException e) {
									logger.error(e.toString());
									logger.info("======队列添加对象出现错误!!!=======");
								}
							}
							
						} else {
							logger.debug(Constant.NONE_CONTENT);
						}
						logger.info("======onNext Parse NewsList End=======");
					}
				});
	}

	/**
	 * 获取新闻详情数据
	 */
	private void parseDetail(final String docId, final NewsPOJO newsPOJO) {
		HttpManager.getClient().getDetailNews(docId)
				 .subscribeOn(Schedulers.newThread())
				.subscribe(new DefaultSubscriber<String>() {

					@Override
					public void onComplete() {
						logger.info("======parseDetail onComplete=======");
					}

					@Override
					public void onError(Throwable arg0) {
						logger.info("======parseDetail onError=======");
						logger.error(docId+":{}", arg0.toString());
					}

					@Override
					public void onNext(String arg0) {
						logger.info("======onNext ParseDetail Start =======");
						if (null != arg0 && !"".equals(arg0)) {
							logger.debug("===ParseDetail==={}",arg0);
							JSONObject jsonObject = JSON.parseObject(arg0);
							JSONObject detailDTO = jsonObject.getJSONObject(docId);
							DetailPOJO detailPojo = JSON.parseObject(detailDTO.toString(), DetailPOJO.class);
							newsPOJO.setBody(detailPojo.getBody());
							newsPOJO.setRelativeKey(detailPojo.getDkeys());
							newsPOJO.setEditor(detailPojo.getEc());
						} else {
							logger.info(Constant.NONE_CONTENT);
						}
						logger.info("======onNext ParseDetail End=======");
					}
				});
	}

	/**
	 * 下载新闻对应的图片
	 */
	private void saveNewsImg(final String url, final NewsPOJO newsPOJO) {
		Map<String, Object> headerMap = new HashMap<String, Object>(1);
		headerMap.put("If-Modified-Since", new Date().toGMTString());
		HttpManager.getClient().getNewsImg(url,headerMap)
				.subscribeOn(Schedulers.newThread())
				.map(new Function<ResponseBody, byte[]>() {

					@Override
					public byte[] apply(ResponseBody arg0) throws Exception {
						// TODO Auto-generated method stub
						return arg0.bytes();
					}
				})
				.subscribe(new DefaultSubscriber<byte[]>() {

					@Override
					public void onComplete() {
						logger.info("======saveNewsImg onComplete=======");
					}

					@Override
					public void onError(Throwable arg0) {
						logger.info("======saveNewsImg onError=======");
						logger.error(url+":{}", arg0.toString());
					}

					@Override
					public void onNext(byte[] arg0) {
						logger.info("======onNext saveNewsImg  Start =======");
						newsPOJO.setLocalImg(CompressUtil.compress(arg0));
						logger.info("======onNext saveNewsImg End=======");

					}
				});
	}
	
	private void validateFirst(String newsId, List<BasePOJO> list, int tempPage) {
		if (tempPage == 0) {
			mBasePOJO = list.get(0);
			if (Constant.PARSER_IDS[0].equals(newsId)) {// 头条
				if (TOUTIAO_NEWS_DOCID.equals(mBasePOJO.getDocid())) {
					logger.debug("TOUTIAO_NEWS_DOCID cache:{}",
							TOUTIAO_NEWS_DOCID);
					return;
				}
				TOUTIAO_NEWS_DOCID = mBasePOJO.getDocid();
				logger.debug("TOUTIAO_NEWS_DOCID:{}", TOUTIAO_NEWS_DOCID);
			} else if (Constant.PARSER_IDS[1].equals(newsId)) {// 社会
				if (SHEHUI_NEWS_DOCID.equals(mBasePOJO.getDocid())) {
					logger.debug("SHEHUI_NEWS_DOCID cache:{}",
							SHEHUI_NEWS_DOCID);
					return;
				}
				SHEHUI_NEWS_DOCID = mBasePOJO.getDocid();
				logger.debug("SHEHUI_NEWS_DOCID:{}", SHEHUI_NEWS_DOCID);
			} else if (Constant.PARSER_IDS[2].equals(newsId)) {// 历史
				if (HISTROY_NEWS_DOCID.equals(mBasePOJO.getDocid())) {
					logger.debug("HISTROY_NEWS_DOCID cache:{}",
							HISTROY_NEWS_DOCID);
					return;
				}
				HISTROY_NEWS_DOCID = mBasePOJO.getDocid();
				logger.debug("HISTROY_NEWS_DOCID:{}", HISTROY_NEWS_DOCID);
			} else if (Constant.PARSER_IDS[3].equals(newsId)) {// 电台
				if (DIANTAI_NEWS_DOCID.equals(mBasePOJO.getDocid())) {
					logger.debug("DIANTAI_NEWS_DOCID cache:{}",
							DIANTAI_NEWS_DOCID);
					return;
				}
				DIANTAI_NEWS_DOCID = mBasePOJO.getDocid();
				logger.debug("DIANTAI_NEWS_DOCID:{}", DIANTAI_NEWS_DOCID);
			} else if (Constant.PARSER_IDS[4].equals(newsId)) {// 军事
				if (JUNSHI_NEWS_DOCID.equals(mBasePOJO.getDocid())) {
					logger.debug("JUNSHI_NEWS_DOCID cache:{}",
							JUNSHI_NEWS_DOCID);
					return;
				}
				JUNSHI_NEWS_DOCID = mBasePOJO.getDocid();
				logger.debug("JUNSHI_NEWS_DOCID:{}", JUNSHI_NEWS_DOCID);
			} else if (Constant.PARSER_IDS[5].equals(newsId)) {// 航空
				if (HANKONHG_NEWS_DOCID.equals(mBasePOJO.getDocid())) {
					logger.debug("HANKONHG_NEWS_DOCID cache:{}",
							HANKONHG_NEWS_DOCID);
					return;
				}
				HANKONHG_NEWS_DOCID = mBasePOJO.getDocid();
				logger.debug("HANKONHG_NEWS_DOCID:{}", HANKONHG_NEWS_DOCID);
			} else if (Constant.PARSER_IDS[6].equals(newsId)) {// 要文
				if (YAOWEN_NEWS_DOCID.equals(mBasePOJO.getDocid())) {
					logger.debug("YAOWEN_NEWS_DOCID cache:{}",
							YAOWEN_NEWS_DOCID);
					return;
				}
				YAOWEN_NEWS_DOCID = mBasePOJO.getDocid();
				logger.debug("YAOWEN_NEWS_DOCID:{}", YAOWEN_NEWS_DOCID);
			} else if (Constant.PARSER_IDS[7].equals(newsId)) {// 娱乐
				if (YULE_NEWS_DOCID.equals(mBasePOJO.getDocid())) {
					logger.debug("YULE_NEWS_DOCID cache:{}", YULE_NEWS_DOCID);
					return;
				}
				YULE_NEWS_DOCID = mBasePOJO.getDocid();
				logger.debug("YULE_NEWS_DOCID:{}", YULE_NEWS_DOCID);
			} else if (Constant.PARSER_IDS[8].equals(newsId)) {// 影视歌
				if (YINGSHIGE_NEWS_DOCID.equals(mBasePOJO.getDocid())) {
					logger.debug("YINGSHIGE_NEWS_DOCID cache:{}",
							YINGSHIGE_NEWS_DOCID);
					return;
				}
				YINGSHIGE_NEWS_DOCID = mBasePOJO.getDocid();
				logger.debug("YINGSHIGE_NEWS_DOCID:{}", YINGSHIGE_NEWS_DOCID);
			} else if (Constant.PARSER_IDS[9].equals(newsId)) {// 财经
				if (CAIJING_NEWS_DOCID.equals(mBasePOJO.getDocid())) {
					logger.debug("CAIJING_NEWS_DOCID cache:{}",
							CAIJING_NEWS_DOCID);
					return;
				}
				CAIJING_NEWS_DOCID = mBasePOJO.getDocid();
				logger.debug("CAIJING_NEWS_DOCID:{}", CAIJING_NEWS_DOCID);
			} else if (Constant.PARSER_IDS[10].equals(newsId)) {// 股票
				if (GUPIAO_NEWS_DOCID.equals(mBasePOJO.getDocid())) {
					logger.debug("GUPIAO_NEWS_DOCID cache:{}",
							GUPIAO_NEWS_DOCID);
					return;
				}
				GUPIAO_NEWS_DOCID = mBasePOJO.getDocid();
				logger.debug("GUPIAO_NEWS_DOCID:{}", GUPIAO_NEWS_DOCID);
			} else if (Constant.PARSER_IDS[11].equals(newsId)) {// 彩票
				if (CAIPIAO_NEWS_DOCID.equals(mBasePOJO.getDocid())) {
					logger.debug("CAIPIAO_NEWS_DOCID cache:{}",
							CAIPIAO_NEWS_DOCID);
					return;
				}
				CAIPIAO_NEWS_DOCID = mBasePOJO.getDocid();
				logger.debug("CAIPIAO_NEWS_DOCID:{}", CAIPIAO_NEWS_DOCID);
			}
		} else if (Constant.PARSER_IDS[12].equals(newsId)) {// 体育
			if (TIYU_NEWS_DOCID.equals(mBasePOJO.getDocid())) {
				logger.debug("TIYU_NEWS_DOCID cache:{}", TIYU_NEWS_DOCID);
				return;
			}
			TIYU_NEWS_DOCID = mBasePOJO.getDocid();
			logger.debug("TIYU_NEWS_DOCID:{}", TIYU_NEWS_DOCID);
		} else if (Constant.PARSER_IDS[13].equals(newsId)) {// 科技
			if (KEJI_NEWS_DOCID.equals(mBasePOJO.getDocid())) {
				logger.debug("KEJI_NEWS_DOCID cache:{}", KEJI_NEWS_DOCID);
				return;
			}
			SHEHUI_NEWS_DOCID = mBasePOJO.getDocid();
			logger.debug("KEJI_NEWS_DOCID:{}", KEJI_NEWS_DOCID);
		} else if (Constant.PARSER_IDS[14].equals(newsId)) {// 手机
			if (PHONE_NEWS_DOCID.equals(mBasePOJO.getDocid())) {
				logger.debug("PHONE_NEWS_DOCID cache:{}", PHONE_NEWS_DOCID);
				return;
			}
			PHONE_NEWS_DOCID = mBasePOJO.getDocid();
			logger.debug("PHONE_NEWS_DOCID:{}", PHONE_NEWS_DOCID);
		} else if (Constant.PARSER_IDS[15].equals(newsId)) {// 数码
			if (SHUMA_NEWS_DOCID.equals(mBasePOJO.getDocid())) {
				logger.debug("SHUMA_NEWS_DOCID cache:{}", SHUMA_NEWS_DOCID);
				return;
			}
			SHUMA_NEWS_DOCID = mBasePOJO.getDocid();
			logger.debug("SHUMA_NEWS_DOCID:{}", SHUMA_NEWS_DOCID);
		} else if (Constant.PARSER_IDS[16].equals(newsId)) {// 智能
			if (ZHINENG_NEWS_DOCID.equals(mBasePOJO.getDocid())) {
				logger.debug("ZHINENG_NEWS_DOCID cache:{}", ZHINENG_NEWS_DOCID);
				return;
			}
			ZHINENG_NEWS_DOCID = mBasePOJO.getDocid();
			logger.debug("ZHINENG_NEWS_DOCID:{}", ZHINENG_NEWS_DOCID);
		} else if (Constant.PARSER_IDS[17].equals(newsId)) {// 轻松一刻
			if (HAPPYTIME_NEWS_DOCID.equals(mBasePOJO.getDocid())) {
				logger.debug("HAPPYTIME_NEWS_DOCID cache:{}",
						HAPPYTIME_NEWS_DOCID);
				return;
			}
			HAPPYTIME_NEWS_DOCID = mBasePOJO.getDocid();
			logger.debug("HAPPYTIME_NEWS_DOCID:{}", HAPPYTIME_NEWS_DOCID);
		} else if (Constant.PARSER_IDS[18].equals(newsId)) {// 独家
			if (DUJIA_NEWS_DOCID.equals(mBasePOJO.getDocid())) {
				logger.debug("DUJIA_NEWS_DOCID cache:{}", DUJIA_NEWS_DOCID);
				return;
			}
			DUJIA_NEWS_DOCID = mBasePOJO.getDocid();
			logger.debug("DUJIA_NEWS_DOCID:{}", DUJIA_NEWS_DOCID);
		} else if (Constant.PARSER_IDS[19].equals(newsId)) {// 汽车
			if (CAR_NEWS_DOCID.equals(mBasePOJO.getDocid())) {
				logger.debug("CAR_NEWS_DOCID cache:{}", CAR_NEWS_DOCID);
				return;
			}
			CAR_NEWS_DOCID = mBasePOJO.getDocid();
			logger.debug("CAR_NEWS_DOCID:{}", CAR_NEWS_DOCID);
		} else if (Constant.PARSER_IDS[20].equals(newsId)) {// 房产
			if (HOUSE_NEWS_DOCID.equals(mBasePOJO.getDocid())) {
				logger.debug("HOUSE_NEWS_DOCID cache:{}", HOUSE_NEWS_DOCID);
				return;
			}
			HOUSE_NEWS_DOCID = mBasePOJO.getDocid();
			logger.debug("HOUSE_NEWS_DOCID:{}", HOUSE_NEWS_DOCID);
		} else if (Constant.PARSER_IDS[21].equals(newsId)) {// 家居
			if (JIAJU_NEWS_DOCID.equals(mBasePOJO.getDocid())) {
				logger.debug("JIAJU_NEWS_DOCID cache:{}", JIAJU_NEWS_DOCID);
				return;
			}
			JIAJU_NEWS_DOCID = mBasePOJO.getDocid();
			logger.debug("JIAJU_NEWS_DOCID:{}", JIAJU_NEWS_DOCID);
		} else if (Constant.PARSER_IDS[22].equals(newsId)) {// 游戏
			if (GAME_NEWS_DOCID.equals(mBasePOJO.getDocid())) {
				logger.debug("GAME_NEWS_DOCID cache:{}", GAME_NEWS_DOCID);
				return;
			}
			GAME_NEWS_DOCID = mBasePOJO.getDocid();
			logger.debug("GAME_NEWS_DOCID:{}", GAME_NEWS_DOCID);
		} else if (Constant.PARSER_IDS[23].equals(newsId)) {// 旅游
			if (tempPage == 0) {
				if (TRAVEL_NEWS_DOCID.equals(mBasePOJO.getDocid())) {
					logger.debug("TRAVEL_NEWS_DOCID cache:{}",
							TRAVEL_NEWS_DOCID);
					return;
				}
				TRAVEL_NEWS_DOCID = mBasePOJO.getDocid();
				logger.debug("TRAVEL_NEWS_DOCID:{}", TRAVEL_NEWS_DOCID);
			} else if (Constant.PARSER_IDS[24].equals(newsId)) {// 健康
				if (HEALTHY_NEWS_DOCID.equals(mBasePOJO.getDocid())) {
					logger.debug("HEALTHY_NEWS_DOCID cache:{}",
							HEALTHY_NEWS_DOCID);
					return;
				}
				HEALTHY_NEWS_DOCID = mBasePOJO.getDocid();
				logger.debug("HEALTHY_NEWS_DOCID:{}", HEALTHY_NEWS_DOCID);
			} else if (Constant.PARSER_IDS[25].equals(newsId)) {// 教育
				if (EDUCATION_NEWS_DOCID.equals(mBasePOJO.getDocid())) {
					logger.debug("EDUCATION_NEWS_DOCID cache:{}",
							EDUCATION_NEWS_DOCID);
					return;
				}
				EDUCATION_NEWS_DOCID = mBasePOJO.getDocid();
				logger.debug("EDUCATION_NEWS_DOCID:{}", EDUCATION_NEWS_DOCID);
			} else if (Constant.PARSER_IDS[26].equals(newsId)) {// 时尚
				if (FANSHION_NEWS_DOCID.equals(mBasePOJO.getDocid())) {
					logger.debug("FANSHION_NEWS_DOCID cache:{}",
							FANSHION_NEWS_DOCID);
					return;
				}
				FANSHION_NEWS_DOCID = mBasePOJO.getDocid();
				logger.debug("FANSHION_NEWS_DOCID:{}", FANSHION_NEWS_DOCID);
			} else if (Constant.PARSER_IDS[27].equals(newsId)) {// 女人
				if (WOMEN_NEWS_DOCID.equals(mBasePOJO.getDocid())) {
					logger.debug("WOMEN_NEWS_DOCID cache:{}", WOMEN_NEWS_DOCID);
					return;
				}
				WOMEN_NEWS_DOCID = mBasePOJO.getDocid();
				logger.debug("WOMEN_NEWS_DOCID:{}", WOMEN_NEWS_DOCID);
			} else if (Constant.PARSER_IDS[28].equals(newsId)) {// 政务
				if (ZHENWU_NEWS_DOCID.equals(mBasePOJO.getDocid())) {
					logger.debug("ZHENWU_NEWS_DOCID cache:{}",
							ZHENWU_NEWS_DOCID);
					return;
				}
				ZHENWU_NEWS_DOCID = mBasePOJO.getDocid();
				logger.debug("ZHENWU_NEWS_DOCID:{}", ZHENWU_NEWS_DOCID);
			} else if (Constant.PARSER_IDS[29].equals(newsId)) {// 艺术
				if (ART_NEWS_DOCID.equals(mBasePOJO.getDocid())) {
					logger.debug("ART_NEWS_DOCID cache:{}", ART_NEWS_DOCID);
					return;
				}
				ART_NEWS_DOCID = mBasePOJO.getDocid();
				logger.debug("ART_NEWS_DOCID:{}", ART_NEWS_DOCID);
			}
		}
	}
}
