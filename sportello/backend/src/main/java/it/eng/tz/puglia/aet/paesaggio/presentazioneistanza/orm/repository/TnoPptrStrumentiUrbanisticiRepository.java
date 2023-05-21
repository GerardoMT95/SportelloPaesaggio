package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TnoPptrStrumentiUrbanisticiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.TnoPptrStrumentiUrbanisticiRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.TnoPptrStrumentiUrbanisticiSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.tno_pptr_strumenti_urbanistici
 */
@Repository
public class TnoPptrStrumentiUrbanisticiRepository extends GenericCrudDao<TnoPptrStrumentiUrbanisticiDTO, TnoPptrStrumentiUrbanisticiSearch>{

    private final TnoPptrStrumentiUrbanisticiRowMapper rowMapper = new TnoPptrStrumentiUrbanisticiRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id\"")
                                     .append(",\"istat_6_prov\"")
                                     .append(",\"tipo_strumento\"")
                                     .append(",\"stato\"")
                                     .append(",\"atto\"")
                                     .append(",\"data_atto\"")
                                     .append(",\"utente_modifica\"")
                                     .append(",\"data_modifica\"")
                                     .append(" from \"presentazione_istanza\".\"tno_pptr_strumenti_urbanistici\"")
                                     .toString();
    @Override
    public List<TnoPptrStrumentiUrbanisticiDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"tno_pptr_strumenti_urbanistici\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public TnoPptrStrumentiUrbanisticiDTO find(TnoPptrStrumentiUrbanisticiDTO pk){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getId());
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    
    /**
     * 
     * @author acolaianni
     *
     * @param codiceIstat anche a 5 cifre, viene convertito a 6 cifre per effettuare search
     * @param tipo
     * @return
     */
    public TnoPptrStrumentiUrbanisticiDTO findByCodiceIstatAndTipo(String codiceIstat, int tipo){ // ce ne possono essere più di 1! è necessario prendere il più recente!
    	StringBuilder codiceIstat6Prov=new StringBuilder(codiceIstat);
    	if(codiceIstat6Prov.length()==5) {
    		codiceIstat6Prov.insert(0, "0");
    	}
    	final String sql = new StringBuilder(selectAll)
                                     .append(" where \"istat_6_prov\" = ?")
                                     .append(" and \"tipo_strumento\" = ?")
                                     .append(" order by \"data_modifica\" DESC")
                                     .append(" LIMIT 1")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(codiceIstat6Prov);
        parameters.add(tipo);
        try {
			return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
		} catch (EmptyResultDataAccessException empty) {
			return null;
		}
    }
    
    /**
     * search method
     */
    @Override
    public PaginatedList<TnoPptrStrumentiUrbanisticiDTO> search(TnoPptrStrumentiUrbanisticiSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getId())){
            sql.append(sep).append("lower(\"id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIstat6Prov())){
            sql.append(sep).append("lower(\"istat_6_prov\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIstat6Prov()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getTipoStrumento())){
            sql.append(sep).append("lower(\"tipo_strumento\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getTipoStrumento()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getStato())){
            sql.append(sep).append("lower(\"stato\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getStato()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getAtto())){
            sql.append(sep).append("lower(\"atto\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getAtto()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataAtto())){
            sql.append(sep).append("lower(\"data_atto\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDataAtto()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getUtenteModifica())){
            sql.append(sep).append("lower(\"utente_modifica\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getUtenteModifica()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataModifica())){
            sql.append(sep).append("lower(\"data_modifica\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDataModifica()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "id":
                    sql.append(" sort by \"id\" ").append(sortType);
            case "istat6Prov":
                    sql.append(" sort by \"istat_6_prov\" ").append(sortType);
            case "tipoStrumento":
                    sql.append(" sort by \"tipo_strumento\" ").append(sortType);
            case "stato":
                    sql.append(" sort by \"stato\" ").append(sortType);
            case "atto":
                    sql.append(" sort by \"atto\" ").append(sortType);
            case "dataAtto":
                    sql.append(" sort by \"data_atto\" ").append(sortType);
            case "utenteModifica":
                    sql.append(" sort by \"utente_modifica\" ").append(sortType);
            case "dataModifica":
                    sql.append(" sort by \"data_modifica\" ").append(sortType);
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
    public long insert(TnoPptrStrumentiUrbanisticiDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"tno_pptr_strumenti_urbanistici\"")
                                     .append("(\"istat_6_prov\"")
                                     .append(",\"tipo_strumento\"")
                                     .append(",\"stato\"")
                                     .append(",\"atto\"")
                                     .append(",\"data_atto\"")
                                     .append(",\"utente_modifica\"")
                                     .append(",\"data_modifica\"")
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
        parameters.add(entity.getIstat6Prov());
        parameters.add(entity.getTipoStrumento());
        parameters.add(entity.getStato());
        parameters.add(entity.getAtto());
        parameters.add(entity.getDataAtto());
        parameters.add(entity.getUtenteModifica());
        parameters.add(entity.getDataModifica());
        return super.insertAndGetAutoincrementValue(sql, parameters, "id");
    }

    /**
     * update by pk method
     */
    @Override
    public int update(TnoPptrStrumentiUrbanisticiDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"tno_pptr_strumenti_urbanistici\"")
                                     .append(" set \"istat_6_prov\" = ?")
                                     .append(", \"tipo_strumento\" = ?")
                                     .append(", \"stato\" = ?")
                                     .append(", \"atto\" = ?")
                                     .append(", \"data_atto\" = ?")
                                     .append(", \"utente_modifica\" = ?")
                                     .append(", \"data_modifica\" = ?")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIstat6Prov());
        parameters.add(entity.getTipoStrumento());
        parameters.add(entity.getStato());
        parameters.add(entity.getAtto());
        parameters.add(entity.getDataAtto());
        parameters.add(entity.getUtenteModifica());
        parameters.add(entity.getDataModifica());
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(TnoPptrStrumentiUrbanisticiDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"tno_pptr_strumenti_urbanistici\"")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

}
