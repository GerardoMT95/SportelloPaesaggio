package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AltreDichiarazioniRichDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.AltreDichiarazioniRichRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.AltreDichiarazioniRichSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.altre_dichiarazioni_rich
 */
@Repository
public class AltreDichiarazioniRichRepository extends GenericCrudDao<AltreDichiarazioniRichDTO, AltreDichiarazioniRichSearch>{

    private final AltreDichiarazioniRichRowMapper rowMapper = new AltreDichiarazioniRichRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"pratica_id\"")
                                     .append(",\"check_136\"")
                                     .append(",\"check_142\"")
                                     .append(",\"check_134\"")
                                     .append(",\"check_136a\"")
                                     .append(",\"check_136b\"")
                                     .append(",\"check_136c\"")
                                     .append(",\"check_136d\"")
                                     .append(",\"ente_rilascio\"")
                                     .append(",\"descr_aut_rilasciata\"")
                                     .append(",\"data_rilascio\"")
                                     .append(",\"is_caso_variante\"")
                                     .append(",\"check_142_parchi\"")
                                     .append(" from \"presentazione_istanza\".\"altre_dichiarazioni_rich\"")
                                     .toString();
    @Override
    public List<AltreDichiarazioniRichDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"altre_dichiarazioni_rich\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public AltreDichiarazioniRichDTO find(AltreDichiarazioniRichDTO pk){
        return this.find(pk, false);
    }
    
    public AltreDichiarazioniRichDTO find(AltreDichiarazioniRichDTO pk,boolean txWrite){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"pratica_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getPraticaId());
        if(txWrite) {
        	return super.queryForObject(sql, this.rowMapper, parameters);
        }else {
        	return super.queryForObjectTxRead(sql, this.rowMapper, parameters);	
        }
    }
    
    /**
     * search method
     */
    @Override
    public PaginatedList<AltreDichiarazioniRichDTO> search(AltreDichiarazioniRichSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getPraticaId())){
            sql.append(sep).append("lower(\"pratica_id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPraticaId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCheck136())){
            sql.append(sep).append("lower(\"check_136\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getCheck136()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCheck142())){
            sql.append(sep).append("lower(\"check_142\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getCheck142()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCheck134())){
            sql.append(sep).append("lower(\"check_134\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getCheck134()));
            sep = " and ";
        }
        
        if(StringUtil.isNotEmpty(search.getEnteRilascio())){
            sql.append(sep).append("lower(\"ente_rilascio\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getEnteRilascio()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDescrAutRilasciata())){
            sql.append(sep).append("lower(\"descr_aut_rilasciata\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDescrAutRilasciata()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataRilascio())){
            sql.append(sep).append("lower(\"data_rilascio\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDataRilascio()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIsCasoVariante())){
            sql.append(sep).append("lower(\"is_caso_variante\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIsCasoVariante()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCheck142Parchi())){
            sql.append(sep).append("lower(\"check_142_parchi\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getCheck142Parchi()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "praticaId":
                    sql.append(" sort by \"pratica_id\" ").append(sortType);
            case "check136":
                    sql.append(" sort by \"check_136\" ").append(sortType);
            case "check142":
                    sql.append(" sort by \"check_142\" ").append(sortType);
            case "check134":
                    sql.append(" sort by \"check_134\" ").append(sortType);
            case "check134a":
                    sql.append(" sort by \"check_136a\" ").append(sortType);
            case "check134b":
                    sql.append(" sort by \"check_136b\" ").append(sortType);
            case "check134c":
                    sql.append(" sort by \"check_136c\" ").append(sortType);
            case "check134d":
                    sql.append(" sort by \"check_136d\" ").append(sortType);
            case "enteRilascio":
                    sql.append(" sort by \"ente_rilascio\" ").append(sortType);
            case "descrAutRilasciata":
                    sql.append(" sort by \"descr_aut_rilasciata\" ").append(sortType);
            case "dataRilascio":
                    sql.append(" sort by \"data_rilascio\" ").append(sortType);
            case "isCasoVariante":
                    sql.append(" sort by \"is_caso_variante\" ").append(sortType);
            case "check142Parchi":
                    sql.append(" sort by \"check_142_parchi\" ").append(sortType);
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
    public long insert(AltreDichiarazioniRichDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"altre_dichiarazioni_rich\"")
                                     .append("(\"pratica_id\"")
                                     .append(",\"check_136\"")
                                     .append(",\"check_142\"")
                                     .append(",\"check_134\"")
                                     .append(",\"check_136a\"")
                                     .append(",\"check_136b\"")
                                     .append(",\"check_136c\"")
                                     .append(",\"check_136d\"")
                                     .append(",\"ente_rilascio\"")
                                     .append(",\"descr_aut_rilasciata\"")
                                     .append(",\"data_rilascio\"")
                                     .append(",\"is_caso_variante\"")
                                     .append(",\"check_142_parchi\"")
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
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getPraticaId());
        parameters.add(entity.getCheck136());
        parameters.add(entity.getCheck142());
        parameters.add(entity.getCheck134());
        parameters.add(entity.getCheck136a());
        parameters.add(entity.getCheck136b());
        parameters.add(entity.getCheck136c());
        parameters.add(entity.getCheck136d());
        parameters.add(entity.getEnteRilascio());
        parameters.add(entity.getDescrAutRilasciata());
        parameters.add(entity.getDataRilascio());
        parameters.add(entity.getIsCasoVariante());
        parameters.add(entity.getCheck142Parchi());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(AltreDichiarazioniRichDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"altre_dichiarazioni_rich\"")
                                     .append(" set \"check_136\" = ?")
                                     .append(", \"check_142\" = ?")
                                     .append(", \"check_134\" = ?")
                                     .append(", \"check_136a\" = ?")
                                     .append(", \"check_136b\" = ?")
                                     .append(", \"check_136c\" = ?")
                                     .append(", \"check_136d\" = ?")
                                     .append(", \"ente_rilascio\" = ?")
                                     .append(", \"descr_aut_rilasciata\" = ?")
                                     .append(", \"data_rilascio\" = ?")
                                     .append(", \"is_caso_variante\" = ?")
                                     .append(", \"check_142_parchi\" = ?")
                                     .append(" where \"pratica_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getCheck136());
        parameters.add(entity.getCheck142());
        parameters.add(entity.getCheck134());
        parameters.add(entity.getCheck136a());
        parameters.add(entity.getCheck136b());
        parameters.add(entity.getCheck136c());
        parameters.add(entity.getCheck136d());
        parameters.add(entity.getEnteRilascio());
        parameters.add(entity.getDescrAutRilasciata());
        parameters.add(entity.getDataRilascio());
        parameters.add(entity.getIsCasoVariante());
        parameters.add(entity.getCheck142Parchi());
        parameters.add(entity.getPraticaId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(AltreDichiarazioniRichDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"altre_dichiarazioni_rich\"")
                                     .append(" where \"pratica_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getPraticaId());
        return super.update(sql, parameters);
    }

}
