package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

import java.sql.Blob;
import java.util.Date;

//@Table(name="TNO_PPTR_TITOLARITA")
public class PptrTitolarita {
	//// @COlumn(name="TNO_PPTR_TITOLARITA_ID")
	private long tnoPptrTitolaritaId;

	// @COlumn(name="CODICE_PRATICA_APPPTR")
	private String codicePraticaAppptr;

	// @COlumn(name="DATA_CARICAMENTO_FILE")
	private Date dataCaricamentoFile;

	// @COlumn(name="FILE_DICH_ASSENSO")
	private Blob fileDichAssenso;

	// @COlumn(name="FORMATO_FILE")
	private String formatoFile;

	// @COlumn(name="NOME_FILE")
	private String nomeFile;

	// @COlumn(name="REFERENTE_PROGETTO_ID")
	private long referenteProgettoId;

	// @COlumn(name="TIT_ESCLUSIVO")
	private String titEsclusivo;

	// @COlumn(name="TIT_RUOLO_ALTRO")
	private String titRuoloAltro;

	// @COlumn(name="TIT_RUOLO_ID")
	private long titRuoloId = 0L;

	// @COlumn( name="PROG" )
	private long prog;

	public long getTnoPptrTitolaritaId() {
		return tnoPptrTitolaritaId;
	}

	public void setTnoPptrTitolaritaId(long tnoPptrTitolaritaId) {
		this.tnoPptrTitolaritaId = tnoPptrTitolaritaId;
	}

	public String getCodicePraticaAppptr() {
		return codicePraticaAppptr;
	}

	public void setCodicePraticaAppptr(String codicePraticaAppptr) {
		this.codicePraticaAppptr = codicePraticaAppptr;
	}

	public Date getDataCaricamentoFile() {
		return dataCaricamentoFile;
	}

	public void setDataCaricamentoFile(Date dataCaricamentoFile) {
		this.dataCaricamentoFile = dataCaricamentoFile;
	}

	public Blob getFileDichAssenso() {
		return fileDichAssenso;
	}

	public void setFileDichAssenso(Blob fileDichAssenso) {
		this.fileDichAssenso = fileDichAssenso;
	}

	public String getFormatoFile() {
		return formatoFile;
	}

	public void setFormatoFile(String formatoFile) {
		this.formatoFile = formatoFile;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public long getReferenteProgettoId() {
		return referenteProgettoId;
	}

	public void setReferenteProgettoId(long referenteProgettoId) {
		this.referenteProgettoId = referenteProgettoId;
	}

	public String getTitEsclusivo() {
		return titEsclusivo;
	}

	public void setTitEsclusivo(String titEsclusivo) {
		this.titEsclusivo = titEsclusivo;
	}

	public String getTitRuoloAltro() {
		return titRuoloAltro;
	}

	public void setTitRuoloAltro(String titRuoloAltro) {
		this.titRuoloAltro = titRuoloAltro;
	}

	public long getTitRuoloId() {
		return titRuoloId;
	}

	public void setTitRuoloId(long titRuoloId) {
		this.titRuoloId = titRuoloId;
	}

	public long getProg() {
		return prog;
	}

	public void setProg(long prog) {
		this.prog = prog;
	}

}
