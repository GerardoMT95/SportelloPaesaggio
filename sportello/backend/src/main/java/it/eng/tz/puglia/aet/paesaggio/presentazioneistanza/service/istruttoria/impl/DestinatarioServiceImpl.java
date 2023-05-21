package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.ApplicationProperties;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.TipologicaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.repository.AssegnamentoFascicoloFullRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.TipologicaDestinarioDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoDestinatario;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoOrganizzazioneSpecifica;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.AuthClient;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinatarioDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IReferentiService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.AssUtenteGruppo;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.UtenteGruppo;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.PaesaggioEmailDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.search.PaesaggioEmailSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.service.RemoteSchemaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.GruppiRuoliService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.DestinatarioService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.VarieUtils;
import it.eng.tz.puglia.configuration.http.HttpConfiguration;
import it.eng.tz.puglia.service.http.IHttpClientService;
import it.eng.tz.puglia.servizi_esterni.remote.utility.TipoEnte;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class DestinatarioServiceImpl implements DestinatarioService
{
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired private RemoteSchemaService commonService;
	@Autowired private AuthClient profileManager;
	@Autowired private IHttpClientService client;
	@Autowired private IReferentiService referentiService;
	@Autowired private AssegnamentoFascicoloFullRepository assegnamentoRepository;
	@Autowired private ApplicationProperties props;
	
	@Value("${autpae.host}")
	private String autpaeHost;
	
	@Autowired
	@Qualifier(HttpConfiguration.REST_TEMPLATE_CONTEXT_NAME)
	private RestTemplate restTemplateDef;
	
	@Transactional(transactionManager="txmng_common", propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	private List<DestinatarioDTO> getAllEmailForEnte(int idEnte, TipoDestinatario tipoDest) throws Exception {
		log.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		List<DestinatarioDTO> destinatari = new ArrayList<>();
		
		TipologicaDTO listaBase = commonService.searchAutomatizzataRubricaIstituzionale(idEnte, commonService.getIdApplicazione(props.getRubricaRiferimentoApplicazione()));
		if (listaBase!=null) {
			String denominazioneEnte = commonService.findEnteById(idEnte).getDescrizione();
			if (StringUtil.isNotEmpty(listaBase.getCodice())) {	// campo "pec"
				DestinatarioDTO tmp = new DestinatarioDTO();
				tmp.setEmail(listaBase.getCodice());
				tmp.setNome(denominazioneEnte);
				tmp.setPec(true);
				tmp.setTipo(tipoDest);
				destinatari.add(tmp);
			}
			if (StringUtil.isNotEmpty(listaBase.getLabel ())) { // campo "mail"
				DestinatarioDTO tmp = new DestinatarioDTO();
				tmp.setEmail(listaBase.getLabel());
				tmp.setNome(denominazioneEnte);
				tmp.setPec(true);
				tmp.setTipo(tipoDest);
				destinatari.add(tmp);
			}
		}
		PaesaggioEmailSearch searchPE = new PaesaggioEmailSearch();
		searchPE.setEnteId(idEnte);
		List<PaesaggioEmailDTO> listaEstesa = commonService.searchRubricaPaesaggio(searchPE);
		if (listaEstesa!=null && !listaEstesa.isEmpty()) {
			for(PaesaggioEmailDTO elem: listaEstesa)
				destinatari.add(new DestinatarioDTO(elem));
		}
		return destinatari;
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public  List<DestinatarioDTO> findDestinatariForEnteDelegato(int idOrganizzazione, Collection<Integer> idEnti, TipoDestinatario tipoDest) throws Exception {
		return findDestinatariFromAutpae(idOrganizzazione, idEnti, tipoDest,"/autpae/public/destinatari/findDestinatariForEnteDelegato");
	}

//	private List<DestinatarioDTO> findDestinatariForEnteDelegatoLocal(int idOrganizzazione, Collection<Integer> idEnti)
//			throws Exception, CustomOperationToAdviceException {
//		log.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
//		List<DestinatarioDTO> destinatari = new ArrayList<>();
//		for (int idEnte : idEnti) {
//			log.info("Ente 'Comune': "+idEnte);
//			PaesaggioEmailSearch searchPE = new PaesaggioEmailSearch();
//			searchPE.setOrganizzazioneId(idOrganizzazione);
//			searchPE.setEnteId(idEnte);
//			List<PaesaggioEmailDTO> listaTerritoriale = commonService.searchRubricaPaesaggio(searchPE);
//			if (listaTerritoriale !=null && !listaTerritoriale.isEmpty()) {
//				log.info("Trovate email per l'Ente Delegato (id="+idOrganizzazione+") in funzione della territorialità");
//				for(PaesaggioEmailDTO elem: listaTerritoriale)
//					destinatari.add(new DestinatarioDTO(elem));
//			}
//			else {
//				log.info("Non ci sono email per l'Ente Delegato (id="+idOrganizzazione+") in funzione della territorialità");
//				searchPE.setEnteId(null);
//				List<PaesaggioEmailDTO> listaNonTerritoriale = commonService.searchRubricaPaesaggio(searchPE);
//				if (listaNonTerritoriale!=null && !listaNonTerritoriale.isEmpty()) {
//					log.info("Trovate email per l'Ente Delegato (id="+idOrganizzazione+") non legate alla territorialità");
//					for(PaesaggioEmailDTO elem: listaTerritoriale)
//						destinatari.add(new DestinatarioDTO(elem));
//				}
//				else {
//					String nomeEnte = commonService.getDenominazioneOrganizzazione(idOrganizzazione);
//					throw new CustomOperationToAdviceException("Errore. Non è ancora stato configurato nessun indirizzo email per l'ENTE DELEGATO '"+nomeEnte+"'");
//				}
//			}
//		}
//		return destinatari;
//	}
	
	
	@Override
	//@Transactional(transactionManager="txmng_common", propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public  List<DestinatarioDTO> findDestinatariForRegione(Collection<Integer> idEnti, TipoDestinatario tipoDest) throws Exception {
		//return findDestinatariForRegioneLocal(idEnti, tipoDest);
		return findDestinatariFromAutpae(null, idEnti, tipoDest, "/autpae/public/destinatari/findDestinatariForRegione");
	}

//	private List<DestinatarioDTO> findDestinatariForRegioneLocal(Collection<Integer> idEnti, TipoDestinatario tipoDest)
//			throws Exception, CustomOperationToAdviceException {
//		log.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
//		List<DestinatarioDTO> destinatari = new ArrayList<>();
//		for (int idEnte : idEnti) {
//			log.info("Ente 'Comune': "+idEnte);
//			List<Integer> regs = commonService.getOrganizzazioniCompetentiOnEnti(Collections.singletonList(idEnte), props.getCodiceApplicazione(), Gruppi.REG_, null);
//			boolean mancante=false;
//			if (regs!=null && !regs.isEmpty()) {
//				log.info("Trovate "+regs.size()+" Organizzazioni 'Regione' per questo Comune");
//				for (int idReg : regs) {
//					log.info("Organizzazione 'Regione': "+idReg);
//					PaesaggioEmailSearch searchPE = new PaesaggioEmailSearch();
//					searchPE.setOrganizzazioneId(idReg);
//					searchPE.setEnteId(idEnte);
//					List<PaesaggioEmailDTO> listaTerritoriale = commonService.searchRubricaPaesaggio(searchPE);
//					if (listaTerritoriale!=null && !listaTerritoriale.isEmpty()) {
//						log.info("Trovate email per l'Organizzazione 'Regione' (id="+idReg+") in funzione della territorialità");
//						for(PaesaggioEmailDTO elem: listaTerritoriale)
//							destinatari.add(new DestinatarioDTO(elem));
//					}
//					else {
//						log.info("Non ci sono email per l'Organizzazione 'Regione' (id="+idReg+") in funzione della territorialità");
//						searchPE.setEnteId(null);
//						List<PaesaggioEmailDTO> listaNonTerritoriale = commonService.searchRubricaPaesaggio(searchPE);
//						if (listaNonTerritoriale!=null && !listaNonTerritoriale.isEmpty()) {
//							log.info("Trovate email per l'Organizzazione 'Regione' (id="+idReg+") non legate alla territorialità");
//							for(PaesaggioEmailDTO elem: listaTerritoriale)
//								destinatari.add(new DestinatarioDTO(elem));
//						}
//						else {
//							log.info("Non ci sono email per l'Organizzazione 'Regione' (id="+idReg+") non legate alla territorialità");
//							mancante=true;
//						}
//					}
//				}
//			}
//			else {
//				log.info("Nessuna Organizzazione 'Regione' trovata per questo Comune");
//			}
//			if (destinatari.isEmpty() || mancante==true) { // cioè   se  non ci sono   ORGANIZZAZIONI (Regione)  competenti
//														   // oppure se, per almeno un'ORGANIZZAZIONE (Regione), non ci sono email associate (né territoriali, né non territoriali)
//				log.info("Cerco l'Ente 'Regione'");
//				int enteRegione = commonService.findRegione().getId();
//				log.info("Ente 'Regione': " + enteRegione);
//				List<DestinatarioDTO> listaEmailEnte = this.getAllEmailForEnte(enteRegione, tipoDest);
//				if (listaEmailEnte!=null && !listaEmailEnte.isEmpty()) {
//					log.info("Trovate email per l'Ente 'Regione'(id="+enteRegione+")");
//					destinatari.addAll(listaEmailEnte);
//				}
//				else {
//					log.info("Non ci sono email per l'Ente 'Regione'(id="+enteRegione+")");
//					throw new CustomOperationToAdviceException("Errore. Non è ancora stato configurato nessun indirizzo email per la Regione Puglia");
//				}
//			}
//		}
//		return destinatari;
//	}
	
	@Override
	//@Transactional(transactionManager="txmng_common", propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public  List<DestinatarioDTO> findDestinatariForSoprintendenze(Collection<Integer> idEnti, TipoDestinatario tipoDest) throws Exception {
		return findDestinatariFromAutpae(null, idEnti, tipoDest, "/autpae/public/destinatari/findDestinatariForSoprintendenze");
//		log.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
//		List<DestinatarioDTO> destinatari = new ArrayList<>();
//		for (int idEnte : idEnti) {
//			log.info("Ente 'Comune': "+idEnte);
//			List<Integer> sbaps = commonService.getOrganizzazioniCompetentiOnEnti(Collections.singletonList(idEnte), props.getCodiceApplicazione(), Gruppi.SBAP_, null);
//			boolean mancante=false;
//			if (sbaps!=null && !sbaps.isEmpty()) {
//				log.info("Trovate "+sbaps.size()+" Organizzazioni 'Soprintendenza' per questo Comune");
//				for (int idSbap : sbaps) {
//					log.info("Organizzazione 'Soprintendenza': "+idSbap);
//					PaesaggioEmailSearch searchPE = new PaesaggioEmailSearch();
//					searchPE.setOrganizzazioneId(idSbap);
//					searchPE.setEnteId(idEnte);
//					List<PaesaggioEmailDTO> listaTerritoriale = commonService.searchRubricaPaesaggio(searchPE);
//					if (listaTerritoriale!=null && !listaTerritoriale.isEmpty()) {
//						log.info("Trovate email per l'Organizzazione 'Soprintendenza' (id="+idSbap+") in funzione della territorialità");
//						for(PaesaggioEmailDTO elem: listaTerritoriale)
//							destinatari.add(new DestinatarioDTO(elem));
//					}
//					else {
//						log.info("Non ci sono email per l'Organizzazione 'Soprintendenza' (id="+idSbap+") in funzione della territorialità");
//						searchPE.setEnteId(null);
//						List<PaesaggioEmailDTO> listaNonTerritoriale = commonService.searchRubricaPaesaggio(searchPE);
//						if (listaNonTerritoriale!=null && !listaNonTerritoriale.isEmpty()) {
//							log.info("Trovate email per l'Organizzazione 'Soprintendenza' (id="+idSbap+") non legate alla territorialità");
//							for(PaesaggioEmailDTO elem: listaNonTerritoriale)
//								destinatari.add(new DestinatarioDTO(elem));
//						}
//						else {
//							log.info("Non ci sono email per l'Organizzazione 'Soprintendenza' (id="+idSbap+") non legate alla territorialità");
//							Integer enteRiferimento = commonService.getEnteDiRiferimentoForOrganizzazione(idSbap);
//							if (enteRiferimento!=null) {
//								log.info("Trovato l'Ente di riferimento (id="+enteRiferimento+") per l'Organizzazione 'Soprintendenza' (id="+idSbap+")");
//								if (!TipoEnte.soprintendenza.getCodice().equals(commonService.getTipoEnte(enteRiferimento))) {
//									throw new Exception("Errore. L'ENTE di riferimento (id="+enteRiferimento+") per l'ORGANIZZAZIONE 'Soprintendenza' (id="+idSbap+") non è di tipo 'Soprintendenza (SI)'");
//								}
//								List<DestinatarioDTO> listaEmailEnte = this.getAllEmailForEnte(enteRiferimento, tipoDest);
//								if (listaEmailEnte!=null && !listaEmailEnte.isEmpty()) {
//									log.info("Trovate email per l'Ente 'Soprintendenza' di riferimento (id="+enteRiferimento+")");
//									destinatari.addAll(listaEmailEnte);
//								}
//								else {
//									log.info("Non ci sono email per l'Ente 'Soprintendenza' di riferimento (id="+enteRiferimento+")");
//									mancante=true;
//								}
//							}
//							else {
//								log.info("Non è presente l'Ente di riferimento per l'Organizzazione 'Soprintendenza' (id="+idSbap+")");
//								mancante=true;
//							}
//						}
//					}
//				}
//			}
//			else {
//				log.info("Nessuna Organizzazione 'Soprintendenza' trovata per questo Comune");
//			}
//			if (destinatari.isEmpty() || mancante==true) { // cioè   se  non ci sono   ORGANIZZAZIONI (Soprintendenza)  competenti
//					 									   // oppure se, per almeno un'ORGANIZZAZIONE (Soprintendenza), non c'è 					  l'ENTE (Soprintendenza) di riferimento
//										 				   // oppure se, per almeno un'ORGANIZZAZIONE (Soprintendenza), non ci sono email associate all'ENTE (Soprintendenza) di riferimento
//				log.info("Cerco gli Enti 'Soprintendenza' localizzati su questo Ente 'Comune' (id="+idEnte+")");
//				int provincia = commonService.findProvinciaForComune(idEnte);
//				String siglaProv = commonService.findEnteById(provincia).getCodice();
//				List<String> nomiProv = new ArrayList<>();
//				if (siglaProv.equalsIgnoreCase("BA" )) { nomiProv.add("Bari"    ); }
//				else if (siglaProv.equalsIgnoreCase("FG" )) { nomiProv.add("Foggia"  ); }
//				else if (siglaProv.equalsIgnoreCase("BR" )) { nomiProv.add("Brindisi"); }
//				else if (siglaProv.equalsIgnoreCase("LE" )) { nomiProv.add("Lecce"   ); }
//				else if (siglaProv.equalsIgnoreCase("TA" )) { nomiProv.add("Taranto" ); }
//				else if (siglaProv.equalsIgnoreCase("BT" )) { nomiProv.add("BT"); nomiProv.add("BAT"); nomiProv.add("Barletta"); nomiProv.add("Andria"); nomiProv.add("Trani"); }
//				else if (siglaProv.equalsIgnoreCase("BAT")) { nomiProv.add("BT"); nomiProv.add("BAT"); nomiProv.add("Barletta"); nomiProv.add("Andria"); nomiProv.add("Trani"); }
//				else {	throw new Exception("Errore. Ente 'Provincia': "+provincia+" non riconosciuto"); }
//
//				Set<Integer> entiSoprintendenze = new HashSet<Integer>();
//				for (String nomeProv : nomiProv) {
//					entiSoprintendenze.addAll(commonService.findSoprintendenzeByDenominazione(nomeProv));
//				}
//
//				if (!entiSoprintendenze.isEmpty()) {
//					log.info("Trovati "+entiSoprintendenze.size()+" Enti 'Soprintendenza' localizzati sull'Ente 'Comune' (id="+idEnte+")");
//					for (int enteSoprintendenza : entiSoprintendenze) {
//						log.info("Ente 'Soprintendenza': " + enteSoprintendenza);
//						List<DestinatarioDTO> listaEmailEnte = this.getAllEmailForEnte(enteSoprintendenza, tipoDest);
//						if (listaEmailEnte!=null && !listaEmailEnte.isEmpty()) {
//							log.info("Trovate email per questo Ente 'Soprintendenza'(id="+enteSoprintendenza+")");
//							destinatari.addAll(listaEmailEnte);
//						}
//						else {
//							log.info("Non ci sono email per questo Ente 'Soprintendenza'(id="+enteSoprintendenza+")");
//							String nomeEnte = commonService.findEnteById(idEnte).getNome();
//							throw new CustomOperationToAdviceException("Errore. Non è ancora stato configurato nessun indirizzo email per la Soprintendenza competente sul Comune di '"+nomeEnte+"'");
//						}
//					}
//				}
//				else {
//					log.info("Nessun Ente 'Soprintendenza' trovato localizzato sull'Ente 'Comune' (id="+idEnte+")");
//					String nomeEnte = commonService.findEnteById(idEnte).getNome();
//					throw new CustomOperationToAdviceException("Errore. Non è ancora stato configurato nessun indirizzo email per la Soprintendenza competente sul Comune di '"+nomeEnte+"'");
//				}
//			}
//		}
//		return destinatari;
	}
	
	@Override
	//@Transactional(transactionManager="txmng_common", propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public  List<DestinatarioDTO> findDestinatariForProvince(Collection<Integer> idEnti, TipoDestinatario tipoDest) throws Exception {
		return findDestinatariFromAutpae(null, idEnti, tipoDest, "/autpae/public/destinatari/findDestinatariForProvince");
//		log.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
//		List<DestinatarioDTO> destinatari = new ArrayList<>();
//		for (int idEnte : idEnti) {
//			log.info("Ente 'Comune': "+idEnte);
//			List<Integer> provs = commonService.getOrganizzazioniCompetentiOnEnti(Collections.singletonList(idEnte), props.getCodiceApplicazione(), Gruppi.ETI_, TipoOrganizzazioneSpecifica.PROVINCIA);
//			boolean mancante=false;
//			if (provs!=null && !provs.isEmpty()) {
//				log.info("Trovate "+provs.size()+" Organizzazioni 'ETI-Provincia' per questo Comune");
//				for (int idProv : provs) {
//					log.info("Organizzazione 'ETI-Provincia': "+idProv);
//					PaesaggioEmailSearch searchPE = new PaesaggioEmailSearch();
//					searchPE.setOrganizzazioneId(idProv);
//					searchPE.setEnteId(idEnte);
//					List<PaesaggioEmailDTO> listaTerritoriale = commonService.searchRubricaPaesaggio(searchPE);
//					if (listaTerritoriale!=null && !listaTerritoriale.isEmpty()) {
//						log.info("Trovate email per l'Organizzazione 'ETI-Provincia' (id="+idProv+") in funzione della territorialità");
//						for (PaesaggioEmailDTO elem : listaTerritoriale) {
//							destinatari.add(new DestinatarioDTO(elem));
//						};
//					}
//					else {
//						log.info("Non ci sono email per l'Organizzazione 'ETI-Provincia' (id="+idProv+") in funzione della territorialità");
//						searchPE.setEnteId(null);
//						List<PaesaggioEmailDTO> listaNonTerritoriale = commonService.searchRubricaPaesaggio(searchPE);
//						if (listaNonTerritoriale!=null && !listaNonTerritoriale.isEmpty()) {
//							log.info("Trovate email per l'Organizzazione 'ETI-Provincia' (id="+idProv+") non legate alla territorialità");
//							for (PaesaggioEmailDTO elem : listaNonTerritoriale) {
//								destinatari.add(new DestinatarioDTO(elem));
//							};
//						}
//						else {
//							log.info("Non ci sono email per l'Organizzazione 'ETI-Provincia' (id="+idProv+") non legate alla territorialità");
//							Integer enteRiferimento = commonService.getEnteDiRiferimentoForOrganizzazione(idProv);
//							if (enteRiferimento!=null) {
//								log.info("Trovato l'Ente di riferimento (id="+enteRiferimento+") per l'Organizzazione 'ETI-Provincia' (id="+idProv+")");
//								if (!TipoEnte.provincia.getCodice().equals(commonService.getTipoEnte(enteRiferimento))) {
//									throw new Exception("Errore. L'ENTE di riferimento (id="+enteRiferimento+") per l'ORGANIZZAZIONE 'ETI-Provincia' (id="+idProv+") non è di tipo 'Provincia ("+TipoEnte.provincia.getCodice()+")'");
//								}
//								List<DestinatarioDTO> listaEmailEnte = this.getAllEmailForEnte(enteRiferimento, tipoDest);
//								if (listaEmailEnte!=null && !listaEmailEnte.isEmpty()) {
//									log.info("Trovate email per l'Ente 'Provincia' di riferimento (id="+enteRiferimento+")");
//									destinatari.addAll(listaEmailEnte);
//								}
//								else {
//									log.info("Non ci sono email per l'Ente 'Provincia' di riferimento (id="+enteRiferimento+")");
//									mancante=true;
//								}
//							}
//							else {
//								log.info("Non è presente l'Ente di riferimento per l'Organizzazione 'ETI-Provincia' (id="+idProv+")");
//								mancante=true;
//							}
//						}
//					}
//				}
//			}
//			else {
//				log.info("Nessuna Organizzazione 'ETI-Provincia' trovata per questo Comune");
//			}
//			if (destinatari.isEmpty() || mancante==true) { // cioè   se  non ci sono   ORGANIZZAZIONI (ETI-Provincia)  competenti
//										 				   // oppure se, per almeno un'ORGANIZZAZIONE (ETI-Provincia), non c'è					     l'ENTE (Provincia) di riferimento 
//										 				   // oppure se, per almeno un'ORGANIZZAZIONE (ETI-Provincia), non ci sono email associate all'ENTE (Provincia) di riferimento
//				log.info("Cerco l'Ente 'Provincia' in cui è localizzato questo Ente 'Comune' (id="+idEnte+")");
//				int enteProvincia = commonService.findProvinciaForComune(idEnte);
//				log.info("Ente 'Provincia': " + enteProvincia);
//				List<DestinatarioDTO> listaEmailEnte = this.getAllEmailForEnte(enteProvincia, tipoDest);
//				if (listaEmailEnte!=null && !listaEmailEnte.isEmpty()) {
//					log.info("Trovate email per l'Ente 'Provincia'(id="+enteProvincia+")");
//					destinatari.addAll(listaEmailEnte);
//				}
//				else {
//					log.info("Non ci sono email per l'Ente 'Provincia'(id="+enteProvincia+")");
//					String nomeEnte = commonService.findEnteById(idEnte).getNome();
//					throw new CustomOperationToAdviceException("Errore. Non è ancora stato configurato nessun indirizzo email per la Provincia competente sul Comune di '"+nomeEnte+"'");
//				}
//			}
//		}
//		return destinatari;
	}
	
	@Override
	//@Transactional(transactionManager="txmng_common", propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public  List<DestinatarioDTO> findDestinatariForComuni(Collection<Integer> idEnti, TipoDestinatario tipoDest) throws Exception {	// "Enti Comunali"
		return findDestinatariFromAutpae(null, idEnti, tipoDest, "/autpae/public/destinatari/findDestinatariForComuni");
//		log.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
//		List<DestinatarioDTO> destinatari = new ArrayList<>();
//		for (int idEnte : idEnti) {
//			log.info("Ente 'Comune': "+idEnte);
//			Set<Integer> orgs = new HashSet<Integer>();
//			orgs.addAll(commonService.getOrganizzazioniCompetentiOnEnti(Collections.singletonList(idEnte), props.getCodiceApplicazione(), Gruppi.ETI_, TipoOrganizzazioneSpecifica.COMUNE));
//			orgs.addAll(commonService.getOrganizzazioniCompetentiOnEnti(Collections.singletonList(idEnte), props.getCodiceApplicazione(), Gruppi.ETI_, TipoOrganizzazioneSpecifica.UNIONE));
//			orgs.addAll(commonService.getOrganizzazioniCompetentiOnEnti(Collections.singletonList(idEnte), props.getCodiceApplicazione(), Gruppi.ETI_, TipoOrganizzazioneSpecifica.ASSOCIAZIONE));
//			boolean mancante=false;
//			if (orgs!=null && !orgs.isEmpty()) {
//				log.info("Trovate "+orgs.size()+" Organizzazioni 'ETI C/U/A' per questo Comune");
//				for (int idOrg : orgs) {
//					log.info("Organizzazione 'ETI C/U/A': "+idOrg);
//					PaesaggioEmailSearch searchPE = new PaesaggioEmailSearch();
//					searchPE.setOrganizzazioneId(idOrg);
//					searchPE.setEnteId(idEnte);
//					List<PaesaggioEmailDTO> listaTerritoriale = commonService.searchRubricaPaesaggio(searchPE);
//					if (listaTerritoriale!=null && !listaTerritoriale.isEmpty()) {
//						log.info("Trovate email per l'Organizzazione 'ETI C/U/A' (id="+idOrg+") in funzione della territorialità");
//						for (PaesaggioEmailDTO elem : listaTerritoriale) {
//							destinatari.add(new DestinatarioDTO(elem));
//						};
//					}
//					else {
//						log.info("Non ci sono email per l'Organizzazione 'ETI C/U/A' (id="+idOrg+") in funzione della territorialità");
//						searchPE.setEnteId(null);
//						List<PaesaggioEmailDTO> listaNonTerritoriale = commonService.searchRubricaPaesaggio(searchPE);
//						if (listaNonTerritoriale!=null && !listaNonTerritoriale.isEmpty()) {
//							log.info("Trovate email per l'Organizzazione 'ETI C/U/A' (id="+idOrg+") non legate alla territorialità");
//							for (PaesaggioEmailDTO elem : listaNonTerritoriale) {
//								destinatari.add(new DestinatarioDTO(elem));
//							};
//						}
//						else {
//							log.info("Non ci sono email per l'Organizzazione 'ETI C/U/A' (id="+idOrg+") non legate alla territorialità");
//							Integer enteRiferimento = commonService.getEnteDiRiferimentoForOrganizzazione(idOrg);
//							if (enteRiferimento!=null) {
//								log.info("Trovato l'Ente di riferimento (id="+enteRiferimento+") per l'Organizzazione 'ETI C/U/A' (id="+idOrg+")");
//								String tipoEnte = commonService.getTipoEnte(enteRiferimento);
//								if ((!TipoEnte.comune				 .getCodice().equals(tipoEnte)) &&  
//								    (!TipoEnte.unione_di_comuni		 .getCodice().equals(tipoEnte)) &&
//								    (!TipoEnte.associazione_di_comuni.getCodice().equals(tipoEnte))) {
//									throw new Exception("Errore. L'ENTE di riferimento (id="+enteRiferimento+") per l'ORGANIZZAZIONE 'ETI C/U/A' (id="+idOrg+") non è di tipo 'Comune/Unione/Associazione (CO/UC/AC)'");
//								}
//								List<DestinatarioDTO> listaEmailEnte = this.getAllEmailForEnte(enteRiferimento, tipoDest);
//								if (listaEmailEnte!=null && !listaEmailEnte.isEmpty()) {
//									log.info("Trovate email per l'Ente 'CO/UC/AC' di riferimento (id="+enteRiferimento+")");
//									destinatari.addAll(listaEmailEnte);
//								}
//								else {
//									log.info("Non ci sono email per l'Ente 'CO/UC/AC' di riferimento (id="+enteRiferimento+")");
//									mancante=true;
//								}
//							}
//							else {
//								log.info("Non è presente l'Ente di riferimento per l'Organizzazione 'ETI C/U/A' (id="+idOrg+")");
//								mancante=true;
//							}
//						}
//					}
//				}
//			}
//			else {
//				log.info("Nessuna Organizzazione 'ETI C/U/A' trovata per questo Comune");
//			}
//			if (destinatari.isEmpty() || mancante==true) { // cioè   se  non ci sono   ORGANIZZAZIONI (ETI C/U/A)  competenti
//										 				   // oppure se, per almeno un'ORGANIZZAZIONE (ETI C/U/A), non c'è					     l'ENTE (CO/UC/AC) di riferimento 
//										 				   // oppure se, per almeno un'ORGANIZZAZIONE (ETI C/U/A), non ci sono email associate all'ENTE (CO/UC/AC) di riferimento
//				log.info("Cerco le email per l'Ente 'Comune' (id="+idEnte+")");
//				List<DestinatarioDTO> listaEmailEnte = this.getAllEmailForEnte(idEnte, tipoDest);
//				if (listaEmailEnte!=null && !listaEmailEnte.isEmpty()) {
//					log.info("Trovate email per l'Ente 'Comune'(id="+idEnte+")");
//					destinatari.addAll(listaEmailEnte);
//				}
//				else {
//					log.info("Non ci sono email per l'Ente 'Comune'(id="+idEnte+")");
//					String nomeEnte = commonService.findEnteById(idEnte).getNome();
//					throw new CustomOperationToAdviceException("Errore. Non è ancora stato configurato nessun indirizzo email per il Comune di '"+nomeEnte+"'");
//				}
//			}
//		}
//		return destinatari;
	}

	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, readOnly=false, timeout=60000, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<DestinatarioDTO> findDestinatariNotifica(UUID idPratica, Integer idOrganizzazione, Gruppi gruppo, boolean perendiReferenti, boolean prendiAdmin) throws Exception
	{
		if(!gruppo.equals(Gruppi.REG_) && !gruppo.equals(Gruppi.ED_))
		{
			log.error("Il metodo 'findDestinatariNotifica' è valido solo per Ente delegato e regione!");
			throw new Exception("Il metodo 'findDestinatariNotifica' è valido solo per Ente delegato e regione!");
		}
		List<DestinatarioDTO> email = new ArrayList<DestinatarioDTO>();
		String jwt = client.getBatchUser().getAuthorization();
		String codiceApplicazione = props.getCodiceApplicazione();
		String codiceBase = gruppo.name() + idOrganizzazione;
		//cerco i dirigenti
//		List<UtenteGruppo> users = Arrays.asList(profileManager.getUtentiGruppo(jwt, codiceApplicazione, codiceBase+"_D").getPayload());
		List<AssUtenteGruppo> users = Arrays.asList(profileManager.utentiInGruppi(jwt, codiceApplicazione, Arrays.asList(codiceBase+"_D")).getPayload());
		if(users != null && !users.isEmpty())
			email = users.stream().filter(p-> StringUtil.isNotBlank(p.getMail())).map(DestinatarioDTO::new).collect(Collectors.toList());
		if(prendiAdmin)
		{
//			users = Arrays.asList(profileManager.getUtentiGruppo(jwt, codiceApplicazione, codiceBase+"_A").getPayload());
			users = Arrays.asList(profileManager.utentiInGruppi(jwt, codiceApplicazione, Arrays.asList(codiceBase+"_A")).getPayload());
			if(users != null && !users.isEmpty())
			{
				List<DestinatarioDTO> temp =  users.stream().filter(p-> StringUtil.isNotBlank(p.getMail())).map(DestinatarioDTO::new).collect(Collectors.toList());
				email = VarieUtils.eliminaDuplicati(email, temp);
			}
		}
		//cerco sul nostro db il funzionario ed il rup assegnato alla pratica
		List<String> assegnatariList = assegnamentoRepository.findRupEFunzionrio(idPratica, idOrganizzazione.longValue());
		if(assegnatariList != null && !assegnatariList.isEmpty())
		{
			Set<String> assegnatari = new HashSet<String>(assegnatariList);
			List<DestinatarioDTO> destinatariAssegnatari = new ArrayList<DestinatarioDTO>();
			for(String user: assegnatari)
			{
				UtenteGruppo[] u = profileManager.getUtentiGruppo(jwt, codiceApplicazione, null, user).getPayload();
				if(u != null && u[0] != null && StringUtil.isNotBlank(u[0].getEmail()))
					destinatariAssegnatari.add(new DestinatarioDTO(u[0]));
				else
					log.warn("ATTENZIONE: {} non trovato sul profile manager", user);
			}
			email = VarieUtils.eliminaDuplicati(email, destinatariAssegnatari);
		}
		//cerco il richiedente (altri titolari, ecnico incaricato no?) se prendireferenti è settato a true
		if(perendiReferenti)
		{
			email = VarieUtils.eliminaDuplicati(email, referentiService.findDestinatariPratica(idPratica));
		}
		return email;
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, readOnly=false, timeout=60000, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<DestinatarioDTO> findDestinatariNotifica(UUID idPratica, Integer idOrganizzazione, Gruppi gruppo) throws Exception
	{
		return findDestinatariNotifica(idPratica, idOrganizzazione, gruppo, true, false);
	}

	@Override
	@Transactional(transactionManager="txmng_common", propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public List<DestinatarioDTO> findDestinatarioCommissioneLocale(Long idEnteDelegato, Date dataValidita) throws Exception
	{
		return commonService.findEmailCommissione(idEnteDelegato, dataValidita);
	}
	
	
	
	private List<DestinatarioDTO> findDestinatariFromAutpae(Integer idOrganizzazione,
			Collection<Integer> idEnti, TipoDestinatario tipoDest,String pathUrl) throws Exception {
		final Map<String, Object> parameters = new HashMap<>();
		if(idOrganizzazione!=null) {
			parameters.put("idOrganizzazione", idOrganizzazione);
		}
		parameters.put("idEnti", ListUtil.isNotEmpty(idEnti)?idEnti.stream().map(id->id.toString()).collect(Collectors.joining(",")):"");
		parameters.put("tipoDestinatario", tipoDest.name());
		final MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		final String url = IHttpClientService.buildUrl(
				StringUtil.concateneString(this.autpaeHost,pathUrl),parameters);
		final HttpEntity<?> entity = new HttpEntity<>(headers);
		List<DestinatarioDTO> retList = new ArrayList<DestinatarioDTO>();
		try {
			final ResponseEntity<TipologicaDestinarioDto[]> response = this.restTemplateDef.exchange(url,
					HttpMethod.GET, entity, TipologicaDestinarioDto[].class);
			TipologicaDestinarioDto[] arrayDest = response.getBody();
			for (TipologicaDestinarioDto destAutpae : arrayDest) {
				retList.add(new DestinatarioDTO(destAutpae.getNome(), destAutpae.getEmail(), destAutpae.isPec(),
						destAutpae.getTipo()));
			}
		} catch (HttpStatusCodeException e) {
			String messageFault = e.getResponseBodyAsString();
			log.error("Errore durante la call ad autpae per il retrieve al path {} idOrg {} idEnti {} messaggio{} " ,pathUrl,idOrganizzazione,idEnti,messageFault,e);
			throw new CustomOperationToAdviceException(messageFault);
		}catch (Exception e) {
			log.error("Errore durante la call ad autpae per il retrieve al path {} idOrg {} idEnti {}  " ,pathUrl,idOrganizzazione,idEnti,e);
			throw new CustomOperationToAdviceException("Impossibile effettuare il retrieve dei destinatari dal servizio di trasmissioni!!!");
		}
		return retList;
	}
	
}
