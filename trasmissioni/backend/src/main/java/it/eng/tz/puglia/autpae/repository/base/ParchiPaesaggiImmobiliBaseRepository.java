package it.eng.tz.puglia.autpae.repository.base;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.tz.puglia.autpae.dbMapping.ParchiPaesaggiImmobili;
import it.eng.tz.puglia.autpae.entity.ParchiPaesaggiImmobiliDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.ParchiPaesaggiImmobiliPK;
import it.eng.tz.puglia.autpae.rowmapper.ParchiPaesaggiImmobiliRowMapper;
import it.eng.tz.puglia.autpae.search.ParchiPaesaggiImmobiliSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Base Repository for table autpae.parchi_paesaggi_immobili
 */

public class ParchiPaesaggiImmobiliBaseRepository extends GenericCrudDao<ParchiPaesaggiImmobiliDTO, ParchiPaesaggiImmobiliSearch, ParchiPaesaggiImmobiliPK>{

	private static final Logger log = LoggerFactory.getLogger(ParchiPaesaggiImmobiliBaseRepository.class);
    private final ParchiPaesaggiImmobiliRowMapper rowMapper = new ParchiPaesaggiImmobiliRowMapper();

	
    @Override
	public List<ParchiPaesaggiImmobiliDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public long count(ParchiPaesaggiImmobiliSearch filter) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public ParchiPaesaggiImmobiliDTO find(ParchiPaesaggiImmobiliPK pk) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public ParchiPaesaggiImmobiliPK insert(ParchiPaesaggiImmobiliDTO entity) {
		//final String sql = new StringBuilder("insert into \"parchi_paesaggi_immobili\"")
		final String sql = new StringBuilder("insert into ")
				.append(ParchiPaesaggiImmobili.getTableName())
				.append("(\"pratica_id\"")
				.append(",\"comune_id\"")
				.append(",\"tipo_vincolo\"")
				.append(",\"codice\"")
				.append(",\"descrizione\"")
				.append(",\"selezionato\"")
				.append(",\"info\"")
				.append(",\"data_inserimento\"")
				.append(") values ")
				.append("(?")
				.append(",?")
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
		parameters.add(entity.getComuneId());
		parameters.add(entity.getTipoVincolo());
		parameters.add(entity.getCodice());
		parameters.add(entity.getDescrizione());
		parameters.add(entity.getSelezionato());
		parameters.add(entity.getInfo());
		parameters.add(entity.getDataInserimento());
		super.update(sql, parameters);
		return new ParchiPaesaggiImmobiliPK(entity.getPraticaId(), entity.getComuneId(), entity.getTipoVincolo(), entity.getCodice());
	}

	@Override
	public int update(ParchiPaesaggiImmobiliDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public int delete(ParchiPaesaggiImmobiliSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("delete from ", ParchiPaesaggiImmobili.getTableName()));

		filter.getSqlWhereClause(sql);
			   
        log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
        return namedJdbcTemplate.update(sql.toString(), filter.getSqlParameters());
	}

	@Override
	public PaginatedList<ParchiPaesaggiImmobiliDTO> search(ParchiPaesaggiImmobiliSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(ParchiPaesaggiImmobili.getTableName());
		
		filter.getSqlWhereClause(sql);
		filter.getSqlOrderByClause(sql);
		
		log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
		return super.paginatedList(sql.toString(), filter.getSqlParameters(), rowMapper, filter.getPage(), filter.getLimit());
	}
    
}