package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.DettaglioCorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.ProvvedimentoFinaleDetailsDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinatarioDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TemplateEmailDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.CorrispondenzaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.bean.ProvvedimentoBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.ProvvedimentoFinaleService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.service.IResolveTemplateService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.service.ITemplateMailService;
import it.eng.tz.puglia.service.http.IHttpClientService;
import it.eng.tz.puglia.service.http.bean.SendMailRequestBean;
import it.eng.tz.puglia.util.json.JsonUtil;
import it.eng.tz.puglia.util.log.LogUtil;

@Component(ProvvedimentoFinaleExecutor.ID)
public class ProvvedimentoFinaleExecutor extends SportelloBaseExecutor {

	/**
	 * ID
	 */
	public static final String ID = "ProvvedimentoFinaleExecutor";
	/**
	 * LOGGER
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(ProvvedimentoFinaleExecutor.class);
	/**
	 * service
	 */
	@Value("${profilemanager.codiceapplicazione}")
	private String applicationName;

	@Value("${url.mail.notifica:}")
	private String urlMailNotifica;
	
	@Autowired
	private ITemplateMailService templateMailSvc;

	@Autowired
	private IHttpClientService mailService;
	
	@Autowired
	private IResolveTemplateService resolveTmplSvc;
	
	@Autowired
	private CorrispondenzaRepository corrispondenzaRepository;
	
	@Autowired
	private ProvvedimentoFinaleService prvSvc;
	/**
	 * 
	 */
	@Override
	public UUID internalExecute(final String parameters) throws Exception {
		final StopWatch sw = LogUtil.startLog("internalExecute");
		LOGGER.info("Start internalExecute");
		try {
			final ProvvedimentoBean bean = JsonUtil.toBean(parameters, ProvvedimentoBean.class);
			final SendMailRequestBean mailBean = new SendMailRequestBean();
			final TemplateEmailDTO template = this.templateMailSvc.findAncheSuDefault(0, "INOLTRO_PROVVEDIMENTO_ISTANZE");
			mailBean.setSubject(this.resolveTmplSvc.risolviTesto(template.getPlaceholders(), template.getOggetto(), UUID.fromString(bean.getIdPratica())));
			mailBean.setText(this.resolveTmplSvc.risolviTesto(template.getPlaceholders(), template.getTesto(), UUID.fromString(bean.getIdPratica())));
			mailBean.setTo(new ArrayList<>());
			mailBean.setKey(this.applicationName);
			DettaglioCorrispondenzaDTO dettCorr = this.corrispondenzaRepository.getDettaglioCorrispondenza(bean.getIdCorrispondenza());
			List<String> listaDestinatari = dettCorr.getDestinatari().stream().map(DestinatarioDTO::getEmail).collect(Collectors.toList());
			mailBean.getTo().addAll(listaDestinatari);
			mailBean.getTo().add("simone.verna@eng.it");
			this.mailService.sendMail(this.urlMailNotifica, mailBean, null, true);
			return UUID.fromString(bean.getIdPratica());
		} finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	@Override
	public String getId() {
		return ID;
	}

}