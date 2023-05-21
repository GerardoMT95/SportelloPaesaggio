package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.istruttoria;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.RoleAwareController;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.FascicoloDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainNumberValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.allegati.DocumentoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istruttoria.ProtocolloDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AttivitaDaEspletare;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoParere;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoRelazione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoSeduta;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PraticaSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IConfigurazioniEnteService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IPraticaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.GruppiRuoliDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.service.RemoteSchemaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.FascicoloService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.RichiestaASRService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.IstrPraticaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.SedutaCommissioneService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.Logging;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.privacy.Privacy;
import it.eng.tz.puglia.util.log.LogUtil;

@RestController
@Privacy(codiceApplicazione ="PAE_ISTRUTTORIA")
@RequestMapping("istruttoria/pratica")
public class IstruttoriaFascicoloController extends RoleAwareController
{
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String SEARCH  		= "/search";
	private static final String FIND	 		= "/find";
	private static final String COUNTERS		= "/getCounters";
	private static final String WORKING 		= "/switchToInWorking";
	private static final String PROTOCOL 		= "/protocolla";
	private static final String DICHIARAZIONI 	= "/getDichiarazioni";
	private static final String VERBALE_REQ		= "/verbaleCLRequired";
	private static final String TRANSMISSION	= "/switchToTransmission";
		
	@Qualifier("istrPraticaService") @Autowired IstrPraticaService praticaService;
	@Autowired SedutaCommissioneService sedutaService;
	@Autowired IPraticaService praticaServiceRich;
	@Autowired FascicoloService service;
	@Autowired UserUtil userUtil;
	@Autowired RichiestaASRService asrService;
	@Autowired IConfigurazioniEnteService confService;
	@Autowired RemoteSchemaService remoteService;
	
	@Logging
	@GetMapping(value=SEARCH, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<PaginatedList<FascicoloDto>>> searchFascicoli(PraticaSearch filters) throws Exception
	{
		praticaService.prepareForSearch(filters);
		filters.setInIstruttoria(true);
		return okResponse(praticaService.search_istr(filters));
	}
	
	@Logging
	@GetMapping(value=FIND, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<FascicoloDto>> findByCode(@RequestParam(value="codiceFascicolo", required=false)String codice,
																	 @RequestParam(value="idPratica", 		required=false)UUID id) throws Exception
	{
		if(codice == null && id == null)
		{
			logger.error("Errore: non è possibile cercare una pratica, id e codice null");
			throw new Exception("Errore: non è possibile cercare una pratica, id e codice null");
		}
		//controlli di accesso
		PraticaSearch filters = new PraticaSearch();
		this.praticaService.prepareForSearch(filters); //qui vengono impostati tutti i criteri di visibilità delle pratiche....
		if(id!=null) {
			filters.setId(id);	
		}
		if(codice!=null) {
			filters.setCodicePraticaAppptr(codice);
		}
		List<PraticaDTO> list = this.praticaService.searchAll(filters);
		if(list==null || list.isEmpty()) {
			logger.error("pratica non trovata o non di competenza dello user corrente "+LogUtil.getUser());
			throw new CustomOperationToAdviceException("Pratica non trovata !");
		}
		//-----------end controlli di accesso
		FascicoloDto response;
		filters.setCodicePraticaAppptr(codice);
		filters.setId(id);
		filters.setInIstruttoria(true);
		response = service.find(filters);
		response.setStoricoASR(asrService.getStorico(response.getId()));
		response.setShowVCL(sedutaService.sedutaPresente(response.getId()));
		service.pulisciLineaTemporale(response);
		return okResponse(response);
	}
	
	@Logging
	@GetMapping(value=COUNTERS, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Map<String, Long>>> getCounterForIstruttoria(PraticaSearch filters) throws Exception
	{
		Map<String, Long> counters = service.getCounterForIstruttoria(filters);
		return okResponse(counters);
	}
	
	@Logging
	@PutMapping(value=WORKING, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Boolean>> inWorking(@RequestParam("idPratica")UUID idPratica) throws Exception
	{
		PraticaDTO pratica = new PraticaDTO();
		pratica.setId(idPratica);
		pratica = praticaServiceRich.find(pratica);
		this.checkDiMiaCompetenza(pratica);
		int idOrganizzazione = userUtil.getIntegerId();
		if(!pratica.getEnteDelegato().equals(idOrganizzazione+"")) {
			new CustomOperationToAdviceException("Pratica non di competenza, impossibile procedere!");
		}
		if(!pratica.getStato().equals(AttivitaDaEspletare.IN_PREISTRUTTORIA))
		{
			logger.error("Non è possibile portare una pratica dallo stato '{}' allo stato di 'In lavorazione'", pratica.getStato());
			throw new Exception("Non è possibile portare una pratica dallo stato '"+ pratica.getStato() +"' allo stato di 'In lavorazione'");
		}
		service.cambiaStato(idPratica, AttivitaDaEspletare.IN_LAVORAZIONE);
		return okResponse(true);
	}
	
	@Logging
	@GetMapping(value=DICHIARAZIONI, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<List<DocumentoDto>>> getDichiarazioni(@RequestParam("idPratica") UUID idPratica)
	{
		try 
		{
			this.checkDiMiaCompetenza(idPratica);
			ResponseEntity<BaseRestResponse<List<DocumentoDto>>> response = super.okResponse(praticaService.getDichiarazioni(idPratica));
			return response;
		} catch (Exception e) 
		{
			logger.error("Errore nel reperimento dei metadati riguardando le dichiarazioni uploadate ", e.getMessage(), e);
			return super.koResponse(e, new BaseRestResponse<>());
		}
	}
	
	@Logging
	@PutMapping(value=PROTOCOL, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Boolean>> protocollaPratica(@RequestBody ProtocolloDto protocollo) throws Exception
	{
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.REG_);
		this.checkDiMiaCompetenzaByCodicePraticaAppptr(protocollo.getCodiceFascicolo());
		this.service.updateProtocolloFascicolo(protocollo);
		return okResponse(true);
	}
	
	@Logging
	@GetMapping(value=VERBALE_REQ, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Boolean>> requiredVCL(@RequestParam UUID idPratica) throws Exception
	{
		checkAbilitazioneFor(Gruppi.REG_, Gruppi.ED_);
		PraticaDTO pratica = new PraticaDTO();
		pratica.setId(idPratica);
		pratica = praticaServiceRich.find(pratica);
		this.checkDiMiaCompetenza(pratica);
		long idUfficio = Long.parseLong(pratica.getEnteDelegato());
		boolean required = false;
		List<PlainNumberValueLabel> orgs = remoteService.getEnteRegione();
		if(orgs == null || !orgs.stream().anyMatch(p -> p.getValue().equals(idUfficio)))
			required = confService.showVerbaleCommissionelocale(idUfficio, pratica.getTipoProcedimento());
		return okResponse(required);
	}
	
	@Logging
	@PutMapping(value=TRANSMISSION, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Boolean>> cambiaADaTrasmettere(@RequestParam("idPratica")UUID idPratica) throws Exception
	{
		checkAbilitazioneFor(Gruppi.REG_, Gruppi.ED_);
		PraticaDTO pratica = new PraticaDTO();
		pratica.setId(idPratica);
		pratica = praticaServiceRich.find(pratica);
		this.checkDiMiaCompetenza(pratica);
		int idOrganizzazione = userUtil.getIntegerId();
		if(!pratica.getEnteDelegato().equals(idOrganizzazione+"")) {
			new CustomOperationToAdviceException("Pratica non di competenza, impossibile procedere!");
		}
		if(!pratica.getStato().equals(AttivitaDaEspletare.IN_LAVORAZIONE))
		{
			logger.error("Non è possibile portare una pratica dallo stato '{}' allo stato di 'In trasmissione'", pratica.getStato());
			throw new Exception("Non è possibile portare una pratica dallo stato '"+ pratica.getStato() +"' allo stato di 'In lavorazione'");
		}
		checkValidityForTransmission(pratica);
		service.passaTrasmissione(idPratica);
		return okResponse(true);
	}
	
	private void checkValidityForTransmission(PraticaDTO pratica) throws Exception
	{
		long idUfficio = Long.parseLong(pratica.getEnteDelegato());
		
		boolean verbaleRequired = false;
		List<PlainNumberValueLabel> orgs = remoteService.getEnteRegione();
		if(orgs == null || !orgs.stream().anyMatch(p -> p.getValue().equals(idUfficio))) {
			verbaleRequired = confService.showVerbaleCommissionelocale(idUfficio, pratica.getTipoProcedimento());
			if(!Arrays.asList(StatoParere.PARERE_INSERITO_SOPRINTENDENZA, StatoParere.PARERE_INSERITO_ENTE).contains(pratica.getStatoParereSoprintendenza()))
			{
				logger.error("Parere della soprintendenza non presente, impossibile procedere");
				throw new Exception("Parere della soprintendenza non presente, impossibile procedere");
			}
		}
		if(verbaleRequired && !pratica.getStatoSedutaCommissione().equals(StatoSeduta.VERBALE_SEDUTA_ALLEGATO))
		{
			logger.error("Verbale seduta di commissione non presente, impossibile procedere");
			throw new Exception("Verbale seduta di commissione non presente, impossibile procedere");
		}
		if(!Arrays.asList(StatoRelazione.RELAZIONE_TRASMESSA_CON_AVVIO, StatoRelazione.RELAZIONE_TRASMESSA_SENZA_AVVIO).contains(pratica.getStatoRelazioneEnte()))
		{
			logger.error("Relazione ente non presente, impossibile procedere");
			throw new Exception("Relazione ente non presente, impossibile procedere");
		}
	}

}
