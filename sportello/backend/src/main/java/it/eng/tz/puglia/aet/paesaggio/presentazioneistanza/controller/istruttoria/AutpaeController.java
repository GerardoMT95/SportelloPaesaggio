package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.istruttoria;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.eng.tz.puglia.aet.paesaggio.bean.MyOrganizzazioniBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.RoleAwareController;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PraticaSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.IstrPraticaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.Logging;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.util.list.ListUtil;

@RestController
@RequestMapping("istruttoria/autpae")
public class AutpaeController extends RoleAwareController
{
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String MYORGTERRITORIO	= "/myorgterritorio";
	private static final String PRATICHE_WITH_GROUP	= "/gruppiaccessopratiche";
	
	@Qualifier("istrPraticaService") 
	@Autowired 
	IstrPraticaService praticaService;
	
	@Autowired
	UserUtil userUtil;
	
	/**
	 * utilizzato da autpae per prelevare la mappa con organizzazioni di appartenenza e in caso
	 * di SBAP ed ETI anche i relativi comuni di competenza. 
	 * @author acolaianni
	 *
	 * @return
	 * @throws Exception
	 */
	@Logging
	@GetMapping(value = MYORGTERRITORIO,produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<MyOrganizzazioniBean>> organizzazioniTerritorio() throws Exception {
		return super.okResponse(this.praticaService.organizzazioniEComunidiCompetenza());
	}
	
	/**
	 * data una lista di codici trasmissione pratiche, effettua check sui gruppi utente a cui appartengo
	 * e se becca uno che mi permette di accedere al dettaglio pratica, lo restituisce nella mappa in output:
	 * es. {AUTPAESAF-10-2020=ED_109_F,AUTPAESAF-11-2020=ED_109_D}
	 * @author acolaianni
	 *
	 * @param codiciTrasmissione
	 * @return
	 * @throws Exception
	 */
	@Logging
	@PostMapping(value = PRATICHE_WITH_GROUP,produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Map<String,String>>> praticheGruppo(@RequestBody List<String> codiciTrasmissione) throws Exception {
		Map<String,String> mapOut=new HashMap<>();
		List<String> myGruppi = userUtil.getMyEnti().stream().map(ente->ente.getCodiceGruppo()).collect(Collectors.toList());
		if(ListUtil.isEmpty(codiciTrasmissione)) {
			return super.okResponse(mapOut);
		}
		//cerco per set di codici trasmissione e se becco pratiche, smaltisco per la successiva richiesta
		for(String codiceGruppo:myGruppi) {
			PraticaSearch psearch = new PraticaSearch();
			List<String> codiciToSearch = codiciTrasmissione.stream().filter(codice->mapOut.get(codice)==null).collect(Collectors.toList());
			if(ListUtil.isEmpty(codiciToSearch)) {
				//ho trovato per tutti il codice gruppo con cui accedere
				break;
			}
			psearch.setCodiciTrasmissione(codiciToSearch);
			psearch.setLimit(100);
			this.praticaService.prepareForSearch(psearch, codiceGruppo, SecurityUtil.getUsername());
			List<PraticaDTO> pratiche = this.praticaService.searchAll(psearch, false);
			if(ListUtil.isNotEmpty(pratiche)) {
				for(PraticaDTO praticaSearched:pratiche) {
					if(codiciTrasmissione.contains(praticaSearched.getCodiceTrasmissione())) {
						mapOut.put(praticaSearched.getCodiceTrasmissione(),codiceGruppo);
					}
				}
			}
		}
		return super.okResponse(mapOut);
	}
}
