package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.repository;

import java.util.List;
import java.util.ArrayList;
import it.eng.tz.puglia.bean.PaginatedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TemplateEmailDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.TemplateEmailRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.dto.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.search.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.service.impl.TemplateMailService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.rowmapper.*;

/**
 * Repository for table presentazione_istanza.template_doc
 */
@Repository
public class TemplateDocRepository extends GenericCrudDao<TemplateDocDTO, TemplateDocSearch>{

	private static final Logger log = LoggerFactory.getLogger(TemplateDocRepository.class);
	
    private final TemplateDocRowMapper rowMapper = new TemplateDocRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id_organizzazione\"")
                                     .append(",\"nome\"")
                                     .append(",\"descrizione\"")
                                     .append(" from \"presentazione_istanza\".\"template_doc\"")
                                     .toString();
    @Override
    public List<TemplateDocDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"template_doc\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public TemplateDocDTO find(TemplateDocDTO pk){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"id_organizzazione\" = ?")
                                     .append(" and \"nome\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getIdOrganizzazione());
        parameters.add(pk.getNome());
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    
    
    /**
     * search method
     */
    @Override
    public PaginatedList<TemplateDocDTO> search(TemplateDocSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getIdOrganizzazione())){
            sql.append(sep).append("lower(\"id_organizzazione\"::text) = ?");
            parameters.add(search.getIdOrganizzazione());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getNome())){
            sql.append(sep).append("\"nome\" = ?");
            parameters.add(search.getNome());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDescrizione())){
            sql.append(sep).append("lower(\"descrizione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDescrizione()).toLowerCase());
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
            case "descrizione":
                    sql.append(" order by \"descrizione\" ").append(sortType);
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
    public long insert(TemplateDocDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"template_doc\"")
                                     .append("(\"id_organizzazione\"")
                                     .append(",\"nome\"")
                                     .append(",\"descrizione\"")
                                     .append(") values ")
                                     .append("(?")
                                     .append(",?")
                                     .append(",?")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIdOrganizzazione());
        parameters.add(entity.getNome());
        parameters.add(entity.getDescrizione());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(TemplateDocDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"template_doc\"")
                                     .append(" set \"descrizione\" = ?")
                                     .append(" where \"id_organizzazione\" = ?")
                                     .append(" and \"nome\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getDescrizione());
        parameters.add(entity.getIdOrganizzazione());
        parameters.add(entity.getNome());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(TemplateDocDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"template_doc\"")
                                     .append(" where \"id_organizzazione\" = ?")
                                     .append(" and \"nome\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIdOrganizzazione());
        parameters.add(entity.getNome());
        return super.update(sql, parameters);
    }
    
    
    /**
     * restituisce la ista dei template non ancora creati per l'ente in questione
     * @author acolaianni
     *
     * @param idOrganizzazione
     * @return
     */
    public List<TemplateDocDTO> getTemplateMancanti(String idOrganizzazione) {
    	final List<Object>  parameters = new ArrayList<Object>();
    	StringBuilder sql =new StringBuilder(); 
    			sql
    			.append("SELECT * from template_doc where id_organizzazione=0 ")
    			.append(" and nome not in (select nome from template_doc where id_organizzazione= ? )");
    	parameters.add(Integer.parseInt(idOrganizzazione));
    	return super.queryForList(sql.toString(),new TemplateDocRowMapper(),parameters);
    }

}
