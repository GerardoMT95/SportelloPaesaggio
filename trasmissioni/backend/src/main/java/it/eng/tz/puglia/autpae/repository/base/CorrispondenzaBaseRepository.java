package it.eng.tz.puglia.autpae.repository.base;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.dbMapping.AllegatoCorrispondenza;
import it.eng.tz.puglia.autpae.dbMapping.AllegatoEnte;
import it.eng.tz.puglia.autpae.dbMapping.Corrispondenza;
import it.eng.tz.puglia.autpae.dbMapping.FascicoloCorrispondenza;
import it.eng.tz.puglia.autpae.entity.CorrispondenzaDTO;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.rowmapper.CorrispondenzaRowMapper;
import it.eng.tz.puglia.autpae.search.CorrispondenzaSearch;
import it.eng.tz.puglia.autpae.utility.Stringhe;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.UserUtil;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.regione.puglia.webmail.be.dto.ConfigurazioneCasellaPostaleDto;

/**
 * Repository for table corrispondenza
 */
public class CorrispondenzaBaseRepository  extends GenericCrudDao<CorrispondenzaDTO, CorrispondenzaSearch, Long>
{
	private static final Logger log = LoggerFactory.getLogger(CorrispondenzaBaseRepository.class);
    private final CorrispondenzaRowMapper rowMapper = new CorrispondenzaRowMapper();
    
    @Autowired private UserUtil userUtil;
    @Autowired private ApplicationProperties props;
    
	//@Value("${email.mittente}")
	//private String mittenteEmail;
    @Autowired
	@Qualifier("casellaMittenteApplicazione")
	private ConfigurazioneCasellaPostaleDto ccpd;
	
   
	@Override
	public List<CorrispondenzaDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public long count(final CorrispondenzaSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("SELECT count (*) from ", Corrispondenza.getTableName()));
		if (filter.getIdFascicolo()!=null)	{
			sql.append(" left join ")
			   .append(FascicoloCorrispondenza.getTableName())
			   .append(" on ")
			   .append(FascicoloCorrispondenza.id_corrispondenza.getCompleteName())
			   .append(" = ")
			   .append(Corrispondenza.id.getCompleteName());
		}
		filter.getSqlWhereClause(sql);
				   
	    log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
	    return namedJdbcTemplate.queryForObject(sql.toString(), filter.getSqlParameters(), Long.class);
	}

	@Override
	public CorrispondenzaDTO find(final Long pk) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(Corrispondenza.getTableName())
		   .append(" where ")
		   .append(Corrispondenza.id.getCompleteName())
		   .append(" = :")
		   .append(Corrispondenza.id.columnName());
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Corrispondenza.id.columnName(), pk);
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, rowMapper);
	}
	
	
	public Long insert(final CorrispondenzaDTO entity,final boolean forMigration) {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		String sql1 = StringUtil.concateneString("insert into "+Corrispondenza.getTableName()+" ( "
                	, Corrispondenza.mittente_email.columnName()
                ,",", Corrispondenza.mittente_denominazione.columnName()
                ,",", Corrispondenza.mittente_username.columnName()
                ,",", Corrispondenza.mittente_ente.columnName()
                ,",", Corrispondenza.oggetto.columnName()
                ,",", Corrispondenza.text.columnName()
                ,",", Corrispondenza.id_eml_cmis.columnName()
                ,",", Corrispondenza.message_id.columnName()
                ,",", Corrispondenza.stato.columnName()
                ,",", Corrispondenza.comunicazione.columnName()
                ,",", Corrispondenza.bozza.columnName()
                ,",", Corrispondenza.data_invio.columnName()
                ," )");
		String sql2 = StringUtil.concateneString(" values ("
                ," :" + Corrispondenza.mittente_email.columnName()
                ,",:" + Corrispondenza.mittente_denominazione.columnName()
                ,",:" + Corrispondenza.mittente_username.columnName()
                ,",:" + Corrispondenza.mittente_ente.columnName()
                ,",:" + Corrispondenza.oggetto.columnName()
                ,",:" + Corrispondenza.text.columnName()
                ,",:" + Corrispondenza.id_eml_cmis.columnName()
                ,",:" + Corrispondenza.message_id.columnName()
                ,",:" + Corrispondenza.stato.columnName()
                ,",:" + Corrispondenza.comunicazione.columnName()
                ,",:" + Corrispondenza.bozza.columnName()
                ,",:" + Corrispondenza.data_invio.columnName()
                ," )");
		String gruppoId="";
		String username="";
		try {
			gruppoId=userUtil.getGruppo_Id();
			username=userUtil.getMyProfile().getUsername();
		}catch(Exception e) {
			log.info("No user info...");
		}
		MapSqlParameterSource parameters = new MapSqlParameterSource();
				if(forMigration) {
					parameters.addValue(Corrispondenza.mittente_email.columnName(), entity.getMittenteEmail());
					parameters.addValue(Corrispondenza.mittente_denominazione.columnName(), entity.getMittenteDenominazione());
					parameters.addValue(Corrispondenza.mittente_username.columnName(), entity.getMittenteUsername());
					parameters.addValue(Corrispondenza.mittente_ente.columnName(), entity.getMittenteEnte());
					parameters.addValue(Corrispondenza.data_invio.columnName(), entity.getDataInvio());
				}else {
					parameters.addValue(Corrispondenza.mittente_email.columnName(), ccpd.getCasellaPostale().getIndirizzoMail());
					parameters.addValue(Corrispondenza.mittente_denominazione.columnName(), ccpd.getCasellaPostale().getNomeVisualizzato());
					parameters.addValue(Corrispondenza.mittente_username.columnName(), username);
					parameters.addValue(Corrispondenza.mittente_ente.columnName(), gruppoId);
					parameters.addValue(Corrispondenza.data_invio.columnName(), new Date());
				}
				parameters
				.addValue(Corrispondenza.oggetto.columnName(), entity.getOggetto())
				.addValue(Corrispondenza.text.columnName(), entity.getTesto())
				.addValue(Corrispondenza.id_eml_cmis.columnName(), entity.getIdEmlCmis())
				.addValue(Corrispondenza.message_id.columnName(), entity.getMessageId())
				.addValue(Corrispondenza.stato.columnName(), entity.isStato())
				.addValue(Corrispondenza.comunicazione.columnName(), entity.isComunicazione())
				.addValue(Corrispondenza.bozza.columnName(), entity.isBozza());
		
		String sql = StringUtil.concateneString(sql1, sql2, " returning ", Corrispondenza.id.columnName());
		
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, Long.class);
	}

	@Override
	public Long insert(final CorrispondenzaDTO entity) throws Exception {
		return this.insert(entity, false);
	}

	@Override
	public int update(final CorrispondenzaDTO entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString("update "+Corrispondenza.getTableName()+" set "
	            	, Corrispondenza.mittente_email.columnName(), "=:", Corrispondenza.mittente_email.columnName()
	            ,",", Corrispondenza.mittente_denominazione.columnName(), "=:", Corrispondenza.mittente_denominazione.columnName()
	            ,",", Corrispondenza.mittente_username.columnName(), "=:", Corrispondenza.mittente_username.columnName()
	            ,",", Corrispondenza.mittente_ente.columnName(), "=:", Corrispondenza.mittente_ente.columnName()
	            ,",", Corrispondenza.oggetto.columnName(), "=:", Corrispondenza.oggetto.columnName()
	            ,",", Corrispondenza.text.columnName(), "=:", Corrispondenza.text.columnName()
	            ,",", Corrispondenza.bozza.columnName(), "=:", Corrispondenza.bozza.columnName());

		sql = StringUtil.concateneString(sql," where ",Corrispondenza.getTableName(),".",Corrispondenza.id.columnName()," = :",
																						 Corrispondenza.id.columnName());  
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(Corrispondenza.id.columnName(), entity.getId())
				.addValue(Corrispondenza.mittente_email.columnName(), ccpd.getCasellaPostale().getIndirizzoMail())
				.addValue(Corrispondenza.mittente_denominazione.columnName(), 
						userUtil.hasUserLogged()?
								userUtil.getMyProfile().getNome().concat(" ").concat(userUtil.getMyProfile().getCognome()):
									props.getBatchUsr()
						//userUtil.getMyProfile().getNome().concat(" ").concat(userUtil.getMyProfile().getCognome())
						)
				.addValue(Corrispondenza.mittente_username.columnName(), 
						//userUtil.getMyProfile().getUsername()
						userUtil.hasUserLogged()?
								userUtil.getMyProfile().getUsername():
									props.getBatchUsr()
						)
				.addValue(Corrispondenza.mittente_ente.columnName(), userUtil.hasUserLogged() ? userUtil.getGruppo_Id() : props.getBatchUsr())
				.addValue(Corrispondenza.oggetto.columnName(), entity.getOggetto())
				.addValue(Corrispondenza.text.columnName(), entity.getTesto())
				.addValue(Corrispondenza.bozza.columnName(), entity.isBozza());
		
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.update(sql, parameters);
	}

	@Override
	public int delete(final CorrispondenzaSearch search) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("delete from ", Corrispondenza.getTableName()));
		
		if (search.getIdFascicolo()!=null)	{
			sql.append(" left join ")
			   .append(FascicoloCorrispondenza.getTableName())
			   .append(" on ")
			   .append(FascicoloCorrispondenza.id_corrispondenza.getCompleteName())
			   .append(" = ")
			   .append(Corrispondenza.id.getCompleteName());
		}
		search.getSqlWhereClause(sql);
			   
        log.trace("Sql -> {} Parameters -> {}", sql, search.getSqlParameters());
        return namedJdbcTemplate.update(sql.toString(), search.getSqlParameters());
	}

	@Override
	public PaginatedList<CorrispondenzaDTO> search(final CorrispondenzaSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select distinct "
																	,     Corrispondenza.id.getCompleteName()
																	,",", Corrispondenza.message_id.getCompleteName()
																	,",", Corrispondenza.mittente_email.getCompleteName()
													                ,",", Corrispondenza.mittente_denominazione.getCompleteName()
													                ,",", Corrispondenza.mittente_username.getCompleteName()
													                ,",", Corrispondenza.mittente_ente.getCompleteName()
													                ,",", Corrispondenza.oggetto.getCompleteName()
													                ,",", Corrispondenza.text.getCompleteName()
													                ,",", Corrispondenza.stato.getCompleteName()
													                ,",", Corrispondenza.bozza.getCompleteName()
													                ,",", Corrispondenza.comunicazione.getCompleteName()
													                ,",", Corrispondenza.data_invio.getCompleteName()));
		sql.append(" from " + Corrispondenza.getTableName());
		
		if (filter.getIdFascicolo()!=null)	{
			sql.append(" left join ")
			   .append(FascicoloCorrispondenza.getTableName())
			   .append(" on ")
			   .append(FascicoloCorrispondenza.id_corrispondenza.getCompleteName())
			   .append(" = ")
			   .append(Corrispondenza.id.getCompleteName());
		}
		if (userUtil.hasUserLogged() && userUtil.getGruppo()!=Gruppi.ADMIN)	{
			sql.append(" left join ")
			   .append(AllegatoCorrispondenza.getTableName())
			   .append(" on ")
			   .append(AllegatoCorrispondenza.id_corrispondenza.getCompleteName())
			   .append(" = ")
			   .append(Corrispondenza.id.getCompleteName())
			   .append(" left join ")
			   .append(AllegatoEnte.getTableName())
			   .append(" on ")
			   .append(AllegatoCorrispondenza.id_allegato.getCompleteName())
			   .append(" = ")
			   .append(AllegatoEnte.id_allegato.getCompleteName());
		}
		
		
		filter.getSqlWhereClause(sql);
		
		if (userUtil.hasUserLogged() && userUtil.getGruppo()!=Gruppi.ADMIN)	{
			sql .append(" AND ( ")
			    .append(AllegatoEnte.codice.getCompleteName())
			    .append(" is NULL")
				.append(" OR ")
				.append(AllegatoEnte.codice.getCompleteName())
				.append(" = ")
				.append(Stringhe.apicizza(userUtil.getGruppo().name()))
				.append(" )");
		}
		
		filter.getSqlOrderByClause(sql);
		
		log.trace("Sql -> {} Parameters -> {}", sql.toString(), filter.getSqlParameters());
		return super.paginatedList(sql.toString(), filter.getSqlParameters(), rowMapper, filter.getPage(), filter.getLimit());
	}
	
}