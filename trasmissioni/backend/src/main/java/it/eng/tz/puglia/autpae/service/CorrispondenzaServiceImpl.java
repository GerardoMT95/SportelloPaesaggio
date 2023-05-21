package it.eng.tz.puglia.autpae.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dto.AllegatoCustomDTO;
import it.eng.tz.puglia.autpae.dto.DestinatarioTemplateDTO;
import it.eng.tz.puglia.autpae.dto.DettaglioCorrispondenzaDTO;
import it.eng.tz.puglia.autpae.dto.InfoPlaceHoldersDTO;
import it.eng.tz.puglia.autpae.dto.InformazioniDTO;
import it.eng.tz.puglia.autpae.dto.NuovaComunicazioneDTO;
import it.eng.tz.puglia.autpae.dto.TemplateEmailDestinatariDTO;
import it.eng.tz.puglia.autpae.dto.TipologicaDestinatarioDTO;
import it.eng.tz.puglia.autpae.entity.AllegatoCorrispondenzaDTO;
import it.eng.tz.puglia.autpae.entity.CorrispondenzaDTO;
import it.eng.tz.puglia.autpae.entity.DestinatarioDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloCorrispondenzaDTO;
import it.eng.tz.puglia.autpae.entity.RicevutaDTO;
import it.eng.tz.puglia.autpae.entity.TemplateEmailDTO;
import it.eng.tz.puglia.autpae.enumeratori.PlaceHolder;
import it.eng.tz.puglia.autpae.enumeratori.StatoCorrispondenza;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.enumeratori.TipoRicevuta;
import it.eng.tz.puglia.autpae.enumeratori.TipoTemplate;
import it.eng.tz.puglia.autpae.repository.CorrispondenzaFullRepository;
import it.eng.tz.puglia.autpae.search.AllegatoCorrispondenzaSearch;
import it.eng.tz.puglia.autpae.search.CorrispondenzaSearch;
import it.eng.tz.puglia.autpae.search.DestinatarioSearch;
import it.eng.tz.puglia.autpae.search.FascicoloCorrispondenzaSearch;
import it.eng.tz.puglia.autpae.search.RicevutaSearch;
import it.eng.tz.puglia.autpae.search.TemplateDestinatarioSearch;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoCorrispondenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoService;
import it.eng.tz.puglia.autpae.service.interfacce.CorrispondenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.DestinatarioService;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloCorrispondenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloService;
import it.eng.tz.puglia.autpae.service.interfacce.LocalizzazioneInterventoService;
import it.eng.tz.puglia.autpae.service.interfacce.RicevutaService;
import it.eng.tz.puglia.autpae.service.interfacce.RichiedenteService;
import it.eng.tz.puglia.autpae.service.interfacce.TemplateDestinatarioService;
import it.eng.tz.puglia.autpae.service.interfacce.TemplateEmailService;
import it.eng.tz.puglia.autpae.utility.DateUtil;
import it.eng.tz.puglia.autpae.utility.validator.EmailValidator;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.CommonService;
import it.eng.tz.puglia.servizi_esterni.webmail.service.WebmailService;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.regione.puglia.webmail.be.dto.ConfigurazioneCasellaPostaleDto;

@Service
public class CorrispondenzaServiceImpl implements CorrispondenzaService {

	private static final Logger log = LoggerFactory.getLogger(CorrispondenzaServiceImpl.class);
//	private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot access attachments for plan exclusion of this municipality";

	@Autowired
	private CorrispondenzaFullRepository repository;
	@Autowired
	WebmailService webmailSvc;
	@Autowired
	@Qualifier("casellaMittenteApplicazione")
	private ConfigurazioneCasellaPostaleDto ccpd;
	@Autowired
	private ApplicationProperties props;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public long count(final CorrispondenzaSearch filter) throws Exception {
		return repository.count(filter);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public CorrispondenzaDTO find(final Long pk) throws Exception {
		return repository.find(pk);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public Long insert(final CorrispondenzaDTO entity) throws Exception {
		return repository.insert(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public Long insert(final CorrispondenzaDTO entity, final boolean forMigration) throws Exception {
		return repository.insert(entity, forMigration);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public int update(final CorrispondenzaDTO entity) throws Exception {
		return repository.update(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public int delete(final CorrispondenzaSearch search) throws Exception {
		return repository.delete(search);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public PaginatedList<CorrispondenzaDTO> search(final CorrispondenzaSearch filter) throws Exception {
		return repository.search(filter);
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public int updateMessageId(final Long idCorrispondenza, final String messageId) throws Exception {
		return repository.updateMessageId(idCorrispondenza, messageId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public int updateBozza(final Long idCorrispondenza, final Boolean bozza) throws Exception {
		return repository.updateBozza(idCorrispondenza, bozza);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public int updateMittenteUsernameAndMittenteDenominazione(Long idCorrispondenza, String mittenteUsername,
			String mittenteDenominazione,String mittenteEnte) throws Exception {
		return repository.updateMittenteUsernameAndMittenteDenominazione(idCorrispondenza, mittenteUsername,mittenteDenominazione,mittenteEnte);
	}

	@Autowired
	private AllegatoService allegatoService;
	@Autowired
	private FascicoloService fascicoloService;
	@Autowired
	private FascicoloCorrispondenzaService fascicoloCorrispondenzaService;
	@Autowired
	private DestinatarioService destinatarioService;
	@Autowired
	private AllegatoCorrispondenzaService allegatoCorrispondenzaService;
	@Autowired
	private RicevutaService ricevutaService;
	@Autowired
	private TemplateEmailService templateEmailService;
	@Autowired
	private RichiedenteService richiedenteService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private LocalizzazioneInterventoService localizzazioneInterventoService;
	@Autowired
	private TemplateDestinatarioService templateDestinatarioService;

	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public DettaglioCorrispondenzaDTO dettaglioCorrispondenza(final long idCorrispondenza, final boolean full_info)
			throws Exception {
		log.trace("Entrato nel service: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");

		DettaglioCorrispondenzaDTO dettaglioCorrispondenza = new DettaglioCorrispondenzaDTO();

		CorrispondenzaSearch searchC = new CorrispondenzaSearch();
		searchC.setId(idCorrispondenza);
		dettaglioCorrispondenza.setCorrispondenza(repository.search(searchC).getList().get(0));

		DestinatarioSearch searchD = new DestinatarioSearch();
		searchD.setIdCorrispondenza(idCorrispondenza);
		dettaglioCorrispondenza.setDestinatari(destinatarioService.search(searchD).getList());

//		dettaglioCorrispondenza.setAllegati(allegatoService.nomeAllegatiCorrispondenza(idCorrispondenza));
		dettaglioCorrispondenza.setAllegatiInfo(allegatoService.infoAllegatiCorrispondenza(idCorrispondenza));

		if (full_info == true) {
//			dettaglioCorrispondenza.setAllegatiInfo(allegatoService.infoAllegatiCorrispondenza(idCorrispondenza));
			RicevutaSearch searchR = new RicevutaSearch();
			searchR.setIdCorrispondenza(idCorrispondenza);
			List<RicevutaDTO> listaRicevuteMail = ricevutaService.search(searchR).getList(); // tutte le ricevute della
																								// mail...
			if (ListUtil.isNotEmpty(listaRicevuteMail)) {
				// cerco l'accettazione
				listaRicevuteMail.stream()
						.filter(ric -> ric.getTipoRicevuta().equals(TipoRicevuta.ACCETTAZIONE)
								|| ric.getTipoRicevuta().equals(TipoRicevuta.NON_ACCETTAZIONE))
						.findAny().ifPresent(ric -> dettaglioCorrispondenza.getRicevutaAccettazione().add(ric));
				for (int i = 0; i < dettaglioCorrispondenza.getDestinatari().size(); i++) {
					searchR.setIdCorrispondenza(idCorrispondenza);
					searchR.setIdDestinatario(dettaglioCorrispondenza.getDestinatari().get(i).getId());
					try {
						final Long idDestinatario = dettaglioCorrispondenza.getDestinatari().get(i).getId();
						List<RicevutaDTO> listaRicevute = listaRicevuteMail.stream()
								.filter(ric -> ric.getIdDestinatario().equals(idDestinatario))
								.collect(Collectors.toList());
						if (listaRicevute != null && listaRicevute.size() > 0) {
							dettaglioCorrispondenza.getDestinatari().get(i).setRicevute(
									Collections.singletonList(this.ricevutaPiuSignificativa(listaRicevute)));
						}
					} catch (Exception e) {
						log.error("Errore nel recupero delle ricevute della corrispondenza per il destinatario con id="
								+ dettaglioCorrispondenza.getDestinatari().get(i).getId());
						throw new Exception(
								"Errore nel recupero delle ricevute della corrispondenza per il destinatario con id="
										+ dettaglioCorrispondenza.getDestinatari().get(i).getId());
					}
				}
			}

		}
		return dettaglioCorrispondenza;
	}

	private RicevutaDTO ricevutaPiuSignificativa(final List<RicevutaDTO> listaRicevute) {
		log.trace("Entrato nel service: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");
		List<RicevutaDTO> listaPriorita1 = new ArrayList<RicevutaDTO>();
		List<RicevutaDTO> listaPriorita2 = new ArrayList<RicevutaDTO>();
		List<RicevutaDTO> listaPriorita3 = new ArrayList<RicevutaDTO>();
		List<RicevutaDTO> listaPriorita4 = new ArrayList<RicevutaDTO>();
		List<RicevutaDTO> listaPriorita5 = new ArrayList<RicevutaDTO>();
		for (RicevutaDTO ricevuta : listaRicevute) {
			if (ricevuta.getTipoRicevuta() == TipoRicevuta.AVVENUTA_CONSEGNA)
				listaPriorita1.add(ricevuta);
			else if (ricevuta.getTipoRicevuta() == TipoRicevuta.PREAVVISO_ERRORE_CONSEGNA
					|| ricevuta.getTipoRicevuta() == TipoRicevuta.ERRORE_CONSEGNA
					|| ricevuta.getTipoRicevuta() == TipoRicevuta.RILEVAZIONE_VIRUS)
				listaPriorita2.add(ricevuta);
			else if (ricevuta.getTipoRicevuta() == TipoRicevuta.NON_ACCETTAZIONE)
				listaPriorita3.add(ricevuta);
			else if (ricevuta.getTipoRicevuta() == TipoRicevuta.PRESA_IN_CARICO
					|| ricevuta.getTipoRicevuta() == TipoRicevuta.POSTA_CERTIFICATA)
				listaPriorita4.add(ricevuta);
			else if (ricevuta.getTipoRicevuta() == TipoRicevuta.ACCETTAZIONE)
				listaPriorita5.add(ricevuta);
		}
		List<RicevutaDTO> listaPriorita = new ArrayList<RicevutaDTO>();
		if (!listaPriorita1.isEmpty()) {
			listaPriorita = listaPriorita1;
		} else if (!listaPriorita2.isEmpty()) {
			listaPriorita = listaPriorita2;
		} else if (!listaPriorita3.isEmpty()) {
			listaPriorita = listaPriorita3;
		} else if (!listaPriorita4.isEmpty()) {
			listaPriorita = listaPriorita4;
		} else if (!listaPriorita5.isEmpty()) {
			listaPriorita = listaPriorita5;
		}

		Collections.sort(listaPriorita, new Comparator<RicevutaDTO>() { // prendo quella cronologicamente più recente
			@Override
			public int compare(final RicevutaDTO o1, final RicevutaDTO o2) {
				return o1.getData().compareTo(o2.getData());
			}
		});
		return listaPriorita.isEmpty() ? new RicevutaDTO() : listaPriorita.get(0);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public PaginatedList<DettaglioCorrispondenzaDTO> searchCorrispondenza(final CorrispondenzaSearch filters)
			throws Exception {
		log.trace("Entrato nel service: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");

		if (filters.getIdFascicolo() == null || filters.getIdFascicolo() <= 0) {
			log.error("ERRORE. Specificare un idFascicolo valido!");
			throw new Exception("ERRORE. Specificare un idFascicolo valido!");
		}

		PaginatedList<CorrispondenzaDTO> listaTemp = repository.search(filters);

		List<DettaglioCorrispondenzaDTO> lista = new ArrayList<DettaglioCorrispondenzaDTO>();

		if (listaTemp.getList() != null) {
			listaTemp.getList().forEach(elem -> {
				DettaglioCorrispondenzaDTO corrispondenza = new DettaglioCorrispondenzaDTO(elem);

				DestinatarioSearch searchD = new DestinatarioSearch();
				searchD.setIdCorrispondenza(elem.getId());
				RicevutaSearch searchR = new RicevutaSearch();
				searchR.setIdCorrispondenza(elem.getId());
				try {
					List<DestinatarioDTO> destinatari = destinatarioService.search(searchD).getList();
					// modifico il colore del pallino in base allo stato della ricevuta più
					// significativa riferita a quel destinatario
					// in teoria il campo "stato" della tabella "destinatario" dovrebbe essere
					// cambiato in modo asincrono dal KafkaConsumer
					for (DestinatarioDTO destinatario : destinatari) {
						searchR.setIdDestinatario(destinatario.getId());
						List<RicevutaDTO> listaRicevute = ricevutaService.search(searchR).getList();
						RicevutaDTO ricevuta = this.ricevutaPiuSignificativa(listaRicevute);
						if (ricevuta.getTipoRicevuta() == TipoRicevuta.AVVENUTA_CONSEGNA) {
							destinatario.setStato(StatoCorrispondenza.ESITO_POSITIVO);
						} else if (ricevuta.getTipoRicevuta() == TipoRicevuta.PREAVVISO_ERRORE_CONSEGNA
								|| ricevuta.getTipoRicevuta() == TipoRicevuta.ERRORE_CONSEGNA
								|| ricevuta.getTipoRicevuta() == TipoRicevuta.RILEVAZIONE_VIRUS
								|| ricevuta.getTipoRicevuta() == TipoRicevuta.NON_ACCETTAZIONE) {
							destinatario.setStato(StatoCorrispondenza.ESITO_CON_ERRORE);
						} else if (ricevuta.getTipoRicevuta() == TipoRicevuta.PRESA_IN_CARICO
								|| ricevuta.getTipoRicevuta() == TipoRicevuta.POSTA_CERTIFICATA
								|| ricevuta.getTipoRicevuta() == TipoRicevuta.ACCETTAZIONE) {
							destinatario.setStato(StatoCorrispondenza.INVIATA);
						}
					}
					corrispondenza.setDestinatari(destinatari); // TODO: fare una search apposita che prelevi anche le
																// info sui destinatari
				} catch (Exception e) {
					log.error("Errore nel recupero dei destinatari per la corrispondenza con id=" + elem.getId(), e);
				}
				try {
//					corrispondenza.setAllegati(allegatoService.nomeAllegatiCorrispondenza(elem.getId()));
					corrispondenza.setAllegatiInfo(allegatoService.infoAllegatiCorrispondenza(elem.getId()));
				} catch (Exception e) {
					log.error("Errore nel recupero degli allegati per la corrispondenza con id=" + elem.getId(), e);
				}
				lista.add(corrispondenza);
			});
		}

		return new PaginatedList<DettaglioCorrispondenzaDTO>(lista, listaTemp.getCount());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public DettaglioCorrispondenzaDTO inviaOsalvaComunicazione(final NuovaComunicazioneDTO comunicazione)
			throws Exception {
		log.trace("Entrato nel service: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");

		if (comunicazione.getIdFascicoli() == null || comunicazione.getIdFascicoli().isEmpty()) {
			log.error("ERRORE. Id Fascicolo nullo o errato!");
			throw new Exception("ERRORE. Id Fascicolo nullo o errato!");
		}

		if (comunicazione.isBozza() == false) {
			if (comunicazione.getDestinatari() == null || comunicazione.getDestinatari().isEmpty()
					|| comunicazione.getOggetto() == null || comunicazione.getOggetto().isEmpty()
					|| comunicazione.getTesto() == null || comunicazione.getTesto().isEmpty()) {
				log.error("ERRORE. I dati della comunicazione sono incompleti!");
				throw new Exception("ERRORE. I dati della comunicazione sono incompleti!");
			}
		} else {
			if (comunicazione.getDestinatari() != null) {
				for (TipologicaDestinatarioDTO destinatario : comunicazione.getDestinatari()) {
					if (destinatario.getEmail() == null)
						destinatario.setEmail("");
				}
			}
		}

		comunicazione.setTipoTemplate(null); // le comunicazioni non hanno un template di default
		comunicazione.setComunicazione(true);

//		if (comunicazione.getIdAllegati()==null)
//			comunicazione.setIdAllegati(new ArrayList<Long>());

		Long idCorrispondenza = comunicazione.getId();

		if (idCorrispondenza != null && idCorrispondenza > 0) { // se sto modificando (una comunicazione) o inviando una
																// (comunicazione) in bozza

			// se NON sto salvando in bozza, devo prendere tutti gli allegati GIA' associati
			// alla comunicazione e protocollarli uno ad uno
			if (comunicazione.isBozza() == false) {
//per adesso non protocolliamo, poi vedremo come ... (Adriano Colaianni 2020.09.29)				
//				List<AllegatoCorrispondenzaDTO> listaAC = allegatoCorrispondenzaService.search(new AllegatoCorrispondenzaSearch(null, idCorrispondenza)).getList();
//				if (listaAC!=null && !listaAC.isEmpty()) {
//					for (int i = 0; i < listaAC.size(); i++) {
//						AllegatoDTO allegato = allegatoService.find(listaAC.get(i).getIdAllegato());
//						File file = allegatoService.downloadFile(null, allegato.getId(), null, null, null);
//						MockMultipartFile mpFile = new MockMultipartFile(allegato.getNome(), file);
//						String numeroProtocollo = allegatoService.protocolla(mpFile, allegato, ProtocolNumberType.O);
//						allegatoService.protocolla(allegato.getId(), null, numeroProtocollo);
//						file.deleteOnExit();
//					}
//				}
			}

			// cancello i destinatari attualmente presenti sul DB
			DestinatarioSearch searchD = new DestinatarioSearch();
			searchD.setIdCorrispondenza(idCorrispondenza);
			if (destinatarioService.count(searchD) > 0)
				if (destinatarioService.delete(searchD) < 1) {
					log.error("Errore nella cancellazione dei destinatari della corrispondenza con id="
							+ idCorrispondenza);
					throw new Exception("Errore nella cancellazione dei destinatari della corrispondenza con id="
							+ idCorrispondenza);
				}

			// non possono esserci nuovi allegati perchè, per le comunuicazioni già create,
			// uso il metodo specifico di upload

//			// cancello gli allegati associati ma non più presenti in bozza -- al momento li cancello direttamente da FE con una apposita chiamata
//			AllegatoCorrispondenzaSearch searchAC = new AllegatoCorrispondenzaSearch();
//			searchAC.setIdCorrispondenza(idCorrispondenza);
//			List<AllegatoCorrispondenzaDTO> listaAC = allegatoCorrispondenzaService.search(searchAC).getList();
//			
//			if (listaAC!=null) {
//				listaAC.forEach(allegatoOld->{
//					boolean presente = false;
//					comunicazione.getIdAllegati().forEach(allegatoNew->{
//						if (allegatoNew.equals(allegatoOld.getIdAllegato()))
//							presente = true;
//					});
//					if (presente==false)
//						try {
//							allegatoService.eliminaAllegato(allegatoOld.getIdAllegato());
//						} catch (Exception e) {
//							log.error("Errore nella cancellazione dell'allegato con id="+allegatoOld.getIdAllegato());
//							e.printStackTrace();
//						}
//				});
//			}

		}

		// operazioni da eseguire in ogni caso:

		if (comunicazione.getAllegati() != null) {
			comunicazione.getAllegati().forEach(allegato -> {
				allegato.setTipo(TipoAllegato.COMUNICAZIONE);
				if (comunicazione.isBozza() == false) {
					allegato.setNumeroProtocolloOut("DA PROTOCOLLARE");
				}
			});
		}
		if (comunicazione.getDestinatari() != null) {
			for (TipologicaDestinatarioDTO destinatario : comunicazione.getDestinatari()) {
				if (destinatario.getNome() == null)
					destinatario.setNome("#GestioneComunicazioni");
			}
		}
		if (!comunicazione.isBozza()) {
			// la salvo in bozza, prelevo gli allegati, compongo l'url e appendo al corpo e
			// poi invio...
			comunicazione.setBozza(true);
			// la salvo per ottenere l'id della corrispondenza e l'id degli allegati
			idCorrispondenza = this.inviaCorrispondenza(comunicazione);
			comunicazione.setId(idCorrispondenza);
			DettaglioCorrispondenzaDTO dettaglioCorrispondenza = dettaglioCorrispondenza(idCorrispondenza, true);
			// appendo gli url nel corpo
			this.aggiungiUrlAllegatiInCorpo(comunicazione, dettaglioCorrispondenza);
			// salvo nuovamente il corpo
			repository.update(new CorrispondenzaDTO(idCorrispondenza, comunicazione.getOggetto(),
					comunicazione.getTesto(), comunicazione.isComunicazione(), comunicazione.isBozza()));
			this.inviaMail(comunicazione);
		} else {
			idCorrispondenza = this.inviaCorrispondenza(comunicazione);
		}

		return this.dettaglioCorrispondenza(idCorrispondenza, false);
	}

	@Override
	public void aggiungiUrlAllegatiInCorpo(final NuovaComunicazioneDTO comunicazione,
			final DettaglioCorrispondenzaDTO dettCorr) throws Exception {
		final StringBuilder paragrafoUrl = new StringBuilder();
		if (ListUtil.isEmpty(dettCorr.getAllegatiInfo()))
			return;
		List<AllegatoCustomDTO> allegatiUrl = dettCorr.getAllegatiInfo().stream()
				.filter(allegato -> allegato.getIsUrl() != null && allegato.getIsUrl()).collect(Collectors.toList());
		if (ListUtil.isEmpty(allegatiUrl))
			return;
		paragrafoUrl.append("<br>").append("Allegati:").append("<br>");
		for (AllegatoCustomDTO allegato : allegatiUrl) {
//			String baseUrl = configuration.getString(CorrispondenzaService.KEY_URL_DOWNLOAD_PREFIX);
			String baseUrl = commonService.getConfigurationValue(CorrispondenzaService.KEY_URL_DOWNLOAD_PREFIX,
					this.props.getCodiceApplicazione());
			String urlAllegato = baseUrl + "/public/" + CorrispondenzaService.PUBLIC_DOWNLOAD_ALLEGATO_COMUNICAZIONE
					+ "?idPratica=" + comunicazione.getIdFascicoli().get(0) + "&idCorrispondenza="
					+ comunicazione.getId() + "&idAllegato=" + allegato.getId();
			paragrafoUrl.append("<br>").append("<a href=\"" + urlAllegato + "\">").append(allegato.getNome())
					.append("</a>");
		}
		comunicazione.setTesto(comunicazione.getTesto() + paragrafoUrl.toString());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public boolean cancellaBozza(final long idCorrispondenza) throws Exception {
		log.trace("Entrato nel service: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");

		// TODO: magari facendomi passare anche l'idFascicolo potrei controllare che la
		// corrispondenza sia collegata a quello specifico fascicolo
		// In questo metodo se qualcosa va in eccezione, il rollback preserva il DB, ma
		// gli allegati di Alfresco vengono cancellati fisicamente!!!

		AllegatoCorrispondenzaSearch searchAC = new AllegatoCorrispondenzaSearch();
		searchAC.setIdCorrispondenza(idCorrispondenza);
		List<AllegatoCorrispondenzaDTO> listaAC = allegatoCorrispondenzaService.search(searchAC).getList();
		if (listaAC != null)
			listaAC.forEach(elem -> {
				try {
					allegatoService.eliminaAllegato(elem.getIdAllegato());
				} catch (Exception e) {
					log.error("Errore nella cancellazione dell'allegato con id=" + elem.getIdAllegato(), e);
				}
			});

		DestinatarioSearch searchD = new DestinatarioSearch();
		searchD.setIdCorrispondenza(idCorrispondenza);
		if (destinatarioService.count(searchD) > 0)
			if (destinatarioService.delete(searchD) < 1) {
				log.error("Errore nella cancellazione dei destinatari della corrispondenza con id=" + idCorrispondenza);
				throw new Exception(
						"Errore nella cancellazione dei destinatari della corrispondenza con id=" + idCorrispondenza);
			}

		FascicoloCorrispondenzaSearch searchFC = new FascicoloCorrispondenzaSearch();
		searchFC.setIdCorrispondenza(idCorrispondenza);
		if (fascicoloCorrispondenzaService.delete(searchFC) != 1) {
			log.error(
					"Errore nella cancellazione del record della tabella 'fascicolo_corrispondenza' [idCorrispondenza="
							+ idCorrispondenza + "]");
			throw new Exception(
					"Errore nella cancellazione del record della tabella 'fascicolo_corrispondenza' [idCorrispondenza="
							+ idCorrispondenza + "]");
		}

		CorrispondenzaSearch searchC = new CorrispondenzaSearch();
		searchC.setId(idCorrispondenza);
		searchC.setBozza(true);
		if (this.delete(searchC) != 1) {
			log.error("Errore nella cancellazione della corrispondenza con id=" + idCorrispondenza);
			throw new Exception("Errore nella cancellazione della corrispondenza con id=" + idCorrispondenza);
		}

		return true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public AllegatoCustomDTO caricaAllegato(final List<Long> idFascicoli, final long idCorrispondenza,
			final MultipartFile file, final boolean isUrl) throws Exception {

		// OCCHIO !!! io devo protocollare gli allegati SOLO al momento dell'invio
		// effettivo della comunicazione

		AllegatoCustomDTO allegato = null;
		try {
			allegato = allegatoService.inserisciAllegato(idFascicoli, TipoAllegato.COMUNICAZIONE, file,
					null /* informazioni */);
			if (allegato == null)
				throw new Exception("Errore. Allegato nullo!");
		} catch (Exception e) {
			log.error("Errore nell'inserimento dell'allegato relativo alla corrispondenza con id=" + idCorrispondenza,
					e);
		}
		try {
			allegatoCorrispondenzaService
					.insert(new AllegatoCorrispondenzaDTO(allegato.getId(), idCorrispondenza, isUrl));
		} catch (Exception e) {
			log.error("Errore nell'inserimento del record nella tabella 'allegato_corrispondenza'. [IdAllegato="
					+ allegato.getId() + " IdCorrispondenza=" + idCorrispondenza + "]", e);
		}
		// non dovrebbe essere necessario inserire dei record (uno per ogni
		// destinatario) nella tabella "allegato_ente"

		return allegato;
	}

	@Override
	public String checkEmail(final String email) {

		// il primo parametro, quando è settato a true , consente che l'email sia un
		// indirizzo locale
		// , quando è settato a false, impedisce che l'email sia un indirizzo locale
		// il secondo parametro non ho ben capito che tipo di validazione fa, nè in
		// quali casi mi restituisce invalid

		if ((EmailValidator.getInstance(true).isValid(email)) && (!EmailValidator.getInstance(false).isValid(email)))
			return "Gli indirizzi locali non sono consentiti!";

		if ((EmailValidator.getInstance(false, true).isValid(email))
				&& (!EmailValidator.getInstance(false, false).isValid(email)))
			return "Errore per TLD=true ";

		if ((EmailValidator.getInstance(false, false).isValid(email))
				&& (!EmailValidator.getInstance(false, true).isValid(email)))
			return "Errore per TLD=false";

		return null;
	}

	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	private void risolviPlaceHolders(final NuovaComunicazioneDTO corrispondenza, final InformazioniDTO info)
			throws Exception {

		TemplateEmailDTO template = templateEmailService.find(corrispondenza.getTipoTemplate());

		String oggetto = template.getOggetto();
		String testo = template.getTesto();

		if (StringUtil.isEmpty(oggetto)) {
			throw new CustomOperationToAdviceException("ERRORE! L'oggetto dell'email non può essere vuoto!");
		}
		if (StringUtil.isEmpty(testo)) {
			throw new CustomOperationToAdviceException("ERRORE! Il testo dell'email non può essere vuoto!");
		}

		PlaceHolder[] phs = corrispondenza.getTipoTemplate().getPlaceholdersAmmessi();
		Set<String> codici = new HashSet<>();
		for (PlaceHolder ph : phs) {
			codici.add(ph.fullName());
		}

		if (corrispondenza.getInfoPlaceHolders().getIdFascicolo() != null) {
			corrispondenza.getInfoPlaceHolders().setFascicolo(info != null ? info
					: fascicoloService.datiFascicolo(corrispondenza.getInfoPlaceHolders().getIdFascicolo()));
			corrispondenza.getInfoPlaceHolders().setRichiedente(
					richiedenteService.datiRichiedente(corrispondenza.getInfoPlaceHolders().getIdFascicolo()));
		}

		for (String codice : codici) {
			String risolto = this.risolviSingoloPH(codice, corrispondenza.getInfoPlaceHolders());
			oggetto = oggetto.replace(codice, risolto);
			testo = testo.replace(codice, risolto);
		}
		corrispondenza.setOggetto(oggetto);
		corrispondenza.setTesto(testo);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = {Exception.class }, timeout = 60000, readOnly = true)
	public String risolviSingoloPH(final String codice, final InfoPlaceHoldersDTO infoPlaceHolders) throws Exception {

		String risolto = "";

		if (codice.equals(PlaceHolder.CODICE_FASCICOLO.fullName())) {
			risolto = infoPlaceHolders.getFascicolo().getCodice();
		} else if (codice.equals(PlaceHolder.OGGETTO.fullName())) {
			risolto = infoPlaceHolders.getFascicolo().getOggettoIntervento();
		} else if (codice.equals(PlaceHolder.TIPO_PRECEDIMENTO.fullName())) {
			risolto = infoPlaceHolders.getFascicolo().getTipoProcedimento().getTextValue();
		} else if (codice.equals(PlaceHolder.COMUNE.fullName())) {
			List<Long> idComuni = localizzazioneInterventoService.selectEffettivo(infoPlaceHolders.getIdFascicolo());
			for (Long elem : idComuni) {
				risolto = risolto + (commonService.findEnteById(elem.intValue()).getNome()) + "-";
			}
			;
			risolto = risolto.substring(0, risolto.length() - 1);
		} else if (codice.equals(PlaceHolder.ENTE_PROCEDENTE.fullName())) {
			risolto = commonService.getDenominazioneOrganizzazione(infoPlaceHolders.getFascicolo().getOrgCreazione());
		} else if (codice.equals(PlaceHolder.NOME_COGNOME_RICHIEDENTE.fullName())) {
			risolto = infoPlaceHolders.getRichiedente().getNome() + " "
					+ infoPlaceHolders.getRichiedente().getCognome();
		} else if (codice.equals(PlaceHolder.NUMERO_PROVVEDIMENTO.fullName())) {
			risolto = infoPlaceHolders.getFascicolo().getNumeroProvvedimento();
		} else if (codice.equals(PlaceHolder.DATA_PROVVEDIMENTO.fullName())) {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			risolto = formatter.format(infoPlaceHolders.getFascicolo().getDataRilascioAutorizzazione());
		} else if (codice.equals(PlaceHolder.RUP.fullName())) {
			risolto = infoPlaceHolders.getFascicolo().getRup();
		} else if (codice.equals(PlaceHolder.ESITO_PROVVEDIMENTO.fullName())) {
			risolto = infoPlaceHolders.getFascicolo().getEsito().getTextValue();
		} else if (codice.equals(PlaceHolder.NOME_FILE_ULTERIORE_DOC.fullName())) {
			risolto = infoPlaceHolders.getUlterDocNomeFile();
		} else if (codice.equals(PlaceHolder.LINK_FILE_ULTERIORE_DOC.fullName())) {
			risolto = infoPlaceHolders.getUlterDocLinkFile();
		}
		else if(codice.equals(PlaceHolder.ALLEGATI_ULTERIORE_DOCUMENTAZIONE.fullName()))
		{
			risolto = infoPlaceHolders.getUlterioreDocumentazione();
		}
		else if (codice.equals(PlaceHolder.TITOLO_FILE_ULTERIORE_DOC.fullName())) {
			risolto = infoPlaceHolders.getUlterDocTitolo();
		} else if (codice.equals(PlaceHolder.DESCRIZIONE_FILE_ULTERIORE_DOC.fullName())) {
			risolto = infoPlaceHolders.getUlterDocDescrizione();
		} else if (codice.equals(PlaceHolder.PROTOCOLLO_SET_CAMPIONAMENTO.fullName())) {
			risolto = infoPlaceHolders.getCampionamento().getProtocollo();
		} else if (codice.equals(PlaceHolder.DATA_INIZIO_CAMPIONAMENTO.fullName())) {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			risolto = formatter.format(infoPlaceHolders.getCampionamento().getDataInizio());
		} else if (codice.equals(PlaceHolder.DATA_PROTOCOLLO_CAMPIONAMENTO.fullName())) {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			risolto = formatter.format(infoPlaceHolders.getCampionamento().getDataProtocollo());
		} else if (codice.equals(PlaceHolder.DATA_FINE_CAMPIONAMENTO.fullName())) {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date dataEnd = DateUtil.addDays(infoPlaceHolders.getCampionamento().getDataCampionatura(), -1);
			risolto = formatter.format(dataEnd);
		} else if (codice.equals(PlaceHolder.DATA_SCADENZA_MODIFICHE.fullName())) {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			risolto = formatter.format(infoPlaceHolders.getFascicolo().getModificabileFino());
		}

		else if (codice.equals(PlaceHolder.DATA_FINE_VERIFICA.fullName())) {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String stringified = formatter.format(infoPlaceHolders.getCampionamento().getDataCampionatura());
			Date dataCampionatura = formatter.parse(stringified);
			LocalDate dataCampionamento = dataCampionatura.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate dataFineVerifica = dataCampionamento
					.plusDays(infoPlaceHolders.getCampionamento().getIntervalloVer());
			risolto = dataFineVerifica.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		} else if (codice.equals(PlaceHolder.NOME_COGNOME_USER_ASSEGNATARIO.fullName())) {
			risolto = infoPlaceHolders.getInfoAssegnatario();
		} else if (codice.equals(PlaceHolder.URL_DOWNLOAD_RICEVUTA_PROTETTO.fullName())) {
			risolto = infoPlaceHolders.getUrlRicevutaTrasmissione();
		} else if (codice.equals(PlaceHolder.URL_DOWNLOAD_PROVVEDIMENTO_PROTETTO.fullName())) {
			risolto = infoPlaceHolders.getUrlProvvedimentoFinale();
		} else if (codice.equals(PlaceHolder.MOTIVAZIONE.fullName())) {
			if (infoPlaceHolders.getRichiestaPostTrasmissione() != null
					&& StringUtil.isNotEmpty(infoPlaceHolders.getRichiestaPostTrasmissione().getMotivazione())) {
				risolto = infoPlaceHolders.getRichiestaPostTrasmissione().getMotivazione();
			}
		}

		return StringUtil.isNotEmpty(risolto) ? risolto : "[Dato non disponibile]";
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public Long inviaCorrispondenza(final NuovaComunicazioneDTO corrispondenza) throws Exception {
		return this.inviaCorrispondenza(corrispondenza, false);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public Long inviaCorrispondenza(final NuovaComunicazioneDTO corrispondenza, final InformazioniDTO info)
			throws Exception {
		return this.inviaCorrispondenza(corrispondenza, false, false, info);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public Long inviaCorrispondenza(final NuovaComunicazioneDTO corrispondenza,
			final boolean aggiungiDestinatariDaTemplate) throws Exception {
		return this.inviaCorrispondenza(corrispondenza, false, false, null);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public Long inviaCorrispondenza(final NuovaComunicazioneDTO corrispondenza,
			final boolean aggiungiDestinatariDaTemplate, final boolean allegatiPrecaricati) throws Exception {
		return this.inviaCorrispondenza(corrispondenza, aggiungiDestinatariDaTemplate, allegatiPrecaricati, null);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public Long inviaCorrispondenza(final NuovaComunicazioneDTO corrispondenza,
			final boolean aggiungiDestinatariDaTemplate, final boolean allegatiPrecaricati, final InformazioniDTO info)
			throws Exception {
		log.trace("Entrato nel service: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");

		TemplateEmailDestinatariDTO templateEmail = null;
		if (corrispondenza.getTipoTemplate() != null) {
			// prendo oggetto e corpo dal template...forse per alcune funzionalità andrebbe
			// bypassato questo comportamento...
			templateEmail = templateEmailService.info(corrispondenza.getTipoTemplate());
			if (templateEmail != null) {
				corrispondenza.setOggetto(templateEmail.getOggetto());
				corrispondenza.setTesto(templateEmail.getTesto());
				if (corrispondenza.getTipoTemplate() != TipoTemplate.INVIO_COMUNICAZIONI)
					this.risolviPlaceHolders(corrispondenza, info);
			}
		}
		Long idCorrispondenza = corrispondenza.getId();
		if (aggiungiDestinatariDaTemplate && templateEmail != null) {
			if (templateEmail != null) {
				List<DestinatarioTemplateDTO> destinatari = templateEmail.getDestinatari();
				if (destinatari != null) {
					destinatari.forEach(dest -> {
						if (dest != null) {
							corrispondenza.getDestinatari().add(new DestinatarioTemplateDTO(dest));
						}
					});
				}
			}
		}

		if (idCorrispondenza == null) {
			// popolo la tabella "corrispondenza"
			idCorrispondenza = repository.insert(new CorrispondenzaDTO(idCorrispondenza, corrispondenza.getOggetto(),
					corrispondenza.getTesto(), corrispondenza.isComunicazione(), corrispondenza.isBozza()));
			final Long Id_Corrispondenza = idCorrispondenza;
			// popolo la tabella "fascicolo_corrispondenza"
			corrispondenza.getIdFascicoli().forEach(idFascicolo -> {
				try {
					fascicoloCorrispondenzaService
							.insert(new FascicoloCorrispondenzaDTO(null, Id_Corrispondenza, idFascicolo));
				} catch (Exception e) {
					log.error("Errore nell'inserimento di un record nella tabella Fascicolo_Corrispondenza", e);
				}
			});
		} else {
			// aggiorno la tabella "corrispondenza"
			repository.update(new CorrispondenzaDTO(idCorrispondenza, corrispondenza.getOggetto(),
					corrispondenza.getTesto(), corrispondenza.isComunicazione(), corrispondenza.isBozza()));
		}

		final Long id_corrispondenza = idCorrispondenza;

		if (corrispondenza.getAllegati() != null) {
			corrispondenza.getAllegati().forEach(allegato -> {
				Long idAllegato = null;
				try {
					if (allegatiPrecaricati) {
						idAllegato = allegato.getId();
					} else {
						idAllegato = allegatoService.inserisciAllegato(corrispondenza.getIdFascicoli(),
								allegato.getTipo(), allegato.getFile(), allegato).getId();
						allegato.setId(idAllegato);
					}
					// popolo le tabelle "allegato" e "allegato_fascicolo"
					if (idAllegato == null)
						throw new Exception("Errore. Id dell'allegato nullo!");
				} catch (Exception e) {
					log.error("Errore nell'inserimento dell'allegato relativo alla corrispondenza con id="
							+ id_corrispondenza, e);
				}
				try {
					// popolo la tabella "allegato_corrispondenza"
					allegatoCorrispondenzaService
							.insert(new AllegatoCorrispondenzaDTO(idAllegato, id_corrispondenza, allegato.getIsUrl()));
				} catch (Exception e) {
					log.error("Errore nell'inserimento del record nella tabella 'allegato_corrispondenza'. [IdAllegato="
							+ allegato + " IdCorrispondenza=" + id_corrispondenza + "]", e);
				}
				// non dovrebbe essere necessario inserire dei record (uno per ogni destinatario
				// o per ogni allegato) nella tabella "allegato_ente"
			});
		}

		if (corrispondenza.getDestinatari() != null && !corrispondenza.getDestinatari().isEmpty()) {
			corrispondenza.getDestinatari().forEach(destinatario -> {
				try {
					// popolo la tabella "destinatario"
					destinatarioService.insert(new DestinatarioDTO(destinatario.getTipo(), destinatario.getEmail(),
							StatoCorrispondenza.INVIATA, id_corrispondenza, destinatario.getNome())); // fabio lopez -
																										// inserire pec
																										// e altre
																										// cose?????
				} catch (Exception e) {
					log.error("Errore nell'inserimento del destinatario: " + destinatario.getNome()
							+ " relativo alla corrispondenza con id=" + id_corrispondenza, e);
				}
			});
		}
		if (corrispondenza.isBozza() == false) {
			webmailSvc.inviaMail(corrispondenza.mapToInvioMailDto(ccpd.getCasellaPostale().getIndirizzoMail()),
					corrispondenza.mapAllegatiToResource(), id_corrispondenza);
		}
		return id_corrispondenza;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public void inviaMail(final NuovaComunicazioneDTO corrispondenza) throws Exception {
		if (corrispondenza.isBozza()) {
			webmailSvc.inviaMail(corrispondenza.mapToInvioMailDto(ccpd.getCasellaPostale().getIndirizzoMail()),
					corrispondenza.mapAllegatiToResource(), corrispondenza.getId());
			this.updateBozza(corrispondenza.getId(), false);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public NuovaComunicazioneDTO getTemplate(final long idFascicolo) throws Exception {
		log.trace("Entrato nel service: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");

		TemplateEmailDestinatariDTO template = templateEmailService.info(TipoTemplate.INVIO_COMUNICAZIONI);

		TemplateDestinatarioSearch searchTD = new TemplateDestinatarioSearch();
		searchTD.setCodiceTemplate(TipoTemplate.INVIO_COMUNICAZIONI);
		List<DestinatarioTemplateDTO> destinatariDefault = templateDestinatarioService.search(searchTD).getList();

		NuovaComunicazioneDTO templateEmail = new NuovaComunicazioneDTO();
		templateEmail.setTipoTemplate(TipoTemplate.INVIO_COMUNICAZIONI);
		templateEmail.setOggetto(template.getOggetto());
		templateEmail.setTesto(template.getTesto());
		templateEmail.getInfoPlaceHolders().setIdFascicolo(idFascicolo);

		this.risolviPlaceHolders(templateEmail, null);

		if (destinatariDefault != null) {
			destinatariDefault.forEach(destinatario -> {
				templateEmail.getDestinatari().add(new DestinatarioTemplateDTO(destinatario));
			});
		}

		return templateEmail;
	}

}