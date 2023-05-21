package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ReferentiDocumentoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.ReferentiDocumentoRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.ReferentiDocumentoSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.referenti_documento
 */
@Repository
public class ReferentiDocumentoRepository extends GenericCrudDao<ReferentiDocumentoDTO, ReferentiDocumentoSearch>{

    private final ReferentiDocumentoRowMapper rowMapper = new ReferentiDocumentoRowMapper();
    /**
     * select all method
     */
    @Override
    public List<ReferentiDocumentoDTO> select(){
        final String sql = new StringBuilder("select")
                                     .append(" \"referente_id\"")
                                     .append(",\"id_tipo\"")
                                     .append(",\"numero\"")
                                     .append(",\"data_rilascio\"")
                                     .append(",\"ente_rilascio\"")
                                     .append(",\"data_scadenza\"")
                                     .append(",\"allegato_id\"")
                                     .append(" from \"presentazione_istanza\".\"referenti_documento\"")
                                     .toString();
        return super.queryForListTxRead(sql, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"referenti_documento\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public ReferentiDocumentoDTO find(ReferentiDocumentoDTO pk){
       return this.find(pk, false);
    }
    
    public ReferentiDocumentoDTO find(ReferentiDocumentoDTO pk,boolean txWrite){
        final String sql = new StringBuilder("select")
                                     .append(" \"referente_id\"")
                                     .append(",\"id_tipo\"")
                                     .append(",\"numero\"")
                                     .append(",\"data_rilascio\"")
                                     .append(",\"ente_rilascio\"")
                                     .append(",\"data_scadenza\"")
                                     .append(",\"allegato_id\"")
                                     .append(" from \"presentazione_istanza\".\"referenti_documento\"")
                                     .append(" where \"referente_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getReferenteId());
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
    public PaginatedList<ReferentiDocumentoDTO> search(ReferentiDocumentoSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder("select")
                                                   .append(" \"referente_id\"")
                                                   .append(",\"id_tipo\"")
                                                   .append(",\"numero\"")
                                                   .append(",\"data_rilascio\"")
                                                   .append(",\"ente_rilascio\"")
                                                   .append(",\"data_scadenza\"")
                                                   .append(",\"allegato_id\"")
                                                   .append(" from \"presentazione_istanza\".\"referenti_documento\" ")
                                                   ;
        if(StringUtil.isNotEmpty(search.getReferenteId())){
            sql.append(sep).append("lower(\"referente_id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getReferenteId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdTipo())){
            sql.append(sep).append("lower(\"id_tipo\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdTipo()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getNumero())){
            sql.append(sep).append("lower(\"numero\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getNumero()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataRilascio())){
            sql.append(sep).append("lower(\"data_rilascio\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDataRilascio()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getEnteRilascio())){
            sql.append(sep).append("lower(\"ente_rilascio\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getEnteRilascio()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataScadenza())){
            sql.append(sep).append("lower(\"data_scadenza\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDataScadenza()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdAllegato())){
            sql.append(sep).append(" \"allegato_id\"::text = ? ");
            parameters.add(search.getIdAllegato().toString());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "referenteId":
                    sql.append(" sort by \"referente_id\" ").append(sortType);
            case "idTipo":
                    sql.append(" sort by \"id_tipo\" ").append(sortType);
            case "numero":
                    sql.append(" sort by \"numero\" ").append(sortType);
            case "dataRilascio":
                    sql.append(" sort by \"data_rilascio\" ").append(sortType);
            case "enteRilascio":
                    sql.append(" sort by \"ente_rilascio\" ").append(sortType);
            case "dataScadenza":
                    sql.append(" sort by \"data_scadenza\" ").append(sortType);
                break;
            default:
            	    logger.warn(StringUtil.concateneString("value ", search.getSortBy(), " not valid for sort by"));
            	    break;
            }
        }
        return super.paginatedList(sql.toString(), parameters,this.rowMapper, search.getPage(), search.getLimit());
    }

    /**
     * insert all method
     */
    @Override
    public long insert(ReferentiDocumentoDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"referenti_documento\"")
                                     .append("(\"referente_id\"")
                                     .append(",\"id_tipo\"")
                                     .append(",\"numero\"")
                                     .append(",\"data_rilascio\"")
                                     .append(",\"ente_rilascio\"")
                                     .append(",\"data_scadenza\"")
                                     .append(",\"allegato_id\"")
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
        parameters.add(entity.getReferenteId());
        parameters.add(entity.getIdTipo());
        parameters.add(entity.getNumero());
        parameters.add(entity.getDataRilascio());
        parameters.add(entity.getEnteRilascio());
        parameters.add(entity.getDataScadenza());
        parameters.add(entity.getIdAllegato());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(ReferentiDocumentoDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"referenti_documento\"")
                                     .append(" set \"numero\" = ?")
                                     .append(", \"id_tipo\" = ?")
                                     .append(", \"data_rilascio\" = ?")
                                     .append(", \"ente_rilascio\" = ?")
                                     .append(", \"data_scadenza\" = ?")
                                     .append(", \"allegato_id\" = ?")
                                     .append(" where \"referente_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getNumero());
        parameters.add(entity.getIdTipo());
        parameters.add(entity.getDataRilascio());
        parameters.add(entity.getEnteRilascio());
        parameters.add(entity.getDataScadenza());
        parameters.add(entity.getIdAllegato());
        parameters.add(entity.getReferenteId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(ReferentiDocumentoDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"referenti_documento\"")
                                     .append(" where \"referente_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getReferenteId());
        return super.update(sql, parameters);
    }

}
