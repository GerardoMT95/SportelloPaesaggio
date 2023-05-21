package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PareriEAttiAssensoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.PareriEAttiAssensoRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PareriEAttiAssensoSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.pareri_e_atti_assenso
 */
@Repository
public class PareriEAttiAssensoRepository extends GenericCrudDao<PareriEAttiAssensoDTO, PareriEAttiAssensoSearch>{

    private final PareriEAttiAssensoRowMapper rowMapper = new PareriEAttiAssensoRowMapper();

        final String selectAll = new StringBuilder("select")
                                     .append(" \"pratica_id\"")
                                     .append(",\"id\"")
                                     .append(",\"tipologia_atto\"")
                                     .append(",\"autorita_rilascio\"")
                                     .append(",\"protocollo\"")
                                     .append(",\"data_rilascio\"")
                                     .append(",\"flag_atto_parere\"")
                                     .append(",\"intestatario_atto\"")
                                     .append(" from \"presentazione_istanza\".\"pareri_e_atti_assenso\"")
                                     .toString();
    
    /**
     * select all method
     */
    @Override
    public List<PareriEAttiAssensoDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"pareri_e_atti_assenso\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public PareriEAttiAssensoDTO find(PareriEAttiAssensoDTO pk){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getId());
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
 
    /**
     * search method
     */
    @Override
    public PaginatedList<PareriEAttiAssensoDTO> search(PareriEAttiAssensoSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getPraticaId())){
            sql.append(sep).append("lower(\"pratica_id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPraticaId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getId())){
            sql.append(sep).append("lower(\"id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getTipologiaAtto())){
            sql.append(sep).append("lower(\"tipologia_atto\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getTipologiaAtto()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getAutoritaRilascio())){
            sql.append(sep).append("lower(\"autorita_rilascio\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getAutoritaRilascio()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getProtocollo())){
            sql.append(sep).append("lower(\"protocollo\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getProtocollo()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataRilascio())){
            sql.append(sep).append("lower(\"data_rilascio\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDataRilascio()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getFlagAttoParere())){
            sql.append(sep).append("lower(\"flag_atto_parere\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getFlagAttoParere()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIntestatarioAtto())){
            sql.append(sep).append("lower(\"intestatario_atto\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIntestatarioAtto()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "praticaId":
                    sql.append(" sort by \"pratica_id\" ").append(sortType);
            case "id":
                    sql.append(" sort by \"id\" ").append(sortType);
            case "tipologiaAtto":
                    sql.append(" sort by \"tipologia_atto\" ").append(sortType);
            case "autoritaRilascio":
                    sql.append(" sort by \"autorita_rilascio\" ").append(sortType);
            case "protocollo":
                    sql.append(" sort by \"protocollo\" ").append(sortType);
            case "dataRilascio":
                    sql.append(" sort by \"data_rilascio\" ").append(sortType);
            case "flagAttoParere":
                    sql.append(" sort by \"flag_atto_parere\" ").append(sortType);
            case "intestatarioAtto":
                    sql.append(" sort by \"intestatario_atto\" ").append(sortType);
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
    public long insert(PareriEAttiAssensoDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"pareri_e_atti_assenso\"")
                                     .append("(\"pratica_id\"")
                                     .append(",\"id\"")
                                     .append(",\"tipologia_atto\"")
                                     .append(",\"autorita_rilascio\"")
                                     .append(",\"protocollo\"")
                                     .append(",\"data_rilascio\"")
                                     .append(",\"flag_atto_parere\"")
                                     .append(",\"intestatario_atto\"")
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
        parameters.add(entity.getId());
        parameters.add(entity.getTipologiaAtto());
        parameters.add(entity.getAutoritaRilascio());
        parameters.add(entity.getProtocollo());
        parameters.add(entity.getDataRilascio());
        parameters.add(entity.getFlagAttoParere());
        parameters.add(entity.getIntestatarioAtto());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(PareriEAttiAssensoDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"pareri_e_atti_assenso\"")
                                     .append(" set \"pratica_id\" = ?")
                                     .append(", \"tipologia_atto\" = ?")
                                     .append(", \"autorita_rilascio\" = ?")
                                     .append(", \"protocollo\" = ?")
                                     .append(", \"data_rilascio\" = ?")
                                     .append(", \"flag_atto_parere\" = ?")
                                     .append(", \"intestatario_atto\" = ?")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getPraticaId());
        parameters.add(entity.getTipologiaAtto());
        parameters.add(entity.getAutoritaRilascio());
        parameters.add(entity.getProtocollo());
        parameters.add(entity.getDataRilascio());
        parameters.add(entity.getFlagAttoParere());
        parameters.add(entity.getIntestatarioAtto());
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(PareriEAttiAssensoDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"pareri_e_atti_assenso\"")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

    /**
     * delete by idPratica method
     */
    public int deleteByPratica(UUID idPratica){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"pareri_e_atti_assenso\"")
                                     .append(" where \"pratica_id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(idPratica);
        return super.update(sql, parameters);
    }
    
}