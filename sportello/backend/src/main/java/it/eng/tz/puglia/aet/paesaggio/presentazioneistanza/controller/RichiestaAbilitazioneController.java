package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller;


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

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.dto.UtenteAttributeDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.ApplicationProperties;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.RichiestaAbilitazioneDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.RichiestaAbilitazioneRichiedenteDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.SegnalazioneDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.AssUtenteGruppo;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.service.RemoteSchemaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.GruppiRuoliService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.RichiestaAbilitazioneSvc;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.webmail.service.WebmailService;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.service.http.IHttpPrivacyClientService;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.regione.puglia.webmail.be.dto.ConfigurazioneCasellaPostaleDto;
import it.eng.tz.regione.puglia.webmail.be.dto.InvioMailDto;

@Controller
@RequestMapping("abilitazioni")
public class RichiestaAbilitazioneController extends RoleAwareController{
	private static final Logger LOGGER = LoggerFactory.getLogger(RichiestaAbilitazioneController.class);
	
//	private void saveUploadedFile(MultipartFile file) throws IOException {
//	    if (!file.isEmpty()) {
//	        byte[] bytes = file.getBytes();
//	        Path path = Paths.get("C:\\tmp\\temp\\" + file.getOriginalFilename());
//	        Files.write(path, bytes);
//	    }
//	}
	
	@Autowired RichiestaAbilitazioneSvc svc;
	@Autowired private WebmailService webmailSvc;
	@Autowired private GruppiRuoliService gruppiRuoliSvc;
	@Autowired private ApplicationProperties props;
	@Autowired
	@Qualifier("casellaMittenteApplicazione")
	private ConfigurazioneCasellaPostaleDto ccpd;
	@Autowired private IHttpPrivacyClientService privacySvc;
	
	@Autowired
	private RemoteSchemaService commonSvc;
	
	@Value("${codice.applicazione.istruttoria}")
	private String codiceApplicazioneIstruttoria;
	
	@Value("${richiesta.abilitazione.maxSizeAllegato:1048576}")
	private long maxSizeAllegato;
	
	@Value("${pm.codice.applicazione}")
	private String codiceApplicazionePM;
	
	/**
     * tipi_procedimento
     */
    @PostMapping(value="/richiesta-abilitazione.pjson",consumes = { "multipart/form-data" }, produces=MEDIA_TYPE)
    public ResponseEntity<BaseRestResponse<String>> sendRichiesta(
    		@RequestPart("data") RichiestaAbilitazioneRichiedenteDto user,
            @RequestPart("allegato")  MultipartFile file){
    	final String TRACE="richiestaAbilitazione";
        LOGGER.info("Start "+TRACE);
        final StopWatch sw = LogUtil.startLog("start "+TRACE);
        try {
        	if(file.getSize()>maxSizeAllegato) {
        		throw new CustomOperationToAdviceException("L'allegato supera la dimensione massima ammessa di "+maxSizeAllegato+" byte");
        	}
        	this.svc.sendRichiesta(user, file);
        	return super.okResponse("OK");
        }catch(Exception e) {
            LOGGER.error("Error in "+TRACE, e);
            return super.koResponse(e, new BaseRestResponse<>());
        }finally {
            LOGGER.info(LogUtil.stopLog(sw));
        }
    }
    
    /**
     * tipi_procedimento
     */
    @PostMapping(value="/richiesta-abilitazione-istruttoria.pjson", consumes={"multipart/form-data"}, produces=MEDIA_TYPE)
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
        	boolean haveToAccept=this.privacySvc.userHaveToAccept(codiceApplicazioneIstruttoria);
        	if(haveToAccept) {
        		this.privacySvc.accept(codiceApplicazioneIstruttoria);//accettazione della privacy appena letta
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
//        	UtenteGruppo[] adminProfiles = gruppiRuoliSvc.getAdministratorsProfile();
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

    /**
     * testo privacy per abilitazione lato istruttoria
     * @return
     */
    @GetMapping(value="/testo-privacy.pjson", produces=MEDIA_TYPE)
    public ResponseEntity<BaseRestResponse<String>> getTestoPrivacy() {
    LOGGER.info("Chiamato il controller: 'abilitazioni'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
    	final String TRACE="testo-privacy";
        LOGGER.info("Start "+TRACE);
        final StopWatch sw = LogUtil.startLog("start "+TRACE);
        try {
        	String privacy="Dichiara di aver letto l'informativa sulla privacy.... (Inserire clausola privacy in privacy manager)";
        	try {
        		privacy=privacySvc.getText(codiceApplicazioneIstruttoria);
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
    
    /**
     * solo per istruttoria, per riprendere alcuni attributi dalla common.utente_attribute e riproporli nel form per eventuale nuova richiesta abilitazione.
     * @author acolaianni
     *
     * @return 
     */
    @GetMapping(value="/get-last-richiesta-data.pjson", produces=MEDIA_TYPE)
    public ResponseEntity<BaseRestResponse<UtenteAttributeDTO>> getLastRichiestaData() {
    LOGGER.info("Chiamato il controller: 'abilitazioni'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
    	final String TRACE="last-richiesta-data";
        LOGGER.info("Start "+TRACE);
        final StopWatch sw = LogUtil.startLog("start "+TRACE);
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
     * solo per istruttoria
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
        	boolean ret = this.privacySvc.userHaveToAccept(codiceApplicazioneIstruttoria);
        	return super.okResponse(ret);
        }catch(Exception e) {
            LOGGER.error("Error in "+TRACE, e);
            return super.koResponse(e, new BaseRestResponse<>());
        }finally {
            LOGGER.info(LogUtil.stopLog(sw));
        }
    }
    
    /**
     * solo per istruttoria
     * @author acolaianni
     *
     * @return
     */
    @GetMapping(value="/privacyAccept.pjson", produces=MEDIA_TYPE)
    public ResponseEntity<BaseRestResponse<String>> privacyAccept() {
    LOGGER.info("Chiamato il controller: 'abilitazioni'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
    	final String TRACE="privacyAccept";
        LOGGER.info("Start "+TRACE);
        final StopWatch sw = LogUtil.startLog("start "+TRACE);
        try {
        	boolean ret = this.privacySvc.userHaveToAccept(codiceApplicazioneIstruttoria);
        	if(ret) {
        		this.privacySvc.accept(codiceApplicazioneIstruttoria);
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
