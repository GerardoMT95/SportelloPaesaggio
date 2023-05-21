package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiTipoContenutoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.AllegatiTipoContenutoRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.AllegatiTipoContenutoSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.allegati_tipo_contenuto
 */
@Repository
public class AllegatiTipoContenutoRepository extends GenericCrudDao<AllegatiTipoContenutoDTO, AllegatiTipoContenutoSearch>{

    private final AllegatiTipoContenutoRowMapper rowMapper = new AllegatiTipoContenutoRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"allegati_id\"")
                                     .append(",\"tipo_contenuto_id\"")
                                     .append(" from \"presentazione_istanza\".\"allegati_tipo_contenuto\"")
                                     .toString();
    @Override
    public List<AllegatiTipoContenutoDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"allegati_tipo_contenuto\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public AllegatiTipoContenutoDTO find(AllegatiTipoContenutoDTO pk){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"allegati_id\" = ?")
                                     .append(" and \"tipo_contenuto_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getAllegatiId());
        parameters.add(pk.getTipoContenutoId());
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    /**
     * search method
     */
    @Override
    public PaginatedList<AllegatiTipoContenutoDTO> search(AllegatiTipoContenutoSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getAllegatiId())){
            sql.append(sep).append("lower(\"allegati_id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getAllegatiId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getTipoContenutoId())){
            sql.append(sep).append("lower(\"tipo_contenuto_id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getTipoContenutoId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "allegatiId":
                    sql.append(" sort by \"allegati_id\" ").append(sortType);
            case "tipoContenutoId":
                    sql.append(" sort by \"tipo_contenuto_id\" ").append(sortType);
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
    public long insert(AllegatiTipoContenutoDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"allegati_tipo_contenuto\"")
                                     .append("(\"allegati_id\"")
                                     .append(",\"tipo_contenuto_id\"")
                                     .append(") values ")
                                     .append("(?")
                                     .append(",?")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getAllegatiId());
        parameters.add(entity.getTipoContenutoId());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(AllegatiTipoContenutoDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"allegati_tipo_contenuto\"")
                                     .append(" where \"allegati_id\" = ?")
                                     .append(" and \"tipo_contenuto_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getAllegatiId());
        parameters.add(entity.getTipoContenutoId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(AllegatiTipoContenutoDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"allegati_tipo_contenuto\"")
                                     .append(" where \"allegati_id\" = ?")
                                     .append(" and \"tipo_contenuto_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getAllegatiId());
        parameters.add(entity.getTipoContenutoId());
        return super.update(sql, parameters);
    }

}
