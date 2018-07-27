package com.chenxing.demoshardingjdbc.config;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.chenxing.demoshardingjdbc.dao.base.MyJdbcTemplate;
import com.google.common.collect.Lists;

import io.shardingsphere.core.api.ShardingDataSourceFactory;
import io.shardingsphere.core.api.config.MasterSlaveRuleConfiguration;
import io.shardingsphere.core.api.config.ShardingRuleConfiguration;
import io.shardingsphere.core.api.config.TableRuleConfiguration;
import io.shardingsphere.core.api.config.strategy.InlineShardingStrategyConfiguration;

/**
 * Description:
 * 
 * @author liuxing
 * @date 2018年7月23日aaa
 * @version 1.0
 */
@Configuration
public class DBShardingConfig {

	public DruidDataSource createDefaultDruidDataSource() {
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setMaxWait(60000l);
		druidDataSource.setMaxActive(5);
		druidDataSource.setInitialSize(1);
		druidDataSource.setMinIdle(1);
		druidDataSource.setTimeBetweenEvictionRunsMillis(3000l);
		druidDataSource.setMinEvictableIdleTimeMillis(300000l);
		druidDataSource.setConnectionProperties("druid.stat.slowSqlMillis=3000");
		druidDataSource.setValidationQuery("SELECT 'x'");
		druidDataSource.setTestWhileIdle(true);
		druidDataSource.setTestOnBorrow(false);
		druidDataSource.setTestOnReturn(false);
		return druidDataSource;
	}
	@Bean(name = "shardingDataSource", destroyMethod = "close")
	@Qualifier("shardingDataSource")
	public DataSource getShardingDataSource() throws Exception {
		// 配置真实数据源
		Map<String, DataSource> dataSourceMap = createDataSourceMap();

		ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
		shardingRuleConfig.getTableRuleConfigs().add(getTableRuleConfig());

		// 配置读写分离
		shardingRuleConfig.setMasterSlaveRuleConfigs(getMasterSlaveRuleConfiguration());

		DataSource dataSource = null;
		try {
			dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig,
					new HashMap<String, Object>(), new Properties());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dataSource;
	}

	// 配置分片规则
	private TableRuleConfiguration getTableRuleConfig() {

		TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration();
		orderTableRuleConfig.setLogicTable("t_order");

		orderTableRuleConfig.setActualDataNodes("ds${0..1}.t_order_${0..1}");

		// 配置分库策略（Groovy表达式配置db规则）
		orderTableRuleConfig.setDatabaseShardingStrategyConfig(
				new InlineShardingStrategyConfiguration("user_id", "ds${user_id % 2}"));

		// 配置分表策略（Groovy表达式配置表路由规则）
		orderTableRuleConfig.setTableShardingStrategyConfig(
				new InlineShardingStrategyConfiguration("order_id", "t_order_${order_id % 2}"));
		return orderTableRuleConfig;
	}
	/** 读写分离规则配置 **/
	public List<MasterSlaveRuleConfiguration> getMasterSlaveRuleConfiguration() throws SQLException {

		MasterSlaveRuleConfiguration masterSlaveRuleConfig1 = new MasterSlaveRuleConfiguration("ds0",
				"ds0", Arrays.asList("ds0_slave0", "ds0_slave1"));
		MasterSlaveRuleConfiguration masterSlaveRuleConfig2 = new MasterSlaveRuleConfiguration("ds1",
				"ds1", Arrays.asList("ds1_slave0", "ds1_slave1"));
		return Lists.newArrayList(masterSlaveRuleConfig1, masterSlaveRuleConfig2);

	}

	private DruidDataSource getDataSource(String dsname) throws Exception {
		DruidDataSource ds = createDefaultDruidDataSource();
		if ("ds0".equals(dsname)) {
			ds.setDriverClassName("com.mysql.jdbc.Driver");
			ds.setUrl("jdbc:mysql://172.16.31.43:3306/ds0?useUnicode=true&characterEncoding=UTF-8&useSSL=false");
			ds.setUsername("liuxing");
			ds.setPassword("Liuxing009!");
			return ds;
		} else if ("ds0_slave0".equals(dsname)) {
			ds.setDriverClassName("com.mysql.jdbc.Driver");
			ds.setUrl("jdbc:mysql://172.16.31.43:3306/ds0_slave0?useUnicode=true&characterEncoding=UTF-8&useSSL=false");
			ds.setUsername("liuxing");
			ds.setPassword("Liuxing009!");
			return ds;
		} else if ("ds0_slave1".equals(dsname)) {
			ds.setDriverClassName("com.mysql.jdbc.Driver");
			ds.setUrl("jdbc:mysql://172.16.31.43:3306/ds0_slave1?useUnicode=true&characterEncoding=UTF-8&useSSL=false");
			ds.setUsername("liuxing");
			ds.setPassword("Liuxing009!");
			return ds;
		} else if ("ds1".equals(dsname)) {
			ds.setDriverClassName("com.mysql.jdbc.Driver");
			ds.setUrl("jdbc:mysql://172.16.31.43:3306/ds1?useUnicode=true&characterEncoding=UTF-8&useSSL=false");
			ds.setUsername("liuxing");
			ds.setPassword("Liuxing009!");
			return ds;
		} else if ("ds1_slave0".equals(dsname)) {
			ds.setDriverClassName("com.mysql.jdbc.Driver");
			ds.setUrl("jdbc:mysql://172.16.31.43:3306/ds1_slave0?useUnicode=true&characterEncoding=UTF-8&useSSL=false");
			ds.setUsername("liuxing");
			ds.setPassword("Liuxing009!");
			return ds;
		} else if ("ds1_slave1".equals(dsname)) {
			ds.setDriverClassName("com.mysql.jdbc.Driver");
			ds.setUrl("jdbc:mysql://172.16.31.43:3306/ds1_slave1?useUnicode=true&characterEncoding=UTF-8&useSSL=false");
			ds.setUsername("liuxing");
			ds.setPassword("Liuxing009!");
			return ds;
		} else {
			throw new Exception("did not discover this datasource name!");
		}
	}

	Map<String, DataSource> createDataSourceMap() throws Exception {
		Map<String, DataSource> result = new HashMap<>();

		result.put("ds0", getDataSource("ds0"));
		result.put("ds0_slave0", getDataSource("ds0_slave0"));
		result.put("ds0_slave1", getDataSource("ds0_slave1"));
		result.put("ds1", getDataSource("ds1"));
		result.put("ds1_slave0", getDataSource("ds1_slave0"));
		result.put("ds1_slave1", getDataSource("ds1_slave1"));

		return result;
	}

	@Bean(name = "myJdbcTemplatep1")
	public MyJdbcTemplate getJdbcTemplatePrimary1(@Qualifier("shardingDataSource") DataSource dataSource) {
		return new MyJdbcTemplate(dataSource);
	}
}
