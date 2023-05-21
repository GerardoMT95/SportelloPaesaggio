package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.DettaglioCorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.RequestSaveComunicationDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AttivitaDaEspletare;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoRelazione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.ComunicazioniService;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.error.exception.CustomValidationException;

/**
 * Service CRUD for table presentazione_istanza.relazione_ente
 */
@Service
public class RelazioneEnteService extends GenericCrudService<RelazioneEnteDTO, RelazioneEnteSearch, RelazioneEnteRepository> implements IRelazioneEnteService{

    /**
     * dao
     */
    @Autowired
    private RelazioneEnteRepository dao;
    
    @Autowired
    private CorrispondenzaRepository corrispondenzaRepository;

    @Autowired
    private ComunicazioniService comunicazioniService;
    
    @Autowired
    private UserUtil userUtil;
    
    @Autowired
    private PraticaRepository praticaRepo;
    
    /**
     * get dao
     */
    @Override
    protected RelazioneEnteRepository getDao(){
        return this.dao;
    }
    /**
     * validate dto for insert
     */
    @Override
    protected void validateInsertDTO(final RelazioneEnteDTO entity) throws CustomValidationException{
        if(entity.getIdPratica() == null){
            throw new CustomValidationException("idPratica non puo' essere null");
        }
        if(entity.getNumeroProtocolloEnte() == null){
            throw new CustomValidationException("numeroProtocolloEnte non può essere null");
        }
        if(entity.getNominativoIstruttore() == null){
            throw new CustomValidationException("nominativoIstruttore non può essere  null");
        }
    }
    
    @Override
    public void validaEntity(final RelazioneEnteDTO entity) throws CustomValidationException{
    	this.validateUpdateDTO(entity);
    }
    
    /**
     * validate dto for update
     */
    @Override
    protected void validateUpdateDTO(final RelazioneEnteDTO entity) throws CustomValidationException{
    	if(entity.getIdPratica() == null){
            throw new CustomValidationException("idPratica non puo' essere null");
        }
        if(entity.getNumeroProtocolloEnte() == null){
            throw new CustomValidationException("numeroProtocolloEnte non può essere null");
        }
        if(entity.getNominativoIstruttore() == null){
            throw new CustomValidationException("nominativoIstruttore non può essere  null");
        }
    }
    
    /**
     * Metodo per la ricerca della relazione_ente partendo dall'id Pratica e se non esiste la crea 
     * 
     * @author G.Lavermicocca
     * @date 3 ott 2020
     */
    @Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
    public RelazioneEnteDTO findByIdPratica(UUID idPratica) {
    	logger.info("RelazioneEnteService - findByIdPratica"+idPratica);
    	RelazioneEnteDTO newRelEnte = new RelazioneEnteDTO();
		newRelEnte.setIdPratica(idPratica);
		
    	List<RelazioneEnteDTO> listRelazioni = this.dao.findByIdPratica(idPratica);
		if(listRelazioni.isEmpty()) {
			newRelEnte.setIdRelazioneEnte(this.dao.insert(newRelEnte));
			
		}else{
			newRelEnte =listRelazioni.get(0);
			List<AllegatiDTO> listaAllegati = this.dao.searchAllegati(newRelEnte.getIdRelazioneEnte());
			newRelEnte.setGrigliaAllegati(listaAllegati);
			Long idCorrispondenza = newRelEnte.getIdCorrispondenza();
			if(idCorrispondenza!=null) {
				DettaglioCorrispondenzaDTO dettaglioCrorrispondenza = corrispondenzaRepository.getDettaglioCorrispondenza(idCorrispondenza);
				newRelEnte.setDettaglioCorrispondenza(dettaglioCrorrispondenza);
			}
		}
		
		return newRelEnte;
    }
    
    @Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public void insertAllegato(UUID idAllegato, long idRelazione) {
		this.dao.insertAllegatoRelazioneEnte(idAllegato,idRelazione);
		
	}
   /**
    * Cerca l'id corrispondenza della relazione e se non esiste ne crea una e la associa alla relazione
    * ritorna il dettaglio della corrispondenza 
    *
    * @author G.Lavermicocca
    * @date 3 ott 2020
    */
    @Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
    public DettaglioCorrispondenzaDTO saveComunicazioneRelazione(RequestSaveComunicationDto reqComunication) {
    	
		try {
			logger.info("RelazioneEnteService - saveComunicazioneRelazione idRelazione"+reqComunication.getIdRelazione());
			RelazioneEnteDTO pk = new RelazioneEnteDTO();
			pk.setIdRelazioneEnte(reqComunication.getIdRelazione());
			pk=this.find(pk);
			DettaglioCorrispondenzaDTO resp=new DettaglioCorrispondenzaDTO();
			if(pk.getIdCorrispondenza()==null) {
				//id_corrispondenza non esistente nella relazione ente 
				Gruppi gruppo = userUtil.getGruppo();
				Integer idOrganizzazione=null;
				try {idOrganizzazione=userUtil.getIntegerId();}catch(Exception e) {}
				resp = comunicazioniService.create(pk.getIdPratica(),idOrganizzazione,gruppo,null,false);
				pk.setIdCorrispondenza(resp.getCorrispondenza().getId());
				this.dao.update(pk);
				this.praticaRepo.updateStatoRelazione(pk.getIdPratica(), StatoRelazione.RELAZIONE_NON_TRASMESSA);
			}else {
				//id_corrispondenza esistente 
				CorrispondenzaDTO corrispondenza = new CorrispondenzaDTO();
				corrispondenza.setId(pk.getIdCorrispondenza());
				resp.setCorrispondenza(this.corrispondenzaRepository.find(corrispondenza));
			}
			return resp;
		} catch (Exception e) {
			logger.error("Errore in saveComunicazioneRelazione",e);
			return null;
		}
    	
    	
    }
}
