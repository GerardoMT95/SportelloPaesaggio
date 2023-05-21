package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

import java.util.Date;

/**
 * SELECT NUMERO_PROTOCOLLO,DATA_PROTOCOLLO,TITOLARIO_PROTOCOLLO,DATA_INVIO_PEC,ESITO 
	FROM CIVILIA_CS.TNO_PPTR_RICEVUTA_ISTANZA ri,CIVILIA_CS.TNO_PPTR_PROTOCOLLO prot WHERE prot.TNO_PPTR_PROTOCOLLO_ID =ri.TNO_PPTR_PROTOCOLLO_ID ;
 * @author acolaianni
 *
 */
public class PptrProtocolloIstanza {
	
	
	private String numeroProtocollo;
	private Date dataProtocollo;
	private String titolarioProtocollo;
	private Date dataInvioPec;
	private String esito; //S N
	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}
	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}
	public Date getDataProtocollo() {
		return dataProtocollo;
	}
	public void setDataProtocollo(Date dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}
	public String getTitolarioProtocollo() {
		return titolarioProtocollo;
	}
	public void setTitolarioProtocollo(String titolarioProtocollo) {
		this.titolarioProtocollo = titolarioProtocollo;
	}
	public Date getDataInvioPec() {
		return dataInvioPec;
	}
	public void setDataInvioPec(Date dataInvioPec) {
		this.dataInvioPec = dataInvioPec;
	}
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	
	
}
