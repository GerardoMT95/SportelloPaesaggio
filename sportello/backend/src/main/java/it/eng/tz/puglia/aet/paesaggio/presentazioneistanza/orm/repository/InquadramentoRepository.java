package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.InquadramentoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.InquadramentoRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.InquadramentoSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.inquadramento
 */
@Repository
public class InquadramentoRepository extends GenericCrudDao<InquadramentoDTO, InquadramentoSearch>{

    private final InquadramentoRowMapper rowMapper = new InquadramentoRowMapper();

        final String selectAll = new StringBuilder("select")
                                     .append(" \"pratica_id\"")
                                     .append(",\"inq_dest_uso_interv\"")
                                     .append(",\"inq_dest_uso_interv_altro\"")
                                     .append(",\"inq_dest_uso_suolo\"")
                                     .append(",\"inq_dest_uso_suolo_altro\"")
                                     .append(",\"inq_contesto_paesag\"")
                                     .append(",\"inq_contesto_paesag_altro\"")
                                     .append(",\"inq_morfologia_paesag\"")
                                     .append(",\"inq_morfologia_paesag_altro\"")
                                     .append(" from \"presentazione_istanza\".\"inquadramento\"")
                                     .toString();

    /**
     * select all method
     */
    @Override
    public List<InquadramentoDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * select by idPratica method
     */
    public InquadramentoDTO selectByIdPratica(UUID idPratica){
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
                                     .append(" from \"presentazione_istanza\".\"inquadramento\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public InquadramentoDTO find(InquadramentoDTO pk){
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
    public PaginatedList<InquadramentoDTO> search(InquadramentoSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getPraticaId())){
            sql.append(sep).append("lower(\"pratica_id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPraticaId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getInqDestUsoInterv())){
            sql.append(sep).append("lower(\"inq_dest_uso_interv\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getInqDestUsoInterv()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getInqDestUsoIntervAltro())){
            sql.append(sep).append("lower(\"inq_dest_uso_interv_altro\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getInqDestUsoIntervAltro()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getInqDestUsoSuolo())){
            sql.append(sep).append("lower(\"inq_dest_uso_suolo\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getInqDestUsoSuolo()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getInqDestUsoSuoloAltro())){
            sql.append(sep).append("lower(\"inq_dest_uso_suolo_altro\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getInqDestUsoSuoloAltro()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getInqContestoPaesag())){
            sql.append(sep).append("lower(\"inq_contesto_paesag\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getInqContestoPaesag()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getInqContestoPaesagAltro())){
            sql.append(sep).append("lower(\"inq_contesto_paesag_altro\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getInqContestoPaesagAltro()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getInqMorfologiaPaesag())){
            sql.append(sep).append("lower(\"inq_morfologia_paesag\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getInqMorfologiaPaesag()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getInqMorfologiaPaesagAltro())){
            sql.append(sep).append("lower(\"inq_morfologia_paesag_altro\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getInqMorfologiaPaesagAltro()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "praticaId":
                    sql.append(" sort by \"pratica_id\" ").append(sortType);
            case "inqDestUsoInterv":
                    sql.append(" sort by \"inq_dest_uso_interv\" ").append(sortType);
            case "inqDestUsoIntervAltro":
                    sql.append(" sort by \"inq_dest_uso_interv_altro\" ").append(sortType);
            case "inqDestUsoSuolo":
                    sql.append(" sort by \"inq_dest_uso_suolo\" ").append(sortType);
            case "inqDestUsoSuoloAltro":
                    sql.append(" sort by \"inq_dest_uso_suolo_altro\" ").append(sortType);
            case "inqContestoPaesag":
                    sql.append(" sort by \"inq_contesto_paesag\" ").append(sortType);
            case "inqContestoPaesagAltro":
                    sql.append(" sort by \"inq_contesto_paesag_altro\" ").append(sortType);
            case "inqMorfologiaPaesag":
                    sql.append(" sort by \"inq_morfologia_paesag\" ").append(sortType);
            case "inqMorfologiaPaesagAltro":
                    sql.append(" sort by \"inq_morfologia_paesag_altro\" ").append(sortType);
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
    public long insert(InquadramentoDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"inquadramento\"")
                                     .append("(\"pratica_id\"")
                                     .append(",\"inq_dest_uso_interv\"")
                                     .append(",\"inq_dest_uso_interv_altro\"")
                                     .append(",\"inq_dest_uso_suolo\"")
                                     .append(",\"inq_dest_uso_suolo_altro\"")
                                     .append(",\"inq_contesto_paesag\"")
                                     .append(",\"inq_contesto_paesag_altro\"")
                                     .append(",\"inq_morfologia_paesag\"")
                                     .append(",\"inq_morfologia_paesag_altro\"")
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
        parameters.add(entity.getPraticaId());
        parameters.add(entity.getInqDestUsoInterv());
        parameters.add(entity.getInqDestUsoIntervAltro());
        parameters.add(entity.getInqDestUsoSuolo());
        parameters.add(entity.getInqDestUsoSuoloAltro());
        parameters.add(entity.getInqContestoPaesag());
        parameters.add(entity.getInqContestoPaesagAltro());
        parameters.add(entity.getInqMorfologiaPaesag());
        parameters.add(entity.getInqMorfologiaPaesagAltro());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(InquadramentoDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"inquadramento\"")
                                     .append(" set \"inq_dest_uso_interv\" = ?")
                                     .append(", \"inq_dest_uso_interv_altro\" = ?")
                                     .append(", \"inq_dest_uso_suolo\" = ?")
                                     .append(", \"inq_dest_uso_suolo_altro\" = ?")
                                     .append(", \"inq_contesto_paesag\" = ?")
                                     .append(", \"inq_contesto_paesag_altro\" = ?")
                                     .append(", \"inq_morfologia_paesag\" = ?")
                                     .append(", \"inq_morfologia_paesag_altro\" = ?")
                                     .append(" where \"pratica_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getInqDestUsoInterv());
        parameters.add(entity.getInqDestUsoIntervAltro());
        parameters.add(entity.getInqDestUsoSuolo());
        parameters.add(entity.getInqDestUsoSuoloAltro());
        parameters.add(entity.getInqContestoPaesag());
        parameters.add(entity.getInqContestoPaesagAltro());
        parameters.add(entity.getInqMorfologiaPaesag());
        parameters.add(entity.getInqMorfologiaPaesagAltro());
        parameters.add(entity.getPraticaId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(InquadramentoDTO entity){
        return this.deleteById(entity.getPraticaId());
    }

    /**
     * count by idPratica method
     */
    public short countByIdPratica(UUID idPratica){
    	final String sql = new StringBuilder("select count(*)")
							    			.append(" from \"presentazione_istanza\".\"inquadramento\"")
		                                    .append(" where \"pratica_id\" = ?")
		                                    .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(idPratica);
        return super.queryForObjectTxRead(sql, Short.class, parameters);
    }

    /**
     * 
     * @author acolaianni
     *
     * @param praticaId
     * @return
     */
	public int deleteById(UUID praticaId) {
		final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"inquadramento\"")
				.append(" where \"pratica_id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(praticaId);
		return super.update(sql, parameters);

	}

}