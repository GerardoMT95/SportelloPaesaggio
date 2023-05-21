package it.eng.tz.puglia.autpae.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.eng.tz.puglia.autpae.civilia.migration.MigratorePptr;
import it.eng.tz.puglia.autpae.civilia.migration.MigratorePutt;
import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.utility.LoggingAet;

/**
 * controller attivo solo in fase di migrazione, a migrazione avvenuta non verrà avviato
 * @author Adriano Colaianni
 * @date 1 set 2021
 */
@RestController()
@ConditionalOnProperty("datasource.civ.enableMigration")
@RequestMapping(value = "/public", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MigratoreController extends _RestController {
	
	@Autowired MigratorePutt migratorePutt;
	@Autowired MigratorePptr migratorePptr;
	@Autowired ApplicationProperties props;

		
	private static final Logger log = LoggerFactory.getLogger(MigratoreController.class);

	/**
	 * avvia la migrazione delle pratiche, chiamare il microservizio con opportuna configurazione di lancio (properties)
	 * @autor Adriano Colaianni
	 * @date 1 set 2021
	 */
	@GetMapping("migrazione/putt/startMigrazionePratiche")
	@LoggingAet
	public void startMigrazionePratichePutt()
	{
		if(migratorePutt==null) 
			{
			log.info("Funzionalità di migrazione disattivate");
			return;
			}
		if(!props.isPutt()) {
			log.info("Funzionalità di migrazione attivate solo per PUTT, questa istanza non è PUTT!!!");
			return;
		}
		try
		{
			migratorePutt.testaMigrazione();
		}
		catch (Exception e) 
		{
			log.error("Errore la migrazione delle pratiche", e);
		}
	}
	
	
	/**
	 * avvia la migrazione delle pratiche, chiamare il microservizio con opportuna configurazione di lancio (properties)
	 * @autor Adriano Colaianni
	 * @date 1 set 2021
	 */
	@GetMapping("migrazione/autpae/startMigrazionePratiche")
	@LoggingAet
	public void startMigrazionePraticheAutpae()
	{
		if(migratorePptr==null) 
			{
			log.info("Funzionalità di migrazione disattivate");
			return;
			}
		if(!props.isAutPae()) {
			log.info("Funzionalità di migrazione attivate solo per Autpae, questa istanza non è Autpae!!!");
			return;
		}
		try
		{
			migratorePptr.migraPratiche();
		}
		catch (Exception e) 
		{
			log.error("Errore la migrazione delle pratiche", e);
		}
	}
	
	
	@GetMapping("migrazione/autpae/startMigrazionePraticheInLavorazione")
	@LoggingAet
	public void startMigrazionePraticheAutpaeInLavorazione()
	{
		if(migratorePptr==null) 
			{
			log.info("Funzionalità di migrazione disattivate");
			return;
			}
		if(!props.isAutPae()) {
			log.info("Funzionalità di migrazione attivate solo per Autpae, questa istanza non è Autpae!!!");
			return;
		}
		try
		{
			migratorePptr.migraPraticheInLavorazione();
		}
		catch (Exception e) 
		{
			log.error("Errore la migrazione delle pratiche", e);
		}
	}
	
	
	/**
	 * avvia la migrazione delle pratiche, chiamare il microservizio con opportuna configurazione di lancio (properties)
	 * @autor Adriano Colaianni
	 * @date 1 set 2021
	 */
	@GetMapping("migrazione/putt/startMigrazionePraticheByCodici")
	@LoggingAet
	public void startMigrazionePratichePuttByCodici(@RequestParam String codiciSeparatiDaVirgola)
	{
		if(migratorePutt==null) 
			{
			log.info("Funzionalità di migrazione disattivate");
			return;
			}
		if(!props.isPutt()) {
			log.info("Funzionalità di migrazione attivate solo per PUTT, questa istanza non è PUTT!!!");
			return;
		}
		try
		{
			migratorePutt.testaMigrazionePuttListaCodici(codiciSeparatiDaVirgola);
		}
		catch (Exception e) 
		{
			log.error("Errore la migrazione delle pratiche", e);
		}
	}

	
	@GetMapping("migrazione/putt/startMigrazioneMailPraticheByCodici")
	@LoggingAet
	public void startMigrazioneMailPratichePuttByCodici(@RequestParam String codiciSeparatiDaVirgola)
	{
		if(migratorePutt==null) 
			{
			log.info("Funzionalità di migrazione disattivate");
			return;
			}
		if(!props.isPutt()) {
			log.info("Funzionalità di migrazione attivate solo per PUTT, questa istanza non è PUTT!!!");
			return;
		}
		try
		{
			migratorePutt.testaMigrazioneMailPuttListaCodici(codiciSeparatiDaVirgola);
		}
		catch (Exception e) 
		{
			log.error("Errore la migrazione delle pratiche", e);
		}
	}
	

	
	@GetMapping("/migrazione/puttbe/startMigrazioneAlfresco")
	@LoggingAet
	public void startMigrazioneAlfresco()
	{
		if(migratorePutt==null) 
			{
			log.info("Funzionalità di migrazione disattivate");
			return;
			}
		if(!props.isPutt()) {
			log.info("Funzionalità di migrazione attivate solo per PUTT, questa istanza non è PUTT!!!");
			return;
		}
		try
		{
			migratorePutt.updateIdCmisAllegati();
		}
		catch (Exception e) 
		{
			log.error("Errore la migrazione delle pratiche", e);
		}
	}
	
	
	@GetMapping("migrazione/autpae/startMigrazionePraticheByCodici")
	@LoggingAet
	public void startMigrazionePraticheAutpaeByCodici(@RequestParam String codiciSeparatiDaVirgola)
	{
		if(migratorePptr==null) 
			{
			log.info("Funzionalità di migrazione disattivate");
			return;
			}
		if(!props.isAutPae()) {
			log.info("Funzionalità di migrazione attivate solo per Autpae, questa istanza non è Autpae!!!");
			return;
		}
		try
		{
			migratorePptr.migraPraticheDaCodici(codiciSeparatiDaVirgola);
		}
		catch (Exception e) 
		{
			log.error("Errore la migrazione delle pratiche", e);
		}
	}
	
	@GetMapping("/migrazione/autpaebe/startMigrazioneAlfresco")
	@LoggingAet
	public void startMigrazioneAlfrescoContent()
	{
		if(migratorePptr==null) 
			{
			log.info("Funzionalità di migrazione disattivate");
			return;
			}
		if(!props.isAutPae()) {
			log.info("Funzionalità di migrazione attivate solo per Autpae, questa istanza non è Autpae!!!");
			return;
		}
		try
		{
			migratorePptr.updateIdCmisAllegati();
		}
		catch (Exception e) 
		{
			log.error("Errore la migrazione delle pratiche", e);
		}
	}
	
	@GetMapping("/migrazione/putt/startMigrazioneFileVuoti")
	@LoggingAet
	public void startMigrazioneFileVuoti()
	{
		if(migratorePutt==null) 
			{
			log.info("Funzionalità di migrazione disattivate");
			return;
			}
		if(!props.isPutt()) {
			log.info("Funzionalità di migrazione attivate solo per Putt, questa istanza non è Putt!!!");
			return;
		}
		try
		{
			migratorePutt.updateAllegatiVuoti();
		}
		catch (Exception e) 
		{
			log.error("Errore la migrazione delle pratiche", e);
		}
	}
	
	@GetMapping("/migrazione/autpae/startMigrazioneFileVuotiPptr")
	@LoggingAet
	public void startMigrazioneFileVuotiPptr()
	{
		if(migratorePptr==null) 
			{
			log.info("Funzionalità di migrazione disattivate");
			return;
			}
		if(!props.isAutPae()) {
			log.info("Funzionalità di migrazione attivate solo per Ppptr, questa istanza non è Pptr!!!");
			return;
		}
		try
		{
			migratorePptr.updateAllegatiVuotiPptr();
		}
		catch (Exception e) 
		{
			log.error("Errore la migrazione delle pratiche", e);
		}
	}
	
	@GetMapping("migrazione/autpae/startMigrazioneMailPratichePptrByCodici")
	@LoggingAet
	public void startMigrazionePraticheMailPratichePptrByCodici(@RequestParam String codiciSeparatiDaVirgola)
	{
		if(migratorePptr==null) 
			{
			log.info("Funzionalità di migrazione disattivate");
			return;
			}
		if(!props.isAutPae()) {
			log.info("Funzionalità di migrazione attivate solo per Autpae, questa istanza non è Autpae!!!");
			return;
		}
		try
		{
			migratorePptr.migraMailPraticheDaCodici(codiciSeparatiDaVirgola);
		}
		catch (Exception e) 
		{
			log.error("Errore la migrazione delle pratiche", e);
		}
	}
	
	
}