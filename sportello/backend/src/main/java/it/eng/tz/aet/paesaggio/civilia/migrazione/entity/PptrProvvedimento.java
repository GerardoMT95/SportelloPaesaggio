/**
 * 
 */
package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

import java.util.Date;

/**
 *<br>
 * mappa la tabella TNO_PPTR_PROVVEDIMENTO cosi composta
 * <ul>
    <li>TNO_PPTR_PROVVEDIMENTO_ID NUMBER </li>
    <li>T_PRATICA_APPTR_ID        NUMBER </li>
    <li>NUMERO_PROVVEDIMENTO      VARCHAR2(20)</li>
    <li>DATA_PROVVEDIMENTO        DATE </li>
    <li>FLAG_ESITO                VARCHAR2(1), A="Autorizzato"  N="Non autorizzato" P="Autorizzato con prescrizione" </li> 
    <li>RUP                       VARCHAR2(200) </li>
    <li>CODICE_PRATICA_APPPTR     VARCHAR2(255) </li>
    <li>PROG                      NUMBER </li>
    </ul> 
 *
 * @author Adriano Colaianni
 * @date 21 apr 2021
 */
public class PptrProvvedimento {

	//@Column(name="T_PRATICA_APPTR_ID")
	private String tPraticaApptrId;
	
    //@Column(name="NUMERO_PROVVEDIMENTO")
    private String numeroProvvedimento;
	
	//@Column(name="DATA_PROVVEDIMENTO")
	//@Temporal(TemporalType.DATE)
	private Date dataProvvedimento;

	//@Column(name="FLAG_ESITO")
	private String flagEsito;
	               
	//@Column(name="RUP")
	private String rup;
	
	//@Column(name="CODICE_PRATICA_APPPTR")
	private String codicePraticaApPptr;
	
	//@Column(name="PROG")
	private long prog;

	public String getCodicePraticaApPptr() {
		return codicePraticaApPptr;
	}

	public void setCodicePraticaApPptr(String codicePraticaApPptr) {
		this.codicePraticaApPptr = codicePraticaApPptr;
	}

	public String gettPraticaApptrId() {
		return tPraticaApptrId;
	}

	public void settPraticaApptrId(String tPraticaApptrId) {
		this.tPraticaApptrId = tPraticaApptrId;
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

	public String getFlagEsito() {
		return flagEsito;
	}

	public void setFlagEsito(String flagEsito) {
		this.flagEsito = flagEsito;
	}

	public String getRup() {
		return rup;
	}

	public void setRup(String rup) {
		this.rup = rup;
	}

	public long getProg() {
		return prog;
	}

	public void setProg(long prog) {
		this.prog = prog;
	}
	
	
}
