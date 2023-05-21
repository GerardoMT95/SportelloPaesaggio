package it.eng.tz.puglia.autpae.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.entity.TipoProcedimentoDTO;
import it.eng.tz.puglia.autpae.enumeratori.Applicativo;
import it.eng.tz.puglia.autpae.repository.FascicoloTipiProcedimentoRepository;
import it.eng.tz.puglia.autpae.repository.base.TipoProcedimentoBaseRepository;
import it.eng.tz.puglia.autpae.search.TipoProcedimentoSearch;
import it.eng.tz.puglia.autpae.service.interfacce.TipoProcedimentoService;
import it.eng.tz.puglia.bean.PaginatedList;

@Service
public class TipoProcedimentoServiceImpl implements TipoProcedimentoService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(TipoProcedimentoServiceImpl.class);
	// private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot
	// access attachments for plan exclusion of this municipality";

	@Autowired
	private TipoProcedimentoBaseRepository repository;
	@Autowired
	private ApplicationProperties props;
	
	@Autowired
	private FascicoloTipiProcedimentoRepository fascicoloTipiProcedimentoRepo;
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public List<TipoProcedimentoDTO> select() throws Exception {
		return repository.select();
	}

	
	/**
	 * Metodo che restituisce la lista dei tipi procedimento (validi in base a data inizio e fine rispetto ad oggi) di un dato applicativo
	 * @author Giuseppe Canciello
	 * @date 11 mag 2021
	 * @param app
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public List<TipoProcedimentoDTO> selectByApplication(Applicativo app) throws Exception {
		return repository.selectByApplication(app);
	}
	
	
	/**
	 * Metodo che restituisce la lista di tipi procedimento (anche quelli scaduti) dell'applicativo in esecuzione
	 * @author Giuseppe Canciello
	 * @date 11 mag 2021
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public List<TipoProcedimentoDTO> selectAllByApplication() throws Exception {
		return repository.selectAllByApplication(Applicativo.valueOf(props.getCodiceApplicazione().toUpperCase()));
	}
	
	/**
	 * Metodo che restituisce la lista di tipi procedimento (validi in base a data inizio e fine rispetto ad oggi) dell'applicativo in esecuzione
	 * @author Giuseppe Canciello
	 * @date 11 mag 2021
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public List<TipoProcedimentoDTO> selectByApplication() throws Exception {
		return repository.selectByApplication(Applicativo.valueOf(props.getCodiceApplicazione().toUpperCase()));
	}

	
	/**
	 * Metodo che restituisce la lista di tipi procedimento associati al fascicolo il cui
	 * id è ricevuto come parametro. La lista conterrà tutti i tipi procedimento che erano attivi
	 * al momento della creazione del fascicolo. Questo per permettere di utilizzarli in fase di editig
	 * di questo fascicolo anche se alcuni sono scaduti
	 * @author Giuseppe Canciello
	 * @date 12 mag 2021
	 * @param idFascicolo
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public List<TipoProcedimentoDTO> findByFascicolo(Long idFascicolo) throws Exception {
		return fascicoloTipiProcedimentoRepo.selectTipiProcedimentoByIdFascicolo(idFascicolo);
	}
	
	/**
	 * Metodo che permette di salvare un nuovo periodo (data inizio e data fine) di validità per un dato tipo procedimento
	 * @author Giuseppe Canciello
	 * @date 12 mag 2021
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public int updateValidita(TipoProcedimentoDTO updateDto) throws Exception {
		//Check che entrambe le date siano valorizzate
		if (updateDto.getInizioValidita()==null || updateDto.getFineValidita()==null) {
			log.error("Data inizio validita o data fine validità non valorizzate. Inizio: {}, fine:{}",updateDto.getInizioValidita(),updateDto.getFineValidita());
			throw new CustomOperationToAdviceException("Data inizio validità e fine validità devono essere valorizzate");
		}
		if (updateDto.getInizioValidita().after(updateDto.getFineValidita())) {
			log.error("La data di fine validita è antecedente alla data di inizio validità. Inizio: {}, fine:{}",updateDto.getInizioValidita(),updateDto.getFineValidita());
			throw new CustomOperationToAdviceException("La data inizio validità deve essere antecedete alla data di fine validità");
		}
		log.info("updateValidita - Aggiornamento data validità tipo procedimento");
		int resp= repository.updateValidita(updateDto);
		
		//Controllo che sia stata aggiornata una sola riga
		if (resp==1) {
			log.info("updateValidita - tipo procedimento con codice {} aggiornato correttamente", updateDto.getCodice());
		}else {
			log.error("updateValidita - *** Attenzione!*** Risultato inatteso durante aggiornamento "
					+ "tipo procedimento con codice= {}. Atteso: 1, effettivo: {}",updateDto.getCodice(),resp);
			throw new CustomOperationToAdviceException("Risultato inatteso (diverso da 1) durante aggiornamento tipo procedimento");
		}
		return resp;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public long count(TipoProcedimentoSearch filter) throws Exception {
		return repository.count(filter);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public TipoProcedimentoDTO find(String pk) throws Exception {
		return repository.find(pk);
	}

//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public String								insert(TipoProcedimentoDTO entity) 	  throws Exception { return repository.insert(entity); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 		update(TipoProcedimentoDTO entity)	  throws Exception { return repository.update(entity); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 		delete(TipoProcedimentoSearch search) throws Exception { return repository.delete(search); }
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public PaginatedList<TipoProcedimentoDTO> search(TipoProcedimentoSearch filter) throws Exception {
		return repository.search(filter);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FullRepository

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Altri
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Metodi

}