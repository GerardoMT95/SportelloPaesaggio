package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.service.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AllowedMimeType;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.AllegatiRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.AllegatoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.dto.TemplateDocDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.dto.TemplateDocSezioniDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.dto.TipoSezioneDoc;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.repository.TemplateDocRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.repository.TemplateDocSezioniRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.search.TemplateDocSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.search.TemplateDocSezioniSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.service.ITemplateDocService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.MockMultipartFile;
import it.eng.tz.puglia.bean.PaginatedList;

@Service
public class TemplateDocService implements ITemplateDocService {

	private static final Logger log = LoggerFactory.getLogger(TemplateDocService.class);

	@Autowired
	TemplateDocRepository templateDao;
	@Autowired
	TemplateDocSezioniRepository templateSezioniDao;
	@Autowired
	AllegatiRepository allegatoDao;
	
	@Autowired
	AllegatoService allegatoSvc;
	
	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public long count(TemplateDocSearch filter) throws Exception {
		return templateDao.search(filter).getCount();
	}

	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public List<TemplateDocDTO> getAll(TemplateDocSearch filter) throws Exception {
		List<TemplateDocDTO> allTemplate = templateDao.search(filter).getList();
		if (allTemplate != null && allTemplate.size() > 0) {
			allTemplate.forEach(template -> {
				this.setAllData(template);
			});
		}
		return allTemplate;
	}
	
	private void setAllData(TemplateDocDTO template) {
		TemplateDocSezioniSearch searchSezioni=new TemplateDocSezioniSearch();
		searchSezioni.setIdOrganizzazione(template.getIdOrganizzazione()+"");
		searchSezioni.setTemplateDocNome(template.getNome());
		PaginatedList<TemplateDocSezioniDTO> sezioni = templateSezioniDao.search(searchSezioni);
		template.setSezioni(sezioni.getList());
		if (template.getSezioni() != null && template.getSezioni().size() > 0) {
			template.getSezioni().forEach(sezione -> {
				if (sezione.getIdAllegato() != null) {
					try {
						AllegatiDTO all=new AllegatiDTO();
						all.setId(sezione.getIdAllegato());
						sezione.setAllegato(allegatoDao.find(all));
						sezione.getAllegato().setDescrizione(sezione.getNome());
					} catch (Exception e) {
						log.error("Errore nel retrieval dei metedati dell'allegato con id:" + sezione.getIdAllegato(),
								e);
					}
				}
			});
		}
	}
	
	
	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public TemplateDocDTO info(int idOrganizzazione,String nome,boolean ancheSuDefault) throws Exception {
		TemplateDocDTO pk=new TemplateDocDTO();
		pk.setIdOrganizzazione(idOrganizzazione);
		pk.setNome(nome);
		TemplateDocDTO ret=null;
		if(ancheSuDefault) {
			ret = this.findAncheSuDefault(pk);
		}else {
			ret = templateDao.find(pk);	
		}
		setAllData(ret);
		return ret;
	}
	
	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public TemplateDocDTO find(TemplateDocDTO pk) throws Exception {
		return templateDao.find(pk);
	}
	
	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public TemplateDocDTO findAncheSuDefault(TemplateDocDTO pk) throws Exception {
		try{
			return templateDao.find(pk);
		}catch(EmptyResultDataAccessException e){
			pk.setIdOrganizzazione(0);
			return templateDao.find(pk);
		}
	}

	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_WRITE, readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Boolean salva(TemplateDocDTO body) {
		body.getSezioni().forEach(sezione -> {
			if(!sezione.getTipoSezione().equals(TipoSezioneDoc.IMAGE.name())) { 
				this.templateSezioniDao.update(sezione);
			}
		});
		return null;
	}

	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_WRITE, readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public TemplateDocDTO reset(TemplateDocDTO pk) throws Exception {
		// leggo il default,
		// per le sezioni con tipo IMAGE se id_allegato del default è lo stesso del non
		// default non faccio nulla sul cms...
		TemplateDocDTO template = this.templateDao.find(pk);
		this.setAllData(template);
		List<TemplateDocSezioniDTO> sezioni = template.getSezioni();
		if (sezioni != null && sezioni.size() > 0) {
			for (TemplateDocSezioniDTO sezione : sezioni) {
				if (sezione.getTipoSezione().equals(TipoSezioneDoc.IMAGE.name())) {
					resetSezioneImageDefault(sezione);
				}
				templateSezioniDao.resetSezione(sezione);
			}
			;
		}
		return null;

	}
	
	private String filenameDefault(String templateDocNome,String nomeSezione) {
		String fileKeyName=templateDocNome+"_"+nomeSezione;
		fileKeyName=fileKeyName.replace(' ', '_');
		fileKeyName=fileKeyName.replace('\\', '_');
		fileKeyName=fileKeyName.replace('/', '_');
		return fileKeyName;
	}
	
	private String filenameDefault(TemplateDocSezioniDTO sezione) {
		return this.filenameDefault(sezione.getTemplateDocNome(),sezione.getNome());
	}
	
	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, readOnly = false, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
	public Resource getImageDefault(TemplateDocSezioniDTO sezione) throws CustomOperationToAdviceException {
		Resource res = new ClassPathResource(
				"jasper/image_template_default/" + this.filenameDefault(sezione) + ".png");
		return res;
	}
	
	
	private void resetSezioneImageDefault(TemplateDocSezioniDTO sezione) throws CustomOperationToAdviceException {
		// controllo se la tabella default ha l'id_allegato....altrimenti lo metto 
		try {
			TemplateDocSezioniDTO sezioneDef = new TemplateDocSezioniDTO();
			sezioneDef.setIdOrganizzazione(0);
			sezioneDef.setTemplateDocNome(sezione.getTemplateDocNome());
			sezioneDef.setNome(sezione.getNome());
			sezioneDef=templateSezioniDao.find(sezioneDef);
			if (sezioneDef.getIdAllegato() == null) {
				//rimetto il logo default sul cms prelevandolo dal classpath con nomefile NOMETEMPLATE_NOMESEZIONE.png
				UUID idAllegato=null;
				Resource res =new ClassPathResource("jasper/image_template_default/"+this.filenameDefault(sezioneDef)+".png");
				MockMultipartFile file=null;
				if(res.exists()) {
					String mime = AllowedMimeType.detectMimeType(res.getInputStream());
					file=new MockMultipartFile(this.filenameDefault(sezioneDef)+".png",mime ,res.getInputStream());
				}else {
					throw new CustomOperationToAdviceException("immagine default per la sezione non trovata: "+"jasper/image_template_default/"+this.filenameDefault(sezioneDef)+".png");
				}
				AllegatiDTO allegatoCustomDTO = allegatoSvc.uploadLogoTemplate(1500, null, file);
				idAllegato=allegatoCustomDTO.getId();
				sezioneDef.setIdAllegato(idAllegato);
				templateSezioniDao.update(sezioneDef);
				sezione.setIdAllegato(idAllegato);
				templateSezioniDao.update(sezione);
			}
		} catch (CustomOperationToAdviceException e) {
			log.error("errore durante il reset del logo "+sezione.getNome(),e);
			throw e;
		} 
		catch (Exception e) {
			log.error("Sezione default non trovata:" + sezione, e);
		}
	}

	
	
	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_WRITE, readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public AllegatiDTO caricaImage(TemplateDocSezioniDTO sezione, MultipartFile file) throws Exception {
		AllegatiDTO ret = allegatoSvc.uploadLogoTemplate(1500, null, file);
		ret.setDescrizione(sezione.getNome());
		sezione.setIdAllegato(ret.getId());
		templateSezioniDao.update(sezione);
		return ret;
	}

	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_WRITE, readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void deleteRifAllegato(int idOrganizzazione,UUID idAllegato) throws Exception {
		TemplateDocSezioniSearch searchSezione=new TemplateDocSezioniSearch();
		searchSezione.setIdOrganizzazione(idOrganizzazione+"");
		searchSezione.setIdAllegato(idAllegato.toString());
		List<TemplateDocSezioniDTO> list = templateSezioniDao.search(searchSezione).getList();
		if(list!=null && list.size()>0) {
			//abblenco il riferimento all'allegato...
			TemplateDocSezioniDTO sez = list.get(0);
			sez.setIdAllegato(null);
			templateSezioniDao.update(sez);
			//cerco la corrispondente sezione default...:
			searchSezione.setIdOrganizzazione("0");
			searchSezione.setIdAllegato(idAllegato.toString());
			List<TemplateDocSezioniDTO> listDef = templateSezioniDao.search(searchSezione).getList();
			if(listDef==null || listDef.size()==0) {
				AllegatiDTO logoSez=new AllegatiDTO();
				logoSez.setId(idAllegato);
				allegatoDao.delete(logoSez);	
			}
		}
		
	}

	@Override  
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public PaginatedList<TemplateDocDTO> getAllPaginated(TemplateDocSearch br) throws Exception {
		PaginatedList<TemplateDocDTO> ret = templateDao.search(br);
		return ret;
	}

	
	/**
	 * a regime andrebbe chiamato una sola volta (a creazione di nuova
	 * organizzazione)
	 * 
	 * @author acolaianni
	 *
	 * @param search
	 * @return
	 */
	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_WRITE, readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int sincronizza(TemplateDocSearch search) {
		long inserted = 0;
		List<TemplateDocDTO> templateToCreate = templateDao.getTemplateMancanti(search.getIdOrganizzazione());
		for (TemplateDocDTO templ : templateToCreate) {
			int idOrganizzazione = Integer.parseInt(search.getIdOrganizzazione());
			templ.setIdOrganizzazione(idOrganizzazione);
			inserted += templateDao.insert(templ);
			// copio le sezioni....
			templateSezioniDao.copiaSezioniDefault(idOrganizzazione, templ.getNome());
		}
		return (int) inserted;
	}
	
}
