package it.eng.tz.puglia.autpae.generated.orm.repository;

import java.sql.*;
import java.math.*;
import java.util.List;

import javax.annotation.PostConstruct;

import java.util.HashMap;
import java.util.ArrayList;
import it.eng.tz.puglia.bean.PaginatedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.puglia.autpae.generated.orm.dto.*;
import it.eng.tz.puglia.autpae.generated.orm.search.*;
import it.eng.tz.puglia.autpae.generated.orm.rowmapper.*;

/**
 * Repository for table common.paesaggio_organizzazione_competenze
 */
@Repository
public class PaesaggioOrganizzazioneCompetenzeRepository extends GenericCrudDao<PaesaggioOrganizzazioneCompetenzeDTO, PaesaggioOrganizzazioneCompetenzeSearch>{

	@Autowired
	@Qualifier("namedJdbcTemplate_common")
	private NamedParameterJdbcTemplate namedJdbcTemplateCommon;
	@Autowired
	@Qualifier("jdbcTemplate_common")
	private JdbcTemplate jdbcTemplateCommon;
	
	@PostConstruct
	public void init() {
		super.jdbcTemplateRead=this.jdbcTemplateCommon;
		super.jdbcTemplateWrite=this.jdbcTemplateCommon;
		super.namedJdbcTemplateRead=this.namedJdbcTemplateCommon;
		super.namedJdbcTemplateWrite=this.namedJdbcTemplateCommon;
	}
	
    private final PaesaggioOrganizzazioneCompetenzeRowMapper rowMapper = new PaesaggioOrganizzazioneCompetenzeRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id\"")
                                     .append(",\"paesaggio_organizzazione_id\"")
                                     .append(",\"ente_id\"")
                                     .append(",\"data_inizio_delega\"")
                                     .append(",\"data_fine_delega\"")
                                     .append(",\"codice_civilia\"")
                                     .append(" from  \"common\".\"paesaggio_organizzazione_competenze\"")
                                     .toString();
    @Override
    public List<PaesaggioOrganizzazioneCompetenzeDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"common\".\"paesaggio_organizzazione_competenze\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public PaesaggioOrganizzazioneCompetenzeDTO find(PaesaggioOrganizzazioneCompetenzeDTO pk){
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
    public PaginatedList<PaesaggioOrganizzazioneCompetenzeDTO> search(PaesaggioOrganizzazioneCompetenzeSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getId())){
            sql.append(sep).append("\"id\" = ?");
            parameters.add(search.getId());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPaesaggioOrganizzazioneId())){
            sql.append(sep).append("\"paesaggio_organizzazione_id\"::text = ?");
            parameters.add(search.getPaesaggioOrganizzazioneId());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getEnteId())){
            sql.append(sep).append("\"ente_id\"::text = ?");
            parameters.add(search.getEnteId());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataInizioDelega())){
            sql.append(sep).append("lower(\"data_inizio_delega\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDataInizioDelega()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataFineDelega())){
            sql.append(sep).append("lower(\"data_fine_delega\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDataFineDelega()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCodiceCivilia())){
            sql.append(sep).append("\"codice_civilia\" = ?");
            parameters.add(search.getCodiceCivilia());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "id":
                    sql.append(" sort by \"id\" ").append(sortType);
            case "paesaggioOrganizzazioneId":
                    sql.append(" sort by \"paesaggio_organizzazione_id\" ").append(sortType);
            case "enteId":
                    sql.append(" sort by \"ente_id\" ").append(sortType);
            case "dataInizioDelega":
                    sql.append(" sort by \"data_inizio_delega\" ").append(sortType);
            case "dataFineDelega":
                    sql.append(" sort by \"data_fine_delega\" ").append(sortType);
            case "codiceCivilia":
                    sql.append(" sort by \"codice_civilia\" ").append(sortType);
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
    public long insert(PaesaggioOrganizzazioneCompetenzeDTO entity){
        final String sql = new StringBuilder("insert into \"common\".\"paesaggio_organizzazione_competenze\"")
                                     .append("(\"paesaggio_organizzazione_id\"")
                                     .append(",\"ente_id\"")
                                     .append(",\"data_inizio_delega\"")
                                     .append(",\"data_fine_delega\"")
                                     .append(",\"codice_civilia\"")
                                     .append(") values ")
                                     .append("(?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getPaesaggioOrganizzazioneId());
        parameters.add(entity.getEnteId());
        parameters.add(entity.getDataInizioDelega());
        parameters.add(entity.getDataFineDelega());
        parameters.add(entity.getCodiceCivilia());
        return super.insertAndGetAutoincrementValue(sql, parameters, "id");
    }

    /**
     * update by pk method
     */
    @Override
    public int update(PaesaggioOrganizzazioneCompetenzeDTO entity){
        final String sql = new StringBuilder("update \"common\".\"paesaggio_organizzazione_competenze\"")
                                     .append(" set \"paesaggio_organizzazione_id\" = ?")
                                     .append(", \"ente_id\" = ?")
                                     .append(", \"data_inizio_delega\" = ?")
                                     .append(", \"data_fine_delega\" = ?")
                                     .append(", \"codice_civilia\" = ?")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getPaesaggioOrganizzazioneId());
        parameters.add(entity.getEnteId());
        parameters.add(entity.getDataInizioDelega());
        parameters.add(entity.getDataFineDelega());
        parameters.add(entity.getCodiceCivilia());
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(PaesaggioOrganizzazioneCompetenzeDTO entity){
        final String sql = new StringBuilder("delete from \"common\".\"paesaggio_organizzazione_competenze\"")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

}
