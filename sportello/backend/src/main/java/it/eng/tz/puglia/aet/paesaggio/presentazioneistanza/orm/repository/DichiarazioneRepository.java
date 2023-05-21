package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DichiarazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.DichiarazioneRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.DichiarazioneSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.date.DateUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.dichiarazione
 */
@Repository
public class DichiarazioneRepository extends GenericCrudDao<DichiarazioneDTO, DichiarazioneSearch>{

    private final DichiarazioneRowMapper rowMapper = new DichiarazioneRowMapper();
 
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id\"")
                                     .append(",\"testo\"")
                                     .append(",\"label_cb\"")
                                     .append(",\"data_inizio\"")
                                     .append(",\"data_fine\"")
                                     .append(",\"id_procedimento\"")
                                     .append(" from \"presentazione_istanza\".\"dichiarazione\"")
                                     .toString();
   
    /**
     * select all method
     */    
    @Override
    public List<DichiarazioneDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"dichiarazione\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public DichiarazioneDTO find(DichiarazioneDTO pk){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where id = ? ")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getId());
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    
    /**
     * search method
     */
    @Override
    public PaginatedList<DichiarazioneDTO> search(DichiarazioneSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getId())){
            sql.append(sep).append("lower(\"id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getTesto())){
            sql.append(sep).append("lower(\"testo\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getTesto()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getLabelCb())){
            sql.append(sep).append("lower(\"label_cb\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getLabelCb()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataInizio())){
            sql.append(sep).append("lower(\"data_inizio\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDataInizio()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataFine())){
            sql.append(sep).append("lower(\"data_fine\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDataFine()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "id":
                    sql.append(" sort by \"id\" ").append(sortType);
            case "testo":
                    sql.append(" sort by \"testo\" ").append(sortType);
            case "labelCb":
                    sql.append(" sort by \"label_cb\" ").append(sortType);
            case "dataInizio":
                    sql.append(" sort by \"data_inizio\" ").append(sortType);
            case "dataFine":
                    sql.append(" sort by \"data_fine\" ").append(sortType);
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
    public long insert(DichiarazioneDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"dichiarazione\"")
                                     .append("(\"testo\"")
                                     .append(",\"label_cb\"")
                                     .append(",\"data_inizio\"")
                                     .append(",\"id_procedimento\"")
                                     .append(") values ")
                                     .append("(?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getTesto());
        parameters.add(entity.getLabelCb());
        parameters.add(entity.getDataInizio()==null?DateUtil.currentTimestamp():entity.getDataInizio());
        parameters.add(entity.getIdProcedimento());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(DichiarazioneDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"dichiarazione\"")
                                     .append(" set \"testo\" = ?")
                                     .append(", \"label_cb\" = ?")
                                     .append(", \"data_fine\" = ?")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getTesto());
        parameters.add(entity.getLabelCb());
        parameters.add(entity.getDataFine());
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(DichiarazioneDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"dichiarazione\"")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }
    
    /**
     * set DichiarazSchTecAccettata by idPratica method
     * @return 
     */

	public int setDichiarazSchTecAccettata(UUID idPratica, boolean accettata) {
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"pratica\"")
                .append(" set \"dichiaraz_sch_tec_accettata\" = ?")
                .append(" where \"id\" = ?")
                .toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(accettata);
		parameters.add(idPratica);
		return super.update(sql, parameters);
	}

    /**
     * get DichiarazSchTecAccettata by idPratica method
     */
	public boolean getDichiarazSchTecAccettata(UUID idPratica) {
        final String sql = new StringBuilder("select \"dichiaraz_sch_tec_accettata\" ")
					                 .append(" from \"presentazione_istanza\".\"pratica\"")
					                 .append(" where \"id\" = ?")
					                 .toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(idPratica);
        return super.queryForObjectTxRead(sql, Boolean.class, parameters);
	}
	
	
	public DichiarazioneDTO findByIdFascicolo(UUID idPratica) {
		final String sqlDataRifPratica="(select data_creazione from pratica where id = ? )";
		final String sqlIdProcFromPratica="(select tipo_procedimento from pratica where id = ? )";
		 final String sql = new StringBuilder(selectAll)
                 .append(" WHERE ")
                 .append(" id_procedimento = ")
                 .append(sqlIdProcFromPratica)
                 .append(" AND data_inizio <= ")
                 .append(sqlDataRifPratica)
                 .append(" AND ")
                 .append("(data_fine is null OR ")
                 .append(" data_fine >= ")
                 .append(sqlDataRifPratica)
                 .append(")")
                 .toString();
		 final List<Object> parameters=new ArrayList<>();
		 parameters.add(idPratica);
		 parameters.add(idPratica);
		 parameters.add(idPratica);
		 return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
      }

	public DichiarazioneDTO findByTipoProcedimentoAndData(Integer id, Date date) {
		final String sql = new StringBuilder(selectAll)
                 .append(" WHERE ")
                 .append(" id_procedimento = ? ")
                 .append(" AND data_inizio <= ? ")
                 .append(" AND ")
                 .append("(data_fine is null OR ")
                 .append(" data_fine >= ? )")
                 .toString();
		 final List<Object> parameters=new ArrayList<>();
		 parameters.add(id);
		 parameters.add(date);
		 parameters.add(date);
		 return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
	}
	
	public DichiarazioneDTO findLastValid(Integer id) {
		final String sql = new StringBuilder(selectAll)
                 .append(" WHERE ")
                 .append(" id_procedimento = ? ")
                 .append(" AND data_fine is null ")
                 .toString();
		 final List<Object> parameters=new ArrayList<>();
		 parameters.add(id);
		 return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
	}
	
}