package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.LegittimitaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.TipologiaDettaglioDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.LeggittimitaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.LeggittimitaProvvedimentiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.LeggittimitaTitoliDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.LeggittimitaProvvedimentiRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.LeggittimitaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.LeggittimitaTitoliRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.LeggittimitaProvvedimentiSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.LeggittimitaTitoliSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.LegittimitaService;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class LegittimitaServiceImpl implements LegittimitaService
{

	
	@Autowired
	MessageSource msgSrc;
	
	@Autowired
	private LeggittimitaRepository repository;
	
	@Autowired
	private LeggittimitaTitoliRepository leggittimitaTitoliRepository;
	
	@Autowired
	private LeggittimitaProvvedimentiRepository leggittimitaProvvedimentiRepository;
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, readOnly=false, propagation=Propagation.MANDATORY, timeout=60000, rollbackFor= {Exception.class})
	public LegittimitaDto saveOrUpdate(LegittimitaDto legittimita) throws Exception
	{
		try {
			valida(legittimita, false);
			LeggittimitaDTO entity = new LeggittimitaDTO(legittimita);
			if (repository.countByIdPratica(legittimita.getIdPratica())==1) {
				repository.update(entity);
			}
			else {
				repository.insert(entity);
			}
			
			// cancello i dati presenti nella tabella 'leggittimita_titoli'
			leggittimitaTitoliRepository.deleteByPratica(legittimita.getIdPratica());

			// inserisco i nuovi elementi nella tabella 'leggittimita_titoli'
			if (legittimita.getLegittimitaInfo()!=null && !legittimita.getLegittimitaInfo().isEmpty()) {
				for (int i = 0; i < legittimita.getLegittimitaInfo().size(); i++) {
					leggittimitaTitoliRepository.insert(new LeggittimitaTitoliDTO(legittimita.getLegittimitaInfo().get(i), legittimita.getIdPratica(), i+1));
				}
			}
			
			// cancello i dati presenti nella tabella 'leggittimita_provvedimenti'
			leggittimitaProvvedimentiRepository.deleteByPratica(legittimita.getIdPratica());

			// inserisco i nuovi elementi nella tabella 'leggittimita_provvedimenti'
			if (legittimita.getAutorizzatoPaesaggisticamenteInfo()!=null && !legittimita.getAutorizzatoPaesaggisticamenteInfo().isEmpty()) {
				for (int i = 0; i < legittimita.getAutorizzatoPaesaggisticamenteInfo().size(); i++) {
					leggittimitaProvvedimentiRepository.insert(new LeggittimitaProvvedimentiDTO(legittimita.getAutorizzatoPaesaggisticamenteInfo().get(i), legittimita.getIdPratica(), i+1));
				}
			}
		} catch (Exception e) {
			logger.error("Errore in saveOrUpdate",e);
			throw e;
		}
		return legittimita;
	}

	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, readOnly=true, propagation=Propagation.MANDATORY, timeout=60000, rollbackFor= {Exception.class})
	public LegittimitaDto findByIdFascicolo(UUID idPratica) throws Exception
	{
		LeggittimitaDTO legittimitaDTO = null;
		List<TipologiaDettaglioDto> tipologiaDettaglioTitoli = null;
		List<TipologiaDettaglioDto> tipologiaDettaglioProvvedimenti = null;
		
		try {
			if (repository.countByIdPratica(idPratica)==0)	// se non c'è ancora niente nel db per quella pratica
				legittimitaDTO = new LeggittimitaDTO();
			else
				legittimitaDTO = repository.selectByIdPratica(idPratica);
			
			LeggittimitaTitoliSearch searchTitoli = new LeggittimitaTitoliSearch();
			searchTitoli.setPraticaId(idPratica.toString());
			List<LeggittimitaTitoliDTO> listaTitoli = leggittimitaTitoliRepository.search(searchTitoli).getList();
			
			if (listaTitoli==null) {
				tipologiaDettaglioTitoli = null;
			}
			else if (listaTitoli.isEmpty()) {
				tipologiaDettaglioTitoli = new ArrayList<>();
			}
			else {
				tipologiaDettaglioTitoli = new ArrayList<>(listaTitoli.size());
				for (LeggittimitaTitoliDTO elem : listaTitoli) {
					tipologiaDettaglioTitoli.add(new TipologiaDettaglioDto(elem));
				}
			}
			
			LeggittimitaProvvedimentiSearch searchProvvedimenti = new LeggittimitaProvvedimentiSearch();
			searchProvvedimenti.setPraticaId(idPratica.toString());
			List<LeggittimitaProvvedimentiDTO> listaProvvedimenti = leggittimitaProvvedimentiRepository.search(searchProvvedimenti).getList();
			
			if (listaProvvedimenti==null) {
				tipologiaDettaglioProvvedimenti = null;
			}
			else if (listaProvvedimenti.isEmpty()) {
				tipologiaDettaglioProvvedimenti = new ArrayList<>();
			}
			else {
				tipologiaDettaglioProvvedimenti = new ArrayList<>(listaProvvedimenti.size());
				for (LeggittimitaProvvedimentiDTO elem : listaProvvedimenti) {
					tipologiaDettaglioProvvedimenti.add(new TipologiaDettaglioDto(elem));
				}
			}
		} catch (Exception e) {
			logger.error("Errore in findByIdFascicolo",e);
			throw e;
		}
		return new LegittimitaDto(legittimitaDTO, tipologiaDettaglioTitoli, tipologiaDettaglioProvvedimenti);
	}

	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, readOnly=false, propagation=Propagation.MANDATORY, timeout=60000, rollbackFor= {Exception.class})
	public Integer delete(UUID idPratica) throws Exception
	{
		throw new Exception("Questo metodo non dovrebbe essere richiamato!");
		
		// cancello i dati presenti nella tabella 'leggittimita'
		// ....................
		
		// cancello i dati presenti nella tabella 'leggittimita_titoli'
		// leggittimitaTitoliRepository.deleteByPratica(idPratica);
		
		// cancello i dati presenti nella tabella 'leggittimita_provvedimenti'
		// leggittimitaProvvedimentiRepository.deleteByPratica(legittimita.getIdPratica());
		
		// return null (???);
	}
	
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, readOnly=true, propagation=Propagation.MANDATORY, timeout=60000, rollbackFor={Exception.class})
	public void valida(LegittimitaDto entity) throws Exception {
		this.valida(entity, true);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// VALIDAZIONE
	
	public void valida(LegittimitaDto legittimita, boolean completo) throws Exception {
		if (!StringUtils.isEmpty(legittimita.getTipoLegittimitaUrbanistica()) &&
			legittimita.getTipoLegittimitaUrbanistica()!=1 &&
			legittimita.getTipoLegittimitaUrbanistica()!=2) 
			throw new CustomOperationToAdviceException(msgSrc.getMessage("leggittimitaUrbanistica.valoreErrato", null, Locale.getDefault()));
			
		if (!StringUtils.isEmpty(legittimita.getTipoLegittimitaPaesaggistica()) &&
			legittimita.getTipoLegittimitaPaesaggistica()!=2 &&
			legittimita.getTipoLegittimitaPaesaggistica()!=1) 
			throw new CustomOperationToAdviceException("leggittimitaPaesaggistica.valoreErrato");
		
		if (!StringUtils.isEmpty(legittimita.getTipoLegittimitaUrbanistica()) &&
			 legittimita.getTipoLegittimitaUrbanistica()==1 &&
			 legittimita.getLegittimitaInfo()!=null && 
			 !legittimita.getLegittimitaInfo().isEmpty()) 
			throw new CustomOperationToAdviceException("Errore in Validazione Legittimità (3)");
		
		if (!StringUtils.isEmpty(legittimita.getTipoLegittimitaPaesaggistica()) &&
				 legittimita.getTipoLegittimitaPaesaggistica()==1 &&
				 legittimita.getAutorizzatoPaesaggisticamenteInfo()!=null && 
				 !legittimita.getAutorizzatoPaesaggisticamenteInfo().isEmpty()) throw new Exception("Errore in Validazione Legittimità (4)");
		
		if (!StringUtils.isEmpty(legittimita.getTipoLegittimitaUrbanistica()) &&
			 legittimita.getTipoLegittimitaUrbanistica()==2 &&
			!StringUtils.isEmpty(legittimita.getLegittimitaUrbanstica())) throw new Exception("Errore in Validazione Legittimità (5)");
		
		if (!StringUtils.isEmpty(legittimita.getTipoLegittimitaPaesaggistica()) &&
			 legittimita.getTipoLegittimitaPaesaggistica()==2 &&
			 legittimita.getDettaglioLegittimitaPaesaggistica()!=null &&
			(legittimita.getDettaglioLegittimitaPaesaggistica().getDataImposizioneVincolo()!=null ||
			 legittimita.getDettaglioLegittimitaPaesaggistica().getDataIntervento()!=null ||
			 !StringUtils.isEmpty(legittimita.getDettaglioLegittimitaPaesaggistica().getTipologiaVincolo()))) throw new Exception("Errore in Validazione Legittimità (6)");

		// elimino le righe tutte vuote
		List<TipologiaDettaglioDto> listaLegittimita = new ArrayList<TipologiaDettaglioDto>();
		if (legittimita.getLegittimitaInfo()!=null && !legittimita.getLegittimitaInfo().isEmpty()) {
			for (TipologiaDettaglioDto elemento : legittimita.getLegittimitaInfo()) {
				if (!(elemento.getDataRilascio()==null &&
					  StringUtils.isEmpty(elemento.getIntestinario ()) &&
					  StringUtils.isEmpty(elemento.getNoProtocollo ()) &&
					  StringUtils.isEmpty(elemento.getRialisciatoDa()) &&
					  StringUtils.isEmpty(elemento.getTipologia	 ())))
				listaLegittimita.add(elemento);
			}
			legittimita.setLegittimitaInfo(listaLegittimita);
		}
		List<TipologiaDettaglioDto> listaAutorizzatoPaesaggisticamente = new ArrayList<TipologiaDettaglioDto>();
		if (legittimita.getAutorizzatoPaesaggisticamenteInfo()!=null && !legittimita.getAutorizzatoPaesaggisticamenteInfo().isEmpty()) {
			for (TipologiaDettaglioDto elemento : legittimita.getAutorizzatoPaesaggisticamenteInfo()) {
				if (!(elemento.getDataRilascio()==null &&
					  StringUtils.isEmpty(elemento.getIntestinario ()) &&
					  StringUtils.isEmpty(elemento.getNoProtocollo ()) &&
					  StringUtils.isEmpty(elemento.getRialisciatoDa()) &&
					  StringUtils.isEmpty(elemento.getTipologia	 ())))
				listaAutorizzatoPaesaggisticamente.add(elemento);
			}
			legittimita.setAutorizzatoPaesaggisticamenteInfo(listaAutorizzatoPaesaggisticamente);
		}
		
		if (completo==true) {
			
			if (legittimita.getLegittimitaInfo()!=null && !legittimita.getLegittimitaInfo().isEmpty()) {
				for (TipologiaDettaglioDto elemento : legittimita.getLegittimitaInfo()) {
					if (elemento.getDataRilascio()==null ||
						StringUtils.isEmpty(elemento.getIntestinario ()) ||
						StringUtils.isEmpty(elemento.getNoProtocollo ()) ||
						StringUtils.isEmpty(elemento.getRialisciatoDa()) ||
						StringUtils.isEmpty(elemento.getTipologia	 ())
					)
					throw new Exception("Errore in Validazione Legittimità (7)");
				}
			}
			if (legittimita.getAutorizzatoPaesaggisticamenteInfo()!=null && !legittimita.getAutorizzatoPaesaggisticamenteInfo().isEmpty()) {
				for (TipologiaDettaglioDto elemento : legittimita.getAutorizzatoPaesaggisticamenteInfo()) {
					if (elemento.getDataRilascio()==null ||
						StringUtils.isEmpty(elemento.getIntestinario ()) ||
						StringUtils.isEmpty(elemento.getNoProtocollo ()) ||
						StringUtils.isEmpty(elemento.getRialisciatoDa()) ||
						StringUtils.isEmpty(elemento.getTipologia	 ())
					)
					throw new Exception("Errore in Validazione Legittimità (8)");
				}
			}
			
			if (StringUtils.isEmpty(legittimita.getTipoLegittimitaUrbanistica  ())) 
				throw new Exception("Errore in Validazione Legittimità (9)");
			
			if (StringUtils.isEmpty(legittimita.getTipoLegittimitaPaesaggistica())) 
				throw new Exception("Errore in Validazione Legittimità (10)");
			
			if (!StringUtils.isEmpty(legittimita.getTipoLegittimitaUrbanistica()) &&
				 legittimita.getTipoLegittimitaUrbanistica()==1 &&
				 StringUtils.isEmpty(legittimita.getLegittimitaUrbanstica())) 
				throw new Exception("Errore in Validazione Legittimità (11)");
			
			if (!StringUtils.isEmpty(legittimita.getTipoLegittimitaUrbanistica()) &&
				 legittimita.getTipoLegittimitaUrbanistica()==2 &&
				(legittimita.getLegittimitaInfo()==null || 
				 legittimita.getLegittimitaInfo().isEmpty())) throw new Exception("Errore in Validazione Legittimità (12)");
			if (!StringUtils.isEmpty(legittimita.getTipoLegittimitaUrbanistica()) &&
					 legittimita.getTipoLegittimitaUrbanistica()==2 &&
					!legittimita.getLegittimitaInfo().isEmpty()){
				//controllo che non ci siano duplicati in Dotato di titolo edilizio
				Set<String> digestRows=legittimita.getLegittimitaInfo().stream()
						.map(el->el.digest()).collect(Collectors.toSet());
				if(digestRows.size()<legittimita.getLegittimitaInfo().size()) {
					throw new CustomOperationToAdviceException(msgSrc.getMessage("leggittimitaUrbanistica.titoliDuplicati", null, Locale.getDefault()));
				}
			} 
			if (!StringUtils.isEmpty(legittimita.getTipoLegittimitaPaesaggistica()) &&
					 legittimita.getTipoLegittimitaPaesaggistica()==2 &&
					(legittimita.getAutorizzatoPaesaggisticamenteInfo()==null || legittimita.getAutorizzatoPaesaggisticamenteInfo().isEmpty())) 
				throw new Exception("Errore in Validazione Legittimità (13)");
			if (!StringUtils.isEmpty(legittimita.getTipoLegittimitaPaesaggistica()) &&
					 legittimita.getTipoLegittimitaPaesaggistica()==2 &&
					!legittimita.getAutorizzatoPaesaggisticamenteInfo().isEmpty()){
				//controllo che non ci siano duplicati in Autorizzazioni paesaggistiche in pannello leggittimita urbanistica
				Set<String> digestRows=legittimita.getAutorizzatoPaesaggisticamenteInfo().stream().map(el->	el.digest()
				).collect(Collectors.toSet());
				if(digestRows.size()<legittimita.getAutorizzatoPaesaggisticamenteInfo().size()) {
					throw new CustomOperationToAdviceException(msgSrc.getMessage("leggittimitaPaesaggistica.autorizzazioniDuplicate", null, Locale.getDefault()));
				}
			}
			
			
			if (!StringUtils.isEmpty(legittimita.getTipoLegittimitaPaesaggistica()) &&
				 legittimita.getTipoLegittimitaPaesaggistica()==1 &&
				(legittimita.getDettaglioLegittimitaPaesaggistica()==null ||
				 legittimita.getDettaglioLegittimitaPaesaggistica().getDataImposizioneVincolo()==null || 
				 legittimita.getDettaglioLegittimitaPaesaggistica().getDataIntervento()==null || 
				 StringUtils.isEmpty(legittimita.getDettaglioLegittimitaPaesaggistica().getTipologiaVincolo()))) throw new Exception("Errore in Validazione Legittimità (14)");
		}
	}
	
}