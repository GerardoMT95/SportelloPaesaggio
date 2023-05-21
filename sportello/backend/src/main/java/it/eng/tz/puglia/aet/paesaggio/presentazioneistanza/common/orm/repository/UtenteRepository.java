package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.repository;

import java.util.List;

import javax.annotation.PostConstruct;

import java.util.ArrayList;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.dto.UtenteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.rowmapper.UtenteRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.search.UtenteSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.bean.PaginatedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table common.utente
 */
@Repository
public class UtenteRepository extends GenericCrudDao<UtenteDTO, UtenteSearch>{

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
	
    private final UtenteRowMapper rowMapper = new UtenteRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id\"")
                                     .append(",\"username\"")
                                     .append(",\"nome\"")
                                     .append(",\"cognome\"")
                                     .append(",\"cf\"")
                                     .append(",\"data_richiesta_registrazione\"")
                                     .append(",\"data_conferma_registrazione\"")
                                     .append(",\"id_stato_registrazione\"")
                                     .append(",\"note\"")
                                     .append(" from \"common\".\"utente\"")
                                     .toString();
    @Override
    public List<UtenteDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"common\".\"utente\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public UtenteDTO find(UtenteDTO pk){
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
    public PaginatedList<UtenteDTO> search(UtenteSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getId())){
            sql.append(sep).append("lower(\"id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getUsername())){
            sql.append(sep).append(" \"username\" =  ?");
            parameters.add(search.getUsername());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getNome())){
            sql.append(sep).append("lower(\"nome\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getNome()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCognome())){
            sql.append(sep).append("lower(\"cognome\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getCognome()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCf())){
            sql.append(sep).append("lower(\"cf\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getCf()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataRichiestaRegistrazione())){
            sql.append(sep).append("lower(\"data_richiesta_registrazione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDataRichiestaRegistrazione()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataConfermaRegistrazione())){
            sql.append(sep).append("lower(\"data_conferma_registrazione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDataConfermaRegistrazione()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdStatoRegistrazione())){
            sql.append(sep).append("lower(\"id_stato_registrazione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdStatoRegistrazione()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getNote())){
            sql.append(sep).append("lower(\"note\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getNote()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "id":
                    sql.append(" sort by \"id\" ").append(sortType);
            case "username":
                    sql.append(" sort by \"username\" ").append(sortType);
            case "nome":
                    sql.append(" sort by \"nome\" ").append(sortType);
            case "cognome":
                    sql.append(" sort by \"cognome\" ").append(sortType);
            case "cf":
                    sql.append(" sort by \"cf\" ").append(sortType);
            case "dataRichiestaRegistrazione":
                    sql.append(" sort by \"data_richiesta_registrazione\" ").append(sortType);
            case "dataConfermaRegistrazione":
                    sql.append(" sort by \"data_conferma_registrazione\" ").append(sortType);
            case "idStatoRegistrazione":
                    sql.append(" sort by \"id_stato_registrazione\" ").append(sortType);
            case "note":
                    sql.append(" sort by \"note\" ").append(sortType);
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
    public long insert(UtenteDTO entity){
        final String sql = new StringBuilder("insert into \"common\".\"utente\"")
                                     .append("(\"username\"")
                                     .append(",\"nome\"")
                                     .append(",\"cognome\"")
                                     .append(",\"cf\"")
                                     .append(",\"data_richiesta_registrazione\"")
                                     .append(",\"data_conferma_registrazione\"")
                                     .append(",\"id_stato_registrazione\"")
                                     .append(",\"note\"")
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
        parameters.add(entity.getUsername());
        parameters.add(entity.getNome());
        parameters.add(entity.getCognome());
        parameters.add(entity.getCf());
        parameters.add(entity.getDataRichiestaRegistrazione());
        parameters.add(entity.getDataConfermaRegistrazione());
        parameters.add(entity.getIdStatoRegistrazione());
        parameters.add(entity.getNote());
        return super.insertAndGetAutoincrementValue(sql, parameters, "id");
    }

    /**
     * update by pk method
     */
    @Override
    public int update(UtenteDTO entity){
        final String sql = new StringBuilder("update \"common\".\"utente\"")
                                     .append(" set \"username\" = ?")
                                     .append(", \"nome\" = ?")
                                     .append(", \"cognome\" = ?")
                                     .append(", \"cf\" = ?")
                                     .append(", \"data_richiesta_registrazione\" = ?")
                                     .append(", \"data_conferma_registrazione\" = ?")
                                     .append(", \"id_stato_registrazione\" = ?")
                                     .append(", \"note\" = ?")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getUsername());
        parameters.add(entity.getNome());
        parameters.add(entity.getCognome());
        parameters.add(entity.getCf());
        parameters.add(entity.getDataRichiestaRegistrazione());
        parameters.add(entity.getDataConfermaRegistrazione());
        parameters.add(entity.getIdStatoRegistrazione());
        parameters.add(entity.getNote());
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(UtenteDTO entity){
        final String sql = new StringBuilder("delete from \"common\".\"utente\"")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }
    
    public UtenteDTO findByUsername(final String username){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"username\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(username);
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }

}
