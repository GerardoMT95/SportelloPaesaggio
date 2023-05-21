package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.repository.AssegnamentoFascicoloFullRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search.AssegnamentoFascicoloSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.DettaglioCorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.RichiestaAsrDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AttivitaDaEspletare;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Ruoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StoricoRichiesta;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoDestinatario;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiTipoContenutoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ConfigurazioniEnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.CorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinatarioDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.SospensioneArchiviazioneAttivazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TemplateEmailDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.AllegatiRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.SospensioneArchiviazioneAttivazioneRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.SospensioneArchiviazioneAttivazioneSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.TemplateEmailSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IConfigurazioniEnteService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.AllegatoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.ComunicazioniService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.RichiestaASRService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.DestinatarioService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.SedutaCommissioneService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.PlaceHolder;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.dto.TemplateEmailDestinatariDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.service.ITemplateMailService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.service.impl.ResolveTemplateService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.VarieUtils;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class RichiestaASRServiceImpl implements RichiestaASRService
{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired private SospensioneArchiviazioneAttivazioneRepository dao;
	@Autowired private PraticaRepository praticaRepo;
	@Autowired private AllegatiRepository allegatiRepository;
	@Autowired private AssegnamentoFascicoloFullRepository afRepository;
	@Autowired private ResolveTemplateService templateResolver;
	@Autowired private AllegatoService allegatiService;
	@Autowired private UserUtil userUtil;
	@Autowired private ITemplateMailService templateService;
	@Autowired private DestinatarioService destinatarioService;
	@Autowired private ComunicazioniService comunicazioniService;
	@Autowired private SedutaCommissioneService sedutaCommissioneService;
	@Autowired private IConfigurazioniEnteService confEnte;
	
	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, propagation=Propagation.REQUIRED, readOnly=true, timeout=60000, rollbackFor= Exception.class)
	public Long count(SospensioneArchiviazioneAttivazioneSearch search) throws Exception
	{
		return dao.count(search);
	}
	
	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, propagation=Propagation.REQUIRED, readOnly=true, timeout=60000, rollbackFor= Exception.class)
	public PaginatedList<SospensioneArchiviazioneAttivazioneDTO> search(SospensioneArchiviazioneAttivazioneSearch search) throws Exception
	{
		return dao.search(search);
	}

	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, propagation=Propagation.REQUIRED, readOnly=true, timeout=60000, rollbackFor= Exception.class)
	public RichiestaAsrDTO findActiveDraft(UUID idPratica) throws Exception
	{
		RichiestaAsrDTO result = new RichiestaAsrDTO(dao.findDraft(idPratica));
		result.setAllegati(allegatiRepository.searchAllegatiRichiesteASR(result.getId()));
		return result;
	}

	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, propagation=Propagation.REQUIRED, readOnly=true, timeout=60000, rollbackFor= Exception.class)
	public RichiestaAsrDTO findById(Long id) throws Exception
	{
		RichiestaAsrDTO result = new RichiestaAsrDTO(dao.find(id));
		result.setAllegati(allegatiRepository.searchAllegatiRichiesteASR(result.getId()));
		return result;
	}

	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_WRITE, propagation=Propagation.REQUIRED, readOnly=false, timeout=60000, rollbackFor= Exception.class)
	public SospensioneArchiviazioneAttivazioneDTO insert(SospensioneArchiviazioneAttivazioneDTO entity) throws Exception
	{
		if(entity.getIdPratica() == null)
		{
			logger.error("Impossibile completare l'operazione, id pratica non definito");
			throw new Exception("Impossibile completare l'operazione, id pratica non definito");
		}
		PraticaDTO pratica = praticaRepo.find(entity.getIdPratica());
		AttivitaDaEspletare stato = pratica.getStato();
		canOperate(pratica, entity.getType());
		if(entity.getDraft().equals(false))
		{
			if(entity.getType() == null || StringUtil.isEmpty(entity.getNote()))
			{
				logger.error("Non è possibile eseguire l'operazione richiesta in quanto il tipo di operazione non è stato specificato o il campo 'note' è vuoto");
				throw new Exception("Non è possibile eseguire l'operazione richiesta in quanto il tipo di operazione non è stato specificato o il campo 'note' è vuoto");
			}
			entity.setStatoPratica(stato);
			AttivitaDaEspletare nuovoStato = statoDaAssumere(entity.getType(), entity.getIdPratica());
			praticaRepo.updateStatoPratica(entity.getIdPratica(), nuovoStato);
			List<AllegatiDTO> allegati = allegatiRepository.searchAllegatiRichiesteASR(entity.getId());
			inviaNotifica(entity, allegati);
		}
		entity.setId(dao.insert(entity));
		return entity;
	}

	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_WRITE, propagation=Propagation.REQUIRED, readOnly=false, timeout=60000, rollbackFor= Exception.class)
	public SospensioneArchiviazioneAttivazioneDTO update(SospensioneArchiviazioneAttivazioneDTO entity) throws Exception
	{
		if(entity.getIdPratica() == null)
		{
			logger.error("Impossibile completare l'operazione, id pratica non definito");
			throw new Exception("Impossibile completare l'operazione, id pratica non definito");
		}
		PraticaDTO pratica = praticaRepo.find(entity.getIdPratica());
		AttivitaDaEspletare stato = pratica.getStato();
		canOperate(pratica, entity.getType());
		if(entity.getDraft().equals(false))
		{
			if(entity.getType() == null || StringUtil.isEmpty(entity.getNote()))
			{
				logger.error("Non è possibile eseguire l'operazione richiesta in quanto il tipo di operazione non è stato specificato o il campo 'note' è vuoto");
				throw new Exception("Non è possibile eseguire l'operazione richiesta in quanto il tipo di operazione non è stato specificato o il campo 'note' è vuoto");
			}
			entity.setStatoPratica(stato);
			AttivitaDaEspletare nuovoStato = statoDaAssumere(entity.getType(), entity.getIdPratica());
			praticaRepo.updateStatoPratica(entity.getIdPratica(), nuovoStato);
			if(Arrays.asList(StoricoRichiesta.ARCHIVIAZIONE, StoricoRichiesta.SOSPENSIONE).contains(entity.getType()))
				sedutaCommissioneService.eliminaPraticaDaSeduteAttive(entity.getIdPratica());
			entity.setData(new Date());
			List<AllegatiDTO> allegati = allegatiRepository.searchAllegatiRichiesteASR(entity.getId());
			inviaNotifica(entity, allegati);
		}
		Integer counter = dao.update(entity);
		if(counter != 1)
		{
			logger.error("Mi aspettavo di modificare 1 elemento e ne sono stati modificati {}", counter);
			throw new Exception("Mi aspettavo di modificare 1 elemento e ne sono stati modificati " + counter);
		}
		return entity;
	}

	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_WRITE, propagation=Propagation.REQUIRED, readOnly=false, timeout=60000, rollbackFor= Exception.class)
	public Integer delete(Long id) throws Exception
	{
		SospensioneArchiviazioneAttivazioneSearch search = new SospensioneArchiviazioneAttivazioneSearch();
		SospensioneArchiviazioneAttivazioneDTO dto = dao.find(id);
		PraticaDTO pratica = praticaRepo.find(dto.getIdPratica());
		verificaUtente(pratica);
		if(dto.getDraft().equals(false))
		{
			logger.error("Non è possibile eliminare la richiesta di {} in quanto il suo stato non è più 'in bozza'", dto.getType().name());
			throw new Exception("Non è possibile eliminare la richiesta di"+ dto.getType().name() +" in quanto il suo stato non è più 'in bozza'");
		}
		List<UUID> ids = dao.findAllegatiRichiesta(id); 
		for(UUID idAllegato: ids)
		{
			logger.info("Cancello l'allegato con id {}\n", idAllegato);
			allegatiService.deleteAllegato(dto.getIdPratica().toString(), idAllegato.toString());
		}
		search.setId(id);
		Integer deleted = dao.delete(search);
		return deleted;
	}
	
	/**
	 * Meotodo per completare la richiesta setando le informazioni corrette prese da db
	 * @param entity
	 * @throws Exception
	 */
//	private void completaRichiesta(SospensioneArchiviazioneAttivazioneDTO entity) throws Exception
//	{
//		if(entity.getId() != null)
//		{
//			SospensioneArchiviazioneAttivazioneDTO dbVersion = dao.find(entity.getId()); 
//			entity.setIdPratica(dbVersion.getIdPratica());
//			entity.setType(dbVersion.getType());
//			entity.setUsernameUtenteCreazione(dbVersion.getUsernameUtenteCreazione());
//		}
//	}
	
	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, propagation=Propagation.REQUIRED, readOnly=true, timeout=60000, rollbackFor= Exception.class)
	public void verifyPermissions(Long idRichiesta, UUID idPratica) throws Exception
	{
		PraticaDTO pratica = praticaRepo.find(idPratica);
		SospensioneArchiviazioneAttivazioneDTO entity = dao.find(idRichiesta);
		canOperate(pratica, entity.getType());
		verificaUtente(pratica);
	}
	
	/**
	 * Metodo che verifica se la richiesta che si vuole inserire/aggiornare ha i parametri richiesti 
	 * (tipologia richiesta e id della procedura a cui associarla) ed è congruente allo stato attuale 
	 * della pratica: Una pratica può essere archiviata sempre fino a quando non viene trasmesso il 
	 * provvedimento, può essere sempre sospesa e sempre attivata.
	 * 
	 * @param idPratica
	 * @param stato
	 * @param type
	 * @throws Exception
	 */
	private void canOperate(PraticaDTO pratica,  StoricoRichiesta type) throws Exception
	{
		AttivitaDaEspletare stato = pratica.getStato();
		//Verifico che l'utente che vuole operare sia un rup
		verificaUtente(pratica);
		//Verifico se il passaggio di stato è lecito in base al ruolo e allo stato della pratica
		if(type != null)
		{
			final List<AttivitaDaEspletare> nonArchiviabile = Arrays.asList(AttivitaDaEspletare.ARCHIVIATA, AttivitaDaEspletare.TRANSMITTED);
			final List<AttivitaDaEspletare> nonSospendibile = Arrays.asList(AttivitaDaEspletare.ARCHIVIATA, AttivitaDaEspletare.SOSPESA);
			final List<AttivitaDaEspletare> attivabile 		= Arrays.asList(AttivitaDaEspletare.SOSPESA, AttivitaDaEspletare.ARCHIVIATA);
	
			switch(type)
			{
			case ARCHIVIAZIONE:
				if(nonArchiviabile.contains(stato))
				{
					logger.error("Stato non valido per l'archiviazione della pratica");
					throw new Exception("Stato non valido per l'archiviazione della pratica");
				}
				break;
			case SOSPENSIONE:
				if(nonSospendibile.contains(stato))
				{
					logger.error("Stato non valido per la sospensione della pratica");
					throw new Exception("Stato non valido per la sospensione della pratica");
				}
				break;
			case ATTIVAZIONE:
				if(!attivabile.contains(stato))
				{
					logger.error("Stato non valido per la riattivazione della pratica");
					throw new Exception("Stato non valido per la riattivazione della pratica");
				}
				else if(stato.equals(AttivitaDaEspletare.ARCHIVIATA) && !userUtil.getRuolo().equals(Ruoli.AMMINISTRATORE))
				{
					logger.error("Solo un amministratore può attivare una pratica archiviata");
					throw new Exception("Solo un amministratore può attivare una pratica archiviata");
				}
				break;
			}
		}
	}
	
	/**
	 * Verifica che l'utente sia loggato con il gruppo a cui è delegata la pratuica e che
	 * sia rup o amministratore di ente
	 * @param pratica
	 * @throws Exception
	 */
	private void verificaUtente(PraticaDTO pratica) throws Exception
	{
		if(!pratica.getEnteDelegato().equals(Integer.toString(userUtil.getIntegerId())))
		{
			logger.error("Non possiedi i permessi per effettuare questa operazione. L'ente delegato associato a questa pratica è {}, mentre quello usato dall'utente è {}.", pratica.getEnteDelegato(), userUtil.getIntegerId());
			throw new Exception("Non possiedi i permessi per effettuare questa operazione. L'ente delegato associato a questa pratica è "+pratica.getEnteDelegato()+ ", mentre quello usato dall'utente è " + userUtil.getIntegerId());
		}
		if(!userUtil.getRuolo().equals(Ruoli.AMMINISTRATORE))
		{
			AssegnamentoFascicoloSearch filters = new AssegnamentoFascicoloSearch();
			filters.setIdFascicolo(pratica.getId());
			filters.setIdOrganizzazione(userUtil.getIntegerId());
			filters.setRup(true);
			if(afRepository.count(filters) != 1)
			{
				logger.info("Solo l'amministratore dell'ente e rup associato alla pratica possono effettuare questa operazione");
				throw new Exception("Solo l'amministratore dell'ente e rup associato alla pratica possono effettuare questa operazione");
			}
		}
	}

	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, propagation=Propagation.REQUIRED, readOnly=true, timeout=60000, rollbackFor= Exception.class)
	public List<RichiestaAsrDTO> getStorico(UUID idPratica) throws Exception
	{
		List<RichiestaAsrDTO> storico = null;
		List<SospensioneArchiviazioneAttivazioneDTO> temp = dao.getStorico(idPratica);
		if(temp != null && !temp.isEmpty())
		{
			storico = new ArrayList<RichiestaAsrDTO>();
			for(SospensioneArchiviazioneAttivazioneDTO t: temp)
			{
				RichiestaAsrDTO s = new RichiestaAsrDTO(t);
				s.setAllegati(allegatiRepository.searchAllegatiRichiesteASR(s.getId()));
				storico.add(s);
			}
		}
		return storico;
	}
	
	private AttivitaDaEspletare statoDaAssumere(StoricoRichiesta type, UUID idPratica) throws Exception
	{
		switch(type)
		{
		case ARCHIVIAZIONE:
			return AttivitaDaEspletare.ARCHIVIATA;
		case SOSPENSIONE:
			return AttivitaDaEspletare.SOSPESA;
		case ATTIVAZIONE:
			return praticaRepo.getUltimoStatoValido(idPratica);
		default:
			logger.error("Il tipo di richiesta è nullo o non valido");
			throw new Exception("Il tipo di richiesta è nullo o non valido");
		}
	}
	
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, readOnly=true, propagation=Propagation.MANDATORY, rollbackFor=Exception.class)
	private void inviaNotifica(SospensioneArchiviazioneAttivazioneDTO entity, List<AllegatiDTO> allegati) throws Exception
	{
		StoricoRichiesta type = entity.getType();
		UUID idPratica = entity.getIdPratica();
		PraticaDTO pratica = praticaRepo.find(idPratica);
		ConfigurazioniEnteDTO conf = confEnte.find(Integer.parseInt(pratica.getEnteDelegato()));
		TemplateEmailDTO template = templateService.findAncheSuDefault(userUtil.getIntegerId(), type.getCodice());
		DettaglioCorrispondenzaDTO comunicazione = comunicazioniService.create(idPratica);
		CorrispondenzaDTO corrispondenza = comunicazione.getCorrispondenza();
		List<DestinatarioDTO> destinatari = destinatarioService.findDestinatariNotifica(idPratica, userUtil.getIntegerId(), userUtil.getGruppo());
		List<DestinatarioDTO> destTemp = destinatarioService.findDestinatariForSoprintendenze(Collections.singletonList(Integer.parseInt(pratica.getEnteDelegato())), TipoDestinatario.TO);
		destinatari = VarieUtils.eliminaDuplicati(destinatari, destTemp);
		
		Boolean seutaEnabled = (conf != null && conf.getTipoPraticaSeduta().contains(pratica.getTipoProcedimento())) ||
								Arrays.asList(1,2).contains(pratica.getTipoProcedimento());
		
		if(seutaEnabled)
		{
			destTemp = destinatarioService.findDestinatarioCommissioneLocale(Long.parseLong(pratica.getEnteDelegato()), pratica.getDataInizioLavorazione());
			destinatari = VarieUtils.eliminaDuplicati(destinatari, destTemp);
		}
		Function<String, String> myResolver = (placeholder) ->
		{
			switch(PlaceHolder.valueOf(placeholder))
			{
			case NOTE_SOSPENSIONE:
			case NOTE_ARCHIVIAZIONE:
			case NOTE_RIATTIVAZIONE:
				return entity.getNote();
			default:
				return placeholder;
			}
		};
		corrispondenza.setOggetto(templateResolver.risolviTesto(template.getPlaceholders(), template.getOggetto(), pratica, myResolver));
		corrispondenza.setTesto(templateResolver.risolviTesto(template.getPlaceholders(), template.getTesto(), pratica, myResolver));
		corrispondenza.setIdOrganizzazioneOwner(userUtil.getIntegerId());
		corrispondenza.setComunicazione(true);
		corrispondenza.setBozza(false);
		comunicazione.setCorrispondenza(corrispondenza);
		comunicazione.setDestinatari(destinatari);
		List<AllegatiTipoContenutoDTO> allegatiTC;
		if(allegati != null && !allegati.isEmpty())
		{
			allegatiTC = allegati.stream().map(m -> new AllegatiTipoContenutoDTO(m.getId(), 800)).collect(Collectors.toList());
			comunicazione.setAllegati(allegatiTC);
			comunicazione.setAllegatiInfo(allegati);
		}
		comunicazioniService.saveComunication(comunicazione, idPratica);
		comunicazioniService.sendComunication(comunicazione, idPratica);
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public DettaglioCorrispondenzaDTO prepareTemplateRichiesta(UUID idPratica, String codice) throws Exception
	{
		DettaglioCorrispondenzaDTO comunicazione = comunicazioniService.create(idPratica);
		CorrispondenzaDTO corrispondenza = comunicazione.getCorrispondenza();
		int idOrganizzazione = userUtil.getIntegerId();
		List<DestinatarioDTO> destinatari = destinatarioService.findDestinatariNotifica(idPratica, userUtil.getIntegerId(), userUtil.getGruppo(), codice.equals("REQ_ATT"), codice.equals("REQ_ATT"));
		Function<String, String> myResolver = (placeholder) ->
		{
			switch(PlaceHolder.valueOf(placeholder))
			{
			case NOME_FUNZIONARIO:
				return userUtil.getMyProfile().getNome();
			case COGNOME_FUNZIONARIO:
				return userUtil.getMyProfile().getCognome();
			default:
				return placeholder;
			}
		};
		
		PraticaDTO pratica = new PraticaDTO();
		pratica.setId(idPratica);
		pratica = praticaRepo.find(pratica);
		
		TemplateEmailSearch search = new TemplateEmailSearch();
		search.setCodice(codice);
		search.setIdOrganizzazione(idOrganizzazione);
		Gruppi gruppo = userUtil.getGruppo();
		search.setVisibilita(gruppo.name());
		search.setTipiProcedimentoAmmessi("%,"+pratica.getTipoProcedimento()+",%");
		templateService.sincronizza(search);
		PaginatedList<TemplateEmailDTO> tempPagList = templateService.search(search);
		if(tempPagList.getCount() != 1)
		{
			logger.error("Errore, trovati {} template con codice {}", tempPagList.getCount(), codice);
			throw new Exception("Errore, trovati "+tempPagList.getCount()+" template con codice "+ codice);
		}
		List<TemplateEmailDTO> tempList = tempPagList.getList();
		TemplateEmailDTO templateEmailDTO = tempList.get(0);
		TemplateEmailDestinatariDto infoTemp = templateService.info(idOrganizzazione,templateEmailDTO.getCodice());
		String oggetto=templateResolver.risolviTesto(templateEmailDTO.getPlaceholders(), infoTemp.getTemplate().getOggetto(), pratica, myResolver);
		corrispondenza.setOggetto(oggetto);
		String testo=templateResolver.risolviTesto(templateEmailDTO.getPlaceholders(), infoTemp.getTemplate().getTesto(), pratica, myResolver);
		corrispondenza.setTesto(testo);
		corrispondenza.setCodiceTemplate(codice);
		corrispondenza.setBozza(true);
		comunicazione.setCorrispondenza(corrispondenza);
		if(infoTemp.getDestinatari() != null)
			comunicazione.setDestinatari(infoTemp.getDestinatari().stream().map(DestinatarioDTO::new).collect(Collectors.toList()));
		
		comunicazione.setDestinatari(VarieUtils.eliminaDuplicati(comunicazione.getDestinatari(), destinatari));
		comunicazioniService.saveComunication(comunicazione, idPratica);
		return comunicazione;
	}

}
