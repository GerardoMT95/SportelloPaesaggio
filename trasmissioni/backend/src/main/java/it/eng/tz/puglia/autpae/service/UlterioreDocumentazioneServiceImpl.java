package it.eng.tz.puglia.autpae.service;

import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.dto.AllegatoCustomDTO;
import it.eng.tz.puglia.autpae.dto.AllegatoInfoDTO;
import it.eng.tz.puglia.autpae.dto.DestinatarioTemplateDTO;
import it.eng.tz.puglia.autpae.dto.NuovaComunicazioneDTO;
import it.eng.tz.puglia.autpae.dto.TipologicaDestinatarioDTO;
import it.eng.tz.puglia.autpae.dto.UlterioreDocumentazioneDTO;
import it.eng.tz.puglia.autpae.entity.AllegatoCorrispondenzaDTO;
import it.eng.tz.puglia.autpae.entity.AllegatoDTO;
import it.eng.tz.puglia.autpae.entity.AllegatoEnteDTO;
import it.eng.tz.puglia.autpae.entity.CorrispondenzaDTO;
import it.eng.tz.puglia.autpae.entity.DestinatarioDTO;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.enumeratori.PlaceHolder;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.enumeratori.TipoTemplate;
import it.eng.tz.puglia.autpae.search.AllegatoCorrispondenzaSearch;
import it.eng.tz.puglia.autpae.search.AllegatoEnteSearch;
import it.eng.tz.puglia.autpae.search.DestinatarioSearch;
import it.eng.tz.puglia.autpae.search.TemplateDestinatarioSearch;
import it.eng.tz.puglia.autpae.search.UlterioreDocumentazioneSearch;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoCorrispondenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoEnteService;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoService;
import it.eng.tz.puglia.autpae.service.interfacce.CorrispondenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.DestinatarioService;
import it.eng.tz.puglia.autpae.service.interfacce.TemplateDestinatarioService;
import it.eng.tz.puglia.autpae.service.interfacce.TemplateEmailService;
import it.eng.tz.puglia.autpae.service.interfacce.UlterioreDocumentazioneService;
import it.eng.tz.puglia.autpae.utility.VarieUtils;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.UserUtil;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.CommonService;

@Service
public class UlterioreDocumentazioneServiceImpl implements UlterioreDocumentazioneService {
	
	private static final Logger log = LoggerFactory.getLogger(UlterioreDocumentazioneServiceImpl.class);
//	private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot access attachments for plan exclusion of this municipality";
	
	@Autowired  private ApplicationProperties props;
	@Autowired AllegatoService allegatoService;
	@Autowired CorrispondenzaService corrispondenzaService;
	@Autowired AllegatoCorrispondenzaService allegatoCorrispondenzaService;
	@Autowired AllegatoEnteService allegatoEnteService;
	@Autowired DestinatarioService destinatarioService;
	@Autowired TemplateEmailService templateEmailService;
	@Autowired TemplateDestinatarioService templateDestinatarioService;
	@Autowired UserUtil userUtil;
	@Autowired CommonService commonSvc;
	//@Autowired  private ISitPugliaConfiguration configuration;
	
	
	@Transactional(propagation=Propagation.MANDATORY, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	private ArrayList<UlterioreDocumentazioneDTO> datiDocumentazione(Long idFascicolo, Long idAllegato) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		ArrayList<UlterioreDocumentazioneDTO> listaDocumentazione = new ArrayList<UlterioreDocumentazioneDTO>();

		List<AllegatoDTO> listaAllegati = allegatoService.datiAllegato(idFascicolo, Collections.singletonList(TipoAllegato.ULTERIORE_DOCUMENTAZIONE));
		if (!listaAllegati.isEmpty()) {
			 listaAllegati.forEach(elem -> {
				if (idAllegato!=null) {						// se ho in input uno specifico idAllegato, recupero le informazioni relative solo a quell'allegato
					if (elem.getId().equals(idAllegato))
						listaDocumentazione.add(new UlterioreDocumentazioneDTO(elem));
				}
				else										// se ho in input idAllegato=null, recupero le informazioni relative a tutti gli allegati
					listaDocumentazione.add(new UlterioreDocumentazioneDTO(elem));
			});
		}
		if (!listaDocumentazione.isEmpty()) {
			 listaDocumentazione.forEach(allegato -> {
				AllegatoCorrispondenzaSearch search1 = new AllegatoCorrispondenzaSearch();
				search1.setIdAllegato(allegato.getId());
				List<AllegatoCorrispondenzaDTO> listaAC = null;
				try {
					listaAC = allegatoCorrispondenzaService.search(search1).getList();
				} catch (Exception e) {
					log.error("Errore nella ricerca dei record di AllegatoCorrispondenza",e);
				}
				if (listaAC!=null) {
					listaAC.forEach(elem -> {
						DestinatarioSearch search2 = new DestinatarioSearch();
						search2.setIdCorrispondenza(elem.getIdCorrispondenza());
						List<DestinatarioDTO> listaDestinatari = null;
						try {
							listaDestinatari = destinatarioService.search(search2).getList();
						} catch (Exception e) {
							log.error("Errore nella ricerca della lista Destinatari",e);
						}
						if (listaDestinatari!=null)
							listaDestinatari.forEach(destinatario -> allegato.getNotifica().add(new TipologicaDestinatarioDTO(destinatario)));
					});
				}
				AllegatoEnteSearch search3 = new AllegatoEnteSearch();
				search3.setIdAllegato(allegato.getId());
				List<AllegatoEnteDTO> listaEnti = null;
				try {
					listaEnti = allegatoEnteService.search(search3).getList();
				} catch (Exception e) {
					log.error("Errore nella ricerca della lista Enti",e);
				}
				if (listaEnti!=null)
					listaEnti.forEach(ente -> allegato.getEnti().add(ente.getCodice()));
			});
		}
		return listaDocumentazione;
	}

	
	@Override
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public UlterioreDocumentazioneDTO inserisciDocumentazione(long idFascicolo, String titolo, String descrizione, List<TipologicaDestinatarioDTO> notificaA,
															  List<String> visualizzabileDa, MultipartFile file) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		Set<String> visualDa = new HashSet<>();
		
		// ripulisco ciò che mi arriva togliendo gli id
		for (String elem : visualizzabileDa) {
			visualDa.add(userUtil.getGruppo(elem).name());
		}
		// in ogni caso, aggiungo alla lista di visualizzabilità il gruppo dell'utente che sta inserendo la documentazione
		visualDa.add(userUtil.getGruppo().name());
		
        NuovaComunicazioneDTO corrispondenza = new NuovaComunicazioneDTO();
        
        corrispondenza.setIdFascicoli(Collections.singletonList(idFascicolo));
        corrispondenza.setBozza(true); //mi serve l'idCorrispondenza e idAllegato
        corrispondenza.setTipoTemplate(TipoTemplate.INVIO_ULTERIORE_DOCUMENTAZIONE);
		
		AllegatoDTO informazioni = new AllegatoDTO();
		informazioni.setTitolo(titolo);
		informazioni.setDescrizione(descrizione);
		informazioni.setTipo(TipoAllegato.ULTERIORE_DOCUMENTAZIONE);
		if (visualDa!=null && visualDa.contains(Gruppi.REG_.name())) {
			informazioni.setNumeroProtocolloOut("DA PROTOCOLLARE");
		}
		//l'allegato va inviato sotto forma di url e non allegato fisico...
		corrispondenza.getAllegati().add(new AllegatoInfoDTO(informazioni, file,true)); 
		
	//	for (TipologicaDestinatarioDTO destinatario : notificaA) {
	//		String erroreEmail = corrispondenzaService.checkEmail(destinatario.getEmail());
	//		if((erroreEmail)!=null) {
	//			log.error("Email non valida: ["+destinatario.getEmail()+"] Tipo errore: " + erroreEmail);
	//			throw new Exception("Email non valida: ["+destinatario.getEmail()+"] Tipo errore: " + erroreEmail);
	//		};
	//		corrispondenza.getDestinatari().add(destinatario); //new TipologicaDestinatarioDTO(emailDestinatario, "#UlterioreDocumentazione", null);
	//	}
	
		// 1) cerco i destinatari di default
		List<TipologicaDestinatarioDTO> listaDestinatariDefault = new ArrayList<>();
		
		TemplateDestinatarioSearch searchTD = new TemplateDestinatarioSearch();
		searchTD.setCodiceTemplate(TipoTemplate.INVIO_ULTERIORE_DOCUMENTAZIONE);
		List<DestinatarioTemplateDTO> destinatariDefault = templateDestinatarioService.search(searchTD).getList();
		
		if (destinatariDefault!=null) {
			destinatariDefault.forEach(destinatario-> {
				listaDestinatariDefault.add(new DestinatarioTemplateDTO(destinatario)); 
			});
		}
		// 2) unisco le 2 liste eliminando i doppioni delle email
		corrispondenza.setDestinatari(VarieUtils.eliminaDuplicati(listaDestinatariDefault, notificaA));
		//
		// inserisco le informazioni per risolvere i PlaceHolders
		corrispondenza.getInfoPlaceHolders().setIdFascicolo(idFascicolo);
		corrispondenza.getInfoPlaceHolders().setUlterDocTitolo(titolo);
		corrispondenza.getInfoPlaceHolders().setUlterDocDescrizione(descrizione);
		corrispondenza.getInfoPlaceHolders().setUlterDocNomeFile(file.getOriginalFilename()!=null ? file.getOriginalFilename() : file.getName());
		//per adesso metto me stesso come risoluzione placeholder, poi dopo il primo salvataggio rimetto il valore corretto contenente l'id dell'allegato e della corrispondenza
		corrispondenza.getInfoPlaceHolders().setUlterDocLinkFile(PlaceHolder.LINK_FILE_ULTERIORE_DOC.fullName());
		
		
		// salvo la corrispondenza
		corrispondenza.setBozza(true); //blocco l'invio...
		Long idCorrispondenza = corrispondenzaService.inviaCorrispondenza(corrispondenza);
		corrispondenza.setId(idCorrispondenza);
		// recupero l'idAllegato riferito a questa documentazione
		Long idAllegato = corrispondenzaService.dettaglioCorrispondenza(idCorrispondenza, false).getAllegatiInfo().get(0).getId();
		
		//costruisco il link del file
//		String baseUrl = configuration.getString(CorrispondenzaService.KEY_URL_DOWNLOAD_PREFIX);
		String baseUrl = commonSvc.getConfigurationValue(CorrispondenzaService.KEY_URL_DOWNLOAD_PREFIX,this.props.getCodiceApplicazione());
		String url = baseUrl + "/public/"+CorrispondenzaService.PUBLIC_DOWNLOAD_ULT_DOC +
				"?idPratica="+idFascicolo+"&idCorrispondenza="+idCorrispondenza + "&idAllegato="+idAllegato;
		corrispondenza.getInfoPlaceHolders().setUlterDocLinkFile(url);//ora inserisco la risoluzione del placeholder effettiva
		String risolto = corrispondenzaService.risolviSingoloPH(PlaceHolder.LINK_FILE_ULTERIORE_DOC.fullName(), corrispondenza.getInfoPlaceHolders());
		corrispondenza.setOggetto(corrispondenza.getOggetto().replace(PlaceHolder.LINK_FILE_ULTERIORE_DOC.fullName(), risolto));
		corrispondenza.setTesto(corrispondenza.getTesto().replace(PlaceHolder.LINK_FILE_ULTERIORE_DOC.fullName(), risolto));
		corrispondenzaService.update(new CorrispondenzaDTO(corrispondenza.getId(), corrispondenza.getOggetto(), corrispondenza.getTesto(), corrispondenza.isComunicazione(), corrispondenza.isBozza()));
		corrispondenzaService.inviaMail(corrispondenza); //setta bozza a false.

		
		// popolo la tabella "allegato_ente"
		visualDa.forEach(ente->{
			try {
				allegatoEnteService.insert(new AllegatoEnteDTO(idAllegato, ente, "#UlterioreDocumentazione"));
			} catch (Exception e) {
				log.error("Errore nell'inserimento dell'ente: "+ente+" relativo all'allegato con id="+idAllegato,e);
			}
		});
		
		return this.datiDocumentazione(idFascicolo, idAllegato).get(0);  // in realtà potrei anche costruirmi la response pezzo per pezzo
	}

	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public PaginatedList<UlterioreDocumentazioneDTO> searchDocumentazione(UlterioreDocumentazioneSearch search) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		if (search.getIdFascicolo()==null) {
			log.error("L'id del fascicolo non è specificato!");
			throw new Exception("L'id del fascicolo non è specificato!");
		}
		// devo aggiungere il gruppo dell'utente loggato per filtrare i risultati di ricerca
		if (!search.getVisibileA().contains(userUtil.getGruppo().name()))
			 search.getVisibileA().add((userUtil.getGruppo().name()));
		//admin vede tutto
		if(userUtil.getGruppo().equals(Gruppi.ADMIN)){
			search.getVisibileA().clear();
		}
	//	PaginatedList<UlterioreDocumentazioneDTO> lista = allegatoService.searchUlterioreDocumentazione(search);   // in realtà mi bastano gli idAllegati
		PaginatedList<Long>						  lista = allegatoService.searchIdDocumentiUD		   (search);

		List<UlterioreDocumentazioneDTO> listaDocumentazione = new ArrayList<UlterioreDocumentazioneDTO>();
		
		getDetails(search, lista, listaDocumentazione);
		
		return new PaginatedList<UlterioreDocumentazioneDTO>(listaDocumentazione, lista.getCount());
	}


	private void getDetails(UlterioreDocumentazioneSearch search, PaginatedList<Long> lista,
			List<UlterioreDocumentazioneDTO> listaDocumentazione) {
		if (lista!=null && !lista.getList().isEmpty())
			lista.getList().forEach(documento -> {
				try {
					listaDocumentazione.add(this.datiDocumentazione(search.getIdFascicolo(), documento).get(0));
				} catch (Exception e) {
					log.error("Errore nel recupero delle informazioni per l'allegato con id="+documento,e);
				}
			});
	}


	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public List<TipologicaDestinatarioDTO> getDestinatariDefault() throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		List<TipologicaDestinatarioDTO> listaDestinatariDefault = new ArrayList<>();

		TemplateDestinatarioSearch searchTD = new TemplateDestinatarioSearch();
		searchTD.setCodiceTemplate(TipoTemplate.INVIO_ULTERIORE_DOCUMENTAZIONE);
		List<DestinatarioTemplateDTO> destinatariDefault = templateDestinatarioService.search(searchTD).getList();

		if (destinatariDefault!=null) {
			destinatariDefault.forEach(destinatario-> {
				listaDestinatariDefault.add(new DestinatarioTemplateDTO(destinatario)); 
			});
		}
		
		return listaDestinatariDefault;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public PaginatedList<UlterioreDocumentazioneDTO> searchDocumentazioneWithoutCheckVisibilita(UlterioreDocumentazioneSearch search) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		if (search.getIdFascicolo()==null) {
			log.error("L'id del fascicolo non è specificato!");
			throw new Exception("L'id del fascicolo non è specificato!");
		}
		search.getVisibileA().clear();
		PaginatedList<Long>						  lista = allegatoService.searchIdDocumentiUD		   (search);
		List<UlterioreDocumentazioneDTO> listaDocumentazione = new ArrayList<UlterioreDocumentazioneDTO>();
		getDetails(search, lista, listaDocumentazione);
		return new PaginatedList<UlterioreDocumentazioneDTO>(listaDocumentazione, lista.getCount());
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class }, timeout = 60000, readOnly = false)
	public UlterioreDocumentazioneDTO inserisciDocumentazione(long idFascicolo, List<TipologicaDestinatarioDTO> notificaA, List<String> visualizzabileDa, List<AllegatoDTO> info, MultipartFile[] file) throws Exception
	{
	    log.info("Entrato nel service: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");

	    Set<String> visualDa = new HashSet<>();

	    // ripulisco ciò che mi arriva togliendo gli id
	    for (String elem : visualizzabileDa)
	    {
		visualDa.add(userUtil.getGruppo(elem).name());
	    }
	    // in ogni caso, aggiungo alla lista di visualizzabilità il gruppo dell'utente
	    // che sta inserendo la documentazione
	    visualDa.add(userUtil.getGruppo().name());

	    NuovaComunicazioneDTO corrispondenza = new NuovaComunicazioneDTO();

	    corrispondenza.setIdFascicoli(Collections.singletonList(idFascicolo));
	    corrispondenza.setBozza(true); // mi serve l'idCorrispondenza e idAllegato
	    corrispondenza.setTipoTemplate(TipoTemplate.INVIO_ULTERIORE_DOCUMENTAZIONE);
		
	    int index = 0;
	    for(AllegatoDTO a: info)
	    {

		if (visualDa != null && visualDa.contains(Gruppi.REG_.name()))
		{
		    a.setNumeroProtocolloOut("DA PROTOCOLLARE");
		}
		// l'allegato va inviato sotto forma di url e non allegato fisico...
		corrispondenza.getAllegati().add(new AllegatoInfoDTO(a, file[index++], true));
	    }
	    
	    // 1) cerco i destinatari di default
	    List<TipologicaDestinatarioDTO> listaDestinatariDefault = new ArrayList<>();
	    TemplateDestinatarioSearch searchTD = new TemplateDestinatarioSearch();
	    searchTD.setCodiceTemplate(TipoTemplate.INVIO_ULTERIORE_DOCUMENTAZIONE);
	    List<DestinatarioTemplateDTO> destinatariDefault = templateDestinatarioService.search(searchTD).getList();

	    if (destinatariDefault != null)
	    {
		destinatariDefault.forEach(destinatario ->
			listaDestinatariDefault.add(new DestinatarioTemplateDTO(destinatario)));
	    }
	    // 2) unisco le 2 liste eliminando i doppioni delle email
	    corrispondenza.setDestinatari(VarieUtils.eliminaDuplicati(listaDestinatariDefault, notificaA));
	    corrispondenza.getInfoPlaceHolders().setIdFascicolo(idFascicolo);

	    // salvo la corrispondenza
	    corrispondenza.setBozza(true); // blocco l'invio...
	    Long idCorrispondenza = corrispondenzaService.inviaCorrispondenza(corrispondenza);
	    corrispondenza.setId(idCorrispondenza);
	    // recupero l'idAllegato riferito a questa documentazione
	    List<Long> idAllegato = corrispondenzaService.dettaglioCorrispondenza(idCorrispondenza, false).getAllegatiInfo().stream().map(AllegatoCustomDTO::getId).collect(Collectors.toList());

	    // costruisco il link del file
	    String baseUrl = commonSvc.getConfigurationValue(CorrispondenzaService.KEY_URL_DOWNLOAD_PREFIX, this.props.getCodiceApplicazione());
	    String sep = "";
	    String html = "";
	    for(AllegatoInfoDTO a: corrispondenza.getAllegati())
	    {
		String url = baseUrl + "/public/" + CorrispondenzaService.PUBLIC_DOWNLOAD_ULT_DOC + "?idPratica=" + idFascicolo
				     + "&idCorrispondenza=" + idCorrispondenza + "&idAllegato=" + a.getId();
		html = html + sep +"<ul><li>Titolo: "+a.getTitolo()+"</li><li>Descrizione: "+a.getDescrizione()
		     +"</li><li>Nome file: "+ a.getNome() +" <a href=\""+ url + "\">Clicca qui</a></li></ul>";
		corrispondenza.getInfoPlaceHolders().setUlterioreDocumentazione(html);
		sep = "<p></p>";
	    }
			
	    String risolto = corrispondenzaService.risolviSingoloPH(PlaceHolder.ALLEGATI_ULTERIORE_DOCUMENTAZIONE.fullName(), corrispondenza.getInfoPlaceHolders());
	    corrispondenza.setTesto(corrispondenza.getTesto().replace(PlaceHolder.ALLEGATI_ULTERIORE_DOCUMENTAZIONE.fullName(), risolto));
	    corrispondenzaService.update(new CorrispondenzaDTO(corrispondenza.getId(), corrispondenza.getOggetto(),
		    			 corrispondenza.getTesto(), corrispondenza.isComunicazione(), corrispondenza.isBozza()));
	    corrispondenzaService.inviaMail(corrispondenza); // setta bozza a false.

	
	    // popolo la tabella "allegato_ente"
	    if(idAllegato != null)
	    {
		visualDa.forEach(ente ->
		{
		    try
		    {
			allegatoEnteService.insert(idAllegato.stream().map(id -> new AllegatoEnteDTO(id, ente, "#UlterioreDocumentazione")).collect(Collectors.toList()));
		    } catch (Exception e)
		    {
			log.error("Errore nell'inserimento dell'ente: " + ente + " relativo all'allegato con id=" + idAllegato, e);
		    }
		});
	    }
		

	    return this.datiDocumentazione(idFascicolo, idAllegato).get(0);
	}
		
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class, timeout = 60000, readOnly = true)
	private ArrayList<UlterioreDocumentazioneDTO> datiDocumentazione(Long idFascicolo, List<Long> idAllegato) throws Exception
	{
	    ArrayList<UlterioreDocumentazioneDTO> listaDocumentazione = new ArrayList<UlterioreDocumentazioneDTO>();
	    List<AllegatoDTO> listaAllegati = allegatoService.datiAllegato(idFascicolo,
			Collections.singletonList(TipoAllegato.ULTERIORE_DOCUMENTAZIONE));
	    if (!listaAllegati.isEmpty())
	    {
		listaAllegati.forEach(elem ->
		{
		    if (idAllegato != null)
		    { // se ho in input uno specifico idAllegato, recupero le informazioni relative
			// solo a quell'allegato
			if (idAllegato.contains(elem.getId()))
			    listaDocumentazione.add(new UlterioreDocumentazioneDTO(elem));
		    } else // se ho in input idAllegato=null, recupero le informazioni relative a tutti gli
			// allegati
			listaDocumentazione.add(new UlterioreDocumentazioneDTO(elem));
		});
	    }
	    if (!listaDocumentazione.isEmpty())
	    {
		listaDocumentazione.forEach(allegato ->
		{
		    AllegatoCorrispondenzaSearch search1 = new AllegatoCorrispondenzaSearch();
		    search1.setIdAllegato(allegato.getId());
		    List<AllegatoCorrispondenzaDTO> listaAC = null;
		    try
		    {
			listaAC = allegatoCorrispondenzaService.search(search1).getList();
		    } catch (Exception e)
		    {
			log.error("Errore nella ricerca dei record di AllegatoCorrispondenza", e);
		    }
		    if (listaAC != null)
		    {
			listaAC.forEach(elem ->
			{
			    DestinatarioSearch search2 = new DestinatarioSearch();
			    search2.setIdCorrispondenza(elem.getIdCorrispondenza());
			    List<DestinatarioDTO> listaDestinatari = null;
			    try
			    {
				listaDestinatari = destinatarioService.search(search2).getList();
			    } catch (Exception e)
			    {
				log.error("Errore nella ricerca della lista Destinatari", e);
			    }
			    if (listaDestinatari != null)
				listaDestinatari.forEach(destinatario -> allegato.getNotifica()
					.add(new TipologicaDestinatarioDTO(destinatario)));
			});
		    }
		    AllegatoEnteSearch search3 = new AllegatoEnteSearch();
		    search3.setIdAllegato(allegato.getId());
		    List<AllegatoEnteDTO> listaEnti = null;
		    try
		    {
			listaEnti = allegatoEnteService.search(search3).getList();
		    } catch (Exception e)
		    {
			log.error("Errore nella ricerca della lista Enti", e);
		    }
		    if (listaEnti != null)
			listaEnti.forEach(ente -> allegato.getEnti().add(ente.getCodice()));
		});
	    }
	    return listaDocumentazione;
	}
}