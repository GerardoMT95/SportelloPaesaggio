package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.FascicoloCorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.FascicoloCorrispondenzaRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.FascicoloCorrispondenzaSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.fascicolo_corrispondenza
 */
@Repository
public class FascicoloCorrispondenzaRepository extends GenericCrudDao<FascicoloCorrispondenzaDTO, FascicoloCorrispondenzaSearch>{

    private final FascicoloCorrispondenzaRowMapper rowMapper = new FascicoloCorrispondenzaRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id_corrispondenza\"")
                                     .append(",\"id_pratica\"")
                                     .append(" from \"presentazione_istanza\".\"fascicolo_corrispondenza\"")
                                     .toString();
    @Override
    public List<FascicoloCorrispondenzaDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }
    
    /**
     * fascicoli legati alla corrispondenza.
     * @author acolaianni
     *
     * @param idCorrispondenza
     * @return
     */
    public List<FascicoloCorrispondenzaDTO> findByIdCorrispondenza(Long idCorrispondenza){
    	final String sql = new StringBuilder(selectAll)
                .append(" where \"id_corrispondenza\" = ? ")
                .toString();
    	final List<Object> parameters = new ArrayList<Object>();
        parameters.add(idCorrispondenza);
        return super.queryForListTxRead(sql, this.rowMapper,parameters);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"fascicolo_corrispondenza\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public FascicoloCorrispondenzaDTO find(FascicoloCorrispondenzaDTO pk){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"id_corrispondenza\" = ?")
                                     .append(" and \"id_pratica\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getIdCorrispondenza());
        parameters.add(pk.getIdPratica());
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    /**
     * search method
     */
    @Override
    public PaginatedList<FascicoloCorrispondenzaDTO> search(FascicoloCorrispondenzaSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getIdCorrispondenza())){
            sql.append(sep).append("lower(\"id_corrispondenza\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdCorrispondenza()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdPratica())){
            sql.append(sep).append("lower(\"id_pratica\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdPratica()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "idCorrispondenza":
                    sql.append(" order by \"id_corrispondenza\" ").append(sortType);
                	   break;
            case "idPratica":
                    sql.append(" order by \"id_pratica\" ").append(sortType);
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
    public long insert(FascicoloCorrispondenzaDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"fascicolo_corrispondenza\"")
                                     .append("(\"id_corrispondenza\"")
                                     .append(",\"id_pratica\"")
                                     .append(") values ")
                                     .append("(?")
                                     .append(",?")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIdCorrispondenza());
        parameters.add(entity.getIdPratica());
        return super.update(sql, parameters);
    }
    
    /**
     * 
     * @author acolaianni
     *
     * @param idPratica
     * @param idCorrispondenza
     * @return
     */
    public long insert(UUID idPratica,long idCorrispondenza){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"fascicolo_corrispondenza\"")
                                     .append("(\"id_corrispondenza\"")
                                     .append(",\"id_pratica\"")
                                     .append(") values ")
                                     .append("(?")
                                     .append(",?")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(idCorrispondenza);
        parameters.add(idPratica);
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(FascicoloCorrispondenzaDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"fascicolo_corrispondenza\"")
                                     .append(" where \"id_corrispondenza\" = ?")
                                     .append(" and \"id_pratica\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIdCorrispondenza());
        parameters.add(entity.getIdPratica());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(FascicoloCorrispondenzaDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"fascicolo_corrispondenza\"")
                                     .append(" where \"id_corrispondenza\" = ?")
                                     .append(" and \"id_pratica\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIdCorrispondenza());
        parameters.add(entity.getIdPratica());
        return super.update(sql, parameters);
    }

}
