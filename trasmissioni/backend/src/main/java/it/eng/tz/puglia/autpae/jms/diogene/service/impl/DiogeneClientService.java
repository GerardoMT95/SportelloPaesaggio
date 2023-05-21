/**
 * 
 */
package it.eng.tz.puglia.autpae.jms.diogene.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.eng.tz.puglia.aet.diogene.bean.DiogeneApplicationFileBaseBean;
import it.eng.tz.puglia.aet.diogene.bean.DiogeneDescrittorePath;
import it.eng.tz.puglia.aet.diogene.bean.DiogeneNotifyWritedBean;
import it.eng.tz.puglia.aet.diogene.bean.ElementoPath;
import it.eng.tz.puglia.aet.diogene.bean.TipoAggregatoElemento;
import it.eng.tz.puglia.util.json.JsonUtil;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.config.AutpaeConstants;
import it.eng.tz.puglia.autpae.dto.AllegatoDiogeneInfoDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.generated.orm.dto.PaesaggioOrganizzazioneDTO;
import it.eng.tz.puglia.autpae.generated.orm.repository.PaesaggioOrganizzazioneRepository;
import it.eng.tz.puglia.autpae.jms.diogene.service.IDiogeneClientService;
import it.eng.tz.puglia.autpae.repository.AllegatoFullRepository;
import it.eng.tz.puglia.jms.producer.IJmsProducer;

/**
 * @author Adriano Colaianni
 * @date 19 ott 2021
 */
@Service
public class DiogeneClientService implements IDiogeneClientService{

	private static final Logger LOGGER = LoggerFactory.getLogger(DiogeneClientService.class);
	
//	private static final String SUB_FASCICOLO_ALLEGATI = "Allegati";
//	private static final String SUB_FASCICOLO_PROVVEDIMENTI = "Provvedimenti";
//	private static final String SUB_FASCICOLO_PARERI_MIBAC = "Pareri_MIBAC";
//	private static final String SUB_FASCICOLO_RICEVUTE_TRASMISSIONE = "Ricevute_Trasmissione";

	@Autowired
	PaesaggioOrganizzazioneRepository paeOrgDao;
	
	@Autowired
	ApplicationProperties props;
	
	@Autowired
	AllegatoFullRepository allDao;
	
	@Value("${pm.codice.applicazione}")
	private String nomeApplicazione;
	
	/**
	 * qui mi arriveranno le notifiche di ritorno
	 */
	@Value("${jms.consumer.queue.name}")
	private String nomeCodaNotifiche;
	
	@Autowired
	IJmsProducer producer;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public void componiEdInviaMessaggio(AllegatoDiogeneInfoDTO docInfoDiogene) throws Exception {
		DiogeneApplicationFileBaseBean file=new DiogeneApplicationFileBaseBean();
		file.setQueueNotifyWrited(nomeCodaNotifiche);
		file.setCodiceApplicazione(nomeApplicazione.toLowerCase());
		file.setExternalKey(docInfoDiogene.getId()+"");
		file.setIdCms(docInfoDiogene.getContenuto());
		ObjectMapper obm=new ObjectMapper();
		DiogeneDescrittorePath descr=buildDescrittore(docInfoDiogene.getNome(),this.getSubfolderDiogene(docInfoDiogene.getTypes()),docInfoDiogene.getCodiceFascicolo(),docInfoDiogene.getOrgCreazione());
		String jsonRequestData = obm.writeValueAsString(descr);
		file.setJsonRequestData(jsonRequestData);
		producer.sendObjectMessage(file);
		allDao.updateSendDiogene(file.getExternalKey());
	}
	
	/**
	 * calcolo della sottocartella fascicolo (default Allegati)
	 * @autor Adriano Colaianni
	 * @date 18 ott 2021
	 * @param types
	 * @return
	 */
	private String getSubfolderDiogene(String types) {
		String tipi[]=types.split(",");
//		String subfolderAllegati=SUB_FASCICOLO_ALLEGATI;
		List<TipoAllegato> tipiAllegati=new ArrayList<>();
		for(String tipo:tipi) {
			TipoAllegato tipoAll =TipoAllegato.valueOf(tipo);
			if(tipoAll!=null) {
				tipiAllegati.add(tipoAll);
			}
		}
//		if(tipiAllegati.contains(TipoAllegato.PROVVEDIMENTO_FINALE)||
//		   tipiAllegati.contains(TipoAllegato.PROVVEDIMENTO_FINALE_PRIVATO)) {
//			subfolderAllegati=SUB_FASCICOLO_PROVVEDIMENTI;
//		}else if(tipiAllegati.contains(TipoAllegato.PARERE_MIBAC)||
//				   tipiAllegati.contains(TipoAllegato.PARERE_MIBAC_PRIVATO)) {
//					subfolderAllegati=SUB_FASCICOLO_PARERI_MIBAC;
//		}else if(tipiAllegati.contains(TipoAllegato.RICEVUTA_TRASMISSIONE)) {
//					subfolderAllegati=SUB_FASCICOLO_RICEVUTE_TRASMISSIONE;
//		}
//		return subfolderAllegati;
		return tipiAllegati.get(0).getSubPathDiogene();
	}

	private void appendPathElem(List<ElementoPath> listElementPath,String nomeFolder,TipoAggregatoElemento tipo,Map<String,Object> attributi){
		ElementoPath appFolder=new ElementoPath();
		appFolder.setNomeAggregato(nomeFolder);
		appFolder.setTipoElemento(tipo);
		listElementPath.add(appFolder);
		if(attributi!=null) {
			appFolder.setAttributi(attributi);
		}
	}
	private void appendPathElem(List<ElementoPath> listElementPath,String nomeFolder,TipoAggregatoElemento tipo){
		appendPathElem(listElementPath, nomeFolder, tipo,null);
	}
	
	private String getCodiceEnte(String codicePratica) {
		String ret=null;
		if(codicePratica.startsWith(AutpaeConstants.PREFISSO_CODICE_REGIONE)) {
			ret=AutpaeConstants.PREFISSO_CODICE_REGIONE;
		}else if(codicePratica.startsWith(AutpaeConstants.PREFISSO_CODICE_AP)||
				codicePratica.startsWith(AutpaeConstants.PREFISSO_CODICE_AP)) {
			String[] tokens= codicePratica.split("-");
			ret=tokens[0].substring(2);
		}else {
			LOGGER.error("codicePratica con prefisso non riconosciuto codice: {}",codicePratica);
		}
		return ret;
	}
	
	private String getAnno(String codicePratica) {
		String ret=null;
		String[] tokens= codicePratica.split("-");
		if(tokens.length<3) {
			LOGGER.error("codicePratica errato, numero  token presenti errato: {}",codicePratica);
		}
		ret=tokens[2];
		return ret;
	}
	
	private String getNumero(String codicePratica) {
		String ret=null;
		String[] tokens= codicePratica.split("-");
		if(tokens.length<3) {
			LOGGER.error("codicePratica errato, numero  token presenti errato: {}",codicePratica);
		}
		ret=tokens[1];
		return ret;
	}
	
	
	/**
	 * costruisco lista token path come da schema
	 * - AUTPAE
	 *   - PUTT 
	 *     - 20006 - Unione dei comuni xyz
	 *       - 2021-0
	 *         - AP20006-123-2021
	 *           -Provvedimenti
	 * @autor Adriano Colaianni
	 * @date 18 ott 2021
	 * @param fileName
	 * @param lastSubfascicolo
	 * @param codicePratica
	 * @param orgCreazione
	 * @return
	 */
	private DiogeneDescrittorePath buildDescrittore(String fileName,String lastSubfascicolo, String codicePratica, String orgCreazione) {
		DiogeneDescrittorePath descr=new DiogeneDescrittorePath();
		List<ElementoPath> listElementPath=new ArrayList<>();
		descr.setNomeFileDiogene(fileName);
		descr.setPathElement(listElementPath);
		appendPathElem(listElementPath,nomeApplicazione.toUpperCase(),TipoAggregatoElemento.C,Map.of("descrizione","Autorizzazioni paesaggistiche")); //livello applicazione
		//livello catergoria procedimenti nell'applicazione (AUTPAE PUTT PARERI)
		appendPathElem(listElementPath,props.getCodiceApplicazione().toUpperCase(),
				TipoAggregatoElemento.C,Map.of("descrizione",props.getCodiceApplicazione().toUpperCase()));
		PaesaggioOrganizzazioneDTO pk=new PaesaggioOrganizzazioneDTO();
		pk.setId(Integer.parseInt(orgCreazione));
		try {
			pk=paeOrgDao.find(pk);	
		}catch(EmptyResultDataAccessException e) {
			pk.setDenominazione("Ente Delegato id "+pk.getId());
		}
		
		String nomeCartellaEnteDelegato=StringUtil.concateneString(
				this.getCodiceEnte(codicePratica),
				" - ",
				pk.getDenominazione());
		//CARTELLA ENTE DELEGATO es. 20006 - Unione dei comuni xyz
		appendPathElem(listElementPath,this.getCodiceEnte(codicePratica),TipoAggregatoElemento.C,
				Map.of("descrizione",nomeCartellaEnteDelegato)); //cartella ente delegato (es. 20006 , AUTPAESAF,
		//CARTELLA ANNO con Modulo mille es 2021-0
		Integer numeroPratica=Integer.parseInt(this.getNumero(codicePratica));
		String cartellaAnno=StringUtil.concateneString(
				this.getAnno(codicePratica),
				'-',
				(int)(numeroPratica/1000));
		appendPathElem(listElementPath,cartellaAnno,
				TipoAggregatoElemento.C,Map.of("descrizione",cartellaAnno)); 
		//CARTELLA CODICE PRATICA
		appendPathElem(listElementPath,codicePratica,TipoAggregatoElemento.R,Map.of("codicePratica",codicePratica));
		appendPathElem(listElementPath,lastSubfascicolo,TipoAggregatoElemento.SR);//cartella della tipologia di file
		return descr;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public void elaboraNotifica(String textMessageFromQueue) throws Exception {
		DiogeneNotifyWritedBean bean=JsonUtil.toBean(textMessageFromQueue, DiogeneNotifyWritedBean.class);
		allDao.updateReceiveDiogene(bean.getExternalKey(), bean.getIdDiogene(), bean.getPath(), bean.getPathReale());
	}

	
}
