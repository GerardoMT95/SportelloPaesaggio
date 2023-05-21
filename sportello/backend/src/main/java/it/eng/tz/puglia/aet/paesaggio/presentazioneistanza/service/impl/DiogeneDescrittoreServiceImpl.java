package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.eng.tz.puglia.aet.diogene.bean.DiogeneApplicationFileBaseBean;
import it.eng.tz.puglia.aet.diogene.bean.DiogeneDescrittorePath;
import it.eng.tz.puglia.aet.diogene.bean.ElementoPath;
import it.eng.tz.puglia.aet.diogene.bean.TipoAggregatoElemento;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.AllegatiRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.service.RemoteSchemaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.DiogeneDescrittoreService;
import it.eng.tz.puglia.diogene.queue.IDiogeneQueueService;
import it.eng.tz.puglia.util.string.StringUtil;


@Service
public class DiogeneDescrittoreServiceImpl implements DiogeneDescrittoreService {
	
	@Value("${spring.application.name}")
	private String applicationName;
	
	private static final Logger log = LoggerFactory.getLogger(DiogeneDescrittoreServiceImpl.class);
	
	@Autowired
	RemoteSchemaService remoteSvc;
	@Autowired
	private ObjectMapper obm;
	@Autowired
	IDiogeneQueueService diogeneService;
	
	@Autowired
	AllegatiRepository allegatoDao;
	

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public void buildAndSendFile(final PraticaDTO dto, 
			final AllegatiDTO allegato, final String nomeSottocartella)
			throws JsonProcessingException, SQLException {
		final UUID id = allegato.getId();
		final String filename = allegato.getNomeFile();
		final DiogeneApplicationFileBaseBean beanFile = this.getBeanToSend(id.toString(), allegato.getIdCms(), filename,
				nomeSottocartella, dto,allegato);
		final String jsonRequestData = this.obm.writeValueAsString(beanFile);
		this.diogeneService.insertNewQueue(id.toString(), allegato.getIdCms(), dto.getCodicePraticaAppptr(), jsonRequestData, filename);
		log.info("Inserted per la pratica {} {} {}", dto.getCodicePraticaAppptr(),filename,nomeSottocartella);
		this.allegatoDao.updateAttachmentDiogeneData(allegato.getId(), id);
	}
	
	
	private DiogeneApplicationFileBaseBean getBeanToSend(final String externalKey, final String idCms,
			final String filename, final String lastSubfascicolo, 
			final PraticaDTO praticaDto, AllegatiDTO allegato) throws JsonProcessingException {
		final DiogeneApplicationFileBaseBean file = new DiogeneApplicationFileBaseBean();
		file.setCodiceApplicazione(this.applicationName.toUpperCase());
		file.setExternalKey(externalKey);
		file.setIdCms(idCms);
		final DiogeneDescrittorePath descrittorePath = this.buildDescrittore(filename, lastSubfascicolo, praticaDto,allegato);
		final String jsonRequestData = this.obm.writeValueAsString(descrittorePath);
		file.setJsonRequestData(jsonRequestData);
		return file;
	}
		
	/**
	 * albero costruito:
	 * Sportello Paesaggio
	 *  Ente competente
	 *    anno presentazione
	 *       codice appptr [fascicolo]
	 *          lastSubfascicolo  [sottofascicolo]
	 * @author acolaianni
	 *
	 * @param fileName
	 * @param lastSubfascicolo
	 * @param codIstatDto
	 * @param praticaDto
	 * @return
	 */
	private DiogeneDescrittorePath buildDescrittore(final String fileName, final String lastSubfascicolo,
			final PraticaDTO praticaDto,final AllegatiDTO allegato) {
		final DiogeneDescrittorePath descr = new DiogeneDescrittorePath();
		final List<ElementoPath> listElementPath = new ArrayList<>();
		descr.setNomeFileDiogene(fileName);
		descr.setPathElement(listElementPath);
		appendPathElem(listElementPath, this.applicationName.toUpperCase(), TipoAggregatoElemento.C,
				Map.of("descrizione", "Sportello Paesaggio")); // livello applicazione
		
		String descrizioneEnte = remoteSvc.getDenominazioneOrganizzazione(Integer.valueOf(praticaDto.getEnteDelegato()));
		final String nomeCartellaEnte = StringUtil.concateneString(descrizioneEnte);
		appendPathElem(listElementPath,nomeCartellaEnte, TipoAggregatoElemento.C,
				Map.of("descrizione", nomeCartellaEnte)); // cartella autorità procedente 
		//es. Regione Puglia
		// CARTELLA ANNO es 2021.
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		String dateStr = sdf.format(praticaDto.getDataPresentazione());
		if (StringUtil.isEmpty(dateStr)) {
			log.warn("Valore data_presentazione inatteso {} ", praticaDto.getDataPresentazione());
			dateStr = sdf.format(new Date());
		}
		final String cartellaAnno = dateStr.substring(6, 10);
		appendPathElem(listElementPath, cartellaAnno, TipoAggregatoElemento.C, Map.of("descrizione", cartellaAnno));
		// CARTELLA CODICE PRATICA
		appendPathElem(listElementPath, praticaDto.getCodicePraticaAppptr(), TipoAggregatoElemento.R,
				Map.of("codicePratica", praticaDto.getCodicePraticaAppptr()));
		appendPathElem(listElementPath, lastSubfascicolo, TipoAggregatoElemento.SR);// cartella della tipologia di file
		if(StringUtil.isNotEmpty(allegato.getProtocollo())){
			if(descr.getAttributi()==null) {
				descr.setAttributi(new HashMap<String,Object>());
				descr.getAttributi().put("numeroProtocollo", allegato.getProtocollo());
			}
		}
		return descr;
	}

	private void appendPathElem(final List<ElementoPath> listElementPath, final String nomeFolder,
			final TipoAggregatoElemento tipo, final Map<String, Object> attributi) {
		final ElementoPath appFolder = new ElementoPath();
		appFolder.setNomeAggregato(nomeFolder);
		appFolder.setTipoElemento(tipo);
		listElementPath.add(appFolder);
		if (attributi != null) {
			appFolder.setAttributi(attributi);
		}
	}

	private void appendPathElem(final List<ElementoPath> listElementPath, final String nomeFolder,
			final TipoAggregatoElemento tipo) {
		appendPathElem(listElementPath, nomeFolder, tipo, null);
	}

	
}
