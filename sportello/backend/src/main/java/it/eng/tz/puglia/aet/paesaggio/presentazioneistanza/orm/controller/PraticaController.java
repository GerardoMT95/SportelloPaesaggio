package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.RoleAwareController;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.FascicoloDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.TipologicaIntegerBooleanDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDelegatoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PraticaSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IConfigurazioniEnteService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IPraticaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.PraticaDelegatoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.FascicoloService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.IstrPraticaService;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.util.log.LogUtil;

/**
 * Controller CRUD for table presentazione_istanza.pratica
 */
@Controller
@RequestMapping("Pratica")
public class PraticaController extends RoleAwareController{

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PraticaController.class);
    /**
     * service
     */
    @Autowired private IPraticaService service;
    
    @Autowired private IstrPraticaService istrPraticaService;
    
    @Autowired private FascicoloService fascicoloService;

    @Autowired private IConfigurazioniEnteService configurazioniEnteService;
    
    @Autowired private PraticaDelegatoService pdService;
    

    /**
     * search
     */
    @PostMapping(value="/search.pjson", produces=MEDIA_TYPE)
    public ResponseEntity<BaseRestResponse<PaginatedList<PraticaDTO>>> search(@RequestBody PraticaSearch search){
        LOGGER.info("Start search");
        final StopWatch sw = LogUtil.startLog("search");
        try {
        	checkAbilitazioneFor(Gruppi.RICHIEDENTI);
        	search.setOwner(SecurityUtil.getUsername());
        	this.istrPraticaService.prepareForSearch(search);
        	PaginatedList<PraticaDTO> res = this.service.search(search);
			//controllo se alcuni campi devono essere visibili
        	if (res!=null && res.getList()!=null && !res.getList().isEmpty()) {
        		Boolean statoAvanzamentoVisibile = true;
        		Set<Integer> idOrganizzazioni = new HashSet<>();
        		res.getList().forEach(elem->{
        			idOrganizzazioni.add(Integer.valueOf(elem.getEnteDelegato()));
        		});
        		List<TipologicaIntegerBooleanDto> infoConf = configurazioniEnteService.visibilitaStatoAvanzamento(idOrganizzazioni);
        		res.getList().forEach(elem->{
        			Boolean statoAvanzamentoConfig = null;
        			if (infoConf.stream().filter(o->o.getIntero().equals(Integer.valueOf(elem.getEnteDelegato()))).findAny().isPresent()) {
        				statoAvanzamentoConfig = infoConf.stream().filter(o->o.getIntero().equals(Integer.valueOf(elem.getEnteDelegato()))).findFirst().get().getBooleano();
        			}
	        		if (!statoAvanzamentoVisibile.equals(statoAvanzamentoConfig)) {
	        			elem.setDataTrasmissioneParere(null);
	        			elem.setDataTrasmissioneRelazione(null);
	        			elem.setDataTrasmissioneVerbale(null);
	        			elem.setStatoSedutaCommissione(null);
	        			elem.setStatoParereSoprintendenza(null);
	        			elem.setStatoRelazioneEnte(null);
	        		}
        		});
        	}
            return super.okResponse(res);
        }catch(Exception e) {
            LOGGER.error("Error in search", e);
            return super.koResponse(e, new BaseRestResponse<>());
        }finally {
            LOGGER.info(LogUtil.stopLog(sw));
        }
    }

    /**
     * insert
     * non piu' utilizzato da FE
     */
    @Deprecated
    @PostMapping(value="/insert.pjson", produces=MEDIA_TYPE)
    public ResponseEntity<BaseRestResponse<FascicoloDto>> insert(@RequestBody PraticaDTO dto){
        LOGGER.info("Start insert");
        final StopWatch sw = LogUtil.startLog("insert");
        try {
        	checkAbilitazioneFor(Gruppi.RICHIEDENTI);
        	FascicoloDto fascicoloCreato =fascicoloService.creaNuovaPratica(dto);
            return super.okResponse(fascicoloCreato);
        }catch(Exception e) {
            LOGGER.error("Error in insert", e);
            return super.koResponse(e, new BaseRestResponse<>());
        }finally {
            LOGGER.info(LogUtil.stopLog(sw));
        }
    }
    
    @PostMapping(value="/creaPratica", produces=MEDIA_TYPE)
    public ResponseEntity<BaseRestResponse<FascicoloDto>> insert(@RequestPart("data") PraticaDTO dto,
	    							 @RequestPart(value = "delegato", required = false) PraticaDelegatoDTO delegato,
	    							 @RequestPart(value = "delega", required = false) MultipartFile delega){
        LOGGER.info("creaPratica");
        final StopWatch sw = LogUtil.startLog("insert");
        try {
        	checkAbilitazioneFor(Gruppi.RICHIEDENTI);
        	if(dto.getRuoloPratica().equals("DE"))
        	{
        	    logger.info("Valido delegato e file");
        	    this.pdService.validaDelegato(delegato, delega);
        	}
        	FascicoloDto fascicoloCreato =fascicoloService.creaNuovaPratica(dto, delegato, delega);
            return super.okResponse(fascicoloCreato);
        }catch(Exception e) {
            LOGGER.error("Error in insert", e);
            return super.koResponse(e, new BaseRestResponse<>());
        }finally {
            LOGGER.info(LogUtil.stopLog(sw));
        }
    }
    
}
