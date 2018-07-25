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

import com.chenxing.demoshardingjdbc.service.Test01s;

/**
 * @author liuxing
 */
@RestController
public class HiController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	Test01s t;

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
	public String tt(@RequestParam String orderid, @RequestParam String userid, @RequestParam String orderstatus) {
		long start = System.currentTimeMillis();
		int res = t.saveOrder(orderid, userid, orderstatus);
		long end = System.currentTimeMillis();
		log.info("cost time =============" + String.valueOf(end - start) + "毫秒！");
		return res + "";

	}

}