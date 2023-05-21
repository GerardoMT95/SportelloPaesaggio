package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoProcedimentoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.TipoProcedimentoRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.TipoProcedimentoSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.tipo_procedimento
 */
@Repository
public class TipoProcedimentoRepository extends GenericCrudDao<TipoProcedimentoDTO, TipoProcedimentoSearch>{

    private final TipoProcedimentoRowMapper rowMapper = new TipoProcedimentoRowMapper();
    
    final String selectAll = StringUtil.concateneString("select"
            ," \"id\""
            ,",\"codice\""
            ,",\"descrizione\""
            ,",\"invia_email\""
            ,",\"sanatoria\""
            ,",\"abilitato_presentazione\""
            ,",\"accertamento\""
            ,",\"descr_stampa\""
            ,",\"descr_stampa_titolo\""
            ,",\"descr_stampa_sottotitolo\""
            ," from \"presentazione_istanza\".\"tipo_procedimento\" ");
    
    /**
     * select all method
     */
    @Override
    public List<TipoProcedimentoDTO> select(){
        final String sql = StringUtil.concateneString(selectAll," order by id ");
        return super.queryForListTxRead(sql, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = StringUtil.concateneString("select count(*)"
                                                     ," from \"presentazione_istanza\".\"tipo_procedimento\""
                                                     );
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public TipoProcedimentoDTO find(TipoProcedimentoDTO pk){
    	return this.findById(pk.getId());
    }
    
    public TipoProcedimentoDTO findById(int id){
        final String sql = StringUtil.concateneString(selectAll
                                                     ," where \"id\" = ?"
                                                     );
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(id);
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    
    /**
     * search method
     */
    @Override
    public PaginatedList<TipoProcedimentoDTO> search(TipoProcedimentoSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(StringUtil.concateneString(selectAll));
        if(StringUtil.isNotEmpty(search.getId())){
            sql.append(sep).append("lower(\"id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCodice())){
            sql.append(sep).append("lower(\"codice\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getCodice()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDescrizione())){
            sql.append(sep).append("lower(\"descrizione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDescrizione()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getInviaEmail())){
            sql.append(sep).append("lower(\"invia_email\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getInviaEmail()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSanatoria())){
            sql.append(sep).append("lower(\"sanatoria\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getSanatoria()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getAbilitatoPresentazione())){
            sql.append(sep).append("lower(\"abilitato_presentazione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getAbilitatoPresentazione()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "id":
                    sql.append(" sort by \"id\" ").append(sortType);
            case "codice":
                    sql.append(" sort by \"codice\" ").append(sortType);
            case "descrizione":
                    sql.append(" sort by \"descrizione\" ").append(sortType);
            case "inviaEmail":
                    sql.append(" sort by \"invia_email\" ").append(sortType);
            case "sanatoria":
                    sql.append(" sort by \"sanatoria\" ").append(sortType);
            case "abilitatoPresentazione":
                    sql.append(" sort by \"abilitato_presentazione\" ").append(sortType);
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
    public long insert(TipoProcedimentoDTO entity){
        throw new RuntimeException("Not implemented!!!");
    }

    /**
     * update by pk method
     */
    @Override
    public int update(TipoProcedimentoDTO entity){
        final String sql = StringUtil.concateneString("update \"presentazione_istanza\".\"tipo_procedimento\""
                                                     ," set \"codice\" = ?"
                                                     ,", \"descrizione\" = ?"
                                                     ,", \"invia_email\" = ?"
                                                     ,", \"sanatoria\" = ?"
                                                     ,", \"abilitato_presentazione\" = ?"
                                                     ,", \"accertamento\" = ?"
                                                     ," where \"id\" = ?"
                                                     );
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getCodice());
        parameters.add(entity.getDescrizione());
        parameters.add(entity.getInviaEmail());
        parameters.add(entity.getSanatoria());
        parameters.add(entity.getAbilitatoPresentazione());
        parameters.add(entity.getAccertamento());
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(TipoProcedimentoDTO entity){
        final String sql = StringUtil.concateneString("delete from \"presentazione_istanza\".\"tipo_procedimento\""
                                                     ," where \"id\" = ?"
                                                     );
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

    
    public String getDescrizione(int id){
    	final String sql = StringUtil.concateneString("select descrizione"
    			," from \"presentazione_istanza\".\"tipo_procedimento\""
    			," where \"id\" = ?"
    			);
    	final List<Object> parameters = new ArrayList<Object>();
    	parameters.add(id);
    	return super.queryForObjectTxRead(sql, String.class, parameters);
    }
    
}