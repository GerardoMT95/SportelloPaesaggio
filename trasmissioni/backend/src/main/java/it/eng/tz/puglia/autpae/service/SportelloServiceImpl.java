package it.eng.tz.puglia.autpae.service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dto.SportelloAmbienteParticelleDTO;
import it.eng.tz.puglia.autpae.dto.SportelloAmbienteShapeDTO;
import it.eng.tz.puglia.autpae.dto.UlterioriInformazioniDto;
import it.eng.tz.puglia.autpae.entity.AllegatoDTO;
import it.eng.tz.puglia.autpae.entity.AllegatoFascicoloDTO;
import it.eng.tz.puglia.autpae.entity.ComuniCompetenzaDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.entity.LocalizzazioneInterventoDTO;
import it.eng.tz.puglia.autpae.entity.ParticelleCatastaliDTO;
import it.eng.tz.puglia.autpae.entity.RichiedenteDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.LocalizzazioneInterventoPK;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.enumeratori.TipoLocalizzazione;
import it.eng.tz.puglia.autpae.generated.orm.dto.DocCaricatiSportelloDTO;
import it.eng.tz.puglia.autpae.generated.orm.dto.VPaesaggioProvvedimentiDTO;
import it.eng.tz.puglia.autpae.generated.orm.repository.DocCaricatiSportelloRepository;
import it.eng.tz.puglia.autpae.generated.orm.repository.VPaesaggioProvvedimentiRepository;
import it.eng.tz.puglia.autpae.generated.orm.search.VPaesaggioProvvedimentiSearch;
import it.eng.tz.puglia.autpae.repository.LocalizzazioneInterventoFullRepository;
import it.eng.tz.puglia.autpae.repository.ProcedimentiAmbientaliRepository;
import it.eng.tz.puglia.autpae.repository.base.AllegatoBaseRepository;
import it.eng.tz.puglia.autpae.repository.base.AllegatoFascicoloBaseRepository;
import it.eng.tz.puglia.autpae.repository.base.ComuniCompetenzaBaseRepository;
import it.eng.tz.puglia.autpae.repository.base.FascicoloBaseRepository;
import it.eng.tz.puglia.autpae.repository.base.ParticelleCatastaliBaseRepository;
import it.eng.tz.puglia.autpae.repository.base.RichiedenteBaseRepository;
import it.eng.tz.puglia.autpae.search.ParticelleCatastaliSearch;
import it.eng.tz.puglia.autpae.service.interfacce.LocalizzazioneService;
import it.eng.tz.puglia.autpae.service.interfacce.SportelloService;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class SportelloServiceImpl implements SportelloService {

	private static final Logger log = LoggerFactory.getLogger(SportelloServiceImpl.class);

	@Autowired
	DocCaricatiSportelloRepository docCarDao;
	
	@Autowired
	VPaesaggioProvvedimentiRepository paeProvvDao;
	
	@Autowired
	RichiedenteBaseRepository richiedenteDao;
	
	@Autowired
	ProcedimentiAmbientaliRepository procAmbDao;
	
	@Autowired
	FascicoloBaseRepository fascicoloDao;
	@Autowired
	LocalizzazioneInterventoFullRepository localBaseDao;
	
	@Autowired
	ComuniCompetenzaBaseRepository comCompBaseDao;
	
	@Autowired
	ParticelleCatastaliBaseRepository particelleDao;
	
	@Autowired
	AllegatoBaseRepository allDao;
	
	@Autowired
	AllegatoFascicoloBaseRepository allFascDao;
	
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public List<VPaesaggioProvvedimentiDTO> getProvvedimentiPotenziali(Long idEnte,String codiceFiscaleEnte,String codiceTrasmissione) throws Exception {
		List<VPaesaggioProvvedimentiDTO> ret= paeProvvDao.findByIdEnteCodiceTrasmissioneCodiceFiscaleEnteIdProvvedimento(idEnte,codiceFiscaleEnte,codiceTrasmissione,null);
		return ret;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public String importProvvedimento(Long idEnte,String codiceFiscaleEnte,String codiceTrasmissione,UUID idProvvedimento,Long idFascicolo,String username) throws Exception {
		//verifico che sia esistente nei provvedimenti potenziali
		StringBuilder infoMessage=new StringBuilder();
		log.info("import provvedimento ");
		List<VPaesaggioProvvedimentiDTO> ret= paeProvvDao.findByIdEnteCodiceTrasmissioneCodiceFiscaleEnteIdProvvedimento(idEnte,codiceFiscaleEnte,codiceTrasmissione,idProvvedimento);
		if(ListUtil.isEmpty(ret)) {
			throw new CustomOperationToAdviceException("Il documento selezionato non esiste o non è utilizzabile!!!");
		}
		//verifico che non sia stato già importato in doc_caricati_sportello
		String codiceFascicolo = docCarDao.caricatoSuFascicolo(idProvvedimento);
		if(StringUtil.isNotEmpty(codiceFascicolo)) {
			throw new CustomOperationToAdviceException("Il documento selezionato risulta già referenziato nel fascicolo: "+codiceFascicolo+" impossibile procedere!!!");
		}
		VPaesaggioProvvedimentiDTO provvSelezionato = ret.get(0);
		settaRichiedente(provvSelezionato,idFascicolo);
		log.info("import provvedimento settato richiedente");
		//setto la localizzazione
		FascicoloDTO fascicoloDTO = fascicoloDao.find(idFascicolo);
		UUID idPraticaAmbiente = UUID.fromString(provvSelezionato.getIdPratica());
		String tipoSelezioneLocalizzazione = procAmbDao.selectTipoLocalizzazione(idPraticaAmbiente);
		TipoLocalizzazione tipoSelLocAutpae = TipoLocalizzazione.fromCodificaProcedimentiAmbiente(tipoSelezioneLocalizzazione);
		fascicoloDTO.setOggettoIntervento(provvSelezionato.getOggetto());
		fascicoloDTO.setTipoSelezioneLocalizzazione(tipoSelLocAutpae);
		String dataProtocolloEsterno="";
		if(provvSelezionato.getDataProtocolloEsterno()!=null) {
			dataProtocolloEsterno=new SimpleDateFormat("dd/MM/yyyy").format(provvSelezionato.getDataProtocolloEsterno());
		}
		fascicoloDTO.setNote(StringUtil.concateneString("Importato da procedimento: "
				,provvSelezionato.getNomeProcedimento()
				,"\nFamiglia: "
				,provvSelezionato.getNomeFamiglia()
				,"\nAutorità procedente: "
				,provvSelezionato.getNomeAutoritaProcedente()
				,"\nCodice trasmissione procedimento: "
				,provvSelezionato.getCodiceTrasmissione()
				,"\nProtocollo: "
				,provvSelezionato.getNumeroProtocolloEsterno()
				,"\nData protocollo: "
				,dataProtocolloEsterno
				));
		fascicoloDao.update(fascicoloDTO);
		if(tipoSelLocAutpae==null) {
			infoMessage
			.append("Tipo selezione localizzazione non contemplato: "+tipoSelezioneLocalizzazione+ ", verrà scartata la localizzazione in importazione!")
			.append("<br>");
		}else {
			migraParticelle(codiceTrasmissione, idFascicolo, username, fascicoloDTO, idPraticaAmbiente,
					tipoSelLocAutpae);
			//prendo il riferimento del file shape
			if(tipoSelLocAutpae==TipoLocalizzazione.SHPF) {
				migraShapefile(idFascicolo, username, idPraticaAmbiente);
			}
		}
		log.info("import provvedimento settata localizzazione");
		//migro il provvedimento
		migraProvvedimento(idFascicolo, username, provvSelezionato);
		log.info("import provvedimento settato provvedimento");
		DocCaricatiSportelloDTO docCarEntity=new DocCaricatiSportelloDTO();
		docCarEntity.setIdFascicolo(idFascicolo);
		docCarEntity.setIdDocCaricato(idProvvedimento);
		docCarEntity.setUserInsert(username);
		docCarDao.insert(docCarEntity);
		return infoMessage.toString();
	}

	private void migraProvvedimento(Long idFascicolo, String username, VPaesaggioProvvedimentiDTO provvSelezionato)
			throws Exception {
		AllegatoDTO provvDTO =new AllegatoDTO();
		provvDTO.setChecksum(provvSelezionato.getHashFile());
		provvDTO.setContenuto(provvSelezionato.getCmisId());
		provvDTO.setContenuto(provvSelezionato.getCmisId());
		provvDTO.setDeletable(false);
		provvDTO.setNome(provvSelezionato.getFileName());
		provvDTO.setDescrizione(TipoAllegato.PROVVEDIMENTO_FINALE.getTextValue());
		provvDTO.setDeleted(false);
		provvDTO.setDimensione(100);//non c'è su ambiente
		provvDTO.setUtenteInserimento(username);
		provvDTO.setUsernameInserimento(username);
		Long allInserted = this.allDao.insert(provvDTO);
		this.allFascDao.insert(new AllegatoFascicoloDTO(allInserted, TipoAllegato.PROVVEDIMENTO_FINALE, idFascicolo));
	}

	private void migraShapefile(Long idFascicolo, String username, UUID idPraticaAmbiente) throws Exception {
		List<SportelloAmbienteShapeDTO> shapes = this.procAmbDao.selectShapeSportelloAmbiente(idPraticaAmbiente);
		if(ListUtil.isNotEmpty(shapes)) {
			//prelevo solo il primo
			SportelloAmbienteShapeDTO shapeFile = shapes.get(0);
			AllegatoDTO allegatoShapeDTO=new AllegatoDTO();
			allegatoShapeDTO.setDeletable(false);
			allegatoShapeDTO.setNome(shapeFile.getNameFile());
			allegatoShapeDTO.setDescrizione(TipoAllegato.LOCALIZZAZIONE.getTextValue());
			allegatoShapeDTO.setContenuto(shapeFile.getCmisId());
			allegatoShapeDTO.setChecksum(shapeFile.getHash());
			allegatoShapeDTO.setDeleted(false);
			allegatoShapeDTO.setDimensione(100);//non c'è su ambiente
			allegatoShapeDTO.setUtenteInserimento(username);
			allegatoShapeDTO.setUsernameInserimento(username);
			Long allInserted = this.allDao.insert(allegatoShapeDTO);
			this.allFascDao.insert(new AllegatoFascicoloDTO(allInserted, TipoAllegato.LOCALIZZAZIONE, idFascicolo));
		}
	}

	@Autowired
	LocalizzazioneService localizzazioneSvc;
	
	/**
	 * migra particelle e corrispondenti geometrie
	 * @autor Adriano Colaianni
	 * @date 26 lug 2022
	 * @param codiceTrasmissione
	 * @param idFascicolo
	 * @param username
	 * @param fascicoloDTO
	 * @param idPraticaAmbiente
	 * @param tipoSelLocAutpae
	 * @throws Exception
	 */
	private void migraParticelle(String codiceTrasmissione, Long idFascicolo, String username,
			FascicoloDTO fascicoloDTO, UUID idPraticaAmbiente, TipoLocalizzazione tipoSelLocAutpae) throws Exception {
		log.info("");
		Map<Long, Long> mappaOids = procAmbDao.migrateLayerGeo(codiceTrasmissione, idFascicolo, username);
		List<Long> idComuni = procAmbDao.selectComuniParticellePratica(idPraticaAmbiente,idFascicolo);
		List<ComuniCompetenzaDTO> comuniCompetenzaPratica = comCompBaseDao.selectByIdPratica(idFascicolo);
		for(Long idComune:idComuni) {
			LocalizzazioneInterventoDTO localIntDTO =null;
			Optional<ComuniCompetenzaDTO> comComp = comuniCompetenzaPratica.stream().filter(com->com.getEnteId()==idComune).findAny();
			try{
				localIntDTO = localBaseDao.findByIdPraticaAndIdComune(idFascicolo, idComune);
			}catch(EmptyResultDataAccessException e) {
				localIntDTO=new LocalizzazioneInterventoDTO();
				localIntDTO.setComuneId(idComune);
				localIntDTO.setPraticaId(idFascicolo);
				if(comComp.isPresent()) {
					localIntDTO.setComune(comComp.get().getDescrizione());
				}
				localBaseDao.insert(localIntDTO);
			}
			List<SportelloAmbienteParticelleDTO> particelleAmbienteComune = 
					procAmbDao.selectParticellePratica(idPraticaAmbiente, idComune);
			log.info("rimozione particelle esistenti su comune " +idComune);
			ParticelleCatastaliSearch particelleSearch=new ParticelleCatastaliSearch();
			particelleSearch.setPraticaId(idFascicolo);
			particelleSearch.setComuneId(idComune);
			particelleDao.delete(particelleSearch);
			int counter=0;
			for(SportelloAmbienteParticelleDTO particellaAmbiente:particelleAmbienteComune) {
				counter++;
				ParticelleCatastaliDTO particellaPaesaggio=new ParticelleCatastaliDTO();
				particellaPaesaggio.setPraticaId(idFascicolo);
				particellaPaesaggio.setComuneId(idComune);
				particellaPaesaggio.setLivello("PARTICELLE");
				particellaPaesaggio.setId(counter);
				if(comComp.isPresent()) {
					particellaPaesaggio.setCodCat(comComp.get().getCodCat());	
				}
				particellaPaesaggio.setDescrSezione(particellaAmbiente.getNomeSezione());
				particellaPaesaggio.setSezione(particellaAmbiente.getSezione());
				particellaPaesaggio.setFoglio(particellaAmbiente.getFoglio());
				particellaPaesaggio.setParticella(particellaAmbiente.getParticella());
				particellaPaesaggio.setSub(particellaAmbiente.getSub());
				particellaPaesaggio.setNote(particellaAmbiente.getNote());
				if(particellaAmbiente.getOid()!=null) {
					particellaPaesaggio.setOid(mappaOids.get(particellaAmbiente.getOid()));
				}
				particelleDao.insert(particellaPaesaggio);
			}
		}
		List<String> codCats = comuniCompetenzaPratica.stream().filter(com->idComuni.contains(com.getEnteId())).map(el->el.getCodCat()).collect(Collectors.toList());
		UlterioriInformazioniDto listeUdto = localizzazioneSvc.getListe(codCats);
		for(Long idComune:idComuni) {
			localizzazioneSvc.creaRecordVincolisticaComune(idFascicolo, idComune, listeUdto);	
		}
	}

	private void settaRichiedente(VPaesaggioProvvedimentiDTO provvSelezionato, Long idFascicolo) throws Exception {
		RichiedenteDTO rich = null;
		try{
			rich = richiedenteDao.findByIdFascicolo(idFascicolo);
		}catch(EmptyResultDataAccessException e) {
			rich=new RichiedenteDTO();
			rich.setIdFascicolo(idFascicolo);
			Long idRich = richiedenteDao.insert(rich);
			rich.setId(idRich);
		}
		rich.setCognome(provvSelezionato.getCognome());
		rich.setNome(provvSelezionato.getNome());
		rich.setCodiceFiscale(provvSelezionato.getCodiceFiscale());
		rich.setSesso(provvSelezionato.getSesso());
		rich.setDataNascita(provvSelezionato.getDataNascita());
		rich.setStatoNascita(provvSelezionato.getNazioneNascita());
		rich.setComuneNascita(StringUtil.isNotEmpty(provvSelezionato.getComuneNascita())?provvSelezionato.getComuneNascita():provvSelezionato.getComuneNascitaEstero());
		rich.setStatoResidenza(provvSelezionato.getNazioneResidenza());
		rich.setComuneResidenza(StringUtil.isNotEmpty(provvSelezionato.getComuneResidenza())?provvSelezionato.getComuneResidenza():provvSelezionato.getComuneResidenzaEstero());
		rich.setEmail(provvSelezionato.getMail());
		rich.setPec(provvSelezionato.getPec());
		rich.setDitta(provvSelezionato.getDitta());
		if(provvSelezionato.getDitta()!=null && provvSelezionato.getDitta()) {
			rich.setDittaInQualitaDi(provvSelezionato.getNomeRuoloAzienda());
			rich.setDittaQualitaAltro(provvSelezionato.getAltroRuoloAzienda());
			rich.setDittaCodiceFiscale(provvSelezionato.getCodiceFiscaleAzienda());
			rich.setDittaPartitaIva(provvSelezionato.getPivaAzienda());	
		}
		richiedenteDao.update(rich);
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public void setLibero(UUID idProvvedimento,String username,Long idFascicolo) throws Exception {
		docCarDao.setLibero(idProvvedimento, username,idFascicolo);
		return;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public boolean hasImport(Long idFascicolo) throws Exception {
		return docCarDao.hasImport(idFascicolo);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public PaginatedList<VPaesaggioProvvedimentiDTO> search(VPaesaggioProvvedimentiSearch search) throws Exception {
		return this.paeProvvDao.search(search);
	}

}