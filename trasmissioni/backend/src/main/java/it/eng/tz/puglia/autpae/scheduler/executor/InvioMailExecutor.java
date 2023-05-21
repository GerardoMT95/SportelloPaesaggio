package it.eng.tz.puglia.autpae.scheduler.executor;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.postgresql.util.PGobject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.dto.AllegatoInfoDTO;
import it.eng.tz.puglia.autpae.dto.InformazioniDTO;
import it.eng.tz.puglia.autpae.dto.NuovaComunicazioneDTO;
import it.eng.tz.puglia.autpae.dto.ProvvedimentoTabDTO;
import it.eng.tz.puglia.autpae.dto.TipologicaDestinatarioDTO;
import it.eng.tz.puglia.autpae.entity.AllegatoCorrispondenzaDTO;
import it.eng.tz.puglia.autpae.entity.AllegatoDTO;
import it.eng.tz.puglia.autpae.entity.CampionamentoDTO;
import it.eng.tz.puglia.autpae.entity.CorrispondenzaDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloCampionamentoDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.enumeratori.PlaceHolder;
import it.eng.tz.puglia.autpae.enumeratori.StatoFascicolo;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.enumeratori.TipoTemplate;
import it.eng.tz.puglia.autpae.scheduler.executor.bean.TrasmissioneBean;
import it.eng.tz.puglia.autpae.search.FascicoloCampionamentoSearch;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoCorrispondenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoService;
import it.eng.tz.puglia.autpae.service.interfacce.AssegnamentoFascicoloService;
import it.eng.tz.puglia.autpae.service.interfacce.CampionamentoService;
import it.eng.tz.puglia.autpae.service.interfacce.CorrispondenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloCampionamentoService;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloService;
import it.eng.tz.puglia.autpae.service.interfacce.ProvvedimentoService;
import it.eng.tz.puglia.autpae.utility.MockMultipartFile;
import it.eng.tz.puglia.batch.queue.executor.IBatchQueueExecutor;
import it.eng.tz.puglia.servizi_esterni.jasper.CreaReportService;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.UserUtil;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.CommonService;
import it.eng.tz.puglia.util.json.JsonUtil;
import it.eng.tz.puglia.util.log.LogUtil;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * 
 * @author Luca Rosario Mazzola
 * @date 19 apr 2022
 */
@Component(InvioMailExecutor.ID_SPRING)
public class InvioMailExecutor implements IBatchQueueExecutor {

	public static final String ID_SPRING = "INVIO_MAIL_AFTER_TRASMISSION_EXECUTOR";
	private static final Logger logger = LoggerFactory.getLogger(InvioMailExecutor.class);

	@Autowired
	private ApplicationProperties props;
	@Autowired
	private FascicoloService fascicoloService;
	@Autowired
	private AllegatoService allegatoService;
	@Autowired
	private CorrispondenzaService corrispondenzaService;
	@Autowired
	private CampionamentoService campionamentoService;
	@Autowired
	private AssegnamentoFascicoloService assegnamentoFascicoloService;
	@Autowired
	private FascicoloCampionamentoService fascicoloCampionamentoService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private CreaReportService creaReportService;
	@Autowired
	private UserUtil userUtil;
	@Autowired
	private ProvvedimentoService provvedimentoService;
	@Autowired
	private AllegatoCorrispondenzaService allegatoCorrispondenzaService;
	

	@Value("${spring.application.name}")
	private String applicationName;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public void execute(final String json) throws Exception {
		logger.info("Start service invioMailBatch");
		final StopWatch sw = LogUtil.startLog("service invioMailBatch");
		try {
			final TrasmissioneBean bean = JsonUtil.toBean(json, TrasmissioneBean.class);
			final List<TipologicaDestinatarioDTO> destinatari = bean.getDestinatari();
			InformazioniDTO infoDTO = bean.getInfoDTO();
			Long idFascicolo = infoDTO.getId();
			boolean inModifica = (infoDTO.getStato() == StatoFascicolo.ON_MODIFY);

			AllegatoDTO anteprimaDto = allegatoService.find(bean.getIdAnteprima());

			JasperPrint jasper2 = creaReportService.creaPdf_RicevutaDiTrasmissione(idFascicolo, bean.getProtocollo(),
					destinatari, infoDTO, true);
			MockMultipartFile file2 = new MockMultipartFile("Ricevuta di Trasmissione.pdf",
					JasperExportManager.exportReportToPdf(jasper2));
			NuovaComunicazioneDTO corrispondenza = new NuovaComunicazioneDTO();

			corrispondenza.setIdFascicoli(Collections.singletonList(idFascicolo));
			corrispondenza.setBozza(false);
			if (inModifica == true)
				corrispondenza.setTipoTemplate(TipoTemplate.INVIO_RITRASMISSIONE);
			else
				corrispondenza.setTipoTemplate(TipoTemplate.INVIO_TRASMISSIONE);
			AllegatoDTO ricevutaDto = new AllegatoDTO();
			ricevutaDto.setMimeType("application/pdf");
			ricevutaDto.setNome("Ricevuta di Trasmissione.pdf");
			ricevutaDto.setTipo(TipoAllegato.RICEVUTA_TRASMISSIONE);
			ricevutaDto.setDataProtocolloOut(anteprimaDto.getDataProtocolloOut());
			ricevutaDto.setNumeroProtocolloOut(anteprimaDto.getNumeroProtocolloOut());
			// noi duplichiamo le info del protocollo per entrambi i file, ma tecnicamente
			// in "numero_protocollo" si riferisce all'hash del file
			// "ANTEPRIMA_TRASMISSIONE"!

			corrispondenza.getAllegati().add(new AllegatoInfoDTO(ricevutaDto, file2, true));

			corrispondenza.setDestinatari(destinatari);

			// inserisco le informazioni per risolvere i PlaceHolders
			corrispondenza.getInfoPlaceHolders().setIdFascicolo(idFascicolo);
			// non sostituisco il placeholder....per adesso (rimetto me stesso)
			corrispondenza.getInfoPlaceHolders()
					.setUrlRicevutaTrasmissione(PlaceHolder.URL_DOWNLOAD_RICEVUTA_PROTETTO.fullName());
			corrispondenza.getInfoPlaceHolders()
			.setUrlProvvedimentoFinale(PlaceHolder.URL_DOWNLOAD_PROVVEDIMENTO_PROTETTO.fullName());

			// salvo la corrispondenza
			corrispondenza.setBozza(true);
			Long idCorrispondenza = corrispondenzaService.inviaCorrispondenza(corrispondenza, infoDTO);
			corrispondenza.setId(idCorrispondenza);
			// a questo punto ho gli id della corrispondenza e dell'allegato ma non ' stata
			// ancora inoltrata
			String urlDownloadRicevuta = this.urlDownloadRicevutaTrasmissione(idFascicolo, corrispondenza.getId(),
					corrispondenza.getAllegati().get(0).getId());
			// riaggiorno il corpo sostituendo il placeholder dell'URL
			corrispondenza.getInfoPlaceHolders().setUrlRicevutaTrasmissione(urlDownloadRicevuta);
			String risolto = corrispondenzaService.risolviSingoloPH(
					PlaceHolder.URL_DOWNLOAD_RICEVUTA_PROTETTO.fullName(), corrispondenza.getInfoPlaceHolders());
			corrispondenza.setOggetto(corrispondenza.getOggetto()
					.replace(PlaceHolder.URL_DOWNLOAD_RICEVUTA_PROTETTO.fullName(), risolto));
			corrispondenza.setTesto(
					corrispondenza.getTesto().replace(PlaceHolder.URL_DOWNLOAD_RICEVUTA_PROTETTO.fullName(), risolto));
			//Sostituzione placeholder PROVVEDIMENTO
			ProvvedimentoTabDTO provvTabDto = this.provvedimentoService.datiProvvedimento(idFascicolo);
			Long provvedimentoId=null;
			if(provvTabDto.getProvvedimento()!=null && provvTabDto.getProvvedimento().getId()!=null) {
				provvedimentoId=provvTabDto.getProvvedimento().getId();
			}
			if(provvTabDto.getProvvedimentoPrivato()!=null && provvTabDto.getProvvedimentoPrivato().getId()!=null) {
				provvedimentoId=provvTabDto.getProvvedimentoPrivato().getId();
			}
			if(provvedimentoId==null) {
				throw new Exception("Errore nel retrieve del provvedimento finale in invio mail trasmissione ");
			}
			allegatoCorrispondenzaService.insert(new AllegatoCorrispondenzaDTO(provvedimentoId, corrispondenza.getId(), true));
			String urlDownloadProvvedimento= this.urlDownloadProvvedimentoFinale(idFascicolo, corrispondenza.getId(),
					provvedimentoId);
			// riaggiorno il corpo sostituendo il placeholder dell'URL
			corrispondenza.getInfoPlaceHolders().setUrlProvvedimentoFinale(urlDownloadProvvedimento);
			String risoltoProvv = corrispondenzaService.risolviSingoloPH(
					PlaceHolder.URL_DOWNLOAD_PROVVEDIMENTO_PROTETTO.fullName(), corrispondenza.getInfoPlaceHolders());
			corrispondenza.setOggetto(corrispondenza.getOggetto()
					.replace(PlaceHolder.URL_DOWNLOAD_PROVVEDIMENTO_PROTETTO.fullName(), risoltoProvv));
			corrispondenza.setTesto(corrispondenza.getTesto()
					.replace(PlaceHolder.URL_DOWNLOAD_PROVVEDIMENTO_PROTETTO.fullName(), risoltoProvv));
			corrispondenzaService.update(new CorrispondenzaDTO(corrispondenza.getId(), corrispondenza.getOggetto(),
					corrispondenza.getTesto(), corrispondenza.isComunicazione(), corrispondenza.isBozza()));
			FascicoloDTO fascicoloDTO = fascicoloService.find(idFascicolo);
			corrispondenzaService.updateMittenteUsernameAndMittenteDenominazione(corrispondenza.getId(), 
					fascicoloDTO.getUsernameUtenteTrasmissione(),
					fascicoloDTO.getDenominazioneUtenteTrasmissione(),
					userUtil.getGruppo_Id(fascicoloDTO.getUfficio()));
			corrispondenzaService.inviaMail(corrispondenza);

			// non dovrebbe essere necessario inserire dei record (uno per ogni
			// destinatario) nella tabella "allegato_ente"

			// modifico lo stato di trasmissione del Fascicolo
			fascicoloService.setStatoInTrasmissione(idFascicolo, false);

			// associo il fascicolo appena trasmesso al campionamento (eventualmente attivo)
			// se non è già associato
			boolean startCamp = true;
			// se è una trasmissione di Regione, non devo considerarlo al campionamento...
			
			Integer orgCreazione = fascicoloDTO.getOrgCreazione();
			boolean isRegione = commonService.isRegione(orgCreazione);
			if (!isRegione) {
				if (inModifica == true) {
					List<FascicoloCampionamentoDTO> listaCampionamenti = fascicoloCampionamentoService
							.search(new FascicoloCampionamentoSearch(null, idFascicolo)).getList();
					if (listaCampionamenti != null && !listaCampionamenti.isEmpty()) { // se c'è già un campionamento in
																						// corso, non devo riassociarlo
						for (FascicoloCampionamentoDTO fascCamp : listaCampionamenti) {
							CampionamentoDTO campionamento = campionamentoService.find(fascCamp.getIdCampionamento());
							if (campionamento.getDataInizio().before(new Date())
									&& (campionamento.getDataCampionatura() == null
											|| campionamento.getDataCampionatura().after(new Date()))
									&& campionamento.isAttivo() == true) {
								startCamp = false;
							}
						}
					}
				}
				if (startCamp == true) {
					campionamentoService.samplingCheck(idFascicolo);
				}
			} else {
				logger.info("Fascicolo di Regione, salto il campionamento!!!");
			}
			// associo eventualmente il funzionario
			assegnamentoFascicoloService.assegnamentoAutomaticoInTrasmissione(idFascicolo, inModifica);
		} finally {
			logger.info(LogUtil.stopLog(sw));
		}
	}

	private String urlDownloadRicevutaTrasmissione(final Long idPratica, final Long idCorrispondenza,
			final Long idAllegato) throws Exception {
		String baseUrl = commonService.getConfigurationValue(CorrispondenzaService.KEY_URL_DOWNLOAD_PREFIX,
				this.props.getCodiceApplicazione());
		String url = baseUrl + "/public/" + CorrispondenzaService.PUBLIC_DOWNLOAD_DOC_TRASM;
		return url + "?idPratica=" + idPratica + "&idCorrispondenza=" + idCorrispondenza + "&idAllegato=" + idAllegato;
	}
	
	private String urlDownloadProvvedimentoFinale(final Long idPratica, final Long idCorrispondenza,
			final Long idAllegato) throws Exception {
		String baseUrl = commonService.getConfigurationValue(CorrispondenzaService.KEY_URL_DOWNLOAD_PREFIX,
				this.props.getCodiceApplicazione());
		String url = baseUrl + "/public/" + CorrispondenzaService.PUBLIC_DOWNLOAD_PROVVEDIMENTO_FINALE;
		return url + "?idPratica=" + idPratica + "&idCorrispondenza=" + idCorrispondenza + "&idAllegato=" + idAllegato;
	}

	public PGobject creaJsonInfo(final InformazioniDTO info) throws SQLException {
		PGobject pgObj = new PGobject();
		pgObj.setType("json");
		pgObj.setValue(info.toJson());
		return pgObj;
	}

}