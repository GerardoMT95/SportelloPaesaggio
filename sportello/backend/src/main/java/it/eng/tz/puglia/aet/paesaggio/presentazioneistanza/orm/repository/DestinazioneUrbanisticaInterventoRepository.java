package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinazioneUrbanisticaInterventoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.DestinazioneUrbanisticaInterventoRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.DestinazioneUrbanisticaInterventoSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.destinazione_urbanistica_intervento
 */
@Repository
public class DestinazioneUrbanisticaInterventoRepository extends GenericCrudDao<DestinazioneUrbanisticaInterventoDTO, DestinazioneUrbanisticaInterventoSearch>{

    private final DestinazioneUrbanisticaInterventoRowMapper rowMapper = new DestinazioneUrbanisticaInterventoRowMapper();

        final String selectAll = new StringBuilder("select")
                                     .append(" \"pratica_id\"")
                                     .append(",\"comune_id\"")
                                     .append(",\"id\"")
                                     .append(",\"strum_urb_approvato\"")
                                     .append(",\"strum_urb_approvato_data\"")
                                     .append(",\"strum_urb_approvato_atto\"")
                                     .append(",\"destin_area_str_vig\"")
                                     .append(",\"strum_approv_ult_tutele\"")
                                     .append(",\"strum_urb_adottato\"")
                                     .append(",\"strum_urb_adottato_data\"")
                                     .append(",\"strum_urb_adottato_atto\"")
                                     .append(",\"destin_area_str_adott\"")
                                     .append(",\"strum_adott_ult_tutele\"")
                                     .append(",\"conforme_discipl_urb_vigente\"")
                                     .append(",\"check_presa_visione\"")
                                     .append(",\"id_strum_urban_art38\"")
                                     .append(",\"id_strum_urban_art100\"")
                                     .append(" from \"presentazione_istanza\".\"destinazione_urbanistica_intervento\"")
                                     .toString();
    
    /**
     * select all method
     */    
    @Override
    public List<DestinazioneUrbanisticaInterventoDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"destinazione_urbanistica_intervento\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * count by idPratica method
     */
    public short countByIdPratica (UUID idPratica){
    	final String sql = new StringBuilder("select count(*)")
							    			.append(" from \"presentazione_istanza\".\"destinazione_urbanistica_intervento\"")
		                                    .append(" where \"pratica_id\" = ?")
		                                    .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(idPratica);
        return super.queryForObjectTxRead(sql, Short.class, parameters);
    }
    
    /**
     * count by idPratica AND idComune method
     */
    public short countByIdPraticaAndIdComune(UUID idPratica, Long idComune){
    	final String sql = new StringBuilder("select count(*)")
							    			.append(" from \"presentazione_istanza\".\"destinazione_urbanistica_intervento\"")
							    			.append(" where \"pratica_id\" = ?")
							    			.append(" and \"comune_id\" = ?")
							    			.toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(idPratica);
		parameters.add(idComune);
		return super.queryForObjectTxRead(sql, Short.class, parameters);
    }
    
    /**
     * find by pk method
     */
    @Override
    public DestinazioneUrbanisticaInterventoDTO find(DestinazioneUrbanisticaInterventoDTO pk){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"pratica_id\" = ?")
                                     .append(" and \"comune_id\" = ?")
                                     .append(" and \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getPraticaId());
        parameters.add(pk.getComuneId());
        parameters.add(pk.getId());
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
 
    /**
     * search method
     */
    @Override
    public PaginatedList<DestinazioneUrbanisticaInterventoDTO> search(DestinazioneUrbanisticaInterventoSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getPraticaId())){
            sql.append(sep).append("lower(\"pratica_id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPraticaId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getComuneId())){
            sql.append(sep).append("lower(\"comune_id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getComuneId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getId())){
            sql.append(sep).append("lower(\"id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getStrumUrbApprovato())){
            sql.append(sep).append("lower(\"strum_urb_approvato\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getStrumUrbApprovato()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getStrumUrbApprovatoData())){
            sql.append(sep).append("lower(\"strum_urb_approvato_data\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getStrumUrbApprovatoData()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getStrumUrbApprovatoAtto())){
            sql.append(sep).append("lower(\"strum_urb_approvato_atto\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getStrumUrbApprovatoAtto()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDestinAreaStrVig())){
            sql.append(sep).append("lower(\"destin_area_str_vig\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDestinAreaStrVig()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getStrumApprovUltTutele())){
            sql.append(sep).append("lower(\"strum_approv_ult_tutele\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getStrumApprovUltTutele()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getStrumUrbAdottato())){
            sql.append(sep).append("lower(\"strum_urb_adottato\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getStrumUrbAdottato()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getStrumUrbAdottatoData())){
            sql.append(sep).append("lower(\"strum_urb_adottato_data\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getStrumUrbAdottatoData()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getStrumUrbAdottatoAtto())){
            sql.append(sep).append("lower(\"strum_urb_adottato_atto\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getStrumUrbAdottatoAtto()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDestinAreaStrAdott())){
            sql.append(sep).append("lower(\"destin_area_str_adott\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDestinAreaStrAdott()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getStrumAdottUltTutele())){
            sql.append(sep).append("lower(\"strum_adott_ult_tutele\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getStrumAdottUltTutele()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getConformeDisciplUrbVigente())){
            sql.append(sep).append("lower(\"conforme_discipl_urb_vigente\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getConformeDisciplUrbVigente()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCheckPresaVisione())){
            sql.append(sep).append("lower(\"check_presa_visione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getCheckPresaVisione()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdStrumUrbanArt38())){
            sql.append(sep).append("lower(\"id_strum_urban_art38\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdStrumUrbanArt38()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdStrumUrbanArt100())){
            sql.append(sep).append("lower(\"id_strum_urban_art100\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdStrumUrbanArt100()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "praticaId":
                    sql.append(" sort by \"pratica_id\" ").append(sortType);
            case "comuneId":
                    sql.append(" sort by \"comune_id\" ").append(sortType);
            case "id":
                    sql.append(" sort by \"id\" ").append(sortType);
            case "strumUrbApprovato":
                    sql.append(" sort by \"strum_urb_approvato\" ").append(sortType);
            case "strumUrbApprovatoData":
                    sql.append(" sort by \"strum_urb_approvato_data\" ").append(sortType);
            case "strumUrbApprovatoAtto":
                    sql.append(" sort by \"strum_urb_approvato_atto\" ").append(sortType);
            case "destinAreaStrVig":
                    sql.append(" sort by \"destin_area_str_vig\" ").append(sortType);
            case "strumApprovUltTutele":
                    sql.append(" sort by \"strum_approv_ult_tutele\" ").append(sortType);
            case "strumUrbAdottato":
                    sql.append(" sort by \"strum_urb_adottato\" ").append(sortType);
            case "strumUrbAdottatoData":
                    sql.append(" sort by \"strum_urb_adottato_data\" ").append(sortType);
            case "strumUrbAdottatoAtto":
                    sql.append(" sort by \"strum_urb_adottato_atto\" ").append(sortType);
            case "destinAreaStrAdott":
                    sql.append(" sort by \"destin_area_str_adott\" ").append(sortType);
            case "strumAdottUltTutele":
                    sql.append(" sort by \"strum_adott_ult_tutele\" ").append(sortType);
            case "conformeDisciplUrbVigente":
                    sql.append(" sort by \"conforme_discipl_urb_vigente\" ").append(sortType);
            case "checkPresaVisione":
                    sql.append(" sort by \"check_presa_visione\" ").append(sortType);
            case "idStrumUrbanArt38":
                    sql.append(" sort by \"id_strum_urban_art38\" ").append(sortType);
            case "idStrumUrbanArt100":
                    sql.append(" sort by \"id_strum_urban_art100\" ").append(sortType);
                break;
            default:
            	    logger.warn(StringUtil.concateneString("value ", search.getSortBy(), " not valid for sort by"));
            	    break;
            }
        }
//        if (search.getLimit()==0) {
//        	search.setLimit(1000) ; search.setPage(1);
//        }
        return super.paginatedList(sql.toString(), parameters, this.rowMapper, search.getPage(), search.getLimit());
    }

    /**
     * insert all method
     */
    @Override
    public long insert(DestinazioneUrbanisticaInterventoDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"destinazione_urbanistica_intervento\"")
                                     .append("(\"pratica_id\"")
                                     .append(",\"comune_id\"")
                                     .append(",\"id\"")
                                     .append(",\"strum_urb_approvato\"")
                                     .append(",\"strum_urb_approvato_data\"")
                                     .append(",\"strum_urb_approvato_atto\"")
                                     .append(",\"destin_area_str_vig\"")
                                     .append(",\"strum_approv_ult_tutele\"")
                                     .append(",\"strum_urb_adottato\"")
                                     .append(",\"strum_urb_adottato_data\"")
                                     .append(",\"strum_urb_adottato_atto\"")
                                     .append(",\"destin_area_str_adott\"")
                                     .append(",\"strum_adott_ult_tutele\"")
                                     .append(",\"conforme_discipl_urb_vigente\"")
                                     .append(",\"check_presa_visione\"")
                                     .append(",\"id_strum_urban_art38\"")
                                     .append(",\"id_strum_urban_art100\"")
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
        parameters.add(entity.getPraticaId());
        parameters.add(entity.getComuneId());
        parameters.add(entity.getId());
        parameters.add(entity.getStrumUrbApprovato());
        parameters.add(entity.getStrumUrbApprovatoData());
        parameters.add(entity.getStrumUrbApprovatoAtto());
        parameters.add(entity.getDestinAreaStrVig());
        parameters.add(entity.getStrumApprovUltTutele());
        parameters.add(entity.getStrumUrbAdottato());
        parameters.add(entity.getStrumUrbAdottatoData());
        parameters.add(entity.getStrumUrbAdottatoAtto());
        parameters.add(entity.getDestinAreaStrAdott());
        parameters.add(entity.getStrumAdottUltTutele());
        parameters.add(entity.getConformeDisciplUrbVigente());
        parameters.add(entity.getCheckPresaVisione());
        parameters.add(entity.getIdStrumUrbanArt38());
        parameters.add(entity.getIdStrumUrbanArt100());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(DestinazioneUrbanisticaInterventoDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"destinazione_urbanistica_intervento\"")
                                     .append(" set \"strum_urb_approvato\" = ?")
                                     .append(", \"strum_urb_approvato_data\" = ?")
                                     .append(", \"strum_urb_approvato_atto\" = ?")
                                     .append(", \"destin_area_str_vig\" = ?")
                                     .append(", \"strum_approv_ult_tutele\" = ?")
                                     .append(", \"strum_urb_adottato\" = ?")
                                     .append(", \"strum_urb_adottato_data\" = ?")
                                     .append(", \"strum_urb_adottato_atto\" = ?")
                                     .append(", \"destin_area_str_adott\" = ?")
                                     .append(", \"strum_adott_ult_tutele\" = ?")
                                     .append(", \"conforme_discipl_urb_vigente\" = ?")
                                     .append(", \"check_presa_visione\" = ?")
                                     .append(", \"id_strum_urban_art38\" = ?")
                                     .append(", \"id_strum_urban_art100\" = ?")
                                     .append(" where \"pratica_id\" = ?")
                                     .append(" and \"comune_id\" = ?")
        //                           .append(" and \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getStrumUrbApprovato());
        parameters.add(entity.getStrumUrbApprovatoData());
        parameters.add(entity.getStrumUrbApprovatoAtto());
        parameters.add(entity.getDestinAreaStrVig());
        parameters.add(entity.getStrumApprovUltTutele());
        parameters.add(entity.getStrumUrbAdottato());
        parameters.add(entity.getStrumUrbAdottatoData());
        parameters.add(entity.getStrumUrbAdottatoAtto());
        parameters.add(entity.getDestinAreaStrAdott());
        parameters.add(entity.getStrumAdottUltTutele());
        parameters.add(entity.getConformeDisciplUrbVigente());
        parameters.add(entity.getCheckPresaVisione());
        parameters.add(entity.getIdStrumUrbanArt38());
        parameters.add(entity.getIdStrumUrbanArt100());
        parameters.add(entity.getPraticaId());
        parameters.add(entity.getComuneId());
//      parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(DestinazioneUrbanisticaInterventoDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"destinazione_urbanistica_intervento\"")
                                     .append(" where \"pratica_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getPraticaId());
        return super.update(sql, parameters);
    }
    
    /**
     * delete for Comune method
     */
    public int deleteForComune(DestinazioneUrbanisticaInterventoDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"destinazione_urbanistica_intervento\"")
                                     .append(" where \"pratica_id\" = ?")
                                     .append(" and \"comune_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getPraticaId());
        parameters.add(entity.getComuneId());
        return super.update(sql, parameters);
    }
    
    /**
     * resetta presa visione
     */
    public int resettaPresaVisione(UUID idPratica, Integer idComune){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"destinazione_urbanistica_intervento\"")
        							 .append(" set \"check_presa_visione\" = null")
                                     .append(" where \"pratica_id\" = ?")
                                     .append(" and \"comune_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(idPratica);
        parameters.add(idComune);
        return super.update(sql, parameters);
    }

}