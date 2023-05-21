package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Ruoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.AuthClient;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.PM_GruppiRuoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.PayloadUserResponse;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.ResponseBase;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.PaesaggioOrganizzazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.service.RemoteSchemaService;
import it.eng.tz.puglia.bean.UserDetail;
import it.eng.tz.puglia.security.ICompleteUserService;
import it.eng.tz.puglia.util.log.LogUtil;

@Component
public class UserDetailPopulate implements ICompleteUserService {
	private static final Logger log = LoggerFactory.getLogger(UserDetailPopulate.class);

	@Autowired
	private AuthClient profileManager;
	
	@Autowired
	private UserUtil userUtil;
	
	@Autowired
	private RemoteSchemaService remoteSchemaService;
	
	@Value("${spring.application.name}")
	private String KEY_APPLICAZIONE_PROFILE_MANAGER;

	
//	private List<PM_GruppiRuoli> mockPMOffline(){
//		PM_GruppiRuoli gr = new PM_GruppiRuoli();
//		gr.setCodiceApplicazione(KEY_APPLICAZIONE_PROFILE_MANAGER);
//		gr.setCodiceGruppo(Gruppi.RICHIEDENTI.name());
//		gr.setDescrizioneGruppo(Gruppi.RICHIEDENTI.name());
//		return Collections.singletonList(gr);
//	}
	
	private List<PM_GruppiRuoli> getGruppiRuoliFromProfileManager(final String token) {
		ResponseBase<PayloadUserResponse> response = this.profileManager.getLoggedUser(token, this.KEY_APPLICAZIONE_PROFILE_MANAGER);
		return response.getPayload().getGruppiRuoli();
		//return mockPMOffline();
	}

	@Override
	public void populateFields(final UserDetail userDetail, final List<SimpleGrantedAuthority> authorities) {
		// prendo gruppi e ruoli dell'utente loggato dal profile manager
		try {
			final String token = LogUtil.getAuthorization();
			if(userDetail.isApiUser() == false)
				userDetail.getRoles().clear();
			if (token == null || token.isEmpty())
				return;
			log.info("Calling populate fields");
			final List<PM_GruppiRuoli> gruppiRuoli = this.getGruppiRuoliFromProfileManager(token);
			log.info("Chiamata alla logged user info per avere i dati dell'utente con token {}. Dati ottenuti {}", token, gruppiRuoli);
			if(gruppiRuoli!=null && !gruppiRuoli.isEmpty()) {
				//decoro ogni gruppo nella additionalProperties i valori "denominazioneEnte" e "idOrganizzazione"
				for(final PM_GruppiRuoli gruppo : gruppiRuoli) {
						if(Gruppi.isValidSyntax(gruppo.getCodiceGruppo())){
							try {
								String descrizione = null;
								Long idOrganizzazione = null;
								if (gruppo.getCodiceGruppo().equalsIgnoreCase(Gruppi.ADMIN.name())) {
									descrizione = Ruoli.ADMIN.getTextValue();
								}
								else if (gruppo.getCodiceGruppo().equalsIgnoreCase(Gruppi.RICHIEDENTI.name())) {
									descrizione = Gruppi.RICHIEDENTI.getTextValue();
									//nel caso di RICHIEDENTI aggiungo nei roles di userDetail (il FE lo testa in questo modo)
									userDetail.getRoles().add("RICHIEDENTE");
								} 
								else if (gruppo.getCodiceGruppo().equalsIgnoreCase(Gruppi.USER_CL.name())) {
									descrizione = Gruppi.USER_CL.getTextValue();
								}
								else if(Gruppi.isGruppoConOrganizzazione(gruppo.getCodiceGruppo())) {
									idOrganizzazione = this.userUtil.getLongId(gruppo.getCodiceGruppo());
									final PaesaggioOrganizzazioneDTO dto = this.remoteSchemaService.findPaesaggioById(idOrganizzazione.longValue());
									descrizione = dto.getDenominazione();
								}
								gruppo.getAdditionalProperties().put("denominazioneEnte", descrizione);
								gruppo.getAdditionalProperties().put("idOrganizzazione" , idOrganizzazione);
							} catch(final Exception e) {
								//codice non appartenente ad una organizzazione paesaggio
								log.error("Errore nel retrieval dell'idOrganizzazione dal codiceGruppo dell'organizzazione paesaggio: "+gruppo.getCodiceGruppo(),e);
							}	
						}
				}
			}
			/**
			 * attivare per non avere piu' il ruolo richiedente.
			 */
			appendGruppoRichiedenti(userDetail, gruppiRuoli);
			userDetail.getOtherFields().put("Gruppi_Ruoli", 
			 gruppiRuoli.stream().filter(el -> {
				 if(!Gruppi.isValidSyntax(el.getCodiceGruppo()))
					 return false;
				 //filtro i gruppi che non hanno una idOrganizzazione correttamente anagrafata in common.paesaggio_organizzazione 
				Long idOrganizzazione = null;
				if (Gruppi.isGruppoConOrganizzazione(el.getCodiceGruppo()) && el.getAdditionalProperties() != null
						&& el.getAdditionalProperties().get("idOrganizzazione") != null) {
					idOrganizzazione = (Long) el.getAdditionalProperties().get("idOrganizzazione");
				}
				return el.getCodiceApplicazione().equals(this.KEY_APPLICAZIONE_PROFILE_MANAGER)
						&& (!Gruppi.isGruppoConOrganizzazione(el.getCodiceGruppo()) || idOrganizzazione != null);

			}).collect(Collectors.toList()));
	
		} catch (final Exception e) {
			log.error("Errore nel retrieval delle info utente dal profile manager ", e);
		}
	}

	/**
	 * si appende sempre il ruolo richiedente visto che non ci sta piu' abilitazione
	 * @author acolaianni
	 *
	 * @param userDetail
	 * @param gruppiRuoli
	 */
	private void appendGruppoRichiedenti(final UserDetail userDetail, final List<PM_GruppiRuoli> gruppiRuoli) {
		if(!userDetail.getRoles().contains("RICHIEDENTE")) {
			userDetail.getRoles().add("RICHIEDENTE");
			Gruppi.RICHIEDENTI.getTextValue();
			PM_GruppiRuoli gruppoRichiedenti = new PM_GruppiRuoli();
			gruppoRichiedenti.setCodiceApplicazione(this.KEY_APPLICAZIONE_PROFILE_MANAGER);
			gruppoRichiedenti.getAdditionalProperties().put("denominazioneEnte", Gruppi.RICHIEDENTI.getTextValue());
			gruppoRichiedenti.getAdditionalProperties().put("idOrganizzazione" , null);
			gruppoRichiedenti.setCodiceGruppo(Gruppi.RICHIEDENTI.name());
			gruppoRichiedenti.setNomeGruppo(Gruppi.RICHIEDENTI.name());
			gruppiRuoli.add(gruppoRichiedenti);	
		}
	}

}