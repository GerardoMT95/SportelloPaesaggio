package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.bean.MyOrganizzazioniBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.TabelleAssegnamentoFascicoliOldDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.FascicoloDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainNumberValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.allegati.DocumentoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.IstrPraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AttivitaDaEspletare;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Ruoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.SezioneAllegati;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.LocalizzazioneInterventoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.LocalizzazioneInterventoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PraticaSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.EnteResponseBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.service.RemoteSchemaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.AllegatoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.FascicoloService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.GruppiRuoliService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.IstrPraticaService;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.util.log.LogUtil;

@Service("istrPraticaService")
public class IstrPraticaServiceImpl implements IstrPraticaService
{
	@Qualifier("allegatiIstruttoria")
	@Autowired
	private AllegatoService allegatiService;
	@Autowired
    private PraticaRepository praticaDao;
	
	@Autowired
    private LocalizzazioneInterventoRepository localizDao;
	
	@Autowired
    private GruppiRuoliService gruppiRuoliService;
	@Autowired 
	private RemoteSchemaService remoteSchemaService;
	@Autowired 
	private UserUtil userUtil;

	private static final Logger LOGGER = LoggerFactory.getLogger(IstrPraticaServiceImpl.class);
	
	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public PaginatedList<TabelleAssegnamentoFascicoliOldDTO> searchForAssegnamento(PraticaSearch search) throws Exception
	{
		search.setUsername(LogUtil.getUser());
		prepareForSearch(search);
		PaginatedList<TabelleAssegnamentoFascicoliOldDTO> ret = praticaDao.searchForAssegnamento(search);
		return ret;
	}
	
	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public PaginatedList<FascicoloDto> search_istr(PraticaSearch search) throws Exception
	{
		search.setUsername(LogUtil.getUser());
		prepareForSearch(search);
		PaginatedList<IstrPraticaDTO> ret = praticaDao.searchForIstr(search);
		PaginatedList<FascicoloDto> out = new PaginatedList<FascicoloDto>();
		out.setCount(ret.getCount());
		out.setList(new ArrayList<>());
		//popolo tutti gli altri dati della pratica che mi servono per renderizzare i dati in colonna
		if(ret!=null && ret.getList()!=null) {
			List<PlainNumberValueLabel> entiDelegati = remoteSchemaService.getAllEntiRiceventi();
			for(IstrPraticaDTO pratica:ret.getList()) {
				FascicoloDto fascicolo =new FascicoloDto();
				fascicolo.setFascicoloByIstrPratica(pratica);
				//aggiungo solo i comuni intervento
				List<LocalizzazioneInterventoDTO> comuniIntervento = localizDao.select(pratica.getId());
				if(ListUtil.isNotEmpty(comuniIntervento)) {
					fascicolo.setDescrComuniIntervento(
							comuniIntervento.stream().map(LocalizzazioneInterventoDTO::getComune).collect(Collectors.toList()));	
				}
				//setto la descrizione ente delegato
				long idEnteDelegato = Long.parseLong(fascicolo.getEnteDelegato());
				entiDelegati.stream()
				.filter(ed->ed.getValue().equals(idEnteDelegato))
				.findAny()
				.ifPresent(ed->fascicolo.setDescrEnteDelegato(ed.getDescription()));
				out.getList().add(fascicolo);
			}
		}
		return out;
	}
	
	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public List<PraticaDTO> searchAll(PraticaSearch search) throws Exception
	{
		return searchAll(search, true);
	}
	
	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public List<PraticaDTO> searchAll(PraticaSearch search, boolean protetto) throws Exception
	{
		if(protetto)
			this.prepareForSearch(search);
		search.setInIstruttoria(true);
		return praticaDao.searchAll(search);
	}
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public List<DocumentoDto> getDichiarazioni(UUID idPratica) throws Exception
	{
		List<AllegatiDTO> allegati = allegatiService.getAllegati(idPratica, SezioneAllegati.GENERA_STAMPA);
		List<DocumentoDto> list = new LinkedList<DocumentoDto>();
		for(AllegatiDTO allegato: allegati)
		{
			DocumentoDto tmp = new DocumentoDto(allegato); 
			tmp.setType(tmp.getType().toLowerCase().equals("istanza") ? "700": "701");
			list.add(tmp);
		}
		return list;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public void prepareForSearch(PraticaSearch search) throws Exception {
		prepareForSearch(search, userUtil.getCodice_GruppoIdRuolo(), SecurityUtil.getUsername());
	}
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public void prepareForSearch(PraticaSearch search, String codiceGruppo, String username) throws Exception 
	{
		Gruppi gruppoLoggato = userUtil.getGruppo(codiceGruppo);
		String userIdLoggato = username;
		if (gruppoLoggato==Gruppi.RICHIEDENTI) {
			String codiceFiscale=SecurityUtil.getUserDetail().getFiscalCode();
			search.setCodiceFiscale(codiceFiscale);
			//presentazione istanza 
			search.setOwner(userIdLoggato);
			return;
		}
		//istruttoria
		if(gruppoLoggato==Gruppi.ADMIN) {
			//nessuna restrizione....
		}
		int idOrganizzazione=-1; //serve comunque nella clausola per l'assegnamento fascicolo.... mettendo a -1 il left join 
		try {
			idOrganizzazione = userUtil.getIntegerId(codiceGruppo);	
		}catch(Exception e){} //rimane a -1
		Ruoli ruolo =userUtil.getRuolo(codiceGruppo);
		search.setInIstruttoria(true);
		search.setOrganizzazioneAutenticata(idOrganizzazione);
		if(gruppoLoggato==null) {
			//pubblico....
			search.setRicercaPubblica(true);
		}else if(gruppoLoggato==Gruppi.ED_ || gruppoLoggato==Gruppi.REG_) {
			//gli stati in istruttoria sono già filtrati sopra...
			search.setEnteDelegato(idOrganizzazione+"");
		}else if(gruppoLoggato==Gruppi.CL_) {
			List<Integer> entiRiferimento = remoteSchemaService.getPaesaggiEntiForCommissione(idOrganizzazione);
			search.setEntiDelegatiRiferimento(entiRiferimento);
		}
		else if(gruppoLoggato==Gruppi.SBAP_ || gruppoLoggato==Gruppi.ETI_) {
			//filtro per territorio....
			List<Integer> entiDiCompetenza=null;
			entiDiCompetenza = gruppiRuoliService.entiForGruppo(userUtil.getGruppo_Id(codiceGruppo));
			if(entiDiCompetenza==null || entiDiCompetenza.size()<=0) {
				throw new CustomOperationToAdviceException("Nessun territorio configurato per l'ente in loggato ");
			}
			search.setComuniIntervento(entiDiCompetenza);
			//poi vedremo se dobbiamo aggiungerne altri....
			search.setStatiAmmessi(Arrays.asList(AttivitaDaEspletare.IN_LAVORAZIONE,AttivitaDaEspletare.IN_TRASMISSIONE,AttivitaDaEspletare.TRANSMITTED));
		}
		//infine il funzionario assegnatario della pratica solo per quelle organizzazioni dove è abilitato il 
		//filtro sull'assegnazione ... (per adesso NO a REG_ e CL_)
		if(gruppoLoggato != Gruppi.REG_ &&  gruppoLoggato != Gruppi.CL_ && ruolo==Ruoli.FUNZIONARIO) {
			search.setUserAssegnatario(userIdLoggato);
			search.setOrganizzazioneAutenticata(idOrganizzazione);
		}
	}
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public boolean diMiaCompetenza(PraticaDTO pratica) throws Exception {
		PraticaSearch search = new PraticaSearch();
		prepareForSearch(search);
		search.setId(pratica.getId());
		try {
			List<PraticaDTO> searched = praticaDao.searchAll(search);
			if(searched!=null && searched.size()>0) {
				return true;
			}
		}catch(Exception e) {}
		return false;
	}
	
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public MyOrganizzazioniBean organizzazioniEComunidiCompetenza(){
		MyOrganizzazioniBean ret=new MyOrganizzazioniBean();
		ret.setCodiciGruppo(new ArrayList<String>());
		Map<String,List<Integer>> mapOut=new HashMap<String,List<Integer>>();
		PraticaSearch psearch=new PraticaSearch();
		boolean isEdRegCl=false;
		for(EnteResponseBean ente:userUtil.getMyEnti()) {
			psearch.setComuniIntervento(null);
			psearch.setEnteDelegato(null);
			psearch.setEntiDelegatiRiferimento(null);
			try {
				this.prepareForSearch(psearch, ente.getCodiceGruppo(), null);
			} catch (Exception e) {
				LOGGER.error("Errore nella prepare for search per il gruppo {},{}",ente.getCodiceGruppo(),e.getMessage());
			}
			//procedurale
			isEdRegCl=false;
			if(StringUtils.isNotEmpty(psearch.getEnteDelegato())){
				mapOut.put(psearch.getEnteDelegato(),null);
				isEdRegCl=true;
			}
			if(ListUtil.isNotEmpty(psearch.getEntiDelegatiRiferimento())){
				for(Integer idEnte:psearch.getEntiDelegatiRiferimento()) {
					mapOut.put(idEnte.toString(),null);	
				}
				isEdRegCl=true;
			}
			//territoriale
			if(!isEdRegCl && ListUtil.isNotEmpty(psearch.getComuniIntervento())){
				mapOut.put(ente.getId().toString(),psearch.getComuniIntervento());	
			}
			ret.getCodiciGruppo().add(ente.getCodiceGruppo());
		}
		ret.setOrganizzazioniMap(mapOut);
		return ret;
	}
}
