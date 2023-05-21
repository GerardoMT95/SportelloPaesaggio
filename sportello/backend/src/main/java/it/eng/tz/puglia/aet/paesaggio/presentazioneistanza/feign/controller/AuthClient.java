package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.FeignConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.AssUtenteGruppo;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.AutomaticUserGroupRequestBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.GruppoBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.PMApplication;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.PMGroupsRequestBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.PM_GruppiRuoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.PayloadRichiestaAbilitazione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.PayloadUserResponse;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.ResponseBase;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.RichiestaAbilitazioneProfileManagerRequestBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.UtenteGruppo;
import it.eng.tz.puglia.cache.ICacheConstants;


/**
 * questa classe fa uso della configurazione in FeignConfiguration e in application.properties  
 * ha una properties che ha il nome del package di mezzo:
 * logging.level.<packagepath>.NomeInterfaceAnnotataFeign: DEBUG
 * @author acolaianni
 *
 */
@FeignClient(contextId="profileManagerContext", name="AuthClient", url="${microservice.pm.url.value}", configuration=FeignConfiguration.class)
public interface AuthClient {
		
	@RequestMapping(method = RequestMethod.GET, value = "/logged/user/info")
	@Cacheable(keyGenerator=ICacheConstants.KEY_CONTEXT_NAME, value="retrieveUser", unless="#result == null")
	ResponseBase<PayloadUserResponse> getLoggedUser(@RequestHeader("Authorization" ) String jwt,
													@RequestParam ("codiceApplicazione") String codiceApplicazione);
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/groups/findAllGruppiNomeECodice")
	ResponseBase<GruppoBean[]> getGruppiUtente(@RequestHeader("Authorization" ) String jwt, 
											   @RequestParam ("codiceApplicazione") String codiceApplicazione, 
											   @RequestParam ("usernameUtente") 	String usernameUtente);
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/requests/save")
	ResponseBase<PayloadRichiestaAbilitazione> sendRichiestaAbilitazione(@RequestHeader("Authorization") String jwt,  
																		 @RequestBody RichiestaAbilitazioneProfileManagerRequestBean beanRichiesta);
	
//	@RequestMapping(method = RequestMethod.GET, value = "/users/findAllUsers")
//	ResponseBase<UtenteGruppo[]> getUtentiGruppo(@RequestHeader("Authorization")  String  jwt, 
//											     @RequestParam ("codiceApplicazione") String  codiceApplicazione, 
//											     @RequestParam ("codiceGruppo")       String  codiceGruppo,
//											     @RequestParam ("usernameUtente")     String  usernameUtente,
//											     @RequestParam ("usaLike")       	  boolean usaLike,
//											     @RequestParam ("colonna")       	  String  colonna);
	@RequestMapping(method = RequestMethod.GET, value = "/users/findAllUsers")
	ResponseBase<UtenteGruppo[]> getUtentiGruppoOrdered(@RequestHeader("Authorization")  String  jwt, 
											     @RequestParam ("codiceApplicazione") String  codiceApplicazione, 
											     @RequestParam ("codiceGruppo")       String  codiceGruppo,
											     @RequestParam ("usernameUtente")     String  usernameUtente,
											     @RequestParam ("usaLike")       	  boolean usaLike,
											     @RequestParam ("colonna")       	  String  colonna,
											     @RequestParam ("direzione")       	  String  direzione);
	
//	@RequestMapping(method = RequestMethod.GET, value = "/users/findAllUsers")
//	ResponseBase<UtenteGruppo[]> getUtentiGruppo(@RequestHeader("Authorization")  String  jwt, 
//											     @RequestParam ("codiceApplicazione") String  codiceApplicazione, 
//											     @RequestParam ("codiceGruppo")       String  codiceGruppo);
	
	@RequestMapping(method = RequestMethod.GET, value = "/users/findAllUsers")
	ResponseBase<UtenteGruppo[]> getUtentiGruppo(@RequestHeader("Authorization")  String  jwt, 
											     @RequestParam ("codiceApplicazione") String  codiceApplicazione, 
											     @RequestParam ("codiceGruppo")       String  codiceGruppo, 
												 @RequestParam ("usernameUtente") 	  String  usernameUtente);
	
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
	
	@RequestMapping(method = RequestMethod.GET, value = "/applications/findbycode/{codiceApplicazione}")
	@Cacheable(keyGenerator=ICacheConstants.KEY_CONTEXT_NAME, value="retrieveApp", unless="#result == null")
	ResponseBase<PMApplication> findAppByCode(@RequestHeader("Authorization") String  jwt, @PathVariable("codiceApplicazione") String codice);
	
	/**
	 * se non si è responsabile applicaione su PM verrà restituito 401
	 * @author acolaianni
	 *
	 * @param jwt
	 * @param body
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/groups/save")
	ResponseBase<PMGroupsRequestBean> saveGroup(@RequestHeader("Authorization") String  jwt, @RequestBody PMGroupsRequestBean body);
	
	@RequestMapping(method = RequestMethod.PUT, value = "/groups/update")
	ResponseBase<Integer> updateGroup(@RequestHeader("Authorization") String  jwt, @RequestBody PMGroupsRequestBean body);
	
	@RequestMapping(method = RequestMethod.POST, value = "/userGroups/automaticUserGroup")
	ResponseBase<Boolean> insertUtente(@RequestHeader("Authorization") String  jwt, @RequestBody AutomaticUserGroupRequestBean body);
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/userGroups/delete")
	ResponseBase<Boolean> deleteUtente(@RequestHeader("Authorization") String  jwt, @RequestParam("idGruppo")String idGruppo, @RequestParam("idUtente")String idUtente);
	
	@RequestMapping(method = RequestMethod.GET, value="/queries/findAllGruppi")
	ResponseBase<List<PM_GruppiRuoli>> findGruppiDetails(@RequestHeader("Authorization") String  jwt, 
														 @RequestParam("codiceApplicazione")String codiceApplicazione, 
														 @RequestParam("codiceGruppo")String codiceGruppo);
	
	@RequestMapping(method = RequestMethod.GET, value="/queries/findAllGruppi")
	ResponseBase<List<PM_GruppiRuoli>> findGruppiFromUserDetails(@RequestHeader("Authorization") String  jwt, 
																 @RequestParam("codiceApplicazione")String codiceApplicazione, 
																 @RequestParam("usernameUtente")String username );
	
}