package it.eng.tz.puglia.servizi_esterni.jasper;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dto.AllegatoCustomDTO;
import it.eng.tz.puglia.autpae.dto.InformazioniDTO;
import it.eng.tz.puglia.autpae.dto.TipologicaDTO;
import it.eng.tz.puglia.autpae.dto.TipologicaDestinatarioDTO;
import it.eng.tz.puglia.autpae.dto.BE_to_FE.SchemaRicercaDTO;
import it.eng.tz.puglia.autpae.entity.CampionamentoDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloPublicDto;
import it.eng.tz.puglia.autpae.entity.PlaceholderDTO;
import it.eng.tz.puglia.autpae.entity.TemplateDocDTO;
import it.eng.tz.puglia.autpae.entity.TemplateDocSezioneDTO;
import it.eng.tz.puglia.autpae.enumeratori.Ditta;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.enumeratori.TipoSezioneDoc;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoService;
import it.eng.tz.puglia.autpae.service.interfacce.AzioniService;
import it.eng.tz.puglia.autpae.service.interfacce.ConfigurazionePermessiService;
import it.eng.tz.puglia.autpae.service.interfacce.EsitoService;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloService;
import it.eng.tz.puglia.autpae.service.interfacce.TemplateDocService;
import it.eng.tz.puglia.autpae.service.interfacce.TipoProcedimentoService;
import it.eng.tz.puglia.autpae.utility.DateUtil;
import it.eng.tz.puglia.autpae.utility.StringWrapper;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.error.exception.CustomOperationException;
import it.eng.tz.puglia.error.exception.CustomValidationException;
import it.eng.tz.puglia.servizi_esterni.jasper.dto.JasperAllegatoDTO;
import it.eng.tz.puglia.servizi_esterni.jasper.dto.JasperCampionamentoDTO;
import it.eng.tz.puglia.servizi_esterni.jasper.dto.JasperCampionamentoFirmaDTO;
import it.eng.tz.puglia.servizi_esterni.jasper.dto.JasperCampionamentoFooterDTO;
import it.eng.tz.puglia.servizi_esterni.jasper.dto.JasperCampionamentoListaDestinatariDTO;
import it.eng.tz.puglia.servizi_esterni.jasper.dto.JasperCampionamentoListaFascicoliDTO;
import it.eng.tz.puglia.servizi_esterni.jasper.dto.JasperDTO;
import it.eng.tz.puglia.servizi_esterni.jasper.dto.JasperFascicoloDTO;
import it.eng.tz.puglia.servizi_esterni.jasper.dto.JasperLocalizzazioneInterventoDTO;
import it.eng.tz.puglia.servizi_esterni.jasper.dto.JasperRicercaFascicoliDTO;
import it.eng.tz.puglia.servizi_esterni.jasper.dto.SezioniDinamiche;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.UserUtil;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.CommonService;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.string.StringUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
public class CreaReportServiceImpl implements CreaReportService {
	
	private static final Logger log = LoggerFactory.getLogger(CreaReportServiceImpl.class.getName());
	
	@Value("${template.jasper.regione.indirizzo}")	          private String indirizzoRegione;
	@Value("${template.jasper.regione.pec}")	              private String pecRegione;
	@Value("${template.jasper.regione.tel}")	              private String telRegione;
	@Value("${template.jasper.regione.sezione}")	          private String sezioneRegione;
	@Value("${template.jasper.regione.dipartimento}")	      private String dipartimentoRegione;
	@Value("${template.jasper.regione.dirigente}")	          private String dirigenteRegione;
	@Value("${template.jasper.regione.titoloDirigente}")	  private String titoloDirigenteRegione;
	@Value("${template.jasper.regione.sezionaleProtocollo}")  private String sezionaleProtocolloRegione;
	
	@Autowired private JasperServiceImpl jasperService;
	@Autowired private FascicoloService fascicoloService;
	@Autowired private AzioniService azioniService;
	@Autowired private CommonService commonService;
	@Autowired private UserUtil userUtil;
	@Autowired private TipoProcedimentoService tipoProcedimentoService;
	@Autowired private ApplicationProperties props;
	@Autowired private AllegatoService allegatoSvc;
	@Autowired private TemplateDocService templaceDocSvc;
	@Autowired private ConfigurazionePermessiService configurazionePermessiService;
	@Autowired private EsitoService esitoService;
	
    
	@Override
	public JasperPrint creaPdf_RicevutaDiTrasmissione(final Long idFascicolo, final String protocollo, final List<TipologicaDestinatarioDTO> ulterioriDestinatari, InformazioniDTO info, final boolean isBatchUser) {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		try {
//			List<?> parameters = this.popolaDati_RicevutaDiTrasmissione(idFascicolo, protocollo, ulterioriDestinatari);
			JasperDTO jDTO = new JasperDTO();
			if(info == null)
				info = this.fascicoloService.datiCompleti(idFascicolo);
			jDTO = this.popolaDati_RicevutaDiTrasmissione(idFascicolo, protocollo, ulterioriDestinatari, info, isBatchUser).get(0);
			this.creaTemplateDinamico("DocumentoDiTrasmissione", jDTO);
			
			JasperPrint jasper = jasperService.compilaReport("/jasper/".concat("DocumentoDiTrasmissione").concat(".jasper"),  Collections.singletonList(jDTO), null);
			return jasper;
		} catch (Exception e) {
			log.error("Errore in durante la creazione della ricevuta di trasmissione",e);
		}
		return null;
	}
	
	@Override
	public JasperPrint creaPdfLetteraDiTrasmissioneEsitoProvvedimento(final Long idFascicolo, final String protocollo, final List<TipologicaDestinatarioDTO> ulterioriDestinatari)
	{
		JasperPrint jasper = null;
		try
		{
			JasperDTO jDTO = popolaLetteraTrasmissione(idFascicolo, protocollo, ulterioriDestinatari);
			creaTemplateDinamico("LETTERA_TRASMISSIONE_ESITO_VERIFICA", jDTO);
			jasper = jasperService.compilaReport("/jasper/LETTERA_TRASMISSIONE_ESITO_VERIFICA.jasper",  Collections.singletonList(jDTO), null);
		}
		catch(Exception e)
		{
			log.error("Errore in durante la creazione della ricevuta di trasmissione",e);
		}
		return jasper;
	}
	
	@Override
	public JasperPrint creaPdf_EsitoCampionamento(final String protocollo, final CampionamentoDTO campionamento, 
												  final List<TipologicaDTO> destinatariTO, final List<TipologicaDTO> destinatariCC,
												  final List< FascicoloDTO>  fascicoliAll, final List< FascicoloDTO> fascicoliSelected, final List<FascicoloDTO> fascicoliNotSelected) {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		try {
			 JasperCampionamentoDTO jDto = this.popolaDati_EsitoCampionamento(protocollo, campionamento, destinatariTO, destinatariCC, fascicoliAll, fascicoliSelected, fascicoliNotSelected);
			creaTemplateDinamico("Campionamento", jDto);
			JasperPrint jasper = jasperService.compilaReport("/jasper/".concat("Campionamento").concat(".jasper"), Collections.singletonList(jDto), null);
			return jasper;
		} catch (Exception e) {
			log.error("Errore durante la creazione del pdf esito campionamento",e);
		}
		return null;
	}
	
	private JasperCampionamentoDTO popolaDati_EsitoCampionamento(final String protocollo, final CampionamentoDTO campionamento, 
																	   final List<TipologicaDTO> destinatariTO, final List<TipologicaDTO> destinatariCC,
																	   final List< FascicoloDTO>  fascicoliAll, final List< FascicoloDTO> fascicoliSelected, final List<FascicoloDTO> fascicoliNotSelected) 
	{
		JasperCampionamentoDTO dati = new JasperCampionamentoDTO();
		dati.setNumeroProtocolo(protocollo);
		dati.setDataProtocolo(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		dati.setSezione(this.sezioneRegione);
		dati.setDipartimento(this.dipartimentoRegione);
		dati.setDirigente(this.dirigenteRegione);
		dati.setSezionaleProtocollo(this.sezionaleProtocolloRegione);
		dati.setDataRegistrazionePrimoPiano(new SimpleDateFormat("dd/MM/yyyy").format(campionamento.getDataInizio()));
		Date dataEnd=DateUtil.addDays(campionamento.getDataCampionatura(), -1);
		dati.setDataRegistrazionePrimoPiano28(new SimpleDateFormat("dd/MM/yyyy").format(dataEnd)); // dovrebbe essere sempre TODAY
		
		JasperCampionamentoFooterDTO footer = new JasperCampionamentoFooterDTO();
		footer.setIndirizzoRegione(this.indirizzoRegione);
		footer.setPecRegione(this.pecRegione);
		footer.setTelRegione(this.telRegione);
		dati.setFooterFields(Collections.singletonList(footer));
		
		JasperCampionamentoFirmaDTO firma = new JasperCampionamentoFirmaDTO();
		firma.setDirigente(this.dirigenteRegione);
		firma.setTitolo_dirigente(this.titoloDirigenteRegione);
		dati.setFirmaFields(Collections.singletonList(firma));
		
		if (destinatariTO!=null) {
			destinatariTO.forEach(dest->{
				dati.getDestinatariTO().add(new JasperCampionamentoListaDestinatariDTO(dest.getLabel(), dest.getCodice()));
			});
		}
		
		if (destinatariCC!=null) {
			destinatariCC.forEach(dest->{
			});
		}
		
		if (fascicoliAll!=null) {
			fascicoliAll.forEach(fascicolo->{
				JasperCampionamentoListaFascicoliDTO infoFascicolo = new JasperCampionamentoListaFascicoliDTO(fascicolo);
				try {
					infoFascicolo.setEnteDiRegistrazione(commonService.getDenominazioneOrganizzazione(fascicolo.getOrgCreazione()));
				} catch (Exception e) {
					infoFascicolo.setEnteDiRegistrazione("ERRORE");
				}
				dati.getListAll().add(infoFascicolo);
			});
		}
		
		if (fascicoliSelected!=null) {
			fascicoliSelected.forEach(fascicolo->{
				JasperCampionamentoListaFascicoliDTO infoFascicolo = new JasperCampionamentoListaFascicoliDTO(fascicolo);
				try {
					infoFascicolo.setEnteDiRegistrazione(commonService.getDenominazioneOrganizzazione(fascicolo.getOrgCreazione()));
				} catch (Exception e) {
					infoFascicolo.setEnteDiRegistrazione("ERRORE");
				}
				dati.getListSelected().add(infoFascicolo);
			});
		}

		if (fascicoliNotSelected!=null) {
			fascicoliNotSelected.forEach(fascicolo->{
				JasperCampionamentoListaFascicoliDTO infoFascicolo = new JasperCampionamentoListaFascicoliDTO(fascicolo);
				try {
					infoFascicolo.setEnteDiRegistrazione(commonService.getDenominazioneOrganizzazione(fascicolo.getOrgCreazione()));
				} catch (Exception e) {
					infoFascicolo.setEnteDiRegistrazione("ERRORE");
				}
				dati.getListNotSelected().add(infoFascicolo);
			});
		}
		
		return dati;
	}
	
	private String parseHtml(String htmlString) {
		if(htmlString.contains("<p>")) {
			htmlString = htmlString.replace("<p>", ""); //alla chiusura metto br
		}
		if(htmlString.contains("</p>")) {
			htmlString = htmlString.replace("</p>", "<br>");
		}
		 if(htmlString.contains("<strong>")){
			htmlString = htmlString.replace("<strong>", "<b>");
		}
		 if(htmlString.contains("</strong>")) {
			htmlString = htmlString.replace("</strong>", "</b>");
		}
		 if(htmlString.contains("<em>")) {
			 htmlString = htmlString.replace("<em>","<i>");
		 }
		 if(htmlString.contains("</em>")) {
			 htmlString = htmlString.replace("</em>","</i>");
		 }
		
		return htmlString;
	}
	
	private String parsePlaceholder(String toParse, final List<PlaceholderDTO> list, final Object jasperDtoObject) {
		JasperDTO jDTO = null;
		JasperCampionamentoDTO jDTOCampionamento = null;
		if (list == null)
			return toParse;
		if (jasperDtoObject instanceof JasperDTO) {
			jDTO = (JasperDTO) jasperDtoObject;
		} else if (jasperDtoObject instanceof JasperCampionamentoDTO) {
			jDTOCampionamento = (JasperCampionamentoDTO) jasperDtoObject;
		}

		for (PlaceholderDTO placeholderDocDTO : list) {
			try {
				switch (placeholderDocDTO.getCodice()) {
				case "ID_FASCICOLO":
					toParse = toParse.replace("{ID_FASCICOLO}", Integer.toString(jDTO.getId_fascicolo()) == null ? ""
							: Integer.toString(jDTO.getId_fascicolo()));
					break;
				case "COMUNE":
					List<JasperLocalizzazioneInterventoDTO> listLocalizzazioni = jDTO.getListaLocalizzazioni();
					if (!listLocalizzazioni.isEmpty()) {
						List<String> listaComuni = new ArrayList<String>();
						listLocalizzazioni.forEach(el -> {
							listaComuni.add(el.getComune());
						});
						String listaComuniString = "";
						listaComuniString = listaComuni.stream().collect(Collectors.joining(", "));
						toParse = toParse.replace("{COMUNE}", listaComuniString == null ? "" : listaComuniString);
					}
					break;

				case "OGGETTO":
					toParse = toParse.replace("{OGGETTO}",
							jDTO.getOggettoIntervento() == null ? "" : jDTO.getOggettoIntervento());
					break;
				case "TIPO_PROCEDIMENTO":
					toParse = toParse.replace("{TIPO_PROCEDIMENTO}",
							jDTO.getTipoProcedimento() == null ? "" : jDTO.getTipoProcedimento());
					break;
				case "CODICE_FASCICOLO":
					toParse = toParse.replace("{CODICE_FASCICOLO}",
							jDTO.getCodicePratica() == null ? "" : jDTO.getCodicePratica());
					break;
				case "DATA_PRESENTAZIONE":
					toParse = toParse.replace("{DATA_PRESENTAZIONE}",
							jDTO.getDataDelibera() == null ? "" : DateUtil.dataParse(jDTO.getDataDelibera()));
					break;
				case "ESITO_VERIFICA":
					toParse = toParse.replace("{ESITO_VERIFICA}",
							jDTO.getEsitoVerifica() == null ? "" : jDTO.getEsitoVerifica().getTextValue());
					break;
				case "NUMERO_PROVVEDIMENTO_ESITO_VERIFICA":
					toParse = toParse.replace("{NUMERO_PROVVEDIMENTO_ESITO_VERIFICA}",
							jDTO.getNumeroProvvedimentoEsito() == null ? "" : jDTO.getNumeroProvvedimentoEsito());
					break;
				case "DATA_PROVVEDIMENTO_ESITO_VERIFICA":
					toParse = toParse.replace("{DATA_PROVVEDIMENTO_ESITO_VERIFICA}",
							jDTO.getDataProvvedimentoEsito() == null ? ""
									: DateUtil.dataParse(jDTO.getDataProvvedimentoEsito()));
					break;
				// campionamento
				case "DATA_REGISTRAZIONE_PRIMO_PIANO":
					toParse = toParse.replace("{DATA_REGISTRAZIONE_PRIMO_PIANO}",
							jDTOCampionamento.getDataRegistrazionePrimoPiano() == null ? ""
									: jDTOCampionamento.getDataRegistrazionePrimoPiano());
					break;
				case "DATA_FINE_CAMPIONAMENTO":
					toParse = toParse.replace("{DATA_FINE_CAMPIONAMENTO}",
							jDTOCampionamento.getDataRegistrazionePrimoPiano28() == null ? ""
									: jDTOCampionamento.getDataRegistrazionePrimoPiano28());
					break;
				}
			} catch (Exception e) {
				log.error("Errore durante parsing placeholder ", e);
			}
		}
		return toParse;
	}
	
	/* Creazione del template dinamico */
	private void creaTemplateDinamico(final String nomeTemplate, final SezioniDinamiche jasperObjConSezioni) throws Exception {

		Map<String,Object> parameters=new HashMap<>();
		
		TemplateDocDTO template = this.templaceDocSvc.info(nomeTemplate);
		List<TemplateDocSezioneDTO> listaSezioni = template.getSezioni();
		
		try {
			listaSezioni.forEach(sezione-> {
				if(sezione.getTipoSezione().equals(TipoSezioneDoc.IMAGE)) {
					 if(sezione.getIdAllegato()!=null) {
						//prelevare dal cms il contenuto del file puntato da idAllegati
							try {
								File f=allegatoSvc.downloadFile(TipoAllegato.IMAGE_TEMPLATE_DOC, sezione.getIdAllegato(), null, null);
								Path path=f.toPath();
								String b64Logo = new String(java.util.Base64.getEncoder().encode(Files.readAllBytes(path)),"UTF-8");
								parameters.put(sezione.getNome(), b64Logo);
							} catch (Exception e) {
								log.error("Errore durante il prelievo del logo con nome sezione :" +sezione.getNome(),e);
							}	 
					 }else {//prelevo il default
						 try {
							Resource res = this.templaceDocSvc.getImageDefault(sezione);
							String b64Logo = new String(java.util.Base64.getEncoder().encode(res.getInputStream().readAllBytes()),"UTF-8");
							parameters.put(sezione.getNome(), b64Logo);
						} catch (CustomOperationToAdviceException | IOException e) {
							log.error("Errore durante il prelievo del logo default con nome sezione :" +sezione.getNome(),e);
						}
					 }
				}
				else if(sezione.getTipoSezione().equals(TipoSezioneDoc.HTML)) {
					String parseParameters = this.parseHtml(sezione.getValore());
					String parsePlaceholder = this.parsePlaceholder(parseParameters, sezione.getPlaceholders(),jasperObjConSezioni);
					parameters.put(sezione.getNome(), parsePlaceholder);
				}
				else {
					String parsePlaceholder = this.parsePlaceholder(sezione.getValore(), sezione.getPlaceholders(),jasperObjConSezioni);
					parameters.put(sezione.getNome(),parsePlaceholder);
				}
			});
		} catch (Exception e1) {
//			e1.printStackTrace();
			log.error("Errore durante la gestione dei placeholder :", e1);
			throw e1;
		}
		
		jasperObjConSezioni.setSezioniDinamiche(parameters);
//		return jasperDTO;
	}

	@Override
	public JasperPrint creaPdf_DemoTemplate(final String nomeTemplate) {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		try {
			SezioniDinamiche jDTO=null;
			//  prelevare parametri da tabella template_doc_sezione tramite repositoriy TemplateDocFullRepository
//			List<TemplateDocSezioneDTO> listaSezioni = templateDocDao.getSezioniTemplate(nomeTemplate);
			//creare la mappa con {nomesezione,valore}
//			Map<String,Object> parameters=new HashMap<>();
			if(nomeTemplate.equals("Campionamento")) {
				 jDTO = new JasperCampionamentoDTO();
			    this.creaTemplateDinamico(nomeTemplate, jDTO);
			}else {
				 jDTO = new JasperDTO();
			    this.creaTemplateDinamico(nomeTemplate, jDTO);	
			}
			
//			jDTO.setSezioniDinamiche(parameters);
//			
//			
//			listaSezioni.forEach(sezione-> {
//				if(sezione.getTipoSezione().equals(TipoSezioneDoc.IMAGE) && sezione.getIdAllegato()!=null) {
//					//prelevare dal cms il contenuto del file puntato da idAllegati
//					try {
//						File f=allegatoSvc.downloadFile(TipoAllegato.IMAGE_TEMPLATE_DOC, sezione.getIdAllegato(), null, null, null);
//						Path path=f.toPath();
//						String b64Logo = new String(java.util.Base64.getEncoder().encode(Files.readAllBytes(path)),"UTF-8");
//						parameters.put(sezione.getNome(), b64Logo);
//					} catch (Exception e) {
//						log.error("Errore durante il prelievo del logo con nome sezione :" +sezione.getNome(),e);
//					}
//					//metterlo in una cartella locale
//					//parameters.put(sezione.getNome(), sezione.getValore());
//				}
//				else if(sezione.getTipoSezione().equals(TipoSezioneDoc.HTML)) {
//					String parseParameters = this.parseHtml(sezione.getValore());
////					String parsePlaceholder = this.parsePlaceholder(parseParameters, sezione.getPlaceholders());
//					parameters.put(sezione.getNome(), parseParameters);
//				}
//				else {
//					parameters.put(sezione.getNome(), sezione.getValore());
//				}
//			});
			JasperPrint jasper = jasperService.compilaReport("/jasper/".concat(nomeTemplate).concat(".jasper"), Collections.singletonList(jDTO), null);
//			JasperPrint jasper = jasperService.compilaReport("/jasper/".concat(nomeTemplate).concat(".jasper"), parameters);
			return jasper;
		} catch (Exception e) {
			log.error("Errore durante la creazione del pdf demo template",e);
		}
		return null;
	}
	
/*	@Override
	public List<JasperDTO> test_JasperJSON(Long idFascicolo) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		try {
			List<JasperDTO> parameters = this.popolaDati_RicevutaDiTrasmissione(idFascicolo, null);
			return parameters;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}	*/
	
	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<InputStreamResource> exportFascicoliCSV(final PaginatedList<? extends FascicoloPublicDto> lista) throws IOException, JRException, CustomOperationException, CustomValidationException {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {

			Path generated=Files.createTempFile("", "ListaFascicoliRicerca".concat("_".concat(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())).concat(".CSV")));
			//File generated = new File(tempPath, "ListaFascicoliRicerca".concat("_".concat(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())).concat(".CSV")));
			Map<String, Object> meterMap = new HashMap<>();	
			meterMap.putAll(creaMappaFascicoli(lista.getList()));

			try (BufferedWriter writer = Files.newBufferedWriter(generated);
					CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.EXCEL.withHeader ( "Codice Fascicolo", 
																								"Descrizione", 
																								"Tipologia Procedimento", 
																								"Ufficio", 
																								"Responsabile Procedimento", 
																								"Numero Provvedimento",
																								"Data Provvedimento",
																								"Esito",
																								"Stato Registrazione",
																								"Esito Verifica" 			));
				)  {
				Iterator<HashMap<String, Object>> iterPlanDto = null;
				try {
					if (!lista.getList().isEmpty()) {
						List<String> listPlanDto = null;
						Map.Entry<String, Object> fascicoloDtoList = meterMap.entrySet().iterator().next();
						iterPlanDto = ((List<HashMap<String, Object>>) fascicoloDtoList.getValue()).iterator();
						while (iterPlanDto.hasNext()) {
							HashMap<String, Object> hmPlanDto = iterPlanDto.next();
							listPlanDto = new ArrayList<String>();
							listPlanDto.add( (String)hmPlanDto.get("codiceFascicolo"));
							listPlanDto.add(((String)hmPlanDto.get("descrizione")).replaceAll("[,\\t\\n\\r]+"," "));
							listPlanDto.add(((String)hmPlanDto.get("tipologiaIntervento")).replaceAll("[,\\t\\n\\r]+"," "));
							listPlanDto.add( (String)hmPlanDto.get("comune"));
							listPlanDto.add( (String)hmPlanDto.get("responsabileProcedimento"));
							listPlanDto.add( (String)hmPlanDto.get("numeroProvvedimento"));
							listPlanDto.add( (String)hmPlanDto.get("dataProvvedimento"));
							listPlanDto.add( (String)hmPlanDto.get("esito"));
							listPlanDto.add( (String)hmPlanDto.get("statoRegistrazione"));
							listPlanDto.add( (String)hmPlanDto.get("esitoVerifica"));
							csvPrinter.printRecord(listPlanDto);
						}
					}
				}
				catch (Exception e) {
					throw new CustomOperationException("Stampa del file CSV failed");				
				}finally {
					if (iterPlanDto!=null) {
						iterPlanDto.remove();
					}
				}
				csvPrinter.flush();
			}
			HttpHeaders headers = getHttpHeaders(generated.getFileName().toString().replaceAll("-[0-9]*[\\.]", "."), true);
			//  headers.setContentDisposition(ContentDisposition.builder("inline").build()); 
			headers.setContentType(MediaType.parseMediaType("application/csv"));
			headers.setContentLength(Files.size(generated));

			//Elimina file
			try (FileInputStream fis = new FileInputStream(generated.toFile());) {
				IOUtils.copy(fis, output);
			} catch (Exception e) {
				throw new CustomOperationException("Copy of downloaded file failed");
			} finally {
				if (StringUtil.isNotEmpty(generated.toString())) {
					try {
						Files.deleteIfExists(generated);
					} catch (IOException e) {
						log.warn("Deleting local file {} failed", generated);
						throw new CustomOperationException("Deleting local file failed");
					}
				}
			}
			InputStream inputStream = new ByteArrayInputStream(output.toByteArray());
			InputStreamResource arrResponse = new InputStreamResource(inputStream);
			return new ResponseEntity<InputStreamResource>(arrResponse, headers, HttpStatus.OK);

		} catch (Exception e) {
			throw new CustomOperationException(e);
		}
	}
	
	private Map<String, Object> creaMappaFascicoli(List<? extends FascicoloPublicDto> list) {
		if (list == null)
			list = Collections.emptyList();

		List<HashMap<Object, Object>> fascicoloDtoList = new ArrayList<>(list.size());
		Map<String, Object> result = new HashMap<>(list.size());		
		
		SchemaRicercaDTO permessi = new SchemaRicercaDTO(true, true);
		if (!userUtil.hasUserLogged()) {
			try {
				permessi = new SchemaRicercaDTO(configurazionePermessiService.find("PUBLIC"));
			} catch (Exception e) {
				log.error("nessuno schema permessi per PUBLIC",e);
				permessi = new SchemaRicercaDTO(false, false);
			}
		}
		
		for(int n=0; n<list.size(); n++) {
			JasperFascicoloDTO dto = new JasperFascicoloDTO(list.get(n), permessi);
			
			HashMap<Object, Object> planDto = new HashMap<>();
			
			planDto.put("codiceFascicolo"         , dto.getCodiceFascicolo());
			planDto.put("descrizione"             , dto.getDescrizione());
			planDto.put("tipologiaIntervento"     , dto.getTipologiaIntervento());					
			planDto.put("responsabileProcedimento", dto.getResponsabileProcedimento());
			planDto.put("numeroProvvedimento"     , dto.getNumeroProvvedimento());
			planDto.put("esito"                   , dto.getEsito());
			planDto.put("statoRegistrazione"      , dto.getStatoRegistrazione());
			planDto.put("esitoVerifica"           , dto.getEsitoVerifica());
			planDto.put("comune"           		  , dto.getComune());
			planDto.put("dataProvvedimento"       , dto.getDataProvvedimento());
			
			fascicoloDtoList.add(planDto);
		}
		result.put("fascicoloDtoList", fascicoloDtoList);
		return result;
	}
	
	private HttpHeaders getHttpHeaders(final String filename, final boolean asAttachment) {
		HttpHeaders headers = new HttpHeaders();
		String attachment = asAttachment ? "attachment; " : "";
		headers.add("Content-Disposition", attachment + "filename=" + filename);
		headers.add("Access-Control-Expose-Headers", "Content-Disposition");
		return headers;
	}
	
	private List<JasperDTO> popolaDati_RicevutaDiTrasmissione(final Long idFascicolo, final String protocollo, final List<TipologicaDestinatarioDTO> ulterioriDestinatari, final InformazioniDTO informazioni, final boolean isBatchUser) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		JasperDTO jasperDTO = new JasperDTO();
		jasperDTO.setCodiceApp(props.getCodiceApplicazione());
		jasperDTO.setProtocollo(protocollo);
		
		List<TipologicaDestinatarioDTO> destinatari = azioniService.getDestinatariTrasmissione(idFascicolo, ulterioriDestinatari, isBatchUser);
		List<TipologicaDTO> destinatariJasper = new ArrayList<TipologicaDTO>(destinatari.size());
		destinatari.forEach(elem->{
			destinatariJasper.add(new TipologicaDTO(elem.getEmail(), elem.getNome()));
		});
		jasperDTO.setListaDestinatari(destinatariJasper);
		
		jasperDTO.setSanatoria(informazioni.getSanatoria());
		jasperDTO.setCodicePratica(informazioni.getCodice());
		jasperDTO.setUfficio(informazioni.getUfficio());
		jasperDTO.setRichiedente(informazioni.getRichiedente().getNome()+" "+informazioni.getRichiedente().getCognome());
		jasperDTO.setCodiceTipoProcedimento(informazioni.getTipoProcedimento().name());
		jasperDTO.setTipoProcedimento(tipoProcedimentoService.find(jasperDTO.getCodiceTipoProcedimento()).getDescrizione());
		
		jasperDTO.setOggettoIntervento(informazioni.getOggettoIntervento());
		
		jasperDTO.getRichiedenteInfo().add(informazioni.getRichiedente());
		if (StringUtil.isNotEmpty(jasperDTO.getRichiedenteInfo().get(0).getDittaInQualitaDi())) {
			if (jasperDTO.getRichiedenteInfo().get(0).getDittaInQualitaDi().equalsIgnoreCase("altro"))
				jasperDTO.getRichiedenteInfo().get(0).setDittaInQualitaDi(jasperDTO.getRichiedenteInfo().get(0).getDittaQualitaAltro());
			else
				jasperDTO.getRichiedenteInfo().get(0).setDittaInQualitaDi(Ditta.valueOf(jasperDTO.getRichiedenteInfo().get(0).getDittaInQualitaDi()).getTextValue());
		}
		
		jasperDTO.setProvvedimentoNumero(informazioni.getNumeroProvvedimento());
		jasperDTO.setProvvedimentoDataAutorizzazione(informazioni.getDataRilascioAutorizzazione());
		jasperDTO.setProvvedimentoEsito(informazioni.getEsito().getTextValue());
		jasperDTO.setProvvedimentoRup(informazioni.getRup());
		
		jasperDTO.getListaAllegatiProvvedimento().add(new JasperAllegatoDTO(informazioni.getProvvedimento().getProvvedimento().getDataCaricamento(), 
																			informazioni.getProvvedimento().getProvvedimento().getNome(), 
																			Collections.singletonList(TipoAllegato.PROVVEDIMENTO_FINALE.getTextValue()),
																			informazioni.getProvvedimento().getProvvedimento().getChecksum()));
		if (informazioni.getProvvedimento().getParere()!=null && informazioni.getProvvedimento().getParere().getId()!=null) {
			jasperDTO.getListaAllegatiProvvedimento().add(new JasperAllegatoDTO(informazioni.getProvvedimento().getParere().getDataCaricamento(), 
																				informazioni.getProvvedimento().getParere().getNome(), 
																				Collections.singletonList(TipoAllegato.PARERE_MIBAC.getTextValue()),
																				informazioni.getProvvedimento().getParere().getChecksum()));
		}
		if(informazioni.getProvvedimento().getProvvedimentoPrivato()!=null && 
			informazioni.getProvvedimento().getProvvedimentoPrivato().getId()!=null) {
			AllegatoCustomDTO itemAllegato = informazioni.getProvvedimento().getProvvedimentoPrivato();
			jasperDTO.getListaAllegatiProvvedimento().add(new JasperAllegatoDTO(
					itemAllegato.getDataCaricamento(), 
					itemAllegato.getNome(), 
					Collections.singletonList(TipoAllegato.PROVVEDIMENTO_FINALE_PRIVATO.getTextValue()),
					itemAllegato.getChecksum()));	
		}
		if(informazioni.getProvvedimento().getParerePrivato()!=null && 
				informazioni.getProvvedimento().getParerePrivato().getId()!=null) {
				AllegatoCustomDTO itemAllegato = informazioni.getProvvedimento().getParerePrivato();
				jasperDTO.getListaAllegatiProvvedimento().add(new JasperAllegatoDTO(
						itemAllegato.getDataCaricamento(), 
						itemAllegato.getNome(), 
						Collections.singletonList(TipoAllegato.PARERE_MIBAC_PRIVATO.getTextValue()),
						itemAllegato.getChecksum()));	
		}
		informazioni.getLocalizzazione().getLocalizzazioneComuni().forEach(elem->{
			jasperDTO.getListaLocalizzazioni().add(new JasperLocalizzazioneInterventoDTO(elem));
			
		});
		
		if (informazioni.getLocalizzazione().getGeoAllegati()!=null) {
			informazioni.getLocalizzazione().getGeoAllegati().forEach(elem->{
				jasperDTO.getListaAllegatiLocalizzazione().add(
						new JasperAllegatoDTO(elem.getDataCaricamento(), elem.getNome(), 
								Collections.singletonList(TipoAllegato.LOCALIZZAZIONE.getTextValue()),elem.getChecksum()));
			});
		}
		if (jasperDTO.getListaAllegatiLocalizzazione()!=null && jasperDTO.getListaAllegatiLocalizzazione().isEmpty())
			jasperDTO.setListaAllegatiLocalizzazione(null);
		
		if (ListUtil.isNotEmpty(informazioni.getAllegati())) {
			Map<Long, List<AllegatoCustomDTO>> raggruppati = informazioni.getAllegati().stream().collect(Collectors.groupingBy(elem->elem.getId()));
			raggruppati.values().forEach(listaAllegatiTipo->{
				AllegatoCustomDTO elem = listaAllegatiTipo.get(0);
				List<String> tipi = new ArrayList<>();
				elem.getTipi().forEach(tipo-> {
					tipi.add(tipo.getTextValue());
				});
				jasperDTO.getListaAllegati().add(new JasperAllegatoDTO(elem.getDataCaricamento(), elem.getNome(), tipi,elem.getChecksum()));
			});
//			informazioni.getAllegati().forEach(elem->{
//				List<String> tipi = new ArrayList<>();
//				elem.getTipi().forEach(tipo-> {
//					tipi.add(tipo.getTextValue());
//				});
//				jasperDTO.getListaAllegati().add(new JasperAllegatoDTO(elem.getDataCaricamento(), elem.getNome(), tipi,elem.getChecksum()));
//			});
		}
		if (jasperDTO.getListaAllegati()!=null && jasperDTO.getListaAllegati().isEmpty()) {
			jasperDTO.setListaAllegati(null);
		}
			
		
		informazioni.getInterventoSelezionati().forEach(selezionato->{
			informazioni.getIntervento().getLista().forEach(opzione->{
				if (selezionato.equals(opzione.getId())) {
					switch (opzione.getZona()) {
					case 1:
						String opzLab1 = "• "+opzione.getLabel();
						if (opzione.getLabel().toLowerCase().startsWith("altro"))
							opzLab1 = opzLab1 + " " + informazioni.getInterventoDettaglio();
						jasperDTO.setInterventoTipologia(opzLab1);
						break;
					case 2:
						String opzLab2 = "• "+opzione.getLabel();
						if (opzione.getLabel().toLowerCase().startsWith("rimessa in ripristino"))
							opzLab2 = opzLab2 + ": " + informazioni.getInterventoDettaglio();
						if (opzione.getLabel().toLowerCase().startsWith("altro"))
							opzLab2 = opzLab2 + " "  + informazioni.getInterventoSpecifica();
						jasperDTO.getInterventoCaratterizzazione().add(new StringWrapper(opzLab2));
						break;
					case 3:
						jasperDTO.setInterventoTempo("• "+opzione.getLabel());
						break;
					case 4:
						jasperDTO.getInterventoQualificazioneA().add(new StringWrapper("• "+opzione.getLabel()));
						break;
					case 400:
						jasperDTO.getInterventoQualificazioneA().add(new StringWrapper("• "+opzione.getLabel()));
						break;
					case 40000:
						jasperDTO.getInterventoQualificazioneB().add(new StringWrapper("- "+opzione.getLabel()));
						break;
					default:
						log.error("Impossibile identificare il tipo di opzione selezionata nel Tab Intervento!");
						break;
					}
				}
			});
		});
		
		if (jasperDTO.getInterventoQualificazioneA()==null || jasperDTO.getInterventoQualificazioneA().isEmpty()) {
			jasperDTO.setInterventoQualificazioneA(jasperDTO.getInterventoQualificazioneB());
			jasperDTO.setInterventoQualificazioneB(null);
		}
			
		if (jasperDTO.getInterventoCaratterizzazione()!=null && jasperDTO.getInterventoCaratterizzazione().isEmpty())
			jasperDTO.setInterventoCaratterizzazione(null);
		if (jasperDTO.getInterventoQualificazioneA  ()!=null && jasperDTO.getInterventoQualificazioneA  ().isEmpty())
			jasperDTO.setInterventoQualificazioneA  (null);
		if (jasperDTO.getInterventoQualificazioneB  ()!=null && jasperDTO.getInterventoQualificazioneB  ().isEmpty())
			jasperDTO.setInterventoQualificazioneB  (null);
		
		if (jasperDTO.getCodiceApp().equalsIgnoreCase("PARERI")) {
			AllegatoCustomDTO allegato = new AllegatoCustomDTO();
			allegato.setNome(informazioni.getRichiedente().getResponsabile().getDocumentoRiconoscimento().getNome());
			allegato.setDataCaricamento(informazioni.getRichiedente().getResponsabile().getDocumentoRiconoscimento().getDataCaricamento());
			allegato.setMimeType(informazioni.getRichiedente().getResponsabile().getRiconoscimentoTipo().name());
			informazioni.getRichiedente().getResponsabile().setListaAllegati(Collections.singletonList(allegato));
			jasperDTO.setResponsabileInfo(Collections.singletonList(informazioni.getRichiedente().getResponsabile()));
			jasperDTO.setDataDelibera(informazioni.getDataDelibera());
			jasperDTO.setDeliberaNum(informazioni.getDeliberaNum());
			jasperDTO.setOggettoDelibera(informazioni.getOggettoDelibera());
			jasperDTO.setInfoDeliberePrecedenti(informazioni.getInfoDeliberePrecedenti());
			jasperDTO.setDescrizioneIntervento(informazioni.getDescrizioneIntervento());
		}
		else if (jasperDTO.getCodiceApp().equalsIgnoreCase("PUTT")) {
			jasperDTO.setDataDelibera(informazioni.getDataDelibera());
		}
		return Collections.singletonList(jasperDTO);
	}
	
	private JasperDTO popolaLetteraTrasmissione(final Long idFascicolo, final String protocollo, final List<TipologicaDestinatarioDTO> ulterioriDestinatari) throws Exception
	{
		InformazioniDTO informazioni = fascicoloService.datiCompleti(idFascicolo);
		JasperDTO jDto = new JasperDTO();
		jDto.setCodicePratica(informazioni.getCodice());
		jDto.setId_fascicolo(informazioni.getId().intValue());
		jDto.setEsitoVerifica(informazioni.getEsitoVerifica());
		jDto.setNumeroProvvedimentoEsito(informazioni.getEsitoNumeroProvvedimento());
		jDto.setDataProvvedimentoEsito(informazioni.getEsitoDataRilascioAutorizzazione());
		jDto.setCodiceTipoProcedimento(informazioni.getTipoProcedimento().name());
		jDto.setDataDelibera(informazioni.getDataDelibera());
		jDto.setProtocollo(protocollo);
		List<TipologicaDestinatarioDTO> destinatari = esitoService.destinatariEsito(idFascicolo);
		List<TipologicaDTO> destinatariJasper = new ArrayList<TipologicaDTO>();
		if(destinatari != null && !destinatari.isEmpty())
			destinatariJasper = destinatari.stream().map(TipologicaDTO::new).collect(Collectors.toList());
		jDto.setListaDestinatari(destinatariJasper);
		return jDto;
	}

	@Override
	public ResponseEntity<InputStreamResource> exportFascicoliPDF(final PaginatedList<? extends FascicoloPublicDto> lista) throws IOException, JRException, CustomOperationException, CustomValidationException {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
			File generated=null;
			if (lista.getList()==null || lista.getList().isEmpty()) {
				FascicoloPublicDto dummyFasc = new FascicoloPublicDto();
				dummyFasc.setUfficio("");
				generated= this.generateFascicoliPDF(Collections.singletonList(dummyFasc));
			}else {
				generated= this.generateFascicoliPDF(lista.getList());	
			}
			HttpHeaders headers = getHttpHeaders(generated.getName().replaceAll("-[0-9]*[\\.]", "."), true);
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			headers.setContentLength(generated.length());
			//Elimina file
			try (FileInputStream fis = new FileInputStream(generated.getPath());) {
				IOUtils.copy(fis, output);
			} catch (Exception e) {
				throw new CustomOperationException("Copy of downloaded file failed");
			} finally {
				if (StringUtil.isNotEmpty(generated.getPath())) {
					try {
						Files.deleteIfExists(generated.toPath());
					} catch (IOException e) {
						log.warn("Deleting local file {} failed", generated.toPath());
						throw new CustomOperationException("Deleting local file failed");
					}
				}
			}
			InputStream inputStream = new ByteArrayInputStream(output.toByteArray());
			InputStreamResource arrResponse = new InputStreamResource(inputStream);
			return new ResponseEntity<InputStreamResource>(arrResponse, headers, HttpStatus.OK);

		} catch (Exception e) {
			log.error("Errore durante la creazione del pdf export fascicoli",e);
			throw new CustomOperationException(e);
		}
	}
	
	private File generateFascicoliPDF(final List<? extends FascicoloPublicDto> listaFascicoli) throws Exception, JRException, IOException, CustomValidationException {
		
		JasperRicercaFascicoliDTO dto = new JasperRicercaFascicoliDTO(); 
		populateFromProperties(dto);
		
		SchemaRicercaDTO permessi = new SchemaRicercaDTO(true, true);
		if (!userUtil.hasUserLogged())
			permessi = new SchemaRicercaDTO(configurazionePermessiService.find("PUBLIC"));
		final SchemaRicercaDTO _permessi = permessi;
		
		if (listaFascicoli!=null) {
			listaFascicoli.forEach(fascicolo->{
				try {
					// TODO: non si capisce se vogliono il nome del gruppo creazione oppure la lista dei comuni effettivamente interessati (localizzazione)
					if (!fascicolo.getUfficio().equals(""))
						fascicolo.setUfficio(commonService.getDenominazioneOrganizzazione(fascicolo.getOrgCreazione()));
				} catch (Exception e) {
					fascicolo.setUfficio("ERRORE");
				}
				
				dto.getFascicoloDtoList().add(new JasperFascicoloDTO(fascicolo, _permessi));
			});
		}
		
		initExportDir();
		
		Path generatedPath= Files.createTempFile("", "Risultati_ricerca_Fascicoli".concat("_".concat(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())).concat(".PDF")));
		//File generated = new File(tempPath,	"Risultati_ricerca_Fascicoli".concat("_".concat(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())).concat(".PDF")));
		File generated = generatedPath.toFile();
		
		Resource res = new ClassPathResource("/jasper/".concat("ListaFascicoliRicerca").concat(".jasper"));
		
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(res.getInputStream());
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
															   null,
															   new JRBeanCollectionDataSource(Collections.singletonList(dto)));
		
		JasperExportManager.exportReportToPdfFile(jasperPrint, generated.getPath());
		
		return generated;
	}
	
	private void populateFromProperties(final JasperRicercaFascicoliDTO dto) {
		dto.setIndirizzoRegione(this.indirizzoRegione);
		dto.setPecRegione(this.pecRegione);
		dto.setTelRegione(this.telRegione);
		dto.setSezioneRegione(this.sezioneRegione);
		dto.setDipartimentoRegione(this.dipartimentoRegione);
		dto.setDirigenteRegione(this.dirigenteRegione);
		dto.setSezionaleProtocolloRegione(this.sezionaleProtocolloRegione);
	}
	
	private void initExportDir() {
//		File outDir = new File(tempPath);
//		if (!outDir.exists()) {
//			 outDir.mkdirs();
//		}
	}

	@Override
	public JasperPrint crea_LetteraEsitoVerifica(final Long idFascicolo, final String protocollo) {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		try {
			List<?> parameters = this.popolaDati_LetteraEsitoVerifica(idFascicolo, protocollo);
			JasperPrint jasper = jasperService.compilaReport("/jasper/".concat("LetteraTrasmissioneEsitoVerifica").concat(".jasper"), parameters, null);
			return jasper;
		} catch (Exception e) {
			log.error("Errore durante la creazione del pdf lettara esito verifica",e);
		}
		return null;
	}

	private List<JasperDTO> popolaDati_LetteraEsitoVerifica(final Long idFascicolo, final String protocollo) {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		JasperDTO dto = new JasperDTO();
		dto.setProtocollo(protocollo);
		return Collections.singletonList(dto);
	}

}