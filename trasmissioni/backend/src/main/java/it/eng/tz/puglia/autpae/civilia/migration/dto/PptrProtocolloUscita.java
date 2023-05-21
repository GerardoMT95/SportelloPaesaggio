/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.dto;

import java.sql.Blob;
import java.util.Date;

/**
 * CIVILIA_CS.TNO_PPTR_PROTOCOLLO_USCITA  contiene le info di protocollazione e la ricevuta di trasmissione
 * @author Adriano Colaianni
 * @date 22 apr 2021
 */
public class PptrProtocolloUscita {

	//@Column(name="TNO_PPTR_PROTOCOLLO_USCITA_ID")
	private long id;
	
	//@Column(name="T_PRATICA_APPTR_ID")
	private long tPraticaId;
	
	//@Column(name="CODICE_PRATICA_APPPTR")
	private String codicePraticaApptr;
		
	//@Column(name="ESITO_PROTOCOLLAZIONE")
	private String esitoProtocollazione;
	
	//@Column(name="MOTIVO_ERRATA_PROTOCOLLAZIONE")
	private String motivoErrataProtocollazione;
	   
	//@Column(name="NUMERO_PROTOCOLLO")
	private String numeroProtocollo;
	  
	//@Column(name="DATA_PROTOCOLLO")
	//@javax.persistence.Temporal( TemporalType.TIMESTAMP)
	private Date dataprotocollo;
	            	    
	//@Column(name="TITOLARIO_PROTOCOLLO")
	private String titolarioProtocollo;
	                
	//@Column(name="DOC_SEGNATO")
	private String docSegnato;	                  
	             
	//@Column(name="ALGORITMO")
	private String algoritmo;
	                      
	//@Column(name="CODIFICA")
	private String codifica;
	                        
	//@Column(name="IMPRONTA")
	private String impronta;
	                       
	//@Column(name="NOTE")
	private String note;
	                    
	//@Column(name="SEGNATURA_BLOB")
	private Blob segnaturaBlob;
	                           
	// PDF da protocollare
	//@Column(name="BIN_PDF_CONTENT")
	private Blob binPdfContent;
	               
	// PDF con timbratura del protocollo
	//@Column(name="BIN_PDFPROT_CONTENT")
	private Blob binPdfProtContent;
		
	//@Column(name="PROG")
	private long prog;
	
	
	/**
	 * il protocollo in uscita aavviane perchè c'e' un ID nella tabella 
	 * TNO_PPTR_TRAMISSIONE, e questo ID viene inserito TNO_PPTR_TRASMISSIONE_ID per sapere se e' stato protocolloato in uscita 
	 * 
	 * fa riferimentoa ID della tabella TNO_PPTR_TRAMISSIONE
	 */
	//@Column(name="TNO_PPTR_TRASMISSIONE_ID")
	private long tnoPptrTrasmissioneId;


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public long gettPraticaId() {
		return tPraticaId;
	}


	public void settPraticaId(long tPraticaId) {
		this.tPraticaId = tPraticaId;
	}


	public String getCodicePraticaApptr() {
		return codicePraticaApptr;
	}


	public void setCodicePraticaApptr(String codicePraticaApptr) {
		this.codicePraticaApptr = codicePraticaApptr;
	}


	public String getEsitoProtocollazione() {
		return esitoProtocollazione;
	}


	public void setEsitoProtocollazione(String esitoProtocollazione) {
		this.esitoProtocollazione = esitoProtocollazione;
	}


	public String getMotivoErrataProtocollazione() {
		return motivoErrataProtocollazione;
	}


	public void setMotivoErrataProtocollazione(String motivoErrataProtocollazione) {
		this.motivoErrataProtocollazione = motivoErrataProtocollazione;
	}


	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}


	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}


	public Date getDataprotocollo() {
		return dataprotocollo;
	}


	public void setDataprotocollo(Date dataprotocollo) {
		this.dataprotocollo = dataprotocollo;
	}


	public String getTitolarioProtocollo() {
		return titolarioProtocollo;
	}


	public void setTitolarioProtocollo(String titolarioProtocollo) {
		this.titolarioProtocollo = titolarioProtocollo;
	}


	public String getDocSegnato() {
		return docSegnato;
	}


	public void setDocSegnato(String docSegnato) {
		this.docSegnato = docSegnato;
	}


	public String getAlgoritmo() {
		return algoritmo;
	}


	public void setAlgoritmo(String algoritmo) {
		this.algoritmo = algoritmo;
	}


	public String getCodifica() {
		return codifica;
	}


	public void setCodifica(String codifica) {
		this.codifica = codifica;
	}


	public String getImpronta() {
		return impronta;
	}


	public void setImpronta(String impronta) {
		this.impronta = impronta;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public Blob getSegnaturaBlob() {
		return segnaturaBlob;
	}


	public void setSegnaturaBlob(Blob segnaturaBlob) {
		this.segnaturaBlob = segnaturaBlob;
	}


	public Blob getBinPdfContent() {
		return binPdfContent;
	}


	public void setBinPdfContent(Blob binPdfContent) {
		this.binPdfContent = binPdfContent;
	}


	public Blob getBinPdfProtContent() {
		return binPdfProtContent;
	}


	public void setBinPdfProtContent(Blob binPdfProtContent) {
		this.binPdfProtContent = binPdfProtContent;
	}


	public long getProg() {
		return prog;
	}


	public void setProg(long prog) {
		this.prog = prog;
	}


	public long getTnoPptrTrasmissioneId() {
		return tnoPptrTrasmissioneId;
	}


	public void setTnoPptrTrasmissioneId(long tnoPptrTrasmissioneId) {
		this.tnoPptrTrasmissioneId = tnoPptrTrasmissioneId;
	}
	
	
}
