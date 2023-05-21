package it.eng.tz.puglia.servizi_esterni.remote.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hsqldb.lib.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.config.AutpaeConstants;
import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dto.SelectOptionDto;
import it.eng.tz.puglia.autpae.dto.TipologicaDTO;
import it.eng.tz.puglia.autpae.dto.BE_to_FE.EnteBean;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.enumeratori.TipoOrganizzazioneSpecifica;
import it.eng.tz.puglia.autpae.enumeratori.TipoPaesaggioOrganizzazione;
import it.eng.tz.puglia.autpae.generated.orm.dto.ApplicazioneDTO;
import it.eng.tz.puglia.autpae.generated.orm.dto.PaesaggioOrganizzazioneDTO;
import it.eng.tz.puglia.autpae.generated.orm.dto.UtenteAttributeDTO;
import it.eng.tz.puglia.autpae.generated.orm.dto.UtenteDTO;
import it.eng.tz.puglia.autpae.generated.orm.repository.ApplicazioneRepository;
import it.eng.tz.puglia.autpae.generated.orm.repository.PaesaggioOrganizzazioneRepository;
import it.eng.tz.puglia.autpae.generated.orm.repository.UtenteAttributeRepository;
import it.eng.tz.puglia.autpae.generated.orm.repository.UtenteRepository;
import it.eng.tz.puglia.autpae.generated.orm.search.ApplicazioneSearch;
import it.eng.tz.puglia.autpae.generated.orm.search.UtenteAttributeSearch;
import it.eng.tz.puglia.autpae.generated.orm.search.UtenteSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.cache.ICacheConstants;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.UserUtil;
import it.eng.tz.puglia.servizi_esterni.remote.dto.CodiceFascicoloDTO;
import it.eng.tz.puglia.servizi_esterni.remote.dto.EnteDTO;
import it.eng.tz.puglia.servizi_esterni.remote.dto.EnteWIstatDTO;
import it.eng.tz.puglia.servizi_esterni.remote.dto.PaesaggioEmailDTO;
import it.eng.tz.puglia.servizi_esterni.remote.dto.RubricaEnteDTO;
import it.eng.tz.puglia.servizi_esterni.remote.dto.RubricaIstituzionaleDTO;
import it.eng.tz.puglia.servizi_esterni.remote.dto.pk.CodiceFascicoloPK;
import it.eng.tz.puglia.servizi_esterni.remote.repository.CommonRepository;
import it.eng.tz.puglia.servizi_esterni.remote.search.CodiceFascicoloSearch;
import it.eng.tz.puglia.servizi_esterni.remote.search.PaesaggioEmailSearch;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.CommonService;
import it.eng.tz.puglia.servizi_esterni.remote.utility.TipoEnte;
import it.eng.tz.puglia.util.list.ListUtil;

@Service
public class CommonServiceImpl implements CommonService {

	private static final Logger log = LoggerFactory.getLogger(CommonServiceImpl.class);

	@Autowired private UserUtil userUtil;
	@Autowired private ApplicationProperties props;
	
	@Autowired private CommonRepository repository;
	@Autowired private ApplicazioneRepository appRepo;
	@Autowired private UtenteRepository utenteRepo;
	@Autowired private UtenteAttributeRepository utenteAttributeRepo;
	@Autowired private PaesaggioOrganizzazioneRepository paesaggioOrgDao;
	
	
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<String>   selectCodiciEntiComuniInProvince(Collection<String> codiciProvince) throws Exception 	{
		return repository.selectCodiciEntiComuniInProvince(					  codiciProvince);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public void 	checkPermessoOrganizzazioneApplicazione(int idOrganizzazione, String codiceApplicazione) throws Exception {
		 repository.checkPermessoOrganizzazioneApplicazione(	idOrganizzazione, 		 codiceApplicazione);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public void    checkValiditaOrganizzazione(int idOrganizzazione) throws Exception {
		repository.checkValiditaOrganizzazione(	   idOrganizzazione);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public String 	 	  getTipoOrganizzazione(int idOrganizzazione) {
		return repository.getTipoOrganizzazione(	idOrganizzazione);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public void    checkValiditaDelegaOrganizzazione(int idOrganizzazione) throws Exception {
		repository.checkValiditaDelegaOrganizzazione(	 idOrganizzazione);		
	}

/*	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public Integer 		  getRiferimentoOrganizzazione(int idOrganizzazione) throws Exception {
		return repository.getRiferimentoOrganizzazione(	   idOrganizzazione);
	}	*/
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<String>   getAllCodiciEntiCOMUNI() throws Exception {
		return repository.getAllCodiciEntiCOMUNI();
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<Integer>  selectEntiComuniInProvincia(int idProvincia) throws Exception {
		return repository.selectEntiComuniInProvincia(	 idProvincia);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public String 		  getCodiceEnte(int idEnte) throws Exception {
		return repository.getCodiceEnte(	idEnte);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public String 		  getTipoEnte(int idEnte) throws Exception {
		return repository.getTipoEnte(	  idEnte);
	}

	//@Override
	@Deprecated
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public Integer 		  findEnteSoprintendenzaByProvincia(int idProvincia) throws Exception {
		return repository.findEnteSoprintendenzaByProvincia(	idProvincia);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<Integer>  selectProvinceByCodiciComuni(List<String> codici) throws Exception {
		return repository.selectProvinceByCodiciComuni(				codici);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<TipologicaDTO> selectInfoEntiByCodici(List<String> codici) throws Exception {
		return repository.	   selectInfoEntiByCodici(			   codici);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public EnteDTO 		  findEnteById(int idEnte) throws Exception {
		return repository.findEnteById(	   idEnte);
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public EnteWIstatDTO 		  findEnteWIstatById(int idEnte) throws Exception {
		return repository.findEnteWIstatById(	   idEnte);
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<EnteWIstatDTO> 		  selectEnteWIstatById(List<Integer> idEnti) throws Exception {
		return repository.selectEnteWIstatById(	   idEnti);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public EnteDTO 		  findEnteByCodice(String codice) throws Exception {
		return repository.findEnteByCodice(		  codice);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public String 		  getCodiceCiviliaByIdOrganizzazione(int idOrganizzazione) throws Exception {
		return repository.getCodiceCiviliaByIdOrganizzazione(	 idOrganizzazione);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<Integer>  selectProvinceByIdComuni(Collection<Long> idEntiComuni) throws Exception {
		return repository.selectProvinceByIdComuni(					idEntiComuni);
	}

/*	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public EnteDTO 		  findEnteByIdOrganizzazione(int idOrganizzazione) throws Exception {
		return repository.findEnteByIdOrganizzazione(	 idOrganizzazione);
	}	*/

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	@Cacheable(keyGenerator = ICacheConstants.KEY_CONTEXT_NAME, value = "it.eng.tz.puglia.servizi_esterni.remote.service.CommonServiceImpl.getAllEntiCOMUNI", unless = "#result == null")
	public List<Integer>  getAllEntiCOMUNI() throws Exception {
		return repository.getAllEntiCOMUNI();
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<Integer>  getOrganizzazioniValidePerApplicazione(String codiceApplicazione) throws Exception {
		return repository.getOrganizzazioniValidePerApplicazione(		codiceApplicazione);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=false, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public long 		  insertRubricaEnte(RubricaEnteDTO info) throws Exception {
		return repository.insertRubricaEnte(			   info) ;
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=false, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public int 			  updateRubricaEnte(RubricaEnteDTO info) throws Exception {
		return repository.updateRubricaEnte(			   info);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=false, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public int 			  deleteRubricaEnte(RubricaEnteDTO info) throws Exception {
		return repository.deleteRubricaEnte(			   info);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=false, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public int 			  updateRubricaIstituzionale(RubricaIstituzionaleDTO info) throws Exception {
		return repository.updateRubricaIstituzionale(						 info);
	}
	
	/**
	 * 
	 * @param codiceApplicazione
	 * @param idsOrganizzazioni idOrganizzazioni provenienti dal PM (fanno da filtro)
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<TipologicaDTO> getOrganizzazioniInfoValidePerApplicazione(String codiceApplicazione, List<String> idsOrganizzazioni) throws Exception {
		return repository.	   getOrganizzazioniInfoValidePerApplicazione(		 codiceApplicazione, 			  idsOrganizzazioni);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public int 			  getIdApplicazione(String codiceApplicazione) throws Exception {
		return repository.getIdApplicazione(	   codiceApplicazione);
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<Integer>  getOrganizzazioniCompetentiOnEnti(Collection<Integer> idEnti, String codiceApplicazione, Gruppi tipoOrganizzazione, TipoOrganizzazioneSpecifica tipoOrganizzazioneSpecifica) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		// prima di avvire la query, devo inserire anche le PROVINCE a cui appartengono i comuni, e REGIONE
		Set<Long> idEntiLong = new HashSet<>();
		idEnti.forEach(idEnteInt->{
			idEntiLong.add(Long.valueOf(idEnteInt));
		});
		
		Set<Integer> enti = new HashSet<>();
		enti.addAll(idEnti);
		enti.addAll(this.selectProvinceByIdComuni(idEntiLong));
		enti.add(this.findRegione().getId());
		
		return repository.getOrganizzazioniCompetentiOnEnti(enti, codiceApplicazione, tipoOrganizzazione, tipoOrganizzazioneSpecifica);
	}

/*	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<Integer>  getAllEnti() throws Exception {
		return repository.getAllEnti();
	}	*/

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<Integer>  getOrganizzazioniValidePerApplicazione(String codiceApplicazione, List<Integer> idsOrganizzazioni) throws Exception {
		return repository.getOrganizzazioniValidePerApplicazione(		codiceApplicazione, 			  idsOrganizzazioni);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	@Cacheable(keyGenerator = ICacheConstants.KEY_CONTEXT_NAME, value = "EntiCOMUNIdiCompetenzaForOrganizzazione", unless = "#result == null")
	public List<Integer>    getEntiCOMUNIdiCompetenzaForOrganizzazione(int idOrganizzazione) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		List<Integer> enti = this.getEntiDiCompetenzaForOrganizzazione(idOrganizzazione);
		Set <Integer> comuni = new HashSet<Integer>();
		
		for (int ente : enti) {
			String tipo = this.getTipoEnte(ente);

			if (tipo.equals("R")) {
				comuni.addAll(this.getAllEntiCOMUNI());
			}
			else if (tipo.equals("P")) {
				comuni.addAll(this.selectEntiComuniInProvincia(ente));
			}
			else if (tipo.equals("CO")) {
				comuni.add(ente);
			}
		}
		return new ArrayList<Integer>(comuni);
	}

	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.MANDATORY, timeout=60000)
	private List<Integer> getEntiDiCompetenzaForOrganizzazione(int idOrganizzazione) throws Exception {
		return repository.getEntiDiCompetenzaForOrganizzazione(	   idOrganizzazione);
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public EnteDTO  	  findRegione() throws Exception {
		return repository.findRegione();
	}
	

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<EnteDTO>  selectEntiById(Collection<Integer> idEnti) throws Exception {
		return repository.selectEntiById(					 idEnti);
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public String getConfigurationValue(String key,String applicationCode) throws Exception {
		return repository.getConfigurationValue(key, applicationCode);
	}
	
	
	private boolean isOrganizzazioneRegione(EnteDTO enteRegione,PaesaggioOrganizzazioneDTO orgDTO) {
		return enteRegione!=null && orgDTO!=null && 
				enteRegione.getId()!=null && orgDTO.getEnteId()!=null && 
				enteRegione.getId().equals(orgDTO.getEnteId());
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=false, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public String generaCodiceFascicolo() throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		Integer idPaeOrg = userUtil.getIntegerId();
		//String tipoOrganizzazione=this.repository.getTipoOrganizzazione(idPaeOrg);
		//String codiceEnte = this.getCodiceCiviliaByIdOrganizzazione(idPaeOrg);
		PaesaggioOrganizzazioneDTO paeOrgDto=new PaesaggioOrganizzazioneDTO();
		paeOrgDto.setId(idPaeOrg);
		paeOrgDto=this.paesaggioOrgDao.find(paeOrgDto);
		EnteDTO enteRegione = this.repository.findRegione(); 
		
		if (StringUtil.isEmpty(paeOrgDto.getCodiceCivilia())) {
			throw new CustomOperationToAdviceException("Errore. L'ente "+userUtil.getGruppo_Id()+" non possiede un codice generatore!");
		}
		
		int annoCorrente = Calendar.getInstance().get(Calendar.YEAR);

		String prefisso = null;
		if (isOrganizzazioneRegione(enteRegione, paeOrgDto)) {
			prefisso = AutpaeConstants.PREFISSO_CODICE_REGIONE; //per tutti anche per i pareri
		} else if (props.isAutPae()) {
			prefisso = AutpaeConstants.PREFISSO_CODICE_AP;
		} else if (props.isPareri()) {
			prefisso = AutpaeConstants.PREFISSO_CODICE_PP;
		} else if (props.isPutt()) {
			prefisso = AutpaeConstants.PREFISSO_CODICE_AP;
		} else {
			throw new CustomOperationToAdviceException("Errore nella generazione del codice fascicolo, non è previsto alcun prefisso !!!");
		}	 
		 
		int seriale = 0;
		
		if (repository. countCodiceFascicolo(new CodiceFascicoloSearch(paeOrgDto.getCodiceCivilia(), annoCorrente, prefisso, null   )) == 0) {
			seriale = 1;
			repository.insertCodiceFascicolo(new CodiceFascicoloDTO   (paeOrgDto.getCodiceCivilia(), annoCorrente, prefisso, seriale));
		}
		else {
			seriale = repository.  findCodiceFascicolo(new CodiceFascicoloPK (paeOrgDto.getCodiceCivilia(), annoCorrente, prefisso)).getSeriale()+1;
					  repository.updateCodiceFascicolo(new CodiceFascicoloDTO(paeOrgDto.getCodiceCivilia(), annoCorrente, prefisso,     seriale));
		}
		/**
		 * per regione il codice è AUTPAESAF-NN-AAAAA
		 */	 
		if(isOrganizzazioneRegione(enteRegione, paeOrgDto)){
			return prefisso+"-"+seriale+"-"+annoCorrente;
		}else {
			return prefisso+paeOrgDto.getCodiceCivilia()+"-"+seriale+"-"+annoCorrente;	
		}
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=false, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public void updateProgressiviCodiceFascicolo(String codiceEnteDelegato,String codiceIstatEnte) throws Exception {
		String[] tokens=codiceEnteDelegato.split("-");
		String prefisso="";
		Integer anno;
		String istatEnte="";
		Integer seriale;
		anno=Integer.parseInt(tokens[2]);
		seriale=Integer.parseInt(tokens[1]);
		if(tokens[0].startsWith(AutpaeConstants.PREFISSO_CODICE_AP)) {
			prefisso=AutpaeConstants.PREFISSO_CODICE_AP;
			istatEnte=tokens[0].substring(2);
		}else if(tokens[0].startsWith(AutpaeConstants.PREFISSO_CODICE_REGIONE)) {
			prefisso=AutpaeConstants.PREFISSO_CODICE_AP;
			istatEnte=codiceIstatEnte;
		}else if(tokens[0].startsWith(AutpaeConstants.PREFISSO_CODICE_PP)){
			prefisso=AutpaeConstants.PREFISSO_CODICE_PP;
			istatEnte=tokens[0].substring(2);
		}
		if(StringUtil.isEmpty(prefisso)||StringUtil.isEmpty(istatEnte)||anno==null){
			throw new Exception("Errore nell'aggiornamento dei progressivi codice : " +codiceEnteDelegato+" codiceIstatEnte Caso Regione:"+codiceIstatEnte);
		} 
		try {
			CodiceFascicoloDTO entity = this.repository.findCodiceFascicolo(new CodiceFascicoloPK(istatEnte, anno, prefisso));
			if(entity.getSeriale()<seriale) {
				entity.setSeriale(seriale);
				this.repository.updateCodiceFascicolo(entity);
			}
		}catch(Exception e) {
			CodiceFascicoloDTO entity = new CodiceFascicoloDTO(istatEnte, anno, prefisso, seriale);
			this.repository.insertCodiceFascicolo(entity);
		}
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public long countRubricaPaesaggio(PaesaggioEmailSearch filter) throws Exception {
		return repository.countRubricaPaesaggio(filter);
	}
	
/*	@Override
	@Transactional(transactionManager="txmng_common", readOnly=false, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public long insertRubricaPaesaggio(PaesaggioEmailDTO entity) throws Exception {
		return repository.insertRubricaPaesaggio(entity);
	}	*/
	
/*	@Override
	@Transactional(transactionManager="txmng_common", readOnly=false, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public long deleteRubricaPaesaggio(PaesaggioEmailSearch filter) throws Exception {
		return repository.deleteRubricaPaesaggio(filter);
	}	*/
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<PaesaggioEmailDTO> searchRubricaPaesaggio(PaesaggioEmailSearch search) throws Exception {
		search.setCodiceApplicazione(props.getCodiceApplicazioneProfile());
		return repository.searchRubricaPaesaggio(search);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public String 		  getDenominazioneOrganizzazione(int idOrganizzazione) {
		return repository.getDenominazioneOrganizzazione(	 idOrganizzazione);
	}

	@Override
	public TipologicaDTO  searchAutomatizzataRubricaIstituzionale(int idEnte, int idApplicazione) throws Exception {
		return repository.searchAutomatizzataRubricaIstituzionale(	  idEnte, 	  idApplicazione);
	}

	@Override
	public Integer getEnteDiRiferimentoForOrganizzazione(int idOrganizzazione) throws Exception {
		return repository.getEnteDiRiferimentoForOrganizzazione(idOrganizzazione);
	}

	@Override
	public Integer findProvinciaForComune(int idEnteComune) throws Exception {
		return repository.findProvinciaForComune(idEnteComune);
	}

	@Override
	public List<Integer>  findSoprintendenzeByDenominazione(String nomeProv) throws Exception {
		return repository.findSoprintendenzeByDenominazione(	   nomeProv);
	}
	
	
/*	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public int getPaesaggioOrganizzazioneCompetenzeId(int idEnte, int idOrganizzazione) throws Exception {
		return repository.getPaesaggioOrganizzazioneCompetenzeId(idEnte, idOrganizzazione);
	}	*/
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<SelectOptionDto> getSezioniCatastali() {
		return repository.getSezioniCatastali();
	}
	
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=false , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public void aggiornaUtenteCommon(Long idRichiestaAbilitazione,String codiceApplicazione,String username,String email, String telefono, String fax) throws Exception {
		ApplicazioneSearch aSearch=new ApplicazioneSearch();
		aSearch.setCodice(codiceApplicazione);
		PaginatedList<ApplicazioneDTO> app = this.appRepo.search(aSearch);
		ApplicazioneDTO appDto=null;
		if(app!=null && app.getList()!=null && ListUtil.isNotEmpty(app.getList())) {
			appDto = app.getList().get(0);
		}else {
			throw new Exception("Applicazione non trovata in common.applicazione con codice "+codiceApplicazione);
		}
		UtenteSearch utSearch=new UtenteSearch();
		utSearch.setUsername(username);
		PaginatedList<UtenteDTO> users = this.utenteRepo.search(utSearch);
		if(users!=null && users.getCount()>0) {
			//utente esistente
			UtenteDTO utente=users.getList().get(0);
			updateUtenteAttribute(idRichiestaAbilitazione, appDto, utente,email,  telefono,  fax);
		}else {
			//utente inesistente
			UtenteDTO ut=new UtenteDTO();
			ut.setUsername(username);
			ut.setDataRichiestaRegistrazione(new Timestamp(System.currentTimeMillis()));
			long idUtente = this.utenteRepo.insert(ut);
			ut.setId((int)idUtente);
			updateUtenteAttribute(idRichiestaAbilitazione, appDto, ut, email,  telefono,  fax);
		}
	}

	private void updateUtenteAttribute(Long idRichiestaAbilitazione, ApplicazioneDTO appDto, UtenteDTO utente,String email, String telefono, String fax) {
		UtenteAttributeSearch utAttSearch=new UtenteAttributeSearch();
		utAttSearch.setApplicazioneId(appDto.getId()+"");
		utAttSearch.setUtenteId(utente.getId()+"");
		PaginatedList<UtenteAttributeDTO> utAttrs = this.utenteAttributeRepo.search(utAttSearch);
		if(utAttrs!=null && ListUtil.isNotEmpty(utAttrs.getList())){
			//ha attributi
			UtenteAttributeDTO utAttr = utAttrs.getList().get(0);
			utAttr.setLastRichiestaAbilitazione(idRichiestaAbilitazione);
			utAttr.setPhoneNumber(telefono);
			utAttr.setMobileNumber(fax);
			utAttr.setMail(email);
			this.utenteAttributeRepo.update(utAttr);
		}else {
			//non ha attributi
			UtenteAttributeDTO utAttr = new UtenteAttributeDTO();
			utAttr.setApplicazioneId(appDto.getId());
			utAttr.setUtenteId(utente.getId());
			utAttr.setLastRichiestaAbilitazione(idRichiestaAbilitazione);
			utAttr.setPhoneNumber(telefono);
			utAttr.setMobileNumber(fax);
			utAttr.setMail(email);
			this.utenteAttributeRepo.insert(utAttr);
		}
	}
	
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public Long getLastIdRichiestaAbilitazione(String codiceApplicazione,String username) throws Exception {
		Long ret=null;
		 UtenteAttributeDTO attr = this.getLastUtenteAttribute(codiceApplicazione, username);
		if(attr!=null) {
			ret=attr.getLastRichiestaAbilitazione();
		}
		return ret;
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public UtenteAttributeDTO getLastUtenteAttribute(String codiceApplicazione,String username) throws Exception {
		UtenteAttributeDTO ret=null;
		ApplicazioneSearch aSearch=new ApplicazioneSearch();
		aSearch.setCodice(codiceApplicazione);
		PaginatedList<ApplicazioneDTO> app = this.appRepo.search(aSearch);
		ApplicazioneDTO appDto=null;
		if(app!=null && app.getList()!=null && ListUtil.isNotEmpty(app.getList())) {
			appDto = app.getList().get(0);
		}else {
			throw new Exception("Applicazione non trovata in common.applicazione con codice "+codiceApplicazione);
		}
		UtenteSearch utSearch=new UtenteSearch();
		utSearch.setUsername(username);
		PaginatedList<UtenteDTO> users = this.utenteRepo.search(utSearch);
		if(users!=null && users.getCount()>0) {
			UtenteDTO utente=users.getList().get(0);
			UtenteAttributeSearch utAttSearch=new UtenteAttributeSearch();
			utAttSearch.setApplicazioneId(appDto.getId()+"");
			utAttSearch.setUtenteId(utente.getId()+"");
			PaginatedList<UtenteAttributeDTO> utAttrs = this.utenteAttributeRepo.search(utAttSearch);
			if(utAttrs!=null && ListUtil.isNotEmpty(utAttrs.getList())){
				//ha attributi
				ret=utAttrs.getList().get(0);
			}	
		}
		return ret;
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public Integer findEnteDelegatoFromCodiceCivilia(String istatAmm,Gruppi tipo) {
		return this.repository.findEnteDelegatoFromCodiceCivilia( istatAmm, tipo);
	}

	@Override
	public List<EnteDTO> getAllEnti() throws Exception {
		return this.repository.getAllEntiData();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<EnteBean> findAllEntiBeTipoOrganizzazione(TipoPaesaggioOrganizzazione tipoOrganizzazione) throws Exception
	{
		return repository.getEntiByTipoOrganizzazione(tipoOrganizzazione);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<EnteBean> findAllEntiBeTipo(List<TipoEnte> tipi) throws Exception
	{
		return repository.getEntiByTipo(tipi);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public boolean isRegione(Integer idOrganizzazione) {
		boolean ret=false;
		PaesaggioOrganizzazioneDTO pk=new PaesaggioOrganizzazioneDTO();
		pk.setId(idOrganizzazione);
		PaesaggioOrganizzazioneDTO paesaggioOrg = paesaggioOrgDao.find(pk);
		Integer rifEnteCommon = paesaggioOrg.getEnteId();
		EnteDTO enteCommon=null;
		if(rifEnteCommon!=null) {
			try {
				enteCommon = repository.findEnteById(rifEnteCommon);
			} catch (Exception e) {
				log.error("Errore nella findEnteById: "+rifEnteCommon,e);
			}	
		}
		if(enteCommon!=null && enteCommon.getTipo().compareTo(TipoEnte.regione)==0) {
			ret=true;
		}
		return ret;
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<Integer> 	getOrganizzazioniValidePerApplicazioneByIdsEnte(String codiceApplicazione, List<Integer> idsEnteCommon) throws Exception {
		return repository.getOrganizzazioniValidePerApplicazioneByIdsEnte(codiceApplicazione,idsEnteCommon);
	}
}