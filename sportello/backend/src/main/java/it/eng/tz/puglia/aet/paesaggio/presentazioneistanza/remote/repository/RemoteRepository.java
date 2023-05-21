package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.repository;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.rowmapper.RemoteRowMapper;

public abstract class RemoteRepository<T extends Serializable> {

	
	//viene costruito con il PostConstruct
	protected JdbcTemplate jdbcTemplate;
	
//	private HikariConfig hikariConfig() throws Exception
//	{
//		HikariConfig configuration = new HikariConfig();
//		String       schema = environment.getProperty("datasource.common.schema");
//		if (StringUtil.isNotEmpty(schema)) 
//			configuration.setSchema(schema);
//		
//		String pwd = this.environment.getProperty("datasource.password");
//		if(this.pwdCrypted) 
//			configuration.setPassword(CryptUtil.decrypt(pwd));
//		else
//			configuration.setPassword(pwd);
//		
//		configuration.setJdbcUrl            (this.environment.getProperty("datasource.common.url"                            ));
//		configuration.setUsername           (this.environment.getProperty("datasource.username"                              ));
//		configuration.setDriverClassName    (this.environment.getProperty("datasource.driverclassname"                       ));
//		configuration.setConnectionTestQuery(this.environment.getProperty("datasource.validationquery"                       ));
//		configuration.setValidationTimeout  (this.environment.getProperty("datasource.validationquery.timeout", Integer.class));
//		configuration.setMaximumPoolSize    (this.environment.getProperty("datasource.maxactive"              , Integer.class));
//		configuration.setMinimumIdle        (this.environment.getProperty("datasource.minidle"                , Integer.class));
//		configuration.setIdleTimeout        (this.environment.getProperty("datasource.idle.timeout"           , Integer.class));
//		configuration.setAutoCommit         (this.environment.getProperty("datasource.defaultautocommit"      , Boolean.class));
//		configuration.setPoolName           ("Datasource_common");
//		return configuration;
//	}
//	
//	
//	
//	/**
//	 * qui costruisco il jdbcTemplate a seconda della classe che mi estende AnagraficaDTO o EnteDTO
//	 * puntando al datasource corretto
//	 * @throws Exception
//	 */
//	@PostConstruct
//	private void initJdbcTemplate() throws Exception {
//		HikariConfig config = hikariConfig();
//		String schema = this.getSchema();
//		config.setSchema(schema);
//		DataSource ds=new HikariDataSource(config);
//		this.jdbcTemplate=new JdbcTemplate();
//		this.jdbcTemplate.setDataSource(ds);
//	}

	protected abstract String getSchema();
	protected abstract RemoteRowMapper<T> getRowMapper();
	protected abstract String getQueryForSelectAll();
	protected abstract String getQueryForFind(String codice);
	protected abstract String getQueryForFindAll(List<String> codices);
	

	/**
	 * find by pk method
	 */
	public List<T> selectAll() {
		try {
	        return this.jdbcTemplate.query(getQueryForSelectAll(), getRowMapper());
		} catch (EmptyResultDataAccessException empty) {
			return Collections.emptyList();
		}
	}

	/**
	 * find by pk method
	 */
	public Optional<T> find(String codice) {
		try {
			return Optional.of(this.jdbcTemplate.queryForObject(getQueryForFind(codice), getRowMapper(), codice));
		} catch (EmptyResultDataAccessException empty) {
			return Optional.empty();
		}
	}

	/**
	 * findAll by pks method
	 */
	public List<T> findAll(List<String> codices) {
		if (codices == null) {
			return Collections.emptyList();
		}
		codices = codices.stream().filter(Objects::nonNull).filter(code -> !code.isBlank())
				.collect(Collectors.toList());
		if (codices.isEmpty()) {
			return Collections.emptyList();
		}

		try {
			return this.jdbcTemplate.query(getQueryForFindAll(codices), getRowMapper());
		} catch (EmptyResultDataAccessException empty) {
			return Collections.emptyList();
		}
	}
	
}