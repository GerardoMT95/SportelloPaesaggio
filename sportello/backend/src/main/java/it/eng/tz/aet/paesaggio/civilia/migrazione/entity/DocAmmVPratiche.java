package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

import java.sql.Blob;

/**
 * allegati sezione amministrativa
 * V_TNO_PPTR_DOC_AMM_PRATICHE
 * @author acolaianni
 *
 */
public class DocAmmVPratiche {
	
	//@Column(name="BIN_CONTENT")
	private Blob binContent;

	//@Column(name="CARICATO_IN_ISTANZA")
	private String caricatoInIstanza;

	//@Column(name="CODICE_PRATICA_APPPTR")
	private String codicePraticaAppptr;

	//@Column(name="FORMATO_FILE")
	private String formatoFile;

	//@Column(name="GRUPPO")
	private String gruppo;

	//@Column(name="LETTERA_STAMPA_LABEL")
	private String letteraStampaLabel;

	//@Column(name="NOME_FILE")
	private String nomeFile;

	//@Column(name="T_PRATICA_APPTR_ID")
	private long tPraticaApptrId;

	//@Column(name="TIPO_DOCUMENTO")
	private String tipoDocumento;

	//@Column(name="TIPOPROCEDIMENTO")
	private String tipoprocedimento;

	//@Column(name="TNO_PPTR_DOC_AMM_TIPO_ID")
	private long tnoPptrDocAmmTipoId;

	//@Column(name="TNO_PPTR_TIPOPROCEDIMENTO_ID")
	private long tnoPptrTipoprocedimentoId;
	
	//@Column(name = "PROG")
	private long prog;

	public Blob getBinContent() {
		return binContent;
	}

	public void setBinContent(Blob binContent) {
		this.binContent = binContent;
	}

	public String getCaricatoInIstanza() {
		return caricatoInIstanza;
	}

	public void setCaricatoInIstanza(String caricatoInIstanza) {
		this.caricatoInIstanza = caricatoInIstanza;
	}

	public String getCodicePraticaAppptr() {
		return codicePraticaAppptr;
	}

	public void setCodicePraticaAppptr(String codicePraticaAppptr) {
		this.codicePraticaAppptr = codicePraticaAppptr;
	}

	public String getFormatoFile() {
		return formatoFile;
	}

	public void setFormatoFile(String formatoFile) {
		this.formatoFile = formatoFile;
	}

	public String getGruppo() {
		return gruppo;
	}

	public void setGruppo(String gruppo) {
		this.gruppo = gruppo;
	}

	public String getLetteraStampaLabel() {
		return letteraStampaLabel;
	}

	public void setLetteraStampaLabel(String letteraStampaLabel) {
		this.letteraStampaLabel = letteraStampaLabel;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public long gettPraticaApptrId() {
		return tPraticaApptrId;
	}

	public void settPraticaApptrId(long tPraticaApptrId) {
		this.tPraticaApptrId = tPraticaApptrId;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getTipoprocedimento() {
		return tipoprocedimento;
	}

	public void setTipoprocedimento(String tipoprocedimento) {
		this.tipoprocedimento = tipoprocedimento;
	}

	public long getTnoPptrDocAmmTipoId() {
		return tnoPptrDocAmmTipoId;
	}

	public void setTnoPptrDocAmmTipoId(long tnoPptrDocAmmTipoId) {
		this.tnoPptrDocAmmTipoId = tnoPptrDocAmmTipoId;
	}

	public long getTnoPptrTipoprocedimentoId() {
		return tnoPptrTipoprocedimentoId;
	}

	public void setTnoPptrTipoprocedimentoId(long tnoPptrTipoprocedimentoId) {
		this.tnoPptrTipoprocedimentoId = tnoPptrTipoprocedimentoId;
	}

	public long getProg() {
		return prog;
	}

	public void setProg(long prog) {
		this.prog = prog;
	}
	
	

}
