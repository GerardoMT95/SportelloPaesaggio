package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.impl;

 
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.transport.WebServiceMessageSender;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import org.springframework.ws.transport.http.HttpComponentsMessageSender.RemoveSoapHeadersInterceptor;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.ProtocolloConfig;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ConfigurazioniEnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.GeneratedFileBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.IProtocolloService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.ProtocolNumberType;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.interceptor.ProtocolloLogInterceptor;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.model.AOO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.model.Amministrazione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.model.CredenzialiUtente;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.model.Descrizione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.model.Destinazione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.model.Documento;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.model.Identificatore;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.model.ImprontaMIME;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.model.IndirizzoPostale;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.model.IndirizzoTelematico;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.model.Intestazione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.model.Mittente;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.model.Origine;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.model.ParametriEseguiProtocollo;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.model.RispostaProtocollo;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.model.Segnatura;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.model.SegnaturaProtocollo;
import it.eng.tz.puglia.configuration.http.HttpConfiguration;
import it.eng.tz.puglia.http.exception.HttpException;
import it.eng.tz.puglia.util.crypt.CryptUtil;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Implemenmtation of {@link IProtocolloService}
 * @author Antonio La Gatta
 * @date 3 apr 2020
 */
@Service
@Deprecated
public class ProtocolloService implements IProtocolloService{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ProtocolloService.class);
	
	@Value("${security.http.client.hostProxy}")
	private String hostProxy;

	@Value("${security.http.client.portProxy}")
	private String portProxy;

	@Value("${security.http.client.usernameProxy}")
	private String usernameProxy;

	@Value("${security.http.client.passwordProxy}")
	private String passwordProxy;

	@Value("${security.http.client.urlNotProxy}")
	private String urlNotProxy;

	private String clientProtocolloEndpoint;

	private String clientProtocolloAdministration;

	private String clientProtocolloAOO;

	private String clientProtocolloRegister;

	private String clientProtocolloUser;

	private String clientProtocolloPassword;

	private String clientProtocolloHashAlgorihtm;
	
	private ConfigurazioniEnteDTO configEnte;
	
	@Value("${protocol.object}")
	private String protocolObject;
	
//	private final static String PROXY_HOST            = "DEMANIO_PROXY_HOST";
//	private final static String PROXY_PORT            = "DEMANIO_PROXY_PORT";
//	private final static String PROXY_PASSWORD        = "DEMANIO_PROXY_PASSWORD";
//	private final static String PROXY_USERNAME        = "DEMANIO_PROXY_USERNAME";
//	private final static String PROXY_NOT_PROXY_HOSTS = "DEMANIO_PROXY_NOT_PROXY_HOSTS";
	private final static int TOTAL_CONNECTION = 3;
	private final static int TOTAL_ROUTE_CONNECTION = 3;
	
//	@Autowired
//	private IConfigurazioneService configuration;
	@Autowired
	@Qualifier(ProtocolloConfig.PROTOCOLLO_MARSHALLER_BEAN_NAME)
	private Jaxb2Marshaller webServiceMarshaller;
	@Autowired
	@Qualifier(ProtocolloConfig.PROTOCOLLO_MESSAGE_FACTORY)
	private SaajSoapMessageFactory messageFactory;
	@Autowired
	@Qualifier("ProtocolloLogInterceptor")
	private ClientInterceptor protocolloLogInterceptor;

	/**
	 * @author Antonio La Gatta
	 * @date 3 apr 2020
	 * @see it.eng.tz.puglia.aet.IProtocolloLogService.demanio.protocollo.IProtocolloService#eseguiProtocollazione(it.eng.tz.puglia.aet.concessioni.demanio.request.bean.Allegato)
	 */
	@Deprecated
	private SegnaturaProtocollo eseguiProtocollazione(GeneratedFileBean allegato,ProtocolNumberType inOut) throws Exception {
		LOGGER.info("Start eseguiProtocollazione");
		StopWatch sw = LogUtil.startLog("eseguiProtocollazione");
		WebServiceTemplate webServiceTemplate = null;
		try {
			ParametriEseguiProtocollo request                 = new ParametriEseguiProtocollo();
			CredenzialiUtente         utente                  = new CredenzialiUtente();
			Segnatura                 segnatura               = new Segnatura();
			Intestazione              intestazione            = new Intestazione();
			Origine                   origine                 = new Origine();
			Mittente                  mittente                = new Mittente();
			Destinazione              destinazione            = new Destinazione();
			Descrizione               descrizione             = new Descrizione();
			Identificatore            identificatore          = new Identificatore();
			IndirizzoTelematico       indirizzoTelematico     = new IndirizzoTelematico();
			String                    codiceAmministratore    = clientProtocolloAdministration;
			String                    codiceAoo               = clientProtocolloAOO;            
			String                    codiceRegistro          = clientProtocolloRegister;       
			String                    idUtente                = clientProtocolloUser;
			String                    mittenteValue           = configEnte.getProtocollazioneDenominazione();
			String                    oggetto                 = " %s - Trasmissione autorizzazione paesaggistica";
			String                    dataRegistrazione       = configEnte.getProtocollazioneDataRegistrazione().toString();
			String                    numeroRegistrazione     = configEnte.getProtocollazioneNumeroRegistrazione();     
			String                    tipoIndirizzoTelematico = configEnte.getProtocollazioneTipoIndirizzoTelematico();
			String                    valoreTelematico        = configEnte.getProtocollazioneIndirizzoTelematico();
			String                    confermaRicezione       = CONFERMA_RICEZIONE;

			LOGGER.info(StringUtil.concateneString("codiceAmministratore    = ", codiceAmministratore   ));
			LOGGER.info(StringUtil.concateneString("codiceAoo               = ", codiceAoo              ));            
			LOGGER.info(StringUtil.concateneString("codiceRegistro          = ", codiceRegistro         ));       
			LOGGER.info(StringUtil.concateneString("idUtente                = ", idUtente               ));
			LOGGER.info(StringUtil.concateneString("mittenteValue           = ", mittenteValue          ));
			LOGGER.info(StringUtil.concateneString("oggetto                 = ", oggetto                ));
			LOGGER.info(StringUtil.concateneString("dataRegistrazione       = ", dataRegistrazione      ));
			LOGGER.info(StringUtil.concateneString("numeroRegistrazione     = ", numeroRegistrazione    ));
			LOGGER.info(StringUtil.concateneString("tipoIndirizzoTelematico = ", tipoIndirizzoTelematico));
			LOGGER.info(StringUtil.concateneString("valoreTelematico        = ", valoreTelematico       ));
			LOGGER.info(StringUtil.concateneString("confermaRicezione       = ", confermaRicezione      ));

			LOGGER.info("Build utente");
			utente.setCodiceAmministrazione(codiceAmministratore);
			utente.setCodiceAOO            (codiceAoo           );
			utente.setCodiceRegistro       (codiceRegistro      );
			utente.setIDUtente             (idUtente            );
			
			LOGGER.info("Build Intestazione");	
			identificatore.setCodiceAmministrazione(codiceAmministratore);
			identificatore.setCodiceAOO            (codiceAoo           );
			identificatore.setCodiceRegistro       (codiceRegistro      );
			identificatore.setDataRegistrazione    (dataRegistrazione   );
			identificatore.setNumeroRegistrazione  (numeroRegistrazione );

			indirizzoTelematico.setTipo(tipoIndirizzoTelematico);
			indirizzoTelematico.setValue(valoreTelematico);
			origine.setIndirizzoTelematico(indirizzoTelematico);
			origine.setMittente(this.popolaMittente(configEnte));


			destinazione.setConfermaRicezione(confermaRicezione);
			destinazione.setIndirizzoTelematico(indirizzoTelematico);

			intestazione.setOggetto(oggetto);
			intestazione.setIdentificatore(identificatore);
			intestazione.setOrigine(origine);
			intestazione.getDestinazione().add(destinazione);

			descrizione.setDocumento(this.getDocumentoProtocollo(allegato));
			

			LOGGER.info("Build Segnatura");
			segnatura.setInOut(inOut.name());
			segnatura.setIntestazione(intestazione);
			segnatura.setDescrizione(descrizione);
			
			request.setCredenzialiUtente(utente   );
			request.setSegnatura        (segnatura);
			RispostaProtocollo soapResponse = null;
			LOGGER.info("Start call");
			StopWatch swInner = LogUtil.startLog("Call ws protocollo");
			try{
				MDC.put(ProtocolloLogInterceptor.MDC_CODICE, allegato.getId()+"");
				webServiceTemplate = this.webServiceTemplate();
				soapResponse = (RispostaProtocollo)webServiceTemplate.marshalSendAndReceive(request);
			}finally {
				LOGGER.info(LogUtil.stopLog(swInner));
				MDC.remove(ProtocolloLogInterceptor.MDC_CODICE);
			}
			LOGGER.info("End call");
			String codiceRisposta = soapResponse.getEsito().getCodice();
			if(COD_OK_PROTOCOLLO.equals(codiceRisposta)){
				//return soapResponse.getSegnaturaProtocollo().getNumeroProtocollo();
				return soapResponse.getSegnaturaProtocollo();
			}else {
				throw new HttpException(StringUtil.concateneString("Codice di risposta non corretto da servizio protocllo", codiceRisposta));
			}
		}catch(Exception e) {
			LOGGER.error("Error in eseguiProtocollazione", e);
			throw e;
		}finally {
			this.closeIdleConnection(webServiceTemplate);
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}
	
	private Mittente popolaMittente(ConfigurazioniEnteDTO configEnte) {
		Mittente mittenteProtocollo = new Mittente();
		Amministrazione amministrazione = new Amministrazione();
		amministrazione.setDenominazione(configEnte.getProtocollazioneDenominazione());
		amministrazione.setCodiceAmministrazione(configEnte.getProtocollazioneAdministration());
		IndirizzoPostale inidirizzoPostale =new IndirizzoPostale();
		inidirizzoPostale.setDenominazione(configEnte.getProtocollazioneIndirizzoPostale());
		amministrazione.setIndirizzoPostale(inidirizzoPostale );
		List<IndirizzoTelematico> indirizzoTelematicoList = new ArrayList<IndirizzoTelematico>();
		IndirizzoTelematico IndirizzoTelematico = new IndirizzoTelematico();
		IndirizzoTelematico.setTipo("smtp");
		IndirizzoTelematico.setValue(configEnte.getProtocollazioneIndirizzoTelematico());
		indirizzoTelematicoList.add(IndirizzoTelematico);
		amministrazione.setIndirizzoTelematico(indirizzoTelematicoList );
		mittenteProtocollo.setAmministrazione(amministrazione );
		AOO aoo = new AOO();
		aoo.setDenominazione(configEnte.getProtocollazioneAooDenominazione());
		aoo.setCodiceAOO(configEnte.getProtocollazioneAoo());
		mittenteProtocollo.setAOO(aoo);
		return mittenteProtocollo;
	}
	
	/**
	 * Close idle connections
	 * @author Antonio La Gatta
	 * @date 8 apr 2020
	 * @param webServiceTemplate
	 */
	@SuppressWarnings("deprecation")
	private void closeIdleConnection(WebServiceTemplate webServiceTemplate) {
		LOGGER.info("closeIdleConnection");
		if(webServiceTemplate != null) {
			try {
				if(webServiceTemplate.getMessageSenders() != null) {
					for(WebServiceMessageSender webServiceMessageSender:webServiceTemplate.getMessageSenders()) {
						if(webServiceMessageSender instanceof HttpComponentsMessageSender) {
							((HttpComponentsMessageSender)webServiceMessageSender).getHttpClient().getConnectionManager().closeIdleConnections(0, TimeUnit.SECONDS);
						}
						
					}
				}
			}catch(Exception e){
				LOGGER.error("Error in closeIdleConnection", e);
			}
		}
	}
	/**
	 * @author Antonio La Gatta
	 * @date 3 apr 2020
	 * @return {@link WebServiceTemplate} to send message to protocollo
	 * @throws Exception 
	 */
	private WebServiceTemplate webServiceTemplate() throws Exception {
		String uri = clientProtocolloEndpoint;//this.configuration.getString(PROTOCOLLO_ENDPOINT);
		LOGGER.info("webServiceTemplate. Uri: " + uri);
		WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		webServiceTemplate.setMarshaller(this.webServiceMarshaller);
		webServiceTemplate.setUnmarshaller(this.webServiceMarshaller);
		webServiceTemplate.setDefaultUri(uri);
		webServiceTemplate.setMessageSender(this.webServiceSender());
		webServiceTemplate.setMessageFactory(this.messageFactory);
		webServiceTemplate.setInterceptors(this.clientInterceptors());
		return webServiceTemplate;
	}
	/**
	 * Sender
	 * @author Antonio La Gatta
	 * @date 8 apr 2020
	 * @return {@link HttpComponentsMessageSender}
	 * @throws Exception 
	 */
	private HttpComponentsMessageSender webServiceSender() throws Exception {
		LOGGER.info("webServiceSender");
		String proxyHost     = hostProxy;//this.configuration.getString(PROXY_HOST);
		String proxyPort     = portProxy;//this.configuration.getString(PROXY_PORT);
		int    proxyPortInt  = 0;
		String proxyUsername = usernameProxy;//this.configuration.getString(PROXY_USERNAME);
		String proxyPassword = passwordProxy;//this.configuration.getString(PROXY_PASSWORD);
		String proxyNotHosts = urlNotProxy;//this.configuration.getString(PROXY_NOT_PROXY_HOSTS);
		
		LOGGER.info(StringUtil.concateneString("proxyHost    : ", proxyHost    ));
		LOGGER.info(StringUtil.concateneString("proxyPort    : ", proxyPort    ));
		LOGGER.info(StringUtil.concateneString("proxyUsername: ", proxyUsername));
		LOGGER.info(StringUtil.concateneString("proxyPassword: ", proxyPassword));
		LOGGER.info(StringUtil.concateneString("proxyNotHosts: ", proxyNotHosts));
		
		try {
			if(StringUtil.isNotBlank(proxyPort))
				proxyPortInt = Integer.valueOf(proxyPort);
		}catch(NumberFormatException e) {
			LOGGER.error(StringUtil.concateneString("Error in parse port. Message: ", e.getMessage()));
		}
		
		HttpClientBuilder clientBuilder = HttpConfiguration.clientBuilder(proxyHost
                                                                         ,proxyPortInt
                                                                         ,proxyUsername
                                                                         ,StringUtil.isBlank(proxyPassword) ? proxyPassword : CryptUtil.decrypt(proxyPassword)
                                                                         ,proxyNotHosts
                                                                         ,TOTAL_CONNECTION
                                                                         ,TOTAL_ROUTE_CONNECTION
                                                                         );
		clientBuilder.addInterceptorFirst(new RemoveSoapHeadersInterceptor());
		HttpComponentsMessageSender sender = new HttpComponentsMessageSender(clientBuilder.build());
		sender.afterPropertiesSet();
		return sender;
	}
	/**
	 * @author Antonio La Gatta
	 * @date 3 apr 2020
	 * @return {@link ClientInterceptor} for wsse:UsernameToken
	 */
	private ClientInterceptor[] clientInterceptors() {
		LOGGER.info("clientInterceptors");
		return new ClientInterceptor[] {this.securityInterceptor(), this.protocolloLogInterceptor};
	}
	/**
	 * @author Antonio La Gatta
	 * @date 3 apr 2020
	 * @return {@link Wss4jSecurityInterceptor} for wsse:UsernameToken
	 */
	private Wss4jSecurityInterceptor securityInterceptor(){
	    Wss4jSecurityInterceptor wss4jSecurityInterceptor = new Wss4jSecurityInterceptor();
	    wss4jSecurityInterceptor.setSecurementActions(WSHandlerConstants.USERNAME_TOKEN);
	    wss4jSecurityInterceptor.setSecurementUsername(clientProtocolloUser);//this.configuration.getString(PROTOCOLLO_ID_UTENTE));
	    wss4jSecurityInterceptor.setSecurementPasswordType(WSConstants.PW_TEXT);
	    wss4jSecurityInterceptor.setSecurementPassword(clientProtocolloPassword);//this.configuration.getEncryptedString(PROTOCOLLO_PASSWORD));
	    return wss4jSecurityInterceptor;
	}
	/**
	 * @author Antonio La Gatta
	 * @date 3 apr 2020
	 * @param allegato
	 * @return Documento from file
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException 
	 */
	private Documento getDocumentoProtocollo(GeneratedFileBean allegato) throws FileNotFoundException, IOException, NoSuchAlgorithmException {
		Documento documento         = new Documento();
		String    algoritmoImpronta = clientProtocolloHashAlgorihtm;//this.configuration.getString(PROTOCOLLO_ALGORITMO_IMPRONTA);
		String    codifica          = CODIFICA;
		String    tipoRiferimento   = TIPO_RIFERIMENTO;
		String    tipoMIME          = TIPO_MIME;
		String    tipoDocumento     = TIPO_DOCUMENTO;
//		String    idDocumento       = AllegatoEnumField.ID_RICHIESTA_PDF;
		String    titoloDocumento   = allegato.getDescription();  //String.format(AllegatoEnumField.TITOLO_DOCUMENTO_RICHIESTA_PDF, allegato.getCodicePratica());
		String    noteDocumento     = "";//String.format(AllegatoEnumField.NOTE_RICHIESTA_PDF            , allegato.getCodicePratica());
		String    oggettoDocumento  = "";//String.format(AllegatoEnumField.OGGETTO_RICHIESTA_PDF         , allegato.getCodicePratica());

		LOGGER.info(StringUtil.concateneString("algoritmoImpronta: ", algoritmoImpronta));
		LOGGER.info(StringUtil.concateneString("codifica: ", codifica));
		LOGGER.info(StringUtil.concateneString("tipoRiferimento: ", tipoRiferimento));
		LOGGER.info(StringUtil.concateneString("tipoMIME: ", tipoMIME));
		LOGGER.info(StringUtil.concateneString("tipoDocumento: ", tipoDocumento));
		LOGGER.info(StringUtil.concateneString("titoloDocumento: ", titoloDocumento));
		LOGGER.info(StringUtil.concateneString("oggettoDocumento: ", oggettoDocumento));
		
//		documento.setId(idDocumento);
		documento.setNome(allegato.getName());
		documento.setTipoMIME(tipoMIME);
		documento.setTipoDocumento(tipoDocumento);
		documento.setTitoloDocumento(titoloDocumento);
		documento.setTipoRiferimento(tipoRiferimento);
		documento.setImprontaMIME(new ImprontaMIME());
		documento.getImprontaMIME().setCodifica(codifica);
		documento.getImprontaMIME().setAlgoritmo(algoritmoImpronta);
		try {
			documento.getImprontaMIME().setValue(this.getValueImpronta(allegato, algoritmoImpronta));
		}finally {
//			if(Files.exists(Paths.get(allegato.getContent()))) {
//				try {
//					Files.delete(Paths.get(allegato.getContent()));
//				}catch(Exception e) {
//					LOGGER.error(StringUtil.concateneString("Error in delete file on path", allegato.getContent()), e);
//				}finally {
//					allegato.setContent(null);
//				}
//			}else {
//				allegato.setContent(null);
//			}
		}
		documento.setNote(noteDocumento);
		documento.setOggetto(oggettoDocumento);
		return documento;
	}
	/**
	 * @author Antonio La Gatta
	 * @date 3 apr 2020
	 * @param allegato
	 * @param algoritmo
	 * @return Impronta from alforitmo
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException 
	 */
	private String getValueImpronta(GeneratedFileBean allegato, String algoritmo) throws FileNotFoundException, IOException, NoSuchAlgorithmException {
//		LOGGER.info("Calcolo impronta");
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		try(ByteArrayInputStream fis = new ByteArrayInputStream(allegato.getContent())) {
//			IOUtils.copy(fis, baos);
//			baos.flush();
			//byte[] digest = MessageDigest.getInstance(algoritmo).digest(baos.toByteArray());
			byte[] digest = MessageDigest.getInstance(algoritmo).digest(allegato.getContent());
			String hex    = (new HexBinaryAdapter()).marshal(digest);
			return StringUtil.base64Encode(hex);
//		}
	}
	
	@Override
	public SegnaturaProtocollo generateSegnatura(GeneratedFileBean pdfWithoutProtocolNumber,
			ProtocolNumberType protocolNumberType) throws Exception{
				return this.eseguiProtocollazione(pdfWithoutProtocolNumber,protocolNumberType);
	};

	
	@Override
	public void setConfiguration(ConfigurazioniEnteDTO configEnte) {

		this.clientProtocolloEndpoint = configEnte.getProtocollazioneEndpoint();

		this.clientProtocolloAdministration = configEnte.getProtocollazioneAdministration();

		this.clientProtocolloAOO = configEnte.getProtocollazioneAoo();

		this.clientProtocolloRegister = configEnte.getProtocollazioneRegister();

		this.clientProtocolloUser = configEnte.getProtocollazioneUser();

		this.clientProtocolloPassword = configEnte.getProtocollazionePassword();

		this.clientProtocolloHashAlgorihtm = configEnte.getProtocollazioneHashAlgorithm();
		
		this.configEnte=configEnte;

	}
}