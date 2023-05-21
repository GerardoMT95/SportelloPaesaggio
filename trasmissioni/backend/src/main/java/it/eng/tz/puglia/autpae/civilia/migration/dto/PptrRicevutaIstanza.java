/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.dto;

import java.util.Date;

/** tabella CIVILIA_CS.TNO_PPTR_RICEVUTA_ISTANZA
 * @author Adriano Colaianni
 * @date 20 apr 2021
 */
public class PptrRicevutaIstanza {
	//@Column( name="PROG" )
	private long prog;

	/**
	 * Codice della pratica
	 */
	//@Column(name = "CODICE_PRATICA_APPPTR")
	private String codicePraticaAppptr;

	/**
	 * data di invio della PEC
	 */
	//@Temporal(TemporalType.TIMESTAMP)
	//@Column(name = "DATA_INVIO_PEC")
	private Date dataInvioPec;

	/**
	 * data di inserimento del record nel DataBase
	 */
	//@Temporal(TemporalType.TIMESTAMP)
	//@Column(name = "DATA_SUBMIT")
	private Date dataSubmit;

	/**
	 * Esito dell'invio della ricveuta
	 */
	//@Column(name = "ESITO")
	private String esito;

	/**
	 * FK alla tabella TNO_PPTR_PROTOCOLLO per il protocollo assegnato alla presentazione dell'istanza
	 */
	//@Column(name = "TNO_PPTR_PROTOCOLLO_ID")
	private long tnoPptrProtocolloId;

	public long getProg() {
		return prog;
	}

	public void setProg(long prog) {
		this.prog = prog;
	}

	public String getCodicePraticaAppptr() {
		return codicePraticaAppptr;
	}

	public void setCodicePraticaAppptr(String codicePraticaAppptr) {
		this.codicePraticaAppptr = codicePraticaAppptr;
	}

	public Date getDataInvioPec() {
		return dataInvioPec;
	}

	public void setDataInvioPec(Date dataInvioPec) {
		this.dataInvioPec = dataInvioPec;
	}

	public Date getDataSubmit() {
		return dataSubmit;
	}

	public void setDataSubmit(Date dataSubmit) {
		this.dataSubmit = dataSubmit;
	}

	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}

	public long getTnoPptrProtocolloId() {
		return tnoPptrProtocolloId;
	}

	public void setTnoPptrProtocolloId(long tnoPptrProtocolloId) {
		this.tnoPptrProtocolloId = tnoPptrProtocolloId;
	}

	@Override
	public String toString() {
		return "PptrRicevutaIstanza [prog=" + prog + ", codicePraticaAppptr=" + codicePraticaAppptr + ", dataInvioPec="
				+ dataInvioPec + ", dataSubmit=" + dataSubmit + ", esito=" + esito + ", tnoPptrProtocolloId="
				+ tnoPptrProtocolloId + "]";
	}

	
}
