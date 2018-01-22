package com.liuwei.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liuwei.pojo.NewsPOJO;

/**
 * @Project: news-crawling
 * @Class: DBHelper.java
 * @Description:数据库操作
 * @Date: 2017年11月14日
 * @author liuwei5
 */
public class DBHelper {
	public static final Logger logger = LoggerFactory.getLogger(DBHelper.class);
	private String url = "jdbc:mysql://127.0.0.1:3306/com_liuwei_db?useUnicode=true&characterEncoding=utf-8";
	private String dbDriver = "com.mysql.jdbc.Driver";
	private String user = "root";
	private String password = "root";
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;

	public DBHelper() {
		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			logger.error(e.toString());
			logger.info("驱动未找到");
		}
	}

	public Connection getConnection() {
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			logger.error(e.toString());
			logger.info("数据库连接异常");
		}
		return connection;
	}

	public void InsertToDB(Vector<NewsPOJO> news) {
		if (news != null && news.size() > 0) {
			for (NewsPOJO vector : news) {
				connection = getConnection();
				String INSERT_NEWS = "insert into news (idx_news_id,idx_news_type,idx_title,idx_short_content,"
						+ "img_url,local_img,news_resouce,uk_docid,idx_product_time,relative_key,idx_body,"
						+ "editor,gmt_create,img_extra) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				try {
					preparedStatement = connection.prepareStatement(INSERT_NEWS);
					preparedStatement.setString(1, vector.getNewsId());
					preparedStatement.setString(2, vector.getNewsType());
					preparedStatement.setString(3, vector.getTitle());
					preparedStatement.setString(4, vector.getShortCentent());
					preparedStatement.setString(5, vector.getImgUrl());
					preparedStatement.setBytes(6, vector.getLocalImg());
					preparedStatement.setString(7, vector.getNewsResouce());
					preparedStatement.setString(8, vector.getDocId());
					preparedStatement.setTimestamp(9, vector.getProductTime());
					preparedStatement.setString(10, vector.getRelativeKey());
					preparedStatement.setString(11, vector.getBody());
					preparedStatement.setString(12, vector.getEditor());
					preparedStatement.setTimestamp(13, vector.getCreateTime());
					preparedStatement.setString(14, vector.getImgExtra());
					preparedStatement.executeUpdate();
					logger.debug(preparedStatement.toString());
				} catch (SQLException e) {
					logger.error(e.toString());
					logger.info("插入数据时出现错误!!!");
				} finally {
					try {
						if(preparedStatement!=null) preparedStatement.close();
						if (connection!=null) connection.close();
					} catch (SQLException e) {
						logger.error(e.toString());
						logger.info("数据库关闭流时出现错误!!!");
					}
				}
			}
		} else {
			logger.info(Thread.currentThread().getName()+ ":数据库插入异常，当前线程不存在可插入的记录!!!");
		}
	}

	/**
	 * 更新数据
	 * 
	 * @param news
	 */
	public void updateToDB(Vector<NewsPOJO> news) {
		if (news != null && news.size() > 0) {	
			for (NewsPOJO vector : news) {
				connection = getConnection();
				String update = "update news set idx_news_id = ? ,idx_news_type = ? ,idx_short_content = ? ,"
						+ "img_url = ? ,local_img = ? ,news_resouce = ? ,idx_title = ? ,idx_product_time = ? ,"
						+ "relative_key = ? ,idx_body = ? ,editor = ? ,img_extra = ? where  uk_docid = ? ";
				try {	
						preparedStatement = connection.prepareStatement(update);
						preparedStatement.setString(1, vector.getNewsId());
						preparedStatement.setString(2, vector.getNewsType());
						preparedStatement.setString(3, vector.getShortCentent());
						preparedStatement.setString(4, vector.getImgUrl());
						preparedStatement.setBytes(5, vector.getLocalImg());
						preparedStatement.setString(6, vector.getNewsResouce());
						preparedStatement.setString(7, vector.getTitle());
						preparedStatement.setTimestamp(8,vector.getProductTime());
						preparedStatement.setString(9, vector.getRelativeKey());
						preparedStatement.setString(10, vector.getBody());
						preparedStatement.setString(11, vector.getEditor());
						preparedStatement.setString(12, vector.getImgExtra());
						preparedStatement.setString(13, vector.getDocId());
						preparedStatement.executeUpdate();
						logger.debug(preparedStatement.toString());
				} catch (SQLException e) {
					logger.error(e.toString());
					logger.info("更新数据时出现错误!!!");
				} finally {
					try {
						if(preparedStatement!=null) preparedStatement.close();
						if (connection!=null) connection.close();
					} catch (SQLException e) {
						logger.error(e.toString());
						logger.info("数据库关闭流时出现错误!!!");
					}
				}
			}
		} else {
			logger.info(Thread.currentThread().getName() + ":数据库插入异常，当前线程不存在可插入的记录!!!");
		}
	}

	/**
	 * 获取news总数
	 * 
	 * @return
	 */
	public int getDBNewsCount() {
		String QUERY_News = "SELECT idx_title FROM news";
		connection = getConnection();
		int rowCount = 0;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(QUERY_News);
			resultSet = preparedStatement.executeQuery(QUERY_News);
			resultSet.last();
			rowCount = resultSet.getRow();
			logger.debug(preparedStatement.toString());
			logger.info("获取新闻的总数:" + rowCount);
		} catch (SQLException e) {
			logger.error(e.toString());
			logger.info("数据库获取新闻总数时出现错误!!!");
		} finally {
			try {
				if(preparedStatement!=null) preparedStatement.close();
				if(connection!=null) connection.close();
			} catch (SQLException e) {
				logger.error(e.toString());
				logger.info("数据库关闭流时出现错误!!!");
			}
		}

		return rowCount;
	}
	
	public NewsPOJO searchFirstNews(){
		NewsPOJO newsPOJO = new NewsPOJO();
		String QUERY_News = "SELECT idx_title , uk_docid FROM news order by idx_product_time desc limit 1";
		connection = getConnection();
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(QUERY_News);
			resultSet = preparedStatement.executeQuery(QUERY_News);
			if(resultSet.next()){
				newsPOJO.setTitle(resultSet.getString(1));
				newsPOJO.setDocId(resultSet.getString(2));
			}
			logger.debug(preparedStatement.toString());
		} catch (SQLException e) {
			logger.error(e.toString());
			logger.info("数据库获取新闻第一条数据时出现错误!!!");
		} finally {
			try {
				if(preparedStatement!=null) preparedStatement.close();
				if(connection!=null) connection.close();
			} catch (SQLException e) {
				logger.error(e.toString());
				logger.info("数据库关闭流时出现错误!!!");
			}
		}
		return newsPOJO;
	}
}
