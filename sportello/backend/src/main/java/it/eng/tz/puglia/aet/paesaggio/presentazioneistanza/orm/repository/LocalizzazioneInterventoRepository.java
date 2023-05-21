package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.LocalizzazioneInterventoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.LocalizzazioneInterventoRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.LocalizzazioneInterventoSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.localizzazione_intervento
 */
@Repository
public class LocalizzazioneInterventoRepository
		extends GenericCrudDao<LocalizzazioneInterventoDTO, LocalizzazioneInterventoSearch> {

	private final LocalizzazioneInterventoRowMapper rowMapper = new LocalizzazioneInterventoRowMapper();
	/**
	 * select all method
	 */
	final String selectAll = new StringBuilder("select").append(" \"pratica_id\"").append(",\"comune_id\"")
			.append(",\"indirizzo\"").append(",\"num_civico\"").append(",\"piano\"").append(",\"interno\"")
			.append(",\"dest_uso_att\"").append(",\"dest_uso_prog\"").append(",\"comune\"")
			.append(",\"sigla_provincia\"").append(",\"data_riferimento_catastale\"").append(",\"strade\"")
			.append(" from \"presentazione_istanza\".\"localizzazione_intervento\"").toString();

	@Override
	public List<LocalizzazioneInterventoDTO> select() {
		return super.queryForListTxRead(selectAll, this.rowMapper);
	}
	
	private String generateWhereClause(String baseSql, List<Object> parameters, LocalizzazioneInterventoSearch search)
	{
		String sep = " where ";
		final StringBuilder sql = new StringBuilder(baseSql);
		if (StringUtil.isNotEmpty(search.getPraticaId())) {
			sql.append(sep).append("lower(\"pratica_id\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getPraticaId()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getComuneId())) {
			sql.append(sep).append("lower(\"comune_id\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getComuneId()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getIndirizzo())) {
			sql.append(sep).append("lower(\"indirizzo\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getIndirizzo()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getNumCivico())) {
			sql.append(sep).append("lower(\"num_civico\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getNumCivico()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getPiano())) {
			sql.append(sep).append("lower(\"piano\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getPiano()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getInterno())) {
			sql.append(sep).append("lower(\"interno\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getInterno()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getDestUsoAtt())) {
			sql.append(sep).append("lower(\"dest_uso_att\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getDestUsoAtt()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getDestUsoProg())) {
			sql.append(sep).append("lower(\"dest_uso_prog\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getDestUsoProg()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getComune())) {
			sql.append(sep).append("lower(\"comune\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getComune()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getSiglaProvincia())) {
			sql.append(sep).append("lower(\"sigla_provincia\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getSiglaProvincia()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getDataRiferimentoCatastale())) {
			sql.append(sep).append("lower(\"data_riferimento_catastale\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getDataRiferimentoCatastale()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getStrade())) {
			sql.append(sep).append("lower(\"strade\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getStrade()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getSortBy())) {
			String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
			switch (search.getSortBy()) {
			case "praticaId":
				sql.append(" sort by \"pratica_id\" ").append(sortType);
			case "comuneId":
				sql.append(" sort by \"comune_id\" ").append(sortType);
			case "indirizzo":
				sql.append(" sort by \"indirizzo\" ").append(sortType);
			case "numCivico":
				sql.append(" sort by \"num_civico\" ").append(sortType);
			case "piano":
				sql.append(" sort by \"piano\" ").append(sortType);
			case "interno":
				sql.append(" sort by \"interno\" ").append(sortType);
			case "destUsoAtt":
				sql.append(" sort by \"dest_uso_att\" ").append(sortType);
			case "destUsoProg":
				sql.append(" sort by \"dest_uso_prog\" ").append(sortType);
			case "comune":
				sql.append(" sort by \"comune\" ").append(sortType);
			case "siglaProvincia":
				sql.append(" sort by \"sigla_provincia\" ").append(sortType);
			case "dataRiferimentoCatastale":
				sql.append(" sort by \"data_riferimento_catastale\" ").append(sortType);
			case "strade":
				sql.append(" sort by \"strade\" ").append(sortType);
				break;
			default:
				logger.warn(StringUtil.concateneString("value ", search.getSortBy(), " not valid for sort by"));
				break;
			}
		}
		return sql.toString();
	}

	public List<LocalizzazioneInterventoDTO> select(UUID praticaId,boolean txWrite) {
		final String sql = new StringBuilder(selectAll)
				.append(" where \"pratica_id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(praticaId);
		if(txWrite) {
			return super.queryForList(sql, this.rowMapper,parameters);
		}else {
			return super.queryForListTxRead(sql, this.rowMapper,parameters);	
		}
		
	}
	
	public List<LocalizzazioneInterventoDTO> select(UUID praticaId) {
		return this.select(praticaId,false);
	}

	/**
	 * count all method
	 */
	@Override
	public long count() {
		final String sql = new StringBuilder("select count(*)")
				.append(" from \"presentazione_istanza\".\"localizzazione_intervento\"").toString();
		return super.queryForObjectTxRead(sql, Long.class);
	}

    /**
     * count by idPratica method
     */
    public short countByIdPratica(UUID idPratica){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"localizzazione_intervento\"")
                                     .append(" where \"pratica_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(idPratica);
        return super.queryForObjectTxRead(sql, Short.class, parameters);
    }
    
	/**
	 * find by pk method
	 */
	@Override
	public LocalizzazioneInterventoDTO find(LocalizzazioneInterventoDTO pk) {
		final String sql = new StringBuilder(selectAll).append(" where \"pratica_id\" = ?")
				.append(" and \"comune_id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(pk.getPraticaId());
		parameters.add(pk.getComuneId());
		return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
	}

	/**
	 * search method
	 */
	@Override
	public PaginatedList<LocalizzazioneInterventoDTO> search(LocalizzazioneInterventoSearch search) {
		final List<Object> parameters = new ArrayList<Object>();
		String sql = generateWhereClause(selectAll, parameters, search);
		return super.paginatedList(sql, parameters, this.rowMapper, search.getPage(), search.getLimit());
	}
	
	public List<LocalizzazioneInterventoDTO> searchForList(LocalizzazioneInterventoSearch search) throws Exception
	{
		final List<Object> parameters = new ArrayList<Object>();
		String sql = generateWhereClause(selectAll, parameters, search);
		return queryForList(sql, rowMapper, parameters);
	}
	
	public List<Integer> searchForEnteId(UUID idPratica) throws Exception
	{
		final List<Object> parameters = new ArrayList<Object>();
		LocalizzazioneInterventoSearch search = new LocalizzazioneInterventoSearch();
		search.setPraticaId(idPratica.toString());
		String sql = "select \"comune_id\" from \"presentazione_istanza\".\"localizzazione_intervento\"";
		sql = generateWhereClause(sql, parameters, search);
		return queryForList(sql, Integer.class, parameters);
	}

	/**
	 * insert all method
	 */
	@Override
	public long insert(LocalizzazioneInterventoDTO entity) {
		final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"localizzazione_intervento\"")
				.append("(\"pratica_id\"").append(",\"comune_id\"").append(",\"indirizzo\"").append(",\"num_civico\"")
				.append(",\"piano\"").append(",\"interno\"").append(",\"dest_uso_att\"").append(",\"dest_uso_prog\"")
				.append(",\"comune\"").append(",\"sigla_provincia\"").append(",\"data_riferimento_catastale\"")
				.append(",\"strade\"").append(") values ").append("(?").append(",?").append(",?").append(",?")
				.append(",?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",?")
				.append(")").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getPraticaId());
		parameters.add(entity.getComuneId());
		parameters.add(entity.getIndirizzo());
		parameters.add(entity.getNumCivico());
		parameters.add(entity.getPiano());
		parameters.add(entity.getInterno());
		parameters.add(entity.getDestUsoAtt());
		parameters.add(entity.getDestUsoProg());
		parameters.add(entity.getComune());
		parameters.add(entity.getSiglaProvincia());
		parameters.add(entity.getDataRiferimentoCatastale());
		parameters.add(entity.getStrade());
		return super.update(sql, parameters);
	}

	/**
	 * update by pk method
	 */
	@Override
	public int update(LocalizzazioneInterventoDTO entity) {
		final String sql = new StringBuilder("update \"presentazione_istanza\".\"localizzazione_intervento\"")
				.append(" set \"indirizzo\" = ?").append(", \"num_civico\" = ?").append(", \"piano\" = ?")
				.append(", \"interno\" = ?").append(", \"dest_uso_att\" = ?").append(", \"dest_uso_prog\" = ?")
				.append(", \"comune\" = ?").append(", \"sigla_provincia\" = ?")
				.append(", \"data_riferimento_catastale\" = ?").append(", \"strade\" = ?")
				.append(" where \"pratica_id\" = ?").append(" and \"comune_id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getIndirizzo());
		parameters.add(entity.getNumCivico());
		parameters.add(entity.getPiano());
		parameters.add(entity.getInterno());
		parameters.add(entity.getDestUsoAtt());
		parameters.add(entity.getDestUsoProg());
		parameters.add(entity.getComune());
		parameters.add(entity.getSiglaProvincia());
		parameters.add(entity.getDataRiferimentoCatastale());
		parameters.add(entity.getStrade());
		parameters.add(entity.getPraticaId());
		parameters.add(entity.getComuneId());
		return super.update(sql, parameters);
	}

	/**
	 * delete by pk method
	 */
	@Override
	public int delete(LocalizzazioneInterventoDTO entity) {
		final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"localizzazione_intervento\"")
				.append(" where \"pratica_id\" = ?").append(" and \"comune_id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getPraticaId());
		parameters.add(entity.getComuneId());
		return super.update(sql, parameters);
	}
	
	/**
	 * seleziona gli idEnti sui quali la pratica sta effettivamente operando in localizzazione
	 */
	public List<Integer> selectEffettivo(UUID idPratica) 
	{
		
		final String sql = new StringBuilder("select distinct \"comune_id\"")
									.append(" from ")
									.append("\"presentazione_istanza\".\"localizzazione_intervento\" ")
									.append(" where ")
									.append("\"pratica_id\"  = :idPratica")
									.append(" UNION ")
									.append(" select distinct \"comune_id\"")
									.append(" from ")
									.append("\"presentazione_istanza\".\"particelle_catastali\"")
									.append(" where ")
									.append("\"pratica_id\" = :idPratica")
									.append(" UNION ")
									.append(" select distinct \"comune_id\"")
									.append(" from ")
									.append("\"presentazione_istanza\".\"parchi_paesaggi_immobili\"")
									.append(" where ")
									.append("\"pratica_id\" = :idPratica ")
									.append(" and \"selezionato\" ilike :selezionato")
									.toString();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idPratica", idPratica);
		parameters.put("selezionato", 'S');
		logger.info("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplateRead.queryForList(sql.toString(), parameters, Integer.class);
	}

}
