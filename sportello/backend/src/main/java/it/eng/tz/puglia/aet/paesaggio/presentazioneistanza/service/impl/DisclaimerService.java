package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoReferente;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DisclaimerDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.DisclaimerRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.IDisclaimerService;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.util.date.DateUtil;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class DisclaimerService implements IDisclaimerService{
	

	@Autowired
	private DisclaimerRepository disclDao;
	

	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, propagation=Propagation.REQUIRED, readOnly=false, timeout=60000, rollbackFor=Exception.class)
	public List<DisclaimerDTO> updateDisclaimers(int tipoProcedimento, List<DisclaimerDTO> disclaimers) throws CustomOperationToAdviceException {
		Timestamp currentTimestamp = DateUtil.currentTimestamp();
		Timestamp nextTime =new Timestamp(currentTimestamp.getTime()+1);
		disclDao.updateEndDate(tipoProcedimento,SecurityUtil.getUsername(),currentTimestamp);
		for(DisclaimerDTO disclaimer:disclaimers) {
			disclaimer.setTipoProcedimento(tipoProcedimento);
			disclaimer.setUserInserted(SecurityUtil.getUsername());
			disclaimer.setDataInizio(nextTime);
			disclDao.insert(disclaimer);
		}
		List<DisclaimerDTO> ret = disclDao.select(null, tipoProcedimento,null,true);
		return ret;
	}

	
	@Override
	public String validate(DisclaimerDTO disclaimer) {
		StringBuilder sb=new StringBuilder();
		if(StringUtil.isEmpty(disclaimer.getTesto())){
			sb.append("Testo mancante <br>");
		}
		if(disclaimer.getOrdine()==null){
			sb.append("Ordine mancante <br>");
		}
		if(StringUtil.isEmpty(disclaimer.getTipoReferente()) || 
				!TipoReferente.tipiReferenteDisclaimer().contains(disclaimer.getTipoReferente())){
			sb
			.append("Tipo referente errato, attesi ")
			.append(TipoReferente.tipiReferenteDisclaimer())
			.append("<br>");
		}
		return sb.toString();
	}
	
	@Override
	public String validate(List<DisclaimerDTO> disclaimerList) {
		StringBuilder sbList=new StringBuilder();
		StringBuilder sb=new StringBuilder();
		for(DisclaimerDTO disclaimer:disclaimerList) {
			sb.setLength(0);
			sb.append(this.validate(disclaimer));
			if(it.eng.tz.puglia.util.string.StringUtil.isNotEmpty(sb.toString())) {
				sbList.append("Disclaimer non valido all'indice ")
				.append(disclaimerList.indexOf(disclaimer))
				.append("<br>");
			}
		}
		return sbList.toString();
	}


	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, propagation=Propagation.REQUIRED, readOnly=true, timeout=60000, rollbackFor=Exception.class)
	public List<DisclaimerDTO> currentDisclaimers(int tipoProcedimento) {
		List<DisclaimerDTO> ret = disclDao.select(null, tipoProcedimento,null,false);
		return ret;
	}
}
