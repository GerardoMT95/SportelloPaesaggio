package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.SimpleAllegatiSedutaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.SezioneAllegati;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.AllegatiRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.PlainStringValueLabelRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.AllegatiSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.allegati
 */
@Repository
public class AllegatiRepository extends GenericCrudDao<AllegatiDTO, AllegatiSearch>{

    private final AllegatiRowMapper rowMapper = new AllegatiRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
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
                                     .append(",a.\"id_diogene_scheduler\"")
                                     .append(",a.\"is_signed\"")
                                     .append(" from \"presentazione_istanza\".\"allegati\" a ")
                                     .toString();
    
    private String generateWhereClause(String baseSql, Map<String, Object> parameters, AllegatiSearch search)
    {
    	String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(baseSql);
        if(StringUtil.isNotEmpty(search.getId())){
            sql.append(sep).append("lower(\"id\"::text) like :id");
            parameters.put("id", search.getId());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getNomeFile())){
            sql.append(sep).append("lower(\"nome_file\"::text) like :nome_file");
            parameters.put("nome_file", search.getNomeFile());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getFormatoFile())){
            sql.append(sep).append("lower(\"formato_file\"::text) like :formato_file");
            parameters.put("formato_file", search.getFormatoFile());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataCaricamento())){
            sql.append(sep).append("lower(\"data_caricamento\"::text) like :data_caricamento");
            parameters.put("data_caricamento", search.getDataCaricamento());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPathCms())){
            sql.append(sep).append("lower(\"path_cms\"::text) like :path_cms");
            parameters.put("path_cms", search.getPathCms());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdCms())){
            sql.append(sep).append("lower(\"id_cms\"::text) like :id_cms");
            parameters.put("id_cms", search.getIdCms().toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPraticaId())){
            sql.append(sep).append("\"pratica_id\"::text = :pratica_id");
            parameters.put("pratica_id", search.getPraticaId());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDescrizione())){
            sql.append(sep).append("lower(\"descrizione\"::text) like :descrizione");
            parameters.put("descrizione", search.getDescrizione());
            sep = " and ";
        }
        if(search.getTipoFile() != null)
        {
        	sql.append(sep)
        	   .append("(select count(*) from \"presentazione_istanza\".\"allegati_tipo_contenuto\" ")
        	   .append("where \"allegati_id\" = \"id\" ")
        	   .append("and \"tipo_contenuto_id\" = :tipo_file ) > 0");
        	parameters.put("tipo_file", search.getTipoFile());
        	sep = " and ";
        }
        if(search.getTipologie() != null && !search.getTipologie().isEmpty())
        {
        	sql.append(sep)
        	   .append("(select count(*) from \"presentazione_istanza\".\"allegati_tipo_contenuto\" ")
     	   	   .append("where \"allegati_id\" = \"id\" ")
     	   	   .append("and \"tipo_contenuto_id\" in (:tipi)) > 0");
        	parameters.put("tipi", search.getTipologie());
        	sep = " and ";
        }
        if(search.getVisibilita() != null && !search.getVisibilita().isEmpty())
        {
        	sql.append(sep)
        		.append("(select count(*) from \"presentazione_istanza\".\"allegato_ente\" ")
  	   	   		.append("where \"id_allegato\" = \"id\" ")
  	   	   		.append("and \"codice\" LIKE :ente) >0 ");
        	parameters.put("ente", search.getVisibilita());
        	sep = " and ";
        }
        
        if(search.getDeleted() != null)
        {
        	sql.append(sep).append("\"deleted\" = :deleted");
        	parameters.put("deleted", search.getDeleted());
        	sep = " and ";
        }
        if(search.getIntegrazioneId() != null)
        {
        	sql.append(sep).append("\"integrazione_id\" = :integrazione_id");
        	parameters.put("integrazione_id", search.getIntegrazioneId());
        	sep = " and ";
        }
        else
        {
        	sql.append(sep)
        	   .append("(\"integrazione_id\" is null or ")
        	   .append("(select count(*) from \"presentazione_istanza\".\"integrazione\" ")
        	   .append("where \"integrazione\".\"id\" = a.\"integrazione_id\" and \"stato\" = 'COMPLETATA') > 0")
        	   .append(")");
        	sep = " and ";
        }
        if(search.getIdSeduta() != null)
        {
        	sql.append(sep).append("\"id\" in (select \"id_allegato\" from ")
        				   .append("\"presentazione_istanza\".\"seduta_allegati\"")
        				   .append(" where \"id_seduta\" = :id_seduta)");
        	parameters.put("id_seduta", search.getIdSeduta());
        	sep = " and ";
        }
        if(search.getIdAllegatoPreProtocollazione()!=null) {
        	sql.append(sep).append("\"id_allegato_pre_protocollazione\" = :id_allegato_pre_protocollazione");
        	parameters.put("id_allegato_pre_protocollazione", search.getIdAllegatoPreProtocollazione().toString());
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
        return sql.toString();
    }
        
    @Override
    public List<AllegatiDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }
    
    public List<AllegatiDTO> findByPratica(UUID praticaId){
    	final List<Object> parameters = new ArrayList<Object>();
        parameters.add(praticaId);
        return super.queryForListTxRead(selectAll+" where \"pratica_id\" = ? ", this.rowMapper,parameters);
    }
    
    public List<AllegatiDTO> findByPraticaETipo(UUID praticaId,Integer tipoFile,boolean txWrite){
    	final List<Object> parameters = new ArrayList<Object>();
        parameters.add(praticaId);
        parameters.add(tipoFile);
        StringBuilder sql=new StringBuilder(selectAll);
        sql.append(" where \"pratica_id\" = ? AND ")
        .append("(select count(*) from \"presentazione_istanza\".\"allegati_tipo_contenuto\" ")
 	    .append("where \"allegati_id\" = \"id\" ")
 	    .append("and \"tipo_contenuto_id\" = ? ) > 0");
        if(txWrite) {
        	return super.queryForList(sql.toString(), this.rowMapper,parameters);
        }else {
        	return super.queryForListTxRead(sql.toString(), this.rowMapper,parameters);	
        }
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"allegati\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }
    
    
    /**
     * find by pk method
     */
    
    public AllegatiDTO find(AllegatiDTO pk,boolean txWrite){
        return findById(pk.getId(), txWrite);
    }

	public AllegatiDTO findById(UUID id, boolean txWrite) {
		final String sql = new StringBuilder(selectAll)
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(id);
        if(txWrite) {
        	return super.queryForObject(sql, this.rowMapper, parameters);
        }else {
        	return super.queryForObjectTxRead(sql, this.rowMapper, parameters);	
        }
	}
    
    @Override
    public AllegatiDTO find(AllegatiDTO pk){
       return this.find(pk, false);
    }
    
    /**
     * search method
     */
    @Override
    public PaginatedList<AllegatiDTO> search(AllegatiSearch search){
    	Map<String, Object> parameters = new HashMap<String, Object>();
        String sql = generateWhereClause(selectAll, parameters, search);
        return paginatedList(sql, parameters, this.rowMapper, search.getPage(), search.getLimit());
    }

    /**
     * insert all method
     */
    @Override
    public long insert(AllegatiDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"allegati\"")
                                     .append("(\"id\"")
                                     .append(",\"nome_file\"")
                                     .append(",\"formato_file\"")
                                     .append(",\"data_caricamento\"")
                                     .append(",\"path_cms\"")
                                     .append(",\"id_cms\"")
                                     .append(",\"pratica_id\"")
                                     .append(",\"descrizione\"")
                                     .append(",\"checksum\"")
                                     .append(",\"size\"")
                                     .append(",\"integrazione_id\"")
                                     .append(",\"titolo\"")
                                     .append(",\"id_allegato_pre_protocollazione\"")
                                     .append(",\"is_signed\"")
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
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        parameters.add(entity.getNomeFile());
        parameters.add(entity.getFormatoFile());
        parameters.add(entity.getDataCaricamento());
        parameters.add(entity.getPathCms());
        parameters.add(entity.getIdCms());
        parameters.add(entity.getPraticaId());
        parameters.add(entity.getDescrizione());
        parameters.add(entity.getChecksum());
        parameters.add(entity.getSize());
        parameters.add(entity.getIdIntegrazione());
        parameters.add(entity.getTitolo());
        parameters.add(entity.getIdAllegatoPreProtocollazione());
        parameters.add(entity.isSigned());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(AllegatiDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"allegati\"")
                                     .append(" set \"nome_file\" = ?")
                                     .append(", \"formato_file\" = ?")
                                     .append(", \"data_caricamento\" = ?")
                                     .append(", \"path_cms\" = ?")
                                     .append(", \"id_cms\" = ?")
                                     .append(", \"pratica_id\" = ?")
                                     .append(", \"descrizione\" = ?")
                                     .append(", \"checksum\" = ?")
                                     .append(", \"size\" = ?")
                                     .append(", \"protocollo\" = ?")
                                     .append(", \"titolo\" = ?")
                                     .append(", \"id_allegato_pre_protocollazione\" = ?")
                                     .append(", \"id_diogene_scheduler\" = ?")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getNomeFile());
        parameters.add(entity.getFormatoFile());
        parameters.add(entity.getDataCaricamento());
        parameters.add(entity.getPathCms());
        parameters.add(entity.getIdCms());
        parameters.add(entity.getPraticaId());
        parameters.add(entity.getDescrizione());
        parameters.add(entity.getChecksum());
        parameters.add(entity.getSize());
        parameters.add(entity.getProtocollo());
        parameters.add(entity.getTitolo());
        parameters.add(entity.getIdAllegatoPreProtocollazione());
        parameters.add(entity.getIdDiogeneScheduler());
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(AllegatiDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"allegati\"")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }
    
    public int deleteAllegatoRelazione(UUID idFile){
    	 final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"allegati_relazione_ente\"")
                 .append(" where \"id_allegato\" = ?")
                 .toString();
			final List<Object> parameters = new ArrayList<Object>();
			parameters.add(idFile);
			super.update(sql, parameters);
        final String sqlallegato = new StringBuilder("delete from \"presentazione_istanza\".\"allegati\"")
                                     .append(" where \"id\" = ?")
                                     .toString();
        return super.update(sqlallegato, parameters);
    }
    
    public List<AllegatiDTO> findByPraticaAndSezione(UUID id, int tipoProcedimento, SezioneAllegati sezione) { 
    	return findByPraticaAndSezione(id, tipoProcedimento, sezione, null,null); }

    /**
     * 
     * @author acolaianni
     *
     * @param id
     * @param tipoProcedimento
     * @param sezione
     * @param idIntegrzione nullable
     * @param idTipoContenuto nullable
     * @return
     */
	public List<AllegatiDTO> findByPraticaAndSezione(UUID id, int tipoProcedimento, SezioneAllegati sezione, Integer idIntegrzione,Integer idTipoContenuto)
	{
		final List<Object> parameters = new ArrayList<Object>();
		StringBuilder sb=new StringBuilder(selectAll);
		sb.append(", \"presentazione_istanza\".\"allegati_tipo_contenuto\" atc ")
		  .append(", \"presentazione_istanza\".\"procedimento_contenuto\" pc ")
		  .append(", \"presentazione_istanza\".\"tipo_contenuto\" tc ")
		  .append("where a.\"pratica_id\" = ? ")
		  .append("and a.\"id\" = atc.\"allegati_id\" and pc.\"tipo_contenuto_id\" =atc.\"tipo_contenuto_id\" ")
		  .append("and tc.\"id\" = pc.\"tipo_contenuto_id\" ")
		  .append("and pc.\"tipo_procedimento\"= ? ")
		  .append("and tc.\"sezione\"= ? ")
		  .append("and a.\"deleted\" = false");
		
		parameters.add(id);
        parameters.add(tipoProcedimento);
        parameters.add(sezione.toString());
        
        if( idTipoContenuto!=null) {
        	sb.append(" and tc.\"id\" = ? ");
			parameters.add(idTipoContenuto);
        }
		
		if(idIntegrzione != null)
		{
			sb.append(" and a.\"integrazione_id\" = ?");
			parameters.add(idIntegrzione);
		}
		else
		{
			sb.append(" and (a.\"integrazione_id\" is null or ")
	     	  .append("(select count(*) from \"presentazione_istanza\".\"integrazione\" ")
	     	  .append("where \"integrazione\".\"id\" = a.\"integrazione_id\" and \"stato\" = 'COMPLETATA') > 0")
	     	  .append(")");
		}
		
		sb.append(" group by a.\"id\" ");
        List<AllegatiDTO> ret = super.queryForListTxRead(sb.toString(), this.rowMapper, parameters);
        return ret; 
	}
	
	
	public List<PlainStringValueLabel> findTipiAllegato(UUID idAllegato) {
		StringBuilder sb=new StringBuilder();
		sb
		.append("select tc.descrizione as label, tc.id as value, '' as linked ")
		.append(" from \"presentazione_istanza\".\"allegati\" a ")
		.append(" , \"presentazione_istanza\".\"allegati_tipo_contenuto\" atc ")
		.append(" , \"presentazione_istanza\".\"tipo_contenuto\" tc ")
		.append(" where a.\"id\" = ? ")
		.append(" and a.\"id\" = atc.\"allegati_id\"  ")
		.append(" and tc.\"id\" = atc.\"tipo_contenuto_id\" ");
		final List<Object> parameters = new ArrayList<Object>();
        parameters.add(idAllegato);
        List<PlainStringValueLabel> ret = super.queryForListTxRead(sb.toString(),new PlainStringValueLabelRowMapper(),parameters);
        return ret; 
	}
	
	public boolean canUpload(UUID praticaId, List<Integer> tipiContenuti)
	{
		Map<String, Object> parameters = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder("select count(*) ")
							.append("from \"presentazione_istanza\".\"allegati\" ")
							.append("where \"pratica_id\" = :praticaid ")
							.append("and (select count(*) from ")
							.append("\"presentazione_istanza\".\"allegati_tipo_contenuto\" ")
							.append("join \"presentazione_istanza\".\"tipo_contenuto\" ")
							.append("on \"tipo_contenuto_id\" = \"tipo_contenuto\".\"id\" ")
							.append("where \"allegati_id\" = \"allegati\".\"id\" ")
							.append("and \"multiple\" = false and \"id\" in (:tipi_contenuti))")
							.append(" > 0");
		parameters.put("praticaid", praticaId);
		parameters.put("tipi_contenuti", tipiContenuti);
		logger.info("Eseguo la query {} con i parametri {}", sb.toString(), parameters);
		Long ret = namedQueryForObject(sb.toString(), Long.class, parameters);
        return ret == 0;
	}
	
	public boolean canUpload(UUID praticaId, Integer tipoContenuto)
	{
		Map<String, Object> parameters = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder("select count(*) ").append("from \"presentazione_istanza\".\"allegati\" ")
																.append("where \"pratica_id\" = :praticaid ")
																.append("and :tipo_contenuto in (select \"tipo_contenuto_id\" from ")
																.append("\"presentazione_istanza\".\"allegati_tipo_contenuto\" ")
																.append("join \"presentazione_istanza\".\"tipo_contenuto\" ")
																.append("on \"tipo_contenuto_id\" = \"tipo_contenuto\".\"id\" ")
																.append("where \"allegati_id\" = \"allegati\".\"id\" and \"multiple\" = false) ");
		parameters.put("praticaid", praticaId);
		parameters.put("tipo_contenuto", tipoContenuto);
		logger.info("Eseguo la query {} con i parametri {}", sb.toString(), parameters);
		Long ret = namedQueryForObject(sb.toString(), Long.class, parameters);
        return ret == 0;
	}


	public List<AllegatiDTO> findAllegatiCorrispondenza(Long idCorrispondenza) throws Exception
	{
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(idCorrispondenza);
		String sql = new StringBuilder(selectAll)
						.append(" join  \"presentazione_istanza\".\"allegato_corrispondenza\" ac ")
						.append("on ac.\"id_allegato\" = a.id and ac.\"id_corrispondenza\" = ?")
						.toString();
		return queryForList(sql, rowMapper, parameters);
	}
	
	
	public List<AllegatiDTO> searchBySezioni(UUID praticaId, List<String> sezioni) throws Exception
	{
		StringBuilder sql = new StringBuilder(selectAll);
					  sql.append("left join \"presentazione_istanza\".\"allegati_tipo_contenuto\" atc ")
					  	 .append("on \"a\".\"id\" = \"atc\".\"allegati_id\" ")
					  	 .append("left join \"presentazione_istanza\".\"tipo_contenuto\" tc ")
					  	 .append("on \"atc\".\"tipo_contenuto_id\" = \"tc\".\"id\" ")
					  	 .append("where \"a\".\"pratica_id\" = :praticaId ")
					  	 .append("and \"tc\".\"sezione\" IN (:sezioni) ");
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("praticaId", praticaId);
		parameters.put("sezioni", (sezioni!=null && sezioni.isEmpty()) ? null : sezioni);
		logger.info("Eseguo la query {} con i parametri {}", sql.toString(), parameters);
		return namedQueryForList(sql.toString(), rowMapper, parameters);
	}
	
	public List<AllegatiDTO> searchAllegatiRichiesteASR(Long idRichiesta) throws Exception
	{
		StringBuilder sql = new StringBuilder(selectAll)
							.append(" left join \"presentazione_istanza\".\"allegato_sosp_arc_att\"")
							.append(" on \"id\" = \"id_allegato\"")
							.append(" where \"id_sosp_arc_att\" = :id_richiesta");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id_richiesta", idRichiesta);
		logger.info("Eseguo la query {} con i parametri {}", sql.toString(), parameters);
		return namedQueryForList(sql.toString(), rowMapper, parameters);
	}
	
	public List<SimpleAllegatiSedutaDTO> searchAllegatiSeduta(UUID idPratica) throws Exception
	{
		String sql = StringUtil.concateneString("select \"allegati\".*, \"seduta_commissione\".\"id\" as id_seduta",
												" from \"presentazione_istanza\".\"allegati\"",
												" join \"presentazione_istanza\".\"seduta_pratica_allegati\" as spa",
												" on \"id_allegato\" = \"id\"",
												" join \"presentazione_istanza\".\"seduta_commissione\"",
												" on \"id_seduta\" = \"seduta_commissione\".\"id\"",
												" where spa.\"id_pratica\" = :id_pratica",
												" and \"seduta_commissione\".\"stato\" = 'CONCLUSA'");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id_pratica", idPratica);
		logger.info("Eseguo la query {} con i parametri {}", sql.toString(), parameters);
		return namedQueryForList(sql, new RowMapper<SimpleAllegatiSedutaDTO>()
		{

			@Override
			public SimpleAllegatiSedutaDTO mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				SimpleAllegatiSedutaDTO result = null;
				if(rs != null)
				{
					result = new SimpleAllegatiSedutaDTO(rowMapper.mapRow(rs, rowNum));
					result.setIdSeduta(rs.getLong("id_seduta"));
				}
				return result;
			}
		}, parameters);
	}
	
	public List<AllegatiDTO> findAllegatiParere(Long idParere) throws Exception
	{
		String sql = StringUtil.concateneString(selectAll, 
												" join \"presentazione_istanza\".\"parere_soprintendenza_allegato\" as psa",
												" on a.\"id\" = psa.\"id_allegato\"",
												" where psa.\"id_parere\" = :id_parere");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id_parere", idParere);
		logger.info("Eseguo la query {} con i parametri {}", sql.toString(), parameters);
		return namedJdbcTemplateRead.query(sql, parameters, rowMapper);
	}

	public int insertVisibilitaUlerioreDoc(UUID idAllegato, String ente) {
		 final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"allegato_ente\"")
                 .append("(\"id_allegato\"").append(",\"codice\"").append(") values ")
                 .append("(?").append(",?)").toString();
	        final List<Object> parameters = new ArrayList<Object>();
	        parameters.add(idAllegato);
	        parameters.add(ente);
	       
	        return super.update(sql, parameters);
	}
	
	/**
	 * cancellazione logica degli allegati che al cambio di tipo procedimento non sono ammessi nel nuovo tipoProcedimento
	 * @author acolaianni
	 *
	 * @param oldTipoProcedimento
	 * @param newTipoProcedimento
	 * @return
	 */
	public int rimuoviAllegatiNonAmmessi(UUID praticaId,int oldTipoProcedimento,int newTipoProcedimento) {
		final StringBuilder sql = new StringBuilder();
		sql.append(StringUtil.concateneString(
				" update \"presentazione_istanza\".\"allegati\" set deleted = true where ", 
				" allegati.id  in ",
				" ( ",
				" select a.id from \"presentazione_istanza\".\"allegati_tipo_contenuto\" atc,\"presentazione_istanza\".\"allegati\" a where ", 
				" atc.allegati_id =a.id  and a.pratica_id = ? ", 
				" and atc.tipo_contenuto_id in( ",
				"select pc.tipo_contenuto_id as contDaRimuovere ", " from \"presentazione_istanza\".\"procedimento_contenuto\" pc  ",
				" where pc.tipo_procedimento = ?  and ", " pc.tipo_contenuto_id  not in ",
				" (select pc.tipo_contenuto_id as idContAmmesso ", "  from \"presentazione_istanza\".\"procedimento_contenuto\" pc  ",
				"  where pc.tipo_procedimento = ? ) ",
				"))"));
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(praticaId);
        parameters.add(oldTipoProcedimento);
        parameters.add(newTipoProcedimento);
        return super.update(sql.toString(), parameters);
	}

	/**
	 * 
	 * @author acolaianni
	 *
	 * @param id chiave primaria allegato
	 * @param idDiogeneScheduler  id_diogene_scheduler
	 */
	public int updateAttachmentDiogeneData(UUID id, UUID idDiogeneScheduler) {
		final StringBuilder sql = new StringBuilder();
		sql.append(StringUtil.concateneString(
		" update \"presentazione_istanza\".\"allegati\" set id_diogene_scheduler = ? where ", 
		" allegati.id = ? "
		));
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(id);
        parameters.add(idDiogeneScheduler);
        return super.update(sql.toString(), parameters);
	}
	
	/**
	 * estrazione senza i check di ownership degli allegati documento referenti
	 * @author acolaianni
	 *
	 * @param idPratica
	 * @return
	 * @throws Exception
	 */
	public List<AllegatiDTO> findDocumentiReferentiForDiogene(UUID idPratica) throws Exception
	{
		String sql = StringUtil.concateneString(selectAll,
				", \"presentazione_istanza\".\"pratica\" p ,",
				" \"presentazione_istanza\".\"referenti\" r ,",
				" \"presentazione_istanza\".\"referenti_documento\" rd ", 
				" WHERE p.id =r.pratica_id ", 
				" and rd.referente_id =r.id ",
				" and a.id =rd.allegato_id ",
				" and a.id_diogene_scheduler is null ", 
				" and p.id = :idPratica ",
				" and a.deleted = false ");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idPratica", idPratica);
		logger.info("Eseguo la query {} con i parametri {}", sql.toString(), parameters);
		return namedJdbcTemplateRead.query(sql, parameters, rowMapper);
	}

	/**
	 * allegati corrispondenza senza alcun controllo
	 * @author acolaianni
	 *
	 * @param idCorrispondenza
	 * @return
	 * @throws Exception
	 */
	public List<AllegatiDTO> findAllegatiCorrispondenzaForDiogene(UUID idPratica) throws Exception
	{
		String sql = StringUtil.concateneString(selectAll,
		" , \"presentazione_istanza\".\"allegato_corrispondenza\" ac ," , 
		" \"presentazione_istanza\".\"corrispondenza\" c ,", 
		" \"presentazione_istanza\".\"fascicolo_corrispondenza\" fc  ", 
		" where ", 
		" a.id =ac.id_allegato ", 
		" and ac.id_corrispondenza =c.id ", 
		" and fc.id_corrispondenza =c.id ", 
		" and a.deleted =false ", 
		" and c.bozza =false ", 
		" and a.id_diogene_scheduler is null ", 
		" and fc.id_pratica = ? ");
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(idPratica);
		return queryForList(sql, rowMapper, parameters);
	}
	
	/**
	 * non effettua controllo su tipi_contenuti ammessi per tipo_procedimento
	 * @author acolaianni
	 *
	 * @param id
	 * @param sezione
	 * @return
	 */
	public List<AllegatiDTO> findByPraticaAndSezioneForDiogene(UUID id, SezioneAllegati sezione)
	{
		final List<Object> parameters = new ArrayList<Object>();
		StringBuilder sb=new StringBuilder(selectAll);
		sb.append(", \"presentazione_istanza\".\"allegati_tipo_contenuto\" atc ")
		  .append(", \"presentazione_istanza\".\"tipo_contenuto\" tc ")
		  .append("where a.\"pratica_id\" = ? ")
		  .append("and a.\"id\" = atc.\"allegati_id\"  ")
		  .append("and tc.\"sezione\"= ? ")
		  .append("and tc.\"id\"=  atc.\"tipo_contenuto_id\" ")
		  .append("and a.\"deleted\" = false ")
		  .append("and a.\"id_diogene_scheduler\" is null ") ;
		parameters.add(id);
        parameters.add(sezione.toString());
        sb.append(" and (a.\"integrazione_id\" is null or ")
	     	  .append("(select count(*) from \"presentazione_istanza\".\"integrazione\" ")
	     	  .append("where \"integrazione\".\"id\" = a.\"integrazione_id\" and \"stato\" = 'COMPLETATA') > 0")
	     	  .append(")");
		sb.append(" group by a.\"id\" ");
        List<AllegatiDTO> ret = super.queryForListTxRead(sb.toString(), this.rowMapper, parameters);
        return ret; 
	}
	
	/**
	 * 
	 * @author acolaianni
	 *
	 * @param id
	 * @param descrizione
	 * @param titolo
	 * @return
	 */
	public int updateDescrizioneTitolo(UUID id, String descrizione,String titolo) {
		final StringBuilder sql = new StringBuilder();
		sql.append(StringUtil.concateneString(
		" update \"presentazione_istanza\".\"allegati\" "
		,"set descrizione = ? "
		," ,titolo = ?"
		," where " 
		," allegati.id = ? "
		));
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(descrizione);
		parameters.add(titolo);
        parameters.add(id);
        return super.update(sql.toString(), parameters);
	}
	
}
