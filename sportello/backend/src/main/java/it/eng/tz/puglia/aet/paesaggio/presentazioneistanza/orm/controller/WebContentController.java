package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.RoleAwareController;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Ruoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.WebContentDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.WebContentSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IWebContentService;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.log.LogUtil;


/**
 * Controller CRUD for table presentazione_istanza.web_content
 */
@Controller
@RequestMapping("/WebContentConfigurabili")
public class WebContentController extends RoleAwareController{

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WebContentController.class);
    /**
     * service
     */
    @Autowired
    private IWebContentService service;

    /**
     * select all
     */
    @GetMapping(value="/select.pjson", produces=MEDIA_TYPE)
    public ResponseEntity<BaseRestResponse<List<WebContentDTO>>> select(){
        LOGGER.info("Start select");
        final StopWatch sw = LogUtil.startLog("select");
        try {
            return super.okResponse(this.service.select());
        }catch(Exception e) {
            LOGGER.error("Error in select", e);
            return super.koResponse(e, new BaseRestResponse<>());
        }finally {
            LOGGER.info(LogUtil.stopLog(sw));
        }
    }

    /**
     * count all
     */
    @GetMapping(value="/count.pjson", produces=MEDIA_TYPE)
    public ResponseEntity<BaseRestResponse<Long>> count(){
        LOGGER.info("Start select");
        final StopWatch sw = LogUtil.startLog("select");
        try {
            return super.okResponse(this.service.count());
        }catch(Exception e) {
            LOGGER.error("Error in count", e);
            return super.koResponse(e, new BaseRestResponse<>());
        }finally {
            LOGGER.info(LogUtil.stopLog(sw));
        }
    }

    /**
     * find by pk
     */
    @PostMapping(value="/find.pjson", produces=MEDIA_TYPE)
    public ResponseEntity<BaseRestResponse<WebContentDTO>> find(@RequestBody WebContentDTO pk){
        LOGGER.info("Start find");
        final StopWatch sw = LogUtil.startLog("find");
        try {
            return super.okResponse(this.service.find(pk));
        }catch(Exception e) {
            LOGGER.error("Error in find", e);
            return super.koResponse(e, new BaseRestResponse<>());
        }finally {
            LOGGER.info(LogUtil.stopLog(sw));
        }
    }

    /**
     * search
     */
    @PostMapping(value="/search.pjson", produces=MEDIA_TYPE)
    public ResponseEntity<BaseRestResponse<PaginatedList<WebContentDTO>>> search(@RequestBody WebContentSearch search){
        LOGGER.info("Start search");
        final StopWatch sw = LogUtil.startLog("search");
        try {
            return super.okResponse(this.service.search(search));
        }catch(Exception e) {
            LOGGER.error("Error in search", e);
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
    public ResponseEntity<BaseRestResponse<Integer>> update(@RequestBody WebContentDTO dto) throws CustomOperationToAdviceException{
        LOGGER.info("Start update");
        checkAbilitazioneFor(Ruoli.ADMIN);
        final StopWatch sw = LogUtil.startLog("update");
        try {
            return super.okResponse(this.service.update(dto));
        }catch(Exception e) {
            LOGGER.error("Error in update", e);
            return super.koResponse(e, new BaseRestResponse<>());
        }finally {
            LOGGER.info(LogUtil.stopLog(sw));
        }
    }
    
    /**
     * update
     * restituisce il numero di record aggiornati
     * @throws CustomOperationToAdviceException 
     */
    @PostMapping(value="/updateSelected.pjson", produces=MEDIA_TYPE)
    public ResponseEntity<BaseRestResponse<Integer>> update(@RequestBody List<WebContentDTO> dtos) throws CustomOperationToAdviceException{
        LOGGER.info("Start updateSelected");
        checkAbilitazioneFor(Ruoli.ADMIN);
        final StopWatch sw = LogUtil.startLog("updateSelected");
        try {
        	return super.okResponse(this.service.updateSelected(dtos));
        }catch(Exception e) {
            LOGGER.error("Error in updateSelected", e);
            return super.koResponse(e, new BaseRestResponse<>());
        }finally {
            LOGGER.info(LogUtil.stopLog(sw));
        }
    }
    
}
