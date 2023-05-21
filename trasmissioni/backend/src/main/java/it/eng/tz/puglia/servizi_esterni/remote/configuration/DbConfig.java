package it.eng.tz.puglia.servizi_esterni.remote.configuration;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import it.eng.tz.puglia.util.crypt.CryptUtil;
import it.eng.tz.puglia.util.string.StringUtil;

@Configuration
@EnableTransactionManagement
@PropertySource(value = { "classpath:database.properties", "classpath:application.properties" }, encoding = "UTF-8", ignoreResourceNotFound = false)
public class DbConfig
{
	private static final Logger logger = LoggerFactory.getLogger(DbConfig.class);
	@Autowired
	Environment environment;
	
	public DbConfig()
	{
		logger.debug("Sono nel contrsuttore");
	}
	
	private HikariConfig hikariConfig() throws Exception
	{
		HikariConfig configuration = new HikariConfig();
		String       schema = environment.getProperty("datasource.common.schema");
		if (StringUtil.isNotEmpty(schema)) 
			configuration.setSchema(schema);
		configuration.setJdbcUrl            (this.environment.getProperty("datasource.common.url"                            ));
		configuration.setUsername           (this.environment.getProperty("datasource.common.username"                       ));
		configuration.setPassword           (CryptUtil.decrypt(this.environment.getProperty("datasource.common.password"                       )));
		configuration.setDriverClassName    (this.environment.getProperty("datasource.common.driverclassname"                ));
		configuration.setConnectionTestQuery(this.environment.getProperty("datasource.validationquery"                       ));
		configuration.setValidationTimeout  (this.environment.getProperty("datasource.validationquery.timeout", Integer.class));
		configuration.setMaximumPoolSize    (this.environment.getProperty("datasource.maxactive"              , Integer.class));
		configuration.setMinimumIdle        (this.environment.getProperty("datasource.minidle"                , Integer.class));
		configuration.setIdleTimeout        (this.environment.getProperty("datasource.idle.timeout"           , Integer.class));
		configuration.setAutoCommit         (this.environment.getProperty("datasource.defaultautocommit"      , Boolean.class));
		configuration.setPoolName           ("Datasource_common");
		
		return configuration;
	}
	
	//schema common
	//annotato anche con questo nome perchè qui punta per il DB di gestione dei job schedulati.
	@Bean(name= {"common_ds","BATCH-SCHEDULER-DATA-SOURCE"
		//, InterceptorConfiguration.DATASOURCE_NAME
		})
	public DataSource datasourceCommon() throws Exception
	{
		return new HikariDataSource(hikariConfig());
	}
	//schema anagrafica
	@Bean(name="anagrafica_ds")
	public DataSource datasourceAnagrafica() throws Exception
	{
		HikariConfig config = hikariConfig();
		String schema = environment.getProperty("datasource.anagrafica.schema");
		config.setSchema(schema);
		return new HikariDataSource(config);
	}
	
	@Bean(name="txmng_common")
	public DataSourceTransactionManager txMngrCommon() throws Exception
	{
		return new DataSourceTransactionManager(datasourceCommon());
	}
	@Bean(name="txmng_anagrafica")
	public DataSourceTransactionManager txMngrAnagrafia() throws Exception
	{
		return new DataSourceTransactionManager(datasourceAnagrafica());
	}
	
	@Bean(name="namedJdbcTemplate_common")
	public NamedParameterJdbcTemplate namedParameterJdbcTemplateCommon() throws Exception
	{
		return new NamedParameterJdbcTemplate(datasourceCommon());
	}
	@Bean(name="namedJdbcTemplate_anagrafica")
	public NamedParameterJdbcTemplate namedParameterJdbcTemplateAnagrafica() throws Exception
	{
		return new NamedParameterJdbcTemplate(datasourceAnagrafica());
	}
	
	@Bean(name="jdbcTemplate_common")
	public JdbcTemplate jdbcTemplateCommon() throws Exception
	{
		return new JdbcTemplate(datasourceCommon());	
	}
	@Bean(name="jdbcTemplate_anagrafica")
	public JdbcTemplate jdbcTemplateAnagrafica() throws Exception
	{
		return new JdbcTemplate(datasourceAnagrafica());
	}
}
