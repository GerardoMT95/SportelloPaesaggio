package it.eng.tz.puglia.autpae.repository.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.util.crypt.CryptUtil;
import it.eng.tz.puglia.autpae.dto.ConfigurazionePECBean;
import it.eng.tz.puglia.autpae.rowmapper.ConfigurazionePECRowMapper;

@Repository
public class ConfigurazionePECRepository
{
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected NamedParameterJdbcTemplate namedJdbcTemplateWrite;
	@Autowired
	protected NamedParameterJdbcTemplate namedJdbcTemplateRead;
	
	@Value("${spring.application.name}")
	private String codiceApplicazione;
	
	public ConfigurazionePECBean getConfiguration() throws Exception
	{
		String sql = "select * from \""+codiceApplicazione+"\".\"configurazione_pec\" order by id desc limit 1";
		logger.info("Eseguo la query {}", sql);
		return namedJdbcTemplateRead.queryForObject(sql, new java.util.HashMap<>(), new ConfigurazionePECRowMapper());
	}
	
	public long count()
	{
		String sql = "select count(*) from \""+codiceApplicazione+"\".\"configurazione_pec\"";
		logger.info("Eseguo la query {}", sql);
		return namedJdbcTemplateRead.queryForObject(sql, new java.util.HashMap<>(), Long.class);
	}
	
	public int deleteAll() throws Exception
	{
		String sql = "delete from \""+codiceApplicazione+"\".\"configurazione_pec\"";
		logger.info("Eseguo la query {}", sql);
		return namedJdbcTemplateWrite.update(sql, new java.util.HashMap<>());
	}
	
	public int insert(ConfigurazionePECBean bean) throws Exception
	{
		String sql = new StringBuilder("insert into \"").append(codiceApplicazione).append("\".\"configurazione_pec\"")
								.append("(pec_indirizzo, pec_nome, pec_username, pec_password, pec_host_in, ")
								.append("pec_host_out, pec_protocollo_in, pec_protocollo_out, ")
								.append("pec_tipo_protocollo_in, pec_tipo_protocollo_out, pec_ssl_in, pec_ssl_out, ")
								.append("pec_tls_in, pec_tls_out, pec_porta_ssl_in, pec_porta_ssl_out, pec_porta_tls_in, ")
								.append("pec_porta_tls_out, pec_start_tls_in, pec_start_tls_out, ")
								.append("pec_autenticazione_in, pec_autenticazione_out) values")
								.append("(:pec_indirizzo, :pec_nome, :pec_username, :pec_password, :pec_host_in, ")
								.append(":pec_host_out, :pec_protocollo_in, :pec_protocollo_out, ")
								.append(":pec_tipo_protocollo_in, :pec_tipo_protocollo_out, :pec_ssl_in, :pec_ssl_out, ")
								.append(":pec_tls_in, :pec_tls_out, :pec_porta_ssl_in, :pec_porta_ssl_out, :pec_porta_tls_in, ")
								.append(":pec_porta_tls_out, :pec_start_tls_in, :pec_start_tls_out, ")
								.append(":pec_autenticazione_in, :pec_autenticazione_out)").toString();
		java.util.Map<String, ?> parameters = prepareParameters(bean);
		logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
		return namedJdbcTemplateWrite.update(sql, parameters);
	}
	
	public int update(ConfigurazionePECBean bean) throws Exception
	{
		String sql = new StringBuilder("update \"").append(codiceApplicazione).append("\".\"configurazione_pec\" set ")
								.append("pec_indirizzo = :pec_indirizzo, pec_nome = :pec_nome, pec_username = :pec_username, ")
								.append("pec_password = :pec_password, pec_host_in = :pec_host_in, pec_host_out = :pec_host_out, ")
								.append("pec_protocollo_in = :pec_protocollo_in, pec_protocollo_out = :pec_protocollo_out, ")
								.append("pec_tipo_protocollo_in = :pec_tipo_protocollo_in, pec_tipo_protocollo_out = :pec_tipo_protocollo_out, ")
								.append("pec_ssl_in = :pec_ssl_in, pec_ssl_out = :pec_ssl_out, pec_tls_in = :pec_tls_in, ")
								.append("pec_tls_out = :pec_tls_out, pec_porta_ssl_in = :pec_porta_ssl_in, pec_porta_ssl_out = :pec_porta_ssl_out, ")
								.append("pec_porta_tls_in = :pec_porta_tls_in, pec_porta_tls_out = :pec_porta_tls_out, ")
								.append("pec_start_tls_in = :pec_start_tls_in, pec_start_tls_out = :pec_start_tls_out, ")
								.append("pec_autenticazione_in = :pec_autenticazione_in, pec_autenticazione_out = :pec_autenticazione_out")
								.toString();
		java.util.Map<String, ?> parameters = prepareParameters(bean);
		logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
		return namedJdbcTemplateWrite.update(sql, parameters);
	}
	
	private java.util.Map<String, Object> prepareParameters(ConfigurazionePECBean bean)
	{
		java.util.Map<String, Object> parameters = new java.util.HashMap<>();
		parameters.put("pec_indirizzo", bean.getPecIndirizzo());
		parameters.put("pec_nome", bean.getPecNome());
		parameters.put("pec_username", bean.getPecUsername());
		parameters.put("pec_password", CryptUtil.encrypt(bean.getPecPassword()));
		parameters.put("pec_host_in", bean.getPecHostIn());
		parameters.put("pec_host_out", bean.getPecHostOut());
		parameters.put("pec_protocollo_in", bean.getPecProtocolloIn());
		parameters.put("pec_protocollo_out", bean.getPecProtocolloOut());
		parameters.put("pec_tipo_protocollo_in", bean.getPecTipoProtocolloIn().toString());
		parameters.put("pec_tipo_protocollo_out", bean.getPecTipoProtocolloOut().toString());
		parameters.put("pec_ssl_in", bean.getPecSslIn());
		parameters.put("pec_ssl_out", bean.getPecSslOut());
		parameters.put("pec_tls_in", bean.getPecTlsIn());
		parameters.put("pec_tls_out", bean.getPecTlsOut());
		parameters.put("pec_porta_ssl_in", bean.getPecPortaSslIn());
		parameters.put("pec_porta_ssl_out", bean.getPecPortaSslOut());
		parameters.put("pec_porta_tls_in", bean.getPecPortaTlsIn());
		parameters.put("pec_porta_tls_out", bean.getPecPortaTlsOut());
		parameters.put("pec_start_tls_in", bean.getPecStartTlsIn());
		parameters.put("pec_start_tls_out", bean.getPecStartTlsOut());
		parameters.put("pec_autenticazione_in", bean.getPecAutenticazioneIn());
		parameters.put("pec_autenticazione_out", bean.getPecAutenticazioneOut());
		return parameters;
	}
}
