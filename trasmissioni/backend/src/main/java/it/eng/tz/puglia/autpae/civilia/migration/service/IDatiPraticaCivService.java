/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.service;

import java.util.List;

import it.eng.tz.puglia.autpae.civilia.migration.dto.AutPaesPptrLocalizInterv;
import it.eng.tz.puglia.autpae.civilia.migration.dto.AutPaesPptrPratica;
import it.eng.tz.puglia.autpae.civilia.migration.dto.CiviliaMail;
import it.eng.tz.puglia.autpae.civilia.migration.dto.CiviliaMailAllegati;
import it.eng.tz.puglia.autpae.civilia.migration.dto.Comuni_completo_cod_istat;
import it.eng.tz.puglia.autpae.civilia.migration.dto.EnteParcoAss;
import it.eng.tz.puglia.autpae.civilia.migration.dto.InfoAutPaesPptrAlfa;
import it.eng.tz.puglia.autpae.civilia.migration.dto.InteressePubblicoAss;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PaesaggioRuraleAss;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrCaratterizzazioneIntervento;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrCodiceInterno;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrGruppoUfficio;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrParticelleCatastali;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrProtocolloUscita;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrProvvedimento;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrQualificIntervento;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrRicevutaIstanza;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrSoprintendenzaNoteSt;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrTipoIntervento;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PraticaLavorazione;
import it.eng.tz.puglia.autpae.civilia.migration.dto.ReferentiProgettoDto;
import it.eng.tz.puglia.autpae.civilia.migration.dto.TnoMailEnti;
import it.eng.tz.puglia.autpae.civilia.migration.dto.TnoSopMatriceTerritorio;
import it.eng.tz.puglia.autpae.civilia.migration.dto.VtnoAllegatiPptr;

/**
 * @author Adriano Colaianni
 * @date 19 apr 2021
 */
public interface IDatiPraticaCivService {
	
	/**
	 * 
	 * @autor Adriano Colaianni
	 * @date 19 apr 2021
	 * @param fromNrow 
	 * @param toNrow
	 * @return
	 */
	public List<InfoAutPaesPptrAlfa> getPratiche(int fromNrow,int toNrow);
	
	public Long getMaxProgressivoPubblicazione(String codicePraticaApptr);
	/**
	 * @autor Adriano Colaianni
	 * @date 20 apr 2021
	 * @param tPraticaId
	 * @param prog
	 * @return
	 */
	List<ReferentiProgettoDto> getRichiedente(Long tPraticaId, Long prog);

	/**
	 * @autor Adriano Colaianni
	 * @date 20 apr 2021
	 * @param codicePraticaApPptr
	 * @param prog
	 * @return
	 */
	AutPaesPptrPratica getAutPaesPptrPratica(String codicePraticaApPptr, long prog);

	/**
	 * @autor Adriano Colaianni
	 * @date 20 apr 2021
	 * @param codicePraticaAppptr
	 * @param prog
	 * @return
	 */
	List<PptrRicevutaIstanza> getRicevutaIstanza(String codicePraticaAppptr, long prog);

	/**
	 * @autor Adriano Colaianni
	 * @date 20 apr 2021
	 * @param codicePraticaAppptr
	 * @param prog
	 * @return
	 */
	List<AutPaesPptrLocalizInterv> getLocalizzazioneIntervento(String codicePraticaAppptr, long prog);

	/**
	 * primo tab in trasmissioni sezione CODICE INTERNO ENTE
	 * @autor Adriano Colaianni
	 * @date 20 apr 2021
	 * @param codicePraticaAppptr
	 * @param prog
	 * @return
	 */
	List<PptrCodiceInterno> getDatiCodiceInterno(String codicePraticaAppptr, long prog);

	/**
	 * tipo intervento (prima radiobutton a selezione singola in pannello intervento 1=true mappare con le nostre...) 
	 * @autor Adriano Colaianni
	 * @date 20 apr 2021
	 * @param tPraticaId
	 * @param prog
	 * @return
	 */
	List<PptrTipoIntervento> getDatiTipoIntervento(long tPraticaId, long prog);

	/** seconda selezione di checkbox a selezione multipla nel pannello intervento 1=true mappare con le nostre
	 * presente anche temporaneo/permanente e i due text field di dettaglio
	 * @autor Adriano Colaianni
	 * @date 20 apr 2021
	 * @param tPraticaId
	 * @param prog
	 * @return
	 */
	List<PptrCaratterizzazioneIntervento> getDatiCaratterizzazioneIntervento(long tPraticaId, long prog);

	/**
	 * ultima selezione pannello (dipende dal tipo procedimento)
	 * D_LGS_42_146_RB_01 			1,2,3 (tipoprocedimento 1 ordinaria)
	 * 
	 * DPR_31_90_CHK_1 - DPR_31_90_CHK_2 
	 *    -  DPR_31_90_CHK_2_1 ...DPR_31_90_CHK_2_42 (1=true)
	 * 
	 * D_LGS_42_167_CHK_A .. D_LGS_42_167_CHK_C  (tipoprocedimento 3 ACCERTAMENTO DI COMPATIBILITA’ PAESAGGISTICA Artt. 167 e 181, D.Lgs. 42/2004 (PER OPERE IN DIFFORMITA’ O ASSENZA DI AUTORIZZAZIONE PAESAGGISTICA)
	 * 
	 * DPR_139_91_CHK_1 .. DPR_139_91_CHK_39 
	 *   
	 * @autor Adriano Colaianni
	 * @date 20 apr 2021
	 * @param tPraticaId
	 * @param prog
	 * @return
	 */
	List<PptrQualificIntervento> getDatiQualificazioneIntervento(long tPraticaId, long prog);

	/**
	 * lista dei comuni (completa)
	 * @autor Adriano Colaianni
	 * @date 20 apr 2021
	 * @return
	 */
	List<Comuni_completo_cod_istat> getComuniCompletoCodIstat();

	/**
	 * @autor Adriano Colaianni
	 * @date 21 apr 2021
	 * @param localizIntervId
	 * @param tPraticaApptrId
	 * @param prog
	 * @return
	 */
	List<PptrParticelleCatastali> getParticelleCatastaliLocalizInterv(long localizIntervId, long tPraticaApptrId,
			long prog);

	/**
	 * @autor Adriano Colaianni
	 * @date 21 apr 2021
	 * @param codicePratica
	 * @param codIstat
	 * @param prog
	 * @return
	 */
	List<EnteParcoAss> getEnteParcoAss(String codicePratica, String codIstat, long prog);

	/**
	 * @autor Adriano Colaianni
	 * @date 21 apr 2021
	 * @param codicePraticaApptr
	 * @param codIstat
	 * @param prog
	 * @return
	 */
	List<PaesaggioRuraleAss> getPaesaggioRuraleAss(String codicePraticaApptr, String codIstat, long prog);

	/**
	 * qui ci sono disallineamenti tra fk in ASS che si riferisce a record mancanti in MAP
	 * @autor Adriano Colaianni
	 * @date 21 apr 2021
	 * @param codicePraticaApptr
	 * @param codIstat
	 * @param prog
	 * @return
	 */
	List<InteressePubblicoAss> getInteressePubblicoAss(String codicePraticaApptr, String codIstat, long prog);

	/**
	 * @autor Adriano Colaianni
	 * @date 22 apr 2021
	 * @param codicePraticaApptr
	 * @param prog
	 * @return
	 */
	List<PptrProvvedimento> getProvvedimento(String codicePraticaApptr, long prog);

	/**
	 * distinguibili da campo pptrTipoAllegatoDescrizione che assume Parere Mibac o Provvedimento finale
	 * @autor Adriano Colaianni
	 * @date 22 apr 2021
	 * @param codicePraticaAppptr
	 * @param prog
	 * @return
	 */
	List<VtnoAllegatiPptr> getListAllegatiProvvedimento(String codicePraticaAppptr, long prog);

	/**
	 * prende anche allegati di versioni di pubblicazioni precedenti...
	 * es. APPPTR-787-2015 i file multitipo sono su + record e vanno raggruppati per getnTKeDocStId()
	 * @see it.eng.tz.puglia.autpae.civilia.migration.dto.VtnoAllegatiPptr#getMapAllegatiFacoltativi_byKey_tnTKeDocStId  
	 * @autor Adriano Colaianni
	 * @date 22 apr 2021
	 * @param codicePraticaAppptr
	 * @return
	 */
	List<VtnoAllegatiPptr> getListAllegatiFascicolo(String codicePraticaAppptr);

	/**
	 * Baco nella Persistenza, il PDF timbrato si dovrebbe trovare un getBinPdfProtContent in realta viene messo in getBinPdfContent
	 * loro fanno test pe prelevare il più grande in termini di dimensione
	 * ... if( pptrProtocolloUscita.getBinPdfProtContent().length > pptrProtocolloUscita.getBinPdfContent().length )
	 * @autor Adriano Colaianni
	 * @date 22 apr 2021
	 * @param codicePraticaAppptr
	 * @param prog
	 * @return
	 */
	List<PptrProtocolloUscita> getRicevutaTrasmissione(String codicePraticaAppptr, long prog);

	/**
	 * restituisce tutte le mail legate alla pratica (comprese le ricevute (VERSO=I)) vanno filtrate 
	 * @autor Adriano Colaianni
	 * @date 23 apr 2021
	 * @param praticaId
	 * @return
	 */
	List<CiviliaMail> getCiviliaEmail(long praticaId,boolean is016);

	/**
	 * restituisce le info degli allegati per mailInviateId con binContent che contiene il contenuto.
	 * @autor Adriano Colaianni
	 * @date 23 apr 2021
	 * @param mailInviateId
	 * @return
	 */
	List<CiviliaMailAllegati> getCiviliaEmailAllegati(long mailInviateId,boolean is016);

	/**
	 * @autor Adriano Colaianni
	 * @date 28 apr 2021
	 * @param codiciEnteDelegato
	 * @return
	 */
	List<InfoAutPaesPptrAlfa> getPraticheByCodiciEnteDelegato(List<String> codiciEnteDelegato);

	/**
	 * @autor Adriano Colaianni
	 * @date 29 apr 2021
	 * @return
	 */
	List<PptrGruppoUfficio> getUffici();

	/**
	 * @autor Adriano Colaianni
	 * @date 16 ago 2021
	 * @return
	 */
	Long countPratichePptrTrasmesse();

	/**
	 * @autor Adriano Colaianni
	 * @date 8 set 2021
	 * @param codicePraticaAppptr
	 * @param progr
	 * @return
	 * @throws Exception
	 */
	List<PptrSoprintendenzaNoteSt> getTnoPptrSopNote(String codicePraticaAppptr, long progr) throws Exception;

	/**
	 * @autor Adriano Colaianni
	 * @date 9 set 2021
	 * @param fromRowNum
	 * @param toRowNum
	 * @return
	 */
	List<PraticaLavorazione> getListaPraticheInLavorazione(int fromRowNum, int toRowNum);

	/**
	 * @autor Adriano Colaianni
	 * @date 9 set 2021
	 * @return
	 */
	Long countPratichePptrInLavorazione();

	/**
	 * @autor Adriano Colaianni
	 * @date 10 set 2021
	 * @return
	 */
	List<TnoMailEnti> getTnoMailEnti();

	/**
	 * @autor Adriano Colaianni
	 * @date 10 set 2021
	 * @return
	 */
	List<TnoSopMatriceTerritorio> getTnoSopMatriceTerritorio();

	/**
	 * @autor Adriano Colaianni
	 * @date 28 nov 2021
	 * @param tKeStDocId
	 * @return
	 */
	String getIdCmsFromTkeStDocId(Integer tKeStDocId);

}
