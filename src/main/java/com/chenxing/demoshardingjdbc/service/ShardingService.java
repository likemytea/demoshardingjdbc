package com.chenxing.demoshardingjdbc.service;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chenxing.demoshardingjdbc.dao.Test01Dao;
import com.chenxing.demoshardingjdbc.dao.UserAccountDao;

@Service
public class ShardingService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private Test01Dao test01Dao;
	@Autowired
	private UserAccountDao userAccountDao;

	public int updateName(String id, String name, int currentpage, int pagesize) {
		log.info("test yilaizhu");
		int count = 0;
		try {
			count = test01Dao.updateName(id, name);
			// test01Dao.findMessage(id, name, currentpage, pagesize);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return count;
	}

	public int saveOrder(long orderid, long userid, long orderStatus) {
		log.info("test sharding insert。。。");
		int count = 0;
		try {
			count = test01Dao.saveOrder(orderid, userid, orderStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return count;
	}

	public void createTable1() {
		log.info("create table ...");
		test01Dao.createtable();
	}

	public void getOrder() {
		log.info("create table ...");
		test01Dao.getOrder();
	}

	public void getUserAccountById(String id) {
		log.info("create table ...");
		userAccountDao.getUserAccount(id);
	}

	// 保存账户信息
	public int saveUserAccount(long id, long totalAmount, String describts) {
		log.info("test sharding insert。。。");
		int count = 0;
		count = userAccountDao.saveUserAccount(id, totalAmount, describts);

		return count;
	}
	// 柔性事务demo
	public void testSoftTransaction(long userAccountid, long totalAmount, String describts, long orderid, long userid,
			long orderstatus) throws SQLException {
		// userAccountDao.testSoftTransaction(userAccountid, totalAmount, describts,
		// orderid, userid, orderstatus);
	}
}
