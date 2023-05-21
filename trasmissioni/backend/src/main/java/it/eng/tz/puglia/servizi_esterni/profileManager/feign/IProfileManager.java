package it.eng.tz.puglia.servizi_esterni.profileManager.feign;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.eng.tz.puglia.autpae.config.FeignConfiguration;
import it.eng.tz.puglia.cache.ICacheConstants;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.AssUtenteGruppo;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.GruppoBean;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.PMApplication;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.PMGroupsRequestBean;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.PM_GruppiRuoli;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.PayloadRichiestaAbilitazione;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.PayloadUserResponse;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.RichiestaAbilitazioneProfileManagerRequestBean;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.UtenteGruppo;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.response.ResponseBase;


@FeignClient(name = "pm", url = "${microservice.pm.url}", configuration = FeignConfiguration.class)
public interface IProfileManager {

	@Cacheable(keyGenerator=ICacheConstants.KEY_CONTEXT_NAME, value="loggedUserInfo", unless="#result == null")
	@RequestMapping(method = RequestMethod.GET, value = "/logged/user/info")
	ResponseBase<PayloadUserResponse> getLoggedUser(@RequestHeader("Authorization")  	 String jwt, // non più DEMO
													@RequestParam ("codiceApplicazione") String codiceApplicazione);

	
	@RequestMapping(method = RequestMethod.GET, value = "/groups/findAllGruppiNomeECodice")
	ResponseBase<GruppoBean[]> getGruppiUtente(@RequestHeader("Authorization")  	String jwt, // non più DEMO
											   @RequestParam ("codiceApplicazione") String codiceApplicazione, 
											   @RequestParam ("usernameUtente")     String usernameUtente);

	
	@RequestMapping(method = RequestMethod.POST, value = "/requests/save")
	ResponseBase<PayloadRichiestaAbilitazione> sendRichiestaAbilitazione(@RequestHeader("Authorization") String jwt, // non più DEMO
																		 @RequestBody RichiestaAbilitazioneProfileManagerRequestBean beanRichiesta);
	

	/**
	 * Il filtro codice gruppo non effettua nessun filtro
	 * il filtro usaLike non è più presente
	 * @param jwt
	 * @param codiceApplicazione
	 * @param codiceGruppo
	 * @param usernameUtente
	 * @param usaLike
	 * @param colonna
	 * @param direzione
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/users/findAllUsers")
	ResponseBase<UtenteGruppo[]> getUtentiGruppo(@RequestHeader("Authorization")  	  String  jwt,  // non più DEMO
											     @RequestParam ("codiceApplicazione") String  codiceApplicazione, 
											     @RequestParam ("codiceGruppo")       String  codiceGruppo,
											     @RequestParam ("usernameUtente")     String  usernameUtente,
											     @RequestParam ("usaLike")       	  boolean usaLike,
											     @RequestParam ("colonna")       	  String  colonna,
												 @RequestParam ("direzione")       	  String  direzione);
	
	/**
	 * Il filtro codice gruppo non effettua nessun filtro
	 * il filtro usaLike non è più presente
	 * @param jwt
	 * @param codiceApplicazione
	 * @param codiceGruppo
	 * @param usernameUtente
	 * @param usaLike
	 * @param colonna
	 * @param direzione
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/users/findAllUsers")
	ResponseBase<UtenteGruppo[]> getUtentiGruppo(@RequestHeader("Authorization")  	  String  jwt,  // non più DEMO
											     @RequestParam ("codiceApplicazione") String  codiceApplicazione, 
											     @RequestParam ("codiceGruppo")       String  codiceGruppo,
											     @RequestParam ("usernameUtente")     String  usernameUtente,
											     @RequestParam ("usaLike")       	  boolean usaLike);
											     
	
	@RequestMapping(method = RequestMethod.GET, value="/queries/findAllGruppi")
	ResponseBase<List<PM_GruppiRuoli>> findGruppiFromUserDetails(@RequestHeader("Authorization") String  jwt, 
																 @RequestParam("codiceApplicazione")String codiceApplicazione, 
																 @RequestParam("usernameUtente")String username );
	
	@RequestMapping(method = RequestMethod.GET, value = "/applications/findbycode/{codiceApplicazione}")
	@Cacheable(keyGenerator=ICacheConstants.KEY_CONTEXT_NAME, value="retrieveApp", unless="#result == null")
	ResponseBase<PMApplication> findAppByCode(@RequestHeader("Authorization") String  jwt, @PathVariable("codiceApplicazione") String codice);
	
	@RequestMapping(method = RequestMethod.GET, value="/queries/findAllGruppi")
	ResponseBase<List<PM_GruppiRuoli>> findGruppiDetails(@RequestHeader("Authorization") String  jwt, 
														 @RequestParam("codiceApplicazione")String codiceApplicazione, 
														 @RequestParam("codiceGruppo")String codiceGruppo);
	
	@RequestMapping(method = RequestMethod.POST, value = "/groups/save")
	ResponseBase<PMGroupsRequestBean> saveGroup(@RequestHeader("Authorization") String  jwt, @RequestBody PMGroupsRequestBean body);
	
	@RequestMapping(method = RequestMethod.PUT, value = "/groups/update")
	ResponseBase<Integer> updateGroup(@RequestHeader("Authorization") String  jwt, @RequestBody PMGroupsRequestBean body);
	
	/**
	 * torna valori duplicati... se si passano piu' gruppi e l'utente appartiene a piu' gruppi
	 * @author acolaianni
	 *
	 * @param jwt
	 * @param codiceApplicazione
	 * @param codiciGruppo
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/users/utentiInGruppi")
	ResponseBase<AssUtenteGruppo[]> utentiInGruppi(@RequestHeader("Authorization")  String  jwt, 
											    @RequestParam ("codiceApplicazione") String  codiceApplicazione, 
											    @RequestParam ("codiceGruppo")       List<String>  codiciGruppo);
	
}