/**
 * 
 */
package com.chenxing.demoshardingjdbc.rest;

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

}