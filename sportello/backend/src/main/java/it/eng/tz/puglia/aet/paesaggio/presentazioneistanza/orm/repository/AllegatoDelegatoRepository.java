package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.SezioneAllegati;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatoDelegatoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.AllegatoDelegatoRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.AllegatoDelegatoSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.allegato_delegato
 */
@Repository
public class AllegatoDelegatoRepository extends GenericCrudDao<AllegatoDelegatoDTO, AllegatoDelegatoSearch>{

    private final AllegatoDelegatoRowMapper rowMapper = new AllegatoDelegatoRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id_allegato\"")
                                     .append(",\"id_pratica\"")
                                     .append(",\"indice_delegato\"")
                                     .append(" from \"presentazione_istanza\".\"allegato_delegato\"")
                                     .toString();
    @Override
    public List<AllegatoDelegatoDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"allegato_delegato\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public AllegatoDelegatoDTO find(AllegatoDelegatoDTO pk){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"id_allegato\" = ?")
                                     .append(" and \"id_pratica\" = ?")
                                     .append(" and \"indice_delegato\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getIdAllegato());
        parameters.add(pk.getIdPratica());
        parameters.add(pk.getIndiceDelegato());
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    /**
     * search method
     */
    @Override
    public PaginatedList<AllegatoDelegatoDTO> search(AllegatoDelegatoSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getIdAllegato())){
            sql.append(sep).append("lower(\"id_allegato\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdAllegato()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdPratica())){
            sql.append(sep).append("\"id_pratica\"::text = ?");
            parameters.add(search.getIdPratica());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIndiceDelegato())){
            sql.append(sep).append("\"indice_delegato\"::text = ?");
            parameters.add(search.getIndiceDelegato());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "idAllegato":
                    sql.append(" order by \"id_allegato\" ").append(sortType);
                	   break;
            case "idPratica":
                    sql.append(" order by \"id_pratica\" ").append(sortType);
                	   break;
            case "indiceDelegato":
                    sql.append(" order by \"indice_delegato\" ").append(sortType);
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
    public long insert(AllegatoDelegatoDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"allegato_delegato\"")
                                     .append("(\"id_allegato\"")
                                     .append(",\"id_pratica\"")
                                     .append(",\"indice_delegato\"")
                                     .append(") values ")
                                     .append("(?")
                                     .append(",?")
                                     .append(",?")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIdAllegato());
        parameters.add(entity.getIdPratica());
        parameters.add(entity.getIndiceDelegato());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(AllegatoDelegatoDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"allegato_delegato\"")
                                     .append(" where \"id_allegato\" = ?")
                                     .append(" and \"id_pratica\" = ?")
                                     .append(" and \"indice_delegato\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIdAllegato());
        parameters.add(entity.getIdPratica());
        parameters.add(entity.getIndiceDelegato());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(AllegatoDelegatoDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"allegato_delegato\"")
                                     .append(" where \"id_allegato\" = ?")
                                     .append(" and \"id_pratica\" = ?")
                                     .append(" and \"indice_delegato\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIdAllegato());
        parameters.add(entity.getIdPratica());
        parameters.add(entity.getIndiceDelegato());
        return super.update(sql, parameters);
    }
    
    public int deleteAllegatoDelegato(UUID praticaId)
    {
	final String sql = "delete from \"presentazione_istanza\".\"allegato_delegato\" where \"id_pratica\" = ?";
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(praticaId);
        return update(sql, parameters);
    }
    

    
    public AllegatoDelegatoDTO findAllegatoBySezione(UUID idPratica,short indiceDelegato,SezioneAllegati allegatoDelega){
    	final String select = new StringBuilder("select")
                .append(" \"ad\".\"id_allegato\"")
                .append(",\"ad\".\"id_pratica\"")
                .append(",\"ad\".\"indice_delegato\"")
                .append(" from \"presentazione_istanza\".\"allegato_delegato\" ad ")
                .append(" ,\"presentazione_istanza\".\"allegati\" a ")
                .append(" ,\"presentazione_istanza\".\"allegati_tipo_contenuto\" atc ")
                .append(" ,\"presentazione_istanza\".\"tipo_contenuto\" tc ")
                .append(" where  ")
                .append("  \"ad\".\"id_pratica\" = ?")
                .append(" and \"ad\".\"indice_delegato\" = ? ")
                .append(" and \"a\".\"id\" =  \"ad\".\"id_allegato\" ")
                .append(" and \"atc\".\"allegati_id\" =  \"ad\".\"id_allegato\" ")
                .append(" and \"atc\".\"tipo_contenuto_id\" =  \"tc\".\"id\" ")
                .append(" and \"tc\".\"sezione\" =  ?  ")
                .append(" limit 1 ") 
                .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(idPratica);
        parameters.add(indiceDelegato);
        parameters.add(allegatoDelega.name());
        return super.queryForObjectTxRead(select, this.rowMapper, parameters);
    }
    

}
