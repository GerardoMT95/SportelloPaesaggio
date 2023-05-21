package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email;

public enum PlaceHolder {
	CODICE_FASCICOLO("Identificativo pratica"),//APPPTR-NN-AAAA
	CODICE_PRATICA("Identificativo pratica"), //alias...
	CODICE_TRASMISSIONE("Identificativo pratica post trasmissione"), //APXXXXX-NN_AAAA oppure AUTPAESAF-NN-AAAA 
	OGGETTO("Oggetto dell'intervento"),
	TIPO_PROCEDIMENTO("Tipologia del procedimento"),
	DATA_PRESENTAZIONE("Data di presentazione/attivazione della pratica"),
	COMUNE("Comune/i in cui è localizzato l'intervento"),
	ENTE_PROCEDENTE("Ente Delegato che ha registrato la pratica"),
	NOME_COGNOME_RICHIEDENTE("Informazioni anagrafiche del Richiedente"),
	NUMERO_PROVVEDIMENTO("Identificativo del provvedimento"),
	DATA_PROVVEDIMENTO("Data rilascio autorizzazione del provvedimento"),
	RUP("Responsabile Unico del Procedimento"),
	NUM_PROTOCOLLO_ISTANZA("Numero protocollo ricezione istanza"),
	DATA_PROTOCOLLO_ISTANZA("Data protocollo ricezione istanza"),
	ESITO_PROVVEDIMENTO("Esito del provvedimento"),
	NOME_FILE_ULTERIORE_DOC("Nome del file allegato"),
	URL_FILE_ULTERIORE_DOC("Collegamento per il download del file allegato"),
	TITOLO_FILE_ULTERIORE_DOC("Titolo della documentazione allegata"),
	DESCRIZIONE_FILE_ULTERIORE_DOC("Descrizione della documentazione allegata"),
//	PROTOCOLLO_SET_CAMPIONAMENTO("Numero di protocollo del campionamento"),
//	DATA_INIZIO_CAMPIONAMENTO("Data di inizio del campionamento"),
//	DATA_FINE_CAMPIONAMENTO("Data di conclusione del campionamento"),
//	DATA_SCADENZA_MODIFICHE("Data entro la quale è consentito modificare la pratica"),
//	DATA_FINE_VERIFICA("Termine ultimo per la verifica della pratica"),
	NOME_COGNOME_USER_ASSEGNATARIO("Informazioni anagrafiche dell'utente assegnatario"),
	//SEDUTA DI COMMISSIONE
	NOME_SEDUTA("Nome assegnato alla seduta di commissione"),
	DATA_SEDUTA("Data in cui si terrà la seduta di commissione"),
	ORARIO_SEDUTA("Orario in cui si terrà la seduta di commissione"),
	PRATICHE_DA_ESAMINARE("Pratiche all'ordine del giorno della seduta di commisisone"),
	//RUP già presente sopra
	MEMBRI_COMMISSIONE("Utenti facenti parte della commissione locale"),
	//INTEGRAZIONE
	DATA_INTEGRAZIONE("Data in cui è stata effettuata l'integrazione"),
	URL_DOWNLOAD_RICEVUTA_PROTETTO("Link per scaricare il documento \"ricevuta di trasmissione\""),
	NOME_FUNZIONARIO("Nome funzionario che inoltra a comunicazione"),
	COGNOME_FUNZIONARIO("Cognome funzionario che inoltra a comunicazione"),
	NOTE_SOSPENSIONE("Note sospensione pratica"),
	NOTE_ARCHIVIAZIONE("Note archiviazione pratica"),
	NOTE_RIATTIVAZIONE("Note riattivazione pratica"),
	FILES_ULTERIORE_DOCUMENTAZIONE("Files ulteriore documentazione"),
	PROTOCOLLO("Sezione protocollo del fascicolo"),
	NOME_RICHIEDENTE("Nome richiedente"),
	COGNOME_RICHIEDENTE("Cognome richiedente"),
	CODICE_SEGRETO("Codice segreto"),
	TIPO_FASCICOLO("Tipologia del fascicolo"),
	LINK_PROVVEDIMENTO("Link Provvedimento");

	
	private String legenda;
	private PlaceHolder(String text) {
		this.legenda = text;
	}
	public String getLegenda() {
		return legenda;
	}
	
	/**
	 * restituisce il set minimo per i nuovi template cancellabili....
	 * @author acolaianni
	 *
	 * @return
	 */
	public static String defaultSet() {
		return String.join(",",new String[] {
				CODICE_FASCICOLO.name(),
				OGGETTO.name(),
				TIPO_PROCEDIMENTO.name(),
				NOME_COGNOME_RICHIEDENTE.name()
				
		});
		
	}
}
