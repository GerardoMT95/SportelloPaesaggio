package it.eng.tz.puglia.autpae.generated.orm.repository;

import java.util.List;
import java.util.ArrayList;
import it.eng.tz.puglia.bean.PaginatedList;
import org.springframework.stereotype.Repository;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.puglia.autpae.enumeratori.StatoRichiestaPostTrasmissione;
import it.eng.tz.puglia.autpae.generated.orm.dto.*;
import it.eng.tz.puglia.autpae.generated.orm.search.*;
import it.eng.tz.puglia.autpae.generated.orm.rowmapper.*;

/**
 * Repository for table autpae.richieste_post_trasmissione
 */
@Repository
public class RichiestePostTrasmissioneRepository extends GenericCrudDao<RichiestePostTrasmissioneDTO, RichiestePostTrasmissioneSearch>{

    private final RichiestePostTrasmissioneRowMapper rowMapper = new RichiestePostTrasmissioneRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id\"")
                                     .append(",\"id_fascicolo\"")
                                     .append(",\"motivazione\"")
                                     .append(",\"stato\"")
                                     .append(",\"tipo\"")
                                     .append(",\"date_created\"")
                                     .append(",\"user_created\"")
                                     .append(",\"date_updated\"")
                                     .append(",\"user_updated\"")
                                     .append(",\"id_corrispondenza\"")
                                     .append(" from  \"richieste_post_trasmissione\"")
                                     .toString();
    @Override
    public List<RichiestePostTrasmissioneDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"richieste_post_trasmissione\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }
    
    /**
     * count drafts method
     */
    public long countDrafts(Long idFascicolo){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"richieste_post_trasmissione\"")
                                     .append(" where stato = ? and id_fascicolo = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(StatoRichiestaPostTrasmissione.Bozza.name());
        parameters.add(idFascicolo);
        return super.queryForObjectTxRead(sql, Long.class, parameters);
    }

    /**
     * find by pk method
     */
    @Override
    public RichiestePostTrasmissioneDTO find(RichiestePostTrasmissioneDTO pk){
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
    public PaginatedList<RichiestePostTrasmissioneDTO> search(RichiestePostTrasmissioneSearch search){
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
        if(StringUtil.isNotEmpty(search.getMotivazione())){
            sql.append(sep).append("lower(\"motivazione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getMotivazione()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getStato())){
            sql.append(sep).append("lower(\"stato\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getStato()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getTipo())){
            sql.append(sep).append("lower(\"tipo\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getTipo()));
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
        if(StringUtil.isNotEmpty(search.getIdCorrispondenza())){
            sql.append(sep).append("lower(\"id_corrispondenza\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdCorrispondenza()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "id":
                    sql.append(" sort by \"id\" ").append(sortType);
            case "idFascicolo":
                    sql.append(" sort by \"id_fascicolo\" ").append(sortType);
            case "motivazione":
                    sql.append(" sort by \"motivazione\" ").append(sortType);
            case "stato":
                    sql.append(" sort by \"stato\" ").append(sortType);
            case "tipo":
                    sql.append(" sort by \"tipo\" ").append(sortType);
            case "dateCreated":
                    sql.append(" sort by \"date_created\" ").append(sortType);
            case "userCreated":
                    sql.append(" sort by \"user_created\" ").append(sortType);
            case "dateUpdated":
                    sql.append(" sort by \"date_updated\" ").append(sortType);
            case "userUpdated":
                    sql.append(" sort by \"user_updated\" ").append(sortType);
            case "idCorrispondenza":
                    sql.append(" sort by \"id_corrispondenza\" ").append(sortType);
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
    public long insert(RichiestePostTrasmissioneDTO entity){
        final String sql = new StringBuilder("insert into \"richieste_post_trasmissione\"")
                                     .append("(\"id_fascicolo\"")
                                     .append(",\"motivazione\"")
                                     .append(",\"stato\"")
                                     .append(",\"tipo\"")
                                     .append(",\"date_created\"")
                                     .append(",\"user_created\"")
                                     .append(",\"date_updated\"")
                                     .append(",\"user_updated\"")
                                     .append(",\"id_corrispondenza\"")
                                     .append(") values ")
                                     .append("(?")
                                     .append(",?")
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
        parameters.add(entity.getMotivazione());
        parameters.add(entity.getStato());
        parameters.add(entity.getTipo());
        parameters.add(entity.getDateCreated());
        parameters.add(entity.getUserCreated());
        parameters.add(entity.getDateUpdated());
        parameters.add(entity.getUserUpdated());
        parameters.add(entity.getIdCorrispondenza());
        return super.insertAndGetAutoincrementValue(sql, parameters, "id");
    }

    /**
     * update by pk method
     */
    @Override
    public int update(RichiestePostTrasmissioneDTO entity){
        final String sql = new StringBuilder("update \"richieste_post_trasmissione\"")
                                     .append(" set \"id_fascicolo\" = ?")
                                     .append(", \"motivazione\" = ?")
                                     .append(", \"stato\" = ?")
                                     .append(", \"tipo\" = ?")
                                     .append(", \"date_created\" = ?")
                                     .append(", \"user_created\" = ?")
                                     .append(", \"date_updated\" = now() ")
                                     .append(", \"user_updated\" = ?")
                                     .append(", \"id_corrispondenza\" = ?")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIdFascicolo());
        parameters.add(entity.getMotivazione());
        parameters.add(entity.getStato());
        parameters.add(entity.getTipo());
        parameters.add(entity.getDateCreated());
        parameters.add(entity.getUserCreated());
        //parameters.add(entity.getDateUpdated());
        parameters.add(entity.getUserUpdated());
        parameters.add(entity.getIdCorrispondenza());
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(RichiestePostTrasmissioneDTO entity){
        final String sql = new StringBuilder("delete from \"richieste_post_trasmissione\"")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

}
