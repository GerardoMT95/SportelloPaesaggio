package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.jasper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.RichiedenteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.TipologicaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.SelectOptionDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza.UlterioriInformazioniDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.CaratterizzazioneInterventoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.ConfigOptionValue;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.ParchiPaesaggiImmobiliSigla;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.RuoloReferente;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.SezioneAllegati;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoQualificazione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoReferente;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperInterventoInfoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.adapter.JasperDichiarazioneTecnicaAdapter;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.enums.CaratterizzazioneIntervento;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.enums.TipologiaIntervento;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DescrizioneSchedaTecnicaValuesDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.LocalizzazioneInterventoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ParchiPaesaggiImmobiliDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ParticelleCatastaliDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ProvvedimentoFinaleDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ReferentiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoInterventoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.AllegatiRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.DescrizioneSchedaTecnicaValuesRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.LocalizzazioneInterventoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ParchiPaesaggiImmobiliRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ParticelleCatastaliRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ProvvedimentoFinaleRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ReferentiRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TipoContenutoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TipoInterventoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TipoProcedimentoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.service.RemoteSchemaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.AllegatoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.jasper.dto.JasperAllegatoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.jasper.dto.JasperDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.jasper.dto.JasperLocalizzazioneInterventoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.dto.TemplateDocDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.dto.TemplateDocSezioniDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.dto.TipoSezioneDoc;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.service.impl.TemplateDocService;
import it.eng.tz.puglia.service.http.bean.CmsDownloadResponseBean;
import it.eng.tz.puglia.util.string.StringUtil;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class CreaReportServiceImpl implements CreaReportService {
	
	private static final Logger log = LoggerFactory.getLogger(CreaReportServiceImpl.class.getName());
	
	@Autowired private AllegatoService allegatoSvc;
	@Autowired private TemplateDocService templaceDocSvc;
	@Autowired private ReferentiRepository referentiDao;
	@Autowired private PraticaRepository praticaDao;
	@Autowired private RemoteSchemaService remoteSvc;
	@Autowired private ProvvedimentoFinaleRepository provvedimentoDao;
	@Autowired private AllegatiRepository allegatiDao;
	@Autowired private TipoContenutoRepository tcDao;
	@Autowired private LocalizzazioneInterventoRepository localIntDao;
	@Autowired private ParticelleCatastaliRepository particelleDao;
	@Autowired private ParchiPaesaggiImmobiliRepository parchiPaesaggiImmobiliDao;
	@Autowired private DescrizioneSchedaTecnicaValuesRepository dstRepo;
	@Autowired private TipoInterventoRepository tiRepo;
	@Autowired private TipoProcedimentoRepository tpRepo;
	
	/**
	 * alcuni tag provenienti dallh'HTML editor in fe (QUILL) non sono supportati da jasper
	 * @author acolaianni
	 *
	 * @param htmlString
	 * @return
	 */
	
	private String parsePlaceholder(String toParse,String placeholders, JasperDTO jDTO,boolean anteprima ) {
		if(anteprima)return toParse; 
		if(StringUtil.isEmpty(placeholders)) {
			return toParse;
			}
		String[] list = placeholders.split(",");
		if(list==null)
				return toParse;
		for (String placeholder : Arrays.asList(list)) {
			switch(placeholder) {
				case "ID_FASCICOLO"		  : toParse = toParse.replace("{ID_FASCICOLO}"		, jDTO.getIdFascicolo().toString());
									 		break;
				case "OGGETTO"			  : toParse = toParse.replace("{OGGETTO}"			, jDTO.getInterventoInfo().get(0).getOggettoIntervento()==null?"":jDTO.getInterventoInfo().get(0).getOggettoIntervento());
											break;
				case "TIPO_PROCEDIMENTO"  : toParse = toParse.replace("{TIPO_PROCEDIMENTO}" , tpRepo.getDescrizione(jDTO.getInterventoInfo().get(0).getTipoProcedimento()));
				 						  	break;
				case "CODICE_FASCICOLO"	  : toParse = toParse.replace("{CODICE_FASCICOLO}"  , jDTO.getCodicePratica()==null?"":jDTO.getCodicePratica());
										  	break;
			}
		}
		return toParse;
	}
	
	
	
	/* Creazione del template dinamico */
	private void creaTemplateDinamico(int idOrganizzazione,String nomeTemplate, JasperDTO jasperDTO,boolean anteprima) throws Exception {
		Map<String,Object> parameters=new HashMap<>();
		TemplateDocDTO template = this.templaceDocSvc.info(idOrganizzazione,nomeTemplate,true);
		List<TemplateDocSezioniDTO> listaSezioni = template.getSezioni();
		
		try {
			listaSezioni.forEach(sezione-> {
				if(sezione.getTipoSezione().equals(TipoSezioneDoc.IMAGE.name())) {
					 if(sezione.getIdAllegato()!=null) {
						//prelevare dal cms il contenuto del file puntato da idAllegati
							try {
								CmsDownloadResponseBean cmsBean = allegatoSvc.downloadAllegatoGenerico(sezione.getIdAllegato());
								Path path=Paths.get(cmsBean.getFileName());
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
				else if(sezione.getTipoSezione().equals(TipoSezioneDoc.HTML.name())) {
					String parseParameters = CreaReportService.convertiATagJasper(sezione.getValore());
					String parsePlaceholder = this.parsePlaceholder(parseParameters, sezione.getPlaceholders(),jasperDTO,anteprima);
					parameters.put(sezione.getNome(), parsePlaceholder);
				}
				else {
					String parsePlaceholder = this.parsePlaceholder(sezione.getValore(), sezione.getPlaceholders(),jasperDTO,anteprima);
					parameters.put(sezione.getNome(),parsePlaceholder);
				}
			});
		} catch (Exception e1) {
			log.error("Errore durante la gestione dei placeholder :" ,e1);
			throw e1;
		}
		jasperDTO.setSezioniDinamiche(parameters);
	}

	@Override
	public JasperPrint creaPdf_DemoTemplate(int idOrganizzazione,String nomeTemplate) {
		log.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		try {
			//  prelevare parametri da tabella template_doc_sezione tramite repositoriy TemplateDocFullRepository
			JasperDTO jDTO = new JasperDTO();
		    this.creaTemplateDinamico(idOrganizzazione,nomeTemplate, jDTO,true);
			JasperPrint jasper = this.compilaReport("/jasper/Trasmissione/".concat(nomeTemplate).concat(".jasper"), Collections.singletonList(jDTO), null);
			return jasper;
		} catch (Exception e) {
			log.error("Errore in creaPdf_DemoTemplate",e);
		}
		return null;
	}
	
	
//	@Override
//	public File creaPdf_DocumentoTrasmissione(UUID idPratica, String protocollo, List<TipologicaDTO> listaDestinatari) {
//		log.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
//		try {
//			JasperDTO jDTO = compilaReportDocumentoDiTrasmissione(idPratica, protocollo, listaDestinatari);
//			JasperPrint jasper = this.compilaReport("/jasper/Trasmissione/DocumentoDiTrasmissione.jasper", Collections.singletonList(jDTO), null);
//			Path pathTemp=Files.createTempFile("DocumentoDiTrasmissione_".concat(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())),".PDF");
//			File generated=pathTemp.toFile();
//			JasperExportManager.exportReportToPdfFile(jasper, generated.getPath());
//			return generated;
//		} catch (Exception e) {
//			log.error("Errore in creaPdf_DocumentoTrasmissione",e);
//		}
//		return null;
//	}
	
	@Override
	public File creaPdf_DocumentoTrasmissione(UUID idPratica, String protocollo, List<TipologicaDTO> listaDestinatari) throws Exception {
		try {
			JasperDTO jDTO = compilaReportDocumentoDiTrasmissione(idPratica, protocollo, listaDestinatari);
			Path pathTemp=Files.createTempFile("DocumentoDiTrasmissione_".concat(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())),".PDF");
			File generated=pathTemp.toFile();
			PraticaDTO pratica = this.praticaDao.find(idPratica);
			this.creaTemplateDinamico(Integer.parseInt(pratica.getEnteDelegato()),"DocumentoDiTrasmissione", jDTO,false);
			JasperPrint jasper = this.compilaReport("/jasper/Trasmissione/".concat("DocumentoDiTrasmissione").concat(".jasper"), Collections.singletonList(jDTO), null);
			JasperExportManager.exportReportToPdfFile(jasper, generated.getPath());
			return generated;
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	private JasperPrint compilaReport(String path, List<?> lista, Map<String, Object> parameters) throws Exception {
		log.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		try
		{
			Resource jasperRes = new ClassPathResource(path);
			if( !jasperRes.exists() ) {
				throw new IllegalArgumentException("NESSUN REPORT TROVATO COL PATH "+path+"."); 
			}
			final InputStream reportInputStream = jasperRes.getInputStream();
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(lista);
		
			// Fill the report
			JasperPrint jasperPrint = JasperFillManager.fillReport(reportInputStream, null /*parameters*/, jrBeanCollectionDataSource);
			return jasperPrint;
		}
		catch (Exception e)
		{
			log.error(String.format("An error occured during PDF creation: %s", e));
			throw e;
		}
	}
	
	
	private JasperDTO compilaReportDocumentoDiTrasmissione(UUID idFascicolo, String protocollo, List<TipologicaDTO> listaDestinatari) throws Exception {
		try {
			JasperDTO dto = new JasperDTO();
			dto.setProtocollo(protocollo);
			dto.setListaDestinatari(listaDestinatari);

			// RICHIEDENTE
			ReferentiDTO richiedente = this.referentiDao.selectRichiedente(idFascicolo, TipoReferente.SD.getValue());
			RichiedenteDTO jasperRichiedente = new RichiedenteDTO();
			jasperRichiedente.setNome(richiedente.getNome());
			jasperRichiedente.setCognome(richiedente.getCognome());
			jasperRichiedente.setCodiceFiscale(richiedente.getCodiceFiscale());
			jasperRichiedente.setSesso(richiedente.getSesso());
			jasperRichiedente.setDataNascita(richiedente.getDataNascita());
			jasperRichiedente.setStatoNascita(richiedente.getNazionalitaNascitaName());
			jasperRichiedente.setProvinciaNascita(!StringUtil.isEmpty(richiedente.getProvinciaNascitaName()) ? richiedente.getProvinciaNascitaName() : richiedente.getProvinciaNascitaEstero());
			jasperRichiedente.setComuneNascita(!StringUtil.isEmpty(richiedente.getComuneNascitaName()) ? richiedente.getComuneNascitaName() : richiedente.getComuneNascitaEstero());
			jasperRichiedente.setStatoResidenza(richiedente.getNazionalitaResidenzaName());
			jasperRichiedente.setProvinciaResidenza(!StringUtil.isEmpty(richiedente.getProvinciaResidenzaName()) ? richiedente.getProvinciaResidenzaName() : richiedente.getProvinciaResidenzaEstero());
			jasperRichiedente.setComuneResidenza(!StringUtil.isEmpty(richiedente.getComuneResidenzaName()) ? richiedente.getComuneResidenzaName() : richiedente.getComuneResidenzaEstero());
			jasperRichiedente.setViaResidenza(richiedente.getIndirizzoResidenza());
			jasperRichiedente.setNumeroResidenza(richiedente.getCivicoResidenza());
			jasperRichiedente.setCap(richiedente.getCapResidenza());
			jasperRichiedente.setPec(richiedente.getPec());
			jasperRichiedente.setEmail(richiedente.getMail());
			jasperRichiedente.setTelefono(richiedente.getTelefono());
			jasperRichiedente.setDitta(richiedente.getDitta()!=null ? richiedente.getDitta() : false);
			jasperRichiedente.setDittaSocieta(richiedente.getDittaEnte());
			if (richiedente.getDittaQualitaDi()!=null) {
				if ((Integer)RuoloReferente.Altro.getValue()==(Integer)richiedente.getDittaQualitaDi()) {
					jasperRichiedente.setDittaInQualitaDi(richiedente.getDittaQualitaAltro());
				}
				else {
					jasperRichiedente.setDittaInQualitaDi(RuoloReferente.getNomeFromValue(richiedente.getDittaQualitaDi()));
				}
			}
			jasperRichiedente.setDittaCodiceFiscale(richiedente.getDittaCf());
			jasperRichiedente.setDittaPartitaIva(richiedente.getDittaPartitaIva());
			dto.setRichiedenteInfo(Collections.singletonList(jasperRichiedente));

			// FASCICOLO
			PraticaDTO pratica = praticaDao.find(idFascicolo);
			dto.setSanatoria(pratica.getInSanatoria());
			//dto.setCodicePratica(pratica.getCodicePraticaAppptr());
			dto.setCodicePratica(pratica.getCodiceTrasmissione());
			dto.setUfficio(remoteSvc.getDenominazioneOrganizzazione(Integer.valueOf(pratica.getEnteDelegato())));

			// PROVVEDIMENTO
			ProvvedimentoFinaleDTO provvedimento = provvedimentoDao.find(idFascicolo);
			dto.setProvvedimentoDataAutorizzazione(provvedimento.getDataRilascioAutorizzazione());
			dto.setProvvedimentoNumero(provvedimento.getNumeroProvvedimento());
			dto.setProvvedimentoRup(provvedimento.getRup());
			dto.setProvvedimentoEsito(provvedimento.getEsitoProvvedimento().getTextValue());

			// ALLEGATI PROVVEDIMENTO
			List<AllegatiDTO> allegatiProvvedimento = allegatiDao.searchBySezioni(idFascicolo, Collections.singletonList(SezioneAllegati.PROVVEDIMENTO.name()));
			if(allegatiProvvedimento!=null && !allegatiProvvedimento.isEmpty())
			{
				for(AllegatiDTO allegato: allegatiProvvedimento)
				{
					List<String> types = tcDao.findDescrEstesaTipiAllegato(allegato.getId());
//					if(types != null) {
//						allegato.setTipiContenuto(types.stream().map(m -> m.toString()).collect(Collectors.toList()));
//					}
					allegato.setTipiContenuto(types);
					dto.getListaAllegatiProvvedimento().add(new JasperAllegatoDTO(allegato.getDataCaricamento(), allegato.getNomeFile(), allegato.getTipiContenuto()));
				}
			}

			// ALLEGATI LOCALIZZAZIONE
			List<AllegatiDTO> allegatiLocalizzazione = allegatiDao.searchBySezioni(idFascicolo, Collections.singletonList(SezioneAllegati.LOCALIZZAZIONE.name()));
			if(allegatiLocalizzazione!=null && !allegatiLocalizzazione.isEmpty())
			{
				for(AllegatiDTO allegato: allegatiLocalizzazione)
				{
					List<String> types = tcDao.findDescrEstesaTipiAllegato(allegato.getId());
//					if(types != null) {
//						allegato.setTipiContenuto(types.stream().map(m -> m.toString()).collect(Collectors.toList()));
//					}
					allegato.setTipiContenuto(types);
					dto.getListaAllegatiLocalizzazione().add(new JasperAllegatoDTO(allegato.getDataCaricamento(), allegato.getNomeFile(), allegato.getTipiContenuto()));
				}
			}

			// LOCALIZZAZIONE
			List<LocalizzazioneInterventoDTO> listaComuni = localIntDao.select(pratica.getId());
			if (listaComuni != null && listaComuni.size() > 0)
			{
				for (LocalizzazioneInterventoDTO comune : listaComuni) {
					// find particelle
					List<ParticelleCatastaliDTO> particelle = particelleDao.select(pratica.getId(), comune.getComuneId());
					if (particelle.size()==0)
						particelle = null;
					if (particelle!= null)
						comune.setParticelle(particelle);
					// find vincolistica
					UlterioriInformazioniDto ultInfo = new UlterioriInformazioniDto();
					List<ParchiPaesaggiImmobiliDTO> parchi   = parchiPaesaggiImmobiliDao.select(pratica.getId(), comune.getComuneId(), ParchiPaesaggiImmobiliSigla.P.toString());
					ultInfo.setBpParchiEReserve(new ArrayList<String>());
					ultInfo.setBpParchiEReserveOptions(new ArrayList<SelectOptionDto>());
					toUlterioriInformazioniDto(parchi, ultInfo.getBpParchiEReserve(), ultInfo.getBpParchiEReserveOptions());
					List<ParchiPaesaggiImmobiliDTO> immobili = parchiPaesaggiImmobiliDao.select(pratica.getId(), comune.getComuneId(), ParchiPaesaggiImmobiliSigla.I.toString());
					ultInfo.setBpImmobileAreePubblico(new ArrayList<String>());
					ultInfo.setBpImmobileAreePubblicoOptions(new ArrayList<SelectOptionDto>());
					toUlterioriInformazioniDto(immobili, ultInfo.getBpImmobileAreePubblico(), ultInfo.getBpImmobileAreePubblicoOptions());
					List<ParchiPaesaggiImmobiliDTO> paesaggi = parchiPaesaggiImmobiliDao.select(pratica.getId(), comune.getComuneId(), ParchiPaesaggiImmobiliSigla.R.toString());
					ultInfo.setUcpPaesaggioRurale(new ArrayList<String>());
					ultInfo.setUcpPaesaggioRuraleOptions(new ArrayList<SelectOptionDto>());
					toUlterioriInformazioniDto(paesaggi, ultInfo.getUcpPaesaggioRurale(), ultInfo.getUcpPaesaggioRuraleOptions());
					comune.setUlterioriInformazioni(ultInfo);
					dto.getListaLocalizzazioni().add(new JasperLocalizzazioneInterventoDTO(comune));
				}
			}

			// INTERVENTO
			JasperInterventoInfoDto intervento = new JasperInterventoInfoDto();
			intervento.setTipoProcedimento(pratica.getTipoProcedimento());
			intervento.setOggettoIntervento(pratica.getOggetto());

			List<DescrizioneSchedaTecnicaValuesDTO> dstValues = dstRepo.find(idFascicolo);
			intervento.setDescrizioneIntervento((dstValues.parallelStream().filter(p -> p.getSezione().equals(TipoQualificazione.DESCR)).findFirst().orElse(new DescrizioneSchedaTecnicaValuesDTO()).getText()));
			intervento.setDurata(dstValues.parallelStream().filter(p -> p.getSezione().equals(TipoQualificazione.CAR_INT_TP)).findFirst().get().getCodice());
			List<ConfigOptionValue> listaConfigCI = new CaratterizzazioneInterventoDto(dstValues).getCaratterizzazione();
			listaConfigCI.forEach(elem->{
				elem.setValue(CaratterizzazioneIntervento.valueOf(elem.getValue()).getLabel());
			});
			intervento.setCaratterizzazioneIntervento(listaConfigCI);
			intervento.setQualificazioneIntervento(JasperDichiarazioneTecnicaAdapter.adaptQualificazioneIntervento(fromEntityToDtoPart(dstValues, TipoQualificazione.QUAL), pratica.getTipoProcedimento()));

			TipoInterventoDTO tiDto = tiRepo.find(idFascicolo);
			intervento.setTipologiaDiIntervento(TipologiaIntervento.valueOf(tiDto.getCodice()).getLabel());
			intervento.setTipologiaDiInterventoDataApprovazione(tiDto.getDataApprovazione());
			intervento.setTipologiaDiInterventoCon(tiDto.getCon());
			intervento.setTipologiaDiInterventoArticoli(tiDto.getArtt());
			dto.setInterventoInfo(Collections.singletonList(intervento));

			// ALLEGATI AMMINISTRATIVI
			List<AllegatiDTO> allegatiDocAmministrativa = allegatiDao.searchBySezioni(idFascicolo, Collections.singletonList(SezioneAllegati.DOC_AMMINISTRATIVA_D.name()));
			allegatiDocAmministrativa.addAll			 (allegatiDao.searchBySezioni(idFascicolo, Collections.singletonList(SezioneAllegati.DOC_AMMINISTRATIVA_E.name())));
			if(allegatiDocAmministrativa!=null && !allegatiDocAmministrativa.isEmpty())
			{
				for(AllegatiDTO allegato: allegatiDocAmministrativa)
				{
					List<String> types = tcDao.findDescrTipiAllegato(allegato.getId());
//					if(types != null) {
//						allegato.setTipiContenuto(types.stream().map(m -> m.toString()).collect(Collectors.toList()));
//					}
					allegato.setTipiContenuto(types);
					dto.getListaAllegatiAmministrativi().add(new JasperAllegatoDTO(allegato.getDataCaricamento(), allegato.getNomeFile(), allegato.getTipiContenuto()));
				}
			}

			// ALLEGATI TECNICI
			List<AllegatiDTO> allegatiDocTecnica = allegatiDao.searchBySezioni(idFascicolo, Collections.singletonList(SezioneAllegati.DOC_TECNICA.name()));
			if(allegatiDocTecnica!=null && !allegatiDocTecnica.isEmpty())
			{
				for(AllegatiDTO allegato: allegatiDocTecnica)
				{
					List<String> types = tcDao.findDescrTipiAllegato(allegato.getId());
//					if(types != null) {
//						allegato.setTipiContenuto(types.stream().map(m -> m.toString()).collect(Collectors.toList()));
//					}
					allegato.setTipiContenuto(types);
					dto.getListaAllegatiTecnici().add(new JasperAllegatoDTO(allegato.getDataCaricamento(), allegato.getNomeFile(), allegato.getTipiContenuto()));
				}
			}
			return dto;
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	
	
	
	/**
	 * precarico le selezioni possibili e le selezioni effettuate
	 * @param entityOptions
	 * @param listaSelezioni
	 * @param listaOpzioni
	 */
	private void toUlterioriInformazioniDto(List<ParchiPaesaggiImmobiliDTO> entityOptions, List<String> listaSelezioni, List<SelectOptionDto> listaOpzioni)	{
		if (entityOptions!=null && entityOptions.size()>0) {
			for (ParchiPaesaggiImmobiliDTO vincolo : entityOptions) {
				SelectOptionDto opzione = new SelectOptionDto();
				opzione.setValue(vincolo.getCodice());
				opzione.setDescription(vincolo.getDescrizione());
				opzione.setLinked(vincolo.getInfo());
				listaOpzioni.add(opzione);
				if (vincolo.getSelezionato() != null && vincolo.getSelezionato().equalsIgnoreCase("S"))
					listaSelezioni.add(vincolo.getCodice());
			}
		}
	}
	
	/**
	 * Metodo che trasforma il set di elementi ottenuti dal db in una lista di ConfigOptionValue 
	 * @param entity {link List} di {@link DescrizioneSchedaTecnicaValuesDTO}
	 * @param tipoQualificazione {@link TipoQualificazione}
	 * @return
	 */
	private List<ConfigOptionValue> fromEntityToDtoPart(List<DescrizioneSchedaTecnicaValuesDTO> entity, TipoQualificazione tipoQualificazione) {
		List<ConfigOptionValue> configValue = new ArrayList<ConfigOptionValue>();
		if (entity!=null && !entity.isEmpty()) {
			for (DescrizioneSchedaTecnicaValuesDTO e : entity) {
				if (e.getSezione().equals(tipoQualificazione))
					configValue.add(new ConfigOptionValue(e.getCodice(), e.getText()));
			}
		}
		return configValue;
	}

}