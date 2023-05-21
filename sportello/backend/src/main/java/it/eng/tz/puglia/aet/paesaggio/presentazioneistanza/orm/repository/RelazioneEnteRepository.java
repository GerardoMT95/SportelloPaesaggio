package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;
import it.eng.tz.puglia.bean.PaginatedList;
import org.springframework.stereotype.Repository;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.*;

/**
 * Repository for table presentazione_istanza.relazione_ente
 */
@Repository
public class RelazioneEnteRepository extends GenericCrudDao<RelazioneEnteDTO, RelazioneEnteSearch>{

    private final RelazioneEnteRowMapper rowMapper = new RelazioneEnteRowMapper();
    
    private final AllegatiRelazioneRowMapper allegatoRowMapper= new AllegatiRelazioneRowMapper();
    
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id_relazione_ente\"")
                                     .append(",\"id_pratica\"")
                                     .append(",\"numero_protocollo_ente\"")
                                     .append(",\"nominativo_istruttore\"")
                                     .append(",\"dettaglio_relazione\"")
                                     .append(",\"nota_interna\"")
                                     .append(",\"id_corrispondenza\"")
                                     .append(" from \"presentazione_istanza\".\"relazione_ente\"")
                                     .toString();
    @Override
    public List<RelazioneEnteDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"relazione_ente\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public RelazioneEnteDTO find(RelazioneEnteDTO pk){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"id_relazione_ente\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getIdRelazioneEnte());
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    /**
     * search method
     */
    @Override
    public PaginatedList<RelazioneEnteDTO> search(RelazioneEnteSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getIdRelazioneEnte())){
            sql.append(sep).append("lower(\"id_relazione_ente\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdRelazioneEnte()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdPratica())){
            sql.append(sep).append("lower(\"id_pratica\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdPratica()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getNumeroProtocolloEnte())){
            sql.append(sep).append("lower(\"numero_protocollo_ente\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getNumeroProtocolloEnte()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getNominativoIstruttore())){
            sql.append(sep).append("lower(\"nominativo_istruttore\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getNominativoIstruttore()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDettaglioRelazione())){
            sql.append(sep).append("lower(\"dettaglio_relazione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDettaglioRelazione()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getNotaInterna())){
            sql.append(sep).append("lower(\"nota_interna\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getNotaInterna()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "idRelazioneEnte":
                    sql.append(" order by \"id_relazione_ente\" ").append(sortType);
                	   break;
            case "idPratica":
                    sql.append(" order by \"id_pratica\" ").append(sortType);
                	   break;
            case "numeroProtocolloEnte":
                    sql.append(" order by \"numero_protocollo_ente\" ").append(sortType);
                	   break;
            case "nominativoIstruttore":
                    sql.append(" order by \"nominativo_istruttore\" ").append(sortType);
                	   break;
            case "dettaglioRelazione":
                    sql.append(" order by \"dettaglio_relazione\" ").append(sortType);
                	   break;
            case "notaInterna":
                    sql.append(" order by \"nota_interna\" ").append(sortType);
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
    public long insert(RelazioneEnteDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"relazione_ente\"")
                                     .append("(\"id_pratica\"")
                                     .append(",\"numero_protocollo_ente\"")
                                     .append(",\"nominativo_istruttore\"")
                                     .append(",\"dettaglio_relazione\"")
                                     .append(",\"nota_interna\"")
                                     .append(") values ")
                                     .append("(?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIdPratica());
        parameters.add(entity.getNumeroProtocolloEnte());
        parameters.add(entity.getNominativoIstruttore());
        parameters.add(entity.getDettaglioRelazione());
        parameters.add(entity.getNotaInterna());
        return super.insertAndGetAutoincrementValue(sql, parameters, "id_relazione_ente");
    }

    /**
     * update by pk method
     */
    @Override
    public int update(RelazioneEnteDTO entity){
        final StringBuilder sql = new StringBuilder("update \"presentazione_istanza\".\"relazione_ente\"")
                                     .append(" set \"id_pratica\" = ?")
                                     .append(", \"numero_protocollo_ente\" = ?")
                                     .append(", \"nominativo_istruttore\" = ?")
                                     .append(", \"dettaglio_relazione\" = ?")
                                     .append(", \"nota_interna\" = ?");
                                     if(entity.getIdCorrispondenza()!=null) {
                                    	 sql.append(", \"id_corrispondenza\" = ?");
                                     }
                                     sql.append(" where \"id_relazione_ente\" = ?");
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIdPratica());
        parameters.add(entity.getNumeroProtocolloEnte());
        parameters.add(entity.getNominativoIstruttore());
        parameters.add(entity.getDettaglioRelazione());
        parameters.add(entity.getNotaInterna());
        if(entity.getIdCorrispondenza()!=null) {
        	parameters.add(entity.getIdCorrispondenza());
        }
        parameters.add(entity.getIdRelazioneEnte());
        return super.update(sql.toString(), parameters);
    }
    
    

    /**
     * delete by pk method
     */
    @Override
    public int delete(RelazioneEnteDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"relazione_ente\"")
                                     .append(" where \"id_relazione_ente\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIdRelazioneEnte());
        return super.update(sql, parameters);
    }

	public List<RelazioneEnteDTO> findByIdPratica(UUID idPratica) {
		final String sql = new StringBuilder(selectAll)
                .append(" where \"id_pratica\" = ?")
                .toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(idPratica);
		List<RelazioneEnteDTO> relazione = super.queryForListTxRead(sql, this.rowMapper, parameters);
		
		return relazione;
	}
	
	public List<AllegatiDTO> searchAllegati(long idRelazione) {
		final String sql ="select a.*,tc.descrizione as type \r\n" + 
				"from \"presentazione_istanza\".\"allegati\" a \r\n" + 
				"inner join presentazione_istanza.allegati_relazione_ente ae on ae.id_allegato =a.id \r\n"+
				"inner join presentazione_istanza.allegati_tipo_contenuto at on at.allegati_id=a.id \r\n"+
				"inner join presentazione_istanza.tipo_contenuto tc on at.tipo_contenuto_id=tc.id \r\n"+
				"where ae.id_relazione_ente =?";
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(idRelazione);
		List<AllegatiDTO> allegatiRelazione = super.queryForListTxRead(sql, this.allegatoRowMapper, parameters);
		return allegatiRelazione;
	}

	public int insertAllegatoRelazioneEnte(UUID idAllegato, long idRelazione) {
		final String sql ="INSERT INTO \"presentazione_istanza\".\"allegati_relazione_ente\" "
							+"(\"id_allegato\",\"id_relazione_ente\")  VALUES(?,?)";
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(idAllegato);
		parameters.add(idRelazione);
		return super.update(sql, parameters);
	}

}
