package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ProcedimentoContenutoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.ProcedimentoContenutoRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.ProcedimentoContenutoSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.procedimento_contenuto
 */
@Repository
public class ProcedimentoContenutoRepository extends GenericCrudDao<ProcedimentoContenutoDTO, ProcedimentoContenutoSearch>{

    private final ProcedimentoContenutoRowMapper rowMapper = new ProcedimentoContenutoRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"tipo_procedimento\"")
                                     .append(",\"tipo_contenuto_id\"")
                                     .append(" from \"presentazione_istanza\".\"procedimento_contenuto\"")
                                     .toString();
    @Override
    public List<ProcedimentoContenutoDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"procedimento_contenuto\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public ProcedimentoContenutoDTO find(ProcedimentoContenutoDTO pk){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"tipo_procedimento\" = ?")
                                     .append(" and \"tipo_contenuto_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getTipoProcedimento());
        parameters.add(pk.getTipoContenutoId());
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    /**
     * search method
     */
    @Override
    public PaginatedList<ProcedimentoContenutoDTO> search(ProcedimentoContenutoSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getTipoProcedimento())){
            sql.append(sep).append("lower(\"tipo_procedimento\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getTipoProcedimento()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getTipoContenutoId())){
            sql.append(sep).append("lower(\"tipo_contenuto_id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getTipoContenutoId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "tipoProcedimento":
                    sql.append(" sort by \"tipo_procedimento\" ").append(sortType);
            case "tipoContenutoId":
                    sql.append(" sort by \"tipo_contenuto_id\" ").append(sortType);
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
    public long insert(ProcedimentoContenutoDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"procedimento_contenuto\"")
                                     .append("(\"tipo_procedimento\"")
                                     .append(",\"tipo_contenuto_id\"")
                                     .append(") values ")
                                     .append("(?")
                                     .append(",?")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getTipoProcedimento());
        parameters.add(entity.getTipoContenutoId());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(ProcedimentoContenutoDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"procedimento_contenuto\"")
                                     .append(" where \"tipo_procedimento\" = ?")
                                     .append(" and \"tipo_contenuto_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getTipoProcedimento());
        parameters.add(entity.getTipoContenutoId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(ProcedimentoContenutoDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"procedimento_contenuto\"")
                                     .append(" where \"tipo_procedimento\" = ?")
                                     .append(" and \"tipo_contenuto_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getTipoProcedimento());
        parameters.add(entity.getTipoContenutoId());
        return super.update(sql, parameters);
    }

}
