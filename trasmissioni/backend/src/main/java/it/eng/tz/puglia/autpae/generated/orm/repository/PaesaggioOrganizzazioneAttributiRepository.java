package it.eng.tz.puglia.autpae.generated.orm.repository;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.generated.orm.dto.PaesaggioOrganizzazioneAttributiDTO;
import it.eng.tz.puglia.autpae.generated.orm.rowmapper.PaesaggioOrganizzazioneAttributiRowMapper;
import it.eng.tz.puglia.autpae.generated.orm.search.PaesaggioOrganizzazioneAttributiSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table common.paesaggio_organizzazione_attributi
 */
@Repository
public class PaesaggioOrganizzazioneAttributiRepository extends GenericCrudDao<PaesaggioOrganizzazioneAttributiDTO, PaesaggioOrganizzazioneAttributiSearch>{
	
	@Autowired ApplicationProperties props;
	
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
	
    private final PaesaggioOrganizzazioneAttributiRowMapper rowMapper = new PaesaggioOrganizzazioneAttributiRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id\"")
                                     .append(",\"applicazione_id\"")
                                     .append(",\"paesaggio_organizzazione_id\"")
                                     .append(",\"data_creazione\"")
                                     .append(",\"data_termine\"") //"common".
                                     .append(" from  \"common\".\"paesaggio_organizzazione_attributi\"")
                                     .toString();
    @Override
    public List<PaesaggioOrganizzazioneAttributiDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"common\".\"paesaggio_organizzazione_attributi\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public PaesaggioOrganizzazioneAttributiDTO find(PaesaggioOrganizzazioneAttributiDTO pk){
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
    public PaginatedList<PaesaggioOrganizzazioneAttributiDTO> search(PaesaggioOrganizzazioneAttributiSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getId())){
            sql.append(sep).append("\"id\" = ?");
            parameters.add(search.getId());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getApplicazioneId())){
            sql.append(sep).append("\"applicazione_id\"::text = ?");
            parameters.add(search.getApplicazioneId());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPaesaggioOrganizzazioneId())){
            sql.append(sep).append("\"paesaggio_organizzazione_id\"::text = ?");
            parameters.add(search.getPaesaggioOrganizzazioneId());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataCreazione())){
            sql.append(sep).append("lower(\"data_creazione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDataCreazione()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataTermine())){
            sql.append(sep).append("lower(\"data_termine\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDataTermine()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "id":
                    sql.append(" order by \"id\" ").append(sortType);
            case "applicazioneId":
                    sql.append(" order by \"applicazione_id\" ").append(sortType);
            case "paesaggioOrganizzazioneId":
                    sql.append(" order by \"paesaggio_organizzazione_id\" ").append(sortType);
            case "dataCreazione":
                    sql.append(" order by \"data_creazione\" ").append(sortType);
            case "dataTermine":
                    sql.append(" order by \"data_termine\" ").append(sortType);
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
    public long insert(PaesaggioOrganizzazioneAttributiDTO entity){
        final String sql = new StringBuilder("insert into \"common\".\"paesaggio_organizzazione_attributi\"")
                                     .append("(\"applicazione_id\"")
                                     .append(",\"paesaggio_organizzazione_id\"")
                                     .append(",\"data_creazione\"")
                                     .append(",\"data_termine\"")
                                     .append(") values ")
                                     .append("(?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getApplicazioneId());
        parameters.add(entity.getPaesaggioOrganizzazioneId());
        parameters.add(entity.getDataCreazione());
        parameters.add(entity.getDataTermine());
        return super.insertAndGetAutoincrementValue(sql, parameters, "id");
    }

    /**
     * update by pk method
     */
    @Override
    public int update(PaesaggioOrganizzazioneAttributiDTO entity){
        final String sql = new StringBuilder("update \"common\".\"paesaggio_organizzazione_attributi\"")
                                     .append(" set \"applicazione_id\" = ?")
                                     .append(", \"paesaggio_organizzazione_id\" = ?")
                                     //.append(", \"data_creazione\" = ?")
                                     .append(", \"data_termine\" = ?")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getApplicazioneId());
        parameters.add(entity.getPaesaggioOrganizzazioneId());
        //parameters.add(entity.getDataCreazione());
        parameters.add(entity.getDataTermine());
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(PaesaggioOrganizzazioneAttributiDTO entity){
        final String sql = new StringBuilder("delete from \"common\".\"paesaggio_organizzazione_attributi\"")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }
    
    public int delete(long idOrganizzazione, long idApplicazione) throws Exception
    {
        final String sql = new StringBuilder("delete from \"common\".\"paesaggio_organizzazione_attributi\"")
                                     .append(" where \"paesaggio_organizzazione_id\" = ? and \"applicazione_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(idOrganizzazione);
        parameters.add(idApplicazione);
        return super.update(sql, parameters);
    }
    
    public long count(PaesaggioOrganizzazioneAttributiSearch search) throws Exception
    {
    	  final List<Object>  parameters = new ArrayList<Object>();
          String sql = new StringBuilder("select count(*)")
			                  .append(" from \"common\".\"paesaggio_organizzazione_attributi\"")
			                  .toString();
          sql = generateWhereSql(search, sql, parameters);
          logger.info("Eseguo la query {} cpm i seguenti parametri {}", sql, parameters);
          return super.queryForObjectTxRead(sql, Long.class, parameters);
    }
    
    public boolean organizzazioneValida(long idOrganizzazione) throws Exception
    {
    	String sql = new StringBuilder("select count(poa.*) > 0 ")
		    			 	.append("from \"common\".\"paesaggio_organizzazione_attributi\" poa ")
			                .append("join \"common\".\"applicazione\" a on poa.applicazione_id = a.id ")
			                .append("where \"paesaggio_organizzazione_id\" = ? ")
			                .append("and a.\"codice\" = ? ")
			                .append("and \"data_termine\" > now()")
			                .toString();
    	final List<Object> parameters = new ArrayList<>();
    	parameters.add(idOrganizzazione);
    	parameters.add(props.getCodiceApplicazioneProfile());
    	return queryForObjectTxRead(sql, Boolean.class, parameters);
    }
    
    public PaesaggioOrganizzazioneAttributiDTO findBeanAttivazioneValido(long idOrganizzazione) throws Exception
    {
    	String sql = new StringBuilder("select poa.* ")
			                .append("from \"common\".\"paesaggio_organizzazione_attributi\" poa ")
			                .append("join \"common\".\"applicazione\" a on poa.applicazione_id = a.id ")
			                .append("where \"paesaggio_organizzazione_id\" = ? ")
			                .append("and a.\"codice\" = ? ")
			                .append("and \"data_termine\" > now()")
			                .toString();
		final List<Object> parameters = new ArrayList<>();
		parameters.add(idOrganizzazione);
		parameters.add(props.getCodiceApplicazioneProfile());
		return queryForObjectTxRead(sql, new PaesaggioOrganizzazioneAttributiRowMapper(), parameters);
    }
    
    private String generateWhereSql(PaesaggioOrganizzazioneAttributiSearch search, String baseSql, final List<Object> parameters)
    {
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(baseSql);
        if(StringUtil.isNotEmpty(search.getId())){
            sql.append(sep).append("\"id\" = ?");
            parameters.add(search.getId());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getApplicazioneId())){
            sql.append(sep).append("\"applicazione_id\"::text = ?");
            parameters.add(search.getApplicazioneId());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPaesaggioOrganizzazioneId())){
            sql.append(sep).append("\"paesaggio_organizzazione_id\"::text = ?");
            parameters.add(search.getPaesaggioOrganizzazioneId());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataCreazione())){
            sql.append(sep).append("lower(\"data_creazione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDataCreazione()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataTermine())){
            sql.append(sep).append("lower(\"data_termine\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDataTermine()));
            sep = " and ";
        }
        if(search.getDataCreazioneDa() != null)
        {
        	sql.append(sep).append("data_creazione >= ?");
        	parameters.add(search.getDataCreazioneDa());
        	sep = " and ";
        }
        if(search.getDataCreazioneA() != null)
        {
        	sql.append(sep).append("data_creazione <= ?");
        	parameters.add(search.getDataCreazioneA());
        	sep = " and ";
        }
        if(search.getDataTermineDa() != null)
        {
        	sql.append(sep).append("data_termine >= ?");
        	parameters.add(search.getDataTermineDa());
        	sep = " and ";
        }
        if(search.getDataTermineA() != null)
        {
        	sql.append(sep).append("data_termine <= ?");
        	parameters.add(search.getDataTermineA());
        	sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "id":
                    sql.append(" order by \"id\" ").append(sortType);
            case "applicazioneId":
                    sql.append(" order by \"applicazione_id\" ").append(sortType);
            case "paesaggioOrganizzazioneId":
                    sql.append(" order by \"paesaggio_organizzazione_id\" ").append(sortType);
            case "dataCreazione":
                    sql.append(" order by \"data_creazione\" ").append(sortType);
            case "dataTermine":
                    sql.append(" order by \"data_termine\" ").append(sortType);
                break;
            default:
            	    logger.warn(StringUtil.concateneString("value ", search.getSortBy(), " not valid for sort by"));
            	    break;
            }
        }
        
        return sql.toString();
    }

}
