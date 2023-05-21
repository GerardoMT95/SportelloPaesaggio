package it.eng.tz.puglia.autpae.scheduler.executor;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.autpae.dto.AllegatoCustomDTO;
import it.eng.tz.puglia.autpae.dto.InformazioniDTO;
import it.eng.tz.puglia.autpae.dto.TipologicaDestinatarioDTO;
import it.eng.tz.puglia.autpae.entity.AllegatoDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.scheduler.InvioMailScheduler;
import it.eng.tz.puglia.autpae.scheduler.executor.bean.TrasmissioneBean;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoService;
import it.eng.tz.puglia.autpae.utility.MockMultipartFile;
import it.eng.tz.puglia.batch.queue.IQueueService;
import it.eng.tz.puglia.batch.queue.executor.IBatchQueueExecutor;
import it.eng.tz.puglia.service.http.bean.ProtocolloRequestBean;
import it.eng.tz.puglia.servizi_esterni.jasper.CreaReportService;
import it.eng.tz.puglia.servizi_esterni.protocollo.ProtocolNumberType;
import it.eng.tz.puglia.util.json.JsonUtil;
import it.eng.tz.puglia.util.log.LogUtil;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@Component(ProtocollazioneFascicoloExecutor.ID_SPRING)
public class ProtocollazioneFascicoloExecutor implements IBatchQueueExecutor {

	public static final String ID_SPRING = "PROTOCOLLAZIONE_FASCICOLO_EXECUTOR";
	private static final Logger logger = LoggerFactory.getLogger(ProtocollazioneFascicoloExecutor.class);

	@Autowired
	private CreaReportService creaReportService;
	@Autowired
	private AllegatoService allegatoService;
	@Autowired
	private IQueueService queueService;

	@Value("${spring.application.name}")
	private String applicationName;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public void execute(final String json) throws Exception {
		logger.info("Start service protocollazioneFascicoloBatch");
		final StopWatch sw = LogUtil.startLog("service protocollazioneFascicoloBatch");
		try {
			final TrasmissioneBean bean = JsonUtil.toBean(json, TrasmissioneBean.class);
			final TrasmissioneBean beanOriginale = JsonUtil.toBean(json, TrasmissioneBean.class);
			final List<TipologicaDestinatarioDTO> destinatari = bean.getDestinatari();
			final InformazioniDTO infoDTO = beanOriginale.getInfoDTO();
			final Long idFascicolo = infoDTO.getId();
			final JasperPrint jasper1 = this.creaReportService.creaPdf_RicevutaDiTrasmissione(idFascicolo, null,
					destinatari, infoDTO, true);
			final MockMultipartFile file1 = new MockMultipartFile("[Anteprima] Ricevuta di Trasmissione.pdf",
					JasperExportManager.exportReportToPdf(jasper1));

			AllegatoDTO anteprimaDto = new AllegatoDTO();
			anteprimaDto.setNome("[Anteprima] Ricevuta di Trasmissione.pdf");
			anteprimaDto.setMimeType("application/pdf");
			anteprimaDto.setTipo(TipoAllegato.ANTEPRIMA_TRASMISSIONE);

			final AllegatoCustomDTO ret = this.allegatoService.inserisciAllegato(Collections.singletonList(idFascicolo),
					TipoAllegato.ANTEPRIMA_TRASMISSIONE, file1, anteprimaDto);
			anteprimaDto.setId(ret.getId());
			// build bean per protocollazione.
			final String[] denominazioneDestinatari = destinatari.stream().map(TipologicaDestinatarioDTO::getNome)
					.collect(Collectors.toList()).toArray(new String[0]);
			final String[] mailTo = destinatari.stream().map(TipologicaDestinatarioDTO::getEmail)
					.collect(Collectors.toList()).toArray(new String[0]);
			final ProtocolloRequestBean protoBean = new ProtocolloRequestBean();
			protoBean.setOggetto("Comunicazione avvenuta trasmissione della Pratica ad oggetto '"
					+ infoDTO.getOggettoIntervento() + "'");
			protoBean.setNomeDocumentoPrimario("Primario");
			protoBean.setTitoloDocumentoPrimario("vedi oggetto");
			protoBean.setOggettoDocumentoPrimario(protoBean.getOggetto());
			protoBean.setTipoRiferimento("MIME");
			protoBean.setTipoRiferimentoPrimario("MIME");
			protoBean.setDenominazioneDestinatari(denominazioneDestinatari);
			protoBean.setMailTo(mailTo);
			protoBean.setInOut(ProtocolloRequestBean.InOut.U.name());
			protoBean.setOggetto(protoBean.getOggettoDocumentoPrimario());
			protoBean.setTitoloDocumento("Stampa avvenuta ricezione");
			protoBean.setTipoDocumento(anteprimaDto.getMimeType());
			final String protocollo = this.allegatoService.protocolla(file1, anteprimaDto, ProtocolNumberType.O,
					protoBean);
			bean.setProtocollo(protocollo);

			// inserisco l'allegato ANTEPRIMA_TRASMISSIONE tra i file riferiti al fascicolo
			anteprimaDto = this.allegatoService.find(anteprimaDto.getId());
			anteprimaDto.setDataProtocolloOut(new Date());
			anteprimaDto.setNumeroProtocolloOut(protocollo);
			this.allegatoService.update(anteprimaDto);

			bean.setIdAnteprima(anteprimaDto.getId());

			this.queueService.setApplicationName(this.applicationName.toUpperCase());
			this.queueService.insertNewQueue(InvioMailScheduler.ID, 3600, bean);
		} finally {
			logger.info(LogUtil.stopLog(sw));
		}
	}

}
