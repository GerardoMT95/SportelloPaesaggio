/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.dto;

import java.util.Date;

/**
 * @author Adriano Colaianni
 * @date 19 apr 2021
 */
public class InfoAutPaesPptrAlfa {

	// INFO_AUT_PAES_PPTR_ALFA.CODICE_PRATICA_APPPTR
	private String codicePraticaAppptr;
	// INFO_AUT_PAES_PPTR_ALFA.CODICE_PRATICA_ENTEDELEGATO
	private String codicePraticaEnteDelegato;
	// INFO_AUT_PAES_PPTR_ALFA.OGGETTO
	private String oggetto;
	//INFO_AUT_PAES_PPTR_ALFA.RESPONSAB
	private String responsabileProvvedimento;
	//INFO_AUT_PAES_PPTR_ALFA.NUMERO_PR
	private String numeroProvvedimento;
	//INFO_AUT_PAES_PPTR_ALFA.DATA_PRO
	private Date dataProvvedimento;
	//INFO_AUT_PAES_PPTR_ALFA.ESITO_RIC
	private String esitoProvvedimento;
	//INFO_AUT_PAES_PPTR_ALFA.DATA_TRASMISSIONE
	private Date dataTrasmissione;
	//INFO_AUT_PAES_PPTR_ALFA.ISTAT_AMM
	private String istatAmm;
	//TNO_PPTR_PRATICA.NOTE
	private String note;
	//INFO_AUT_PAES_PPTR_ALFA.PROG
	private Long progPubblicazione;
	//T_PRATICA.T_PRATICA_ID
	private Long tPraticaId;
	//T_PRATICA.T_PRATICA_ID derivante da join via codice APPPTR 
	private Long tPraticaAppptrId;
	//campo aggiunto NO IN DB usato solo per quelle in lavorazione
	private Date dataCreazione;
	
	
	public Date getDataCreazione() {
		return dataCreazione;
	}
	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
	public Long gettPraticaId() {
		return tPraticaId;
	}
	public void settPraticaId(Long tPraticaId) {
		this.tPraticaId = tPraticaId;
	}
	public Long getProgPubblicazione() {
		return progPubblicazione;
	}
	public void setProgPubblicazione(Long progPubblicazione) {
		this.progPubblicazione = progPubblicazione;
	}
	public String getResponsabileProvvedimento() {
		return responsabileProvvedimento;
	}
	public void setResponsabileProvvedimento(String responsabileProvvedimento) {
		this.responsabileProvvedimento = responsabileProvvedimento;
	}
	public String getNumeroProvvedimento() {
		return numeroProvvedimento;
	}
	public void setNumeroProvvedimento(String numeroProvvedimento) {
		this.numeroProvvedimento = numeroProvvedimento;
	}
	public Date getDataProvvedimento() {
		return dataProvvedimento;
	}
	public void setDataProvvedimento(Date dataProvvedimento) {
		this.dataProvvedimento = dataProvvedimento;
	}
	public String getEsitoProvvedimento() {
		return esitoProvvedimento;
	}
	public void setEsitoProvvedimento(String esitoProvvedimento) {
		this.esitoProvvedimento = esitoProvvedimento;
	}
	public Date getDataTrasmissione() {
		return dataTrasmissione;
	}
	public void setDataTrasmissione(Date dataTrasmissione) {
		this.dataTrasmissione = dataTrasmissione;
	}
	public String getIstatAmm() {
		return istatAmm;
	}
	public void setIstatAmm(String istatAmm) {
		this.istatAmm = istatAmm;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getCodicePraticaAppptr() {
		return codicePraticaAppptr;
	}
	public void setCodicePraticaAppptr(String codicePraticaAppptr) {
		this.codicePraticaAppptr = codicePraticaAppptr;
	}
	
	public String getCodicePraticaEnteDelegato() {
		return codicePraticaEnteDelegato;
	}
	public void setCodicePraticaEnteDelegato(String codicePraticaEnteDelegato) {
		this.codicePraticaEnteDelegato = codicePraticaEnteDelegato;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	
	public Long gettPraticaAppptrId() {
		return tPraticaAppptrId;
	}
	public void settPraticaAppptrId(Long tPraticaAppptrId) {
		this.tPraticaAppptrId = tPraticaAppptrId;
	}
	@Override
	public String toString() {
		return "InfoAutPaesPptrAlfa [codicePraticaAppptr=" + codicePraticaAppptr + ", codicePraticaEnteDelegato="
				+ codicePraticaEnteDelegato + ", oggetto=" + oggetto + ", responsabileProvvedimento="
				+ responsabileProvvedimento + ", numeroProvvedimento=" + numeroProvvedimento + ", dataProvvedimento="
				+ dataProvvedimento + ", esitoProvvedimento=" + esitoProvvedimento + ", dataTrasmissione="
				+ dataTrasmissione + ", istatAmm=" + istatAmm + ", note=" + note + ", progPubblicazione="
				+ progPubblicazione + ", tPraticaId=" + tPraticaId + ", tPraticaAppptrId=" + tPraticaAppptrId + "]";
	}
	
		
	
}
