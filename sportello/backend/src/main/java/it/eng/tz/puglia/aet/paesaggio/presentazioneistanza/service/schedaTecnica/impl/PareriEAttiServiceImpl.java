package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.impl;

import java.util.ArrayList;
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
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.PareriAttiDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.TipologiaDettaglioDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PareriEAttiAssensoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PareriEAttiAssensoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PareriEAttiAssensoSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.PareriEAttiService;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class PareriEAttiServiceImpl implements PareriEAttiService
{
	@Autowired
	MessageSource msgSrc;
	
	@Autowired
	private PareriEAttiAssensoRepository repository;
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, readOnly=false, propagation=Propagation.MANDATORY, timeout=60000, rollbackFor= {Exception.class})
	public PareriAttiDto saveOrUpdate(PareriAttiDto dto) throws Exception
	{
		try {
			valida(dto, false);
			
			// cancello i dati presenti nella tabella
			repository.deleteByPratica(dto.getIdPratica());

			// inserisco i nuovi elementi nella tabella
			int index = 1;
			if (dto.getParreriInfo()!=null && !dto.getParreriInfo().isEmpty()) {
				for (int i = 0; i < dto.getParreriInfo().size(); i++) {
					repository.insert(new PareriEAttiAssensoDTO(dto.getParreriInfo().get(i), "P", dto.getIdPratica(), index));
					index++;
				}
			}
			if (dto.getAttiInfo()!=null && !dto.getAttiInfo().isEmpty()) {
				for (int i = 0; i < dto.getAttiInfo().size(); i++) {
					repository.insert(new PareriEAttiAssensoDTO(dto.getAttiInfo()   .get(i), "A", dto.getIdPratica(), index));
					index++;
				}
			}
		} catch (Exception e) {
			logger.error("Error in saveOrUpdate",e);
			throw e;
		}
		return dto;
	}

	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, readOnly=true, propagation=Propagation.MANDATORY, timeout=60000, rollbackFor= {Exception.class})
	public PareriAttiDto findByIdFascicolo(UUID idPratica) throws Exception
	{
		List<TipologiaDettaglioDto> listaPareri = null;
		List<TipologiaDettaglioDto> listaAtti   = null;
		
		try {
			PareriEAttiAssensoSearch searchPareri = new PareriEAttiAssensoSearch();
			searchPareri.setPraticaId(idPratica.toString());
			searchPareri.setFlagAttoParere("p");
			List<PareriEAttiAssensoDTO> lista_PARERI = repository.search(searchPareri).getList();
			
			if (lista_PARERI==null) {
				listaPareri = null;
			}
			else if (lista_PARERI.isEmpty()) {
				listaPareri = new ArrayList<>();
			}
			else {
				listaPareri = new ArrayList<>(lista_PARERI.size());
				for (PareriEAttiAssensoDTO elem : lista_PARERI) {
					listaPareri.add(new TipologiaDettaglioDto(elem));
				}
			}
			
			PareriEAttiAssensoSearch searchAtti = new PareriEAttiAssensoSearch();
			searchAtti.setPraticaId(idPratica.toString());
			searchAtti.setFlagAttoParere("a");
			List<PareriEAttiAssensoDTO> lista_ATTI = repository.search(searchAtti).getList();
			
			if (lista_ATTI==null) {
				listaAtti = null;
			}
			else if (lista_ATTI.isEmpty()) {
				listaAtti = new ArrayList<>();
			}
			else {
				listaAtti = new ArrayList<>(lista_ATTI.size());
				for (PareriEAttiAssensoDTO elem : lista_ATTI) {
					listaAtti.add(new TipologiaDettaglioDto(elem));
				}
			}
		} catch (Exception e) {
			logger.error("Error in findByIdFascicolo",e);
			throw e;
		}
		return new PareriAttiDto(listaPareri, listaAtti);
	}

	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, readOnly=false, propagation=Propagation.MANDATORY, timeout=60000, rollbackFor= {Exception.class})
	public Integer delete(UUID idPratica) throws Exception
	{
		throw new Exception("Questo metodo non dovrebbe essere richiamato!");
		// return repository.deleteByPratica(idPratica);
	}

	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, readOnly=true, propagation=Propagation.MANDATORY, timeout=60000, rollbackFor={Exception.class})
	public void valida(PareriAttiDto entity) throws Exception {
		this.valida(entity, true);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// VALIDAZIONE
	
	private void valida(PareriAttiDto pareriAtti, boolean completo) throws Exception {
		
		// elimino le righe tutte vuote
		List<TipologiaDettaglioDto> listaPareri = new ArrayList<TipologiaDettaglioDto>();
		if (pareriAtti.getParreriInfo()!=null && !pareriAtti.getParreriInfo().isEmpty()) {
			for (TipologiaDettaglioDto elemento : pareriAtti.getParreriInfo()) {
				if (!(elemento.getDataRilascio()==null &&
					  StringUtils.isEmpty(elemento.getIntestinario ()) &&
					  StringUtils.isEmpty(elemento.getNoProtocollo ()) &&
					  StringUtils.isEmpty(elemento.getRialisciatoDa()) &&
					  StringUtils.isEmpty(elemento.getTipologia	 ())))
				listaPareri.add(elemento);
			}
			pareriAtti.setParreriInfo(listaPareri);
		}
		List<TipologiaDettaglioDto> listaAtti = new ArrayList<TipologiaDettaglioDto>();
		if (pareriAtti.getAttiInfo()!=null && !pareriAtti.getAttiInfo().isEmpty()) {
			for (TipologiaDettaglioDto elemento : pareriAtti.getAttiInfo()) {
				if (!(elemento.getDataRilascio()==null &&
					  StringUtils.isEmpty(elemento.getIntestinario ()) &&
					  StringUtils.isEmpty(elemento.getNoProtocollo ()) &&
					  StringUtils.isEmpty(elemento.getRialisciatoDa()) &&
					  StringUtils.isEmpty(elemento.getTipologia	 ())))
				listaAtti.add(elemento);
			}
			pareriAtti.setAttiInfo(listaAtti);
		}
		
		if (completo==true) {
			if (pareriAtti.getParreriInfo()!=null && !pareriAtti.getParreriInfo().isEmpty()) {
				for (TipologiaDettaglioDto elemento : pareriAtti.getParreriInfo()) {
					if (elemento.getDataRilascio()==null ||
						StringUtils.isEmpty(elemento.getIntestinario ()) ||
						StringUtils.isEmpty(elemento.getNoProtocollo ()) ||
						StringUtils.isEmpty(elemento.getRialisciatoDa()) ||
						StringUtils.isEmpty(elemento.getTipologia	 ())
					)
					throw new Exception("Errore in Validazione Pareri e Atti (1)");
				}
			}
			
			if (pareriAtti.getAttiInfo()!=null && !pareriAtti.getAttiInfo().isEmpty()) {
				for (TipologiaDettaglioDto elemento : pareriAtti.getAttiInfo()) {
					if (elemento.getDataRilascio()==null ||
						StringUtils.isEmpty(elemento.getIntestinario ()) ||
						StringUtils.isEmpty(elemento.getNoProtocollo ()) ||
						StringUtils.isEmpty(elemento.getRialisciatoDa()) ||
						StringUtils.isEmpty(elemento.getTipologia	 ())
					)
					throw new Exception("Errore in Validazione Pareri e Atti (2)");
				}
			}
			if(ListUtil.isNotEmpty(pareriAtti.getParreriInfo())) {
				Set<String> digests = pareriAtti.getParreriInfo().stream().map(el->
					el.digest()
				).collect(Collectors.toSet());
				if(digests.size()<pareriAtti.getParreriInfo().size()) {
					throw new CustomOperationToAdviceException(
							msgSrc.getMessage("pareriEatti.pareriDuplicati", null, Locale.getDefault()));
				} 
			}
			if(ListUtil.isNotEmpty(pareriAtti.getAttiInfo())) {
				Set<String> digests = pareriAtti.getAttiInfo().stream().map(el->
					el.digest()
				).collect(Collectors.toSet());
				if(digests.size()<pareriAtti.getAttiInfo().size()) {
					throw new CustomOperationToAdviceException(
							msgSrc.getMessage("pareriEatti.attiDuplicati", null, Locale.getDefault()));
				} 
			}
		}
	}
	
}