package it.eng.tz.puglia.autpae.civilia.migration.service;

import java.util.List;

import it.eng.tz.puglia.autpae.civilia.migration.dto.CiviliaPratica;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrSoprintendenzaNoteSt;
import it.eng.tz.puglia.autpae.civilia.migration.dto.putt.InfoAutPaesAlfaBean;
import it.eng.tz.puglia.autpae.civilia.migration.dto.putt.LocalizzazionePuttBean;
import it.eng.tz.puglia.autpae.civilia.migration.dto.putt.PuttDocBean;
import it.eng.tz.puglia.autpae.civilia.migration.dto.putt.TnoProtocolloUscita;

public interface PuttMigrationService
{
	List<InfoAutPaesAlfaBean> getPuttPratiche(int from, int to) throws Exception;
	List<LocalizzazionePuttBean> getPuttLocalizzazione(Long praticaId,boolean is016) throws Exception;
	List<PuttDocBean> getPuttAllegati(String codicePratica,boolean is016) throws Exception;
	List<TnoProtocolloUscita> getProtocolloUscita(long tPraticaId) throws Exception;
	/**
	 * @autor Adriano Colaianni
	 * @date 20 lug 2021
	 * @param listaCodici
	 * @return
	 * @throws Exception
	 */
	List<InfoAutPaesAlfaBean> getPuttPraticheFromCodes(List<String> listaCodici) throws Exception;
	/**
	 * altri attributi della pratica del framework civilia
	 * da qui su putt pendiamo data creazione
	 * @autor Adriano Colaianni
	 * @date 30 lug 2021
	 * @param tPraticaId
	 * @param is016
	 * @return
	 * @throws Exception
	 */
	List<CiviliaPratica> getCiviliaPratica(Long tPraticaId, boolean is016) throws Exception;
	/**
	 * @autor Adriano Colaianni
	 * @date 16 ago 2021
	 * @return
	 * @throws Exception
	 */
	Long countPuttPraticheTrasmesse() throws Exception;
	
	
}
