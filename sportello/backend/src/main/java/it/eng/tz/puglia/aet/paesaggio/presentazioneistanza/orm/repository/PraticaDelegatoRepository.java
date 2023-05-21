package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AttivitaDaEspletare;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoReferente;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDelegatoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.SubentroDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.PraticaDelegatoRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PraticaDelegatoSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.pratica_delegato
 */
@Repository
public class PraticaDelegatoRepository extends GenericCrudDao<PraticaDelegatoDTO, PraticaDelegatoSearch> {

	private final PraticaDelegatoRowMapper rowMapper = new PraticaDelegatoRowMapper();
	/**
	 * select all method
	 */
	final String selectAll = new StringBuilder("select").append(" \"id\"").append(",\"indice\"").append(",\"cognome\"")
			.append(",\"nome\"").append(",\"codice_fiscale\"").append(",\"sesso\"").append(",\"data_nascita\"")
			.append(",\"id_nazione_nascita\"").append(",\"nazione_nascita\"").append(",\"id_comune_nascita\"")
			.append(",\"comune_nascita\"").append(",\"comune_nascita_estero\"").append(",\"id_nazione_residenza\"")
			.append(",\"nazione_residenza\"").append(",\"id_comune_residenza\"").append(",\"comune_residenza\"")
			.append(",\"comune_residenza_estero\"").append(",\"indirizzo_residenza\"").append(",\"civico_residenza\"")
			.append(",\"cap_residenza\"").append(",\"pec\"").append(",\"mail\"").append(",\"date_create\"")
			.append(",\"delgato_corrente\"").append(",\"id_provincia_residenza\"").append(",\"provincia_residenza\"")
			.append(",\"id_provincia_nascita\"").append(",\"provincia_nascita\"")
			.append(" from \"presentazione_istanza\".\"pratica_delegato\"").toString();

	@Override
	public List<PraticaDelegatoDTO> select() {
		return super.queryForListTxRead(selectAll, this.rowMapper);
	}

	/**
	 * count all method
	 */
	@Override
	public long count() {
		final String sql = new StringBuilder("select count(*)")
				.append(" from \"presentazione_istanza\".\"pratica_delegato\"").toString();
		return super.queryForObjectTxRead(sql, Long.class);
	}

	/**
	 * find by pk method
	 */
	@Override
	public PraticaDelegatoDTO find(PraticaDelegatoDTO pk) {
		final String sql = new StringBuilder(selectAll).append(" where \"id\" = ?").append(" and \"indice\" = ?")
				.toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(pk.getId());
		parameters.add(pk.getIndice());
		return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
	}

	/**
	 * search method
	 */
	@Override
	public PaginatedList<PraticaDelegatoDTO> search(PraticaDelegatoSearch search) {
		final List<Object> parameters = new ArrayList<Object>();
		String sep = " where ";
		final StringBuilder sql = new StringBuilder(selectAll);
		if (StringUtil.isNotEmpty(search.getId())) {
			sql.append(sep).append("(\"id\"::text) = ?");
			parameters.add(search.getId());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getIndice())) {
			sql.append(sep).append("lower(\"indice\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getIndice()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getCognome())) {
			sql.append(sep).append("lower(\"cognome\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getCognome()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getNome())) {
			sql.append(sep).append("lower(\"nome\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getNome()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getCodiceFiscale())) {
			sql.append(sep).append("\"codice_fiscale\" = ?");
			parameters.add(search.getCodiceFiscale());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getSesso())) {
			sql.append(sep).append("lower(\"sesso\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getSesso()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getDataNascita())) {
			sql.append(sep).append("lower(\"data_nascita\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getDataNascita()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getIdNazioneNascita())) {
			sql.append(sep).append("lower(\"id_nazione_nascita\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getIdNazioneNascita()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getNazioneNascita())) {
			sql.append(sep).append("lower(\"nazione_nascita\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getNazioneNascita()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getIdComuneNascita())) {
			sql.append(sep).append("lower(\"id_comune_nascita\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getIdComuneNascita()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getComuneNascita())) {
			sql.append(sep).append("lower(\"comune_nascita\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getComuneNascita()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getComuneNascitaEstero())) {
			sql.append(sep).append("lower(\"comune_nascita_estero\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getComuneNascitaEstero()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getIdNazioneResidenza())) {
			sql.append(sep).append("lower(\"id_nazione_residenza\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getIdNazioneResidenza()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getNazioneResidenza())) {
			sql.append(sep).append("lower(\"nazione_residenza\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getNazioneResidenza()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getIdComuneResidenza())) {
			sql.append(sep).append("lower(\"id_comune_residenza\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getIdComuneResidenza()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getComuneResidenza())) {
			sql.append(sep).append("lower(\"comune_residenza\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getComuneResidenza()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getComuneResidenzaEstero())) {
			sql.append(sep).append("lower(\"comune_residenza_estero\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getComuneResidenzaEstero()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getIndirizzoResidenza())) {
			sql.append(sep).append("lower(\"indirizzo_residenza\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getIndirizzoResidenza()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getCivicoResidenza())) {
			sql.append(sep).append("lower(\"civico_residenza\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getCivicoResidenza()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getCapResidenza())) {
			sql.append(sep).append("lower(\"cap_residenza\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getCapResidenza()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getPec())) {
			sql.append(sep).append("lower(\"pec\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getPec()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getMail())) {
			sql.append(sep).append("lower(\"mail\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getMail()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getDateCreate())) {
			sql.append(sep).append("lower(\"date_create\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getDateCreate()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getDelgatoCorrente())) {
			sql.append(sep).append("lower(\"delgato_corrente\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getDelgatoCorrente()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getSortBy())) {
			String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
			switch (search.getSortBy()) {
			case "id":
				sql.append(" order by \"id\" ").append(sortType);
				break;
			case "indice":
				sql.append(" order by \"indice\" ").append(sortType);
				break;
			case "cognome":
				sql.append(" order by \"cognome\" ").append(sortType);
				break;
			case "nome":
				sql.append(" order by \"nome\" ").append(sortType);
				break;
			case "codiceFiscale":
				sql.append(" order by \"codice_fiscale\" ").append(sortType);
				break;
			case "sesso":
				sql.append(" order by \"sesso\" ").append(sortType);
				break;
			case "dataNascita":
				sql.append(" order by \"data_nascita\" ").append(sortType);
				break;
			case "idNazioneNascita":
				sql.append(" order by \"id_nazione_nascita\" ").append(sortType);
				break;
			case "nazioneNascita":
				sql.append(" order by \"nazione_nascita\" ").append(sortType);
				break;
			case "idComuneNascita":
				sql.append(" order by \"id_comune_nascita\" ").append(sortType);
				break;
			case "comuneNascita":
				sql.append(" order by \"comune_nascita\" ").append(sortType);
				break;
			case "comuneNascitaEstero":
				sql.append(" order by \"comune_nascita_estero\" ").append(sortType);
				break;
			case "idNazioneResidenza":
				sql.append(" order by \"id_nazione_residenza\" ").append(sortType);
				break;
			case "nazioneResidenza":
				sql.append(" order by \"nazione_residenza\" ").append(sortType);
				break;
			case "idComuneResidenza":
				sql.append(" order by \"id_comune_residenza\" ").append(sortType);
				break;
			case "comuneResidenza":
				sql.append(" order by \"comune_residenza\" ").append(sortType);
				break;
			case "comuneResidenzaEstero":
				sql.append(" order by \"comune_residenza_estero\" ").append(sortType);
				break;
			case "indirizzoResidenza":
				sql.append(" order by \"indirizzo_residenza\" ").append(sortType);
				break;
			case "civicoResidenza":
				sql.append(" order by \"civico_residenza\" ").append(sortType);
				break;
			case "capResidenza":
				sql.append(" order by \"cap_residenza\" ").append(sortType);
				break;
			case "pec":
				sql.append(" order by \"pec\" ").append(sortType);
				break;
			case "mail":
				sql.append(" order by \"mail\" ").append(sortType);
				break;
			case "dateCreate":
				sql.append(" order by \"date_create\" ").append(sortType);
				break;
			case "delgatoCorrente":
				sql.append(" order by \"delgato_corrente\" ").append(sortType);
				break;
			default:
				logger.warn(StringUtil.concateneString("value ", search.getSortBy(), " not valid for sort by"));
				break;
			}
		}
		return super.paginatedList(sql.toString(), parameters, this.rowMapper, search.getPage(), search.getLimit());
	}

	/**
	 * insert all method
	 */
	@Override
	public long insert(PraticaDelegatoDTO entity) {
		final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"pratica_delegato\"")
				.append("(\"id\"").append(",\"indice\"").append(",\"cognome\"").append(",\"nome\"")
				.append(",\"codice_fiscale\"").append(",\"sesso\"").append(",\"data_nascita\"")
				.append(",\"id_nazione_nascita\"").append(",\"nazione_nascita\"").append(",\"id_comune_nascita\"")
				.append(",\"comune_nascita\"").append(",\"comune_nascita_estero\"").append(",\"id_nazione_residenza\"")
				.append(",\"nazione_residenza\"").append(",\"id_comune_residenza\"").append(",\"comune_residenza\"")
				.append(",\"comune_residenza_estero\"").append(",\"indirizzo_residenza\"")
				.append(",\"civico_residenza\"").append(",\"cap_residenza\"").append(",\"pec\"").append(",\"mail\"")
				.append(",\"delgato_corrente\"").append(",\"id_provincia_residenza\"")
				.append(",\"provincia_residenza\"").append(",\"id_provincia_nascita\"").append(",\"provincia_nascita\"")
				.append(") values ").append("(?").append(",?").append(",?").append(",?").append(",?").append(",?")
				.append(",?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",?")
				.append(",?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",?")
				.append(",?").append(",?").append(",?").append(",?").append(",?").append(")").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getId());
		parameters.add(entity.getIndice());
		parameters.add(entity.getCognome());
		parameters.add(entity.getNome());
		parameters.add(entity.getCodiceFiscale());
		parameters.add(entity.getSesso());
		parameters.add(entity.getDataNascita());
		parameters.add(entity.getIdNazioneNascita());
		parameters.add(entity.getNazioneNascita());
		parameters.add(entity.getIdComuneNascita());
		parameters.add(entity.getComuneNascita());
		parameters.add(entity.getComuneNascitaEstero());
		parameters.add(entity.getIdNazioneResidenza());
		parameters.add(entity.getNazioneResidenza());
		parameters.add(entity.getIdComuneResidenza());
		parameters.add(entity.getComuneResidenza());
		parameters.add(entity.getComuneResidenzaEstero());
		parameters.add(entity.getIndirizzoResidenza());
		parameters.add(entity.getCivicoResidenza());
		parameters.add(entity.getCapResidenza());
		parameters.add(entity.getPec());
		parameters.add(entity.getMail());
		parameters.add(entity.getDelgatoCorrente());
		parameters.add(entity.getIdProvinciaResidenza());
		parameters.add(entity.getProvinciaResidenza());
		parameters.add(entity.getIdProvinciaNascita());
		parameters.add(entity.getProvinciaNascita());
		return super.update(sql, parameters);
	}
	
	public short insertFromSubentro(SubentroDTO entity, UUID idPratica) throws Exception {
		final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"pratica_delegato\"")
				.append("(\"id\"").append(",\"indice\"").append(",\"cognome\"").append(",\"nome\"")
				.append(",\"codice_fiscale\"").append(",\"sesso\"").append(",\"data_nascita\"")
				.append(",\"id_nazione_nascita\"").append(",\"nazione_nascita\"").append(",\"id_comune_nascita\"")
				.append(",\"comune_nascita\"").append(",\"comune_nascita_estero\"").append(",\"id_nazione_residenza\"")
				.append(",\"nazione_residenza\"").append(",\"id_comune_residenza\"").append(",\"comune_residenza\"")
				.append(",\"comune_residenza_estero\"").append(",\"indirizzo_residenza\"")
				.append(",\"civico_residenza\"").append(",\"cap_residenza\"").append(",\"pec\"").append(",\"mail\"")
				.append(",\"delgato_corrente\"").append(",\"date_create\"").append(",\"id_provincia_nascita\"")
				.append(",\"provincia_nascita\"").append(",\"id_provincia_residenza\"").append(",\"provincia_residenza\"")
				.append(") values ").append("(?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",?")
				.append(",?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",?")
				.append(",?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",true")
				.append(",current_timestamp").append(",?").append(",?").append(",?").append(",?").append(")").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(idPratica);
		final short index = this.nextIndex(entity.getIdPratica());
		parameters.add(index);
		parameters.add(entity.getCognome());
		parameters.add(entity.getNome());
		parameters.add(entity.getCodiceFiscale());
		parameters.add(entity.getSesso());
		parameters.add(entity.getDataNascita());
		parameters.add(entity.getIdNazioneNascita());
		parameters.add(entity.getNazioneNascita());
		parameters.add(entity.getIdComuneNascita());
		parameters.add(entity.getComuneNascita());
		parameters.add(entity.getComuneNascitaEstero());
		parameters.add(entity.getIdNazioneResidenza());
		parameters.add(entity.getNazioneResidenza());
		parameters.add(entity.getIdComuneResidenza());
		parameters.add(entity.getComuneResidenza());
		parameters.add(entity.getComuneResidenzaEstero());
		parameters.add(entity.getIndirizzoResidenza());
		parameters.add(entity.getCivicoResidenza());
		parameters.add(entity.getCapResidenza());
		parameters.add(entity.getPec());
		parameters.add(entity.getMail());
		parameters.add(entity.getIdProvinciaNascita());
		parameters.add(entity.getProvinciaNascita());
		parameters.add(entity.getIdProvinciaResidenza());
		parameters.add(entity.getProvinciaResidenza());

		super.update(sql, parameters);
		
		return index;
	}


	/**
	 * update by pk method
	 */
	@Override
	public int update(PraticaDelegatoDTO entity) {
		final String sql = new StringBuilder("update \"presentazione_istanza\".\"pratica_delegato\"")
				.append(" set \"cognome\" = ?").append(", \"nome\" = ?").append(", \"codice_fiscale\" = ?")
				.append(", \"sesso\" = ?").append(", \"data_nascita\" = ?").append(", \"id_nazione_nascita\" = ?")
				.append(", \"nazione_nascita\" = ?").append(", \"id_comune_nascita\" = ?")
				.append(", \"comune_nascita\" = ?").append(", \"comune_nascita_estero\" = ?")
				.append(", \"id_nazione_residenza\" = ?").append(", \"nazione_residenza\" = ?")
				.append(", \"id_comune_residenza\" = ?").append(", \"comune_residenza\" = ?")
				.append(", \"comune_residenza_estero\" = ?").append(", \"indirizzo_residenza\" = ?")
				.append(", \"civico_residenza\" = ?").append(", \"cap_residenza\" = ?").append(", \"pec\" = ?")
				.append(", \"mail\" = ?").append(", \"date_create\" = ?").append(", \"delgato_corrente\" = ?")
				.append(", \"id_provincia_residenza\" = ?").append(", \"provincia_residenza\" = ?")
				.append(", \"id_provincia_nascita\" = ?").append(", \"provincia_nascita\" = ?")
				.append(" where \"id\" = ?").append(" and \"indice\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getCognome());
		parameters.add(entity.getNome());
		parameters.add(entity.getCodiceFiscale());
		parameters.add(entity.getSesso());
		parameters.add(entity.getDataNascita());
		parameters.add(entity.getIdNazioneNascita());
		parameters.add(entity.getNazioneNascita());
		parameters.add(entity.getIdComuneNascita());
		parameters.add(entity.getComuneNascita());
		parameters.add(entity.getComuneNascitaEstero());
		parameters.add(entity.getIdNazioneResidenza());
		parameters.add(entity.getNazioneResidenza());
		parameters.add(entity.getIdComuneResidenza());
		parameters.add(entity.getComuneResidenza());
		parameters.add(entity.getComuneResidenzaEstero());
		parameters.add(entity.getIndirizzoResidenza());
		parameters.add(entity.getCivicoResidenza());
		parameters.add(entity.getCapResidenza());
		parameters.add(entity.getPec());
		parameters.add(entity.getMail());
		parameters.add(entity.getDateCreate());
		parameters.add(entity.getDelgatoCorrente());
		parameters.add(entity.getIdProvinciaResidenza());
		parameters.add(entity.getProvinciaResidenza());
		parameters.add(entity.getIdProvinciaNascita());
		parameters.add(entity.getProvinciaNascita());
		parameters.add(entity.getId());
		parameters.add(entity.getIndice());
		return super.update(sql, parameters);
	}

	/**
	 * delete by pk method
	 */
	@Override
	public int delete(PraticaDelegatoDTO entity) {
		final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"pratica_delegato\"")
				.append(" where \"id\" = ?").append(" and \"indice\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getId());
		parameters.add(entity.getIndice());
		return super.update(sql, parameters);
	}

	public void setDelegatoCorrenteFalse(UUID idPratica) throws Exception {
		final String sql = "update \"presentazione_istanza\".\"pratica_delegato\" set delgato_corrente = false where id = :id";
		final MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", idPratica);
		logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
		namedJdbcTemplateWrite.update(sql, parameters);
	}

	public short nextIndex(UUID idPratica) throws Exception {
		short index = 0;
		final String sql = "select (max(indice) + 1) from \"presentazione_istanza\".\"pratica_delegato\" where id = :id";
		final MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", idPratica);
		logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
		List<Short> list = namedJdbcTemplateRead.queryForList(sql, parameters, Short.class);
		if (list != null && !list.isEmpty())
			index = list.get(0) != null ? list.get(0) : 1;
		return index;
	}

	public void deleteByPratica(UUID praticaId) {
		final String sql = "delete from \"presentazione_istanza\".\"pratica_delegato\" where id = :id";
		final MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", praticaId);
		logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
		namedJdbcTemplateWrite.update(sql, parameters);
	}
	
	
	/**
	 * 
	 * @author acolaianni
	 *
	 * @param praticaId
	 * @return delegato corrente altrimenti null
	 */
	public PraticaDelegatoDTO getDelegatoCorrente(UUID praticaId) {
		PraticaDelegatoDTO ret=null;
		final String sql = new StringBuilder(selectAll)
				.append(" where \"id\" = ?")
				.append(" and \"delgato_corrente\" = true ")
				.append(" limit 1 ")
				.toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(praticaId);
		try {
			ret = super.queryForObjectTxRead(sql, this.rowMapper, parameters);	
		}catch(EmptyResultDataAccessException e) {
			logger.error("delegato corrente inesistente praticaId={}",praticaId);
		}
		return ret;
	}
	
	
	/**
	 * check se ho la terna corretta per accedere all'ownership
	 * @author acolaianni
	 *
	 * @param codicePraticaAppptr
	 * @param codiceSegreto
	 * @param cfRichiedente
	 * @return owner altrimenti null
	 */
	public String  hasAccessCambioOwnership(String codicePraticaAppptr,String codiceSegreto,String cfRichiedente) {
		String ownerPratica=null;
		StringBuilder sb=new StringBuilder();
		final Map<String,Object> parameters = new HashMap<>();
		sb.append("select p.owner ")
				.append(" from presentazione_istanza.pratica p , presentazione_istanza.referenti r ")
				.append(" where ")
				.append(" r.pratica_id =p.id and r.tipo_referente = :tipoReferente ")
				.append(" and p.validazione_richiedente = true ")
				.append(" and p.codice_pratica_appptr = :codicePraticaAppptr ")
				.append(" and r.codice_fiscale = :cfRichiedente ")
				.append(" and p.codice_segreto = :codiceSegreto  ")
				.append(" and p.stato not in (:statiNonAmmessi) ");
		parameters.put("tipoReferente",TipoReferente.SD.name());
		parameters.put("codicePraticaAppptr",codicePraticaAppptr);
		parameters.put("cfRichiedente",cfRichiedente);
		parameters.put("codiceSegreto",codiceSegreto);
		parameters.put("statiNonAmmessi",
				AttivitaDaEspletare.getStatiNonAmmessiCambioOwnership().stream()
				.map(AttivitaDaEspletare::name).collect(Collectors.toList()));
		try {
			ownerPratica = super.namedQueryForObject(sb.toString(), String.class, parameters);
		}catch(EmptyResultDataAccessException e) {
			
		}
		return ownerPratica;
	}
	
	public int setUserCorrenteToFalse(UUID idPratica) {
		final String sql = new StringBuilder("update \"presentazione_istanza\".\"pratica_delegato\"")
				.append(" set \"delgato_corrente\" = false")
				.append(" where \"id\" = ?")
				.toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(idPratica);
		return super.update(sql, parameters);
	}


}
