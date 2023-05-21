package it.eng.tz.puglia.autpae.utility;

import it.eng.tz.puglia.util.log.LogUtil;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingService.class);

	@Around("@annotation(it.eng.tz.puglia.autpae.utility.LoggingAet)")
	public Object beforeMethod(ProceedingJoinPoint joinPoint) throws Throwable
	{
		// --> CIO CHE VIENE ESEGUITO PRIMA DELLA PORZIONE DI CODICE ALL'INTERNO DEL
		// METODO
		String invokedClass = joinPoint.getTarget().getClass().getCanonicalName();
		String methodName="";
		try {
			MethodSignature signature = (MethodSignature) joinPoint.getSignature();
			methodName = signature.getMethod().getName();
		} catch (Exception e1) {}
		StopWatch sw = LogUtil.startLog(invokedClass);
		LOGGER.info("Start " + invokedClass + "."+methodName);
		// <--
		try
		{
			return joinPoint.proceed();// Prosegui con l'esecuzione del metodo
		} 
		catch (Exception e)
		{
			LOGGER.error("Error in " + invokedClass, e);
			throw e;
		} 
		catch (Throwable e)
		{
			LOGGER.warn("Eccezione sollevata nell'eseguire l'Aspetto per il metodo {}", invokedClass+"."+methodName);
			throw new Throwable("Eccezione sollevata nell'eseguire l'Aspetto per il metodo " + invokedClass +"."+methodName, e);
		} 
		finally
		{
			// --> CIO CHE VIENE ESEGUITO DOPO LA PORZIONE DI CODICE NEL METODO
			LOGGER.info(LogUtil.stopLog(sw));
			// <--
		}
	}
}
