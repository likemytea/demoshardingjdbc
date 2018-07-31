package com.chenxing.demoshardingjdbc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

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

/**
 * Description:ceshi
 * 
 * @author liuxing
 * @date 2018年7月30日
 * @version 1.0
 */
@Repository
public class UserAccountDao {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	@Qualifier("myJdbcTemplatep1")
	private MyJdbcTemplate myjdbcTemplate;
	// @Autowired
	// private BEDSoftTransaction transaction;

	public int getUserAccount(String id) {
		String sql = "select id,total_amount,descripts from user_account where id = " + id;
		Pagination pagination = new Pagination();
		pagination.setCurrentPage(1);
		pagination.setPageSize(1);
		SortBy sortBy;
		try {
			sortBy = new SortBy("id", 1, SortType.DESC);
			pagination.addSortBy(sortBy);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		PaginationResult<?> ms = myjdbcTemplate.queryForPage(sql, pagination, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				log.info(rs.getString(3));
				return "";
			}
		});
		return ms.getCurrentPage() > 0 ? 1 : 2;
	}

	public int saveUserAccount(long id, long totalAmount, String descripts) {
		String sql = "insert into user_account (id,total_amount,descripts) VALUES(?,?,?)";
		int rs = myjdbcTemplate.update(sql, id, totalAmount, descripts);
		return rs;
	}

	public int saveOrder(long orderid, long userid, long orderStatus) {
		String sql = "insert into t_order (order_id,user_id,status) VALUES(?,?,?)";
		int rs = myjdbcTemplate.update(sql, orderid, userid, orderStatus);
		return 0;
	}
	// 柔性事务demo
	// public void testSoftTransaction(long userAccountid, long totalAmount, String
	// describts, long orderid, long userid,
	// long orderstatus) throws SQLException {
	// log.info("testSoftTransaction...");
	// transaction.begin(myjdbcTemplate.getDataSource().getConnection());
	// int res1 = saveUserAccount(userAccountid, totalAmount, describts);
	// log.info("" + res1);
	// saveOrder(18073114384000008l, userid, orderstatus);
	// transaction.end();
	// }

}
