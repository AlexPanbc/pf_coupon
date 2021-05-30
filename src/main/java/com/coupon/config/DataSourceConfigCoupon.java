//package com.coupon.config;
//
//import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
//import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
//import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//
//@Configuration
//@MapperScan(basePackages = "com.coupon.mapper.coupon", sqlSessionFactoryRef = "couponSqlSessionFactory")
//public class DataSourceConfigCoupon {
//    @Primary
//    @Bean(name = "couponDataSource")
//    @ConfigurationProperties("spring.datasource.coupon")
//    public DataSource masterDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "couponSqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactory(@Qualifier("couponDataSource") DataSource dataSource, PaginationInterceptor paginationInterceptor) throws Exception {
//        MybatisSqlSessionFactoryBean sessionFactoryBean = new MybatisSqlSessionFactoryBean();
//        sessionFactoryBean.setDataSource(dataSource);
//        sessionFactoryBean.setPlugins(new Interceptor[]{paginationInterceptor});
//        return sessionFactoryBean.getObject();
//    }
//
//    @Bean(name = "couponTransactionManager")
//    public PlatformTransactionManager couponTransactionManager(@Qualifier("couponDataSource") DataSource couponDataSource) {
//        return new DataSourceTransactionManager(couponDataSource);
//    }
//}
