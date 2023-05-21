package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.impl;
import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.TipologicaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.DettaglioCorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.ProvvedimentoFinaleDetailsDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AllowedMimeType;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.SezioneAllegati;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoDestinatario;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiTipoContenutoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.CorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinatarioDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ProvvedimentoFinaleDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TemplateEmailDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.AllegatiRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.CorrispondenzaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.DestinatarioRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.LocalizzazioneInterventoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ProvvedimentoFinaleRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TipoContenutoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.ProvvedimentoFinaleSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.TipoContenutoSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IReferentiService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.ProvvedimentoFinaleScheduler;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.SendPlanToDiogeneScheduler;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.bean.FascicoloStatoBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.bean.ProvvedimentoBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.AllegatoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.ComunicazioniService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.DestinatarioService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.ProvvedimentoFinaleService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.jasper.CreaReportService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.GeneratedFileBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.ProtocolNumberType;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.PlaceHolder;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.dto.TemplateEmailDestinatariDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.service.ITemplateMailService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.service.impl.ResolveTemplateService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.MockMultipartFile;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.VarieUtils;
import it.eng.tz.puglia.batch.queue.IQueueService;
import it.eng.tz.puglia.service.http.bean.ProtocolloResponseBean;

@Service
public class ProvvedimentoFinaleServiceImpl implements ProvvedimentoFinaleService
{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired private ProvvedimentoFinaleRepository dao;
	@Autowired private PraticaRepository praticaRepository;
	@Autowired private AllegatiRepository allegatiRepository;
	@Autowired private TipoContenutoRepository tcRepository;
	@Autowired private DestinatarioRepository destinatariRepository;
	@Autowired private LocalizzazioneInterventoRepository localizzazioneRepository;
	@Autowired private AllegatoService allegatoService;
	@Autowired private UserUtil userUtil;
	@Autowired private ComunicazioniService comunicazioniService;
	@Autowired private DestinatarioService destinatarioService;
	@Autowired private ResolveTemplateService templateResolver;
	@Autowired private ITemplateMailService templateService;;
	@Autowired private CreaReportService reportService;
	@Autowired private ProtocolloCallService protocollaCallService;
	@Autowired private IReferentiService referentiService;
	@Autowired private CorrispondenzaRepository corrispondenzaRepository;
	
	@Autowired Validator validator;

	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class, timeout=60000)
	public Long count(ProvvedimentoFinaleSearch search) throws Exception
	{
		return dao.count(search);
	}

	/**
	 * è utilizzato anche senza token e quindi senza user per prelevare il provvedimento finale
	 */
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class, timeout=60000)
	public ProvvedimentoFinaleDetailsDTO find(UUID idPratica) throws Exception
	{
		return find(idPratica, userUtil.getIntegerId());
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class, timeout=60000)
	public ProvvedimentoFinaleDetailsDTO find(UUID idPratica, Integer idOrganizzazione) throws Exception
	{
		ProvvedimentoFinaleDetailsDTO result = new ProvvedimentoFinaleDetailsDTO(dao.find(idPratica));
		List<AllegatiDTO> allegati = allegatiRepository.searchBySezioni(idPratica, Collections.singletonList(SezioneAllegati.PROVVEDIMENTO.name()));
		if(allegati != null && !allegati.isEmpty())
		{
			result.setAllegati(new ArrayList<AllegatiDTO>());
			for(AllegatiDTO a: allegati)
			{
				List<Integer> types = tcRepository.findTipiAllegato(a.getId());
				if(types != null)
				{
					a.setTipiContenuto(types.stream().map(m -> m.toString()).collect(Collectors.toList()));
					a.setType(a.getTipiContenuto().get(0));
				}
				result.getAllegati().add(a);
			}
		}
		result.setDestinatariFissi(findDestinatariFissi(idPratica, idOrganizzazione));
		if(result.getIdCorrispondenza()!=null) {//in caso di migrazione non c'è il legame con la mail di trasmissione
			result.setUlterioriDestinatari(destinatariRepository.searchWithoutPaging(result.getIdCorrispondenza()));	
		}
		return result;
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class, timeout=60000)
	public List<AllegatiDTO> findAllegatiProvvedimenti(UUID idPratica) throws Exception
	{
		List<AllegatiDTO> allegati = allegatiRepository.searchBySezioni(idPratica, Collections.singletonList(SezioneAllegati.PROVVEDIMENTO.name()));
		if(allegati != null && !allegati.isEmpty())
		{
			for(AllegatiDTO a: allegati)
			{
				List<Integer> types = tcRepository.findTipiAllegato(a.getId());
				if(types != null)
				{
					a.setTipiContenuto(types.stream().map(m -> m.toString()).collect(Collectors.toList()));
					a.setType(a.getTipiContenuto().get(0));
				}
			}
		}
		return allegati;
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class, timeout=60000)
	public List<AllegatiDTO> findDocumentiTrasmissione(UUID idPratica) throws Exception
	{
		List<AllegatiDTO> allegati = allegatiRepository.searchBySezioni(idPratica, Collections.singletonList(SezioneAllegati.TRASMISSIONE.name()));
		if(allegati != null && !allegati.isEmpty())
		{
			for(AllegatiDTO a: allegati)
			{
				List<Integer> types = tcRepository.findTipiAllegato(a.getId());
				if(types != null)
				{
					a.setTipiContenuto(types.stream().map(m -> m.toString()).collect(Collectors.toList()));
					a.setType(a.getTipiContenuto().get(0));
				}
			}
		}
		return allegati;
	}

	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class, timeout=60000)
	public ProvvedimentoFinaleDTO saveProvvedimento(ProvvedimentoFinaleDTO entity) throws Exception
	{
		checkPermessi(entity.getIdPratica());
		DettaglioCorrispondenzaDTO dto = comunicazioniService.create(entity.getIdPratica());
		entity.setIdCorrispondenza(dto.getCorrispondenza().getId());
		entity.setId(dao.insert(entity));
		return entity;
	}

	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class, timeout=60000)
	public ProvvedimentoFinaleDTO updateProvvedimento(ProvvedimentoFinaleDTO entity) throws Exception
	{
		checkPermessi(entity.getIdPratica());
		dao.update(entity);
		return entity;
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class, timeout=60000)	
	public AllegatiDTO uploadAllegatoProvvedimento(MultipartFile file, List<Integer> tipiContenuto, UUID idPratica) throws Exception
	{
		checkPermessi(idPratica);
		TipoContenutoSearch tcSearch = new TipoContenutoSearch();
		tcSearch.setIds(tipiContenuto);
		if(tcRepository.count(tcSearch) == 0)
		{
			logger.error("Tipo contenuto [{}] non adeguato al provvedimento finale", tipiContenuto);
			throw new Exception("Tipo contenuto [" + tipiContenuto + "] non adeguato al provvedimento finale");
		}
		AllegatiDTO allegato = allegatoService.uploadAllegato(idPratica.toString(), tipiContenuto, file.getOriginalFilename(), file);
		allegato.setTipiContenuto(tipiContenuto.stream().map(m -> m.toString()).collect(Collectors.toList()));
		allegato.setType(allegato.getTipiContenuto().get(0));
		return allegato;
	}

	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class, timeout=60000)
	public AllegatiDTO uploadAllegatoProvvedimento(MultipartFile file, UUID idPratica) throws Exception
	{
		return uploadAllegatoProvvedimento(file, Collections.singletonList(950), idPratica);
	}

	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class, timeout=60000)
	public void deleteAllegatoProvvedimento(UUID idAllegato) throws Exception
	{
		AllegatiDTO a = new AllegatiDTO();
		a.setId(idAllegato);
		a = allegatiRepository.find(a);
		checkPermessi(a.getPraticaId());
		allegatoService.deleteAllegato(idAllegato.toString());
	}

	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class, timeout=60000)
	public File generaAnteprimaPDFRicevutaTrasmissione(UUID idPratica,List<DestinatarioDTO>destinatari) throws Exception {
		PraticaDTO pratica = praticaRepository.find(idPratica);
		checkPermessi(pratica);
		ProvvedimentoFinaleDetailsDTO entity = find(idPratica);
		List<DestinatarioDTO> destinatariFinali;
		if(destinatari != null)
			destinatariFinali = destinatari;
		else
			destinatariFinali = entity.getUlterioriDestinatari();
		destinatariFinali = VarieUtils.eliminaDuplicati(destinatariFinali, entity.getDestinatariFissi());
		List<TipologicaDTO> temp = destinatariFinali.stream().map(TipologicaDTO::new).collect(Collectors.toList());
		File f = reportService.creaPdf_DocumentoTrasmissione(idPratica, null, temp);
		return f;
	} 
	
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class, timeout=60000)
	public ProvvedimentoFinaleDetailsDTO trasmettiProvvedimento(List<DestinatarioDTO>destinatari, UUID idPratica, String baseUrl) throws Exception
	{
		PraticaDTO pratica = praticaRepository.find(idPratica);
		checkPermessi(pratica);
		ProvvedimentoFinaleDetailsDTO entity = find(idPratica);
		
		Set<ConstraintViolation<ProvvedimentoFinaleDTO>> violations = validator.validate(entity);
		if(violations != null && !violations.isEmpty())
		{
			String errors = "Impossibile proseguire con la trasmissioni del provvedimento, errori presenti: " 
						  + violations.stream().map(m -> m.getMessage()).collect(Collectors.joining(", "));
			logger.error(errors);
			throw new Exception(errors);
		}
		//anticipato al passaggio (in istruttoria) come da mail di C.Cici 16/11/2020
		//Ciao Adriano,
		//nel sistema attuale il secondo codice viene generato quando si passa dalla fase preistruttoria ad istruttoria.
//		String codiceTrasmissione=remoteService.generaCodiceFascicolo(Integer.parseInt(pratica.getEnteDelegato()));
//		pratica.setCodiceTrasmissione(codiceTrasmissione);
//		praticaRepository.update(pratica);
		List<DestinatarioDTO> destinatariFinali;
		if(destinatari != null)
			destinatariFinali = destinatari;
		else
			destinatariFinali = entity.getUlterioriDestinatari();
		destinatariFinali = VarieUtils.eliminaDuplicati(destinatariFinali, entity.getDestinatariFissi());
		List<TipologicaDTO> temp = null;
		temp = destinatariFinali.stream().map(TipologicaDTO::new).collect(Collectors.toList());
		List<String> emailDestinatari=destinatariFinali.stream().map(DestinatarioDTO::getEmail).collect(Collectors.toList());
		List<String> denominazioneDestinatari=destinatariFinali.stream().map(DestinatarioDTO::getNome).collect(Collectors.toList());
		String filename = "Ricevuta trasmissione " + pratica.getCodiceTrasmissione();
		File f = reportService.creaPdf_DocumentoTrasmissione(idPratica, null, temp);
		
		GeneratedFileBean beanFile=new GeneratedFileBean();
		beanFile.setContent(Files.readAllBytes(f.toPath()));
		beanFile.setName(f.getName());
		String oggetto="Comunicazione avvenuta trasmissione della Pratica ad oggetto'"+pratica.getOggetto()+"'";
		ProtocolloResponseBean segnatura = 
				protocollaCallService.getNumeroProtocollo(beanFile, idPratica, ProtocolNumberType.U,true,
						emailDestinatari.toArray(new String[emailDestinatari.size()]),
						denominazioneDestinatari.toArray(new String[denominazioneDestinatari.size()]),oggetto);
		if(segnatura == null)
		{
			logger.error("Errore durante la protocollazione della ricevuta di trasmissione");
			throw new Exception("Errore durante la protocollazione della ricevuta di trasmissione");
		}
		File fp = reportService.creaPdf_DocumentoTrasmissione(idPratica, segnatura.toFormatoEsteso(), temp);
		MultipartFile multipartProt = new MockMultipartFile(filename+".pdf", AllowedMimeType.PDF.getMimeType(), fp);
		AllegatiDTO a = allegatoService.uploadAllegato(idPratica.toString(), Collections.singletonList(1101), filename, multipartProt);
		
		MultipartFile multipart = new MockMultipartFile(filename+"_preprot.pdf", AllowedMimeType.PDF.getMimeType(), f);
		allegatoService.uploadAllegato(idPratica.toString(), Collections.singletonList(1100), filename, null,  a.getId(), multipart,null);
		
		AllegatiTipoContenutoDTO atp = new AllegatiTipoContenutoDTO();
		atp.setAllegatiId(a.getId());
		atp.setTipoContenutoId(1101);
		
		f.delete();
		fp.delete();
		
		
		
		TemplateEmailDTO template = templateService.findAncheSuDefault(userUtil.getIntegerId(), "INVIO_TRASMISSIONE");
		DettaglioCorrispondenzaDTO det = new DettaglioCorrispondenzaDTO();
		CorrispondenzaDTO corrispondenza = new CorrispondenzaDTO();
		corrispondenza.setId(entity.getIdCorrispondenza());
				
		Function<String,String> customResolving = (pl)->
		{
			String url = baseUrl + "/public/"+ComunicazioniService.PUBLIC_DOWNLOAD_DOC_TRASM;
			if(pl.equals(PlaceHolder.URL_DOWNLOAD_RICEVUTA_PROTETTO.name())) 
			{
				return url + "?idPratica="+pratica.getId();
			}
			else if(pl.equals(PlaceHolder.DATA_PROVVEDIMENTO.name())) 
			{
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				return sdf.format(entity.getDataRilascioAutorizzazione());
			}
			
			return pl;
		};
		
		corrispondenza.setOggetto(templateResolver.risolviTesto(template.getPlaceholders(), template.getOggetto(), pratica));
		corrispondenza.setTesto(templateResolver.risolviTesto(template.getPlaceholders(), template.getTesto(), pratica, customResolving));
		det.setDestinatari(destinatariFinali);
		det.setCorrispondenza(corrispondenza);
		det.setAllegati(Collections.singletonList(atp));
		//passo lo stato a trasmesso sul provvedimento
		entity.setDraft(false);
		dao.update(entity);
		//salvo e poi invio la comunicazione
		comunicazioniService.saveComunication(det, idPratica);
		praticaRepository.trasmettiPratica(idPratica);
		comunicazioniService.sendComunication(det, idPratica, null, null, null);
		ProvvedimentoBean provBean = new ProvvedimentoBean();
		provBean.setIdPratica(String.valueOf(idPratica));
		provBean.setIdCorrispondenza(corrispondenza.getId());
		this.queueService.insertNewQueue(ProvvedimentoFinaleScheduler.ID, provBean);
		//invio in coda la sincronizzazione per diogene allineaDiogene(bean)
		FascicoloStatoBean fsb=new FascicoloStatoBean();
		fsb.setPratica(pratica);
		fsb.setSezioniAllegati(List.of(SezioneAllegati.PROVVEDIMENTO,SezioneAllegati.TRASMISSIONE));
		this.queueService.insertNewQueue(SendPlanToDiogeneScheduler.ID, fsb);
		return entity;
	}
	
	@Autowired
	private IQueueService queueService;
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class, timeout=60000)
	public List<DestinatarioDTO> findDestinatariFissi(UUID idPratica, Integer idOrganizzazione) throws Exception
	{
		List<Integer> enti = localizzazioneRepository.searchForEnteId(idPratica);
		List<DestinatarioDTO> dReg  = destinatarioService.findDestinatariForRegione(enti, TipoDestinatario.TO);
		List<DestinatarioDTO> dEnte = destinatarioService.findDestinatariForEnteDelegato(idOrganizzazione, enti, TipoDestinatario.TO);
		List<DestinatarioDTO> dSopr = destinatarioService.findDestinatariForSoprintendenze(enti, TipoDestinatario.TO);
		List<DestinatarioDTO> dCom  = destinatarioService.findDestinatariForComuni(enti, TipoDestinatario.TO);
		List<DestinatarioDTO> dPrt  = referentiService.findDestinatariPratica(idPratica);
		TemplateEmailDestinatariDto templateDetail = templateService.info(idOrganizzazione, "INVIO_TRASMISSIONE");
		List<DestinatarioDTO> dTempl = null;
		if(templateDetail.getDestinatari() != null)
			templateDetail.getDestinatari().stream().map(DestinatarioDTO::new).collect(Collectors.toList());
		return VarieUtils.eliminaDuplicati(dReg, dEnte, dSopr, dCom, dTempl, dPrt);
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class, timeout=60000)
	public List<DestinatarioDTO> insertUlterioriDestinatari(List<DestinatarioDTO> ulterioriDestinatari, UUID idPratica) throws Exception
	{
		checkPermessi(idPratica);
		ProvvedimentoFinaleDTO entity = dao.find(idPratica);
		Long idCorrispondenza = entity.getIdCorrispondenza();
		destinatariRepository.delete(idCorrispondenza);
		for(DestinatarioDTO d: ulterioriDestinatari)
		{
			d.setIdCorrispondenza(idCorrispondenza);
			d.setId(destinatariRepository.insert(d));
		}
		return ulterioriDestinatari;
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class, timeout=60000)
	public List<DestinatarioDTO> findDestinatari(UUID idPratica) throws Exception
	{
		ProvvedimentoFinaleDTO dto = dao.find(idPratica);
		return destinatariRepository.searchWithoutPaging(dto.getIdCorrispondenza());
	}
	
	private void checkPermessi(UUID idPratica) throws Exception
	{
		PraticaDTO pratica = praticaRepository.find(idPratica);
		checkPermessi(pratica);
	}	
	private void checkPermessi(PraticaDTO pratica) throws Exception
	{
		if(!pratica.getEnteDelegato().equals(Integer.toString(userUtil.getIntegerId())))
		{
			logger.error("Non hai i permessi per effettuare operazioni in scrittura su provvedimento finale della pratica {}", pratica.getId());
			throw new Exception("Non hai i permessi per effettuare operazioni in scrittura su provvedimento finale della pratica " + pratica.getId());
		}
	}
	
}
