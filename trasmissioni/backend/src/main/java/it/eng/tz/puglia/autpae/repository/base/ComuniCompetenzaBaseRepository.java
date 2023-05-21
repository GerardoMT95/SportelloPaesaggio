package it.eng.tz.puglia.autpae.repository.base;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.ComuniCompetenza;
import it.eng.tz.puglia.autpae.entity.ComuniCompetenzaDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.ComuniCompetenzaPK;
import it.eng.tz.puglia.autpae.rowmapper.ComuniCompetenzaRowMapper;
import it.eng.tz.puglia.autpae.search.ComuniCompetenzaSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Base Repository for table autpae.comuni_competenza
 */
@Repository
public class ComuniCompetenzaBaseRepository	extends GenericCrudDao<ComuniCompetenzaDTO, ComuniCompetenzaSearch, ComuniCompetenzaPK> {

	private static final Logger log = LoggerFactory.getLogger(ComuniCompetenzaBaseRepository.class);
    private final ComuniCompetenzaRowMapper rowMapper = new ComuniCompetenzaRowMapper();
    
	@Override
	public List<ComuniCompetenzaDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}
	
	@Override
	public long count(ComuniCompetenzaSearch filter) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public ComuniCompetenzaDTO find(ComuniCompetenzaPK pk) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}
	
	@Override
	public PaginatedList<ComuniCompetenzaDTO> search(ComuniCompetenzaSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(ComuniCompetenza.getTableName());
		
		filter.getSqlWhereClause(sql);
		filter.getSqlOrderByClause(sql);
		
		log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
		return super.paginatedList(sql.toString(), filter.getSqlParameters(), rowMapper, filter.getPage(), filter.getLimit());
	}

	@Override
	public ComuniCompetenzaPK insert(ComuniCompetenzaDTO entity) throws Exception {
		//final String sql = new StringBuilder("insert into \"autpae\".\"comuni_competenza\"")
		final String sql = new StringBuilder("insert into ")
				.append(ComuniCompetenza.getTableName())
				.append("(\"pratica_id\"").append(",\"ente_id\"")
				.append(",\"descrizione\"")
				.append(",\"cod_cat\"").append(",\"cod_istat\"")
				.append(",\"creazione\"")
				.append(",\"effettivo\"")
				.append(") values ")
				.append("(?")
				.append(",?")
				.append(",?")
				.append(",?")
				.append(",?")
				.append(",?")
				.append(",?")
				.append(")")
				.toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getPraticaId());
		parameters.add(entity.getEnteId());
		parameters.add(entity.getDescrizione());
		parameters.add(entity.getCodCat());
		parameters.add(entity.getCodIstat());
		parameters.add(entity.isCreazione());
		parameters.add(entity.isEffettivo());
		super.update(sql, parameters);
		return new ComuniCompetenzaPK(entity.getPraticaId(), entity.getEnteId(), entity.isCreazione());
	}

	@Override
	public int update(ComuniCompetenzaDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public int delete(ComuniCompetenzaSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("delete from ", ComuniCompetenza.getTableName()));

		filter.getSqlWhereClause(sql);
			   
        log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
        return namedJdbcTemplate.update(sql.toString(), filter.getSqlParameters());
	}

	/**
	 * @autor Adriano Colaianni
	 * @date 26 lug 2022
	 * @param idFascicolo
	 * @return
	 */
	public List<ComuniCompetenzaDTO> selectByIdPratica(Long idFascicolo) {
		StringBuilder sql = new StringBuilder(
				StringUtil.concateneString("SELECT * from ", 
						ComuniCompetenza.getTableName(),
						" WHERE pratica_id = ? "));
		return super.queryForList(sql.toString(), rowMapper,List.of(idFascicolo));
	}
	
}