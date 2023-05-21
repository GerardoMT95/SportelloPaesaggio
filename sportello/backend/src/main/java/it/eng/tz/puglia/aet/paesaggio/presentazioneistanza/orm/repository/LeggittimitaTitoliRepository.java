package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.LeggittimitaTitoliDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.LeggittimitaTitoliRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.LeggittimitaTitoliSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.leggittimita_titoli
 */
@Repository
public class LeggittimitaTitoliRepository extends GenericCrudDao<LeggittimitaTitoliDTO, LeggittimitaTitoliSearch>{

    private final LeggittimitaTitoliRowMapper rowMapper = new LeggittimitaTitoliRowMapper();

        final String selectAll = new StringBuilder("select")
                                     .append(" \"pratica_id\"")
                                     .append(",\"id\"")
                                     .append(",\"leg_tit_denominazione\"")
                                     .append(",\"leg_tit_rilasciato_da\"")
                                     .append(",\"leg_tit_num_protocollo\"")
                                     .append(",\"leg_tit_data_rilascio\"")
                                     .append(",\"leg_tit_intestatario\"")
                                     .append(" from \"presentazione_istanza\".\"leggittimita_titoli\"")
                                     .toString();
    
    /**
     * select all method
     */
    @Override
    public List<LeggittimitaTitoliDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"leggittimita_titoli\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public LeggittimitaTitoliDTO find(LeggittimitaTitoliDTO pk){
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
    public PaginatedList<LeggittimitaTitoliDTO> search(LeggittimitaTitoliSearch search){
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
        if(StringUtil.isNotEmpty(search.getLegTitDenominazione())){
            sql.append(sep).append("lower(\"leg_tit_denominazione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getLegTitDenominazione()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getLegTitRilasciatoDa())){
            sql.append(sep).append("lower(\"leg_tit_rilasciato_da\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getLegTitRilasciatoDa()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getLegTitNumProtocollo())){
            sql.append(sep).append("lower(\"leg_tit_num_protocollo\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getLegTitNumProtocollo()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getLegTitDataRilascio())){
            sql.append(sep).append("lower(\"leg_tit_data_rilascio\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getLegTitDataRilascio()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getLegTitIntestatario())){
            sql.append(sep).append("lower(\"leg_tit_intestatario\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getLegTitIntestatario()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "praticaId":
                    sql.append(" sort by \"pratica_id\" ").append(sortType);
            case "id":
                    sql.append(" sort by \"id\" ").append(sortType);
            case "legTitDenominazione":
                    sql.append(" sort by \"leg_tit_denominazione\" ").append(sortType);
            case "legTitRilasciatoDa":
                    sql.append(" sort by \"leg_tit_rilasciato_da\" ").append(sortType);
            case "legTitNumProtocollo":
                    sql.append(" sort by \"leg_tit_num_protocollo\" ").append(sortType);
            case "legTitDataRilascio":
                    sql.append(" sort by \"leg_tit_data_rilascio\" ").append(sortType);
            case "legTitIntestatario":
                    sql.append(" sort by \"leg_tit_intestatario\" ").append(sortType);
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
    public long insert(LeggittimitaTitoliDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"leggittimita_titoli\"")
                                     .append("(\"pratica_id\"")
                                     .append(",\"id\"")
                                     .append(",\"leg_tit_denominazione\"")
                                     .append(",\"leg_tit_rilasciato_da\"")
                                     .append(",\"leg_tit_num_protocollo\"")
                                     .append(",\"leg_tit_data_rilascio\"")
                                     .append(",\"leg_tit_intestatario\"")
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
        parameters.add(entity.getLegTitDenominazione());
        parameters.add(entity.getLegTitRilasciatoDa());
        parameters.add(entity.getLegTitNumProtocollo());
        parameters.add(entity.getLegTitDataRilascio());
        parameters.add(entity.getLegTitIntestatario());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(LeggittimitaTitoliDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"leggittimita_titoli\"")
                                     .append(" set \"pratica_id\" = ?")
                                     .append(", \"leg_tit_denominazione\" = ?")
                                     .append(", \"leg_tit_rilasciato_da\" = ?")
                                     .append(", \"leg_tit_num_protocollo\" = ?")
                                     .append(", \"leg_tit_data_rilascio\" = ?")
                                     .append(", \"leg_tit_intestatario\" = ?")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getPraticaId());
        parameters.add(entity.getLegTitDenominazione());
        parameters.add(entity.getLegTitRilasciatoDa());
        parameters.add(entity.getLegTitNumProtocollo());
        parameters.add(entity.getLegTitDataRilascio());
        parameters.add(entity.getLegTitIntestatario());
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(LeggittimitaTitoliDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"leggittimita_titoli\"")
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
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"leggittimita_titoli\"")
                                     .append(" where \"pratica_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(idPratica);
        return super.update(sql, parameters);
    }
    
}