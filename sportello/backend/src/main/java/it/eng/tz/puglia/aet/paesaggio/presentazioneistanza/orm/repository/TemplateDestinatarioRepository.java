package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.sql.*;
import java.math.*;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import it.eng.tz.puglia.bean.PaginatedList;
import org.springframework.stereotype.Repository;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.*;

/**
 * Repository for table presentazione_istanza.template_destinatario
 */
@Repository
public class TemplateDestinatarioRepository extends GenericCrudDao<TemplateDestinatarioDTO, TemplateDestinatarioSearch>{

    private final TemplateDestinatarioRowMapper rowMapper = new TemplateDestinatarioRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id\"")
                                     .append(",\"id_organizzazione\"")
                                     .append(",\"codice_template\"")
                                     .append(",\"email\"")
                                     .append(",\"pec\"")
                                     .append(",\"denominazione\"")
                                     .append(",\"tipo\"")
                                     .append(" from \"presentazione_istanza\".\"template_destinatario\"")
                                     .toString();
    @Override
    public List<TemplateDestinatarioDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"template_destinatario\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public TemplateDestinatarioDTO find(TemplateDestinatarioDTO pk){
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
    public PaginatedList<TemplateDestinatarioDTO> search(TemplateDestinatarioSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getId())){
            sql.append(sep).append("\"id\" = ?");
            parameters.add(search.getId());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdOrganizzazione())){
            sql.append(sep).append("\"id_organizzazione\"::text = ?");
            parameters.add(search.getIdOrganizzazione());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCodiceTemplate())){
            sql.append(sep).append("lower(\"codice_template\"::text) like lower(?)");
            parameters.add(StringUtil.convertLike(search.getCodiceTemplate()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getEmail())){
            sql.append(sep).append("lower(\"email\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getEmail()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPec())){
            sql.append(sep).append("lower(\"pec\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPec()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDenominazione())){
            sql.append(sep).append("lower(\"denominazione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDenominazione()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getTipo())){
            sql.append(sep).append("lower(\"tipo\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getTipo()).toLowerCase());
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
            case "codiceTemplate":
                    sql.append(" order by \"codice_template\" ").append(sortType);
                	   break;
            case "email":
                    sql.append(" order by \"email\" ").append(sortType);
                	   break;
            case "pec":
                    sql.append(" order by \"pec\" ").append(sortType);
                	   break;
            case "denominazione":
                    sql.append(" order by \"denominazione\" ").append(sortType);
                	   break;
            case "tipo":
                    sql.append(" order by \"tipo\" ").append(sortType);
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
    public long insert(TemplateDestinatarioDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"template_destinatario\"")
                                     .append("(\"id_organizzazione\"")
                                     .append(",\"codice_template\"")
                                     .append(",\"email\"")
                                     .append(",\"pec\"")
                                     .append(",\"denominazione\"")
                                     .append(",\"tipo\"")
                                     .append(") values ")
                                     .append("(?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIdOrganizzazione());
        parameters.add(entity.getCodiceTemplate());
        parameters.add(entity.getEmail());
        parameters.add(entity.getPec());
        parameters.add(entity.getDenominazione());
        parameters.add(entity.getTipo());
        return super.insertAndGetAutoincrementValue(sql, parameters, "id");
    }

    /**
     * update by pk method
     */
    @Override
    public int update(TemplateDestinatarioDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"template_destinatario\"")
                                     .append(" set \"id_organizzazione\" = ?")
                                     .append(", \"codice_template\" = ?")
                                     .append(", \"email\" = ?")
                                     .append(", \"pec\" = ?")
                                     .append(", \"denominazione\" = ?")
                                     .append(", \"tipo\" = ?")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIdOrganizzazione());
        parameters.add(entity.getCodiceTemplate());
        parameters.add(entity.getEmail());
        parameters.add(entity.getPec());
        parameters.add(entity.getDenominazione());
        parameters.add(entity.getTipo());
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(TemplateDestinatarioDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"template_destinatario\"")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

	public int copiaDestinatariDefault(int idOrganizzazione, String codice) {
		final String sql = new StringBuilder(
					"INSERT INTO \"presentazione_istanza\".\"template_destinatario\" (id_organizzazione,codice_template,email,pec,denominazione,tipo) ")
					.append(" SELECT  ? ,codice_template,email,pec,denominazione,tipo from \"presentazione_istanza\".\"template_destinatario\"  ")
	                .append(" where \"id_organizzazione\" = ? AND  \"codice_template\" = ? ")
                .toString();
		final List<Object> parameters = new ArrayList<Object>();
        parameters.add(idOrganizzazione);
        parameters.add(0);
        parameters.add(codice);
        return super.update(sql, parameters);
	}

}
