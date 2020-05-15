package com.example.cache.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DBConfig {
    @Autowired
    private DataSourceProperties dataSourceProperties;
    //    private static final String MAPPER_LOCATION = "classpath:mapper/**/*.xml";
    private static final String MAPPER_LOCATION = "classpath*:mapper/**/*.xml";

    //    classpath: ：表示从类路径中加载资源，classpath:和classpath:/是等价的，都是相对于类的根路径。资源文件库标准的在文件系统中，也可以在JAR或ZIP的类包中。
//    classpath*:：假设多个JAR包或文件系统类路径都有一个相同的配置文件，classpath:只会在第一个加载的类路径下查找，而classpath*:会扫描所有这些JAR包及类路径下出现的同名文件。
//            ————————————————
//    版权声明：本文为CSDN博主「p7+」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//    原文链接：https://blog.csdn.net/qq_30038111/article/details/82116559
    @Bean
    public DataSource dataSource() throws Exception {
        Properties props = new Properties();
        props.put(DruidDataSourceFactory.PROP_DRIVERCLASSNAME, dataSourceProperties.getDriverClassName());
        props.put(DruidDataSourceFactory.PROP_URL, dataSourceProperties.getUrl());
        props.put(DruidDataSourceFactory.PROP_USERNAME, dataSourceProperties.getUsername());
        props.put(DruidDataSourceFactory.PROP_PASSWORD, dataSourceProperties.getPassword());
        props.put(DruidDataSourceFactory.PROP_INITCONNECTIONSQLS, "set names utf8mb4;");
        return DruidDataSourceFactory.createDataSource(props);
    }

    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactory(DataSource masterDataSource,
                                               GlobalConfig globalConfig)
            throws Exception {
        MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
        sessionFactory.setDataSource(masterDataSource);
//        sessionFactory.setMapperLocations(
//                new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
//        Interceptor[] plugins = new Interceptor[]{pageHelper(), new SqlCostInterceptor()};
//        sessionFactory.setPlugins(plugins);
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
        configuration.setMapUnderscoreToCamelCase(true);
        sessionFactory.setConfiguration(configuration);
        sessionFactory.setGlobalConfig(globalConfig);
        return sessionFactory.getObject();
    }

    @Bean
    public GlobalConfig globalConfiguration() {
        GlobalConfig conf = new GlobalConfig();
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        dbConfig.setLogicNotDeleteValue("1");
        dbConfig.setLogicDeleteValue("0");
        dbConfig.setUpdateStrategy(FieldStrategy.NOT_NULL);
//        conf.setMetaObjectHandler(new TimeMetaObjectHandler());
//        conf.setSqlInjector(new MySqlInjector());
        conf.setDbConfig(dbConfig);
        return conf;
    }
}
