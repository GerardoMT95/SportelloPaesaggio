package it.eng.tz.puglia.autpae.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dto.AllegatoCustomDTO;
import it.eng.tz.puglia.autpae.entity.AllegatoDTO;
import it.eng.tz.puglia.autpae.entity.TemplateDocDTO;
import it.eng.tz.puglia.autpae.entity.TemplateDocSezioneDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.enumeratori.TipoSezioneDoc;
import it.eng.tz.puglia.autpae.repository.AllegatoFullRepository;
import it.eng.tz.puglia.autpae.repository.TemplateDocFullRepository;
import it.eng.tz.puglia.autpae.search.TemplateDocSearch;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoService;
import it.eng.tz.puglia.autpae.service.interfacce.TemplateDocService;
import it.eng.tz.puglia.autpae.utility.MockMultipartFile;
import it.eng.tz.puglia.bean.PaginatedList;

@Service
public class TemplateDocServiceImpl implements TemplateDocService {

	private static final Logger log = LoggerFactory.getLogger(TemplateDocServiceImpl.class);

	
	@Autowired
	TemplateDocFullRepository templateDao;
	@Autowired
	AllegatoFullRepository allegatoDao;
	
	@Autowired
	AllegatoService allegatoSvc;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public long count(TemplateDocSearch filter) throws Exception {
		return templateDao.count(filter);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public TemplateDocDTO find(String pk) throws Exception {
		return templateDao.find(pk);
	}

	/**
	 * restituisce tutti i template con i relativi dettagli... sembra inefficiente
	 * ma non saranno più di qualche unità... quindi niente paginazioni etc...
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public List<TemplateDocDTO> getAll() throws Exception {
		List<TemplateDocDTO> allTemplate = templateDao.select();
		if (allTemplate != null && allTemplate.size() > 0) {
			allTemplate.forEach(template -> {
				this.getAllData(template);
			});
		}
		return allTemplate;
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
	public Resource getImageDefault(TemplateDocSezioneDTO sezione) throws CustomOperationToAdviceException {
		Resource res = new ClassPathResource("jasper/Immagine3.png");
		return res;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public PaginatedList<TemplateDocDTO> getAllPaginated(TemplateDocSearch br) throws Exception{
		PaginatedList<TemplateDocDTO> allPaginated = templateDao.search(br);
		List<TemplateDocDTO> allTemplatePaginated = allPaginated.getList();
		if (allTemplatePaginated != null && allTemplatePaginated.size() > 0) {
			allTemplatePaginated.forEach(template -> {
				this.getAllData(template);
			});
		}
		return allPaginated;
	}

	private void getAllData(TemplateDocDTO template) {
		template.setSezioni(templateDao.getSezioniTemplate(template.getNome()));
		if (template.getSezioni() != null && template.getSezioni().size() > 0) {
			template.getSezioni().forEach(sezione -> {
				sezione.setPlaceholders(templateDao.getPlaceholderSezione(template.getNome(), sezione.getNome()));
				if (sezione.getIdAllegato() != null) {
					try {
						sezione.setAllegato(allegatoDao.find(sezione.getIdAllegato()));
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
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public TemplateDocDTO info(String nome) throws Exception {
		TemplateDocDTO template = templateDao.find(nome);
		this.getAllData(template);
		return template;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class }, timeout = 60000, readOnly = false)
	public Boolean salva(TemplateDocDTO body) {
		body.getSezioni().forEach(sezione -> {
			if(sezione.getTipoSezione()!=TipoSezioneDoc.IMAGE) { 
				this.templateDao.updateSezioneTemplate(sezione.getTemplateDocnome(), sezione.getNome(),
						sezione.getValore(),sezione.getIdAllegato());	
			}
		});
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public TemplateDocDTO reset(String nome) throws Exception {
		// leggo il default,
		// per le sezioni con tipo IMAGE se id_allegato del default è lo stesso del non
		// default non faccio nulla sul cms...
		TemplateDocDTO template = this.templateDao.find(nome);
		template.setSezioni(templateDao.getSezioniTemplate(nome));
		List<TemplateDocSezioneDTO> sezioni = template.getSezioni();
		if (sezioni != null && sezioni.size() > 0) {
			for(TemplateDocSezioneDTO sezione:sezioni){
				if (sezione.getTipoSezione().equals(TipoSezioneDoc.IMAGE)) {
					resetSezioneImageDefault(sezione);
				}
				templateDao.resetSezioneTemplate(sezione.getTemplateDocnome(), sezione.getNome());
			};
		}
		return null;
	}
	
	private String filenameDefault(TemplateDocSezioneDTO sezione) {
		String fileKeyName=sezione.getTemplateDocnome()+"_"+sezione.getNome();
		fileKeyName=fileKeyName.replace(' ', '_');
		fileKeyName=fileKeyName.replace('\\', '_');
		fileKeyName=fileKeyName.replace('/', '_');
		return fileKeyName;
	}
	
	/**
	 * sostituisce spazio / \ con _
	 * @param folder
	 * @return
	 */
	private String withoutCharSpecial(String folder) {
		folder=folder.replace(' ', '_');
		folder=folder.replace('\\', '_');
		folder=folder.replace('/', '_');
		return folder;
	}
	
	private String pathCmsSezione(TemplateDocSezioneDTO sezione) {
		return "configurazione/template_doc/"+
				this.withoutCharSpecial(sezione.getTemplateDocnome())+"/"+this.withoutCharSpecial(sezione.getNome());
	}

	private void resetSezioneImageDefault(TemplateDocSezioneDTO sezione) throws CustomOperationToAdviceException {
		// controllo se la tabella default ha l'id_allegato....altrimenti lo metto 
		try {
			TemplateDocSezioneDTO sezioneDef = templateDao
					.getSezioneDefaultTemplate(sezione.getTemplateDocnome(), sezione.getNome());
			if (sezioneDef.getIdAllegato() == null) {
				//rimetto il logo default sul cms prelevandolo dal classpath con nomefile NOMETEMPLATE_NOMESEZIONE.png
				Long idAllegato=null;
				
				Resource res =new ClassPathResource("jasper/image_template_default/"+this.filenameDefault(sezioneDef)+".png");
				MockMultipartFile file=null;
				if(res.exists()) {
					file=new MockMultipartFile(this.filenameDefault(sezioneDef)+".png", res.getInputStream());
				}else {
					throw new CustomOperationToAdviceException("immagine default per la sezione non trovata: "+"jasper/image_template_default/"+this.filenameDefault(sezioneDef)+".png");
				}
				AllegatoCustomDTO allegatoCustomDTO = 
						allegatoSvc.inserisciAllegato(null, List.of(TipoAllegato.IMAGE_TEMPLATE_DOC), file, null,
								this.pathCmsSezione(sezioneDef));
				idAllegato=allegatoCustomDTO.getId();
				sezioneDef.setIdAllegato(idAllegato);
				templateDao.updateSezioneTemplateDefault(sezioneDef.getTemplateDocnome(), sezioneDef.getNome(), sezioneDef.getIdAllegato());
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
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class }, timeout = 60000, readOnly = false)
	public AllegatoDTO caricaImage(TemplateDocSezioneDTO sezione, MultipartFile file) throws Exception {
		AllegatoCustomDTO retCustom = allegatoSvc.inserisciAllegato(null, List.of(TipoAllegato.IMAGE_TEMPLATE_DOC), file, null,this.pathCmsSezione(sezione));
		AllegatoDTO ret=new AllegatoDTO(retCustom); 
		ret.setTipo(TipoAllegato.IMAGE_TEMPLATE_DOC);
		ret.setDescrizione(sezione.getNome());
		this.templateDao.updateSezioneTemplate(sezione.getTemplateDocnome(), sezione.getNome(), null,ret.getId());
		return ret;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class }, timeout = 60000, readOnly = false)
	public void deleteRifAllegato(long idAllegato) throws Exception {
		this.templateDao.removeAllegatoId(idAllegato);
		List<TemplateDocSezioneDTO> listaSezioniDefault = this.templateDao.getSezioneDefaultByAllegatoId(idAllegato);
		if(listaSezioniDefault==null || listaSezioniDefault.size()==0) {
			allegatoSvc.eliminaAllegatoWithoutCheck(idAllegato);	
		}
	}

}