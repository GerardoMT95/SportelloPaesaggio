/**
 * 
 */
package it.eng.tz.puglia.autpae.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dto.AllegatoCustomDTO;
import it.eng.tz.puglia.autpae.dto.AllegatoInfoDTO;
import it.eng.tz.puglia.autpae.dto.DettaglioCorrispondenzaDTO;
import it.eng.tz.puglia.autpae.dto.NuovaComunicazioneDTO;
import it.eng.tz.puglia.autpae.dto.RichiestePostTrasmissioneDetailDto;
import it.eng.tz.puglia.autpae.dto.TipologicaDestinatarioDTO;
import it.eng.tz.puglia.autpae.entity.AllegatoDTO;
import it.eng.tz.puglia.autpae.entity.CorrispondenzaDTO;
import it.eng.tz.puglia.autpae.entity.DestinatarioDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.enumeratori.StatoRichiestaPostTrasmissione;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.enumeratori.TipoDestinatario;
import it.eng.tz.puglia.autpae.enumeratori.TipoRichiestaPostTrasmissione;
import it.eng.tz.puglia.autpae.enumeratori.TipoTemplate;
import it.eng.tz.puglia.autpae.generated.orm.dto.RichiestePostTrasmissioneDTO;
import it.eng.tz.puglia.autpae.generated.orm.repository.RichiestePostTrasmissioneRepository;
import it.eng.tz.puglia.autpae.generated.orm.search.RichiestePostTrasmissioneSearch;
import it.eng.tz.puglia.autpae.search.DestinatarioSearch;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoCorrispondenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoService;
import it.eng.tz.puglia.autpae.service.interfacce.AmministratoreService;
import it.eng.tz.puglia.autpae.service.interfacce.AzioniService;
import it.eng.tz.puglia.autpae.service.interfacce.CorrispondenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.DestinatarioService;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloService;
import it.eng.tz.puglia.autpae.service.interfacce.GruppiRuoliService;
import it.eng.tz.puglia.autpae.service.interfacce.RichiestePostTrasmissioneService;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.AssUtenteGruppo;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * @author Adriano Colaianni
 * @date 30 ago 2021
 */
@Service
public class RichiestePostTrasmissioneServiceImpl implements RichiestePostTrasmissioneService {

	private static final Logger log = LoggerFactory.getLogger(RichiestePostTrasmissioneServiceImpl.class);

	@Autowired
	private RichiestePostTrasmissioneRepository richiestePostDao;

	@Autowired
	private AllegatoService allegatoService;
	
	@Autowired
	private FascicoloService fascicoloService;
	
	@Autowired
	private GruppiRuoliService gruppiRuoliService;
	
	@Autowired
	private AmministratoreService amministratoreService;
	
	@Autowired
	private AzioniService azioniService;
	
	@Autowired
	private DestinatarioService destinatarioService;
	
	@Autowired
	private AllegatoCorrispondenzaService allegatoCorrispondenzaService;
	
	@Autowired
	private CorrispondenzaService corrispondenzaService;
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, timeout = 60000)
	public RichiestePostTrasmissioneDTO create(Long idFascicolo, TipoRichiestaPostTrasmissione tipo) throws Exception {
		/**
		 * effettuare tutti i controlli sui permessi sia per l'utenza sia per lo stato
		 * del fascicolo
		 */
		this.userIsOwner(idFascicolo);
		if(tipo==null) {
			throw new CustomOperationToAdviceException("Tipo richiesta non valido");
		}
		// controllo che non ci siano ulteriori bozze per il fascicolo corrente
		if (richiestePostDao.countDrafts(idFascicolo) > 0) {
			throw new CustomOperationToAdviceException("Esiste già una bozza di richiesta modifica/cancellazione per questo fascicolo.");
		}
		praticaAcceptRequestPostTrasmissione(tipo,idFascicolo);
		RichiestePostTrasmissioneDTO entity = new RichiestePostTrasmissioneDTO();
		entity.setTipo(tipo.name());
		entity.setStato(StatoRichiestaPostTrasmissione.Bozza.name());
		entity.setMotivazione("");
		entity.setIdFascicolo(idFascicolo);
		entity.setDateCreated(new Timestamp(new Date().getTime()));
		entity.setUserCreated(SecurityUtil.getUsername());
		Long pk = richiestePostDao.insert(entity);
		entity.setId(pk);
		return entity;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, timeout = 60000)
	public boolean delete(Long idFascicolo, Long idRichiestaPostTrasmissione) throws Exception {
		// controllo su accesso all'item e cancellabile se stato=bozza
		// vanno cancellati anche gli allegati..
		this.userIsOwner(idFascicolo);
		RichiestePostTrasmissioneDetailDto richiestaDetail = this.getDetail(idFascicolo, idRichiestaPostTrasmissione);
		if(richiestaDetail.getRichiestaBase().getStato()==null || 
			!richiestaDetail.getRichiestaBase().getStato().equals(StatoRichiestaPostTrasmissione.Bozza.name())) {
			throw new CustomOperationToAdviceException("Stato richiesta post trasmissione invalido, impossibile eliminare !!!");
		}
		//cancello tutti gli allegati:
		if(ListUtil.isNotEmpty(richiestaDetail.getAllegati()))
		{
			List<Long> listaAllegati = richiestaDetail.getAllegati().stream().map(AllegatoCustomDTO::getId).collect(Collectors.toList());
			allegatoService.eliminaAllegati(listaAllegati);
		}
		int deleted = this.richiestePostDao.delete(richiestaDetail.getRichiestaBase());
		return deleted>0;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, timeout = 60000)
	public RichiestePostTrasmissioneDTO update(RichiestePostTrasmissioneDTO dto) throws Exception {
		// controllo su accesso all'item e aggiornabile se stato=bozza
		// da aggiornare solo il campo motivazioni
		if(dto==null || dto.getIdFascicolo()==0) {
			throw new CustomOperationToAdviceException("Fascicolo non trovato in update richiesta post trasmissione!!!");
		}
		RichiestePostTrasmissioneDetailDto richiestaDetail = this.getDetail(dto.getIdFascicolo(), dto.getId());
		if(richiestaDetail.getRichiestaBase().getStato()==null || 
			!richiestaDetail.getRichiestaBase().getStato().equals(StatoRichiestaPostTrasmissione.Bozza.name())) {
			throw new CustomOperationToAdviceException("Stato richiesta post trasmissione invalido, impossibile aggiornare !!!");
		}
		richiestaDetail.getRichiestaBase().setMotivazione(dto.getMotivazione());
		richiestaDetail.getRichiestaBase().setDateUpdated(new Timestamp(new Date().getTime()));
		richiestaDetail.getRichiestaBase().setUserUpdated(SecurityUtil.getUsername());
		int updatedRow = this.richiestePostDao.update(richiestaDetail.getRichiestaBase());
		return richiestaDetail.getRichiestaBase();
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, timeout = 60000)
	public PaginatedList<RichiestePostTrasmissioneDTO> search(RichiestePostTrasmissioneSearch dtoSearch) {
		return richiestePostDao.search(dtoSearch);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, timeout = 60000)
	public RichiestePostTrasmissioneDTO trasmettiRichiesta(Long idFascicolo, Long idRichiestaPostTrasmissione) throws Exception {
		this.userIsOwner(idFascicolo);
		RichiestePostTrasmissioneDetailDto richiestaDetail = this.getDetail(idFascicolo, idRichiestaPostTrasmissione);
		if(richiestaDetail.getRichiestaBase().getStato()==null || 
			!richiestaDetail.getRichiestaBase().getStato().equals(StatoRichiestaPostTrasmissione.Bozza.name())) {
			throw new CustomOperationToAdviceException("Stato richiesta post trasmissione invalido, impossibile trasmettere !!!");
		}
		this.praticaAcceptRequestPostTrasmissione(TipoRichiestaPostTrasmissione.valueOf(richiestaDetail.getRichiestaBase().getTipo()),idFascicolo);
		// set stato a Trasmessa e set idcorrispondenza
		Long idCorrispondenza = this.generaEinviaCorrispondenza(richiestaDetail);
		richiestaDetail.getRichiestaBase().setIdCorrispondenza(idCorrispondenza);
		richiestaDetail.getRichiestaBase().setStato(StatoRichiestaPostTrasmissione.Trasmessa.name());
		this.richiestePostDao.update(richiestaDetail.getRichiestaBase());
		return richiestaDetail.getRichiestaBase();
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, timeout = 60000)
	public RichiestePostTrasmissioneDetailDto getDetail(Long idFascicolo, Long idRichiestaPostTrasmissione)
			throws Exception {
		this.userIsOwner(idFascicolo);
		RichiestePostTrasmissioneDetailDto ret=new RichiestePostTrasmissioneDetailDto();
		RichiestePostTrasmissioneDTO pk=new RichiestePostTrasmissioneDTO();
		pk.setId(idRichiestaPostTrasmissione);
		try{
			pk=this.richiestePostDao.find(pk);
		}catch(DataAccessException e) {
			throw new CustomOperationToAdviceException("Richiesta non trovata !");
		}
		ret.setRichiestaBase(pk);
		List<AllegatoDTO> allegati = allegatoService.infoAllegatiRichiestaPostTrasmissione(idFascicolo, idRichiestaPostTrasmissione);
		if(ListUtil.isNotEmpty(allegati)) {
			List<AllegatoCustomDTO> allCustom = allegati.stream().map(all->new AllegatoCustomDTO(all)).collect(Collectors.toList());
			ret.setAllegati(allCustom);
		}
		return ret;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, timeout = 60000)
	public AllegatoCustomDTO caricaAllegato(List<Long> fascicoli, long idRichiesta, MultipartFile file) {
		AllegatoCustomDTO allegato = null;
		try {
			allegato = allegatoService.inserisciAllegato(fascicoli, TipoAllegato.COMUNICAZIONE,
					file, null);
			if (allegato == null)
				throw new Exception("Errore. Allegato nullo!");
			AllegatoDTO allegatoEntity = allegatoService.find(allegato.getId());
			allegatoEntity.setIdRichiestaPostTrasmissione(idRichiesta);
			allegatoService.update(allegatoEntity);
		} catch (Exception e) {
			log.error("Errore nell'inserimento dell'allegato relativo alla richiesta post trasmissione con id="
					+ idRichiesta, e);
		}
		return allegato;
	}

	/**
	 * chec se l'utente fa parte del gruppo che ha creato e trasmesso il fascicolo
	 * @autor Adriano Colaianni
	 * @date 31 ago 2021
	 * @param idFascicolo
	 * @throws Exception
	 */
	private void userIsOwner(Long idFascicolo) throws Exception {
		this.gruppiRuoliService.checkAbilitazioneForTrasmissione();
		this.fascicoloService.checkPermission(idFascicolo);
	}
	
	private void praticaAcceptRequestPostTrasmissione(TipoRichiestaPostTrasmissione tipo,Long idFascicolo) throws Exception {
		switch (tipo) {
		case Modifica:
			this.praticaIsModificabile(idFascicolo);
			break;
		case Cancellazione:
			this.praticaIsCancellabile(idFascicolo);
			break;
		default:
			break;
		}
	}
	
	private void praticaIsModificabile(Long idFascicolo) throws Exception {
		FascicoloDTO fascicolo = this.fascicoloService.find(idFascicolo);
		this.amministratoreService.isModificabile(fascicolo.getStato(), fascicolo.getEsitoVerifica());
	}
	
	private void praticaIsCancellabile(Long idFascicolo) throws Exception {
		FascicoloDTO fascicolo = this.fascicoloService.find(idFascicolo);
		this.amministratoreService.isAnnullabile(fascicolo.getStato(), fascicolo.getEsitoVerifica());
		
	}
	
	/**
	 * viene generata la mail da inviare a partire dal modello determinato dal tipo della richiesta @see {@link TipoRichiestaPostTrasmissione}
	 * vengono inseriti in CC tutti i destinatari della trasmissione più recente e vengono aggiunti in to gli amministratori dell'applicazione
	 * prelevati dal profile manager 
	 * eventuali allegati vengono aggiunti come url al corpo della mail
	 * @autor Adriano Colaianni
	 * @date 31 ago 2021
	 * @param richiesta
	 * @return id del record corrispondenza generato
	 * @throws Exception
	 */
	private Long generaEinviaCorrispondenza(RichiestePostTrasmissioneDetailDto richiesta) throws Exception {
		//cerco la corrispondenza dell'ultima trasmissione per prelevare i destinatari
		long idCorrispondenzaLastTrasmissione = azioniService.getIdCorrispondenzaTrasmissione(richiesta.getRichiestaBase().getIdFascicolo());
		DestinatarioSearch searchD = new DestinatarioSearch();
		searchD.setIdCorrispondenza(idCorrispondenzaLastTrasmissione);
		List<DestinatarioDTO> destinatari = destinatarioService.search(searchD).getList();
		List<TipologicaDestinatarioDTO> listaDestinatariTrasmissione = new ArrayList<TipologicaDestinatarioDTO>();
		destinatari.forEach(destinatario-> {
			TipologicaDestinatarioDTO dest = new TipologicaDestinatarioDTO(destinatario);
			dest.setTipo(TipoDestinatario.CC);
			listaDestinatariTrasmissione.add(dest); 
		});
		//aggiungo in TO gli amministratori dell'applicazione
		boolean almenoUnAdmin=false;
		AssUtenteGruppo[] profiliAdmin = gruppiRuoliService.getAdministratorsProfilePM();
		if(profiliAdmin==null || profiliAdmin.length==0) {
			throw new CustomOperationToAdviceException("Nessun profilo di amministratore applicazione presente, impossibile procedere!!!");
		}
		for(AssUtenteGruppo profiloAdmin:profiliAdmin){
			TipologicaDestinatarioDTO destAdmin=new TipologicaDestinatarioDTO(profiloAdmin.getMail(),profiloAdmin.getNome()+profiloAdmin.getCognome(),TipoDestinatario.TO);
			if(StringUtil.isEmail(destAdmin.getEmail())){
				listaDestinatariTrasmissione.add(destAdmin);
				almenoUnAdmin=true;
			}
		}
		if(!almenoUnAdmin) {
			throw new CustomOperationToAdviceException("Nessun profilo di amministratore applicazione con email valida, impossibile procedere!!!");
		}
		// creo la corrispondenza
		NuovaComunicazioneDTO corrispondenza = new NuovaComunicazioneDTO();
		corrispondenza.setIdFascicoli(Collections.singletonList(richiesta.getRichiestaBase().getIdFascicolo()));
		corrispondenza.setBozza(true);
		if(richiesta.getRichiestaBase().getTipo().equals(TipoRichiestaPostTrasmissione.Modifica.name())){
			corrispondenza.setTipoTemplate(TipoTemplate.RICHIESTA_MODIFICA_POST_TRASMISSIONE);	
		}else if(richiesta.getRichiestaBase().getTipo().equals(TipoRichiestaPostTrasmissione.Cancellazione.name())) {
			corrispondenza.setTipoTemplate(TipoTemplate.RICHIESTA_ELIMINAZIONE_POST_TRASMISSIONE);
		}else {
			throw new CustomOperationToAdviceException("Tipo richiesta non valido "+richiesta.getRichiestaBase().getTipo()+" !!!");
		}
		corrispondenza.getInfoPlaceHolders().setIdFascicolo(richiesta.getRichiestaBase().getIdFascicolo());
		corrispondenza.getInfoPlaceHolders().setRichiestaPostTrasmissione(richiesta.getRichiestaBase());
		//compongo la comunicazione a partire dal template
		if(ListUtil.isNotEmpty(richiesta.getAllegati())) {
			corrispondenza.setAllegati(
					richiesta.getAllegati().stream().map(
							allegatoDTO->
							new AllegatoInfoDTO(new AllegatoDTO(allegatoDTO), null,true)).collect(Collectors.toList()));
		}
		corrispondenza.setDestinatari(listaDestinatariTrasmissione);
		//salvo la comunicazione in bozza
		//bozza=true verrà solo salvata
		Long idCorrispondenza = corrispondenzaService.inviaCorrispondenza(corrispondenza, true, true);
		corrispondenza.setId(idCorrispondenza);
		//risolvo gli allegati logici (isUrl)
		DettaglioCorrispondenzaDTO dettaglioCorrispondenza = corrispondenzaService.dettaglioCorrispondenza(idCorrispondenza,true);
		corrispondenzaService.aggiungiUrlAllegatiInCorpo(corrispondenza, dettaglioCorrispondenza);
		//salvo nuovamente il corpo
		corrispondenzaService.update(new CorrispondenzaDTO(idCorrispondenza, corrispondenza.getOggetto(), corrispondenza.getTesto(), corrispondenza.isComunicazione(), corrispondenza.isBozza()));
		corrispondenzaService.inviaMail(corrispondenza);
		return idCorrispondenza;
	}
}
