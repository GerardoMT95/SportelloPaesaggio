/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.civilia.migration.IDataSourceCiviliaConstants;
import it.eng.tz.puglia.autpae.civilia.migration.dto.AutPaesPptrLocalizInterv;
import it.eng.tz.puglia.autpae.civilia.migration.dto.AutPaesPptrPratica;
import it.eng.tz.puglia.autpae.civilia.migration.dto.CiviliaMail;
import it.eng.tz.puglia.autpae.civilia.migration.dto.CiviliaMailAllegati;
import it.eng.tz.puglia.autpae.civilia.migration.dto.Comuni_completo_cod_istat;
import it.eng.tz.puglia.autpae.civilia.migration.dto.EnteParcoAss;
import it.eng.tz.puglia.autpae.civilia.migration.dto.InfoAutPaesPptrAlfa;
import it.eng.tz.puglia.autpae.civilia.migration.dto.InteressePubblicoAss;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PaesaggioRuraleAss;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrCaratterizzazioneIntervento;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrCodiceInterno;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrGruppoUfficio;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrParticelleCatastali;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrProtocolloUscita;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrProvvedimento;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrQualificIntervento;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrRicevutaIstanza;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrSoprintendenzaNoteSt;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrTipoIntervento;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PraticaLavorazione;
import it.eng.tz.puglia.autpae.civilia.migration.dto.ReferentiProgettoDto;
import it.eng.tz.puglia.autpae.civilia.migration.dto.TnoMailEnti;
import it.eng.tz.puglia.autpae.civilia.migration.dto.TnoSopMatriceTerritorio;
import it.eng.tz.puglia.autpae.civilia.migration.dto.VtnoAllegatiPptr;
import it.eng.tz.puglia.autpae.civilia.migration.repository.PraticaRepository;
import it.eng.tz.puglia.autpae.civilia.migration.service.IDatiPraticaCivService;

/**
 * @author Adriano Colaianni
 * @date 19 apr 2021
 */
@Service
public class DatiPraticaCivService implements IDatiPraticaCivService {

	@Autowired(required=false)
	private PraticaRepository datiPraticaDao;
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public List<InfoAutPaesPptrAlfa> getPratiche(int fromNrow, int toNrow) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getListaPratiche(fromNrow,toNrow);
	}
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public List<InfoAutPaesPptrAlfa> getPraticheByCodiciEnteDelegato(List<String> codiciEnteDelegato) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getListaPratiche(codiciEnteDelegato);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public List<ReferentiProgettoDto> getRichiedente(Long tPraticaId,Long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getReferentiPratica("SD", tPraticaId,prog);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public Long getMaxProgressivoPubblicazione(String codicePraticaApptr) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getMaxProgPubblicazione(codicePraticaApptr);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public AutPaesPptrPratica getAutPaesPptrPratica( String codicePraticaApPptr , long prog ) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getAutPaesPptrPratica(codicePraticaApPptr,prog);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public List<AutPaesPptrLocalizInterv> getLocalizzazioneIntervento(String codicePraticaAppptr,long prog){
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getLocalizzazioneIntervento(codicePraticaAppptr,prog);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public List<PptrRicevutaIstanza> getRicevutaIstanza(String codicePraticaAppptr,long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getRicevutaIstanza(codicePraticaAppptr,prog);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public List<PptrCodiceInterno> getDatiCodiceInterno(String codicePraticaAppptr,long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getDatiCodiceInterno(codicePraticaAppptr,prog);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public List<PptrTipoIntervento> getDatiTipoIntervento(long tPraticaId,long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getDatiTipoIntervento(tPraticaId,prog);
	}
	
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public List<PptrCaratterizzazioneIntervento> getDatiCaratterizzazioneIntervento(long tPraticaId,long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getDatiCaratterizzazioneIntervento(tPraticaId,prog);
	}
	

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public List<PptrQualificIntervento> getDatiQualificazioneIntervento(long tPraticaId,long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getDatiQualificazioneIntervento(tPraticaId,prog);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public List<Comuni_completo_cod_istat> getComuniCompletoCodIstat() {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getComuniCompletoCodIstat();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public List<PptrParticelleCatastali> getParticelleCatastaliLocalizInterv(long localizIntervId, long tPraticaApptrId , long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getParticelleCatastaliLocalizInterv(localizIntervId,tPraticaApptrId,prog);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public List<EnteParcoAss> getEnteParcoAss(String codicePratica, String codIstat,long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getEnteParcoAss(codicePratica,codIstat,prog);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public List<PaesaggioRuraleAss> getPaesaggioRuraleAss(String codicePraticaApptr, String codIstat,long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getPaesaggioRuraleAss(codicePraticaApptr,codIstat,prog);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public List<InteressePubblicoAss> getInteressePubblicoAss(String codicePraticaApptr, String codIstat,long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getInteressePubblicoAss(codicePraticaApptr,codIstat,prog);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public List<PptrProvvedimento> getProvvedimento(String codicePraticaApptr, long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getProvvedimento(codicePraticaApptr,prog);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public List<VtnoAllegatiPptr> getListAllegatiProvvedimento(String codicePraticaAppptr,long prog){
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getListAllegatiProvvedimento(codicePraticaAppptr,prog);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public List<VtnoAllegatiPptr> getListAllegatiFascicolo(String codicePraticaAppptr){
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getListAllegatiFascicolo(codicePraticaAppptr);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public List<PptrProtocolloUscita> getRicevutaTrasmissione(String codicePraticaAppptr,long prog){
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getRicevutaTrasmissione(codicePraticaAppptr,prog);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public List<CiviliaMail> getCiviliaEmail(long praticaId,boolean is016){
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getCiviliaEmail(praticaId,is016);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public List<CiviliaMailAllegati> getCiviliaEmailAllegati(long mailInviateId,boolean is016){
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getCiviliaEmailAllegati(mailInviateId,is016);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public List<PptrGruppoUfficio> getUffici(){
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getAllUffici();
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public Long countPratichePptrTrasmesse() {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.countPratichePptrTrasmesse();
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true, transactionManager=IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public List<PptrSoprintendenzaNoteSt> getTnoPptrSopNote(String codicePraticaAppptr,long progr) throws Exception {
		if(datiPraticaDao == null) return null;
		return datiPraticaDao.getTnoPptrSopNote(codicePraticaAppptr,progr);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true, transactionManager=IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public List<PraticaLavorazione> getListaPraticheInLavorazione(int fromRowNum, int toRowNum) {
		if(datiPraticaDao == null) return null;
		return datiPraticaDao.getListaPraticheInLavorazione(fromRowNum,toRowNum);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true, transactionManager=IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public Long countPratichePptrInLavorazione() {
		if(datiPraticaDao == null) return null;
		return datiPraticaDao.countPratichePptrInLavorazione();
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true, transactionManager=IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public List<TnoMailEnti> getTnoMailEnti() {
		if(datiPraticaDao == null) return null;
		return datiPraticaDao.getTnoMailEnti();
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true, transactionManager=IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public List<TnoSopMatriceTerritorio> getTnoSopMatriceTerritorio() {
		if(datiPraticaDao == null) return null;
		return datiPraticaDao.getTnoSopMatriceTerritorio();	
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true, transactionManager=IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME, rollbackFor=Exception.class)
	public String getIdCmsFromTkeStDocId(Integer tKeStDocId) {
		if(datiPraticaDao == null) return null;
		return datiPraticaDao.getIdCmsFromTkeStDocId(tKeStDocId);
	}
}


