package com.example.demo.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.mysql.cj.jdbc.Driver;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@MapperScan(value = "com.example.demo.mapper", sqlSessionFactoryRef = "activeDbSqlSessionFactory")
public class MybatisPlusConfig {

    @Bean("activeDb")
    @Primary
    public DataSource activeDb() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(Driver.class.getName());
        dataSourceBuilder.url("jdbc:mysql://127.0.0.1:3306/strawberry?useUnicode=true&characterEncoding=UTF8&autoReconnect=true&serverTimezone=Asia/Shanghai&useSSL=false");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("");
        return dataSourceBuilder.build();
    }

    @Bean("activeDbSqlSessionFactory")
    public MybatisSqlSessionFactoryBean createSqlSessionFactory(
            @Qualifier("activeDb") DataSource dataSource,
            @Qualifier("plugins") Interceptor[] interceptors) {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPlugins(interceptors);
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        // mybatisConfiguration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
        factoryBean.setConfiguration(mybatisConfiguration);
        return factoryBean;
    }

    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));
        return interceptor;
    }

    @Bean(name = "plugins")
    public Interceptor[] getPlugins() {
        return new Interceptor[]{ mybatisPlusInterceptor() };
    }
}
