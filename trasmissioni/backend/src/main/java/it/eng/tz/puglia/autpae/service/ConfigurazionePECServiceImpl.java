package it.eng.tz.puglia.autpae.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dto.ConfigurazionePECBean;
import it.eng.tz.puglia.autpae.repository.base.ConfigurazioneCasellaPostaleBaseRepository;
import it.eng.tz.puglia.autpae.repository.base.ConfigurazionePECRepository;
import it.eng.tz.puglia.autpae.service.interfacce.ConfigurazionePECService;
import it.eng.tz.puglia.servizi_esterni.webmail.internal_event.KafkaConsumerEvent;
import it.eng.tz.puglia.servizi_esterni.webmail.service.WebmailService;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.regione.puglia.webmail.be.dto.ConfigurazioneCasellaPostaleDto;

@Service
public class ConfigurazionePECServiceImpl implements ConfigurazionePECService
{
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired private ConfigurazionePECRepository dao;
	@Autowired private WebmailService webmailService;
	//@Autowired private DynamicKafkaConsumer dk;
	@Autowired private ObjectMapper obj;
	
	@Autowired ConfigurazioneCasellaPostaleBaseRepository ccpdDao;
	
	@Autowired
    private ApplicationEventPublisher applicationEventPublisher;
	
	
	@Value("${spring.application.name}")
	private String codiceApplicazione;
	
	@Autowired
	@Qualifier("casellaMittenteApplicazione")
	ConfigurazioneCasellaPostaleDto ccpd;
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor=Exception.class, timeout=60000, readOnly=true)
	public boolean configurationExist() throws Exception
	{
		long nElem = dao.count();
		return nElem > 0;
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class, timeout=60000, readOnly=false)
	public void saveOrUpdate(ConfigurazionePECBean bean) throws Exception 
	{ 
		long nElem = dao.count();
		if(nElem ==0)
		{
			logger.info("Nessuna configurazione PEC presente su DB, procedo con l'inserimento");
			dao.insert(bean);
		}
		else
		{
			logger.info("Configurazione PEC presente su DB, procedo con l'update");
			dao.update(bean);
		}		
		inviaConfigurazioneEmail(bean);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class, timeout=60000, readOnly=false)
	public void delete() throws Exception 
	{
		dao.deleteAll();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class, timeout=60000, readOnly=false)
	public ConfigurazionePECBean resetDefault() throws Exception
	{
		ConfigurazionePECBean bean = getDefaultConfiguration();
		dao.deleteAll();
		dao.insert(bean);
		inviaConfigurazioneEmail(bean);
		return bean;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class, timeout=60000, readOnly=false)
	public void caricaDefault() throws Exception
	{
		long numConf = dao.count();
		if(numConf==0) {
			ConfigurazionePECBean bean = getDefaultConfiguration();
			dao.insert(bean);
			try {
				ccpdDao.find(bean.getPecIndirizzo());
				//se esiste già vuol dire che la casella è già configurata sul  microservice webmail
			}catch(EmptyResultDataAccessException e) {
				inviaConfigurazioneEmail(bean);	
			}
				
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor=Exception.class, timeout=60000, readOnly=true)
	public ConfigurazionePECBean getConfiguration() throws Exception
	{
		ConfigurazionePECBean conf = getDefaultConfiguration();
		if(dao.count() > 0)
			conf = dao.getConfiguration();
		return conf;
	}
	
	public ConfigurazionePECBean getDefaultConfiguration() throws Exception
	{
		ConfigurazionePECBean bean = new ConfigurazionePECBean(ccpd);
		return bean;
	}
	
	@Override
	public void initBeanCasellaMittenteApplicazione() throws Exception {
		ConfigurazionePECBean pecBean = this.getConfiguration();
		ConfigurazioneCasellaPostaleDto ccpdLocal = pecBean.toCasellaPostaleDTO();
		toBeanCasellaMittente(ccpdLocal);
	}
	
	private void inviaConfigurazioneEmail(ConfigurazionePECBean dto) throws Exception
	{
		String token = LogUtil.getAuthorization();
		LogUtil.addAuthorization(null);
		try 
		{
			ConfigurazioneCasellaPostaleDto ccpdLocal = dto.toCasellaPostaleDTO();
			webmailService.configuraEAttivaCasellaPostale(ccpdLocal);
			toBeanCasellaMittente(ccpdLocal);
			//emetto evento per avvisare il kafkaconsumer
			applicationEventPublisher.publishEvent(new KafkaConsumerEvent());
		}
		catch (CustomOperationToAdviceException e) {
			throw e;
		}
		catch (Exception e) {
			logger.error("Errore in inviaConfigurazioneEmail",e);
			throw e;
		}
		finally 
		{
			LogUtil.addAuthorization(token);
		}
	}

	private void toBeanCasellaMittente(ConfigurazioneCasellaPostaleDto ccpdLocal) {
		ccpd.setCasellaPostale(ccpdLocal.getCasellaPostale());
		ccpd.setConfigurazioneMailIngresso(ccpdLocal.getConfigurazioneMailIngresso());
		ccpd.setConfigurazioneMailUscita(ccpdLocal.getConfigurazioneMailUscita());
	}
}
