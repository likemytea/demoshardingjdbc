package com.chenxing.demoshardingjdbc.config;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.chenxing.demoshardingjdbc.dao.base.MyJdbcTemplate;

import io.shardingsphere.core.api.ShardingDataSourceFactory;
import io.shardingsphere.core.api.config.ShardingRuleConfiguration;
import io.shardingsphere.core.api.config.TableRuleConfiguration;
import io.shardingsphere.core.api.config.strategy.InlineShardingStrategyConfiguration;

/**  
* Description:
* @author liuxing
* @date 2018年7月23日  
* @version 1.0  
*/
@Configuration
public class DBShardingConfig {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

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
	public DataSource getShardingDataSource() {
		// 配置真实数据源
		Map<String, DataSource> dataSourceMap = new HashMap<>(3);

		// 配置第一个数据源
		DruidDataSource dataSource1 = createDefaultDruidDataSource();
		dataSource1.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource1.setUrl("jdbc:mysql://172.16.31.43:3306/ds0?useUnicode=true&characterEncoding=UTF-8&useSSL=false");
		dataSource1.setUsername("liuxing");
		dataSource1.setPassword("Liuxing009!");
		dataSourceMap.put("ds0", dataSource1);

		// 配置第二个数据源
		DruidDataSource dataSource2 = new DruidDataSource();
		dataSource2.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource2.setUrl("jdbc:mysql://172.16.31.43:3306/ds1?useUnicode=true&characterEncoding=UTF-8&useSSL=false");
		dataSource2.setUsername("liuxing");
		dataSource2.setPassword("Liuxing009!");
		dataSourceMap.put("ds1", dataSource2);

		// 配置Order表规则
		TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration();
		orderTableRuleConfig.setLogicTable("t_order");

		orderTableRuleConfig.setActualDataNodes("ds${0..1}.t_order_${0..1}");
		// orderTableRuleConfig.setActualDataNodes("ds0.t_order_0,ds0.t_order_1,ds1.t_order_0,ds1.t_order_1,ds2.t_order_0,ds2.t_order_1");

		// 配置分库策略（Groovy表达式配置db规则）
		orderTableRuleConfig.setDatabaseShardingStrategyConfig(
				new InlineShardingStrategyConfiguration("user_id", "ds${user_id % 2}"));

		// 配置分表策略（Groovy表达式配置表路由规则）
		orderTableRuleConfig.setTableShardingStrategyConfig(
				new InlineShardingStrategyConfiguration("order_id", "t_order_${order_id % 2}"));

		// 配置分片规则
		ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
		shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);

		// 配置order_items表规则...

		// 获取数据源对象
		DataSource dataSource = null;
		try {
			dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig,
					new ConcurrentHashMap(), new Properties());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dataSource;
	}

	@Bean(name = "myJdbcTemplatep1")
	public MyJdbcTemplate getJdbcTemplatePrimary1(@Qualifier("shardingDataSource") DataSource dataSource) {
		return new MyJdbcTemplate(dataSource);
	}
}
