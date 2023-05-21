package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.repository;

import java.util.List;

import javax.annotation.PostConstruct;

import java.util.ArrayList;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.dto.ApplicazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.rowmapper.ApplicazioneRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.search.ApplicazioneSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.bean.PaginatedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table common.applicazione
 */
@Repository
public class ApplicazioneRepository extends GenericCrudDao<ApplicazioneDTO, ApplicazioneSearch>{

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
	
    private final ApplicazioneRowMapper rowMapper = new ApplicazioneRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id\"")
                                     .append(",\"nome\"")
                                     .append(",\"codice\"")
                                     .append(" from  \"common\".\"applicazione\"")
                                     .toString();
    @Override
    public List<ApplicazioneDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"common\".\"applicazione\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public ApplicazioneDTO find(ApplicazioneDTO pk){
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
    public PaginatedList<ApplicazioneDTO> search(ApplicazioneSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getId())){
            sql.append(sep).append("lower(\"id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getNome())){
            sql.append(sep).append("lower(\"nome\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getNome()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCodice())){
            sql.append(sep).append("\"codice\" = ?");
            parameters.add(search.getCodice());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "id":
                    sql.append(" sort by \"id\" ").append(sortType);
            case "nome":
                    sql.append(" sort by \"nome\" ").append(sortType);
            case "codice":
                    sql.append(" sort by \"codice\" ").append(sortType);
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
    public long insert(ApplicazioneDTO entity){
        final String sql = new StringBuilder("insert into \"common\".\"applicazione\"")
                                     .append("(\"nome\"")
                                     .append(",\"codice\"")
                                     .append(") values ")
                                     .append("(?")
                                     .append(",?")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getNome());
        parameters.add(entity.getCodice());
        return super.insertAndGetAutoincrementValue(sql, parameters, "id");
    }

    /**
     * update by pk method
     */
    @Override
    public int update(ApplicazioneDTO entity){
        final String sql = new StringBuilder("update \"common\".\"applicazione\"")
                                     .append(" set \"nome\" = ?")
                                     .append(", \"codice\" = ?")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getNome());
        parameters.add(entity.getCodice());
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(ApplicazioneDTO entity){
        final String sql = new StringBuilder("delete from \"common\".\"applicazione\"")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }
    
    public int getIdByCodice(String codice){
        final String sql = new StringBuilder("select app.\"id\"")
                .append(" from \"common\".\"applicazione\" app")
                .append(" where app.\"codice\" = ?;")
                .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(codice);
        return super.queryForObjectTxRead(sql, Integer.class, parameters);

    }

}
