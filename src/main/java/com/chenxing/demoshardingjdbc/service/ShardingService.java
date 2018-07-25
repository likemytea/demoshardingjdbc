package com.chenxing.demoshardingjdbc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chenxing.demoshardingjdbc.dao.Test01Dao;

@Service
public class ShardingService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private Test01Dao test01Dao;
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

	public int saveOrder(String orderid, String userid, String orderStatus) {
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

}
