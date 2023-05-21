package it.eng.tz.puglia.autpae.enumeratori;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.eng.tz.puglia.autpae.config.ApplicationProperties;

public enum PlaceHolder {
	
	CODICE_FASCICOLO("Identificativo pratica"),
	OGGETTO("Oggetto dell'intervento"),
	TIPO_PRECEDIMENTO("Tipologia del procedimento"),
	DATA_PRESENTAZIONE("Data di presentazione/attivazione della pratica"),
	COMUNE("Comune/i in cui è localizzato l'intervento"),
	ENTE_PROCEDENTE("Ente Delegato che ha registrato la pratica"),
	NOME_COGNOME_RICHIEDENTE("Informazioni anagrafiche del Richiedente"),
	NUMERO_PROVVEDIMENTO("Identificativo del provvedimento"),
	DATA_PROVVEDIMENTO("Data rilascio autorizzazione del provvedimento"),
	RUP("Responsabile Unico del Procedimento"),
	ESITO_PROVVEDIMENTO("Esito del provvedimento"),
	NOME_FILE_ULTERIORE_DOC("Nome del file allegato"),
	LINK_FILE_ULTERIORE_DOC("Collegamento per il download del file allegato"),
	TITOLO_FILE_ULTERIORE_DOC("Titolo della documentazione allegata"),
	DESCRIZIONE_FILE_ULTERIORE_DOC("Descrizione della documentazione allegata"),
	PROTOCOLLO_SET_CAMPIONAMENTO("Numero di protocollo del campionamento"),
	DATA_INIZIO_CAMPIONAMENTO("Data di inizio del campionamento"),
	DATA_FINE_CAMPIONAMENTO("Data di conclusione del campionamento"),
	DATA_SCADENZA_MODIFICHE("Data entro la quale è consentito modificare la pratica"),
	DATA_FINE_VERIFICA("Termine ultimo per la verifica della pratica"),
	NOME_COGNOME_USER_ASSEGNATARIO("Informazioni anagrafiche dell'utente assegnatario"),
	URL_DOWNLOAD_RICEVUTA_PROTETTO("url di download di ricevuta di trasmissione"),
	URL_DOWNLOAD_PROVVEDIMENTO_PROTETTO("url di download provvedimento finale"),
	MOTIVAZIONE("Motivo richiesta modifica/eliminazione post trasmissione"),
	DATA_PROTOCOLLO_CAMPIONAMENTO("Data di protocollazione del documento di campionamento"),
	ALLEGATI_ULTERIORE_DOCUMENTAZIONE("Lista degli allegati inseriti in un'ulteriore documentazione");
	
	
	private static ApplicationProperties props;
	private String legenda;

	
	private void setProps(ApplicationProperties props) {
		PlaceHolder.props = props;
	}
	private PlaceHolder(String text) {
		this.legenda = text;
	}
	public String getLegenda() {
		return legenda;
	}
	
	
	public static  PlaceHolder[] getSetComunicazioni() {
		PlaceHolder[] ph = new PlaceHolder[]{CODICE_FASCICOLO, OGGETTO, TIPO_PRECEDIMENTO, COMUNE, ENTE_PROCEDENTE, NOME_COGNOME_RICHIEDENTE, DATA_PROVVEDIMENTO};
		if (props.isPutt()) {  PlaceHolder.aggiungiElemento(ph, PlaceHolder.DATA_PRESENTAZIONE); }	// da aggiungere eventualmente anche negli altri set
		return ph;
	}
	public static  PlaceHolder[] getSetTrasmissione() {
		PlaceHolder[] ph = new PlaceHolder[]{CODICE_FASCICOLO, OGGETTO, TIPO_PRECEDIMENTO, COMUNE, ENTE_PROCEDENTE, NOME_COGNOME_RICHIEDENTE, DATA_PROVVEDIMENTO,URL_DOWNLOAD_RICEVUTA_PROTETTO,URL_DOWNLOAD_PROVVEDIMENTO_PROTETTO};
		if (props.isPutt()) {  PlaceHolder.aggiungiElemento(ph, PlaceHolder.DATA_PRESENTAZIONE); }
		return ph;
	}
	public static  PlaceHolder[] getSetUlterioreDocumentazione() {
		return new PlaceHolder[]{CODICE_FASCICOLO, NOME_FILE_ULTERIORE_DOC, LINK_FILE_ULTERIORE_DOC, TITOLO_FILE_ULTERIORE_DOC, DESCRIZIONE_FILE_ULTERIORE_DOC};
	}
	public static  PlaceHolder[] getSetEsitoCampionamento() {
		return new PlaceHolder[]{PROTOCOLLO_SET_CAMPIONAMENTO, DATA_INIZIO_CAMPIONAMENTO, DATA_FINE_CAMPIONAMENTO,DATA_PROTOCOLLO_CAMPIONAMENTO};
	}
	public static  PlaceHolder[] getSetEsitoVerifica() {
		return new PlaceHolder[]{CODICE_FASCICOLO};
	}
	public static  PlaceHolder[] getSetModificaPostTrasmissione() {
		return new PlaceHolder[]{CODICE_FASCICOLO, DATA_SCADENZA_MODIFICHE};
	}
	public static  PlaceHolder[] getSetRevocaModificaPostTrasmissione() {
		return new PlaceHolder[]{CODICE_FASCICOLO};
	}
	public static  PlaceHolder[] getSetEliminazionePostTrasmissione() {
		return new PlaceHolder[]{CODICE_FASCICOLO};
	}
	public static  PlaceHolder[] getSetNotificaPreCampionamento() {
		return new PlaceHolder[]{DATA_FINE_CAMPIONAMENTO};
	}
	public static  PlaceHolder[] getSetNotificaPreVerifica() {
		return new PlaceHolder[]{DATA_FINE_VERIFICA};
	}
	public static  PlaceHolder[] getSetAssegnamentoFascicolo() {
		return new PlaceHolder[]{CODICE_FASCICOLO, NOME_COGNOME_USER_ASSEGNATARIO};
	}
	public static  PlaceHolder[] getSetDisassegnamentoFascicolo() {
		return new PlaceHolder[]{CODICE_FASCICOLO, NOME_COGNOME_USER_ASSEGNATARIO};
	}
	
	public static  PlaceHolder[] getSetRichiestePostTrasmissione() {
		PlaceHolder[] ph = new PlaceHolder[]{CODICE_FASCICOLO, OGGETTO, TIPO_PRECEDIMENTO, 
				COMUNE, ENTE_PROCEDENTE, NOME_COGNOME_RICHIEDENTE, DATA_PROVVEDIMENTO,
				MOTIVAZIONE};
		return ph;
	}
	
	
	public static PlaceHolder[] aggiungiElemento(PlaceHolder[] phArray, PlaceHolder elem) {
		// create a new ArrayList 
		List<PlaceHolder> arrlist = new ArrayList<PlaceHolder>(Arrays.asList(phArray)); 
		// Add the new element
		arrlist.add(elem); 
		// Convert the Arraylist to array 
		phArray = arrlist.toArray(phArray); 
		// return the array 
		return phArray;
	}
	
	public String fullName() {
		return "{"+this.name()+"}";
	}
	
	
	
    @Component
    public static class ReportTypeServiceInjector {
        @Autowired
        private ApplicationProperties props;

        @PostConstruct
        public void postConstruct() {
            for (PlaceHolder ph : EnumSet.allOf(PlaceHolder.class)) {
            	ph.setProps(props);
            }
        }
    }
}