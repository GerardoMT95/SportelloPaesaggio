package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.interceptor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;

import it.eng.tz.puglia.util.string.StringUtil;

@Component(value = "ProtocolloLogInterceptor")
public class ProtocolloLogInterceptor implements ClientInterceptor{

	private final static Logger LOGGER = LoggerFactory.getLogger(ProtocolloLogInterceptor.class);
	
	public static final String MDC_CODICE = "MDC_CODICE_RICHIESTA_PROTOCOLLO";
	public static final String MDC_SOAP_REQUEST = "MDC_SOAP_REQUEST";   
	public static final String MDC_SOAP_RESPONSE = "MDC_SOAP_RESPONSE";
	
	/**
	 * @author Antonio La Gatta
	 * @date 3 apr 2020
	 * @see org.springframework.ws.client.support.interceptor.ClientInterceptor#handleRequest(org.springframework.ws.context.MessageContext)
	 */
	@Override
	public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
		LOGGER.info("handleRequest");
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			messageContext.getRequest().writeTo(os);
			final String soapRequest = os.toString("UTF-8");
			LOGGER.info("Soap Request: {} ", soapRequest);
			MDC.put(MDC_SOAP_REQUEST, soapRequest);
		} catch (IOException e) {
			LOGGER.error(StringUtil.concateneString("Error in log request. ", e.getMessage()));
		}
		return true;
	}

	/**
	 * @author Antonio La Gatta
	 * @date 3 apr 2020
	 * @see org.springframework.ws.client.support.interceptor.ClientInterceptor#handleResponse(org.springframework.ws.context.MessageContext)
	 */
	@Override
	public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
		LOGGER.info("handleResponse");
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			messageContext.getResponse().writeTo(os);
			final String soapResponse = os.toString("UTF-8");
			LOGGER.info("Soap Response: {} ", soapResponse);
			MDC.put(MDC_SOAP_RESPONSE, soapResponse);
		} catch (IOException e) {
			LOGGER.error(StringUtil.concateneString("Error in log response. ", e.getMessage()));
		}
		return true;
	}

	/**
	 * @author Antonio La Gatta
	 * @date 3 apr 2020
	 * @see org.springframework.ws.client.support.interceptor.ClientInterceptor#handleFault(org.springframework.ws.context.MessageContext)
	 */
	@Override
	public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
		LOGGER.info("handleFault");
		return true;
	}

	/**
	 * @author Antonio La Gatta
	 * @date 3 apr 2020
	 * @see org.springframework.ws.client.support.interceptor.ClientInterceptor#afterCompletion(org.springframework.ws.context.MessageContext, java.lang.Exception)
	 */
	@Override
	public void afterCompletion(MessageContext messageContext, Exception ex) throws WebServiceClientException {
	}

}
