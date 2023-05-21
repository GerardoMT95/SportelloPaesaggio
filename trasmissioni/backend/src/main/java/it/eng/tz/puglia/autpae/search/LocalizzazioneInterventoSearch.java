package it.eng.tz.puglia.autpae.search;

import java.util.Date;

import org.hsqldb.lib.StringUtil;

import it.eng.tz.puglia.autpae.dbMapping.LocalizzazioneIntervento;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;

/**
 * Search for table autpae.localizzazione_intervento
 */
public class LocalizzazioneInterventoSearch extends WhereClauseGenerator<LocalizzazioneIntervento>{

    private static final long serialVersionUID = 8427839322L;
    
    //COLUMN pratica_id
    private Long praticaId;
    //COLUMN comune_id
    private Long comuneId;
    //COLUMN indirizzo
    private String indirizzo;
    //COLUMN num_civico
    private String numCivico;
    //COLUMN piano
    private String piano;
    //COLUMN interno
    private String interno;
    //COLUMN dest_uso_att
    private String destUsoAtt;
    //COLUMN dest_uso_prog
    private String destUsoProg;
    //COLUMN comune
    private String comune;
    //COLUMN sigla_provincia
    private String siglaProvincia;
    //COLUMN data_riferimento_catastale
    private Date dataRiferimentoCatastale;
    //COLUMN strade
    private Boolean strade;

    
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

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getNumCivico() {
		return numCivico;
	}

	public void setNumCivico(String numCivico) {
		this.numCivico = numCivico;
	}

	public String getPiano() {
		return piano;
	}

	public void setPiano(String piano) {
		this.piano = piano;
	}

	public String getInterno() {
		return interno;
	}

	public void setInterno(String interno) {
		this.interno = interno;
	}

	public String getDestUsoAtt() {
		return destUsoAtt;
	}

	public void setDestUsoAtt(String destUsoAtt) {
		this.destUsoAtt = destUsoAtt;
	}

	public String getDestUsoProg() {
		return destUsoProg;
	}

	public void setDestUsoProg(String destUsoProg) {
		this.destUsoProg = destUsoProg;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getSiglaProvincia() {
		return siglaProvincia;
	}

	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}

	public Date getDataRiferimentoCatastale() {
		return dataRiferimentoCatastale;
	}

	public void setDataRiferimentoCatastale(Date dataRiferimentoCatastale) {
		this.dataRiferimentoCatastale = dataRiferimentoCatastale;
	}

	public Boolean getStrade() {
		return strade;
	}

	public void setStrade(Boolean strade) {
		this.strade = strade;
	}

	@Override
	public String toString() {
		return "LocalizzazioneInterventoSearch [praticaId=" + praticaId + ", comuneId=" + comuneId + ", indirizzo="
				+ indirizzo + ", numCivico=" + numCivico + ", piano=" + piano + ", interno=" + interno + ", destUsoAtt="
				+ destUsoAtt + ", destUsoProg=" + destUsoProg + ", comune=" + comune + ", siglaProvincia="
				+ siglaProvincia + ", dataRiferimentoCatastale=" + dataRiferimentoCatastale + ", strade=" + strade
				+ "]";
	}

	@Override
	protected void generateWhereCriteria() { 
		String separatore = " where ";
		if(praticaId != null)
		{
			sql.append(separatore)
			   .append(LocalizzazioneIntervento.pratica_id.getCompleteName())
			   .append(" = :")
			   .append(LocalizzazioneIntervento.pratica_id.columnName());
			parameters.put(LocalizzazioneIntervento.pratica_id.columnName(), praticaId);
			separatore = " and ";
		}
		if(comuneId != null)
		{
			sql.append(separatore)
			   .append(LocalizzazioneIntervento.comune_id.getCompleteName())
			   .append(" = :")
			   .append(LocalizzazioneIntervento.comune_id.columnName());
			parameters.put(LocalizzazioneIntervento.comune_id.columnName(), comuneId);
			separatore = " and ";
		}
		if(!StringUtil.isEmpty(indirizzo))
		{
			sql.append(separatore)
			   .append(LocalizzazioneIntervento.indirizzo.getCompleteName())
			   .append(" = :")
			   .append(LocalizzazioneIntervento.indirizzo.columnName());
			parameters.put(LocalizzazioneIntervento.indirizzo.columnName(), indirizzo);
			separatore = " and ";
		}
		if(!StringUtil.isEmpty(numCivico))
		{
			sql.append(separatore)
			   .append(LocalizzazioneIntervento.num_civico.getCompleteName())
			   .append(" = :")
			   .append(LocalizzazioneIntervento.num_civico.columnName());
			parameters.put(LocalizzazioneIntervento.num_civico.columnName(), numCivico);
			separatore = " and ";
		}
		if(!StringUtil.isEmpty(piano))
		{
			sql.append(separatore)
			   .append(LocalizzazioneIntervento.piano.getCompleteName())
			   .append(" = :")
			   .append(LocalizzazioneIntervento.piano.columnName());
			parameters.put(LocalizzazioneIntervento.piano.columnName(), piano);
			separatore = " and ";
		}
		if(!StringUtil.isEmpty(interno))
		{
			sql.append(separatore)
			   .append(LocalizzazioneIntervento.interno.getCompleteName())
			   .append(" = :")
			   .append(LocalizzazioneIntervento.interno.columnName());
			parameters.put(LocalizzazioneIntervento.interno.columnName(), interno);
			separatore = " and ";
		}
		if(!StringUtil.isEmpty(destUsoAtt))
		{
			sql.append(separatore)
			   .append(LocalizzazioneIntervento.dest_uso_att.getCompleteName())
			   .append(" = :")
			   .append(LocalizzazioneIntervento.dest_uso_att.columnName());
			parameters.put(LocalizzazioneIntervento.dest_uso_att.columnName(), destUsoAtt);
			separatore = " and ";
		}
		if(!StringUtil.isEmpty(destUsoProg))
		{
			sql.append(separatore)
			   .append(LocalizzazioneIntervento.dest_uso_prog.getCompleteName())
			   .append(" = :")
			   .append(LocalizzazioneIntervento.dest_uso_prog.columnName());
			parameters.put(LocalizzazioneIntervento.dest_uso_prog.columnName(), destUsoProg);
			separatore = " and ";
		}
		if(!StringUtil.isEmpty(comune))
		{
			sql.append(separatore)
			   .append(LocalizzazioneIntervento.comune.getCompleteName())
			   .append(" = :")
			   .append(LocalizzazioneIntervento.comune.columnName());
			parameters.put(LocalizzazioneIntervento.comune.columnName(), comune);
			separatore = " and ";
		}
		if(!StringUtil.isEmpty(siglaProvincia))
		{
			sql.append(separatore)
			   .append(LocalizzazioneIntervento.sigla_provincia.getCompleteName())
			   .append(" = :")
			   .append(LocalizzazioneIntervento.sigla_provincia.columnName());
			parameters.put(LocalizzazioneIntervento.sigla_provincia.columnName(), siglaProvincia);
			separatore = " and ";
		}
		if(dataRiferimentoCatastale != null)
		{
			sql.append(separatore)
			   .append(LocalizzazioneIntervento.data_riferimento_catastale.getCompleteName())
			   .append(" = :")
			   .append(LocalizzazioneIntervento.data_riferimento_catastale.columnName());
			parameters.put(LocalizzazioneIntervento.data_riferimento_catastale.columnName(), dataRiferimentoCatastale);
			separatore = " and ";
		}
		if(strade != null)
		{
			sql.append(separatore)
			   .append(LocalizzazioneIntervento.strade.getCompleteName())
			   .append(" = :")
			   .append(LocalizzazioneIntervento.strade.columnName());
			parameters.put(LocalizzazioneIntervento.strade.columnName(), strade);
			separatore = " and ";
		}
	}

}