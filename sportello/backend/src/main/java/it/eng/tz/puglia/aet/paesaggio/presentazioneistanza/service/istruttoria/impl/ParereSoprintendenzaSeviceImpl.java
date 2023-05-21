package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.cds.service.IConferenzaDeiServiziService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.DettaglioCorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.ParereSoprintendenzaDetailsDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.SezioneAllegati;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoParere;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.CorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinatarioDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ParereSoprintendenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TemplateEmailDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.AllegatiRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.CorrispondenzaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ParereSoprintendenzaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TipoContenutoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PraticaSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.SendPlanToDiogeneScheduler;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.bean.FascicoloStatoBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.AllegatoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.ComunicazioniService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.IstrPraticaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.ParereSoprintendenzaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.dto.TemplateEmailDestinatariDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.service.ITemplateMailService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.service.impl.ResolveTemplateService;
import it.eng.tz.puglia.batch.queue.IQueueService;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.log.LogUtil;

@Service
public class ParereSoprintendenzaSeviceImpl implements ParereSoprintendenzaService
{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired private ParereSoprintendenzaRepository dao;
	@Autowired private PraticaRepository praticaRepository;
	@Autowired private AllegatiRepository allegatiRepository;
	@Autowired private TipoContenutoRepository tcRepository;
	@Autowired private CorrispondenzaRepository corrispondenzaRepository;
	@Autowired private ResolveTemplateService templateResolver;
	@Autowired private ComunicazioniService comunicazioniService;
	@Autowired private UserUtil userUtil;
	@Autowired private IstrPraticaService istrPraticaService;
	@Autowired private ITemplateMailService templateService;
	@Autowired private IQueueService queueService;
	@Autowired private IConferenzaDeiServiziService conferenzaSvc;
	
	@Autowired 
	@Qualifier("allegatiIstruttoria") 
	private AllegatoService allegatoService;
	
	@Autowired Validator validator;
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, readOnly=true, timeout=60000, rollbackFor=Exception.class, propagation=Propagation.REQUIRED)
	public ParereSoprintendenzaDetailsDTO find(UUID idPratica) throws Exception
	{
		ParereSoprintendenzaDetailsDTO entity;
		List<AllegatiDTO> allegati;
		entity = new ParereSoprintendenzaDetailsDTO(dao.find(idPratica));
		allegati = allegatiRepository.findAllegatiParere(entity.getId());
		if(allegati != null && !allegati.isEmpty())
		{
			logger.info("Cerco, per ogni allegato, la tipologia corrispondente");
			for(AllegatiDTO a: allegati)
			{
				List<Integer> temp = tcRepository.findTipiAllegato(a.getId());
				if(temp != null)
				{
					a.setTipiContenuto(temp.stream().map(m -> m.toString()).collect(Collectors.toList()));
					a.setType(a.getTipiContenuto().get(0));
				}
			}
			entity.setAllegati(allegati);
		}
		if(entity.getIdCorrispondenza() != null && entity.getIdCorrispondenza() > 0)
		{
			Long idCorrispondenza = entity.getIdCorrispondenza();
			DettaglioCorrispondenzaDTO dettaglio = corrispondenzaRepository.getDettaglioCorrispondenza(idCorrispondenza);
			entity.setComunicazione(dettaglio);
		}

		return entity;
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, readOnly=false, timeout=60000, rollbackFor=Exception.class, propagation=Propagation.REQUIRED)
	public ParereSoprintendenzaDTO insert(ParereSoprintendenzaDTO entity) throws Exception
	{
		checkPermission(entity.getIdPratica());
		checkParereDaInserire(entity.getIdPratica());
		PraticaSearch search = new PraticaSearch();
		search.setId(entity.getIdPratica());
		this.istrPraticaService.prepareForSearch(search);
		if(praticaRepository.count(search) > 0)
		entity.setUsernameUtenteCreazione(LogUtil.getUser());
		entity.setOrganizzazioneCreazione(userUtil.getLongId());
		entity.setId(dao.insert(entity));
		StatoParere stato = StatoParere.PARERE_IN_BOZZA_ENTE;
		if(userUtil.getGruppo().equals(Gruppi.SBAP_))
			stato = StatoParere.PARERE_IN_BOZZA_SOPRINTENDENZA;
		praticaRepository.updateStatoParere(entity.getIdPratica(), stato);
		return entity;
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, readOnly=false, timeout=60000, rollbackFor=Exception.class, propagation=Propagation.REQUIRED)
	public ParereSoprintendenzaDTO update(ParereSoprintendenzaDTO entity) throws Exception
	{
		checkPermission(entity.getIdPratica());
		checkOrganizzazioneCreazione(entity.getIdPratica());
		checkParereDaAllegare(entity.getIdPratica());
		Integer n = dao.update(entity);
		if(n != 1)
		{
			logger.error("Mi aspettavo di aggiornare un solo parere e ne ho aggiornati {}", n);
			throw new Exception("Mi aspettavo di aggiornare un solo parere e ne ho aggiornati " + n);
		}
		return entity;
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, readOnly=false, timeout=60000, rollbackFor=Exception.class, propagation=Propagation.REQUIRED)
	public Integer delete(Long idParere) throws Exception
	{
		ParereSoprintendenzaDTO parere = dao.find(idParere);
		checkPermission(parere.getIdPratica());
		checkOrganizzazioneCreazione(parere.getIdPratica());
		checkParereDaAllegare(parere.getIdPratica());
		
		List<UUID> allegatiDaCancellare = dao.getAllegatiID(idParere);
		for(UUID id: allegatiDaCancellare)
			deleteAllegato(id, idParere);
		
		Integer n = dao.delete(idParere);
		if(n != 1)
		{
			logger.error("Errore: mi aspettavo di eliminare 1 elemento e ne sono stati eliminati {}", n);
			throw new Exception("Errore: mi aspettavo di eliminare 1 elemento e ne sono stati eliminati " + n);
		}
		praticaRepository.updateStatoParere(parere.getIdPratica(), StatoParere.PARERE_NON_ALLEGATO);
		return n;
	}
	
//	@Override
//	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, readOnly=false, timeout=60000, rollbackFor=Exception.class, propagation=Propagation.REQUIRED)
//	public ParereSoprintendenzaDetailsDTO insertAll(MultipartFile file, ParereSoprintendenzaDTO entity) throws Exception
//	{
//		checkPermission(entity.getIdPratica());
//		checkOrganizzazioneCreazione(entity.getIdPratica());
//		checkParereDaAllegare(entity.getIdPratica());
//		StatoParere stato;	
//		ParereSoprintendenzaDetailsDTO dto;
//		List<AllegatiDTO> allegati = new ArrayList<AllegatiDTO>();
//		dto = new ParereSoprintendenzaDetailsDTO(insert(entity));
//		allegati.add(uploadAllegato(file, dto.getId()));
//		dto.setAllegati(allegati);
//		if(userUtil.getGruppo().equals(Gruppi.SBAP_))
//			stato = StatoParere.PARERE_INSERITO_SOPRINTENDENZA;
//		else 
//			stato = StatoParere.PARERE_INSERITO_ENTE;
//		praticaRepository.updateStatoParere(dto.getIdPratica(), stato);
//		//invio in coda la sincronizzazione per diogene allineaDiogene(bean)
//		FascicoloStatoBean fsb=new FascicoloStatoBean();
//		PraticaDTO pratica=this.praticaRepository.find(entity.getIdPratica());
//		fsb.setPratica(pratica);
//		fsb.setSezioniAllegati(List.of(SezioneAllegati.PARERE_SOPR));
//		this.queueService.insertNewQueue(SendPlanToDiogeneScheduler.ID, fsb);
//		return dto;
//	}
	
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, readOnly=false, timeout=60000, rollbackFor=Exception.class, propagation=Propagation.REQUIRED)
	public AllegatiDTO uploadAllegato(MultipartFile file, List<Integer> tipiContenuto, Long idParere) throws Exception
	{
		ParereSoprintendenzaDTO parere = dao.find(idParere);
		checkPermission(parere.getIdPratica());
		checkOrganizzazioneCreazione(parere.getIdPratica());
		checkParereDaAllegare(parere.getIdPratica());
		ParereSoprintendenzaDTO entity = dao.find(idParere);
		String idPratica = entity.getIdPratica().toString();
		String filename = file.getOriginalFilename();
		AllegatiDTO allegato = allegatoService.uploadAllegato(idPratica, tipiContenuto, filename, file);
		allegato.setTipiContenuto(tipiContenuto.stream().map(m -> m.toString()).collect(Collectors.toList()));
		allegato.setType(allegato.getTipiContenuto().get(0));
		Integer n = dao.insertParereAllegato(idParere, allegato.getId());
		if(n != 1)
		{
			logger.error("Mi aspettavo di inserire un solo record e ne ho inseriti {}", n);
			throw new Exception("Mi aspettavo di inserire un solo record e ne ho inseriti " + n);
		}
		return allegato;
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, readOnly=false, timeout=60000, rollbackFor=Exception.class, propagation=Propagation.REQUIRED)
	public AllegatiDTO uploadAllegato(MultipartFile file, Long idParere) throws Exception
	{
		return uploadAllegato(file, Collections.singletonList(903), idParere);
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, readOnly=false, timeout=60000, rollbackFor=Exception.class, propagation=Propagation.REQUIRED)
	public void deleteAllegato(UUID idAllegato, Long idParere) throws Exception
	{
		ParereSoprintendenzaDTO parere = dao.find(idParere);
		checkPermission(parere.getIdPratica());
		checkOrganizzazioneCreazione(parere.getIdPratica());
		checkParereDaAllegare(parere.getIdPratica());
		dao.deleteAllegatoParere(idAllegato);
		allegatoService.deleteAllegato(idAllegato.toString());
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, readOnly=false, timeout=60000, rollbackFor=Exception.class, propagation=Propagation.REQUIRED)
	public ParereSoprintendenzaDTO allegaParere(ParereSoprintendenzaDTO entity) throws Exception
	{
		checkPermission(entity.getIdPratica());
		checkOrganizzazioneCreazione(entity.getIdPratica());
		checkParereDaAllegare(entity.getIdPratica());
		StatoParere stato = StatoParere.PARERE_INSERITO_ENTE;
		dao.update(entity);
		ParereSoprintendenzaDetailsDTO details = find(entity.getIdPratica());
		completaParere(details, entity);
		Set<ConstraintViolation<ParereSoprintendenzaDetailsDTO>> violations = validator.validate(details);
		if(violations != null && !violations.isEmpty())
		{
			String errors = "Impossibile proseguire con la trasmissioni del provvedimento, errori presenti: " 
						  + violations.stream().map(m -> m.getMessage()).collect(Collectors.joining(", "));
			logger.error(errors);
			throw new Exception(errors);
		}
		
		if(userUtil.getGruppo().equals(Gruppi.SBAP_))
		{
			comunicazioniService.sendComunication(details.getIdCorrispondenza(), entity.getIdPratica());
			stato = StatoParere.PARERE_INSERITO_SOPRINTENDENZA;
		}
		praticaRepository.updateStatoParere(entity.getIdPratica(), stato);
		FascicoloStatoBean fsb=new FascicoloStatoBean();
		PraticaDTO pratica=this.praticaRepository.find(entity.getIdPratica());
		fsb.setPratica(pratica);
		fsb.setSezioniAllegati(List.of(SezioneAllegati.PARERE_SOPR));
		this.queueService.insertNewQueue(SendPlanToDiogeneScheduler.ID, fsb);
		//allineo la cds 
		this.allineaCds(entity.getIdPratica(),details.getAllegati());
		return entity;
	}
	
	private void allineaCds(UUID idPratica, List<AllegatiDTO> allegati) {
		if(ListUtil.isNotEmpty(allegati)) {
			for(AllegatiDTO allegato:allegati) {
				try {
					this.conferenzaSvc.appendiDocumentoACds(idPratica, SezioneAllegati.PARERE_SOPR, allegato);
				} catch (JsonProcessingException | SQLException e) {
					logger.error("Errore nell'allineaCDS per l'allegato {} ,error {}",allegato.getId(),e);
				}
			}
		}
	}

	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, readOnly=false, timeout=60000, rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
	public DettaglioCorrispondenzaDTO creaComunicazione(Long idParere) throws Exception
	{
		ParereSoprintendenzaDTO parere = dao.find(idParere);
		PraticaDTO pratica = praticaRepository.find(parere.getIdPratica());
		checkPermission(parere.getIdPratica());
		checkOrganizzazioneCreazione(parere.getIdPratica());
		checkParereDaAllegare(parere.getIdPratica());
		if(!userUtil.getGruppo().equals(Gruppi.SBAP_))
		{
			logger.error("Errore: operazione non conentita per gruppi differenti da 'Soprintendenza'");
			throw new Exception("Errore: operazione non conentita per gruppi differenti da 'Soprintendenza'");
		}

//		Gruppi gruppo = userUtil.getGruppo();
		Integer idOrganizzazione=userUtil.getIntegerId();
		
		TemplateEmailDestinatariDto template = templateService.info(idOrganizzazione, "COM_IMM_PAR_SOP_10");	
		TemplateEmailDTO templateEmail = template.getTemplate();
		DettaglioCorrispondenzaDTO comunicazione = new DettaglioCorrispondenzaDTO();
		CorrispondenzaDTO corrispondenza = new CorrispondenzaDTO();
		
		corrispondenza.setIdOrganizzazioneOwner(idOrganizzazione);
		corrispondenza.setBozza(true);
		corrispondenza.setTesto(templateResolver.risolviTesto(templateEmail.getPlaceholders(), templateEmail.getTesto(), pratica));
		corrispondenza.setOggetto(templateResolver.risolviTesto(templateEmail.getPlaceholders(), templateEmail.getOggetto(), pratica));
		
		comunicazione.setCorrispondenza(corrispondenza);
		if(template.getDestinatari() != null)
			comunicazione.setDestinatari(template.getDestinatari().stream().map(DestinatarioDTO::new).collect(Collectors.toList()));
		comunicazioniService.saveComunication(comunicazione, parere.getIdPratica());
		dao.insertIdCorrispondenza(idParere, comunicazione.getCorrispondenza().getId());
		return comunicazione;
	}
	
	private void checkPermission(UUID idPratica) throws Exception
	{
		PraticaSearch search = new PraticaSearch();
		this.istrPraticaService.prepareForSearch(search);
		search.setId(idPratica);
		if(praticaRepository.count(search) < 1)
		{
			final String message="Errore, non possiedi i permessi per visualizzare/modificare questa pratica";
			logger.error(message);
			throw new CustomOperationToAdviceException(message);
		}
		search.setEscludiAccertamento(true);
		if(praticaRepository.count(search) < 1)
		{
			final String message="Il procedimento della pratica è di tipo ACCERTAMENTO e non sono ammesse azioni  !!";
			logger.error(message);
			throw new CustomOperationToAdviceException(message);
		}
		
	}
	
	private void checkOrganizzazioneCreazione(UUID idPratica) throws Exception
	{
		ParereSoprintendenzaDTO parere = dao.find(idPratica);
		if(!parere.getOrganizzazioneCreazione().equals(userUtil.getLongId()))
		{
			logger.error("Non hai i permessi per operare su questo parere");
			throw new CustomOperationToAdviceException("Non hai i permessi per operare su questo parere");
		}
	}
	
	private void checkParereDaInserire(UUID idAllegato) throws Exception
	{
		StatoParere stato = praticaRepository.getStatoParere(idAllegato);
		if(!stato.equals(StatoParere.PARERE_NON_ALLEGATO))
		{
			if(stato.equals(StatoParere.PARERE_NON_PREVISTO))
			{
				logger.error("Errore: parere soprintendenza non previsto per questa pratica!");
				throw new Exception("Errore: parere soprintendenza non previsto per questa pratica!");
			}
			else if(stato.equals(StatoParere.PARERE_IN_BOZZA_ENTE) ||
					stato.equals(StatoParere.PARERE_IN_BOZZA_SOPRINTENDENZA))
			{
				logger.error("Errore: è presente una bozza di parere, non è possibile aggiungerne una nuova!");
				throw new Exception("Errore: è presente una bozza di parere, non è possibile aggiungerne una nuova!");
			}
			else
			{
				logger.error("Errore: parere soprintendenza già allegato a questa pratica!");
				throw new Exception("Errore: parere soprintendenza già allegato a questa pratica!");
			}
		}
	}
	
	private void checkParereDaAllegare(UUID idAllegato) throws Exception
	{
		StatoParere stato = praticaRepository.getStatoParere(idAllegato);
		if(!stato.equals(StatoParere.PARERE_NON_ALLEGATO) && 
		   !stato.equals(StatoParere.PARERE_IN_BOZZA_ENTE) &&
		   !stato.equals(StatoParere.PARERE_IN_BOZZA_SOPRINTENDENZA))
		{
			if(stato.equals(StatoParere.PARERE_NON_PREVISTO))
			{
				logger.error("Errore: parere soprintendenza non previsto per questa pratica!");
				throw new Exception("Errore: parere soprintendenza non previsto per questa pratica!");
			}
			else
			{
				logger.error("Errore: parere soprintendenza già allegato a questa pratica!");
				throw new Exception("Errore: parere soprintendenza già allegato a questa pratica!");
			}
		}
	}
	
	private void completaParere(ParereSoprintendenzaDetailsDTO toComplete, ParereSoprintendenzaDTO base)
	{
		toComplete.setEsitoParere(base.getEsitoParere());
		toComplete.setDettaglioPrescrizione(base.getDettaglioPrescrizione());
		toComplete.setNote(base.getNote());
		toComplete.setNumeroProtocollo(base.getNumeroProtocollo());
		toComplete.setNominativoIstruttore(base.getNominativoIstruttore());
	}
	
}
