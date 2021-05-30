//package com.coupon.config;
//
//import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import javax.sql.DataSource;
//
//@Configuration
//@MapperScan(basePackages = "com.coupon.mapper.group", sqlSessionFactoryRef = "groupSqlSessionFactory")
//public class DataSourceConfigGroup {
//    @Primary
//    @Bean(name = "groupDataSource")
//    @ConfigurationProperties("spring.datasource.group")
//    public DataSource masterDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "groupSqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactory(@Qualifier("groupDataSource") DataSource dataSource) throws Exception {
//        MybatisSqlSessionFactoryBean sessionFactoryBean = new MybatisSqlSessionFactoryBean();
//        sessionFactoryBean.setDataSource(dataSource);
//
//        return sessionFactoryBean.getObject();
//    }
//}
