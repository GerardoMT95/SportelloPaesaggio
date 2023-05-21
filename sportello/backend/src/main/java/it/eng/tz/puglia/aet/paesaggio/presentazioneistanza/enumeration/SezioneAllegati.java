package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration;

/**
 * colonna tipo_contenuto.sezione corrisponde alla sezione nel FE del tipo di allegato
 * @author acolaianni
 *
 */
public enum SezioneAllegati {
	DOC_AMMINISTRATIVA_D("Sportello_DocAmministrativaD"), //oneri istruttoria
	DOC_AMMINISTRATIVA_E("Sportello_DocAmministrativaE"), //vari tipi di contenuto allegato (selezione singola per file)
	DOC_TECNICA("Sportello_DocTecnica"),//vari tipi di contenuto allegato (selezione multipla per file)
	DICHIARAZIONI_ASSENSO("Sportello_DichiarazioniAssenso"),
	LOCALIZZAZIONE("Sportello_Localizzazione"),
	GENERA_STAMPA("Sportello_Istanza"),
	GENERA_STAMPA_PREVIEW("Sportello_IstanzaPreview"),
	INT_DOC("Sportello_IntegrazioneDocumentale"),
	INT_DOC_PREVIEW("Sportello_IntegrazioneDocumentalePreview"),
	REQ_ASR("Istruttoria_ArchiviazioneSospensioneRiattivazione"),
	COM_LOC("Istruttoria_CommissioneLocale"),
	PARERE_SOPR("Istruttoria_ParereSop"),
	CONF_TEMPLATE_DOC("TemplateDocumentazione"),  //sezione non dipendente dalla pratica... qui vanno i file di configurazione.
	PROVVEDIMENTO("Istruttoria_ProvvedimentoFinale"),
	ULTERIORE_DOCUMENTAZIONE("Istruttoria_UlterioreDocumentazione"),
	TRASMISSIONE("Istruttoria_DocumentoTrasmissione"),
	RELAZIONE_ENTE("Istruttoria_RelazioneEnte"),
	COMUNICAZIONI("Istruttoria_Comunicazioni"),
	ALLEGATO_DELEGA("Allegato_delega"),
	ALLEGATO_SOLLEVAMENTO_INCARICO("Allegato_sollevamento_incarico"),
	OTHER("Istruttoria_Altro");
	private String folderDiogene;
	
	private SezioneAllegati(String folderDiogene) {
		this.folderDiogene=folderDiogene;
	}

	/**
	 * @return the folderDiogene
	 */
	public String getFolderDiogene() {
		return folderDiogene;
	}

	/**
	 * @param folderDiogene the folderDiogene to set
	 */
	public void setFolderDiogene(String folderDiogene) {
		this.folderDiogene = folderDiogene;
	}
	
}
