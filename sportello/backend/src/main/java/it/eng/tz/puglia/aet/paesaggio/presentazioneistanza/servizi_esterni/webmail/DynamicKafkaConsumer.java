package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.webmail;

import static org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AlfrescoPaths;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoErrore;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoRicevuta;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ConfigurazioneCasellaPostaleDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.CorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinatarioDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.FascicoloCorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.RicevutaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.CorrispondenzaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.DestinatarioRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.FascicoloCorrispondenzaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.ConfigurazioneCasellaPostaleSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.CorrispondenzaSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.FascicoloCorrispondenzaSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl.ConfigurazioneCasellaPostaleService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl.RicevutaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.webmail.feign.Webmail;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.service.http.IHttpClientService;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.regione.puglia.webmail.be.dto.AttivaDisattivaCasellaPostaleDto;
import it.eng.tz.regione.puglia.webmail.be.dto.BaseResponse;
import it.eng.tz.regione.puglia.webmail.be.dto.ConfigurazioneCasellaPostaleDto;
import it.eng.tz.regione.puglia.webmail.be.dto.ConfigurazioneCasellaPostaleResponseDto;
import it.eng.tz.regione.puglia.webmail.be.dto.DestinatariPecDto;
import it.eng.tz.regione.puglia.webmail.be.dto.ReceivedMailHolder;
import it.eng.tz.regione.puglia.webmail.be.dto.RicevutaPecDto;

@Component
@Scope("singleton")
public class DynamicKafkaConsumer {

	private static final Logger logger = LoggerFactory.getLogger(DynamicKafkaConsumer.class.getName());

	private static final String PATH_SEPARATOR = "/";
	
	private static final String CORRISPONDENZA = "Corrispondenza";

	private String topicName;

	private String groupId;
	
	List<ConcurrentMessageListenerContainer<String, ReceivedMailHolder>> listContainer= new ArrayList<ConcurrentMessageListenerContainer<String, ReceivedMailHolder>>();

	@Value("${microservice.kafka.url}")
	private String brokerAddress;

	@Value("${microservice.webmail.url}")
	private String baseUrl;
	
	@Value("/${cms.path.base}")
	private String cmsRootPath;
	
//	@Value("${cms.temp.path}")
//	private String cmsFilesystemTempPath;
	
	@Value("${spring.application.name}")
	private String codiceApplicazione;
	
//	@Value("${casella.postale.config.webmail}")
//	private String nomeJsonDiConfigurazione;
//	
//	@Value("${email.registration.sender}")
//	private String emailPec;
	
	@Value("${abilita.ricezionemail}")
	private String ricezionemail;
	
	@Value("${cms.url.upload}")
	private String uploadUrl;
	
	@Autowired
	@Qualifier("casellaMittenteApplicazione")
	private ConfigurazioneCasellaPostaleDto ccpd;
	
	@Autowired
	private Webmail webmailFeign;
	
	@Autowired
	private ObjectMapper obj;

	@Autowired 
	private IHttpClientService cmsService;

	//@Autowired
	//private IFeignAlfrescoService alfrescoService;
	
	@Autowired
	private ConfigurazioneCasellaPostaleService confCasellaPostaleService ;
	
	@Autowired
	private CorrispondenzaRepository corrispondenzaRepository;
	@Autowired
	private FascicoloCorrispondenzaRepository fascCorrDao;
	
	@Autowired
	private DestinatarioRepository destinatarioRepo;	
	
	@Autowired
	private RicevutaService ricevutaService;

	@Autowired
	private PraticaRepository praticaDao;
	
	public DynamicKafkaConsumer() {
		logger.info("costruttore");
	}
	
	/**
	 *Funzione per inviare al servizio di webmail la configurazione locale presa dal file json  
	 *
	 */
	private Optional<ConfigurazioneCasellaPostaleResponseDto> configuraEAttivaCasellaPostale() throws Exception {
		try {
//			ConfigurazioneCasellaPostaleDto ccpd = new ConfigurazioneCasellaPostaleDto();
//			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("configurazione_casella_postale/" + nomeJsonDiConfigurazione);
			//ccpd = obj.readValue(inputStream, ConfigurazioneCasellaPostaleDto.class);
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
	
	private Boolean configurazionePresenteNelDbLocale(String email) throws Exception {
		ConfigurazioneCasellaPostaleSearch search = new ConfigurazioneCasellaPostaleSearch();
		search.setEmail(email);
		int resultCount = confCasellaPostaleService.search(search).getCount();
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
	
	/**
	 * Funzione che ttrasforma in DTO la risposta del server
	 *
	 */
	public ConfigurazioneCasellaPostaleDTO creaDtoFromResponse(ConfigurazioneCasellaPostaleResponseDto response) {
		ConfigurazioneCasellaPostaleDTO ccpDTO = new ConfigurazioneCasellaPostaleDTO();
		ccpDTO.setEmail(response.getIndirizzoMail());
		Gson gson = new Gson();
		String json = gson.toJson(response);
		ccpDTO.setConfigurazione(json);
		return ccpDTO;
	}
	
	/**
	 *Funzione che serve per verificare l'esistenza della configurazione dell'email all'interno del db locale
	 *esterno e per richiamare la funzione di invio di configurazione al servizio esterno 
	 *
	 **/
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE,propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	private Optional<ConfigurazioneCasellaPostaleResponseDto> controllaEconfigura(String email) throws Exception {
		try {
			if (configurazionePresenteNelDbLocale(email)) {
				if (configurazionePresenteNelDbRemoto(email)) {
					//Configurazione email presente sia in locale che sul servizio remoto
					logger.info("controllaEconfigura - la configurazione è presente sia in locale che sul server");
					ConfigurazioneCasellaPostaleSearch search= new ConfigurazioneCasellaPostaleSearch();
					search.setEmail(email);
					ConfigurazioneCasellaPostaleDTO ccpDTO = confCasellaPostaleService.search(search).getList().get(0);
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
					//Configurazione email presente in locale ma non sul servizio remoto
					logger.info("controllaEconfigura - la configurazione è presente in locale ma non sul server");
					Optional<ConfigurazioneCasellaPostaleResponseDto> configurazioneCasellaPostale = configuraEAttivaCasellaPostale();
					if (configurazioneCasellaPostale.isPresent()) {
						ConfigurazioneCasellaPostaleResponseDto confCasellaPostale = configurazioneCasellaPostale.get();
						ConfigurazioneCasellaPostaleDTO ccpDTO = creaDtoFromResponse(confCasellaPostale);
						Integer resultUpdate = confCasellaPostaleService.update(ccpDTO);
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
					//Configurazione email non presente in locale ma presente sul servizio remoto
					ResponseEntity<BaseResponse<ConfigurazioneCasellaPostaleDto>> configurazioneCasellaPostale = webmailFeign.getConfigurazioneCasellaPostale(email);
					if (configurazioneCasellaPostale.getStatusCode().is2xxSuccessful()) {
						ConfigurazioneCasellaPostaleDto configurazioneCasellaPostaleDto = configurazioneCasellaPostale.getBody().getPayload();
						ConfigurazioneCasellaPostaleResponseDto responseDto = new ConfigurazioneCasellaPostaleResponseDto();
						responseDto.setIndirizzoMail(configurazioneCasellaPostaleDto.getCasellaPostale().getIndirizzoMail());
						responseDto.setIdCasellaPostale(configurazioneCasellaPostaleDto.getCasellaPostale().getPk().toString());
						responseDto.setNomeTopiRicezioneMessaggi(configurazioneCasellaPostaleDto.getCasellaPostale().getPk().toString());
						ConfigurazioneCasellaPostaleDTO ccpDTO = creaDtoFromResponse(responseDto);
						Long resultInsert = confCasellaPostaleService.insert(ccpDTO);
						if (resultInsert != null) {
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
					//configurazione non presente in locale e sul servizio remoto
					Optional<ConfigurazioneCasellaPostaleResponseDto> configurazioneCasellaPostale = configuraEAttivaCasellaPostale();
					if (configurazioneCasellaPostale.isPresent()) {
						ConfigurazioneCasellaPostaleResponseDto confCasellaPostale = configurazioneCasellaPostale.get();
						ConfigurazioneCasellaPostaleDTO ccpDTO = creaDtoFromResponse(confCasellaPostale);
						Long resultInsert = confCasellaPostaleService.insert(ccpDTO);
						if (resultInsert!=null) {
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
	
	private List<String> creaListaEmailDestinatario(List<DestinatariPecDto> listDestinatariPecDto) {
		List<String> listEmailDestinatario = new ArrayList<String>();
		listDestinatariPecDto.forEach(destinatario -> {
			String emailDestinatario = destinatario.getDestinatario();
			listEmailDestinatario.add(emailDestinatario);
		});
		return listEmailDestinatario;
	}
	
	/**
	 * Aggiorna il campo PEC nella tabella destinatario per quei destinatari che hanno certificato
	 *
	 */
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
				int resultUpdate = destinatarioRepo.updateFieldPec(listIdDestinatarioCertificato, true);
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
				int resultUpdate = destinatarioRepo.updateFieldPec(listIdDestinatarioEsterno, false);
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
			ricevutaDTO.setData(new Timestamp(rpd.getDati().getDataRicevuta().getTime()));
			listRicevutaDTO.add(ricevutaDTO);
			//TODO inserimento multiplo????
			Integer resultInsert;
			try {
				resultInsert = (int) ricevutaService.insert(ricevutaDTO);
				if (resultInsert <= 0) {
					logger.error("Errore durante la insert della ricevuta");
					throw new Exception("Errore durante la insert della ricevuta");
				}
			} catch (Exception e) {
				logger.error("Errore in inseriscoRicevute",e);
			}
			
		});
		
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	private void elaboraMessaggio(ConsumerRecord<String, ReceivedMailHolder> record){
		ReceivedMailHolder messaggioRicevuto = record.value();
		if( logger.isInfoEnabled() ){
			logger.info("Ricevuto messaggio {} su topic {}", messaggioRicevuto, topicName);
		}
		//Scarico la mail EML
		if( messaggioRicevuto.getInformazioniMail() != null ) {
//			ReceivedMailInfo rmi = messaggioRicevuto.getInformazioniMail();
//			try {
//				String emlIdEncoded = URLEncoder.encode(rmi.getEmlId(), "UTF-8");
//				ResponseEntity<InputStreamResource> response = webmailFeign.retrieveEml(rmi.getPkMail(), emlIdEncoded);
//				if( response.getStatusCode().is2xxSuccessful() ){
//					// per il momento lascio questa logica, ma sarà da cambiare in base alle esigenze di progetto -->
//					HttpHeaders headers = response.getHeaders();
//					ContentDisposition cd = headers.getContentDisposition();
//					String fileName = cd.getFilename();
//					File parent = new File("C:\\Users\\gvitto\\Desktop\\mailEml");
//					IOUtils.copy(response.getBody().getInputStream(), new FileOutputStream(new File(parent, fileName.replaceAll("[\\/:*?\"<>|]", "_"))));
//					// <--
//				}
//			} catch (Exception e) {
//				logger.error("Errore nel download del file eml", e);
//			}

		}else{//ricevuta pec
			//Gestisco la ricevuta
			RicevutaPecDto rpd = messaggioRicevuto.getInformazioniRicevuta();
			String idRicevuta = rpd.getPkRicevuta();
			String idMessaggio = rpd.getDati().getIdMessaggio();
			logger.info("elaboraMessaggio - id messaggio"+rpd.getDati().getIdMessaggio());
			String identificativo = rpd.getDati().getIdentificativo();
			List<DestinatariPecDto> listDestinatariPecDto = rpd.getIntestazione().getDestinatari();
			List<String> listEmailDestinatario = creaListaEmailDestinatario(listDestinatariPecDto);
			try{
				CorrispondenzaSearch corrispondenzaSearch = new CorrispondenzaSearch();
				corrispondenzaSearch.setMessageId(idMessaggio);
				List<CorrispondenzaDTO> listCorrispondenzaDTO = corrispondenzaRepository.search(corrispondenzaSearch).getList();
				if (listCorrispondenzaDTO != null && !listCorrispondenzaDTO.isEmpty()) {
					long idCorrispondenza = listCorrispondenzaDTO.get(0).getId();
					List<DestinatarioDTO> listDestinatarioDTO = destinatarioRepo.getDestFromIdComunicazioni(idCorrispondenza);
					if (listDestinatarioDTO != null && !listDestinatarioDTO.isEmpty()) {
						aggiornoCampoPec(listDestinatarioDTO, listDestinatariPecDto);
						//retrieve del codice pratica
						FascicoloCorrispondenzaSearch pkCorrFasc=new FascicoloCorrispondenzaSearch();
						pkCorrFasc.setIdCorrispondenza(idCorrispondenza+"");
						PaginatedList<FascicoloCorrispondenzaDTO> listaFascicoli = fascCorrDao.search(pkCorrFasc);
						String codicePraticaApptr=null;
						if(listaFascicoli.getCount()>0) {
							UUID idPratica =null;
							idPratica = listaFascicoli.getList().get(0).getIdPratica();
							try{
								PraticaDTO pratica=praticaDao.find(idPratica);
								codicePraticaApptr=pratica.getCodicePraticaAppptr();
							}catch(Exception e) {
								logger.error("Errore nel retrieval della pratica "+idPratica,e);
							}
						}
						//messo perchè è capitato che la retrieve eml non trovava l'id passato,
						//come se la persist su db dal task di ricezione mail non era stato ancora concluso...
						Thread.sleep(2000);
						ResponseEntity<Resource> response = webmailFeign.retrieveEml(idRicevuta); // capire se questo servizio oppure l'altro
						if (response.getStatusCode().is2xxSuccessful()) {
							Resource eml = response.getBody();
//							InputStreamResource daticert = response.getBody(); da cambiare la response
//							InputStreamResource smime = response.getBody(); da cambiare la response
							Map<String, String> resultUpload = uploadEml(eml, idMessaggio, identificativo,codicePraticaApptr);
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
	public void start() {
		if(!this.ricezionemail.equals("S")) return; //non faccio partire nulla....
		try {
			//invio la configurazione al server dell'email di default se c'è una modifica verrà recepita dal servizio
			configuraEAttivaCasellaPostale();
			// inserisce la configurazione dell'email principale se non presente
			this.controllaEconfigura(ccpd.getCasellaPostale().getIndirizzoMail());
			// disattivazione delle email non presenti nella tabella enti
			List<ConfigurazioneCasellaPostaleDTO> caselleDadisativare = this.confCasellaPostaleService.getCaselleDaDisattivare();
			for (ConfigurazioneCasellaPostaleDTO itemConfCasellaPostaleDTO : caselleDadisativare) {
				this.disattivaCasellaPosta(itemConfCasellaPostaleDTO);
			}
			//creazione dei nuovi processi in ascolto di eventi da kafka 
			List<ConfigurazioneCasellaPostaleDTO> listCongigEmail;
			
			//lista delle email da configurare compresa quella principale escluse quelle non presenti in enti 
			listCongigEmail = this.confCasellaPostaleService.getCaselleDaAttivare();
			for (ConfigurazioneCasellaPostaleDTO itemListConfigEmail : listCongigEmail) {
				this.addListenerContainer(itemListConfigEmail);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Errore in start ",e);
		}
			
	}
	/**
	 * Aggiunta di un listener per ogni email configurata
	 *
	 * @author G.Lavermicocca
	 * @date 21 set 2020
	 */
	
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
				if(this.ricezionemail.equals("S")) {
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
	
	public void restart() {
		//kill di tutti i processi in ascolto 
		for (ConcurrentMessageListenerContainer<String, ReceivedMailHolder> itemcontainer : this.listContainer) {
			itemcontainer.stop();
		}
		this.start();
	}

	@PreDestroy
	public void destroy() {
		logger.info("SONO ENTRATO NEL METODO DESTROY");
	}
	
	private Map<String, String> uploadEml(Resource eml, String idMailOriginale, String identificativo,String codicePraticaApptr) throws Exception {
		Map<String, String> result = new HashMap<String, String>();
		StringBuilder pathCms = getUploadPathRicevuta(cmsRootPath,idMailOriginale, codicePraticaApptr);
		//String pathCms = StringUtil.concateneString(cmsRootPath, generaUploadPath(idMailOriginale.replaceAll("[<>]", "")));
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
		String idCms=cmsService.uploadCms(fileEml, uploadUrl, pathCms.toString(), codiceApplicazione,true);
		try {if(fileEml!=null && fileEml.exists()) { fileEml.delete();}}catch(Exception e) {}
		//if (!cmsResponse.getId().isBlank()) {
		if (StringUtil.isNotEmpty(idCms)) {
			//result.put("content", cmsResponse.getId());
			result.put("content", idCms);
			result.put("path", pathCms.toString());
			
			return result;
		}
		else {
			logger.error("Errore durante l'upload su Alfresco");
			throw new Exception("Errore durante l'upload su Alfresco");
		}
	}

	public static StringBuilder getUploadPathRicevuta(String cmsRootPath,String idMailOriginale, String codicePraticaApptr) {
		StringBuilder pathCms = new StringBuilder(cmsRootPath);
		if(StringUtil.isNotBlank(codicePraticaApptr)) {
			pathCms
			.append(AlfrescoPaths.PATH_SEPARATOR.getTextValue())
			.append(codicePraticaApptr);
		}
		pathCms.append(AlfrescoPaths.PATH_SEPARATOR.getTextValue())
				.append(AlfrescoPaths.ALLEGATI_COMUNICAZIONE.getTextValue())
				.append(generaUploadPath(idMailOriginale.replaceAll("[<>]", "")));
		return pathCms;
	}
	
	private static String generaUploadPath(String idMailOriginale) {
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

	@Override
	public String toString() {
		return "DynamicKafkaConsumer [brokerAddress=" + brokerAddress + ", topicName=" + topicName + ", groupId="
				+ groupId + "]";
	}

}

