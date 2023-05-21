package it.eng.tz.puglia.autpae.generated.orm.repository;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.generated.orm.dto.ConfigurazioneDTO;
import it.eng.tz.puglia.autpae.generated.orm.rowmapper.ConfigurazioneRowMapper;
import it.eng.tz.puglia.autpae.generated.orm.search.ConfigurazioneSearch;
import it.eng.tz.puglia.bean.PaginatedList;

/**
 * Repository for table configurazione
 */
@Repository
public class ConfigurazioneRepository extends GenericCrudDao<ConfigurazioneDTO, ConfigurazioneSearch>{

    private final ConfigurazioneRowMapper rowMapper = new ConfigurazioneRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id\"")
                                     .append(",\"chiave\"")
                                     .append(",\"valore\"")
                                     .append(",\"start_date\"")
                                     .append(",\"end_date\"")
                                     .append(",\"user_create\"")
                                     .append(",\"user_update\"")
                                     .append(" from \"configurazione\"")
                                     .toString();
    public List<ConfigurazioneDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    
    /**
     * find by pk method
     */
    public ConfigurazioneDTO find(ConfigurazioneDTO pk){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getId());
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    
    
    /**
     * update by pk method
     */
    public int update(ConfigurazioneDTO entity){
        final String sql = new StringBuilder("update \"configurazione\"")
                                     .append(" set ")
                                     .append(" \"end_date\" = ?")
                                     .append(" ,\"user_update\" = ?")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getEndDate());
        parameters.add(entity.getUserUpdate());
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

    
    public long insert(ConfigurazioneDTO entity){
        final String sql = new StringBuilder("insert into \"configurazione\"")
                                     .append("(\"chiave\"")
                                     .append(",\"valore\"")
                                     .append(",\"start_date\"")
                                     .append(",\"user_create\"")
                                     .append(") values ")
                                     .append("(?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getChiave());
        parameters.add(entity.getValore());
        parameters.add(entity.getStartDate());
        parameters.add(entity.getUserCreate());
        return super.insertAndGetAutoincrementValue(sql, parameters, "id");
    }

    
    public ConfigurazioneDTO findByChiaveEData(String chiave,Date data){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"chiave\" = ?")
                                     .append(" AND \"start_date\" <= ?")
                                     .append(" AND (\"end_date\" is null OR \"end_date\">= ? )")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(chiave);
        parameters.add(data);
        parameters.add(data);
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    
    public ConfigurazioneDTO findByChiaveLast(String chiave){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"chiave\" = ?")
                                     .append(" AND \"end_date\" is null ")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(chiave);
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }


	@Override
	public long count() {
		return 0;
	}


	@Override
	public int delete(ConfigurazioneDTO entity) {
		return 0;
	}


	@Override
	public PaginatedList<ConfigurazioneDTO> search(ConfigurazioneSearch bean) {
		return null;
	}
    
    
}
