package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration;

/**
 * è il vecchio attività da espletare che ha senso solo in caso di stato pratica = AttivitaDaEspletare=IN_LAVORAZIONE
 * @author acolaianni
 *
 */
public enum StatoAttivita {
	
	//set per ente delegato:
	IN_ATTESA_VERBALE_COMMISSIONE, //(previsto Verbale commissione ma non ancora inserito)
	RELAZIONE_ENTE_DA_TRASMETTERE, //Relazione ente non ancora trasmessa
	IN_ATTESA_PARERE_SOPRINTENDENZA, //Verbale e Relazione ente conclusi
	PARERE_SOPRINTENDENZA_RICEVUTO, //Parere ricevuto dalla Soprintendenza
	PARERE_SOPRINTENDENZA_INSERITO, //Parere inserito dall'ente
	IN_LAVORAZIONE,
	
	//set per commissione locale
	IN_ATTESA_INSERIMENTO_VERBALE, 
	VERBALE_INSERITO, 
	
	//SBAP
	PARERE_DA_TRASMETTERE, 
	PARERE_TRASMESSO, 
	PARERE_INSERITO_DA_ENTE; 
	
	public static StatoAttivita statoAttivita(AttivitaDaEspletare stato,StatoSeduta ss, StatoRelazione sr, StatoParere sp,
			Gruppi gruppo) {
		if(!(stato.equals(AttivitaDaEspletare.IN_LAVORAZIONE)))
			return null;
		
		if(gruppo.equals(Gruppi.ED_)|| gruppo.equals(Gruppi.REG_) ) {
			if(ss.equals(StatoSeduta.VERBALE_SEDUTA_ASSENTE)) {
				return IN_ATTESA_VERBALE_COMMISSIONE;
			}else if(sr.equals(StatoRelazione.RELAZIONE_NON_TRASMESSA)) {
				return RELAZIONE_ENTE_DA_TRASMETTERE;
			}else if(sp.equals(StatoParere.PARERE_NON_ALLEGATO) ||  
					 sp.equals(StatoParere.PARERE_IN_BOZZA_ENTE) || 
					 sp.equals(StatoParere.PARERE_IN_BOZZA_SOPRINTENDENZA) ) {
				return IN_ATTESA_PARERE_SOPRINTENDENZA;
			}else if(sp.equals(StatoParere.PARERE_NON_PREVISTO)) {
				return IN_LAVORAZIONE;
			}else if(sp.equals(StatoParere.PARERE_INSERITO_ENTE)) {
				return PARERE_SOPRINTENDENZA_INSERITO;
			}else if(sp.equals(StatoParere.PARERE_INSERITO_SOPRINTENDENZA)) {
				return StatoAttivita.PARERE_SOPRINTENDENZA_RICEVUTO;
			}
		}else if(gruppo.equals(Gruppi.CL_)) {
			if(ss.equals(StatoSeduta.VERBALE_SEDUTA_ASSENTE)) {
				return IN_ATTESA_INSERIMENTO_VERBALE;
			}else if(ss.equals(StatoSeduta.VERBALE_SEDUTA_ALLEGATO)) {
				return VERBALE_INSERITO;
			}
		}else if(gruppo.equals(Gruppi.SBAP_)) {
			if(sp.equals(StatoParere.PARERE_NON_ALLEGATO) ||  
					 sp.equals(StatoParere.PARERE_IN_BOZZA_ENTE) || 
					 sp.equals(StatoParere.PARERE_IN_BOZZA_SOPRINTENDENZA) ) {
				return PARERE_DA_TRASMETTERE;
			}else if(sp.equals(StatoParere.PARERE_INSERITO_ENTE)) {
				return PARERE_INSERITO_DA_ENTE;
			}else if(sp.equals(StatoParere.PARERE_INSERITO_SOPRINTENDENZA)) {
				return PARERE_TRASMESSO;
			}	
		}
		return null;
	}
	
}
