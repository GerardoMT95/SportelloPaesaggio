package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TariffaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.TariffaRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.TariffaSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.tariffa
 */
@Repository
public class TariffaRepository extends GenericCrudDao<TariffaDTO, TariffaSearch> {
	
	@Autowired
	private UserUtil userUtil;

    private final TariffaRowMapper rowMapper = new TariffaRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id\"")
                                     .append(",\"id_organizzazione\"")
                                     .append(",\"id_tipo_procedimento\"")
                                     .append(",\"soglia_minima\"")
                                     .append(",\"soglia_massima\"")
                                     .append(",\"delete_eccedente\"")
                                     .append(",\"percentuale\"")
                                     .append(",\"cifra_da_aggiungere\"")
                                     .append(",\"percentuale_finale\"")
                                     .append(",\"start_date\"")
                                     .append(",\"end_date\"")
                                     .append(",\"create_date\"")
                                     .append(",\"create_user\"")
                                     .append(" from \"presentazione_istanza\".\"tariffa\"")
                                     .toString();
    @Override
    public List<TariffaDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"tariffa\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public TariffaDTO find(TariffaDTO pk){
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
    public PaginatedList<TariffaDTO> search(TariffaSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getId())){
            sql.append(sep).append("lower(\"id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getId()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdOrganizzazione())){
            sql.append(sep).append("lower(\"id_organizzazione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdOrganizzazione()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdTipoProcedimento())){
            sql.append(sep).append("lower(\"id_tipo_procedimento\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdTipoProcedimento()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSogliaMinima())){
            sql.append(sep).append("lower(\"soglia_minima\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getSogliaMinima()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSogliaMassima())){
            sql.append(sep).append("lower(\"soglia_massima\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getSogliaMassima()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDeleteEccedente())){
            sql.append(sep).append("lower(\"delete_eccedente\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDeleteEccedente()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPercentuale())){
            sql.append(sep).append("lower(\"percentuale\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPercentuale()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCifraDaAggiungere())){
            sql.append(sep).append("lower(\"cifra_da_aggiungere\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getCifraDaAggiungere()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPercentualeFinale())){
            sql.append(sep).append("lower(\"percentuale_finale\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPercentualeFinale()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getStartDate())){
            sql.append(sep).append("lower(\"start_date\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getStartDate()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getEndDate())){
            sql.append(sep).append("lower(\"end_date\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getEndDate()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCreateDate())){
            sql.append(sep).append("lower(\"create_date\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getCreateDate()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCreateUser())){
            sql.append(sep).append("lower(\"create_user\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getCreateUser()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "id":
                    sql.append(" order by \"id\" ").append(sortType);
                	   break;
            case "idOrganizzazione":
                    sql.append(" order by \"id_organizzazione\" ").append(sortType);
                	   break;
            case "idTipoProcedimento":
                    sql.append(" order by \"id_tipo_procedimento\" ").append(sortType);
                	   break;
            case "sogliaMinima":
                    sql.append(" order by \"soglia_minima\" ").append(sortType);
                	   break;
            case "sogliaMassima":
                    sql.append(" order by \"soglia_massima\" ").append(sortType);
                	   break;
            case "deleteEccedente":
                    sql.append(" order by \"delete_eccedente\" ").append(sortType);
                	   break;
            case "percentuale":
                    sql.append(" order by \"percentuale\" ").append(sortType);
                	   break;
            case "cifraDaAggiungere":
                    sql.append(" order by \"cifra_da_aggiungere\" ").append(sortType);
                	   break;
            case "percentualeFinale":
                    sql.append(" order by \"percentuale_finale\" ").append(sortType);
                	   break;
            case "startDate":
                    sql.append(" order by \"start_date\" ").append(sortType);
                	   break;
            case "endDate":
                    sql.append(" order by \"end_date\" ").append(sortType);
                	   break;
            case "createDate":
                    sql.append(" order by \"create_date\" ").append(sortType);
                	   break;
            case "createUser":
                    sql.append(" order by \"create_user\" ").append(sortType);
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
    public long insert(TariffaDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"tariffa\"")
                                     .append("(\"id_organizzazione\"")
                                     .append(",\"id_tipo_procedimento\"")
                                     .append(",\"soglia_minima\"")
                                     .append(",\"soglia_massima\"")
                                     .append(",\"delete_eccedente\"")
                                     .append(",\"percentuale\"")
                                     .append(",\"cifra_da_aggiungere\"")
                                     .append(",\"percentuale_finale\"")
                                     .append(",\"start_date\"")
                                     .append(",\"create_date\"")
                                     .append(",\"create_user\"")
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
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIdOrganizzazione());
        parameters.add(entity.getIdTipoProcedimento());
        parameters.add(entity.getSogliaMinima());
        parameters.add(entity.getSogliaMassima());
        parameters.add(entity.getDeleteEccedente());
        parameters.add(entity.getPercentuale());
        parameters.add(entity.getCifraDaAggiungere());
        parameters.add(entity.getPercentualeFinale());
        parameters.add(entity.getStartDate());
        parameters.add(entity.getCreateDate());
        parameters.add(entity.getCreateUser());
        return super.insertAndGetAutoincrementValue(sql, parameters, "id");
    }

    /**
     * update by pk method
     */
    @Override
    public int update(TariffaDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"tariffa\"")
                                     .append(" set \"id_organizzazione\" = ?")
                                     .append(", \"id_tipo_procedimento\" = ?")
                                     .append(", \"soglia_minima\" = ?")
                                     .append(", \"soglia_massima\" = ?")
                                     .append(", \"delete_eccedente\" = ?")
                                     .append(", \"percentuale\" = ?")
                                     .append(", \"cifra_da_aggiungere\" = ?")
                                     .append(", \"percentuale_finale\" = ?")
                                     .append(", \"start_date\" = ?")
                                     .append(", \"end_date\" = ?")
                                     .append(", \"create_date\" = ?")
                                     .append(", \"create_user\" = ?")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIdOrganizzazione());
        parameters.add(entity.getIdTipoProcedimento());
        parameters.add(entity.getSogliaMinima());
        parameters.add(entity.getSogliaMassima());
        parameters.add(entity.getDeleteEccedente());
        parameters.add(entity.getPercentuale());
        parameters.add(entity.getCifraDaAggiungere());
        parameters.add(entity.getPercentualeFinale());
        parameters.add(entity.getStartDate());
        parameters.add(entity.getEndDate());
        parameters.add(entity.getCreateDate());
        parameters.add(entity.getCreateUser());
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(TariffaDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"tariffa\"")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }
    
    /**
     * restituisce la tariffa a partire dall'importo e data validità
     * @author acolaianni
     *
     * @param idOrganizzazione
     * @param tipoProcedimento
     * @param importo
     * @param date
     * @return
     */
    public TariffaDTO getTariffaByImportoTipoProcOrganizzazione(Integer idOrganizzazione,Integer tipoProcedimento,double importo,java.util.Date date){
    	TariffaDTO ret=null;
        final String sql = new StringBuilder(
        		StringUtil.concateneString("select * from presentazione_istanza.tariffa t ",
        		" WHERE id_tipo_procedimento=? and ? >=soglia_minima and ", 
        		" ( ? <=soglia_massima or soglia_massima is null) and ", 
        		" start_date<=? and (end_date>=? or end_date is null) ", 
        		" and (id_organizzazione=? or id_organizzazione=0)  ",
        		" ORDER BY id_organizzazione DESC")).toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(tipoProcedimento);
        parameters.add(importo);
        parameters.add(importo);
        parameters.add(date);
        parameters.add(date);
        parameters.add(idOrganizzazione);
        List<TariffaDTO> lista = super.queryForList(sql,rowMapper,parameters);
        if(ListUtil.isNotEmpty(lista)) {
        	ret=lista.get(0);
        }
        return ret;
    }
    
    public List<TariffaDTO> getTariffeByTipoProcedimento(final Integer tipoProcedimento){
        String sql = new StringBuilder(
        		StringUtil.concateneString("select * from presentazione_istanza.tariffa t ",
        		"WHERE id_tipo_procedimento=? and ",
        		"id_organizzazione=? and ", 
        		"(start_date<=? and end_date>=? or end_date is null) ", 
        		"ORDER BY soglia_minima ASC")).toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(tipoProcedimento);
        parameters.add(userUtil.getIntegerId());
        parameters.add(new Date());
        parameters.add(new Date());
        final List<TariffaDTO> list = super.queryForList(sql,rowMapper,parameters);
        if(list != null && list.size() > 0) {
        	return list;
        }
        parameters.clear();
        sql = new StringBuilder(
        		StringUtil.concateneString("select * from presentazione_istanza.tariffa t ",
        		"WHERE id_tipo_procedimento=? and ",
        		"id_organizzazione=0 and ", 
        		"(start_date<=? and end_date>=? or end_date is null) ", 
        		"ORDER BY soglia_minima ASC")).toString();
        parameters.add(tipoProcedimento);
        parameters.add(new Date());
        parameters.add(new Date());
        return super.queryForList(sql,rowMapper,parameters);
        
    }
    
    public void endConfigurazioniTariffa(final Integer tipoProcedimento){
        final String sql = new StringBuilder(
        		StringUtil.concateneString("update presentazione_istanza.tariffa t ",
                "SET end_date=? ", 
        		"WHERE id_tipo_procedimento=? and ",
        		"id_organizzazione=? and ", 
        		"end_date is null ")).toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(new Timestamp(System.currentTimeMillis()));
        parameters.add(tipoProcedimento);
        parameters.add(userUtil.getIntegerId());
        super.update(sql, parameters);
    }


}
