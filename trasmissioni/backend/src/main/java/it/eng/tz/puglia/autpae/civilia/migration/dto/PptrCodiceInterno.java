/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.dto;

import java.util.Date;

/**
 * CIVILIA_CS.TNO_PPTR_CODICE_INTERNO  parte del primo tab sotto OGGETTO dell'intervento
 * @author Adriano Colaianni
 * @date 20 apr 2021
 */
public class PptrCodiceInterno {
	
	//@Column(name="CODICE_PRATICA_APPPTR")
	private String codicePraticaApptr;
	
	//@Column(name="RIF_NUMERO")
	private String rifNumero;  //nostro fascicolo.numeroInternoEnte
	
	//@Column(name="RIF_PROTOCOLLO")
	private String rifProtocollo;  //nostro fascicolo.protocollo
	
	//@Column(name="RIF_INT_ALFAN") //nostro fascicolo.codiceInternoEnte
	private String rifIntAlfanumerico;
	                  
	//@Column(name="RIF_DATA")
	private Date rifData; //nostro fascicolo.dataProtocollo
	
	//@Column(name="PROG")
	private long prog;

	public String getCodicePraticaApptr() {
		return codicePraticaApptr;
	}

	public void setCodicePraticaApptr(String codicePraticaApptr) {
		this.codicePraticaApptr = codicePraticaApptr;
	}

	public String getRifNumero() {
		return rifNumero;
	}

	public void setRifNumero(String rifNumero) {
		this.rifNumero = rifNumero;
	}

	public String getRifProtocollo() {
		return rifProtocollo;
	}

	public void setRifProtocollo(String rifProtocollo) {
		this.rifProtocollo = rifProtocollo;
	}

	public String getRifIntAlfanumerico() {
		return rifIntAlfanumerico;
	}

	public void setRifIntAlfanumerico(String rifIntAlfanumerico) {
		this.rifIntAlfanumerico = rifIntAlfanumerico;
	}

	public Date getRifData() {
		return rifData;
	}

	public void setRifData(Date rifData) {
		this.rifData = rifData;
	}

	public long getProg() {
		return prog;
	}

	public void setProg(long prog) {
		this.prog = prog;
	}
	

}
