package it.eng.tz.puglia.autpae.search;

import java.util.Date;

import org.hsqldb.lib.StringUtil;

import it.eng.tz.puglia.autpae.dbMapping.ParchiPaesaggiImmobili;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;

/**
 * Search for table autpae.parchi_paesaggi_immobili
 */
public class ParchiPaesaggiImmobiliSearch extends WhereClauseGenerator<ParchiPaesaggiImmobili>{

    private static final long serialVersionUID = 6772840931L;
    
    //COLUMN pratica_id
    private Long praticaId;
    //COLUMN comune_id
    private Long comuneId;
    //COLUMN tipo_vincolo
    private String tipoVincolo;
    //COLUMN codice
    private String codice;
    //COLUMN descrizione
    private String descrizione;
    //COLUMN selezionato
    private String selezionato;
    //COLUMN info
    private String info;
    //COLUMN data_inserimento
    private Date dataInserimento;
    
    
	public Long getPraticaId() {
		return praticaId;
	}

	public void setPraticaId(Long praticaId) {
		this.praticaId = praticaId;
	}

	public Long getComuneId() {
		return comuneId;
	}

	public void setComuneId(Long comuneId) {
		this.comuneId = comuneId;
	}

	public String getTipoVincolo() {
		return tipoVincolo;
	}

	public void setTipoVincolo(String tipoVincolo) {
		this.tipoVincolo = tipoVincolo;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getSelezionato() {
		return selezionato;
	}

	public void setSelezionato(String selezionato) {
		this.selezionato = selezionato;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	@Override
	public String toString() {
		return "ParchiPaesaggiImmobiliSearch [praticaId=" + praticaId + ", comuneId=" + comuneId + ", tipoVincolo="
				+ tipoVincolo + ", codice=" + codice + ", descrizione=" + descrizione + ", selezionato=" + selezionato
				+ ", info=" + info + ", dataInserimento=" + dataInserimento + "]";
	}

	@Override
	protected void generateWhereCriteria() { 
		String separatore = " where ";
		if(praticaId != null)
		{
			sql.append(separatore)
			   .append(ParchiPaesaggiImmobili.pratica_id.getCompleteName())
			   .append(" = :")
			   .append(ParchiPaesaggiImmobili.pratica_id.columnName());
			parameters.put(ParchiPaesaggiImmobili.pratica_id.columnName(), praticaId);
			separatore = " and ";
		}
		if(comuneId != null)
		{
			sql.append(separatore)
			   .append(ParchiPaesaggiImmobili.comune_id.getCompleteName())
			   .append(" = :")
			   .append(ParchiPaesaggiImmobili.comune_id.columnName());
			parameters.put(ParchiPaesaggiImmobili.comune_id.columnName(), comuneId);
			separatore = " and ";
		}
		if(!StringUtil.isEmpty(tipoVincolo))
		{
			sql.append(separatore)
			   .append(ParchiPaesaggiImmobili.tipo_vincolo.getCompleteName())
			   .append(" = :")
			   .append(ParchiPaesaggiImmobili.tipo_vincolo.columnName());
			parameters.put(ParchiPaesaggiImmobili.tipo_vincolo.columnName(), tipoVincolo);
			separatore = " and ";
		}
		if(!StringUtil.isEmpty(codice))
		{
			sql.append(separatore)
			   .append(ParchiPaesaggiImmobili.codice.getCompleteName())
			   .append(" = :")
			   .append(ParchiPaesaggiImmobili.codice.columnName());
			parameters.put(ParchiPaesaggiImmobili.codice.columnName(), codice);
			separatore = " and ";
		}
		if(!StringUtil.isEmpty(descrizione))
		{
			sql.append(separatore)
			   .append(ParchiPaesaggiImmobili.descrizione.getCompleteName())
			   .append(" = :")
			   .append(ParchiPaesaggiImmobili.descrizione.columnName());
			parameters.put(ParchiPaesaggiImmobili.descrizione.columnName(), descrizione);
			separatore = " and ";
		}
		if(!StringUtil.isEmpty(selezionato))
		{
			sql.append(separatore)
			   .append(ParchiPaesaggiImmobili.selezionato.getCompleteName())
			   .append(" = :")
			   .append(ParchiPaesaggiImmobili.selezionato.columnName());
			parameters.put(ParchiPaesaggiImmobili.selezionato.columnName(), selezionato);
			separatore = " and ";
		}
		if(!StringUtil.isEmpty(info))
		{
			sql.append(separatore)
			   .append(ParchiPaesaggiImmobili.info.getCompleteName())
			   .append(" = :")
			   .append(ParchiPaesaggiImmobili.info.columnName());
			parameters.put(ParchiPaesaggiImmobili.info.columnName(), info);
			separatore = " and ";
		}
		if(dataInserimento != null)
		{
			sql.append(separatore)
			   .append(ParchiPaesaggiImmobili.data_inserimento.getCompleteName())
			   .append(" = :")
			   .append(ParchiPaesaggiImmobili.data_inserimento.columnName());
			parameters.put(ParchiPaesaggiImmobili.data_inserimento.columnName(), dataInserimento);
			separatore = " and ";
		}
	}

}