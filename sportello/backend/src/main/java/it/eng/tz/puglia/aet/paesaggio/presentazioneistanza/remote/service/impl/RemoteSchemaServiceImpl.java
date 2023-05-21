package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.dto.ApplicazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.dto.UtenteAttributeDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.dto.UtenteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.repository.ApplicazioneRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.repository.UtenteAttributeRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.repository.UtenteRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.search.ApplicazioneSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.search.UtenteAttributeSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.search.UtenteSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.RubricaSearchDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.TipologicaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainNumberValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.SelectOptionDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoOrganizzazioneSpecifica;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ComuniCompetenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinatarioDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ConfigurazioniEnteRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.SezioneCatastaleSearchDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.AnagraficaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.EnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.PaesaggioEmailDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.PaesaggioOrganizzazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.RubricaEnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.RubricaIstituzionaleDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.search.PaesaggioEmailSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.repository.AnagraficaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.repository.CommonRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.repository.RemoteRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.service.RemoteSchemaService;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.bean.UserDetail;
import it.eng.tz.puglia.cache.ICacheConstants;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.string.StringUtil;


@Service
public class RemoteSchemaServiceImpl implements RemoteSchemaService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RemoteSchemaServiceImpl.class);
	
	@Autowired
	private RemoteRepository<EnteDTO> remoteCommonRepository;

	@Autowired
	private AnagraficaRepository anagraficaRepository;
	
	@Autowired
	private CommonRepository commonRepository;
	
	
	@Autowired private ApplicazioneRepository appRepo;
	@Autowired private UtenteRepository utenteRepo;
	@Autowired private UtenteAttributeRepository utenteAttributeRepo;
	@Autowired private ConfigurazioniEnteRepository confEnteRepo;
///////////////////////////////////////////////////////////////////////////////////////////////////////////////// REMOTE (Common-Ente)
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED,transactionManager="txmng_common")
	public List<EnteDTO> getAllEnti() {
		return remoteCommonRepository.selectAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED,transactionManager="txmng_common")
	public EnteDTO findEnteByCode(String code) {
		Optional<EnteDTO> result = remoteCommonRepository.find(code);
		if (result.isPresent()) {
			return result.get();
		}
		return new EnteDTO();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED)
	public List<EnteDTO> findAllEnteByCodes(List<String> codes) {
		return remoteCommonRepository.findAll(codes);
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////// ANAGRAFICA
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, transactionManager="txmng_anagrafica")
	public AnagraficaDTO findAnagraficaByCode(String code) {
		Optional<AnagraficaDTO> result = anagraficaRepository.find(code);
		if (result.isPresent()) {
			return result.get();
		}
		return new AnagraficaDTO();
	}
	
	/**
	 * {@inheritDoc}
	 * @throws Exception 
	 */
	@Override
	@Transactional(readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, transactionManager="txmng_anagrafica")
	public PlainNumberValueLabel findByCatastale(String codCatastale) throws Exception {
		return anagraficaRepository.findByCatastale(codCatastale);
	};
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, transactionManager="txmng_anagrafica")
	public String getCodiceIstatFromCatastale(String codiceCatastale) {
		return anagraficaRepository.getCodiceIstatFromCatastale(codiceCatastale);
	};
	
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, transactionManager="txmng_anagrafica")
	public List<AnagraficaDTO> findComuniLike(String pattern){
		return anagraficaRepository.findComuniPugliaLike(pattern);
	};
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, transactionManager="txmng_anagrafica")
	public List<PlainNumberValueLabel> autocompleteNazioni(String filter, int limit) throws Exception
	{
		return anagraficaRepository.autocompleteNazioni(filter, limit);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, transactionManager="txmng_anagrafica")
	public List<PlainNumberValueLabel> autocompleteProvince(String filter, int limit) throws Exception
	{
		return anagraficaRepository.autocompleteProvince(filter, limit);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, transactionManager="txmng_anagrafica")
	public List<PlainNumberValueLabel> autocompleteComuni(String filter, Integer idProvincia, Integer limit) throws Exception
	{
		return anagraficaRepository.autocompleteComuni(filter, idProvincia, limit);
	}
	
	@Override
	@Transactional(readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, transactionManager="txmng_anagrafica")
	public List<PlainStringValueLabel> findBpParchi(Set<String> codIstat) throws Exception
	{
		return anagraficaRepository.findBpParchi(codIstat);
	}
	
	@Override
	@Transactional(readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, transactionManager="txmng_anagrafica")
	public List<PlainStringValueLabel> findUcpPaesaggi(Set<String> codIstat) throws Exception
	{
		return anagraficaRepository.findUcpPaesaggi(codIstat);
	}
	
	@Override
	@Transactional(readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, transactionManager="txmng_anagrafica")
	public List<PlainStringValueLabel> findBpImmobili(Set<String> codIstat) throws Exception
	{
		return anagraficaRepository.findBpImmobili(codIstat);
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////// COMMON
		
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, transactionManager="txmng_common")
	public List<EnteDTO> findEntiProvinceLike(String patternNome){
		return commonRepository.findEntiProvinceLike(patternNome);
	}

	@Override
	@Transactional(readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, transactionManager="txmng_common")
	public List<PlainNumberValueLabel> getEntiRiceventi() {
		return commonRepository.getEntiRiceventi();
	};
	
	@Override
	@Transactional(readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, transactionManager="txmng_common")
	public List<PlainNumberValueLabel> getAllEntiRiceventi() {
		return commonRepository.getAllEntiRiceventi();
	};
	
	@Override
	@Transactional(readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, transactionManager="txmng_common")
	public List<PlainNumberValueLabel> getEnteRegione() {
		return commonRepository.getEnteRegione();
	}
	
	@Override
	@Transactional(readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, transactionManager="txmng_common")
	public List<PlainNumberValueLabel> getEntiCompetenza(int enteRicevente, Date riferimento) {
		return commonRepository.getEntiDiCompetenza(enteRicevente, riferimento);
	};
	
	@Override
	@Transactional(readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, transactionManager="txmng_common")
	public List<ComuniCompetenzaDTO> getEntiCompetenzaConCodici(int enteRicevente, Date riferimento) {
		List<ComuniCompetenzaDTO> ret = commonRepository.getEntiDiCompetenzaConCodici(enteRicevente, riferimento);
		ArrayList<ComuniCompetenzaDTO> comuniSenzaIstat = new ArrayList<ComuniCompetenzaDTO>();
		if(ret!=null && ret.size()>0) {
			for(ComuniCompetenzaDTO comune:ret) {
				//mi popolo il codice istat
				try {
					if(StringUtil.isNotBlank(comune.getCodCat())) {
						PlainNumberValueLabel anaComune = anagraficaRepository.findByCatastale(comune.getCodCat());
						comune.setCodIstat(anaComune.getDescription());	
					}else {
						comuniSenzaIstat.add(comune);//non lo tratto....
					}
				} catch (Exception e) {
					LOGGER.error("Errore nel retrieval dell'anagrafica comune dal codice catastale "+comune.getCodCat(),e);
					comuniSenzaIstat.add(comune);//non lo tratto....
				}
			}
			if(comuniSenzaIstat.size()>0) {
				comuniSenzaIstat.forEach(comToRemove->ret.remove(comToRemove));
			}
		}
		return ret;
	};
	
	@Override
	@Transactional(readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, transactionManager="txmng_common")
	public String getCodiceCatastaleFromId(long id) {
		return commonRepository.getCodiceCatastaleFromId(id);
	};
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<String>   		selectCodiciEntiComuniInProvince(Collection<String> codiciProvince) throws Exception 	{
		return commonRepository.selectCodiciEntiComuniInProvince(					codiciProvince);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public void 		 checkPermessoOrganizzazioneApplicazione(int idOrganizzazione, String codiceApplicazione) throws Exception {
		commonRepository.checkPermessoOrganizzazioneApplicazione(	 idOrganizzazione, 		  codiceApplicazione);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public void    		 checkValiditaOrganizzazione(int idOrganizzazione) throws Exception {
		commonRepository.checkValiditaOrganizzazione(	 idOrganizzazione);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public String 	 	  		getTipoOrganizzazione(int idOrganizzazione) throws Exception {
		return commonRepository.getTipoOrganizzazione(	  idOrganizzazione);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public void    		 checkValiditaDelegaOrganizzazione(int idOrganizzazione) throws Exception {
		commonRepository.checkValiditaDelegaOrganizzazione(	   idOrganizzazione);		
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public Integer 		  		getRiferimentoOrganizzazione(int idOrganizzazione) throws Exception {
		return commonRepository.getRiferimentoOrganizzazione(	 idOrganizzazione);
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<String>   		getAllCodiciEntiCOMUNI() throws Exception {
		return commonRepository.getAllCodiciEntiCOMUNI();
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<Integer>  		selectEntiComuniInProvincia(int idProvincia) throws Exception {
		return commonRepository.selectEntiComuniInProvincia(	idProvincia);
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<EnteDTO> selectEntiComuniInProvinciaDetails(int idProvincia) throws Exception
	{
		return commonRepository.selectEntiComuniInProvinciaDetails(idProvincia);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public String 		  		getCodiceEnte(int idEnte) throws Exception {
		return commonRepository.getCodiceEnte(	  idEnte);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public String 		  		getTipoEnte(int idEnte) throws Exception {
		return commonRepository.getTipoEnte(	idEnte);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public Integer 		  		findEnteSoprintendenzaByProvincia(int idProvincia) throws Exception {
		return commonRepository.findEnteSoprintendenzaByProvincia(	  idProvincia);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<Integer>  		selectProvinceByCodiciComuni(List<String> codici) throws Exception {
		return commonRepository.selectProvinceByCodiciComuni(			  codici);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<PlainStringValueLabel> selectInfoEntiByCodici(List<String> codici) throws Exception {
		return commonRepository.	   selectInfoEntiByCodici(			   codici);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public EnteDTO 		  		findEnteById(int idEnte) throws Exception {
		return commonRepository.findEnteById(	 idEnte);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public EnteDTO 		  		findEnteByCodice(String codice) throws Exception {
		return commonRepository.findEnteByCodice(		codice);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public String 		  		getCodiceCiviliaByIdOrganizzazione(int idOrganizzazione) throws Exception {
		return commonRepository.getCodiceCiviliaByIdOrganizzazione(	   idOrganizzazione);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<Integer>  		selectProvinceByIdComuni(Collection<Long> idEntiComuni) throws Exception {
		return commonRepository.selectProvinceByIdComuni(				  idEntiComuni);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public EnteDTO 		  		findEnteByIdOrganizzazione(int idOrganizzazione) throws Exception {
		return commonRepository.findEnteByIdOrganizzazione(	   idOrganizzazione);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<Integer>  		getAllEntiCOMUNI() throws Exception {
		return commonRepository.getAllEntiCOMUNI();
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<EnteDTO> getAllEntiComuniDetails() throws Exception 
	{
		return commonRepository.getAllEntiComuniDetails();
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public int 			  		getIdApplicazione(String codiceApplicazione) throws Exception {
		return commonRepository.getIdApplicazione(	     codiceApplicazione);
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<Integer> getOrganizzazioniCompetentiOnEnti(Collection<Integer> idEnti, String codiceApplicazione) throws Exception {
		
		// prima di avvire la query, devo inserire anche le PROVINCE a cui appartengono i comuni, e REGIONE
		Set<Long> idEntiLong = new HashSet<>();
		idEnti.forEach(idEnteInt->{
			idEntiLong.add(Long.valueOf(idEnteInt));
		});
		
		Set<Integer> enti = new HashSet<>();
		enti.addAll(this.selectProvinceByIdComuni(idEntiLong));
		enti.addAll(idEnti);
		enti.add(this.findRegione().getId());
		
		return commonRepository.getOrganizzazioniCompetentiOnEnti(enti, codiceApplicazione);
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<PaesaggioOrganizzazioneDTO> getOrganizzazioniCompetentiOnEntiDetails(Collection<Integer> idEnti, String codiceApplicazione) throws Exception 
	{	
		// prima di avvire la query, devo inserire anche le PROVINCE a cui appartengono i comuni, e REGIONE
		Set<Long> idEntiLong = new HashSet<>();
		idEnti.forEach(idEnteInt->{
			idEntiLong.add(Long.valueOf(idEnteInt));
		});
		
		Set<Integer> enti = new HashSet<>();
		enti.addAll(this.selectProvinceByIdComuni(idEntiLong));
		enti.addAll(idEnti);
		enti.add(this.findRegione().getId());
		
		return commonRepository.getOrganizzazioniCompetentiOnEntiDetails(enti, codiceApplicazione);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<Integer>    getEntiCOMUNIdiCompetenzaForOrganizzazione(int idOrganizzazione) throws Exception {
		List<Integer> enti = this.getEntiDiCompetenzaForOrganizzazione(	   idOrganizzazione);

		Set<Integer> comuni = new HashSet<Integer>();
		
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
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<PlainNumberValueLabel> getEntiCOMUNIdiCompetenzaForOrganizzazioneDetail(int idOrganizzazione) throws Exception 
	{
		List<EnteDTO> enti = this.getEntiDiCompetenzaForOrganizzazioneDetails(idOrganizzazione);
		Set<PlainNumberValueLabel> comuni = new HashSet<PlainNumberValueLabel>();
		for (EnteDTO ente : enti) 
		{
			String tipo = this.getTipoEnte(ente.getId().intValue());
			if (tipo.equals("R")) 
			{
				List<EnteDTO> t = this.getAllEntiComuniDetails();
				if(t != null)
					comuni.addAll(t.parallelStream().map(PlainNumberValueLabel::new).collect(Collectors.toSet()));
			}
			else if (tipo.equals("P")) 
			{
				List<EnteDTO> t = this.selectEntiComuniInProvinciaDetails(ente.getId().intValue());
				if(t != null)
					comuni.addAll(t.parallelStream().map(PlainNumberValueLabel::new).collect(Collectors.toSet()));
			}
			else if (tipo.equals("CO")) 
			{
				comuni.add(new PlainNumberValueLabel(ente));

			}
		}
		return new ArrayList<PlainNumberValueLabel>(comuni);
	}

	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.MANDATORY, timeout=60000)
	private List<Integer> 		getEntiDiCompetenzaForOrganizzazione(int idOrganizzazione) throws Exception {
		return commonRepository.getEntiDiCompetenzaForOrganizzazione(	 idOrganizzazione);
	}
	
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.MANDATORY, timeout=60000)
	private List<EnteDTO> getEntiDiCompetenzaForOrganizzazioneDetails(int idOrganizzazione) throws Exception 
	{
		return commonRepository.getEntiDiCompetenzaForOrganizzazioneDetails(idOrganizzazione);
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.MANDATORY, timeout=60000)
	public EnteDTO  	  		findRegione() throws Exception {
		return commonRepository.findRegione();
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<EnteDTO>  		selectEntiById(Collection<Integer> idEnti) throws Exception {
		return commonRepository.selectEntiById(					   idEnti);
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
	public List<PlainStringValueLabel> getOrganizzazioniInfoValidePerApplicazione(String codiceApplicazione, List<String> idsOrganizzazioni) throws Exception {
		return commonRepository.	   getOrganizzazioniInfoValidePerApplicazione(		 codiceApplicazione, 			  idsOrganizzazioni);
	}
	
	/**
	 * Comprende anche le commissioni locali
	 * @param codiceApplicazione
	 * @param idsOrganizzazioni idOrganizzazioni provenienti dal PM (fanno da filtro)
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<PlainStringValueLabel> getOrganizzazioniInfoValidePerApplicazioneConCL(String codiceApplicazione, List<String> idsOrganizzazioni) throws Exception {
		return commonRepository.	   getOrganizzazioniInfoValidePerApplicazioneConCL(		 codiceApplicazione, 			  idsOrganizzazioni);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=false, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public long 		  		insertRubricaEnte(RubricaEnteDTO info) throws Exception {
		return commonRepository.insertRubricaEnte(			     info);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=false, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public int 			  		updateRubricaEnte(RubricaEnteDTO info) throws Exception {
		return commonRepository.updateRubricaEnte(		  	  	 info);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=false, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public int 			  		deleteRubricaEnte(RubricaEnteDTO info) throws Exception {
		return commonRepository.deleteRubricaEnte(			     info);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=false, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public int 			  		updateRubricaIstituzionale(RubricaIstituzionaleDTO info) throws Exception {
		return commonRepository.updateRubricaIstituzionale(						   info);
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<Integer> getPaesaggiEntiForCommissione(Integer idCommissioneSeduta) throws Exception
	{
		return commonRepository.getEntiForCommissioneLocale(idCommissioneSeduta);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<PaesaggioEmailDTO> searchRubricaPaesaggio(PaesaggioEmailSearch search) throws Exception
	{
		return commonRepository.searchRubricaPaesaggio(search);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=false, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public long insertRubricaPaesaggio(PaesaggioEmailDTO entity) throws Exception
	{
		return commonRepository.insertRubricaPaesaggio(entity);
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=false, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public long updateRubricaPaesaggio(PaesaggioEmailDTO entity) throws Exception
	{
		return commonRepository.updateRubricaPaesaggio(entity);
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public String 		  getDenominazioneOrganizzazione(int idOrganizzazione) {
		return commonRepository.getDenominazioneOrganizzazione(	 idOrganizzazione);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<Integer>  getOrganizzazioniCompetentiOnEnti(Collection<Integer> idEnti, String codiceApplicazione, Gruppi tipoOrganizzazione, TipoOrganizzazioneSpecifica tipoOrganizzazioneSpecifica) throws Exception {
		
		// prima di avvire la query, devo inserire anche le PROVINCE a cui appartengono i comuni, e REGIONE
		Set<Long> idEntiLong = new HashSet<>();
		idEnti.forEach(idEnteInt->{
			idEntiLong.add(Long.valueOf(idEnteInt));
		});
		
		Set<Integer> enti = new HashSet<>();
		enti.addAll(idEnti);
		enti.addAll(this.selectProvinceByIdComuni(idEntiLong));
		enti.add(this.findRegione().getId());
		
		return commonRepository.getOrganizzazioniCompetentiOnEnti(enti, codiceApplicazione, tipoOrganizzazione, tipoOrganizzazioneSpecifica);
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public TipologicaDTO  searchAutomatizzataRubricaIstituzionale(int idEnte, int idApplicazione) throws Exception {
		return commonRepository.searchAutomatizzataRubricaIstituzionale(	  idEnte, 	  idApplicazione);
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public Integer getEnteDiRiferimentoForOrganizzazione(int idOrganizzazione) throws Exception {
		return commonRepository.getEnteDiRiferimentoForOrganizzazione(idOrganizzazione);
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public Integer findProvinciaForComune(int idEnteComune) throws Exception {
		return commonRepository.findProvinciaForComune(idEnteComune);
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<Integer>  findSoprintendenzeByDenominazione(String nomeProv) throws Exception {
		return commonRepository.findSoprintendenzeByDenominazione(nomeProv);
	}
	
	@Override
	@Cacheable(keyGenerator=ICacheConstants.KEY_CONTEXT_NAME, value="findPaesaggioById", unless="#result == null")
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public PaesaggioOrganizzazioneDTO findPaesaggioById(Long id) throws Exception
	{
		return commonRepository.findPaesaggioOrganizzazione(id, false);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<PaesaggioOrganizzazioneDTO> getAllEntiDelegati() throws Exception
	{
		return commonRepository.getAllEntiDelegati();
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<EnteDTO> getAllComuniPuglia() throws Exception
	{
		return commonRepository.findComuniPuglia();
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<DestinatarioDTO> findEmailCommissione(Long idEnteDelegato, Date dataValidita) throws Exception
	{
		List<DestinatarioDTO> destinatari = null;
		List<PaesaggioEmailDTO> list = commonRepository.findCommissioneEmailByEnte(idEnteDelegato, dataValidita);
		if(list != null && !list.isEmpty())
			destinatari = list.stream().map(DestinatarioDTO::new).collect(Collectors.toList());
		return destinatari;
	}
	
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=false , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public String generaCodiceFascicolo(Integer idOrganizzazioneEnteDelegato) throws Exception {
		LOGGER.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		Integer idPaeOrg = idOrganizzazioneEnteDelegato;
		String tipoOrganizzazione=this.commonRepository.getTipoOrganizzazione(idPaeOrg);
		
		String codiceEnte = this.getCodiceCiviliaByIdOrganizzazione(idPaeOrg);
		
		if (StringUtil.isEmpty(codiceEnte)) {
			throw new CustomOperationToAdviceException("Errore. L'ente con id:"+idPaeOrg+" non possiede un codice generatore!");
		}
		
		int annoCorrente = Calendar.getInstance().get(Calendar.YEAR);

		String prefisso = "AP";
		if (tipoOrganizzazione.equals(Gruppi.REG_.getTipoOrganizzazione())) {
			prefisso = "AUTPAESAF";
		} 
		int seriale =commonRepository.getSerialeCodice(prefisso, codiceEnte, annoCorrente);
		/**
		 * per regione il codice è AUTPAESAF-NN-AAAAA
		 */	 
		if(tipoOrganizzazione.equals(Gruppi.REG_.getTipoOrganizzazione())){
			return prefisso+"-"+seriale+"-"+annoCorrente;
		}else {
			return prefisso+codiceEnte+"-"+seriale+"-"+annoCorrente;	
		}
		
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<SelectOptionDto> getSezioniCatastali() {
		return commonRepository.getSezioniCatastali();
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public PaginatedList<SelectOptionDto> sezioneCatastaleSearch(SezioneCatastaleSearchDTO filter) {
		return commonRepository.sezioneCatastaleSearch(filter);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=false , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public int deleteSezioneCatastale(String codCatastale, String sezione) {
		return commonRepository.sezioneCatastaleDelete(codCatastale,sezione);
	}

	@Override
	@Transactional(transactionManager="txmng_common", readOnly=false , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public int saveOrUpdateSezioneCatastale(SelectOptionDto item) {
		SezioneCatastaleSearchDTO search=new SezioneCatastaleSearchDTO();
		search.setCodCatastale(item.getValue());
		search.setSezione(item.getLinked());
		PaginatedList<SelectOptionDto> ret = commonRepository.sezioneCatastaleSearch(search);
		if(ret!=null && ret.getCount()>0) {
			return commonRepository.sezioneCatastaleUpdate(item);
		}else {
			return commonRepository.sezioneCatastaleInsert(item);
		}
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
			final UserDetail user = SecurityUtil.getUserDetail();
			ut.setUsername(username);
			ut.setCf(user.getFiscalCode());
			ut.setNome(user.getFirstName());
			ut.setCognome(user.getLastName());
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
	public Long getIdSoprintendenzaByComuneId(Long comuneId,Date dataRiferimento) {
		return commonRepository.getIdSoprintendenzaByComuneId( comuneId, dataRiferimento);
	}

	@Override
	@Transactional(readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, transactionManager="txmng_common")
	public List<RubricaSearchDTO> getIndirizziMailDefaultEnte(Integer id) throws Exception {
		try {
			//recuperare la denominazione da common -> paesaggio_organizzazione
			String denominazione = commonRepository.getDenominazioneOrganizzazione(id);
			List<RubricaSearchDTO> response = new ArrayList<RubricaSearchDTO>();
			//recuperare la stringa da configurazione
			String indirizziMailDefaultCompatti = confEnteRepo.getIndirizziMailDefault(id);
			if(indirizziMailDefaultCompatti != null && !indirizziMailDefaultCompatti.isEmpty()) {
				String[] indirizziMailDefault = indirizziMailDefaultCompatti.split(",");
				for (String mail : indirizziMailDefault) {
					RubricaSearchDTO obj = new RubricaSearchDTO();
					obj.setEmail(mail);
					obj.setNome(denominazione);
					obj.setPec(false);
					obj.setMailDefault(true);
					response.add(obj);
				}
			}
			
			return response;
		} catch (Exception e) {
			LOGGER.error("Errore nel recupero degli indirizzi di default" ,e);
			throw new Exception(e);
		}
		
	}
}