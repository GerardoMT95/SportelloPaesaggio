package it.eng.tz.puglia.autpae.config;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;


/**
 * Configuration for protocollo
 * @author Antonio La Gatta
 * @date 3 apr 2020
 */
@Configuration
public class ProtocolloConfig {

	private final static Logger LOGGER = LoggerFactory.getLogger(ProtocolloConfig.class);
	
	public ProtocolloConfig() {
		LOGGER.info("Constructor");
	}
	
	public static final String PROTOCOLLO_MARSHALLER_BEAN_NAME = "PROTOCOLLO_MARSHALLER";
	public static final String PROTOCOLLO_MESSAGE_FACTORY = "PROTOCOLLO_MESSAGE_FACTORY";
	
	/**
	 * @author Antonio La Gatta
	 * @date 05 set 2019
	 * @return {@link Jaxb2Marshaller} instance
	 */
	@Bean(PROTOCOLLO_MARSHALLER_BEAN_NAME)
	public Jaxb2Marshaller webServiceMarshaller() {
		LOGGER.info("webServiceMarshaller");
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setPackagesToScan(it.eng.tz.puglia.servizi_esterni.protocollo.model.Allegati.class.getPackageName());
		return marshaller;
	}
	/**
	 * @author Antonio La Gatta
	 * @date 05 set 2019
	 * @return {@link SaajSoapMessageFactory} instance
	 * @throws SOAPException 
	 */
	@Bean(PROTOCOLLO_MESSAGE_FACTORY)
	public SaajSoapMessageFactory messageFactory() throws SOAPException {
		LOGGER.info("messageFactory");
		MessageFactory msgFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
		SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory(msgFactory);
		messageFactory.setSoapVersion(SoapVersion.SOAP_11);
		return messageFactory;
	}
}
