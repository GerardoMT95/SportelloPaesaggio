/**
 * 
 */
package it.eng.tz.aet.paesaggio.civilia.migrazione;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import it.eng.tz.puglia.util.string.StringUtil;

/**
 * @author Adriano Colaianni
 * @date 19 apr 2021
 */
@Configuration
@PropertySource(value = { "classpath:migration.properties"}, encoding = "UTF-8", ignoreResourceNotFound = false)
public class DatasourceMigrationCivilia
{
	private static final Logger logger = LoggerFactory.getLogger(DatasourceMigrationCivilia.class);
	@Autowired
	Environment env;
	
	
	private HikariConfig hikariConfig() throws Exception
	{
		HikariConfig config = new HikariConfig();
		String       schema = env.getProperty("datasource.civ.schema");
		if (StringUtil.isNotEmpty(schema)) 
			config.setSchema(schema);
        config.setJdbcUrl            (this.env.getProperty("datasource.civ.url"                                   ));
        config.setUsername           (this.env.getProperty("datasource.civ.username"                              ));
        config.setPassword           (this.env.getProperty("datasource.civ.password"                              ));
        config.setDriverClassName    (this.env.getProperty("datasource.civ.driverclassname"                       ));
        config.setConnectionTestQuery(this.env.getProperty("datasource.civ.validationquery"                       ));
        config.setValidationTimeout  (this.env.getProperty("datasource.civ.validationquery.timeout", Integer.class));
        config.setMaximumPoolSize    (this.env.getProperty("datasource.civ.maxactive"              , Integer.class));
        config.setMinimumIdle        (this.env.getProperty("datasource.civ.minidle"                , Integer.class));
        config.setIdleTimeout        (this.env.getProperty("datasource.civ.idle.timeout"           , Integer.class));
        config.setAutoCommit         (this.env.getProperty("datasource.civ.defaultautocommit"      , Boolean.class));
        config.setPoolName           ("DatasourceCIV");
		return config;
	}
	
	@Bean(name= IDataSourceCiviliaConstants.ORACLE_CIV_DS_SPRING_BEAN_NAME)
	@ConditionalOnProperty(name="datasource.civ.enableMigration",havingValue="true")
	public DataSource datasourceCivilia() throws Exception
	{
		return new HikariDataSource(hikariConfig());
	}

	@Bean(name=IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	@ConditionalOnProperty(name="datasource.civ.enableMigration",havingValue="true")
	public DataSourceTransactionManager txMngrCommon() throws Exception
	{
		return new DataSourceTransactionManager(datasourceCivilia());
	}
	
	@Bean(name=IDataSourceCiviliaConstants.ORACLE_CIV_NAMEDPARAMETERJDBCTEMPLATE_SPRING_BEAN_NAME)
	@ConditionalOnProperty(name="datasource.civ.enableMigration",havingValue="true")
	public NamedParameterJdbcTemplate namedParameterJdbcTemplateCommon() throws Exception
	{
		return new NamedParameterJdbcTemplate(datasourceCivilia());
	}
	
	@Bean(name=IDataSourceCiviliaConstants.ORACLE_CIV_TEMPLATE_SPRING_BEAN_NAME)
	@ConditionalOnProperty(name="datasource.civ.enableMigration",havingValue="true")
	public JdbcTemplate jdbcTemplateCommon() throws Exception
	{
		return new JdbcTemplate(datasourceCivilia());	
	}
	
}