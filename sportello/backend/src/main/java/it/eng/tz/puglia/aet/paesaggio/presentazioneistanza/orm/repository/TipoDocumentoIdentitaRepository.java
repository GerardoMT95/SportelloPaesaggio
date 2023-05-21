package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoDocumentoIdentitaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.TipoDocumentoIdentitaRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.TipoDocumentoIdentitaSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.tipo_documento_identita
 */
@Repository
public class TipoDocumentoIdentitaRepository extends GenericCrudDao<TipoDocumentoIdentitaDTO, TipoDocumentoIdentitaSearch>{

    private final TipoDocumentoIdentitaRowMapper rowMapper = new TipoDocumentoIdentitaRowMapper();
    /**
     * select all method
     */
    @Override
    public List<TipoDocumentoIdentitaDTO> select(){
        final String sql = StringUtil.concateneString("select"
                                                     ," \"id\""
                                                     ,",\"nome\""
                                                     ,",\"order\""
                                                     ," from \"presentazione_istanza\".\"tipo_documento_identita\""
                                                     ," ORDER BY \"order\""
                                                     );
        return super.queryForListTxRead(sql, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = StringUtil.concateneString("select count(*)"
                                                     ," from \"presentazione_istanza\".\"tipo_documento_identita\""
                                                     );
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public TipoDocumentoIdentitaDTO find(TipoDocumentoIdentitaDTO pk){
        final String sql = StringUtil.concateneString("select"
                                                     ," \"id\""
                                                     ,",\"nome\""
                                                     ,",\"order\""
                                                     ," from \"presentazione_istanza\".\"tipo_documento_identita\""
                                                     ," where \"id\" = ?"
                                                     );
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getId());
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
 
    /**
     * search method
     */
    @Override
    public PaginatedList<TipoDocumentoIdentitaDTO> search(TipoDocumentoIdentitaSearch search){
    	search.setSortBy("order");
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(StringUtil.concateneString("select"
                                                                                     ," \"id\""
                                                                                     ,",\"nome\""
                                                                                     ,",\"order\""
                                                                                     ," from \"presentazione_istanza\".\"tipo_documento_identita\""
                                                                                     )
                                                            );
        if(StringUtil.isNotEmpty(search.getId())){
            sql.append(sep).append("lower(\"id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getNome())){
            sql.append(sep).append("lower(\"nome\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getNome()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getOrder())){
            sql.append(sep).append("lower(\"order\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getOrder()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "id":
                    sql.append(" sort by \"id\" ").append(sortType);
            case "nome":
                    sql.append(" sort by \"nome\" ").append(sortType);
            case "order":
                    sql.append(" sort by \"order\" ").append(sortType);
                break;
            default:
            	    logger.warn(StringUtil.concateneString("value ", search.getSortBy(), " not valid for sort by"));
            	    break;
            }
        }
        return super.paginatedList(sql.toString(), this.rowMapper, search.getPage(), search.getLimit());
    }

    /**
     * insert all method
     */
    @Override
    public long insert(TipoDocumentoIdentitaDTO entity){
        final String sql = StringUtil.concateneString("insert into \"presentazione_istanza\".\"tipo_documento_identita\""
                                                     ,"(\"nome\""
                                                     ,",\"order\""
                                                     ,") values "
                                                     ,"(?"
                                                     ,",?"
                                                     ,")"
                                                     );
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getNome());
        parameters.add(entity.getOrder());
        return super.insertAndGetAutoincrementValue(sql, parameters, "id");
    }

    /**
     * update by pk method
     */
    @Override
    public int update(TipoDocumentoIdentitaDTO entity){
        final String sql = StringUtil.concateneString("update \"presentazione_istanza\".\"tipo_documento_identita\""
                                                     ," set \"nome\" = ?"
                                                     ,", \"order\" = ?"
                                                     ," where \"id\" = ?"
                                                     );
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getNome());
        parameters.add(entity.getOrder());
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(TipoDocumentoIdentitaDTO entity){
        final String sql = StringUtil.concateneString("delete from \"presentazione_istanza\".\"tipo_documento_identita\""
                                                     ," where \"id\" = ?"
                                                     );
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

}
