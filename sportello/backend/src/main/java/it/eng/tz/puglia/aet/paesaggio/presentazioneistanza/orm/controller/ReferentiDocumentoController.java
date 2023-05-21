package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.controller;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.*;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.controller.BaseRestController;

/**
 * Controller CRUD for table presentazione_istanza.referenti_documento
 */
@Controller
@RequestMapping("/ReferentiDocumento")
public class ReferentiDocumentoController extends BaseRestController{

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ReferentiDocumentoController.class);
    /**
     * service
     */
    @Autowired
    private IReferentiDocumentoService service;

    /**
     * select all
     */
    @GetMapping(value="/select.pjson", produces=MEDIA_TYPE)
    public ResponseEntity<BaseRestResponse<List<ReferentiDocumentoDTO>>> select(){
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
    public ResponseEntity<BaseRestResponse<ReferentiDocumentoDTO>> find(@RequestBody ReferentiDocumentoDTO pk){
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
    public ResponseEntity<BaseRestResponse<PaginatedList<ReferentiDocumentoDTO>>> search(@RequestBody ReferentiDocumentoSearch search){
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
     * insert
     */
    @PostMapping(value="/insert.pjson", produces=MEDIA_TYPE)
    public ResponseEntity<BaseRestResponse<Long>> insert(@RequestBody ReferentiDocumentoDTO dto){
        LOGGER.info("Start insert");
        final StopWatch sw = LogUtil.startLog("insert");
        try {
            return super.okResponse(this.service.insert(dto));
        }catch(Exception e) {
            LOGGER.error("Error in insert", e);
            return super.koResponse(e, new BaseRestResponse<>());
        }finally {
            LOGGER.info(LogUtil.stopLog(sw));
        }
    }

    /**
     * update
     */
    @PostMapping(value="/update.pjson", produces=MEDIA_TYPE)
    public ResponseEntity<BaseRestResponse<Integer>> update(@RequestBody ReferentiDocumentoDTO dto){
        LOGGER.info("Start update");
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
     * delete
     */
    @PostMapping(value="/delete.pjson", produces=MEDIA_TYPE)
    public ResponseEntity<BaseRestResponse<Integer>> delete(@RequestBody ReferentiDocumentoDTO dto){
        LOGGER.info("Start delete");
        final StopWatch sw = LogUtil.startLog("delete");
        try {
            return super.okResponse(this.service.delete(dto));
        }catch(Exception e) {
            LOGGER.error("Error in delete", e);
            return super.koResponse(e, new BaseRestResponse<>());
        }finally {
            LOGGER.info(LogUtil.stopLog(sw));
        }
    }

}
