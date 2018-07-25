package com.chenxing.demoshardingjdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.chenxing.demoshardingjdbc.service.ShardingService;

/**
 * Description: 注意：需要添加@Component注解
 * 
 * @author liuxing
 * @date 2018年7月25日
 * @version 1.0
 */
@Component
public class InitializationRunner implements ApplicationRunner {
	@Autowired
	ShardingService t;
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.boot.ApplicationRunner#run(org.springframework.boot.
	 * ApplicationArguments)
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("创建sharding-jdbc数据节点（物理表）");
		createtable();
	}

	/**
	 * 创建shardingjdbc数据节点（也就是物理表）
	 * 
	 * 
	 */
	private void createtable() {
		t.createTable1();
	}
}
