package com.chenxing.demoshardingjdbc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.chenxing.common.pagination.IllegalArgumentException;
import com.chenxing.common.pagination.Pagination;
import com.chenxing.common.pagination.PaginationResult;
import com.chenxing.common.pagination.SortBy;
import com.chenxing.common.pagination.SortType;
import com.chenxing.demoshardingjdbc.dao.base.MyJdbcTemplate;
import com.chenxing.demoshardingjdbc.entity.Test01;

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

	public int saveOrder(String orderid, String userid, String orderStatus) {
		String sql = "insert into t_order (order_id,user_id,status) VALUES(?,?,?)";
		int rs = myjdbcTemplate.update(sql, Integer.parseInt(orderid), Integer.parseInt(userid),
				Integer.parseInt(orderStatus));
		return 0;
	}

	public void createtable() {
		String sql = "CREATE TABLE IF NOT EXISTS `t_order` (`user_id` int(11) DEFAULT NULL,`order_id` int(11) NOT NULL,`status` int(11) DEFAULT NULL,PRIMARY KEY (`order_id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
		
		myjdbcTemplate.execute(sql);
	}

	public int findMessage(String id, String name, int currentpage, int pagesize) {

		try {
			DataSource ds = myjdbcTemplate.getDataSource();
			ds.getLoginTimeout();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String sql = "select name,id from test_01 where name like '%" + name + "%'";
		Pagination pagination = new Pagination();
		pagination.setCurrentPage(currentpage);
		pagination.setPageSize(pagesize);
		SortBy sortBy;
		try {
			sortBy = new SortBy("id", 1, SortType.DESC);
			pagination.addSortBy(sortBy);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		PaginationResult<?> ms = myjdbcTemplate.queryForPage(sql, pagination, new RowMapper<Test01>() {
			@Override
			public Test01 mapRow(ResultSet rs, int rowNum) throws SQLException {
				Test01 t = new Test01();
				t.setName(rs.getString(1));
				t.setId(rs.getString(2));
				return t;
			}
		});
		log.info("测试分页组件start");
		for (Object obj : ms.getData()) {
			Test01 t = (Test01) obj;
			log.info(t.getName());
			log.info(t.getId());
		}
		log.info("测试分页组件end");
		return ms.getCurrentPage() > 0 ? 1 : 2;
	}
}