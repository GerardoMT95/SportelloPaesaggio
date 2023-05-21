//package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.FascicoloDto;
//import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperDomandaDichiarazioneTecnicaDto;
//import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperDomandaIstanzaDto;
//import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.adapter.JasperDichiarazioneIstanzaAdapter;
//import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.adapter.JasperDichiarazioneTecnicaAdapter;
//import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.FascicoloService;
//import it.eng.tz.puglia.bean.BaseRestResponse;
//import it.eng.tz.puglia.controller.BaseRestController;
//
//@RestController
//@RequestMapping("public/test")
//public class Test extends BaseRestController
//{
//	private final Logger logger = LoggerFactory.getLogger(getClass());
//	
//	@Autowired
//	private FascicoloService service;
//	
//	@Autowired
//	JasperDichiarazioneTecnicaAdapter adapter;
//	@Autowired
//	JasperDichiarazioneIstanzaAdapter istanzaAdapter;
//	
//	@GetMapping("test")
//	public ResponseEntity<BaseRestResponse<Boolean>> methodTest(@RequestParam("codice")String codice) throws Exception
//	{
//		FascicoloDto fascicolo = new FascicoloDto();
//		fascicolo.setCodicePraticaAppptr(codice);
//		fascicolo = service.find(fascicolo);
//		JasperDomandaIstanzaDto jasper = istanzaAdapter.adapt(fascicolo);
//		logger.info("jasper -> {}", jasper);
//		return okResponse(true);
//	}
//	
//	@GetMapping("testDichiarazione")
//	public ResponseEntity<BaseRestResponse<Boolean>> methodTestDichiarazione(@RequestParam("codice")String codice) throws Exception
//	{
//		//JasperDichiarazioneTecnicaAdapter adapter = new JasperDichiarazioneTecnicaAdapter();
//		FascicoloDto fascicolo = new FascicoloDto();
//		fascicolo.setCodicePraticaAppptr(codice);
//		fascicolo = service.find(fascicolo);
//		JasperDomandaDichiarazioneTecnicaDto jasper = adapter.adapt(fascicolo);
//		logger.info("jasper -> {}", jasper);
//		return okResponse(true);
//	}
//}
