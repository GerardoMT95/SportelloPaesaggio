package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.cds.service.IConferenzaDeiServiziService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.ApplicationProperties;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.DettaglioCorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.TipologicaAllegatoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AllowedMimeType;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.SezioneAllegati;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoCorrispondenza;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoRelazione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoDestinatario;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoRicevuta;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiTipoContenutoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatoCorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ConfigurazioniEnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.CorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinatarioDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.FascicoloCorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.IntegrazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ReferentiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.RelazioneEnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.RicevutaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TemplateEmailDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoContenutoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoProcedimentoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.AllegatiRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.AllegatoCorrispondenzaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.CorrispondenzaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.DestinatarioRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.FascicoloCorrispondenzaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ReferentiRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.RelazioneEnteRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TipoContenutoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.CorrispondenzaSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.DestinatarioSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.RicevutaSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.TemplateEmailSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.ITipoProcedimentoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl.ConfigurazioniEnteService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl.RelazioneEnteService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl.RicevutaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.PaesaggioOrganizzazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.repository.CommonRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.SendPlanToDiogeneScheduler;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.bean.FascicoloStatoBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.AllegatoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.ComunicazioniService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.FascicoloService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.IntegrazioneDocumentale;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.impl.ProtocolloCallService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.jasper.dto.JasperInfoEmailAllegatoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.jasper.dto.JasperInfoEmailDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.jasper.dto.JasperInfoEmailDestinatarioDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.GeneratedFileBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.ProtocolNumberType;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.webmail.service.WebmailService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.PlaceHolder;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.SezioneIstruttoria;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.dto.TemplateEmailDestinatariDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.service.IResolveTemplateService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.service.ITemplateMailService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.MockMultipartFile;
import it.eng.tz.puglia.batch.queue.IQueueService;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.error.exception.CustomCmisException;
import it.eng.tz.puglia.error.exception.CustomOperationException;
import it.eng.tz.puglia.error.exception.CustomValidationException;
import it.eng.tz.puglia.service.http.IHttpClientService;
import it.eng.tz.puglia.service.http.bean.CmsDownloadResponseBean;
import it.eng.tz.puglia.service.http.bean.ProtocolloResponseBean;
import it.eng.tz.puglia.service.http.bean.SendMailRequestBean;
import it.eng.tz.puglia.service.http.bean.SendMailResponseBean;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.regione.puglia.webmail.be.dto.ConfigurazioneCasellaPostaleDto;
import it.eng.tz.regione.puglia.webmail.be.dto.InvioMailDto;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
public class ComunicazioniServiceImpl implements ComunicazioniService
{
	@Autowired
	@Qualifier("casellaMittenteApplicazione")
	private ConfigurazioneCasellaPostaleDto ccpd;
	@Autowired private ConfigurazioniEnteService confEnteService;
	@Autowired private CorrispondenzaRepository corrispondenzaRepository;
	@Autowired private DestinatarioRepository destinatarioRepository;
	@Autowired private FascicoloCorrispondenzaRepository fascicoloCorrispondenzaRepository;
	@Autowired private AllegatoCorrispondenzaRepository allegatoCorrispondenzaRepository;
	@Autowired private AllegatiRepository allegatiRepository;
	@Autowired private TipoContenutoRepository tipoContenutoRepository;
 	@Autowired private ProtocolloCallService protocolloCallService;
 	@Autowired private RelazioneEnteService relService;
 	@Autowired private RelazioneEnteRepository relRepo;
 	@Autowired private PraticaRepository praticaRepo;
 	@Autowired private RicevutaService ricevutaService;
 	@Autowired private CommonRepository commonRepository;
 	@Autowired private ITemplateMailService templateService;
 	@Autowired private FascicoloService fascicoloSvc;
 	@Autowired private IntegrazioneDocumentale integrazioneService;
 	@Autowired private AllegatoService allegatoService;
	@Autowired private WebmailService webMaiService;
 	@Autowired private UserUtil userUtil;
	@Autowired private ITipoProcedimentoService tipoService;
 	@Autowired ApplicationProperties props;
 	@Autowired private IQueueService queueService;
 	@Autowired private IHttpClientService clientService;
 	@Autowired private ReferentiRepository referentiRepository;
 	@Autowired private IConferenzaDeiServiziService cdsService;
 	
 	@Autowired private IResolveTemplateService resolvePlaceholderSvc;
 	@Value("${prefix.oggetto.comunicazione}")
 	private String prefixOggetto;
 	
 	@Value("${url.pec}")
	private String urlPec;
	@Value("${mail.key}")
	private String mailKey;
 	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final static SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, propagation=Propagation.REQUIRED, readOnly=true, timeout=60000, rollbackFor=Exception.class)
	public DettaglioCorrispondenzaDTO find(Long idCorrispondenza) throws Exception
	{
		DettaglioCorrispondenzaDTO ret = corrispondenzaRepository.getDettaglioCorrispondenza(idCorrispondenza);
		RicevutaSearch searchR=new RicevutaSearch();
		searchR.setIdCorrispondenza(idCorrispondenza);
		if(!ret.getCorrispondenza().getBozza() && ret.getDestinatari()!=null) {
			//prelievo delle ricevute..
			this.addStatoRicezione(ret.getDestinatari(), idCorrispondenza);
			ret.getDestinatari().forEach(dest->{
				searchR.setIdDestinatario(dest.getId());
				try {
					List<RicevutaDTO> listaRicevute = ricevutaService.search(searchR).getList();
					dest.setRicevute(listaRicevute);
				} catch (Exception e) {
					logger.error("Errore nella search delle ricevute della corrispondenza con id "+idCorrispondenza,e);
				}
			});
		}
		return ret;

	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, propagation=Propagation.REQUIRED, readOnly=false, timeout=60000, rollbackFor=Exception.class)
	public DettaglioCorrispondenzaDTO create(UUID idPratica) throws Exception
	{
		final PraticaDTO pratica = this.praticaRepo.find(idPratica);
		return this.create(idPratica, Integer.valueOf(pratica.getEnteDelegato()), userUtil.getGruppo(), null, true);
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, propagation=Propagation.REQUIRED, readOnly=false, timeout=60000, rollbackFor=Exception.class)
	public DettaglioCorrispondenzaDTO create(UUID idPratica,Integer idOrganizzazione,Gruppi gruppo,TemplateEmailDestinatariDto template,boolean isComunicazione) throws Exception
	{
		return createCorrispondenza(idPratica, idOrganizzazione, gruppo, template, isComunicazione, LogUtil.getUser(), userUtil.getRuolo().toString());
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, propagation=Propagation.REQUIRED, readOnly=false, timeout=60000, rollbackFor=Exception.class)
	public DettaglioCorrispondenzaDTO create(UUID idPratica, Integer idOrganizzazione, Gruppi gruppo, TemplateEmailDestinatariDto template, boolean isComunicazione, String denominazioneMittente, String ruoloMittente) throws Exception {
		return createCorrispondenza(idPratica, idOrganizzazione, gruppo, template, isComunicazione, denominazioneMittente, ruoloMittente);
	}

	private DettaglioCorrispondenzaDTO createCorrispondenza(UUID idPratica, Integer idOrganizzazione, Gruppi gruppo,
			TemplateEmailDestinatariDto template, boolean isComunicazione, String denominazioneMittente, String ruoloMittente) {
		DettaglioCorrispondenzaDTO result = null;
		CorrispondenzaDTO corrispondenza = new CorrispondenzaDTO();
		FascicoloCorrispondenzaDTO entity = new  FascicoloCorrispondenzaDTO();
		
		corrispondenza.setBozza(true);
		corrispondenza.setRuolo(ruoloMittente);
		corrispondenza.setMittenteEnte(gruppo.name());
		corrispondenza.setMittenteDenominazione(denominazioneMittente);
		corrispondenza.setMittenteUsername(denominazioneMittente);
		//prende il mittente dalla configurazione di default o dalla tabella configurazionEnte
		PraticaDTO pratica = null;
		if(idOrganizzazione == null) {
			pratica = this.praticaRepo.find(idPratica);
			idOrganizzazione = Integer.valueOf(pratica.getEnteDelegato());
		}
		corrispondenza.setMittenteEmail(this.getMittente(idOrganizzazione));
		corrispondenza.setOggetto("");
		corrispondenza.setComunicazione(isComunicazione);
		corrispondenza.setIdOrganizzazioneOwner(idOrganizzazione);
		corrispondenza.setTipoOrganizzazioneOwner(gruppo.name());
		corrispondenza.setId(corrispondenzaRepository.insert(corrispondenza));
		if(template!=null && template.getTemplate()!=null) {
			corrispondenza.setCodiceTemplate(template.getTemplate().getCodice());
			corrispondenza.setRiservata(template.getTemplate().isRiservata());
		}
		entity.setIdPratica(idPratica);
		entity.setIdCorrispondenza(corrispondenza.getId());
		fascicoloCorrispondenzaRepository.insert(entity);
		
		result = new DettaglioCorrispondenzaDTO(corrispondenza);
		return result;
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, readOnly=false, timeout=60000, rollbackFor=Exception.class, propagation=Propagation.MANDATORY)
	public DettaglioCorrispondenzaDTO createAndSendComunication(DettaglioCorrispondenzaDTO comunicazione, UUID idPratica, Long idOrganizzazione, boolean isComunicazione) throws Exception
	{
		PaesaggioOrganizzazioneDTO organizzazione = commonRepository.findPaesaggioOrganizzazione(idOrganizzazione, false);
		FascicoloCorrispondenzaDTO fascicoloCorrispondenza = new  FascicoloCorrispondenzaDTO();
		CorrispondenzaDTO corrispondenza = comunicazione.getCorrispondenza();
		corrispondenza.setBozza(false);
		PraticaDTO pratica = null;
		if(idOrganizzazione == null) {
			pratica = this.praticaRepo.find(idPratica);
			idOrganizzazione = Long.valueOf(pratica.getEnteDelegato());
		}
		corrispondenza.setMittenteEmail(getMittente(idOrganizzazione.intValue()));
		corrispondenza.setMittenteDenominazione(organizzazione.getDenominazione());
		corrispondenza.setMittenteEnte(organizzazione.getDenominazione());
		corrispondenza.setComunicazione(isComunicazione);
		corrispondenza.setDataInvio(new Timestamp(new Date().getTime()));
		corrispondenza.setIdOrganizzazioneOwner(idOrganizzazione.intValue());
		if(corrispondenza.getMittenteUsername() == null)
			corrispondenza.setMittenteUsername("MESSAGGIO AUTOMATICO");
		corrispondenza.setId(corrispondenzaRepository.insert(corrispondenza));
		
		if(comunicazione.getDestinatari() != null)
		{
			for(DestinatarioDTO destinatario: comunicazione.getDestinatari())
			{
				DestinatarioDTO tmp = new DestinatarioDTO(destinatario);
				tmp.setIdCorrispondenza(corrispondenza.getId());
				tmp.setId(destinatarioRepository.insert(tmp));
			}
		}
		
		fascicoloCorrispondenza.setIdPratica(idPratica);
		fascicoloCorrispondenza.setIdCorrispondenza(corrispondenza.getId());
		sendComunication(comunicazione, idPratica);
		return comunicazione;
	}
	
	
	/**
	 * Metodo per ricavare l'indirizzo email con cui inviare la comunicazione:
	 * configurazione_ente o predefinita nel file json nella cartella resources/configCasellaPostale 
	 * @author G.Lavermicocca
	 * @date 3 ott 2020
	 */
	private String getMittente(Integer idOrganizzazione) {
		logger.info("ComunicazioniServiceImp - getMittente");
		String mittenteEmail = null;
		try {
			ConfigurazioniEnteDTO confEnteDto= new ConfigurazioniEnteDTO();
			try {
				confEnteDto= this.confEnteService.find(idOrganizzazione);
			}catch(DataAccessException e) {
				logger.info("nessuna configurazione casella custom per organizzazione !!!");
			}
			if(confEnteDto.getPecIndirizzo()!=null) {
				mittenteEmail=confEnteDto.getPecIndirizzo();
			}else {
				mittenteEmail= this.ccpd.getCasellaPostale().getIndirizzoMail();
			}
			logger.info("ComunicazioniServiceImp - getMittente:"+mittenteEmail);	
		} catch (Exception e) {
			logger.info("Error in getMittente:",e);
		}
		return mittenteEmail;
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, propagation=Propagation.REQUIRED, readOnly=false, timeout=60000, rollbackFor=Exception.class)
	public DettaglioCorrispondenzaDTO saveComunication(DettaglioCorrispondenzaDTO corrispondenza, UUID idPratica) throws Exception
	{
		return execSaveComunication(corrispondenza, idPratica, null, LogUtil.getUser(), userUtil.getNomeGruppo(), userUtil.getRuolo().toString());
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, propagation=Propagation.REQUIRED, readOnly=false, timeout=60000, rollbackFor=Exception.class)
	public DettaglioCorrispondenzaDTO saveComunication(DettaglioCorrispondenzaDTO corrispondenza, Integer idOrganizzazione) throws Exception
	{
		return execSaveComunication(corrispondenza, null, idOrganizzazione, LogUtil.getUser(), userUtil.getNomeGruppo(), userUtil.getRuolo().toString());
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, propagation=Propagation.REQUIRED, readOnly=false, timeout=60000, rollbackFor=Exception.class)
	public DettaglioCorrispondenzaDTO saveComunication(DettaglioCorrispondenzaDTO corrispondenza, UUID idPratica, String username, String gruppo, String ruolo) throws Exception{
		return execSaveComunication(corrispondenza, idPratica, null, username, gruppo, ruolo);
	}

	
	

	private DettaglioCorrispondenzaDTO execSaveComunication(DettaglioCorrispondenzaDTO corrispondenza, UUID idPratica, Integer idOrganizzazione, String username, 
			String gruppo, String ruolo)
			throws Exception {
		Long idCorrispondenza =  corrispondenza.getCorrispondenza().getId();
		if(idCorrispondenza != null && !corrispondenzaRepository.isInBozza(idCorrispondenza))
		{
			logger.error("Non puoi effettuare l'update di una corrispondenza che non si trova più in bozza!");
			throw new Exception("Non puoi effettuare l'update di una corrispondenza che non si trova più in bozza!");
		}
		corrispondenza.getCorrispondenza().setRuolo(ruolo);
		corrispondenza.getCorrispondenza().setMittenteEnte(gruppo);
		corrispondenza.getCorrispondenza().setMittenteDenominazione(username);
		corrispondenza.getCorrispondenza().setMittenteUsername(username);
		//in genere vengono popolati con la create....
		if(StringUtil.isEmpty(corrispondenza.getCorrispondenza().getMittenteEnte())) 
			corrispondenza.getCorrispondenza().setMittenteEnte(gruppo);	
		if(StringUtil.isEmpty(corrispondenza.getCorrispondenza().getMittenteEmail())) {
			if(idOrganizzazione == null) {
				final PraticaDTO pratica = this.praticaRepo.find(idPratica);
				corrispondenza.getCorrispondenza().setMittenteEmail(getMittente(Integer.valueOf(pratica.getEnteDelegato())));	
			} else {
				corrispondenza.getCorrispondenza().setMittenteEmail(getMittente(idOrganizzazione));	
			}
		}
		if(StringUtil.isEmpty(corrispondenza.getCorrispondenza().getMittenteDenominazione()))
			corrispondenza.getCorrispondenza().setMittenteDenominazione(LogUtil.getUser());	
		if(StringUtil.isEmpty(corrispondenza.getCorrispondenza().getMittenteUsername()))
			corrispondenza.getCorrispondenza().setMittenteUsername(LogUtil.getUser());
		if(StringUtil.isEmpty(corrispondenza.getCorrispondenza().getRuolo()))
			corrispondenza.getCorrispondenza().setRuolo(userUtil.getRuolo().toString());	
			
		
		if(idCorrispondenza != null)
		{
			Integer n = corrispondenzaRepository.update(corrispondenza.getCorrispondenza());
			if(n != 1) 
			{
				logger.error("Errore nell'update della corrispondenza. Mi aspettavo di aggiornare un record e ne ho aggiornati {}", n);
				throw new Exception("Errore nell'update della corrispondenza. Mi aspettavo di aggiornare un record e ne ho aggiornati " + n);
			}
			destinatarioRepository.delete(idCorrispondenza);
		}
		else
		{
			idCorrispondenza = corrispondenzaRepository.insert(corrispondenza.getCorrispondenza());
			if(idPratica!=null) {
				try {
					fascicoloCorrispondenzaRepository.insert(idPratica,idCorrispondenza);
				}catch(Exception e) {
					logger.error("Errore nell'update fascicolo_corrispondenza idPratica {} idCorrispondenza {} , l'elaborazione continua.",idPratica,idCorrispondenza,e);
				}
			}
			corrispondenza.getCorrispondenza().setId(idCorrispondenza);
		}
		
		if(corrispondenza.getDestinatari() != null)
		{
			for(DestinatarioDTO destinatario: corrispondenza.getDestinatari())
			{
				DestinatarioDTO tmp = new DestinatarioDTO(destinatario);
				tmp.setIdCorrispondenza(idCorrispondenza);
				tmp.setId(destinatarioRepository.insert(tmp));
			}
		}
		
		return corrispondenza;
	}
	
	private RicevutaDTO ricevutaPiuSignificativa(List<RicevutaDTO> listaRicevute) {
		logger.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		List<RicevutaDTO> listaPriorita1 = new ArrayList<RicevutaDTO>();
		List<RicevutaDTO> listaPriorita2 = new ArrayList<RicevutaDTO>();
		List<RicevutaDTO> listaPriorita3 = new ArrayList<RicevutaDTO>();
		List<RicevutaDTO> listaPriorita4 = new ArrayList<RicevutaDTO>();
		List<RicevutaDTO> listaPriorita5 = new ArrayList<RicevutaDTO>();
		for (RicevutaDTO ricevuta : listaRicevute) {
				 if (ricevuta.getTipoRicevuta()==TipoRicevuta.AVVENUTA_CONSEGNA)
				listaPriorita1.add(ricevuta);
			else if (ricevuta.getTipoRicevuta()==TipoRicevuta.PREAVVISO_ERRORE_CONSEGNA || 
					 ricevuta.getTipoRicevuta()==TipoRicevuta.ERRORE_CONSEGNA ||
					 ricevuta.getTipoRicevuta()==TipoRicevuta.RILEVAZIONE_VIRUS)
				listaPriorita2.add(ricevuta);
			else if (ricevuta.getTipoRicevuta()==TipoRicevuta.NON_ACCETTAZIONE)
				listaPriorita3.add(ricevuta);
			else if (ricevuta.getTipoRicevuta()==TipoRicevuta.PRESA_IN_CARICO || 
					 ricevuta.getTipoRicevuta()==TipoRicevuta.POSTA_CERTIFICATA)
				listaPriorita4.add(ricevuta);
			else if (ricevuta.getTipoRicevuta()==TipoRicevuta.ACCETTAZIONE)
				listaPriorita5.add(ricevuta);
		}
		List<RicevutaDTO> listaPriorita = new ArrayList<RicevutaDTO>();
			 if (!listaPriorita1.isEmpty()) { listaPriorita = listaPriorita1; }
		else if (!listaPriorita2.isEmpty()) { listaPriorita = listaPriorita2; }
		else if (!listaPriorita3.isEmpty()) { listaPriorita = listaPriorita3; }
		else if (!listaPriorita4.isEmpty()) { listaPriorita = listaPriorita4; }
		else if (!listaPriorita5.isEmpty()) { listaPriorita = listaPriorita5; }
		
		Collections.sort(listaPriorita, new Comparator<RicevutaDTO>() {	// prendo quella cronologicamente più recente
			public int compare(RicevutaDTO o1, RicevutaDTO o2) {
				return o1.getData().compareTo(o2.getData());
			}
		});
		return listaPriorita.isEmpty() ? new RicevutaDTO() : listaPriorita.get(0);
	}
	
	private void addStatoRicezione(List<DestinatarioDTO> destinatari,Long idCorrispondenza) {
		RicevutaSearch 	   searchR = new     RicevutaSearch();  searchR.setIdCorrispondenza(idCorrispondenza);
		try {
			// modifico il colore del pallino in base allo stato della ricevuta più significativa riferita a quel destinatario
			// in teoria il campo "stato" della tabella "destinatario" dovrebbe essere cambiato in modo asincrono dal KafkaConsumer
			for (DestinatarioDTO destinatario : destinatari) {
				searchR.setIdDestinatario(destinatario.getId());
				List<RicevutaDTO> listaRicevute = ricevutaService.search(searchR).getList();
				RicevutaDTO ricevuta = this.ricevutaPiuSignificativa(listaRicevute);
					 if (ricevuta.getTipoRicevuta()==TipoRicevuta.AVVENUTA_CONSEGNA) 			{ destinatario.setStato(StatoCorrispondenza.ESITO_POSITIVO); }
				else if (ricevuta.getTipoRicevuta()==TipoRicevuta.PREAVVISO_ERRORE_CONSEGNA || 
						 ricevuta.getTipoRicevuta()==TipoRicevuta.ERRORE_CONSEGNA ||
						 ricevuta.getTipoRicevuta()==TipoRicevuta.RILEVAZIONE_VIRUS ||
						 ricevuta.getTipoRicevuta()==TipoRicevuta.NON_ACCETTAZIONE)				{ destinatario.setStato(StatoCorrispondenza.ESITO_CON_ERRORE); }
				else if (ricevuta.getTipoRicevuta()==TipoRicevuta.PRESA_IN_CARICO || 
						 ricevuta.getTipoRicevuta()==TipoRicevuta.POSTA_CERTIFICATA ||
						 ricevuta.getTipoRicevuta()==TipoRicevuta.ACCETTAZIONE)		 			{ destinatario.setStato(StatoCorrispondenza.INVIATA); }
			}
			
		} catch (Exception e) {
			this.logger.error("errore nel calcolo dello stato di ricezione della mail...",e);
		}
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, propagation=Propagation.REQUIRED, readOnly=true)
	public PaginatedList<DettaglioCorrispondenzaDTO> search(CorrispondenzaSearch filters) throws Exception
	{
		PaginatedList<DettaglioCorrispondenzaDTO> result = new PaginatedList<DettaglioCorrispondenzaDTO>();
		String visibileA=userUtil.getGruppo().toString();
		filters.setVisibilita(visibileA);
		if(userUtil.getGruppo().equals(Gruppi.RICHIEDENTI)) {
			filters.setRiservata("false");
		}
		PaginatedList<CorrispondenzaDTO> corrispondenze = corrispondenzaRepository.search(filters);
		result.setCount(corrispondenze.getCount());
		List<CorrispondenzaDTO> list = corrispondenze.getList();
		List<DettaglioCorrispondenzaDTO> resultList = new ArrayList<DettaglioCorrispondenzaDTO>();
		if(list != null && !list.isEmpty())
		{
			for(CorrispondenzaDTO corrispondenza: list)
			{
				CorrispondenzaDTO tmp = new CorrispondenzaDTO(corrispondenza);
				//cerco i destinatari e allegati
				DestinatarioSearch searchDestinatario = new DestinatarioSearch();
				searchDestinatario.setIdCorrispondenza(Long.toString(tmp.getId()));
				List<DestinatarioDTO> destinatari = destinatarioRepository.searchWithoutPaging(searchDestinatario);
				this.addStatoRicezione(destinatari, corrispondenza.getId());
				List<AllegatiDTO> allegati = allegatiRepository.findAllegatiCorrispondenza(corrispondenza.getId());
				//setto il dettaglio
				DettaglioCorrispondenzaDTO d = new DettaglioCorrispondenzaDTO(tmp);
				d.setDestinatari(destinatari);
				if(allegati != null && !allegati.isEmpty())
				{
					//TODO
					//d.setAllegati(allegati.stream().map(AllegatiTipoContenutoDTO::new).collect(Collectors.toList()));
					//d.setAllegatiInfo(allegati.stream().map(AllegatiDTO::new).collect(Collectors.toList()));
				}
				resultList.add(d);
			}
		}
		result.setList(resultList);
		return result;
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, propagation=Propagation.REQUIRED, readOnly=false, timeout=60000, rollbackFor=Exception.class)
	public Integer eraseComunication(Long idCorrispondenza) throws Exception
	{
		CorrispondenzaDTO dto = new CorrispondenzaDTO();
		dto.setId(idCorrispondenza);
		List<UUID> ids = corrispondenzaRepository.getIdAllegati(idCorrispondenza);
		for(UUID id: ids)
			allegatoService.deleteAllegato(id.toString());
		return corrispondenzaRepository.delete(dto);
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, propagation=Propagation.REQUIRED, readOnly=false, timeout=60000, rollbackFor=Exception.class)
	public AllegatiDTO addAttachmentAndGetDetail(UUID idPratica, Long idCorrispondenza, MultipartFile attachment) throws Exception
	{
		return doAddAttachment(idPratica, idCorrispondenza, attachment);
	}

	private AllegatiDTO doAddAttachment(UUID idPratica, Long idCorrispondenza, MultipartFile attachment)
			throws Exception {
		AllegatiDTO dto = allegatoService.uploadAllegatiComunicazione(idPratica, attachment);
		AllegatoCorrispondenzaDTO ac = new AllegatoCorrispondenzaDTO();
		ac.setIdAllegato(dto.getId());
		ac.setIdCorrispondenza(idCorrispondenza);
		allegatoCorrispondenzaRepository.insert(ac);
		return dto;
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, propagation=Propagation.REQUIRED, readOnly=false, timeout=60000, rollbackFor=Exception.class)
	public TipologicaAllegatoDTO addAttachment(UUID idPratica, Long idCorrispondenza, MultipartFile attachment) throws Exception
	{
		AllegatiDTO dto=doAddAttachment(idPratica, idCorrispondenza, attachment);
		TipologicaAllegatoDTO result = new TipologicaAllegatoDTO();
		result.setCodice(dto.getId());
		result.setLabel(dto.getNomeFile());
		return result;
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, propagation=Propagation.REQUIRED, readOnly=false, timeout=60000, rollbackFor=Exception.class)
	public Integer removeAttachment(UUID idPratica, UUID idAllegato) throws Exception
	{
		AllegatoCorrispondenzaDTO ac = new AllegatoCorrispondenzaDTO();
		ac.setIdAllegato(idAllegato);
		//allegatoService.deleteAllegato(idPratica.toString(), idAllegato.toString());
		return allegatoCorrispondenzaRepository.delete(ac);
	}

	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, propagation=Propagation.REQUIRED, readOnly=false, timeout=60000, rollbackFor=Exception.class)
	public void sendComunication(Long idCorrispondenza, UUID idPratica) throws Exception
	{
		DettaglioCorrispondenzaDTO dettCorr = this.corrispondenzaRepository.getDettaglioCorrispondenza(idCorrispondenza);
		sendComunication(dettCorr, idPratica, null, null,null);
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, propagation=Propagation.REQUIRED, readOnly=false, timeout=60000, rollbackFor=Exception.class)
	public void sendComunication(Long idCorrispondenza,UUID idPratica,boolean withProto) throws Exception
	{
		final Date dataInvio=new Date();
		DettaglioCorrispondenzaDTO dettCorr = this.corrispondenzaRepository.getDettaglioCorrispondenza(idCorrispondenza);
		List<DestinatarioDTO> destinatariSportello=new ArrayList<DestinatarioDTO>();
		PraticaDTO pratica = praticaRepo.find(idPratica);
		if(dettCorr!=null && dettCorr.getCorrispondenza()!=null && 
				StringUtil.isEmpty(dettCorr.getCorrispondenza().getCodiceTemplate())) {
			//nel caso di corrispondenza senza un template, verrà aggiunto il prefisso Fascicolo APPPTR-NN-AAAA nell'oggetto
			String prefissoRisolto = resolvePlaceholderSvc.risolviTesto(
					String.join(",",
							PlaceHolder.CODICE_FASCICOLO.name(),
							PlaceHolder.CODICE_PRATICA.name()
							), prefixOggetto, pratica);
			final String newOggetto=StringUtil.concateneString(
					prefissoRisolto,
					" ",
					dettCorr.getCorrispondenza().getOggetto()
					);
			dettCorr.getCorrispondenza().setOggetto(newOggetto);
			corrispondenzaRepository.updateOggetto(dettCorr.getCorrispondenza().getId(), newOggetto);
		} 
		if(dettCorr.getCorrispondenza().getCodiceTemplate() != null &&
			dettCorr.getCorrispondenza().getCodiceTemplate().equals(SezioneIstruttoria.RICH_INTEGRAZIONE.name())){
					//aggiungo richiedente e user owner della pratica 
				this.fascicoloSvc.addDestinatariPratica(destinatariSportello, pratica, false, false);
		}
		if(withProto) {
			//genero file pdf comunicazione
			File fnoProto = this.generaPDFinformazioniEmail(dettCorr, dettCorr.getAllegatiInfo(), dataInvio, "",pratica);
			MockMultipartFile attachment=new MockMultipartFile("preProtocollazione.pdf",AllowedMimeType.PDF.getMimeType(), fnoProto);
			AllegatiDTO dto = allegatoService.uploadAllegatiComunicazione(idPratica, attachment,"preProtocollazione");
			//chiamo la protocollazione sul file..
			GeneratedFileBean beanFile=new GeneratedFileBean();
			beanFile.setContent(Files.readAllBytes(fnoProto.toPath()));
			beanFile.setName(fnoProto.getName());
			//Il processo di recupero dei destinatari va pulito
			List<String> listaDestinatari = dettCorr.getDestinatari().stream().map(DestinatarioDTO::getEmail).collect(Collectors.toList());
			listaDestinatari.addAll(destinatariSportello.stream().map(DestinatarioDTO::getEmail).collect(Collectors.toList()));
			List<String> listaDenominazioneDestinatari = dettCorr.getDestinatari().stream().map(DestinatarioDTO::getNome).collect(Collectors.toList());
			listaDenominazioneDestinatari.addAll(destinatariSportello.stream().map(DestinatarioDTO::getNome).collect(Collectors.toList()));
			String oggetto=dettCorr.getCorrispondenza().getOggetto();
			ProtocolloResponseBean segnatura = 
					protocolloCallService.getNumeroProtocollo(beanFile, idPratica, ProtocolNumberType.U,false,
						listaDestinatari.toArray(new String[listaDestinatari.size()]),
						listaDenominazioneDestinatari.toArray(new String[listaDenominazioneDestinatari.size()]),oggetto);
			corrispondenzaRepository.updateProtocollo(dettCorr.getCorrispondenza().getId(), segnatura.toFormatoEsteso(),dataInvio);			
			if(fnoProto.exists()) {
				fnoProto.delete();
			}
			File fProto = this.generaPDFinformazioniEmail(dettCorr, dettCorr.getAllegatiInfo(), dataInvio, segnatura.toFormatoEsteso(),pratica);
			MockMultipartFile attachmentProto=new MockMultipartFile("ReportProtocollo.pdf",AllowedMimeType.PDF.getMimeType(), fProto);
			AllegatiDTO dtoProto = allegatoService.uploadAllegatiComunicazione(idPratica, attachmentProto);
			AllegatoCorrispondenzaDTO ac = new AllegatoCorrispondenzaDTO();
			ac.setIdAllegato(dtoProto.getId());
			ac.setIdCorrispondenza(idCorrispondenza);
			allegatoCorrispondenzaRepository.insert(ac);
			//aggancio il preproto al protocollato.
			dtoProto.setIdAllegatoPreProtocollazione(dto.getId());
			dtoProto.setProtocollo(segnatura.toString());
			allegatiRepository.update(dtoProto);
			if(fProto.exists()) {
				fProto.delete();
			}
		}
		//se è una richiesta di integrazione allora aggiungo tra i destinatari il richiedente 
		//e lo user che ha inserito la pratica
		if(dettCorr.getCorrispondenza().getCodiceTemplate() != null &&
		   dettCorr.getCorrispondenza().getCodiceTemplate().equals(SezioneIstruttoria.RICH_INTEGRAZIONE.name())){
			//aggiungo richiedente e user owner della pratica 
			List<DestinatarioDTO> destinatariSportelloDb=new ArrayList<DestinatarioDTO>();
			this.fascicoloSvc.addDestinatariPratica(destinatariSportelloDb, pratica, false, false);
			//allineo su DB i destinatari aggiunti...
			for(DestinatarioDTO destinatario: destinatariSportelloDb)
			{
				DestinatarioDTO tmp = new DestinatarioDTO(destinatario);
				tmp.setIdCorrispondenza(idCorrispondenza);
				tmp.setId(destinatarioRepository.insert(tmp));
				dettCorr.getDestinatari().add(tmp);
			}
			//creo il record di integrazione.
			IntegrazioneDTO entityIntegrazione=new IntegrazioneDTO();
			entityIntegrazione.setDescrizione("INTEGRAZIONE SU RICHIESTA DELL'ENTE DELEGATO NOTIFICATA IL "+sdf.format(dataInvio));
			entityIntegrazione.setNote("Dettagli nella comunicazione del "+sdf.format(dataInvio) +" (nel pannello comunicazioni della pagina di riepilogo della pratica)");
			entityIntegrazione.setPraticaId(idPratica);
			integrazioneService.save(entityIntegrazione,false);
			dettCorr.getCorrispondenza().setDataInvio(Timestamp.from(dataInvio.toInstant()));
		}
		sendComunication(dettCorr, idPratica, null, null, null);
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, propagation=Propagation.REQUIRED, readOnly=false, timeout=60000, rollbackFor=Exception.class)
	public void sendComunication(DettaglioCorrispondenzaDTO comunicazione, UUID idPratica) throws Exception
	{
		sendComunication(comunicazione, idPratica, null, null, null);
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, propagation=Propagation.REQUIRED, readOnly=false, timeout=60000, rollbackFor=Exception.class)
	public void sendComunication(DettaglioCorrispondenzaDTO comunicazione, Integer idOrganizzazione) throws Exception
	{
		sendComunication(comunicazione, null, idOrganizzazione, null, null);
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, propagation=Propagation.REQUIRED, readOnly=false, timeout=60000, rollbackFor=Exception.class)
	public void inserisciAllegatiCorrispondenzaDaRelazione(DettaglioCorrispondenzaDTO comunicazione,String tipoInvia,Long idRelazione) throws Exception {
		try {
			List<AllegatiDTO> listaAllegati= new ArrayList<AllegatiDTO>();
			RelazioneEnteDTO relazione= new RelazioneEnteDTO();
			relazione.setIdRelazioneEnte(idRelazione);
			relazione=relService.find(relazione);
			listaAllegati = this.relRepo.searchAllegati(idRelazione);
			listaAllegati.forEach(allegato->{
				AllegatoCorrispondenzaDTO allCorr=new AllegatoCorrispondenzaDTO();
				allCorr.setIdAllegato(allegato.getId());
				allCorr.setIdCorrispondenza(comunicazione.getCorrispondenza().getId());
				try{
					allegatoCorrispondenzaRepository.find(allCorr);
				}catch(DataAccessException e) {
					allegatoCorrispondenzaRepository.insert(allCorr);	
				}
			});
		} catch (Exception e) {
			logger.error("Errore nella inserisciALlegatiCorrispondenza per la relazione ente:",e);
			throw e;
		}
	}
	
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, propagation=Propagation.REQUIRED, readOnly=false, timeout=60000, rollbackFor=Exception.class)
	public void sendComunicationRelazione(DettaglioCorrispondenzaDTO comunicazione,UUID idPratica,String tipoInvia,Long idRelazione) {
		try {
			//List<AllegatiDTO> listaAllegati= new ArrayList<AllegatiDTO>();
			RelazioneEnteDTO relazione= new RelazioneEnteDTO();
			relazione.setIdRelazioneEnte(idRelazione);
			relazione=relService.find(relazione);
			if(tipoInvia.equals("WITH_PROT")) {
				this.sendComunication(comunicazione.getCorrispondenza().getId(),relazione.getIdPratica(),true);
			}else {
				this.sendComunication(comunicazione, idPratica);	
			}
			
			// passaggio stato
			String codicetemplate = comunicazione.getCorrispondenza().getCodiceTemplate();
			TemplateEmailSearch searchTemp = new TemplateEmailSearch();
			searchTemp.setCodice(codicetemplate);
			PaginatedList<TemplateEmailDTO> templateRes = this.templateService.search(searchTemp );
			//non serve prenderlo per id_organizzazione...mi basta per cpire se CON_AVVIO o SENZA_AVVIO
			TemplateEmailDTO template = templateRes.getList().get(0);
			if(template.getSottoSezione()=="CON_AVVIO") {
				praticaRepo.updateStatoRelazione(relazione.getIdPratica(), StatoRelazione.RELAZIONE_TRASMESSA_CON_AVVIO);
			}else{
				praticaRepo.updateStatoRelazione(relazione.getIdPratica(), StatoRelazione.RELAZIONE_TRASMESSA_SENZA_AVVIO);
			}
			//invio in coda la sincronizzazione per diogene allineaDiogene(bean)
			FascicoloStatoBean fsb=new FascicoloStatoBean();
			PraticaDTO pratica=this.praticaRepo.find(relazione.getIdPratica());
			fsb.setPratica(pratica);
			fsb.setSezioniAllegati(List.of(SezioneAllegati.RELAZIONE_ENTE));
			this.queueService.insertNewQueue(SendPlanToDiogeneScheduler.ID, fsb);
		} catch (Exception e) {
			logger.error("Error in sendComunicationRelazione ",e);
		}
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, propagation=Propagation.REQUIRED, readOnly=false, timeout=60000, rollbackFor=Exception.class)
	public void sendComunication(DettaglioCorrispondenzaDTO comunicazione, UUID idPratica, Integer idOrganizzazione, String tipoInvia, List<AllegatiDTO> listaAllegati) throws Exception
	{
		Resource[] resource = {};
		try {
			InvioMailDto datiMail = new InvioMailDto();
			List<DestinatarioDTO> listDest = comunicazione.getDestinatari();
			if(listDest==null || listDest.size()==0)
				throw new CustomOperationToAdviceException("Impossibile inviare la comunicazione, lista destinatari vuota!");
			
			String[] emailToList=listDest.stream().filter(el->el.getTipo().equals(TipoDestinatario.TO)).map(el->el.getEmail()).collect(Collectors.toList()).toArray(new String[0]);
 			//String[] emailToList = new String[(int)listDest.stream().filter(el->el.getTipo()=="TO").count()];
			String[] emailCCList=listDest.stream().filter(el->el.getTipo().equals(TipoDestinatario.CC)).map(el->el.getEmail()).collect(Collectors.toList()).toArray(new String[0]);
			//se esiste già il from non lo aggiorno...
			if(comunicazione.getCorrispondenza()!=null && StringUtil.isEmail(comunicazione.getCorrispondenza().getMittenteEmail())){
				datiMail.setFrom(comunicazione.getCorrispondenza().getMittenteEmail());	
			}else {
				if(idOrganizzazione == null) {
					final PraticaDTO pratica = this.praticaRepo.find(idPratica);
					datiMail.setFrom(this.getMittente(Integer.valueOf(pratica.getEnteDelegato())));
				} else {
					datiMail.setFrom(this.getMittente(idOrganizzazione));
				}
			}
			datiMail.setTo(emailToList);
			datiMail.setBcc(emailCCList);
			datiMail.setOggettoMail(comunicazione.getCorrispondenza().getOggetto());
			datiMail.setCorpoMail(comunicazione.getCorrispondenza().getTesto());
			List<AllegatiDTO> listaAllegatiCompleta=new ArrayList<AllegatiDTO>();
			//recupero allegati della comunicazione
			if(listaAllegati!=null) {
				listaAllegatiCompleta.addAll(listaAllegati);
			}
			if(comunicazione.getAllegatiInfo()!=null && comunicazione.getAllegatiInfo().size()>0) {
				listaAllegatiCompleta.addAll(comunicazione.getAllegatiInfo());
			}
			//gli allegati sono sempre logici ovvero vengono codificati con link nella mail
			if(ListUtil.isNotEmpty(listaAllegatiCompleta)) {
				this.aggiungiUrlAllegati(comunicazione.getCorrispondenza().getId(),datiMail,listaAllegatiCompleta);
			}
			this.webMaiService.inviaMail(datiMail , null ,comunicazione.getCorrispondenza().getId());
			//Una volta inviata la comunicazione, il record non è più in stato di bozza
			if(comunicazione.getCorrispondenza().getDataInvio()==null) {
				comunicazione.getCorrispondenza().setDataInvio(Timestamp.valueOf(LocalDateTime.now()));
			}
			int n = corrispondenzaRepository.updateDraft(comunicazione.getCorrispondenza().getId(),comunicazione.getCorrispondenza().getDataInvio());
			if(n != 1)
			{
				logger.error("Mi aspettavo di aggiornare 1 corrispondenza ma ne ho aggiornate {}", n);
				throw new Exception("Mi aspettavo di aggiornare 1 corrispondenza ma ne ho aggiornate " + n);
			}
			destinatarioRepository.updateStato(comunicazione.getCorrispondenza().getId(),StatoCorrispondenza.INVIATA);
			if(comunicazione.getAllegatiInfo()!=null && comunicazione.getAllegatiInfo().size()>0) {
				informaDiogene(comunicazione);
				aggiornaCds(comunicazione,idPratica,listaAllegatiCompleta);
			}
		}catch (Exception e) {
			logger.error("Errore in sendCommunication",e);
			throw e;
		}finally {
			if(resource!=null) {
				for (Resource res : resource) {
					String folder = res.getFile().getParent();
					res.getFile().delete();
					File folderFile = new File(folder);
					folderFile.delete();
				}
			}
		}
	}
	
	
	/**
	 * vengono inviati gli allegati della comunicazione alle CDS eventualmente aperte sulla pratica
	 * @author acolaianni
	 *
	 * @param idPratica
	 * @param listaAllegatiCompleta
	 */
	private void aggiornaCds(DettaglioCorrispondenzaDTO comunicazione,UUID idPratica, List<AllegatiDTO> listaAllegatiCompleta) {
		if(ListUtil.isEmpty(listaAllegatiCompleta)) {
			return;
		}
		logger.info("Aggiornamento cds a partire da comunicazione uuidPratica {}, idComunicazione {}",idPratica,comunicazione.getCorrispondenza().getId());
		CorrispondenzaDTO corrispondenzaDto = comunicazione.getCorrispondenza();
		if(corrispondenzaDto.isRiservata()) {
			logger.info("Aggiornamento cds skipped, comunicazione riservata. uuidPratica {}, idComunicazione {}",idPratica,comunicazione.getCorrispondenza().getId());
			return;
		}
		for(AllegatiDTO allegato:listaAllegatiCompleta) {
			try {
				SezioneAllegati sezioneAllegato= SezioneAllegati.COMUNICAZIONI;
				//cerco la tipologia dell'allegato
				List<AllegatiTipoContenutoDTO> allegatiTipiContenuto = comunicazione.getAllegati();
				if(ListUtil.isNotEmpty(allegatiTipiContenuto)) {
					for(AllegatiTipoContenutoDTO allTipoCont:allegatiTipiContenuto) {
						if(allTipoCont.getAllegatiId().equals(allegato.getId())) {
							TipoContenutoDTO tipoContenuto = 
									this.tipoContenutoRepository.findById(allTipoCont.getTipoContenutoId());
							sezioneAllegato=SezioneAllegati.valueOf(tipoContenuto.getSezione());
							break;
						}
					}
				}
				this.cdsService.appendiDocumentoACds(idPratica, sezioneAllegato, allegato);
			} catch (JsonProcessingException | SQLException e) {
				logger.error("Errore durante l'aggiornamento della cds a partire da comunicazione uuidPratica {}, idComunicazione {}, errore {}",idPratica,comunicazione.getCorrispondenza().getId(),e);
			}	
		}
	}

	private void informaDiogene(DettaglioCorrispondenzaDTO comunicazione) {
		try {
			if(comunicazione.getCorrispondenza()!=null && comunicazione.getCorrispondenza().getId()!=null) {
				Long idCorrispondenza = comunicazione.getCorrispondenza().getId();
				List<FascicoloCorrispondenzaDTO> fascicoliCorrs = this.fascicoloCorrispondenzaRepository.findByIdCorrispondenza(idCorrispondenza);
				if(ListUtil.isEmpty(fascicoliCorrs)) {
					this.logger.error("Errore inatteso: DettaglioCorrispondenzaDTO non relazionato ad alcun fascicolo, impossibile procedere con la schedulazione del SendPlanToDiogene {}",comunicazione);
				}
				for(FascicoloCorrispondenzaDTO fascicoloCorr:fascicoliCorrs) {
					PraticaDTO pratica=this.praticaRepo.find(fascicoloCorr.getIdPratica());
					FascicoloStatoBean fsb=new FascicoloStatoBean();
					this.logger.info("Invio a scheduling SendPlanToDiogene a partire dalla comunicazione avente oggetto {} e codice pratica {}",comunicazione.getCorrispondenza().getOggetto(),pratica.getCodicePraticaAppptr());
					fsb.setPratica(pratica);
					fsb.setSezioniAllegati(List.of(SezioneAllegati.COMUNICAZIONI));
					this.queueService.insertNewQueue(SendPlanToDiogeneScheduler.ID, fsb);					
				}
			}else {
				this.logger.error("Errore inatteso: DettaglioCorrispondenzaDTO senza id corrispondenza, impossibile procedere con la schedulazione del SendPlanToDiogene {}",comunicazione);
			}
		}catch(Exception e) {
			this.logger.error("Errore durante la schedulazione del jon SendPlanToDiogene in invioComunicazioni idComunicazione {}",comunicazione.getCorrispondenza().getId(),e);
		}
		
	}

	/**
	 * qui appendo al corpo gli url degli allegati
	 * @author acolaianni
	 *
	 * @param datiMail viene aggiornato il corpo e vengono appesi gli url degli allegati
	 * @param listaAllegatiCompleta
	 * @throws Exception 
	 */
	private void aggiungiUrlAllegati(final Long id,InvioMailDto datiMail, final List<AllegatiDTO> listaAllegatiCompleta) throws Exception {
		StringBuilder testo=new StringBuilder(datiMail.getCorpoMail());
		String prefixUrl = this.commonRepository.getConfigurationValue(ComunicazioniService.KEY_URL_DOWNLOAD_PREFIX,props.getCodiceApplicazione());
		testo
		.append("<br><br>Allegati:<br>");
		String pathFileDownload =
				prefixUrl+
				"/public/"+ComunicazioniService.PUBLIC_DOWNLOAD_ALLEGATO_MAIL;
		listaAllegatiCompleta.forEach(allegato->{
			StringBuilder queryString=new StringBuilder();
			queryString
			.append("?idDocumento=")
			.append(allegato.getId())
			.append("&idCorrispondenza=")
			.append(id);
			testo.append("<br>")
				.append("<a href=\""+pathFileDownload+queryString.toString()+"\">")
				.append(allegato.getNomeFile())
				.append("</a>");
		});
		datiMail.setCorpoMail(testo.toString());
		corrispondenzaRepository.updateTesto(id, testo.toString());
	}

	/**
	 * nomefile.ext diventa nomefile(2).ext
	 * @author acolaianni
	 *
	 * @param filename
	 * @param numCopia
	 * @return
	 */
	private String aggiungiSuffissoNomefile(String filename,int numCopia) {
		String[] parts = filename.split("\\.");
		if(parts.length>1) {
			parts[parts.length-2]=parts[parts.length-2]+"("+numCopia+")";
		}else {
			parts[0]=parts[0]+"("+numCopia+")";
		}
		return String.join(".", parts);
	}
	
	/**
	 * Metodo che converte una lista di allegati in array di Resource
	 *
	 * @author G.Lavermicocca
	 * @date 5 ott 2020
	 */
	private Resource[] getAllegati(List<AllegatiDTO> allegati) {
		Resource[] resources=new Resource[allegati.size()];
		int i=0;
		Map<String,Integer> mapFileDuplicati=new HashMap<>();
		for (AllegatiDTO allegato : allegati) {
			CmsDownloadResponseBean cms;
			File f = null;
			try {
				logger.info("ComunicazioniService - downloadAllegato "+allegato.getId());
				cms = allegatoService.downloadAllegatoGenerico(allegato.getId());
			
				if (cms == null || cms.getFileName() == null)
					throw new CustomOperationToAdviceException("Nessun file trovato...");
				f = new File(cms.getFileName());
				//controllo che non ci sono allegati con lo stesso nome... eventualmente metto un suffisso nel nome del file...
				mapFileDuplicati.merge(f.getName(), 1, (val1,val2)->val1+val2);
				if(mapFileDuplicati.get(f.getName())>1) {
					//rinomino con filename(xx).ext
					String nuovoNome=aggiungiSuffissoNomefile(f.getName(),mapFileDuplicati.get(f.getName()));
					Path newPath=f.toPath().resolveSibling(nuovoNome);
					f.renameTo(newPath.toFile());
					f=newPath.toFile();
					
				}
				Resource res = new FileSystemResource(f);
				resources[i]= res;
				i++;
			} catch (CustomCmisException | CustomOperationException | IOException | CustomValidationException e) {
				logger.error("Errore in getAllegati", e);
			}
		}
		return resources;
	}

	private File generaPDFinformazioniEmail(JasperInfoEmailDTO jasperDTO) throws Exception {
		try {
			Path pathTemp=Files.createTempFile("Informazioni_Email_".concat(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())),".PDF");
			File generated=pathTemp.toFile();
			Resource res = new ClassPathResource("/jasper/".concat("infoEmail").concat(".jasper"));
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(res.getInputStream());
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,
																   new JRBeanCollectionDataSource(Collections.singletonList(jasperDTO)));
			JasperExportManager.exportReportToPdfFile(jasperPrint, generated.getPath());
			return generated;
		} catch (Exception e) {
			throw e;
		}
	}

	
	// il parametro di input "data" deve essere lo stesso per il PDF non protocollato e per il PDF protocollato
	@Override
	public File generaPDFinformazioniEmail(DettaglioCorrispondenzaDTO corrispondenza, List<AllegatiDTO> listaAllegati, Date data, String protocollo,PraticaDTO praticaDTO) throws Exception {
		try {
			List<DestinatarioDTO> destinDefault=new ArrayList<DestinatarioDTO>();
			JasperInfoEmailDTO jasperDTO = new JasperInfoEmailDTO();
			if(corrispondenza.getCorrispondenza().getCodiceTemplate() != null &&
			   corrispondenza.getCorrispondenza().getCodiceTemplate().equals(SezioneIstruttoria.RICH_INTEGRAZIONE.name())){
				//aggiungo richiedente e user owner della pratica 
				this.fascicoloSvc.addDestinatariPratica(destinDefault, praticaDTO, false, false);
			}
			jasperDTO.setData(data);
			jasperDTO.setProtocollo(protocollo);
			jasperDTO.setMittenteEmail(corrispondenza.getCorrispondenza().getMittenteEmail());	
			jasperDTO.setMittenteNome(corrispondenza.getCorrispondenza().getMittenteDenominazione());
			jasperDTO.setOggetto(corrispondenza.getCorrispondenza().getOggetto());
			jasperDTO.setCorpo(corrispondenza.getCorrispondenza().getTesto());
			corrispondenza.getDestinatari().forEach(elem->{
				jasperDTO.getListaDestinatari().add(new JasperInfoEmailDestinatarioDTO(elem.getTipo().name(), elem.getEmail(), elem.getNome()));
			});
			destinDefault.forEach(elem->{
				jasperDTO.getListaDestinatari().add(new JasperInfoEmailDestinatarioDTO(elem.getTipo().name(), elem.getEmail(), elem.getNome()));
			});
			listaAllegati.forEach(elem->{
				jasperDTO.getListaAllegati().add(new JasperInfoEmailAllegatoDTO(elem.getNomeFile(), elem.getChecksum(), elem.getSize()));
			});
			return this.generaPDFinformazioniEmail(jasperDTO);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public CorrispondenzaDTO getCorrispondenzaFromAllegato(UUID idAllegato) {
		return this.corrispondenzaRepository.getCorrispondenzaFromAllegato(idAllegato);
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, propagation=Propagation.REQUIRED, readOnly=false, timeout=60000, rollbackFor=Exception.class)
	public void sendComunicazioneRichiedente(UUID idPratica, String codiceSegreto) throws Exception
	{
	    TemplateEmailDestinatariDto template = templateService.info(0, "CONVALIDA_RICHIEDENTE");
	    DettaglioCorrispondenzaDTO dettaglio = new DettaglioCorrispondenzaDTO();
	    CorrispondenzaDTO corrispondenza = new CorrispondenzaDTO();
	    DestinatarioDTO destinatario = new DestinatarioDTO();
	    PraticaDTO pratica = praticaRepo.find(idPratica);
	    ReferentiDTO richiedente = referentiRepository.selectRichiedente(idPratica, "SD");
	    
	    pratica.setCodiceSegreto(codiceSegreto);	    
	    List<String> placeholders = Arrays.asList(template.getTemplate().getPlaceholders().split(","));
	    String testo = template.getTemplate().getTesto();
	    String oggetto = template.getTemplate().getOggetto();
	    
	    replace(placeholders, testo, oggetto, pratica, richiedente, corrispondenza);
		
	    corrispondenza.setBozza(false);
//	    corrispondenza.setOggetto(oggetto);
//	    corrispondenza.setTesto(testo);
	    corrispondenza.setRuolo(userUtil.getRuolo().toString());
	    corrispondenza.setMittenteEnte(userUtil.getNomeGruppo());
	    corrispondenza.setMittenteDenominazione("SYSTEM");
	    corrispondenza.setMittenteUsername("SYSTEM");
	    //prende il mittente dalla configurazione di default o dalla tabella configurazionEnte
	    corrispondenza.setComunicazione(false);
	    corrispondenza.setIdOrganizzazioneOwner(0);
	    corrispondenza.setTipoOrganizzazioneOwner(null);
	    if(template!=null && template.getTemplate()!=null) 
	    {
		corrispondenza.setCodiceTemplate(template.getTemplate().getCodice());
		corrispondenza.setRiservata(template.getTemplate().isRiservata());
	    }
	    destinatario.setEmail(richiedente.getPec() != null ? richiedente.getPec() : richiedente.getMail());
	    destinatario.setPec(richiedente.getPec() != null);
	    destinatario.setNome(richiedente.getNome() + " " + richiedente.getCognome());
	    destinatario.setTipo(TipoDestinatario.TO);
	    
	    dettaglio.setCorrispondenza(corrispondenza);
	    dettaglio.setDestinatari(Collections.singletonList(destinatario));	    
	    
//	    dettaglio = saveComunication(dettaglio);
	    corrispondenza.setMittenteEmail("");
	    sendMailClient(dettaglio);
//
//	    FascicoloCorrispondenzaDTO entity = new  FascicoloCorrispondenzaDTO();
//	    entity.setIdPratica(idPratica);
//	    entity.setIdCorrispondenza(dettaglio.getCorrispondenza().getId());
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, propagation=Propagation.REQUIRED, readOnly=false, timeout=60000, rollbackFor=Exception.class)
	public void sendNuovoCodiceSegreto(UUID idPratica, String codiceSegreto) throws Exception
	{
	    TemplateEmailDestinatariDto template = templateService.info(0, "CONVALIDA_RICHIEDENTE_NUOVO_CODICE");
	    DettaglioCorrispondenzaDTO dettaglio = new DettaglioCorrispondenzaDTO();
	    CorrispondenzaDTO corrispondenza = new CorrispondenzaDTO();
	    DestinatarioDTO destinatario = new DestinatarioDTO();
	    PraticaDTO pratica = praticaRepo.find(idPratica);
	    ReferentiDTO richiedente = referentiRepository.selectRichiedente(idPratica, "SD");
	    
	    pratica.setCodiceSegreto(codiceSegreto);	    
	    List<String> placeholders = Arrays.asList(template.getTemplate().getPlaceholders().split(","));
	    String testo = template.getTemplate().getTesto();
	    String oggetto = template.getTemplate().getOggetto();
	    
	    replace(placeholders, testo, oggetto, pratica, richiedente, corrispondenza);
		
	    corrispondenza.setBozza(false);
//	    corrispondenza.setOggetto(oggetto);
//	    corrispondenza.setTesto(testo);
	    corrispondenza.setRuolo(userUtil.getRuolo().toString());
	    corrispondenza.setMittenteEnte(userUtil.getNomeGruppo());
	    corrispondenza.setMittenteDenominazione("SYSTEM");
	    corrispondenza.setMittenteUsername("SYSTEM");
	    //prende il mittente dalla configurazione di default o dalla tabella configurazionEnte
	    corrispondenza.setComunicazione(false);
	    corrispondenza.setIdOrganizzazioneOwner(0);
	    corrispondenza.setTipoOrganizzazioneOwner(null);
	    if(template!=null && template.getTemplate()!=null) 
	    {
		corrispondenza.setCodiceTemplate(template.getTemplate().getCodice());
		corrispondenza.setRiservata(template.getTemplate().isRiservata());
	    }
	    destinatario.setEmail(richiedente.getPec() != null ? richiedente.getPec() : richiedente.getMail());
	    destinatario.setPec(richiedente.getPec() != null);
	    destinatario.setNome(richiedente.getNome() + " " + richiedente.getCognome());
	    destinatario.setTipo(TipoDestinatario.TO);
	    
	    dettaglio.setCorrispondenza(corrispondenza);
	    dettaglio.setDestinatari(Collections.singletonList(destinatario));	    
	    
//	    dettaglio = saveComunication(dettaglio);
	    corrispondenza.setMittenteEmail("");
	    sendMailClient(dettaglio);
//
//	    FascicoloCorrispondenzaDTO entity = new  FascicoloCorrispondenzaDTO();
//	    entity.setIdPratica(idPratica);
//	    entity.setIdCorrispondenza(dettaglio.getCorrispondenza().getId());
	}
	
	private void replace(List<String> placeholders, String testo, String oggetto, PraticaDTO pratica, ReferentiDTO ref, CorrispondenzaDTO corrispondenza)
	throws Exception {
	    for(String placeholder: placeholders)
	    {
		String toReplace = "{"+placeholder.trim()+"}";
		String sobstitute = "";
		switch (PlaceHolder.valueOf(placeholder.trim()))
		{
		case CODICE_FASCICOLO:
		    sobstitute = pratica.getCodicePraticaAppptr();
		    break;
		case NOME_RICHIEDENTE:
		    sobstitute = ref.getNome();
		    break;
		case COGNOME_RICHIEDENTE:
		    sobstitute = ref.getCognome();
		    break;
		case CODICE_SEGRETO:
		    sobstitute = pratica.getCodiceSegreto() != null ? pratica.getCodiceSegreto() : "";    
		    break;
		case TIPO_FASCICOLO:
			TipoProcedimentoDTO tipoProc = new TipoProcedimentoDTO();
			tipoProc.setId(pratica.getTipoProcedimento());
			try {
				tipoProc=tipoService.find(tipoProc);
			    sobstitute = tipoProc != null? tipoProc.getDescrizione() : "";
			} catch (Exception e) {
				logger.error("Errore nel recupero del tipo procedimento");
			    sobstitute = "";
			}
			break;
		default: 
		    break;
		}
		oggetto = oggetto.replace(toReplace, sobstitute);
		testo = testo.replace(toReplace, sobstitute);
	    }
	    corrispondenza.setOggetto(oggetto);
	    corrispondenza.setTesto(testo);
	}
	
	private SendMailResponseBean sendMailClient(DettaglioCorrispondenzaDTO dettaglio) throws Exception
	{
	    final SendMailRequestBean mailBean = new SendMailRequestBean();
	    mailBean.setKey(this.mailKey);
	    mailBean.setSubject(dettaglio.getCorrispondenza().getOggetto());
	    mailBean.setText(dettaglio.getCorrispondenza().getTesto());
	    mailBean.setTo(dettaglio.getDestinatari().stream().map(DestinatarioDTO::getEmail).collect(Collectors.toList()));
	    
	    return clientService.sendMail(this.urlPec, mailBean, null);
	}
}