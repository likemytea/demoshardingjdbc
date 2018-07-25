// package com.chenxing.demoshardingjdbc.config;
//
// import javax.sql.DataSource;
//
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.boot.context.properties.ConfigurationProperties;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.Primary;
//
// import com.alibaba.druid.pool.DruidDataSource;
// import com.chenxing.demoshardingjdbc.dao.base.MyJdbcTemplate;
//
/// **
// * Description:
// * @author liuxing
// * @date 2018年7月23日
// * @version 1.0
// */
// @Configuration
// public class DBConfig {
// private final Logger log = LoggerFactory.getLogger(this.getClass());
//
// /** 数据源1 start */
// @Bean(name = "dataSourcep1", destroyMethod = "close", initMethod = "init")
// @ConfigurationProperties("spring.datasource.p1")
// public com.alibaba.druid.pool.DruidDataSource getDataSourcep1() {
//
// log.info("dataSourcep1注入druid executed！");
// DruidDataSource druidDataSource = new DruidDataSource();
// return druidDataSource;
// }
//
// @Bean(name = "myJdbcTemplatep1")
// public MyJdbcTemplate getJdbcTemplatePrimary1(@Qualifier("dataSourcep1")
// DataSource dataSource) {
// return new MyJdbcTemplate(dataSource);
// }
//
// /** 数据源2 start */
// @Bean(name = "dataSourcep2", destroyMethod = "close", initMethod = "init")
// @Primary
// @ConfigurationProperties("spring.datasource.p2")
// public com.alibaba.druid.pool.DruidDataSource getDataSourcep2() {
//
// log.info("dataSourcep2注入druid executed！");
// DruidDataSource druidDataSource = new DruidDataSource();
// return druidDataSource;
// }
//
// @Bean(name = "myJdbcTemplatep2")
// public MyJdbcTemplate getJdbcTemplatePrimary2(@Qualifier("dataSourcep2")
// DataSource dataSource) {
// return new MyJdbcTemplate(dataSource);
// }
//
// /** 数据源3 start */
// @Bean(name = "dataSourcep3", destroyMethod = "close", initMethod = "init")
// @ConfigurationProperties("spring.datasource.p3")
// public com.alibaba.druid.pool.DruidDataSource getDataSourcep3() {
//
// log.info("dataSourcep3注入druid executed！");
// DruidDataSource druidDataSource = new DruidDataSource();
// return druidDataSource;
// }
//
// @Bean(name = "myJdbcTemplatep3")
// public MyJdbcTemplate getJdbcTemplatePrimary3(@Qualifier("dataSourcep3")
// DataSource dataSource) {
// return new MyJdbcTemplate(dataSource);
// }
// }
