/**
 * 
 */
package it.eng.tz.aet.paesaggio.civilia.migrazione.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.aet.paesaggio.civilia.migrazione.IDataSourceCiviliaConstants;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.AutPaesPptrLocalizInterv;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.CiviliaMail;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.CiviliaMailAllegati;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.Comuni_completo_cod_istat;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.DocAmmVPratiche;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.EnteParcoAss;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.InteressePubblicoAss;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PaesaggioRuraleAss;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrAltreDichiarRich;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrApprovato;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrAssoggProcedEdil;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrAttiAcquisiti;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrCaratterizzazioneIntervento;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrDescrIntervento;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrDestUrbInterv;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrDisclaimerXPratica;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrInquadramentoIntervento;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrIstanzaStampe;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrLegittProvved;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrLegittTitoli;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrLegittimita;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrMailInviate;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrParereSop;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrParticelleCatastali;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrProcedimentiContenzioso;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrProtocollo;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrProtocolloIstanza;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrProtocolloUscita;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrProvvedimento;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrQualificIntervento;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrReferentiDoc;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrReferentiProgetto;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrRelazioneEnte;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrSchedaAllegato;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrStatoEffMitigaz;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrStrumentiUrbanistici;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrStrutAntroStorCult;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrStrutEcosistemica;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrStrutIdrogeomorf;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrTipoIntervento;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrTitolarita;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrVincoliArchArch;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.TPratica;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.VtnoAllegatiPptr;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.VtnoAttivitaPptr;
import it.eng.tz.aet.paesaggio.civilia.migrazione.repository.PraticaCiviliaRepository;
import it.eng.tz.aet.paesaggio.civilia.migrazione.service.IDatiPraticaCivService;

/**
 * @author Adriano Colaianni
 * @date 19 apr 2021
 */
@Service
public class DatiPraticaCivService implements IDatiPraticaCivService {

	@Autowired(required = false)
	private PraticaCiviliaRepository datiPraticaDao;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<VtnoAttivitaPptr> getListVtnoAttivitaPptr() {
		if (datiPraticaDao == null)
			return null;
		return this.datiPraticaDao.getListVtnoAttivitaPptr();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<Comuni_completo_cod_istat> getComuniCompletoCodIstat() {
		if (datiPraticaDao == null)
			return null;
		return this.datiPraticaDao.getComuniCompletoCodIstat();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<VtnoAttivitaPptr> getListVtnoAttivitaPptr_Cittadino(String jbpUname) {
		if (datiPraticaDao == null)
			return null;
		return this.datiPraticaDao.getListVtnoAttivitaPptr_Cittadino(jbpUname);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public Long getMaxProgCittadino(String codicePraticaAppptr) {
		if (datiPraticaDao == null)
			return null;
		return this.datiPraticaDao.getMaxProgCittadino(codicePraticaAppptr);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public Long getProvvedimentoSanatoria(String codicePraticaAppptr, Long progr) {
		if (datiPraticaDao == null)
			return null;
		return this.datiPraticaDao.getProvvedimentoSanatoria(codicePraticaAppptr, progr);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public TPratica getTPratica(String codiceAppptr) {
		if (datiPraticaDao == null)
			return null;
		return this.datiPraticaDao.getTPratica(codiceAppptr);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public PptrIstanzaStampe getPptrIstanzaStampe(String codiceAppptr, Long progr) {
		if (datiPraticaDao == null)
			return null;
		return this.datiPraticaDao.getPptrIstanzaStampe(codiceAppptr, progr);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public PptrProtocolloIstanza getPptrProtocolloIstanza(String codiceAppptr, Long progr) {
		if (datiPraticaDao == null)
			return null;
		return this.datiPraticaDao.getPptrProtocolloIstanza(codiceAppptr, progr);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrReferentiProgetto> getReferenti(Long tPraticaId, Long progr) {
		if (datiPraticaDao == null)
			return null;
		return this.datiPraticaDao.getReferenti(tPraticaId, progr);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrReferentiDoc> getDocumento(Long treferenteId) {
		if (datiPraticaDao == null)
			return null;
		return this.datiPraticaDao.getDocumento(treferenteId);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrTitolarita> getTitolaritaReferente(String codicePraticaApptr,Long treferenteId,Long prog) {
		if (datiPraticaDao == null)
			return null;
		return this.datiPraticaDao.getTitolaritaReferente(codicePraticaApptr,treferenteId,prog);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrDisclaimerXPratica> getDisclaimerXPratica(String codicePraticaApptr,Long treferenteId,Long prog) {
		if (datiPraticaDao == null)
			return null;
		return this.datiPraticaDao.getDisclaimerXPratica(codicePraticaApptr,treferenteId,prog);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrAltreDichiarRich> getAltreDichRich(Long tPraticaApptrId) {
		if (datiPraticaDao == null)
			return null;
		return this.datiPraticaDao.getAltreDichRich(tPraticaApptrId);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<AutPaesPptrLocalizInterv> getLocalizzazioneIntervento(String codicePraticaAppptr,long prog){
		if (datiPraticaDao == null)
			return null;
		return this.datiPraticaDao.getLocalizzazioneIntervento(codicePraticaAppptr,prog);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrParticelleCatastali> getParticelleCatastaliLocalizInterv(long localizIntervId, long tPraticaApptrId , long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getParticelleCatastaliLocalizInterv(localizIntervId,tPraticaApptrId,prog);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<EnteParcoAss> getEnteParcoAss(String codicePratica, String codIstat,long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getEnteParcoAss(codicePratica,codIstat,prog);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PaesaggioRuraleAss> getPaesaggioRuraleAss(String codicePraticaApptr, String codIstat,long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getPaesaggioRuraleAss(codicePraticaApptr,codIstat,prog);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<InteressePubblicoAss> getInteressePubblicoAss(String codicePraticaApptr, String codIstat,long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getInteressePubblicoAss(codicePraticaApptr,codIstat,prog);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrTipoIntervento> getDatiTipoIntervento(long tPraticaId,long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getDatiTipoIntervento(tPraticaId,prog);
	}
	
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrCaratterizzazioneIntervento> getDatiCaratterizzazioneIntervento(long tPraticaId,long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getDatiCaratterizzazioneIntervento(tPraticaId,prog);
	}
	

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrQualificIntervento> getDatiQualificazioneIntervento(long tPraticaId,long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getDatiQualificazioneIntervento(tPraticaId,prog);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrDescrIntervento> getDescrIntervento(String codicePraticaAppptr,long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getDescrIntervento(codicePraticaAppptr,prog);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrDestUrbInterv> getDestinazioneUrbanisticaOldMode(String codicePraticaAppptr,long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getDestinazioneUrbanisticaOldMode(codicePraticaAppptr,prog);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrDestUrbInterv> getDestinazioneUrbanistica(Long localizzazioneInterventoId) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getDestinazioneUrbanistica(localizzazioneInterventoId);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrStrumentiUrbanistici> getPptrStrumentiUrbanistici(Long id) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getPptrStrumentiUrbanistici(id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrLegittimita> getLeggittimita(String codicePraticaAppptr, long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getLeggittimita(codicePraticaAppptr,prog);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrLegittProvved> getLeggittimitaProvv(String codicePraticaAppptr, long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getLeggittimitaProvv(codicePraticaAppptr,prog);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrLegittTitoli> getLeggittimitaTitoli(String codicePraticaAppptr, long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getLeggittimitaTitoli(codicePraticaAppptr,prog);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrAssoggProcedEdil> getProcedureEdilizie(String codicePraticaAppptr, long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getProcedureEdilizie(codicePraticaAppptr,prog);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrInquadramentoIntervento> getInquadramentoIntervento(String codicePraticaAppptr, long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getInquadramentoIntervento(codicePraticaAppptr,prog);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrStatoEffMitigaz> getStatoEffmitigazione(String codicePraticaAppptr, long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getStatoEffmitigazione(codicePraticaAppptr,prog);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrProcedimentiContenzioso> getProcedimentiContenzioso(String codicePraticaAppptr, long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getProcedimentiContenzioso(codicePraticaAppptr,prog);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrAttiAcquisiti> getPareriAtti(String codicePraticaAppptr, long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getPareriAtti(codicePraticaAppptr,prog);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrApprovato> getPptrApprovato(String codicePraticaAppptr, long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getPptrApprovato(codicePraticaAppptr,prog);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrStrutAntroStorCult> getPptrAntroStorCult(String codicePraticaAppptr, long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getPptrAntroStorCult(codicePraticaAppptr,prog);
	}
	
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrStrutEcosistemica> getPptrEcosistemica(String codicePraticaAppptr, long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getPptrEcosistemica(codicePraticaAppptr,prog);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrStrutIdrogeomorf> getPptrIdrogeomorf(String codicePraticaAppptr, long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getPptrIdrogeomorf(codicePraticaAppptr,prog);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrVincoliArchArch> getPptrVincolistica(String codicePraticaAppptr, long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getPptrVincolistica(codicePraticaAppptr,prog);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<DocAmmVPratiche> getDocumentazioneAmministrativa(String codicePraticaAppptr, long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getDocumentazioneAmministrativa(codicePraticaAppptr,prog);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<VtnoAllegatiPptr> getDocumentazioneTecnica(String codicePraticaAppptr, long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getDocumentazioneTecnica(codicePraticaAppptr,prog);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrRelazioneEnte> getRelazioneEnte(String codicePraticaAppptr, long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getRelazioneEnte(codicePraticaAppptr,prog);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<VtnoAllegatiPptr> getAllegatiRelazioneEnte(String codicePraticaAppptr, long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getAllegatiRelazioneEnte(codicePraticaAppptr,prog);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<CiviliaMail> getCiviliaEmail(long praticaId){
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getCiviliaEmail(praticaId);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<CiviliaMailAllegati> getCiviliaEmailAllegati(long mailInviateId){
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getCiviliaEmailAllegati(mailInviateId);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrMailInviate> getPptrMailInviate_by_codicePraticaApPptr_tnoGruppoComCodice_tnoTipoComVisibilita(String codicePraticaAppptr , String codice , String visibilita){
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getPptrMailInviate_by_codicePraticaApPptr_tnoGruppoComCodice_tnoTipoComVisibilita(
				codicePraticaAppptr,codice,visibilita);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrParereSop> getParereSOP(String codicePraticaAppptr,long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getParereSOP(
				codicePraticaAppptr,prog);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<VtnoAllegatiPptr> getListParereSopVtnoAllegatiPptr(String codicePraticaAppptr) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getListParereSopVtnoAllegatiPptr(codicePraticaAppptr);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<VtnoAllegatiPptr> getListParereSopDaEnteDelegato(String codicePraticaAppptr,long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getListParereSopDaEnteDelegato(codicePraticaAppptr,prog);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrSchedaAllegato> getListaUlterioreDocumentazione(String codicePraticaAppptr) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getListaUlterioreDocumentazione(codicePraticaAppptr);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrMailInviate> getPptrMailInviate(String codicePraticaAppptr){
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getPptrMailInviate(codicePraticaAppptr);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrProtocollo> getProtocolloByCodice(String codicePraticaAppptr,String codiceTipoProtocollo){
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getProtocolloByCodice(codicePraticaAppptr,codiceTipoProtocollo);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrProvvedimento> getProvvedimento(String codicePraticaAppptr, long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getProvvedimento(codicePraticaAppptr,prog);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<VtnoAllegatiPptr> getProvvedimentoAllegati(String codicePraticaAppptr,long prog) {
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getProvvedimentoAllegati(codicePraticaAppptr,prog);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = IDataSourceCiviliaConstants.ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME)
	public List<PptrProtocolloUscita> getRicevutaTrasmissione(String codicePraticaAppptr,long prog){
		if(datiPraticaDao==null) return null;
		return this.datiPraticaDao.getRicevutaTrasmissione(codicePraticaAppptr,prog);
	}
}
