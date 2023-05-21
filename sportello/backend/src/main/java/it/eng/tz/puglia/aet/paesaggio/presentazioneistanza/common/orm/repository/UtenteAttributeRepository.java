package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.repository;

import java.util.List;

import javax.annotation.PostConstruct;

import java.util.ArrayList;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.dto.UtenteAttributeDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.rowmapper.UtenteAttributeRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.search.UtenteAttributeSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.bean.PaginatedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import it.eng.tz.puglia.util.string.StringUtil;
/**
 * Repository for table common.utente_attribute
 */
@Repository
public class UtenteAttributeRepository extends GenericCrudDao<UtenteAttributeDTO, UtenteAttributeSearch>{

	@Autowired
	@Qualifier("namedJdbcTemplate_common")
	private NamedParameterJdbcTemplate namedJdbcTemplateCommon;
	@Autowired
	@Qualifier("jdbcTemplate_common")
	private JdbcTemplate jdbcTemplateCommon;
	
	@Value("${spring.application.name}")
	private String codiceApplicazione;
	
	@Value("${rubrica.riferimento.applicazione}")
	private String codiceApplicazioneRubrica;
	
	@PostConstruct
	public void init() {
		super.jdbcTemplateRead=this.jdbcTemplateCommon;
		super.jdbcTemplateWrite=this.jdbcTemplateCommon;
		super.namedJdbcTemplateRead=this.namedJdbcTemplateCommon;
		super.namedJdbcTemplateWrite=this.namedJdbcTemplateCommon;
	}
	
    private final UtenteAttributeRowMapper rowMapper = new UtenteAttributeRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"applicazione_id\"")
                                     .append(",\"utente_id\"")
                                     .append(",\"pec\"")
                                     .append(",\"mail\"")
                                     .append(",\"birth_date\"")
                                     .append(",\"birth_place\"")
                                     .append(",\"phone_number\"")
                                     .append(",\"mobile_number\"")
                                     .append(",\"last_richiesta_abilitazione\"")
                                     .append(",\"create_time\"")
                                     .append(" from \"common\".\"utente_attribute\"")
                                     .toString();
    @Override
    public List<UtenteAttributeDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"common\".\"utente_attribute\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public UtenteAttributeDTO find(UtenteAttributeDTO pk){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"applicazione_id\" = ?")
                                     .append(" and \"utente_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getApplicazioneId());
        parameters.add(pk.getUtenteId());
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    /**
     * search method
     */
    @Override
    public PaginatedList<UtenteAttributeDTO> search(UtenteAttributeSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getApplicazioneId())){
            sql.append(sep).append("lower(\"applicazione_id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getApplicazioneId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getUtenteId())){
            sql.append(sep).append("lower(\"utente_id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getUtenteId()));
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
        if(StringUtil.isNotEmpty(search.getBirthDate())){
            sql.append(sep).append("lower(\"birth_date\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getBirthDate()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getBirthPlace())){
            sql.append(sep).append("lower(\"birth_place\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getBirthPlace()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPhoneNumber())){
            sql.append(sep).append("lower(\"phone_number\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPhoneNumber()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getMobileNumber())){
            sql.append(sep).append("lower(\"mobile_number\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getMobileNumber()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getLastRichiestaAbilitazione())){
            sql.append(sep).append("lower(\"last_richiesta_abilitazione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getLastRichiestaAbilitazione()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCreateTime())){
            sql.append(sep).append("lower(\"create_time\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getCreateTime()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "applicazioneId":
                    sql.append(" sort by \"applicazione_id\" ").append(sortType);
            case "utenteId":
                    sql.append(" sort by \"utente_id\" ").append(sortType);
            case "pec":
                    sql.append(" sort by \"pec\" ").append(sortType);
            case "mail":
                    sql.append(" sort by \"mail\" ").append(sortType);
            case "birthDate":
                    sql.append(" sort by \"birth_date\" ").append(sortType);
            case "birthPlace":
                    sql.append(" sort by \"birth_place\" ").append(sortType);
            case "phoneNumber":
                    sql.append(" sort by \"phone_number\" ").append(sortType);
            case "mobileNumber":
                    sql.append(" sort by \"mobile_number\" ").append(sortType);
            case "lastRichiestaAbilitazione":
                    sql.append(" sort by \"last_richiesta_abilitazione\" ").append(sortType);
            case "createTime":
                    sql.append(" sort by \"create_time\" ").append(sortType);
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
    public long insert(UtenteAttributeDTO entity){
        final String sql = new StringBuilder("insert into \"common\".\"utente_attribute\"")
                                     .append("(\"applicazione_id\"")
                                     .append(",\"utente_id\"")
                                     .append(",\"pec\"")
                                     .append(",\"mail\"")
                                     .append(",\"birth_date\"")
                                     .append(",\"birth_place\"")
                                     .append(",\"phone_number\"")
                                     .append(",\"mobile_number\"")
                                     .append(",\"last_richiesta_abilitazione\"")
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
        parameters.add(entity.getApplicazioneId());
        parameters.add(entity.getUtenteId());
        parameters.add(entity.getPec());
        parameters.add(entity.getMail());
        parameters.add(entity.getBirthDate());
        parameters.add(entity.getBirthPlace());
        parameters.add(entity.getPhoneNumber());
        parameters.add(entity.getMobileNumber());
        parameters.add(entity.getLastRichiestaAbilitazione());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(UtenteAttributeDTO entity){
        final String sql = new StringBuilder("update \"common\".\"utente_attribute\"")
                                     .append(" set \"pec\" = ?")
                                     .append(", \"mail\" = ?")
                                     .append(", \"birth_date\" = ?")
                                     .append(", \"birth_place\" = ?")
                                     .append(", \"phone_number\" = ?")
                                     .append(", \"mobile_number\" = ?")
                                     .append(", \"last_richiesta_abilitazione\" = ?")
                                     .append(" where \"applicazione_id\" = ?")
                                     .append(" and \"utente_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getPec());
        parameters.add(entity.getMail());
        parameters.add(entity.getBirthDate());
        parameters.add(entity.getBirthPlace());
        parameters.add(entity.getPhoneNumber());
        parameters.add(entity.getMobileNumber());
        parameters.add(entity.getLastRichiestaAbilitazione());
        parameters.add(entity.getApplicazioneId());
        parameters.add(entity.getUtenteId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(UtenteAttributeDTO entity){
        final String sql = new StringBuilder("delete from \"common\".\"utente_attribute\"")
                                     .append(" where \"applicazione_id\" = ?")
                                     .append(" and \"utente_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getApplicazioneId());
        parameters.add(entity.getUtenteId());
        return super.update(sql, parameters);
    }
    
    public UtenteAttributeDTO findByUsername(final String username){
        final String sql = new StringBuilder(selectAll)
        							 .append(" join \"common\".\"utente\" u on \"common\".\"utente_attribute\".\"utente_id\" = u.\"id\"")
        							 .append(" join \"common\".\"applicazione\" a on \"common\".\"utente_attribute\".\"applicazione_id\" = a.\"id\"")
                                     .append(" where u.\"username\" = ? and a.\"codice\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(username);
        parameters.add(this.codiceApplicazione);
        final List<UtenteAttributeDTO> user = super.queryForList(sql, this.rowMapper, parameters);
        if(user != null && user.size() > 0)
        	return user.get(0);
        return null;
    }

}
