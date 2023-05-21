package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AttivitaDaEspletare;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ComuniCompetenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.LocalizzazioneInterventoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.service.RemoteSchemaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.AllegatoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.IstrPraticaService;

@Service("allegatiIstruttoria")
public class AllegatiIstruttoriaServiceImpl extends AllegatoServiceImpl implements AllegatoService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(AllegatiIstruttoriaServiceImpl.class);
	
	@Autowired private UserUtil userUtil;
	@Autowired private RemoteSchemaService remoteService;
	@Autowired private LocalizzazioneInterventoRepository liRepo;
	@Autowired private IstrPraticaService istrPraticaService;
	
	@Override
	public void checkDiMiaCompetenza(PraticaDTO pratica) throws CustomOperationToAdviceException
	{
		try
		{
			if (!diMiaCompetenza(pratica))
			{
				LOGGER.error("Pratica non trovata o di proprietà di altro utente...");
				throw new CustomOperationToAdviceException("Pratica non trovata..");
			}
		}catch(Exception e)
		{
			LOGGER.error("Errore nella verifica della competenza della pratica {}", e.getMessage(), e);
			throw new CustomOperationToAdviceException("Errore nella verifica della competenza della pratica");
		}
	}
	
	@Override
	public void checkStatoForUpdate(PraticaDTO pratica) throws CustomOperationToAdviceException
	{
		if (pratica.getStato().equals(AttivitaDaEspletare.TRANSMITTED))
		{
			LOGGER.error("Pratica in stato avanzato, impossibile procedere...");
			throw new CustomOperationToAdviceException("Pratica in stato avanzato, impossibile procedere...");
		}
	}

	@Override
	public void checkComuniCompetenza(List<ComuniCompetenzaDTO> entiCompetenza)
	{
		if (entiCompetenza == null || entiCompetenza.size() <= 0)
		{
			new CustomOperationToAdviceException("L'ente delegato selezionato non ha alcun territorio di competenza, impossibile procedere!!!");
		}
	}
	
	/**
	 * Metodo che verifica che l'utente loggato abbia la competenza su una determinata pratica
	 * @param pratica
	 * @return
	 * @throws Exception
	 */
	private boolean diMiaCompetenza(PraticaDTO pratica) throws Exception
	{
		Integer idOrganizzazione = null;
		switch(userUtil.getGruppo())
		{
		case REG_: 
		case ED_: 
			idOrganizzazione = userUtil.getIntegerId();
			userUtil.getLongId();
			return pratica.getEnteDelegato().equals(idOrganizzazione.toString());
		case CL_: //l'ente delegato deve essere tra gli enti a cui afferisce la commissione locale
			idOrganizzazione = userUtil.getIntegerId();
			return this.istrPraticaService.diMiaCompetenza(pratica);
		case ETI_:
		case SBAP_:
			idOrganizzazione = userUtil.getIntegerId();
			final List<Integer> comuni = remoteService.getEntiCOMUNIdiCompetenzaForOrganizzazione(idOrganizzazione);
			final List<Integer> comuniLI = liRepo.searchForEnteId(pratica.getId());
			return comuni.stream().anyMatch(cu -> comuniLI.stream().anyMatch(ci -> ci.equals(cu)));
		case RICHIEDENTI: 
		case ADMIN: return true;
		default: return false;
		}		
	}
	
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public int insertVisibilitaUlerioreDoc(UUID idAllegato,String ente) {
		this.allegatiDao.insertVisibilitaUlerioreDoc(idAllegato,ente);
		return 1;
	}
	
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public int update(AllegatiDTO allegato) {
		return this.allegatiDao.update(allegato);
	}
}
