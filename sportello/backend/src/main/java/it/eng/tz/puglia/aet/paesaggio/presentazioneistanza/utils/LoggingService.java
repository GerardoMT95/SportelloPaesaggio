package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils;

import it.eng.tz.puglia.util.log.LogUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(Logging.class);

	@Around("@annotation(it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.Logging)")
	public Object beforeMethod(ProceedingJoinPoint joinPoint) throws Throwable
	{
		// --> CIO CHE VIENE ESEGUITO PRIMA DELLA PORZIONE DI CODICE ALL'INTERNO DEL
		// METODO
		String invokedMethod = joinPoint.getTarget().getClass().getCanonicalName();
		StopWatch sw = LogUtil.startLog(invokedMethod);
		LOGGER.info("Start " + invokedMethod);
		// <--
		try
		{
			return joinPoint.proceed();// Prosegui con l'esecuzione del metodo
		} 
		catch (Exception e)
		{
			LOGGER.error("Error in " + invokedMethod, e);
			throw e;
		} 
		catch (Throwable e)
		{
			LOGGER.warn("Eccezione sollevata nell'eseguire l'Aspetto per il metodo {}", invokedMethod);
			throw new Throwable("Eccezione sollevata nell'eseguire l'Aspetto per il metodo " + invokedMethod, e);
		} 
		finally
		{
			// --> CIO CHE VIENE ESEGUITO DOPO LA PORZIONE DI CODICE NEL METODO
			LOGGER.info(LogUtil.stopLog(sw));
			// <--
		}
	}
}
