package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.PptrDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.SchedaTecnicaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PptrSelezioniDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipiEQualificazioniDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.UlterioriContestiPaesaggisticiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.EffettiMitigazioneRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.InquadramentoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TipiEQualificazioniRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.UlterioriContestiPaesaggisticiRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.UlterioriContestiPaesaggisticiSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.SchedaTecnicaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.DescrizioneService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.DestinazioneUrbanisticaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.DichiarazioneService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.EffettiMitigazioneService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.InquadramentoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.LegittimitaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.PareriEAttiService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.PptrService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.ProcedimentoContenziosoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.ProcedureEdilizieService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.VincolisticaService;

@Service
public class SchedaTecnicaServiceImpl implements SchedaTecnicaService
{
	//repository
	@Autowired private PraticaRepository praticaRepo;
	@Autowired private TipiEQualificazioniRepository tqRepo;
	@Autowired private UlterioriContestiPaesaggisticiRepository ucpRepo;
	@Autowired private InquadramentoRepository inquadramentoRepo;
	@Autowired private EffettiMitigazioneRepository effettiMitigazioneRepo;
	
	//service che compongono scheda tecnica
	@Autowired DescrizioneService descrizioneService;
	@Autowired DestinazioneUrbanisticaService destinazioneService;
	@Autowired LegittimitaService legittimitaService;
	@Autowired ProcedureEdilizieService procedureEdilizieService;
	@Autowired InquadramentoService inquadramentoService;
	@Autowired EffettiMitigazioneService effettiMitigazioneService;
	@Autowired ProcedimentoContenziosoService procedimentoContenziosoService;
	@Autowired PptrService pptrService;
	@Autowired PareriEAttiService pareriService;
	@Autowired VincolisticaService vincolisticaService;
	@Autowired DichiarazioneService dichiarazioneService;
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, readOnly=true, timeout=60000)
	public List<TipiEQualificazioniDTO> findTipiEQualificazioni(Integer idTipoProcedimento) throws Exception
	{
		List<TipiEQualificazioniDTO> tipiEQualificazioni;
		tipiEQualificazioni = tqRepo.search(idTipoProcedimento);
		return tipiEQualificazioni;
	}

	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, readOnly=true, timeout=60000)
	public List<UlterioriContestiPaesaggisticiDTO> findUlterioreContestiPaesaggistici(
			UlterioriContestiPaesaggisticiSearch filters) throws Exception
	{
		return ucpRepo.searchForList(filters);
	}

	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, readOnly=false, timeout=60000)
	public SchedaTecnicaDto saveOrUpdate(SchedaTecnicaDto schedaTecnica) throws Exception
	{
		settaIdPratica(schedaTecnica);
		schedaTecnica.setDescrizione(descrizioneService.saveOrUpdate(schedaTecnica.getDescrizione()));
		schedaTecnica.setDestinazione(destinazioneService.saveOrUpdate(schedaTecnica.getDestinazione(), schedaTecnica.getIdPratica()));
		schedaTecnica.setLegittimita(legittimitaService.saveOrUpdate(schedaTecnica.getLegittimita()));
		schedaTecnica.setProcedureEdilizie(procedureEdilizieService.saveOrUpdate(schedaTecnica.getProcedureEdilizie()));
		if (schedaTecnica.getInquadramento()	!=null) schedaTecnica.setInquadramento(inquadramentoService.saveOrUpdate(schedaTecnica.getInquadramento()));
		if (schedaTecnica.getEffetiMitigazione()!=null)	schedaTecnica.setEffetiMitigazione(effettiMitigazioneService.saveOrUpdate(schedaTecnica.getEffetiMitigazione()));
		schedaTecnica.setEventualiProcedimenti(procedimentoContenziosoService.saveOrUpdate(schedaTecnica.getEventualiProcedimenti()));
		schedaTecnica.setPptr(pptrService.saveOrUpdate(schedaTecnica.getPptr()));
		schedaTecnica.setParreriEAtti(pareriService.saveOrUpdate(schedaTecnica.getParreriEAtti()));
		schedaTecnica.setVincolistica(vincolisticaService.saveOrUpdate(schedaTecnica.getVincolistica()));
		schedaTecnica.setDichiarazione(dichiarazioneService.saveOrUpdate(schedaTecnica.getDichiarazione()));
		return schedaTecnica;
	}

	private void settaIdPratica(SchedaTecnicaDto schedaTecnica) 
	{
		schedaTecnica.getDescrizione().setIdPratica(schedaTecnica.getIdPratica());
		if (schedaTecnica.getDestinazione()!=null) {
			schedaTecnica.getDestinazione().forEach(elem->{
				elem.setIdPratica(schedaTecnica.getIdPratica());
			});
		}
		schedaTecnica.getLegittimita().setIdPratica(schedaTecnica.getIdPratica());
		schedaTecnica.getProcedureEdilizie().setIdPratica(schedaTecnica.getIdPratica());
		if (schedaTecnica.getInquadramento()	!=null) schedaTecnica.getInquadramento().setIdPratica(schedaTecnica.getIdPratica());
		if (schedaTecnica.getEffetiMitigazione()!=null) schedaTecnica.getEffetiMitigazione().setIdPratica(schedaTecnica.getIdPratica());
		schedaTecnica.getEventualiProcedimenti().setIdPratica(schedaTecnica.getIdPratica());
		setIdPptr(schedaTecnica.getPptr(), schedaTecnica.getIdPratica());
		schedaTecnica.getParreriEAtti().setIdPratica(schedaTecnica.getIdPratica());
		schedaTecnica.getVincolistica().setIdPratica(schedaTecnica.getIdPratica());
		schedaTecnica.getDichiarazione().setIdPratica(schedaTecnica.getIdPratica());
	}

	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, readOnly=true, timeout=60000)
	public SchedaTecnicaDto find(UUID idPratica) throws Exception
	{
		SchedaTecnicaDto schedaTecnica = new SchedaTecnicaDto();
		schedaTecnica.setDescrizione(descrizioneService.findByIdFascicolo(idPratica));
		schedaTecnica.setDestinazione(destinazioneService.findByIdFascicolo(idPratica));
		schedaTecnica.setLegittimita(legittimitaService.findByIdFascicolo(idPratica));
		schedaTecnica.setProcedureEdilizie(procedureEdilizieService.findByIdFascicolo(idPratica));
		schedaTecnica.setInquadramento(inquadramentoService.findByIdFascicolo(idPratica));
		schedaTecnica.setEffetiMitigazione(effettiMitigazioneService.findByIdFascicolo(idPratica));
		schedaTecnica.setEventualiProcedimenti(procedimentoContenziosoService.findByIdFascicolo(idPratica));
		schedaTecnica.setPptr(pptrService.findByIdFascicolo(idPratica));
		schedaTecnica.setParreriEAtti(pareriService.findByIdFascicolo(idPratica));
		schedaTecnica.setVincolistica(vincolisticaService.findByIdFascicolo(idPratica));
		schedaTecnica.setDichiarazione(dichiarazioneService.findByIdFascicolo(idPratica));
		return schedaTecnica;
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, readOnly=true, timeout=60000)
	public boolean valida(UUID idPratica) throws Exception
	{
		try {
			SchedaTecnicaDto schedaTecnica = this.find(idPratica);
			schedaTecnica.setIdPratica(idPratica);
			settaIdPratica(schedaTecnica);
			
			int tipoProcedimento = praticaRepo.find(idPratica).getTipoProcedimento();
					
			descrizioneService.valida(schedaTecnica.getDescrizione());
			destinazioneService.valida(schedaTecnica.getDestinazione(), schedaTecnica.getIdPratica());
			legittimitaService.valida(schedaTecnica.getLegittimita());
			procedureEdilizieService.valida(schedaTecnica.getProcedureEdilizie());
			if (inquadramentoRepo.countByIdPratica(idPratica)==0 && tipoProcedimento==2) {
				throw new CustomOperationToAdviceException("Errore. Tab Inquadramento mancante!"); }
			else if (tipoProcedimento==2) {
				inquadramentoService.valida(schedaTecnica.getInquadramento()); }
			if (effettiMitigazioneRepo.countByIdPratica(idPratica)==0 && tipoProcedimento==2) {
				throw new CustomOperationToAdviceException("Errore. Tab Effetti Mitigazione mancante!"); }
			else if (tipoProcedimento==2) {
				effettiMitigazioneService.valida(schedaTecnica.getEffetiMitigazione()); }
			procedimentoContenziosoService.valida(schedaTecnica.getEventualiProcedimenti());
			pptrService.valida(schedaTecnica.getPptr());
			pareriService.valida(schedaTecnica.getParreriEAtti());
			vincolisticaService.valida(schedaTecnica.getVincolistica());
			dichiarazioneService.valida(schedaTecnica.getDichiarazione());
		} catch (Exception e) {
			throw e;
		}
		return true;
	}
	
	private void setIdPptr(PptrDto pptr, UUID idPratica)
	{
		if(pptr != null)
		{
			pptr.setIdPratica(idPratica);
			if(pptr.getUlteririContestiPaesaggistici() != null && pptr.getUlteririContestiPaesaggistici().size() > 0)
			{
				for(PptrSelezioniDTO s: pptr.getUlteririContestiPaesaggistici())
					s.setIdPratica(idPratica);
			}
		}
	}
	
}