package com.chenxing.demoshardingjdbc.entity;

/**  
* Description:
* @author liuxing
* @date 2018年7月27日  
* @version 1.0  
*/
public class TOrder {
	private long userid;
	private long orderid;
	private long status;

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public long getOrderid() {
		return orderid;
	}

	public void setOrderid(long orderid) {
		this.orderid = orderid;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

}
