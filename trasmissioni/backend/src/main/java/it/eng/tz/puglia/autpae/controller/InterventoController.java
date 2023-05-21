package it.eng.tz.puglia.autpae.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.eng.tz.puglia.autpae.dto.InterventoTabDTO;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.enumeratori.TipoProcedimento;
import it.eng.tz.puglia.autpae.service.interfacce.InterventoService;
import it.eng.tz.puglia.autpae.utility.LoggingAet;
import it.eng.tz.puglia.bean.BaseRestResponse;

@RestController
@RequestMapping(value = "intervento", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class InterventoController extends _RestController {

	private static final Logger log = LoggerFactory.getLogger(InterventoController.class);
	
	@Autowired private InterventoService interventoService;
	
	@GetMapping(value = "/get")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<InterventoTabDTO>> tabIntervento(
			@RequestParam(name="codiceTipoProcedimento", required=true) TipoProcedimento tipoProcedimento,
			@RequestParam(name="idFascicolo",required=true)Long idFascicolo) throws Exception {
			super.checkPermission(idFascicolo);
			return super.okResponse(interventoService.tabIntervento(tipoProcedimento,idFascicolo));
	}
	
	@GetMapping(value = "/dati")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<List<Long>>> datiIntervento(@RequestParam(name="idFascicolo", required=true) long idFascicolo) throws Exception {
			super.checkPermission(idFascicolo);
			return super.okResponse(interventoService.datiIntervento(idFascicolo));
	}
	
}