package it.eng.tz.puglia.autpae.repository.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import it.eng.tz.puglia.autpae.dbMapping.Ricevuta;
import it.eng.tz.puglia.autpae.entity.RicevutaDTO;
import it.eng.tz.puglia.autpae.rowmapper.RicevutaRowMapper;
import it.eng.tz.puglia.autpae.search.RicevutaSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table ricevuta
 */
public class RicevutaBaseRepository  extends GenericCrudDao<RicevutaDTO, RicevutaSearch, Long>
{
	private static final Logger log = LoggerFactory.getLogger(RicevutaBaseRepository.class);
    private final RicevutaRowMapper rowMapper = new RicevutaRowMapper();
   
	@Override
	public List<RicevutaDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public long count(RicevutaSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(
				   StringUtil.concateneString("SELECT count (*) from ", Ricevuta.getTableName()));

		filter.getSqlWhereClause(sql);
				   
	    log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
	    return namedJdbcTemplate.queryForObject(sql.toString(), filter.getSqlParameters(), Long.class);
	}

	@Override
	public RicevutaDTO find(Long pk) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(Ricevuta.getTableName())
		   .append(" where ")
		   .append(Ricevuta.id.getCompleteName())
		   .append(" = :")
		   .append(Ricevuta.id.columnName());
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Ricevuta.id.columnName(), pk);
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, rowMapper);
	}

	@Override
	public Long insert(RicevutaDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
		
//		String sql1 = StringUtil.concateneString("insert into "+Ricevuta.getTableName()+" ( "
//                	, Ricevuta.id_corrispondenza.columnName()
//                ,",", Ricevuta.id_destinatario.columnName()
//                ,",", Ricevuta.tipo_ricevuta.columnName()
//                ,",", Ricevuta.errore.columnName()
//                ,",", Ricevuta.descrizione_errore.columnName()
//                ,",", Ricevuta.id_cms_eml.columnName()
//                ,",", Ricevuta.id_cms_dati_cert.columnName()
//                ,",", Ricevuta.id_cms_smime.columnName()
//                ,",", Ricevuta.data.columnName()
//                ," )");
//		String sql2 = StringUtil.concateneString("values ("
//				," :" + Ricevuta.id_corrispondenza.columnName()
//                ,",:" + Ricevuta.id_destinatario.columnName()
//                ,",:" + Ricevuta.tipo_ricevuta.columnName()
//                ,",:" + Ricevuta.errore.columnName()
//                ,",:" + Ricevuta.descrizione_errore.columnName()
//                ,",:" + Ricevuta.id_cms_eml.columnName()
//                ,",:" + Ricevuta.id_cms_dati_cert.columnName()
//                ,",:" + Ricevuta.id_cms_smime.columnName()
//                ,",:" + Ricevuta.data.columnName()
//                ," )");
//		SqlParameterSource parameters = new MapSqlParameterSource()
//				.addValue(Ricevuta.id_corrispondenza.columnName(), entity.getIdCorrispondenza())
//				.addValue(Ricevuta.id_destinatario.columnName(), entity.getIdDestinatario())
//				.addValue(Ricevuta.tipo_ricevuta.columnName(), entity.getTipoRicevuta().name())
//				.addValue(Ricevuta.errore.columnName(), entity.getErrore().name())
//				.addValue(Ricevuta.descrizione_errore.columnName(), entity.getDescrizioneErrore())
//				.addValue(Ricevuta.id_cms_eml.columnName(), entity.getIdCmsEml())
//				.addValue(Ricevuta.id_cms_dati_cert.columnName(), entity.getIdCmsDatiCert())
//				.addValue(Ricevuta.id_cms_smime.columnName(), entity.getIdCmsSmime())
//				.addValue(Ricevuta.data.columnName(), entity.getData());
//		
//		String sql = StringUtil.concateneString(sql1, sql2, " returning ", Ricevuta.id.columnName());
//		
//		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
//		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, Long.class);
	}

	@Override
	public int update(RicevutaDTO entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString("update "+Ricevuta.getTableName()+" set "
	            	, Ricevuta.id_corrispondenza.columnName(), "=:", Ricevuta.id_corrispondenza.columnName()
		        ,",", Ricevuta.id_destinatario.columnName(), "=:", Ricevuta.id_destinatario.columnName()
	            ,",", Ricevuta.tipo_ricevuta.columnName(), "=:", Ricevuta.tipo_ricevuta.columnName()
	            ,",", Ricevuta.errore.columnName(), "=:", Ricevuta.errore.columnName()
	            ,",", Ricevuta.descrizione_errore.columnName(), "=:", Ricevuta.descrizione_errore.columnName()
	            ,",", Ricevuta.id_cms_eml.columnName(), "=:", Ricevuta.id_cms_eml.columnName()
	            ,",", Ricevuta.id_cms_dati_cert.columnName(), "=:", Ricevuta.id_cms_dati_cert.columnName()
	            ,",", Ricevuta.id_cms_smime.columnName(), "=:", Ricevuta.id_cms_smime.columnName()
	            ,",", Ricevuta.data.columnName(), "=:", Ricevuta.data.columnName()
	            ,",", Ricevuta.id_allegato_dati_cert.columnName(), "=:", Ricevuta.id_allegato_dati_cert.columnName()
	            ," ");

		sql=StringUtil.concateneString(sql," where ",Ricevuta.getTableName(),".",Ricevuta.id.columnName()," = :",
																			 Ricevuta.id.columnName());
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(Ricevuta.id.columnName(), entity.getId())
				.addValue(Ricevuta.id_corrispondenza.columnName(), entity.getIdCorrispondenza())
				.addValue(Ricevuta.id_destinatario.columnName(), entity.getIdDestinatario())
				.addValue(Ricevuta.tipo_ricevuta.columnName(), entity.getTipoRicevuta().name())
				.addValue(Ricevuta.errore.columnName(), entity.getErrore().name())
				.addValue(Ricevuta.descrizione_errore.columnName(), entity.getDescrizioneErrore())
				.addValue(Ricevuta.id_cms_eml.columnName(), entity.getIdCmsEml())
				.addValue(Ricevuta.id_cms_dati_cert.columnName(), entity.getIdCmsDatiCert())
				.addValue(Ricevuta.id_cms_smime.columnName(), entity.getIdCmsSmime())
				.addValue(Ricevuta.data.columnName(), entity.getData())
				.addValue(Ricevuta.id_allegato_dati_cert.columnName(), entity.getIdAllegatoDatiCert());
		
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.update(sql, parameters);
	}

	@Override
	public int delete(RicevutaSearch search) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(
			   StringUtil.concateneString("delete from ", Ricevuta.getTableName()));

		search.getSqlWhereClause(sql);
			   
        log.trace("Sql -> {} Parameters -> {}", sql, search.getSqlParameters());
        return super.update(sql.toString(), search.getSqlParameters());
	}

	@Override
	public PaginatedList<RicevutaDTO> search(RicevutaSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(Ricevuta.getTableName());
		
		filter.getSqlWhereClause(sql);
		filter.getSqlOrderByClause(sql);
		
		log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
		return super.paginatedList(sql.toString(), filter.getSqlParameters(), rowMapper, filter.getPage(), filter.getLimit());
	}

}