package it.eng.tz.puglia.autpae.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.autpae.dbMapping.Ricevuta;
import it.eng.tz.puglia.autpae.entity.RicevutaDTO;
import it.eng.tz.puglia.autpae.repository.base.RicevutaBaseRepository;
import it.eng.tz.puglia.autpae.rowmapper.RicevutaRowMapper;
import it.eng.tz.puglia.autpae.search.RicevutaSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Full Repository for table ricevuta
 */
@Repository
public class RicevutaFullRepository  extends RicevutaBaseRepository
{
	private static final Logger log = LoggerFactory.getLogger(RicevutaFullRepository.class);
    private final RicevutaRowMapper rowMapper = new RicevutaRowMapper();
   
	
	public PaginatedList<RicevutaDTO> getRicevuteCorrispondenza(RicevutaSearch filtro) throws Exception { // FIXME non posso mettere in input solo Long idCorrispondenza a causa della paginazione!
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(Ricevuta.getTableName());
		
		sql.append(" WHERE ")
		   .append(Ricevuta.id_corrispondenza.columnName())
		   .append(" =: ")
		   .append(Ricevuta.id_corrispondenza.columnName())
		   .append(" AND ")
		   .append(Ricevuta.id_destinatario.columnName())
		   .append(" IS NULL");
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Ricevuta.id_corrispondenza.columnName(), filtro.getIdCorrispondenza());
				
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return super.paginatedList(sql.toString(),  parameters, rowMapper, filtro.getPage(), filtro.getLimit());
	}
	
	public int insertMultipla(List<RicevutaDTO> listRicevutaDTO) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		if(ListUtil.isEmpty(listRicevutaDTO))
			return 0;
		StringBuilder sql = new StringBuilder();
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		sql.append("insert into ")
		   .append(Ricevuta.getTableName())
		   .append(" ( ")
		   .append(Ricevuta.id_corrispondenza.columnName())  .append(", ")
		   .append(Ricevuta.id_destinatario.columnName())	 .append(", ")
		   .append(Ricevuta.tipo_ricevuta.columnName())		 .append(", ")
		   .append(Ricevuta.errore.columnName())			 .append(", ")
		   .append(Ricevuta.descrizione_errore.columnName()) .append(", ")
		   .append(Ricevuta.id_cms_eml.columnName())		 .append(", ")
		   .append(Ricevuta.id_cms_dati_cert.columnName())	 .append(", ")
		   .append(Ricevuta.id_cms_smime.columnName())		 .append(", ")
		   .append(Ricevuta.data.columnName()).append(", ")
		   .append(Ricevuta.id_allegato_dati_cert.columnName())
		   .append(" ) ")
		   .append(" values ");
		for (int i = 0; i < listRicevutaDTO.size(); i++) {
			RicevutaDTO ricevutaDTO = listRicevutaDTO.get(i);
			sql.append(" ( ")
			   .append(":").append(Ricevuta.id_corrispondenza.columnName()  + i) .append(", ")
			   .append(":").append(Ricevuta.id_destinatario.columnName() 	+ i) .append(", ")
			   .append(":").append(Ricevuta.tipo_ricevuta.columnName() 		+ i) .append(", ")
			   .append(":").append(Ricevuta.errore.columnName() 			+ i) .append(", ")
			   .append(":").append(Ricevuta.descrizione_errore.columnName() + i) .append(", ")
			   .append(":").append(Ricevuta.id_cms_eml.columnName() 		+ i) .append(", ")
			   .append(":").append(Ricevuta.id_cms_dati_cert.columnName()   + i) .append(", ")
			   .append(":").append(Ricevuta.id_cms_smime.columnName() 		+ i) .append(", ")
			   .append(":").append(Ricevuta.data.columnName() 				+ i) .append(", ")
			   .append(":").append(Ricevuta.id_allegato_dati_cert.columnName() + i)
			   .append(" ) ");
			if ((i + 1) < listRicevutaDTO.size()) {
				sql.append(", ");
			}
			parameters.put(Ricevuta.id_corrispondenza.columnName()  + i, ricevutaDTO.getIdCorrispondenza());
			parameters.put(Ricevuta.id_destinatario.columnName()    + i, ricevutaDTO.getIdDestinatario());
			parameters.put(Ricevuta.tipo_ricevuta.columnName() 		+ i, ricevutaDTO.getTipoRicevuta().name());
			parameters.put(Ricevuta.errore.columnName() 			+ i, ricevutaDTO.getErrore().name());
			parameters.put(Ricevuta.descrizione_errore.columnName() + i, ricevutaDTO.getDescrizioneErrore());
			parameters.put(Ricevuta.id_cms_eml.columnName() 		+ i, ricevutaDTO.getIdCmsEml());
			parameters.put(Ricevuta.id_cms_dati_cert.columnName()   + i, ricevutaDTO.getIdCmsDatiCert());
			parameters.put(Ricevuta.id_cms_smime.columnName() 		+ i, ricevutaDTO.getIdCmsSmime());
			parameters.put(Ricevuta.data.columnName() 				+ i, ricevutaDTO.getData());
			parameters.put(Ricevuta.id_allegato_dati_cert.columnName() + i, ricevutaDTO.getIdAllegatoDatiCert());
		}
		
		log.trace("Sql -> {} Parameters -> {}", sql, parameters.toString());
		return namedJdbcTemplate.update(sql.toString(), parameters);
	}

}