package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.cds.service.IConferenzaDeiServiziService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.dto.interfacce.UlterioreDocumentazioneAttribute;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.DettaglioCorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.UlterioreDocumentazioneMultiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.SezioneAllegati;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatoCorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.CorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinatarioDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TemplateEmailDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.UlterioreDocumentazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.AllegatoCorrispondenzaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.UlterioreDocRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.UlterioreDocSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl.PraticaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.SendPlanToDiogeneScheduler;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.bean.FascicoloStatoBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.ComunicazioniService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.impl.AllegatiIstruttoriaServiceImpl;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.UlterioriDocService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.service.impl.ResolveTemplateService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.service.impl.TemplateMailService;
import it.eng.tz.puglia.batch.queue.IQueueService;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.string.StringUtil;


@Service
public class UlterioreDocServiceImpl implements UlterioriDocService  {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired private UserUtil userUtil;
	@Autowired private PraticaService praticaService;
	@Autowired private AllegatiIstruttoriaServiceImpl allegatiService;
	@Autowired private TemplateMailService templateMailService;
	@Autowired private ResolveTemplateService resolveTemplate;
	@Autowired private ComunicazioniService comService;
	@Autowired private AllegatoCorrispondenzaRepository allegatoCorrispondenzaRepository ;
	@Autowired private UlterioreDocRepository ulterioreDocDao;
	@Autowired private IQueueService queueService;
	@Autowired private IConferenzaDeiServiziService cdsService;
	
	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_WRITE, readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void insertUlterioreDoc(UlterioreDocumentazioneDTO data, MultipartFile[] file, String urlFile ) throws Exception {
		//inserimento allegato di ulteriore documentazione
		valida(data.getNotificaA(),List.of(data.getVisibileA()),List.of(data),file);
		List<Integer> tipiContenuto = new ArrayList<Integer>();
		tipiContenuto.add(1200);
		String nomeContenuto=SezioneAllegati.ULTERIORE_DOCUMENTAZIONE.toString();
		AllegatiDTO allegatoUlterioreDoc =allegatiService.uploadAllegato(data.getIdFascicolo().toString(), tipiContenuto, nomeContenuto, file[0]);
		allegatoUlterioreDoc.setTitolo(data.getTitoloDocumento());
		allegatoUlterioreDoc.setDescrizione(data.getDescrizioneContenuto());
		allegatiService.update(allegatoUlterioreDoc);
		
				
		//Ricerca e risoluzione template
		TemplateEmailDTO templateUlterioriDoc= new TemplateEmailDTO();
		 Gruppi gruppo = userUtil.getGruppo();
		String codiceTemplate="ULTER_DOC_ED";
		if(gruppo.equals(Gruppi.ED_)){
			codiceTemplate="ULTER_DOC_ED";
		}else if(gruppo.equals(Gruppi.CL_)){
			codiceTemplate="ULTER_DOC_CL";
			
		}
		else if(gruppo.equals(Gruppi.SBAP_)) {
			codiceTemplate="ULTER_DOC_SOP";
			
		}else if(gruppo.equals(Gruppi.ETI_)) {
			codiceTemplate="ULTER_DOC_ET";
		}
		templateUlterioriDoc = templateMailService.findAncheSuDefault(userUtil.getIntegerId(), codiceTemplate );
		DettaglioCorrispondenzaDTO comunicazione = new DettaglioCorrispondenzaDTO();
		comunicazione=comService.create(data.getIdFascicolo());
		List<AllegatiDTO> allegati = new ArrayList<AllegatiDTO>();
		
		//allegati.add(allegatoUlterioreDoc);
		comunicazione.setAllegatiInfo(allegati);
		comunicazione.setDestinatari(data.getNotificaA());
		CorrispondenzaDTO corrispondenza = comunicazione.getCorrispondenza();
		AllegatoCorrispondenzaDTO ac = new AllegatoCorrispondenzaDTO();
		ac.setIdAllegato(allegatoUlterioreDoc.getId());
		ac.setIdCorrispondenza(corrispondenza.getId());
		allegatoCorrispondenzaRepository.insert(ac);
		PraticaDTO praticaDto= new PraticaDTO();
		praticaDto.setId(data.getIdFascicolo());
		praticaDto=praticaService.find(praticaDto);
		String placeholders=templateUlterioriDoc.getPlaceholders();
		Function<String,String> customResolving= (pl)->{
			if(pl.equals("URL_FILE_ULTERIORE_DOC")) {
				return urlFile+"?idDocumento="+allegatoUlterioreDoc.getId();
			}
			if(pl.equals("NOME_FILE_ULTERIORE_DOC")) {
				return file[0].getOriginalFilename();
			}
			else if(pl.equals("TITOLO_FILE_ULTERIORE_DOC")) {
				return data.getTitoloDocumento();
			}
			else if(pl.equals("DESCRIZIONE_FILE_ULTERIORE_DOC")) {
				return data.getDescrizioneContenuto();
			}
			return pl;
			
		};
		
		String testoEmail=resolveTemplate.risolviTesto(placeholders, templateUlterioriDoc.getTesto(), praticaDto,customResolving);
		String oggettoEmail=resolveTemplate.risolviTesto("CODICE_FASCICOLO", templateUlterioriDoc.getOggetto(),praticaDto);
		corrispondenza.setOggetto(oggettoEmail);
		corrispondenza.setTesto(testoEmail);
		corrispondenza.setVisibilita(String.join("_,",data.getVisibileA())+"_");
		corrispondenza.setCodiceTemplate(codiceTemplate);
		comunicazione.setCorrispondenza(corrispondenza);
		
		
		for (String ente : data.getVisibileA()) {
			this.allegatiService.insertVisibilitaUlerioreDoc(allegatoUlterioreDoc.getId(), ente+"_");
		}
		comService.saveComunication(comunicazione, praticaDto.getId());
		comService.sendComunication(comunicazione, praticaDto.getId());
		//invio in coda la sincronizzazione per diogene allineaDiogene(bean)
		FascicoloStatoBean fsb=new FascicoloStatoBean();
		fsb.setPratica(praticaDto);
		fsb.setSezioniAllegati(List.of(SezioneAllegati.ULTERIORE_DOCUMENTAZIONE));
		this.queueService.insertNewQueue(SendPlanToDiogeneScheduler.ID, fsb);
	}
	
	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_WRITE, readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void insertUlterioreDoc(UlterioreDocumentazioneMultiDTO data, MultipartFile[] file, String urlFile ) throws Exception 
	{
		valida(data.getNotifica(),data.getEnti(),data.getAllegati(),file);
		//Ricerca e risoluzione template
	    TemplateEmailDTO templateUlterioriDoc= new TemplateEmailDTO();
	    Gruppi gruppo = userUtil.getGruppo();
	    String codiceTemplate="ULTER_DOC_ED";
	    if(gruppo.equals(Gruppi.ED_)){
		codiceTemplate="ULTER_DOC_ED";
	    }else if(gruppo.equals(Gruppi.CL_)){
		codiceTemplate="ULTER_DOC_CL";
		
	    }
	    else if(gruppo.equals(Gruppi.SBAP_)) {
		codiceTemplate="ULTER_DOC_SOP";
		
	    }else if(gruppo.equals(Gruppi.ETI_)) {
		codiceTemplate="ULTER_DOC_ET";
	    }
	    templateUlterioriDoc = templateMailService.findAncheSuDefault(userUtil.getIntegerId(), codiceTemplate );
	    DettaglioCorrispondenzaDTO comunicazione = new DettaglioCorrispondenzaDTO();
	    comunicazione=comService.create(data.getIdFascicolo());
	    
	    List<AllegatiDTO> allegati = new ArrayList<AllegatiDTO>();

	    //allegati.add(allegatoUlterioreDoc);
	    comunicazione.setAllegatiInfo(allegati);
	    comunicazione.setDestinatari(data.getNotifica());
	    CorrispondenzaDTO corrispondenza = comunicazione.getCorrispondenza();
	    
	    PraticaDTO praticaDto= new PraticaDTO();
	    praticaDto.setId(data.getIdFascicolo());
	    praticaDto=praticaService.find(praticaDto);
	    String placeholders=templateUlterioriDoc.getPlaceholders();
	    
	    //inserimento allegato di ulteriore documentazione
	    int i = 0;
	    List<Integer> tipiContenuto = new ArrayList<Integer>();
	    tipiContenuto.add(1200);
	    String nomeContenuto=SezioneAllegati.ULTERIORE_DOCUMENTAZIONE.toString();
	    List<AllegatiDTO> listaAllegatiCaricati=new ArrayList<>();
	    final StringBuilder html = new StringBuilder();

	    for(AllegatiDTO a: data.getAllegati())
	    {
		AllegatiDTO allegatoUlterioreDoc = allegatiService.uploadAllegato(data.getIdFascicolo().toString(), tipiContenuto, nomeContenuto, file[i]);
		//allegatiService.update(a);
		//aggiorno descrizione e titolo
		allegatiService.updateDescrizioneTitolo(allegatoUlterioreDoc.getId(), a.descrizioneAttibute(), a.titoloAttribute());
		AllegatoCorrispondenzaDTO ac = new AllegatoCorrispondenzaDTO();
		ac.setIdAllegato(allegatoUlterioreDoc.getId());
		ac.setIdCorrispondenza(corrispondenza.getId());
		allegatoCorrispondenzaRepository.insert(ac);
		
		for (String ente : data.getEnti()) 
		    this.allegatiService.insertVisibilitaUlerioreDoc(allegatoUlterioreDoc.getId(), ente+"_");
		html.append("<ul><li>Nome file: <a href=\"" + urlFile 
		     +"?idDocumento=" + allegatoUlterioreDoc.getId() + "\">"
		     + HtmlUtils.htmlEscape(file[i].getOriginalFilename()) + "</a></li>"
		     +"<li>Titolo: " + HtmlUtils.htmlEscape(a.titoloAttribute())+ "</li>"
		     +"<li>Descrizione: " + HtmlUtils.htmlEscape(a.descrizioneAttibute()) + "</li></ul><br/>");
		i++;
	    }
	    Function<String,String> customResolving= (pl)->{
		if(pl.equals("FILES_ULTERIORE_DOCUMENTAZIONE")) {
		    return html.toString();
		}
		return pl;
	    };
	    
	    String testoEmail=resolveTemplate.risolviTesto(placeholders, templateUlterioriDoc.getTesto(), praticaDto, customResolving);
	    String oggettoEmail=resolveTemplate.risolviTesto("CODICE_FASCICOLO", templateUlterioriDoc.getOggetto(),praticaDto);
	    corrispondenza.setOggetto(oggettoEmail);
	    corrispondenza.setTesto(testoEmail);
	    corrispondenza.setVisibilita(String.join("_,",data.getEnti())+"_");
	    corrispondenza.setCodiceTemplate(codiceTemplate);
	    comunicazione.setCorrispondenza(corrispondenza);
	    
	    comService.saveComunication(comunicazione, praticaDto.getId());
	    comService.sendComunication(comunicazione, praticaDto.getId());
	    //invio in coda la sincronizzazione per diogene allineaDiogene(bean)
	    FascicoloStatoBean fsb=new FascicoloStatoBean();
	    fsb.setPratica(praticaDto);
	    fsb.setSezioniAllegati(List.of(SezioneAllegati.ULTERIORE_DOCUMENTAZIONE));
	    this.queueService.insertNewQueue(SendPlanToDiogeneScheduler.ID, fsb);
	    //invio a cds se attive...
	    if(ListUtil.isNotEmpty(listaAllegatiCaricati)) {
			aggiornaCds(praticaDto.getId(),listaAllegatiCaricati);
	    }
	}

	/**
	 * validazione bean per aggiunta ulteriore documentazione
	 * @author acolaianni
	 *
	 * @param data
	 * @param file
	 * @throws CustomOperationToAdviceException 
	 */
	private void valida(List<DestinatarioDTO> listaNotifica,
			List<String> listaEnti,List<? extends UlterioreDocumentazioneAttribute> listaAllegati, MultipartFile[] file) throws CustomOperationToAdviceException {
		StringBuilder message=new StringBuilder();
		if(file==null || file.length==0) {
			message.append("File mancanti<br>");
		}
		if(ListUtil.isEmpty(listaAllegati)) {
			message.append("Metadati allegati mancanti<br>");
		}
		if(ListUtil.isEmpty(listaEnti)){
			message.append("Lista visibile a mancante <br>");
		}
		if(ListUtil.isEmpty(listaNotifica)){
			message.append("Lista destinatari notifica vuota <br>");
		}
		int index=0;
		for(UlterioreDocumentazioneAttribute allegato:listaAllegati) {
			if(StringUtil.isEmpty(allegato.titoloAttribute())){
				message.append("Titolo allegato vuoto <br>");
			}
			if(StringUtil.isEmpty(allegato.descrizioneAttibute())){
				message.append("Descrizione allegato vuoto <br>");	
			}
			if(file[index]==null || StringUtil.isEmpty(file[index].getName())) {
				message.append("File allegato mancante <br>");
			}
			index++;
		}
		if(StringUtil.isNotEmpty(message.toString())) {
			throw new CustomOperationToAdviceException("Impossibile procedere errori nella richiesta:<br>"+message.toString());
		}
	}

	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public PaginatedList<AllegatiDTO> search(UlterioreDocSearch search) throws Exception {
		return this.ulterioreDocDao.search(search);
	}
	
	/**
	 * 
	 * @author acolaianni
	 *
	 * @param comunicazione
	 * @param idPratica
	 * @param listaAllegatiCompleta
	 * @param tipiContenuto 
	 */
	private void aggiornaCds(UUID idPratica, List<AllegatiDTO> listaAllegatiCompleta) {
		logger.info("Aggiornamento cds a partire da ulterioreDocumentazione uuidPratica {}, idComunicazione {}",idPratica);
		if(ListUtil.isEmpty(listaAllegatiCompleta)) {
			logger.info("Nessun aggiornamento cds a partire da ulterioreDocumentazione uuidPratica {}, idComunicazione {}, lista allegati vuota",idPratica);
			return;
		}
		for(AllegatiDTO allegato:listaAllegatiCompleta) {
			try {
				SezioneAllegati sezioneAllegato= SezioneAllegati.ULTERIORE_DOCUMENTAZIONE;
				this.cdsService.appendiDocumentoACds(idPratica, sezioneAllegato, allegato);
			} catch (JsonProcessingException | SQLException e) {
				logger.error("Errore durante l'aggiornamento della cds a partire da ulteriore documentazione allegata uuidPratica {}, idComunicazione {}, errore {}",idPratica,e);
			}	
		}
	}
	
}
