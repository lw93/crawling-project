//package com.liuwei.util;
//
//import java.net.CookieManager;
//import java.net.UnknownHostException;
//import java.util.Iterator;
//import java.util.List;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.NoSuchElementException;
//import org.openqa.selenium.TimeoutException;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.remote.ErrorHandler.UnknownServerException;
//import org.openqa.selenium.support.ui.ExpectedCondition;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.liuwei.comment.Constant;
//import com.liuwei.pojo.BasePoJo;
//
//public class Parse {
//
//	public static final Logger logger = LoggerFactory.getLogger(Parse.class);
//	public static WebDriver driver;
//
//	public static void destroyChrome() {
//		if (driver != null) {
//			driver.quit();
//			driver = null;
//		}
//	}
//
//	public synchronized static void openChrome() {
////		System.setProperty("webdriver.chrome.driver", "E:\\chromedriver.exe");
////		ChromeOptions options = new ChromeOptions();
////		options.setBinary("D:\\Chrome\\Application\\chrome.exe");
////		options.addArguments("start-maximized");
//		driver = new FirefoxDriver();
//	}
//
//	public synchronized static int ParsePageNum(String html) {
//		Document document = Jsoup.parse(html);
//		Elements navLinks = document.getElementsByClass("nav-links");
//		logger.debug(navLinks.toString());
//		Elements spanElement = navLinks
//				.select("span[class=\"page-numbers dots\"]");
//		logger.debug(spanElement.toString());
//		Elements aNavLinks = navLinks.select("a[class=\"page-numbers\"]");
//		if (aNavLinks.isEmpty()) {
//			logger.info(Constant.NONE_TAG);
//			return 0;
//		}
//		int pageNum = 0;
//		if (!spanElement.isEmpty()) {
//			pageNum = Integer.parseInt(aNavLinks.get(3).text());
//		} else {
//			pageNum = Integer.parseInt(aNavLinks.get(2).text());
//		}
//		return pageNum;
//	}
//
//	// 一页一页解析
//	public synchronized static void parseHtml(String html) {
//		Document document = Jsoup.parse(html);
//		Element section = document.getElementById("primary");
//		if (null == section) {
//			logger.info(Constant.NONE_TAG);
//			return;
//		}
//		Elements elements = section.getElementsByTag("article");
//		if (null == elements && elements.size() < 1) {
//			logger.info(Constant.NONE_TAG);
//			return;
//		}
//		logger.debug("======{}======", elements.toString());
//		int elementSize = elements.size();
//		for (int i = 0; i < elementSize; i++) {
//			logger.info("=======Parse start======");
//			// 获取图片资源
//			Elements figureElements = elements.get(i).getElementsByClass(
//					"thumbnail");
//			if (figureElements.isEmpty()) {
//				logger.info(Constant.NONE_TAG);
//				continue;
//			}
//			logger.debug("======{}======", figureElements.toString());
//
//			Elements imgElements = figureElements.select("img");
//			if (imgElements.isEmpty()) {
//				logger.info(Constant.NONE_TAG);
//				continue;
//			}
//			// TODO imageurl
//			String imgUrl = imgElements.attr("src");
//			if (imgUrl.isEmpty()) {
//				logger.info(Constant.NONE_TAG);
//				continue;
//			}
//			logger.info("======{}======", imgUrl.toString());
//
//			BasePoJo basePoJo = new BasePoJo();
//			basePoJo.setImageUrl(imgUrl);
//			// 获取视频类型资源
//			Elements spanElements = figureElements.select("span");
//			if (spanElements.isEmpty()) {
//				logger.info(Constant.NONE_TAG);
//				continue;
//			}
//			Elements typeElements = spanElements.select("a");
//			if (typeElements.isEmpty()) {
//				logger.info(Constant.NONE_TAG);
//				continue;
//			}
//
//			// TODO videotype
//			String videoType = typeElements.text();
//			if (videoType.isEmpty()) {
//				logger.info(Constant.NONE_TAG);
//				continue;
//			}
//			logger.info("======{}======", videoType.toString());
//
//			basePoJo.setVideoType(videoType);
//
//			// 获取标题资源
//			Elements headerElements = elements.get(i).getElementsByClass(
//					"entry-header");
//			if (headerElements.isEmpty()) {
//				logger.info(Constant.NONE_TAG);
//				continue;
//			}
//			Elements h2 = headerElements.select("h2");
//			if (h2.isEmpty()) {
//				logger.info(Constant.NONE_TAG);
//				continue;
//			}
//			Elements entry_title = h2.select("a");
//			if (entry_title.isEmpty()) {
//				logger.info(Constant.NONE_TAG);
//				continue;
//			}
//			// TODO title
//			String title = entry_title.text();
//			if (title.isEmpty()) {
//				logger.info(Constant.NONE_TAG);
//				continue;
//			}
//			logger.info("======{}======", title);
//
//			basePoJo.setTitle(title);
//
//			// 获取短文简介
//			Elements archive_content = elements.get(i).getElementsByClass(
//					"archive-content");
//			if (archive_content.isEmpty()) {
//				logger.info(Constant.NONE_TAG);
//				continue;
//			}
//			// TODO shortconetent
//			String shortContent = archive_content.text();
//			if (shortContent.isEmpty()) {
//				logger.info(Constant.NONE_TAG);
//				continue;
//			}
//			logger.info("======{}======", shortContent);
//
//			basePoJo.setShortDesc(shortContent);
//
//			// TODO 具体内容解析
//			String detailUrl = entry_title.attr("href");
//			if (detailUrl.isEmpty()) {
//				logger.info(Constant.NONE_TAG);
//				continue;
//			}
//			logger.debug("======{}======", detailUrl);
//
//			parseDetail(detailUrl);
//			// TODO 添加bean
//			logger.info("=======Parse end======");
//		}
//
//	}
//
//	// 解析具体内容
//	private synchronized static void parseDetail(String detailUrl) {
//
//		try {
//			// 开始解析数量
//			driver.get(detailUrl);
//			// 识别元素时的超时时间
//			// driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//			// 页面加载时的超时时间
//			// driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
//			// 异步脚本的超时时间　　
//			// driver.manage().timeouts().setScriptTimeout(20,
//			// TimeUnit.SECONDS);
//			WebDriverWait wait = new WebDriverWait(driver, 20, 2000);
//			wait.until(new ExpectedCondition<Boolean>() {
//				public Boolean apply(WebDriver webDriver) {
//					logger.debug("Searching ...");
//					return webDriver.findElement(By.id("main")).isDisplayed();
//				}
//			});
//			WebElement elementDiv = driver.findElement(By
//					.className("single-content"));
//			if (null == elementDiv) {
//				logger.info(Constant.NONE_TAG);
//				return;
//			}
//			List<WebElement> h2Elements = elementDiv.findElements(By
//					.tagName("h2"));
//			if (null == h2Elements && h2Elements.size() < 1) {
//				logger.info(Constant.NONE_TAG);
//				return;
//			}
//			logger.debug("======h2Element======" + h2Elements.toString());
//
//			// TODO
//			if (h2Elements.size() < 3) {
//				// 影片名称
//				String videoTitle = h2Elements.get(0).getText();
//				if (videoTitle == null || "".equals(videoTitle)) {
//					logger.info(Constant.NONE_TAG);
//					return;
//				}
//				logger.info("======videoTitle======" + videoTitle);
//
//				if (h2Elements.size() > 1) {
//					// 影片内容标题
//					String videoContentTitle = h2Elements.get(1).getText();
//					if (videoContentTitle == null
//							|| "".equals(videoContentTitle)) {
//						logger.info(Constant.NONE_TAG);
//						return;
//					}
//					logger.info("======videoContentTitle======"
//							+ videoContentTitle);
//
//				}
//			} else {
//				// 影片名称
//				String videoTitle = h2Elements.get(1).getText();
//				if (videoTitle == null || "".equals(videoTitle)) {
//					logger.info(Constant.NONE_TAG);
//					return;
//				}
//				logger.info("======videoTitle======" + videoTitle);
//
//				// 影片内容标题
//				String videoContentTitle = h2Elements.get(2).getText();
//				if (videoContentTitle == null || "".equals(videoContentTitle)) {
//					logger.info(Constant.NONE_TAG);
//					return;
//				}
//				logger.info("======videoContentTitle======" + videoContentTitle);
//
//			}
//
//			List<WebElement> pElements = elementDiv.findElements(By
//					.tagName("p"));
//			int elementP = pElements.size();
//			if (elementP < 1) {
//				logger.info(Constant.NONE_TAG);
//				return;
//			}
//			logger.debug("======pElements======" + pElements.toString());
//
//			// TODO
//			// 判断第一个标签P是否是简介
//			String pString = pElements.get(0).getAttribute("outerHTML");
//			if (pString.contains("<img")) {
//				// 影片简介
//				String videoTitleDetail = pElements.get(1).getAttribute(
//						"outerHTML");
//				if (videoTitleDetail == null || "".equals(videoTitleDetail)) {
//					logger.info(Constant.NONE_TAG);
//					return;
//				}
//				logger.info("======videoDetail======" + videoTitleDetail);
//
//				if (elementP > 2 && elementP < 4) {
//					// 影片内容简介
//					String ContentDetail = pElements.get(2).getAttribute(
//							"outerHTML");
//					if (ContentDetail == null || "".equals(ContentDetail)) {
//						logger.info(Constant.NONE_TAG);
//						return;
//					}
//					logger.info("======ContentDetail======" + ContentDetail);
//
//				} else if (elementP == 4) {
//					// 影片内容简介
//					String ContentDetail = pElements.get(2).getAttribute(
//							"outerHTML");
//					if (ContentDetail == null || "".equals(ContentDetail)) {
//						logger.info(Constant.NONE_TAG);
//						return;
//					}
//					logger.info("======ContentDetail======" + ContentDetail);
//
//					// 影片内容网盘和密码地址
//					String baiduUrl = pElements.get(3)
//							.getAttribute("outerHTML");
//					if (baiduUrl == null || "".equals(baiduUrl)) {
//						logger.info(Constant.NONE_TAG);
//						return;
//					}
//					if (baiduUrl.contains("<a")) {
//						logger.info("======baiduUrl======" + baiduUrl);
//					}
//				} else if (elementP > 4) {
//					// 影片内容简介
//					String ContentDetail = pElements.get(2).getAttribute(
//							"outerHTML");
//					if (ContentDetail == null || "".equals(ContentDetail)) {
//						logger.info(Constant.NONE_TAG);
//						return;
//					}
//					logger.info("======ContentDetail======" + ContentDetail);
//
//					// 影片内容网盘地址
//					String baiduUrl = pElements.get(3)
//							.getAttribute("outerHTML");
//					if (baiduUrl == null || "".equals(baiduUrl)) {
//						logger.info(Constant.NONE_TAG);
//						return;
//					}
//					if (baiduUrl.contains("<a")) {
//						logger.info("======baiduUrl======" + baiduUrl);
//					}
//
//					// 影片内容网盘提取密码
//					String baiduNetPass = pElements.get(4).getAttribute(
//							"outerHTML");
//					if (baiduNetPass == null || "".equals(baiduNetPass)) {
//						logger.info(Constant.NONE_TAG);
//						return;
//					}
//					if (baiduNetPass.contains("提取密码")) {
//						logger.info("======baiduNetPass======" + baiduNetPass);
//					}
//
//				}
//			} else {
//				String pString2 = pElements.get(1).getAttribute("outerHTML");
//				if (pString2.contains("<img")) {
//					if (elementP == 4) {
//						// 影片简介
//						String videoTitleDetail = pElements.get(2)
//								.getAttribute("outerHTML");
//						if (videoTitleDetail == null
//								|| "".equals(videoTitleDetail)) {
//							logger.info(Constant.NONE_TAG);
//							return;
//						}
//						logger.info("======videoDetail======"
//								+ videoTitleDetail);
//
//						// 影片内容简介
//						String ContentDetail = pElements.get(3).getAttribute(
//								"outerHTML");
//						if (ContentDetail == null || "".equals(ContentDetail)) {
//							logger.info(Constant.NONE_TAG);
//							return;
//						}
//						logger.info("======ContentDetail======" + ContentDetail);
//
//						// 影片内容网盘和密码地址
//						String baiduUrl = pElements.get(4).getAttribute(
//								"outerHTML");
//						if (baiduUrl == null || "".equals(baiduUrl)) {
//							logger.info(Constant.NONE_TAG);
//						}
//						if (baiduUrl.contains("<a")) {
//							logger.info("======baiduUrl======" + baiduUrl);
//						}
//					} else if (elementP == 5) {
//						// 影片简介
//						String videoTitleDetail = pElements.get(2)
//								.getAttribute("outerHTML");
//						if (videoTitleDetail == null
//								|| "".equals(videoTitleDetail)) {
//							logger.info(Constant.NONE_TAG);
//							return;
//						}
//						logger.info("======videoDetail======"
//								+ videoTitleDetail);
//
//						// 影片内容简介
//						String ContentDetail = pElements.get(3).getAttribute(
//								"outerHTML");
//						if (ContentDetail == null || "".equals(ContentDetail)) {
//							logger.info(Constant.NONE_TAG);
//							return;
//						}
//						logger.info("======ContentDetail======" + ContentDetail);
//
//						// 影片内容网盘和密码地址
//						String baiduUrl = pElements.get(4).getAttribute(
//								"outerHTML");
//						if (baiduUrl == null || "".equals(baiduUrl)) {
//							logger.info(Constant.NONE_TAG);
//						}
//						if (baiduUrl.contains("<a")) {
//							logger.info("======baiduUrl======" + baiduUrl);
//						}
//
//						// 影片内容网盘提取密码
//						String baiduNetPass = pElements.get(3).getAttribute(
//								"outerHTML");
//						if (baiduNetPass == null || "".equals(baiduNetPass)) {
//							logger.info(Constant.NONE_TAG);
//						}
//						if (baiduNetPass.contains("提取密码")) {
//							logger.info("======baiduNetPass======"
//									+ baiduNetPass);
//						}
//					}
//				} else {
//					// 影片简介
//					String videoTitleDetail = pElements.get(0).getAttribute(
//							"outerHTML");
//					if (videoTitleDetail == null || "".equals(videoTitleDetail)) {
//						logger.info(Constant.NONE_TAG);
//						return;
//					}
//					logger.info("======videoDetail======" + videoTitleDetail);
//
//					if (elementP == 2) {
//						// 影片内容简介
//						String ContentDetail = pElements.get(1).getAttribute(
//								"outerHTML");
//						if (ContentDetail == null || "".equals(ContentDetail)) {
//							logger.info(Constant.NONE_TAG);
//							return;
//						}
//						logger.info("======ContentDetail======" + ContentDetail);
//
//					} else if (elementP > 2 && elementP < 4) {
//						// 影片内容简介
//						String ContentDetail = pElements.get(1).getAttribute(
//								"outerHTML");
//						if (ContentDetail == null || "".equals(ContentDetail)) {
//							logger.info(Constant.NONE_TAG);
//							return;
//						}
//						logger.info("======ContentDetail======" + ContentDetail);
//
//					} else if (elementP >= 4 && elementP < 6) {
//						// 影片内容简介
//						String ContentDetail = pElements.get(1).getAttribute(
//								"outerHTML");
//						if (ContentDetail == null || "".equals(ContentDetail)) {
//							logger.info(Constant.NONE_TAG);
//							return;
//						}
//						logger.info("======ContentDetail======" + ContentDetail);
//
//						// 影片内容网盘和密码地址
//						String baiduUrl = pElements.get(2).getAttribute(
//								"outerHTML");
//						if (baiduUrl == null || "".equals(baiduUrl)) {
//							logger.info(Constant.NONE_TAG);
//						}
//						if (baiduUrl.contains("<a")) {
//							logger.info("======baiduUrl======" + baiduUrl);
//						}
//						// 影片内容网盘提取密码
//						String baiduNetPass = pElements.get(3).getAttribute(
//								"outerHTML");
//						if (baiduNetPass == null || "".equals(baiduNetPass)) {
//							logger.info(Constant.NONE_TAG);
//						}
//						if (baiduNetPass.contains("提取密码")) {
//							logger.info("======baiduNetPass======"
//									+ baiduNetPass);
//						}
//					} else if (elementP > 4) {
//						// 影片内容简介
//						String ContentDetail = pElements.get(1).getAttribute(
//								"outerHTML");
//						if (ContentDetail == null || "".equals(ContentDetail)) {
//							logger.info(Constant.NONE_TAG);
//							return;
//						}
//						logger.info("======ContentDetail======" + ContentDetail);
//
//						// 影片内容网盘和密码地址
//						String baiduUrl = pElements.get(2).getAttribute(
//								"outerHTML");
//						if (baiduUrl == null || "".equals(baiduUrl)) {
//							logger.info(Constant.NONE_TAG);
//						}
//						if (baiduUrl.contains("<a")) {
//							logger.info("======baiduUrl======" + baiduUrl);
//						}
//						// 影片内容网盘提取密码
//						String baiduNetPass = pElements.get(3).getAttribute(
//								"outerHTML");
//						if (baiduNetPass == null || "".equals(baiduNetPass)) {
//							logger.info(Constant.NONE_TAG);
//						}
//						if (baiduNetPass.contains("提取密码")) {
//							logger.info("======baiduNetPass======"
//									+ baiduNetPass);
//						}
//					}
//				}
//			}
//			// 大于5的时候作判断
//			// else {
//			// // 判断第一个标签P是否是简介
//			// String pString = pElements.get(0).getAttribute("outerHTML");
//			// if (pString.contains("<img")) {
//			// // 影片简介
//			// String videoTitleDetail = pElements.get(1).getAttribute(
//			// "outerHTML");
//			// if (videoTitleDetail == null || "".equals(videoTitleDetail)) {
//			// logger.info(Constant.NONE_TAG);
//			// return;
//			// }
//			// logger.info("======videoDetail======" + videoTitleDetail);
//			//
//			// if (elementP > 2 && elementP < 4) {
//			// // 影片内容简介
//			// String ContentDetail = pElements.get(2).getAttribute(
//			// "outerHTML");
//			// if (ContentDetail == null || "".equals(ContentDetail)) {
//			// logger.info(Constant.NONE_TAG);
//			// return;
//			// }
//			// logger.info("======ContentDetail======" + ContentDetail);
//			//
//			// } else if (elementP == 4) {
//			// // 影片内容简介
//			// String ContentDetail = pElements.get(2).getAttribute(
//			// "outerHTML");
//			// if (ContentDetail == null || "".equals(ContentDetail)) {
//			// logger.info(Constant.NONE_TAG);
//			// return;
//			// }
//			// logger.info("======ContentDetail======" + ContentDetail);
//			//
//			// // 影片内容网盘和密码地址
//			// String baiduUrl = pElements.get(3).getAttribute(
//			// "outerHTML");
//			// if (baiduUrl == null || "".equals(baiduUrl)) {
//			// logger.info(Constant.NONE_TAG);
//			// return;
//			// }
//			// if (baiduUrl.contains("<a")) {
//			// logger.info("======baiduUrl======" + baiduUrl);
//			// }
//			// } else if (elementP > 4) {
//			// // 影片内容简介
//			// String ContentDetail = pElements.get(2).getAttribute(
//			// "outerHTML");
//			// if (ContentDetail == null || "".equals(ContentDetail)) {
//			// logger.info(Constant.NONE_TAG);
//			// return;
//			// }
//			// logger.info("======ContentDetail======" + ContentDetail);
//			//
//			// // 影片内容网盘地址
//			// String baiduUrl = pElements.get(3).getAttribute(
//			// "outerHTML");
//			// if (baiduUrl == null || "".equals(baiduUrl)) {
//			// logger.info(Constant.NONE_TAG);
//			// return;
//			// }
//			// if (baiduUrl.contains("<a")) {
//			// logger.info("======baiduUrl======" + baiduUrl);
//			// }
//			//
//			// // 影片内容网盘提取密码
//			// String baiduNetPass = pElements.get(4).getAttribute(
//			// "outerHTML");
//			// if (baiduNetPass == null || "".equals(baiduNetPass)) {
//			// logger.info(Constant.NONE_TAG);
//			// return;
//			// }
//			// if (baiduNetPass.contains("提取密码")) {
//			// logger.info("======baiduNetPass======"
//			// + baiduNetPass);
//			// }
//			//
//			// }
//			// } else {
//			// String imgString = pElements.get(1).getAttribute(
//			// "outerHTML");
//			// if (imgString.contains("<img")) {
//			// // 影片简介
//			// String videoTitleDetail = pElements.get(2)
//			// .getAttribute("outerHTML");
//			// if (videoTitleDetail == null
//			// || "".equals(videoTitleDetail)) {
//			// logger.info(Constant.NONE_TAG);
//			// return;
//			// }
//			// logger.info("======videoDetail======"
//			// + videoTitleDetail);
//			//
//			// // 影片内容简介
//			// String ContentDetail = pElements.get(3).getAttribute(
//			// "outerHTML");
//			// if (ContentDetail == null || "".equals(ContentDetail)) {
//			// logger.info(Constant.NONE_TAG);
//			// return;
//			// }
//			// logger.info("======ContentDetail======" + ContentDetail);
//			//
//			// // 影片内容网盘和密码地址
//			// String baiduUrl = pElements.get(5).getAttribute(
//			// "outerHTML");
//			// if (baiduUrl == null || "".equals(baiduUrl)) {
//			// logger.info(Constant.NONE_TAG);
//			// return;
//			// }
//			// if (baiduUrl.contains("<a")) {
//			// logger.info("======baiduUrl======" + baiduUrl);
//			// }
//			// // 影片内容网盘提取密码
//			// String baiduNetPass = pElements.get(6).getAttribute(
//			// "outerHTML");
//			// if (baiduNetPass == null || "".equals(baiduNetPass)) {
//			// logger.info(Constant.NONE_TAG);
//			// return;
//			// }
//			// if (baiduNetPass.contains("提取密码")) {
//			// logger.info("======baiduNetPass======"
//			// + baiduNetPass);
//			// }
//			// }
//			// }
//			// }
//
//		} catch (UnknownServerException exception) {
//			logger.error(exception.toString());
//			return;
//		} catch (TimeoutException timeoutException) {
//			logger.error(timeoutException.toString());
//		} catch (Exception exception) {
//			logger.error(exception.toString());
//		} finally {
//
//			WebElement element = null;
//			try {
//				element = driver.findElement(By.className("result_album"));
//				if (element == null) {
//					logger.info(Constant.NONE_TAG);
//					return;
//				}
//				logger.debug("====== div ======{}", element.toString());
//			} catch (TimeoutException exception) {
//				logger.error(exception.toString());
//				parseHtml(detailUrl);
//			} catch (NoSuchElementException exception) {
//				logger.error(exception.toString());
//				return;
//			} catch (Exception exception) {
//				logger.error(exception.toString());
//			} finally {
//				if (element == null) {
//					return;
//				}
//				List<WebElement> elementA = element.findElements(By
//						.tagName("a"));
//				parseWeb(elementA.size(), 1);
//			}
//		}
//
//	}
//
//	// 判断共有多少集或进行采集
//	private synchronized static void parseWeb(int size, int zearo) {
//		if (size < 1)
//			return;
//		if (size > 0 && !(zearo > size)) {
//			try {
//				JavascriptExecutor js = (JavascriptExecutor) driver;
//				WebElement tagA = driver
//						.findElement(By.xpath("//*[@class=\"result_album\"]/a["
//								+ zearo + "]"));
//				if (tagA == null) {
//					logger.info(Constant.NONE_TAG);
//					return;
//				}
//				String value = tagA.getText();
//				logger.info(value);
//				if (value == null || "".equals(value)) {
//					logger.info(Constant.NONE_TAG);
//					return;
//				}
//
//				js.executeScript("arguments[0].click();", tagA);
//				WebElement elementTd = driver.findElement(By
//						.className("playleft"));
//				WebElement elementIframe = elementTd.findElement(By
//						.tagName("iframe"));
//				String srcUrl = elementIframe.getAttribute("src");
//				logger.debug("====== src ======", srcUrl);
//				zearo++;
//				logger.info(ValidateURL.subRelativeURL(srcUrl));
//				logger.info(ValidateURL.subAbsoluteURL(srcUrl));
//				parseWeb(size, zearo);
//			} catch (Exception exception) {
//				logger.error(exception.toString());
//				parseWeb(size, zearo);
//			}
//		}
//	}
//}
