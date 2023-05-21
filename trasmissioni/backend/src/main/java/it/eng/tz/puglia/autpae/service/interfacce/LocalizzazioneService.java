package it.eng.tz.puglia.autpae.service.interfacce;

import java.util.List;

import it.eng.tz.puglia.autpae.dto.LocalizzazioneTabDTO;
import it.eng.tz.puglia.autpae.dto.TipologicaDTO;
import it.eng.tz.puglia.autpae.dto.UlterioriInformazioniDto;
import it.eng.tz.puglia.autpae.enumeratori.ErroriValidazioneBE;
import it.eng.tz.puglia.autpae.enumeratori.TipoLocalizzazione;

public interface LocalizzazioneService {
	
	public void salva(LocalizzazioneTabDTO localizzazioneTab, long idFascicolo) throws Exception;
	
	public LocalizzazioneTabDTO datiLocalizzazione(long idFascicolo) throws Exception;

	public UlterioriInformazioniDto getListe(List<String> comuni) throws Exception;
	
	public List<TipologicaDTO> particelleComuniForGruppo() throws Exception;

	/**
	 * @autor Adriano Colaianni
	 * @date 13 mag 2021
	 * @param localizzazioneTab
	 * @param idFascicolo
	 * @param tipoSelezLocalizzazione
	 * @return
	 * @throws Exception
	 */
	List<ErroriValidazioneBE> validazione(LocalizzazioneTabDTO localizzazioneTab, long idFascicolo,
			TipoLocalizzazione tipoSelezLocalizzazione) throws Exception;

	/**
	 * @autor Adriano Colaianni
	 * @date 27 lug 2022
	 * @param idFascicolo
	 * @param idComune
	 * @param ulterioriInfo
	 */
	void creaRecordVincolisticaComune(long idFascicolo, long idComune, UlterioriInformazioniDto ulterioriInfo);
	
	
}