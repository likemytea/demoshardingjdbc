package com.chenxing.demoshardingjdbc.config;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

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
		// 配置库分片和表分片规则
		ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
		shardingRuleConfig.getTableRuleConfigs().add(getTableRuleConfig());
		// 配置默认的数据源名称（如果不配置此项，没有分库的表在插入数据库的时候，就不知路由到哪个数据库）
		shardingRuleConfig.setDefaultDataSourceName("ds");

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

	// 配置默认的分片规则
	private TableRuleConfiguration configDefaultTableRule() {

		TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration();

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
		if ("ds".equals(dsname)) {
			ds.setDriverClassName("com.mysql.jdbc.Driver");
			ds.setUrl("jdbc:mysql://172.16.31.43:3306/ds?useUnicode=true&characterEncoding=UTF-8&useSSL=false");
			ds.setUsername("liuxing");
			ds.setPassword("Liuxing009!");
			return ds;
		} else if ("ds0".equals(dsname)) {
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
		result.put("ds", getDataSource("ds"));
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

	// @Bean(name = "myBEDSoftTransaction")
	// public BEDSoftTransaction
	// initializeSoftTransaction(@Qualifier("shardingDataSource") DataSource
	// dataSource,
	// @Qualifier("commonDataSource") DataSource commonDataSource)
	// throws SQLException {
	// // 1. 配置SoftTransactionConfiguration
	// SoftTransactionConfiguration transactionConfig =
	// configSoftTransaction(dataSource, commonDataSource);
	//
	// // 2. 初始化SoftTransactionManager
	// SoftTransactionManager transactionManager = new
	// SoftTransactionManager(transactionConfig);
	// transactionManager.init();
	// // 3. 获取BEDSoftTransaction
	// BEDSoftTransaction transaction = (BEDSoftTransaction) transactionManager
	// .getTransaction(SoftTransactionType.BestEffortsDelivery);
	// return transaction;
	// }
	//
	// // 配置柔性事务配置
	// private SoftTransactionConfiguration
	// configSoftTransaction(@Qualifier("shardingDataSource") DataSource dataSource,
	// @Qualifier("commonDataSource") DataSource commonDataSource) {
	// // 事务管理器管理的数据源
	// SoftTransactionConfiguration transactionConfig = new
	// SoftTransactionConfiguration(dataSource);
	// // 事务日志存储类型。可选值: RDB,MEMORY。使用RDB类型将自动建表
	// transactionConfig.setStorageType(TransactionLogDataSourceType.RDB);
	// // 存储事务日志的数据源，如果storageType为RDB则必填
	// transactionConfig.setTransactionLogDataSource(commonDataSource);
	// // 同步的事务送达的最大尝试次数
	// transactionConfig.setSyncMaxDeliveryTryTimes(3);
	// return transactionConfig;
	// }

	/** common 数据源 start */
	@Primary
	@Bean(name = "commonDataSource", destroyMethod = "close", initMethod = "init")
	@ConfigurationProperties("spring.datasource.commondb")
	public com.alibaba.druid.pool.DruidDataSource getDataSourcep3() {

		DruidDataSource druidDataSource = new DruidDataSource();
		return druidDataSource;
	}
}
