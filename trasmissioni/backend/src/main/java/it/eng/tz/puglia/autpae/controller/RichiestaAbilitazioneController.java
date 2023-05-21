package it.eng.tz.puglia.autpae.controller;


import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dto.RichiestaAbilitazioneDto;
import it.eng.tz.puglia.autpae.dto.SegnalazioneDto;
import it.eng.tz.puglia.autpae.generated.orm.dto.UtenteAttributeDTO;
import it.eng.tz.puglia.autpae.service.interfacce.GruppiRuoliService;
import it.eng.tz.puglia.autpae.service.interfacce.RichiestaAbilitazioneSvc;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.service.http.IHttpPrivacyClientService;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.AssUtenteGruppo;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.CommonService;
import it.eng.tz.puglia.servizi_esterni.webmail.service.WebmailService;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.regione.puglia.webmail.be.dto.ConfigurazioneCasellaPostaleDto;
import it.eng.tz.regione.puglia.webmail.be.dto.InvioMailDto;

@Controller
@RequestMapping("abilitazioni")
public class RichiestaAbilitazioneController extends _RestController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RichiestaAbilitazioneController.class);
	
	@Autowired private RichiestaAbilitazioneSvc svc;
	@Autowired private WebmailService webmailSvc;
	@Autowired private IHttpPrivacyClientService privacySvc;
	@Autowired private GruppiRuoliService gruppiRuoliSvc;
	@Autowired private ApplicationProperties props;
	@Autowired private CommonService commonSvc;
	
	@Autowired
	@Qualifier("casellaMittenteApplicazione")
	private ConfigurazioneCasellaPostaleDto ccpd;
	
	@Value("${pm.codice.applicazione}")
	private String codiceApplicazionePM;
	
	/**
     * 
     */
    @PostMapping(value="/richiesta-abilitazione.pjson", consumes={"multipart/form-data"}, produces=MEDIA_TYPE)
    public ResponseEntity<BaseRestResponse<String>> sendRichiesta(@RequestPart("data"    ) RichiestaAbilitazioneDto user,
            													  @RequestPart("allegato") MultipartFile file) {
    LOGGER.info("Chiamato il controller: 'abilitazioni'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
    	final String TRACE="richiestaAbilitazione";
        LOGGER.info("Start "+TRACE);
        final StopWatch sw = LogUtil.startLog("start "+TRACE);
        try {
        	LOGGER.info(user.toString());
        	this.svc.validaRichiesta(user, file);
        	this.svc.sendRichiesta(user, file);
        	if(StringUtil.isNotBlank(user.getPrivacy())){
        		boolean haveToAccept = this.privacySvc.userHaveToAccept(codiceApplicazionePM);
        		if(haveToAccept) {
        			this.privacySvc.accept();//accettazione della privacy		
        		}
        	}
        	return super.okResponse("OK");
        }catch(Exception e) {
            LOGGER.error("Error in "+TRACE, e);
            return super.koResponse(e, new BaseRestResponse<>());
        }finally {
            LOGGER.info(LogUtil.stopLog(sw));
        }
    }
    
    @PostMapping(value="/segnala-organizzazione-mancante.pjson", consumes={"multipart/form-data"}, produces=MEDIA_TYPE)
    public ResponseEntity<BaseRestResponse<String>> segnalaOrganizzazione(@RequestPart("data") SegnalazioneDto data) {
    LOGGER.info("Chiamato il controller: 'abilitazioni'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
    	final String TRACE="segnala-organizzazione-mancante";
        LOGGER.info("Start "+TRACE);
        final StopWatch sw = LogUtil.startLog("start "+TRACE);
        try {
        	String br="<br>";
        	LOGGER.info(data.toString());
        	InvioMailDto datiMail=new InvioMailDto();
        	datiMail.setFrom(ccpd.getCasellaPostale().getIndirizzoMail());
        	AssUtenteGruppo[] adminProfiles = gruppiRuoliSvc.getAdministratorsProfilePM();
        	Set<String> indirizziAdmin=new HashSet<>();
        	for(AssUtenteGruppo adminProfile: adminProfiles) {
        		if(StringUtil.isEmail(adminProfile.getMail())) {
        			indirizziAdmin.add(adminProfile.getMail());
        		}
        	}
        	if(indirizziAdmin.isEmpty()) {
        		throw new CustomOperationToAdviceException("Nessuna mail di amministratore configurata sui profili, contattare l'amministratore dei servizi!");
        	}
        	datiMail.setTo(indirizziAdmin.toArray(new String[] {}));
        	String userMail = SecurityUtil.getUserDetail().getEmailAddress();
        	if(StringUtil.isEmail(userMail)){
        		datiMail.setBcc(new String[] {userMail});
        	}
        	datiMail.setOggettoMail("Segnalazione organizzazione mancante in applicazione: "+props.getCodiceApplicazione());
        	StringBuilder corpoMail=new StringBuilder();
        	corpoMail.append("Messaggio inviato dal form di richiesta abilitazione dall'utente: "+br)
        	.append(br+"Username:")
        	.append(data.getUsername())
        	.append(br+"Nome:")
        	.append(data.getNome())
        	.append(br+"Cognome:")
        	.append(data.getCognome())
        	.append(br+br+"Testo della segnalazione:"+br)
        	.append(data.getNotifica())
        	.append(br+br)
        	.append("Messaggio inviato da una casella di posta automatica, si prega di non rispondere.");
        	datiMail.setCorpoMail(corpoMail.toString());
        	LOGGER.info(datiMail.toString());
        	this.webmailSvc.inviaMail(datiMail, null, null);
        	return super.okResponse("OK");
        }catch(Exception e) {
            LOGGER.error("Error in "+TRACE, e);
            return super.koResponse(e, new BaseRestResponse<>());
        }finally {
            LOGGER.info(LogUtil.stopLog(sw));
        }
    }

    @GetMapping(value="/testo-privacy.pjson", produces=MEDIA_TYPE)
    public ResponseEntity<BaseRestResponse<String>> getTestoPrivacy() {
    LOGGER.info("Chiamato il controller: 'abilitazioni'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
    	final String TRACE="testo-privacy";
        LOGGER.info("Start "+TRACE);
        final StopWatch sw = LogUtil.startLog("start "+TRACE);
        try {
        	String privacy="Dichiara di aver letto l'informativa sulla privacy.... (Inserire clausola privacy in privacy manager)";
        	try {
        		privacy=privacySvc.getText();
        		if(privacy==null || privacy.equals("null")) {
        			privacy="";
        		}
        	}catch(Exception e) {
        		LOGGER.error("errore nel retrieval della privacy dallo userManager..." );
        	}
        	return super.okResponse(privacy);
        }catch(Exception e) {
            LOGGER.error("Error in "+TRACE, e);
            return super.koResponse(e, new BaseRestResponse<>());
        }finally {
            LOGGER.info(LogUtil.stopLog(sw));
        }
    }
    
    @GetMapping(value="/get-last-richiesta-data.pjson", produces=MEDIA_TYPE)
    public ResponseEntity<BaseRestResponse<UtenteAttributeDTO>> getLastRichiestaData() {
    LOGGER.info("Chiamato il controller: 'abilitazioni'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
    	final String TRACE="last-richiesta-data";
        LOGGER.info("Start "+TRACE);
        final StopWatch sw = LogUtil.startLog("start "+TRACE);
        //List<SelectOptionDto> out=null;
        try {
        	UtenteAttributeDTO utAttr = commonSvc.getLastUtenteAttribute(codiceApplicazionePM, SecurityUtil.getUsername());
        	return super.okResponse(utAttr);
        }catch(Exception e) {
            LOGGER.error("Error in "+TRACE, e);
            return super.koResponse(e, new BaseRestResponse<>());
        }finally {
            LOGGER.info(LogUtil.stopLog(sw));
        }
    }
    
    /**
     * chiede se l'utente ha da riaccettare il testo della privacy
     * @autor Adriano Colaianni
     * @date 22 apr 2021
     * @return
     */
    @GetMapping(value="/privacyUserHaveToAccept.pjson", produces=MEDIA_TYPE)
    public ResponseEntity<BaseRestResponse<Boolean>> userHaveToAccept() {
    LOGGER.info("Chiamato il controller: 'abilitazioni'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
    	final String TRACE="userHaveToAccept";
        LOGGER.info("Start "+TRACE);
        final StopWatch sw = LogUtil.startLog("start "+TRACE);
        try {
        	boolean ret = this.privacySvc.userHaveToAccept();
        	return super.okResponse(ret);
        }catch(Exception e) {
            LOGGER.error("Error in "+TRACE, e);
            return super.koResponse(e, new BaseRestResponse<>());
        }finally {
            LOGGER.info(LogUtil.stopLog(sw));
        }
    }
    
    
    @GetMapping(value="/privacyAccept.pjson", produces=MEDIA_TYPE)
    public ResponseEntity<BaseRestResponse<String>> privacyAccept() {
    LOGGER.info("Chiamato il controller: 'abilitazioni'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
    	final String TRACE="privacyAccept";
        LOGGER.info("Start "+TRACE);
        final StopWatch sw = LogUtil.startLog("start "+TRACE);
        try {
        	boolean ret = this.privacySvc.userHaveToAccept();
        	if(ret) {
        		this.privacySvc.accept();
        	}
        	return super.okResponse("OK");
        }catch(Exception e) {
            LOGGER.error("Error in "+TRACE, e);
            return super.koResponse(e, new BaseRestResponse<>());
        }finally {
            LOGGER.info(LogUtil.stopLog(sw));
        }
    }
    
}