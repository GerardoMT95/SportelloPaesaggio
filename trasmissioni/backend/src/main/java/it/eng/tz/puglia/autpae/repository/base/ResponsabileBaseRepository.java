package it.eng.tz.puglia.autpae.repository.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.Responsabile;
import it.eng.tz.puglia.autpae.entity.ResponsabileDTO;
import it.eng.tz.puglia.autpae.rowmapper.ResponsabileRowMapper;
import it.eng.tz.puglia.autpae.search.ResponsabileSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table responsabile
 */
@Repository
public class ResponsabileBaseRepository extends GenericCrudDao<ResponsabileDTO, ResponsabileSearch, Long> {
	
	private static final Logger log = LoggerFactory.getLogger(ResponsabileBaseRepository.class);
	private final ResponsabileRowMapper rowMapper = new ResponsabileRowMapper();

	@Override
	public List<ResponsabileDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public long count(ResponsabileSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString(" select count(*) "                                           
											   ," from ", Responsabile.getTableName()
											   );
		sql = filter.getSqlWhereClause(new StringBuilder(sql)).toString();
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, filter.getSqlParameters());
		return namedJdbcTemplate.queryForObject(sql, filter.getSqlParameters(), Long.class);
	}

	@Override
	public ResponsabileDTO find(Long pk) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString(" select * "
												, " from " , Responsabile.getTableName()
												, " where ", Responsabile.id.columnName(), " = ?"
												);
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(pk);
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, pk);
		return super.queryForObject(sql, rowMapper, parameters);
	}

	@Override
	public Long insert(ResponsabileDTO entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString(" insert into ", Responsabile.getTableName()
												," ( "
												, Responsabile.nome.columnName(), ", "
												, Responsabile.cognome.columnName(), ", "
												, Responsabile.in_qualita_di.columnName(), ", "
												, Responsabile.servizio_settore_ufficio.columnName(), ", "
												, Responsabile.pec.columnName(), ", "
												, Responsabile.mail.columnName(), ", "
												, Responsabile.telefono.columnName(), ", "
												, Responsabile.riconoscimento_tipo.columnName(), ", "
												, Responsabile.riconoscimento_numero.columnName(), ", "
												, Responsabile.riconoscimento_data_rilascio.columnName(), ", "
												, Responsabile.riconoscimento_data_scadenza.columnName(), ", "
												, Responsabile.riconoscimento_rilasciato_da.columnName(), ", "
												, Responsabile.id_fascicolo.columnName()
												, " ) "
												, " values "
												, " (:", Responsabile.nome.columnName()
												, ", :", Responsabile.cognome.columnName()
												, ", :", Responsabile.in_qualita_di.columnName()
												, ", :", Responsabile.servizio_settore_ufficio.columnName()
												, ", :", Responsabile.pec.columnName()
												, ", :", Responsabile.mail.columnName()
												, ", :", Responsabile.telefono.columnName()
												, ", :", Responsabile.riconoscimento_tipo.columnName()
												, ", :", Responsabile.riconoscimento_numero.columnName()
												, ", :", Responsabile.riconoscimento_data_rilascio.columnName()
												, ", :", Responsabile.riconoscimento_data_scadenza.columnName()
												, ", :", Responsabile.riconoscimento_rilasciato_da.columnName()
												, ", :", Responsabile.id_fascicolo.columnName()
												, " ) "
												, " returning ", Responsabile.id.columnName()
												);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Responsabile.nome.columnName(), entity.getNome());
		parameters.put(Responsabile.cognome.columnName(), entity.getCognome());
		parameters.put(Responsabile.in_qualita_di.columnName(), entity.getInQualitaDi());
		parameters.put(Responsabile.servizio_settore_ufficio.columnName(), entity.getServizioSettoreUfficio());
		parameters.put(Responsabile.pec.columnName(), entity.getPec());
		parameters.put(Responsabile.mail.columnName(), entity.getMail());
		parameters.put(Responsabile.telefono.columnName(), entity.getTelefono());
		parameters.put(Responsabile.riconoscimento_tipo.columnName(), entity.getRiconoscimentoTipo()!=null?entity.getRiconoscimentoTipo().name():null);
		parameters.put(Responsabile.riconoscimento_numero.columnName(), entity.getRiconoscimentoNumero());
		parameters.put(Responsabile.riconoscimento_data_rilascio.columnName(), entity.getRiconoscimentoDataRilascio());
		parameters.put(Responsabile.riconoscimento_data_scadenza.columnName(), entity.getRiconoscimentoDataScadenza());
		parameters.put(Responsabile.riconoscimento_rilasciato_da.columnName(), entity.getRiconoscimentoRilasciatoDa());
		parameters.put(Responsabile.id_fascicolo.columnName(), entity.getIdFascicolo());
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql, parameters, Long.class);
	}

	@Override
	public int update(ResponsabileDTO entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString(  " update ", Responsabile.getTableName()
												   , " set ", Responsabile.nome.columnName(), " = ?, "
															, Responsabile.cognome.columnName(), " = ?, "
															, Responsabile.in_qualita_di.columnName(), " = ?, "
															, Responsabile.servizio_settore_ufficio.columnName(), " = ?, "
															, Responsabile.pec.columnName(), " = ?, "
															, Responsabile.mail.columnName(), " = ?, "
															, Responsabile.telefono.columnName(), " = ?, "
															, Responsabile.riconoscimento_tipo.columnName(), " = ?, "
															, Responsabile.riconoscimento_numero.columnName(), " = ?, "
															, Responsabile.riconoscimento_data_rilascio.columnName(), " = ?, "
															, Responsabile.riconoscimento_data_scadenza.columnName(), " = ?, "
															, Responsabile.riconoscimento_rilasciato_da.columnName(), " = ?, "
															, Responsabile.id_fascicolo.columnName(), " = ? "
												  ," where ", Responsabile.id.columnName(), " = ?"
												 );
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getNome());
		parameters.add(entity.getCognome());
		parameters.add(entity.getInQualitaDi());
		parameters.add(entity.getServizioSettoreUfficio());
		parameters.add(entity.getPec());
		parameters.add(entity.getMail());
		parameters.add(entity.getTelefono());
		parameters.add(entity.getRiconoscimentoTipo()!=null?entity.getRiconoscimentoTipo().name():null);
		parameters.add(entity.getRiconoscimentoNumero());
		parameters.add(entity.getRiconoscimentoDataRilascio());
		parameters.add(entity.getRiconoscimentoDataScadenza());
		parameters.add(entity.getRiconoscimentoRilasciatoDa());
		parameters.add(entity.getIdFascicolo());
		parameters.add(entity.getId());
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return super.update(sql, parameters);
	}

	@Override
	public int delete(ResponsabileSearch entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString(" delete from ", Responsabile.getTableName());
		sql = entity.getSqlWhereClause(new StringBuilder(sql)).toString();
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, entity.getSqlParameters());
		return namedJdbcTemplate.update(sql, entity.getSqlParameters());
	}

	@Override
	public PaginatedList<ResponsabileDTO> search(ResponsabileSearch bean) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString(" select * "
												, " from ", Responsabile.getTableName()
												);
		sql = bean.getSqlWhereClause(new StringBuilder(sql)).toString();
		sql = bean.getSqlOrderByClause(new StringBuilder(sql)).toString();
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, bean.getSqlParameters());
		return super.paginatedList(sql.toString(), bean.getSqlParameters(), rowMapper, bean.getPage(), bean.getLimit());
	}

}