package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.repository;

import java.sql.*;
import java.math.*;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import it.eng.tz.puglia.bean.PaginatedList;
import org.springframework.stereotype.Repository;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.dto.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.search.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.rowmapper.*;

/**
 * Repository for table presentazione_istanza.template_doc_sezioni
 */
@Repository
public class TemplateDocSezioniRepository extends GenericCrudDao<TemplateDocSezioniDTO, TemplateDocSezioniSearch>{

    private final TemplateDocSezioniRowMapper rowMapper = new TemplateDocSezioniRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id_organizzazione\"")
                                     .append(",\"nome\"")
                                     .append(",\"template_doc_nome\"")
                                     .append(",\"valore\"")
                                     .append(",\"id_allegato\"")
                                     .append(",\"placeholders\"")
                                     .append(",\"tipo_sezione\"")
                                     .append(" from \"presentazione_istanza\".\"template_doc_sezioni\"")
                                     .toString();
    @Override
    public List<TemplateDocSezioniDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"template_doc_sezioni\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public TemplateDocSezioniDTO find(TemplateDocSezioniDTO pk){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"id_organizzazione\" = ?")
                                     .append(" and \"nome\" = ?")
                                     .append(" and \"template_doc_nome\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getIdOrganizzazione());
        parameters.add(pk.getNome());
        parameters.add(pk.getTemplateDocNome());
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    /**
     * search method
     */
    @Override
    public PaginatedList<TemplateDocSezioniDTO> search(TemplateDocSezioniSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getIdOrganizzazione())){
            sql.append(sep).append("\"id_organizzazione\"::text = ?");
            parameters.add(search.getIdOrganizzazione());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getNome())){
            sql.append(sep).append("\"nome\" = ?");
            parameters.add(search.getNome());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getTemplateDocNome())){
            sql.append(sep).append("\"template_doc_nome\" = ?");
            parameters.add(search.getTemplateDocNome());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getValore())){
            sql.append(sep).append("lower(\"valore\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getValore()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdAllegato())){
            sql.append(sep).append("lower(\"id_allegato\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdAllegato()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPlaceholders())){
            sql.append(sep).append("lower(\"placeholders\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPlaceholders()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getTipoSezione())){
            sql.append(sep).append("lower(\"tipo_sezione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getTipoSezione()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "idOrganizzazione":
                    sql.append(" order by \"id_organizzazione\" ").append(sortType);
                	   break;
            case "nome":
                    sql.append(" order by \"nome\" ").append(sortType);
                	   break;
            case "templateDocNome":
                    sql.append(" order by \"template_doc_nome\" ").append(sortType);
                	   break;
            case "valore":
                    sql.append(" order by \"valore\" ").append(sortType);
                	   break;
            case "idAllegato":
                    sql.append(" order by \"id_allegato\" ").append(sortType);
                	   break;
            case "placeholders":
                    sql.append(" order by \"placeholders\" ").append(sortType);
                	   break;
            case "tipoSezione":
                    sql.append(" order by \"tipo_sezione\" ").append(sortType);
                	   break;
            default:
            	    logger.warn(StringUtil.concateneString("value ", search.getSortBy(), " not valid for sort by"));
            	    break;
            }
        }else {//per default è bene dare un ordine... altrimenti cambia sempre...
        	sql.append(" order by \"nome\" ");
        }
        return super.paginatedList(sql.toString(), parameters, this.rowMapper, search.getPage(), search.getLimit());
    }

    /**
     * insert all method
     */
    @Override
    public long insert(TemplateDocSezioniDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"template_doc_sezioni\"")
                                     .append("(\"id_organizzazione\"")
                                     .append(",\"nome\"")
                                     .append(",\"template_doc_nome\"")
                                     .append(",\"valore\"")
                                     .append(",\"id_allegato\"")
                                     .append(",\"placeholders\"")
                                     .append(",\"tipo_sezione\"")
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
        parameters.add(entity.getIdOrganizzazione());
        parameters.add(entity.getNome());
        parameters.add(entity.getTemplateDocNome());
        parameters.add(entity.getValore());
        parameters.add(entity.getIdAllegato());
        parameters.add(entity.getPlaceholders());
        parameters.add(entity.getTipoSezione());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(TemplateDocSezioniDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"template_doc_sezioni\"")
                                     .append(" set \"valore\" = ?")
                                     .append(", \"id_allegato\" = ?")
                                     .append(", \"placeholders\" = ?")
                                     .append(", \"tipo_sezione\" = ?")
                                     .append(" where \"id_organizzazione\" = ?")
                                     .append(" and \"nome\" = ?")
                                     .append(" and \"template_doc_nome\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getValore());
        parameters.add(entity.getIdAllegato());
        parameters.add(entity.getPlaceholders());
        parameters.add(entity.getTipoSezione());
        parameters.add(entity.getIdOrganizzazione());
        parameters.add(entity.getNome());
        parameters.add(entity.getTemplateDocNome());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(TemplateDocSezioniDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"template_doc_sezioni\"")
                                     .append(" where \"id_organizzazione\" = ?")
                                     .append(" and \"nome\" = ?")
                                     .append(" and \"template_doc_nome\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIdOrganizzazione());
        parameters.add(entity.getNome());
        parameters.add(entity.getTemplateDocNome());
        return super.update(sql, parameters);
    }
    
    /**
     * ricopia i valori dal record con idOrganizzazione=0
     * @author acolaianni
     *
     * @param entity
     * @return
     */
    public int resetSezione(TemplateDocSezioniDTO entity){
    	TemplateDocSezioniDTO entityDef = new TemplateDocSezioniDTO();
    	entityDef.setIdOrganizzazione(0);
    	entityDef.setTemplateDocNome(entity.getTemplateDocNome());
    	entityDef.setNome(entity.getNome());
    	entityDef=this.find(entityDef);
    	entityDef.setIdOrganizzazione(entity.getIdOrganizzazione());
    	return this.update(entityDef);
    }

	public int copiaSezioniDefault(int idOrganizzazione, String nome) {
		final String sql = new StringBuilder(
				"INSERT INTO \"presentazione_istanza\".\"template_doc_sezioni\" (id_organizzazione, nome, template_doc_nome, valore, id_allegato, placeholders, tipo_sezione) ")
						.append(" SELECT  ?, nome, template_doc_nome, valore, id_allegato, placeholders, tipo_sezione from \"presentazione_istanza\".\"template_doc_sezioni\"  ")
						.append(" where \"id_organizzazione\" = ? AND  \"template_doc_nome\" = ? ").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(idOrganizzazione);
		parameters.add(0);
		parameters.add(nome);
		return super.update(sql, parameters);
	}

}
