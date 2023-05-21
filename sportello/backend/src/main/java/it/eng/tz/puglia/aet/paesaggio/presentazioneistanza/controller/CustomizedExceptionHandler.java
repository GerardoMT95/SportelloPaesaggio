package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.controller.BaseRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.oxm.ValidationFailureException;
import org.springframework.security.jwt.crypto.sign.InvalidSignatureException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * Controller che si occupa di centralizzare le eccezioni costruendo il {@link BaseRestResponse}, con il messaggio
 * d'errore opportuno, da far tornare nella response.
 * 
 * @author <a href="mailto:Luciano.Faretra@eng.it">Luciano Faretra</a>
 * @author <a href="mailto:Michele.Santoro2@eng.it">Michele Santoro</a>
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomizedExceptionHandler extends BaseRestController
{
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomizedExceptionHandler.class.getName());
	
	/**
	 * Metodo che cattura l'eccezione {@link MethodArgumentNotValidException} e
	 *  popola la {@link ResponseEntity} con un {@link BaseRestResponse} con codice
	 * e messaggio d'errore opportunamente settati.
	 *
	 * @param ex - {@link MethodArgumentNotValidException} sollevata da gestire
	 * @return Risposta contenente il {@link BaseRestResponse} con payload vuoto
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public final ResponseEntity<BaseRestResponse<Void>> handleValidationException(MethodArgumentNotValidException ex)
	{
		LOGGER.error("ECCEZIONE GESTITA IN MethodArgumentNotValidException: ", ex);
		return super.koResponse(ex, new BaseRestResponse<>());
	}

	/**
	 * Metodo che cattura l'eccezione {@link InvalidFormatException} e popola la {@link ResponseEntity} con un {@link BaseRestResponse} con codice
	 * e messaggio d'errore opportunamente settati.
	 *
	 * @param e - {@link InvalidFormatException} sollevata da gestire
	 * @return Risposta contenente il {@link BaseRestResponse} con payload vuoto
	 */
	@ExceptionHandler({InvalidFormatException.class, HttpMessageNotReadableException.class})
	public final ResponseEntity<BaseRestResponse<Void>> handleInvalidFormatException(Exception e)
	{
		LOGGER.error("ECCEZIONE GESTITA IN handleInvalidFormatException: ", e);
		return super.koResponse(e, new BaseRestResponse<>());
	}

	/**
	 * Metodo che cattura l'eccezione {@link HttpRequestMethodNotSupportedException} e popola la {@link ResponseEntity} con un {@link BaseRestResponse} con codice
	 * e messaggio d'errore opportunamente settati.
	 *
	 * @param e - {@link HttpRequestMethodNotSupportedException} sollevata da gestire
	 * @return Risposta contenente il {@link BaseRestResponse} con payload vuoto
	 */
	@ExceptionHandler({HttpRequestMethodNotSupportedException.class})
	public final ResponseEntity<BaseRestResponse<Void>> handleHttpRequestMethodNotSupportedException(Exception e)
	{
		LOGGER.error("ECCEZIONE GESTITA IN handleHttpRequestMethodNotSupportedException: ", e);
		return super.koResponse(e, new BaseRestResponse<>());

	}

	/**
	 * Metodo che cattura l'eccezione non prevista e popola la {@link ResponseEntity} con un {@link BaseRestResponse} con codice
	 * e messaggio d'errore opportunamente settati.
	 *
	 * @param ex - {@link InvalidSignatureException} sollevata da gestire
	 * @return Risposta contenente il {@link BaseRestResponse} con payload vuoto
	 */
    @ExceptionHandler(InvalidSignatureException.class)
    public final ResponseEntity<BaseRestResponse<Void>> handleInvalidToken(InvalidSignatureException ex)
    {
		LOGGER.error("ECCEZIONE GESTITA IN handleTutteLeException: InvalidSignatureException", ex);
		return super.koResponse(ex, new BaseRestResponse<>());

    }
    
    /**
     * Eccezione da notificare all'utente con messaggio custom...
     * il FE ha in app.component uno switch-case dove nel ramo default testa se descrizioneEsito.startsWith("CUSTOM-OPERATION-TO-ADVICE:")
     * @param ex
     * @return
     */
    @ExceptionHandler(CustomOperationToAdviceException.class)
    public final ResponseEntity<BaseRestResponse<Void>> handleCustomOperationToAdvice(CustomOperationToAdviceException ex)
    {
    	return super.koResponse("CUSTOM-OPERATION-TO-ADVICE:"+ex.getMessage(),new BaseRestResponse<>() );
    }

    @ExceptionHandler(ValidationFailureException.class)
    public final ResponseEntity<BaseRestResponse<Void>> handleCustomOperationToAdvice(ValidationFailureException ex)
    {
    	return super.koResponse("CUSTOM-OPERATION-TO-ADVICE:"+ex.getMessage(),new BaseRestResponse<>() );
    }
    
    /**
	 * Metodo che cattura l'eccezione non prevista e popola la {@link ResponseEntity} con un {@link BaseRestResponse} con codice
	 * e messaggio d'errore opportunamente settati.
	 *
	 * @param ex - {@link Exception} sollevata da gestire
	 * @return Risposta contenente il {@link BaseRestResponse} con payload vuoto
	 */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<BaseRestResponse<Void>> handleTutteLeException(Exception ex)
    {
		LOGGER.error("ECCEZIONE GESTITA IN handleTutteLeException: ", ex);
		return super.koResponse(ex, new BaseRestResponse<>());

    }
    
}
