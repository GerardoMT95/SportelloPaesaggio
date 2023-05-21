package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.DestinazioneUrbanisticaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ComuniCompetenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinazioneUrbanisticaInterventoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.LocalizzazioneInterventoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TnoPptrStrumentiUrbanisticiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ComuniCompetenzaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.DestinazioneUrbanisticaInterventoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.LocalizzazioneInterventoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TnoPptrStrumentiUrbanisticiRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.DestinazioneUrbanisticaInterventoSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.service.RemoteSchemaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.DestinazioneUrbanisticaService;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class DestinazioneUrbanisticaServiceImpl implements DestinazioneUrbanisticaService {
	@Autowired
	private DestinazioneUrbanisticaInterventoRepository repository;

	
	@Autowired
	private LocalizzazioneInterventoRepository localizzazioneInterventoRepository;

	@Autowired
	private ComuniCompetenzaRepository comuniCompetenzaRepository;

	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<DestinazioneUrbanisticaDto> saveOrUpdate(List<DestinazioneUrbanisticaDto> destinazione, UUID idPratica)
			throws Exception {
		if (destinazione == null || destinazione.isEmpty()) {
			// cancello tutto ciò che riguarda la "Destinazione Urbanistica" per quella
			// pratica
			this.delete(idPratica);

			return destinazione;
		} else {
			return this.saveOrUpdate(destinazione);
		}
	}

	@Override
	public List<DestinazioneUrbanisticaDto> saveOrUpdate(List<DestinazioneUrbanisticaDto> dto) throws Exception {
		try {
			// cancello tutto ciò che riguarda la "Destinazione Urbanistica" per quella
			// pratica
			this.delete(dto.get(0).getIdPratica());

			validaLista(dto, false);

			int index = 1;
			for (DestinazioneUrbanisticaDto destinazione : dto) {
				validaSingolo(destinazione, false);
				DestinazioneUrbanisticaInterventoDTO entity = new DestinazioneUrbanisticaInterventoDTO(destinazione,
						index, null, null);
				repository.insert(entity);
				index++;
			}
		} catch (Exception e) {
			logger.error("Errore in saveOrUpdate ", e);
			throw e;
		}
		return dto;
	}

	@Override
	public List<DestinazioneUrbanisticaDto> findByIdFascicolo(UUID idPratica) throws Exception {
		List<DestinazioneUrbanisticaDto> destinazioneUrbanistica = new ArrayList<>();

		try {
			List<LocalizzazioneInterventoDTO> comuniLocalizzazione = localizzazioneInterventoRepository
					.select(idPratica);
			if (comuniLocalizzazione == null || comuniLocalizzazione.isEmpty()) {
				return destinazioneUrbanistica;
			}

			List<Long> idComuniLocalizzazione = new ArrayList<>(comuniLocalizzazione.size());
			comuniLocalizzazione.forEach(elem -> {
				idComuniLocalizzazione.add(elem.getComuneId());
			});

			if (repository.countByIdPratica(idPratica) > 0) {
				DestinazioneUrbanisticaInterventoSearch search = new DestinazioneUrbanisticaInterventoSearch();
				search.setPraticaId(idPratica.toString());
				List<DestinazioneUrbanisticaInterventoDTO> listaDTO = repository.search(search).getList();
				listaDTO.forEach(elem -> {
					if (idComuniLocalizzazione.contains(elem.getComuneId())) {
						ComuniCompetenzaDTO comuneCompDto = new ComuniCompetenzaDTO();
						comuneCompDto.setPraticaId(idPratica);
						comuneCompDto.setEnteId((int) elem.getComuneId());
						comuneCompDto = comuniCompetenzaRepository.find(comuneCompDto);
						boolean mostraCoerenza = false;
						Date coerenzaData = null;
						String coerenzaAtto = null;
						coerenzaAtto = comuneCompDto.getVincoloArt100();
						destinazioneUrbanistica
								.add(new DestinazioneUrbanisticaDto(elem, mostraCoerenza, coerenzaData, coerenzaAtto));
					}
				});
			}
		} catch (Exception e) {
			logger.error("Errore in findByIdFascicolo ", e);
			throw e;
		}
		return destinazioneUrbanistica;
	}

	@Override
	public Integer delete(UUID idPratica) throws Exception {
		DestinazioneUrbanisticaInterventoDTO entity = new DestinazioneUrbanisticaInterventoDTO();
		entity.setPraticaId(idPratica);
		return repository.delete(entity);
	}

	@Override
	public Integer deleteForComune(UUID idPratica, long idComune) throws Exception {
		DestinazioneUrbanisticaInterventoDTO entity = new DestinazioneUrbanisticaInterventoDTO();
		entity.setPraticaId(idPratica);
		entity.setComuneId(idComune);
		return repository.deleteForComune(entity);
	}

	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, readOnly = true, propagation = Propagation.MANDATORY, timeout = 60000, rollbackFor = {
			Exception.class })
	public void valida(List<DestinazioneUrbanisticaDto> destinazione, UUID idPratica) throws Exception {
		if (destinazione != null && !destinazione.isEmpty()) {
			this.valida(destinazione);
		} else {
			if (localizzazioneInterventoRepository.countByIdPratica(idPratica) != 0) {
				throw new CustomOperationToAdviceException("Errore in Validazione Destinazione Urbanistica!");
			} else {
				throw new CustomOperationToAdviceException("Errore in Validazione Destinazione Urbanistica: Impossibile validare in quanto"
						+ " non sono presenti comuni nella localizzazione dell'istanza");
			}
		}
	}

	@Override
	public void valida(List<DestinazioneUrbanisticaDto> entity) throws Exception {
		this.validaLista(entity, true);
		for (DestinazioneUrbanisticaDto elem : entity) {
			this.validaSingolo(elem, true);
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// VALIDAZIONE

	private void validaLista(List<DestinazioneUrbanisticaDto> listaDestinazione, boolean completo) throws Exception {

		List<LocalizzazioneInterventoDTO> comuniLocalizzazione = localizzazioneInterventoRepository
				.select(listaDestinazione.get(0).getIdPratica());
		List<Long> idComuniLocalizzazione = new ArrayList<>(comuniLocalizzazione.size());
		comuniLocalizzazione.forEach(elem -> {
			idComuniLocalizzazione.add(elem.getComuneId());
		});

		List<Long> idComuniDestinazione = new ArrayList<>(listaDestinazione.size());
		for (DestinazioneUrbanisticaDto destinazione : listaDestinazione) {
			if (!idComuniLocalizzazione.contains(destinazione.getComuneId()))
				throw new Exception("Errore in Validazione Destinazione Urbanistica (15)");
			idComuniDestinazione.add(destinazione.getComuneId());
		}

		if (completo == true) {
			if (!(idComuniDestinazione.containsAll(idComuniLocalizzazione)
					&& idComuniLocalizzazione.containsAll(idComuniDestinazione)))
				throw new Exception("Errore in Validazione Destinazione Urbanistica (16)");
		}
	}

	@Autowired
	ComuniCompetenzaRepository comuniCompDao;
	
	private void validaSingolo(DestinazioneUrbanisticaDto destinazione, boolean completo) throws Exception {

		if (destinazione == null)
			throw new Exception("Errore in Validazione Destinazione Urbanistica (1)");

		if (destinazione.getStrumentoUrbanistico() != null
				&& (destinazione.getStrumentoUrbanistico() <= 0 || destinazione.getStrumentoUrbanistico() > 3))
			throw new Exception("Errore in Validazione Destinazione Urbanistica (2)");

		if (destinazione.getStrumentoInAdozione() != null
				&& (destinazione.getStrumentoInAdozione() <= 0 || destinazione.getStrumentoInAdozione() > 3))
			throw new Exception("Errore in Validazione Destinazione Urbanistica (3)");

		if (destinazione.getStrumentoInAdozione() != null && destinazione.getStrumentoInAdozione() == 1) {
			if (destinazione.getAdottatoData() != null || !StringUtil.isEmpty(destinazione.getAdottatoCon()))
				throw new Exception("Errore in Validazione Destinazione Urbanistica (3b)");
		}

		if (completo == true) {
			if (destinazione.getStrumentoUrbanistico() == null)
				throw new Exception("Errore in Validazione Destinazione Urbanistica (4)");
			if (destinazione.getApprovatoData() == null)
				throw new Exception("Errore in Validazione Destinazione Urbanistica (5)");
			if (StringUtil.isEmpty(destinazione.getApprovatoCon()))
				throw new Exception("Errore in Validazione Destinazione Urbanistica (6)");
			if (StringUtil.isEmpty(destinazione.getDestinazioneUrbanistica()))
				throw new Exception("Errore in Validazione Destinazione Urbanistica (7)");

			//se lo strumento in adozione art.100 non è adeguato, non è obbligatoria
			if (destinazione.getConfermaCoerenza() == null ||destinazione.getConfermaCoerenza() == false){
				ComuniCompetenzaDTO comuneComp = comuniCompDao.findByPraticaAndEnteId(destinazione.getIdPratica(), destinazione.getComuneId().intValue());
				if(!comuneComp.getVincoloArt100().toLowerCase().contains("non adeguato")) {
					throw new CustomOperationToAdviceException("Errore nella validazione della scheda Destinazione urbanistica, selezionare il check-box <i>Conferma della coerenza dei dati inseriti nell'istanza rispetto al vigente strumento urbanistico</i>");	
				}
			}
				
			if (destinazione.getStrumentoInAdozione() == null)
				throw new Exception("Errore in Validazione Destinazione Urbanistica (9)");
			if (destinazione.getStrumentoInAdozione() != 1) {
				if (destinazione.getAdottatoData() == null)
					throw new Exception("Errore in Validazione Destinazione Urbanistica (10)");
				if (StringUtil.isEmpty(destinazione.getAdottatoCon()))
					throw new Exception("Errore in Validazione Destinazione Urbanistica (11)");
			} else { // destinazione.getStrumentoInAdozione()==1
				if (destinazione.getAdottatoData() != null || !StringUtil.isEmpty(destinazione.getAdottatoCon()))
					throw new Exception("Errore in Validazione Destinazione Urbanistica (11b)");
			}
			if (StringUtil.isEmpty(destinazione.getDestinazioneUrbanisticaAdottato()))
				throw new Exception("Errore in Validazione Destinazione Urbanistica (12)");
		}
	}

}