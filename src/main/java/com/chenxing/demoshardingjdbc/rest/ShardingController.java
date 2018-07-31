/**
 * 
 */
package com.chenxing.demoshardingjdbc.rest;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chenxing.common.distributedKey.PrimarykeyGenerated;
import com.chenxing.demoshardingjdbc.service.ShardingService;

/**
 * @author liuxing
 */
@RestController
public class ShardingController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ShardingService t;

	@RequestMapping(value = "/tt", method = RequestMethod.GET)
	public String tt(@RequestParam String id, @RequestParam String name) {
		long start = System.currentTimeMillis();
		String result = null;
		log.info(name);
		t.updateName(id, name, 1, 3);
		long end = System.currentTimeMillis();
		log.info("cost time =" + String.valueOf(end - start) + "毫秒！");
		return id + name;

	}

	@RequestMapping(value = "/saveorder", method = RequestMethod.GET)
	public String saveorder(@RequestParam String userid, @RequestParam String orderstatus) {

		long start = System.currentTimeMillis();
		int res = t.saveOrder(Long.parseLong(PrimarykeyGenerated.generateId(true)), Long.parseLong(userid),
				Long.parseLong(orderstatus));
		long end = System.currentTimeMillis();
		log.info("cost time =============" + String.valueOf(end - start) + "毫秒！");
		return res + "";

	}

	@RequestMapping(value = "/getorder", method = RequestMethod.GET)
	public String getorder(@RequestParam String userid, @RequestParam String orderid) {

		long start = System.currentTimeMillis();
		t.getOrder();
		long end = System.currentTimeMillis();
		log.info("cost time =============" + String.valueOf(end - start) + "毫秒！");
		return "success";

	}

	@RequestMapping(value = "/getUserAccountById", method = RequestMethod.GET)
	public String getUserAccountById(@RequestParam String id) {

		long start = System.currentTimeMillis();
		t.getUserAccountById(id);
		long end = System.currentTimeMillis();
		log.info("cost time =============" + String.valueOf(end - start) + "毫秒！");
		return "success";

	}

	@RequestMapping(value = "/saveAccount", method = RequestMethod.GET)
	public String saveAccount(@RequestParam String id, @RequestParam String desc) {

		long start = System.currentTimeMillis();
		t.saveUserAccount(Long.parseLong(PrimarykeyGenerated.generateId(true)), 1000, desc);
		long end = System.currentTimeMillis();
		log.info("cost time =============" + String.valueOf(end - start) + "毫秒！");
		return "success";

	}

	/**
	 * 柔性事务test
	 * 
	 * @throws SQLException
	 * @throws NumberFormatException
	 * 
	 */
	@RequestMapping(value = "/testSoftTransaction", method = RequestMethod.GET)
	public String testSoftTransaction(@RequestParam String id, @RequestParam String desc,
			@RequestParam String orderstatus) throws NumberFormatException, SQLException {

		long start = System.currentTimeMillis();
		t.testSoftTransaction(Long.parseLong(PrimarykeyGenerated.generateId(true)), 10, desc,
				Long.parseLong(PrimarykeyGenerated.generateId(true)),
				9, Long.parseLong(orderstatus));
		long end = System.currentTimeMillis();
		log.info("cost time =============" + String.valueOf(end - start) + "毫秒！");
		return "success";

	}

}