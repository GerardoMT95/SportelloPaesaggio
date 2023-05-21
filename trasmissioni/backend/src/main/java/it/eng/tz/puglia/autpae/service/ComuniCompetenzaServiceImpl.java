package it.eng.tz.puglia.autpae.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.dto.LocalizzazioneTabDTO;
import it.eng.tz.puglia.autpae.entity.ComuniCompetenzaDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.ComuniCompetenzaPK;
import it.eng.tz.puglia.autpae.repository.base.ComuniCompetenzaBaseRepository;
import it.eng.tz.puglia.autpae.search.ComuniCompetenzaSearch;
import it.eng.tz.puglia.autpae.service.interfacce.ComuniCompetenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.GruppiRuoliService;
import it.eng.tz.puglia.autpae.service.interfacce.LocalizzazioneInterventoService;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.remote.dto.EnteDTO;
import it.eng.tz.puglia.servizi_esterni.remote.dto.EnteWIstatDTO;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.AnagraficaService;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.CommonService;

@Service
public class ComuniCompetenzaServiceImpl implements ComuniCompetenzaService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ComuniCompetenzaServiceImpl.class);
//	private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot access attachments for plan exclusion of this municipality";

	@Autowired 	private ComuniCompetenzaBaseRepository repository;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository
			  @Transactional(propagation=Propagation.MANDATORY, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	private ComuniCompetenzaPK insert(ComuniCompetenzaDTO entity) throws Exception {
		return repository.insert(entity);
	}
			  @Transactional(propagation=Propagation.MANDATORY, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	private int delete(ComuniCompetenzaSearch filter) throws Exception {
		return repository.delete(filter);
	}
	@Override @Transactional(propagation=Propagation.REQUIRED,  rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public PaginatedList<ComuniCompetenzaDTO> search(ComuniCompetenzaSearch filter) throws Exception {
		return repository.search(filter);
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FullRepository
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Altri metodi
	
	@Autowired	private CommonService commonService;
	@Autowired	private AnagraficaService anagraficaService;
	@Autowired	private GruppiRuoliService gruppiRuoliService;
	@Autowired	private LocalizzazioneInterventoService localizzazioneInterventoService;
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,  rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public void aggiorna(long idFascicolo, LocalizzazioneTabDTO localizzazione) throws Exception {
		
		ComuniCompetenzaSearch filter = new ComuniCompetenzaSearch();
		filter.setPraticaId(idFascicolo);
		filter.setCreazione(false);
		this.delete(filter);
		
		this.crea_aggiorna(idFascicolo, false, localizzazione);
	}
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,  rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public void crea(long idFascicolo) throws Exception {
		this.crea_aggiorna(idFascicolo, true, null);
	}
	
	
	private void crea_aggiorna(long idFascicolo, boolean creazione, LocalizzazioneTabDTO localizzazione) throws Exception {
		
		List<Integer> enti = gruppiRuoliService.entiForGruppoUtenteLoggato();
		
		List<Long> idEntiEffettivi = null;
		if (creazione==false)
			idEntiEffettivi = localizzazioneInterventoService.selectEffettivo(idFascicolo);
		
		List<EnteWIstatDTO> entiWIstat = commonService.selectEnteWIstatById(enti);
		for (EnteWIstatDTO ente : entiWIstat) {
			ComuniCompetenzaDTO comuniCompetenza = new ComuniCompetenzaDTO();
			comuniCompetenza.setPraticaId(idFascicolo);
			comuniCompetenza.setEnteId(ente.getId());
			//EnteDTO ente = commonService.findEnteById(idEnte);
			comuniCompetenza.setCodCat(ente.getCodice());
			comuniCompetenza.setDescrizione(ente.getDescrizione());
			//comuniCompetenza.setCodIstat(anagraficaService.getCodIstatFromCatastale(comuniCompetenza.getCodCat()));
			comuniCompetenza.setCodIstat(ente.getIstat());
			comuniCompetenza.setCreazione(creazione);
			
			if (creazione==false && idEntiEffettivi!=null && idEntiEffettivi.contains(Long.valueOf(ente.getId()))) {
				comuniCompetenza.setEffettivo(true);
			}
		/*	if (creazione==false && localizzazione!=null && localizzazione.getLocalizzazioneComuni()!=null && !localizzazione.getLocalizzazioneComuni().isEmpty()) {
				boolean effettivo = false;
				for (LocalizzazioneInterventoDTO intervento : localizzazione.getLocalizzazioneComuni()) {
					
					if (intervento.getComuneId()==idEnte) {
						effettivo = true;
						break;
						}
					
					if (effettivo==false && intervento.getParticelle()!=null && !intervento.getParticelle().isEmpty()) {
						for (ParticelleCatastaliDTO particella : intervento.getParticelle()) {
							if (particella.getComuneId()==idEnte || (particella.getCodCat()!=null && comuniCompetenza.getCodCat().equalsIgnoreCase(particella.getCodCat()))) {
								effettivo = true;
								break;
							}
						};
					}
					// teoricamente il resto è inutile
					if (effettivo==false && intervento.getUlterioriInformazioni()!=null) {
						if (intervento.getUlterioriInformazioni().getBpParchiEReserve()!=null && !intervento.getUlterioriInformazioni().getBpParchiEReserve().isEmpty()) {
							if (intervento.getComuneId()==idEnte) {
								effettivo = true;
								}
						}
						if (intervento.getUlterioriInformazioni().getUcpPaesaggioRurale()!=null && !intervento.getUlterioriInformazioni().getUcpPaesaggioRurale().isEmpty()) {
							if (intervento.getComuneId()==idEnte) {
								effettivo = true;
								}
						}
						if (intervento.getUlterioriInformazioni().getBpImmobileAreePubblico()!=null && !intervento.getUlterioriInformazioni().getBpImmobileAreePubblico().isEmpty()) {
							if (intervento.getComuneId()==idEnte) {
								effettivo = true;
								}
						}
					}
				}
				if (effettivo==true) {
					comuniCompetenza.setEffettivo(true);
				}
			}	*/
			this.insert(comuniCompetenza);
		};
	}
	
}