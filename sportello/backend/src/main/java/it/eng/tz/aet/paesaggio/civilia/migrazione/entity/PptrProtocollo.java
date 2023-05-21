package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

import java.sql.Blob;
import java.util.Date;

/**
 * @Table(name="TNO_PPTR_PROTOCOLLO")
 * @author acolaianni
 *
 */
public class PptrProtocollo {
	
	//@Column(name="TNO_PPTR_PROTOCOLLO_ID")
	private long id;

	//@Column( name="PROG" )
	private long prog;
	
	//@Column(name="VERSO")
	private String verso;
	
	//@Column(name="TNO_PPTR_TIPOPROTOCOLLO_ID")
	private long tipoProtocolloId;
	
	//@Column(name="CODICE_TIPOPROTOCOLLO")
	private String codiceTipoProtocollo;
	
	//@Column(name="CODICE_PRATICA_APPPTR")
	private String codicePraticaApptr;
	
	//@Column(name="CODICE_PRATICA_ENTEDELEGATO")
	private String codicePraticaEnteDelegato;
	
	//@Column(name="T_PRATICA_ID_APPPTR")
	private long tPraticaIdAppptr;
	
	//@Column(name="T_PRATICA_ID_ENTEDELEGATO")
	private long tPraticaIdEnteDelegato;
	
	//@Column(name="BIN")
	private Blob bin;
	
	//@Column(name="BIN_TIMBRATO")
	private Blob binTimbrato;
	            
	//@Column(name="T_KE_DOC_ST_ID")
	private long tKeDocStId;
	      
	//@Column(name="ESITO_PROTOCOLLAZIONE")
	private String esitoProtocollazione;
	                   
	//@Column(name="MOTIVO_ERRATA_PROTOCOLLAZIONE")
	private String motivoErrataProtocollazione;
	            
	//@Column(name="NUMERO_PROTOCOLLO")
	private String numeroProtocollo;
	    
	//@Column(name="DATA_PROTOCOLLO")
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getProg() {
		return prog;
	}

	public void setProg(long prog) {
		this.prog = prog;
	}

	public String getVerso() {
		return verso;
	}

	public void setVerso(String verso) {
		this.verso = verso;
	}

	public long getTipoProtocolloId() {
		return tipoProtocolloId;
	}

	public void setTipoProtocolloId(long tipoProtocolloId) {
		this.tipoProtocolloId = tipoProtocolloId;
	}

	public String getCodiceTipoProtocollo() {
		return codiceTipoProtocollo;
	}

	public void setCodiceTipoProtocollo(String codiceTipoProtocollo) {
		this.codiceTipoProtocollo = codiceTipoProtocollo;
	}

	public String getCodicePraticaApptr() {
		return codicePraticaApptr;
	}

	public void setCodicePraticaApptr(String codicePraticaApptr) {
		this.codicePraticaApptr = codicePraticaApptr;
	}

	public String getCodicePraticaEnteDelegato() {
		return codicePraticaEnteDelegato;
	}

	public void setCodicePraticaEnteDelegato(String codicePraticaEnteDelegato) {
		this.codicePraticaEnteDelegato = codicePraticaEnteDelegato;
	}

	public long gettPraticaIdAppptr() {
		return tPraticaIdAppptr;
	}

	public void settPraticaIdAppptr(long tPraticaIdAppptr) {
		this.tPraticaIdAppptr = tPraticaIdAppptr;
	}

	public long gettPraticaIdEnteDelegato() {
		return tPraticaIdEnteDelegato;
	}

	public void settPraticaIdEnteDelegato(long tPraticaIdEnteDelegato) {
		this.tPraticaIdEnteDelegato = tPraticaIdEnteDelegato;
	}

	public Blob getBin() {
		return bin;
	}

	public void setBin(Blob bin) {
		this.bin = bin;
	}

	public Blob getBinTimbrato() {
		return binTimbrato;
	}

	public void setBinTimbrato(Blob binTimbrato) {
		this.binTimbrato = binTimbrato;
	}

	public long gettKeDocStId() {
		return tKeDocStId;
	}

	public void settKeDocStId(long tKeDocStId) {
		this.tKeDocStId = tKeDocStId;
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

	
}
