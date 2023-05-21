package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.SimpleAllegatiSedutaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.SezioneAllegati;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.PlainStringValueLabelRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.UlterioreDocRowmapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.UlterioreDocSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.allegati
 */
@Repository
public class UlterioreDocRepository extends GenericCrudDao<AllegatiDTO, UlterioreDocSearch>{

    private final UlterioreDocRowmapper rowMapper = new UlterioreDocRowmapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select ")
                                     .append(" a.\"id\"")
                                     .append(",a.\"nome_file\"")
                                     .append(",a.\"formato_file\"")
                                     .append(",a.\"data_caricamento\"")
                                     .append(",a.\"path_cms\"")
                                     .append(",a.\"id_cms\"")
                                     .append(",a.\"pratica_id\"")
                                     .append(",a.\"descrizione\"")
                                     .append(",a.\"checksum\"")
                                     .append(",a.\"size\"")
                                     .append(",a.\"deleted\"")
                                     .append(",a.\"integrazione_id\"")
                                     .append(",a.\"protocollo\"")
                                     .append(",a.\"titolo\"")
                                     .append(",a.\"id_allegato_pre_protocollazione\"")
                                     .append(",c.visibilita as visibilita")
                                     .append(",c.mittente_ruolo as ruolo")
                                     .append(",c.mittente_ente as inseritoDa")
                                     .append(",string_agg(d.email,',') as notificaA")
                                     .append(" from \"presentazione_istanza\".\"allegati\" a ")
                                     .append(" left join \"presentazione_istanza\".\"allegato_corrispondenza\" ac on ac.id_allegato=a.id  ")
                                     .append(" left join \"presentazione_istanza\".\"corrispondenza\" c on ac.id_corrispondenza=c.id  ")
                                     .append(" left join \"presentazione_istanza\".\"destinatario\" d on d.id_corrispondenza=c.id  ")
                                     .toString();
    
    private String generateWhereClause(String baseSql, Map<String, Object> parameters, UlterioreDocSearch search)
    {
    	String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(baseSql);
        if(search.getIdFascicolo() != null)
        {
        	sql.append(sep).append(" a.pratica_id = :idFascicolo");
        	parameters.put("idFascicolo", search.getIdFascicolo());
        	sep = " and ";
        }
        if(search.getVisibileA() != null && !search.getVisibileA().isEmpty())
        {
        	String[] visibileArr= search.getVisibileA().split(",");
        	List<String> lista = Arrays.asList(visibileArr);
        	Set<String> tokenSet=new HashSet<>(lista);
        	String[] tokens = tokenSet.toArray(new String[] {});
        	String whereEnte="";
        	if(tokens.length>0) {
        		whereEnte="\"codice\" in (:codici) ";
        		parameters.put("codici", Arrays.asList(tokens));
//        		if(tokens.length==1) {
//            		whereEnte="\"codice\" LIKE :ente";
//            		parameters.put("ente", tokens[0]);
//            	}else {
//            		whereEnte=" (\"codice\" LIKE :ente OR \"codice\" LIKE :ente1 )";
//            		parameters.put("ente", tokens[0]);
//            		parameters.put("ente1", tokens[1]);
//            	}
            	sql.append(sep)
            		.append("(select count(*) from \"presentazione_istanza\".\"allegato_ente\" ")
      	   	   		.append("where \"id_allegato\" = a.id ")
      	   	   		.append("and "+whereEnte+") >= :numRec ");
            	parameters.put("numRec",tokens.length);
            	sep = " and ";	
        	}
        	
        }
        
        if(search.getTitoloDocumento() != null)
        {
        	sql.append(sep).append(" lower(a.titolo) LIKE :titolo");
        	parameters.put("titolo", StringUtil.convertFullLike(search.getTitoloDocumento().toLowerCase()));
        	sep = " and ";
        }
        if(search.getDescrizioneContenuto() != null)
        {
        	sql.append(sep).append(" lower(a.descrizione) LIKE :descrizione");
        	parameters.put("descrizione", StringUtil.convertFullLike(search.getDescrizioneContenuto().toLowerCase()));
        	sep = " and ";
        }
        
        if(search.getDestinatarioNotifica() != null)
        {
        	sql.append(sep)
    		.append("(select count(*) from \"presentazione_istanza\".\"destinatario\" ")
	   	   		.append("where id_corrispondenza=c.id ")
	   	   		.append("and email LIKE :email) >0 ");
        	
        	parameters.put("email", "%"+search.getDestinatarioNotifica()+"%");
        	sep = " and ";
        }
        
        if(search.getDataCondivisioneDa() !=null) {
        	sql.append(sep).append(" date(c.data_invio) >= :dataDa");
        	parameters.put("dataDa",search.getDataCondivisioneDa());
        	sep = " and ";
        }
        if(search.getDataCondivisioneA() !=null) {
        	sql.append(sep).append(" date(c.data_invio) <= :dataA");
        	parameters.put("dataA",search.getDataCondivisioneA());
        	sep = " and ";
        }
       
        if(StringUtil.isNotEmpty(search.getSortBy()))
        {
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "id":
                sql.append(" order by \"id\" ").append(sortType);
                break;
            case "nomeFile":
                sql.append(" order by \"nome_file\" ").append(sortType);
                break;
            case "formatoFile":
                sql.append(" order by \"formato_file\" ").append(sortType);
                break;
            case "dataCaricamento":
                sql.append(" order by \"data_caricamento\" ").append(sortType);
                break;
            case "pathCms":
                sql.append(" order by \"path_cms\" ").append(sortType);
                break;
            case "idCms":
                sql.append(" order by \"id_cms\" ").append(sortType);
                break;
            case "praticaId":
                sql.append(" order by \"pratica_id\" ").append(sortType);
                break;
            case "descrizione":
                sql.append(" order by \"descrizione\" ").append(sortType);
                break;
            default:
        	    logger.warn(StringUtil.concateneString("value ", search.getSortBy(), " not valid for sort by"));
        	    break;
            }
        }
        sql.append(" GROUP BY a.id,c.visibilita,c.mittente_ruolo,c.mittente_ente");
        
        return sql.toString();
    }
        
    @Override
    public List<AllegatiDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }
    
//    public List<AllegatiDTO> findByPratica(UUID praticaId){
//    	final List<Object> parameters = new ArrayList<Object>();
//        parameters.add(praticaId);
//        return super.queryForListTxRead(selectAll+" where \"pratica_id\" = ? ", this.rowMapper,parameters);
//    }

    /**
     * count all method
     */
    @Override
    public long count(){
//        final String sql = new StringBuilder("select count(*)")
//                                     .append(" from \"presentazione_istanza\".\"allegati\"")
//                                     .toString();
//        return super.queryForObjectTxRead(sql, Long.class);
    	throw new RuntimeException("Metodo non richamabile!");
    }

    /**
     * find by pk method
     */
    @Override
    public AllegatiDTO find(AllegatiDTO pk){
//        final String sql = new StringBuilder(selectAll)
//                                     .append(" where \"id\" = ?")
//                                     .toString();
//        final List<Object> parameters = new ArrayList<Object>();
//        parameters.add(pk.getId());
//        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    	throw new RuntimeException("metodo non richiamabile!");
    }
    /**
     * search method
     */
    @Override
    public PaginatedList<AllegatiDTO> search(UlterioreDocSearch search){
    	Map<String, Object> parameters = new HashMap<String, Object>();
        String sql = generateWhereClause(selectAll, parameters, search);
        return paginatedList(sql, parameters, this.rowMapper, search.getPage(), search.getLimit());
    }

    /**
     * insert all method
     */
    @Override
    public long insert(AllegatiDTO entity){
//        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"allegati\"")
//                                     .append("(\"id\"")
//                                     .append(",\"nome_file\"")
//                                     .append(",\"formato_file\"")
//                                     .append(",\"data_caricamento\"")
//                                     .append(",\"path_cms\"")
//                                     .append(",\"id_cms\"")
//                                     .append(",\"pratica_id\"")
//                                     .append(",\"descrizione\"")
//                                     .append(",\"checksum\"")
//                                     .append(",\"size\"")
//                                     .append(",\"integrazione_id\"")
//                                     .append(",\"titolo\"")
//                                     .append(",\"id_allegato_pre_protocollazione\"")
//                                     .append(") values ")
//                                     .append("(?")
//                                     .append(",?")
//                                     .append(",?")
//                                     .append(",?")
//                                     .append(",?")
//                                     .append(",?")
//                                     .append(",?")
//                                     .append(",?")
//                                     .append(",?")
//                                     .append(",?")
//                                     .append(",?")
//                                     .append(",?")
//                                     .append(",?")
//                                     .append(")")
//                                     .toString();
//        final List<Object> parameters = new ArrayList<Object>();
//        parameters.add(entity.getId());
//        parameters.add(entity.getNomeFile());
//        parameters.add(entity.getFormatoFile());
//        parameters.add(entity.getDataCaricamento());
//        parameters.add(entity.getPathCms());
//        parameters.add(entity.getIdCms());
//        parameters.add(entity.getPraticaId());
//        parameters.add(entity.getDescrizione());
//        parameters.add(entity.getChecksum());
//        parameters.add(entity.getSize());
//        parameters.add(entity.getIdIntegrazione());
//        parameters.add(entity.getTitolo());
//        parameters.add(entity.getIdAllegatoPreProtocollazione());
//        return super.update(sql, parameters);
    	throw new RuntimeException("non implementato");
    }

    /**
     * update by pk method
     */
    @Override
    public int update(AllegatiDTO entity){
    	throw new RuntimeException("Metodo non richiamabile!");
//        final String sql = new StringBuilder("update \"presentazione_istanza\".\"allegati\"")
//                                     .append(" set \"nome_file\" = ?")
//                                     .append(", \"formato_file\" = ?")
//                                     .append(", \"data_caricamento\" = ?")
//                                     .append(", \"path_cms\" = ?")
//                                     .append(", \"id_cms\" = ?")
//                                     .append(", \"pratica_id\" = ?")
//                                     .append(", \"descrizione\" = ?")
//                                     .append(", \"checksum\" = ?")
//                                     .append(", \"size\" = ?")
//                                     .append(", \"protocollo\" = ?")
//                                     .append(", \"titolo\" = ?")
//                                     .append(", \"id_allegato_pre_protocollazione\" = ?")
//                                     .append(" where \"id\" = ?")
//                                     .toString();
//        final List<Object> parameters = new ArrayList<Object>();
//        parameters.add(entity.getNomeFile());
//        parameters.add(entity.getFormatoFile());
//        parameters.add(entity.getDataCaricamento());
//        parameters.add(entity.getPathCms());
//        parameters.add(entity.getIdCms());
//        parameters.add(entity.getPraticaId());
//        parameters.add(entity.getDescrizione());
//        parameters.add(entity.getChecksum());
//        parameters.add(entity.getSize());
//        parameters.add(entity.getProtocollo());
//        parameters.add(entity.getTitolo());
//        parameters.add(entity.getIdAllegatoPreProtocollazione());
//        parameters.add(entity.getId());
//        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(AllegatiDTO entity){
    	throw new RuntimeException("Metodo non richiamabile!");
//        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"allegati\"")
//                                     .append(" where \"id\" = ?")
//                                     .toString();
//        final List<Object> parameters = new ArrayList<Object>();
//        parameters.add(entity.getId());
//        return super.update(sql, parameters);
    }
    
//    public int deleteAllegatoRelazione(UUID idFile){
//    	 final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"allegati_relazione_ente\"")
//                 .append(" where \"id_allegato\" = ?")
//                 .toString();
//			final List<Object> parameters = new ArrayList<Object>();
//			parameters.add(idFile);
//			super.update(sql, parameters);
//        final String sqlallegato = new StringBuilder("delete from \"presentazione_istanza\".\"allegati\"")
//                                     .append(" where \"id\" = ?")
//                                     .toString();
//        return super.update(sqlallegato, parameters);
//    }
    
//    public List<AllegatiDTO> findByPraticaAndSezione(UUID id, int tipoProcedimento, SezioneAllegati sezione) { return findByPraticaAndSezione(id, tipoProcedimento, sezione, null); }
//
//	public List<AllegatiDTO> findByPraticaAndSezione(UUID id, int tipoProcedimento, SezioneAllegati sezione, Integer idIntegrzione)
//	{
//		final List<Object> parameters = new ArrayList<Object>();
//		StringBuilder sb=new StringBuilder(selectAll);
//		sb.append(", \"presentazione_istanza\".\"allegati_tipo_contenuto\" atc ")
//		  .append(", \"presentazione_istanza\".\"procedimento_contenuto\" pc ")
//		  .append(", \"presentazione_istanza\".\"tipo_contenuto\" tc ")
//		  .append("where a.\"pratica_id\" = ? ")
//		  .append("and a.\"id\" = atc.\"allegati_id\" and pc.\"tipo_contenuto_id\" =atc.\"tipo_contenuto_id\" ")
//		  .append("and tc.\"id\" = pc.\"tipo_contenuto_id\" ")
//		  .append("and pc.\"tipo_procedimento\"= ? ")
//		  .append("and tc.\"sezione\"= ? ")
//		  .append("and a.\"deleted\" = false");
//		
//		parameters.add(id);
//        parameters.add(tipoProcedimento);
//        parameters.add(sezione.toString());
//		
//		if(idIntegrzione != null)
//		{
//			sb.append(" and a.\"integrazione_id\" = ?");
//			parameters.add(idIntegrzione);
//		}
//		else
//		{
//			sb.append(" and (a.\"integrazione_id\" is null or ")
//	     	  .append("(select count(*) from \"presentazione_istanza\".\"integrazione\" ")
//	     	  .append("where \"integrazione\".\"id\" = a.\"integrazione_id\" and \"stato\" = 'COMPLETATA') > 0")
//	     	  .append(")");
//		}
//		
//		sb.append(" group by a.\"id\" ");
//        List<AllegatiDTO> ret = super.queryForListTxRead(sb.toString(), this.rowMapper, parameters);
//        return ret; 
//	}
//	
//	public List<PlainStringValueLabel> findTipiAllegato(UUID idAllegato) {
//		StringBuilder sb=new StringBuilder();
//		sb
//		.append("select tc.descrizione as label, tc.id as value, '' as linked ")
//		.append(" from \"presentazione_istanza\".\"allegati\" a ")
//		.append(" , \"presentazione_istanza\".\"allegati_tipo_contenuto\" atc ")
//		.append(" , \"presentazione_istanza\".\"tipo_contenuto\" tc ")
//		.append(" where a.\"id\" = ? ")
//		.append(" and a.\"id\" = atc.\"allegati_id\"  ")
//		.append(" and tc.\"id\" = atc.\"tipo_contenuto_id\" ");
//		final List<Object> parameters = new ArrayList<Object>();
//        parameters.add(idAllegato);
//        List<PlainStringValueLabel> ret = super.queryForListTxRead(sb.toString(),new PlainStringValueLabelRowMapper(),parameters);
//        return ret; 
//	}
//	
//	public boolean canUpload(UUID praticaId, List<Integer> tipiContenuti)
//	{
//		Map<String, Object> parameters = new HashMap<String, Object>();
//		StringBuilder sb = new StringBuilder("select count(*) ").append("from \"presentazione_istanza\".\"allegati\" ")
//																.append("where \"pratica_id\" = :praticaid ")
//																.append("and (select \"tipo_contenuto_id\" from ")
//																.append("\"presentazione_istanza\".\"allegati_tipo_contenuto\" ")
//																.append("join \"presentazione_istanza\".\"tipo_contenuto\" ")
//																.append("on \"tipo_contenuto_id\" = \"tipo_contenuto\".\"id\" ")
//																.append("where \"allegati_id\" = \"allegati\".\"id\" and \"multiple\" = false) ")
//																.append("in (:tipiContenuti)");
//		parameters.put("praticaid", praticaId);
//		parameters.put("tipiContenuti", tipiContenuti);
//		logger.info("Eseguo la query {} con i parametri {}", sb.toString(), parameters);
//		Long ret = namedQueryForObject(sb.toString(), Long.class, parameters);
//        return ret == 0;
//	}
//
//	public List<AllegatiDTO> findAllegatiCorrispondenza(Long idCorrispondenza) throws Exception
//	{
//		List<Object> parameters = new ArrayList<Object>();
//		parameters.add(idCorrispondenza);
//		String sql = new StringBuilder(selectAll)
//						.append(" join  \"presentazione_istanza\".\"allegato_corrispondenza\" ac ")
//						.append("on ac.\"id_allegato\" = a.id and ac.\"id_corrispondenza\" = ?")
//						.toString();
//		return queryForList(sql, rowMapper, parameters);
//	}
//	
//	
//	
//	
//	public List<AllegatiDTO> searchBySezioni(UUID praticaId, List<String> sezioni) throws Exception
//	{
//		StringBuilder sql = new StringBuilder(selectAll);
//					  sql.append("left join \"presentazione_istanza\".\"allegati_tipo_contenuto\" atc ")
//					  	 .append("on \"a\".\"id\" = \"atc\".\"allegati_id\" ")
//					  	 .append("left join \"presentazione_istanza\".\"tipo_contenuto\" tc ")
//					  	 .append("on \"atc\".\"tipo_contenuto_id\" = \"tc\".\"id\" ")
//					  	 .append("where \"a\".\"pratica_id\" = :praticaId ")
//					  	 .append("and \"tc\".\"sezione\" IN (:sezioni) ");
//		
//		Map<String, Object> parameters = new HashMap<String, Object>();
//		parameters.put("praticaId", praticaId);
//		parameters.put("sezioni", (sezioni!=null && sezioni.isEmpty()) ? null : sezioni);
//		logger.info("Eseguo la query {} con i parametri {}", sql.toString(), parameters);
//		return namedQueryForList(sql.toString(), rowMapper, parameters);
//	}
//	
//	public List<AllegatiDTO> searchAllegatiRichiesteASR(Long idRichiesta) throws Exception
//	{
//		StringBuilder sql = new StringBuilder(selectAll)
//							.append(" left join \"presentazione_istanza\".\"allegato_sosp_arc_att\"")
//							.append(" on \"id\" = \"id_allegato\"")
//							.append(" where \"id_sosp_arc_att\" = :id_richiesta");
//		final Map<String, Object> parameters = new HashMap<String, Object>();
//		parameters.put("id_richiesta", idRichiesta);
//		logger.info("Eseguo la query {} con i parametri {}", sql.toString(), parameters);
//		return namedQueryForList(sql.toString(), rowMapper, parameters);
//	}
//	
//	public List<SimpleAllegatiSedutaDTO> searchAllegatiSeduta(UUID idPratica) throws Exception
//	{
//		String sql = StringUtil.concateneString("select \"allegati\".*, \"seduta_commissione\".\"id\" as id_seduta",
//												" from \"presentazione_istanza\".\"allegati\"",
//												" join \"presentazione_istanza\".\"seduta_pratica_allegati\" as spa",
//												" on \"id_allegato\" = \"id\"",
//												" join \"presentazione_istanza\".\"seduta_commissione\"",
//												" on \"id_seduta\" = \"seduta_commissione\".\"id\"",
//												" where spa.\"id_pratica\" = :id_pratica",
//												" and \"seduta_commissione\".\"stato\" = 'CONCLUSA'");
//		Map<String, Object> parameters = new HashMap<String, Object>();
//		parameters.put("id_pratica", idPratica);
//		logger.info("Eseguo la query {} con i parametri {}", sql.toString(), parameters);
//		return namedQueryForList(sql, new RowMapper<SimpleAllegatiSedutaDTO>()
//		{
//
//			@Override
//			public SimpleAllegatiSedutaDTO mapRow(ResultSet rs, int rowNum) throws SQLException
//			{
//				SimpleAllegatiSedutaDTO result = null;
//				if(rs != null)
//				{
//					result = new SimpleAllegatiSedutaDTO(rowMapper.mapRow(rs, rowNum));
//					result.setIdSeduta(rs.getLong("id_seduta"));
//				}
//				return result;
//			}
//		}, parameters);
//	}
//	
//	public List<AllegatiDTO> findAllegatiParere(Long idParere) throws Exception
//	{
//		String sql = StringUtil.concateneString(selectAll, 
//												" join \"presentazione_istanza\".\"parere_soprintendenza_allegato\" as psa",
//												" on a.\"id\" = psa.\"id_allegato\"",
//												" where psa.\"id_parere\" = :id_parere");
//		final Map<String, Object> parameters = new HashMap<String, Object>();
//		parameters.put("id_parere", idParere);
//		logger.info("Eseguo la query {} con i parametri {}", sql.toString(), parameters);
//		return namedJdbcTemplateRead.query(sql, parameters, rowMapper);
//	}
//
//	public int insertVisibilitaUlerioreDoc(UUID idAllegato, String ente) {
//		 final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"allegato_ente\"")
//                 .append("(\"id_allegato\"").append(",\"codice\"").append(") values ")
//                 .append("(?").append(",?)").toString();
//	        final List<Object> parameters = new ArrayList<Object>();
//	        parameters.add(idAllegato);
//	        parameters.add(ente);
//	       
//	        return super.update(sql, parameters);
//	}
	
}
