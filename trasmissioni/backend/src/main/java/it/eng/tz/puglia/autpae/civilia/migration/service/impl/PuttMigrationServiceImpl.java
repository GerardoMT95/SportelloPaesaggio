package it.eng.tz.puglia.autpae.civilia.migration.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.civilia.migration.IDataSourceCiviliaConstants;
import it.eng.tz.puglia.autpae.civilia.migration.dto.CiviliaPratica;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrSoprintendenzaNoteSt;
import it.eng.tz.puglia.autpae.civilia.migration.dto.putt.InfoAutPaesAlfaBean;
import it.eng.tz.puglia.autpae.civilia.migration.dto.putt.LocalizzazionePuttBean;
import it.eng.tz.puglia.autpae.civilia.migration.dto.putt.PuttDocBean;
import it.eng.tz.puglia.autpae.civilia.migration.dto.putt.TnoProtocolloUscita;
import it.eng.tz.puglia.autpae.civilia.migration.repository.PraticaRepository;
import it.eng.tz.puglia.autpae.civilia.migration.service.PuttMigrationService;

@Service
public class PuttMigrationServiceImpl implements PuttMigrationService
{
	@Autowired(required=false)
	private PraticaRepository dao;
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true, transactionManager=IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public List<InfoAutPaesAlfaBean> getPuttPratiche(int from, int to) throws Exception
	{
		if(dao == null) return null;
		return dao.getPuttPratiche(from, to);
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true, transactionManager=IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public Long countPuttPraticheTrasmesse() throws Exception{
		if(dao == null) return null;
		return dao.countPuttPraticheTrasmesse();
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true, transactionManager=IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public List<InfoAutPaesAlfaBean> getPuttPraticheFromCodes(List<String> listaCodici) throws Exception
	{
		if(dao == null) return null;
		return dao.getPuttPraticheFromCodes(listaCodici);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true, transactionManager=IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public List<LocalizzazionePuttBean> getPuttLocalizzazione(Long praticaId,boolean is016) throws Exception
	{
		if(dao == null) return null;
		return dao.getPuttLocalizzazione(praticaId,is016);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true, transactionManager=IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public List<PuttDocBean> getPuttAllegati(String codicePratica,boolean is016) throws Exception
	{
		if(dao == null) return null;
		return dao.getPuttAllegati(codicePratica,is016);
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true, transactionManager=IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public List<TnoProtocolloUscita> getProtocolloUscita(long tPraticaId) throws Exception
	{
		if(dao == null) return null;
		return dao.getPuttRicevutaTrasmissione(tPraticaId);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true, transactionManager=IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public List<CiviliaPratica> getCiviliaPratica(Long tPraticaId, boolean is016) throws Exception {
		if(dao == null) return null;
		return dao.getCiviliaPratica(tPraticaId,is016);
	}
	
}