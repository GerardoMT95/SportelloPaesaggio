package it.eng.tz.puglia.autpae.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import feign.FeignException;
import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dto.BE_to_FE.EnteBean;
import it.eng.tz.puglia.autpae.dto.BE_to_FE.PaesaggioEmailBean;
import it.eng.tz.puglia.autpae.dto.BE_to_FE.organizzazioni.PaesaggioOrganizzazioneBean;
import it.eng.tz.puglia.autpae.dto.BE_to_FE.organizzazioni.PaesaggioOrganizzazioniSearchBean;
import it.eng.tz.puglia.autpae.enumeratori.Ruoli;
import it.eng.tz.puglia.autpae.enumeratori.TipoPaesaggioOrganizzazione;
import it.eng.tz.puglia.autpae.generated.orm.dto.PaesaggioOrganizzazioneAttributiDTO;
import it.eng.tz.puglia.autpae.generated.orm.dto.PaesaggioOrganizzazioneCompetenzeDTO;
import it.eng.tz.puglia.autpae.generated.orm.dto.PaesaggioOrganizzazioneDTO;
import it.eng.tz.puglia.autpae.generated.orm.repository.PaesaggioOrganizzazioneAttributiRepository;
import it.eng.tz.puglia.autpae.generated.orm.repository.PaesaggioOrganizzazioneCompetenzeRepository;
import it.eng.tz.puglia.autpae.generated.orm.repository.PaesaggioOrganizzazioneRepository;
import it.eng.tz.puglia.autpae.search.PesaggioEmailSearchBean;
import it.eng.tz.puglia.autpae.service.interfacce.PaesaggioOrganizzazioneService;
import it.eng.tz.puglia.autpae.utility.validator.GenericValidator;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.service.http.IHttpClientService;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.PMApplication;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.PMGroupsRequestBean;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.PM_GruppiRuoli;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.IProfileManager;
import it.eng.tz.puglia.servizi_esterni.remote.dto.PaesaggioEmailDTO;
import it.eng.tz.puglia.servizi_esterni.remote.repository.CommonRepository;
import it.eng.tz.puglia.util.log.LogUtil;

@Service
public class PaesaggioOrganizzazioneServiceImpl implements PaesaggioOrganizzazioneService
{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired private ApplicationProperties props;
	
	@Autowired private PaesaggioOrganizzazioneRepository dao;
	@Autowired private PaesaggioOrganizzazioneCompetenzeRepository orgCompDao;
	@Autowired private PaesaggioOrganizzazioneAttributiRepository orgAttrDao;
	@Autowired private CommonRepository commondao;
	@Autowired private IProfileManager profileManager;
	@Autowired private IHttpClientService clientSvc;
	@Autowired GenericValidator validator;
	
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public PaginatedList<PaesaggioOrganizzazioneBean> searchPaesaggioOrganizzazione(PaesaggioOrganizzazioniSearchBean search) throws Exception
	{
		logger.info("Chiamo serch organizzazioni");
		//search.setTipiCercati(Arrays.asList(TipoPaesaggioOrganizzazione.ED, TipoPaesaggioOrganizzazione.ETI, TipoPaesaggioOrganizzazione.SBAP));
		return dao.searchOrganizzazioni(search);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public PaesaggioOrganizzazioneBean findPaesaggioOrganizzazione(Long idOrganizzazione) throws Exception
	{
		logger.info("Chiamo find organizzazione by id");
		PaesaggioOrganizzazioniSearchBean search = new PaesaggioOrganizzazioniSearchBean();
		search.setId(idOrganizzazione.toString());
		search.setTipiCercati(Arrays.asList(TipoPaesaggioOrganizzazione.ED, TipoPaesaggioOrganizzazione.ETI, TipoPaesaggioOrganizzazione.SBAP));
		PaesaggioOrganizzazioneBean bean = dao.searchOrganizzazioni(search).getList().get(0);
		bean.setEnti(dao.findEntiByOrganizzazione(idOrganizzazione));
		return bean;
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public PaesaggioOrganizzazioneBean findPaesaggioOrganizzazioneAllTipi(Long idOrganizzazione) throws Exception
	{
		logger.info("Chiamo find organizzazione by id");
		PaesaggioOrganizzazioniSearchBean search = new PaesaggioOrganizzazioniSearchBean();
		search.setId(idOrganizzazione.toString());
		PaesaggioOrganizzazioneBean bean = dao.searchOrganizzazioni(search).getList().get(0);
		bean.setEnti(dao.findEntiByOrganizzazione(idOrganizzazione));
		return bean;
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<EnteBean> findCompetenze(long idOrganizzazione) throws Exception
	{
		List<EnteBean> bean = dao.findEntiByOrganizzazione(idOrganizzazione);
		return bean;
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=false , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public PaesaggioOrganizzazioneBean insert(PaesaggioOrganizzazioneBean bean) throws Exception
	{
		validator.doValidate(bean);
		Long id = dao.insert(bean);
		bean.setId(id.intValue());
		//Attivo organizzazione per applicazione corrente
		int applicationId = commondao.getIdApplicazione(props.getCodiceApplicazioneProfile());
		PaesaggioOrganizzazioneAttributiDTO attivaBean = bean.getBeanAttivazione(applicationId);
		orgAttrDao.insert(attivaBean);
		//Associo enti comuni
		if(bean.getEnti() != null && !bean.getEnti().isEmpty())
		{
			for(EnteBean ente: bean.getEnti())
			{
				PaesaggioOrganizzazioneCompetenzeDTO e = new PaesaggioOrganizzazioneCompetenzeDTO();
				Integer idOrgComp = ente.getIdOrganizzazioneCompetenze() != null ? ente.getIdOrganizzazioneCompetenze().intValue() : null;
				e.setId(idOrgComp);
				e.setEnteId(ente.getIdEnte().intValue());
				e.setPaesaggioOrganizzazioneId(id.intValue());
				e.setDataInizioDelega(bean.getDataCreazione());
				e.setDataFineDelega(ente.getDataTermine());
				ente.setIdOrganizzazioneCompetenze(orgCompDao.insert(e));
			}
		}
		createGroup(bean);
		return bean;
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=false , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public PaesaggioOrganizzazioneBean update(PaesaggioOrganizzazioneBean bean) throws Exception
	{
		validator.doValidate(bean);
		Integer id = bean.getId();
		dao.update(bean);
		if(bean.getEnti() != null && !bean.getEnti().isEmpty())
		{
			for(EnteBean ente: bean.getEnti())
			{
				PaesaggioOrganizzazioneCompetenzeDTO e = new PaesaggioOrganizzazioneCompetenzeDTO();
				Long idOrgComp = ente.getIdOrganizzazioneCompetenze();
				e.setId(idOrgComp != null ? idOrgComp.intValue() : null);
				e.setEnteId(ente.getIdEnte().intValue());
				e.setPaesaggioOrganizzazioneId(id.intValue());
				e.setDataInizioDelega(bean.getDataCreazione());
				e.setDataFineDelega(ente.getDataTermine());
				if(idOrgComp == null)
					ente.setIdOrganizzazioneCompetenze(orgCompDao.insert(e));
				else
					orgCompDao.update(e);
			}
		}
		String codiceGruppo = bean.getTipoOrganizzazione().toString() + "_" + bean.getId();
		if(updateGruppo(codiceGruppo + "_A", Ruoli.AMMINISTRATORE, bean))
		{
			//se torna true vuol dire che il nome del gruppo è cambiato e vale la pena
			//eseguire l'update anche per dirigenti e funzionari
			updateGruppo(codiceGruppo + "_D", Ruoli.DIRIGENTE, bean);
			updateGruppo(codiceGruppo + "_F", Ruoli.FUNZIONARIO, bean);
		}
		return bean;
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=false , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public void attivaOrganizzazione(PaesaggioOrganizzazioneAttributiDTO bean) throws Exception
	{
		checkBeanAttivazioneValido(bean);
		int applicationId = commondao.getIdApplicazione(props.getCodiceApplicazioneProfile());
		bean.setApplicazioneId(applicationId);
		bean.setDataCreazione(new java.util.Date());
		orgAttrDao.insert(bean);
		//creo su pm se non esiste
		Integer id = bean.getPaesaggioOrganizzazioneId();
		PaesaggioOrganizzazioneBean pobean = findPaesaggioOrganizzazione(id.longValue());
		String jwt = LogUtil.getAuthorization();
		String codiceGruppo = pobean.getTipoOrganizzazione().toString() + "_" + pobean.getId() + "_A";
		List<PM_GruppiRuoli> groups = profileManager.findGruppiDetails(jwt, props.getCodiceApplicazioneProfile(), codiceGruppo).getPayload();
		if(groups == null || groups.isEmpty())
		{
			logger.info("Non esistono gruppi associati all'organizzazione con id {}", pobean.getId());
			createGroup(pobean);
		}
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=false , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public void aggiornaTermineAbilitazione(PaesaggioOrganizzazioneAttributiDTO bean) throws Exception
	{
		PaesaggioOrganizzazioneAttributiDTO oldbean = orgAttrDao.findBeanAttivazioneValido(bean.getPaesaggioOrganizzazioneId());
		int applicationId = commondao.getIdApplicazione(props.getCodiceApplicazioneProfile());
		if(oldbean == null)
		{
			logger.info("Organizzazione con id {} non attiva per l'applicazione {}", bean.getPaesaggioOrganizzazioneId(), applicationId);
			throw new Exception("Organizzazione con id " + bean.getPaesaggioOrganizzazioneId() + " non attiva per l'applicazione " + applicationId);
		}
		bean.setId(oldbean.getId());
		bean.setApplicazioneId(applicationId);
		orgAttrDao.update(bean);
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=false , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public void disattivaOrganizzazione(Long idOrganizzazione) throws Exception
	{
		PaesaggioOrganizzazioneAttributiDTO bean = orgAttrDao.findBeanAttivazioneValido(idOrganizzazione);
		int applicationId = commondao.getIdApplicazione(props.getCodiceApplicazioneProfile());
		if(bean == null)
		{
			logger.info("Organizzazione con id {} non attiva per l'applicazione {}", idOrganizzazione, applicationId);
			throw new Exception("Organizzazione con id " + idOrganizzazione + " non attiva per l'applicazione " + applicationId);
		}
		bean.setDataTermine(new java.util.Date());
		orgAttrDao.update(bean);
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public java.util.List<PaesaggioOrganizzazioneDTO> searchPaesaggioOrganizzazioneNoPag(PaesaggioOrganizzazioniSearchBean search) throws Exception
	{
		logger.info("Chiamo serch organizzazioni non paginata");
		return dao.searchOrganizzazioniNoPag(search);
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public java.util.List<EnteBean> listEntiOrganizzazione(Long idOrganizzazione) throws Exception
	{
		return listEntiOrganizzazione(idOrganizzazione, false);
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public java.util.List<EnteBean> listEntiOrganizzazione(Long idOrganizzazione, boolean isSbap) throws Exception
	{
		logger.info("Chiamo serch organizzazioni non paginata");
		java.util.List<EnteBean> enti = dao.findEntiByOrganizzazione(idOrganizzazione);
		if(isSbap)
		{
			java.util.List<EnteBean> tmp = dao.findComuniOfProvinciaOrganizzazione(idOrganizzazione);
			for(EnteBean comune: tmp)
			{
				if(!enti.stream().map(EnteBean::getIdEnte).anyMatch(p -> comune.getIdEnte().equals(p)))
					enti.add(comune);
			}
		}
		return enti;
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public PaginatedList<PaesaggioEmailBean> listPaesaggioEmail(PesaggioEmailSearchBean search) throws Exception
	{
		search.setCodiceApplicazione(props.getCodiceApplicazioneProfile());
		return dao.searchPaesaggioEmail(search);
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=false , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public PaesaggioEmailBean savePaesaggioEmail(PaesaggioEmailDTO bean) throws Exception
	{
		bean.setCodiceApplicazione(props.getCodiceApplicazioneProfile());
		Long id = dao.insertPaesaggioEmail(bean);
		PesaggioEmailSearchBean search = new PesaggioEmailSearchBean();
		search.setCodiceApplicazione(props.getCodiceApplicazione());
		search.setId(id);
		PaesaggioEmailBean r = this.listPaesaggioEmail(search).getList().get(0);
		return r;
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=false , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public PaesaggioEmailBean updatePaesaggioEmail(PaesaggioEmailDTO bean) throws Exception
	{
		bean.setCodiceApplicazione(props.getCodiceApplicazioneProfile());
		dao.updatePaesaggioEmail(bean);
		PesaggioEmailSearchBean search = new PesaggioEmailSearchBean();
		search.setCodiceApplicazione(props.getCodiceApplicazione());
		search.setId(bean.getId());
		PaesaggioEmailBean r = this.listPaesaggioEmail(search).getList().get(0);
		return r;
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=false , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public void deletePaesaggioEmail(Long idPaesaggioEmail) throws Exception
	{
		dao.deletePaesaggioEmail(idPaesaggioEmail);
	}
	
	private void checkBeanAttivazioneValido(PaesaggioOrganizzazioneAttributiDTO bean) throws Exception
	{
		if(orgAttrDao.organizzazioneValida(bean.getPaesaggioOrganizzazioneId()))
		{
			logger.error("Errore, l'organizzazione con id {} è giò abilitata all'applicazione", bean.getPaesaggioOrganizzazioneId());
			throw new Exception("Errore, l'organizzazione con id "+ bean.getPaesaggioOrganizzazioneId() +" è giò abilitata all'applicazione");
		}
		
		if(bean.getDataTermine() == null || bean.getDataTermine().getTime() <= new java.util.Date().getTime())
		{
			logger.error("Errore, data scadenza autorizzazione non presente o non valida");
			throw new Exception("Errore, data scadenza autorizzazione non presente o non valida");
		}
	}
	
	private void createGroup(PaesaggioOrganizzazioneBean bean) throws Exception
	{
		String jwt =  LogUtil.getAuthorization();
		try
		{
			PMApplication app = profileManager.findAppByCode(jwt, props.getCodiceApplicazioneProfile()).getPayload();
			PMGroupsRequestBean pmbean = new PMGroupsRequestBean();
			String codiceGruppoBase = bean.getTipoOrganizzazione().toString() + "_" + bean.getId();
			pmbean.setIdApplicazione(app.getId());
			//save admin
			pmbean.setCodiceGruppo(codiceGruppoBase + "_A");
			pmbean.setNomeGruppo(bean.getDenominazione() + " (Amm)");
			profileManager.saveGroup(jwt, pmbean);
			//save dirigenti
			pmbean.setCodiceGruppo(codiceGruppoBase + "_D");
			pmbean.setNomeGruppo(bean.getDenominazione() + " (Dir)");
			profileManager.saveGroup(jwt, pmbean);
			//save funzionari
			pmbean.setCodiceGruppo(codiceGruppoBase + "_F");
			pmbean.setNomeGruppo(bean.getDenominazione() + " (Funz)");
			profileManager.saveGroup(jwt, pmbean);
		}
		catch(FeignException e)
		{
			if(e.getMessage().startsWith("status 401"))
			{
				logger.error("Errore: utente non responsabile di applicazione su applicativo Profile Manager");
				throw new CustomOperationToAdviceException("Errore: non possiedi i permessi per inserire/aggiornare gruppi sul Profile Manager. Contattare un responsabile di applicazione per risolvere il problema.");
			}
			else throw e;
		}
	}
	
	/**
	 * Metodo che verifica l'allineamento del nome gruppo su PM, se questo non è allineato
	 * lo aggiorna e torna true, altrimenti torna false
	 * 
	 * @param codice
	 * @param role - Ruoli.ADMIN non è ammesso, se passato l'applicativo torna false
	 * @param bean
	 * @return true se aggiorna il gruppo sul PM, false altrimenti
	 * 
	 * @throws Exception
	 */
	private boolean updateGruppo(String codiceGruppo, Ruoli role, PaesaggioOrganizzazioneBean bean) throws Exception
	{
		String descrizione = "";
		switch(role)
		{
		case AMMINISTRATORE: descrizione = (" (Adm)"); break;
		case DIRIGENTE:		 descrizione = (" (Dir)"); break;
		case FUNZIONARIO:	 descrizione = (" (Funz)"); break;
		default: return false;
		}
		String jwt = LogUtil.getAuthorization();
		boolean ok = false;
		try
		{
			List<PM_GruppiRuoli> groups = profileManager.findGruppiDetails(jwt, props.getCodiceApplicazioneProfile(), codiceGruppo).getPayload();
			if(groups == null || groups.isEmpty())
			{
				//Volendo posso intercettare questa casistica per rigenerare il gruppo in quanto, probabilmente, cancellato a mano per errore
				logger.error("Errore: non è stato trovato nessun gruppo '{}' sul Profile Manager", codiceGruppo);
				throw new Exception("Errore: non è stato trovato nessun gruppo '"+codiceGruppo+"' sul Profile Manager");
			}
			PM_GruppiRuoli g = groups.get(0);
			String idApplicazione = g.getIdApplicazione();
			String idGruppo = g.getId();
			String nomeGruppo = bean.getDenominazione();
			if(!g.getNomeGruppo().equals(nomeGruppo))
			{			
				PMGroupsRequestBean pmbean= new PMGroupsRequestBean();
				pmbean.setId(idGruppo);
				pmbean.setCodiceGruppo(g.getCodiceGruppo());
				pmbean.setIdApplicazione(idApplicazione);
				pmbean.setNomeGruppo(nomeGruppo + descrizione);
				pmbean.setDescrizioneGruppo(nomeGruppo + descrizione + " " + bean.getTipoOrganizzazione());
				profileManager.updateGroup(jwt, pmbean);
				ok = true;
			}	
		}
		catch(FeignException e)
		{
			if(e.getMessage().startsWith("status 401"))
			{
				logger.error("Errore: utente non responsabile di applicazione su applicativo Profile Manager");
				throw new CustomOperationToAdviceException("Errore: non possiedi i permessi per inserire/aggiornare gruppi sul Profile Manager. Contattare un responsabile di applicazione per risolvere il problema.");
			}
			else throw e;
		}
		
		return ok;
	}
}

