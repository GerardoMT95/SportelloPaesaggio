package it.eng.tz.puglia.geo.config;

import javax.sql.DataSource;

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

import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.util.crypt.CryptUtil;

@Configuration
@EnableTransactionManagement
@PropertySource(value = { "classpath:database.properties" }, encoding = "UTF-8", ignoreResourceNotFound = false)
public class DbConfigGeo {

	private static final Logger LOGGER = LoggerFactory.getLogger(DbConfigGeo.class);

	public DbConfigGeo() {
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
//		if (StringUtil.isNotEmpty(schema))
//		config.setSchema(schema);
		config.setJdbcUrl(this.env.getProperty("datasource.url"));
		config.setConnectionInitSql("SET search_path="+schema+",public,topology");
		LOGGER.info("datasource geo setting url: "+config.getJdbcUrl());
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
		config.setPoolName("Datasource_ESRI");
		return config;
	}

	/**
	 * @author Antonio La Gatta
	 * @date 05 giu 2019
	 * @return {@link DataSource}
	 * @throws Exception
	 */
	@Bean(name="ds_geo")
	public DataSource datasource() throws Exception {
		LOGGER.info("datasource");
		DataSource dataSource = new HikariDataSource(hikaryConfig());
	LOGGER.info("datasource geo= "+dataSource.hashCode());
		return dataSource;
	}

	/**
	 * @author Antonio La Gatta
	 * @date 05 giu 2019
	 * @return {@link DataSourceTransactionManager}
	 * @throws Exception
	 */
	@Bean(name="tx_geo")
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
	@Bean(name="named_jdbctemplate_geo")
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate() throws Exception {
		LOGGER.info("namedParameterJdbcTemplate");
		NamedParameterJdbcTemplate ret = new NamedParameterJdbcTemplate(this.datasource());
		return ret;
	}

}
