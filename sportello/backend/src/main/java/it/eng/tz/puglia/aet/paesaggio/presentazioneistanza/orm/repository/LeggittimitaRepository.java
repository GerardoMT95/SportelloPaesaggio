package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.LeggittimitaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.LeggittimitaRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.LeggittimitaSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.leggittimita
 */
@Repository
public class LeggittimitaRepository extends GenericCrudDao<LeggittimitaDTO, LeggittimitaSearch>{

    private final LeggittimitaRowMapper rowMapper = new LeggittimitaRowMapper();

        final String selectAll = new StringBuilder("select")
                                     .append(" \"pratica_id\"")
                                     .append(",\"leg_urb_tit_edilizio\"")
                                     .append(",\"leg_urb_privo_specifica\"")
                                     .append(",\"leg_paesag_immobile\"")
                                     .append(",\"leg_pae_tipo_vincolo\"")
                                     .append(",\"leg_pae_data_intervento\"")
                                     .append(",\"leg_pae_data_vincolo\"")
                                     .append(" from \"presentazione_istanza\".\"leggittimita\"")
                                     .toString();

    /**
     * select all method
     */
    @Override
    public List<LeggittimitaDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * select by idPratica method
     */
    public LeggittimitaDTO selectByIdPratica(UUID idPratica){
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
                                     .append(" from \"presentazione_istanza\".\"leggittimita\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }
    
    /**
     * count by idPratica method
     */
    public short countByIdPratica(UUID idPratica){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"leggittimita\"")
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
    public LeggittimitaDTO find(LeggittimitaDTO pk){
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
    public PaginatedList<LeggittimitaDTO> search(LeggittimitaSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getPraticaId())){
            sql.append(sep).append("lower(\"pratica_id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPraticaId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getLegUrbTitEdilizio())){
            sql.append(sep).append("lower(\"leg_urb_tit_edilizio\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getLegUrbTitEdilizio()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getLegUrbPrivoSpecifica())){
            sql.append(sep).append("lower(\"leg_urb_privo_specifica\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getLegUrbPrivoSpecifica()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getLegPaesagImmobile())){
            sql.append(sep).append("lower(\"leg_paesag_immobile\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getLegPaesagImmobile()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getLegPaeTipoVincolo())){
            sql.append(sep).append("lower(\"leg_pae_tipo_vincolo\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getLegPaeTipoVincolo()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getLegPaeDataIntervento())){
            sql.append(sep).append("lower(\"leg_pae_data_intervento\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getLegPaeDataIntervento()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getLegPaeDataVincolo())){
            sql.append(sep).append("lower(\"leg_pae_data_vincolo\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getLegPaeDataVincolo()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "praticaId":
                    sql.append(" sort by \"pratica_id\" ").append(sortType);
            case "legUrbTitEdilizio":
                    sql.append(" sort by \"leg_urb_tit_edilizio\" ").append(sortType);
            case "legUrbPrivoSpecifica":
                    sql.append(" sort by \"leg_urb_privo_specifica\" ").append(sortType);
            case "legPaesagImmobile":
                    sql.append(" sort by \"leg_paesag_immobile\" ").append(sortType);
            case "legPaeTipoVincolo":
                    sql.append(" sort by \"leg_pae_tipo_vincolo\" ").append(sortType);
            case "legPaeDataIntervento":
                    sql.append(" sort by \"leg_pae_data_intervento\" ").append(sortType);
            case "legPaeDataVincolo":
                    sql.append(" sort by \"leg_pae_data_vincolo\" ").append(sortType);
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
    public long insert(LeggittimitaDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"leggittimita\"")
                                     .append("(\"pratica_id\"")
                                     .append(",\"leg_urb_tit_edilizio\"")
                                     .append(",\"leg_urb_privo_specifica\"")
                                     .append(",\"leg_paesag_immobile\"")
                                     .append(",\"leg_pae_tipo_vincolo\"")
                                     .append(",\"leg_pae_data_intervento\"")
                                     .append(",\"leg_pae_data_vincolo\"")
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
        parameters.add(entity.getLegUrbTitEdilizio());
        parameters.add(entity.getLegUrbPrivoSpecifica());
        parameters.add(entity.getLegPaesagImmobile());
        parameters.add(entity.getLegPaeTipoVincolo());
        parameters.add(entity.getLegPaeDataIntervento());
        parameters.add(entity.getLegPaeDataVincolo());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(LeggittimitaDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"leggittimita\"")
                                     .append(" set \"leg_urb_tit_edilizio\" = ?")
                                     .append(", \"leg_urb_privo_specifica\" = ?")
                                     .append(", \"leg_paesag_immobile\" = ?")
                                     .append(", \"leg_pae_tipo_vincolo\" = ?")
                                     .append(", \"leg_pae_data_intervento\" = ?")
                                     .append(", \"leg_pae_data_vincolo\" = ?")
                                     .append(" where \"pratica_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getLegUrbTitEdilizio());
        parameters.add(entity.getLegUrbPrivoSpecifica());
        parameters.add(entity.getLegPaesagImmobile());
        parameters.add(entity.getLegPaeTipoVincolo());
        parameters.add(entity.getLegPaeDataIntervento());
        parameters.add(entity.getLegPaeDataVincolo());
        parameters.add(entity.getPraticaId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(LeggittimitaDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"leggittimita\"")
                                     .append(" where \"pratica_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getPraticaId());
        return super.update(sql, parameters);
    }

}