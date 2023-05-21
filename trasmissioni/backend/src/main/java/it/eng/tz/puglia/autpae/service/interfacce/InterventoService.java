package it.eng.tz.puglia.autpae.service.interfacce;

import java.util.Date;
import java.util.List;

import it.eng.tz.puglia.autpae.dto.InterventoTabDTO;
import it.eng.tz.puglia.autpae.enumeratori.ErroriValidazioneBE;
import it.eng.tz.puglia.autpae.enumeratori.TipoProcedimento;

public interface InterventoService {

	public void salva(List<Long> interventi, Long idFascicolo) throws Exception;

	/**
	 * 
	 * @autor Adriano Colaianni
	 * @date 25 giu 2021
	 * @param interventoSelezionati
	 * @param idFascicolo
	 * @param tipoProcedimento
	 * @param interventoDettaglio
	 * @param dataCreazioneFascicolo
	 * @param gruppoOwner campo fascicolo.ufficio, mi serve per filtrare i tipi_qualifiaczioni in caso di ETI_xxx_y
	 * @return
	 * @throws Exception
	 */
	public List<ErroriValidazioneBE> validazione(List<Long> interventoSelezionati, Long idFascicolo, TipoProcedimento tipoProcedimento,String interventoDettaglio,Date dataCreazioneFascicolo,String gruppoOwner) throws Exception;

	/**
	 * per i fascicoli migrati ci possono essere valori che nella nuova applicazione (nei vari tipi procedimento) non sono + ammessi es:
	 * radiobutton temporaneo/permanente prevedeva nella storia anche PERMANENTE RIMOVIBILE (id tipi_qualificazioni=227)
	 * @autor Adriano Colaianni
	 * @date 26 apr 2021
	 * @param tipoProcedimento
	 * @param idFascicolo 
	 * @return
	 * @throws Exception
	 */
	public InterventoTabDTO tabIntervento(TipoProcedimento tipoProcedimento,Long idFascicolo) throws Exception;

	public List<Long> datiIntervento(Long idFascicolo) throws Exception;

}