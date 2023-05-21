package it.eng.tz.puglia.autpae.repository.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.dbMapping.Allegato;
import it.eng.tz.puglia.autpae.entity.AllegatoDTO;
import it.eng.tz.puglia.autpae.rowmapper.AllegatoRowMapper;
import it.eng.tz.puglia.autpae.search.AllegatoSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.UserUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table allegato
 */
public class AllegatoBaseRepository  extends GenericCrudDao<AllegatoDTO, AllegatoSearch, Long>
{
	private static final Logger log = LoggerFactory.getLogger(AllegatoBaseRepository.class);
    private final AllegatoRowMapper rowMapper = new AllegatoRowMapper();
   
	@Autowired private UserUtil userUtil;
	@Autowired private ApplicationProperties props;
	
	@Override
	public List<AllegatoDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public long count(AllegatoSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(
				   StringUtil.concateneString("SELECT count (*) from ", Allegato.getTableName()));

		filter.getSqlWhereClause(sql);
				   
	    log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
	    return namedJdbcTemplate.queryForObject(sql.toString(), filter.getSqlParameters(), Long.class);
	}

	@Override
	public AllegatoDTO find(Long pk) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(Allegato.getTableName())
		   .append(" where ")
		   .append(Allegato.id.getCompleteName())
		   .append(" = :")
		   .append(Allegato.id.columnName());
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Allegato.id.columnName(), pk);
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, rowMapper);
	}
	
	@Override
	public Long insert(AllegatoDTO entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
	
		String sql1 = StringUtil.concateneString("insert into "+Allegato.getTableName()+" ( "
                	, Allegato.nome.columnName()
                ,",", Allegato.descrizione.columnName()
                ,",", Allegato.titolo.columnName()
                ,",", Allegato.mime_type.columnName()
                ,",", Allegato.contenuto.columnName()
                ,",", Allegato.checksum.columnName()
                ,",", Allegato.deleted.columnName()
                ,",", Allegato.dimensione.columnName()
                ,",", Allegato.numero_protocollo_in.columnName()
                ,",", Allegato.numero_protocollo_out.columnName()
                ,",", Allegato.data_protocollo_in.columnName()
                ,",", Allegato.data_protocollo_out.columnName()
                ,",", Allegato.utente_inserimento.columnName()
                ,",", Allegato.username_inserimento.columnName()
                ,",", Allegato.path_cms.columnName()
                ,",", Allegato.id_richiesta_post_trasmissione.columnName()
                ,",", Allegato.deletable.columnName()
                ," )");
		String sql2 = StringUtil.concateneString("values ("
                ," :" + Allegato.nome.columnName()
                ,",:" + Allegato.descrizione.columnName()
                ,",:" + Allegato.titolo.columnName()
                ,",:" + Allegato.mime_type.columnName()
                ,",:" + Allegato.contenuto.columnName()
                ,",:" + Allegato.checksum.columnName()
                ,",:" + Allegato.deleted.columnName()
                ,",:" + Allegato.dimensione.columnName()
                ,",:" + Allegato.numero_protocollo_in.columnName()
                ,",:" + Allegato.numero_protocollo_out.columnName()
                ,",:" + Allegato.data_protocollo_in.columnName()
                ,",:" + Allegato.data_protocollo_out.columnName()
                ,",:" + Allegato.utente_inserimento.columnName()
                ,",:" + Allegato.username_inserimento.columnName()
                ,",:" + Allegato.path_cms.columnName()
                ,",:" + Allegato.id_richiesta_post_trasmissione.columnName()
                ,",:" + Allegato.deletable.columnName()
                ," )");
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(Allegato.nome.columnName(), entity.getNome())
				.addValue(Allegato.descrizione.columnName(), entity.getDescrizione())
				.addValue(Allegato.titolo.columnName(), entity.getTitolo())
				.addValue(Allegato.mime_type.columnName(), entity.getMimeType())
				.addValue(Allegato.contenuto.columnName(), entity.getContenuto())
				.addValue(Allegato.checksum.columnName(), entity.getChecksum())
				.addValue(Allegato.deleted.columnName(), entity.getDeleted())
				.addValue(Allegato.dimensione.columnName(), entity.getDimensione())
				.addValue(Allegato.numero_protocollo_in.columnName(), entity.getNumeroProtocolloIn())
				.addValue(Allegato.numero_protocollo_out.columnName(), entity.getNumeroProtocolloOut())
				.addValue(Allegato.data_protocollo_in.columnName(), entity.getDataProtocolloIn())
				.addValue(Allegato.data_protocollo_out.columnName(), entity.getDataProtocolloOut())
				.addValue(Allegato.username_inserimento.columnName(), 
						//userUtil.getMyProfile().getNome().concat(" ").concat(userUtil.getMyProfile().getCognome())
						userUtil.hasUserLogged()?
								userUtil.getMyProfile().getNome().concat(" ").concat(userUtil.getMyProfile().getCognome()):
									props.getBatchUsr())
				.addValue(Allegato.utente_inserimento.columnName(), 
						//userUtil.getMyProfile().getUsername()
						userUtil.hasUserLogged()?
								userUtil.getMyProfile().getUsername():
									props.getBatchUsr()
						)
				.addValue(Allegato.path_cms.columnName(), entity.getPathCms())
				.addValue(Allegato.id_richiesta_post_trasmissione.columnName(), entity.getIdRichiestaPostTrasmissione())
				.addValue(Allegato.deletable.columnName(), entity.getDeletable());
		
		
		String sql = StringUtil.concateneString(sql1, sql2, " returning ", Allegato.id.columnName());
		
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, Long.class);
	}

	@Override
	public int update(AllegatoDTO entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString("update "+Allegato.getTableName()+" set "
	            	, Allegato.nome.columnName(), "=:", Allegato.nome.columnName()
	            ,",", Allegato.descrizione.columnName(), "=:", Allegato.descrizione.columnName()
	            ,",", Allegato.titolo.columnName(), "=:", Allegato.titolo.columnName()
	            ,",", Allegato.mime_type.columnName(), "=:", Allegato.mime_type.columnName()
	            ,",", Allegato.contenuto.columnName(), "=:", Allegato.contenuto.columnName()
	            ,",", Allegato.checksum.columnName(), "=:", Allegato.checksum.columnName()
	            ,",", Allegato.deleted.columnName(), "=:", Allegato.deleted.columnName()
	            ,",", Allegato.dimensione.columnName(), "=:", Allegato.dimensione.columnName()
	            ,",", Allegato.numero_protocollo_in.columnName(), "=:", Allegato.numero_protocollo_in.columnName()
	            ,",", Allegato.numero_protocollo_out.columnName(), "=:", Allegato.numero_protocollo_out.columnName()
	            ,",", Allegato.data_protocollo_in.columnName(), "=:", Allegato.data_protocollo_in.columnName()
	            ,",", Allegato.data_protocollo_out.columnName(), "=:", Allegato.data_protocollo_out.columnName()
	            ,",", Allegato.utente_inserimento.columnName(), "=:", Allegato.utente_inserimento.columnName()
	            ,",", Allegato.username_inserimento.columnName(), "=:", Allegato.username_inserimento.columnName()
	            ,",", Allegato.path_cms.columnName(), "=:", Allegato.path_cms.columnName()
	            ,",", Allegato.id_richiesta_post_trasmissione.columnName(), "=:", Allegato.id_richiesta_post_trasmissione.columnName()
	            ,",", Allegato.id_annotazione_interna.columnName(), "=:", Allegato.id_annotazione_interna.columnName()
				);

		sql=StringUtil.concateneString(sql," where ",Allegato.getTableName(),".",Allegato.id.columnName()," = :",
																			 Allegato.id.columnName());  
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(Allegato.id.columnName(), entity.getId())
				.addValue(Allegato.nome.columnName(), entity.getNome())
				.addValue(Allegato.descrizione.columnName(), entity.getDescrizione())
				.addValue(Allegato.titolo.columnName(), entity.getTitolo())
				.addValue(Allegato.mime_type.columnName(), entity.getMimeType())
				.addValue(Allegato.contenuto.columnName(), entity.getContenuto())
				.addValue(Allegato.checksum.columnName(), entity.getChecksum())
				.addValue(Allegato.deleted.columnName(), entity.getDeleted())
				.addValue(Allegato.dimensione.columnName(), entity.getDimensione())
				.addValue(Allegato.numero_protocollo_in.columnName(), entity.getNumeroProtocolloIn())
				.addValue(Allegato.numero_protocollo_out.columnName(), entity.getNumeroProtocolloOut())
				.addValue(Allegato.data_protocollo_in.columnName(), entity.getDataProtocolloIn())
				.addValue(Allegato.data_protocollo_out.columnName(), entity.getDataProtocolloOut())
				.addValue(Allegato.utente_inserimento.columnName(), entity.getUtenteInserimento())
				.addValue(Allegato.username_inserimento.columnName(), entity.getUsernameInserimento())
				.addValue(Allegato.path_cms.columnName(), entity.getPathCms())
				.addValue(Allegato.id_richiesta_post_trasmissione.columnName(), entity.getIdRichiestaPostTrasmissione())
				.addValue(Allegato.id_annotazione_interna.columnName(), entity.getIdAnnotazioneInterna());
		
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.update(sql, parameters);
	}

	@Override
	public int delete(AllegatoSearch search) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(
			   StringUtil.concateneString("delete from ", Allegato.getTableName()));

		search.getSqlWhereClause(sql);
			   
		log.trace("Sql -> {} Parameters -> {}", sql, search.getSqlParameters());
        return namedJdbcTemplate.update(sql.toString(), search.getSqlParameters());
	}

	@Override
	public PaginatedList<AllegatoDTO> search(AllegatoSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(Allegato.getTableName());
		
		filter.getSqlWhereClause(sql);
		filter.getSqlOrderByClause(sql);
		
		log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
		return super.paginatedList(sql.toString(), filter.getSqlParameters(), rowMapper, filter.getPage(), filter.getLimit());
	}
	
}