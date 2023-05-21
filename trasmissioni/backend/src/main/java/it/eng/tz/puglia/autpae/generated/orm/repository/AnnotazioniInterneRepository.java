package it.eng.tz.puglia.autpae.generated.orm.repository;

import java.util.List;
import java.util.ArrayList;
import it.eng.tz.puglia.bean.PaginatedList;
import org.springframework.stereotype.Repository;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.puglia.autpae.generated.orm.dto.*;
import it.eng.tz.puglia.autpae.generated.orm.search.*;
import it.eng.tz.puglia.autpae.generated.orm.rowmapper.*;

/**
 * Repository for table autpae.annotazioni_interne
 */
@Repository
public class AnnotazioniInterneRepository extends GenericCrudDao<AnnotazioniInterneDTO, AnnotazioniInterneSearch>{

    private final AnnotazioniInterneRowMapper rowMapper = new AnnotazioniInterneRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id\"")
                                     .append(",\"id_fascicolo\"")
                                     .append(",\"id_organizzazione\"")
                                     .append(",\"esito_controllo\"")
                                     .append(",\"note\"")
                                     .append(",\"date_created\"")
                                     .append(",\"user_created\"")
                                     .append(",\"date_updated\"")
                                     .append(",\"user_updated\"")
                                     .append(" from  \"annotazioni_interne\"")
                                     .toString();
    @Override
    public List<AnnotazioniInterneDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"annotazioni_interne\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public AnnotazioniInterneDTO find(AnnotazioniInterneDTO pk){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getId());
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    /**
     * search method
     */
    @Override
    public PaginatedList<AnnotazioniInterneDTO> search(AnnotazioniInterneSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getId())){
            sql.append(sep).append("lower(\"id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdFascicolo())){
            sql.append(sep).append("lower(\"id_fascicolo\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdFascicolo()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdOrganizzazione())){
            sql.append(sep).append("lower(\"id_organizzazione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdOrganizzazione()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getEsitoControllo())){
            sql.append(sep).append("lower(\"esito_controllo\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getEsitoControllo()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getNote())){
            sql.append(sep).append("lower(\"note\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getNote()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDateCreated())){
            sql.append(sep).append("lower(\"date_created\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDateCreated()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getUserCreated())){
            sql.append(sep).append("lower(\"user_created\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getUserCreated()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDateUpdated())){
            sql.append(sep).append("lower(\"date_updated\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDateUpdated()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getUserUpdated())){
            sql.append(sep).append("lower(\"user_updated\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getUserUpdated()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "id":
                    sql.append(" sort by \"id\" ").append(sortType);
            case "idFascicolo":
                    sql.append(" sort by \"id_fascicolo\" ").append(sortType);
            case "idOrganizzazione":
                    sql.append(" sort by \"id_organizzazione\" ").append(sortType);
            case "esitoControllo":
                    sql.append(" sort by \"esito_controllo\" ").append(sortType);
            case "note":
                    sql.append(" sort by \"note\" ").append(sortType);
            case "dateCreated":
                    sql.append(" sort by \"date_created\" ").append(sortType);
            case "userCreated":
                    sql.append(" sort by \"user_created\" ").append(sortType);
            case "dateUpdated":
                    sql.append(" sort by \"date_updated\" ").append(sortType);
            case "userUpdated":
                    sql.append(" sort by \"user_updated\" ").append(sortType);
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
    public long insert(AnnotazioniInterneDTO entity){
        final String sql = new StringBuilder("insert into \"annotazioni_interne\"")
                                     .append("(\"id_fascicolo\"")
                                     .append(",\"id_organizzazione\"")
                                     .append(",\"esito_controllo\"")
                                     .append(",\"note\"")
                                     .append(",\"date_created\"")
                                     .append(",\"user_created\"")
                                     .append(",\"date_updated\"")
                                     .append(",\"user_updated\"")
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
        parameters.add(entity.getIdFascicolo());
        parameters.add(entity.getIdOrganizzazione());
        parameters.add(entity.getEsitoControllo());
        parameters.add(entity.getNote());
        parameters.add(entity.getDateCreated());
        parameters.add(entity.getUserCreated());
        parameters.add(entity.getDateUpdated());
        parameters.add(entity.getUserUpdated());
        return super.insertAndGetAutoincrementValue(sql, parameters, "id");
    }

    /**
     * update by pk method
     */
    @Override
    public int update(AnnotazioniInterneDTO entity){
        final String sql = new StringBuilder("update \"annotazioni_interne\"")
                                     .append(" set \"id_fascicolo\" = ?")
                                     .append(", \"id_organizzazione\" = ?")
                                     .append(", \"esito_controllo\" = ?")
                                     .append(", \"note\" = ?")
                                     .append(", \"date_created\" = ?")
                                     .append(", \"user_created\" = ?")
                                     .append(", \"date_updated\" = ?")
                                     .append(", \"user_updated\" = ?")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIdFascicolo());
        parameters.add(entity.getIdOrganizzazione());
        parameters.add(entity.getEsitoControllo());
        parameters.add(entity.getNote());
        parameters.add(entity.getDateCreated());
        parameters.add(entity.getUserCreated());
        parameters.add(entity.getDateUpdated());
        parameters.add(entity.getUserUpdated());
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(AnnotazioniInterneDTO entity){
        final String sql = new StringBuilder("delete from \"annotazioni_interne\"")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

}
