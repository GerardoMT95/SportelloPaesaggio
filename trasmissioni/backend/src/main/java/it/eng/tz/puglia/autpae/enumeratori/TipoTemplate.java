package it.eng.tz.puglia.autpae.enumeratori;

public enum TipoTemplate {
	
	INVIO_COMUNICAZIONI(getDestinatariInvioComunicazioni(), PlaceHolder.getSetComunicazioni()),
	INVIO_TRASMISSIONE(getDestinatariInvioTrasmissione(), PlaceHolder.getSetTrasmissione()),
	INVIO_RITRASMISSIONE(getDestinatariInvioTrasmissione(), PlaceHolder.getSetTrasmissione()),
	INVIO_ULTERIORE_DOCUMENTAZIONE(getDestinatariInvioUlterioreDocumentazione(),
			PlaceHolder.getSetUlterioreDocumentazione()),
	ESITO_CAMPIONAMENTO(getDestinatariEsitoCampionamento(), PlaceHolder.getSetEsitoCampionamento()),
	ESITO_VERIFICA(getDestinatariEsitoVerifica(), PlaceHolder.getSetEsitoVerifica()),
	MODIFICA_POST_TRASMISSIONE(getDestinatariModificaPostTrasmissione(), PlaceHolder.getSetModificaPostTrasmissione()),
	REVOCA_MODIFICA_POST_TRASMISSIONE(getDestinatariRevocaModificaPostTrasmissione(),
			PlaceHolder.getSetRevocaModificaPostTrasmissione()),
	ELIMINAZIONE_POST_TRASMISSIONE(getDestinatariEliminazionePostTrasmissione(),
			PlaceHolder.getSetEliminazionePostTrasmissione()),
	NOTIFICA_PRE_CAMPIONAMENTO(getDestinatariNotificaPreCampionamento(), PlaceHolder.getSetNotificaPreCampionamento()),
	NOTIFICA_PRE_VERIFICA(getDestinatariNotificaPreVerifica(), PlaceHolder.getSetNotificaPreVerifica()),
	ASSEGNAMENTO_FASCICOLO(getDestinatariAssegnamentoFascicolo(), PlaceHolder.getSetAssegnamentoFascicolo()),
	DISASSEGNAMENTO_FASCICOLO(getDestinatariDisassegnamentoFascicolo(), PlaceHolder.getSetDisassegnamentoFascicolo()),
	RICHIESTA_MODIFICA_POST_TRASMISSIONE(getDestinatariRichiestaModificaPostTrasmissione(),
			PlaceHolder.getSetRichiestePostTrasmissione()),
	RICHIESTA_ELIMINAZIONE_POST_TRASMISSIONE(getDestinatariRichiestaEliminazionePostTrasmissione(),
			PlaceHolder.getSetRichiestePostTrasmissione());
	
	private PlaceHolder[] placeholdersAmmessi;
	private String[] destinatariAutomatici;
	
	
	
	private TipoTemplate(PlaceHolder[] placeholders) {
		this.placeholdersAmmessi=placeholders;
	}
	private TipoTemplate(String[] destinatariAutomatici) {
		this.destinatariAutomatici=destinatariAutomatici;
	}
	private TipoTemplate(String[] destinatariAutomatici, PlaceHolder[] placeholders) {
		this.placeholdersAmmessi   = placeholders;
		this.destinatariAutomatici = destinatariAutomatici;
	}
	
	
	
	public String[] getDestinatariAutomatici() {
		return destinatariAutomatici;
	}
	public void setDestinatariAutomatici(String[] destinatariAutomatici) {
		this.destinatariAutomatici=destinatariAutomatici;
	}
	public PlaceHolder[] getPlaceholdersAmmessi() {
		return placeholdersAmmessi;
	}
	
	
	
	private static String[] getDestinatariInvioComunicazioni() {
		return new String[] { };
	}
	private static String[] getDestinatariInvioTrasmissione() {
		return new String[] { "[TO] Enti Parco interessati dall'intervento", 
							  "[TO] Soprintendenze interessate dall'intervento (per le Tipologie di Procedimento previste)",
							  "[CC] Utente che sta effettuando la trasmissione",
							  "[CC] Ente Delegato che sta effettuando la trasmissione",
							  "[CC] Richiedente della pratica",
							  "[TO] Enti Comunali nei quali è localizzato l'intervento",
							  "[CC] Provincia di competenza", 
							  "[CC] Regione Puglia" };
	}
	private static String[] getDestinatariInvioUlterioreDocumentazione() {
		return new String[] { };
	}
	private static String[] getDestinatariEsitoCampionamento() {
		return new String[] { "[TO] Enti Delegati che hanno trasmesso pratiche durante il periodo di campionamento",
							  "[TO] Enti Comunali nei quali sono localizzati gli interventi",
						   // "[CC] Responsabili comunali",
							  "[CC] Utenti che hanno creato, modificato o trasmesso le pratiche",
							  "[CC] Richiedenti dello strumento urbanistico esecutivo",
							  "[CC] Regione Puglia" };
	}
	private static String[] getDestinatariEsitoVerifica() {
		return new String[] { "[TO] Ente Delegato che ha registrato la pratica",
							  "[CC] Richedente della pratica" };
	}
	private static String[] getDestinatariModificaPostTrasmissione() {
		return new String[] { "[TO] Utente abilitato alla modifica della pratica (colui che ha effettuato la trasmissione)" };
	}
	private static String[] getDestinatariRevocaModificaPostTrasmissione() {
		return new String[] { "[TO] Utente abilitato alla modifica della pratica (colui che ha effettuato la trasmissione)" };
	}
	private static String[] getDestinatariEliminazionePostTrasmissione() {
		return new String[] { "[TO/CC] Tutti i destinatari relativi alla trasmissione della pratica" };
	}
	private static String[] getDestinatariNotificaPreCampionamento() {	// operatori Regionali
		return new String[] { };
	}
	private static String[] getDestinatariNotificaPreVerifica() {  // operatori Regionali
		return new String[] { };
	}
	private static String[] getDestinatariAssegnamentoFascicolo() {
		return new String[] { "[TO] Funzionario al quale è stata assegnata la pratica" };
	}
	private static String[] getDestinatariDisassegnamentoFascicolo() {
		return new String[] { "[TO] Funzionario al quale è stato revocato l'assegnamento della pratica" };
	}
	private static String[] getDestinatariRichiestaModificaPostTrasmissione() {
		return new String[] { 
				"[CC] Tutti i destinatari relativi alla trasmissione della pratica",
				"[TO] Amministratori applicazione",
				};
	}
	private static String[] getDestinatariRichiestaEliminazionePostTrasmissione() {
		return new String[] { 
				"[CC] Tutti i destinatari relativi alla trasmissione della pratica",
				"[TO] Amministratori applicazione",
				};
	}
	
}