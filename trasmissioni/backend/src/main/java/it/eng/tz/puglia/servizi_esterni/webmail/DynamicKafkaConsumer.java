package it.eng.tz.puglia.servizi_esterni.webmail;

import static org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.io.IOUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import it.eng.tz.puglia.autpae.dto.ConfigurazioneCasellaPostaleDTO;
import it.eng.tz.puglia.autpae.entity.CorrispondenzaDTO;
import it.eng.tz.puglia.autpae.entity.DestinatarioDTO;
import it.eng.tz.puglia.autpae.entity.RicevutaDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoErrore;
import it.eng.tz.puglia.autpae.enumeratori.TipoRicevuta;
import it.eng.tz.puglia.autpae.search.ConfigurazioneCasellaPostaleSearch;
import it.eng.tz.puglia.autpae.search.CorrispondenzaSearch;
import it.eng.tz.puglia.autpae.service.interfacce.ConfigurazioneCasellaPostaleService;
import it.eng.tz.puglia.autpae.service.interfacce.ConfigurazionePECService;
import it.eng.tz.puglia.autpae.service.interfacce.CorrispondenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.DestinatarioService;
import it.eng.tz.puglia.autpae.service.interfacce.RicevutaService;
import it.eng.tz.puglia.service.http.IHttpClientService;
import it.eng.tz.puglia.servizi_esterni.webmail.feign.Webmail;
import it.eng.tz.puglia.servizi_esterni.webmail.internal_event.KafkaConsumerEvent;
import it.eng.tz.puglia.servizi_esterni.webmail.service.WebmailService;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.regione.puglia.webmail.be.dto.AttivaDisattivaCasellaPostaleDto;
import it.eng.tz.regione.puglia.webmail.be.dto.BaseResponse;
import it.eng.tz.regione.puglia.webmail.be.dto.ConfigurazioneCasellaPostaleDto;
import it.eng.tz.regione.puglia.webmail.be.dto.ConfigurazioneCasellaPostaleResponseDto;
import it.eng.tz.regione.puglia.webmail.be.dto.DestinatariPecDto;
import it.eng.tz.regione.puglia.webmail.be.dto.ReceivedMailHolder;
import it.eng.tz.regione.puglia.webmail.be.dto.RicevutaPecDto;
import it.eng.tz.regione.puglia.webmail.be.enumerations.TipoPostaCertificataEnum;

@Component
@Scope("singleton")
public class DynamicKafkaConsumer{
	/**
	 * evento lanciato per lanciare il metodo di restart
	 */
	public static final String EVENT_RESTART_KAFKA="restartKafka";
	
	private static final Logger logger = LoggerFactory.getLogger(DynamicKafkaConsumer.class.getName());

	private static final String PATH_SEPARATOR = "/";
	
	private static final String CORRISPONDENZA = "Corrispondenza";
	
	
	@Value("${abilita.scheduling}")
	private String abilitaScheduling;
	
	@Value("${microservice.kafka.url}")
	private String brokerAddress;

	private String topicName;
	private String groupId;

	@Value("${microservice.webmail.url}")
	private String baseUrl;
	
	@Value("${alfresco.path.base}")
	private String cmsRootPath;
	
	@Value("${cms.codice.applicazione}")
	private String codiceApplicazione;
	
	@Value("${cms.url.upload}")
	private String uploadUrl;

	@Autowired
	private Webmail webmailFeign;
	
	@Autowired
	private ObjectMapper obj;
	
	@Autowired
	@Qualifier("casellaMittenteApplicazione")
	private ConfigurazioneCasellaPostaleDto ccpd;
	
	@Autowired 
	private IHttpClientService cmsService;

	@Autowired
	private ConfigurazioneCasellaPostaleService configurazioneCasellaPostaleService;
	
	@Autowired
	private CorrispondenzaService corrispondenzaService;
	
	@Autowired
	private DestinatarioService destinatarioService;
	
	@Autowired
	private RicevutaService ricevutaService;
	
	@Autowired
	private ConfigurazionePECService confPecSvc;
	
	List<ConcurrentMessageListenerContainer<String, ReceivedMailHolder>> listContainer= new ArrayList<ConcurrentMessageListenerContainer<String, ReceivedMailHolder>>();
	
	
	public DynamicKafkaConsumer() { 
		logger.info("Avviato kafka consumer....");
	}
	

	private Optional<ConfigurazioneCasellaPostaleResponseDto> configuraEAttivaCasellaPostale() throws Exception {
		try {
			ResponseEntity<BaseResponse<ConfigurazioneCasellaPostaleResponseDto>> esitoConfigurazione = webmailFeign.configuraCasellaPostale(ccpd);
			if(esitoConfigurazione.getStatusCode().is2xxSuccessful()){
				ConfigurazioneCasellaPostaleResponseDto ccprd = esitoConfigurazione.getBody().getPayload();
				if( logger.isInfoEnabled() ){
					logger.info("Ottenuto ccprd {}", ccprd);
				}
				attivaDisattivaCasellaPosta(ccprd);
				return Optional.of(ccprd);
			}
		} catch (Exception e) {
			logger.error("Errore nella configurazione/attivazione della casella postale", e);
			throw e;
		} 
		return Optional.empty();
	}
	
	private void attivaDisattivaCasellaPosta(ConfigurazioneCasellaPostaleResponseDto ccprd) throws Exception {
		AttivaDisattivaCasellaPostaleDto attivaDisattivaCasellaPostaleDto = new AttivaDisattivaCasellaPostaleDto();
		attivaDisattivaCasellaPostaleDto.setPk(ccprd.getIdCasellaPostale());
		attivaDisattivaCasellaPostaleDto.setIndirizzoMail(ccprd.getIndirizzoMail());
		attivaDisattivaCasellaPostaleDto.setAttiva(true);
		ResponseEntity<BaseResponse<Boolean>> result = webmailFeign.attivaDisattivaCasellaPosta(attivaDisattivaCasellaPostaleDto);
		if (result.getStatusCode().is2xxSuccessful()) {
			logger.info("Attivazione casella postale {} terminata con successo", ccprd.getIdCasellaPostale());
		}
		else {
			logger.error("Errore nell' attivazione della casella postale {}", ccprd.getIdCasellaPostale());
			throw new Exception("Errore nell' attivazione della casella postale " + ccprd.getIdCasellaPostale());
		}
	}
	
	private Boolean configurazionePresenteNelDbAutpae(String email) throws Exception {
		ConfigurazioneCasellaPostaleSearch filter = new ConfigurazioneCasellaPostaleSearch();
		filter.setEmail(email);
		Long resultCount = configurazioneCasellaPostaleService.count(filter);
		if (resultCount > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private Boolean configurazionePresenteNelDbRemoto(String email) throws Exception {
		ResponseEntity<BaseResponse<ConfigurazioneCasellaPostaleDto>> response = webmailFeign.getConfigurazioneCasellaPostale(email);
		if (response.getStatusCode().is2xxSuccessful()) {
			ConfigurazioneCasellaPostaleDto payload = response.getBody().getPayload();
			if (payload != null) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			logger.error("Errore nel feign getConfigurazioneCasellaPostale");
			throw new Exception("Errore nel feign getConfigurazioneCasellaPostale");
		}
		
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	private Optional<ConfigurazioneCasellaPostaleResponseDto> controllaEconfigura(String email) throws Exception {
		try {
			if (configurazionePresenteNelDbAutpae(email)) {
				if (configurazionePresenteNelDbRemoto(email)) {
					ConfigurazioneCasellaPostaleDTO ccpDTO = configurazioneCasellaPostaleService.find(email);
					if (ccpDTO != null) {
						ConfigurazioneCasellaPostaleResponseDto confDto = obj.readValue(ccpDTO.getConfigurazione(), ConfigurazioneCasellaPostaleResponseDto.class);
						return Optional.of(confDto);
					}
					else {
						logger.error("Errore nel find della configurazione_casella_postale");
						throw new Exception("Errore nel find della tabella configurazione_casella_postale");
					}
				}
				else {
					Optional<ConfigurazioneCasellaPostaleResponseDto> configurazioneCasellaPostale = configuraEAttivaCasellaPostale();
					if (configurazioneCasellaPostale.isPresent()) {
						ConfigurazioneCasellaPostaleResponseDto confCasellaPostale = configurazioneCasellaPostale.get();
						ConfigurazioneCasellaPostaleDTO ccpDTO = WebmailService.creaDtoFromResponse(confCasellaPostale);
						Integer resultUpdate = configurazioneCasellaPostaleService.update(ccpDTO);
						if (resultUpdate > 0) {
							return configurazioneCasellaPostale;
						}
						else {
							logger.error("Errore nell'update della tabella configurazione_casella_postale");
							throw new Exception("Errore nell'update della tabella configurazione_casella_postale");
						}
					}
					else {
						logger.error("errore nella configurazione della casella postale");
						return Optional.empty();
					}
				}
			}
			else {
				if (configurazionePresenteNelDbRemoto(email)) {
					ResponseEntity<BaseResponse<ConfigurazioneCasellaPostaleDto>> configurazioneCasellaPostale = webmailFeign.getConfigurazioneCasellaPostale(email);
					if (configurazioneCasellaPostale.getStatusCode().is2xxSuccessful()) {
						ConfigurazioneCasellaPostaleDto configurazioneCasellaPostaleDto = configurazioneCasellaPostale.getBody().getPayload();
						ConfigurazioneCasellaPostaleResponseDto responseDto = new ConfigurazioneCasellaPostaleResponseDto();
						responseDto.setIndirizzoMail(configurazioneCasellaPostaleDto.getCasellaPostale().getIndirizzoMail());
						responseDto.setIdCasellaPostale(configurazioneCasellaPostaleDto.getCasellaPostale().getPk().toString());
						responseDto.setNomeTopiRicezioneMessaggi(configurazioneCasellaPostaleDto.getCasellaPostale().getPk().toString());
						ConfigurazioneCasellaPostaleDTO ccpDTO = WebmailService.creaDtoFromResponse(responseDto);
						String resultInsert = configurazioneCasellaPostaleService.insert(ccpDTO);
						if (!resultInsert.isBlank()) {
							return Optional.of(responseDto);
						}
						else {
							logger.error("Errore nella insert della tabella configurazione_casella_postale");
							throw new Exception("Errore nella insert della tabella configurazione_casella_postale");
						}
					}
					else {
						logger.error("Errore nel feign getConfigurazioneCasellaPostale");
						throw new Exception("Errore nel feign getConfigurazioneCasellaPostale");
					}
				}
				else {
					Optional<ConfigurazioneCasellaPostaleResponseDto> configurazioneCasellaPostale = configuraEAttivaCasellaPostale();
					if (configurazioneCasellaPostale.isPresent()) {
						ConfigurazioneCasellaPostaleResponseDto confCasellaPostale = configurazioneCasellaPostale.get();
						ConfigurazioneCasellaPostaleDTO ccpDTO = WebmailService.creaDtoFromResponse(confCasellaPostale);
						String resultInsert = configurazioneCasellaPostaleService.insert(ccpDTO);
						if (!resultInsert.isBlank()) {
							return configurazioneCasellaPostale;
						}
						else {
							logger.error("Errore nella insert della tabella configurazione_casella_postale");
							throw new Exception("Errore nella insert della tabella configurazione_casella_postale");
						}
					}
					else {
						logger.error("errore nella configurazione della casella postale");
						return Optional.empty();
					}
				}
			}
		} catch (Exception e) {
			logger.error("Errore nella configurazione della casella postale ",e);
			throw new Exception("Errore nella configurazione della casella postale");
		}
	}
	
//	private List<String> creaListaEmailDestinatario(List<DestinatariPecDto> listDestinatariPecDto) {
//		List<String> listEmailDestinatario = new ArrayList<String>();
//		listDestinatariPecDto.forEach(destinatario -> {
//			String emailDestinatario = destinatario.getDestinatario();
//			listEmailDestinatario.add(emailDestinatario);
//		});
//		return listEmailDestinatario;
//	}
	
	private void aggiornoCampoPec(List<DestinatarioDTO> listDestinatarioDTO, List<DestinatariPecDto> listDestinatariPecDto) throws Exception {
		List<Long> listIdDestinatarioCertificato = new ArrayList<Long>();
		List<Long> listIdDestinatarioEsterno = new ArrayList<Long>();
		listDestinatarioDTO.forEach(destinatarioDTO -> {
			String email = destinatarioDTO.getEmail();
			listDestinatariPecDto.forEach(destinatarioPecDto -> {
				if (destinatarioPecDto.getDestinatario().equals(email)) {
					if (destinatarioPecDto.getTipoDesinatario().equalsIgnoreCase("certificato")) {
						Long idDestinatario = destinatarioDTO.getId();
						listIdDestinatarioCertificato.add(idDestinatario);
					}
					else if (destinatarioPecDto.getTipoDesinatario().equalsIgnoreCase("esterno")) {
						Long idDestinatario = destinatarioDTO.getId();
						listIdDestinatarioEsterno.add(idDestinatario);
					}
				}
			});
		});
		if (!listIdDestinatarioCertificato.isEmpty()) {
			try {
				int resultUpdate = destinatarioService.updateFieldPec(listIdDestinatarioCertificato, true);
				if (resultUpdate <= 0) {
					logger.error("Errore durante l'aggiornamento del campo pec nella tabella destinatario");
					throw new Exception("Errore durante l'aggiornamento del campo pec nella tabella destinatario");
				}
			} catch (Exception e) {
				logger.error("Errore durante l'aggiornamento del campo pec nella tabella destinatario ", e);
				throw e;
			}
		}
		if (!listIdDestinatarioEsterno.isEmpty()) {
			try {
				int resultUpdate = destinatarioService.updateFieldPec(listIdDestinatarioEsterno, false);
				if (resultUpdate <= 0) {
					logger.error("Errore durante l'aggiornamento del campo pec nella tabella destinatario");
					throw new Exception("Errore durante l'aggiornamento del campo pec nella tabella destinatario");
				}
			} catch (Exception e) {
				logger.error("Errore durante l'aggiornamento del campo pec nella tabella destinatario ", e);
				throw e;
			}
		}
	}
	
	private void inseriscoRicevute(Long idCorrispondenza, List<DestinatarioDTO> listDestinatarioDTO, RicevutaPecDto rpd, String contentEml, String contentDaticert, String contentSmime) throws Exception {
		List<RicevutaDTO> listRicevutaDTO = new ArrayList<RicevutaDTO>();
		listDestinatarioDTO.forEach(destinatarioDTO -> {
			RicevutaDTO ricevutaDTO = new RicevutaDTO();
			ricevutaDTO.setIdCorrispondenza(idCorrispondenza);
			ricevutaDTO.setIdDestinatario(destinatarioDTO.getId());
			ricevutaDTO.setTipoRicevuta(TipoRicevuta.fromString(rpd.getTipoRicevuta()));
			ricevutaDTO.setErrore(TipoErrore.fromString(rpd.getErrore()));
			ricevutaDTO.setDescrizioneErrore(rpd.getDati().getErroreEsteso()); // forse sbagliato
			ricevutaDTO.setIdCmsEml(contentEml);
			ricevutaDTO.setIdCmsDatiCert(contentDaticert);
			ricevutaDTO.setIdCmsSmime(contentSmime);
			ricevutaDTO.setData(rpd.getDati().getDataRicevuta());
			listRicevutaDTO.add(ricevutaDTO);
		});
		if(ListUtil.isEmpty(listDestinatarioDTO)) {
			//inserisco la ricevuta con nessun destinatario....caso di accettazinoe con nessun destinatario
			RicevutaDTO ricevutaDTO = new RicevutaDTO();
			ricevutaDTO.setIdCorrispondenza(idCorrispondenza);
			ricevutaDTO.setIdDestinatario(null);
			ricevutaDTO.setTipoRicevuta(TipoRicevuta.fromString(rpd.getTipoRicevuta()));
			ricevutaDTO.setErrore(TipoErrore.fromString(rpd.getErrore()));
			ricevutaDTO.setDescrizioneErrore(rpd.getDati().getErroreEsteso()); // forse sbagliato
			ricevutaDTO.setIdCmsEml(contentEml);
			ricevutaDTO.setIdCmsDatiCert(contentDaticert);
			ricevutaDTO.setIdCmsSmime(contentSmime);
			ricevutaDTO.setData(rpd.getDati().getDataRicevuta());
			listRicevutaDTO.add(ricevutaDTO);
		}
		Integer resultInsert = ricevutaService.insertMultipla(listRicevutaDTO);
		if (resultInsert <= 0) {
			logger.error("Errore durante la insert della ricevuta");
			throw new Exception("Errore durante la insert della ricevuta");
		}
	}
	
	
	private List<String> destinatariRicevuta(RicevutaPecDto rpd) {
		List<String> destinatari = new ArrayList<String>();
		if (rpd.getTipoRicevuta().equalsIgnoreCase(TipoPostaCertificataEnum.ACCETTAZIONE.getTextValue())
				|| rpd.getTipoRicevuta().equalsIgnoreCase(TipoPostaCertificataEnum.NON_ACCETTAZIONE.getTextValue())) {
			// qui i destinatari li prendo da datiRicevuta....
			rpd.getIntestazione().getDestinatari().forEach(destinatario -> {
				destinatari.add(destinatario.getDestinatario());
			});
		} else { // caso consegna
			// qui i destinatari li prendo da consegna
			destinatari.add(rpd.getDati().getConsegna());
		}
		return destinatari;
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	private void elaboraMessaggio(ConsumerRecord<String, ReceivedMailHolder> record){
		ReceivedMailHolder messaggioRicevuto = record.value();
		elaboraEvento(messaggioRicevuto);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public void elaboraMessaggio(ReceivedMailHolder messaggioRicevuto){
		elaboraEvento(messaggioRicevuto);
	}


	private void elaboraEvento(ReceivedMailHolder messaggioRicevuto ) {
		if( logger.isInfoEnabled() ){
			logger.info("Ricevuto messaggio {} su topic {}", messaggioRicevuto, topicName);
		}
		//Scarico la mail EML
		if( messaggioRicevuto.getInformazioniMail() != null ) {
		}else{//ricevuta pec
			//Gestisco la ricevuta
			RicevutaPecDto rpd = messaggioRicevuto.getInformazioniRicevuta();
			String idRicevuta = rpd.getPkRicevuta();
			String idMessaggio = rpd.getDati().getIdMessaggio();
			String identificativo = rpd.getDati().getIdentificativo();
			List<DestinatariPecDto> listDestinatariPecDto = rpd.getIntestazione().getDestinatari();
			//List<String> listEmailDestinatario = creaListaEmailDestinatario(listDestinatariPecDto);
			List<String> listEmailDestinatario = this.destinatariRicevuta(rpd);
			try{
				CorrispondenzaSearch corrispondenzaSearch = new CorrispondenzaSearch();
				corrispondenzaSearch.setMessageId(idMessaggio);
				List<CorrispondenzaDTO> listCorrispondenzaDTO = corrispondenzaService.search(corrispondenzaSearch).getList();
				if (listCorrispondenzaDTO != null && !listCorrispondenzaDTO.isEmpty()) {
					Long idCorrispondenza = listCorrispondenzaDTO.get(0).getId();
					List<DestinatarioDTO> listDestinatarioDTO = destinatarioService.searchByIdCorrispondenzaAndEmails(idCorrispondenza, listEmailDestinatario);
					if (listDestinatarioDTO != null && !listDestinatarioDTO.isEmpty()) {
						aggiornoCampoPec(listDestinatarioDTO, listDestinatariPecDto);
						//messo perchè è capitato che la retrieve eml non trovava l'id passato,
						//come se la persist su db dal task di ricezione mail non era stato ancora concluso...
						Thread.sleep(2000);
						ResponseEntity<Resource> response = webmailFeign.retrieveEml(idRicevuta); // capire se questo servizio oppure l'altro
						if (response.getStatusCode().is2xxSuccessful()) {
							Resource eml = response.getBody();
							Map<String, String> resultUpload = uploadEml(eml, idMessaggio, identificativo);
							String contentEml = resultUpload.get("content");
							String contentDaticert = "";
							String contentSmime = "";
							inseriscoRicevute(idCorrispondenza, listDestinatarioDTO, rpd, contentEml, contentDaticert, contentSmime);
						}
						else {
							logger.error("Errore durante il retrieve della mail");
							throw new Exception("Errore durante il retrieve della mail");
						}
					}
					else {
						logger.info("Nessun destinatario trovato associato ad una corrispondenza con id: {} e con email tra queste: {}", idCorrispondenza, listEmailDestinatario);
					}
				}
				else {
					logger.info("Nessuna corrispondenza trovata con message_id {}", idMessaggio);
				}
			} catch (Exception e) {
				logger.error("Errore nella gestione della ricevuta con id: {}", idRicevuta, e);
			}
		}
	}
	
	@PostConstruct
	private void start() throws Exception {
		logger.info("Start DynamicKafkaConsumer");
		this.confPecSvc.caricaDefault();
		this.confPecSvc.initBeanCasellaMittenteApplicazione();
		if(!this.abilitaScheduling.equals("S")) {
			return; //non faccio partire nulla....
		} 
		try {
			//invio la configurazione al server dell'email di default se c'è una modifica verrà recepita dal servizio
			configuraEAttivaCasellaPostale();
			// inserisce la configurazione dell'email principale se non presente
			this.controllaEconfigura(ccpd.getCasellaPostale().getIndirizzoMail());
			// disattivazione delle email non presenti nella tabella enti
			List<ConfigurazioneCasellaPostaleDTO> caselleDadisativare = configurazioneCasellaPostaleService.getCaselleDaDisattivare();
			for (ConfigurazioneCasellaPostaleDTO itemConfCasellaPostaleDTO : caselleDadisativare) {
				disattivaCasellaPosta(itemConfCasellaPostaleDTO);
			}
			//creazione dei nuovi processi in ascolto di eventi da kafka 
			List<ConfigurazioneCasellaPostaleDTO> listCongigEmail;
			//lista delle email da configurare compresa quella principale escluse quelle non presenti in enti 
			listCongigEmail = configurazioneCasellaPostaleService.getCaselleDaAttivare();
			for (ConfigurazioneCasellaPostaleDTO itemListConfigEmail : listCongigEmail) {
				this.addListenerContainer(itemListConfigEmail);
			}
		} catch (Exception e) {
			logger.error("Errore in start ",e);
		}
			
	}

	@PreDestroy
	public void destroy() {
		logger.info("SONO ENTRATO NEL METODO DESTROY");
	}
	
	private Map<String, String> uploadEml(Resource eml, String idMailOriginale, String identificativo) throws Exception {
		Map<String, String> result = new HashMap<String, String>();
		String pathCms = StringUtil.concateneString(cmsRootPath, generaUploadPath(idMailOriginale.replaceAll("[<>]", "")));
	//	String contentType = "message/rfc822";
		String nomeFileEml = identificativo.replaceAll("[<>]", "") + ".eml";
		Path tmpDir = Files.createTempDirectory("uploadEml");
		Path tmpFile = Files.createTempFile(tmpDir, "",nomeFileEml);
		File fileEml = tmpFile.toFile();
		InputStream emlInputStream = eml.getInputStream();
		Files.copy(emlInputStream, fileEml.toPath(), StandardCopyOption.REPLACE_EXISTING);
		IOUtils.closeQuietly(emlInputStream);
	//	Long size = FileUtils.sizeOf(fileEml);
		//AlfrescoUploadInputDTO documento = new AlfrescoUploadInputDTO(nomeFileEml, nomeFileEml, pathCms, contentType, size, codiceApplicazione);
		//CmsResponseBean cmsResponse = alfrescoService.uploadAlfresco(documento);
		String idCms=cmsService.uploadCms(fileEml, uploadUrl, pathCms, codiceApplicazione.toLowerCase(),true);
		//if (!cmsResponse.getId().isBlank()) {
		if (StringUtil.isNotEmpty(idCms)) {
			//result.put("content", cmsResponse.getId());
			result.put("content", idCms);
			result.put("path", pathCms);
			return result;
		}
		else {
			logger.error("Errore durante l'upload su Alfresco");
			throw new Exception("Errore durante l'upload su Alfresco");
		}
	}
	
	private String generaUploadPath(String idMailOriginale) {
		StringBuilder path = new StringBuilder(PATH_SEPARATOR);
		return path.append(CORRISPONDENZA)
				   .append(PATH_SEPARATOR)
				   .append(idMailOriginale)
				   .toString();
	}

	private DefaultKafkaConsumerFactory<String, ReceivedMailHolder> consumerFactory(String brokerAddress, String groupId) {
		return new DefaultKafkaConsumerFactory<>(
				consumerConfig(brokerAddress, groupId),
				new StringDeserializer(),
				new JsonDeserializer<>(ReceivedMailHolder.class));
	}

	private ContainerProperties containerProperties(String topic, MessageListener<String, ReceivedMailHolder> messageListener) {
		ContainerProperties containerProperties = new ContainerProperties(topic);
		containerProperties.setMessageListener(messageListener);
		return containerProperties;
	}

	private Map<String, Object> consumerConfig(String brokerAddress, String groupId) {
		return Map.of (
						BOOTSTRAP_SERVERS_CONFIG, brokerAddress,
						GROUP_ID_CONFIG, groupId,
						AUTO_OFFSET_RESET_CONFIG, "earliest"
		//				ALLOW_AUTO_CREATE_TOPICS_CONFIG, "false"
					  );
	}
	
	public void addListenerContainer(ConfigurazioneCasellaPostaleDTO itemListConfigEmail) {
		Optional<ConfigurazioneCasellaPostaleResponseDto> configurazioneCasellaPostale;
		try {
			configurazioneCasellaPostale = this.controllaEconfigura(itemListConfigEmail.getEmail());
		
			if (configurazioneCasellaPostale.isPresent()) {
				topicName = configurazioneCasellaPostale.get().getNomeTopiRicezioneMessaggi();
				groupId = configurazioneCasellaPostale.get().getNomeTopiRicezioneMessaggi();
				//ATTENZIONE: I messaggi dai topic Kafka non sono cancellati una volta consumati.
				//È compito del consumatore gestire opportunamente i messaggi ed evitare i doppioni
				MessageListener<String, ReceivedMailHolder> messageListener = this::elaboraMessaggio;
				
				ConcurrentMessageListenerContainer<String, ReceivedMailHolder> container =
						new ConcurrentMessageListenerContainer<>(
								consumerFactory(brokerAddress, groupId),
								containerProperties(topicName, messageListener));
				if(this.abilitaScheduling.equals("S")) {
					this.listContainer.add(container);
					container.start();
				}
			}
			else {
				logger.error("Attenzione non è stato possibile configurare la casella postale: {}", configurazioneCasellaPostale.toString());
			}
		} catch (Exception e) {
			logger.error("error  in addListenerContainer",e);
		}
	}
	
	private void disattivaCasellaPosta(ConfigurazioneCasellaPostaleDTO ccprd) throws Exception {
		String jsonConfig=ccprd.getConfigurazione();
		Gson gson = new Gson();
		
		ConfigurazioneCasellaPostaleResponseDto casellaPostaleDto = new ConfigurazioneCasellaPostaleResponseDto();
		casellaPostaleDto = gson.fromJson(jsonConfig, ConfigurazioneCasellaPostaleResponseDto.class);
		AttivaDisattivaCasellaPostaleDto attivaDisattivaCasellaPostaleDto= new AttivaDisattivaCasellaPostaleDto();
		attivaDisattivaCasellaPostaleDto.setPk(casellaPostaleDto.getIdCasellaPostale());
		attivaDisattivaCasellaPostaleDto.setIndirizzoMail(casellaPostaleDto.getIndirizzoMail());
		attivaDisattivaCasellaPostaleDto.setAttiva(false);
		ResponseEntity<BaseResponse<Boolean>> result = webmailFeign.attivaDisattivaCasellaPosta(attivaDisattivaCasellaPostaleDto);
		if (result.getStatusCode().is2xxSuccessful()) {
			logger.info("Attivazione casella postale {} terminata con successo", attivaDisattivaCasellaPostaleDto.getPk());
		}
		else {
			logger.error("Errore nell' attivazione della casella postale {}", attivaDisattivaCasellaPostaleDto.getPk());
			throw new Exception("Errore nell' attivazione della casella postale " + attivaDisattivaCasellaPostaleDto.getPk());
		}
	}
	
	public void restart() throws Exception {
		//kill di tutti i processi in ascolto
		synchronized (this) {
			for (ConcurrentMessageListenerContainer<String, ReceivedMailHolder> itemcontainer : listContainer) {
				itemcontainer.stop();
			}
			this.start();	
		}
	}


	@Override
	public String toString() {
		return "DynamicKafkaConsumer [brokerAddress=" + brokerAddress + ", topicName=" + topicName + ", groupId="
				+ groupId + "]";
	}
	
	@EventListener(KafkaConsumerEvent.class)
	void handleKafkaConsumerEvent(KafkaConsumerEvent event) throws Exception {
		logger.info("Received evento restart kafka....");
		this.restart();
		
	}

}

