package com.liuwei.util;

import javax.swing.JOptionPane;

public class ValidateURL {

	// 匹配url
	public static boolean validateURL(String url) {
		String urlString = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
		if (url.matches(urlString)) {
			return true;
		}
		try {
			JOptionPane.showMessageDialog(null, "请求的url地址有误，请输入正确的url地址！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// 截取相对地址 的url
	public static String subRelativeURL(String url) {
		return url.substring(url.indexOf("?"));
	}
	
	// 截取绝对地址 的url
	public static String subAbsoluteURL(String url) {
		if (url.contains("=http")) {
			return url.substring(url.indexOf("=http") + 1);
		}
		return url;
	}
}
