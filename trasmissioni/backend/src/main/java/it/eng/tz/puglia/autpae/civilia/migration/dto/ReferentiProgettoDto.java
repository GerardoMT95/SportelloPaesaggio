/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.dto;

import java.util.Date;

/**
 * @author Adriano Colaianni
 * @date 19 apr 2021
 */
public class ReferentiProgettoDto {
	// --richiedente
	// TNO_PPTR_REFERENTI_PROGETTO.T_PRATICA_APPTR_ID chiave su T_PRATICA
	private Long tPraticaApptrId; 
	// TNO_PPTR_REFERENTI_PROGETTO.NOME
	private String richiedenteNome;
	// TNO_PPTR_REFERENTI_PROGETTO.COGNOME
	private String richiedenteCognome;
	// TNO_PPTR_REFERENTI_PROGETTO.CODICE_FISCALE
	private String richiedenteCodiceFiscale;
	// TNO_PPTR_REFERENTI_PROGETTO.DITTA_RUOLO_RICH
	private String dittaRuoloRich; // es. legale rappresentante / PROCURATORE / TITOLARE IMPRESA OMONIMA
	// TNO_PPTR_REFERENTI_PROGETTO.DITTA_RAGIONE_SOCIALE
	private String dittaRagioneSociale;
	// TNO_PPTR_REFERENTI_PROGETTO.DITTA_PARTITA_IVA
	private String dittaPartitaIva;
	// TNO_PPTR_REFERENTI_PROGETTO.DITTA_CODICE_FISCALE
	private String dittaCodiceFiscale;
	// TNO_PPTR_REFERENTI_PROGETTO.COMUNE_NASCITA
	private String comuneNascita;
	// TNO_PPTR_REFERENTI_PROGETTO.PROV_NASCITA
	private String provNascita; // es BA
	// TNO_PPTR_REFERENTI_PROGETTO.STATO_NASCITA
	private String statoNascita; // es. Italia
	// TNO_PPTR_REFERENTI_PROGETTO.DATA_NASCITA
	private Date dataNascita;
	// TNO_PPTR_REFERENTI_PROGETTO.STATO_RESIDENZA
	private String statoResidenza; // es. Italia
	// TNO_PPTR_REFERENTI_PROGETTO.COMUNE_RESIDENZA
	private String comuneResidenza;
	// TNO_PPTR_REFERENTI_PROGETTO.PROV_RESIDENZA
	private String provResidenza; // es BA
	// TNO_PPTR_REFERENTI_PROGETTO.INDIRIZZO
	private String indirizzo; // es BA
	// TNO_PPTR_REFERENTI_PROGETTO.NUM_CIVICO
	private String numCivico; // es BA
	// TNO_PPTR_REFERENTI_PROGETTO.CAP
	private String cap;
	// TNO_PPTR_REFERENTI_PROGETTO.TEL_FISSO
	private String telFisso;
	// TNO_PPTR_REFERENTI_PROGETTO.TEL_CELLULARE
	private String telCellulare;
	// TNO_PPTR_REFERENTI_PROGETTO.TEL_FAX
	private String telFax;
	// TNO_PPTR_REFERENTI_PROGETTO.INDIRIZZO_MAIL
	private String indirizzoMail;
	// TNO_PPTR_REFERENTI_PROGETTO.INDIRIZZO_PEC
	private String indirizzoPec;

	public String getRichiedenteNome() {
		return richiedenteNome;
	}

	public void setRichiedenteNome(String richiedenteNome) {
		this.richiedenteNome = richiedenteNome;
	}

	public String getRichiedenteCognome() {
		return richiedenteCognome;
	}

	public void setRichiedenteCognome(String richiedenteCognome) {
		this.richiedenteCognome = richiedenteCognome;
	}

	public String getRichiedenteCodiceFiscale() {
		return richiedenteCodiceFiscale;
	}

	public void setRichiedenteCodiceFiscale(String richiedenteCodiceFiscale) {
		this.richiedenteCodiceFiscale = richiedenteCodiceFiscale;
	}

	public String getDittaRuoloRich() {
		return dittaRuoloRich;
	}

	public void setDittaRuoloRich(String dittaRuoloRich) {
		this.dittaRuoloRich = dittaRuoloRich;
	}

	public String getDittaRagioneSociale() {
		return dittaRagioneSociale;
	}

	public void setDittaRagioneSociale(String dittaRagioneSociale) {
		this.dittaRagioneSociale = dittaRagioneSociale;
	}

	public String getDittaPartitaIva() {
		return dittaPartitaIva;
	}

	public void setDittaPartitaIva(String dittaPartitaIva) {
		this.dittaPartitaIva = dittaPartitaIva;
	}

	public String getDittaCodiceFiscale() {
		return dittaCodiceFiscale;
	}

	public void setDittaCodiceFiscale(String dittaCodiceFiscale) {
		this.dittaCodiceFiscale = dittaCodiceFiscale;
	}

	public String getComuneNascita() {
		return comuneNascita;
	}

	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}

	public String getProvNascita() {
		return provNascita;
	}

	public void setProvNascita(String provNascita) {
		this.provNascita = provNascita;
	}

	public String getStatoNascita() {
		return statoNascita;
	}

	public void setStatoNascita(String statoNascita) {
		this.statoNascita = statoNascita;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getStatoResidenza() {
		return statoResidenza;
	}

	public void setStatoResidenza(String statoResidenza) {
		this.statoResidenza = statoResidenza;
	}

	public String getComuneResidenza() {
		return comuneResidenza;
	}

	public void setComuneResidenza(String comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
	}

	public String getProvResidenza() {
		return provResidenza;
	}

	public void setProvResidenza(String provResidenza) {
		this.provResidenza = provResidenza;
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

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getTelFisso() {
		return telFisso;
	}

	public void setTelFisso(String telFisso) {
		this.telFisso = telFisso;
	}

	public String getTelCellulare() {
		return telCellulare;
	}

	public void setTelCellulare(String telCellulare) {
		this.telCellulare = telCellulare;
	}

	public String getTelFax() {
		return telFax;
	}

	public void setTelFax(String telFax) {
		this.telFax = telFax;
	}

	public String getIndirizzoMail() {
		return indirizzoMail;
	}

	public void setIndirizzoMail(String indirizzoMail) {
		this.indirizzoMail = indirizzoMail;
	}

	public String getIndirizzoPec() {
		return indirizzoPec;
	}

	public void setIndirizzoPec(String indirizzoPec) {
		this.indirizzoPec = indirizzoPec;
	}

	public Long gettPraticaApptrId() {
		return tPraticaApptrId;
	}

	public void settPraticaApptrId(Long tPraticaApptrId) {
		this.tPraticaApptrId = tPraticaApptrId;
	}

	@Override
	public String toString() {
		return "ReferentiProgettoDto [tPraticaApptrId=" + tPraticaApptrId + ", richiedenteNome=" + richiedenteNome
				+ ", richiedenteCognome=" + richiedenteCognome + ", richiedenteCodiceFiscale="
				+ richiedenteCodiceFiscale + ", dittaRuoloRich=" + dittaRuoloRich + ", dittaRagioneSociale="
				+ dittaRagioneSociale + ", dittaPartitaIva=" + dittaPartitaIva + ", dittaCodiceFiscale="
				+ dittaCodiceFiscale + ", comuneNascita=" + comuneNascita + ", provNascita=" + provNascita
				+ ", statoNascita=" + statoNascita + ", dataNascita=" + dataNascita + ", statoResidenza="
				+ statoResidenza + ", comuneResidenza=" + comuneResidenza + ", provResidenza=" + provResidenza
				+ ", indirizzo=" + indirizzo + ", numCivico=" + numCivico + ", cap=" + cap + ", telFisso=" + telFisso
				+ ", telCellulare=" + telCellulare + ", telFax=" + telFax + ", indirizzoMail=" + indirizzoMail
				+ ", indirizzoPec=" + indirizzoPec + "]";
	}

	
}
