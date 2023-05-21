package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.DocumentazioneCdsDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDocumentazioneCdsDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.DocumentazioneCdsRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.PraticaDocumentazioneCdsRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PraticaDocumentazioneCdsSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.puglia.util.uuid.UuidUtil;

/**
 * Repository for table presentazione_istanza.pratica_documentazione_cds
 */
@Repository
public class PraticaDocumentazioneCdsRepository extends GenericCrudDao<PraticaDocumentazioneCdsDTO, PraticaDocumentazioneCdsSearch>{

    private final PraticaDocumentazioneCdsRowMapper rowMapper = new PraticaDocumentazioneCdsRowMapper();
    private final DocumentazioneCdsRowMapper docRowMapper = new DocumentazioneCdsRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id\"")
                                     .append(",\"id_pratica\"")
                                     .append(",\"id_tipo\"")
                                     .append(",\"id_documento_cds\"")
                                     .append(",\"user_create\"")
                                     .append(",\"date_create\"")
                                     .append(" from \"presentazione_istanza\".\"pratica_documentazione_cds\"")
                                     .toString();
    @Override
    public List<PraticaDocumentazioneCdsDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"pratica_documentazione_cds\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public PraticaDocumentazioneCdsDTO find(PraticaDocumentazioneCdsDTO pk){
        final String sql = new StringBuilder(selectAll)
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    /**
     * search method
     */
    @Override
    public PaginatedList<PraticaDocumentazioneCdsDTO> search(PraticaDocumentazioneCdsSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getId())){
            sql.append(sep).append("lower(\"id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getId()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdPratica())){
            sql.append(sep).append("lower(\"id_pratica\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdPratica()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdTipo())){
            sql.append(sep).append("lower(\"id_tipo\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdTipo()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdDocumentoCds())){
            sql.append(sep).append("lower(\"id_documento_cds\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdDocumentoCds()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getUserCreate())){
            sql.append(sep).append("lower(\"user_create\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getUserCreate()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDateCreate())){
            sql.append(sep).append("lower(\"date_create\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDateCreate()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "id":
                    sql.append(" order by \"id\" ").append(sortType);
                	   break;
            case "idPratica":
                    sql.append(" order by \"id_pratica\" ").append(sortType);
                	   break;
            case "idTipo":
                    sql.append(" order by \"id_tipo\" ").append(sortType);
                	   break;
            case "idDocumentoCds":
                    sql.append(" order by \"id_documento_cds\" ").append(sortType);
                	   break;
            case "userCreate":
                    sql.append(" order by \"user_create\" ").append(sortType);
                	   break;
            case "dateCreate":
                    sql.append(" order by \"date_create\" ").append(sortType);
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
    public long insert(PraticaDocumentazioneCdsDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"pratica_documentazione_cds\"")
                                     .append("(\"id\"")
                                     .append(",\"id_pratica\"")
                                     .append(",\"id_tipo\"")
                                     .append(",\"id_documento_cds\"")
                                     .append(",\"user_create\"")
                                     .append(",\"date_create\"")
                                     .append(") values ")
                                     .append("(?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(", current_timestamp")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(UuidUtil.newUUID().toString());
        parameters.add(entity.getIdPratica());
        parameters.add(entity.getIdTipo());
        parameters.add(entity.getIdDocumentoCds());
        parameters.add(SecurityUtil.getUsername());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(PraticaDocumentazioneCdsDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"pratica_documentazione_cds\"")
                                     .append(" set \"id\" = ?")
                                     .append(", \"id_pratica\" = ?")
                                     .append(", \"id_tipo\" = ?")
                                     .append(", \"id_documento_cds\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        parameters.add(entity.getIdPratica());
        parameters.add(entity.getIdTipo());
        parameters.add(entity.getIdDocumentoCds());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(PraticaDocumentazioneCdsDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"pratica_documentazione_cds\"")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        return super.update(sql, parameters);
    }
    
	public List<DocumentazioneCdsDto> list(final String idPratica, final boolean regione) {
		final StopWatch sw = LogUtil.startLog("list ", idPratica);
		this.logger.info("Start list {}", idPratica);
		try {
			final Map<String, Object> parameters = new HashMap<String, Object>();
			String sql = StringUtil.concateneString(
					"select vcds.\"id\", vcds.\"nome\", vcds.\"id_conferenza\",vcds.\"riferimento_conferenza\",",
					" vcds.\"stato_conferenza\",vcds.\"protocollo\", vcds.\"categoria\",",
					" vcds.\"tipo\", vcds.\"date_create\" as data_creazione, vcds.\"rif_esterno\", tipo.\"id\" as id_tipo_documento, tipo.\"descrizione\" as tipo_documento",
					" from \"presentazione_istanza\".\"v_cds_documentazione\" vcds",
					" join \"presentazione_istanza\".\"pratica_cds\" pcds on pcds.\"id_cds\" = vcds.\"id_conferenza\"",
					" left join \"presentazione_istanza\".\"pratica_documentazione_cds\" pdoc on pdoc.\"id_documento_cds\" = vcds.\"id\"",
					" left join \"presentazione_istanza\".\"tipo_contenuto\" tipo on tipo.\"id\" = pdoc.\"id_tipo\"",
					" where pcds.\"id_pratica\" = :idPratica "
//					.orderBy(new OrderSpecifier<>(Order.ASC, cdsDocument.tipoCaricaDocumento.descrizione))
//					.orderBy(new OrderSpecifier<>(Order.ASC, cds.categoria))
//					.orderBy(new OrderSpecifier<>(Order.ASC, cds.tipo))
					);
			parameters.put("idPratica", UUID.fromString(idPratica));
			logger.info("SQL-> " + sql.toString() + " PARAMETERS-> " + parameters);
			return namedJdbcTemplateRead.query(sql, parameters, docRowMapper);
		}finally {
			LogUtil.stopLog(sw);
		}
	}

	public DocumentazioneCdsDto find(final String idPratica
			                         ,final Long id
			                         ,final boolean regione
			                         ) {
		final StopWatch sw = LogUtil.startLog("find ", idPratica, " ", id, " ", regione);
		this.logger.info("Start find {} {} {}", idPratica, id, regione);
		try {
	        final List<Object> parameters = new ArrayList<Object>();
			String sql = StringUtil.concateneString(
					"select vcds.\"id\", vcds.\"nome\", vcds.\"id_conferenza\",vcds.\"riferimento_conferenza\",",
					" vcds.\"stato_conferenza\",vcds.\"protocollo\", vcds.\"categoria\",",
					" vcds.\"tipo\", vcds.\"date_create\" as data_creazione, vcds.\"rif_esterno\", tipo.\"id\" as id_tipo_documento, tipo.\"descrizione\" as tipo_documento",
					" from \"presentazione_istanza\".\"v_cds_documentazione\" vcds",
					" join \"presentazione_istanza\".\"pratica_cds\" pcds on pcds.\"id_cds\" = vcds.\"id_conferenza\"",
					" left join \"presentazione_istanza\".\"pratica_documentazione_cds\" pdoc on pdoc.\"id_documento_cds\" = vcds.\"id\"",
					" left join \"presentazione_istanza\".\"tipo_contenuto\" tipo on tipo.\"id\" = pdoc.\"id_tipo\"",
					" where pcds.\"id_pratica\" = ? ",
					" and vcds.\"id\" = ?;"
					);
			parameters.add(UUID.fromString(idPratica));
			parameters.add(id);
			logger.info("SQL-> " + sql.toString() + " PARAMETERS-> " + parameters);
			return super.queryForObjectTxRead(sql, docRowMapper, parameters);
		}finally {
			LogUtil.stopLog(sw);
		}
	}
}
