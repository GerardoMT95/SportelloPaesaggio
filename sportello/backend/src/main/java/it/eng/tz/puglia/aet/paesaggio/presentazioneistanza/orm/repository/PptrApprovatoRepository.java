package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PptrApprovatoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.PptrApprovatoRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PptrApprovatoSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.pptr_approvato
 */
@Repository
public class PptrApprovatoRepository extends GenericCrudDao<PptrApprovatoDTO, PptrApprovatoSearch>{

    private final PptrApprovatoRowMapper rowMapper = new PptrApprovatoRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"pratica_id\"")
                                     .append(",\"ambito_paesaggistico\"")
                                     .append(",\"figure_ambito\"")
                                     .append(",\"ricade_terr_art_1_03_co_5_6\"")
                                     .append(",\"ricade_terr_art_142_co_2\"")
                                     .append(" from \"presentazione_istanza\".\"pptr_approvato\"")
                                     .toString();
    @Override
    public List<PptrApprovatoDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"pptr_approvato\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public PptrApprovatoDTO find(PptrApprovatoDTO pk){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"pratica_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getPraticaId());
        List<PptrApprovatoDTO> val = super.queryForList(sql, this.rowMapper, parameters);
        return val != null && val.size() > 0 ? val.get(0) : null;
    }
    /**
     * search method
     */
    @Override
    public PaginatedList<PptrApprovatoDTO> search(PptrApprovatoSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getPraticaId())){
            sql.append(sep).append("lower(\"pratica_id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPraticaId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getAmbitoPaesaggistico())){
            sql.append(sep).append("lower(\"ambito_paesaggistico\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getAmbitoPaesaggistico()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getFigureAmbito())){
            sql.append(sep).append("lower(\"figure_ambito\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getFigureAmbito()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getRicadeTerrArt103Co56())){
            sql.append(sep).append("lower(\"ricade_terr_art_1_03_co_5_6\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getRicadeTerrArt103Co56()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getRicadeTerrArt142Co2())){
            sql.append(sep).append("lower(\"ricade_terr_art_142_co_2\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getRicadeTerrArt142Co2()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "praticaId":
                    sql.append(" sort by \"pratica_id\" ").append(sortType);
            case "ambitoPaesaggistico":
                    sql.append(" sort by \"ambito_paesaggistico\" ").append(sortType);
            case "figureAmbito":
                    sql.append(" sort by \"figure_ambito\" ").append(sortType);
            case "ricadeTerrArt103Co56":
                    sql.append(" sort by \"ricade_terr_art_1_03_co_5_6\" ").append(sortType);
            case "ricadeTerrArt142Co2":
                    sql.append(" sort by \"ricade_terr_art_142_co_2\" ").append(sortType);
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
    public long insert(PptrApprovatoDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"pptr_approvato\"")
                                     .append("(\"pratica_id\"")
                                     .append(",\"ambito_paesaggistico\"")
                                     .append(",\"figure_ambito\"")
                                     .append(",\"ricade_terr_art_1_03_co_5_6\"")
                                     .append(",\"ricade_terr_art_142_co_2\"")
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
        parameters.add(entity.getAmbitoPaesaggistico());
        parameters.add(entity.getFigureAmbito());
        parameters.add(entity.getRicadeTerrArt103Co56());
        parameters.add(entity.getRicadeTerrArt142Co2());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(PptrApprovatoDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"pptr_approvato\"")
                                     .append(" set \"ambito_paesaggistico\" = ?")
                                     .append(", \"figure_ambito\" = ?")
                                     .append(", \"ricade_terr_art_1_03_co_5_6\" = ?")
                                     .append(", \"ricade_terr_art_142_co_2\" = ?")
                                     .append(" where \"pratica_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getAmbitoPaesaggistico());
        parameters.add(entity.getFigureAmbito());
        parameters.add(entity.getRicadeTerrArt103Co56());
        parameters.add(entity.getRicadeTerrArt142Co2());
        parameters.add(entity.getPraticaId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(PptrApprovatoDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"pptr_approvato\"")
                                     .append(" where \"pratica_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getPraticaId());
        return super.update(sql, parameters);
    }

}
