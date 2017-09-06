package top.lshaci.dt.rmfc.server.config;

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
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@MapperScan(basePackages = "top.lshaci.dt.rmfc.server.mapper", sqlSessionTemplateRef = "sqlSessionTemplate")
@PropertySource("classpath:config/properties/datasource.properties")
public class DataSourceConfig {

	private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);
	
	@Value("${mybatis.mapper-locations}")
	private String mapperLocation;
	
	@Value("${mybatis.type-aliases-package}")
	private String typeAliasesPackage;

	@Primary
	@Bean("dataSource")
	@ConfigurationProperties(prefix = "druid.datasource")
	public DataSource dataSource() {
		logger.debug("Init Primary DataSource use DruidDataSource...");

		return new DruidDataSource();
	}
	
	@Primary
	@Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
		logger.debug("Init Primary SqlSessionFactory use Primary dataSource...");
		
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocation));
        bean.setTypeAliasesPackage(typeAliasesPackage);
        
        return bean.getObject();
    }
	
	@Primary
    @Bean("transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
    	logger.debug("Init Primary TransactionManager use Primary dataSource...");
    	
        return new DataSourceTransactionManager(dataSource);
    }

    @Primary
    @Bean("sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
    	logger.debug("Init Primary SqlSessionTemplate use Primary sqlSessionFactory...");
    	
        return new SqlSessionTemplate(sqlSessionFactory);
    }
	
}
