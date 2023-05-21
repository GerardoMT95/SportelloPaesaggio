package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoRicevuta;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.RicevutaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.RicevutaRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.RicevutaSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.ricevuta
 */
@Repository
public class RicevutaRepository extends GenericCrudDao<RicevutaDTO, RicevutaSearch>{

    private final RicevutaRowMapper rowMapper = new RicevutaRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id\"")
                                     .append(",\"id_corrispondenza\"")
                                     .append(",\"id_destinatario\"")
                                     .append(",\"tipo_ricevura\"")
                                     .append(",\"errore\"")
                                     .append(",\"descrizione_errore\"")
                                     .append(",\"id_cms_eml\"")
                                     .append(",\"id_cms_dati_cert\"")
                                     .append(",\"id_cms_smime\"")
                                     .append(",\"data\"")
                                     .append(" from \"presentazione_istanza\".\"ricevuta\"")
                                     .toString();
    @Override
    public List<RicevutaDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"ricevuta\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public RicevutaDTO find(RicevutaDTO pk){
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
    public PaginatedList<RicevutaDTO> search(RicevutaSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(search.getId()!=null){
            sql.append(sep).append(" \"id\" = ?");
            parameters.add(search.getId());
            sep = " and ";
        }
        if(search.getIdCorrispondenza()!=null){
            sql.append(sep).append("\"id_corrispondenza\" = ?");
            parameters.add(search.getIdCorrispondenza());
            sep = " and ";
        }
        if(search.getIdDestinatario()!=null){
            sql.append(sep).append("\"id_destinatario\" = ?");
            parameters.add(search.getIdDestinatario());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getTipoRicevura())){
            sql.append(sep).append("lower(\"tipo_ricevura\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getTipoRicevura()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getErrore())){
            sql.append(sep).append("lower(\"errore\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getErrore()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDescrizioneErrore())){
            sql.append(sep).append("lower(\"descrizione_errore\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDescrizioneErrore()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdCmsEml())){
            sql.append(sep).append("lower(\"id_cms_eml\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdCmsEml()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdCmsDatiCert())){
            sql.append(sep).append("lower(\"id_cms_dati_cert\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdCmsDatiCert()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdCmsSmime())){
            sql.append(sep).append("lower(\"id_cms_smime\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdCmsSmime()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getData())){
            sql.append(sep).append("lower(\"data\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getData()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "id":
                    sql.append(" order by \"id\" ").append(sortType);
                	   break;
            case "idCorrispondenza":
                    sql.append(" order by \"id_corrispondenza\" ").append(sortType);
                	   break;
            case "idDestinatario":
                    sql.append(" order by \"id_destinatario\" ").append(sortType);
                	   break;
            case "tipoRicevura":
                    sql.append(" order by \"tipo_ricevura\" ").append(sortType);
                	   break;
            case "errore":
                    sql.append(" order by \"errore\" ").append(sortType);
                	   break;
            case "descrizioneErrore":
                    sql.append(" order by \"descrizione_errore\" ").append(sortType);
                	   break;
            case "idCmsEml":
                    sql.append(" order by \"id_cms_eml\" ").append(sortType);
                	   break;
            case "idCmsDatiCert":
                    sql.append(" order by \"id_cms_dati_cert\" ").append(sortType);
                	   break;
            case "idCmsSmime":
                    sql.append(" order by \"id_cms_smime\" ").append(sortType);
                	   break;
            case "data":
                    sql.append(" order by \"data\" ").append(sortType);
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
    public long insert(RicevutaDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"ricevuta\"")
                                     .append("(\"id_corrispondenza\"")
                                     .append(",\"id_destinatario\"")
                                     .append(",\"tipo_ricevura\"")
                                     .append(",\"errore\"")
                                     .append(",\"descrizione_errore\"")
                                     .append(",\"id_cms_eml\"")
                                     .append(",\"id_cms_dati_cert\"")
                                     .append(",\"id_cms_smime\"")
                                     .append(",\"data\"")
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
        parameters.add(entity.getIdCorrispondenza());
        parameters.add(entity.getIdDestinatario());
        parameters.add(entity.getTipoRicevuta().toString());
        parameters.add(entity.getErrore().toString());
        parameters.add(entity.getDescrizioneErrore());
        parameters.add(entity.getIdCmsEml());
        parameters.add(entity.getIdCmsDatiCert());
        parameters.add(entity.getIdCmsSmime());
        parameters.add(entity.getData());
        return super.insertAndGetAutoincrementValue(sql, parameters, "id");
    }

    /**
     * update by pk method
     */
    @Override
    public int update(RicevutaDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"ricevuta\"")
                                     .append(" set \"id_corrispondenza\" = ?")
                                     .append(", \"id_destinatario\" = ?")
                                     .append(", \"tipo_ricevura\" = ?")
                                     .append(", \"errore\" = ?")
                                     .append(", \"descrizione_errore\" = ?")
                                     .append(", \"id_cms_eml\" = ?")
                                     .append(", \"id_cms_dati_cert\" = ?")
                                     .append(", \"id_cms_smime\" = ?")
                                     .append(", \"data\" = ?")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIdCorrispondenza());
        parameters.add(entity.getIdDestinatario());
        parameters.add(entity.getTipoRicevuta().toString());
        parameters.add(entity.getErrore().toString());
        parameters.add(entity.getDescrizioneErrore());
        parameters.add(entity.getIdCmsEml());
        parameters.add(entity.getIdCmsDatiCert());
        parameters.add(entity.getIdCmsSmime());
        parameters.add(entity.getData());
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(RicevutaDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"ricevuta\"")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

}
