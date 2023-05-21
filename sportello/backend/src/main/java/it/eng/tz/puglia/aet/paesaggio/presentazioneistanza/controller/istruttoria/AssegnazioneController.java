package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.istruttoria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.RoleAwareController;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.AssegnamentoFascicoloNewDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.AssegnamentoFascicoloOldDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.ConfigurazioneAssegnamentoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.StoricoAssegnamentoNewDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.StoricoAssegnamentoOldDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.TabelleAssegnamentoFascicoliNewDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.TabelleAssegnamentoFascicoliOldDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.ValoriAssegnamentoNewDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.enumetori.TipoOperazione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search.TabelleAssegnamentoFascicoliSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.service.interfacce.AssegnamentoFascicoloService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.service.interfacce.ConfigurazioneAssegnamentoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.service.interfacce.StoricoAssegnamentoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringUtentiValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Ruoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.PaginatedList;

@RestController
@RequestMapping(value = "istruttoria/assegnazione", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AssegnazioneController extends RoleAwareController {

	private static final Logger log = LoggerFactory.getLogger(AssegnazioneController.class);

	@Autowired private AssegnamentoFascicoloService assegnamentoFascicoloService;
	@Autowired private StoricoAssegnamentoService storicoAssegnamentoService;
	@Autowired private ConfigurazioneAssegnamentoService configurazioneAssegnamentoService;
	@Autowired private UserUtil userUtil;
//	@Autowired private ValoriAssegnamentoService valoriAssegnamentoService;


	
	@GetMapping(value = "/getUtentiForOrganizzazione")
	public ResponseEntity<BaseRestResponse<List<PlainStringUtentiValueLabel>>> getUtentiForOrganizzazione() throws Exception {
		log.info("Chiamato il controller: 'assegnazione'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE, Ruoli.DIRIGENTE);	
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.SBAP_, Gruppi.ETI_);
		return super.okResponse(configurazioneAssegnamentoService.getUtentiForOrganizzazione());
	}
	
/*	@GetMapping(value = "/getFunzionariForOrganizzazione")
	public ResponseEntity<BaseRestResponse<List<PlainStringValueLabel>>> getFunzionariForOrganizzazione() throws Exception {
		log.info("Chiamato il controller: 'assegnazione'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE, Ruoli.DIRIGENTE);	checkAbilitazioneFor(Gruppi.ED_, Gruppi.SBAP_, Gruppi.ETI_);
		return super.okResponse(configurazioneAssegnamentoService.getFunzionariForOrganizzazione());
	}	*/
	
/*	@GetMapping(value = "/getRupForOrganizzazione")
	public ResponseEntity<BaseRestResponse<List<PlainStringValueLabel>>> getRupForOrganizzazione() throws Exception {
		log.info("Chiamato il controller: 'assegnazione'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE, Ruoli.DIRIGENTE);	checkAbilitazioneFor(Gruppi.ED_, Gruppi.SBAP_, Gruppi.ETI_);
		return super.okResponse(configurazioneAssegnamentoService.getRupForOrganizzazione());
	}	*/
	
/*	@GetMapping(value = "/getPrefissoCodice")
	public ResponseEntity<BaseRestResponse<String>> getPrefissoCodice() throws Exception {
		log.info("Chiamato il controller: 'assegnazione'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE, Ruoli.DIRIGENTE);	checkAbilitazioneFor(Gruppi.ED_, Gruppi.SBAP_, Gruppi.ETI_);
		return super.okResponse(assegnamentoFascicoloService.getPrefissoCodice());
	}	*/
	
	@GetMapping(value = "/autocompleteCodice")
	public ResponseEntity<BaseRestResponse<List<String>>> autocompleteCodice(@RequestParam(value="codice"	   , required=false) String  codice,
																			 @RequestParam(value="giaAssegnato", required=true ) boolean giaAssegnato) throws Exception {
		log.info("Chiamato il controller: 'amministratore'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE, Ruoli.DIRIGENTE);	checkAbilitazioneFor(Gruppi.ED_, Gruppi.SBAP_, Gruppi.ETI_);
		if (giaAssegnato==true)
			return super.okResponse(assegnamentoFascicoloService.autocompleteCodiceFascicoliAssegnati   (codice));
		else
			return super.okResponse(assegnamentoFascicoloService.autocompleteCodiceFascicoliNonAssegnati(codice));
	}
	
	@GetMapping(value = "/getConfigurazioneForOrganizzazione")
	public ResponseEntity<BaseRestResponse<ConfigurazioneAssegnamentoDTO>> getConfigurazioneForOrganizzazione() throws Exception {
		log.info("Chiamato il controller: 'assegnazione'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE, Ruoli.DIRIGENTE);	checkAbilitazioneFor(Gruppi.ED_, Gruppi.SBAP_, Gruppi.ETI_);
		return super.okResponse(configurazioneAssegnamentoService.getConfigurazioneForOrganizzazioneUtenteLoggato());
	}

	@PostMapping(value = "/saveConfigurazioneForOrganizzazione")
	public ResponseEntity<BaseRestResponse<Boolean>> saveConfigurazioneForOrganizzazione(@RequestBody ConfigurazioneAssegnamentoDTO configurazione) throws Exception {
		log.info("Chiamato il controller: 'assegnazione'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE, Ruoli.DIRIGENTE);	checkAbilitazioneFor(Gruppi.ED_, Gruppi.SBAP_, Gruppi.ETI_);
		configurazione.setRup(true ); boolean resR = configurazioneAssegnamentoService.saveConfigurazioneForOrganizzazione(configurazione);
		configurazione.setRup(false); boolean resF = configurazioneAssegnamentoService.saveConfigurazioneForOrganizzazione(configurazione);
		return super.okResponse(resR && resF);
	}

	@GetMapping(value = "/configurazioneAutomaticaSearch")
	public ResponseEntity<BaseRestResponse<List<ValoriAssegnamentoNewDTO>>> configurazioneAutomaticaSearch(@RequestPart  ConfigurazioneAssegnamentoDTO configurazione,
																										   @RequestParam(name="idComuneTipoProcedimento", required=false) Integer idComuneTipoProcedimento,
																										   @RequestParam(name="usernameFunzionario"	    , required=false) String  usernameFunzionario	  ) throws Exception {
		log.info("Chiamato il controller: 'assegnazione'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE, Ruoli.DIRIGENTE);	checkAbilitazioneFor(Gruppi.ED_, Gruppi.SBAP_, Gruppi.ETI_);
		return super.okResponse(configurazioneAssegnamentoService.configurazioneAutomaticaSearch(configurazione, idComuneTipoProcedimento, usernameFunzionario));
	}
	
	@GetMapping(value = "/assegnamentoFascicoliSearch")
	public ResponseEntity<BaseRestResponse<PaginatedList<TabelleAssegnamentoFascicoliNewDTO>>> tabellaAssegnamentoFascicoliSearch(TabelleAssegnamentoFascicoliSearch filter) throws Exception {
		log.info("Chiamato il controller: 'assegnazione'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE, Ruoli.DIRIGENTE);	
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.SBAP_, Gruppi.ETI_,Gruppi.REG_);
//		if (filter.isGiaAssegnato()==true) {
//			filter.setLimit(filter.getLimit()*2);
//		}
		Gruppi myGruppo = userUtil.getGruppo();
		if(Gruppi.hasRup(myGruppo)) {
			filter.setRup(true);
		}else {
			filter.setRup(false);
		}
		PaginatedList<TabelleAssegnamentoFascicoliOldDTO> tabelle = assegnamentoFascicoloService.tabelleAssegnamentoFascicoliSearch(filter);
		if (tabelle==null || tabelle.getList()==null || tabelle.getList().isEmpty()) {
			return super.okResponse(new PaginatedList<TabelleAssegnamentoFascicoliNewDTO>(new ArrayList<TabelleAssegnamentoFascicoliNewDTO>(), 0));
		}
		else {
			List<TabelleAssegnamentoFascicoliOldDTO> listaOld = tabelle.getList();
//			if (filter.isGiaAssegnato()==false) {
				List<TabelleAssegnamentoFascicoliNewDTO> listaNew = new ArrayList<TabelleAssegnamentoFascicoliNewDTO>(listaOld.size());
				listaOld.forEach(elem->{
					listaNew.add(new TabelleAssegnamentoFascicoliNewDTO(elem));
				});
				return super.okResponse(new PaginatedList<TabelleAssegnamentoFascicoliNewDTO>(listaNew, tabelle.getCount()));
//			}
//			else {
				//non serve piu' mergiare.... arriva già un solo oggetto per assegnazione riferito allo user e non al rup...
//				Collections.sort(listaOld, new Comparator<TabelleAssegnamentoFascicoliOldDTO>() {	// ordino in base al campo "data Operazione"
//					public int compare(TabelleAssegnamentoFascicoliOldDTO o1, TabelleAssegnamentoFascicoliOldDTO o2) {
//						return (o1.getUltimaOperazione()).compareTo(o2.getUltimaOperazione());
//					}
//				});
//				List<TabelleAssegnamentoFascicoliNewDTO> listaNew = new ArrayList<TabelleAssegnamentoFascicoliNewDTO>(listaOld.size()/2);
//				for (int i=0; i<listaOld.size(); i++) {
//					if(i==0 || i%2==0) {
//						listaNew.add(new TabelleAssegnamentoFascicoliNewDTO(listaOld.get(i), listaOld.size()>i+1?listaOld.get(i+1):null));
//					}
//				}
//				return super.okResponse(new PaginatedList<TabelleAssegnamentoFascicoliNewDTO>(listaNew, tabelle.getCount()/2));
//			}
		}
	}
	
	@GetMapping(value = "/getStoricoAssegnamento")
	public ResponseEntity<BaseRestResponse<List<StoricoAssegnamentoNewDTO>>> getStoricoAssegnamento(@RequestParam(name="idFascicolo", required=true) UUID idFascicolo) throws Exception {
		log.info("Chiamato il controller: 'assegnazione'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE, Ruoli.DIRIGENTE);	checkAbilitazioneFor(Gruppi.ED_, Gruppi.SBAP_, Gruppi.ETI_);
		List<StoricoAssegnamentoOldDTO> storicoOld = storicoAssegnamentoService.getStoricoAssegnamento(idFascicolo);
		return super.okResponse(storicoAssegnamentoService.creaNewDTO(storicoOld));
	}
	
	@PostMapping(value = "/assegnaFascicolo")
	public ResponseEntity<BaseRestResponse<AssegnamentoFascicoloNewDTO>> assegnaFascicolo(@RequestBody AssegnamentoFascicoloNewDTO body) throws Exception {
		log.info("Chiamato il controller: 'assegnazione'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE, Ruoli.DIRIGENTE);	checkAbilitazioneFor(Gruppi.ED_, Gruppi.SBAP_, Gruppi.ETI_);
		body.setDataOperazione(new Date());
		AssegnamentoFascicoloOldDTO rup 		= assegnamentoFascicoloService.assegnamentoFascicolo(new AssegnamentoFascicoloOldDTO(body, true ), TipoOperazione.ASSEGNAZIONE);
		AssegnamentoFascicoloOldDTO funzionario = assegnamentoFascicoloService.assegnamentoFascicolo(new AssegnamentoFascicoloOldDTO(body, false), TipoOperazione.ASSEGNAZIONE);
		return super.okResponse(new AssegnamentoFascicoloNewDTO(rup, funzionario));
	}
	
	@PostMapping(value = "/riassegnaFascicolo")
	public ResponseEntity<BaseRestResponse<AssegnamentoFascicoloNewDTO>> riassegnaFascicolo(@RequestBody AssegnamentoFascicoloNewDTO body) throws Exception {
		log.info("Chiamato il controller: 'assegnazione'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE, Ruoli.DIRIGENTE);	checkAbilitazioneFor(Gruppi.ED_, Gruppi.SBAP_, Gruppi.ETI_);
		body.setDataOperazione(new Date());
		AssegnamentoFascicoloOldDTO rup 		= assegnamentoFascicoloService.assegnamentoFascicolo(new AssegnamentoFascicoloOldDTO(body, true ), TipoOperazione.RIASSEGNAZIONE);
		AssegnamentoFascicoloOldDTO funzionario = assegnamentoFascicoloService.assegnamentoFascicolo(new AssegnamentoFascicoloOldDTO(body, false), TipoOperazione.RIASSEGNAZIONE);
		return super.okResponse(new AssegnamentoFascicoloNewDTO(rup, funzionario));
	}
	
	@PostMapping(value = "/disassegnaFascicolo")
	public ResponseEntity<BaseRestResponse<AssegnamentoFascicoloNewDTO>> disassegnaFascicolo(@RequestBody AssegnamentoFascicoloNewDTO body) throws Exception {
		log.info("Chiamato il controller: 'assegnazione'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE, Ruoli.DIRIGENTE);	checkAbilitazioneFor(Gruppi.ED_, Gruppi.SBAP_, Gruppi.ETI_);
		body.setDataOperazione(new Date());
		AssegnamentoFascicoloOldDTO rup 		= assegnamentoFascicoloService.assegnamentoFascicolo(new AssegnamentoFascicoloOldDTO(body, true ), TipoOperazione.DISASSEGNAZIONE);
		AssegnamentoFascicoloOldDTO funzionario = assegnamentoFascicoloService.assegnamentoFascicolo(new AssegnamentoFascicoloOldDTO(body, false), TipoOperazione.DISASSEGNAZIONE);
		return super.okResponse(new AssegnamentoFascicoloNewDTO(rup, funzionario));
	}
	
}