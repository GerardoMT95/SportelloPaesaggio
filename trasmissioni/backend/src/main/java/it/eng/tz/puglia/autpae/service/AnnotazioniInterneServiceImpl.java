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
import it.eng.tz.puglia.autpae.dto.AnnotazioniInterneDetailDto;
import it.eng.tz.puglia.autpae.dto.DettaglioCorrispondenzaDTO;
import it.eng.tz.puglia.autpae.dto.NuovaComunicazioneDTO;
import it.eng.tz.puglia.autpae.dto.RichiestePostTrasmissioneDetailDto;
import it.eng.tz.puglia.autpae.dto.TipologicaDestinatarioDTO;
import it.eng.tz.puglia.autpae.entity.AllegatoDTO;
import it.eng.tz.puglia.autpae.entity.CorrispondenzaDTO;
import it.eng.tz.puglia.autpae.entity.DestinatarioDTO;
import it.eng.tz.puglia.autpae.enumeratori.EsitoControllo;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.enumeratori.TipoDestinatario;
import it.eng.tz.puglia.autpae.enumeratori.TipoRichiestaPostTrasmissione;
import it.eng.tz.puglia.autpae.enumeratori.TipoTemplate;
import it.eng.tz.puglia.autpae.generated.orm.dto.AnnotazioniInterneDTO;
import it.eng.tz.puglia.autpae.generated.orm.repository.AnnotazioniInterneRepository;
import it.eng.tz.puglia.autpae.generated.orm.search.AnnotazioniInterneSearch;
import it.eng.tz.puglia.autpae.search.DestinatarioSearch;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoService;
import it.eng.tz.puglia.autpae.service.interfacce.AnnotazioniInterneService;
import it.eng.tz.puglia.autpae.service.interfacce.AzioniService;
import it.eng.tz.puglia.autpae.service.interfacce.CorrispondenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.DestinatarioService;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloService;
import it.eng.tz.puglia.autpae.service.interfacce.GruppiRuoliService;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.AssUtenteGruppo;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.UserUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * @author Raffaele Del Basso, Marta Zecchillo,
 * @date 07 set 2021
 */
@Service
public class AnnotazioniInterneServiceImpl implements AnnotazioniInterneService {

	private static final Logger log = LoggerFactory.getLogger(AnnotazioniInterneServiceImpl.class);

	@Autowired
	private AnnotazioniInterneRepository annotazioniInterneDao;

	@Autowired
	private AllegatoService allegatoService;
	
	@Autowired
	private FascicoloService fascicoloService;
	
	@Autowired
	private UserUtil userUtil;
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, timeout = 60000)
	public AnnotazioniInterneDTO create(Long idFascicolo) throws Exception {
		fascicoloService.checkPermission(idFascicolo);
		AnnotazioniInterneDTO entity = new AnnotazioniInterneDTO();
		entity.setIdFascicolo(idFascicolo);
		entity.setIdOrganizzazione(userUtil.getIntegerId());
		entity.setEsitoControllo("");
		entity.setNote("");
		entity.setDateCreated(new Timestamp(new Date().getTime()));
		entity.setUserCreated(SecurityUtil.getUsername());
		Long pk = annotazioniInterneDao.insert(entity);
		entity.setId(pk);
		return entity;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, timeout = 60000)
	public boolean delete(Long idFascicolo, Long idAnnotazioneInterna) throws Exception {

		fascicoloService.checkPermission(idFascicolo);
		AnnotazioniInterneDetailDto richiestaDetail = this.getDetail(idFascicolo, idAnnotazioneInterna);
		//cancello tutti gli allegati:
		if(ListUtil.isNotEmpty(richiestaDetail.getAllegati()))
		{
			List<Long> listaAllegati = richiestaDetail.getAllegati().stream().map(AllegatoCustomDTO::getId).collect(Collectors.toList());
			allegatoService.eliminaAllegati(listaAllegati);
		}
		int deleted = this.annotazioniInterneDao.delete(richiestaDetail.getAnnotazioneBase());
		return deleted>0;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, timeout = 60000)
	public AnnotazioniInterneDTO update(AnnotazioniInterneDTO dto) throws Exception {
		// controllo su accesso all'item e aggiornabile se stato=bozza
		// da aggiornare solo il campo motivazioni
		if(dto==null || dto.getIdFascicolo()==0) {
			throw new CustomOperationToAdviceException("Fascicolo non trovato in update annotazione interna !!!");
		}
		AnnotazioniInterneDetailDto richiestaDetail = this.getDetail(dto.getIdFascicolo(), dto.getId());
		richiestaDetail.getAnnotazioneBase().setNote(dto.getNote());
		richiestaDetail.getAnnotazioneBase().setEsitoControllo(EsitoControllo.valueOf(dto.getEsitoControllo()).getTextEsito());
		richiestaDetail.getAnnotazioneBase().setDateUpdated(new Timestamp(new Date().getTime()));
		richiestaDetail.getAnnotazioneBase().setUserUpdated(SecurityUtil.getUsername());
		this.annotazioniInterneDao.update(richiestaDetail.getAnnotazioneBase());
		return richiestaDetail.getAnnotazioneBase();
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, timeout = 60000)
	public PaginatedList<AnnotazioniInterneDTO> search(AnnotazioniInterneSearch dtoSearch) {
		return annotazioniInterneDao.search(dtoSearch);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, timeout = 60000)
	public AnnotazioniInterneDetailDto getDetail(Long idFascicolo, Long idAnnotazioneInterna) throws Exception {
		fascicoloService.checkPermission(idFascicolo);
		
		AnnotazioniInterneDetailDto ret=new AnnotazioniInterneDetailDto();
		AnnotazioniInterneDTO pk=new AnnotazioniInterneDTO();
		pk.setId(idAnnotazioneInterna);
		try{
			pk=this.annotazioniInterneDao.find(pk);
		}catch(DataAccessException e) {
			throw new CustomOperationToAdviceException("Annotazione non trovata !");
		}
		ret.setAnnotazioneBase(pk);
		List<AllegatoDTO> allegati = allegatoService.infoAllegatiAnnotazioniInterne(idFascicolo, idAnnotazioneInterna);
		if(ListUtil.isNotEmpty(allegati)) {
			List<AllegatoCustomDTO> allCustom = allegati.stream().map(all->new AllegatoCustomDTO(all)).collect(Collectors.toList());
			ret.setAllegati(allCustom);
		}
		return ret;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, timeout = 60000)
	public AllegatoCustomDTO caricaAllegato(List<Long> fascicoli, long idAnnotazioneInterna, MultipartFile file) {
		AllegatoCustomDTO allegato = null;
		try {
			allegato = allegatoService.inserisciAllegato(fascicoli, TipoAllegato.COMUNICAZIONE,
					file, null);
			if (allegato == null)
				throw new Exception("Errore. Allegato nullo!");
			AllegatoDTO allegatoEntity = allegatoService.find(allegato.getId());
			allegatoEntity.setIdAnnotazioneInterna(idAnnotazioneInterna);
			allegatoService.update(allegatoEntity);
		} catch (Exception e) {
			log.error("Errore nell'inserimento dell'allegato relativo all'annotazione con id="
					+ idAnnotazioneInterna, e);
		}
		return allegato;
	}
	
	@Override
	public List<AllegatoCustomDTO> getDetailSoloAllegati(Long idFascicolo, long id) throws Exception {
		List<AllegatoDTO> allegati = allegatoService.infoAllegatiAnnotazioniInterne(idFascicolo, id);
		List<AllegatoCustomDTO> allCustom = new ArrayList<AllegatoCustomDTO>();
		if(ListUtil.isNotEmpty(allegati)) {
			allCustom = allegati.stream().map(all->new AllegatoCustomDTO(all)).collect(Collectors.toList());
		}
		return allCustom;
	}
}
