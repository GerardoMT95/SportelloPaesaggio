package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DisclaimerPraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.DisclaimerPraticaRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.DisclaimerPraticaSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.disclaimer_pratica
 */
@Repository
public class DisclaimerPraticaRepository extends GenericCrudDao<DisclaimerPraticaDTO, DisclaimerPraticaSearch>{

    private final DisclaimerPraticaRowMapper rowMapper = new DisclaimerPraticaRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append("\"disclaimer_id\"")
                                     .append(",\"flag\"")
                                     .append(",\"pratica_id\"")
                                     .append(" from \"presentazione_istanza\".\"disclaimer_pratica\"")
                                     .toString();
    @Override
    public List<DisclaimerPraticaDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }
    
    
    public List<Integer> selectIdByPratica(UUID praticaId,String flag,boolean txWrite){
    	final List<Object> parameters = new ArrayList<Object>();
    	final StringBuilder sql = new StringBuilder(selectAll)
                .append(" where \"pratica_id\"= ? ");
    	parameters.add(praticaId);
    	
                if(flag!=null) {
                	sql.append(" and \"flag\" = ? ");
                	parameters.add(flag);
                }		
    	List<Integer> ret = null;
    	List<DisclaimerPraticaDTO> res=null;
    	if(txWrite) {
    		res= super.queryForList(sql.toString(), this.rowMapper,parameters);
    	}else {
    		res= super.queryForListTxRead(sql.toString(), this.rowMapper,parameters);	
    	}
        if(res!=null) {
        	ret=
        	res.stream()
        	.map(el->el.getDisclaimerId())
        	.collect(Collectors.toList());
        }
        return ret;
    }
    
    public List<DisclaimerPraticaDTO> selectByPratica(UUID praticaId,String flag){
    	final List<Object> parameters = new ArrayList<Object>();
    	final StringBuilder sql = new StringBuilder(selectAll)
                .append(" where \"pratica_id\"= ? ");
    	parameters.add(praticaId);
    	
                if(flag!=null) {
                	sql.append(" and \"flag\" = ? ");
                	parameters.add(flag);
                }		
    	
        return  super.queryForListTxRead(sql.toString(), this.rowMapper,parameters);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"disclaimer_pratica\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public DisclaimerPraticaDTO find(DisclaimerPraticaDTO pk){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"disclaimer_id\" = ? and \"pratica_id\" ")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getDisclaimerId());
        parameters.add(pk.getPraticaId());
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    /**
     * search method
     */
    @Override
    public PaginatedList<DisclaimerPraticaDTO> search(DisclaimerPraticaSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getDisclaimerId())){
            sql.append(sep).append("lower(\"disclaimer_id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDisclaimerId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getFlag())){
            sql.append(sep).append("lower(\"flag\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getFlag()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPraticaId())){
            sql.append(sep).append("lower(\"pratica_id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPraticaId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "disclaimerId":
                    sql.append(" sort by \"disclaimer_id\" ").append(sortType);
            case "flag":
                    sql.append(" sort by \"flag\" ").append(sortType);
            case "praticaId":
                    sql.append(" sort by \"pratica_id\" ").append(sortType);
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
    public long insert(DisclaimerPraticaDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"disclaimer_pratica\"")
                                     .append("(\"disclaimer_id\"")
                                     .append(",\"flag\"")
                                     .append(",\"pratica_id\"")
                                     .append(") values ")
                                     .append("(?")
                                     .append(",?")
                                     .append(",?")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        //parameters.add(entity.getId());
        parameters.add(entity.getDisclaimerId());
        parameters.add(entity.getFlag());
        parameters.add(entity.getPraticaId());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(DisclaimerPraticaDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"disclaimer_pratica\"")
                                     .append(" set \"disclaimer_id\" = ?")
                                     .append(", \"flag\" = ?")
                                     .append(", \"pratica_id\" = ?")
                                     .append(" where \"pratica_id\" = ? and  \"disclaimer_id\" = ? ")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getDisclaimerId());
        parameters.add(entity.getFlag());
        parameters.add(entity.getPraticaId());
        parameters.add(entity.getPraticaId());
        parameters.add(entity.getDisclaimerId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(DisclaimerPraticaDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"disclaimer_pratica\"")
        							 .append(" where \"pratica_id\" = ? and  \"disclaimer_id\" = ? ")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getPraticaId());
        parameters.add(entity.getDisclaimerId());
        return super.update(sql, parameters);
    }
    
    /**
     * delete by praticaId
     */
    public int deleteByPratica(UUID praticaId){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"disclaimer_pratica\"")
                                     .append(" where \"pratica_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(praticaId);
        return super.update(sql, parameters);
    }

}
