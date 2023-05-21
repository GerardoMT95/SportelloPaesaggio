package it.eng.tz.puglia.autpae.generated.orm.repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.ArrayList;
import java.util.HashMap;

import it.eng.tz.puglia.bean.PaginatedList;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.puglia.autpae.generated.orm.dto.*;
import it.eng.tz.puglia.autpae.generated.orm.search.*;
import it.eng.tz.puglia.autpae.generated.orm.rowmapper.*;

/**
 * Repository for table pareri.doc_caricati_sportello
 */
@Repository
public class DocCaricatiSportelloRepository extends GenericCrudDao<DocCaricatiSportelloDTO, DocCaricatiSportelloSearch>{

    private final DocCaricatiSportelloRowMapper rowMapper = new DocCaricatiSportelloRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id\"")
                                     .append(",\"id_doc_caricato\"")
                                     .append(",\"date_insert\"")
                                     .append(",\"user_insert\"")
                                     .append(",\"deleted\"")
                                     .append(",\"id_fascicolo\"")
                                     .append(",\"date_updated\"")
                                     .append(",\"user_updated\"")
                                     .append(" from  \"doc_caricati_sportello\"")
                                     .toString();
    @Override
    public List<DocCaricatiSportelloDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"doc_caricati_sportello\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public DocCaricatiSportelloDTO find(DocCaricatiSportelloDTO pk){
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
    public PaginatedList<DocCaricatiSportelloDTO> search(DocCaricatiSportelloSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getId())){
            sql.append(sep).append("lower(\"id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdDocCaricato())){
            sql.append(sep).append("lower(\"id_doc_caricato\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdDocCaricato()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDateInsert())){
            sql.append(sep).append("lower(\"date_insert\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDateInsert()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getUserInsert())){
            sql.append(sep).append("lower(\"user_insert\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getUserInsert()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDeleted())){
            sql.append(sep).append("lower(\"deleted\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDeleted()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdFascicolo())){
            sql.append(sep).append("lower(\"id_fascicolo\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdFascicolo()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDateUpdated())){
            sql.append(sep).append("lower(\"date_updated\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDateUpdated()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getUserUpdated())){
            sql.append(sep).append("lower(\"user_updated\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getUserUpdated()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "id":
                    sql.append(" sort by \"id\" ").append(sortType);
            case "idDocCaricato":
                    sql.append(" sort by \"id_doc_caricato\" ").append(sortType);
            case "dateInsert":
                    sql.append(" sort by \"date_insert\" ").append(sortType);
            case "userInsert":
                    sql.append(" sort by \"user_insert\" ").append(sortType);
            case "deleted":
                    sql.append(" sort by \"deleted\" ").append(sortType);
            case "idFascicolo":
                    sql.append(" sort by \"id_fascicolo\" ").append(sortType);
            case "dateUpdated":
                    sql.append(" sort by \"date_updated\" ").append(sortType);
            case "userUpdated":
                    sql.append(" sort by \"user_updated\" ").append(sortType);
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
    public long insert(DocCaricatiSportelloDTO entity){
        final String sql = new StringBuilder("insert into \"doc_caricati_sportello\"")
                                     .append("(\"id_doc_caricato\"")
                                     .append(",\"user_insert\"")
                                     .append(",\"id_fascicolo\"")
                                     .append(",\"deleted\"")
                                     .append(") values ")
                                     .append("(?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",false")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIdDocCaricato());
        parameters.add(entity.getUserInsert());
        parameters.add(entity.getIdFascicolo());
        return super.insertAndGetAutoincrementValue(sql, parameters, "id");
    }

    /**
     * update by pk method
     */
    @Override
    public int update(DocCaricatiSportelloDTO entity){
        final String sql = new StringBuilder("update \"doc_caricati_sportello\"")
                                     .append(" set \"id_doc_caricato\" = ?")
                                     .append(", \"date_insert\" = ?")
                                     .append(", \"user_insert\" = ?")
                                     .append(", \"deleted\" = ?")
                                     .append(", \"id_fascicolo\" = ?")
                                     .append(", \"date_updated\" = ?")
                                     .append(", \"user_updated\" = ?")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIdDocCaricato());
        parameters.add(entity.getDateInsert());
        parameters.add(entity.getUserInsert());
        parameters.add(entity.getDeleted());
        parameters.add(entity.getIdFascicolo());
        parameters.add(entity.getDateUpdated());
        parameters.add(entity.getUserUpdated());
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(DocCaricatiSportelloDTO entity){
        final String sql = new StringBuilder("delete from \"doc_caricati_sportello\"")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }
    
    
    /**
     * 
     * @autor Adriano Colaianni
     * @date 25 lug 2022
     * @param idDocCaricato
     * @return null se il documento è utilizzabile per import altrimenti indica il codice del fascicolo su cui è stato caricato
     */
    public String caricatoSuFascicolo(UUID idDocCaricato){
    	Map<String,Object> params=new HashMap<>();
    	StringBuilder query=new StringBuilder("SELECT f.codice FROM \"doc_caricati_sportello\" dcs ");
    	query
    	.append(", \"fascicolo\" f WHERE ")
    	.append(" dcs.id_doc_caricato= :idDocCaricato ")
    	.append(" AND dcs.id_fascicolo= f.id ")
    	.append(" AND dcs.deleted=false limit 1 ")
    	;
    	params.put("idDocCaricato",idDocCaricato);
    	String ret=null;
    	try {
    		ret = super.namedQueryForObjectTxRead(query.toString(), String.class,params);
    	}catch(EmptyResultDataAccessException e) {}
        return ret;
    }
    
    
    /**
     * marca come rimosso l'utilizzo dell'idDocCaricato, in modo da poterlo riutilizzare
     * Chiamato in caso di rimozione fascicolo
     * @autor Adriano Colaianni
     * @date 26 lug 2022
     * @param idDocCaricato nullable
     * @param username
     * @param idFascicolo
     * @return
     */
    public int setLibero(UUID idDocCaricato,String username,Long idFascicolo){
        final StringBuilder sql = new StringBuilder("update \"doc_caricati_sportello\"")
                .append(" set \"deleted\" = true ")
                .append(",\"user_updated\" = ? ")
                .append(",\"date_updated\" = current_timestamp ")
                .append(" where ")
                .append(" \"id_fascicolo\" = ?");
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(username);
        parameters.add(idFascicolo);
        if(idDocCaricato!=null) {
        	sql.append(" AND \"id\" = ? ");
        	parameters.add(idDocCaricato);
        }
        return super.update(sql.toString(), parameters);
    }

	/**
	 * @autor Adriano Colaianni
	 * @date 26 lug 2022
	 * @param idFascicolo
	 * @return
	 */
	public boolean hasImport(Long idFascicolo) {
		Map<String,Object> params=new HashMap<>();
    	StringBuilder query=new StringBuilder("SELECT id_doc_caricato::text FROM \"doc_caricati_sportello\" dcs  ");
    	query
    	.append(" WHERE ")
    	.append(" dcs.id_fascicolo= :idFascicolo ")
    	.append(" AND dcs.deleted= false ")
    	.append(" LIMIT 1 ")
    	;
    	params.put("idFascicolo",idFascicolo);
    	boolean ret=false;
    	try {
    		super.namedQueryForObjectTxRead(query.toString(), String.class,params);
    		ret=true;
    	}catch(EmptyResultDataAccessException e) {}
        return ret;
	}
    

}
