package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.RuoloReferenteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.RuoloReferenteRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.RuoloReferenteSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.ruolo_referente
 */
@Repository
public class RuoloReferenteRepository extends GenericCrudDao<RuoloReferenteDTO, RuoloReferenteSearch>{

    private final RuoloReferenteRowMapper rowMapper = new RuoloReferenteRowMapper();
    /**
     * select all method
     */
    @Override
    public List<RuoloReferenteDTO> select(){
        final String sql = new StringBuilder("select")
                                     .append(" \"id\"")
                                     .append(",\"descrizione\"")
                                     .append(",\"titolarita\"")
                                     .append(",\"altra_titolarita\"")
                                     .append(",\"attiva_specifica\"")
                                     .append(" from \"presentazione_istanza\".\"ruolo_referente\"")
                                     .append(" order by \"id\" ")
                                     .toString();
        return super.queryForListTxRead(sql, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"ruolo_referente\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public RuoloReferenteDTO find(RuoloReferenteDTO pk){
        final String sql = new StringBuilder("select")
                                     .append(" \"id\"")
                                     .append(",\"descrizione\"")
                                     .append(",\"titolarita\"")
                                     .append(",\"altra_titolarita\"")
                                     .append(",\"attiva_specifica\"")
                                     .append(" from \"presentazione_istanza\".\"ruolo_referente\"")
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
    public PaginatedList<RuoloReferenteDTO> search(RuoloReferenteSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder("select")
                                                   .append(" \"id\"")
                                                   .append(",\"descrizione\"")
                                                   .append(",\"titolarita\"")
                                                   .append(",\"altra_titolarita\"")
                                                   .append(",\"attiva_specifica\"")
                                                   .append(" from \"presentazione_istanza\".\"ruolo_referente\"")
                                                   ;
        if(StringUtil.isNotEmpty(search.getId())){
            sql.append(sep).append("lower(\"id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDescrizione())){
            sql.append(sep).append("lower(\"descrizione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDescrizione()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getTitolarita())){
            sql.append(sep).append("lower(\"titolarita\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getTitolarita()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getAltraTitolarita())){
            sql.append(sep).append("lower(\"altra_titolarita\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getAltraTitolarita()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getAttivaSpecifica())){
            sql.append(sep).append("lower(\"attiva_specifica\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getAttivaSpecifica()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "id":
                    sql.append(" sort by \"id\" ").append(sortType);
            case "descrizione":
                    sql.append(" sort by \"descrizione\" ").append(sortType);
            case "titolarita":
                    sql.append(" sort by \"titolarita\" ").append(sortType);
            case "altraTitolarita":
                    sql.append(" sort by \"altra_titolarita\" ").append(sortType);
            case "attivaSpecifica":
                    sql.append(" sort by \"attiva_specifica\" ").append(sortType);
                break;
            default:
            	    logger.warn(StringUtil.concateneString("value ", search.getSortBy(), " not valid for sort by"));
            	    break;
            }
        }
        return super.paginatedList(sql.toString(), this.rowMapper, search.getPage(), search.getLimit());
    }

    /**
     * insert all method
     */
    @Override
    public long insert(RuoloReferenteDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"ruolo_referente\"")
                                     .append("(\"id\"")
                                     .append(",\"descrizione\"")
                                     .append(",\"titolarita\"")
                                     .append(",\"altra_titolarita\"")
                                     .append(",\"attiva_specifica\"")
                                     .append(") values ")
                                     .append("(?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        parameters.add(entity.getDescrizione());
        parameters.add(entity.getTitolarita());
        parameters.add(entity.getAltraTitolarita());
        parameters.add(entity.getAttivaSpecifica());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(RuoloReferenteDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"ruolo_referente\"")
                                     .append(" set \"descrizione\" = ?")
                                     .append(", \"titolarita\" = ?")
                                     .append(", \"altra_titolarita\" = ?")
                                     .append(", \"attiva_specifica\" = ?")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getDescrizione());
        parameters.add(entity.getTitolarita());
        parameters.add(entity.getAltraTitolarita());
        parameters.add(entity.getAttivaSpecifica());
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(RuoloReferenteDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"ruolo_referente\"")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

}
