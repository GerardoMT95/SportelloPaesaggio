package it.eng.tz.puglia.autpae.service;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import it.eng.tz.puglia.aet.paesaggio.bean.MyOrganizzazioniBean;
import it.eng.tz.puglia.autpae.service.interfacce.ClientSportelloService;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.CodiceEsitoEnum;
import it.eng.tz.puglia.cache.ICacheConstants;
import it.eng.tz.puglia.configuration.http.HttpConfiguration;
import it.eng.tz.puglia.http.exception.HttpException;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class ClientSportelloServiceImpl implements ClientSportelloService {

	static class BaseRestResponseMyOrganizzazione extends BaseRestResponse<MyOrganizzazioniBean>{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	}
	
	static class BaseRestResponseMapStringString extends BaseRestResponse<Map<String,String>>{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	}
	
	private static final Logger log = LoggerFactory.getLogger(ClientSportelloServiceImpl.class);
	
	@Value("${pae_pres_ist.host}")
	private String hostSportello;
	@Value("${pae_pres_ist.url}")
	private String pathAutpae;
	private static final String MYORGTERRITORIO	= "/myorgterritorio";
	private static final String PRATICHE_WITH_GROUP	= "/gruppiaccessopratiche"; 
	
	@Autowired
	@Qualifier(HttpConfiguration.REST_TEMPLATE_CONTEXT_NAME)
	private RestTemplate restTemplate;
	
	
	@Cacheable(keyGenerator = ICacheConstants.KEY_CONTEXT_NAME, value = "buildGruppiRuoliSportelloFromTokenCached", unless = "#result == null")
	@Override
	public MyOrganizzazioniBean buildOrganizzazioniSportelloFromTokenCached(String token) throws URISyntaxException, HttpException {
		String urlMyOrg=StringUtil.concateneString(hostSportello,pathAutpae,MYORGTERRITORIO);
		final URIBuilder builder = new URIBuilder(urlMyOrg);
		final HttpEntity<?>            entity      = new HttpEntity<>(this.buildAuthorizationHeader(token));
		final ResponseEntity<BaseRestResponseMyOrganizzazione> response = this.restTemplate.exchange(builder.build(), HttpMethod.GET, entity, BaseRestResponseMyOrganizzazione.class);
		if(response.getStatusCode() != HttpStatus.OK) {
			log.error(StringUtil.concateneString("Error in call . Error code: ", response.getStatusCode()));
        	throw new HttpException("Error in call sportello ", response.getStatusCode().value());
        }
        return response.getBody().getPayload();
	}
	
	private MultiValueMap<String, String> buildAuthorizationHeader(String token) {
		final MultiValueMap<String, String> headers  = new LinkedMultiValueMap<>();
		headers.add(HttpHeaders.AUTHORIZATION, token);
		return headers;
	}

	@Override
	public Map<String,String> praticheGruppo(String token,List<String> codiciTrasmissione) throws Exception {
		String url=StringUtil.concateneString(hostSportello,pathAutpae,PRATICHE_WITH_GROUP);
		final URIBuilder builder = new URIBuilder(url);
		final HttpEntity<?>            entity      = new HttpEntity<>(codiciTrasmissione,this.buildAuthorizationHeader(token));
		final ResponseEntity<BaseRestResponseMapStringString> response = this.restTemplate.exchange(builder.build(), HttpMethod.POST, entity, BaseRestResponseMapStringString.class);
		if(response.getStatusCode() != HttpStatus.OK) {
			log.error(StringUtil.concateneString("Error in call . Error code: ", response.getStatusCode()));
        	throw new HttpException("Error in call sportello ", response.getStatusCode().value());
        }if(response.getBody().getCodiceEsito().equals(CodiceEsitoEnum.OK)) {
        	return response.getBody().getPayload();
        }else {
        	throw new Exception("errore nel retrieve dei codici gruppo per accesso alle pratiche ");
        }
	}
		
}