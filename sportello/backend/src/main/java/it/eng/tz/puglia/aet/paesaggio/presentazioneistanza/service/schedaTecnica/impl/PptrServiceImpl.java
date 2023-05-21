package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.ValidationFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.PptrDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PptrApprovatoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PptrSelezioniDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.UlterioriContestiPaesaggisticiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PptrApprovatoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PptrSelezioniRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.UlterioriContestiPaesaggisticiRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.UlterioriContestiPaesaggisticiSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.PptrService;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class PptrServiceImpl implements PptrService
{
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(PptrServiceImpl.class);
	
	@Autowired PraticaRepository praticaRepo;
	@Autowired UlterioriContestiPaesaggisticiRepository ucpRepo;
	@Autowired PptrApprovatoRepository approvatoRepo;
	@Autowired PptrSelezioniRepository selezioniRepo;
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, readOnly=false, propagation=Propagation.MANDATORY, timeout=60000, rollbackFor= {Exception.class})
	public PptrDto saveOrUpdate(PptrDto entity) throws Exception
	{
		PptrApprovatoDTO pptr1 = new PptrApprovatoDTO(entity);
		approvatoRepo.delete(pptr1);
		long i1 = approvatoRepo.insert(pptr1);
		selezioniRepo.delete(entity.getIdPratica());
		long i2 = selezioniRepo.insert(entity.getUlteririContestiPaesaggistici());
		
		if(i1 != 1 || i2 != entity.getUlteririContestiPaesaggistici().size())
		{
			logger.error("Errore! Inseriti {} record su 1 in pptr_approvato Inseriti {} su {} in pptr_selezioni", i1, i2, entity.getUlteririContestiPaesaggistici().size());
			throw new Exception("Errore durante l'inserimento dei record nele tabella di pptr");
		}
		return entity;
	}

	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, readOnly=true, propagation=Propagation.MANDATORY, timeout=60000, rollbackFor= {Exception.class})
	public PptrDto findByIdFascicolo(UUID idFascicolo) throws Exception
	{
		PptrDto pptr = null;
		PptrApprovatoDTO app;
		PptrApprovatoDTO pk = new PptrApprovatoDTO();
		pk.setPraticaId(idFascicolo);
		app = approvatoRepo.find(pk);
		if(app != null)
			pptr = new PptrDto(app);
		else
			pptr = new PptrDto();
		
		pptr.setUlteririContestiPaesaggistici(selezioniRepo.searchByIdPratica(idFascicolo));
		return pptr;
	}

	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, readOnly=false, propagation=Propagation.MANDATORY, timeout=60000, rollbackFor= {Exception.class})
	public Integer delete(UUID idFascicolo) throws Exception
	{
		PptrApprovatoDTO pk = new PptrApprovatoDTO();
		pk.setPraticaId(idFascicolo);
		int cancellati = approvatoRepo.delete(pk);
		cancellati += selezioniRepo.delete(idFascicolo);
		return cancellati;
	}

	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, readOnly=true, propagation=Propagation.MANDATORY, timeout=60000, rollbackFor={Exception.class})
	public void valida(PptrDto entity) throws Exception 
	{
		if(StringUtil.isEmpty(entity.getAmbitoPaesaggistico()))
			throw new ValidationFailureException("Validazione fallita pptr: campo ambito paesaggistico assente");
		if(StringUtil.isEmpty(entity.getFigura()))
			throw new ValidationFailureException("Validazione fallita pptr: campo figura assente");
		
		if(entity.getUlteririContestiPaesaggistici() != null && !entity.getUlteririContestiPaesaggistici().isEmpty())
		{
			UUID idPratica = entity.getIdPratica();
			UlterioriContestiPaesaggisticiSearch filters = new UlterioriContestiPaesaggisticiSearch();
			filters.setIdTipoProcedimento(praticaRepo.find(idPratica).getTipoProcedimento());
			List<UlterioriContestiPaesaggisticiDTO> ucps = ucpRepo.searchForList(filters);
			
			if(ucps != null && !ucps.isEmpty())
			{
				for(PptrSelezioniDTO selezione: entity.getUlteririContestiPaesaggistici())
				{
					if(ucps.parallelStream()
						   .filter(p -> p.getCodice().equals(selezione.getCodice()))
						   .findFirst()
						   .orElse(new UlterioriContestiPaesaggisticiDTO()).getHastext() && 
						StringUtil.isEmpty(selezione.getSpecifica()))
					{
						throw new ValidationFailureException("Validazione fallita pptr: una delle selezioni scelte presenta una specifica obbligatoria non compilata");
					}
				}
			}
		}
	}

	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, readOnly=false, propagation=Propagation.MANDATORY, timeout=60000, rollbackFor= {Exception.class})
	public Integer deleteNonAmmessi(UUID idPratica,int tipoProcedimento) throws Exception
	{
		return selezioniRepo.deleteNonAmmessi(idPratica, tipoProcedimento);
		
	}
}