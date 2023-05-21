package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ComuniCompetenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.ComuniCompetenzaRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.ComuniCompetenzaSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.bean.SortBy;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.comuni_competenza
 */
@Repository
public class ComuniCompetenzaRepository extends GenericCrudDao<ComuniCompetenzaDTO, ComuniCompetenzaSearch>{

    private final ComuniCompetenzaRowMapper rowMapper = new ComuniCompetenzaRowMapper();
    private String selectAll=new StringBuilder("select")
            .append(" \"pratica_id\"")
            .append(",\"ente_id\"")
            .append(",\"descrizione\"")
            .append(",\"cod_cat\"")
            .append(",\"cod_istat\"")
            .append(",\"data_inserimento\"")
            .append(",\"vincolo_art_38\"")
            .append(",\"vincolo_art_100\"")
            .append(",\"notifica\"")
            .append(" from \"presentazione_istanza\".\"comuni_competenza\"")
            .toString();
    
    private String generateWhereClause(String baseSql, Map<String, Object> parameters, ComuniCompetenzaSearch search)
    {
    	String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(baseSql);
        if(search.getPraticaId() != null)
        {
            sql.append(sep).append("\"pratica_id\" = :id_pratica");
            if(search.isSoloComuniIntervento())
            {
            	sql.append(" and \"ente_id\" in (")
            	   .append(" select \"comune_id\" from \"localizzazione_intervento\"")
            	   .append(" where \"pratica_id\" = :id_pratica)");
            }
            parameters.put("id_pratica", search.getPraticaId());
            sep = " and ";
        }
        if(search.getEnteId() != null)
        {
            sql.append(sep).append("\"ente_id\" = :id_ente");
            parameters.put("id_ente", search.getEnteId());
            sep = " and ";
        }
        if(search.getDataInserimento() != null)
        {
            sql.append(sep).append("\"data_inserimento\" = :data_inserimento");
            parameters.put("data_inserimento", search.getDataInserimento());
            sep = " and ";
        }
    	return sql.toString();
    }
    private String generateOrderBy(String baseSql, String column, SortBy direction)
    {
    	StringBuilder sql = new StringBuilder(baseSql);
    	if(StringUtil.isNotEmpty(column))
    	{
            switch (column) 
            {
            case "praticaId":
                    sql.append(" sort by \"pratica_id\" ").append(direction.toString());
                    break;
            case "enteId":
                    sql.append(" sort by \"ente_id\" ").append(direction.toString());
                    break;
            case "dataInserimento":
                    sql.append(" sort by \"data_inserimento\" ").append(direction.toString());
                break;
            default:
        	    logger.warn(StringUtil.concateneString("value ", column, " not valid for sort by"));
        	    break;
            }
        }
    	return sql.toString();
    }
    /**
     * select all method
     */
    @Override
    public List<ComuniCompetenzaDTO> select(){
        final String sql = selectAll;
        return super.queryForListTxRead(sql, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"comuni_competenza\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public ComuniCompetenzaDTO find(ComuniCompetenzaDTO pk){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"pratica_id\" = ?")
                                     .append(" and \"ente_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getPraticaId());
        parameters.add(pk.getEnteId());
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    /**
     * search method
     */
//    @Override
//    public PaginatedList<ComuniCompetenzaDTO> search(ComuniCompetenzaSearch search){
//        final List<Object>  parameters = new ArrayList<Object>();
//        String              sep        = " where ";
//        final StringBuilder sql        = new StringBuilder(selectAll);
//        if(StringUtil.isNotEmpty(search.getPraticaId())){
//            sql.append(sep).append("lower(\"pratica_id\"::text) like ?");
//            parameters.add(StringUtil.convertLike(search.getPraticaId()));
//            sep = " and ";
//        }
//        if(StringUtil.isNotEmpty(search.getEnteId())){
//            sql.append(sep).append("lower(\"ente_id\"::text) like ?");
//            parameters.add(StringUtil.convertLike(search.getEnteId()));
//            sep = " and ";
//        }
//        if(StringUtil.isNotEmpty(search.getDataInserimento())){
//            sql.append(sep).append("lower(\"data_inserimento\"::text) like ?");
//            parameters.add(StringUtil.convertLike(search.getDataInserimento()));
//            sep = " and ";
//        }
//        if(StringUtil.isNotEmpty(search.getSortBy())){
//            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
//            switch (search.getSortBy()) {
//            case "praticaId":
//                    sql.append(" sort by \"pratica_id\" ").append(sortType);
//            case "enteId":
//                    sql.append(" sort by \"ente_id\" ").append(sortType);
//            case "dataInserimento":
//                    sql.append(" sort by \"data_inserimento\" ").append(sortType);
//                break;
//            default:
//            	    logger.warn(StringUtil.concateneString("value ", search.getSortBy(), " not valid for sort by"));
//            	    break;
//            }
//        }
//        return super.paginatedList(sql.toString(),parameters, this.rowMapper, search.getPage(), search.getLimit());
//    }
    
    @Override
    public PaginatedList<ComuniCompetenzaDTO> search(ComuniCompetenzaSearch search)
    {
        final Map<String, Object>  parameters = new HashMap<String, Object>();
        String sql = generateWhereClause(selectAll, parameters, search); 
        sql = generateOrderBy(sql, search.getSortBy(), search.getSortType());
        logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
        return paginatedList(sql, parameters, rowMapper, search.getPage(), search.getLimit());
    }

    /**
     * insert all method
     */
    @Override
    public long insert(ComuniCompetenzaDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"comuni_competenza\"")
                                     .append("(\"pratica_id\"")
                                     .append(",\"ente_id\"")
                                     .append(",\"data_inserimento\"")
                                     .append(",\"descrizione\"")
                                     .append(",\"cod_cat\"")
                                     .append(",\"cod_istat\"")
                                     .append(",\"vincolo_art_38\"")
                                     .append(",\"vincolo_art_100\"")
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
        parameters.add(entity.getPraticaId());
        parameters.add(entity.getEnteId());
        parameters.add(entity.getDataInserimento());
        parameters.add(entity.getDescrizione());
        parameters.add(entity.getCodCat());
        parameters.add(entity.getCodIstat());
        parameters.add(entity.getVincoloArt38());
        parameters.add(entity.getVincoloArt100());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(ComuniCompetenzaDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"comuni_competenza\"")
                                     .append(" set \"data_inserimento\" = ?")
                                     .append(" , \"descrizione\" = ?")
                                     .append(" , \"cod_cat\" = ?")
                                     .append(" , \"cod_istat\" = ?")
                                     .append(" where \"pratica_id\" = ?")
                                     .append(" and \"ente_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getDataInserimento());
        parameters.add(entity.getDescrizione());
        parameters.add(entity.getCodCat());
        parameters.add(entity.getCodIstat());
        parameters.add(entity.getPraticaId());
        parameters.add(entity.getEnteId());
        return super.update(sql, parameters);
    }

    
    public int aggiornaVincoli(ComuniCompetenzaDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"comuni_competenza\"")
                                     .append(" set \"vincolo_art_38\" = ?")
                                     .append(" , \"vincolo_art_100\" = ?")
                                     .append(" , \"notifica\" = ?")
                                     .append(" where \"pratica_id\" = ?")
                                     .append(" and \"ente_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getVincoloArt38());
        parameters.add(entity.getVincoloArt100());
        parameters.add(entity.getNotifica());
        parameters.add(entity.getPraticaId());
        parameters.add(entity.getEnteId());
        return super.update(sql, parameters);
    }
    
    
    public int accettaCambiamentiVincoli(ComuniCompetenzaDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"comuni_competenza\"")
                                     .append(" set \"notifica\" = ?")
                                     .append(" , \"data_accettazione_cambio_vincoli\" = ?")
                                     .append(" where \"pratica_id\" = ?")
                                     .append(" and \"ente_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getNotifica());
        parameters.add(new Date());
        parameters.add(entity.getPraticaId());
        parameters.add(entity.getEnteId());
        return super.update(sql, parameters);
    }
    
    
    
    /**
     * delete by pk method
     */
    @Override
    public int delete(ComuniCompetenzaDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"comuni_competenza\"")
                                     .append(" where \"pratica_id\" = ?")
                                     .append(" and \"ente_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getPraticaId());
        parameters.add(entity.getEnteId());
        return super.update(sql, parameters);
    }
    
    /**
     * delete by pratica_id
     */
    public int deleteByPraticaId(UUID praticaId){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"comuni_competenza\"")
                                     .append(" where \"pratica_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(praticaId);
        return super.update(sql, parameters);
    }

	public List<ComuniCompetenzaDTO> selectByPratica(UUID id,boolean txWrite) {
		final String sql = new StringBuilder(selectAll)
				.append(" where \"pratica_id\"= ? ")
				.toString();
		final List<Object> parameters = new ArrayList<Object>();
        parameters.add(id);
        if(txWrite) {
        	return super.queryForList(sql, this.rowMapper,parameters);
        }else {
        	return super.queryForListTxRead(sql, this.rowMapper,parameters);	
        }
        
	}
	
	
	/**
	 * 
	 * @author acolaianni
	 *
	 * @param praticaId
	 * @param enteId
	 * @return
	 */
	public ComuniCompetenzaDTO findByPraticaAndEnteId(UUID praticaId, Integer enteId){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"pratica_id\" = ?")
                                     .append(" and \"ente_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(praticaId);
        parameters.add(enteId);
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }

	
	

}
