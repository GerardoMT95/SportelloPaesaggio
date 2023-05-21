package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import it.eng.tz.puglia.util.crypt.CryptUtil;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.puglia.web.interceptor.configuration.InterceptorConfiguration;

@Configuration
public class DatabaseRemoteConfiguration
{
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseRemoteConfiguration.class);
	
	@Autowired
	Environment environment;
	
	public DatabaseRemoteConfiguration()
	{
		LOGGER.debug("Sono nel construttore");
	}
	
	
	private HikariConfig hikariConfig() throws Exception
	{
		HikariConfig configuration = new HikariConfig();
		String       schema = environment.getProperty("datasource.common.schema");
		if (StringUtil.isNotEmpty(schema)) 
			configuration.setSchema(schema);
		String pwd = this.environment.getProperty("datasource.common.password");
		configuration.setPassword(CryptUtil.decrypt(pwd));
		configuration.setJdbcUrl            (this.environment.getProperty("datasource.common.url"                            ));
		configuration.setUsername           (this.environment.getProperty("datasource.common.username"                       ));
		configuration.setDriverClassName    (this.environment.getProperty("datasource.common.driverclassname"                     ));
		configuration.setConnectionTestQuery(this.environment.getProperty("datasource.read.validationquery"                       ));
		configuration.setValidationTimeout  (this.environment.getProperty("datasource.read.validationquery.timeout", Integer.class));
		configuration.setMaximumPoolSize    (this.environment.getProperty("datasource.read.maxactive"              , Integer.class));
		configuration.setMinimumIdle        (this.environment.getProperty("datasource.read.minidle"                , Integer.class));
		configuration.setIdleTimeout        (this.environment.getProperty("datasource.read.idle.timeout"           , Integer.class));
		configuration.setAutoCommit         (this.environment.getProperty("datasource.read.defaultautocommit"      , Boolean.class));
		configuration.setPoolName           ("Datasource_common");
		return configuration;
	}
	
	//schema common
	//annotato anche con questo nome perchè qui punta per il DB di gestione dei job schedulati.
	@Bean(name= {"common_ds","BATCH-SCHEDULER-DATA-SOURCE","DIOGENE-SCHEDULER-DATA-SOURCE"})
	public DataSource datasourceCommon() throws Exception
	{
		LOGGER.info("datasourceRemote");
		DataSource dataSource = new HikariDataSource(this.hikariConfig());
		
		FluentConfiguration fluentConfiguration = new FluentConfiguration();
		fluentConfiguration.baselineOnMigrate(true);
		fluentConfiguration.locations(this.environment.getProperty("flyway.location.migration.common"));
		fluentConfiguration.placeholderReplacement(false);
		String schema = environment.getProperty("datasource.common.schema");
		if (StringUtil.isNotEmpty(schema)) 
			fluentConfiguration.schemas(schema);
		fluentConfiguration.table("fw_paesaggio");
		fluentConfiguration.dataSource(dataSource);
		LOGGER.info("Start migration");
		Flyway flyway = new Flyway(fluentConfiguration);
		flyway.migrate();
		LOGGER.info("End migration");
		return dataSource;
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
}
