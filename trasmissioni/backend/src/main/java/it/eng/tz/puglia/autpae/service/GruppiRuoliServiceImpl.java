package it.eng.tz.puglia.autpae.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dto.TipologicaDTO;
import it.eng.tz.puglia.autpae.dto.BE_to_FE.PlainTypeStringIdDTO;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.enumeratori.Ruoli;
import it.eng.tz.puglia.autpae.generated.orm.repository.PaesaggioOrganizzazioneAttributiRepository;
import it.eng.tz.puglia.autpae.generated.orm.repository.PaesaggioOrganizzazioneRepository;
import it.eng.tz.puglia.autpae.service.interfacce.GruppiRuoliService;
import it.eng.tz.puglia.bean.UserDetail;
import it.eng.tz.puglia.cache.ICacheConstants;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.service.http.IHttpClientService;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.AssUtenteGruppo;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.GruppoBean;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.PM_GruppiRuoli;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.UtenteGruppo;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.response.ResponseBase;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.IProfileManager;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.UserUtil;
import it.eng.tz.puglia.servizi_esterni.remote.dto.EnteWIstatDTO;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.CommonService;
import it.eng.tz.puglia.servizi_esterni.remote.utility.TipoEnte;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class GruppiRuoliServiceImpl implements GruppiRuoliService {

	private static final Logger log = LoggerFactory.getLogger(GruppiRuoliServiceImpl.class);
//	private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot access attachments for plan exclusion of this municipality";
	
	@Autowired private CommonService commonService;
	
	@Autowired private UserUtil userUtil;
	
	@Autowired private IProfileManager profileManager;
	@Autowired private PaesaggioOrganizzazioneAttributiRepository orgAttrDao;
	@Autowired private PaesaggioOrganizzazioneRepository paesaggioOrganizzazioneDao;
	
	@Autowired
	IHttpClientService clientSvc;
	
	@Autowired
	ApplicationProperties props;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public void checkGruppo(final String codice_GruppoIdRuolo) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		if (StringUtil.isEmpty(codice_GruppoIdRuolo)) {
			log.error("Errore. Il gruppo non può essere vuoto!");
			throw new Exception("Errore. Il gruppo non può essere vuoto!");
		}
		else if (codice_GruppoIdRuolo.toUpperCase().equals(Gruppi.ADMIN.name())) {
			return;
		}
		else {
			final int idPaeOrg = this.userUtil.getIntegerId(codice_GruppoIdRuolo);

			// verifico che il gruppo abbia il permesso per l'applicazione
			try {
				this.commonService.checkPermessoOrganizzazioneApplicazione(idPaeOrg, this.props.getCodiceApplicazioneProfile());
			} catch (final Exception e) {
				log.error("Errore. Il gruppo "+codice_GruppoIdRuolo+" (id="+idPaeOrg+") non possiede [più] i permessi per questa applicazione ("+this.props.getCodiceApplicazioneProfile()+")",e);
				throw new CustomOperationToAdviceException("Errore. Il gruppo "+codice_GruppoIdRuolo+" (id="+idPaeOrg+") non possiede [più] i permessi per questa applicazione ("+this.props.getCodiceApplicazioneProfile()+")");
			}

			// verifico che il gruppo sia ancora valido
			try {
				this.commonService.checkValiditaOrganizzazione(idPaeOrg);
			} catch (final Exception e) {
				log.error("Errore. Il periodo di validità del gruppo "+codice_GruppoIdRuolo+" (id="+idPaeOrg+") è scaduto");
				throw new CustomOperationToAdviceException("Errore. Il periodo di validità del gruppo "+codice_GruppoIdRuolo+" (id="+idPaeOrg+") è scaduto");
			}

			// verifico che il gruppo abbia deleghe valide su almeno un Ente
			try {
				this.commonService.checkValiditaDelegaOrganizzazione(idPaeOrg);
			} catch (final Exception e) {
				log.error("Errore. Il gruppo "+codice_GruppoIdRuolo+" (id="+idPaeOrg+") attualmente non ha competenza territoriale su nessun ente");
				throw new CustomOperationToAdviceException("Errore. Il gruppo "+codice_GruppoIdRuolo+" (id="+idPaeOrg+") attualmente non ha competenza territoriale su nessun ente");
			}

			// verifico che il 'tipo_organizzazione' del gruppo sia tra quelli ammessi
			final String to = this.commonService.getTipoOrganizzazione(idPaeOrg)+"_";
			Gruppi tipoOrg = null;
			try {
				tipoOrg = Gruppi.valueOf(to.toUpperCase());
				if (tipoOrg==null) throw new Exception();
			} catch (final Exception e) {
				log.error("Errore. Il tipo di organizzazione '"+to+"' non corrisponde a nessun gruppo gestito da questo applicativo");
				throw new CustomOperationToAdviceException("Errore. Il tipo di organizzazione '"+to+"' non corrisponde a nessun gruppo gestito da questo applicativo");
			}
			if (!tipoOrg.name().equalsIgnoreCase(to)) {
				log.error("Errore. Il tipo di organizzazione '"+to+"' non corrisponde a quello dichiarato ("+tipoOrg.name()+")");
				throw new CustomOperationToAdviceException("Errore. Il tipo di organizzazione '"+to+"' non corrisponde a quello dichiarato ("+tipoOrg.name()+")");
			}
		}
	}
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public  List<String> comuniForGruppoUtenteLoggato() throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		return this.comuniForGruppo(this.userUtil.getGruppo_Id());
	}
	
	@Transactional(propagation=Propagation.MANDATORY, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	private List<String> comuniForGruppo(final String gruppo_id) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		//	this.checkGruppo(gruppo);	// TODO: vedere quando fare la verifica, se ogni volta o no
		
		if (gruppo_id.equals(Gruppi.ADMIN.name())) {
			return this.commonService.getAllCodiciEntiCOMUNI();
		}
		
		final int idPaeOrg = this.userUtil.getIntegerId(gruppo_id);
		
		final Set<Integer> idEntiComuni = new HashSet<>();
		idEntiComuni.addAll(this.commonService.getEntiCOMUNIdiCompetenzaForOrganizzazione(idPaeOrg));		
		final List<EnteWIstatDTO> entiWIstat = this.commonService.selectEnteWIstatById(new ArrayList<>(idEntiComuni));
		//List<String> codiciComuni = new ArrayList<>();
		
//		for (int idComune : idEntiComuni) {
//			codiciComuni.add(commonService.getCodiceEnte(idComune));
//		}
		final List<String> codiciComuni = entiWIstat.stream().map(EnteWIstatDTO::getCodice).collect(Collectors.toList());
		return codiciComuni;
	}
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public  List<Integer> entiForGruppoUtenteLoggato() throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		return this.entiForGruppo(this.userUtil.getGruppo_Id());
	}
	
	@Override
	@Transactional(propagation=Propagation.MANDATORY, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	@Cacheable(keyGenerator = ICacheConstants.KEY_CONTEXT_NAME, value = "it.eng.tz.puglia.autpae.service.GruppiRuoliServiceImpl.entiForGruppo", unless = "#result == null")
	public List<Integer> entiForGruppo(final String gruppo_id) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		if (gruppo_id.equals(Gruppi.ADMIN.name())) {
			return this.commonService.getAllEntiCOMUNI();
		}
		
		final int idPaeOrg = this.userUtil.getIntegerId(gruppo_id);
		
		final List<Integer> enti = this.commonService.getEntiCOMUNIdiCompetenzaForOrganizzazione(idPaeOrg);		
	
		return enti;
	}
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public void checkAbilitazioneFor(final Gruppi... gruppiAmmessi) throws CustomOperationToAdviceException {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		final Gruppi gruppoLoggato = this.userUtil.getGruppo();
		
		for (final Gruppi i : gruppiAmmessi) {
			if (i.equals(gruppoLoggato))
				return;
		}
		log.error("Gruppo "+gruppoLoggato+" non abilitato per eseguire questa operazione!");
		throw new CustomOperationToAdviceException("Gruppo "+gruppoLoggato+" non abilitato per eseguire questa operazione!");
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public void checkAbilitazioneFor(final Ruoli... ruoliAmmessi) throws CustomOperationToAdviceException {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		final Ruoli ruoloLoggato = this.userUtil.getRuolo();
		
		for (final Ruoli i : ruoliAmmessi) {
			if (i.equals(ruoloLoggato))
				return;
		}
		log.error("Ruolo "+ruoloLoggato+" non abilitato per eseguire questa operazione!");
		throw new CustomOperationToAdviceException("Ruolo "+ruoloLoggato+" non abilitato per eseguire questa operazione!");
	}
	
	/**
	 * true se il gruppo è ammesso alla richiesta abilitazione
	 * @param codiceGruppo
	 * @return
	 */
	private  boolean okAbilitazione(final String codiceGruppo) {
		boolean ret=false;
		final String[] parts=codiceGruppo.split("_");
		try {
			if(codiceGruppo.equals(Gruppi.ADMIN.toString())|| parts.length<3) {
				return false;
			}
			//verifico che sia presente nelle organizzazioni ammesse
			ret=List.of(Gruppi.tipoOrganizzazioniAbilitazione(this.props.isPareri()))
					.stream()
					.map(el->{return el.toString().split("_")[0];})
					.filter(el->el.equals(parts[0])).findAny().isPresent();
			if(!ret) return false;
			//verifico che sia ancora attivo
			//this.checkGruppo(codiceGruppo);
			ret=true;
		} catch (final Exception e) {
			log.info("gruppo non valido: "+codiceGruppo,e);
			ret=false;
		}
		return ret;
	}
	
	private String withoutRole(final String codiceGruppo) {
		final String[] parts=codiceGruppo.split("_");
		if(parts.length<3) return "";
		return parts[0]+'_'+parts[1];
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public List<PlainTypeStringIdDTO> getGruppiOrganizzazioni() throws Exception{
		final List<PlainTypeStringIdDTO> ret=new ArrayList<>();
		final ResponseBase<GruppoBean[]> response = this.profileManager.getGruppiUtente(LogUtil.getAuthorization(), this.props.getCodiceApplicazioneProfile(),"");
		final GruppoBean[] gruppi = response.getPayload();
		//lista di codici gruppo troncati della parte ruolo es. ED_46
		final List<String> idsOrganizzazioniPM = 
				Arrays.asList(gruppi)
				.stream()
				.filter(gruppo->this.okAbilitazione(gruppo.getCodiceGruppo()))
				.map(gruppo->this.withoutRole(gruppo.getCodiceGruppo()))
				.collect(Collectors.toList());
		final List<TipologicaDTO> organizzazioniAttive=this.commonService.getOrganizzazioniInfoValidePerApplicazione(this.props.getCodiceApplicazioneProfile(), idsOrganizzazioniPM);
		for(final GruppoBean gruppoPM:gruppi) {
			//per ogni gruppo verifico che il prefisso a meno del ruolo sia attivo...es. ED_43 presente in organizzazioniAttive
			for(final TipologicaDTO org:organizzazioniAttive) {
				if(org.getCodice().equals(this.withoutRole(gruppoPM.getCodiceGruppo()))) {
					final PlainTypeStringIdDTO item = new PlainTypeStringIdDTO(gruppoPM.getCodiceGruppo(), org.getLabel());
					ret.add(item);
					break;
				}
			}
		}
		return ret;
	}
	
	@Override
	@Deprecated
	@Transactional(propagation=Propagation.SUPPORTS, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public UtenteGruppo[] getAdministratorsProfile() throws Exception {
		UtenteGruppo[] infoUtenti=null;
		final String authorization=this.clientSvc.getBatchUser().getAuthorization();
		final String oldAuth=LogUtil.getAuthorization();
		try {
			LogUtil.addAuthorization(authorization);
			//in modo che venga aggiunto nell'header dall'FeignRequestInterceptor	
			infoUtenti = this.profileManager.getUtentiGruppo(authorization, this.props.getCodiceApplicazioneProfile(), Gruppi.ADMIN.name(), null, true).getPayload();
		} catch(final Throwable e) {
			throw e;
		} finally {
			LogUtil.addAuthorization(oldAuth);
		}
		return infoUtenti;
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public AssUtenteGruppo[] getAdministratorsProfilePM() throws Exception {
		AssUtenteGruppo[] infoUtenti=null;
		final String authorization=this.clientSvc.getBatchUser().getAuthorization();
		final String oldAuth=LogUtil.getAuthorization();
		try {
			LogUtil.addAuthorization(authorization);
			//in modo che venga aggiunto nell'header dall'FeignRequestInterceptor	
			infoUtenti = this.profileManager.utentiInGruppi(authorization, this.props.getCodiceApplicazioneProfile(), Arrays.asList(Gruppi.ADMIN.name())).getPayload();
		} catch(final Throwable e) {
			throw e;
		} finally {
			LogUtil.addAuthorization(oldAuth);
		}
		return infoUtenti;
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public boolean organizzazioneValida() throws Exception
	{
		return this.orgAttrDao.organizzazioneValida(this.userUtil.getLongId());
	}
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public void checkAbilitazioneForTrasmissione() throws CustomOperationToAdviceException {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		final Gruppi gruppoLoggato = this.userUtil.getGruppo();
		if(Gruppi.ED_.equals(gruppoLoggato)) {
			return; //enti delegati sempre ammessi
		}else if(this.props.isPutt() && Gruppi.ETI_.equals(gruppoLoggato)) {
			// per ente territorialmente interessato, devo verificare se sono nel caso di PUTT con un COMUNE con codiceCivilia.
			final UserDetail userDetail = SecurityUtil.getUserDetail();
			if(userDetail.getOtherFields()!=null && userDetail.getOtherFields().get("GruppoRuoli")!=null) {
				try {
				@SuppressWarnings("unchecked")
				final
				List<PM_GruppiRuoli> gruppoRuoli = (List<PM_GruppiRuoli>)userDetail.getOtherFields().get("GruppoRuoli");
				final boolean isComune = gruppoRuoli.stream().filter(gruppoRuolo->{
					if(gruppoRuolo.getCodiceGruppo().equals(this.userUtil.getCodice_GruppoIdRuolo())) {
						final String codiceCivilia=(String) gruppoRuolo.getAdditionalProperties().get("codiceCivilia");
						final String tipologiaEnte=(String) gruppoRuolo.getAdditionalProperties().get("tipologiaEnte");
						return StringUtil.isNotEmpty(codiceCivilia) && tipologiaEnte.equals(TipoEnte.comune.getCodice());
					}else 
						return false;
					}).findAny().isPresent();
					if(isComune)
						return; //ok passed
				}catch(final Exception e) {
					log.error("Errore durante il fetch delle informazioni specifiche sull'organizzazione del gruppo {}",this.userUtil.getCodice_GruppoIdRuolo(),e);
				}
			}
		}
		log.error("Gruppo "+gruppoLoggato+" non abilitato per eseguire questa operazione!");
		throw new CustomOperationToAdviceException("Gruppo "+gruppoLoggato+" non abilitato per eseguire questa operazione!");
	}
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public void checkAbilitazioneForCancellazione() throws CustomOperationToAdviceException {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		final Gruppi gruppoLoggato = this.userUtil.getGruppo();
		if(Gruppi.ADMIN.equals(gruppoLoggato)) {
			return; 
		}else {
		  this.checkAbilitazioneForTrasmissione();
		}
	}
	
	/**
	 * lista delle organizzazioni dell'utente loggato (SecurityUtil.getUserDetail())
	 * @autor Adriano Colaianni
	 * @date 20 set 2021
	 * @return
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public List<Integer> organizzazioniUser(){
		final List<Integer> ids=new ArrayList<>();
		final UserDetail userDetail = SecurityUtil.getUserDetail();
		if(userDetail!=null && userDetail.getOtherFields()!=null && userDetail.getOtherFields().get("GruppoRuoli")!=null) {
			try {
			@SuppressWarnings("unchecked")
			final
			List<PM_GruppiRuoli> gruppoRuoli = (List<PM_GruppiRuoli>)userDetail.getOtherFields().get("GruppoRuoli");
			ids.addAll(gruppoRuoli.stream().filter(grR->Gruppi.isGruppoConOrganizzazione(grR.getCodiceGruppo()))
					.map(grRuolo->{
				return Gruppi.getIdOrganizzazione(grRuolo.getCodiceGruppo());
				}).collect(Collectors.toList()));
			}catch(final Exception e) {
				log.error("Errore durante il fetch delle informazioni specifiche sull'organizzazione del gruppo {}",this.userUtil.getCodice_GruppoIdRuolo(),e);
			}
		}
		return ids;
	}
	
	/**
	 * true se l'organizzazione contiene anche l'utente loggato, verifica nel profilo dell'utente loggato
	 * SecurityUtil.getUserDetail() 
	 * @autor Adriano Colaianni
	 * @date 20 set 2021
	 * @param idOrganizzazione
	 * @return
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public boolean userHasOrganizzazione(final long idOrganizzazione) {
		final List<Integer> ids = this.organizzazioniUser();
		return ListUtil.isNotEmpty(ids) &&
				ids.contains((int)idOrganizzazione);
	}


	/**
	 * @author Antonio La Gatta
	 * @date 26 mag 2022
	 * @see it.eng.tz.puglia.autpae.service.interfacce.GruppiRuoliService#isMultiComune(java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, timeout=60000, readOnly=true, transactionManager = "txmng_common")
	public boolean isMultiComune(final String codiceGruppo) {
		final StopWatch sw = LogUtil.startLog("isMultiComune ", codiceGruppo);
		log.info("isMultiComune {}", codiceGruppo);
		try {
			if(StringUtil.isEmpty(codiceGruppo))
				return false;
			String[] parts = codiceGruppo.split("\\_");
			if(parts.length<2)
				return false;
			return this.paesaggioOrganizzazioneDao.isMultiComune(Long.parseLong(parts[1]));
		}finally {
			log.info(LogUtil.stopLog(sw));
		}
	} 
	
	
}