package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.EffettiMitigazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.EffettiMitigazioneRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.EffettiMitigazioneSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.effetti_mitigazione
 */
@Repository
public class EffettiMitigazioneRepository extends GenericCrudDao<EffettiMitigazioneDTO, EffettiMitigazioneSearch>{

    private final EffettiMitigazioneRowMapper rowMapper = new EffettiMitigazioneRowMapper();
      
    final String selectAll = new StringBuilder("select")
                                     .append(" \"pratica_id\"")
                                     .append(",\"descr_stato_attuale\"")
                                     .append(",\"effetti_realiz_opera\"")
                                     .append(",\"mitigazione_imp_interv\"")
                                     .append(",\"indicaz_contenuti_percettivi\"")
                                     .append(" from \"presentazione_istanza\".\"effetti_mitigazione\"")
                                     .toString();
  
    /**
     * select all method
     */    
    @Override
    public List<EffettiMitigazioneDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * select by idPratica method
     */
	public EffettiMitigazioneDTO selectByIdPratica(UUID idPratica) {
    	String sql = selectAll.concat(" where \"pratica_id\" = ?");
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(idPratica);
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
	}
	
    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"effetti_mitigazione\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }
    
    /**
     * count by idPratica method
     */
    public short countByIdPratica(UUID idPratica){
    	final String sql = new StringBuilder("select count(*)")
							    			.append(" from \"presentazione_istanza\".\"effetti_mitigazione\"")
		                                    .append(" where \"pratica_id\" = ?")
		                                    .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(idPratica);
        return super.queryForObjectTxRead(sql, Short.class, parameters);
    }

    /**
     * find by pk method
     */
    @Override
    public EffettiMitigazioneDTO find(EffettiMitigazioneDTO pk){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"pratica_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getPraticaId());
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }

    /**
     * search method
     */
    @Override
    public PaginatedList<EffettiMitigazioneDTO> search(EffettiMitigazioneSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getPraticaId())){
            sql.append(sep).append("lower(\"pratica_id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPraticaId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDescrStatoAttuale())){
            sql.append(sep).append("lower(\"descr_stato_attuale\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDescrStatoAttuale()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getEffettiRealizOpera())){
            sql.append(sep).append("lower(\"effetti_realiz_opera\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getEffettiRealizOpera()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getMitigazioneImpInterv())){
            sql.append(sep).append("lower(\"mitigazione_imp_interv\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getMitigazioneImpInterv()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIndicazContenutiPercettivi())){
            sql.append(sep).append("lower(\"indicaz_contenuti_percettivi\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIndicazContenutiPercettivi()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "praticaId":
                    sql.append(" sort by \"pratica_id\" ").append(sortType);
            case "descrStatoAttuale":
                    sql.append(" sort by \"descr_stato_attuale\" ").append(sortType);
            case "effettiRealizOpera":
                    sql.append(" sort by \"effetti_realiz_opera\" ").append(sortType);
            case "mitigazioneImpInterv":
                    sql.append(" sort by \"mitigazione_imp_interv\" ").append(sortType);
            case "indicazContenutiPercettivi":
                    sql.append(" sort by \"indicaz_contenuti_percettivi\" ").append(sortType);
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
    public long insert(EffettiMitigazioneDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"effetti_mitigazione\"")
                                     .append("(\"pratica_id\"")
                                     .append(",\"descr_stato_attuale\"")
                                     .append(",\"effetti_realiz_opera\"")
                                     .append(",\"mitigazione_imp_interv\"")
                                     .append(",\"indicaz_contenuti_percettivi\"")
                                     .append(") values ")
                                     .append("(?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getPraticaId());
        parameters.add(entity.getDescrStatoAttuale());
        parameters.add(entity.getEffettiRealizOpera());
        parameters.add(entity.getMitigazioneImpInterv());
        parameters.add(entity.getIndicazContenutiPercettivi());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(EffettiMitigazioneDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"effetti_mitigazione\"")
                                     .append(" set \"descr_stato_attuale\" = ?")
                                     .append(", \"effetti_realiz_opera\" = ?")
                                     .append(", \"mitigazione_imp_interv\" = ?")
                                     .append(", \"indicaz_contenuti_percettivi\" = ?")
                                     .append(" where \"pratica_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getDescrStatoAttuale());
        parameters.add(entity.getEffettiRealizOpera());
        parameters.add(entity.getMitigazioneImpInterv());
        parameters.add(entity.getIndicazContenutiPercettivi());
        parameters.add(entity.getPraticaId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(EffettiMitigazioneDTO entity){
        return this.deleteById(entity.getPraticaId());
    }

    /**
     * 
     * @author acolaianni
     *
     * @param praticaId
     * @return
     */
    public int deleteById(UUID praticaId) {
		final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"effetti_mitigazione\"")
                .append(" where \"pratica_id\" = ?")
                .toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(praticaId);
		return super.update(sql, parameters);
	}

}