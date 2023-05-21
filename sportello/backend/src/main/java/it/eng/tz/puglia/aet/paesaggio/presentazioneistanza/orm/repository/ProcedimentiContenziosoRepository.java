package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ProcedimentiContenziosoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.ProcedimentiContenziosoRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.ProcedimentiContenziosoSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.procedimenti_contenzioso
 */
@Repository
public class ProcedimentiContenziosoRepository extends GenericCrudDao<ProcedimentiContenziosoDTO, ProcedimentiContenziosoSearch>{

    private final ProcedimentiContenziosoRowMapper rowMapper = new ProcedimentiContenziosoRowMapper();
    
        final String selectAll = new StringBuilder("select")
                                     .append(" \"pratica_id\"")
                                     .append(",\"flag_contenzioso_in_atto\"")
                                     .append(",\"descrizione\"")
                                     .append(" from \"presentazione_istanza\".\"procedimenti_contenzioso\"")
                                     .toString();
    
    /**
     * select all method
     */
    @Override
    public List<ProcedimentiContenziosoDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * select by idPratica method
     */
    public ProcedimentiContenziosoDTO selectByIdPratica(UUID idPratica){
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
                                     .append(" from \"presentazione_istanza\".\"procedimenti_contenzioso\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * count by idPratica method
     */
    public short countByIdPratica(UUID idPratica){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"procedimenti_contenzioso\"")
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
    public ProcedimentiContenziosoDTO find(ProcedimentiContenziosoDTO pk){
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
    public PaginatedList<ProcedimentiContenziosoDTO> search(ProcedimentiContenziosoSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getPraticaId())){
            sql.append(sep).append("lower(\"pratica_id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPraticaId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getFlagContenziosoInAtto())){
            sql.append(sep).append("lower(\"flag_contenzioso_in_atto\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getFlagContenziosoInAtto()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDescrizione())){
            sql.append(sep).append("lower(\"descrizione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDescrizione()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "praticaId":
                    sql.append(" sort by \"pratica_id\" ").append(sortType);
            case "flagContenziosoInAtto":
                    sql.append(" sort by \"flag_contenzioso_in_atto\" ").append(sortType);
            case "descrizione":
                    sql.append(" sort by \"descrizione\" ").append(sortType);
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
    public long insert(ProcedimentiContenziosoDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"procedimenti_contenzioso\"")
                                     .append("(\"pratica_id\"")
                                     .append(",\"flag_contenzioso_in_atto\"")
                                     .append(",\"descrizione\"")
                                     .append(") values ")
                                     .append("(?")
                                     .append(",?")
                                     .append(",?")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getPraticaId());
        parameters.add(entity.getFlagContenziosoInAtto());
        parameters.add(entity.getDescrizione());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(ProcedimentiContenziosoDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"procedimenti_contenzioso\"")
                                     .append(" set \"flag_contenzioso_in_atto\" = ?")
                                     .append(", \"descrizione\" = ?")
                                     .append(" where \"pratica_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getFlagContenziosoInAtto());
        parameters.add(entity.getDescrizione());
        parameters.add(entity.getPraticaId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(ProcedimentiContenziosoDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"procedimenti_contenzioso\"")
                                     .append(" where \"pratica_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getPraticaId());
        return super.update(sql, parameters);
    }

}