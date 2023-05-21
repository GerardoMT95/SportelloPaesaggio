package it.eng.tz.puglia.geo.service.impl;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import it.eng.tz.puglia.configuration.http.HttpConfiguration;
import it.eng.tz.puglia.geo.service.IConfiniService;
import it.eng.tz.puglia.geo.util.GeometryUtils;
import it.eng.tz.puglia.geo.util.esri.EsriGeometryWithAttribute;
import it.eng.tz.puglia.service.http.IHttpClientService;
import it.eng.tz.puglia.util.crypt.CryptUtil;
import it.eng.tz.puglia.util.json.JsonUtil;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;

@Component
public class ConfiniService implements IConfiniService{
	
	
	/**
	 * LOGGER
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(ConfiniService.class);
	

	@Value("${proxHostMyPay:}")
	private String proxyHost;
	@Value("${proxPortMyPay:0}")
	private int proxyPort;
	@Value("${proxUsernameMyPay:}")
	private String proxyUsername;
	@Value("${proxPasswordMyPay:}")
	private String proxyPassword;
	@Value("${proxNotHostsMyPay:}")
	private String proxyNotHosts;
	
	@Value("${connection.timeout:60}")
	private int connectionTimeout;
	@Value("${read.timeout:120}")
	private int readTimeout;

	@Value("${total.connection:1000}")
	private int totalConnection;
	@Value("${total.route.connection:100}")
	private int totalRouteConnection;
	
	private RestTemplate rt;
	
	@Value("${url.layer.particelle}")
	private String urlParticelle;
	
	
	@PostConstruct
	public void init() throws KeyManagementException, NoSuchAlgorithmException {
		final RestTemplateBuilder builder      = new RestTemplateBuilder();
		this.rt = builder.setConnectTimeout(Duration.ofSeconds(this.connectionTimeout))
		                 .setReadTimeout   (Duration.ofSeconds(this.readTimeout      ))
		                 .build()
		                 ;
		
		final HttpClientBuilder clientBuilder = HttpConfiguration.clientBuilder(this.proxyHost
                                                                               ,this.proxyPort
                                                                               ,StringUtil.isBlank(this.proxyUsername) ? this.proxyUsername : CryptUtil.decrypt(this.proxyUsername)
                                                                               ,StringUtil.isBlank(this.proxyPassword) ? this.proxyPassword : CryptUtil.decrypt(this.proxyPassword)
                                                                               ,this.proxyNotHosts
                                                                               ,this.totalConnection
                                                                               ,this.totalRouteConnection
                                                                               );

		final HttpComponentsClientHttpRequestFactory httpFactory = new HttpComponentsClientHttpRequestFactory(clientBuilder.build());
		httpFactory.setConnectTimeout(this.connectionTimeout * 1000);
		httpFactory.setReadTimeout(this.readTimeout * 1000);
		this.rt.setRequestFactory(httpFactory);
		LOGGER.info("Add interceptors");
	}
	
	/**
	 * @author Antonio La Gatta
	 * @date 19 ott 2021
	 * @param url
	 * @return lista wkt geometie
	 * @throws Exception
	 */
	private List<String> wkt(final String url) throws Exception {
		LOGGER.info("url: {}", url);
		final String json = this.rt.exchange(url, HttpMethod.POST, HttpEntity.EMPTY, String.class).getBody();
		final Map<String,Object> map = JsonUtil.toMap(json);
		final List<String> result = new ArrayList<>();
		if(map.get("features") != null) {
			final String geometry = JsonUtil.toJson((map.get("features")));
			final EsriGeometryWithAttribute[] esri = JsonUtil.toBean(geometry, EsriGeometryWithAttribute[].class);
			for(int i = 0 ; i < esri.length; i++) {
				result.add(GeometryUtils.esriGeometryToWKT(esri[i].getGeometry()));
			}
		}
		LOGGER.info("Found {}", result);
		return result;
	}

	/**
	 * @author Antonio La Gatta
	 * @date 12 mag 2022
	 * @see it.eng.tz.puglia.geo.service.IConfiniService#wktParticelle(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<String> wktParticelle(final String comune, final String foglio, final String particella, final String sezione)
	throws Exception {
		final StopWatch sw = LogUtil.startLog("wktParticelle ", comune, " ", foglio, " ", particella, " ", sezione);
		LOGGER.info("Start wktParticelle {} {} {} {}", comune, foglio, particella, sezione);
		try {
			final String query = StringUtil.concateneString("COMUNE='", comune, "'"
					                                       ," AND FOGLIO='", foglio, "'"
	                                                       ," AND NUMERO='", particella, "'"
	                                                       ,(StringUtil.isEmpty(sezione) ? "" : StringUtil.concateneString(" AND SEZIONE='", sezione, "'"))
	                                                       );
			final Map<String, Object> parameters = new HashMap<>();
			parameters.put("where",query);
			parameters.put("text","");
			parameters.put("objectIds","");
			parameters.put("time","");
			parameters.put("geometry","");
			parameters.put("geometryType","esriGeometryEnvelope");
			parameters.put("inSR","");
			parameters.put("spatialRel","esriSpatialRelIntersects");
			parameters.put("relationParam","");
			parameters.put("outFields","");
			parameters.put("returnGeometry","true");
			parameters.put("maxAllowableOffset","");
			parameters.put("geometryPrecision","");
			parameters.put("outSR","");
			parameters.put("returnIdsOnly","false");
			parameters.put("returnCountOnly","false");
			parameters.put("orderByFields","");
			parameters.put("groupByFieldsForStatistics","");
			parameters.put("outStatistics","");
			parameters.put("returnZ","false");
			parameters.put("returnM","false");
			parameters.put("gdbVersion","");
			parameters.put("returnDistinctValues","false");
			parameters.put("f","pjson");
			final String url = IHttpClientService.buildUrl(this.urlParticelle, parameters);
			LOGGER.info("url {}", url);
			return this.wkt(url);
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));			
		}
	}
	
	
}
