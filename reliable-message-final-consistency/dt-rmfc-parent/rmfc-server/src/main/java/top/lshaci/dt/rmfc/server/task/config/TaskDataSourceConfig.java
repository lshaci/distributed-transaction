package top.lshaci.dt.rmfc.server.task.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@MapperScan(basePackages = "top.lshaci.dt.rmfc.server.task.mapper", sqlSessionTemplateRef = "taskSqlSessionTemplate")
@PropertySource("classpath:config/properties/taskDatasource.properties")
public class TaskDataSourceConfig {

	private static final Logger logger = LoggerFactory.getLogger(TaskDataSourceConfig.class);
	
	@Value("${task.mybatis.mapper-locations}")
	private String mapperLocation;
	
	@Value("${task.mybatis.type-aliases-package}")
	private String typeAliasesPackage;

	@Bean("taskDataSource")
	@ConfigurationProperties(prefix = "task.druid.datasource")
	public DataSource taskDataSource() {
		logger.debug("Init Task DataSource use DruidDataSource...");

		return new DruidDataSource();
	}
	
	@Bean("taskSqlSessionFactory")
    public SqlSessionFactory taskSqlSessionFactory(@Qualifier("taskDataSource") DataSource taskDataSource) throws Exception {
		logger.debug("Init taskSqlSessionFactory use taskDataSource...");
		
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(taskDataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocation));
        bean.setTypeAliasesPackage(typeAliasesPackage);
        
        return bean.getObject();
    }
	
    @Bean(name = "taskTransactionManager")
    public DataSourceTransactionManager taskTransactionManager(@Qualifier("taskDataSource") DataSource taskDataSource) {
    	logger.debug("Init taskTransactionManager use taskDataSource...");
    	
        return new DataSourceTransactionManager(taskDataSource);
    }

    @Bean(name = "taskSqlSessionTemplate")
    public SqlSessionTemplate taskSqlSessionTemplate(@Qualifier("taskSqlSessionFactory") SqlSessionFactory taskSqlSessionFactory) throws Exception {
    	logger.debug("Init taskSqlSessionTemplate use taskSqlSessionFactory...");
    	
        return new SqlSessionTemplate(taskSqlSessionFactory);
    }
	
}
