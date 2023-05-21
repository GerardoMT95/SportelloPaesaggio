package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ParchiPaesaggiImmobiliDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.ParchiPaesaggiImmobiliRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.ParchiPaesaggiImmobiliSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.parchi_paesaggi_immobili
 */
@Repository
public class ParchiPaesaggiImmobiliRepository extends GenericCrudDao<ParchiPaesaggiImmobiliDTO, ParchiPaesaggiImmobiliSearch>{

    private final ParchiPaesaggiImmobiliRowMapper rowMapper = new ParchiPaesaggiImmobiliRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"pratica_id\"")
                                     .append(",\"comune_id\"")
                                     .append(",\"tipo_vincolo\"")
                                     .append(",\"codice\"")
                                     .append(",\"descrizione\"")
                                     .append(",\"selezionato\"")
                                     .append(",\"info\"")
                                     .append(",\"data_inserimento\"")
                                     .append(",\"tipo_variazione\"")
                                     .append(",\"notifica\"")
                                     .append(" from \"presentazione_istanza\".\"parchi_paesaggi_immobili\"")
                                     .toString();
    @Override
    public List<ParchiPaesaggiImmobiliDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"parchi_paesaggi_immobili\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public ParchiPaesaggiImmobiliDTO find(ParchiPaesaggiImmobiliDTO pk){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"pratica_id\" = ?")
                                     .append(" and \"comune_id\" = ?")
                                     .append(" and \"tipo_vincolo\" = ?")
                                     .append(" and \"codice\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getPraticaId());
        parameters.add(pk.getComuneId());
        parameters.add(pk.getTipoVincolo());
        parameters.add(pk.getCodice());
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    
    /**
     * search method
     */
    @Override
    public PaginatedList<ParchiPaesaggiImmobiliDTO> search(ParchiPaesaggiImmobiliSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getPraticaId())){
            sql.append(sep).append("lower(\"pratica_id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPraticaId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getComuneId())){
            sql.append(sep).append("lower(\"comune_id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getComuneId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getTipoVincolo())){
            sql.append(sep).append("lower(\"tipo_vincolo\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getTipoVincolo()));
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
        if(StringUtil.isNotEmpty(search.getSelezionato())){
            sql.append(sep).append("lower(\"selezionato\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getSelezionato()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getInfo())){
            sql.append(sep).append("lower(\"info\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getInfo()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataInserimento())){
            sql.append(sep).append("lower(\"data_inserimento\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDataInserimento()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "praticaId":
                    sql.append(" sort by \"pratica_id\" ").append(sortType);
            case "comuneId":
                    sql.append(" sort by \"comune_id\" ").append(sortType);
            case "tipoVincolo":
                    sql.append(" sort by \"tipo_vincolo\" ").append(sortType);
            case "codice":
                    sql.append(" sort by \"codice\" ").append(sortType);
            case "descrizione":
                    sql.append(" sort by \"descrizione\" ").append(sortType);
            case "selezionato":
                    sql.append(" sort by \"selezionato\" ").append(sortType);
            case "info":
                    sql.append(" sort by \"info\" ").append(sortType);
            case "dataInserimento":
                    sql.append(" sort by \"data_inserimento\" ").append(sortType);
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
    public long insert(ParchiPaesaggiImmobiliDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"parchi_paesaggi_immobili\"")
                                     .append("(\"pratica_id\"")
                                     .append(",\"comune_id\"")
                                     .append(",\"tipo_vincolo\"")
                                     .append(",\"codice\"")
                                     .append(",\"descrizione\"")
                                     .append(",\"selezionato\"")
                                     .append(",\"info\"")
                                     .append(",\"data_inserimento\"")
                                     .append(",\"tipo_variazione\"")
                                     .append(",\"notifica\"")
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
                                     .append(",?")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getPraticaId());
        parameters.add(entity.getComuneId());
        parameters.add(entity.getTipoVincolo());
        parameters.add(entity.getCodice());
        parameters.add(entity.getDescrizione());
        parameters.add(entity.getSelezionato());
        parameters.add(entity.getInfo());
        parameters.add(entity.getDataInserimento());
        parameters.add(entity.getTipoVariazione());
        parameters.add(entity.getNotifica());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(ParchiPaesaggiImmobiliDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"parchi_paesaggi_immobili\"")
                                     .append(" set \"descrizione\" = ?")
                                     .append(", \"selezionato\" = ?")
                                     .append(", \"info\" = ?")
                                     .append(", \"data_inserimento\" = ?")
                                     .append(" where \"pratica_id\" = ?")
                                     .append(" and \"comune_id\" = ?")
                                     .append(" and \"tipo_vincolo\" = ?")
                                     .append(" and \"codice\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getDescrizione());
        parameters.add(entity.getSelezionato());
        parameters.add(entity.getInfo());
        parameters.add(entity.getDataInserimento());
        parameters.add(entity.getPraticaId());
        parameters.add(entity.getComuneId());
        parameters.add(entity.getTipoVincolo());
        parameters.add(entity.getCodice());
        return super.update(sql, parameters);
    }

    
    
    
    public int aggiornaVincoli(ParchiPaesaggiImmobiliDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"parchi_paesaggi_immobili\"")
                                     .append(" set \"tipo_variazione\" = ?")
                                     .append(", \"notifica\" = ?")
                                     .append(" where \"pratica_id\" = ?")
                                     .append(" and \"comune_id\" = ?")
                                     .append(" and \"tipo_vincolo\" = ?")
                                     .append(" and \"codice\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getTipoVariazione());
        parameters.add(entity.getNotifica());
        parameters.add(entity.getPraticaId());
        parameters.add(entity.getComuneId());
        parameters.add(entity.getTipoVincolo());
        parameters.add(entity.getCodice());
        return super.update(sql, parameters);
    }
    
    
    
    
    public int accettaCambiamentiVincoli(ParchiPaesaggiImmobiliDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"parchi_paesaggi_immobili\"")
                                     .append(" set \"notifica\" = ?")
                                     .append(", \"data_accettazione_cambio_vincolo\" = ?")
                                     .append(" where \"pratica_id\" = ?")
                                     .append(" and \"comune_id\" = ?")
                                     .append(" and \"tipo_vincolo\" = ?")
                                     .append(" and \"codice\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getNotifica());
        parameters.add(new Date());
        parameters.add(entity.getPraticaId());
        parameters.add(entity.getComuneId());
        parameters.add(entity.getTipoVincolo());
        parameters.add(entity.getCodice());
        return super.update(sql, parameters);
    }
    
    
    /**
     * delete by pk method
     */
    @Override
    public int delete(ParchiPaesaggiImmobiliDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"parchi_paesaggi_immobili\"")
                                     .append(" where \"pratica_id\" = ?")
                                     .append(" and \"comune_id\" = ?")
                                     .append(" and \"tipo_vincolo\" = ?")
                                     .append(" and \"codice\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getPraticaId());
        parameters.add(entity.getComuneId());
        parameters.add(entity.getTipoVincolo());
        parameters.add(entity.getCodice());
        return super.update(sql, parameters);
    }

    public List<ParchiPaesaggiImmobiliDTO> select(UUID praticaId,Long comuneId,String tipoVincolo){
    	return this.select(praticaId, comuneId, tipoVincolo, false);
    }
    
    //CUSTOM
    public List<ParchiPaesaggiImmobiliDTO> select(UUID praticaId,Long comuneId,String tipoVincolo,boolean txWrite){
    	StringBuilder sb=new StringBuilder(selectAll)
    			.append(" where \"pratica_id\" = ?")
                .append(" and \"comune_id\" = ?")
                .append(" and \"tipo_vincolo\" = ?");
    	final List<Object> parameters = new ArrayList<Object>();
        parameters.add(praticaId);
        parameters.add(comuneId);
        parameters.add(tipoVincolo);
        if(txWrite) {
        	return super.queryForList(sb.toString(), this.rowMapper,parameters);
        }else {
        	return super.queryForListTxRead(sb.toString(), this.rowMapper,parameters);	
        }
    }
    
    
    public List<ParchiPaesaggiImmobiliDTO> select(UUID praticaId, Integer comuneId) {
    	StringBuilder sb=new StringBuilder(selectAll)
    			.append(" where \"pratica_id\" = ?")
                .append(" and \"comune_id\" = ?");
    	final List<Object> parameters = new ArrayList<Object>();
        parameters.add(praticaId);
        parameters.add(comuneId);
        return super.queryForListTxRead(sb.toString(), this.rowMapper,parameters);
    }
    
    
    /**
     * resetta il campo selezionato a null in tutte le possibili selezioni
     * @param praticaId
     * @param comuneId
     * @param tipoVincolo
     * @return
     */
    private int resetSelezioni(UUID praticaId,Long comuneId,String tipoVincolo){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"parchi_paesaggi_immobili\"")
                                     .append(" set \"selezionato\" = null ")
                                     .append(" where \"pratica_id\" = ?")
                                     .append(" and \"comune_id\" = ?")
                                     .append(" and \"tipo_vincolo\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(praticaId);
        parameters.add(comuneId);
        parameters.add(tipoVincolo);
        return super.update(sql, parameters);
    }
    
    /**
     * imposta il campo selezionato=S sulla lista di codici passati
     * @param praticaId
     * @param comuneId
     * @param tipoVincolo
     * @param codici
     * @return
     */
    public int setSelezioni(UUID praticaId,Long comuneId,String tipoVincolo,List<String> codici){
    	this.resetSelezioni(praticaId, comuneId, tipoVincolo);
    	if(codici==null || codici.size()<=0) return 0;
    	Set<String> codiciSet=codici.stream().collect(Collectors.toSet());
    	final String sql = new StringBuilder("update \"presentazione_istanza\".\"parchi_paesaggi_immobili\"")
                                     .append(" set \"selezionato\" = 'S' ")
                                     .append(" where \"pratica_id\" = :pratica_id ")
                                     .append(" and \"comune_id\" = :comune_id ")
                                     .append(" and \"tipo_vincolo\" = :tipo_vincolo ")
                                     .append(" and \"codice\" IN ( :codici ) ")
                                     .toString();
        final Map<String,Object> parameters = new HashMap<String,Object>();
        parameters.put("pratica_id",praticaId);
        parameters.put("comune_id",comuneId);
        parameters.put("tipo_vincolo",tipoVincolo);
        parameters.put("codici",codiciSet);
        return super.namedUpdate(sql, parameters);
    }
    
    
}
