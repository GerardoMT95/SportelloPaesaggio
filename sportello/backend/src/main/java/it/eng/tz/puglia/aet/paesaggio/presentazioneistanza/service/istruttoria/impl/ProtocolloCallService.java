package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainNumberValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ConfigurazioniEnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ProtocolloDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoProcedimentoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TipoProcedimentoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.ConfigurazioniEnteSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl.ConfigurazioniEnteService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl.ProtocolloLogService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.service.RemoteSchemaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.AllegatoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.impl.FascicoloServiceImpl;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.IProtocolloCallService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.GeneratedFileBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.IProtocolloService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.ProtocolNumberType;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.interceptor.ProtocolloLogInterceptor;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.model.Amministrazione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.model.IndirizzoPostale;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.model.IndirizzoTelematico;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.model.Mittente;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.model.SegnaturaProtocollo;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.MockMultipartFile;
import it.eng.tz.puglia.error.exception.CustomCmisException;
import it.eng.tz.puglia.error.exception.CustomOperationException;
import it.eng.tz.puglia.error.exception.CustomValidationException;
import it.eng.tz.puglia.service.http.IHttpProtocolloService;
import it.eng.tz.puglia.service.http.bean.CmsDownloadResponseBean;
import it.eng.tz.puglia.service.http.bean.ProtocolloConfigurationRequestBean;
import it.eng.tz.puglia.service.http.bean.ProtocolloRequestBean;
import it.eng.tz.puglia.service.http.bean.ProtocolloResponseBean;
import it.eng.tz.puglia.util.crypt.CryptUtil;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class ProtocolloCallService implements IProtocolloCallService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FascicoloServiceImpl.class);

	@Value("${spring.application.name}")
	private String nomeApplicazione;
	
	@Value("${client.protocollo.endpoint}")
	private String clientProtocolloEndpoint;

	@Value("${client.protocollo.administration}")
	private String clientProtocolloAdministration;

	@Value("${client.protocollo.aoo}")
	private String clientProtocolloAOO;

	@Value("${client.protocollo.register}")
	private String clientProtocolloRegister;

	@Value("${client.protocollo.user}")
	private String clientProtocolloUser;

	@Value("${client.protocollo.password}")
	private String clientProtocolloPassword;

	@Value("${client.protocollo.hash.algorithm}")
	private String clientProtocolloHashAlgorihtm;

	@Value("${client.protocollo.indirizzopostale}")
	private String clientProtocolloIndirizzopostale;

	@Value("${client.protocollo.indirizzotelematico}")
	private String clientProtocolloIndirizzotelematico;

	@Value("${client.protocollo.aoo.denominazione}")
	private String clientProtocolloAooDenominazione;

	@Value("${client.protocollo.denominazione}")
	private String clientProtocolloDenominazione;

	@Value("${client.protocollo.tipoindirizzotelematico}")
	private String clientProtocolloTipoindIrizzoTelematico;

	@Value("${client.protocollo.numero_registrazione}")
	private String clientProtocolloNumeroRegistrazione;

	@Value("${client.protocollo.data_registrazione}")
	private String clientProtocolloDataRegistrazione;

	@Autowired
	private PraticaRepository praticaDao;
	@Autowired
	ConfigurazioniEnteService confEnteService;

	@Autowired
	private TipoProcedimentoRepository tipoProcDao;
	
	//@Autowired
	//IProtocolloService protocolNumber;

	@Autowired
	ProtocolloLogService protocolloService;

	@Autowired
	private UserUtil userUtil;

	@Autowired
	private AllegatoService allegatoSvc;

	@Autowired
	private RemoteSchemaService remoteSchema;
	
	@Autowired 
	private IHttpProtocolloService protoSvc;

	@PostConstruct
	private void initDefaultConfiguration() throws Exception {
		ConfigurazioniEnteSearch confSearch = new ConfigurazioniEnteSearch();
		ConfigurazioniEnteDTO configEnte = new ConfigurazioniEnteDTO();
		List<PlainNumberValueLabel> enteRegione = null;
		enteRegione = this.remoteSchema.getEnteRegione();
		confSearch.setIdOrganizzazione(enteRegione.get(0).getValue().toString());

		List<ConfigurazioniEnteDTO> configList = null;
		;
		try {
			configList = this.confEnteService.search(confSearch).getList();
		} catch (Exception e) {
			LOGGER.error("Errore nella search della configurazione ente per Regione");
			throw e;
		}
		if (configList.size() > 0) {
			configEnte = configList.get(0);
			if (!configEnte.isSistemaProtocollazione()) {
				configEnte = this.stroreDefaultConfiguration(enteRegione.get(0).getValue());
			}
		} else {
			configEnte = this.stroreDefaultConfiguration(enteRegione.get(0).getValue());
		}
	}

	/**
	 * Metodo che richiama il servizio SOAP per la protocollazione
	 *
	 * @author G.Lavermicocca
	 * @throws Exception 
	 * @date 11 set 2020
	 */
//	@Override
//	@Deprecated
//	public SegnaturaProtocollo getNumeroProtocollo(UUID idAllegato, UUID idPratica, ProtocolNumberType pt) {
//		CmsDownloadResponseBean cmsResponse = null;
//		try {
//			// Protocollazione
//			LOGGER.info("ProtpcolloService - inizio protocollazione codicePratica:" + idPratica);
//			// ricavo l'id dell'ente amministratore
//			PraticaDTO praticaDto = this.praticaDao.find(idPratica);
//			// Ricavo dati per accedere al servizio di protocollazione
//
//			ConfigurazioniEnteDTO configEnte = new ConfigurazioniEnteDTO();
//			configEnte.setIdOrganizzazione(Integer.parseInt(praticaDto.getEnteDelegato()));
//			configEnte = this.confEnteService.find(configEnte);
//
//			// Sacarico il primo l'allegato
//			cmsResponse = this.allegatoSvc.downloadAllegatoGenerico(idAllegato);
//
//			// inizializzo il servizio di protocollazione
//			GeneratedFileBean generatedFileBean = new GeneratedFileBean();
//			generatedFileBean.setContent(Files.readAllBytes(Path.of(cmsResponse.getFileName())));
//			generatedFileBean.setName(cmsResponse.getFilePathName());
//			// setto la configurazione per il client di protocollazione
//			this.protocolNumber.setConfiguration(configEnte);
//			if (configEnte.isSistemaProtocollazione()) {
//				String senderName = "";
//				if (userUtil.hasUserLogged()) {
//					senderName = userUtil.getMyProfile().getNome().concat(" ")
//							.concat(userUtil.getMyProfile().getCognome());
//				} else {
//					// TODO: Batch user
//					// name= props.getBatchUsr();
//					senderName = null;
//				}
//				SegnaturaProtocollo segnatura = this.protocolNumber.generateSegnatura(generatedFileBean, pt);
//				LOGGER.info("protocollaFascicolo - numeroProtocollo:" + segnatura.getNumeroProtocollo());
//				this.salvaDatiProtocollo(configEnte, segnatura, pt, idPratica);
//				return segnatura;
//			}
//
//		} catch (CustomCmisException | CustomOperationException | IOException | CustomValidationException e) {
//			LOGGER.info("Errore in getNUmeroProtocollo ",e);
//		} catch (Exception e) {
//			LOGGER.info("Errore in getNUmeroProtocollo ",e);
//		} finally {
//			if (cmsResponse != null) {
//				File res = new File(cmsResponse.getFileName());
//				String folder = res.getParent();
//				res.delete();
//				File folderFile = new File(folder);
//				folderFile.delete();
//			}
//		}
//		return null;
//	}
	
	@Override
	public ProtocolloResponseBean protocollaIstanza(UUID idIstanza,UUID idSchedaTecnica, UUID idPratica, ProtocolNumberType pt) throws Exception {
		CmsDownloadResponseBean cmsResponseIstanza = null;
		CmsDownloadResponseBean cmsResponseSchedaTecnica = null;
		try {
			// Protocollazione
			LOGGER.info("ProtpcolloService - inizio protocollazione codicePratica:" + idPratica);
			// ricavo l'id dell'ente amministratore
			PraticaDTO praticaDto = this.praticaDao.find(idPratica);
			TipoProcedimentoDTO pkTp=new TipoProcedimentoDTO();
			pkTp.setId(praticaDto.getTipoProcedimento());
			TipoProcedimentoDTO tpProc = this.tipoProcDao.find(pkTp);
			// Ricavo dati per accedere al servizio di protocollazione

			ConfigurazioniEnteDTO configEnte = new ConfigurazioniEnteDTO();
			configEnte.setIdOrganizzazione(Integer.parseInt(praticaDto.getEnteDelegato()));
			configEnte = this.confEnteService.find(configEnte);

			// Sacarico il primo l'allegato
			cmsResponseIstanza = this.allegatoSvc.downloadAllegatoGenerico(idIstanza);
			cmsResponseSchedaTecnica = this.allegatoSvc.downloadAllegatoGenerico(idSchedaTecnica);

			// inizializzo il servizio di protocollazione
			GeneratedFileBean generatedFileBeanIstanza = new GeneratedFileBean();
			generatedFileBeanIstanza.setContent(Files.readAllBytes(Path.of(cmsResponseIstanza.getFileName())));
			generatedFileBeanIstanza.setName(cmsResponseIstanza.getFilePathName());
			
			GeneratedFileBean generatedFileBeanSchedaTecnica = new GeneratedFileBean();
			generatedFileBeanSchedaTecnica.setContent(Files.readAllBytes(Path.of(cmsResponseSchedaTecnica.getFileName())));
			generatedFileBeanSchedaTecnica.setName(cmsResponseSchedaTecnica.getFilePathName());
			// setto la configurazione per il client di protocollazione
			//this.protocolNumber.setConfiguration(configEnte);
			if (configEnte.isSistemaProtocollazione()) {
				String senderName = "";
				if (userUtil.hasUserLogged()) {
					senderName = userUtil.getMyProfile().getNome().concat(" ")
							.concat(userUtil.getMyProfile().getCognome());
				} else {
					// TODO: Batch user
					// name= props.getBatchUsr();
					senderName = null;
				}
				//SegnaturaProtocollo segnatura = this.protocolNumber.generateSegnatura(generatedFileBeanIstanza, pt);
				ProtocolloRequestBean protoBean=new ProtocolloRequestBean();
				/**
				 * ex oggetto loro...
				 * ricezione - AUTORIZZAZIONE PAESAGGISTICA SEMPLIFICATA D.P.R. 139/2010 - art. 90, NTA PPTR (PER
				LE OPERE IL CUI IMPATTO PAESAGGISTICO E' VALUTATO MEDIANTE UNA DOCUMENTAZIONE
				SEMPLIFICATA) - Codice - APPPTR-81-2016

				 */
				protoBean.setOggetto("ricezione - " +
						tpProc.getDescrizione()+
						" - Codice - "+praticaDto.getCodicePraticaAppptr());
				protoBean.setNomeDocumentoPrimario("Primario");
				protoBean.setTitoloDocumentoPrimario("vedi oggetto");
				protoBean.setOggettoDocumentoPrimario(protoBean.getOggetto());
				protoBean.setTipoRiferimento("MIME");
				protoBean.setTipoRiferimentoPrimario("MIME");
				protoBean.setOggetto(protoBean.getOggettoDocumentoPrimario());
				protoBean.setTipoDocumento("pdf");
				protoBean.setInOut(pt.name());
				protoBean.setOggettoDocumento(protoBean.getOggetto());
				MockMultipartFile[] files=new MockMultipartFile[2];
				String[] titoloAllegati= new String[2];
				titoloAllegati[0]="Istanza";
				titoloAllegati[1]="Scheda tecnica";
				protoBean.setTitoloDocumentiAllegati(titoloAllegati);
				files[0]=new MockMultipartFile(generatedFileBeanIstanza.getName(),generatedFileBeanIstanza.getContent());
				files[1]=new MockMultipartFile(generatedFileBeanSchedaTecnica.getName(),generatedFileBeanSchedaTecnica.getContent());
				ProtocolloConfigurationRequestBean configBean = buildConfigProto(configEnte);
				ProtocolloResponseBean segnatura = this.protoSvc.eseguiProtocollazioneConfigCustom(files, protoBean, configBean);
				LOGGER.info("protocollaFascicolo - numeroProtocollo:" + segnatura.getNumeroProtocollo());
				this.salvaDatiProtocollo(configEnte, segnatura, pt, idPratica);
				return segnatura;
			}

		} catch (CustomCmisException | CustomOperationException | IOException | CustomValidationException e) {
			LOGGER.info("Errore in getNUmeroProtocollo ",e);
			throw e;
		} catch (Exception e) {
			LOGGER.info("Errore in getNUmeroProtocollo ",e);
			throw e;
		} finally {
			if (cmsResponseIstanza != null) {
				File res = new File(cmsResponseIstanza.getFileName());
				String folder = res.getParent();
				res.delete();
				File folderFile = new File(folder);
				folderFile.delete();
			}
		}
		return null;
	}

	private ProtocolloConfigurationRequestBean buildConfigProto(ConfigurazioniEnteDTO configEnte) {
		ProtocolloConfigurationRequestBean configBean=new ProtocolloConfigurationRequestBean();
		configBean.setCodiceApplicazione(this.nomeApplicazione);
		configBean.setAlgoritmoImpronta(configEnte.getProtocollazioneHashAlgorithm());
		configBean.setAmministrazione(configEnte.getProtocollazioneAdministration());
		configBean.setAmministrazioneDenominazione(configEnte.getProtocollazioneDenominazione());
		configBean.setAoo(configEnte.getProtocollazioneAoo());
		configBean.setDataRegistrazione(configEnte.getProtocollazioneDataRegistrazione());
		configBean.setDenominazioneAOO(configEnte.getProtocollazioneAooDenominazione());
		configBean.setEndpoint(configEnte.getProtocollazioneEndpoint());
		configBean.setIndirizzoPostale(configEnte.getProtocollazioneIndirizzoPostale());
		configBean.setMittAooCodiceAoo(null);
		configBean.setNumeroRegistrazione(configEnte.getProtocollazioneNumeroRegistrazione());
		configBean.setPwd(configEnte.getProtocollazionePassword());
		configBean.setRegistro(configEnte.getProtocollazioneRegister());
		configBean.setTipoIndirizzoTelematico(configEnte.getProtocollazioneTipoIndirizzoTelematico());
		configBean.setUtente(configEnte.getProtocollazioneUser());
		configBean.setValoreIndirizzoTelematico(configEnte.getProtocollazioneIndirizzoTelematico());
		return configBean;
	}
	
	

	@Override
	public ProtocolloResponseBean getNumeroProtocollo(GeneratedFileBean generatedFileBean, UUID idPratica,
			ProtocolNumberType pt, boolean usaRegione,String[] mailDestinatari,String[] denominazioneDestinatari,String oggetto) {
		try {
			// Protocollazione
			LOGGER.info("ProtpcolloService - inizio protocollazione codicePratica:" + idPratica);
			// ricavo l'id dell'ente amministratore
			PraticaDTO praticaDto = this.praticaDao.find(idPratica);
			// Ricavo dati per accedere al servizio di protocollazione
			ConfigurazioniEnteDTO configEnte = new ConfigurazioniEnteDTO();
			ConfigurazioniEnteSearch confSearch = new ConfigurazioniEnteSearch();
			List<PlainNumberValueLabel> enteRegione = null;
			if (usaRegione) {
				enteRegione = this.remoteSchema.getEnteRegione();
				confSearch.setIdOrganizzazione(enteRegione.get(0).getValue().toString());
			} else {
				confSearch.setIdOrganizzazione(userUtil.getIntegerId() + "");
			}
			List<ConfigurazioniEnteDTO> configList = this.confEnteService.search(confSearch).getList();
			// per regione c'è già un init ad hoc....
			if (configList == null || configList.size() == 0) {
				new CustomOperationToAdviceException("Configurazione protocollo non presente impossibile procedere !!!");
			}
			configEnte = configList.get(0);
			if (!configEnte.isSistemaProtocollazione()) {
				new CustomOperationToAdviceException("Configurazione protocollo non presente impossibile procedere !!!");
			}
			// inizializzo il servizio di protocollazione
			// setto la configurazione per il client di protocollazione
			//this.protocolNumber.setConfiguration(configEnte);
			if (configEnte.isSistemaProtocollazione()) {
				String senderName = "";
				if (userUtil.hasUserLogged()) {
					senderName = userUtil.getMyProfile().getNome().concat(" ")
							.concat(userUtil.getMyProfile().getCognome());
				} else {
					// TODO: Batch user
					// name= props.getBatchUsr();
					senderName = null;
				}

				//SegnaturaProtocollo segnatura = this.protocolNumber.generateSegnatura(generatedFileBean, pt);
				ProtocolloRequestBean protoBean=new ProtocolloRequestBean();
				if(StringUtil.isNotBlank(oggetto)) {
					protoBean.setOggetto(oggetto);
				}else {
					protoBean.setOggetto("Documento - " + generatedFileBean.getName() +
							"  -  Codice  -  "+praticaDto.getCodicePraticaAppptr());	
				}
				protoBean.setNomeDocumentoPrimario("Primario");
				protoBean.setTitoloDocumentoPrimario("vedi oggetto");
				protoBean.setOggettoDocumentoPrimario(protoBean.getOggetto());
				protoBean.setTipoRiferimento("MIME");
				protoBean.setTipoRiferimentoPrimario("MIME");
				protoBean.setInOut(pt.name());
				protoBean.setMailTo(mailDestinatari);
				protoBean.setDenominazioneDestinatari(denominazioneDestinatari);
				protoBean.setOggetto(protoBean.getOggettoDocumentoPrimario());
				protoBean.setTipoDocumento("pdf");
				MockMultipartFile[] files=new MockMultipartFile[1];
				files[0]=new MockMultipartFile(generatedFileBean.getName(),generatedFileBean.getContent());
				ProtocolloConfigurationRequestBean configBean = buildConfigProto(configEnte);
				ProtocolloResponseBean segnatura = this.protoSvc.eseguiProtocollazioneConfigCustom(files, protoBean, configBean);
				LOGGER.info("protocollaFascicolo - numeroProtocollo:" + segnatura.getNumeroProtocollo());
				this.salvaDatiProtocollo(configEnte, segnatura, pt, idPratica);
				return segnatura;
			}

		} catch (CustomCmisException | CustomOperationException | IOException | CustomValidationException e) {
			LOGGER.info("Errore in getNUmeroProtocollo ",e);
		} catch (Exception e) {
			LOGGER.info("Errore in getNUmeroProtocollo ",e);
		} finally {
		}
		return null;
	}

	private void setFromProperties(ConfigurazioniEnteDTO confEnte) {
		confEnte.setProtocollazioneEndpoint(this.clientProtocolloEndpoint);
		confEnte.setProtocollazioneAdministration(this.clientProtocolloAdministration);
		confEnte.setProtocollazioneAoo(this.clientProtocolloAOO);
		confEnte.setProtocollazioneRegister(this.clientProtocolloRegister);
		confEnte.setProtocollazioneUser(this.clientProtocolloUser);
		confEnte.setProtocollazionePassword(this.clientProtocolloPassword);
		confEnte.setProtocollazioneHashAlgorithm(this.clientProtocolloHashAlgorihtm);
		confEnte.setSistemaProtocollazione(true);
		confEnte.setProtocollazioneIndirizzoPostale(this.clientProtocolloIndirizzopostale);
		confEnte.setProtocollazioneIndirizzoTelematico(this.clientProtocolloIndirizzotelematico);
		confEnte.setProtocollazioneAooDenominazione(this.clientProtocolloAooDenominazione);
		;
		confEnte.setProtocollazioneDenominazione(this.clientProtocolloDenominazione);
		confEnte.setProtocollazioneTipoIndirizzoTelematico(this.clientProtocolloTipoindIrizzoTelematico);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date dateRegistrazione = new Date();
		try {
			dateRegistrazione = format.parse(this.clientProtocolloDataRegistrazione);
		} catch (ParseException e1) {
			LOGGER.info("Errore in parse dataRegistrazione protocollo ",e1);
		}
		confEnte.setProtocollazioneDataRegistrazione(dateRegistrazione);
		confEnte.setProtocollazioneNumeroRegistrazione(this.clientProtocolloNumeroRegistrazione);
	}

	private ConfigurazioniEnteDTO stroreDefaultConfiguration(Long idRegione) {
		ConfigurazioniEnteDTO confEnte = new ConfigurazioniEnteDTO();
		this.setFromProperties(confEnte);
		confEnte.setIdOrganizzazione(idRegione.intValue());
		ConfigurazioniEnteDTO confEnteOld = new ConfigurazioniEnteDTO();
		confEnteOld.setIdOrganizzazione(idRegione.intValue());
		try {
			confEnteOld = confEnteService.find(confEnteOld);
			this.setFromProperties(confEnteOld);
			confEnteService.update(confEnteOld);
		} catch (Exception e1) {
			try {
				confEnteService.insert(confEnte);
			} catch (Exception e) {
				LOGGER.error("Errore nella storeDefaultConfiguration ",e);
			}
		}
		return confEnte;
	}

	/**
	 * Metodo per il salvataggio dei dati utilizzati e dei dati ritornati dalla
	 * protocollazione
	 *
	 * @author G.Lavermicocca
	 * @param idPratica
	 * @date 11 set 2020
	 */
	void salvaDatiProtocollo(ConfigurazioniEnteDTO configEnte, ProtocolloResponseBean segnatura, ProtocolNumberType tipo,
			UUID idPratica) {
		try {
			ProtocolloDTO protocolloEntity = new ProtocolloDTO();
			protocolloEntity.setIdProtocollo(UUID.randomUUID());

			protocolloEntity.setClientprotocolloadministration(configEnte.getProtocollazioneAdministration());
			protocolloEntity.setClientprotocolloaoo(configEnte.getProtocollazioneAoo());
			protocolloEntity.setClientprotocolloendpoint(configEnte.getProtocollazioneEndpoint());
			protocolloEntity.setClientprotocollohashalgorihtm(configEnte.getProtocollazioneHashAlgorithm());
			protocolloEntity.setClientprotocollopassword(configEnte.getProtocollazionePassword());
			protocolloEntity.setClientprotocolloregister(configEnte.getProtocollazioneRegister());
			protocolloEntity.setClientprotocollouser(configEnte.getProtocollazioneUser());

			protocolloEntity.setDataregistrazione(segnatura.getDataRegistrazione());
			protocolloEntity.setNumeroprotocollo(segnatura.getNumeroProtocollo());
			protocolloEntity.setCodiceregistro(segnatura.getCodiceRegistro());
			protocolloEntity.setCodiceamministrazione(segnatura.getCodiceAmministrazione());
			protocolloEntity.setCodiceaoo(segnatura.getCodiceAOO());
			protocolloEntity.setRequest(MDC.get(ProtocolloLogInterceptor.MDC_SOAP_REQUEST));
			protocolloEntity.setResponse(MDC.get(ProtocolloLogInterceptor.MDC_SOAP_RESPONSE));
			protocolloEntity.setIdAllegato(null);
			protocolloEntity.setIdPratica(idPratica);
			protocolloEntity.setProtocollo(segnatura.toFormatoEsteso());
			protocolloEntity.setVerso(tipo.name());
			// verso
			LocalDate dd = LocalDate.parse(segnatura.getDataRegistrazione(), DateTimeFormatter.ofPattern("ddMMyyyy"));
			Date d = Date.from(dd.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			protocolloEntity.setDataEsecuzione(new Timestamp((new Date()).getTime()));
			this.protocolloService.insert(protocolloEntity);
		} catch (Exception e) {
			LOGGER.error("Errore durante il salvataggio dei dati della transazione protocollo", e);
		}
	}
}
