package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.VincArchDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.VincArchRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.VincArchSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.vinc_arch
 */
@Repository
public class VincArchRepository extends GenericCrudDao<VincArchDTO, VincArchSearch>{

    private final VincArchRowMapper rowMapper = new VincArchRowMapper();

        final String selectAll = new StringBuilder("select")
                                     .append(" \"pratica_id\"")
                                     .append(",\"vinc_arc_no_tutela\"")
                                     .append(",\"vinc_arc_monum_diretto\"")
                                     .append(",\"vinc_arc_monum_indiretto\"")
                                     .append(",\"vinc_arc_archeo_diretto\"")
                                     .append(",\"vinc_arc_archeo_indiretto\"")
                                     .append(",\"altri_vincoli\"")
                                     .append(" from \"presentazione_istanza\".\"vinc_arch\"")
                                     .toString();
    
    /**
     * select all method
     */    
    @Override
    public List<VincArchDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * select by idPratica method
     */
    public VincArchDTO selectByIdPratica(UUID idPratica){
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
                                     .append(" from \"presentazione_istanza\".\"vinc_arch\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * count by idPratica method
     */
    public short countByIdPratica(UUID idPratica){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"vinc_arch\"")
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
    public VincArchDTO find(VincArchDTO pk){
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
    public PaginatedList<VincArchDTO> search(VincArchSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getPraticaId())){
            sql.append(sep).append("lower(\"pratica_id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPraticaId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getVincArcNoTutela())){
            sql.append(sep).append("lower(\"vinc_arc_no_tutela\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getVincArcNoTutela()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getVincArcMonumDiretto())){
            sql.append(sep).append("lower(\"vinc_arc_monum_diretto\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getVincArcMonumDiretto()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getVincArcMonumIndiretto())){
            sql.append(sep).append("lower(\"vinc_arc_monum_indiretto\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getVincArcMonumIndiretto()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getVincArcArcheoDiretto())){
            sql.append(sep).append("lower(\"vinc_arc_archeo_diretto\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getVincArcArcheoDiretto()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getVincArcArcheoIndiretto())){
            sql.append(sep).append("lower(\"vinc_arc_archeo_indiretto\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getVincArcArcheoIndiretto()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getAltriVincoli())){
            sql.append(sep).append("lower(\"altri_vincoli\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getAltriVincoli()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "praticaId":
                    sql.append(" sort by \"pratica_id\" ").append(sortType);
            case "vincArcNoTutela":
                    sql.append(" sort by \"vinc_arc_no_tutela\" ").append(sortType);
            case "vincArcMonumDiretto":
                    sql.append(" sort by \"vinc_arc_monum_diretto\" ").append(sortType);
            case "vincArcMonumIndiretto":
                    sql.append(" sort by \"vinc_arc_monum_indiretto\" ").append(sortType);
            case "vincArcArcheoDiretto":
                    sql.append(" sort by \"vinc_arc_archeo_diretto\" ").append(sortType);
            case "vincArcArcheoIndiretto":
                    sql.append(" sort by \"vinc_arc_archeo_indiretto\" ").append(sortType);
            case "altriVincoli":
                    sql.append(" sort by \"altri_vincoli\" ").append(sortType);
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
    public long insert(VincArchDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"vinc_arch\"")
                                     .append("(\"pratica_id\"")
                                     .append(",\"vinc_arc_no_tutela\"")
                                     .append(",\"vinc_arc_monum_diretto\"")
                                     .append(",\"vinc_arc_monum_indiretto\"")
                                     .append(",\"vinc_arc_archeo_diretto\"")
                                     .append(",\"vinc_arc_archeo_indiretto\"")
                                     .append(",\"altri_vincoli\"")
                                     .append(") values ")
                                     .append("(?")
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
        parameters.add(entity.getVincArcNoTutela());
        parameters.add(entity.getVincArcMonumDiretto());
        parameters.add(entity.getVincArcMonumIndiretto());
        parameters.add(entity.getVincArcArcheoDiretto());
        parameters.add(entity.getVincArcArcheoIndiretto());
        parameters.add(entity.getAltriVincoli());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(VincArchDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"vinc_arch\"")
                                     .append(" set \"vinc_arc_no_tutela\" = ?")
                                     .append(", \"vinc_arc_monum_diretto\" = ?")
                                     .append(", \"vinc_arc_monum_indiretto\" = ?")
                                     .append(", \"vinc_arc_archeo_diretto\" = ?")
                                     .append(", \"vinc_arc_archeo_indiretto\" = ?")
                                     .append(", \"altri_vincoli\" = ?")
                                     .append(" where \"pratica_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getVincArcNoTutela());
        parameters.add(entity.getVincArcMonumDiretto());
        parameters.add(entity.getVincArcMonumIndiretto());
        parameters.add(entity.getVincArcArcheoDiretto());
        parameters.add(entity.getVincArcArcheoIndiretto());
        parameters.add(entity.getAltriVincoli());
        parameters.add(entity.getPraticaId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(VincArchDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"vinc_arch\"")
                                     .append(" where \"pratica_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getPraticaId());
        return super.update(sql, parameters);
    }

}