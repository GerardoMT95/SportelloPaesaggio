package it.eng.tz.puglia.autpae.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.autpae.dto.AllegatoCustomDTO;
import it.eng.tz.puglia.autpae.dto.AllegatoInfoDTO;
import it.eng.tz.puglia.autpae.dto.DestinatarioTemplateDTO;
import it.eng.tz.puglia.autpae.dto.InformazioniDTO;
import it.eng.tz.puglia.autpae.dto.NuovaComunicazioneDTO;
import it.eng.tz.puglia.autpae.dto.TipologicaDestinatarioDTO;
import it.eng.tz.puglia.autpae.entity.AllegatoDTO;
import it.eng.tz.puglia.autpae.entity.AllegatoFascicoloDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.entity.RichiedenteDTO;
import it.eng.tz.puglia.autpae.enumeratori.EsitoVerifica;
import it.eng.tz.puglia.autpae.enumeratori.StatoFascicolo;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.enumeratori.TipoDestinatario;
import it.eng.tz.puglia.autpae.enumeratori.TipoTemplate;
import it.eng.tz.puglia.autpae.search.AllegatoFascicoloSearch;
import it.eng.tz.puglia.autpae.search.FascicoloSearch;
import it.eng.tz.puglia.autpae.search.TemplateDestinatarioSearch;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoFascicoloService;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoService;
import it.eng.tz.puglia.autpae.service.interfacce.CorrispondenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.DestinatarioService;
import it.eng.tz.puglia.autpae.service.interfacce.EsitoService;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloService;
import it.eng.tz.puglia.autpae.service.interfacce.LocalizzazioneInterventoService;
import it.eng.tz.puglia.autpae.service.interfacce.RichiedenteService;
import it.eng.tz.puglia.autpae.service.interfacce.TemplateDestinatarioService;
import it.eng.tz.puglia.autpae.utility.MockMultipartFile;
import it.eng.tz.puglia.autpae.utility.VarieUtils;
import it.eng.tz.puglia.service.http.bean.ProtocolloRequestBean;
import it.eng.tz.puglia.servizi_esterni.jasper.CreaReportService;
import it.eng.tz.puglia.servizi_esterni.protocollo.ProtocolNumberType;
import it.eng.tz.puglia.servizi_esterni.protocollo.model.SegnaturaProtocollo;
import it.eng.tz.puglia.util.string.StringUtil;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;

@Service
public class EsitoServiceImpl implements EsitoService {

	private static final Logger log = LoggerFactory.getLogger(EsitoServiceImpl.class);
//	private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot access attachments for plan exclusion of this municipality";

	@Autowired private AllegatoService allegatoService;
	@Autowired private DestinatarioService destinatarioService;
	@Autowired private FascicoloService fascicoloService;
	@Autowired private AllegatoFascicoloService allegatoFascicoloService;
	@Autowired private CorrispondenzaService corrispondenzaService;
	@Autowired private RichiedenteService richiedenteService;
	@Autowired private CreaReportService creaReportService;
	@Autowired private TemplateDestinatarioService templateDestinatarioService;
	@Autowired private LocalizzazioneInterventoService localizzazioneInterventoService;
//	@Autowired private CommonService commonService;
//	@Autowired private ApplicationProperties props;
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public List<AllegatoCustomDTO> datiEsito(long idFascicolo) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		List<AllegatoCustomDTO> lista = new ArrayList<>(2);

		AllegatoFascicoloSearch search1 = new AllegatoFascicoloSearch();
		search1.setIdFascicolo(idFascicolo);
		List<AllegatoFascicoloDTO> listaAF = null;

		search1.setType(TipoAllegato.PROVVEDIMENTO_FINALE_ESITO);
		listaAF = allegatoFascicoloService.search(search1).getList();
		if (listaAF!=null && !listaAF.isEmpty()) {
			Long idProvv = listaAF.get(0).getIdAllegato();
			AllegatoCustomDTO allegato1 = allegatoService.infoAllegato(idProvv);
			allegato1.setTipo(TipoAllegato.PROVVEDIMENTO_FINALE_ESITO);
			lista.add(allegato1);
		}
		search1.setType(TipoAllegato.PROVVEDIMENTO_FINALE_ESITO_LETTERA);
		listaAF = allegatoFascicoloService.search(search1).getList();
		if (listaAF!=null && !listaAF.isEmpty()) {
			Long idLettera = listaAF.get(0).getIdAllegato();
			AllegatoCustomDTO allegato2 = allegatoService.infoAllegato(idLettera);
			allegato2.setTipo(TipoAllegato.PROVVEDIMENTO_FINALE_ESITO_LETTERA);
			lista.add(allegato2);
		}
		return lista;
	}

	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public boolean inviaEsito(long idFascicolo) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		// controllo che lo Stato sia SELECTED
		FascicoloSearch searchF = new FascicoloSearch();
		searchF.setId(idFascicolo);
		searchF.setStato(StatoFascicolo.SELECTED);
		fascicoloService.prepareForSearch(searchF);
		if (fascicoloService.count(searchF)!=1) {
			log.error("Errore. Lo stato fascicolo non è 'in corso'!");
			throw new Exception("Errore. Lo stato fascicolo non è 'in corso'!");
		}

		// in teoria dovrei fare una select del fascicolo e assicurarmi che tutti i campi di ESITO siano già popolati e coerenti
		
		AllegatoFascicoloSearch search = new AllegatoFascicoloSearch();
		search.setIdFascicolo(idFascicolo);
		
		search.setType(TipoAllegato.PROVVEDIMENTO_FINALE_ESITO);
		if (allegatoFascicoloService.count(search)!=1)
			return false;
		
		search.setType(TipoAllegato.PROVVEDIMENTO_FINALE_ESITO_LETTERA);
		if (allegatoFascicoloService.count(search)!=1)
			return false;
		FascicoloDTO fascicolo = fascicoloService.find(idFascicolo);
		// a questo punto sul DB potrei avere un file PDF (caricato dall'utente) oppure il file RTF (autogenerato)
		File file = null;
		String nomeFile = allegatoService.datiAllegato(idFascicolo, Collections.singletonList(TipoAllegato.PROVVEDIMENTO_FINALE_ESITO_LETTERA)).get(0).getNome();
		
		boolean stampaProtocollo = false;
		
		if (nomeFile.toUpperCase().endsWith("RTF")) {
			this.caricaOresettaLettera(idFascicolo, generaLetteraTrasmissionePDF(idFascicolo, null));
			file = allegatoService.downloadFile(TipoAllegato.PROVVEDIMENTO_FINALE_ESITO_LETTERA, null, idFascicolo, null);
			stampaProtocollo = true;
		}
		else if (nomeFile.toUpperCase().endsWith("PDF")) {
			file = allegatoService.downloadFile(TipoAllegato.PROVVEDIMENTO_FINALE_ESITO_LETTERA, null, idFascicolo, null);
		}
		
		// protocollo il file PDF
		MockMultipartFile mFile = new MockMultipartFile("Lettera trasmissione Esito Provvedimento.pdf", file);
		AllegatoDTO informazioni = new AllegatoDTO();
		informazioni.setNome("Lettera trasmissione Esito Provvedimento.pdf");
		informazioni.setMimeType("application/pdf");
		informazioni.setTipo(TipoAllegato.PROVVEDIMENTO_FINALE_ESITO_LETTERA);
		
		AllegatoDTO informazioniOriginale = new AllegatoDTO();
		informazioniOriginale.setNome("Lettera trasmissione Esito Provvedimento.pdf");
		informazioniOriginale.setMimeType("application/pdf");
		//mi salvo il pdf originale non protocollato
		AllegatoCustomDTO ret = allegatoService.inserisciPdfPreTimbratura(Collections.singletonList(idFascicolo),mFile,informazioniOriginale);
		informazioniOriginale.setId(ret.getId());
		List<TipologicaDestinatarioDTO> listaDestinatariEsito = destinatariEsito(idFascicolo);
		//build bean per protocollazione.
		String[] denominazioneDestinatari=listaDestinatariEsito.stream().map(TipologicaDestinatarioDTO::getNome).collect(Collectors.toList()).toArray(new String[0]);
		String[] mailTo=listaDestinatariEsito.stream().map(TipologicaDestinatarioDTO::getEmail).collect(Collectors.toList()).toArray(new String[0]);
		ProtocolloRequestBean protoBean = new ProtocolloRequestBean();
		protoBean.setOggetto("Comunicazione esito verifica a campione della pratica avente oggetto '"
				+ fascicolo.getOggettoIntervento() + "'");
		protoBean.setNomeDocumentoPrimario("Primario");
		protoBean.setTitoloDocumentoPrimario("vedi oggetto");
		protoBean.setOggettoDocumentoPrimario(protoBean.getOggetto());
		protoBean.setTipoRiferimento("MIME");
		protoBean.setTipoRiferimentoPrimario("MIME");
		protoBean.setDenominazioneDestinatari(denominazioneDestinatari);
		protoBean.setMailTo(mailTo);
		protoBean.setInOut(ProtocolloRequestBean.InOut.U.name());
		protoBean.setOggetto(protoBean.getOggettoDocumentoPrimario());
		protoBean.setTitoloDocumento("Lettera trasmissione Esito Provvedimento");
		protoBean.setTipoDocumento(informazioniOriginale.getMimeType());
		SegnaturaProtocollo protocollo = allegatoService.protocollaEgetSegnatura(mFile, informazioniOriginale, ProtocolNumberType.O, protoBean);
		SimpleDateFormat sdfProto =new SimpleDateFormat("ddMMyyyy");
		informazioni.setDataProtocolloOut(sdfProto.parse(protocollo.getDataRegistrazione()));
		informazioni.setNumeroProtocolloOut(protocollo.toString());
		
		if (stampaProtocollo==true) {
			this.caricaOresettaLettera(idFascicolo, generaLetteraTrasmissionePDF(idFascicolo, protocollo.toString()));
			file = allegatoService.downloadFile(TipoAllegato.PROVVEDIMENTO_FINALE_ESITO_LETTERA, null, idFascicolo, null);
		}
		
		// TODO: qui dovrei stampare sul PDF (con una sovra-impressione) le informazioni di protocollo 
		//       e poi salvare su Alfresco anche il nuovo PDF protocollato (l'originale ce l'ho già)
		// occhio che quando invio l'email e metto un allegato il service lo rimette nel db e in pratica lo abbiamo a doppio
		// bisogna vedere BENE BENE il sistema di protocollazione, e quando protocollare le email
		
		NuovaComunicazioneDTO corrispondenza = new NuovaComunicazioneDTO();

		corrispondenza.setIdFascicoli(Collections.singletonList(idFascicolo));
		corrispondenza.setBozza(false);
		corrispondenza.setTipoTemplate(TipoTemplate.ESITO_VERIFICA);
		mFile = new MockMultipartFile("Lettera trasmissione Esito Provvedimento.pdf", file);
		corrispondenza.getAllegati().add(new AllegatoInfoDTO(informazioni, mFile)); // qua va inserito il file PDF già protocollato		
		corrispondenza.setDestinatari(listaDestinatariEsito);
		// inserisco le informazioni per risolvere i PlaceHolders
		corrispondenza.getInfoPlaceHolders().setIdFascicolo(idFascicolo);
		// invio la corrispondenza
		corrispondenzaService.inviaCorrispondenza(corrispondenza);
		// aggiorno il Fascicolo
		return (fascicoloService.cambiaStato(idFascicolo, StatoFascicolo.FINISHED, true)==1);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public List<TipologicaDestinatarioDTO> destinatariEsito(Long idFascicolo) throws Exception
	{
		List<TipologicaDestinatarioDTO> listaDestinatari = new ArrayList<>();

		// 1) cerco i destinatari di default
		TemplateDestinatarioSearch searchTD = new TemplateDestinatarioSearch();
		searchTD.setCodiceTemplate(TipoTemplate.ESITO_VERIFICA);
		List<DestinatarioTemplateDTO> destinatariDefault = templateDestinatarioService.search(searchTD).getList();
		
		if (destinatariDefault!=null) {
			destinatariDefault.forEach(destinatario-> {
				listaDestinatari.add(new DestinatarioTemplateDTO(destinatario)); 
			});
		}
		// 2) cerco la Pec dell'Ente (TO)
		List<Long> idComuni = localizzazioneInterventoService.selectEffettivo(idFascicolo);
		Set<Integer> comuni = new HashSet<>();
		idComuni.forEach(elem->{
			comuni.add(elem.intValue());
		});
		
		destinatarioService.findDestinatariForEnteDelegato(fascicoloService.find(idFascicolo).getOrgCreazione(), comuni, TipoDestinatario.TO);
		/* RubricaIstituzionaleSearch searchRI = new RubricaIstituzionaleSearch();
		searchRI.setApplicazioneId(commonService.getIdApplicazione(props.getCodiceApplicazioneProfile()));
		searchRI.setEnteId(commonService.findEnteByIdOrganizzazione(fascicoloService.find(idFascicolo).getOrgCreazione()).getId());
		List<RubricaIstituzionaleDTO> listaMailEnte = rubricaIstituzionaleService.search(searchRI).getList();
		if(listaMailEnte!=null && listaMailEnte.size()>0) {
			RubricaIstituzionaleDTO infoEnte = listaMailEnte.get(0);
			if (StringUtil.isNotEmpty(infoEnte.getPec()))
				listaDestinatari.add(new TipologicaDestinatarioDTO(infoEnte.getPec(), true, infoEnte.getNome(),
						TipoDestinatario.TO));
			else if (StringUtil.isNotEmpty(infoEnte.getMail()))
				listaDestinatari.add(new TipologicaDestinatarioDTO(infoEnte.getMail(), false, infoEnte.getNome(),
						TipoDestinatario.TO));
		}	*/
		// 3) cerco la Pec o l'email del richiedente (CC) 	// TODO: in realtà in analisi è "il proponente..."
		RichiedenteDTO richiedente = richiedenteService.datiRichiedente(idFascicolo);
			 if (StringUtil.isNotEmpty(richiedente.getPec()))
				listaDestinatari.add(new TipologicaDestinatarioDTO(richiedente.getPec  (),  true, richiedente.getNome()+" "+richiedente.getCognome(), TipoDestinatario.CC));
		else if (StringUtil.isNotEmpty(richiedente.getEmail()))
				listaDestinatari.add(new TipologicaDestinatarioDTO(richiedente.getEmail(), false, richiedente.getNome()+" "+richiedente.getCognome(), TipoDestinatario.CC));
		// 4) unisco le 3 liste eliminando i doppioni delle email 
		return VarieUtils.eliminaDuplicati(listaDestinatari);
	}

	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public List<AllegatoCustomDTO> proseguiEsito(InformazioniDTO body, MultipartFile file) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		if (body.getId()==null ||
			body.getEsitoVerifica()==null ||
		   (body.getEsitoVerifica()!=EsitoVerifica.POSITIVE && body.getEsitoVerifica()!=EsitoVerifica.NEGATIVE) ||
		    body.getEsitoNumeroProvvedimento()==null ||
		    body.getEsitoNumeroProvvedimento().trim().isEmpty() ||
		    body.getEsitoDataRilascioAutorizzazione()==null) 
		{
				log.error("Errore. Dati in input non corretti!");
				throw new Exception("Errore. Dati in input non corretti!");
		}
		
		// controllo che lo Stato sia SELECTED (in corso)
		FascicoloSearch searchF = new FascicoloSearch();
		searchF.setId(body.getId());
		searchF.setStato(StatoFascicolo.SELECTED);
		fascicoloService.prepareForSearch(searchF);
		if (fascicoloService.count(searchF)!=1) {
			log.error("Errore. Lo stato fascicolo non è 'in corso'!");
			throw new Exception("Errore. Lo stato fascicolo non è 'in corso'!");
		}
		
		// controllo che non sia già presente uno specifico allegato di questo tipo
		AllegatoFascicoloSearch search1 = new AllegatoFascicoloSearch();
		search1.setIdFascicolo(body.getId());
		search1.setType(TipoAllegato.PROVVEDIMENTO_FINALE_ESITO);
		if (allegatoFascicoloService.count(search1)!=0) {
			log.error("Esiste già un documento 'Provvedimento_Finale_Esito' associato al fascicolo!");
			throw new Exception("Esiste già un documento 'Provvedimento_Finale_Esito' associato al fascicolo!");
		}
		
		// inserisco l'allegato
		allegatoService.inserisciAllegato(Collections.singletonList(body.getId()), TipoAllegato.PROVVEDIMENTO_FINALE_ESITO, file, null);
		
		// controllo che non sia già presente uno specifico allegato di questo tipo
		AllegatoFascicoloSearch search2 = new AllegatoFascicoloSearch();
		search2.setIdFascicolo(body.getId());
		search2.setType(TipoAllegato.PROVVEDIMENTO_FINALE_ESITO_LETTERA);
		if (allegatoFascicoloService.count(search2)!=0) {
			log.error("Esiste già un documento 'Lettera trasmissione Esito Provvedimento' associato al fascicolo!");
			throw new Exception("Esiste già un documento 'Lettera trasmissione Esito Provvedimento' associato al fascicolo!");
		}
		
		// creo e inserisco l'allegato (lettera)
		MockMultipartFile lettera = generaLetteraTrasmissioneRTF(body.getId(), null);

		AllegatoDTO informazioni = new AllegatoDTO();
		informazioni.setNome("Lettera trasmissione Esito provvedimento.rtf");
		informazioni.setMimeType("application/rtf");
		
		allegatoService.inserisciAllegato(Collections.singletonList(body.getId()), TipoAllegato.PROVVEDIMENTO_FINALE_ESITO_LETTERA, lettera, informazioni);

		// aggiorno il fascicolo
		fascicoloService.aggiornaEsito(body.getId(),body.getEsitoVerifica(), body.getEsitoDataRilascioAutorizzazione(), body.getEsitoNumeroProvvedimento());
		
		return this.datiEsito(body.getId());
	}

	
	private MockMultipartFile generaLetteraTrasmissioneRTF(long idFascicolo, String protocollo) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		// TODO: creare la vera "Lettera di trasmissione Esito provvedimento" in formato .RTF
		try {
			//JasperPrint jasperP = creaReportService.crea_LetteraEsitoVerifica(idFascicolo, protocollo);
			JasperPrint jasperP = creaReportService.creaPdfLetteraDiTrasmissioneEsitoProvvedimento(idFascicolo, protocollo, new ArrayList<>());
			Path path=Files.createTempFile(idFascicolo+"_","[Anteprima]_Lettera_trasmissione_Esito_Provvedimento.rtf");
			//File file = new File(exportPath, "[Anteprima]_Lettera_trasmissione_Esito_Provvedimento.rtf");

			JRRtfExporter rtfExporter = new JRRtfExporter();
			rtfExporter.setExporterInput(new SimpleExporterInput(jasperP));
			rtfExporter.setExporterOutput(new SimpleWriterExporterOutput(path.toFile()));
			rtfExporter.exportReport();
			
			return new MockMultipartFile("[Anteprima] Lettera trasmissione Esito Provvedimento.rtf", path.toFile());
		} catch (Exception e) {
			throw e;
		}
	}

	private MockMultipartFile generaLetteraTrasmissionePDF(long idFascicolo, String protocollo) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		// TODO: creare la vera "Lettera di trasmissione Esito provvedimento" in formato .PDF
		try {
			//JasperPrint jasperP = creaReportService.crea_LetteraEsitoVerifica(idFascicolo, protocollo);
			JasperPrint jasperP = creaReportService.creaPdfLetteraDiTrasmissioneEsitoProvvedimento(idFascicolo, protocollo, new ArrayList<>());

			byte[] bytesArray = JasperExportManager.exportReportToPdf(jasperP);
			return new MockMultipartFile("[Anteprima] Lettera trasmissione Esito Provvedimento.pdf", bytesArray);
		} catch (Exception e) {
			throw e;
		}
	}
	

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public long caricaOresettaLettera(long idFascicolo, MultipartFile file) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		// TODO: devo controllare che il file sia effettivamente un PDF [se riusciremo potrà andare bene anche RTF] (probabilmente usare tika)
		
		// controllo che lo Stato sia SELECTED (in corso) e che l'esitoVerifica sia POSITIVO o NEGATIVO
		FascicoloSearch searchF1 = new FascicoloSearch();
		searchF1.setId(idFascicolo);
		searchF1.setStato(StatoFascicolo.SELECTED);
		searchF1.setEsitoVerifica(EsitoVerifica.POSITIVE);
		fascicoloService.prepareForSearch(searchF1);
		
		FascicoloSearch searchF2 = new FascicoloSearch();
		searchF2.setId(idFascicolo);
		searchF2.setStato(StatoFascicolo.SELECTED);
		searchF2.setEsitoVerifica(EsitoVerifica.NEGATIVE);
		fascicoloService.prepareForSearch(searchF2);
		
		if (fascicoloService.count(searchF1)!=1 && fascicoloService.count(searchF2)!=1) {
			log.error("Errore. Lo stato fascicolo non è 'in corso' oppure l'esito verifica non è ammesso!");
			throw new Exception("Errore. Lo stato fascicolo non è 'in corso' oppure l'esito verifica non è ammesso!");
		}
		
		AllegatoFascicoloSearch searchAF = new AllegatoFascicoloSearch();
		searchAF.setIdFascicolo(idFascicolo);
		searchAF.setType(TipoAllegato.PROVVEDIMENTO_FINALE_ESITO_LETTERA);
		List<AllegatoFascicoloDTO> listaAF = allegatoFascicoloService.search(searchAF).getList();
		if (listaAF==null || listaAF.size()!=1) {
			log.error("Trovati"+ listaAF!=null ? (" "+listaAF.size()) : (" "+0)+" documenti 'Lettera_Esito_Provvedimento_Finale' associati al fascicolo!");
			throw new Exception("Trovati"+ listaAF!=null ? (" "+listaAF.size()) : (" "+0)+" documenti 'Lettera_Esito_Provvedimento_Finale' associati al fascicolo!");
		}
		
		// elimino l'allegato (lettera) attuale
		allegatoService.eliminaAllegato(listaAF.get(0).getIdAllegato());
		
		// creo e inserisco l'allegato (lettera)
		AllegatoDTO informazioni = new AllegatoDTO();
		
		if (file==null) {
			file = generaLetteraTrasmissioneRTF(idFascicolo, null);	// genero la lettera
			informazioni.setNome("Lettera trasmissione Esito provvedimento.rtf");
			informazioni.setMimeType("application/rtf");
		}
		else {
		// TODO: qui dovrei distinguere se il file "Lettera di trasmissione Esito Provvedimento" che mi arriva dal FE è di tipo PDF oppure RTF:
		//		-se è un RTF devo trasformarlo in qualche strano modo in un file PDF
		//	    -se è un PDF va bene così, non faccio nessun'altra operazione pre-salvataggio
		//		 Attualmente obbligo l'utente a caricare per forza un file PDF
			informazioni.setNome("Lettera trasmissione Esito provvedimento.pdf");
			informazioni.setMimeType("application/pdf");
		}
		
		return allegatoService.inserisciAllegato(Collections.singletonList(idFascicolo), TipoAllegato.PROVVEDIMENTO_FINALE_ESITO_LETTERA, file, informazioni).getId();
	}
	
}