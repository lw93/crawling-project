import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DefaultSubscriber;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liuwei.comment.HttpManager;


/**
 * @Project: news-crawling
 * @Class: ImgTest.java
 * @Description: 
 * @Date: 2017年12月9日
 * @author liuwei5
 */
public class ImgTest {
	public static final Logger logger = LoggerFactory.getLogger(ImgTest.class);

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub  http://cms-bucket.nosdn.127.net/f9e5c7e0e3c94b868dbd63278f36ba8b20171201101921.jpeg
		// http://www.hztarena.org/mbaidu/image/index_13.png
		//nameImg("http://cms-bucket.nosdn.127.net/f9e5c7e0e3c94b868dbd63278f36ba8b20171201101921.jpeg");
		
		
		String url = "http://cms-bucket.nosdn.127.net/f9e5c7e0e3c94b868dbd63278f36ba8b20171201101921.jpeg";
		Map<String, Object> header = new HashMap<String, Object>(1);
		header.put("If-Modified-Since", new Date().toGMTString());
		HttpManager.getClient().getNewsImg(url,header)
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
				logger.info("======onComplete=======");
			}

			@Override
			public void onError(Throwable arg0) {
				logger.info("======saveNewsImg onError=======");
				logger.error("{}", arg0.toString());
			}

			@Override
			public void onNext(byte[] arg0) {
				logger.info("======onNext Download Image  Start =======");
				logger.debug(arg0.toString());
//				File file = new File("D:/img_"+System.currentTimeMillis()+".png");
//				
//				if (file.exists()) {
//					file.delete();
//				}
//				try {
//					FileOutputStream foStream = new FileOutputStream(file);
//					foStream.write(arg0);
//					foStream.close();
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				newsPOJO.setLocalImg(CompressUtil.compress(arg0));
				logger.info("======onNext Download Image End=======");

			}
		});
	}
	
	
	private static void nameImg(String requestUrl) throws IOException {
		// 建立连接
				URL url = new URL(requestUrl);
				HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
				httpUrlConn.setDoInput(true);
				httpUrlConn.setRequestMethod("GET");
				httpUrlConn.setRequestProperty("Accept", "image/webp,image/*,*/*;q=0.8");
				httpUrlConn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
				httpUrlConn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
				httpUrlConn.setRequestProperty("Cache-Control", "max-age=0");
				httpUrlConn.setRequestProperty("Connection:", "keep-alive");
//				httpUrlConn.setRequestProperty("Host", "t12.baidu.com");
//				httpUrlConn.setRequestProperty("Referer","http://news.baidu.com/ns?word="+keyword+"");
				httpUrlConn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36");

				// 获取输入流
				InputStream inputStream = httpUrlConn.getInputStream();
				
				File file = new File("D:/img_"+System.currentTimeMillis()+".png");
								
				if  (file .exists())      
				{       
					file.delete();
				  // Log.WLog("图片已存在！");
				} 
				
				FileOutputStream out = new FileOutputStream(file); // 输出的文件流
				byte[] bs = new byte[1024];
				// 读取到的数据长度
				int len;
				// 开始读取
				while ((len = inputStream.read                     (bs)) != -1) {
					out.write(bs, 0, len);
				}
				// 完毕，关闭所有链接
				out.close();
				inputStream.close();
	}

}
