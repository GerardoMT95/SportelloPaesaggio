package it.eng.tz.puglia.autpae.generated.orm.repository;

import java.util.List;

import javax.annotation.PostConstruct;

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
 * Repository for table common.paesaggio_organizzazione_rubrica
 */
@Repository
public class PaesaggioOrganizzazioneRubricaRepository extends GenericCrudDao<PaesaggioOrganizzazioneRubricaDTO, PaesaggioOrganizzazioneRubricaSearch>{

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
	
    private final PaesaggioOrganizzazioneRubricaRowMapper rowMapper = new PaesaggioOrganizzazioneRubricaRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id\"")
                                     .append(",\"paesaggio_organizzazione_id\"")
                                     .append(",\"codice_applicazione\"")
                                     .append(",\"nome\"")
                                     .append(",\"pec\"")
                                     .append(",\"mail\"")
                                     .append(",\"data_creazione\"")
                                     .append(",\"data_modifica\"")
                                     .append(",\"username_creazione\"")
                                     .append(",\"username_modifica\"")
                                     .append(" from  \"common\".\"paesaggio_organizzazione_rubrica\"")
                                     .toString();
    @Override
    public List<PaesaggioOrganizzazioneRubricaDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"common\".\"paesaggio_organizzazione_rubrica\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public PaesaggioOrganizzazioneRubricaDTO find(PaesaggioOrganizzazioneRubricaDTO pk){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"id\" = ?")
                                     .append(" and \"paesaggio_organizzazione_id\" = ?")
                                     .append(" and \"codice_applicazione\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getId());
        parameters.add(pk.getPaesaggioOrganizzazioneId());
        parameters.add(pk.getCodiceApplicazione());
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    /**
     * search method
     */
    @Override
    public PaginatedList<PaesaggioOrganizzazioneRubricaDTO> search(PaesaggioOrganizzazioneRubricaSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getId())){
            sql.append(sep).append("\"id\"::text = ?");
            parameters.add(search.getId());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPaesaggioOrganizzazioneId())){
            sql.append(sep).append("\"paesaggio_organizzazione_id\"::text = ?");
            parameters.add(search.getPaesaggioOrganizzazioneId());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCodiceApplicazione())){
            sql.append(sep).append("\"codice_applicazione\" = ?");
            parameters.add(search.getCodiceApplicazione());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getNome())){
            sql.append(sep).append("lower(\"nome\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getNome()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPec())){
            sql.append(sep).append("lower(\"pec\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPec()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getMail())){
            sql.append(sep).append("lower(\"mail\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getMail()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataCreazione())){
            sql.append(sep).append("lower(\"data_creazione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDataCreazione()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataModifica())){
            sql.append(sep).append("lower(\"data_modifica\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDataModifica()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getUsernameCreazione())){
            sql.append(sep).append("lower(\"username_creazione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getUsernameCreazione()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getUsernameModifica())){
            sql.append(sep).append("lower(\"username_modifica\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getUsernameModifica()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "id":
                    sql.append(" sort by \"id\" ").append(sortType);
            case "paesaggioOrganizzazioneId":
                    sql.append(" sort by \"paesaggio_organizzazione_id\" ").append(sortType);
            case "codiceApplicazione":
                    sql.append(" sort by \"codice_applicazione\" ").append(sortType);
            case "nome":
                    sql.append(" sort by \"nome\" ").append(sortType);
            case "pec":
                    sql.append(" sort by \"pec\" ").append(sortType);
            case "mail":
                    sql.append(" sort by \"mail\" ").append(sortType);
            case "dataCreazione":
                    sql.append(" sort by \"data_creazione\" ").append(sortType);
            case "dataModifica":
                    sql.append(" sort by \"data_modifica\" ").append(sortType);
            case "usernameCreazione":
                    sql.append(" sort by \"username_creazione\" ").append(sortType);
            case "usernameModifica":
                    sql.append(" sort by \"username_modifica\" ").append(sortType);
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
    public long insert(PaesaggioOrganizzazioneRubricaDTO entity){
        final String sql = new StringBuilder("insert into \"common\".\"paesaggio_organizzazione_rubrica\"")
                                     .append("(\"paesaggio_organizzazione_id\"")
                                     .append(",\"codice_applicazione\"")
                                     .append(",\"nome\"")
                                     .append(",\"pec\"")
                                     .append(",\"mail\"")
                                     .append(",\"data_creazione\"")
                                     .append(",\"data_modifica\"")
                                     .append(",\"username_creazione\"")
                                     .append(",\"username_modifica\"")
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
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getPaesaggioOrganizzazioneId());
        parameters.add(entity.getCodiceApplicazione());
        parameters.add(entity.getNome());
        parameters.add(entity.getPec());
        parameters.add(entity.getMail());
        parameters.add(entity.getDataCreazione());
        parameters.add(entity.getDataModifica());
        parameters.add(entity.getUsernameCreazione());
        parameters.add(entity.getUsernameModifica());
        return super.insertAndGetAutoincrementValue(sql, parameters, "id");
    }

    /**
     * update by pk method
     */
    @Override
    public int update(PaesaggioOrganizzazioneRubricaDTO entity){
        final String sql = new StringBuilder("update \"common\".\"paesaggio_organizzazione_rubrica\"")
                                     .append(" set \"nome\" = ?")
                                     .append(", \"pec\" = ?")
                                     .append(", \"mail\" = ?")
                                     .append(", \"data_creazione\" = ?")
                                     .append(", \"data_modifica\" = ?")
                                     .append(", \"username_creazione\" = ?")
                                     .append(", \"username_modifica\" = ?")
                                     .append(" where \"id\" = ?")
                                     .append(" and \"paesaggio_organizzazione_id\" = ?")
                                     .append(" and \"codice_applicazione\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getNome());
        parameters.add(entity.getPec());
        parameters.add(entity.getMail());
        parameters.add(entity.getDataCreazione());
        parameters.add(entity.getDataModifica());
        parameters.add(entity.getUsernameCreazione());
        parameters.add(entity.getUsernameModifica());
        parameters.add(entity.getId());
        parameters.add(entity.getPaesaggioOrganizzazioneId());
        parameters.add(entity.getCodiceApplicazione());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(PaesaggioOrganizzazioneRubricaDTO entity){
        final String sql = new StringBuilder("delete from \"common\".\"paesaggio_organizzazione_rubrica\"")
                                     .append(" where \"id\" = ?")
                                     .append(" and \"paesaggio_organizzazione_id\" = ?")
                                     .append(" and \"codice_applicazione\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        parameters.add(entity.getPaesaggioOrganizzazioneId());
        parameters.add(entity.getCodiceApplicazione());
        return super.update(sql, parameters);
    }

}
