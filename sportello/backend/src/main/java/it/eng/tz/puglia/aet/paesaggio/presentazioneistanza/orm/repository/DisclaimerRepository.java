package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.error.exception.CustomOperationException;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.util.date.DateUtil;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.*;

/**
 * Repository for table presentazione_istanza.disclaimer
 */
@Repository
public class DisclaimerRepository extends GenericCrudDao<DisclaimerDTO, DisclaimerSearch>{

    private final DisclaimerRowMapper rowMapper = new DisclaimerRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id\"")
                                     .append(",\"testo\"")
                                     .append(",\"tipo_procedimento\"")
                                     .append(",\"tipo_referente\"")
                                     .append(",\"data_inizio\"")
                                     .append(",\"data_fine\"")
                                     .append(",\"user_inserted\"")
                                     .append(",\"user_updated\"")
                                     .append(",\"ordine\"")
                                     .append(",\"required\"")
                                     .append(" from \"presentazione_istanza\".\"disclaimer\" ")
                                     .toString();
    @Override
    public List<DisclaimerDTO> select(){
    	throw new RuntimeException("Non implementato!");
    }
    
    
    public List<DisclaimerDTO> selectByIds(List<Integer> ids){
    	StringBuilder sb=new StringBuilder(selectAll);
    	sb
    	.append(" WHERE ")
    	.append(" id in ( :ids ) ")
    	.append(" ORDER BY ordine ");
    	final Map<String,Object> parameters = new HashMap<>();
    	parameters.put("ids", ids);
    	return super.namedQueryForListTxRead(sb.toString(), this.rowMapper,parameters);
    }
    
    
    /**
     * selezione dei disclaimer già agganciati alla pratica
     * @author acolaianni
     *
     * @param tipoReferente
     * @param codicePratica
     * @return
     */
    public List<DisclaimerDTO> selectByCodicePratica(String tipoReferente,String codicePratica){
    	final List<Object> parameters = new ArrayList<Object>();
    	StringBuilder query=new StringBuilder(selectAll);
    	StringBuilder queryIds=new StringBuilder();
    	queryIds
    	.append("(SELECT dp.disclaimer_id ")
    	.append(" FROM presentazione_istanza.pratica p ")
    	.append(",presentazione_istanza.disclaimer_pratica dp ")
    	.append(" WHERE ")
    	.append(" p.codice_pratica_appptr = ? ")
    	.append(" AND ")
    	.append(" dp.pratica_id=p.id")
    	.append(")"); 
    	query
    	.append(" where  \"tipo_referente\" = ? ")
    	.append(" AND id in (")
    	.append(queryIds)
    	.append(")")
    	.append(" ORDER BY ordine "); 
    	parameters.add(tipoReferente);
    	parameters.add(codicePratica);
        return super.queryForListTxRead(query.toString()
        		,this.rowMapper,parameters);
    }
    
    public List<DisclaimerDTO> selectByIdPratica(String tipoReferente,UUID idPratica){
    	final List<Object> parameters = new ArrayList<Object>();
    	StringBuilder query=new StringBuilder(selectAll);
    	StringBuilder queryIds=new StringBuilder();
    	queryIds
    	.append("(SELECT dp.disclaimer_id ")
    	.append(" FROM ")
    	.append(" presentazione_istanza.disclaimer_pratica dp ")
    	.append(" WHERE ")
    	.append(" dp.pratica_id = ? ")
    	.append(")"); 
    	query
    	.append(" where  \"tipo_referente\" = ? ")
    	.append(" AND id in (")
    	.append(queryIds)
    	.append(")")
    	.append(" ORDER BY ordine "); 
    	parameters.add(tipoReferente);
    	parameters.add(idPratica);
        return super.queryForListTxRead(query.toString()
        		,this.rowMapper,parameters);
    }
    
    /**
     * 
     * @author acolaianni
     *
     * @param tipoReferente se null non effettua filtro
     * @param tipoProcedimento
     * @param dataRif data riferimento configurazione (normalmente è la data di creazione dell'istanza) 
     * se a null vengono considerate le correnti (data_fine is null)
     * @return
     */
    public List<DisclaimerDTO> select(String tipoReferente,Integer tipoProcedimento,Date dataRif,boolean txWrite){
    	final List<Object> parameters = new ArrayList<Object>();
    	StringBuilder query=new StringBuilder(selectAll);
    	query
    	.append(" WHERE ")
    	.append(" \"tipo_procedimento\" = ? ");
    	parameters.add(tipoProcedimento);
    	if(StringUtil.isNotEmpty(tipoReferente)) {
    		query.append(" AND \"tipo_referente\" = ? ");
    		parameters.add(tipoReferente);
    	}
    	if(dataRif==null) {
    		query.append(" AND \"data_fine\" is null ");
    	}else {
    		query.append(" AND \"data_inizio\" <= ? ")
        	.append(" AND ( \"data_fine\" is null OR \"data_fine\" >= ? )");
    		parameters.add(dataRif);
        	parameters.add(dataRif);
    	}
    	query.append(" ORDER BY ordine ");
    	if(txWrite) {
    		return super.queryForList(query.toString(), this.rowMapper,parameters);
    	}else {
    		return super.queryForListTxRead(query.toString(), this.rowMapper,parameters);	
    	}
        
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"disclaimer\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public DisclaimerDTO find(DisclaimerDTO pk){
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
    public PaginatedList<DisclaimerDTO> search(DisclaimerSearch search){
    	throw new RuntimeException("Non implementato!");
    }

    /**
     * insert all method
     */
    @Override
    public long insert(DisclaimerDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"disclaimer\"")
                                     .append("(")
                                     .append(" \"testo\"")
                                     .append(",\"tipo_procedimento\"")
                                     .append(",\"tipo_referente\"")
                                     .append(",\"data_inizio\"")
                                     .append(",\"user_inserted\"")
                                     .append(",\"required\"")
                                     .append(",\"ordine\"")
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
        parameters.add(entity.getTesto());
        parameters.add(entity.getTipoProcedimento());
        parameters.add(entity.getTipoReferente());
        parameters.add(entity.getDataInizio()==null?DateUtil.currentTimestamp():entity.getDataInizio());
        parameters.add(entity.getUserInserted());
        parameters.add(entity.getRequired());
        parameters.add(entity.getOrdine());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(DisclaimerDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"disclaimer\" ")
                                     .append(" set \"data_fine\" = ? ")
                                     .append(", \"user_updated\" = ? ")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getDataFine());
        parameters.add(entity.getUserUpdated());
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(DisclaimerDTO entity){
       throw new RuntimeException("Non implementato!!!");
    }


    /**
     * set data fine ai record correnti
     * @author acolaianni
     *
     * @param tipoProcedimento
     * @param userUpdated
     * @param endDate
     * @return record updated
     */
	public int updateEndDate(int tipoProcedimento, String userUpdated,Date endDate) {
		final String sql = new StringBuilder("update \"presentazione_istanza\".\"disclaimer\" ")
				.append(" set \"data_fine\" = ? ")
				.append(", \"user_updated\" = ? ")
				.append(" where \"tipo_procedimento\" = ? ")
				.append("  AND \"data_fine\" is null ")
				.toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(endDate);
		parameters.add(userUpdated);
		parameters.add(tipoProcedimento);
		return super.update(sql, parameters);
	}

	
}
