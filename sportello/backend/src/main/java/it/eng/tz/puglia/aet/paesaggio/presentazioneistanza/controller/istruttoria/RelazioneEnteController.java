package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.istruttoria;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.RoleAwareController;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.DettaglioCorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.RequestSaveComunicationDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.RelazioneEnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IRelazioneEnteService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl.PraticaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.AllegatoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.ComunicazioniService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.IstrPraticaService;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.error.exception.CustomValidationException;
import it.eng.tz.puglia.util.log.LogUtil;

/**
 * Controller CRUD for table presentazione_istanza.relazione_ente
 */
@Controller
@RequestMapping("/relazione")
public class RelazioneEnteController extends RoleAwareController{

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RelazioneEnteController.class);
    /**
     * service
     */
    @Autowired
    private IRelazioneEnteService service;
    
    @Autowired 
    private AllegatoService allegatiService;
    
    @Autowired
    private ComunicazioniService comunicazioniService;
    
    @Autowired
    private PraticaService praticaService;
    @Autowired
    private IstrPraticaService istPraticaService;

    /**
     * search
     * @throws CustomOperationToAdviceException 
     */
    @PostMapping(value="/search.pjson", produces=MEDIA_TYPE)
    public ResponseEntity<BaseRestResponse<RelazioneEnteDTO>> search(@RequestParam UUID idPratica) throws CustomOperationToAdviceException{
        LOGGER.info("Start search");		
        final StopWatch sw = LogUtil.startLog("search");
        try {
        	PraticaDTO praticaDTO=new PraticaDTO();
			praticaDTO.setId(idPratica);
			praticaDTO=praticaService.find(praticaDTO);
			boolean okCompetenza=this.istPraticaService.diMiaCompetenza(praticaDTO);
			if(!okCompetenza) {
				throw new CustomOperationToAdviceException("Pratica non di competenza, impossibile procedere!");
			}
			return super.okResponse(this.service.findByIdPratica(idPratica));
        }catch(Exception e) {
            LOGGER.error("Error in search", e);
            return super.koResponse(e, new BaseRestResponse<>());
        }finally {
            LOGGER.info(LogUtil.stopLog(sw));
        }
    }


    /**
     * delete
     * @throws CustomOperationToAdviceException 
     */
    @PostMapping(value="/deleteAllegato.pjson", produces=MEDIA_TYPE)
    public ResponseEntity<BaseRestResponse<Boolean>> deleteAllegato(
    		@RequestParam UUID idFile,
    		@RequestParam("idPratica") UUID idPratica
    		) throws CustomOperationToAdviceException{
        LOGGER.info("Start delete Allegato");
		checkAbilitazioneFor(Gruppi.ED_,Gruppi.REG_);

        final StopWatch sw = LogUtil.startLog("delete");
        try {
        	this.checkDiMiaCompetenza(idPratica);
        	this.allegatiService.deleteAllegatoRelazione(idFile);
            return super.okResponse(true);
        	
        }catch(Exception e) {
            LOGGER.error("Error in delete", e);
            return super.koResponse(e, new BaseRestResponse<>());
        }finally {
            LOGGER.info(LogUtil.stopLog(sw));
        }
    }
    
    /**
     * update
     * @throws CustomOperationToAdviceException 
     */
    @PostMapping(value="/update.pjson", produces=MEDIA_TYPE)
    public ResponseEntity<BaseRestResponse<Integer>> update(@RequestBody RelazioneEnteDTO dto) throws CustomOperationToAdviceException{
        LOGGER.info("Start update");
		checkAbilitazioneFor(Gruppi.ED_,Gruppi.REG_);

        final StopWatch sw = LogUtil.startLog("update");
        try {
        	this.checkDiMiaCompetenza(dto.getIdPratica());
        	this.service.validaEntity(dto);
        	return super.okResponse(this.service.update(dto));
        }
        catch(CustomValidationException e) {
        	throw new CustomOperationToAdviceException(e.getMessage());
        }
        catch(Exception e) {
            LOGGER.error("Error in update", e);
            return super.koResponse(e, new BaseRestResponse<>());
        }finally {
            LOGGER.info(LogUtil.stopLog(sw));
        }
    }


	@PostMapping(value = "/upload_allegato.pjson", produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<AllegatiDTO>> uploadDocumentoReferente(
			@RequestParam("tipoAllegato") String tipoAllegato,
			@RequestParam("idRelazione") long idRelazione,
			@RequestParam("codiceFascicolo") UUID idPratica,
			@RequestParam("file") MultipartFile file) throws CustomOperationToAdviceException {
		LOGGER.info("Start upload");
		checkAbilitazioneFor(Gruppi.ED_,Gruppi.REG_);
		
		final StopWatch sw = LogUtil.startLog("upload");
		try {
			this.checkDiMiaCompetenza(idPratica);
			AllegatiDTO ret = allegatiService.uploadAllegatiRelazione(idPratica,file,tipoAllegato,idRelazione);
			return super.okResponse(ret);
		} catch (Exception e) {
			LOGGER.error("Error in upload", e);
			return super.koResponse(e, new BaseRestResponse<>());
		} finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}
	
	@PostMapping(value = "/saveComunicazione.pjson", produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<DettaglioCorrispondenzaDTO>> saveComunicazione(
			@RequestParam("idPratica") UUID idPratica,
			@RequestBody RequestSaveComunicationDto reqComunication) throws CustomOperationToAdviceException{
		LOGGER.info("Start relazione - saveComunicazione");
			checkAbilitazioneFor(Gruppi.ED_,Gruppi.REG_);

		try {
			this.checkDiMiaCompetenza(reqComunication.getIdPratica());
			DettaglioCorrispondenzaDTO resp = this.service.saveComunicazioneRelazione(reqComunication);
			return super.okResponse(resp);
		} catch (Exception e) {
			LOGGER.error("Errore nella saveComunicazione ",e);
			return super.koResponse(e, new BaseRestResponse<>());
		}
	}
	
	@PostMapping(value = "/updateComunicazione.pjson", produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<DettaglioCorrispondenzaDTO>> updateComunicazione(
			@RequestBody RequestSaveComunicationDto reqComunication) throws CustomOperationToAdviceException{
		LOGGER.info("Start relazione - updateComunicazione");
			checkAbilitazioneFor(Gruppi.ED_,Gruppi.REG_);
			
		try {
			this.checkDiMiaCompetenza(reqComunication.getIdPratica());
			DettaglioCorrispondenzaDTO resp = comunicazioniService.saveComunication(reqComunication.getCorrispondenza(), reqComunication.getIdPratica());
			if(!resp.getCorrispondenza().getBozza()) {
				this.comunicazioniService.inserisciAllegatiCorrispondenzaDaRelazione(
						resp,
						reqComunication.getTipoInvia(),
						reqComunication.getIdRelazione());
				//rileggo la corrispondenza per riprendermi gli allegati che ora sono relazionati al record corrispondenza...
				DettaglioCorrispondenzaDTO corrNew = 
						this.comunicazioniService.find(reqComunication.getCorrispondenza().getCorrispondenza().getId());
				resp.setAllegati(corrNew.getAllegati());
				resp.setAllegatiInfo(corrNew.getAllegatiInfo());
				this.comunicazioniService.sendComunicationRelazione(resp,
															reqComunication.getIdPratica(),
															reqComunication.getTipoInvia(),
															reqComunication.getIdRelazione());
				
			}
			return super.okResponse(resp);
		} catch (Exception e) {
			LOGGER.error("Errore in updateComunicazione ",e);
			return super.koResponse(e, new BaseRestResponse<>());
		}
	
	}

	
}
