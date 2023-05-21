package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.cds.service.IConferenzaDeiServiziService;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.allegati.AllegatiDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.allegati.DocumentazioneAmministrativaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.allegati.DocumentazioneTecnicaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AllowedMimeType;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.SezioneAllegati;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoIntegrazioneDocumentale;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperIntegrazioneDocumentaleDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.adapter.JasperIntegrazioneDocumentaleAdapter;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.IntegrazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoContenutoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.AllegatiRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.IntegrazioneRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TipoContenutoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.IntegrazioneSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.SendPlanToDiogeneScheduler;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.bean.FascicoloStatoBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.AllegatoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.FascicoloService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.IntegrazioneDocumentale;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.SezioneIstruttoria;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.MockMultipartFile;
import it.eng.tz.puglia.batch.queue.IQueueService;
import it.eng.tz.puglia.error.exception.CustomCmisException;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.log.LogUtil;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
public class IntegrazioneDocumentaleServiceImpl extends PraticaDataAwareService implements IntegrazioneDocumentale
{
	private final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
	
	@Autowired private IntegrazioneRepository dao;
	@Autowired private AllegatoService allegatoService;
	@Autowired private FascicoloService fascicoloService;
	@Autowired private PraticaRepository praticaDao;
	@Autowired private AllegatiRepository allegatiDao;
	@Autowired private TipoContenutoRepository tipiContenutoDao;
	@Autowired private IQueueService queueService;
	@Autowired private IConferenzaDeiServiziService cdsSvc;
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public IntegrazioneDTO save(IntegrazioneDTO entity,boolean spontanea) throws Exception
	{
		if(spontanea) {
			IntegrazioneSearch search = new IntegrazioneSearch();
			search.setAttiva(true);
			search.setPraticaId(entity.getPraticaId());

			long attive = dao.count(search);
			if(attive > 0)
				throw new Exception("ERRORE: sono già presenti " + attive + " integrazioni in sospeso per la pratica con id " + entity.getPraticaId() + ". Non è possibile crearne una nuova");
	
		}else {
			entity.setRichiestaIntegrazione(true);//imposto che è una richiesta ad hoc
		}
		//se su richiesta dell'ente, potremmo avere piu' richieste....
		entity.setStato(StatoIntegrazioneDocumentale.IN_BOZZA);
		entity.setDataCaricamento(null);
		entity.setUsernameUtenteCreazione(LogUtil.getUser());
		Integer id = (int) dao.insert(entity);
		entity.setId(id);
		return entity;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public Integer update(IntegrazioneDTO entity) throws Exception
	{
		return dao.update(entity);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public List<IntegrazioneDTO> search(IntegrazioneSearch filters) throws Exception
	{
		return dao.searchForList(filters);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public Integer delete(IntegrazioneSearch filters) throws Exception
	{
		if(filters.getId() == null && filters.getPraticaId() != null)
			throw new Exception("Non è possibile effettuare la cancellazione in quanto non sono stati passati ne id che id pratica per procedere");
		IntegrazioneDTO entity = new IntegrazioneDTO();
		if(filters.getId() != null)
			entity.setId(filters.getId());
		if(filters.getPraticaId() != null)
			entity.setPraticaId(filters.getPraticaId());
		entity=dao.find(entity);
		if(entity.getRichiestaIntegrazione()!=null && entity.getRichiestaIntegrazione()==true) {
			throw new CustomOperationToAdviceException("Impossibile rimuovere una richiesta di integrazione creata dall'ente procedente !!!");
		}
		return dao.delete(entity);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public IntegrazioneDTO findActiveIntegration(UUID idPratica) throws Exception
	{
		IntegrazioneSearch filters = new IntegrazioneSearch();
		filters.setPraticaId(idPratica);
		filters.setAttiva(true);
		List<IntegrazioneDTO> results = dao.searchForList(filters);
		if(results != null && results.size() > 1)
			throw new Exception("ERRORE: Trovate più integrazione attive per la pratica con id " + idPratica);
		return results != null && !results.isEmpty() ? results.get(0) : null;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public AllegatiDto getAllegatiIntegrazione(UUID idPratica, Integer idIntegrazione) throws Exception
	{
		return doGetAllegatiIntegrazione(idPratica, idIntegrazione);
	}

	private AllegatiDto doGetAllegatiIntegrazione(UUID idPratica, Integer idIntegrazione)
			throws CustomOperationToAdviceException {
		AllegatiDto allegati = new AllegatiDto();
		DocumentazioneAmministrativaDto docAmm = new DocumentazioneAmministrativaDto();
		DocumentazioneTecnicaDto docTecnica = new DocumentazioneTecnicaDto();
		allegati.setDocumentazioneAmministrativa(docAmm);
		allegati.setDocumentazioneTecnica(docTecnica);
		docAmm.setGrigliaPagamentoAllegati(allegatoService.getAllegati(idPratica, SezioneAllegati.DOC_AMMINISTRATIVA_D, idIntegrazione));
		docAmm.setGrigliaAllegatiCaricati(allegatoService.getAllegati(idPratica, SezioneAllegati.DOC_AMMINISTRATIVA_E, idIntegrazione));
		docTecnica.setGrigliaAllegatiCaricati(allegatoService.getAllegati(idPratica, SezioneAllegati.DOC_TECNICA, idIntegrazione));
		return allegati;
	}
	
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public Integer updateStatus(Integer idIntegrazione, StatoIntegrazioneDocumentale nuovoStato) throws Exception
	{
		Timestamp dataCaricamento=null;
		IntegrazioneDTO intDto=new IntegrazioneDTO();
		intDto.setId(idIntegrazione);
		intDto=dao.find(intDto);
		PraticaDTO praticaDto=new PraticaDTO();
		praticaDto.setId(intDto.getPraticaId());
		praticaDto=praticaDao.find(praticaDto);
		this.checkDiMiaCompetenza(praticaDto);
		if(nuovoStato==StatoIntegrazioneDocumentale.COMPLETATA) {
			//preparo la mail per notificarlo all'ente
			dataCaricamento = Timestamp.valueOf(LocalDateTime.now());
			intDto.setDataCaricamento(dataCaricamento);
			this.fascicoloService.inviaMailRichiedente(praticaDto, SezioneIstruttoria.RIC_INTEGR_IST, intDto, false, false);
			//invio in coda la sincronizzazione per diogene allineaDiogene(bean)
			FascicoloStatoBean fsb=new FascicoloStatoBean();
			fsb.setPratica(praticaDto);
			fsb.setSezioniAllegati(List.of(SezioneAllegati.INT_DOC,
					SezioneAllegati.DOC_AMMINISTRATIVA_D,
					SezioneAllegati.DOC_AMMINISTRATIVA_E,
					SezioneAllegati.DOC_TECNICA));
			this.queueService.insertNewQueue(SendPlanToDiogeneScheduler.ID, fsb);
			this.allineaCds(praticaDto,intDto);
		}
		Integer result = dao.updateStatus(idIntegrazione, nuovoStato,dataCaricamento);
		if(result != 1)
		{
			//eventuali passaggi di stato non amemssi si fermano qui...
			logger.error("ERRORE: Impossibile aggiornare lo stato dell'integrazione con id {}. Passaggio di stato non valido", idIntegrazione);
			throw new CustomOperationToAdviceException("ERRORE: Impossibile aggiornare lo stato dell'integrazione con id "+idIntegrazione+". Passaggio di stato non valido");
		}
		if(nuovoStato==StatoIntegrazioneDocumentale.IN_ATTESA) {
			//se passo in attesa, genero già il documento di riepilogo originale...
			this.eliminaRiepilogoPrecedente(praticaDto,SezioneAllegati.INT_DOC_PREVIEW,idIntegrazione,752 );
			this.generateJasperIntegrazioneDocumentale(idIntegrazione, praticaDto.getId(), praticaDto.getCodicePraticaAppptr());
		}
		if(nuovoStato==StatoIntegrazioneDocumentale.IN_BOZZA) {
			//elimino eventuali documenti di riepilogo caricati precedentemente
			this.eliminaRiepilogoPrecedente(praticaDto,SezioneAllegati.INT_DOC_PREVIEW,idIntegrazione,752);
			this.eliminaRiepilogoPrecedente(praticaDto,SezioneAllegati.INT_DOC,idIntegrazione,702);
		}
		return result;
	}
	
	private void allineaCds(PraticaDTO praticaDto, IntegrazioneDTO intDto) {
		//prelevo gli allegati della integrazione appena inviata e li invio alla cds
		logger.info("allineaCds pratica {} integrazioneId {}",praticaDto.getCodicePraticaAppptr(),intDto.getId());
		try {
			AllegatiDto allegatoDto = doGetAllegatiIntegrazione(praticaDto.getId(), intDto.getId());
			DocumentazioneAmministrativaDto docAmm = allegatoDto.getDocumentazioneAmministrativa();
			if(ListUtil.isNotEmpty(docAmm.getGrigliaPagamentoAllegati())) {
				for(AllegatiDTO all:docAmm.getGrigliaPagamentoAllegati()) {
					this.cdsSvc.appendiDocumentoACds(praticaDto.getId(), SezioneAllegati.DOC_AMMINISTRATIVA_D, all);	
				}
			}
			if(ListUtil.isNotEmpty(docAmm.getGrigliaAllegatiCaricati())) {
				for(AllegatiDTO all:docAmm.getGrigliaAllegatiCaricati()) {
					this.cdsSvc.appendiDocumentoACds(praticaDto.getId(), SezioneAllegati.DOC_AMMINISTRATIVA_E, all);	
				}
			}
			DocumentazioneTecnicaDto docTec = allegatoDto.getDocumentazioneTecnica();
			if(ListUtil.isNotEmpty(docTec.getGrigliaAllegatiCaricati())) {
				for(AllegatiDTO all:docTec.getGrigliaAllegatiCaricati()) {
					this.cdsSvc.appendiDocumentoACds(praticaDto.getId(), SezioneAllegati.DOC_TECNICA, all);	
				}
			}
		} catch (CustomOperationToAdviceException | JsonProcessingException | SQLException e) {
			logger.error("Errore in allineaCds pratica id {},integrazione id {} , error {}",praticaDto.getId(),intDto.getId(),e);
		}
		
	}

	private void eliminaRiepilogoPrecedente(PraticaDTO praticaDto,SezioneAllegati sezione,Integer idIntegrazione,Integer idTipoContenuto) throws CustomOperationToAdviceException, CustomCmisException {
		List<AllegatiDTO> allegati = allegatiDao.findByPraticaAndSezione(praticaDto.getId(), praticaDto.getTipoProcedimento(),sezione, idIntegrazione, idTipoContenuto);
		if(allegati!=null && allegati.size()>0) {
			for(AllegatiDTO allegato:allegati) {
				allegatoService.deleteAllegato(allegato.getId().toString());
			}
		}
	}
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public AllegatiDTO getMetadatiDocumentoIntegrazione(UUID idPratica, Integer idIntegrazione) throws Exception
	{
		AllegatiDTO allegato = null;
		List<AllegatiDTO> allegatoList = this.allegatoService.getAllegati(idPratica, SezioneAllegati.INT_DOC, idIntegrazione);
		if(allegatoList != null && !allegatoList.isEmpty())
			allegato = allegatoList.get(0);
		return allegato;
	}
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public File generateJasperIntegrazioneDocumentale(Integer idIntegrazione, UUID idPratica, String codicePratica) throws Exception
	{
		File pdf = null;
		JasperIntegrazioneDocumentaleDto jasperDto=this.doGenerateJasper(idIntegrazione, idPratica, codicePratica);
//		String jsonString=this.generateJsonJasperIntegrazioneDocumentaleDto(idIntegrazione, idPratica, codicePratica);
		pdf = generateReportIntegrazione(jasperDto, "Integrazione_documentale");
		//spedisco su cms ed inserisco negli allegati
		MockMultipartFile mFile=new MockMultipartFile(pdf.getName(),AllowedMimeType.PDF.getMimeType(), pdf);
		allegatoService.uploadAllegato(idPratica.toString(), Collections.singletonList(752), 
				"Integrazione_documentale" + codicePratica +"_originale" ,idIntegrazione, mFile,null);
		return pdf;
	}
	
	private File generateReportIntegrazione(JasperIntegrazioneDocumentaleDto jasperDto, String nomeFile) throws Exception
	{
		Path pathTemp=Files.createTempFile(nomeFile.concat("_").concat(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())),".PDF");
		File generated=pathTemp.toFile();
		Resource res = new ClassPathResource("/jasper/".concat("integrazioneDocumentale.jasper"));
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(res.getInputStream());
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,
				new JRBeanCollectionDataSource(Collections.singletonList(jasperDto)));
		JasperExportManager.exportReportToPdfFile(jasperPrint, generated.getPath());
		return generated;
	}
	
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public String generateJsonJasperIntegrazioneDocumentaleDto(Integer idIntegrazione, UUID idPratica, String codicePratica) throws Exception
	{
		JasperIntegrazioneDocumentaleDto jasperDto = doGenerateJasper(idIntegrazione, idPratica, codicePratica);
		final ObjectMapper om = new ObjectMapper();
		om.canSerialize(jasperDto.getClass());
		return om.writeValueAsString(jasperDto);
	}

	private JasperIntegrazioneDocumentaleDto doGenerateJasper(Integer idIntegrazione, UUID idPratica,
			String codicePratica) throws Exception {
		AllegatiDto allegati = getAllegatiIntegrazione(idPratica, idIntegrazione);
		List<TipoContenutoDTO> allTipi = tipiContenutoDao.select();
		JasperIntegrazioneDocumentaleDto jasperDto = JasperIntegrazioneDocumentaleAdapter.adapt(codicePratica, allegati,allTipi);
		return jasperDto;
	}

}
