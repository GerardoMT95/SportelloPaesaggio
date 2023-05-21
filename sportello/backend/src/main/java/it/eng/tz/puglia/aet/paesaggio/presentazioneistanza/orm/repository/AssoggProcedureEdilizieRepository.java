package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AssoggProcedureEdilizieDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.AssoggProcedureEdilizieRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.AssoggProcedureEdilizieSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.assogg_procedure_edilizie
 */
@Repository
public class AssoggProcedureEdilizieRepository extends GenericCrudDao<AssoggProcedureEdilizieDTO, AssoggProcedureEdilizieSearch>{

    private final AssoggProcedureEdilizieRowMapper rowMapper = new AssoggProcedureEdilizieRowMapper();

        final String selectAll = new StringBuilder("select")
                                     .append(" \"pratica_id\"")
                                     .append(",\"flag_assoggettato\"")
                                     .append(",\"no_assogg_specificare\"")
                                     .append(",\"assogg_flag_pratica_in_corso\"")
                                     .append(",\"assogg_ente_pratica_in_corso\"")
                                     .append(",\"assogg_datapr_pratica_in_corso\"")
                                     .append(",\"assogg_flag_parere_urb_espr\"")
                                     .append(",\"assogg_data_approv_pratica\"")
                                     .append(" from \"presentazione_istanza\".\"assogg_procedure_edilizie\"")
                                     .toString();
    
    /**
     * select all method
     */
    @Override
    public List<AssoggProcedureEdilizieDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * select by idPratica method
     */
    public AssoggProcedureEdilizieDTO selectByIdPratica(UUID idPratica){
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
                                     .append(" from \"presentazione_istanza\".\"assogg_procedure_edilizie\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * count by idPratica method
     */
    public short countByIdPratica(UUID idPratica){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"assogg_procedure_edilizie\"")
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
    public AssoggProcedureEdilizieDTO find(AssoggProcedureEdilizieDTO pk){
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
    public PaginatedList<AssoggProcedureEdilizieDTO> search(AssoggProcedureEdilizieSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getPraticaId())){
            sql.append(sep).append("lower(\"pratica_id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPraticaId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getFlagAssoggettato())){
            sql.append(sep).append("lower(\"flag_assoggettato\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getFlagAssoggettato()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getNoAssoggSpecificare())){
            sql.append(sep).append("lower(\"no_assogg_specificare\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getNoAssoggSpecificare()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getAssoggFlagPraticaInCorso())){
            sql.append(sep).append("lower(\"assogg_flag_pratica_in_corso\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getAssoggFlagPraticaInCorso()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getAssoggEntePraticaInCorso())){
            sql.append(sep).append("lower(\"assogg_ente_pratica_in_corso\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getAssoggEntePraticaInCorso()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getAssoggDataprPraticaInCorso())){
            sql.append(sep).append("lower(\"assogg_datapr_pratica_in_corso\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getAssoggDataprPraticaInCorso()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getAssoggFlagParereUrbEspr())){
            sql.append(sep).append("lower(\"assogg_flag_parere_urb_espr\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getAssoggFlagParereUrbEspr()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getAssoggDataApprovPratica())){
            sql.append(sep).append("lower(\"assogg_data_approv_pratica\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getAssoggDataApprovPratica()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "praticaId":
                    sql.append(" sort by \"pratica_id\" ").append(sortType);
            case "flagAssoggettato":
                    sql.append(" sort by \"flag_assoggettato\" ").append(sortType);
            case "noAssoggSpecificare":
                    sql.append(" sort by \"no_assogg_specificare\" ").append(sortType);
            case "assoggFlagPraticaInCorso":
                    sql.append(" sort by \"assogg_flag_pratica_in_corso\" ").append(sortType);
            case "assoggEntePraticaInCorso":
                    sql.append(" sort by \"assogg_ente_pratica_in_corso\" ").append(sortType);
            case "assoggDataprPraticaInCorso":
                    sql.append(" sort by \"assogg_datapr_pratica_in_corso\" ").append(sortType);
            case "assoggFlagParereUrbEspr":
                    sql.append(" sort by \"assogg_flag_parere_urb_espr\" ").append(sortType);
            case "assoggDataApprovPratica":
                    sql.append(" sort by \"assogg_data_approv_pratica\" ").append(sortType);
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
    public long insert(AssoggProcedureEdilizieDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"assogg_procedure_edilizie\"")
                                     .append("(\"pratica_id\"")
                                     .append(",\"flag_assoggettato\"")
                                     .append(",\"no_assogg_specificare\"")
                                     .append(",\"assogg_flag_pratica_in_corso\"")
                                     .append(",\"assogg_ente_pratica_in_corso\"")
                                     .append(",\"assogg_datapr_pratica_in_corso\"")
                                     .append(",\"assogg_flag_parere_urb_espr\"")
                                     .append(",\"assogg_data_approv_pratica\"")
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
        parameters.add(entity.getFlagAssoggettato()!=null ? entity.getFlagAssoggettato() : "N");
        parameters.add(entity.getNoAssoggSpecificare());
        parameters.add(entity.getAssoggFlagPraticaInCorso());
        parameters.add(entity.getAssoggEntePraticaInCorso());
        parameters.add(entity.getAssoggDataprPraticaInCorso());
        parameters.add(entity.getAssoggFlagParereUrbEspr());
        parameters.add(entity.getAssoggDataApprovPratica());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(AssoggProcedureEdilizieDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"assogg_procedure_edilizie\"")
                                     .append(" set \"flag_assoggettato\" = ?")
                                     .append(", \"no_assogg_specificare\" = ?")
                                     .append(", \"assogg_flag_pratica_in_corso\" = ?")
                                     .append(", \"assogg_ente_pratica_in_corso\" = ?")
                                     .append(", \"assogg_datapr_pratica_in_corso\" = ?")
                                     .append(", \"assogg_flag_parere_urb_espr\" = ?")
                                     .append(", \"assogg_data_approv_pratica\" = ?")
                                     .append(" where \"pratica_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getFlagAssoggettato()!=null ? entity.getFlagAssoggettato() : "N");
        parameters.add(entity.getNoAssoggSpecificare());
        parameters.add(entity.getAssoggFlagPraticaInCorso());
        parameters.add(entity.getAssoggEntePraticaInCorso());
        parameters.add(entity.getAssoggDataprPraticaInCorso());
        parameters.add(entity.getAssoggFlagParereUrbEspr());
        parameters.add(entity.getAssoggDataApprovPratica());
        parameters.add(entity.getPraticaId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(AssoggProcedureEdilizieDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"assogg_procedure_edilizie\"")
                                     .append(" where \"pratica_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getPraticaId());
        return super.update(sql, parameters);
    }

}