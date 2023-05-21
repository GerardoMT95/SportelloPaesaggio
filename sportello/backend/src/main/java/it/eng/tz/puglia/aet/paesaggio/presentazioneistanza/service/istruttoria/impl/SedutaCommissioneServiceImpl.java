package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.ApplicationProperties;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.DettaglioCorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainNumberValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.AllegatiCommissioneLocaleDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.CLEntiCommissioniDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.CommissioneLocaleDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.SedutaCommissioneDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.SedutaCommissioneExtendedDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.SedutaCommissioneInputDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.SimpleAllegatiSedutaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.SezioneAllegati;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoSeduta;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoSedutaCommissione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoAllegatoSeduta;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoDestinatario;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.InavlidDateCLException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.AuthClient;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.CorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinatarioDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.SedutaCommissioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TemplateDestinatarioDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TemplateEmailDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.AllegatiRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.SedutaCommissioneRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TemplateDestinatarioRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TemplateEmailRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TipoContenutoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.AllegatiSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PraticaSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.SedutaCommissioneSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.TemplateDestinatarioSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.AssUtenteGruppo;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.AutomaticUserGroupRequestBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.GruppoBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.PMApplication;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.PMGroupsRequestBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.PM_GruppiRuoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.UtenteGruppo;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.UtenteInsertDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.PaesaggioOrganizzazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.search.CommissioneLocaleSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.enumeration.TipoOrganizzazione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.repository.CommonRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.SendPlanToDiogeneScheduler;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.bean.FascicoloStatoBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.AllegatoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.ComunicazioniService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.IstrPraticaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.SedutaCommissioneService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.PlaceHolder;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.dto.TemplateEmailDestinatariDto;
import it.eng.tz.puglia.batch.queue.IQueueService;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.service.http.IHttpClientService;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class SedutaCommissioneServiceImpl implements SedutaCommissioneService
{
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired private ApplicationProperties props;

	@Autowired private SedutaCommissioneRepository dao;
	@Autowired private CommonRepository commonRepository;
	@Autowired private PraticaRepository praticaRepository;
	@Autowired private AllegatiRepository allegatiRepository;
	@Autowired private TipoContenutoRepository tcRepository;
	@Autowired private TemplateEmailRepository templateRepository;
	@Autowired private TemplateDestinatarioRepository templateDestRepository;
	@Autowired private UserUtil userUtil;
	@Autowired private IHttpClientService client;
	@Autowired private AuthClient profileManager;
	@Autowired private ComunicazioniService comunicazioniService;
	@Autowired private IQueueService queueService;

	@Qualifier("allegatiIstruttoria")
	@Autowired
	private AllegatoService allegatiService;
	
	@Autowired IstrPraticaService istrPraticaService;
	
	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public PaginatedList<SedutaCommissioneDto> search(SedutaCommissioneSearch search) throws Exception
	{
		PaginatedList<SedutaCommissioneDto> dto = dao.searchWitCount(search);
		return dto;
	}

	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public SedutaCommissioneExtendedDTO find(Long idSeduta) throws Exception
	{
		SedutaCommissioneExtendedDTO dto = new SedutaCommissioneExtendedDTO(dao.find(idSeduta));
		PraticaSearch search = new PraticaSearch();
		this.istrPraticaService.prepareForSearch(search);
		search.setIdSeduta(idSeduta);
		dto.setPraticheDetails(praticaRepository.searchAll(search));
		dto.setAllegati(getAllegati(idSeduta));
		return dto;
	}

	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_WRITE, readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public SedutaCommissioneInputDTO insert(SedutaCommissioneInputDTO entity) throws Exception
	{
		Integer enteId = userUtil.getIntegerId();
		Integer idOrganizzazione = null;
		try
		{
			idOrganizzazione = commonRepository.getIdCommissioneByEnte(enteId, new Date());
		} catch (Exception e)
		{
			logger.error("Errore, non è stato possibile recuperare una commissione locale per l'ente cnìon id organizzazione {}",enteId);
			throw new Exception("Errore, non è stato possibile recuperare una commissione locale per l'ente cnìon id organizzazione " + enteId);
		}
		entity.setIdOrganizzazione(idOrganizzazione);
		entity.setIdEnteDelegato(enteId);
		entity.setUsernameUtenteCreazione(LogUtil.getUser());
		entity.setStato(StatoSedutaCommissione.PUBBLICATA);
		entity.setId(dao.insert(entity));
		Integer n = dao.insertSedutaPratica(entity.getPratiche(), entity.getId());
		if (n != entity.getPratiche().size())
		{
			logger.error("Errore, mi aspettavo di inserire {} record in \"seduta_pratica\" e invece ne sono stati inseriti {}", entity.getPratiche().size(), n);
			throw new Exception("Errore, mi aspettavo di inserire " + entity.getPratiche().size() + " record in \"seduta_pratica\" e invece ne sono stati inseriti " + n);
		}
		SedutaCommissioneExtendedDTO details = new SedutaCommissioneExtendedDTO(entity);
		details.setPraticheDetails(praticaRepository.getDetails(entity.getPratiche()));
		DettaglioCorrispondenzaDTO comunicazione = getTemplateComunicazione(details, TipoTemplate.INSERT);
		Long idCorrispondenza = sendMail(idOrganizzazione, comunicazione);
		dao.insertSedutaCorrispondenza(entity.getId(), idCorrispondenza);
		return entity;
	}

	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_WRITE, readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public SedutaCommissioneInputDTO update(SedutaCommissioneInputDTO entity) throws Exception
	{
		if (dao.getStatoSeduta(entity.getId()).equals(StatoSedutaCommissione.CONCLUSA))
		{
			logger.error("Non è possibile modificare una seduta di commisione dopo che questa è stato pubblicata");
			throw new Exception("Non è possibile modificare una seduta di commisione dopo che questa è stato pubblicata");
		}
		entity.setStato(StatoSedutaCommissione.PUBBLICATA);
		dao.update(entity);
		// Gestione pratica per seduta
		List<UUID> ids = dao.getPratiche(entity.getId());
		List<UUID> toDelete = ids.parallelStream().filter(p -> !entity.getPratiche().contains(p))
				.collect(Collectors.toList());
		List<UUID> toAdd = entity.getPratiche().parallelStream().filter(p -> !ids.contains(p))
				.collect(Collectors.toList());
		Integer n1 = dao.removeSedutaPratica(toDelete, entity.getId());
		Integer n2 = dao.insertSedutaPratica(toAdd, entity.getId());
		if (n1 != toDelete.size() || n2 != toAdd.size())
		{
			logger.error("Errore nell'aggiornamento dei record in \"seduta_pratica\"");
			throw new Exception("Errore nell'aggiornamento dei record in \"seduta_pratica\"");
		}
		Integer idOrganizzazione = find(entity.getId()).getIdOrganizzazione();
		entity.setIdOrganizzazione(idOrganizzazione);
		SedutaCommissioneExtendedDTO details = new SedutaCommissioneExtendedDTO(entity);
		details.setPraticheDetails(praticaRepository.getDetails(entity.getPratiche()));
		DettaglioCorrispondenzaDTO comunicazione = getTemplateComunicazione(details, TipoTemplate.INSERT);
		Long idCorrispondenza = sendMail(idOrganizzazione, comunicazione);
		dao.insertSedutaCorrispondenza(entity.getId(), idCorrispondenza);
		return entity;
	}

	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_WRITE, readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer delete(Long idSeduta) throws Exception
	{
		if (dao.getStatoSeduta(idSeduta).equals(StatoSedutaCommissione.CONCLUSA))
		{
			logger.error("Non è possibile eliminare una seduta conclusa");
			throw new Exception("Non è possibile eliminare una seduta conclusa");
		}
		// cancello gli allegati
		List<UUID> idsAllegati = dao.getAllegatiSeduta(idSeduta);
		if (idsAllegati != null && idsAllegati.size() > 0)
		{
			for (UUID id : idsAllegati)
				allegatiService.deleteAllegato(id.toString());
		}
		// cancello le comunicazioni
		List<Long> idsCorrispondenza = dao.getIdCorrispondenze(idSeduta);
		if (idsCorrispondenza != null && idsCorrispondenza.size() > 0)
		{
			for (Long id : idsCorrispondenza)
				comunicazioniService.eraseComunication(id);
		}
		return dao.delete(idSeduta);
	}

	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_WRITE, readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public AllegatiCommissioneLocaleDTO upload(MultipartFile file, Integer tipoContenuto, List<UUID> pratiche, Long idSeduta) throws Exception
	{
		SedutaCommissioneDTO seduta = dao.find(idSeduta);
		TipoAllegatoSeduta tipoAllegato = TipoAllegatoSeduta.fromValue(tipoContenuto);
		if ((tipoAllegato == TipoAllegatoSeduta.VERBALE || tipoAllegato == TipoAllegatoSeduta.SCHEDA_TECNICA)
				&& dao.countPerTipologia(idSeduta, tipoAllegato) > 0)
		{
			logger.error("Non è possibile avere più di un documento di tipo {} per seduta di commissione.", tipoAllegato.name());
			throw new Exception("Non è possibile avere più di un documento di tipo " + tipoAllegato.name() + " per seduta di commissione.");
		}
		String nomeSedutaPath = StringUtil.concateneString(seduta.getNomeSeduta(), "__", seduta.getId());
		List<Integer> tcList = Collections.singletonList(tipoContenuto);
		String filename = file.getOriginalFilename();
		AllegatiDTO dto = allegatiService.uploadAllegatoSeduta(tcList, filename, file, nomeSedutaPath);
		AllegatiCommissioneLocaleDTO allegato = new AllegatiCommissioneLocaleDTO(dto);
		allegato.setPraticheAssociate(pratiche);
		dao.insertSedutaAllegati(allegato.getId(), idSeduta);
		dao.insertAllegatoSedutaPratica(allegato, idSeduta);
		allegato.setTipoAllegato(tipoAllegato);
		return allegato;
	}

	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_WRITE, readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer remove(UUID idAllegato, Long idSeduta) throws Exception
	{
		if (dao.getStatoSeduta(idSeduta).equals(StatoSedutaCommissione.CONCLUSA))
		{
			logger.error("Non è possibile eliminare una seduta conclusa");
			throw new Exception("Non è possibile eliminare una seduta conclusa");
		}
		Integer n = dao.removeAllegatoSedutaPratica(idAllegato);
		dao.deleteAllegato(idAllegato);
		allegatiService.deleteAllegato(idAllegato.toString());
		return n;
	}

	private List<AllegatiCommissioneLocaleDTO> getAllegati(Long idSeduta) throws Exception
	{
		List<AllegatiCommissioneLocaleDTO> allegatiCommissione = new LinkedList<AllegatiCommissioneLocaleDTO>();
		AllegatiSearch search = new AllegatiSearch();
		search.setIdSeduta(idSeduta);
		List<AllegatiDTO> allegati = allegatiRepository.search(search).getList();
		for (AllegatiDTO allegato : allegati)
		{
			AllegatiCommissioneLocaleDTO acl = new AllegatiCommissioneLocaleDTO(allegato);
			List<Integer> temp = tcRepository.findTipiAllegato(allegato.getId());
			acl.setIdSeduta(idSeduta);
			acl.setPraticheAssociate(dao.getCodiciPratica(allegato.getId(), idSeduta));
			if (temp != null && !temp.isEmpty())
				acl.setTipoAllegato(TipoAllegatoSeduta.fromValue(temp.get(0)));
			allegatiCommissione.add(acl);
		}
		return allegatiCommissione;
	}

	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_WRITE, readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void pubblicaSeduta(Long idSeduta) throws Exception
	{
		SedutaCommissioneDTO dto = dao.find(idSeduta);
		if (!dto.getStato().equals(StatoSedutaCommissione.PUBBLICATA) || dao.countPraticheEsaminate(idSeduta) == 0)
		{
			logger.error("Errore durante la conclusione della seduta: la seduta non riespetta i requisiti per essere conlcusa, lo ststo deve essere 'pubblicato' e deve essere stata esaminata almeno una pratica");
			throw new Exception("Errore durante la conclusione della seduta: la seduta non riespetta i requisiti per essere conlcusa, lo stato deve essere 'pubblicato' e deve essere stata esaminata almeno una pratica");
		}
		dto.setStato(StatoSedutaCommissione.CONCLUSA);
		Integer n = dao.update(dto);
		if (n != 1)
		{
			logger.error("Errore durante la conclusione della seduta, dovevo aggiornare 1 seduta ma ne sono state aggiornate {}", n);
			throw new Exception("Errore durante la conclusione della seduta, dovevo aggiornare 1 seduta ma ne sono state aggiornate " + n);
		}
		praticaRepository.updateStatoSeduta(idSeduta, StatoSeduta.VERBALE_SEDUTA_ALLEGATO);
		List<UUID> idPratiche = dao.getPratiche(idSeduta);
		if(ListUtil.isNotEmpty(idPratiche)) {
			for(UUID id:idPratiche) {
				PraticaDTO pratica = this.praticaRepository.find(id);
				//invio in coda la sincronizzazione per diogene allineaDiogene(bean)
				FascicoloStatoBean fsb=new FascicoloStatoBean();
				fsb.setPratica(pratica);
				fsb.setSezioniAllegati(List.of(SezioneAllegati.COM_LOC));
				this.queueService.insertNewQueue(SendPlanToDiogeneScheduler.ID, fsb);
			}
		}
	}

	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Boolean checkOrganizzazione(Long idSeduta) throws Exception
	{
		return dao.verificaAbilitazioneSeduta(idSeduta, userUtil.getIntegerId());
	}

	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public List<SimpleAllegatiSedutaDTO> searchAttachments(UUID idPratica) throws Exception
	{
		List<SimpleAllegatiSedutaDTO> entities = allegatiRepository.searchAllegatiSeduta(idPratica);
		if (entities != null && !entities.isEmpty())
		{
			for (SimpleAllegatiSedutaDTO a : entities)
			{
				List<Integer> types = tcRepository.findTipiAllegato(a.getId());
				a.setPratiche(dao.getPraticaAllegati(a.getId()));
				if (types != null && !types.isEmpty())
					a.setTipoAllegato(TipoAllegatoSeduta.fromValue(types.get(0)));
			}
		}
		return entities;
	}
	
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, readOnly=true, rollbackFor=Exception.class, propagation=Propagation.MANDATORY, timeout=60000)
	private DettaglioCorrispondenzaDTO getTemplateComunicazione(SedutaCommissioneExtendedDTO entity, TipoTemplate type) throws Exception
	{
		try
		{
			DettaglioCorrispondenzaDTO comunicazione = new DettaglioCorrispondenzaDTO();
			TemplateDestinatarioSearch search = new TemplateDestinatarioSearch();
			search.setIdOrganizzazione(Integer.toString(userUtil.getIntegerId()));
			search.setCodiceTemplate(type.getCodice());
			TemplateEmailDTO templateEmail = templateRepository.find(userUtil.getIntegerId(), type.getCodice());
			List<TemplateDestinatarioDTO> templateDest = templateDestRepository.search(search).getList();
			List<DestinatarioDTO> destinatari = new ArrayList<DestinatarioDTO>();
			if(entity.getDestinatari() != null)
				destinatari.addAll(entity.getDestinatari());
			if(templateDest != null && !templateDest.isEmpty())
			{
				destinatari.addAll(templateDest.stream()
											   .filter(p -> destinatari.stream()
													   		.allMatch(e -> !e.getEmail().equals(p.getEmail())))
											   .map(TemplateDestinatarioDTO::toDestinatarioDTO).collect(Collectors.toList()));
			}
			TemplateEmailDestinatariDto template = new TemplateEmailDestinatariDto(templateEmail, templateDest);
			comunicazione.setCorrispondenza(findAndReplace(entity, template));
			comunicazione.setDestinatari(destinatari);	
			return comunicazione;
		}
		catch(Exception e)
		{
			logger.error("Errore durante la get del template {} e sostituzione dei placeholder: {}", type.getCodice(), e.getMessage(), e);
			throw new Exception("Errore durante la get del template " + type.getCodice() + " e sostituzione dei placeholder");
		}
	}
	
	private CorrispondenzaDTO findAndReplace(SedutaCommissioneExtendedDTO entity, TemplateEmailDestinatariDto template) throws Exception
	{		
		List<String> placeholders = Arrays.asList(template.getTemplate().getPlaceholders().split(","));
		String oggetto = template.getTemplate().getOggetto();
		String testo = template.getTemplate().getTesto();
		
		CorrispondenzaDTO corrispondenza = new CorrispondenzaDTO();
		corrispondenza.setComunicazione(false);
		corrispondenza.setMittenteEnte(userUtil.getNomeGruppo());
		corrispondenza.setMittenteUsername(LogUtil.getUser());
		
		SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdfHours = new SimpleDateFormat("HH:mm");
		
		for(String placeholder: placeholders)
		{
			String toReplace = "{"+placeholder.trim()+"}";
			String sobstitute = "";
			switch (PlaceHolder.valueOf(placeholder.trim()))
			{
			case NOME_SEDUTA:
				sobstitute = entity.getNomeSeduta();
				break;
			case DATA_SEDUTA:
				sobstitute = sdfDate.format(entity.getDataSeduta());
				break;
			case ORARIO_SEDUTA:
				sobstitute = sdfHours.format(entity.getDataSeduta());
				break;
			case PRATICHE_DA_ESAMINARE:
				sobstitute = StringUtil.concateneString("<ul>",
														entity.getPraticheDetails().stream()
																				   .map(m -> "<li>" + m.getCodicePraticaAppptr() + 
																						     " - " + m.getOggetto() + "</li>")
																				   .collect(Collectors.joining(" ")),
														"</ul>");
				
				break;
			case RUP:
				//Se invio una mail sono sicuro di essere il rup che fa operazioni in modifica su una seduta
				sobstitute = userUtil.getMyProfile().getNome() + " " + userUtil.getMyProfile().getCognome();
				break;
			case MEMBRI_COMMISSIONE:
				String jwt = client.getBatchUser().getAuthorization();
//				List<UtenteGruppo> usersCL = Arrays.asList(profileManager.getUtentiGruppo(jwt,
//																						  props.getCodiceApplicazione(),
//																						  "CL_" + entity.getIdOrganizzazione() + "_F").getPayload());
				String clGroup = "CL_" + entity.getIdOrganizzazione() + "_F";
				List<AssUtenteGruppo> usersCL = Arrays.asList(profileManager.utentiInGruppi(jwt, props.getCodiceApplicazione(), Arrays.asList(clGroup)).getPayload());
				if(usersCL != null && !usersCL.isEmpty())
				{
					sobstitute = StringUtil.concateneString("<ul>",
															usersCL.stream()
																   .map(m -> "<li>" + m.getNome() + 
																		     " " + m.getCognome() + "</li>")
																   .collect(Collectors.joining(" ")),
															"</ul>");
				}
				
				break;
			default: 
				
			}
			oggetto = oggetto.replace(toReplace, sobstitute);
			testo = testo.replace(toReplace, sobstitute);
				
		}
		
		corrispondenza.setOggetto(oggetto);
		corrispondenza.setTesto(testo);
		return corrispondenza;
	}

	private List<DestinatarioDTO> findDestinatari(Integer idCommissione, List<DestinatarioDTO> destinatariFissi) throws Exception
	{
		List<DestinatarioDTO> destinatari = new ArrayList<DestinatarioDTO>();
		destinatariFissi.addAll(destinatariFissi);
		String jwt = client.getBatchUser().getAuthorization();
//		List<UtenteGruppo> usersCL = 
//				Arrays.asList(profileManager.getUtentiGruppo(jwt,props.getCodiceApplicazione(), "CL_" + idCommissione + "_F").getPayload());
		String code = "CL_" + idCommissione + "_F";
		List<AssUtenteGruppo> usersCL = Arrays.asList(profileManager.utentiInGruppi(jwt,props.getCodiceApplicazione(), Arrays.asList(code)).getPayload());
		if (usersCL != null && !usersCL.isEmpty())
		{
			for (AssUtenteGruppo user : usersCL)
			{
				if (!destinatariFissi.stream().map(DestinatarioDTO::getEmail).anyMatch(d -> user.getMail().equals(d)))
				{
					DestinatarioDTO dest = new DestinatarioDTO();
					dest.setEmail(user.getMail());
					dest.setNome(user.getNome() + " " + user.getCognome());
					dest.setTipo(TipoDestinatario.TO);
					destinatari.add(dest);
				}
			}
		}
		return destinatari;
	}

	@Transactional(transactionManager = DatabaseConfiguration.TX_WRITE, readOnly = false, rollbackFor = Exception.class, propagation = Propagation.MANDATORY)
	private Long sendMail(Integer idCommissione, DettaglioCorrispondenzaDTO comunicazione) throws Exception
	{
		List<DestinatarioDTO> destinatari = findDestinatari(idCommissione, comunicazione.getDestinatari());
		comunicazione.setDestinatari(destinatari);
		comunicazione = comunicazioniService.saveComunication(comunicazione, idCommissione);
		comunicazioniService.sendComunication(comunicazione, idCommissione);
		return comunicazione.getCorrispondenza().getId();
	}
	
	private enum TipoTemplate
	{
		INSERT("CONV_SED_COM"),
		UPDATE("AGG_SED_COM");
		
		private String codice;
		
		private TipoTemplate(String codice) { this.codice = codice; }
		private String getCodice() { return codice; }
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED)
	public Boolean sedutaPresente(UUID idPratica) throws Exception
	{
		return dao.praticaEsaminata(idPratica);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=false, rollbackFor=Exception.class, propagation=Propagation.REQUIRES_NEW)
	public CommissioneLocaleDTO creaCommissione(CommissioneLocaleDTO entity) throws Exception
	{
		List<CLEntiCommissioniDTO> clEsistenti = evaluateRangeDate(entity.getOrganizzazioniAssociate(), entity.getDataCreazione(), entity.getDataTermine());
		if(clEsistenti != null && !clEsistenti.isEmpty())
		{
			logger.error("Errore: alcuni enti sono associati ad altre commissione locali per il range di date inserite: {}", clEsistenti);
			throw new InavlidDateCLException("Errore: alcuni enti sono associati ad altre commissione locali per il range di date inserite");
		}
		entity.setTipoOrganizzazione(TipoOrganizzazione.CL);
		entity.setTipoOrganizzazioneSpecifica("Commissione locale");
		entity.setId(commonRepository.insertPaesaggioOrganizzazione(entity));
		int idApplicazione = commonRepository.getIdApplicazione(props.getCodiceApplicazione());
		commonRepository.insertPaesaggioOrganizzazioneAttributi(
				idApplicazione,entity.getId(),entity.getDataCreazione(),entity.getDataTermine());
		long n = commonRepository.insertPaesaggioCommissioneLocale(entity.getId(), entity.getOrganizzazioniAssociate());
		if(n == 0)
		{
			logger.error("Errore nell'assciazione delle organizzazioni alla commisione locale '{}'", entity.getDenominazione());
			throw new Exception("Errore nell'associazione delle organizzazioni alla commisione locale '"+entity.getDenominazione()+"'");
		}
		String jwt = LogUtil.getAuthorization();
		PMApplication app = profileManager.findAppByCode(jwt, props.getCodiceApplicazione()).getPayload();
		PMGroupsRequestBean bean = new PMGroupsRequestBean();
		bean.setCodiceGruppo("CL_"+entity.getId()+"_F");
		bean.setIdApplicazione(app.getId());
		bean.setNomeGruppo(entity.getDenominazione());
		profileManager.saveGroup(jwt, bean);
		return entity;
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=false, rollbackFor=Exception.class, propagation=Propagation.REQUIRES_NEW)
	public CommissioneLocaleDTO aggiornaCommissione(CommissioneLocaleDTO entity) throws Exception
	{
		List<CLEntiCommissioniDTO> clEsistenti = evaluateRangeDate(entity.getId(), entity.getOrganizzazioniAssociate(), entity.getDataCreazione(), entity.getDataTermine());
		if(clEsistenti != null && !clEsistenti.isEmpty())
		{
			logger.error("Errore: alcuni enti sono associati ad altre commissione locali per il range di date inserite: {}", clEsistenti);
			throw new InavlidDateCLException("Errore: alcuni enti sono associati ad altre commissione locali per il range di date inserite");
		}
		PaesaggioOrganizzazioneDTO oldEntity = commonRepository.findCommissioneLocale(entity.getId());
		entity.setTipoOrganizzazione(TipoOrganizzazione.CL);
		entity.setTipoOrganizzazioneSpecifica("Commissione locale");
		long n1 = commonRepository.updatePaesaggioOrganizzazione(entity);
		int idApp = commonRepository.getIdApplicazione(props.getCodiceApplicazione());
		commonRepository.updatePaesaggioOrganizzazioneAttributi(
				idApp,entity.getId(),oldEntity.getDataCreazione(),oldEntity.getDataTermine(),
				entity.getDataCreazione(),entity.getDataTermine());
		commonRepository.deletePaesaggioCommissioneLocale(entity.getId());
		long n2 = commonRepository.insertPaesaggioCommissioneLocale(entity.getId(), entity.getOrganizzazioniAssociate());
		
		if(n1 == 0 || n2 == 0)
		{
			logger.error("Errore nell'aggiornamento della commisione locale '{}'", entity.getDenominazione());
			throw new Exception("Errore nell'aggiornamento della commisione locale '"+entity.getDenominazione()+"'");
		}
		String jwt = LogUtil.getAuthorization();
		String codiceGruppo = "CL_"+entity.getId()+"_F";
		List<PM_GruppiRuoli> groups = profileManager.findGruppiDetails(jwt, props.getCodiceApplicazione(), codiceGruppo).getPayload();
		if(groups == null || groups.isEmpty())
		{
			//Volendo posso intercettare questa casistica per rigenerare il gruppo in quanto, probabilmente, cancellato a mano per errore
			logger.error("Errore: non è stato trovato nessun gruppo '{}' sul Profile Manager", codiceGruppo);
			throw new Exception("Errore: non è stato trovato nessun gruppo '"+codiceGruppo+"' sul Profile Manager");
		}
		//Se il nome del gruppo su profile manager è diverso lo aggiorno
		if(!groups.get(0).getNomeGruppo().equals(entity.getDenominazione()))
		{
			String idApplicazione = groups.get(0).getIdApplicazione();
			String idGruppo = groups.get(0).getId();
			PMGroupsRequestBean bean = new PMGroupsRequestBean();
			bean.setId(idGruppo);
			bean.setCodiceGruppo("CL_"+entity.getId()+"_F");
			bean.setIdApplicazione(idApplicazione);
			bean.setNomeGruppo(entity.getDenominazione());
			profileManager.updateGroup(jwt, bean);
		}
		return entity;
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRES_NEW)
	public List<PlainNumberValueLabel> getEnti() throws Exception
	{
		List<PaesaggioOrganizzazioneDTO> entiPaes = commonRepository.findEnti();
		List<PlainNumberValueLabel> enti = null;
		if(entiPaes != null)
			enti = entiPaes.stream().map(m -> new PlainNumberValueLabel(m.getId(), m.getDenominazione())).collect(Collectors.toList());
		return enti;
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED)
	public List<CLEntiCommissioniDTO> evaluateRangeDate(Long idCommissione, List<Long> enti, Date dataInizioVal, Date dataFineVal) throws Exception
	{
		return commonRepository.checkEntiForCommissione(enti, idCommissione, dataInizioVal, dataFineVal);
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED)
	public List<CLEntiCommissioniDTO> evaluateRangeDate(List<Long> enti, Date dataInizioVal, Date dataFineVal) throws Exception
	{
		return commonRepository.checkEntiForCommissione(enti, null, dataInizioVal, dataFineVal);
	}
	
	@Override
	public List<PlainStringValueLabel> utentiAssociabili() throws Exception
	{
		String codice = Gruppi.USER_CL.name();
		return genericCercaUtenti(codice);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRES_NEW)
	public PaginatedList<PaesaggioOrganizzazioneDTO> cercaCommissione(CommissioneLocaleSearch search) throws Exception
	{
		String jwt = LogUtil.getAuthorization();
		if(StringUtil.isNotEmpty(search.getUsername()))
		{
			List<String> invalidGroups = Arrays.asList(Gruppi.ADMIN.name(), Gruppi.RICHIEDENTI.name(), Gruppi.USER_CL.name());
			List<Long> ids = new ArrayList<Long>();
			GruppoBean[] gr = profileManager.getGruppiUtente(jwt, props.getCodiceApplicazione(), search.getUsername()).getPayload();
			for(GruppoBean g: gr)
			{
				if(!invalidGroups.contains(g.getCodiceGruppo()))
				{
					String[] tmp = org.apache.commons.lang3.StringUtils.split(g.getCodiceGruppo(), "_");
					if(tmp != null)
					{
						Long id = Long.parseLong(tmp[1]);
						ids.add(id);
					}
				}
			}
			if(!ids.isEmpty())
				search.setIds(ids);
		}
		PaginatedList<PaesaggioOrganizzazioneDTO> pList = commonRepository.searchCommissioneLocale(search);
		return pList;
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED)
	public CommissioneLocaleDTO findCommissioneLocale(Long idCommissione) throws Exception
	{
		CommissioneLocaleDTO com = new CommissioneLocaleDTO(commonRepository.findCommissioneLocale(idCommissione));
		com.setUsers(genericCercaUtenti("CL_"+idCommissione+"_F"));
		com.setEntiLabelValue(commonRepository.searchPaesaggioCommissioneLocale(com.getId()));
		if(com.getEntiLabelValue() != null)
			com.setOrganizzazioniAssociate(com.getEntiLabelValue().stream().map(PlainNumberValueLabel::getValue).collect(Collectors.toList()));
		return com;
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED)
	public CommissioneLocaleDTO findCommissioneByEnte(Long idEnte) throws Exception
	{
		CommissioneLocaleDTO commissione = new CommissioneLocaleDTO(commonRepository.findCommissioneByEnte(idEnte, new Date()));
		return commissione;
	}
	
	@Override
	public void aggiungiUtente(String username, Long idCommissione) throws Exception
	{
		String jwt = LogUtil.getAuthorization();
		String codiceGruppo = "CL_"+idCommissione+"_F";
		
		AutomaticUserGroupRequestBean body = new AutomaticUserGroupRequestBean();
		body.setApplicazioni(props.getCodiceApplicazione());
		body.setGruppi(codiceGruppo);
		body.setRuoli(new ArrayList<>());
		body.setAssociaRuoliTotale(true);
		body.setUsernameUtente(username);
		profileManager.insertUtente(jwt, body);
	}   
	
	@Override
	public void eliminaUtente(String username, Long idCommissione) throws Exception
	{
		String jwt = LogUtil.getAuthorization();
		String codiceGruppo = "CL_"+idCommissione+"_F";
		UtenteInsertDTO u = findIdUtente(username, codiceGruppo);
		PM_GruppiRuoli g = findGroup(codiceGruppo);
		String idGruppo = g.getId();
		String idUtente = u.getId();
		profileManager.deleteUtente(jwt, idGruppo, idUtente);

	}  
	
	@Override
	public List<PlainStringValueLabel> utentiCommissine(Long idCommissione) throws Exception
	{
		String codice = "CL_"+idCommissione+"_F";
		return genericCercaUtenti(codice);
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class, timeout=60000)
	public void eliminaPraticaDaSeduteAttive(UUID idPratica) throws Exception
	{
		dao.eliminaDaSeduteAttive(idPratica);
	}
	
	private List<PlainStringValueLabel> genericCercaUtenti(String codice) throws Exception
	{
		List<PlainStringValueLabel> items = null;
		//String jwt = LogUtil.getAuthorization();
		String jwt = client.getBatchUser().getAuthorization();
//		List<UtenteGruppo> payload = Arrays.asList(profileManager.getUtentiGruppo(jwt, props.getCodiceApplicazione(), codice).getPayload());
		List<AssUtenteGruppo> payload = Arrays.asList(profileManager.utentiInGruppi(jwt, props.getCodiceApplicazione(), Arrays.asList(codice)).getPayload());
		if(payload != null)
			items = payload.stream().map(PlainStringValueLabel::new).collect(Collectors.toList());
		return items;
	}
	
	private UtenteInsertDTO findIdUtente(String username, String codiceGruppo) throws Exception
	{
		UtenteInsertDTO result = new UtenteInsertDTO();
		String jwt = LogUtil.getAuthorization();
		List<UtenteGruppo> response = Arrays.asList(profileManager.getUtentiGruppo(jwt, props.getCodiceApplicazione(), codiceGruppo, username).getPayload());
		if(response != null && !response.isEmpty())
		{
			result.setId(response.get(0).getId());
			return result;
		}
		else
		{
			logger.error("Errore: dati dell'utente utente {} non trovati sul PM!", username);
			throw new Exception("Errore: dati dell'utente utente " + username + " non trovati sul PM!");
		}
	}
	
	private PM_GruppiRuoli findGroup(String codiceGruppo) throws Exception
	{
		String jwt = LogUtil.getAuthorization();
		List<PM_GruppiRuoli> groups = profileManager.findGruppiDetails(jwt, props.getCodiceApplicazione(), codiceGruppo).getPayload();
		if(groups == null || groups.isEmpty())
		{
			//Volendo posso intercettare questa casistica per rigenerare il gruppo in quanto, probabilmente, cancellato a mano per errore
			logger.error("Errore: non è stato trovato nessun gruppo '{}' sul Profile Manager", codiceGruppo);
			throw new Exception("Errore: non è stato trovato nessun gruppo '"+codiceGruppo+"' sul Profile Manager");
		}
		return groups.get(0);
	}
}
