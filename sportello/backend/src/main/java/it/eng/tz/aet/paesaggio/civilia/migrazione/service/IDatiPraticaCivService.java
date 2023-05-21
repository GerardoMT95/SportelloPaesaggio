/**
 * 
 */
package it.eng.tz.aet.paesaggio.civilia.migrazione.service;

import java.util.List;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.AutPaesPptrLocalizInterv;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.CiviliaMail;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.CiviliaMailAllegati;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.Comuni_completo_cod_istat;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.DocAmmVPratiche;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.EnteParcoAss;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.InteressePubblicoAss;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PaesaggioRuraleAss;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrAltreDichiarRich;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrApprovato;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrAssoggProcedEdil;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrAttiAcquisiti;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrCaratterizzazioneIntervento;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrDescrIntervento;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrDestUrbInterv;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrDisclaimerXPratica;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrInquadramentoIntervento;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrIstanzaStampe;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrLegittProvved;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrLegittTitoli;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrLegittimita;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrMailInviate;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrParereSop;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrParticelleCatastali;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrProcedimentiContenzioso;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrProtocollo;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrProtocolloIstanza;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrProtocolloUscita;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrProvvedimento;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrQualificIntervento;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrReferentiDoc;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrReferentiProgetto;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrRelazioneEnte;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrSchedaAllegato;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrStatoEffMitigaz;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrStrumentiUrbanistici;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrStrutAntroStorCult;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrStrutEcosistemica;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrStrutIdrogeomorf;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrTipoIntervento;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrTitolarita;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrVincoliArchArch;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.TPratica;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.VtnoAllegatiPptr;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.VtnoAttivitaPptr;



/**
 * @author Adriano Colaianni
 * @date 19 apr 2021
 */
public interface IDatiPraticaCivService {

	List<VtnoAttivitaPptr> getListVtnoAttivitaPptr();
	
	/**
	 * lista dei comuni (completa)
	 * @autor Adriano Colaianni
	 * @date 20 apr 2021
	 * @return
	 */
	List<Comuni_completo_cod_istat> getComuniCompletoCodIstat();

	List<VtnoAttivitaPptr> getListVtnoAttivitaPptr_Cittadino(String jbpUname);

	Long getMaxProgCittadino(String codicePraticaAppptr);

	Long getProvvedimentoSanatoria(String codicePraticaAppptr, Long progr);

	PptrProtocolloIstanza getPptrProtocolloIstanza(String codiceAppptr, Long progr);

	PptrIstanzaStampe getPptrIstanzaStampe(String codiceAppptr, Long progr);

	TPratica getTPratica(String codiceAppptr);

	List<PptrReferentiProgetto> getReferenti(Long tPraticaId, Long progr);

	List<PptrReferentiDoc> getDocumento(Long treferenteId);

	List<PptrTitolarita> getTitolaritaReferente(String codicePraticaApptr, Long treferenteId, Long prog);

	List<PptrDisclaimerXPratica> getDisclaimerXPratica(String codicePraticaApptr, Long treferenteId, Long prog);

	List<PptrAltreDichiarRich> getAltreDichRich(Long tPraticaApptrId);

	List<AutPaesPptrLocalizInterv> getLocalizzazioneIntervento(String codicePraticaAppptr, long prog);

	List<PptrParticelleCatastali> getParticelleCatastaliLocalizInterv(long localizIntervId, long tPraticaApptrId,
			long prog);

	List<EnteParcoAss> getEnteParcoAss(String codicePratica, String codIstat, long prog);

	List<PaesaggioRuraleAss> getPaesaggioRuraleAss(String codicePraticaApptr, String codIstat, long prog);

	List<InteressePubblicoAss> getInteressePubblicoAss(String codicePraticaApptr, String codIstat, long prog);

	List<PptrTipoIntervento> getDatiTipoIntervento(long tPraticaId, long prog);

	List<PptrCaratterizzazioneIntervento> getDatiCaratterizzazioneIntervento(long tPraticaId, long prog);

	List<PptrQualificIntervento> getDatiQualificazioneIntervento(long tPraticaId, long prog);

	List<PptrDescrIntervento> getDescrIntervento(String codicePraticaAppptr, long prog);

	List<PptrDestUrbInterv> getDestinazioneUrbanisticaOldMode(String codicePraticaAppptr, long prog);

	List<PptrDestUrbInterv> getDestinazioneUrbanistica(Long localizzazioneInterventoId);

	List<PptrStrumentiUrbanistici> getPptrStrumentiUrbanistici(Long id);

	List<PptrLegittimita> getLeggittimita(String codicePraticaAppptr, long prog);

	List<PptrLegittProvved> getLeggittimitaProvv(String codicePraticaAppptr, long prog);

	List<PptrLegittTitoli> getLeggittimitaTitoli(String codicePraticaAppptr, long prog);

	List<PptrAssoggProcedEdil> getProcedureEdilizie(String codicePraticaAppptr, long prog);

	List<PptrInquadramentoIntervento> getInquadramentoIntervento(String codicePraticaAppptr, long prog);

	List<PptrStatoEffMitigaz> getStatoEffmitigazione(String codicePraticaAppptr, long prog);

	List<PptrProcedimentiContenzioso> getProcedimentiContenzioso(String codicePraticaAppptr, long prog);

	List<PptrAttiAcquisiti> getPareriAtti(String codicePraticaAppptr, long prog);

	List<PptrApprovato> getPptrApprovato(String codicePraticaAppptr, long prog);

	List<PptrStrutAntroStorCult> getPptrAntroStorCult(String codicePraticaAppptr, long prog);

	List<PptrStrutEcosistemica> getPptrEcosistemica(String codicePraticaAppptr, long prog);

	List<PptrStrutIdrogeomorf> getPptrIdrogeomorf(String codicePraticaAppptr, long prog);

	List<PptrVincoliArchArch> getPptrVincolistica(String codicePraticaAppptr, long prog);

	List<DocAmmVPratiche> getDocumentazioneAmministrativa(String codicePraticaAppptr, long prog);

	List<VtnoAllegatiPptr> getDocumentazioneTecnica(String codicePraticaAppptr, long prog);

	List<PptrRelazioneEnte> getRelazioneEnte(String codicePraticaAppptr, long prog);

	List<VtnoAllegatiPptr> getAllegatiRelazioneEnte(String codicePraticaAppptr, long prog);

	List<CiviliaMail> getCiviliaEmail(long praticaId);

	List<CiviliaMailAllegati> getCiviliaEmailAllegati(long mailInviateId);

	/**
	 * prelievo delle mail specifiche non riservate es codice=RICH_PAR_SOP , visibilita=ED per prelevare la mail di invio relazione ente
	 * @author acolaianni
	 *
	 * @param codicePraticaAppptr
	 * @param codice
	 * @param visibilita
	 * @return
	 */
	List<PptrMailInviate> getPptrMailInviate_by_codicePraticaApPptr_tnoGruppoComCodice_tnoTipoComVisibilita(
			String codicePraticaAppptr, String codice, String visibilita);

	List<PptrParereSop> getParereSOP(String codicePraticaAppptr, long prog);

	/**
	 * parere eventualmente inserito dalla soprintendenza
	 * @author acolaianni
	 *
	 * @param codicePraticaAppptr
	 * @return
	 */
	List<VtnoAllegatiPptr> getListParereSopVtnoAllegatiPptr(String codicePraticaAppptr);

	/**
	 * solo pareri SOP inseriti da ente delegato
	 * @author acolaianni
	 *
	 * @param codicePraticaAppptr
	 * @param prog
	 * @return
	 */
	List<VtnoAllegatiPptr> getListParereSopDaEnteDelegato(String codicePraticaAppptr, long prog);

	/**
	 * lista ulteriore documentazione allegata alla pratica con attributi di ruolo mittente, destinatari e visibilità sui destinatari
	 * @author acolaianni
	 *
	 * @param codicePraticaAppptr
	 * @return
	 */
	List<PptrSchedaAllegato> getListaUlterioreDocumentazione(String codicePraticaAppptr);

	/**
	 * lista di tutte le mail interne (pannello comunicazioni) comprese anche quelle relative 
	 * a trasmissione relazione ente e parere SOP
	 * @author acolaianni
	 *
	 * @param codicePraticaAppptr
	 * @return
	 */
	List<PptrMailInviate> getPptrMailInviate(String codicePraticaAppptr);

	/**
	 * estrazione record protocollo associato eventualmente alla mail di trasmissione della relazione ente codice=U_PROT_RICH_PAR
	 * @author acolaianni
	 *
	 * @param codicePraticaAppptr
	 * @param codiceTipoProtocollo
	 * @return
	 */
	List<PptrProtocollo> getProtocolloByCodice(String codicePraticaAppptr, String codiceTipoProtocollo);

	/**
	 * pannello provvedimento
	 * @author acolaianni
	 *
	 * @param codicePraticaAppptr
	 * @param prog
	 * @return
	 */
	List<PptrProvvedimento> getProvvedimento(String codicePraticaAppptr, long prog);

	/**
	 * allegati pannello provvedimento (lato ente delegato) gli altri (SOP e ETI non lo vedono nella vecchia portlet)
	 * @author acolaianni
	 *
	 * @param codicePraticaAppptr
	 * @param prog
	 * @return
	 */
	List<VtnoAllegatiPptr> getProvvedimentoAllegati(String codicePraticaAppptr, long prog);

	/**
	 * ricevuta di trasmissione con protocollo
	 * @author acolaianni
	 *
	 * @param codicePraticaAppptr
	 * @param prog
	 * @return
	 */
	List<PptrProtocolloUscita> getRicevutaTrasmissione(String codicePraticaAppptr, long prog);
	
	
}
