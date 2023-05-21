package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import it.eng.tz.puglia.util.crypt.CryptUtil;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.puglia.web.interceptor.configuration.InterceptorConfiguration;

/**
 * Database configuration
 * @author Antonio La Gatta
 * @date 12 lug 2019
 */
@Configuration
@EnableTransactionManagement
@PropertySource(value = { "classpath:database.properties" }, encoding = "UTF-8", ignoreResourceNotFound = false)
public class DatabaseConfiguration {

	
	public static final String DS_WRITE = "datasourceWrite";
	public static final String DS_READ = "datasourceRead";
	public static final String TX_WRITE = "txWrite";
	public static final String TX_READ = "txRead";
	public static final String NAMED_JDBC_TEMPLATE_WRITE = "namedJdbcTempalteWrite";
	public static final String NAMED_JDBC_TEMPLATE_READ = "namedJdbcTempalteRead";
	
	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConfiguration.class);

	public DatabaseConfiguration() {
		LOGGER.info("Constructor");
	}
	
	/**
	 * environment for properties
	 */
	@Autowired
	private Environment env;
	
	/**
	 * @author Antonio La Gatta
	 * @date 12 lug 2019
	 * @return {@link HikariConfig}
	 * @throws Exception
	 */
	private HikariConfig hikaryConfigWrite() throws Exception {
		HikariConfig config = new HikariConfig();
		String       schema = this.env.getProperty("datasource.write.schema",this.env.getProperty("datasource.schema"));
		if (StringUtil.isNotEmpty(schema)) 
			config.setSchema(schema);
		config.setPassword(CryptUtil.decrypt(this.env.getProperty("datasource.write.password", this.env.getProperty("datasource.password"))));
        config.setJdbcUrl            (this.env.getProperty("datasource.write.url"                                   , this.env.getProperty("datasource.url"                                   )));
        config.setUsername           (this.env.getProperty("datasource.write.username"                              , this.env.getProperty("datasource.username"                              )));
        config.setDriverClassName    (this.env.getProperty("datasource.write.driverclassname"                       , this.env.getProperty("datasource.driverclassname"                       )));
        config.setConnectionTestQuery(this.env.getProperty("datasource.write.validationquery"                       , this.env.getProperty("datasource.validationquery"                       )));
        config.setValidationTimeout  (this.env.getProperty("datasource.write.validationquery.timeout", Integer.class, this.env.getProperty("datasource.validationquery.timeout", Integer.class)));
        config.setMaximumPoolSize    (this.env.getProperty("datasource.write.maxactive"              , Integer.class, this.env.getProperty("datasource.maxactive"              , Integer.class)));
        config.setMinimumIdle        (this.env.getProperty("datasource.write.minidle"                , Integer.class, this.env.getProperty("datasource.minidle"                , Integer.class)));
        config.setIdleTimeout        (this.env.getProperty("datasource.write.idle.timeout"           , Integer.class, this.env.getProperty("datasource.idle.timeout"           , Integer.class)));
        config.setAutoCommit         (this.env.getProperty("datasource.write.defaultautocommit"      , Boolean.class, this.env.getProperty("datasource.defaultautocommit"      , Boolean.class)));
        config.setPoolName           ("WRITE-DATA-SOURCE");
		return config;
	}
	/**
	 * @author Antonio La Gatta
	 * @date 12 lug 2019
	 * @return write datasource
	 * @throws Exception
	 */
	@Bean(DS_WRITE)
	public DataSource datasourceWrite() throws Exception {
		LOGGER.info("datasourceWrite");
		DataSource dataSource = new HikariDataSource(this.hikaryConfigWrite());
		
		FluentConfiguration fluentConfiguration = new FluentConfiguration();
		fluentConfiguration.baselineOnMigrate(true);
		fluentConfiguration.locations(this.env.getProperty("flyway.location.migration"));
		fluentConfiguration.placeholderReplacement(false);
		String schema = env.getProperty("datasource.schema.flyway");
		if (StringUtil.isNotEmpty(schema)) 
			fluentConfiguration.schemas(schema);
		fluentConfiguration.table("fw_presentazione_istanza");
		fluentConfiguration.dataSource(dataSource);
		LOGGER.info("Start migration");
		Flyway flyway = new Flyway(fluentConfiguration);
		flyway.migrate();
		LOGGER.info("End migration");
		return dataSource;
	}
	/**
	 * Datasource Audit
	 * @author Antonio La Gatta
	 * @date 12 lug 2019
	 * @return xwe datasource
	 * @throws Exception
	 */
	 /*
	@Bean(InterceptorConfiguration.DATASOURCE_NAME)
	public DataSource datasourceAudit() throws Exception {
		LOGGER.info("datasourceAudit");
		HikariConfig config = new HikariConfig();
        config.setJdbcUrl            (this.env.getProperty("datasource.audit.url"                                   ));
        config.setUsername           (this.env.getProperty("datasource.audit.username"                              ));
        config.setPassword           (CryptUtil.decrypt(this.env.getProperty("datasource.audit.password")));
        config.setDriverClassName    (this.env.getProperty("datasource.audit.driverclassname"                       ));
        config.setConnectionTestQuery(this.env.getProperty("datasource.audit.validationquery"                       ));
        config.setValidationTimeout  (this.env.getProperty("datasource.audit.validationquery.timeout", Integer.class));
        config.setMaximumPoolSize    (this.env.getProperty("datasource.audit.maxactive"              , Integer.class));
        config.setMinimumIdle        (this.env.getProperty("datasource.audit.minidle"                , Integer.class));
        config.setIdleTimeout        (this.env.getProperty("datasource.audit.idle.timeout"           , Integer.class));
        config.setAutoCommit         (this.env.getProperty("datasource.audit.defaultautocommit"      , Boolean.class));
        config.setPoolName           ("AUDIT-DATA-SOURCE");
		DataSource dataSource = new HikariDataSource(config);
		return dataSource;
	}
	*/
	/**
	 * @author Antonio La Gatta
	 * @date 12 lug 2019
	 * @return transactionWrite
	 * @throws Exception
	 */
	@Bean(TX_WRITE)
	public DataSourceTransactionManager transactionWrite() throws Exception {
		LOGGER.info("transactionWrite");
		return new DataSourceTransactionManager(this.datasourceWrite());
	}
	/**
	 * @author Antonio La Gatta
	 * @date 12 lug 2019
	 * @return {@link NamedParameterJdbcTemplate}
	 * @throws Exception
	 */
	@Bean(NAMED_JDBC_TEMPLATE_WRITE)
	public NamedParameterJdbcTemplate namedParameterJdbcTemplateWrite() throws Exception {
		LOGGER.info("namedParameterJdbcTemplateWrite");
		return new NamedParameterJdbcTemplate(this.datasourceWrite());
	}
	/**
	 * @author Antonio La Gatta
	 * @date 12 lug 2019
	 * @return {@link HikariConfig}
	 * @throws Exception
	 */
	private HikariConfig hikaryConfigRead() throws Exception {
		HikariConfig config = new HikariConfig();
		String       schema = this.env.getProperty("datasource.read.schema",this.env.getProperty("datasource.schema"));
		if (StringUtil.isNotEmpty(schema)) 
			config.setSchema(schema);
		config.setPassword(CryptUtil.decrypt(this.env.getProperty("datasource.read.password", this.env.getProperty("datasource.password"))));
        config.setJdbcUrl            (this.env.getProperty("datasource.read.url"                                   , this.env.getProperty("datasource.url"                                   )));
        config.setUsername           (this.env.getProperty("datasource.read.username"                              , this.env.getProperty("datasource.username"                              )));
        config.setDriverClassName    (this.env.getProperty("datasource.read.driverclassname"                       , this.env.getProperty("datasource.driverclassname"                       )));
        config.setConnectionTestQuery(this.env.getProperty("datasource.read.validationquery"                       , this.env.getProperty("datasource.validationquery"                       )));
        config.setValidationTimeout  (this.env.getProperty("datasource.read.validationquery.timeout", Integer.class, this.env.getProperty("datasource.validationquery.timeout", Integer.class)));
        config.setMaximumPoolSize    (this.env.getProperty("datasource.read.maxactive"              , Integer.class, this.env.getProperty("datasource.maxactive"              , Integer.class)));
        config.setMinimumIdle        (this.env.getProperty("datasource.read.minidle"                , Integer.class, this.env.getProperty("datasource.minidle"                , Integer.class)));
        config.setIdleTimeout        (this.env.getProperty("datasource.read.idle.timeout"           , Integer.class, this.env.getProperty("datasource.idle.timeout"           , Integer.class)));
        config.setAutoCommit         (this.env.getProperty("datasource.read.defaultautocommit"      , Boolean.class, this.env.getProperty("datasource.defaultautocommit"      , Boolean.class)));
        config.setPoolName           ("READ-DATA-SOURCE");
		return config;
	}
	/**
	 * @author Antonio La Gatta
	 * @date 12 lug 2019
	 * @return read datasource
	 * @throws Exception
	 */
	@Bean(DS_READ)
	public DataSource datasourceRead() throws Exception {
		LOGGER.info("datasourceRead");
		return new HikariDataSource(this.hikaryConfigRead());
	}
	
	/**
	 * @author Antonio La Gatta
	 * @date 12 lug 2019
	 * @return transaction for read environment
	 * @throws Exception
	 */
	@Bean(TX_READ)
	public DataSourceTransactionManager transactionRead() throws Exception {
		LOGGER.info("transactionRead");
		return new DataSourceTransactionManager(this.datasourceRead());
	}
	/**
	 * @author Antonio La Gatta
	 * @date 12 lug 2019
	 * @return {@link NamedParameterJdbcTemplate}
	 * @throws Exception
	 */
	@Bean(NAMED_JDBC_TEMPLATE_READ)
	public NamedParameterJdbcTemplate namedParameterJdbcTemplateRead() throws Exception {
		LOGGER.info("namedParameterJdbcTemplateRead");
		return new NamedParameterJdbcTemplate(this.datasourceRead());
	}
	
	
}
