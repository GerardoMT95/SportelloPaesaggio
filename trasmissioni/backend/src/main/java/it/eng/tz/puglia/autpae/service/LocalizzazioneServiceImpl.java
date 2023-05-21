package it.eng.tz.puglia.autpae.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dbMapping.LocalizzazioneIntervento;
import it.eng.tz.puglia.autpae.dto.AllegatoCustomDTO;
import it.eng.tz.puglia.autpae.dto.LocalizzazioneTabDTO;
import it.eng.tz.puglia.autpae.dto.SelectOptionDto;
import it.eng.tz.puglia.autpae.dto.TipologicaDTO;
import it.eng.tz.puglia.autpae.dto.UlterioriInformazioniDto;
import it.eng.tz.puglia.autpae.entity.AllegatoDTO;
import it.eng.tz.puglia.autpae.entity.ComuniCompetenzaDTO;
import it.eng.tz.puglia.autpae.entity.LocalizzazioneInterventoDTO;
import it.eng.tz.puglia.autpae.entity.ParchiPaesaggiImmobiliDTO;
import it.eng.tz.puglia.autpae.entity.ParticelleCatastaliDTO;
import it.eng.tz.puglia.autpae.enumeratori.ErroriValidazioneBE;
import it.eng.tz.puglia.autpae.enumeratori.ParchiPaesaggiImmobiliSigla;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.enumeratori.TipoLocalizzazione;
import it.eng.tz.puglia.autpae.search.ComuniCompetenzaSearch;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoService;
import it.eng.tz.puglia.autpae.service.interfacce.ComuniCompetenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.GruppiRuoliService;
import it.eng.tz.puglia.autpae.service.interfacce.LocalizzazioneInterventoService;
import it.eng.tz.puglia.autpae.service.interfacce.LocalizzazioneService;
import it.eng.tz.puglia.autpae.service.interfacce.ParchiPaesaggiImmobiliService;
import it.eng.tz.puglia.autpae.service.interfacce.ParticelleCatastaliService;
import it.eng.tz.puglia.geo.service.LayerGeoService;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.servizi_esterni.protocollo.model.Livello;
import it.eng.tz.puglia.servizi_esterni.remote.repository.CommonRepository;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.AnagraficaService;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.CommonService;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class LocalizzazioneServiceImpl implements LocalizzazioneService {

	private static final Logger log = LoggerFactory.getLogger(LocalizzazioneServiceImpl.class);
//	private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot access attachments for plan exclusion of this municipality";

	@Autowired 	private LocalizzazioneInterventoService localizzazioneInterventoService;
	@Autowired	private ParchiPaesaggiImmobiliService parchiPaesaggiImmobiliService;
	@Autowired 	private ParticelleCatastaliService particelleCatastaliService;
	@Autowired 	private ComuniCompetenzaService comuniCompetenzaService;
	@Autowired 	private AllegatoService allegatoService;
	@Autowired	private AnagraficaService anagraficaService;
	@Autowired	private CommonService commonService;
	@Autowired 	private GruppiRuoliService gruppiRuoliService;
	@Autowired 	private LayerGeoService layerGeoSvc;
//	@Autowired 	private UserUtil userUtil;
	@Autowired 	private ApplicationProperties props;
	
/*	@Override	//	salva OLD 
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class, timeout=60000)
	public void salva(LocalizzazioneTabDTO localizzazioneTab, long idFascicolo) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		Set<String> codiciComuni = new HashSet<>(gruppiRuoliService.comuniForGruppoUtenteLoggato());

		// TODO: qua probabilmente ci va un unico blocco try-catch fino alla fine dell'insert
		
		// creo una lista con tutte le opzioni selezionabili
		List<TipologicaDTO> listaSelezionabili = new ArrayList<TipologicaDTO>();
		listaSelezionabili.addAll(anagraficaService.getListaBPparchiRiserve(codiciComuni));
		listaSelezionabili.addAll(anagraficaService.getListaUcpPaesaggioRurale(codiciComuni));
		listaSelezionabili.addAll(anagraficaService.getListaBPimmobiliAree(codiciComuni));
		listaSelezionabili.forEach(e->{
			e.setLabel("false");
		});
		
		// creo una lista con tutte le opzioni selezionate
		List<TipologicaDTO> listaSelezionati = new ArrayList<TipologicaDTO>();
		listaSelezionati.addAll(localizzazioneTab.getParchi());
		listaSelezionati.addAll(localizzazioneTab.getPaesaggiRurali());
		listaSelezionati.addAll(localizzazioneTab.getImmobiliAreeInteresse());
		// elimino gli evenutuali elementi della lista con label a "false" che mi potrebbero arrivare dal json (usato per la modifica post-trasmissione)
		for (Iterator<TipologicaDTO> iterator = listaSelezionati.iterator(); iterator.hasNext();) {
			TipologicaDTO elemento = iterator.next();
			if (elemento!=null && elemento.getLabel()!=null && elemento.getLabel().equals("false")) {
				iterator.remove();
			}
		}
		
		// setto a "true" gli elementi della lista corrispondenti alle opzioni selezionate
		listaSelezionati.forEach(selezionato->{
			for (int i = 0; i < listaSelezionabili.size(); i++) {
				if (selezionato.getCodice().equals(listaSelezionabili.get(i).getCodice())) {
					listaSelezionabili.get(i).setLabel("true");
				}
			}
		});
		
		List<TipologicaDTO> lista = listaSelezionabili;
		int countTrue=0;
		// verifico che il numero delle opzioni selezionate coincida con il numero degli elementi a "true"
		for (TipologicaDTO elemento : lista) {
			if (elemento.getLabel().equals("true")) countTrue++;
		};
		if (listaSelezionati.size()!=countTrue) {
			log.error("Errore in Localizzazione! E' stata selezionata un'opzione non consentita");
			throw new Exception("Errore in Localizzazione! E' stata selezionata un'opzione non consentita");
		}
		
		// cancello i dati già presenti sul DB
		ParchiPaesaggiImmobiliSearch filter = new ParchiPaesaggiImmobiliSearch();
		filter.setPraticaId(idFascicolo);
		parchiPaesaggiImmobiliService.delete(filter);
		
		// inserisco i nuovi dati sul DB
		if (!lista.isEmpty())
			parchiPaesaggiImmobiliService.insertMultiple(lista, idFascicolo);		
		
		
		ParticelleCatastaliSearch search = new ParticelleCatastaliSearch();
		search.setPraticaId(idFascicolo);
		particelleCatastaliService.delete(search);
		
		if (!localizzazioneTab.getParticelle().isEmpty()) {
			localizzazioneTab.getParticelle().forEach(elem ->{
				if (elem.getTipoLocalizzazione() != null && !elem.getTipoLocalizzazione().name().equals(TipoLocalizzazione.PTC.name())) {
				//	 elem.setComuneId("");
					 elem.setSezione("");
					 elem.setFoglio("");
					 elem.setParticella("");
					 elem.setSub("");
				}
			});
			particelleCatastaliService.insertMultipleParticelle(localizzazioneTab.getParticelle(), idFascicolo);
		}
	}	*/
	
/*	@Override	//	datiLocalizzazione OLD 
	@Transactional(propagation=Propagation.REQUIRED, timeout=60000, readOnly=true, rollbackFor=Exception.class)
	public LocalizzazioneTabDTO datiLocalizzazione(long idFascicolo) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		ParcoPaesaggioAreaSearch search1 = new ParcoPaesaggioAreaSearch();
		search1.setIdFascicolo(idFascicolo);
		List<ParcoPaesaggioAreaDTO> lista = parcoPaesaggioAreaService.search(search1).getList();
		
		List<TipologicaDTO> listaParchi = new ArrayList<TipologicaDTO>();
		List<TipologicaDTO> listaPaesaggiRurali = new ArrayList<TipologicaDTO>();
		List<TipologicaDTO> listaImmobiliAreeInteressePubblico = new ArrayList<TipologicaDTO>();
		
		if (lista!=null && !lista.isEmpty()) {
			for (ParcoPaesaggioAreaDTO dto : lista) {
					 if (dto.getCodice().toUpperCase().startsWith("PR")) {
						if (dto.isSelezionato())
							listaParchi.add(new TipologicaDTO(dto.getCodice(), null)); 
				}
				else if (dto.getCodice().toUpperCase().startsWith("PAERUR")) {
						if (dto.isSelezionato())
							listaPaesaggiRurali.add(new TipologicaDTO(dto.getCodice(), null));
				}
				else if (dto.getCodice().toUpperCase().startsWith("PAE")) {
						if (dto.isSelezionato()) 
							listaImmobiliAreeInteressePubblico.add(new TipologicaDTO(dto.getCodice(), null));
				}
				else {
						log.error("Elemento di Parchi/Paesaggi/Aree non identificato!");
						throw new Exception("Elemento di Parchi/Paesaggi/Aree non identificato!");
				}
			}
		}
		
		LocalizzazioneSearch search2 = new LocalizzazioneSearch();
		search2.setIdFascicolo(idFascicolo);
		List<LocalizzazioneDTO> particelle = repository.search(search2).getList();
		
		return new LocalizzazioneTabDTO(particelle, listaParchi, listaPaesaggiRurali, listaImmobiliAreeInteressePubblico, this.tabellaAllegati(idFascicolo));
	}	*/
	
/*	@Override	//	validazione	OLD OLD 
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public boolean validazione(LocalizzazioneTabDTO localizzazioneTab) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		Set<String> hashProvince = new HashSet<String>();
		Set<String> hashComuni   = new HashSet<String>();
		int regione = 0;
		
		if (localizzazioneTab.getParticelle()!=null && !localizzazioneTab.getParticelle().isEmpty()) {
			for(LocalizzazioneDTO particella: localizzazioneTab.getParticelle())
			{
				switch (particella.getTipoLocalizzazione().name()) {
					case "PTC":
						hashComuni.add(particella.getCodiceEnte());
						break;
					case "CO":
						hashComuni.add(particella.getCodiceEnte());
						break;
					case "P":
						hashProvince.add(particella.getCodiceEnte());
						break;
					case "R":
						regione++;
						break;
					default:
						log.error("Tipo di elemento Localizzazione non identificato!");
						throw new Exception("Tipo di elemento Localizzazione non identificato!");
					}
			}
		}
		// per il momento mischio Regione, Comuni e Province, quando sarà completa l'analisi dovrò controllare la mutua esclusività delle scelte
		
		if (!hashProvince.isEmpty())
			hashComuni.addAll(commonService.selectComuniInProvince(hashProvince));
		
		if ((hashComuni.isEmpty() && regione==0) || regione>1)
			return false;
		
		if (regione==1) {
			if (localizzazioneTab.getParchi().isEmpty() ||
				localizzazioneTab.getPaesaggiRurali().isEmpty() ||
				localizzazioneTab.getImmobiliAreeInteresse().isEmpty() )
				return false;
			else return true;
		}
		else {
			List<String> listaCodiciCatastali = new ArrayList<String>(hashComuni);

			for (int i=0; i<listaCodiciCatastali.size(); i++) {

				long listaParchi = localizzazioneTab.getParchi().size(); // = 0;
				long listaPaesaggiRurali = localizzazioneTab.getPaesaggiRurali().size(); // = 0;
				long listaImmobiliAreeInteressePubblico = localizzazioneTab.getImmobiliAreeInteresse().size(); // = 0;

//				for (int j = 0; j < localizzazioneTab.getParchi().size(); j++) {
//					if (localizzazioneTab.getParchi().get(j).getCodice().equals(listaCodiciCatastali.get(i)))
//						listaParchi++;
//				}
//				for (int j = 0; j < localizzazioneTab.getPaesaggiRurali().size(); j++) {
//					if (localizzazioneTab.getPaesaggiRurali().get(j).getCodice().equals(listaCodiciCatastali.get(i)))
//						listaPaesaggiRurali++;
//				}
//				for (int j = 0; j < localizzazioneTab.getImmobiliAreeInteresse().size(); j++) {
//					if (localizzazioneTab.getImmobiliAreeInteresse().get(j).getCodice().equals(listaCodiciCatastali.get(i)))
//						listaImmobiliAreeInteressePubblico++;
//				}

				if (validaSingoloCodiceCatastale(listaParchi, listaPaesaggiRurali, listaImmobiliAreeInteressePubblico, listaCodiciCatastali.get(i))==false)
					return false;
			}
			return true;
		}
	}	*/
	
/*	@Override	//	validazione OLD 
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public boolean validazione(LocalizzazioneTabDTO localizzazioneTab) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		if (localizzazioneTab.getParticelle()!=null && !localizzazioneTab.getParticelle().isEmpty()) {
			
			List<String> codiciPossibili = gruppiRuoliService.comuniForGruppoUtenteLoggato();
			
			for(LocalizzazioneDTO particella: localizzazioneTab.getParticelle())
			{
				if (particella.getTipoLocalizzazione()!=TipoLocalizzazione.PTC) {
						log.error("Errore. La localizzazione ammette soltanto Particelle.");
						throw new Exception("Errore. La localizzazione ammette soltanto Particelle.");
					}
				if (!codiciPossibili.contains(particella.getEnte())) {
					log.error("Errore. Il comune di riferimento della Particella non è di competenza territoriale dell'utente loggato.");
					throw new Exception("Errore. Il comune di riferimento della Particella non è di competenza territoriale dell'utente loggato.");
				}
			}
		}
		
		// creo una lista con i codici possibili per il gruppo
		LocalizzazioneTabDTO sceltePossibili = this.getListe();
		List<String> listaCodiciPossibili = new ArrayList<>();
		sceltePossibili.getParchi().forEach(e->{
			listaCodiciPossibili.add(e.getCodice());
		});
		sceltePossibili.getPaesaggiRurali().forEach(e->{
			listaCodiciPossibili.add(e.getCodice());
		});
		sceltePossibili.getImmobiliAreeInteresse().forEach(e->{
			listaCodiciPossibili.add(e.getCodice());
		});
		
		// creo una lista con i codici selezionati
		List<String> listaCodiciSelezionati = new ArrayList<>();
		localizzazioneTab.getParchi().forEach(e->{
			listaCodiciSelezionati.add(e.getCodice());
		});
		localizzazioneTab.getPaesaggiRurali().forEach(e->{
			listaCodiciSelezionati.add(e.getCodice());
		});
		localizzazioneTab.getImmobiliAreeInteresse().forEach(e->{
			listaCodiciSelezionati.add(e.getCodice());
		});
		
		// controllo che i codici selezionati siano un sottoinsieme di quelli possibili
		if (!listaCodiciPossibili.containsAll(listaCodiciSelezionati)) {
			log.error("Errore. Sono stati selezionati elementi delle liste non consentiti per il territorio di competenza.");
			throw new Exception("Errore. Sono stati selezionati elementi delle liste non consentiti per il territorio di competenza.");
		}

		return true;
	}	*/
	
/*				//	validaSingoloCodiceCatastale OLD 
	@Transactional(propagation=Propagation.MANDATORY, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	private boolean validaSingoloCodiceCatastale(long listaParchi, long listaPaesaggiRurali, long listaImmobiliAreeInteressePubblico, String codiceCatastale) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		// TODO: quando capirò bene cosa vuole l'analisi posso fare un repository con ... IN {  }

		boolean validoParchi = true;
		boolean validoPaesaggiRurali = true;
		boolean validoImmobiliAreeInteressePubblico = true;

		long countParchi = anagraficaService.countParchi(codiceCatastale);
		if (countParchi>0 && listaParchi==0)
			validoParchi = false;

		long countPaesaggiRurali = anagraficaService.countPaesaggiRurali(codiceCatastale);
		if (countPaesaggiRurali>0 && listaPaesaggiRurali==0)
			validoPaesaggiRurali = false;

		long countImmobiliAreeInteressePubblico = anagraficaService.countImmobiliAreeInteressePubblico(codiceCatastale);
		if (countImmobiliAreeInteressePubblico>0 && listaImmobiliAreeInteressePubblico==0)
			validoImmobiliAreeInteressePubblico = false;

		return (validoParchi && validoPaesaggiRurali && validoImmobiliAreeInteressePubblico);
	}	*/
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public UlterioriInformazioniDto getListe(List<String> comuni) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		if (comuni.size()!=1) {
			throw new Exception("Attualmente è possibile gestire soltanto una lista contenente un solo comune!");
		}
		Set<String> comuniPossibili = new HashSet<>(gruppiRuoliService.comuniForGruppoUtenteLoggato());
		if (!comuniPossibili.containsAll(comuni)) {
			throw new Exception("Non hai le autorizzazioni per operare su alcuni di questi comuni: "+comuni);
		}
		
		Set<String> setComune = new HashSet<>();
		setComune.add(comuni.get(0));			 // TODO: se sarà necessario, replicare la logica per tutti i comuni della lista in input, ma capire come distinguerli alla fine
		
		UlterioriInformazioniDto ultInf = new UlterioriInformazioniDto();
		
		List<TipologicaDTO> bPparchiRiserve = anagraficaService.getListaBPparchiRiserve(setComune);
		if (bPparchiRiserve!=null) 
			bPparchiRiserve.forEach(elem->{
				ultInf.getBpParchiEReserveOptions().add(new SelectOptionDto(elem));
			});
		
		if (!props.isPutt()) {
			List<TipologicaDTO> ucpPaesaggioRurale = anagraficaService.getListaUcpPaesaggioRurale(setComune);
			if (!props.isPutt() && ucpPaesaggioRurale!=null) 
				ucpPaesaggioRurale.forEach(elem->{
					ultInf.getUcpPaesaggioRuraleOptions().add(new SelectOptionDto(elem));
				});		
		}
		
		if (!props.isPutt()) {
			List<TipologicaDTO> immobiliAreeInteresse = anagraficaService.getListaBPimmobiliAree(setComune); 
			if (!props.isPutt() && immobiliAreeInteresse!=null) 
				immobiliAreeInteresse.forEach(elem->{
					ultInf.getBpImmobileAreePubblicoOptions().add(new SelectOptionDto(elem));
				});
		}
		return ultInf;
	}
	
	@Transactional(propagation=Propagation.MANDATORY, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	private List<AllegatoCustomDTO> tabellaAllegati(Long idFascicolo) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		List<AllegatoDTO> listaAllegati = allegatoService.datiAllegato(idFascicolo, Collections.singletonList(TipoAllegato.LOCALIZZAZIONE));

		ArrayList<AllegatoCustomDTO> lista = new ArrayList<AllegatoCustomDTO>();
		listaAllegati.forEach(elem -> {
			lista.add(new AllegatoCustomDTO(elem));
		});
		
		return lista;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public List<TipologicaDTO> particelleComuniForGruppo() throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		return commonService.selectInfoEntiByCodici(gruppiRuoliService.comuniForGruppoUtenteLoggato());
	}
	
/*	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public List<Integer> competenzaTerritorialeForGruppoLoggato() throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		int idPaeOrg = userUtil.getIntegerId();
		
		// creo una lista di Enti (id della tabella ENTE) per i quali il gruppo loggato ha competenza territoriale
		Set<Integer> enti = new HashSet<>();
		enti.addAll(commonService.getEntiDiCompetenzaForOrganizzazione(idPaeOrg));		
		
		// creo una lista di Organizzazioni (id della tabella Paesaggio_Organizzazione) che hanno competenza territoriale su quegli enti
		List<Integer> organizzazioni = commonService.getOrganizzazioniCompetentiOnEnti(enti, props.getCodiceApplicazione());	
		
		return organizzazioni;
	}	*/

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class, timeout=60000)
	public LocalizzazioneTabDTO datiLocalizzazione(long idFascicolo) throws Exception {
		
		List<LocalizzazioneInterventoDTO> listaComuni = localizzazioneInterventoService.select(idFascicolo);
		if (listaComuni != null && listaComuni.size() > 0) {
			for (LocalizzazioneInterventoDTO comune : listaComuni) {
				//find particelle
				List<ParticelleCatastaliDTO> particelle = particelleCatastaliService.select(idFascicolo, comune.getComuneId());
				if (particelle != null) {
					comune.setParticelle(particelle);
				}
				//find vincolistica
				UlterioriInformazioniDto ultInfo = new UlterioriInformazioniDto();
					List<ParchiPaesaggiImmobiliDTO> parchi   = parchiPaesaggiImmobiliService.select(idFascicolo, comune.getComuneId(), ParchiPaesaggiImmobiliSigla.P.toString());
					toUlterioriInformazioniDto(parchi  , ultInfo.getBpParchiEReserve()      , ultInfo.getBpParchiEReserveOptions());
				if (!props.isPutt()) { 
					List<ParchiPaesaggiImmobiliDTO> immobili = parchiPaesaggiImmobiliService.select(idFascicolo, comune.getComuneId(), ParchiPaesaggiImmobiliSigla.I.toString());
					toUlterioriInformazioniDto(immobili, ultInfo.getBpImmobileAreePubblico(), ultInfo.getBpImmobileAreePubblicoOptions()); }
				if (!props.isPutt()) {
					List<ParchiPaesaggiImmobiliDTO> paesaggi = parchiPaesaggiImmobiliService.select(idFascicolo, comune.getComuneId(), ParchiPaesaggiImmobiliSigla.R.toString());
					toUlterioriInformazioniDto(paesaggi, ultInfo.getUcpPaesaggioRurale()    , ultInfo.getUcpPaesaggioRuraleOptions()); }
				comune.setUlterioriInformazioni(ultInfo);
			}
		}
		return new LocalizzazioneTabDTO(listaComuni, this.tabellaAllegati(idFascicolo));
	}

	/**
	 * precarico le selezioni possibili e le selezioni effettuate
	 * @param entityOptions
	 * @param listaSelezioni
	 * @param listaOpzioni
	 */
	private void toUlterioriInformazioniDto(List<ParchiPaesaggiImmobiliDTO> entityOptions, List<String> listaSelezioni, List<SelectOptionDto> listaOpzioni) {
		if(entityOptions!=null && entityOptions.size()>0) {
			for(ParchiPaesaggiImmobiliDTO vincolo:entityOptions) {
				SelectOptionDto opzione = new SelectOptionDto();
				opzione.setValue(vincolo.getCodice());
				opzione.setDescription(vincolo.getDescrizione());
			//	opzione.setLinked(vincolo.getInfo());
				listaOpzioni.add(opzione);
				if(vincolo.getSelezionato()!=null && vincolo.getSelezionato().equalsIgnoreCase("S")) {
					listaSelezioni.add(vincolo.getCodice());
				};
			}
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class, timeout=60000)
	public void salva(LocalizzazioneTabDTO localizzazioneTab, long idFascicolo) throws Exception {
		List<LocalizzazioneInterventoDTO> localComuniOld = localizzazioneInterventoService.select(idFascicolo);
		List<LocalizzazioneInterventoDTO> localComuni = localizzazioneTab.getLocalizzazioneComuni();
		// rimuovo quelle che dal FE non mi sono arrivate più
		if (localComuniOld != null) {
			for (LocalizzazioneInterventoDTO locComuneOld : localComuniOld) {
				boolean esiste = false;
				//cerco nel new...
				if (localComuni != null && localComuni.size() > 0) {
					for (LocalizzazioneInterventoDTO locNew : localComuni) {
						if (locNew.getComuneId() == locComuneOld.getComuneId()) {
							esiste = true;
						}
					}
				}
				if (!esiste) {
					localizzazioneInterventoService.delete(locComuneOld);
				}
			}
		}
		if (localComuni != null && localComuni.size() > 0) {
			for (LocalizzazioneInterventoDTO locComune : localComuni) {
				// vincolistica parchi paesaggi immobili
				UlterioriInformazioniDto ulterioriInfo = locComune.getUlterioriInformazioni();
				locComune.setPraticaId(idFascicolo);
				int affected = localizzazioneInterventoService.update(locComune);
				if (affected == 0) {
					localizzazioneInterventoService.insert(locComune);
					// inserisco le selezioni possibili in parchi_paesaggi_immobili solo all'inserimento della localizzazioneIntervento
					creaRecordVincolistica(idFascicolo, locComune.getComuneId(), ulterioriInfo);
				}
				// update selezioni vincolistica
									   parchiPaesaggiImmobiliService.setSelezioni(idFascicolo, locComune.getComuneId(), ParchiPaesaggiImmobiliSigla.P.toString(), ulterioriInfo.getBpParchiEReserve());
				if (!props.isPutt()) { parchiPaesaggiImmobiliService.setSelezioni(idFascicolo, locComune.getComuneId(), ParchiPaesaggiImmobiliSigla.I.toString(), ulterioriInfo.getBpImmobileAreePubblico()); }
				if (!props.isPutt()) { parchiPaesaggiImmobiliService.setSelezioni(idFascicolo, locComune.getComuneId(), ParchiPaesaggiImmobiliSigla.R.toString(), ulterioriInfo.getUcpPaesaggioRurale());	  }
				// particelle
				particelleCatastaliService.deleteByKeyLoc(idFascicolo, locComune.getComuneId());
				List<ParticelleCatastaliDTO> particelle = locComune.getParticelle();
				if (particelle != null && particelle.size() > 0) {
					int index = 1;
					for (ParticelleCatastaliDTO p : particelle) {
						if (StringUtil.isEmpty(p.getLivello()) && StringUtil.isEmpty(p.getSezione()) && StringUtil.isEmpty(p.getFoglio())
							&& StringUtil.isEmpty(p.getParticella()) && StringUtil.isEmpty(p.getSub()))
							continue;
						p.setPraticaId(idFascicolo);
						p.setComuneId(locComune.getComuneId());
						p.setId(index++);
						particelleCatastaliService.insert(p);
					}
				}
			}
		}
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class, timeout=60000)
	public void creaRecordVincolisticaComune(long idFascicolo, long idComune,
			UlterioriInformazioniDto ulterioriInfo) {
		this.creaRecordVincolistica(idFascicolo, idComune, ulterioriInfo);
	}
	
	private void creaRecordVincolistica(long idFascicolo, long idComune,
			UlterioriInformazioniDto ulterioriInfo) {
		creaOptions(idFascicolo, idComune, ulterioriInfo.getBpParchiEReserveOptions()		, ParchiPaesaggiImmobiliSigla.P.toString());
		if (!props.isPutt()) { creaOptions(idFascicolo, idComune, ulterioriInfo.getBpImmobileAreePubblicoOptions() , ParchiPaesaggiImmobiliSigla.I.toString()); }
		if (!props.isPutt()) { creaOptions(idFascicolo, idComune, ulterioriInfo.getUcpPaesaggioRuraleOptions()		, ParchiPaesaggiImmobiliSigla.R.toString()); }
	}
	
	private void creaOptions(Long idFascicolo, long idComune, List<SelectOptionDto> lista, String tipoVincolo) {
		if (lista != null) {
			lista.forEach(opzione -> {
				ParchiPaesaggiImmobiliDTO ppi = new ParchiPaesaggiImmobiliDTO();
				ppi.setPraticaId(idFascicolo);
				ppi.setComuneId(idComune);
				ppi.setTipoVincolo(tipoVincolo);
				ppi.setCodice(opzione.getValue());
				ppi.setDescrizione(opzione.getDescription());
				ppi.setSelezionato(null);
				ppi.setDataInserimento(new Date());
				parchiPaesaggiImmobiliService.insert(ppi);
			});
		}
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class, timeout=60000)
	public List<ErroriValidazioneBE> validazione(LocalizzazioneTabDTO localizzazioneTab, long idFascicolo,TipoLocalizzazione tipoSelezLocalizzazione) throws Exception {
		List<ErroriValidazioneBE> erroriValidazione=new ArrayList<ErroriValidazioneBE>();
		if (localizzazioneTab==null || localizzazioneTab.getLocalizzazioneComuni()==null || localizzazioneTab.getLocalizzazioneComuni().isEmpty()) {
			erroriValidazione.add(ErroriValidazioneBE.LOCALIZZAZIONE_COMUNE_MANCANTE);
			log.error("Errore. E' necessario inserire almeno un comune nella localizzazione!");
			return erroriValidazione;
			//throw new Exception("Errore. E' necessario inserire almeno un comune nella localizzazione!");
		}
		
		List<ComuniCompetenzaDTO> competenze = comuniCompetenzaService.search(new ComuniCompetenzaSearch(idFascicolo, true)).getList();
		List<String> comuniCreazione = new ArrayList<>();
		competenze.forEach(elem->{
			comuniCreazione.add(elem.getCodCat());
		});
		List<String> comuniAttuali = gruppiRuoliService.comuniForGruppoUtenteLoggato();
		
		// i comuni su cui è possibile operare sono quelli di competenza del gruppo 1) al momento della creazione della pratica  +  2) nel momento attuale
		Set<String> comuni = new HashSet<>();
		comuni.addAll(comuniCreazione);
		comuni.addAll(comuniAttuali);
		Set<Long> oids=new HashSet<>();
		for (LocalizzazioneInterventoDTO intervento : localizzazioneTab.getLocalizzazioneComuni()) {
		//	if (intervento.getPraticaId()!=idFascicolo)				// attualmente "praticaId" arriva sempre a "0" dal FE
		//		throw new Exception("Errore. Dati incoerenti!");
			
			String codCatComune = commonService.findEnteById(((Long) intervento.getComuneId()).intValue()).getCodice();
			
			if (!comuni.contains(codCatComune))
				{
					erroriValidazione.add(ErroriValidazioneBE.LOCALIZZAZIONE_COMUNE_INVALIDO);
					continue;
					//throw new Exception("Autorizzazioni mancanti per operare sul comune con codice: "+codCatComune);
				}
			if (StringUtil.isNotEmpty(intervento.getSiglaProvincia())) {
				if (intervento.getSiglaProvincia().length()!=2 || !intervento.getSiglaProvincia().matches("[a-zA-Z]+"))
				{
					erroriValidazione.add(ErroriValidazioneBE.LOCALIZZAZIONE_PROVINCIA_NON_RICONOSCIUTA);
					//throw new Exception("Errore. Provincia non riconosciuta!");
				}
			}
			
			if (intervento.getStrade()!=null && intervento.getStrade()==true) {
				if (StringUtil.isEmpty(intervento.getIndirizzo()) || StringUtil.isEmpty(intervento.getNumCivico()))
					{
					erroriValidazione.add(ErroriValidazioneBE.LOCALIZZAZIONE_INFO_AREA_STRADALE_INCOMPLETE);
					//throw new Exception("Errore. Informazioni sull'area stradale incomplete!");
					}
			}
			else { // se "Strade" è "null" o "false"
			/*	if (StringUtil.isNotEmpty(intervento.getIndirizzo()) 	  || 	// Adriano: --> lasciare la possibilità di selezioni ibride e potenzialmente incoerenti
					StringUtil.isNotEmpty(intervento.getNumCivico()) 	  ||
					StringUtil.isNotEmpty(intervento.getSiglaProvincia()) ||
					StringUtil.isNotEmpty(intervento.getPiano()) 		  ||
					StringUtil.isNotEmpty(intervento.getInterno()) 		  ||
										  intervento.getDataRiferimentoCatastale()!=null ||
					StringUtil.isNotEmpty(intervento.getDestUsoAtt()) 	  ||
					StringUtil.isNotEmpty(intervento.getDestUsoProg())     )
					throw new Exception("Errore. Informazioni sull'area stradale non coerenti!");	*/
				if (intervento.getParticelle()==null || intervento.getParticelle().isEmpty())
					{
					erroriValidazione.add(ErroriValidazioneBE.LOCALIZZAZIONE_NESSUNA_PARTICELLA);
					//throw new Exception("Errore. Nessuna particella è stata inserita!");
					}
			}
			if (intervento.getParticelle()!=null) {
				for (ParticelleCatastaliDTO particella : intervento.getParticelle()) {
					if(StringUtil.isEmpty(particella.getFoglio())) {
						erroriValidazione.add(ErroriValidazioneBE.LOCALIZZAZIONE_PARTICELLA_FOGLIO_MANCANTE);
					}
					if (StringUtil.isEmpty(particella.getParticella()))
					{
						erroriValidazione.add(ErroriValidazioneBE.LOCALIZZAZIONE_PARTICELLA_VUOTA);
					//throw new CustomOperationToAdviceException("Errore. Particella vuota!");
					}
					if (StringUtil.isEmpty(particella.getCodCat()))
					{
						erroriValidazione.add(ErroriValidazioneBE.LOCALIZZAZIONE_PARTICELLA_CODICE_CATASTALE_VUOTO);
					}
					if (!particella.getCodCat().equals(codCatComune))
					{
						erroriValidazione.add(ErroriValidazioneBE.LOCALIZZAZIONE_PARTICELLA_CODICE_CATASTALE_DIVERSO);
					}
					if (StringUtil.isEmpty(particella.getLivello())||!particella.getLivello().equalsIgnoreCase("PARTICELLE")) /* ||
						StringUtil.isEmpty(particella.getCodCat()) 	    || particella.getCodCat() 	 != intervento.getComune() ||	 // attualmente "codCat"    arriva sempre a "null" dal FE
						particella.getPraticaId() == null 				|| particella.getPraticaId() == idFascicolo 		   ||	 // attualmente "praticaId" arriva sempre a "null" dal FE
						particella.getComuneId()  <= 0				 															) */ // attualmente "comuneId"  arriva sempre a "0"    dal FE
						//throw new CustomOperationToAdviceException("Errore. Campo Livello errato o mancante, atteso PARTICELLE: "+particella);
						erroriValidazione.add(ErroriValidazioneBE.LOCALIZZAZIONE_LIVELLO_ERRATO);
					if(particella.getOid()!=null) {
						oids.add(particella.getOid());
					} 
				}
			}
			if (intervento.getUlterioriInformazioni()!=null) {
				
				long idComune =  intervento.getComuneId();
				Set<String> setComune = new HashSet<>(1);
				setComune.add(codCatComune);
				
				// i vincoli selezionabili sono quelli relativi al comune 1) al momento della creazione della pratica  +  2) nel momento attuale
				
					List<ParchiPaesaggiImmobiliDTO>   parchiCreazione = parchiPaesaggiImmobiliService.select(idFascicolo, idComune, ParchiPaesaggiImmobiliSigla.P.toString());
					List<TipologicaDTO>   parchiAttuali = anagraficaService.getListaBPparchiRiserve   (setComune);
					Set<String>   parchiSelezionabili = new HashSet<>();
					if (  parchiCreazione!=null)   parchiCreazione.forEach(elem-> {   parchiSelezionabili.add(elem.getCodice()); });
					if (  parchiAttuali  !=null)   parchiAttuali  .forEach(elem-> {   parchiSelezionabili.add(elem.getCodice()); });
					if (intervento.getUlterioriInformazioni().getBpParchiEReserveOptions()!=null)
						for (SelectOptionDto elem : intervento.getUlterioriInformazioni().getBpParchiEReserveOptions()) {
							if (!parchiSelezionabili.contains(elem.getValue()))
							{
								log.error("Il vincolo paesaggistico :"+elem.getValue()+" non è selezionabile per il comune con codice: "+codCatComune);
								erroriValidazione.add(ErroriValidazioneBE.LOCALIZZAZIONE_VINCOLO_ERRATO);
								//throw new CustomOperationToAdviceException("Il vincolo paesaggistico :"+elem.getValue()+" non è selezionabile per il comune con codice: "+codCatComune);
							}
								
						};
				if (!props.isPutt()) {
					List<ParchiPaesaggiImmobiliDTO> immobiliCreazione = parchiPaesaggiImmobiliService.select(idFascicolo, idComune, ParchiPaesaggiImmobiliSigla.I.toString());
					List<TipologicaDTO> immobiliAttuali = anagraficaService.getListaBPimmobiliAree    (setComune);
					Set<String> immobiliSelezionabili = new HashSet<>();
					if (immobiliCreazione!=null) immobiliCreazione.forEach(elem-> { immobiliSelezionabili.add(elem.getCodice()); });
					if (immobiliAttuali  !=null) immobiliAttuali  .forEach(elem-> { immobiliSelezionabili.add(elem.getCodice()); });
					if (intervento.getUlterioriInformazioni().getBpImmobileAreePubblicoOptions()!=null)
						for (SelectOptionDto elem : intervento.getUlterioriInformazioni().getBpImmobileAreePubblicoOptions()) {
							if (!immobiliSelezionabili.contains(elem.getValue())) {
								erroriValidazione.add(ErroriValidazioneBE.LOCALIZZAZIONE_VINCOLO_ERRATO);
								log.error("Il vincolo paesaggistico :"+elem.getValue()+" non è selezionabile per il comune con codice: "+codCatComune);
								//throw new CustomOperationToAdviceException("Il vincolo paesaggistico :"+elem.getValue()+" non è selezionabile per il comune con codice: "+codCatComune);
							}
						};
				}
				if (!props.isPutt()) {
					List<ParchiPaesaggiImmobiliDTO> paesaggiCreazione = parchiPaesaggiImmobiliService.select(idFascicolo, idComune, ParchiPaesaggiImmobiliSigla.R.toString());
					List<TipologicaDTO> paesaggiAttuali = anagraficaService.getListaUcpPaesaggioRurale(setComune);
					Set<String> paesaggiSelezionabili = new HashSet<>();
					if (paesaggiCreazione!=null) paesaggiCreazione.forEach(elem-> { paesaggiSelezionabili.add(elem.getCodice()); });
					if (paesaggiAttuali  !=null) paesaggiAttuali  .forEach(elem-> { paesaggiSelezionabili.add(elem.getCodice()); });
					if (intervento.getUlterioriInformazioni().getUcpPaesaggioRuraleOptions()!=null)
						for (SelectOptionDto elem : intervento.getUlterioriInformazioni().getUcpPaesaggioRuraleOptions()) {
							if (!paesaggiSelezionabili.contains(elem.getValue())) {
								erroriValidazione.add(ErroriValidazioneBE.LOCALIZZAZIONE_VINCOLO_ERRATO);
								log.error("Il vincolo paesaggistico :"+elem.getValue()+" non è selezionabile per il comune con codice: "+codCatComune);
							}
								
						};
					}
			}
		}
		checkForApi(idFascicolo, tipoSelezLocalizzazione, erroriValidazione, oids);
		return erroriValidazione;
		//return true;
	}

	private void checkForApi(long idFascicolo, TipoLocalizzazione tipoSelezLocalizzazione,
			List<ErroriValidazioneBE> erroriValidazione, Set<Long> oids) throws CustomOperationToAdviceException {
		List<Long> oidsInLayer = layerGeoSvc.findShapeFascicolo(idFascicolo);
		//verifico che gli oid inviati sono effettivamente legati al fascicolo
		if(SecurityUtil.isApiUser() && oids.size()>0) {
			List<Long> oidsOrfani = new ArrayList<>();
			if(oidsInLayer!=null && oidsInLayer.size()>0) {
				oids.forEach(oid->{
					if(!oidsInLayer.contains(oid)) {
						oidsOrfani.add(oid);
					}
				});
			}else {
				oidsOrfani.addAll(oids);
			}
			if(oidsOrfani.size()>0) {
				erroriValidazione.add(ErroriValidazioneBE.LOCALIZZAZIONE_OID_SHAPE_INVALIDO);
				log.error("I seguenti oid sulle particelle risultano orfani:"+oidsOrfani);
			}
		}
		//verifico che in caso di SHAPEFILE ci sia il file shape
		if(SecurityUtil.isApiUser() && tipoSelezLocalizzazione.equals(TipoLocalizzazione.SHPF)) {
			//controllo che abbia uploadato il file shape...
			List<AllegatoDTO> listaShapeFiles=null;
			try {
				 listaShapeFiles= allegatoService.datiAllegato(idFascicolo, Collections.singletonList(TipoAllegato.LOCALIZZAZIONE));
			} catch (Exception e) {
				log.error("Errore nel retrieval degli allegati SHAPE",e);
			}
			if(ListUtil.isEmpty(listaShapeFiles)) {
				erroriValidazione.add(ErroriValidazioneBE.LOCALIZZAZIONE_MANCA_SHAPE_FILE);
			}
		}
		if(SecurityUtil.isApiUser() && tipoSelezLocalizzazione.equals(TipoLocalizzazione.SHPD) && 
				(oids==null || oids.size()<=0)) {
			//controllo che abbia uploadato le geometrie in mappa..
			erroriValidazione.add(ErroriValidazioneBE.LOCALIZZAZIONE_MANCA_SHAPE_IN_MAPPA);
		}
	}
	
}