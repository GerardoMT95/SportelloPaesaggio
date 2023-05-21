package it.eng.tz.puglia.autpae.repository.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.Richiedente;
import it.eng.tz.puglia.autpae.entity.RichiedenteDTO;
import it.eng.tz.puglia.autpae.rowmapper.RichiedenteRowMapper;
import it.eng.tz.puglia.autpae.search.RichiedenteSearch;
import it.eng.tz.puglia.autpae.utility.validator.CapValidator;
import it.eng.tz.puglia.autpae.utility.validator.CodiceFiscaleValidator;
import it.eng.tz.puglia.autpae.utility.validator.PartitaIvaValidator;
import it.eng.tz.puglia.autpae.utility.validator.TelefonoValidator;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table richiedente
 */
@Repository
public class RichiedenteBaseRepository extends GenericCrudDao<RichiedenteDTO, RichiedenteSearch, Long> {
	
	private static final Logger log = LoggerFactory.getLogger(RichiedenteBaseRepository.class);
	private final RichiedenteRowMapper rowMapper = new RichiedenteRowMapper();

	@Override
	public List<RichiedenteDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public long count(RichiedenteSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString(" select count(*) "                                           
												," from ", Richiedente.getTableName());
		
		sql = filter.getSqlWhereClause(new StringBuilder(sql)).toString();
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, filter.getSqlParameters());
		return namedJdbcTemplate.queryForObject(sql, filter.getSqlParameters(), Long.class);
	}

	@Override
	public RichiedenteDTO find(Long pk) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString(" select * "
												, " from ", Richiedente.getTableName()
												, " where ", Richiedente.id.columnName(), " = ?"
												);
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(pk);
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, pk);
		return super.queryForObject(sql, rowMapper, parameters);
	}

	@Override
	public Long insert(RichiedenteDTO entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString(" insert into ", Richiedente.getTableName()
												," ( "
												, Richiedente.nome.columnName(), ", "
												, Richiedente.cognome.columnName(), ", "
												, Richiedente.codice_fiscale.columnName(), ", "
												, Richiedente.sesso.columnName(), ", "
												, Richiedente.data_nascita.columnName(), ", "
												, Richiedente.stato_nascita.columnName(), ", "
												, Richiedente.provincia_nascita.columnName(), ", "
												, Richiedente.comune_nascita.columnName(), ", "
												, Richiedente.stato_residenza.columnName(), ", "
												, Richiedente.provincia_residenza.columnName(), ", "
												, Richiedente.comune_residenza.columnName(), ", "
												, Richiedente.via_residenza.columnName(), ", "
												, Richiedente.numero_residenza.columnName(), ", "
												, Richiedente.cap.columnName(), ", "
												, Richiedente.pec.columnName(), ", "
												, Richiedente.email.columnName(), ", "
												, Richiedente.telefono.columnName(), ", "
												, Richiedente.ditta_societa.columnName(), ", "
												, Richiedente.ditta_in_qualita_di.columnName(), ", "
												, Richiedente.ditta_qualita_altro.columnName(), ", "
												, Richiedente.ditta_codice_fiscale.columnName(), ", "
												, Richiedente.ditta_partita_iva.columnName(), ", "
												, Richiedente.id_fascicolo.columnName()
												, " ) "
												, " values "
												, " (:", Richiedente.nome.columnName()
												, ", :", Richiedente.cognome.columnName()
												, ", :", Richiedente.codice_fiscale.columnName()
												, ", :", Richiedente.sesso.columnName()
												, ", :", Richiedente.data_nascita.columnName()
												, ", :", Richiedente.stato_nascita.columnName()
												, ", :", Richiedente.provincia_nascita.columnName()
												, ", :", Richiedente.comune_nascita.columnName()
												, ", :", Richiedente.stato_residenza.columnName()
												, ", :", Richiedente.provincia_residenza.columnName()
												, ", :", Richiedente.comune_residenza.columnName()
												, ", :", Richiedente.via_residenza.columnName()
												, ", :", Richiedente.numero_residenza.columnName()
												, ", :", Richiedente.cap.columnName()
												, ", :", Richiedente.pec.columnName()
												, ", :", Richiedente.email.columnName()
												, ", :", Richiedente.telefono.columnName()
												, ", :", Richiedente.ditta_societa.columnName()
												, ", :", Richiedente.ditta_in_qualita_di.columnName()
												, ", :", Richiedente.ditta_qualita_altro.columnName()
												, ", :", Richiedente.ditta_codice_fiscale.columnName()
												, ", :", Richiedente.ditta_partita_iva.columnName()
												, ", :", Richiedente.id_fascicolo.columnName()
												, " ) "
												, " returning ", Richiedente.id.columnName()
												);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Richiedente.nome.columnName(), entity.getNome());
		parameters.put(Richiedente.cognome.columnName(), entity.getCognome());
		parameters.put(Richiedente.codice_fiscale.columnName(), CodiceFiscaleValidator.normalize(entity.getCodiceFiscale()));
		parameters.put(Richiedente.sesso.columnName(), entity.getSesso());
		parameters.put(Richiedente.data_nascita.columnName(), entity.getDataNascita());
		parameters.put(Richiedente.stato_nascita.columnName(), entity.getStatoNascita());
		parameters.put(Richiedente.provincia_nascita.columnName(), entity.getProvinciaNascita());
		parameters.put(Richiedente.comune_nascita.columnName(), entity.getComuneNascita());
		parameters.put(Richiedente.stato_residenza.columnName(), entity.getStatoResidenza());
		parameters.put(Richiedente.provincia_residenza.columnName(), entity.getProvinciaResidenza());
		parameters.put(Richiedente.comune_residenza.columnName(), entity.getComuneResidenza());
		parameters.put(Richiedente.via_residenza.columnName(), entity.getViaResidenza());
		parameters.put(Richiedente.numero_residenza.columnName(), entity.getNumeroResidenza());
		parameters.put(Richiedente.cap.columnName(), CapValidator.normalize(entity.getCap()));
		parameters.put(Richiedente.pec.columnName(), entity.getPec()==null ? null : entity.getPec().trim().toLowerCase());
		parameters.put(Richiedente.email.columnName(), entity.getEmail()==null ? null : entity.getEmail().trim().toLowerCase());
		parameters.put(Richiedente.telefono.columnName(), TelefonoValidator.normalize(entity.getTelefono()));
		parameters.put(Richiedente.ditta_societa.columnName(), entity.getDittaSocieta());
		parameters.put(Richiedente.ditta_in_qualita_di.columnName(), entity.getDittaInQualitaDi());
		parameters.put(Richiedente.ditta_qualita_altro.columnName(), entity.getDittaQualitaAltro());
		parameters.put(Richiedente.ditta_codice_fiscale.columnName(), CodiceFiscaleValidator.normalize(entity.getDittaCodiceFiscale()));
		parameters.put(Richiedente.ditta_partita_iva.columnName(), PartitaIvaValidator.normalize(entity.getDittaPartitaIva()));
		parameters.put(Richiedente.id_fascicolo.columnName(), entity.getIdFascicolo());
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql, parameters, Long.class);
	}

	@Override
	public int update(RichiedenteDTO entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString(" update ", Richiedente.getTableName()
												, " set ", Richiedente.nome.columnName(), " = ?, "
												, Richiedente.cognome.columnName(), " = ?, "
												, Richiedente.codice_fiscale.columnName(), " = ?, "
												, Richiedente.sesso.columnName(), " = ?, "
												, Richiedente.data_nascita.columnName(), " = ?, "
												, Richiedente.stato_nascita.columnName(), " = ?, "
												, Richiedente.provincia_nascita.columnName(), " = ?, "
												, Richiedente.comune_nascita.columnName(), " = ?, "
												, Richiedente.stato_residenza.columnName(), " = ?, "
												, Richiedente.provincia_residenza.columnName(), " = ?, "
												, Richiedente.comune_residenza.columnName(), " = ?, "
												, Richiedente.via_residenza.columnName(), " = ?, "
												, Richiedente.numero_residenza.columnName(), " = ?, "
												, Richiedente.cap.columnName(), " = ?, "
												, Richiedente.pec.columnName(), " = ?, "
												, Richiedente.email.columnName(), " = ?, "
												, Richiedente.telefono.columnName(), " = ?, "
												, Richiedente.ditta_societa.columnName(), " = ?, "
												, Richiedente.ditta_in_qualita_di.columnName(), " = ?, "
												, Richiedente.ditta_qualita_altro.columnName(), " = ?, "
												, Richiedente.ditta_codice_fiscale.columnName(), " = ?, "
												, Richiedente.ditta_partita_iva.columnName(), " = ?, "
												, Richiedente.id_fascicolo.columnName(), " = ?"
												, " where ", Richiedente.id.columnName(), " = ?"
												);
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getNome());
		parameters.add(entity.getCognome());
		parameters.add(CodiceFiscaleValidator.normalize(entity.getCodiceFiscale()));
		parameters.add(entity.getSesso());
		parameters.add(entity.getDataNascita());
		parameters.add(entity.getStatoNascita());
		parameters.add(entity.getProvinciaNascita());
		parameters.add(entity.getComuneNascita());
		parameters.add(entity.getStatoResidenza());
		parameters.add(entity.getProvinciaResidenza());
		parameters.add(entity.getComuneResidenza());
		parameters.add(entity.getViaResidenza());
		parameters.add(entity.getNumeroResidenza());
		parameters.add(CapValidator.normalize(entity.getCap()));
		parameters.add(entity.getPec  ()==null ? null : entity.getPec  ().trim().toLowerCase());
		parameters.add(entity.getEmail()==null ? null : entity.getEmail().trim().toLowerCase());
		parameters.add(TelefonoValidator.normalize(entity.getTelefono()));
		parameters.add(entity.getDittaSocieta());
		parameters.add(entity.getDittaInQualitaDi());
		parameters.add(entity.getDittaQualitaAltro());
		parameters.add(CodiceFiscaleValidator.normalize(entity.getDittaCodiceFiscale()));
		parameters.add(PartitaIvaValidator.normalize(entity.getDittaPartitaIva()));
		parameters.add(entity.getIdFascicolo());
		parameters.add(entity.getId());
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return super.update(sql, parameters);
	}

	@Override
	public int delete(RichiedenteSearch entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString(" delete from ", Richiedente.getTableName());
		sql = entity.getSqlWhereClause(new StringBuilder(sql)).toString();
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, entity.getSqlParameters());
		return namedJdbcTemplate.update(sql, entity.getSqlParameters());
	}

	@Override
	public PaginatedList<RichiedenteDTO> search(RichiedenteSearch bean) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString(" select * "
												, " from ", Richiedente.getTableName());
		
		sql = bean.getSqlWhereClause(new StringBuilder(sql)).toString();
		sql = bean.getSqlOrderByClause(new StringBuilder(sql)).toString();
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, bean.getSqlParameters());
		return super.paginatedList(sql.toString(), bean.getSqlParameters(), rowMapper, bean.getPage(), bean.getLimit());
	}
	
	
	public RichiedenteDTO findByIdFascicolo(Long idFascicolo) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString(" select * "
												, " from ", Richiedente.getTableName()
												, " where ", Richiedente.id_fascicolo.columnName(), " = ?"
												);
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(idFascicolo);
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, idFascicolo);
		return super.queryForObject(sql, rowMapper, parameters);
	}

}