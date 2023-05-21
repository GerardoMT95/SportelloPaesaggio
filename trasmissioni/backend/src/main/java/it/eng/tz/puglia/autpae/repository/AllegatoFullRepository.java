package it.eng.tz.puglia.autpae.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.Allegato;
import it.eng.tz.puglia.autpae.dbMapping.AllegatoCorrispondenza;
import it.eng.tz.puglia.autpae.dbMapping.AllegatoEnte;
import it.eng.tz.puglia.autpae.dbMapping.AllegatoFascicolo;
import it.eng.tz.puglia.autpae.dbMapping.Destinatario;
import it.eng.tz.puglia.autpae.dto.AllegatoCustomDTO;
import it.eng.tz.puglia.autpae.dto.TipologicaNumeriDTO;
import it.eng.tz.puglia.autpae.entity.AllegatoDTO;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.repository.base.AllegatoBaseRepository;
import it.eng.tz.puglia.autpae.rowmapper.AllegatoCustomRowMapper;
import it.eng.tz.puglia.autpae.rowmapper.AllegatoRowMapper;
import it.eng.tz.puglia.autpae.search.AllegatoSearch;
import it.eng.tz.puglia.autpae.search.UlterioreDocumentazioneSearch;
import it.eng.tz.puglia.autpae.utility.Stringhe;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.UserUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Full Repository for Allegato
 */
@Repository
public class AllegatoFullRepository extends AllegatoBaseRepository
{
	private static final Logger log = LoggerFactory.getLogger(AllegatoFullRepository.class);
	
	
	private static final AllegatoRowMapper allegatoRowMapper=new AllegatoRowMapper();
	
/*	public Long findIdByIdAlfresco(String idAlfresco) throws Exception {
//		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
//
//		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select "));
//		sql .append(Allegato.id.getCompleteName())
//			.append(" from ")
//			.append(Allegato.getTableName())
//			.append(" where ")
//			.append(Allegato.contenuto.getCompleteName())
//			.append(" = :")
//			.append(Allegato.contenuto.columnName());
//
//		Map<String, Object> parameters = new HashMap<String, Object>();
//		parameters.put(Allegato.contenuto.columnName(), idAlfresco);
//		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
//		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, Long.class);
//	}	*/

	public String findIdAlfrescoById(Long id) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select "));
		sql .append(Allegato.contenuto.getCompleteName())
			.append(" from ")
			.append(Allegato.getTableName())
			.append(" where ")
			.append(Allegato.id.getCompleteName())
			.append(" = :")
			.append(Allegato.id.columnName());

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Allegato.id.columnName(), id);
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, String.class);
	}

	public List<AllegatoDTO> datiAllegato(Long idFascicolo, List<TipoAllegato> listaTipi) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql  = StringUtil.concateneString("select "
												, AllegatoFascicolo.type.columnName()
												,",", Allegato.id.getCompleteName()
												,",", Allegato.nome.columnName()
												,",", Allegato.contenuto.columnName()
												,",", Allegato.data_caricamento.columnName()
												,",", Allegato.mime_type.columnName()
												,",", Allegato.dimensione.columnName()
												,",", Allegato.descrizione.columnName()
												,",", Allegato.titolo.columnName()
												,",", Allegato.numero_protocollo_in.columnName()
												,",", Allegato.numero_protocollo_out.columnName()
												,",", Allegato.utente_inserimento.columnName()
												,",", Allegato.username_inserimento.columnName()
												,",", Allegato.checksum.columnName()
												, " from "
												,Allegato.getTableName()
												," join " 
												,AllegatoFascicolo.getTableName()
												," on "
												, Allegato.id.getCompleteName()
												, " = "
												, AllegatoFascicolo.id_allegato.getCompleteName()
												, " where "
												, AllegatoFascicolo.id_fascicolo.getCompleteName()
												, " = :"
												, AllegatoFascicolo.id_fascicolo.columnName()
												, " and "
												, AllegatoFascicolo.type.getCompleteName()
												, " in (:listaTipi)"
												//        	, " ORDER BY "
												//         	, Allegato.id.getCompleteName()						// necessario per simulare l'ordine cronologico
												);

		List<String> listaTipi_String = new ArrayList<>(listaTipi.size());
		listaTipi.forEach(elem->{
			listaTipi_String.add(elem.name());
		});
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(AllegatoFascicolo.id_fascicolo.columnName(), idFascicolo);
		parameters.put("listaTipi", (listaTipi_String!=null && listaTipi_String.isEmpty()) ? null : listaTipi_String);

		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return super.namedQueryForList(sql.toString(), new RowMapper<AllegatoDTO>()
		{
			@Override
			public AllegatoDTO mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				AllegatoDTO dto = null;
				if(rs != null)
				{
					dto = new AllegatoDTO();
					dto.setTipo(rs.getObject(AllegatoFascicolo.type.columnName()) != null ? TipoAllegato.valueOf(rs.getString(AllegatoFascicolo.type.columnName())) : null);
					dto.setId(rs.getObject(Allegato.id.columnName()) != null ? rs.getLong(Allegato.id.columnName()) : null);
					dto.setNome(rs.getObject(Allegato.nome.columnName()) != null ? rs.getString(Allegato.nome.columnName()) : null);
					dto.setContenuto(rs.getObject(Allegato.contenuto.columnName()) != null ? rs.getString(Allegato.contenuto.columnName()) : null);
					dto.setDataCaricamento(Date.from((rs.getObject(Allegato.data_caricamento.columnName(), LocalDateTime.class)).atZone(ZoneId.systemDefault()).toInstant()));
					dto.setMimeType(rs.getObject(Allegato.mime_type.columnName()) != null ? rs.getString(Allegato.mime_type.columnName()) : null);
					dto.setDimensione(rs.getObject(Allegato.dimensione.columnName()) != null ? rs.getInt(Allegato.dimensione.columnName()) : null);
					dto.setDescrizione(rs.getObject(Allegato.descrizione.columnName()) != null ? rs.getString(Allegato.descrizione.columnName()) : null);
					dto.setTitolo(rs.getObject(Allegato.titolo.columnName()) != null ? rs.getString(Allegato.titolo.columnName()) : null);
					dto.setNumeroProtocolloIn(rs.getObject(Allegato.numero_protocollo_in.columnName()) != null ? rs.getString(Allegato.numero_protocollo_in.columnName()) : null);
					dto.setNumeroProtocolloOut(rs.getObject(Allegato.numero_protocollo_out.columnName()) != null ? rs.getString(Allegato.numero_protocollo_out.columnName()) : null);
					dto.setUtenteInserimento(rs.getObject(Allegato.utente_inserimento.columnName()) != null ? rs.getString(Allegato.utente_inserimento.columnName()) : null);
					dto.setUsernameInserimento(rs.getObject(Allegato.username_inserimento.columnName()) != null ? rs.getString(Allegato.username_inserimento.columnName()) : null);
					dto.setChecksum(rs.getObject(Allegato.checksum.columnName()) != null ? rs.getString(Allegato.checksum.columnName()) : null);
				}
				return dto;
			}
		},parameters);
	}

/*	public int aggiorna(long idAllegato, String descrizione, String titolo) throws Exception {
//		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
//
//		String sql = StringUtil.concateneString ("update "+Allegato.getTableName()+" set "
//												, Allegato.descrizione.columnName()
//												," = :"   , Allegato.descrizione.columnName()
//												,","      , Allegato.titolo.columnName()
//												," = :"   , Allegato.titolo.columnName()
//												," where ", Allegato.getTableName(),".",Allegato.id.columnName()
//												," = :"   , Allegato.id.columnName());
//
//		SqlParameterSource parameters = new MapSqlParameterSource()
//				.addValue(Allegato.id.columnName(), idAllegato)
//				.addValue(Allegato.descrizione.columnName(), descrizione)
//				.addValue(Allegato.titolo.columnName(), titolo);
//
//		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
//		return namedJdbcTemplate.update(sql.toString(), parameters);
//	}	*/

	public int protocolla(long idAllegato, String protocolloIn, String protocolloOut) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("update " + Allegato.getTableName() + " set "));

		if (protocolloIn !=null && !protocolloIn .isEmpty()) {
			sql .append(Allegato.numero_protocollo_in .columnName()) 
				.append(" = :")
				.append(Allegato.numero_protocollo_in .columnName())
				.append(",")
				.append(Allegato.data_protocollo_in   .columnName()) 
				.append(" = :")  
				.append(Allegato.data_protocollo_in   .columnName());
		}

		if (protocolloIn !=null && !protocolloIn .isEmpty() && protocolloOut!=null && !protocolloOut.isEmpty()) {
			sql.append(" , "); 
		}

		if (protocolloOut!=null && !protocolloOut.isEmpty()) {
			sql .append(Allegato.numero_protocollo_out.columnName()) 
				.append(" = :")
				.append(Allegato.numero_protocollo_out.columnName())
				.append(",")
				.append(Allegato.data_protocollo_out  .columnName()) 
				.append(" = :")  
				.append(Allegato.data_protocollo_out  .columnName());
		}

		sql.append(" where ")
		.append(Allegato.id.getCompleteName())
		.append(" = :")
		.append(Allegato.id.columnName());

		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(Allegato.numero_protocollo_in .columnName(), protocolloIn)
				.addValue(Allegato.numero_protocollo_out.columnName(), protocolloOut)
				.addValue(Allegato.data_protocollo_in   .columnName(), new Date())
				.addValue(Allegato.data_protocollo_out  .columnName(), new Date())
				.addValue(Allegato.id                   .columnName(), idAllegato);

		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.update(sql.toString(), parameters);
	}

	@Deprecated
	public List<TipologicaNumeriDTO> nomeAllegatiCorrispondenza(Long idCorrispondenza) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql  = StringUtil.concateneString("select "
												, Allegato.id.columnName()
												,",", Allegato.nome.columnName()
												, " from "
												, Allegato.getTableName()
												,",", AllegatoCorrispondenza.getTableName()
												, " where "
												, AllegatoCorrispondenza.id_allegato.getCompleteName()
												, " = "
												, Allegato.id.getCompleteName()
												, " and "
												, AllegatoCorrispondenza.id_corrispondenza.getCompleteName()
												, " = :"
												, AllegatoCorrispondenza.id_corrispondenza.columnName()
												);

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(AllegatoCorrispondenza.id_corrispondenza.columnName(), idCorrispondenza);

		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return super.namedQueryForList(sql.toString(), new RowMapper<TipologicaNumeriDTO>()
		{
			@Override
			public TipologicaNumeriDTO mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				TipologicaNumeriDTO dto = null;
				if(rs != null)
				{
					dto = new TipologicaNumeriDTO();
					dto.setLabel (rs.getString(Allegato.nome.columnName()));
					dto.setCodice(rs.getLong  (Allegato.id  .columnName()));
				}
				return dto;
			}
		},parameters);
	}

	public List<AllegatoCustomDTO> infoAllegatiCorrispondenza(Long idCorrispondenza) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql  = StringUtil.concateneString("select "
												, Allegato.id.columnName()
												,",", Allegato.nome.columnName()
												,",", Allegato.numero_protocollo_in.columnName()
												,",", Allegato.numero_protocollo_out.columnName()
												,",", Allegato.data_protocollo_in.columnName()
												,",", Allegato.data_protocollo_out.columnName()
												,",", Allegato.dimensione.columnName()
												,",", Allegato.mime_type.columnName()
												,",", Allegato.data_caricamento.columnName()
												,",", Allegato.checksum.columnName()
												,",", AllegatoCorrispondenza.is_url.columnName()
												, " from "
												, Allegato.getTableName()
												,",", AllegatoCorrispondenza.getTableName()
												, " where "
												, AllegatoCorrispondenza.id_allegato.getCompleteName()
												, " = "
												, Allegato.id.getCompleteName()
												, " and "
												, AllegatoCorrispondenza.id_corrispondenza.getCompleteName()
												, " = :"
												, AllegatoCorrispondenza.id_corrispondenza.columnName()
												);

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(AllegatoCorrispondenza.id_corrispondenza.columnName(), idCorrispondenza);

		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return super.namedQueryForList(sql.toString(), new AllegatoCustomRowMapper(), parameters);
	}

	public TipologicaNumeriDTO cercaRecente(AllegatoSearch filter, List<Long> listaId) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select ",
																		 Allegato.id.columnName(),					  " as ", "codice",
																		 " , ",
																		 Allegato.numero_protocollo_out.columnName(), " as ", "label" ,
																		 " from ",
																		 Allegato.getTableName()));
		filter.getSqlWhereClause(sql);

		String listaString = null;
		if(listaId!=null) {
			listaString = listaId.toString();
			listaString = listaString.replaceAll("\\[", "");
			listaString = listaString.replaceAll("\\]", "");
		}
		
		sql .append(" and ")
			.append(Allegato.id.columnName())
			.append(" IN ")
			.append("(")
			.append((listaString!=null && listaString.isEmpty()) ? null : listaString)
			.append(") ");	   

		filter.getSqlOrderByClause(sql);

		log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
		List<TipologicaNumeriDTO> ret = super.namedQueryForList(sql.toString(), new RowMapper<TipologicaNumeriDTO>()
		{
			@Override
			public TipologicaNumeriDTO mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				TipologicaNumeriDTO out = new TipologicaNumeriDTO();
				if(rs != null)
					out.setCodice(rs.getLong("codice"));
				out.setLabel(rs.getString("label"));
				return out;
			}
		},filter.getSqlParameters());
		return (ret!=null?ret.get(0):null);
	}
	
/*	public PaginatedList<UlterioreDocumentazioneDTO> searchUlterioreDocumentazione(UlterioreDocumentazioneSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
	
		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select "));
		
		sql					 .append(Allegato.id.getCompleteName())
		   .append(", ")	 .append(Allegato.nome.getCompleteName())
		   .append(", ")	 .append(Allegato.titolo.getCompleteName())
		   .append(", ")	 .append(Allegato.descrizione.getCompleteName())
		   .append(", ")	 .append(Allegato.utente_inserimento.getCompleteName())
		   .append(", ")	 .append(Allegato.username_inserimento.getCompleteName())
		   .append(", ") 	 .append(Allegato.data_caricamento.getCompleteName())
		   .append(", ") 	 .append(Allegato.mime_type.getCompleteName())				// non serve al FE, li ha chiesti Michele
		   .append(", ") 	 .append(Allegato.dimensione.getCompleteName());			// non serve al FE, li ha chiesti Michele
	  //   .append(", ")	 .append(Destinatario.email.getCompleteName())
	  //   .append(", ")	 .append(AllegatoEnte.codice.getCompleteName())
		
		sql.append(" FROM ") .append(Allegato.getTableName())
		   .append(", ") 	 .append(AllegatoEnte.getTableName())
		   .append(", ") 	 .append(AllegatoCorrispondenza.getTableName())
		   .append(", ") 	 .append(AllegatoFascicolo.getTableName())
		   .append(", ") 	 .append(Destinatario.getTableName());
		
		sql.append(" WHERE ")
		   .append(Allegato			  	 .id			   .getCompleteName()) .append(" = ") .append(AllegatoFascicolo     .id			   	  .getCompleteName()) .append("AND")
		   .append(Allegato			  	 .id			   .getCompleteName()) .append(" = ") .append(AllegatoEnte          .id_allegato	  .getCompleteName()) .append("AND")
		   .append(Allegato			  	 .id			   .getCompleteName()) .append(" = ") .append(AllegatoCorrispondenza.id_allegato	  .getCompleteName()) .append("AND")
		   .append(AllegatoCorrispondenza.id_corrispondenza.getCompleteName()) .append(" = ") .append(Destinatario			.id_corrispondenza.getCompleteName()) .append("AND")
		   .append(AllegatoFascicolo     .id_fascicolo	   .getCompleteName()) .append(" = ") .append(":idFascicolo");
			
			filter.getSqlWhereClause(sql);
			filter.getSqlOrderByClause(sql);
			
			log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
			return super.paginatedList(sql.toString(), filter.getSqlParameters(), rowMapper, filter.getPage(), filter.getLimit());
	}	 */
	
	public PaginatedList<Long> searchIdDocumentiUD(UlterioreDocumentazioneSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
	
		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select distinct "));
		
		sql.append(Allegato.id.getCompleteName());
		if(filter.getOrderByFields()!=null) {
			filter.getOrderByFields().stream().forEach(field->sql.append(",").append(field));
		}
		
		sql.append(" FROM ")
		   .append(Allegato.getTableName())
		   .append(" left join ")
		   .append(AllegatoEnte.getTableName())
		   .append(" on ")
		   .append(Allegato.id.getCompleteName() + " = " + AllegatoEnte.id_allegato.getCompleteName())
		   .append(", ")
		   .append(AllegatoCorrispondenza.getTableName())
		   .append(" left join ")
		   .append(Destinatario.getTableName())
		   .append(" on ")
		   .append(AllegatoCorrispondenza.id_corrispondenza.getCompleteName() + " = " + Destinatario.id_corrispondenza.getCompleteName())
		   .append(", ")
		   .append(AllegatoFascicolo.getTableName());
		
		sql.append(" WHERE ")
		   .append(Allegato			.id	 .getCompleteName()) .append(" = ") .append(AllegatoFascicolo     .id_allegato.getCompleteName())
		   .append(" AND ")
		   .append(Allegato			.id	 .getCompleteName()) .append(" = ") .append(AllegatoCorrispondenza.id_allegato.getCompleteName())  // forse andrebbe in left join
		   .append(" AND ")
		   .append(AllegatoFascicolo.type.getCompleteName()) .append(" = ") .append(Stringhe.apicizza(TipoAllegato.ULTERIORE_DOCUMENTAZIONE.name()));
			
		filter.getSqlWhereClause(sql);
		
//		if (userUtil.getGruppo()!=Gruppi.ADMIN) {
//		sql.append(" AND ")
//		   .append(AllegatoEnte   .codice.getCompleteName()) .append(" = ") .append(Stringhe.apicizza(userUtil.getGruppo().name()));
//		}
		//già gestito dal service
		filter.getSqlOrderByClause(sql);

		log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
		return super.paginatedList(sql.toString(), filter.getSqlParameters(), new RowMapper<Long>()
					{
						@Override
						public Long mapRow(ResultSet rs, int rowNum) throws SQLException
						{
							Long id = null;
							if(rs != null)
								id = rs.getLong(Allegato.id.columnName());
							return id;
						}
					}, filter.getPage(), filter.getLimit());
	}
	
	
	public List<AllegatoDTO> datiAllegatoRichiestaPostTrasmissione(Long idFascicolo, Long idRichiestaPostTrasmissione) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql  = StringUtil.concateneString("select "
												,Allegato.getTableName()
												,".* "
												, " from "
												,Allegato.getTableName()
												," join " 
												,AllegatoFascicolo.getTableName()
												," on "
												, Allegato.id.getCompleteName()
												, " = "
												, AllegatoFascicolo.id_allegato.getCompleteName()
												, " where "
												, AllegatoFascicolo.id_fascicolo.getCompleteName()
												, " = :"
												, AllegatoFascicolo.id_fascicolo.columnName()
												, " and "
												, Allegato.id_richiesta_post_trasmissione.getCompleteName()
												, " = :"
												, Allegato.id_richiesta_post_trasmissione.columnName()
												);

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(AllegatoFascicolo.id_fascicolo.columnName(), idFascicolo);
		parameters.put(Allegato.id_richiesta_post_trasmissione.columnName(), idRichiestaPostTrasmissione);
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return super.namedQueryForList(sql.toString(), allegatoRowMapper,parameters);
	}

	public List<AllegatoDTO> datiAllegatoAnnotazioniInterne(Long idFascicolo, Long idAnnotazioneInterna) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql  = StringUtil.concateneString("select "
												,Allegato.getTableName()
												,".* "
												, " from "
												,Allegato.getTableName()
												," join " 
												,AllegatoFascicolo.getTableName()
												," on "
												, Allegato.id.getCompleteName()
												, " = "
												, AllegatoFascicolo.id_allegato.getCompleteName()
												, " where "
												, AllegatoFascicolo.id_fascicolo.getCompleteName()
												, " = :"
												, AllegatoFascicolo.id_fascicolo.columnName()
												, " and "
												, Allegato.id_annotazione_interna.getCompleteName()
												, " = :"
												, Allegato.id_annotazione_interna.columnName()
												);

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(AllegatoFascicolo.id_fascicolo.columnName(), idFascicolo);
		parameters.put(Allegato.id_annotazione_interna.columnName(), idAnnotazioneInterna);
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return super.namedQueryForList(sql.toString(), allegatoRowMapper,parameters);
	}
	
	/**
	 * aggiornamento appena inviato messaggio a microservizio di inoltro
	 * @autor Adriano Colaianni
	 * @date 19 ott 2021
	 * @param id
	 * @param idDiogene
	 * @param path
	 * @param pathReale
	 * @return
	 */
	public int updateSendDiogene(String id) {
		String sql  = StringUtil.concateneString(
				"update "
				,Allegato.getTableName()
				," set "
				,Allegato.last_send_diogene.columnName()
				, " = now() "
				, " where "
				, Allegato.id.columnName()
				, "::text = :"
				, Allegato.id.columnName());
				
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Allegato.id.columnName(), id);
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return super.namedUpdate(sql.toString(), parameters);
	}
	
	/**
	 * aggiornamento alla ricezione della notifica post sincronizzazione dal microservizio di inoltro
	 * @autor Adriano Colaianni
	 * @date 19 ott 2021
	 * @param id
	 * @param idDiogene
	 * @param path
	 * @param pathReale
	 * @return
	 */
	public int updateReceiveDiogene(String id,String idDiogene,String pathDiogene,String pathRealeDiogene) {
		String sql  = StringUtil.concateneString(
				"update "
				,Allegato.getTableName()
				," set "
				, Allegato.id_diogene.columnName()
				, " = :"
				, Allegato.id_diogene.columnName()
				,","
				,Allegato.last_update_diogene.columnName()
				, " = now() "
				,","
				,Allegato.path_diogene.columnName()
				, " = :"
				,Allegato.path_diogene.columnName()
				,","
				,Allegato.path_reale_diogene.columnName()
				, " = :"
				,Allegato.path_reale_diogene.columnName()
				, " where "
				, Allegato.id.columnName()
				, "::text = :"
				, Allegato.id.columnName());
				
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Allegato.id.columnName(), id);
		parameters.put(Allegato.id_diogene.columnName(), idDiogene);
		parameters.put(Allegato.path_diogene.columnName(), pathDiogene);
		parameters.put(Allegato.path_reale_diogene.columnName(), pathRealeDiogene);
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return super.namedUpdate(sql.toString(), parameters);
	}
	
}