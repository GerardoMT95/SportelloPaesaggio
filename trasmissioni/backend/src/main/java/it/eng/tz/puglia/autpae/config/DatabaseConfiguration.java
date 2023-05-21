package it.eng.tz.puglia.autpae.config;

import java.util.Map;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import it.eng.tz.puglia.util.crypt.CryptUtil;
import it.eng.tz.puglia.util.string.StringUtil;

@Configuration
@EnableTransactionManagement
@PropertySource(value = { "classpath:database.properties" }, encoding = "UTF-8", ignoreResourceNotFound = false)
public class DatabaseConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConfiguration.class);

	public DatabaseConfiguration() {
		LOGGER.info("Constructor");
	}

	@Autowired
	ApplicationProperties props;

		/**
	 * environment for properties
	 */
	@Autowired
	private Environment env;

	private HikariConfig hikaryConfig() throws Exception {
		HikariConfig config = new HikariConfig();
		//String schema = env.getProperty("datasource.schema");
		String schema = props.schemaName();
		if (StringUtil.isNotEmpty(schema))
		config.setSchema(schema);
//		config.setJdbcUrl(this.env.getProperty("datasource.url")+"?searchpath="+schema+",public,topology");
		config.setJdbcUrl(this.env.getProperty("datasource.url"));
		config.setUsername(this.env.getProperty("datasource.username."+schema));
		config.setPassword(CryptUtil.decrypt(this.env.getProperty("datasource.password."+schema)));
		config.setDriverClassName(this.env.getProperty("datasource.driverclassname"));
		config.setConnectionTestQuery(this.env.getProperty("datasource.validationquery"));
		config.setValidationTimeout(this.env.getProperty("datasource.validationquery.timeout", Integer.class));
		config.setMaximumPoolSize(this.env.getProperty("datasource.maxactive", Integer.class));
		config.setMinimumIdle(this.env.getProperty("datasource.minidle", Integer.class));
		config.setIdleTimeout(this.env.getProperty("datasource.idle.timeout", Integer.class));
		config.setAutoCommit(this.env.getProperty("datasource.defaultautocommit", Boolean.class));
//        config.addDataSourceProperty ("cachePrepStmts", "true");
//        config.addDataSourceProperty ("prepStmtCacheSize", "250");
//        config.addDataSourceProperty ("prepStmtCacheSqlLimit", "2048");
		config.setPoolName("Datasource");
		return config;
	}

	/**
	 * @author Antonio La Gatta
	 * @date 05 giu 2019
	 * @return {@link DataSource}
	 * @throws Exception
	 */
	@Bean
	@Primary
	public DataSource datasource() throws Exception {
		LOGGER.info("datasource");
		DataSource dataSource = new HikariDataSource(hikaryConfig());
		FluentConfiguration fluentConfiguration = new FluentConfiguration();
		fluentConfiguration.baselineOnMigrate(true);
		fluentConfiguration.locations(this.env.getProperty("flyway.location.migration"));
		String schema = props.schemaName();
		if (StringUtil.isNotEmpty(schema))
			fluentConfiguration.schemas(schema);
		fluentConfiguration.dataSource(dataSource);
		if (StringUtil.isNotEmpty(schema)) {
			LOGGER.info("set search_path to " + schema);
			fluentConfiguration.initSql("SELECT pg_catalog.set_config('search_path', '" + schema +",public,topology"+ "', false)");
		}
		fluentConfiguration.placeholderReplacement(true);
		fluentConfiguration.placeholders(Map.of("schemaApplicazione",schema));
		LOGGER.info("Start migration");
		Flyway flyway = new Flyway(fluentConfiguration);
		flyway.migrate();
		LOGGER.info("End migration");
		return dataSource;
	}

	/**
	 * @author Antonio La Gatta
	 * @date 05 giu 2019
	 * @return {@link DataSourceTransactionManager}
	 * @throws Exception
	 */
	@Bean
	@Primary
	public DataSourceTransactionManager transaction() throws Exception {
		LOGGER.info("transaction");
		return new DataSourceTransactionManager(this.datasource());
	}

	/**
	 * @author Antonio La Gatta
	 * @date 05 giu 2019
	 * @return {@link DataSourceTransactionManager}
	 * @throws Exception
	 */
	@Bean
	@Primary
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate() throws Exception {
		LOGGER.info("namedParameterJdbcTemplate");
		return new NamedParameterJdbcTemplate(this.datasource());
	}
}
