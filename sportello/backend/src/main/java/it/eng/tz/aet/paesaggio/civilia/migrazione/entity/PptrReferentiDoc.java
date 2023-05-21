package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

import java.sql.Blob;
import java.util.Date;

public class PptrReferentiDoc {
	//NOME_FILE	FORMATO_FILE	CONTENUTO_FILE	DATA_CARICAMENTO_FILE	TIPO_DOC_IDENT_ID	NUM_DOC_IDENT	DATA_DOC_IDENT	ENTE_RIL_DOC_IDENT
	private String nomeFile;
	private String formatoFile;
	private Blob contenutoFile;
	private Date dataCaricamentoFile;
	private Long tipoDocIdentId;
	private String numDocIdent;
	private Date dataDocIdent;
	private String enteRilDocIdent;
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public String getFormatoFile() {
		return formatoFile;
	}
	public void setFormatoFile(String formatoFile) {
		this.formatoFile = formatoFile;
	}
	public Blob getContenutoFile() {
		return contenutoFile;
	}
	public void setContenutoFile(Blob contenutoFile) {
		this.contenutoFile = contenutoFile;
	}
	public Date getDataCaricamentoFile() {
		return dataCaricamentoFile;
	}
	public void setDataCaricamentoFile(Date dataCaricamentoFile) {
		this.dataCaricamentoFile = dataCaricamentoFile;
	}
	public Long getTipoDocIdentId() {
		return tipoDocIdentId;
	}
	public void setTipoDocIdentId(Long tipoDocIdentId) {
		this.tipoDocIdentId = tipoDocIdentId;
	}
	public String getNumDocIdent() {
		return numDocIdent;
	}
	public void setNumDocIdent(String numDocIdent) {
		this.numDocIdent = numDocIdent;
	}
	public Date getDataDocIdent() {
		return dataDocIdent;
	}
	public void setDataDocIdent(Date dataDocIdent) {
		this.dataDocIdent = dataDocIdent;
	}
	public String getEnteRilDocIdent() {
		return enteRilDocIdent;
	}
	public void setEnteRilDocIdent(String enteRilDocIdent) {
		this.enteRilDocIdent = enteRilDocIdent;
	}

}
