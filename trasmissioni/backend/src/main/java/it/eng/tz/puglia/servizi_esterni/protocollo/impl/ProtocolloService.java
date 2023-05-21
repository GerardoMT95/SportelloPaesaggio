package it.eng.tz.puglia.servizi_esterni.protocollo.impl;

 
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import it.eng.tz.puglia.autpae.config.ProtocolloConfig;

import it.eng.tz.puglia.configuration.http.HttpConfiguration;
import it.eng.tz.puglia.http.exception.HttpException;
import it.eng.tz.puglia.servizi_esterni.protocollo.GeneratedFileBean;
import it.eng.tz.puglia.servizi_esterni.protocollo.IProtocolloService;
import it.eng.tz.puglia.servizi_esterni.protocollo.ProtocolNumberType;
import it.eng.tz.puglia.servizi_esterni.protocollo.interceptor.ProtocolloLogInterceptor;
import it.eng.tz.puglia.servizi_esterni.protocollo.model.AOO;
import it.eng.tz.puglia.servizi_esterni.protocollo.model.Allegati;
import it.eng.tz.puglia.servizi_esterni.protocollo.model.Amministrazione;
import it.eng.tz.puglia.servizi_esterni.protocollo.model.CredenzialiUtente;
import it.eng.tz.puglia.servizi_esterni.protocollo.model.Descrizione;
import it.eng.tz.puglia.servizi_esterni.protocollo.model.Destinatario;
import it.eng.tz.puglia.servizi_esterni.protocollo.model.Destinazione;
import it.eng.tz.puglia.servizi_esterni.protocollo.model.Documento;
import it.eng.tz.puglia.servizi_esterni.protocollo.model.Identificatore;
import it.eng.tz.puglia.servizi_esterni.protocollo.model.ImprontaMIME;
import it.eng.tz.puglia.servizi_esterni.protocollo.model.IndirizzoPostale;
import it.eng.tz.puglia.servizi_esterni.protocollo.model.IndirizzoTelematico;
import it.eng.tz.puglia.servizi_esterni.protocollo.model.Intestazione;
import it.eng.tz.puglia.servizi_esterni.protocollo.model.Mittente;
import it.eng.tz.puglia.servizi_esterni.protocollo.model.OraRegistrazione;
import it.eng.tz.puglia.servizi_esterni.protocollo.model.Origine;
import it.eng.tz.puglia.servizi_esterni.protocollo.model.ParametriEseguiProtocollo;
import it.eng.tz.puglia.servizi_esterni.protocollo.model.Persona;
import it.eng.tz.puglia.servizi_esterni.protocollo.model.RispostaProtocollo;
import it.eng.tz.puglia.servizi_esterni.protocollo.model.Segnatura;
import it.eng.tz.puglia.servizi_esterni.protocollo.model.SegnaturaProtocollo;
import it.eng.tz.puglia.util.crypt.CryptUtil;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * **************** utilizzato microservizio protocollo IHttpProtocollo
 * 
 * Implemenmtation of {@link IProtocolloService}
 * @author Antonio La Gatta
 * @date 3 apr 2020
 */
@Service
public class ProtocolloService implements IProtocolloService{
	
//	private final static Logger LOGGER = LoggerFactory.getLogger(ProtocolloService.class);
//	
//	@Value("${security.http.client.hostProxy}")
//	private String hostProxy;
//
//	@Value("${security.http.client.portProxy}")
//	private String portProxy;
//
//	@Value("${security.http.client.usernameProxy}")
//	private String usernameProxy;
//
//	@Value("${security.http.client.passwordProxy}")
//	private String passwordProxy;
//
//	@Value("${security.http.client.urlNotProxy}")
//	private String urlNotProxy;
//
//	@Value("${client.protocollo.endpoint}")
//	private String clientProtocolloEndpoint;
//
//	@Value("${client.protocollo.administration}")
//	private String clientProtocolloAdministration;
//
//	@Value("${client.protocollo.aoo}")
//	private String clientProtocolloAOO;
//
//	@Value("${client.protocollo.register}")
//	private String clientProtocolloRegister;
//
//	@Value("${client.protocollo.user}")
//	private String clientProtocolloUser;
//
//	@Value("${client.protocollo.password}")
//	private String clientProtocolloPassword;
//
//	@Value("${client.protocollo.hash.algorithm}")
//	private String clientProtocolloHashAlgorihtm;
//	
//	@Value("${protocol.object}")
//	private String protocolObject;
//	
////	private final static String PROXY_HOST            = "DEMANIO_PROXY_HOST";
////	private final static String PROXY_PORT            = "DEMANIO_PROXY_PORT";
////	private final static String PROXY_PASSWORD        = "DEMANIO_PROXY_PASSWORD";
////	private final static String PROXY_USERNAME        = "DEMANIO_PROXY_USERNAME";
////	private final static String PROXY_NOT_PROXY_HOSTS = "DEMANIO_PROXY_NOT_PROXY_HOSTS";
//	private final static int TOTAL_CONNECTION = 3;
//	private final static int TOTAL_ROUTE_CONNECTION = 3;
//	
////	@Autowired
////	private IConfigurazioneService configuration;
//	@Autowired
//	@Qualifier(ProtocolloConfig.PROTOCOLLO_MARSHALLER_BEAN_NAME)
//	private Jaxb2Marshaller webServiceMarshaller;
//	@Autowired
//	@Qualifier(ProtocolloConfig.PROTOCOLLO_MESSAGE_FACTORY)
//	private SaajSoapMessageFactory messageFactory;
//	@Autowired
//	@Qualifier("ProtocolloLogInterceptor")
//	private ClientInterceptor protocolloLogInterceptor;
//	
//	private Destinazione[] createIntestazioneDestinazione(String[] mailTo,String[] denominazioneDestinatario) throws Exception
//	{
//		LOGGER.debug(">>ENTER createIntestazioneDestinazione");    	
//		try
//		{
//			Destinazione listDestinazione[] = new Destinazione[ mailTo.length ];
//						
//			for( int i = 0 ; i < listDestinazione.length ; i++ )
//			{
//				Destinazione destinazione = new Destinazione();
//				destinazione.setConfermaRicezione( CONFERMA_RICEZIONE);
//				IndirizzoTelematico indTelematicoDest = new IndirizzoTelematico(); 
//				indTelematicoDest.setTipo(TIPO_INDIRIZZO_SMTP );
//				indTelematicoDest.setValue( mailTo[i] );
//				LOGGER.debug("indTelematicoDest.setValue" +  mailTo[i] + ": " );
//				destinazione.setIndirizzoTelematico( indTelematicoDest );
//				//unico destinatario
//				Destinatario listDestinatari[] = new Destinatario[ 1 ];
//				Destinatario destinatario = new Destinatario();
//				Persona listPersona[] = new Persona[1]; 
//				Persona persona = new Persona();  	
//				persona.setDenominazione( denominazioneDestinatario[i] );
//				listPersona[0] = persona; 
//				destinatario.setPersona( Arrays.asList(listPersona) );				
//				listDestinatari[0] = destinatario;				
//				destinazione.setDestinatario( Arrays.asList(listDestinatari) );
//				listDestinazione[i] = destinazione;				
//			}
//			LOGGER.debug("<<EXIT createIntestazioneDestinazione");
//			return listDestinazione;
//		}		
//		catch (Exception e) 
//		{
//			throw e;	 		
//		}		      
//	}
//	
//	/**
//	 * 
//	 * @param mailTo
//	 * @param denominazioneDestinatari
//	 * @param allegato
//	 * @param titoloDocumento "Stampa avvenuta ricezione"
//	 * @param oggettoDocumento "Comunicazione avvenuta trasmissione della Pratica ad oggetto '" + oggettoPratica +"'"
//	 * @param tipoDocumento  "application/pdf"
//	 * @param tipoRiferimento "MIME"
//	 * @return
//	 * @throws Exception
//	 */
//	
//	private Segnatura generazioneSegnaturaComeDaCivilia(String mailTo[], String denominazioneDestinatari[], GeneratedFileBean allegato, String titoloDocumento, String oggettoDocumento, String tipoDocumento, String tipoRiferimento) throws Exception {
//		Segnatura                 segnatura               = new Segnatura();
//		//su civilia esiste nel DB una tabella TNO_PROCEDIMENTI_USER_PROT dove ci sono i parametri utilizzati per l'invocazione del servizio protocollo
//		/*
//		 * @Column(name="USERNAME")
//	private String username;
//	
//	@Column(name="USER_PI")
//	private String user_pi;
//	                        
//	@Column(name="PSW_PI")
//	private String psw_pi;	
//	
//	@Column(name="COD_AMM")
//	private String cod_amm;
//	
//	@Column(name="DESC_AMM")
//	private String desc_amm;
//	
//	@Column(name="COD_AOO")
//	private String cod_aoo;	
//	
//	@Column(name="DESC_AOO")
//	private String desc_aoo;	                          
//
//	@Column(name="INDIRIZZOTELEMATICOVALUE")
//	private String indirizzotelematicovalue;	  
//
//	@Column(name="INDIRIZZOTELEMATICOTIPO")
//	private String indirizzotelematicotipo;	  
//
//	@Column(name="MITTAMMDENOMINAZIONE")
//	private String mittammdenominazione;	  
//
//	@Column(name="MITTAMMCODICEAMMINISTRAZIONE")
//	private String mittammcodiceamministrazione;	  
//
//	@Column(name="MITTAMMINDIRIZZOPOSTALEDEN")
//	private String mittammindirizzopostaleden;	  
//
//	@Column(name="MITTAMMINDIRIZZOTELEMATICOTIPO")
//	private String mittammindirizzotelematicotipo;	  
//
//	@Column(name="MITTAMMINDIRIZZOTELEMATICOVAL")
//	private String mittammindirizzotelematicoval;	  
//
//	@Column(name="MITTAOODENOMINAZIONE")
//	private String mittaoodenominazione;	  
//
//	@Column(name="MITTAOOCODICEAOO")
//	private String mittaoocidiceaoo;
//		 */
//		Map<String,String> parametri =new HashMap<>();
//		parametri.put("amministrazione","r_puglia");
//		parametri.put("aoo","ct_rupar_puglia");
//		parametri.put("numero_registrazione","0000220"); //0000220
//		parametri.put("data_registrazione","2004-01-07");//2004-01-07
//		parametri.put("ora_registrazione","15:23:43");
//		parametri.put("tipo_indirizzo_telematico","smtp");
//		parametri.put("valore_indirizzo_telematico","settore.territorio@regione.puglia.it"); //settore.territorio@regione.puglia.it
//		parametri.put("amministrazione_denominazione","Regione Puglia");
//		parametri.put("indirizzo_postale","via delle Magnolie, 6-8 Z.I. - 70026 - Modugno ( BA )");
//		parametri.put("denominazione_aoo","Assetto del Territorio");
//		parametri.put("mittaoo_codiceaoo","AOO_145"); //AOO_145
//		parametri.put("registro","PROT");
//		
//		
//		Intestazione intestazione = new Intestazione(); 
//		Identificatore identificatore = new Identificatore(); 
//		identificatore.setCodiceAmministrazione( parametri.get("amministrazione")); 
//		identificatore.setCodiceAOO( parametri.get("aoo") );
//		identificatore.setNumeroRegistrazione(parametri.get("numero_registrazione") );
//		identificatore.setDataRegistrazione(parametri.get("data_registrazione") );
//		intestazione.setIdentificatore( identificatore );
//		
//		OraRegistrazione oraRegistrazione = new OraRegistrazione(); 			
//		oraRegistrazione.setValue( parametri.get("ora_registrazione")  );
//		intestazione.setOraRegistrazione( oraRegistrazione );
//		
//		Origine origine = new Origine(); 
//		
//		IndirizzoTelematico indirizzoTelematico = new IndirizzoTelematico(); 			
//		indirizzoTelematico.setTipo( parametri.get("tipo_indirizzo_telematico") ); 
//		indirizzoTelematico.setValue( parametri.get("valore_indirizzo_telematico") );
//		origine.setIndirizzoTelematico( indirizzoTelematico );
//		//crea mittente
//		Mittente mittente = null;
//		//creaamministrazionemittente
//		Amministrazione amministrazioneMittente = new Amministrazione(); 
//		amministrazioneMittente.setDenominazione( parametri.get("amministrazione_denominazione") ); 
//		amministrazioneMittente.setCodiceAmministrazione(parametri.get("amministrazione") );
//		//createIndirizzoPostaleMittente
//		IndirizzoPostale indirizzoPostaleMittente = new IndirizzoPostale();  
//		indirizzoPostaleMittente.setDenominazione( parametri.get("indirizzo_postale"));
//		amministrazioneMittente.setIndirizzoPostale( indirizzoPostaleMittente );
//		
//		IndirizzoTelematico indTelematicoMittente = new IndirizzoTelematico(); 
//		indTelematicoMittente.setTipo(parametri.get("tipo_indirizzo_telematico")  ); 
//		indTelematicoMittente.setValue( parametri.get("valore_indirizzo_telematico") ); 
//		//parte non usata nella protocollazione in INGRESSO, viene usata in quella in USCITA
//		
//		List<IndirizzoTelematico> listIndirizzoTelematico = new ArrayList<>();
//		listIndirizzoTelematico.add(indTelematicoMittente);
//		amministrazioneMittente.setIndirizzoTelematico(listIndirizzoTelematico);
//		
//		
//		mittente = new Mittente(); 
//		mittente.setAmministrazione( amministrazioneMittente );
//		AOO aOOMittente = new AOO(); 
//		aOOMittente.setDenominazione( parametri.get("denominazione_aoo"));
//		aOOMittente.setCodiceAOO(  parametri.get("mittaoo_codiceaoo") );
//		mittente.setAOO( aOOMittente );
//		
//		origine.setMittente( mittente );
//		intestazione.setOrigine( origine );
//		identificatore.setCodiceRegistro( parametri.get("registro") );
//		Destinazione[] destinazioneList = createIntestazioneDestinazione( mailTo,denominazioneDestinatari );
//		// questa linea di codice  e' stata spostata dentro il metodo di sopra this.intestazione.getDestinazione().add( this.destinazione );			
//		intestazione.setOggetto( oggettoDocumento);
//		intestazione.setDestinazione( Arrays.asList(destinazioneList) );
//		segnatura.setIntestazione( intestazione );
//		Descrizione descrizione = new Descrizione(); 
//		Documento documento = new Documento(); 
//		documento.setTipoRiferimento(tipoRiferimento);
//		documento.setNome( "Primario" );
//		documento.setTitoloDocumento( titoloDocumento ); // "Stampa avvenuta ricezione"
//		documento.setOggetto( oggettoDocumento );
//		descrizione.setDocumento( documento );
//		// Creo gli allegati
//		Allegati allegati[] = new Allegati[1];
//		Documento doc = this.getDocumentoProtocollo(allegato); 
//		doc.setNome( allegato.getName() );
//		doc.setOggetto( oggettoDocumento );
//		doc.setTitoloDocumento( titoloDocumento );
//		doc.setTipoDocumento( tipoDocumento );
//		Allegati allegatoPdf = new Allegati();
//		// Aggiungo gli eventuali allegati
//		LOGGER.debug("Aggiungo allegato");
//		allegatoPdf.setDocumento( doc );
//		allegati[0] = allegatoPdf;
//		descrizione.setAllegati( Arrays.asList(allegati) );
//		segnatura.setDescrizione( descrizione );
//		return segnatura;
//	}
//
//	/**
//	 * @author Antonio La Gatta
//	 * @date 3 apr 2020
//	 * @see it.eng.tz.puglia.aet.concessioni.demanio.protocollo.IProtocolloService#eseguiProtocollazione(it.eng.tz.puglia.aet.concessioni.demanio.request.bean.Allegato)
//	 */
//	private SegnaturaProtocollo eseguiProtocollazione(GeneratedFileBean allegato,ProtocolNumberType inOut,
//			String denominazioneMittente,
//			IProtocolloUscitaBean protoBean) throws Exception {
//		
//		LOGGER.info("Start eseguiProtocollazione");
//		StopWatch sw = LogUtil.startLog("eseguiProtocollazione");
//		WebServiceTemplate webServiceTemplate = null;
//		try {
//			ParametriEseguiProtocollo request                 = new ParametriEseguiProtocollo();
//			CredenzialiUtente         utente                  = new CredenzialiUtente();
//			String                    codiceAmministratore    = clientProtocolloAdministration;
//			String                    codiceAoo               = clientProtocolloAOO;            
//			String                    codiceRegistro          = clientProtocolloRegister;       
//			String                    idUtente                = clientProtocolloUser;
//			LOGGER.info("Build utente");
//			LOGGER.info(StringUtil.concateneString("codiceAmministratore    = ", codiceAmministratore   ));
//			LOGGER.info(StringUtil.concateneString("codiceAoo               = ", codiceAoo              ));            
//			LOGGER.info(StringUtil.concateneString("codiceRegistro          = ", codiceRegistro         ));       
//			LOGGER.info(StringUtil.concateneString("idUtente                = ", idUtente               ));
//			utente.setCodiceAmministrazione(codiceAmministratore);
//			utente.setCodiceAOO            (codiceAoo           );
//			utente.setCodiceRegistro       (codiceRegistro      );
//			utente.setIDUtente             (idUtente            );
//			Segnatura                 segnatura               = new Segnatura();
//			if(protoBean==null) {
//				//vecchio modo
//				buildSegnatura(allegato, denominazioneMittente, utente, segnatura,null);	
//			}else {
//				//buildSegnatura(allegato, denominazioneMittente, utente, segnatura,protoBean);
//				segnatura=generazioneSegnaturaComeDaCivilia(protoBean.getMailTo(),protoBean.getDenominazioneDestinatario(), 
//						allegato, protoBean.getTitoloDocumento(),protoBean.getOggetto(), protoBean.getTipoDocumento(), protoBean.getTipoRiferimento());
//			}
//			segnatura.setInOut(inOut.name());
//			if(inOut==ProtocolNumberType.I) {
//				segnatura.setTipoOperazione( "Protocollazione In Ingresso" );
//			}else {
//				segnatura.setTipoOperazione( "Protocollazione In Uscita" );
//			}
//			request.setCredenzialiUtente(utente);
//			request.setSegnatura        (segnatura);
//			RispostaProtocollo soapResponse = null;
//			LOGGER.info("Start call");
//			StopWatch swInner = LogUtil.startLog("Call ws protocollo");
//			try{
//				MDC.put(ProtocolloLogInterceptor.MDC_CODICE, allegato.getId()+"");
//				webServiceTemplate = this.webServiceTemplate();
//				soapResponse = (RispostaProtocollo)webServiceTemplate.marshalSendAndReceive(request);
//			}finally {
//				LOGGER.info(LogUtil.stopLog(swInner));
//				MDC.remove(ProtocolloLogInterceptor.MDC_CODICE);
//			}
//			LOGGER.info("End call");
//			String codiceRisposta = soapResponse.getEsito().getCodice();
//			if(COD_OK_PROTOCOLLO.equals(codiceRisposta)){
//				return soapResponse.getSegnaturaProtocollo();
//			}else {
//				throw new HttpException(StringUtil.concateneString("Codice di risposta non corretto da servizio protocollo", codiceRisposta));
//			}
//		}catch(Exception e) {
//			LOGGER.error("Error in eseguiProtocollazione", e);
//			throw e;
//		}finally {
//			this.closeIdleConnection(webServiceTemplate);
//			LOGGER.info(LogUtil.stopLog(sw));
//		}
//	}
//
//	/**
//	 * classica build della segnatura
//	 * @param allegato
//	 * @param inOut
//	 * @param denominazioneMittente
//	 * @param utente
//	 * @param segnatura
//	 * @throws FileNotFoundException
//	 * @throws IOException
//	 * @throws NoSuchAlgorithmException
//	 */
//	private void buildSegnatura(GeneratedFileBean allegato, String denominazioneMittente,
//			CredenzialiUtente utente, Segnatura segnatura,IProtocolloUscitaBean protoBean)
//			throws FileNotFoundException, IOException, NoSuchAlgorithmException {
//		Intestazione              intestazione            = new Intestazione();
//		Origine                   origine                 = new Origine();
//		Mittente                  mittente                = new Mittente();
//		Destinazione              destinazione            = new Destinazione();
//		Descrizione               descrizione             = new Descrizione();
//		Identificatore            identificatore          = new Identificatore();
//		IndirizzoTelematico       indirizzoTelematico     = new IndirizzoTelematico();
//		
//		
//		String                    mittenteValue           = denominazioneMittente;
//		String                    oggetto                 = " %s - Trasmissione autorizzazione paesaggistica";
//		String                    dataRegistrazione       = DATA_REGISTRAZIONE;
//		String                    numeroRegistrazione     = NUMERO_REGISTRAZIONE;     
//		String                    tipoIndirizzoTelematico = TIPO_INDIRIZZO_TELEMATICO;
//		String                    valoreTelematico        = VALORE_TELEMATICO;
//		String                    confermaRicezione       = CONFERMA_RICEZIONE;
//
//		
//		if(protoBean!=null) {
//			oggetto=protoBean.getOggetto();
//		}
//		LOGGER.info(StringUtil.concateneString("mittenteValue           = ", mittenteValue          ));
//		LOGGER.info(StringUtil.concateneString("oggetto                 = ", oggetto                ));
//		LOGGER.info(StringUtil.concateneString("dataRegistrazione       = ", dataRegistrazione      ));
//		LOGGER.info(StringUtil.concateneString("numeroRegistrazione     = ", numeroRegistrazione    ));
//		LOGGER.info(StringUtil.concateneString("tipoIndirizzoTelematico = ", tipoIndirizzoTelematico));
//		LOGGER.info(StringUtil.concateneString("valoreTelematico        = ", valoreTelematico       ));
//		LOGGER.info(StringUtil.concateneString("confermaRicezione       = ", confermaRicezione      ));
//
//		
//		LOGGER.info("Build Intestazione");	
//		identificatore.setCodiceAmministrazione(utente.getCodiceAmministrazione());
//		identificatore.setCodiceAOO            (utente.getCodiceAOO());
//		identificatore.setCodiceRegistro       (utente.getCodiceRegistro());
//		identificatore.setDataRegistrazione    (dataRegistrazione   );
//		identificatore.setNumeroRegistrazione  (numeroRegistrazione );
//
//		indirizzoTelematico.setTipo(tipoIndirizzoTelematico);
//		indirizzoTelematico.setValue(valoreTelematico);
//		mittente.setDenominazione(mittenteValue);
//		origine.setIndirizzoTelematico(indirizzoTelematico);
//		origine.setMittente(mittente);
//
//
//		destinazione.setConfermaRicezione(confermaRicezione);
//		destinazione.setIndirizzoTelematico(indirizzoTelematico);
//
//		intestazione.setOggetto(oggetto);
//		intestazione.setIdentificatore(identificatore);
//		intestazione.setOrigine(origine);
//		if(protoBean!=null) {
//			try {
//				Destinazione[] listaDestinazioni = this.createIntestazioneDestinazione(protoBean.getMailTo(),protoBean.getDenominazioneDestinatario());
//				intestazione.getDestinazione().addAll(Arrays.asList(listaDestinazioni));
//			} catch (Exception e) {
//				LOGGER.error("Errore in createIntestazioneDestinazione");
//				//dummy
//				intestazione.getDestinazione().add(destinazione);
//			}
//		}else {
//			//dummy
//			intestazione.getDestinazione().add(destinazione);	
//		}
//		
//
//		descrizione.setDocumento(this.getDocumentoProtocollo(allegato));
//		segnatura.setIntestazione(intestazione);
//		segnatura.setDescrizione(descrizione);
//		LOGGER.info("Build Segnatura");
//	}
//	/**
//	 * Close idle connections
//	 * @author Antonio La Gatta
//	 * @date 8 apr 2020
//	 * @param webServiceTemplate
//	 */
//	@SuppressWarnings("deprecation")
//	private void closeIdleConnection(WebServiceTemplate webServiceTemplate) {
//		LOGGER.info("closeIdleConnection");
//		if(webServiceTemplate != null) {
//			try {
//				if(webServiceTemplate.getMessageSenders() != null) {
//					for(WebServiceMessageSender webServiceMessageSender:webServiceTemplate.getMessageSenders()) {
//						if(webServiceMessageSender instanceof HttpComponentsMessageSender) {
//							((HttpComponentsMessageSender)webServiceMessageSender).getHttpClient().getConnectionManager().closeIdleConnections(0, TimeUnit.SECONDS);
//						}
//						
//					}
//				}
//			}catch(Exception e){
//				LOGGER.error("Error in closeIdleConnection", e);
//			}
//		}
//	}
//	/**
//	 * @author Antonio La Gatta
//	 * @date 3 apr 2020
//	 * @return {@link WebServiceTemplate} to send message to protocollo
//	 * @throws Exception 
//	 */
//	private WebServiceTemplate webServiceTemplate() throws Exception {
//		String uri = clientProtocolloEndpoint;//this.configuration.getString(PROTOCOLLO_ENDPOINT);
//		LOGGER.info("webServiceTemplate. Uri: " + uri);
//		WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
//		webServiceTemplate.setMarshaller(this.webServiceMarshaller);
//		webServiceTemplate.setUnmarshaller(this.webServiceMarshaller);
//		webServiceTemplate.setDefaultUri(uri);
//		webServiceTemplate.setMessageSender(this.webServiceSender());
//		webServiceTemplate.setMessageFactory(this.messageFactory);
//		webServiceTemplate.setInterceptors(this.clientInterceptors());
//		return webServiceTemplate;
//	}
//	/**
//	 * Sender
//	 * @author Antonio La Gatta
//	 * @date 8 apr 2020
//	 * @return {@link HttpComponentsMessageSender}
//	 * @throws Exception 
//	 */
//	private HttpComponentsMessageSender webServiceSender() throws Exception {
//		LOGGER.info("webServiceSender");
//		String proxyHost     = hostProxy;//this.configuration.getString(PROXY_HOST);
//		String proxyPort     = portProxy;//this.configuration.getString(PROXY_PORT);
//		int    proxyPortInt  = 0;
//		String proxyUsername = usernameProxy;//this.configuration.getString(PROXY_USERNAME);
//		String proxyPassword = passwordProxy;//this.configuration.getString(PROXY_PASSWORD);
//		String proxyNotHosts = urlNotProxy;//this.configuration.getString(PROXY_NOT_PROXY_HOSTS);
//		
//		LOGGER.info(StringUtil.concateneString("proxyHost    : ", proxyHost    ));
//		LOGGER.info(StringUtil.concateneString("proxyPort    : ", proxyPort    ));
//		LOGGER.info(StringUtil.concateneString("proxyUsername: ", proxyUsername));
//		LOGGER.info(StringUtil.concateneString("proxyPassword: ", proxyPassword));
//		LOGGER.info(StringUtil.concateneString("proxyNotHosts: ", proxyNotHosts));
//		
//		try {
//			if(StringUtil.isNotBlank(proxyPort))
//				proxyPortInt = Integer.valueOf(proxyPort);
//		}catch(NumberFormatException e) {
//			LOGGER.error(StringUtil.concateneString("Error in parse port. Message: ", e.getMessage()));
//		}
//		
//		HttpClientBuilder clientBuilder = HttpConfiguration.clientBuilder(proxyHost
//                                                                         ,proxyPortInt
//                                                                         ,proxyUsername
//                                                                         ,StringUtil.isBlank(proxyPassword) ? proxyPassword : CryptUtil.decrypt(proxyPassword)
//                                                                         ,proxyNotHosts
//                                                                         ,TOTAL_CONNECTION
//                                                                         ,TOTAL_ROUTE_CONNECTION
//                                                                         );
//		clientBuilder.addInterceptorFirst(new RemoveSoapHeadersInterceptor());
//		HttpComponentsMessageSender sender = new HttpComponentsMessageSender(clientBuilder.build());
//		sender.afterPropertiesSet();
//		return sender;
//	}
//	/**
//	 * @author Antonio La Gatta
//	 * @date 3 apr 2020
//	 * @return {@link ClientInterceptor} for wsse:UsernameToken
//	 */
//	private ClientInterceptor[] clientInterceptors() {
//		LOGGER.info("clientInterceptors");
//		return new ClientInterceptor[] {this.securityInterceptor(), this.protocolloLogInterceptor};
//	}
//	/**
//	 * @author Antonio La Gatta
//	 * @date 3 apr 2020
//	 * @return {@link Wss4jSecurityInterceptor} for wsse:UsernameToken
//	 */
//	private Wss4jSecurityInterceptor securityInterceptor(){
//	    Wss4jSecurityInterceptor wss4jSecurityInterceptor = new Wss4jSecurityInterceptor();
//	    wss4jSecurityInterceptor.setSecurementActions(WSHandlerConstants.USERNAME_TOKEN);
//	    wss4jSecurityInterceptor.setSecurementUsername(clientProtocolloUser);//this.configuration.getString(PROTOCOLLO_ID_UTENTE));
//	    wss4jSecurityInterceptor.setSecurementPasswordType(WSConstants.PW_TEXT);
//	    wss4jSecurityInterceptor.setSecurementPassword(clientProtocolloPassword);//this.configuration.getEncryptedString(PROTOCOLLO_PASSWORD));
//	    return wss4jSecurityInterceptor;
//	}
//	/**
//	 * @author Antonio La Gatta
//	 * @date 3 apr 2020
//	 * @param allegato
//	 * @return Documento from file
//	 * @throws FileNotFoundException
//	 * @throws IOException
//	 * @throws NoSuchAlgorithmException 
//	 */
//	private Documento getDocumentoProtocollo(GeneratedFileBean allegato) throws FileNotFoundException, IOException, NoSuchAlgorithmException {
//		Documento documento         = new Documento();
//		String    algoritmoImpronta = clientProtocolloHashAlgorihtm;//this.configuration.getString(PROTOCOLLO_ALGORITMO_IMPRONTA);
//		String    codifica          = CODIFICA;
//		String    tipoRiferimento   = TIPO_RIFERIMENTO;
//		String    tipoMIME          = TIPO_MIME;
//		String    tipoDocumento     = TIPO_DOCUMENTO;
////		String    idDocumento       = AllegatoEnumField.ID_RICHIESTA_PDF;
//		String    titoloDocumento   = allegato.getDescription();  //String.format(AllegatoEnumField.TITOLO_DOCUMENTO_RICHIESTA_PDF, allegato.getCodicePratica());
//		String    noteDocumento     = "";//String.format(AllegatoEnumField.NOTE_RICHIESTA_PDF            , allegato.getCodicePratica());
//		String    oggettoDocumento  = "";//String.format(AllegatoEnumField.OGGETTO_RICHIESTA_PDF         , allegato.getCodicePratica());
//
//		LOGGER.info(StringUtil.concateneString("algoritmoImpronta: ", algoritmoImpronta));
//		LOGGER.info(StringUtil.concateneString("codifica: ", codifica));
//		LOGGER.info(StringUtil.concateneString("tipoRiferimento: ", tipoRiferimento));
//		LOGGER.info(StringUtil.concateneString("tipoMIME: ", tipoMIME));
//		LOGGER.info(StringUtil.concateneString("tipoDocumento: ", tipoDocumento));
//		LOGGER.info(StringUtil.concateneString("titoloDocumento: ", titoloDocumento));
//		LOGGER.info(StringUtil.concateneString("oggettoDocumento: ", oggettoDocumento));
//		
////		documento.setId(idDocumento);
//		documento.setNome(allegato.getName());
//		documento.setTipoMIME(tipoMIME);
//		documento.setTipoDocumento(tipoDocumento);
//		documento.setTitoloDocumento(titoloDocumento);
//		documento.setTipoRiferimento(tipoRiferimento);
//		documento.setImprontaMIME(new ImprontaMIME());
//		documento.getImprontaMIME().setCodifica(codifica);
//		documento.getImprontaMIME().setAlgoritmo(algoritmoImpronta);
//		try {
//			documento.getImprontaMIME().setValue(this.getValueImpronta(allegato, algoritmoImpronta));
//		}finally {
////			if(Files.exists(Paths.get(allegato.getContent()))) {
////				try {
////					Files.delete(Paths.get(allegato.getContent()));
////				}catch(Exception e) {
////					LOGGER.error(StringUtil.concateneString("Error in delete file on path", allegato.getContent()), e);
////				}finally {
////					allegato.setContent(null);
////				}
////			}else {
////				allegato.setContent(null);
////			}
//		}
//		documento.setNote(noteDocumento);
//		documento.setOggetto(oggettoDocumento);
//		return documento;
//	}
//	/**
//	 * @author Antonio La Gatta
//	 * @date 3 apr 2020
//	 * @param allegato
//	 * @param algoritmo
//	 * @return Impronta from alforitmo
//	 * @throws FileNotFoundException
//	 * @throws IOException
//	 * @throws NoSuchAlgorithmException 
//	 */
//	private String getValueImpronta(GeneratedFileBean allegato, String algoritmo) throws FileNotFoundException, IOException, NoSuchAlgorithmException {
////		LOGGER.info("Calcolo impronta");
////		ByteArrayOutputStream baos = new ByteArrayOutputStream();
////		try(ByteArrayInputStream fis = new ByteArrayInputStream(allegato.getContent())) {
////			IOUtils.copy(fis, baos);
////			baos.flush();
//			//byte[] digest = MessageDigest.getInstance(algoritmo).digest(baos.toByteArray());
//			byte[] digest = MessageDigest.getInstance(algoritmo).digest(allegato.getContent());
//			String hex    = (new HexBinaryAdapter()).marshal(digest);
//			return StringUtil.base64Encode(hex);
////		}
//	}
//	
////	@Override
////	public SegnaturaProtocollo generateSegnatura(GeneratedFileBean pdfWithoutProtocolNumber,
////			ProtocolNumberType protocolNumberType,
////			String denominazioneMittente) throws Exception{
////				return this.eseguiProtocollazione(pdfWithoutProtocolNumber,protocolNumberType,denominazioneMittente,
////						null);
////	};
////	
////	@Override
////	/**
////	 * @param titoloDocumento "Stampa avvenuta ricezione"
////	 * @param oggettoDocumento "Comunicazione avvenuta trasmissione della Pratica ad oggetto '" + oggettoPratica +"'"
////	 * @param tipoDocumento  "application/pdf"
////	 * @param tipoRiferimento "MIME"
////	 */
////	public SegnaturaProtocollo generateSegnatura(GeneratedFileBean pdfWithoutProtocolNumber,
////			ProtocolNumberType protocolNumberType,String denominazioneMittente,
////			IProtocolloUscitaBean protoBean) throws Exception{
////				return this.eseguiProtocollazione(pdfWithoutProtocolNumber,protocolNumberType,denominazioneMittente,
////						protoBean);
////	}
	

}