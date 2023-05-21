package it.eng.tz.puglia.autpae.generated.orm.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.dto.BE_to_FE.EnteBean;
import it.eng.tz.puglia.autpae.dto.BE_to_FE.PaesaggioEmailBean;
import it.eng.tz.puglia.autpae.dto.BE_to_FE.organizzazioni.PaesaggioOrganizzazioneBean;
import it.eng.tz.puglia.autpae.dto.BE_to_FE.organizzazioni.PaesaggioOrganizzazioniSearchBean;
import it.eng.tz.puglia.autpae.enumeratori.TipoOrganizzazioneSpecifica;
import it.eng.tz.puglia.autpae.enumeratori.TipoPaesaggioOrganizzazione;
import it.eng.tz.puglia.autpae.generated.orm.dto.PaesaggioOrganizzazioneDTO;
import it.eng.tz.puglia.autpae.generated.orm.rowmapper.PaesaggioOrganizzazioneExtendedRowMapper;
import it.eng.tz.puglia.autpae.generated.orm.rowmapper.PaesaggioOrganizzazioneRowMapper;
import it.eng.tz.puglia.autpae.generated.orm.search.PaesaggioOrganizzazioneSearch;
import it.eng.tz.puglia.autpae.rowmapper.EnteBeanRowMapper;
import it.eng.tz.puglia.autpae.search.PesaggioEmailSearchBean;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Common_Paesaggio_Email;
import it.eng.tz.puglia.servizi_esterni.remote.dto.PaesaggioEmailDTO;
import it.eng.tz.puglia.servizi_esterni.remote.repository.CommonRepository;
import it.eng.tz.puglia.servizi_esterni.remote.utility.TipoEnte;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table common.paesaggio_organizzazione
 */
@Repository
public class PaesaggioOrganizzazioneRepository extends GenericCrudDao<PaesaggioOrganizzazioneDTO, PaesaggioOrganizzazioneSearch>{
	
	@Autowired
	@Qualifier("namedJdbcTemplate_common")
	private NamedParameterJdbcTemplate namedJdbcTemplateCommon;
	@Autowired
	@Qualifier("jdbcTemplate_common")
	private JdbcTemplate jdbcTemplateCommon;
	
	@Autowired ApplicationProperties props;
	@Autowired CommonRepository common;
	
	@PostConstruct
	public void init() {
		super.jdbcTemplateRead=this.jdbcTemplateCommon;
		super.jdbcTemplateWrite=this.jdbcTemplateCommon;
		super.namedJdbcTemplateRead=this.namedJdbcTemplateCommon;
		super.namedJdbcTemplateWrite=this.namedJdbcTemplateCommon;
	}
	
    private final PaesaggioOrganizzazioneRowMapper rowMapper = new PaesaggioOrganizzazioneRowMapper();
    /**
     * select all method
     */
    final String selectAll = new StringBuilder("select")
                                 .append(" \"id\"")
                                 .append(",\"ente_id\"")
                                 .append(",\"tipo_organizzazione\"")
                                 .append(",\"tipo_organizzazione_specifica\"")
                                 .append(",\"codice_civilia\"")
                                 .append(",\"riferimento_organizzazione\"")
                                 .append(",\"data_creazione\"")
                                 .append(",\"data_termine\"")
                                 .append(",\"denominazione\"")
                                 .append(" from  \"common\".\"paesaggio_organizzazione\" o")
                                 .toString();
    
    final String searchPaesOrg = new StringBuilder("select ")
							    		.append(" o.\"id\"")
							            .append(",o.\"ente_id\"")
							            .append(",o.\"tipo_organizzazione\"")
							            .append(",o.\"tipo_organizzazione_specifica\"")
							            .append(",o.\"codice_civilia\"")
							            .append(",o.\"riferimento_organizzazione\"")
							            .append(",o.\"data_creazione\" as data_creazione")
							            .append(",o.\"data_termine\" as data_termine")
							            .append(",o.\"denominazione\"")
							            .append(",poa.\"data_termine\" as data_fine_abilitazione")
							            .append(",(poa.\"data_termine\" is not null) as enabled ")
	//						            .append(",(select count(*) > 0 from \"common\".\"paesaggio_organizzazione_attributi\" ")
	//						            .append("where \"applicazione_id\" = ? and paesaggio_organizzazione_id = o.id ")
	//						            .append("and \"data_termine\" > now()) as enabled ")
							            .append("from  \"common\".\"paesaggio_organizzazione\" o ")
							            .append("left join \"common\".\"paesaggio_organizzazione_attributi\" poa ")
							            .append("on \"applicazione_id\" = :app_id and \"paesaggio_organizzazione_id\" = o.\"id\" ")
							            .append("and poa.\"data_termine\" > now() ")
		                                .toString();
    @Override
    public List<PaesaggioOrganizzazioneDTO> select(){
        return super.queryForListTxRead(this.selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"common\".\"paesaggio_organizzazione\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public PaesaggioOrganizzazioneDTO find(final PaesaggioOrganizzazioneDTO pk){
        final String sql = new StringBuilder(this.selectAll)
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
    public PaginatedList<PaesaggioOrganizzazioneDTO> search(final PaesaggioOrganizzazioneSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(this.selectAll);
        if(StringUtil.isNotEmpty(search.getId())){
            sql.append(sep).append("o.\"id\"::text = ?");
            parameters.add(search.getId());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getEnteId())){
            sql.append(sep).append("o.\"ente_id\"::text = ?");
            parameters.add(search.getEnteId());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getTipoOrganizzazione())){
            sql.append(sep).append("lower(o.\"tipo_organizzazione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getTipoOrganizzazione()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getTipoOrganizzazioneSpecifica())){
            sql.append(sep).append("lower(o.\"tipo_organizzazione_specifica\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getTipoOrganizzazioneSpecifica()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCodiceCivilia())){
            sql.append(sep).append("o.\"codice_civilia\" = ?");
            parameters.add(search.getCodiceCivilia());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getRiferimentoOrganizzazione())){
            sql.append(sep).append("lower(o.\"riferimento_organizzazione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getRiferimentoOrganizzazione()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataCreazione())){
            sql.append(sep).append("lower(o.\"data_creazione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDataCreazione()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataTermine())){
            sql.append(sep).append("lower(o.\"data_termine\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDataTermine()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDenominazione())){
            sql.append(sep).append("lower(o.\"denominazione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDenominazione()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            final String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "id":
                sql.append(" order by o.\"id\" ").append(sortType);
                break;
            case "enteId":
                sql.append(" order by o.\"ente_id\" ").append(sortType);
                break;
            case "tipoOrganizzazione":
                sql.append(" order by o.\"tipo_organizzazione\" ").append(sortType);
                break;
            case "tipoOrganizzazioneSpecifica":
                sql.append(" order by o.\"tipo_organizzazione_specifica\" ").append(sortType);
                break;
            case "codiceCivilia":
                sql.append(" order by o.\"codice_civilia\" ").append(sortType);
                break;
            case "riferimentoOrganizzazione":
                sql.append(" order by o.\"riferimento_organizzazione\" ").append(sortType);
                break;
            case "dataCreazione":
                sql.append(" order by o.\"data_creazione\" ").append(sortType);
                break;
            case "dataTermine":
                sql.append(" order by o.\"data_termine\" ").append(sortType);
                break;
            case "denominazione":
                sql.append(" order by o.\"denominazione\" ").append(sortType);
                break;
            default:
        	    this.logger.warn(StringUtil.concateneString("value ", search.getSortBy(), " not valid for sort by"));
        	    break;
            }
        }
        return super.paginatedList(sql.toString(), parameters, this.rowMapper, search.getPage(), search.getLimit());
    }

    /**
     * insert all method
     */
    @Override
    public long insert(final PaesaggioOrganizzazioneDTO entity){
        final String sql = new StringBuilder("insert into \"common\".\"paesaggio_organizzazione\"")
                                     .append("(\"ente_id\"")
                                     .append(",\"tipo_organizzazione\"")
                                     .append(",\"tipo_organizzazione_specifica\"")
                                     .append(",\"codice_civilia\"")
                                     .append(",\"riferimento_organizzazione\"")
                                     .append(",\"data_creazione\"")
                                     .append(",\"data_termine\"")
                                     .append(",\"denominazione\"")
                                     .append(") values ")
                                     .append("(?")
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
        parameters.add(entity.getEnteId());
        parameters.add(entity.getTipoOrganizzazione());
        parameters.add(entity.getTipoOrganizzazioneSpecifica());
        parameters.add(entity.getCodiceCivilia());
        parameters.add(entity.getRiferimentoOrganizzazione());
        parameters.add(entity.getDataCreazione());
        parameters.add(entity.getDataTermine());
        parameters.add(entity.getDenominazione());
        return super.insertAndGetAutoincrementValue(sql, parameters, "id");
    }

    /**
     * update by pk method
     */
    @Override
    public int update(final PaesaggioOrganizzazioneDTO entity){
        final String sql = new StringBuilder("update \"common\".\"paesaggio_organizzazione\"")
                                     .append(" set \"ente_id\" = ?")
                                     .append(", \"tipo_organizzazione\" = ?")
                                     .append(", \"tipo_organizzazione_specifica\" = ?")
                                     .append(", \"codice_civilia\" = ?")
                                     .append(", \"riferimento_organizzazione\" = ?")
                                     .append(", \"data_creazione\" = ?")
                                     .append(", \"data_termine\" = ?")
                                     .append(", \"denominazione\" = ?")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getEnteId());
        parameters.add(entity.getTipoOrganizzazione());
        parameters.add(entity.getTipoOrganizzazioneSpecifica());
        parameters.add(entity.getCodiceCivilia());
        parameters.add(entity.getRiferimentoOrganizzazione());
        parameters.add(entity.getDataCreazione());
        parameters.add(entity.getDataTermine());
        parameters.add(entity.getDenominazione());
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(final PaesaggioOrganizzazioneDTO entity){
        final String sql = new StringBuilder("delete from \"common\".\"paesaggio_organizzazione\"")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }
    
    public PaginatedList<PaesaggioOrganizzazioneBean> searchOrganizzazioni(final PaesaggioOrganizzazioniSearchBean search) throws Exception
    {
    	final java.util.Map<String, Object> parameters = new java.util.HashMap<>();
    	final int applicationId = this.common.getIdApplicazione(this.props.getCodiceApplicazioneProfile());
    	parameters.put("app_id", applicationId);
    	//search.setTipiCercati(Arrays.asList(TipoPaesaggioOrganizzazione.ED, TipoPaesaggioOrganizzazione.ETI, TipoPaesaggioOrganizzazione.SBAP));
    	final String sql = this.generateWhereClause(this.searchPaesOrg, search, parameters);
        this.logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
    	return super.paginatedList(sql.toString(), parameters, new PaesaggioOrganizzazioneExtendedRowMapper(), search.getPage(), search.getLimit());
    }
    
    public List<PaesaggioOrganizzazioneDTO> searchOrganizzazioniNoPag(final PaesaggioOrganizzazioniSearchBean search) throws Exception
    {
    	final java.util.Map<String, Object> parameters = new java.util.HashMap<>();
    	//search.setTipiCercati(Arrays.asList(TipoPaesaggioOrganizzazione.ED, TipoPaesaggioOrganizzazione.ETI));
    	final String sql = this.generateWhereClause(this.selectAll, search, parameters);
        this.logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
    	return this.namedJdbcTemplateRead.query(sql.toString(), parameters, new PaesaggioOrganizzazioneRowMapper());
    }
    
    public List<EnteBean> findEntiByOrganizzazione(final long idOrganizzazione) throws Exception
    {
    	final String sql = new StringBuilder("select oc.\"id\", e.\"descrizione\" as nomeEnte, oc.\"data_inizio_delega\", oc.\"data_fine_delega\", ")
	    						.append("e.\"id\" as ente_id, e.\"tipo\" from \"common\".\"paesaggio_organizzazione_competenze\" oc ")
	    						.append("join \"common\".\"ente\" e on e.\"id\" = oc.\"ente_id\" ")
	    						.append("where oc.\"paesaggio_organizzazione_id\" = ?").toString();
    	final List<Object> parameters = new ArrayList<>();
    	parameters.add(idOrganizzazione);
    	this.logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
    	return super.queryForList(sql, new EnteBeanRowMapper(), parameters);
    }
    
    public List<EnteBean> findComuniOfProvinciaOrganizzazione(final long idOrganizzazione) throws Exception
    {
    	final String sql = new StringBuilder("select oc.\"id\", comune.\"descrizione\" as nomeEnte, oc.\"data_inizio_delega\", ")
								.append("oc.\"data_fine_delega\", comune.\"id\" as ente_id, comune.\"tipo\" ")
								.append("from \"common\".\"ente\" comune ")
								.append("join \"common\".\"ente\" prov on prov.\"id\" = comune.\"parent_id\" ")
								.append("join \"common\".\"paesaggio_organizzazione_competenze\" oc on oc.\"ente_id\" = prov.\"id\"")
								.append("where oc.\"paesaggio_organizzazione_id\" = ? and comune.\"tipo\" = ?")
								.append("and prov.\"tipo\" = ?").toString();
    	final List<Object> parameters = new ArrayList<>();
    	parameters.add(idOrganizzazione);
    	parameters.add(TipoEnte.comune.getCodice());
    	parameters.add(TipoEnte.provincia.getCodice());
    	this.logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
    	return super.queryForList(sql, new EnteBeanRowMapper(), parameters);
    }
    
    public PaginatedList<PaesaggioEmailBean> searchPaesaggioEmail(final PesaggioEmailSearchBean search) throws Exception
    {
    	final StringBuilder sql = new StringBuilder("select \"paesaggio_email\".*, \"ente\".\"descrizione\" as denominazione_ente, ")
    										.append("\"paesaggio_organizzazione\".\"tipo_organizzazione_specifica\" as tipo_organizzazione_specifica, ")
    										.append("\"paesaggio_organizzazione\".\"denominazione\" as denominazione_organizzazione ")
    										.append("from \"common\".\"paesaggio_email\" ")
    										.append("left join \"common\".\"ente\" on \"ente\".\"id\" = \"paesaggio_email\".\"ente_id\" ")
    										.append("left join \"common\".\"paesaggio_organizzazione\" on \"paesaggio_organizzazione\".\"id\" = \"paesaggio_email\".\"organizzazione_id\"");
    	search.getSqlWhereClause(sql);
    	search.getSqlOrderByClause(sql);
    	final java.util.Map<String, Object> parameters = search.getSqlParameters();
    	this.logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
    	return super.paginatedList(sql.toString(), parameters, new RowMapper<PaesaggioEmailBean>()
    	{

			@Override
			public PaesaggioEmailBean mapRow(final ResultSet rs, final int rowNum) throws SQLException
			{
				PaesaggioEmailBean bean = null;
				if(rs != null)
				{
					bean = new PaesaggioEmailBean();
					bean.setId(rs.getLong(Common_Paesaggio_Email.id.columnName()));
					bean.setEmail(rs.getString (Common_Paesaggio_Email.email.columnName()));
					bean.setDenominazione(rs.getString(Common_Paesaggio_Email.denominazione.columnName()));
					bean.setPec(rs.getBoolean(Common_Paesaggio_Email.pec.columnName()));
					bean.setOrganizzazioneId(rs.getInt(Common_Paesaggio_Email.organizzazione_id.columnName()));
					if(rs.getObject(Common_Paesaggio_Email.ente_id.columnName()) != null)
						bean.setEnteId(rs.getInt(Common_Paesaggio_Email.ente_id.columnName()));	
					bean.setDenominazioneEnte(rs.getString("denominazione_ente"));
					bean.setDenominazioneOrganizzazione(rs.getString("denominazione_organizzazione"));
					bean.setTipoOrganizzazione(rs.getString("tipo_organizzazione_specifica") != null ? TipoOrganizzazioneSpecifica.fromValue(rs.getString("tipo_organizzazione_specifica")) : null);
				}
				return bean;
			}
    		
    	}, search.getPage(), search.getLimit());
    }
    
    public Long insertPaesaggioEmail(final PaesaggioEmailDTO bean) throws Exception
    {
    	final String sql = new StringBuilder("insert into \"common\".\"paesaggio_email\"(\"codice_applicazione\", ")
    								.append("\"email\", \"denominazione\", \"pec\", \"organizzazione_id\", \"ente_id\") ")
    								.append("values(:codice_applicazione, :email, :denominazione, :pec, :organizzazione_id, ")
    								.append(":ente_id) returning \"id\"").toString();
    	final java.util.Map<String, Object> parameters = new HashMap<>();
    	this.toParamaters(bean, parameters);
    	this.logger.info("Eseguo la query {} con i eguenti parametri {}", sql, parameters);
    	return this.namedJdbcTemplateWrite.queryForObject(sql, parameters, Long.class);
    }
    
    public void updatePaesaggioEmail(final PaesaggioEmailDTO bean) throws Exception
    {
    	final String sql = new StringBuilder("update \"common\".\"paesaggio_email\" set ")
    								.append("\"email\" = :email, \"denominazione\" = :denominazione, \"pec\" = :pec, ")
    								.append("\"organizzazione_id\" = :organizzazione_id, \"ente_id\" = :ente_id where \"id\" = :id")
    								.toString();
    	final java.util.Map<String, Object> parameters = new HashMap<>();
    	this.toParamaters(bean, parameters);
    	parameters.put("id", bean.getId());
    	this.logger.info("Eseguo la query {} con i eguenti parametri {}", sql, parameters);
    	this.namedJdbcTemplateWrite.update(sql, parameters);
    }
    
    public void deletePaesaggioEmail(final Long idPaesaggioEmail) throws Exception
    {
    	final String sql = "delete from \"common\".\"paesaggio_email\" where \"id\" = :id";
    	final java.util.Map<String, Object> parameters = new HashMap<>();
    	parameters.put("id", idPaesaggioEmail);
    	this.logger.info("Eseguo la query {} con i eguenti parametri {}", sql, parameters);
    	this.namedJdbcTemplateWrite.update(sql, parameters);
    }
    
    private void toParamaters(final PaesaggioEmailDTO bean, final java.util.Map<String, Object> parameters) throws Exception
    {
    	parameters.put("codice_applicazione", bean.getCodiceApplicazione());
    	parameters.put("email", bean.getEmail());
    	parameters.put("denominazione", bean.getDenominazione());
    	parameters.put("pec", bean.getPec());
    	parameters.put("organizzazione_id", bean.getOrganizzazioneId());
    	parameters.put("ente_id", bean.getEnteId());
    }
    
    private String generateWhereClause(final String baseSql, final PaesaggioOrganizzazioniSearchBean search, final java.util.Map<String, Object> parameters)
    {
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(baseSql);
        if(StringUtil.isNotEmpty(search.getId()))
        {
            sql.append(sep).append("o.\"id\"::text = :id");
            parameters.put("id", search.getId());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getEnteId()))
        {
            sql.append(sep).append("o.\"ente_id\"::text = :ente_id");
            parameters.put("ente_id", search.getEnteId());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getTipoOrganizzazione()))
        {
            sql.append(sep).append("o.\"tipo_organizzazione\" like :tipo_org");
            parameters.put("tipo_org", search.getTipoOrganizzazione());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getTipoOrganizzazioneSpecifica()))
        {
            sql.append(sep).append("o.\"tipo_organizzazione_specifica\" like :tipo_org_spec");
            parameters.put("tipo_org_spec", search.getTipoOrganizzazioneSpecifica());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCodiceCivilia()))
        {
            sql.append(sep).append("o.\"codice_civilia\" = :cod_civilia");
            parameters.put("cod_civilia", search.getCodiceCivilia());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getRiferimentoOrganizzazione()))
        {
            sql.append(sep).append("lower(o.\"riferimento_organizzazione\"::text) like :rif_org");
            parameters.put("rif_org", StringUtil.convertLike(search.getRiferimentoOrganizzazione()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataCreazione()))
        {
            sql.append(sep).append("lower(o.\"data_creazione\"::text) like :data_creazione");
            parameters.put("data_creazione", StringUtil.convertLike(search.getDataCreazione()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataTermine()))
        {
            sql.append(sep).append("lower(o.\"data_termine\"::text) like :data_termine");
            parameters.put("data_termine", StringUtil.convertLike(search.getDataTermine()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDenominazione()))
        {
            sql.append(sep).append("lower(o.\"denominazione\"::text) like :denominazione");
            parameters.put("denominazione", StringUtil.convertLike(search.getDenominazione().toLowerCase()));
            sep = " and ";
        }
        if(search.getEnabled() != null)
        {
        	sql.append(sep).append("o.\"enabled\" = :enabled");
        	parameters.put("enabled", search.getEnabled());
        	sep = " and ";
        }
        if(search.getDataCreazioneDa() != null)
        {
        	sql.append(sep).append("(o.\"data_creazione\"::date) >= :creazione_da");
        	parameters.put("creazione_da", search.getDataCreazioneDa());
        	sep = " and ";
        }
        if(search.getDataCreazioneA() != null)
        {
        	sql.append(sep).append("(o.\"data_creazione\"::date) <= :creazione_a");
        	parameters.put("creazione_a", search.getDataCreazioneA());
        	sep = " and ";
        }
        if(search.getDataTermineDa() != null)
        {
        	sql.append(sep).append("(o.\"data_termine\"::date) >= :termine_da");
        	parameters.put("termine_da", search.getDataTermineDa());
        	sep = " and ";
        }
        if(search.getDataTermineA() != null)
        {
        	sql.append(sep).append("(o.\"data_termine\"::date) <= :termine_a");
        	parameters.put("termine_a", search.getDataTermineA());
        	sep = " and ";
        }
        if(search.getTipiCercati() != null && !search.getTipiCercati().isEmpty())
        {
        	sql.append(sep).append("o.\"tipo_organizzazione\" in (:tipi_cercati)");
        	parameters.put("tipi_cercati", search.getTipiCercati().stream().map(TipoPaesaggioOrganizzazione::toString).collect(Collectors.toList()));
        	sep = " and ";
        }
        if(search.getTipiSpecCercati() != null && !search.getTipiSpecCercati().isEmpty())
        {
        	sql.append(sep).append("o.\"tipo_organizzazione_specifica\" in (:tipi_cercati_spec)");
        	parameters.put("tipi_cercati_spec", search.getTipiSpecCercati().stream().map(TipoOrganizzazioneSpecifica::getValue).collect(Collectors.toList()));
        	sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy()))
        {
            final String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "id": sql.append(" order by o.\"id\" ").append(sortType); break;
            case "enteId": sql.append(" order by o.\"ente_id\" ").append(sortType); break;
            case "tipoOrganizzazione": sql.append(" order by o.\"tipo_organizzazione\" ").append(sortType); break;
            case "tipoOrganizzazioneSpecifica": sql.append(" order by o.\"tipo_organizzazione_specifica\" ").append(sortType); break;
            case "codiceCivilia": sql.append(" order by o.\"codice_civilia\" ").append(sortType); break;
            case "riferimentoOrganizzazione": sql.append(" order by o.\"riferimento_organizzazione\" ").append(sortType); break;
            case "dataCreazione": sql.append(" order by o.\"data_creazione\" ").append(sortType); break;
            case "dataTermine": sql.append(" order by o.\"data_termine\" ").append(sortType); break;
            case "denominazione": sql.append(" order by o.\"denominazione\" ").append(sortType); break;
            default: this.logger.warn(StringUtil.concateneString("value ", search.getSortBy(), " not valid for sort by")); break;
            }
        }
        return sql.toString();
    }

    public boolean isMultiComune(final Long id) {
    	final String sql = "select count(*) from common.paesaggio_organizzazione where id = ? and tipo_organizzazione_specifica in (?, ?)";
    	final List<Integer> list = this.jdbcTemplateCommon.queryForList(sql
    			                                                       ,Integer.class
    			                                                       ,id
    			                                                       ,TipoOrganizzazioneSpecifica.PROVINCIA.getValue()
    			                                                       ,TipoOrganizzazioneSpecifica.REGIONE.getValue()
    			                                                       );
    	return ListUtil.isNotEmpty(list) && list.get(0) > 0;
    	
    }
}
