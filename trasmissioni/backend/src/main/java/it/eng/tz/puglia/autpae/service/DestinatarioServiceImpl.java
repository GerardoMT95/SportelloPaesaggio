package it.eng.tz.puglia.autpae.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dto.TipologicaDTO;
import it.eng.tz.puglia.autpae.dto.TipologicaDestinatarioDTO;
import it.eng.tz.puglia.autpae.entity.DestinatarioDTO;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.enumeratori.TipoDestinatario;
import it.eng.tz.puglia.autpae.enumeratori.TipoOrganizzazioneSpecifica;
import it.eng.tz.puglia.autpae.repository.DestinatarioFullRepository;
import it.eng.tz.puglia.autpae.search.DestinatarioSearch;
import it.eng.tz.puglia.autpae.service.interfacce.DestinatarioService;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.remote.dto.PaesaggioEmailDTO;
import it.eng.tz.puglia.servizi_esterni.remote.search.PaesaggioEmailSearch;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.CommonService;
import it.eng.tz.puglia.servizi_esterni.remote.utility.TipoEnte;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class DestinatarioServiceImpl implements DestinatarioService {

	private static final Logger log = LoggerFactory.getLogger(DestinatarioServiceImpl.class);
	
	@Autowired	private DestinatarioFullRepository repository;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository
		
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 		  List<DestinatarioDTO> select() 					  	  throws Exception { return repository.select(); 	   }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public long 						 	count (DestinatarioSearch filter) throws Exception { return repository.count (filter); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 			   DestinatarioDTO  find  (Long pk) 			  	  throws Exception { return repository.find  (pk); 	   }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public Long 						 	insert(DestinatarioDTO entity) 	  throws Exception { return repository.insert(entity); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 	update(DestinatarioDTO entity)	  throws Exception { return repository.update(entity); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 	delete(DestinatarioSearch search) throws Exception { return repository.delete(search); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public PaginatedList<DestinatarioDTO> search(DestinatarioSearch filter) throws Exception { return repository.search(filter); }

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FullRepository

	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public List<DestinatarioDTO> searchByIdCorrispondenzaAndEmails(Long idCorrispondenza, List<String> emails) throws Exception {
																																  return 			repository.searchByIdCorrispondenzaAndEmails(	  idCorrispondenza, 			 emails);				  }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int				   updateFieldPec					(List<Long> listaId, Boolean pec) 			 throws Exception {
																																  return 			repository.updateFieldPec					(			listaId, 		 pec);							  }
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Altri Metodi
	
	
	@Autowired private CommonService commonService;
	@Autowired private ApplicationProperties props;
	
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	private List<TipologicaDestinatarioDTO> getAllEmailForEnte(int idEnte, TipoDestinatario tipoDest) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		List<TipologicaDestinatarioDTO> destinatari = new ArrayList<>();
		
		TipologicaDTO listaBase = commonService.searchAutomatizzataRubricaIstituzionale(idEnte, commonService.getIdApplicazione(props.getCodiceApplicazioneProfile()));
		if (listaBase!=null) {
			String denominazioneEnte = commonService.findEnteById(idEnte).getDescrizione();
			if (StringUtil.isNotEmpty(listaBase.getCodice())) {	// campo "pec"
				destinatari.add(new TipologicaDestinatarioDTO(listaBase.getCodice(), true , denominazioneEnte, tipoDest));
			}
			if (StringUtil.isNotEmpty(listaBase.getLabel ())) { // campo "mail"
				destinatari.add(new TipologicaDestinatarioDTO(listaBase.getLabel (), false, denominazioneEnte, tipoDest));
			}
		}
		PaesaggioEmailSearch searchPE = new PaesaggioEmailSearch();
		searchPE.setEnteId(idEnte);
		List<PaesaggioEmailDTO> listaEstesa = commonService.searchRubricaPaesaggio(searchPE);
		if (listaEstesa!=null && !listaEstesa.isEmpty()) {
			listaEstesa.forEach(elem->{
				destinatari.add(new TipologicaDestinatarioDTO(elem.getEmail(), elem.getPec()!=null ? elem.getPec() : false, elem.getDenominazione(), tipoDest));
			});
		}
		return destinatari;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public  List<TipologicaDestinatarioDTO> findDestinatariForEnteDelegato(int idOrganizzazione, Collection<Integer> idEnti, TipoDestinatario tipoDest) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		List<TipologicaDestinatarioDTO> destinatari = new ArrayList<>();
		for (int idEnte : idEnti) {
			log.info("Ente 'Comune': "+idEnte);
			PaesaggioEmailSearch searchPE = new PaesaggioEmailSearch();
			searchPE.setOrganizzazioneId(idOrganizzazione);
			searchPE.setEnteId(idEnte);
			List<PaesaggioEmailDTO> listaTerritoriale = commonService.searchRubricaPaesaggio(searchPE);
			if (listaTerritoriale!=null && !listaTerritoriale.isEmpty()) {
				log.info("Trovate email per l'Ente (id="+idOrganizzazione+") in funzione della territorialità");
				listaTerritoriale.forEach(elem->{
					destinatari.add(new TipologicaDestinatarioDTO(elem.getEmail(), elem.getPec()!=null ? elem.getPec() : false, elem.getDenominazione(), tipoDest));
				});
			}
			else {
				log.info("Non ci sono email per l'Ente  (id="+idOrganizzazione+") in funzione della territorialità");
				searchPE.setEnteId(null);
				List<PaesaggioEmailDTO> listaNonTerritoriale = commonService.searchRubricaPaesaggio(searchPE);
				if (listaNonTerritoriale!=null && !listaNonTerritoriale.isEmpty()) {
					log.info("Trovate email per l'Ente  (id="+idOrganizzazione+") non legate alla territorialità");
					listaNonTerritoriale.forEach(elem->{
						destinatari.add(new TipologicaDestinatarioDTO(elem.getEmail(), elem.getPec()!=null ? elem.getPec() : false, elem.getDenominazione(), tipoDest));
					});
				}
				else {
					String nomeEnte = commonService.getDenominazioneOrganizzazione(idOrganizzazione);
					throw new CustomOperationToAdviceException("Errore. Non è ancora stato configurato nessun indirizzo email per l'ENTE '"+nomeEnte+"'");
				}
			}
		}
		return destinatari;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public  List<TipologicaDestinatarioDTO> findDestinatariForRegione(Collection<Integer> idEnti, TipoDestinatario tipoDest) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		List<TipologicaDestinatarioDTO> destinatari = new ArrayList<>();
		for (int idEnte : idEnti) {
			log.info("Ente 'Comune': "+idEnte);
			List<Integer> regs = commonService.getOrganizzazioniCompetentiOnEnti(Collections.singletonList(idEnte), props.getCodiceApplicazione(), Gruppi.REG_, null);
			boolean mancante=false;
			if (regs!=null && !regs.isEmpty()) {
				log.info("Trovate "+regs.size()+" Organizzazioni 'Regione' per questo Comune");
				for (int idReg : regs) {
					log.info("Organizzazione 'Regione': "+idReg);
					PaesaggioEmailSearch searchPE = new PaesaggioEmailSearch();
					searchPE.setOrganizzazioneId(idReg);
					searchPE.setEnteId(idEnte);
					List<PaesaggioEmailDTO> listaTerritoriale = commonService.searchRubricaPaesaggio(searchPE);
					if (listaTerritoriale!=null && !listaTerritoriale.isEmpty()) {
						log.info("Trovate email per l'Organizzazione 'Regione' (id="+idReg+") in funzione della territorialità");
						listaTerritoriale.forEach(elem->{
							destinatari.add(new TipologicaDestinatarioDTO(elem.getEmail(), elem.getPec()!=null ? elem.getPec() : false, elem.getDenominazione(), tipoDest));
						});
					}
					else {
						log.info("Non ci sono email per l'Organizzazione 'Regione' (id="+idReg+") in funzione della territorialità");
						searchPE.setEnteId(null);
						List<PaesaggioEmailDTO> listaNonTerritoriale = commonService.searchRubricaPaesaggio(searchPE);
						if (listaNonTerritoriale!=null && !listaNonTerritoriale.isEmpty()) {
							log.info("Trovate email per l'Organizzazione 'Regione' (id="+idReg+") non legate alla territorialità");
							listaNonTerritoriale.forEach(elem->{
								destinatari.add(new TipologicaDestinatarioDTO(elem.getEmail(), elem.getPec()!=null ? elem.getPec() : false, elem.getDenominazione(), tipoDest));
							});
						}
						else {
							log.info("Non ci sono email per l'Organizzazione 'Regione' (id="+idReg+") non legate alla territorialità");
							mancante=true;
						}
					}
				}
			}
			else {
				log.info("Nessuna Organizzazione 'Regione' trovata per questo Comune");
			}
			if (destinatari.isEmpty() || mancante==true) { // cioè   se  non ci sono   ORGANIZZAZIONI (Regione)  competenti
														   // oppure se, per almeno un'ORGANIZZAZIONE (Regione), non ci sono email associate (né territoriali, né non territoriali)
				log.info("Cerco l'Ente 'Regione'");
				int enteRegione = commonService.findRegione().getId();
				log.info("Ente 'Regione': " + enteRegione);
				List<TipologicaDestinatarioDTO> listaEmailEnte = this.getAllEmailForEnte(enteRegione, tipoDest);
				if (listaEmailEnte!=null && !listaEmailEnte.isEmpty()) {
					log.info("Trovate email per l'Ente 'Regione'(id="+enteRegione+")");
					destinatari.addAll(listaEmailEnte);
				}
				else {
					log.info("Non ci sono email per l'Ente 'Regione'(id="+enteRegione+")");
					throw new CustomOperationToAdviceException("Errore. Non è ancora stato configurato nessun indirizzo email per la Regione Puglia");
				}
			}
		}
		return destinatari;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public  List<TipologicaDestinatarioDTO> findDestinatariForSoprintendenze(Collection<Integer> idEnti, TipoDestinatario tipoDest) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		List<TipologicaDestinatarioDTO> destinatari = new ArrayList<>();
		for (int idEnte : idEnti) {
			log.info("Ente 'Comune': "+idEnte);
			List<Integer> sbaps = commonService.getOrganizzazioniCompetentiOnEnti(Collections.singletonList(idEnte), props.getCodiceApplicazione(), Gruppi.SBAP_, null);
			boolean mancante=false;
			if (sbaps!=null && !sbaps.isEmpty()) {
				log.info("Trovate "+sbaps.size()+" Organizzazioni 'Soprintendenza' per questo Comune");
				for (int idSbap : sbaps) {
					log.info("Organizzazione 'Soprintendenza': "+idSbap);
					PaesaggioEmailSearch searchPE = new PaesaggioEmailSearch();
					searchPE.setOrganizzazioneId(idSbap);
					searchPE.setEnteId(idEnte);
					List<PaesaggioEmailDTO> listaTerritoriale = commonService.searchRubricaPaesaggio(searchPE);
					if (listaTerritoriale!=null && !listaTerritoriale.isEmpty()) {
						log.info("Trovate email per l'Organizzazione 'Soprintendenza' (id="+idSbap+") in funzione della territorialità");
						for (PaesaggioEmailDTO elem : listaTerritoriale) {
							destinatari.add(new TipologicaDestinatarioDTO(elem.getEmail(), elem.getPec()!=null ? elem.getPec() : false, elem.getDenominazione(), tipoDest));
						};
					}
					else {
						log.info("Non ci sono email per l'Organizzazione 'Soprintendenza' (id="+idSbap+") in funzione della territorialità");
						searchPE.setEnteId(null);
						List<PaesaggioEmailDTO> listaNonTerritoriale = commonService.searchRubricaPaesaggio(searchPE);
						if (listaNonTerritoriale!=null && !listaNonTerritoriale.isEmpty()) {
							log.info("Trovate email per l'Organizzazione 'Soprintendenza' (id="+idSbap+") non legate alla territorialità");
							for (PaesaggioEmailDTO elem : listaNonTerritoriale) {
								destinatari.add(new TipologicaDestinatarioDTO(elem.getEmail(), elem.getPec()!=null ? elem.getPec() : false, elem.getDenominazione(), tipoDest));
							};
						}
						else {
							log.info("Non ci sono email per l'Organizzazione 'Soprintendenza' (id="+idSbap+") non legate alla territorialità");
							Integer enteRiferimento = commonService.getEnteDiRiferimentoForOrganizzazione(idSbap);
							if (enteRiferimento!=null) {
								log.info("Trovato l'Ente di riferimento (id="+enteRiferimento+") per l'Organizzazione 'Soprintendenza' (id="+idSbap+")");
								if (!TipoEnte.soprintendenza.getCodice().equals(commonService.getTipoEnte(enteRiferimento))) {
									throw new Exception("Errore. L'ENTE di riferimento (id="+enteRiferimento+") per l'ORGANIZZAZIONE 'Soprintendenza' (id="+idSbap+") non è di tipo 'Soprintendenza (SI)'");
								}
								List<TipologicaDestinatarioDTO> listaEmailEnte = this.getAllEmailForEnte(enteRiferimento, tipoDest);
								if (listaEmailEnte!=null && !listaEmailEnte.isEmpty()) {
									log.info("Trovate email per l'Ente 'Soprintendenza' di riferimento (id="+enteRiferimento+")");
									destinatari.addAll(listaEmailEnte);
								}
								else {
									log.info("Non ci sono email per l'Ente 'Soprintendenza' di riferimento (id="+enteRiferimento+")");
									mancante=true;
								}
							}
							else {
								log.info("Non è presente l'Ente di riferimento per l'Organizzazione 'Soprintendenza' (id="+idSbap+")");
								mancante=true;
							}
						}
					}
				}
			}
			else {
				log.info("Nessuna Organizzazione 'Soprintendenza' trovata per questo Comune");
			}
			if (destinatari.isEmpty() || mancante==true) { // cioè   se  non ci sono   ORGANIZZAZIONI (Soprintendenza)  competenti
					 									   // oppure se, per almeno un'ORGANIZZAZIONE (Soprintendenza), non c'è 					  l'ENTE (Soprintendenza) di riferimento
										 				   // oppure se, per almeno un'ORGANIZZAZIONE (Soprintendenza), non ci sono email associate all'ENTE (Soprintendenza) di riferimento
				log.info("Cerco gli Enti 'Soprintendenza' localizzati su questo Ente 'Comune' (id="+idEnte+")");
				int provincia = commonService.findProvinciaForComune(idEnte);
				String siglaProv = commonService.findEnteById(provincia).getCodice();
				List<String> nomiProv = new ArrayList<>();
				if (siglaProv.equalsIgnoreCase("BA" )) { nomiProv.add("Bari"    ); }
				else if (siglaProv.equalsIgnoreCase("FG" )) { nomiProv.add("Foggia"  ); }
				else if (siglaProv.equalsIgnoreCase("BR" )) { nomiProv.add("Brindisi"); }
				else if (siglaProv.equalsIgnoreCase("LE" )) { nomiProv.add("Lecce"   ); }
				else if (siglaProv.equalsIgnoreCase("TA" )) { nomiProv.add("Taranto" ); }
				else if (siglaProv.equalsIgnoreCase("BT" )) { nomiProv.add("BT"); nomiProv.add("BAT"); nomiProv.add("Barletta"); nomiProv.add("Andria"); nomiProv.add("Trani"); }
				else if (siglaProv.equalsIgnoreCase("BAT")) { nomiProv.add("BT"); nomiProv.add("BAT"); nomiProv.add("Barletta"); nomiProv.add("Andria"); nomiProv.add("Trani"); }
				else {	throw new Exception("Errore. Ente 'Provincia': "+provincia+" non riconosciuto"); }

				Set<Integer> entiSoprintendenze = new HashSet<Integer>();
				for (String nomeProv : nomiProv) {
					entiSoprintendenze.addAll(commonService.findSoprintendenzeByDenominazione(nomeProv));
				}

				if (!entiSoprintendenze.isEmpty()) {
					log.info("Trovati "+entiSoprintendenze.size()+" Enti 'Soprintendenza' localizzati sull'Ente 'Comune' (id="+idEnte+")");
					for (int enteSoprintendenza : entiSoprintendenze) {
						log.info("Ente 'Soprintendenza': " + enteSoprintendenza);
						List<TipologicaDestinatarioDTO> listaEmailEnte = this.getAllEmailForEnte(enteSoprintendenza, tipoDest);
						if (listaEmailEnte!=null && !listaEmailEnte.isEmpty()) {
							log.info("Trovate email per questo Ente 'Soprintendenza'(id="+enteSoprintendenza+")");
							destinatari.addAll(listaEmailEnte);
						}
						else {
							log.info("Non ci sono email per questo Ente 'Soprintendenza'(id="+enteSoprintendenza+")");
							String nomeEnte = commonService.findEnteById(idEnte).getNome();
							throw new CustomOperationToAdviceException("Errore. Non è ancora stato configurato nessun indirizzo email per la Soprintendenza competente sul Comune di '"+nomeEnte+"'");
						}
					}
				}
				else {
					log.info("Nessun Ente 'Soprintendenza' trovato localizzato sull'Ente 'Comune' (id="+idEnte+")");
					String nomeEnte = commonService.findEnteById(idEnte).getNome();
					throw new CustomOperationToAdviceException("Errore. Non è ancora stato configurato nessun indirizzo email per la Soprintendenza competente sul Comune di '"+nomeEnte+"'");
				}
			}
		}
		return destinatari;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public  List<TipologicaDestinatarioDTO> findDestinatariForProvince(Collection<Integer> idEnti, TipoDestinatario tipoDest) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		List<TipologicaDestinatarioDTO> destinatari = new ArrayList<>();
		for (int idEnte : idEnti) {
			log.info("Ente 'Comune': "+idEnte);
			List<Integer> provs = commonService.getOrganizzazioniCompetentiOnEnti(Collections.singletonList(idEnte), props.getCodiceApplicazione(), Gruppi.ETI_, TipoOrganizzazioneSpecifica.PROVINCIA);
			boolean mancante=false;
			if (provs!=null && !provs.isEmpty()) {
				log.info("Trovate "+provs.size()+" Organizzazioni 'ETI-Provincia' per questo Comune");
				for (int idProv : provs) {
					log.info("Organizzazione 'ETI-Provincia': "+idProv);
					PaesaggioEmailSearch searchPE = new PaesaggioEmailSearch();
					searchPE.setOrganizzazioneId(idProv);
					searchPE.setEnteId(idEnte);
					List<PaesaggioEmailDTO> listaTerritoriale = commonService.searchRubricaPaesaggio(searchPE);
					if (listaTerritoriale!=null && !listaTerritoriale.isEmpty()) {
						log.info("Trovate email per l'Organizzazione 'ETI-Provincia' (id="+idProv+") in funzione della territorialità");
						for (PaesaggioEmailDTO elem : listaTerritoriale) {
							destinatari.add(new TipologicaDestinatarioDTO(elem.getEmail(), elem.getPec()!=null ? elem.getPec() : false, elem.getDenominazione(), tipoDest));
						};
					}
					else {
						log.info("Non ci sono email per l'Organizzazione 'ETI-Provincia' (id="+idProv+") in funzione della territorialità");
						searchPE.setEnteId(null);
						List<PaesaggioEmailDTO> listaNonTerritoriale = commonService.searchRubricaPaesaggio(searchPE);
						if (listaNonTerritoriale!=null && !listaNonTerritoriale.isEmpty()) {
							log.info("Trovate email per l'Organizzazione 'ETI-Provincia' (id="+idProv+") non legate alla territorialità");
							for (PaesaggioEmailDTO elem : listaNonTerritoriale) {
								destinatari.add(new TipologicaDestinatarioDTO(elem.getEmail(), elem.getPec()!=null ? elem.getPec() : false, elem.getDenominazione(), tipoDest));
							};
						}
						else {
							log.info("Non ci sono email per l'Organizzazione 'ETI-Provincia' (id="+idProv+") non legate alla territorialità");
							Integer enteRiferimento = commonService.getEnteDiRiferimentoForOrganizzazione(idProv);
							if (enteRiferimento!=null) {
								log.info("Trovato l'Ente di riferimento (id="+enteRiferimento+") per l'Organizzazione 'ETI-Provincia' (id="+idProv+")");
								if (!TipoEnte.provincia.getCodice().equals(commonService.getTipoEnte(enteRiferimento))) {
									throw new Exception("Errore. L'ENTE di riferimento (id="+enteRiferimento+") per l'ORGANIZZAZIONE 'ETI-Provincia' (id="+idProv+") non è di tipo 'Provincia ("+TipoEnte.provincia.getCodice()+")'");
								}
								List<TipologicaDestinatarioDTO> listaEmailEnte = this.getAllEmailForEnte(enteRiferimento, tipoDest);
								if (listaEmailEnte!=null && !listaEmailEnte.isEmpty()) {
									log.info("Trovate email per l'Ente 'Provincia' di riferimento (id="+enteRiferimento+")");
									destinatari.addAll(listaEmailEnte);
								}
								else {
									log.info("Non ci sono email per l'Ente 'Provincia' di riferimento (id="+enteRiferimento+")");
									mancante=true;
								}
							}
							else {
								log.info("Non è presente l'Ente di riferimento per l'Organizzazione 'ETI-Provincia' (id="+idProv+")");
								mancante=true;
							}
						}
					}
				}
			}
			else {
				log.info("Nessuna Organizzazione 'ETI-Provincia' trovata per questo Comune");
			}
			if (destinatari.isEmpty() || mancante==true) { // cioè   se  non ci sono   ORGANIZZAZIONI (ETI-Provincia)  competenti
										 				   // oppure se, per almeno un'ORGANIZZAZIONE (ETI-Provincia), non c'è					     l'ENTE (Provincia) di riferimento 
										 				   // oppure se, per almeno un'ORGANIZZAZIONE (ETI-Provincia), non ci sono email associate all'ENTE (Provincia) di riferimento
				log.info("Cerco l'Ente 'Provincia' in cui è localizzato questo Ente 'Comune' (id="+idEnte+")");
				int enteProvincia = commonService.findProvinciaForComune(idEnte);
				log.info("Ente 'Provincia': " + enteProvincia);
				List<TipologicaDestinatarioDTO> listaEmailEnte = this.getAllEmailForEnte(enteProvincia, tipoDest);
				if (listaEmailEnte!=null && !listaEmailEnte.isEmpty()) {
					log.info("Trovate email per l'Ente 'Provincia'(id="+enteProvincia+")");
					destinatari.addAll(listaEmailEnte);
				}
				else {
					log.info("Non ci sono email per l'Ente 'Provincia'(id="+enteProvincia+")");
					String nomeEnte = commonService.findEnteById(idEnte).getNome();
					throw new CustomOperationToAdviceException("Errore. Non è ancora stato configurato nessun indirizzo email per la Provincia competente sul Comune di '"+nomeEnte+"'");
				}
			}
		}
		return destinatari;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public  List<TipologicaDestinatarioDTO> findDestinatariForComuni(Collection<Integer> idEnti, TipoDestinatario tipoDest) throws Exception {	// "Enti Comunali"
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		List<TipologicaDestinatarioDTO> destinatariGlob = new ArrayList<>();
		for (int idEnte : idEnti) {
			log.info("Ente 'Comune': "+idEnte);
			List<TipologicaDestinatarioDTO> destinatari = new ArrayList<>();
			Set<Integer> orgs = new HashSet<Integer>();
			orgs.addAll(commonService.getOrganizzazioniCompetentiOnEnti(Collections.singletonList(idEnte), props.getCodiceApplicazione(), Gruppi.ETI_, TipoOrganizzazioneSpecifica.COMUNE));
			orgs.addAll(commonService.getOrganizzazioniCompetentiOnEnti(Collections.singletonList(idEnte), props.getCodiceApplicazione(), Gruppi.ETI_, TipoOrganizzazioneSpecifica.UNIONE));
			//orgs.addAll(commonService.getOrganizzazioniCompetentiOnEnti(Collections.singletonList(idEnte), props.getCodiceApplicazione(), Gruppi.ETI_, TipoOrganizzazioneSpecifica.ASSOCIAZIONE));
			boolean mancante=false;
			if (orgs!=null && !orgs.isEmpty()) {
				log.info("Trovate "+orgs.size()+" Organizzazioni 'ETI C/U/A' per questo Comune");
				for (int idOrg : orgs) {
					log.info("Organizzazione 'ETI C/U/A': "+idOrg);
					PaesaggioEmailSearch searchPE = new PaesaggioEmailSearch();
					searchPE.setOrganizzazioneId(idOrg);
					searchPE.setEnteId(idEnte);
					List<PaesaggioEmailDTO> listaTerritoriale = commonService.searchRubricaPaesaggio(searchPE);
					if (listaTerritoriale!=null && !listaTerritoriale.isEmpty()) {
						log.info("Trovate email per l'Organizzazione 'ETI C/U/A' (id="+idOrg+") in funzione della territorialità");
						for (PaesaggioEmailDTO elem : listaTerritoriale) {
							destinatari.add(new TipologicaDestinatarioDTO(elem.getEmail(), elem.getPec()!=null ? elem.getPec() : false, elem.getDenominazione(), tipoDest));
						};
					}
					else {
						log.info("Non ci sono email per l'Organizzazione 'ETI C/U/A' (id="+idOrg+") in funzione della territorialità");
						searchPE.setEnteId(null);
						List<PaesaggioEmailDTO> listaNonTerritoriale = commonService.searchRubricaPaesaggio(searchPE);
						if (listaNonTerritoriale!=null && !listaNonTerritoriale.isEmpty()) {
							log.info("Trovate email per l'Organizzazione 'ETI C/U/A' (id="+idOrg+") non legate alla territorialità");
							for (PaesaggioEmailDTO elem : listaNonTerritoriale) {
								destinatari.add(new TipologicaDestinatarioDTO(elem.getEmail(), elem.getPec()!=null ? elem.getPec() : false, elem.getDenominazione(), tipoDest));
							};
						}
						else {
							log.info("Non ci sono email per l'Organizzazione 'ETI C/U/A' (id="+idOrg+") non legate alla territorialità");
							Integer enteRiferimento = commonService.getEnteDiRiferimentoForOrganizzazione(idOrg);
							if (enteRiferimento!=null) {
								log.info("Trovato l'Ente di riferimento (id="+enteRiferimento+") per l'Organizzazione 'ETI C/U/A' (id="+idOrg+")");
								String tipoEnte = commonService.getTipoEnte(enteRiferimento);
								if ((!TipoEnte.comune				 .getCodice().equals(tipoEnte)) &&  
								    (!TipoEnte.unione_di_comuni		 .getCodice().equals(tipoEnte)) &&
								    (!TipoEnte.associazione_di_comuni.getCodice().equals(tipoEnte))) {
									throw new Exception("Errore. L'ENTE di riferimento (id="+enteRiferimento+") per l'ORGANIZZAZIONE 'ETI C/U/A' (id="+idOrg+") non è di tipo 'Comune/Unione/Associazione (CO/UC/AC)'");
								}
								List<TipologicaDestinatarioDTO> listaEmailEnte = this.getAllEmailForEnte(enteRiferimento, tipoDest);
								if (listaEmailEnte!=null && !listaEmailEnte.isEmpty()) {
									log.info("Trovate email per l'Ente 'CO/UC/AC' di riferimento (id="+enteRiferimento+")");
									destinatari.addAll(listaEmailEnte);
								}
								else {
									log.info("Non ci sono email per l'Ente 'CO/UC/AC' di riferimento (id="+enteRiferimento+")");
									mancante=true;
								}
							}
							else {
								log.info("Non è presente l'Ente di riferimento per l'Organizzazione 'ETI C/U/A' (id="+idOrg+")");
								mancante=true;
							}
						}
					}
				}
			}
			else {
				log.info("Nessuna Organizzazione 'ETI C/U/A' trovata per questo Comune");
			}
			if (destinatari.isEmpty() || mancante==true) { // cioè   se  non ci sono   ORGANIZZAZIONI (ETI C/U/A)  competenti
										 				   // oppure se, per almeno un'ORGANIZZAZIONE (ETI C/U/A), non c'è					     l'ENTE (CO/UC/AC) di riferimento 
										 				   // oppure se, per almeno un'ORGANIZZAZIONE (ETI C/U/A), non ci sono email associate all'ENTE (CO/UC/AC) di riferimento
				log.info("Cerco le email per l'Ente 'Comune' (id="+idEnte+")");
				List<TipologicaDestinatarioDTO> listaEmailEnte = this.getAllEmailForEnte(idEnte, tipoDest);
				if (listaEmailEnte!=null && !listaEmailEnte.isEmpty()) {
					log.info("Trovate email per l'Ente 'Comune'(id="+idEnte+")");
					destinatari.addAll(listaEmailEnte);
				}
				else {
					log.info("Non ci sono email per l'Ente 'Comune'(id="+idEnte+")");
					String nomeEnte = commonService.findEnteById(idEnte).getNome();
					throw new CustomOperationToAdviceException("Errore. Non è ancora stato configurato nessun indirizzo email per il Comune di '"+nomeEnte+"'");
				}
			}
			destinatariGlob.addAll(destinatari);
		}
		return destinatariGlob;
	}
	
}