package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.poi.EmptyFileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.dto.UtenteAttributeDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.dto.UtenteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.repository.ApplicazioneRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.repository.UtenteAttributeRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.repository.UtenteRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.ApplicationProperties;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Ruoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.AuthClient;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.RupRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.AssUtenteGruppo;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.GruppoBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.ProfileUserResponseBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.ResponseBase;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.UtenteGruppo;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.service.RemoteSchemaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.UtenteAttributeBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.GruppiRuoliService;
import it.eng.tz.puglia.bean.UserDetail;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.service.http.IHttpClientService;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class GruppiRuoliServiceImpl implements GruppiRuoliService {

	private static final Logger log = LoggerFactory.getLogger(GruppiRuoliServiceImpl.class);
	
	@Autowired private RemoteSchemaService remoteSchemaService;
	
	@Autowired private UserUtil userUtil;
	
	@Autowired private AuthClient profileManager;
	
	@Autowired private ApplicationProperties props;
	
	@Autowired private IHttpClientService clientSvc;
	
	@Autowired private RupRepository rupRepository;
	
	@Autowired private UtenteRepository userDao;
	
	@Autowired private UtenteAttributeRepository userAttDao;
	
	@Autowired private ApplicazioneRepository appDao;
	
	
	@Override
	@Transactional(transactionManager="txmng_common", propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public void checkGruppo(String codice_GruppoIdRuolo) throws Exception {
		log.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		if (StringUtil.isEmpty(codice_GruppoIdRuolo)) {
			log.error("Errore. Il gruppo non può essere vuoto!");
			throw new Exception("Errore. Il gruppo non può essere vuoto!");
		}
		else if (codice_GruppoIdRuolo.toUpperCase().equals(Gruppi.ADMIN.name())) {
			return;
		}
		else {
			int 	idPaeOrg	= userUtil.getIntegerId(codice_GruppoIdRuolo);
//			Integer riferimento = remoteSchemaService.getRiferimentoOrganizzazione(idPaeOrg);

			// verifico che SIA il gruppo, SIA il gruppo di riferimento, abbiano i permessi per l'applicazione
			try {
				remoteSchemaService.checkPermessoOrganizzazioneApplicazione(idPaeOrg, props.getCodiceApplicazioneProfile());
			} catch (Exception e) {
				log.error("Errore. Il gruppo "+codice_GruppoIdRuolo+" (id="+idPaeOrg+") non possiede [più] i permessi per questa applicazione ("+props.getCodiceApplicazioneProfile()+")",e);
				throw new Exception("Errore. Il gruppo "+codice_GruppoIdRuolo+" (id="+idPaeOrg+") non possiede [più] i permessi per questa applicazione ("+props.getCodiceApplicazioneProfile()+")");
			}
//			if (riferimento!=null) {
//				try {
//					remoteSchemaService.checkPermessoOrganizzazioneApplicazione(idPaeOrg, props.getCodiceApplicazioneProfile());
//				} catch (Exception e) {
//					log.error("Errore. L'organizzazione (id="+riferimento+") a cui fa riferimento il gruppo "+codice_GruppoIdRuolo+" non possiede [più] i permessi per questa applicazione ("+props.getCodiceApplicazioneProfile()+")");
//					throw new Exception("Errore. L'organizzazione (id="+riferimento+") a cui fa riferimento il gruppo "+codice_GruppoIdRuolo+" non possiede [più] i permessi per questa applicazione ("+props.getCodiceApplicazioneProfile()+")");
//				}
//			}

			// verifico che SIA il gruppo, SIA il gruppo di riferimento, siano ancora validi
			try {
				remoteSchemaService.checkValiditaOrganizzazione(idPaeOrg);
			} catch (Exception e) {
				log.error("Errore. Il periodo di validità del gruppo "+codice_GruppoIdRuolo+" (id="+idPaeOrg+") è scaduto");
				throw new Exception("Errore. Il periodo di validità del gruppo "+codice_GruppoIdRuolo+" (id="+idPaeOrg+") è scaduto");
			}
//			if (riferimento!=null) {
//				try {
//					remoteSchemaService.checkValiditaOrganizzazione(riferimento);
//				} catch (Exception e) {
//					log.error("Errore. Il periodo di validità dell'organizzazione (id="+riferimento+") a cui fa riferimento il gruppo "+codice_GruppoIdRuolo+" è scaduto");
//					throw new Exception("Errore. Il periodo di validità dell'organizzazione (id="+riferimento+") a cui fa riferimento il gruppo "+codice_GruppoIdRuolo+" è scaduto");
//				}
//			}

			// verifico che O il gruppo, OPPURE il gruppo di riferimento, abbiano deleghe valide su qualche ente
			if(!userUtil.getGruppo(codice_GruppoIdRuolo).equals(Gruppi.CL_))
			{
				try {
					remoteSchemaService.checkValiditaDelegaOrganizzazione(idPaeOrg);
				} catch (Exception e) {
					log.error("Errore. Il gruppo "+codice_GruppoIdRuolo+" (id="+idPaeOrg+") attualmente non ha competenza territoriale su nessun ente");
					throw new Exception("Errore. Il gruppo "+codice_GruppoIdRuolo+" (id="+idPaeOrg+") attualmente non ha competenza territoriale su nessun ente");
//					if (riferimento==null) {
//						log.error("Errore. Il gruppo "+codice_GruppoIdRuolo+" (id="+idPaeOrg+") attualmente non ha competenza territoriale su nessun ente");
//						throw new Exception("Errore. Il gruppo "+codice_GruppoIdRuolo+" (id="+idPaeOrg+") attualmente non ha competenza territoriale su nessun ente");
//					}
//					else {
//						try {
//							remoteSchemaService.checkValiditaDelegaOrganizzazione(riferimento);
//						} catch (Exception e1) {
//							log.error("Errore. L'organizzazione (id="+riferimento+") a cui fa riferimento il gruppo "+codice_GruppoIdRuolo+" attualmente non ha competenza territoriale su nessun ente");
//							throw new Exception("Errore. L'organizzazione (id="+riferimento+") a cui fa riferimento il gruppo "+codice_GruppoIdRuolo+" attualmente non ha competenza territoriale su nessun ente");
//						}
//					}
				}
			}
			else
			{
				//Sono una commissione locale: verifico che i criteri verificati sopra valgano per 
				//ciascuno degli ED a cui sono associato
				List<Integer> entiAssociati = remoteSchemaService.getPaesaggiEntiForCommissione(idPaeOrg);
				if(entiAssociati == null || entiAssociati.isEmpty())
				{
					log.error("Errore: nessun ente associato alla commissione locale utilizzata");
					throw new Exception("Errore: nessun ente associato alla commissione locale utilizzata");
				}
				for(Integer ente: entiAssociati)
				{
					try {
						remoteSchemaService.checkPermessoOrganizzazioneApplicazione(ente, props.getCodiceApplicazioneProfile());
					} catch (Exception e) {
						log.error("Errore. Il gruppo ED_"+ente+" non possiede [più] i permessi per questa applicazione ("+props.getCodiceApplicazioneProfile()+")",e);
						throw new Exception("Errore. Il gruppo ED_\"+ente+\" non possiede [più] i permessi per questa applicazione ("+props.getCodiceApplicazioneProfile()+")");
					}
					try {
						remoteSchemaService.checkValiditaOrganizzazione(ente);
					} catch (Exception e) {
						log.error("Errore. Il periodo di validità del gruppo ED_"+ente+") è scaduto");
						throw new Exception("Errore. Il periodo di validità del gruppo ED_"+ente+") è scaduto");
					}
					try {
						remoteSchemaService.checkValiditaDelegaOrganizzazione(ente);
					} catch (Exception e) {
						log.error("Errore. Il gruppo ED_"+ente+") attualmente non ha competenza territoriale su nessun ente");
						throw new Exception("Errore. Il gruppo ED_"+ente+") attualmente non ha competenza territoriale su nessun ente");
					}
				}
			}

			// verifico che, SIA per il gruppo, SIA per il gruppo di riferimento, il 'tipo_organizzazione' è tra quelli ammessi
			String to = remoteSchemaService.getTipoOrganizzazione(idPaeOrg)+"_";
			Gruppi tipoOrg = null;
			try {
				tipoOrg = Gruppi.valueOf(to.toUpperCase());
				if (tipoOrg==null) throw new Exception();
			} catch (Exception e) {
				log.error("Errore. Il tipo di organizzazione '"+to+"' non corrisponde a nessun gruppo gestito da questo applicativo");
				throw new Exception("Errore. Il tipo di organizzazione '"+to+"' non corrisponde a nessun gruppo gestito da questo applicativo");
			}
			if (!tipoOrg.name().equalsIgnoreCase(to)) {
				log.error("Errore. Il tipo di organizzazione '"+to+"' non corrisponde a quello dichiarato ("+tipoOrg.name()+")");
				throw new Exception("Errore. Il tipo di organizzazione '"+to+"' non corrisponde a quello dichiarato ("+tipoOrg.name()+")");
			}
//			if (riferimento!=null) {
//				String toRif = remoteSchemaService.getTipoOrganizzazione(riferimento)+"_";
//				Gruppi tipoOrgRif = null;
//				try {
//					tipoOrgRif = Gruppi.valueOf(toRif.toUpperCase());
//					if (tipoOrgRif==null) throw new Exception();
//				} catch (Exception e) {
//					log.error("Errore. Il tipo di organizzazione '"+toRif+"' (id="+riferimento+") a cui fa riferimento il gruppo "+codice_GruppoIdRuolo+" non corrisponde a nessun gruppo gestito da questo applicativo");
//					throw new Exception("Errore. Il tipo di organizzazione '"+toRif+"' (id="+riferimento+") a cui fa riferimento il gruppo "+codice_GruppoIdRuolo+" non corrisponde a nessun gruppo gestito da questo applicativo");
//				}
//				if (!tipoOrgRif.name().equalsIgnoreCase(toRif)) {
//					log.error("Errore. Il tipo di organizzazione di riferimento '"+toRif+"' non corrisponde a quello dichiarato ("+tipoOrgRif.name()+")");
//					throw new Exception("Errore. Il tipo di organizzazione di riferimento '"+toRif+"' non corrisponde a quello dichiarato ("+tipoOrgRif.name()+")");
//				}
//			}
		}
	}
	
	
	@Override
	@Transactional(transactionManager="txmng_common", propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public  List<String> comuniForGruppoUtenteLoggato() throws Exception {
		log.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		return this.comuniForGruppo(userUtil.getGruppo_Id());
	}
	
	@Transactional(transactionManager="txmng_common", propagation=Propagation.MANDATORY, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	private List<String> comuniForGruppo(String gruppo_id) throws Exception {
		log.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		//	this.checkGruppo(gruppo);	// TODO: vedere quando fare la verifica, se ogni volta o no
		
		if (gruppo_id.equals(Gruppi.ADMIN.name())) {
			return remoteSchemaService.getAllCodiciEntiCOMUNI();
		}
		
		int 	idPaeOrg 	= userUtil.getIntegerId(gruppo_id);
		Integer riferimento = remoteSchemaService.getRiferimentoOrganizzazione(idPaeOrg);
		
		Set<Integer> entiComuni = new HashSet<>();
		// gli enti di competenza sono: quelli di sua diretta competenza PIU' quelli di competenza del gruppo di riferimento
		entiComuni.addAll(remoteSchemaService.getEntiCOMUNIdiCompetenzaForOrganizzazione(idPaeOrg));		
		if (riferimento!=null) {
			entiComuni.addAll(remoteSchemaService.getEntiCOMUNIdiCompetenzaForOrganizzazione(riferimento));
		}
		
		List<String> codici = new ArrayList<>();
		
		for (int comune : entiComuni) {
			codici.add(remoteSchemaService.getCodiceEnte(comune));
		}
		return codici;
	}
	
	
	@Override
	@Transactional(transactionManager="txmng_common", propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public List<Integer> entiForGruppoUtenteLoggato() throws Exception {
		log.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		return this.entiForGruppo(userUtil.getGruppo_Id());
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public List<Integer> entiForGruppo(String gruppo_id) throws Exception {
		log.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		if (gruppo_id.equals(Gruppi.ADMIN.name())) {
			return remoteSchemaService.getAllEntiCOMUNI();
		}
		
		int 	idPaeOrg 	= userUtil.getIntegerId(gruppo_id);
		Integer riferimento = remoteSchemaService.getRiferimentoOrganizzazione(idPaeOrg);
		
		Set<Integer> enti = new HashSet<>();
		// gli enti di competenza sono: quelli di sua diretta competenza PIU' quelli di competenza del gruppo di riferimento
		enti.addAll(remoteSchemaService.getEntiCOMUNIdiCompetenzaForOrganizzazione(idPaeOrg));		
		if (riferimento!=null) {
			enti.addAll(remoteSchemaService.getEntiCOMUNIdiCompetenzaForOrganizzazione(riferimento));
		}
		return new ArrayList<>(enti);
	}
	
	
	@Override
	@Transactional(transactionManager="txmng_common",propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public void checkAbilitazioneFor(Gruppi... gruppiAmmessi) throws CustomOperationToAdviceException {
		log.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		Gruppi gruppoLoggato = userUtil.getGruppo();
		
		for (Gruppi i : gruppiAmmessi) {
			if (i.equals(gruppoLoggato))
				return;
		}
		log.error("Gruppo "+gruppoLoggato+" non abilitato per eseguire questa operazione!");
		throw new CustomOperationToAdviceException("Gruppo "+gruppoLoggato+" non abilitato per eseguire questa operazione!");
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public void checkAbilitazioneFor(Ruoli... ruoliAmmessi) throws CustomOperationToAdviceException {
		log.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		Ruoli ruoloLoggato = userUtil.getRuolo();
		
		for (Ruoli i : ruoliAmmessi) {
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
	private  boolean okAbilitazione(String codiceGruppo) {
		boolean ret=false;
		String[] parts=codiceGruppo.split("_");
		try {
			if(codiceGruppo.equals(Gruppi.ADMIN.toString()) || parts.length<3) {
				return false;
			}
			//verifico che sia presente nelle organizzazioni ammesse
			ret=List.of(Gruppi.tipoOrganizzazioniAbilitazione())
					.stream()
					.map(el->{return el.toString().split("_")[0];})
					.filter(el->el.equals(parts[0])).findAny().isPresent();
			if(!ret) return false;
			//verifico che sia ancora attivo
			//this.checkGruppo(codiceGruppo);
			ret=true;
		} catch (Exception e) {
			log.info("gruppo non valido: "+codiceGruppo,e);
			ret=false;
		}
		return ret;
	}
	
	private String withoutRole(String codiceGruppo) {
		String[] parts=codiceGruppo.split("_");
		if(parts.length<3) return "";
		return parts[0]+'_'+parts[1];
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", propagation=Propagation.SUPPORTS, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public List<PlainStringValueLabel> getGruppiOrganizzazioni() throws Exception {
		List<PlainStringValueLabel> ret=new ArrayList<>();
		ResponseBase<GruppoBean[]> response = profileManager.getGruppiUtente(LogUtil.getAuthorization(), props.getCodiceApplicazioneProfile(),"");
		GruppoBean[] gruppi = response.getPayload();
		//lista di codici gruppo troncati della parte ruolo es. ED_46
		List<String> idsOrganizzazioniPM = 
				Arrays.asList(gruppi)
				.stream()
				.filter(gruppo->this.okAbilitazione(gruppo.getCodiceGruppo()))
				.map(gruppo->this.withoutRole(gruppo.getCodiceGruppo()))
				.collect(Collectors.toList());
		List<PlainStringValueLabel> organizzazioniAttive=remoteSchemaService.getOrganizzazioniInfoValidePerApplicazione(props.getCodiceApplicazioneProfile(), idsOrganizzazioniPM);
		for(GruppoBean gruppoPM:gruppi) {
			//per ogni gruppo verifico che il prefisso a meno del ruolo sia attivo...es. ED_43 presente in organizzazioniAttive
			for(PlainStringValueLabel org : organizzazioniAttive) {
				if(org.getValue().equals(this.withoutRole(gruppoPM.getCodiceGruppo()))) {
					PlainStringValueLabel item = new PlainStringValueLabel(gruppoPM.getCodiceGruppo(), org.getDescription());
					ret.add(item);
					break;
				}
			}
		}
		return ret;
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", propagation=Propagation.SUPPORTS, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public List<PlainStringValueLabel> getGruppiOrganizzazioniConCL() throws Exception {
		List<PlainStringValueLabel> ret=new ArrayList<>();
		ResponseBase<GruppoBean[]> response = profileManager.getGruppiUtente(LogUtil.getAuthorization(), props.getCodiceApplicazioneProfile(),"");
		GruppoBean[] gruppi = response.getPayload();
		//lista di codici gruppo troncati della parte ruolo es. ED_46
		List<String> idsOrganizzazioniPM = 
				Arrays.asList(gruppi)
				.stream()
				.filter(gruppo->this.okAbilitazione(gruppo.getCodiceGruppo()))
				.map(gruppo->this.withoutRole(gruppo.getCodiceGruppo()))
				.collect(Collectors.toList());
		List<PlainStringValueLabel> organizzazioniAttive=remoteSchemaService.getOrganizzazioniInfoValidePerApplicazioneConCL(props.getCodiceApplicazioneProfile(), idsOrganizzazioniPM);
		for(GruppoBean gruppoPM:gruppi) {
			//per ogni gruppo verifico che il prefisso a meno del ruolo sia attivo...es. ED_43 presente in organizzazioniAttive
			for(PlainStringValueLabel org : organizzazioniAttive) {
				if(org.getValue().equals(this.withoutRole(gruppoPM.getCodiceGruppo()))) {
					PlainStringValueLabel item = new PlainStringValueLabel(gruppoPM.getCodiceGruppo(), org.getDescription());
					ret.add(item);
					break;
				}
			}
		}
		return ret;
	}
	
	@Override
	@Deprecated
	@Transactional(transactionManager="txmng_common", propagation=Propagation.SUPPORTS, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public UtenteGruppo[] getAdministratorsProfile() throws Exception {
		UtenteGruppo[] infoUtenti=null;
		String authorization=clientSvc.getBatchUser().getAuthorization();
		String oldAuth=LogUtil.getAuthorization();
		try {
			LogUtil.addAuthorization(authorization);
			//in modo che venga aggiunto nell'header dall'FeignRequestInterceptor	
			infoUtenti = profileManager.getUtentiGruppoOrdered(authorization, props.getCodiceApplicazioneProfile(), Gruppi.ADMIN.name(), null, true, "cognomeUtente","ASC").getPayload();
		} catch(Throwable e) {
			throw e;
		} finally {
			LogUtil.addAuthorization(oldAuth);
		}
		return infoUtenti;
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", propagation=Propagation.SUPPORTS, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public AssUtenteGruppo[] getAdministratorsProfilePM() throws Exception 
	{
		AssUtenteGruppo[] infoUtenti = null;
		String authorization = clientSvc.getBatchUser().getAuthorization();
		String oldAuth = LogUtil.getAuthorization();
		try 
		{
			LogUtil.addAuthorization(authorization);
			//in modo che venga aggiunto nell'header dall'FeignRequestInterceptor	
			infoUtenti = profileManager.utentiInGruppi(authorization, props.getCodiceApplicazioneProfile(), Arrays.asList(Gruppi.ADMIN.name())).getPayload();
		} 
		catch(Throwable e) 
		{
			throw e;
		} 
		finally 
		{
			LogUtil.addAuthorization(oldAuth);
		}
		return infoUtenti;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Deprecated
	@Transactional(transactionManager="txmng_common", propagation=Propagation.SUPPORTS, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public UtenteGruppo getUtenteProfiles(Gruppi gruppo,String username) throws Exception {
		UtenteGruppo[] infoUtenti=null;
		UtenteGruppo infoUtente=null;
		String authorization=clientSvc.getBatchUser().getAuthorization();
		String oldAuth=LogUtil.getAuthorization();
		try {
			LogUtil.addAuthorization(authorization);
			//in modo che venga aggiunto nell'header dall'FeignRequestInterceptor	
			infoUtenti = profileManager.getUtentiGruppoOrdered(authorization, props.getCodiceApplicazioneProfile(), gruppo.name(), null, true, "cognomeUtente","ASC").getPayload();
			if(infoUtenti.length>0) {
				for(int i=0;i<infoUtenti.length;i++) {
					if(infoUtenti[i].getUsernameUtente().equals(username)){
						infoUtente=infoUtenti[i];
						break;
					}
				}
			}
		} catch(Throwable e) {
			throw e;
		} finally {
			LogUtil.addAuthorization(oldAuth);
		}
		return infoUtente;
	}	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(transactionManager="txmng_common", propagation=Propagation.SUPPORTS, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public AssUtenteGruppo getUtenteProfilesPM(Gruppi gruppo, String username) throws Exception {
		AssUtenteGruppo[] infoUtenti=null;
		AssUtenteGruppo infoUtente=null;
		String authorization=clientSvc.getBatchUser().getAuthorization();
		String oldAuth=LogUtil.getAuthorization();
		try {
			LogUtil.addAuthorization(authorization);
			//in modo che venga aggiunto nell'header dall'FeignRequestInterceptor	
			infoUtenti = profileManager.utentiInGruppi(authorization, props.getCodiceApplicazioneProfile(), Arrays.asList(gruppo.name())).getPayload();
			if(infoUtenti.length>0) {
				for(int i=0;i<infoUtenti.length;i++) {
					if(infoUtenti[i].getUsername().equals(username)){
						infoUtente=infoUtenti[i];
						break;
					}
				}
			}
		} catch(Throwable e) {
			throw e;
		} finally {
			LogUtil.addAuthorization(oldAuth);
		}
		return infoUtente;
	}	

	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public boolean isValidRup() throws Exception
	{
		return rupRepository.validRup(LogUtil.getUser(), new Date(), userUtil.getIntegerId());
	}
	
	
	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Object execWithbatchToken(Function<String,?> performCall) throws Exception {
		String authorization=clientSvc.getBatchUser().getAuthorization();
		String oldAuth=LogUtil.getAuthorization();
		Object ret=null;
		try {
			LogUtil.addAuthorization(authorization);
			//in modo che venga aggiunto nell'header dall'FeignRequestInterceptor
			ret=performCall.apply(authorization);
			return ret;
		} catch(Throwable e) {
			throw e;
		} finally {
			LogUtil.addAuthorization(oldAuth);
		}
	}
	
	@Override
	@Transactional(transactionManager = "txmng_common", readOnly = false, rollbackFor = Exception.class)
	public void upsertInfoUser() throws Exception
	{
//		final ProfileUserResponseBean user = userUtil.getMyProfile();
		final UserDetail user = SecurityUtil.getUserDetail();
		UtenteDTO utente=null;
		try {
			utente= this.userDao.findByUsername(user.getUsername());
		}catch (EmptyResultDataAccessException e) {
			utente=new UtenteDTO();
			utente.setUsername(user.getUsername());
			utente.setDataRichiestaRegistrazione(new Timestamp(System.currentTimeMillis()));
			utente.setCf(user.getFiscalCode());
			utente.setNome(user.getFirstName());
			utente.setCognome(user.getLastName());
			long idUtente = this.userDao.insert(utente);
			utente.setId((int)idUtente);
		}
		UtenteAttributeDTO utenteAtt = this.userAttDao.findByUsername(user.getUsername());
		if(utenteAtt == null) {
			final ProfileUserResponseBean profile = userUtil.getMyProfile();
			utenteAtt = new UtenteAttributeDTO();
			utenteAtt.setApplicazioneId(this.appDao.getIdByCodice(props.getCodiceApplicazione()));
			utenteAtt.setUtenteId(utente.getId());
			utenteAtt.setPec(profile.getPec());
			utenteAtt.setMail(profile.getEmail());
			utenteAtt.setBirthDate(SecurityUtil.getUserDetail().getBirthDate());
			utenteAtt.setBirthPlace(SecurityUtil.getUserDetail().getBirthPlace());
			utenteAtt.setPhoneNumber(SecurityUtil.getUserDetail().getPhone());
			this.userAttDao.insert(utenteAtt);
		}
	}
	
	
	@Override
	@Transactional(transactionManager = "txmng_common", readOnly = false, rollbackFor = Exception.class)
	public void updateUtenteAttribute(final UtenteAttributeBean bean) throws Exception {
		final UtenteDTO utente = this.userDao.findByUsername(SecurityUtil.getUsername());
		final UtenteAttributeDTO utenteAtt = new UtenteAttributeDTO();
		utenteAtt.setApplicazioneId(this.appDao.getIdByCodice(props.getCodiceApplicazione()));
		utenteAtt.setUtenteId(utente.getId());
		utenteAtt.setPec(bean.getPec());
		utenteAtt.setMail(bean.getMail());
		utenteAtt.setPhoneNumber(bean.getPhoneNumber());
		this.userAttDao.update(utenteAtt);
	}

	
}
