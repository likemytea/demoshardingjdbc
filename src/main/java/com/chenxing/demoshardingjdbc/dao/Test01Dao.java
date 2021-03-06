package com.chenxing.demoshardingjdbc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.chenxing.common.pagination.Pagination;
import com.chenxing.common.pagination.PaginationResult;
import com.chenxing.demoshardingjdbc.dao.base.MyJdbcTemplate;
import com.chenxing.demoshardingjdbc.entity.TOrder;

/**
 * Description:ceshi
 * 
 * @author liuxing
 * @date 2018年5月9日
 * @version 1.0
 */
@Repository
public class Test01Dao {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("myJdbcTemplatep1")
	private MyJdbcTemplate myjdbcTemplate;


	public int updateName(String orderStatus, String orderid) {
		String sql = "update t_order set status=? where order_id=?";
		int rs = myjdbcTemplate.update(sql, orderStatus, orderid);
		return rs;
	}

	public int saveOrder(long orderid, long userid, long orderStatus) {
		String sql = "insert into t_order (order_id,user_id,status) VALUES(?,?,?)";
		int rs = myjdbcTemplate.update(sql, orderid, userid, orderStatus);
		return 0;
	}

	public void getOrder() {
		try {

			String sql = "select order_id,user_id,status from  t_order";
			Pagination pagination = new Pagination();
			pagination.setCurrentPage(1);
			pagination.setPageSize(6);
			PaginationResult<?> ms = myjdbcTemplate.queryForPage(sql, pagination, new RowMapper<TOrder>() {
				@Override
				public TOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
					TOrder t = new TOrder();
					t.setOrderid(rs.getLong(1));
					t.setUserid(rs.getLong(2));
					t.setStatus(rs.getLong(3));
					return t;
				}
			});
			log.info("测试shardingjdbc组件start");
			for (Object obj : ms.getData()) {
				TOrder t = (TOrder) obj;
				// log.info("订单号" + t.getOrderid());
				// log.info("userid号" + t.getUserid());
				log.info("----" + t.getStatus());
			}
			log.info("测试shardingjdbc组件end");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void createtable() {
		String sql = "CREATE TABLE IF NOT EXISTS `t_order` (`user_id` bigint(20) DEFAULT NULL,`order_id` bigint(20) NOT NULL,`status` bigint(20) DEFAULT NULL,PRIMARY KEY (`order_id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
		
		myjdbcTemplate.execute(sql);
	}

}