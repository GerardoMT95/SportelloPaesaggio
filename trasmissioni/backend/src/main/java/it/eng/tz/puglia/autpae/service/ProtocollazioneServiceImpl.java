package it.eng.tz.puglia.autpae.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.autpae.entity.AllegatoDTO;
import it.eng.tz.puglia.autpae.service.interfacce.ProtocollazioneService;
import it.eng.tz.puglia.service.http.IHttpProtocolloService;
import it.eng.tz.puglia.service.http.bean.ProtocolloRequestBean;
import it.eng.tz.puglia.service.http.bean.ProtocolloResponseBean;
import it.eng.tz.puglia.servizi_esterni.protocollo.GeneratedFileBean;
import it.eng.tz.puglia.servizi_esterni.protocollo.ProtocolNumberType;
import it.eng.tz.puglia.servizi_esterni.protocollo.generated.orm.dto.LogProtocolloDTO;
import it.eng.tz.puglia.servizi_esterni.protocollo.generated.orm.repository.LogProtocolloRepository;
import it.eng.tz.puglia.servizi_esterni.protocollo.interceptor.ProtocolloLogInterceptor;
import it.eng.tz.puglia.servizi_esterni.protocollo.model.SegnaturaProtocollo;

@Service
public class ProtocollazioneServiceImpl implements ProtocollazioneService {

	private static final Logger log = LoggerFactory.getLogger(ProtocollazioneServiceImpl.class);
//	private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot access attachments for plan exclusion of this municipality";

	// @Autowired private ProtocolNumberService protocolNumberService;
//	@Autowired
//	private IProtocolloService protocolNumberService;
	
	@Autowired
	private IHttpProtocolloService protocolClientService;

	@Autowired
	private LogProtocolloRepository logRepo;
	
	@Value("${spring.application.name}")
	private String codiceApplicazione;

//	@Override
//	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = { Exception.class }, timeout = 60000)
//	public SegnaturaProtocollo ottieniNumeroProtocollo(MultipartFile file, AllegatoDTO informazioni,
//			ProtocolNumberType tipo, String denominazioneMittente) throws Exception {
//		return this.ottieniNumeroProtocollo(file, informazioni, tipo, denominazioneMittente,null);
//	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = { Exception.class }, timeout = 60000)
	public SegnaturaProtocollo ottieniNumeroProtocollo(MultipartFile file, AllegatoDTO informazioni,
			ProtocolNumberType tipo, String denominazioneMittente,ProtocolloRequestBean beanProto) throws Exception {
		log.trace("Entrato nel service: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");

		GeneratedFileBean fileBean = new GeneratedFileBean();

		fileBean.setName(informazioni.getNome());
		fileBean.setMimeType(informazioni.getMimeType());
		fileBean.setContent(file.getBytes());
		fileBean.setDescription(informazioni.getDescrizione());
		SegnaturaProtocollo ret=null;
		ProtocolloResponseBean seg = protocolClientService.eseguiProtocollazione(file,beanProto, codiceApplicazione.toUpperCase());
		ret=SegnaturaProtocollo.fromProtocolloResponse(seg);
		//ret = protocolNumberService.generateSegnatura(fileBean, tipo, denominazioneMittente,beanProto);
		
		try {
			// scrivo nel repository request e response
			LogProtocolloDTO logDto = new LogProtocolloDTO();
			logDto.setRequest(MDC.get(ProtocolloLogInterceptor.MDC_SOAP_REQUEST));
			logDto.setResponse(MDC.get(ProtocolloLogInterceptor.MDC_SOAP_RESPONSE));
			logDto.setIdAllegato(informazioni.getId());
			logDto.setProtocollo(ret.toString());
			logDto.setVerso(tipo.name());
			//verso
			LocalDate dd = LocalDate.parse(ret.getDataRegistrazione(), DateTimeFormatter.ofPattern("ddMMyyyy"));
			Date d = Date.from(dd.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			logDto.setDataProtocollo(new Timestamp(d.getTime()));
			logDto.setDataEsecuzione(new Timestamp((new Date()).getTime()));
			logRepo.insert(logDto);
		} catch (Exception e) {
			log.error("Errore nella scrittura su DB della request e response SOAP protocollo ", e);
		}
		return ret;
	}
	
}