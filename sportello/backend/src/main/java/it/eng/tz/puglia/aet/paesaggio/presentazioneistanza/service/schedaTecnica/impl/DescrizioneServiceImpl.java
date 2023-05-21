package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.ValidationFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.CaratterizzazioneInterventoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.ConfigOptionValue;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.SchedaTecnicaDescrizioneDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.TipoInterventoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoQualificazione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoStileQual;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DescrizioneSchedaTecnicaValuesDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipiEQualificazioniDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoInterventoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.DescrizioneSchedaTecnicaValuesRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TipiEQualificazioniRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TipoInterventoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.DescrizioneService;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class DescrizioneServiceImpl implements DescrizioneService
{
	private final Logger logger = LoggerFactory.getLogger(DescrizioneServiceImpl.class);
	
	@Autowired private TipoInterventoRepository tiRepo;
	@Autowired private DescrizioneSchedaTecnicaValuesRepository dstRepo;
	@Autowired private TipiEQualificazioniRepository teqRepo;
	@Autowired private PraticaRepository praticaRepo;
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, readOnly=false, propagation=Propagation.MANDATORY, timeout=60000, rollbackFor= {Exception.class})
	public int deleteValueNonAmmessi(UUID praticaId,int tipoProcedimento) {
		return dstRepo.deleteValueNonAmmessi(praticaId, tipoProcedimento);
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, readOnly=false, propagation=Propagation.MANDATORY, timeout=60000, rollbackFor= {Exception.class})
	public SchedaTecnicaDescrizioneDto saveOrUpdate(SchedaTecnicaDescrizioneDto entity) throws Exception
	{
		logger.info("Sono in saveOrUpdate di DescrizioneServiceImpl");
		TipoInterventoDTO dtoTI = new TipoInterventoDTO(entity.getTipoIntervento(), entity.getIdPratica());
		List<DescrizioneSchedaTecnicaValuesDTO> dtoValues = fromDtoToEntityList(entity);
		delete(dtoTI.getIdPratica());
		long elementsTI = tiRepo.insert(dtoTI);
		long elementsDST = dstRepo.insert(dtoValues);
		if(elementsTI != 1 || elementsDST != dtoValues.size())
		{
			logger.error(" tipo intervento [atteso 1, salvati {}]; "
					+ "DescrizioneSchedaTecnicaValues [atteso {}, salvati {}]", 
					elementsTI, dtoValues.size(), elementsDST);
			throw new Exception("Errore nel salvataggio di descrizione scheda tecnica");
		}
		return entity;
	}

	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, readOnly=true, propagation=Propagation.MANDATORY, timeout=60000, rollbackFor= {Exception.class})
	public SchedaTecnicaDescrizioneDto findByIdFascicolo(UUID idFascicolo) throws Exception
	{
		logger.info("Sono in findByIdFascicolo di DescrizioneServiceImpl: cerco descrizone scheda tecnica con id fascicolo {}", idFascicolo);
		SchedaTecnicaDescrizioneDto dto = new SchedaTecnicaDescrizioneDto();
		TipoInterventoDTO temp = tiRepo.find(idFascicolo);
		dto.setTipoIntervento(temp != null ? new TipoInterventoDto(temp) : null);
		List<DescrizioneSchedaTecnicaValuesDTO> values = dstRepo.find(idFascicolo);
		dto.setIdPratica(idFascicolo);
		if(values != null && !values.isEmpty())
		{
			dto.setDescrizione(values.parallelStream().filter(p -> p.getSezione().equals(TipoQualificazione.DESCR)).findFirst().orElse(new DescrizioneSchedaTecnicaValuesDTO()).getText());
			dto.setCaratterizzazioneIntervento(new CaratterizzazioneInterventoDto(values));
			dto.setQualificazioneIntervento(fromEntityToDtoPart(values, TipoQualificazione.QUAL));
		}
		return dto;
	}

	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, readOnly=false, propagation=Propagation.MANDATORY, timeout=60000, rollbackFor= {Exception.class})
	public Integer delete(UUID idFascicolo) throws Exception
	{
		long deleted = tiRepo.delete(idFascicolo);
		deleted += dstRepo.delete(idFascicolo);
		return (int)deleted;
	}

	/**
	 * Metodo che trasforma il dto in arrivo dal FE in un dto adatto ad essere salvato sul database
	 * @param dto {@link SchedaTecnicaDescrizioneDto} che rappresenta un dto in arrivo dal FE
	 * @return {@link List} di {@link DescrizioneSchedaTecnicaValuesDTO}
	 */
	private List<DescrizioneSchedaTecnicaValuesDTO> fromDtoToEntityList(SchedaTecnicaDescrizioneDto dto)
	{
		List<DescrizioneSchedaTecnicaValuesDTO> values = new ArrayList<DescrizioneSchedaTecnicaValuesDTO>();
		values.add(new DescrizioneSchedaTecnicaValuesDTO(dto.getIdPratica(), TipoQualificazione.DESCR.toString(), dto.getDescrizione(), TipoQualificazione.DESCR));
		if(dto.getCaratterizzazioneIntervento() != null)
		{
			CaratterizzazioneInterventoDto car = dto.getCaratterizzazioneIntervento();
			if(car.getCaratterizzazione() != null && 
				!car.getCaratterizzazione().isEmpty())
			{
				for(ConfigOptionValue e: car.getCaratterizzazione())
					values.add(new DescrizioneSchedaTecnicaValuesDTO(dto.getIdPratica(), e.getValue(), e.getText(), TipoQualificazione.CAR_INT));
			}
			if(car.getDurata() != null)
				values.add(new DescrizioneSchedaTecnicaValuesDTO(dto.getIdPratica(), car.getDurata(), TipoQualificazione.CAR_INT_TP));
		}
		if(dto.getQualificazioneIntervento() != null)
		{
			List<ConfigOptionValue> qual = dto.getQualificazioneIntervento();
			if(qual!= null && !qual.isEmpty())
			{
				for(ConfigOptionValue e: qual)
					values.add(new DescrizioneSchedaTecnicaValuesDTO(dto.getIdPratica(), e.getValue(), e.getText(), TipoQualificazione.QUAL));
			}
		}
		return values;
	}
	
	/**
	 * Metodo che trasforma il set di elementi ottenuti dal db in una lista di ConfigOptionValue 
	 * @param entity {link List} di {@link DescrizioneSchedaTecnicaValuesDTO}
	 * @param tipoQualificazione {@link TipoQualificazione}
	 * @return
	 */
	private List<ConfigOptionValue> fromEntityToDtoPart(List<DescrizioneSchedaTecnicaValuesDTO> entity, TipoQualificazione tipoQualificazione)
	{
		List<ConfigOptionValue> configValue = new ArrayList<ConfigOptionValue>();
		if(entity != null && !entity.isEmpty())
		{
			for(DescrizioneSchedaTecnicaValuesDTO e: entity)
			{
				if(e.getSezione().equals(tipoQualificazione))
					configValue.add(new ConfigOptionValue(e.getCodice(), e.getText()));
			}
		}
		return configValue;
	}

	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, readOnly=true, propagation=Propagation.MANDATORY, timeout=60000, rollbackFor={Exception.class})
	public void valida(SchedaTecnicaDescrizioneDto entity) throws Exception 
	{
		UUID idPratica = entity.getIdPratica();
		Integer tipoProcedimento = praticaRepo.find(idPratica).getTipoProcedimento();
		List<TipoQualificazione> tipiqual = Arrays.asList(TipoQualificazione.QUAL, TipoQualificazione.DLGS_42_2004, TipoQualificazione.DPCM_12_2005, TipoQualificazione.DPR_31_2017);
		
		List<TipiEQualificazioniDTO> teq = teqRepo.search(tipoProcedimento);
		List<TipiEQualificazioniDTO> teqWithText = teq.parallelStream().filter(p -> p.getHastext() && p.getRaggruppamento().equals(TipoQualificazione.CAR_INT)).collect(Collectors.toList());
		List<TipiEQualificazioniDTO> temp = teq.parallelStream().filter(p -> p.getDependency() == null && tipiqual.contains(p.getRaggruppamento())).collect(Collectors.toList());
		
		String tipoIntervento = entity.getTipoIntervento() != null ? entity.getTipoIntervento().getTipologiaDiIntervento() : null;
		List<ConfigOptionValue> caratterizzazione = entity.getCaratterizzazioneIntervento() != null ? entity.getCaratterizzazioneIntervento().getCaratterizzazione() : null;
		String durata = entity.getCaratterizzazioneIntervento() != null ? entity.getCaratterizzazioneIntervento().getDurata() : null;
		List<ConfigOptionValue> qualificazioni = entity.getQualificazioneIntervento();

		
		if(StringUtil.isEmpty(tipoIntervento))
			throw new ValidationFailureException("Validazione descrizione fallita: deve essere selezionata una tipologia di intervento");
		if(caratterizzazione == null || caratterizzazione.isEmpty())
			throw new ValidationFailureException("Validazione descrizione fallita: deve essere selezionata almeno un elemento dalla sezione 'caratterizzazione'");
		if(caratterizzazione != null)
		{
			for(TipiEQualificazioniDTO t: teqWithText)
			{
				ConfigOptionValue v = caratterizzazione.parallelStream().filter(p -> p.getValue().equals(t.getCodice())).findFirst().orElse(null);
				if(v != null && StringUtil.isEmpty(v.getText()))
					throw new ValidationFailureException("Validazione descrizione fallita: è presente almeno un campo che richiede una descrizione testuale che risulta però assente");
			}
		}
		if(StringUtil.isEmpty(durata))
			throw new ValidationFailureException("Validazione descrizione fallita: è necessario specificare se l'intervento è temporaneo o permanente");
		if(qualificazioni == null || qualificazioni.isEmpty())
			throw new ValidationFailureException("Validazione descrizione fallita: deve essere selezionata almeno un elemento dalla sezione 'qualificazioni'");
		
		int qualificazioniVerificate = 0;
		while(temp != null && !temp.isEmpty())
		{
			boolean isRadio = temp.get(0).getStile().equals(TipoStileQual.radiobutton);
			List<TipiEQualificazioniDTO> scelte = temp.parallelStream().filter(p1 -> qualificazioni.stream().anyMatch(p2 -> p1.getCodice().equals(p2.getValue()))).collect(Collectors.toList());
			Integer key = scelte.parallelStream().filter(p -> p.getKey() != null).findFirst().orElse(new TipiEQualificazioniDTO()).getKey();
			if(scelte == null || scelte.isEmpty())
				throw new ValidationFailureException("Validazione descrizione fallita: sezione 'qualificazioni' incompleta");
			if(isRadio && scelte.size() > 1)
				throw new ValidationFailureException("Validazione descrizione fallita: sono state selezionate più scelte di quanto richiesto");			
			if(key != null)
				temp = findChild(teq, key);
			else
				temp = null;
			qualificazioniVerificate+= scelte.size();
		}
		
		if(qualificazioniVerificate != qualificazioni.size())
			throw new ValidationFailureException("Validazione descrizione fallita: sono presenti più elementi di quanti ne erano attesi");
	}
	
	private List<TipiEQualificazioniDTO> findChild(List<TipiEQualificazioniDTO> set, Integer key)
	{
		List<TipiEQualificazioniDTO> children = null;
		if(set != null && !set.isEmpty())
			children = set.parallelStream().filter(p -> p.getDependency() != null && p.getDependency().equals(key)).collect(Collectors.toList());
		return children;
	}
	
}