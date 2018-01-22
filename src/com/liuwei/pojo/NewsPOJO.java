package com.liuwei.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

/**
 * @Project: DataCrawling
 * @Class: NewsPOJO.java
 * @Description: 新闻实体类
 * @Date: 2017年11月13日
 * @author liuwei5
 */
public class NewsPOJO implements Serializable{
	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String newsId;
	private String newsType;
	private String title;
	private String shortCentent;
	private String imgUrl;
	private byte[] localImg;
	private String imgExtra;
	private String newsResouce;
	private String docId;
	private Timestamp productTime;
	private String relativeKey;
	private String body;
	private String editor;
	private Timestamp createTime;
	private Timestamp modifiedTime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNewsId() {
		return newsId;
	}
	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}
	public String getNewsType() {
		return newsType;
	}
	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getShortCentent() {
		return shortCentent;
	}
	public void setShortCentent(String shortCentent) {
		this.shortCentent = shortCentent;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public byte[] getLocalImg() {
		return localImg;
	}
	public void setLocalImg(byte[] localImg) {
		this.localImg = localImg;
	}
	public String getImgExtra() {
		return imgExtra;
	}
	public void setImgExtra(String imgExtra) {
		this.imgExtra = imgExtra;
	}
	public String getNewsResouce() {
		return newsResouce;
	}
	public void setNewsResouce(String newsResouce) {
		this.newsResouce = newsResouce;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public Timestamp getProductTime() {
		return productTime;
	}
	public void setProductTime(Timestamp productTime) {
		this.productTime = productTime;
	}
	public String getRelativeKey() {
		return relativeKey;
	}
	public void setRelativeKey(String relativeKey) {
		this.relativeKey = relativeKey;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getEditor() {
		return editor;
	}
	public void setEditor(String editor) {
		this.editor = editor;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Timestamp modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	
	@Override
	public String toString() {
		return "NewsPOJO [id=" + id + ", newsId=" + newsId + ", newsType="
				+ newsType + ", title=" + title + ", shortCentent="
				+ shortCentent + ", imgUrl=" + imgUrl + ", localImg="
				+ Arrays.toString(localImg) +",imgExtra=" + imgExtra 
				+", newsResouce=" + newsResouce	+ ", docId=" + docId 
				+ ", productTimes=" + productTime + ", relativeKey=" 
				+ relativeKey + ", body=" + body + ", editor=" + editor 
				+ ", createTime=" + createTime + ", modifiedTime=" + modifiedTime + "]";
	}
	
	
	
}
