package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * coincide con il campo stato in DB pratica.stato
 * 
 * @author acolaianni
 *
 */
public enum AttivitaDaEspletare
{
	//stati presentazione istanza
	COMPILA_DOMANDA("Compila Domanda"), 
	GENERA_STAMPA_DOMANDA("Genera Stampa Domanda"),
	ALLEGA_DOCUMENTI_SOTTOSCRITTI("Allega Documenti Sottoscritti"),
	IN_ATTESA_DI_PROTOCOLLAZIONE("In Attesa Di Protocollazione"), 
	//ISTANZA_PRESENTATA("Istanza Presentata"), //equivalente a in preistruttoria
	//stati istruttoria
	//NON_ASSEGNATA("Non assegnata"), ///probabilmente si toglie... va indagato se viene testato per l'endoprocesso di assegnazione
	//IN_PROTOCOLLAZIONE("In protocollazione"), //forse è un refuso....
	IN_PREISTRUTTORIA("Presa in carico"),  //EQUIVALENTE A ISTANZA PRESENTATA  si puo' togliere
	IN_LAVORAZIONE("In lavorazione"),
	//RELAZIONE_DA_TRASMETTERE("Relazione da trasmettere"),  //non sono stati.... diventano colonna ad hoc sulla pratica (magari enum)
	//ATTESA_PARERE_SOPRINTENDENZA("In attesa del parere della soprintendenza"), //non sono stati.... diventano colonna ad hoc sulla pratica (magari enum)
	IN_TRASMISSIONE("In trasmissione"),
	SOSPESA("Sospesa"),
	ARCHIVIATA("Archiviata"),
	TRANSMITTED("Trasmesso");// come su presentazione e mi serve per il layer_geo pubblico...

	private String descrizione;

	public String getDescrizione()
	{
		return descrizione;
	}
	private AttivitaDaEspletare(String descrizione)
	{
		this.descrizione = descrizione;
	}

	public static List<AttivitaDaEspletare> getStatiPresentazioneIstanza()
	{
		return Arrays.asList(AttivitaDaEspletare.COMPILA_DOMANDA, 
							 AttivitaDaEspletare.ALLEGA_DOCUMENTI_SOTTOSCRITTI, 
							 AttivitaDaEspletare.GENERA_STAMPA_DOMANDA);
	}	
	
	public static List<AttivitaDaEspletare> getStatiIstruttoria()
	{
		return Arrays.asList(AttivitaDaEspletare.IN_ATTESA_DI_PROTOCOLLAZIONE,
						   	 AttivitaDaEspletare.IN_PREISTRUTTORIA,
						   	 AttivitaDaEspletare.IN_LAVORAZIONE,
						   	 AttivitaDaEspletare.IN_TRASMISSIONE,
						   	 AttivitaDaEspletare.TRANSMITTED,
						   	 AttivitaDaEspletare.SOSPESA,
						   	 AttivitaDaEspletare.ARCHIVIATA);
	}
	
	public static List<AttivitaDaEspletare> getStatiNonEditabili()
	{
		return Arrays.asList(AttivitaDaEspletare.COMPILA_DOMANDA,
							 AttivitaDaEspletare.ALLEGA_DOCUMENTI_SOTTOSCRITTI,
							 AttivitaDaEspletare.GENERA_STAMPA_DOMANDA,
							 AttivitaDaEspletare.TRANSMITTED,
							 AttivitaDaEspletare.ARCHIVIATA,
							 AttivitaDaEspletare.SOSPESA);
	}
	
	public static List<AttivitaDaEspletare> getStatiNonAmmessiCambioOwnership()
	{
		return Arrays.asList(AttivitaDaEspletare.TRANSMITTED,
							 AttivitaDaEspletare.ARCHIVIATA,
							 AttivitaDaEspletare.SOSPESA);
	}
	
	public static List<String> getStatiPresentazioneIstanzaName()
	{
		return getStatiPresentazioneIstanza().stream().map(AttivitaDaEspletare::name).collect(Collectors.toList());
	}
	
	public static List<String> getStatiIstruttoriaName()
	{
		return getStatiIstruttoria().stream().map(AttivitaDaEspletare::name).collect(Collectors.toList());
	}
	
	public static List<String> getStatiNonEditabiliName()
	{
		return getStatiNonEditabili().stream().map(AttivitaDaEspletare::name).collect(Collectors.toList());
	}
	
	public static List<AttivitaDaEspletare> getStatiIntegrazioneAmmessa(Boolean suRichiestaEnte) {
		if(suRichiestaEnte!=null && suRichiestaEnte) {
			return Arrays.asList(AttivitaDaEspletare.IN_ATTESA_DI_PROTOCOLLAZIONE,
			AttivitaDaEspletare.IN_PREISTRUTTORIA,
			AttivitaDaEspletare.IN_LAVORAZIONE,
			AttivitaDaEspletare.IN_TRASMISSIONE);
		}else {//spontanea
			return Arrays.asList(AttivitaDaEspletare.IN_ATTESA_DI_PROTOCOLLAZIONE,
					AttivitaDaEspletare.IN_PREISTRUTTORIA);
		}
	}
	
}
