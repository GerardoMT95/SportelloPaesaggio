package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.LeggittimitaProvvedimentiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.LeggittimitaProvvedimentiRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.LeggittimitaProvvedimentiSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.leggittimita_provvedimenti
 */
@Repository
public class LeggittimitaProvvedimentiRepository extends GenericCrudDao<LeggittimitaProvvedimentiDTO, LeggittimitaProvvedimentiSearch>{

    private final LeggittimitaProvvedimentiRowMapper rowMapper = new LeggittimitaProvvedimentiRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"pratica_id\"")
                                     .append(",\"id\"")
                                     .append(",\"leg_provv_denominazione\"")
                                     .append(",\"leg_provv_rilasciato_da\"")
                                     .append(",\"leg_provv_num_protocollo\"")
                                     .append(",\"leg_provv_data_rilascio\"")
                                     .append(",\"leg_provv_intestatario\"")
                                     .append(" from \"presentazione_istanza\".\"leggittimita_provvedimenti\"")
                                     .toString();
    @Override
    public List<LeggittimitaProvvedimentiDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"leggittimita_provvedimenti\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public LeggittimitaProvvedimentiDTO find(LeggittimitaProvvedimentiDTO pk){
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
    public PaginatedList<LeggittimitaProvvedimentiDTO> search(LeggittimitaProvvedimentiSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getPraticaId())){
            sql.append(sep).append("lower(\"pratica_id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPraticaId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getId())){
            sql.append(sep).append("lower(\"id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getLegProvvDenominazione())){
            sql.append(sep).append("lower(\"leg_provv_denominazione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getLegProvvDenominazione()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getLegProvvRilasciatoDa())){
            sql.append(sep).append("lower(\"leg_provv_rilasciato_da\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getLegProvvRilasciatoDa()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getLegProvvNumProtocollo())){
            sql.append(sep).append("lower(\"leg_provv_num_protocollo\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getLegProvvNumProtocollo()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getLegProvvDataRilascio())){
            sql.append(sep).append("lower(\"leg_provv_data_rilascio\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getLegProvvDataRilascio()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getLegProvvIntestatario())){
            sql.append(sep).append("lower(\"leg_provv_intestatario\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getLegProvvIntestatario()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "praticaId":
                    sql.append(" sort by \"pratica_id\" ").append(sortType);
            case "id":
                    sql.append(" sort by \"id\" ").append(sortType);
            case "legProvvDenominazione":
                    sql.append(" sort by \"leg_provv_denominazione\" ").append(sortType);
            case "legProvvRilasciatoDa":
                    sql.append(" sort by \"leg_provv_rilasciato_da\" ").append(sortType);
            case "legProvvNumProtocollo":
                    sql.append(" sort by \"leg_provv_num_protocollo\" ").append(sortType);
            case "legProvvDataRilascio":
                    sql.append(" sort by \"leg_provv_data_rilascio\" ").append(sortType);
            case "legProvvIntestatario":
                    sql.append(" sort by \"leg_provv_intestatario\" ").append(sortType);
                break;
            default:
            	    logger.warn(StringUtil.concateneString("value ", search.getSortBy(), " not valid for sort by"));
            	    break;
            }
        }
//        if (search.getLimit()==0) {
//        	search.setLimit(1000) ; search.setPage(1);
//        }
        return super.paginatedList(sql.toString(), parameters, this.rowMapper, search.getPage(), search.getLimit());
    }

    /**
     * insert all method
     */
    @Override
    public long insert(LeggittimitaProvvedimentiDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"leggittimita_provvedimenti\"")
                                     .append("(\"pratica_id\"")
                                     .append(",\"id\"")
                                     .append(",\"leg_provv_denominazione\"")
                                     .append(",\"leg_provv_rilasciato_da\"")
                                     .append(",\"leg_provv_num_protocollo\"")
                                     .append(",\"leg_provv_data_rilascio\"")
                                     .append(",\"leg_provv_intestatario\"")
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
        parameters.add(entity.getId());
        parameters.add(entity.getLegProvvDenominazione());
        parameters.add(entity.getLegProvvRilasciatoDa());
        parameters.add(entity.getLegProvvNumProtocollo());
        parameters.add(entity.getLegProvvDataRilascio());
        parameters.add(entity.getLegProvvIntestatario());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(LeggittimitaProvvedimentiDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"leggittimita_provvedimenti\"")
                                     .append(" set \"pratica_id\" = ?")
                                     .append(", \"leg_provv_denominazione\" = ?")
                                     .append(", \"leg_provv_rilasciato_da\" = ?")
                                     .append(", \"leg_provv_num_protocollo\" = ?")
                                     .append(", \"leg_provv_data_rilascio\" = ?")
                                     .append(", \"leg_provv_intestatario\" = ?")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getPraticaId());
        parameters.add(entity.getLegProvvDenominazione());
        parameters.add(entity.getLegProvvRilasciatoDa());
        parameters.add(entity.getLegProvvNumProtocollo());
        parameters.add(entity.getLegProvvDataRilascio());
        parameters.add(entity.getLegProvvIntestatario());
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(LeggittimitaProvvedimentiDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"leggittimita_provvedimenti\"")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }
    
    /**
     * delete by idPratica method
     */
    public int deleteByPratica(UUID idPratica){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"leggittimita_provvedimenti\"")
                                     .append(" where \"pratica_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(idPratica);
        return super.update(sql, parameters);
    }

}