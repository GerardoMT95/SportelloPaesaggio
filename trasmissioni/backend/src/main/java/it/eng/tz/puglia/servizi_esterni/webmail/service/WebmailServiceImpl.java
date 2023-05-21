package it.eng.tz.puglia.servizi_esterni.webmail.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dto.ConfigurazioneCasellaPostaleDTO;
import it.eng.tz.puglia.autpae.service.interfacce.ConfigurazioneCasellaPostaleService;
import it.eng.tz.puglia.autpae.service.interfacce.CorrispondenzaService;
import it.eng.tz.puglia.servizi_esterni.webmail.dto.Risposta;
import it.eng.tz.puglia.servizi_esterni.webmail.feign.Webmail;
import it.eng.tz.regione.puglia.webmail.be.dto.AttivaDisattivaCasellaPostaleDto;
import it.eng.tz.regione.puglia.webmail.be.dto.BaseResponse;
import it.eng.tz.regione.puglia.webmail.be.dto.ConfigurazioneCasellaPostaleDto;
import it.eng.tz.regione.puglia.webmail.be.dto.ConfigurazioneCasellaPostaleResponseDto;
import it.eng.tz.regione.puglia.webmail.be.dto.InvioMailDto;
import it.eng.tz.regione.puglia.webmail.be.dto.InvioMailResultDto;

@Service
public class WebmailServiceImpl implements WebmailService {
	
	private static final Logger logger = LoggerFactory.getLogger(WebmailServiceImpl.class);
	
	@Value("${microservice.webmail.url}")
	private String baseUrl;
	
	@Autowired
	private Webmail webmailFeign;
	
	@Autowired
	private ConfigurazioneCasellaPostaleService confCasPostService;
	
	
	@Autowired
	private ObjectMapper obj;
	
	@Autowired
	private CorrispondenzaService corrispondenzaService;

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public InvioMailResultDto inviaMail(InvioMailDto datiMail, Resource[] allegati, Long idCorrispondenza) throws Exception {
		logger.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA); // tipo di cosa che sto inviando nel body
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<String, Object>();
		body.add("datiMail", datiMail);
		if (allegati != null && allegati.length > 0) {
			for (int i = 0; i < allegati.length; i++) {
				body.add("allegatiMail", allegati[i]);
			} 
		}
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(body, headers);
		RestTemplate restTemplate = new RestTemplate();
		logger.info("webmail baseUrl: "+baseUrl);
		ResponseEntity<String> response = restTemplate.exchange( baseUrl + "/casella-postale/invia-mail", HttpMethod.POST, entity, String.class);
		if (response.getStatusCode().is2xxSuccessful()) {
			logger.info("Email inviata con successo");
			Risposta risposta = obj.readValue(response.getBody(), Risposta.class);
			InvioMailResultDto result = risposta.getPayload();
			if(idCorrispondenza==null) {
				return result;
			}
			//persisto nel DB la chiave del messaggio mail...
			Integer resultUpdate = corrispondenzaService.updateMessageId(idCorrispondenza, result.getMailMessageId());
			if (resultUpdate > 0) {
				logger.info("Aggiornamento della corrispondenza effettuato con successo");
				return result;
			}
			else {
				logger.error("Errore durante l'aggiornamento della corrispondenza");
				throw new Exception("Errore durante l'aggiornamento della corrispondenza");
			}
		}
		else {
			logger.error("Errore durante l'invio della mail");
			throw new Exception("Errore durante l'invio della mail");
		}
	}
	
	/**
	 * verifica remota e in caso di esistenza viene disattivata
	 * @author acolaianni
	 *
	 * @param ccpd
	 * @throws Exception
	 */
	private void checkRemoto(ConfigurazioneCasellaPostaleDto ccpd) throws Exception {
		// verifico se lato microservizio esiste già la casella di posta....
		ResponseEntity<BaseResponse<ConfigurazioneCasellaPostaleDto>> cfgRemota = webmailFeign
				.getConfigurazioneCasellaPostale(ccpd.getCasellaPostale().getIndirizzoMail());
		if (cfgRemota.getStatusCode().is2xxSuccessful() && cfgRemota.getBody() != null
				&& cfgRemota.getBody().getPayload() != null) {
			// esiste già
			// la disattivo...
			try {
				this.disattivaCasellaPosta(cfgRemota.getBody().getPayload().getCasellaPostale().getPk().toString(),
						cfgRemota.getBody().getPayload().getCasellaPostale().getIndirizzoMail());
			} catch (Exception e) {
				logger.error("Errore durante la disattivazione della casella {}",
						cfgRemota.getBody().getPayload().getCasellaPostale().getIndirizzoMail(), e);
				throw new CustomOperationToAdviceException(
						"La casella di posta è già configurata sul gestore, errore durante la disattivazione per la riapplicazione dei nuovi parametri!");
			}
		}
	}
	
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, timeout=60000, rollbackFor=Exception.class)
	public Optional<ConfigurazioneCasellaPostaleResponseDto> configuraEAttivaCasellaPostale(ConfigurazioneCasellaPostaleDto ccpd) throws Exception {
		try {
			checkRemoto(ccpd);
			ResponseEntity<BaseResponse<ConfigurazioneCasellaPostaleResponseDto>> esitoConfigurazione = webmailFeign.configuraCasellaPostale(ccpd);
			if(esitoConfigurazione.getStatusCode().is2xxSuccessful()){
				ConfigurazioneCasellaPostaleResponseDto ccprd = esitoConfigurazione.getBody().getPayload();
				if( logger.isInfoEnabled() ){
					logger.info("Ottenuto ccprd {}", ccprd);
				}
				attivaDisattivaCasellaPosta(ccprd);
				ConfigurazioneCasellaPostaleDTO ccpDTO = WebmailService.creaDtoFromResponse(ccprd);
				confCasPostService.insertOrUpdate(ccpDTO);
				return Optional.of(ccprd);
			}
		} catch (Exception e) {
			logger.error("Errore nella configurazione/attivazione della casella postale", e);
			throw e;
		} 
		return Optional.empty();
	}
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, timeout=60000, rollbackFor=Exception.class)
	public Optional<ConfigurazioneCasellaPostaleResponseDto> disattivaCasellaPostale(ConfigurazioneCasellaPostaleDto ccpd) throws Exception {
		try {
			ResponseEntity<BaseResponse<ConfigurazioneCasellaPostaleResponseDto>> esitoConfigurazione = webmailFeign.configuraCasellaPostale(ccpd);
			if(esitoConfigurazione.getStatusCode().is2xxSuccessful()){
				ConfigurazioneCasellaPostaleResponseDto ccprd = esitoConfigurazione.getBody().getPayload();
				if( logger.isInfoEnabled() ){
					logger.info("Ottenuto ccprd {}", ccprd);
				}
				disattivaCasellaPosta(ccprd);
				ConfigurazioneCasellaPostaleDTO ccpDTO = WebmailService.creaDtoFromResponse(ccprd);
				confCasPostService.insertOrUpdate(ccpDTO);
				return Optional.of(ccprd);
			}
		} catch (Exception e) {
			logger.error("Errore nella configurazione/attivazione della casella postale", e);
			throw e;
		} 
		return Optional.empty();
	}
	
	private void attivaDisattivaCasellaPosta(ConfigurazioneCasellaPostaleResponseDto ccprd) throws Exception {
		//se è già attiva non la riattivo
		ResponseEntity<BaseResponse<ConfigurazioneCasellaPostaleDto>> response = webmailFeign
				.getConfigurazioneCasellaPostale(ccprd.getIndirizzoMail());
		if (response.getStatusCode().is2xxSuccessful() && response.hasBody() && response.getBody() != null
				&& response.getBody().getPayload() != null
				&& response.getBody().getPayload().getCasellaPostale().isCasellaAttiva()) {
			logger.info("Casella postale {} già attiva", ccprd.getIndirizzoMail());
			return;
		}
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
	
	private void disattivaCasellaPosta(ConfigurazioneCasellaPostaleResponseDto ccprd) throws Exception {
		this.disattivaCasellaPosta(ccprd.getIdCasellaPostale(),ccprd.getIndirizzoMail());
	}

	private void disattivaCasellaPosta(String idCasellaPostale,String indirizzoMail ) throws Exception {
		AttivaDisattivaCasellaPostaleDto attivaDisattivaCasellaPostaleDto = new AttivaDisattivaCasellaPostaleDto();
		attivaDisattivaCasellaPostaleDto.setPk(idCasellaPostale);
		attivaDisattivaCasellaPostaleDto.setIndirizzoMail(indirizzoMail);
		attivaDisattivaCasellaPostaleDto.setAttiva(false);
		ResponseEntity<BaseResponse<Boolean>> result = webmailFeign.attivaDisattivaCasellaPosta(attivaDisattivaCasellaPostaleDto);
		if (result.getStatusCode().is2xxSuccessful()) {
			logger.info("Attivazione casella postale {} terminata con successo", idCasellaPostale);
		}
		else {
			logger.error("Errore nella disattivazione della casella postale {}", idCasellaPostale);
			throw new Exception("Errore nella disttivazione della casella postale " + idCasellaPostale);
		}
	}
	
}
