package it.eng.tz.puglia.autpae.repository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.PlaceholderDoc;
import it.eng.tz.puglia.autpae.dbMapping.PlaceholderDocSezione;
import it.eng.tz.puglia.autpae.dbMapping.TemplateDoc;
import it.eng.tz.puglia.autpae.dbMapping.TemplateDocSezioni;
import it.eng.tz.puglia.autpae.dbMapping.TemplateDocSezioniDefault;
import it.eng.tz.puglia.autpae.entity.PlaceholderDTO;
import it.eng.tz.puglia.autpae.entity.TemplateDocSezioneDTO;
import it.eng.tz.puglia.autpae.repository.base.TemplateDocBaseRepository;
import it.eng.tz.puglia.autpae.rowmapper.PlaceholderDocRowMapper;
import it.eng.tz.puglia.autpae.rowmapper.TemplateDocSezioniRowMapper;

/**
 * Full Repository for table template_doc e altre tabelle collegate
 */
@Repository
public class TemplateDocFullRepository extends TemplateDocBaseRepository {
	private static final Logger log = LoggerFactory.getLogger(TemplateDocFullRepository.class);
	final TemplateDocSezioniRowMapper rowMapper = new TemplateDocSezioniRowMapper();
	final PlaceholderDocRowMapper rowMapperPlaceholder = new PlaceholderDocRowMapper();

	public List<TemplateDocSezioneDTO> getSezioniTemplate(String nome) {
		log.trace("Entrato nel repository: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");
		StringBuilder sql = new StringBuilder("SELECT ");
		sql.append(TemplateDocSezioni.nome.getCompleteName());
		sql.append("," + TemplateDocSezioni.id_allegato.getCompleteName());
		sql.append("," + TemplateDocSezioni.template_doc_nome.getCompleteName());
		sql.append("," + TemplateDocSezioni.valore.getCompleteName());
		sql.append("," + TemplateDocSezioni.tipo_sezione.getCompleteName());
		sql.append(" FROM ").append(TemplateDocSezioni.getTableName()).append(",").append(TemplateDoc.getTableName())
				.append(" WHERE ").append(TemplateDoc.nome.getCompleteName()).append(" = ")
				.append(TemplateDocSezioni.template_doc_nome.getCompleteName()).append(" AND ")
				.append(TemplateDoc.nome.getCompleteName()).append(" = ? ")
				.append(" ORDER BY  ")
				.append(TemplateDocSezioni.nome.getCompleteName());
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(nome);
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return super.queryForList(sql.toString(), this.rowMapper, parameters);
	}

	public List<PlaceholderDTO> getPlaceholderSezione(String nomeTemplate, String nomeSezione) {
		log.trace("Entrato nel repository: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");
		StringBuilder sql = new StringBuilder("SELECT ");
		sql.append(PlaceholderDoc.codice.getCompleteName());
		sql.append("," + PlaceholderDoc.info.getCompleteName());
		sql.append(" FROM ").append(PlaceholderDoc.getTableName()).append(" , ")
				.append(PlaceholderDocSezione.getTableName()).append(" WHERE ")
				.append(PlaceholderDoc.codice.getCompleteName()).append(" = ")
				.append(PlaceholderDocSezione.placeholder_codice.getCompleteName()).append(" AND ")
				.append(PlaceholderDocSezione.template_doc_nome.getCompleteName()).append(" = ? ").append(" AND ")
				.append(PlaceholderDocSezione.template_doc_sezione_nome.getCompleteName()).append(" = ? ")
				.append(" ORDER BY ")
				.append(PlaceholderDoc.codice.getCompleteName());
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(nomeTemplate);
		parameters.add(nomeSezione);
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return super.queryForList(sql.toString(), this.rowMapperPlaceholder, parameters);
	}

	public int updateSezioneTemplate(String nomeTemplate, String nomeSezione, String valore, Long idAllegato) {
		log.trace("Entrato nel repository: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");
		StringBuilder sql = new StringBuilder("UPDATE  ");
		sql.append(TemplateDocSezioni.getTableName()).append(" SET ")
				.append(TemplateDocSezioni.valore.columnName()).append(" = ? , ")
				.append(TemplateDocSezioni.id_allegato.columnName()).append(" = ? ")
				.append(" WHERE ")
				.append(TemplateDocSezioni.nome.columnName()).append(" = ? ").append(" AND ")
				.append(TemplateDocSezioni.template_doc_nome.columnName()).append(" = ? ");
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(valore);
		parameters.add(idAllegato);
		parameters.add(nomeSezione);
		parameters.add(nomeTemplate);
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return super.update(sql.toString(), parameters);
	}
	
	/**
	 * utilizzata per aggiornare il campo idAllegato dopo aver caricato su cms l'image default...
	 * @param nomeTemplate
	 * @param nomeSezione
	 * @param idAllegato
	 * @return
	 */
	public int updateSezioneTemplateDefault(String nomeTemplate, String nomeSezione, Long idAllegato) {
		log.trace("Entrato nel repository: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");
		StringBuilder sql = new StringBuilder("UPDATE  ");
		sql.append(TemplateDocSezioniDefault.getTableName())
			.append(" SET ")
				.append(TemplateDocSezioniDefault.id_allegato.columnName()).append(" = ? ")
				.append(" WHERE ")
				.append(TemplateDocSezioniDefault.nome.columnName()).append(" = ? ")
				.append(" AND ")
				.append(TemplateDocSezioniDefault.template_doc_nome.columnName())
				.append(" = ? ");
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(idAllegato);
		parameters.add(nomeSezione);
		parameters.add(nomeTemplate);
		
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return super.update(sql.toString(), parameters);
	}

	public TemplateDocSezioneDTO getSezioneDefaultTemplate(String nomeTemplate, String nomeSezione) {
		log.trace("Entrato nel repository: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");
		StringBuilder sql = new StringBuilder("SELECT ");
		sql.append(TemplateDocSezioniDefault.nome.getCompleteName());
		sql.append("," + TemplateDocSezioniDefault.id_allegato.getCompleteName());
		sql.append("," + TemplateDocSezioniDefault.template_doc_nome.getCompleteName());
		sql.append("," + TemplateDocSezioniDefault.valore.getCompleteName());
		sql.append("," + TemplateDocSezioniDefault.tipo_sezione.columnName());
		sql.append(" FROM ").append(TemplateDocSezioniDefault.getTableName()).append(" WHERE ")
				.append(TemplateDocSezioniDefault.template_doc_nome.getCompleteName()).append(" = ? ").append(" AND ")
				.append(TemplateDocSezioniDefault.nome.getCompleteName()).append(" = ? ");
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(nomeTemplate);
		parameters.add(nomeSezione);
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return super.queryForObject(sql.toString(), this.rowMapper, parameters);
	}

	public int resetSezioneTemplate(String nomeTemplate, String nomeSezione) {
		log.trace("Entrato nel repository: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");
		StringBuilder sql = new StringBuilder("UPDATE  ");
		sql.append(TemplateDocSezioni.getTableName()).append(" SET ")
				.append(TemplateDocSezioni.valore.columnName())
				.append(" =  ")
				.append(TemplateDocSezioniDefault.valore.getCompleteName())
				.append(" , ")
				.append(TemplateDocSezioni.id_allegato.columnName())
				.append(" =  ")
				.append(TemplateDocSezioniDefault.id_allegato.getCompleteName())
				.append(" , ")
				.append(TemplateDocSezioni.tipo_sezione.columnName())
				.append(" =  ")
				.append(TemplateDocSezioniDefault.tipo_sezione.getCompleteName())
				.append(" FROM ")
				.append(TemplateDocSezioniDefault.getTableName())
				.append(" WHERE ")
				.append(TemplateDocSezioniDefault.nome.getCompleteName()).append(" =  ")
				.append(TemplateDocSezioni.nome.getCompleteName())
				.append(" AND ")
				.append(TemplateDocSezioni.template_doc_nome.getCompleteName()).append(" =  ")
				.append(TemplateDocSezioniDefault.template_doc_nome.getCompleteName())
				.append(" AND ")
				.append(TemplateDocSezioniDefault.nome.getCompleteName()).append(" =  ? ")
				.append(" AND ")
				.append(TemplateDocSezioniDefault.template_doc_nome.getCompleteName()).append(" =  ? ");
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(nomeSezione);
		parameters.add(nomeTemplate);
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return super.update(sql.toString(), parameters);
	}

	public int removeAllegatoId(long idAllegato) {
		log.trace("Entrato nel repository: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");
		StringBuilder sql = new StringBuilder("UPDATE  ");
		sql.append(TemplateDocSezioni.getTableName()).append(" SET ")
				.append(TemplateDocSezioni.id_allegato.columnName())
				.append(" =  null ")
				.append(" WHERE ")
				.append(TemplateDocSezioni.id_allegato.getCompleteName())
				.append(" = ? ")
				;
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(idAllegato);
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return super.update(sql.toString(), parameters);
	}
	
	public List<TemplateDocSezioneDTO> getSezioneDefaultByAllegatoId(long idAllegato) {
		log.trace("Entrato nel repository: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");
		StringBuilder sql = new StringBuilder("SELECT ");
		sql.append(TemplateDocSezioniDefault.nome.getCompleteName());
		sql.append("," + TemplateDocSezioniDefault.id_allegato.getCompleteName());
		sql.append("," + TemplateDocSezioniDefault.template_doc_nome.getCompleteName());
		sql.append("," + TemplateDocSezioniDefault.valore.getCompleteName());
		sql.append("," + TemplateDocSezioniDefault.tipo_sezione.columnName());
		sql.append(" FROM ").append(TemplateDocSezioniDefault.getTableName()).append(" WHERE ")
				.append(TemplateDocSezioniDefault.id_allegato.getCompleteName())
				.append(" = ? ");
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(idAllegato);
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return super.queryForList(sql.toString(), this.rowMapper, parameters);
	}

}