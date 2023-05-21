package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TemplateDestinatarioDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TemplateEmailDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TemplateDestinatarioRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TemplateEmailRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.TemplateDestinatarioSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.TemplateEmailSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.PlaceHolder;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.dto.TemplateEmailDestinatariDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.service.ITemplateMailService;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.error.exception.CustomOperationException;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class TemplateMailService implements ITemplateMailService {

	@Autowired
	TemplateEmailRepository templateEmailDao;
	@Autowired
	TemplateDestinatarioRepository templateDestinatarioDao;

	private static final Logger log = LoggerFactory.getLogger(TemplateMailService.class);

	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public PaginatedList<TemplateEmailDTO> search(TemplateEmailSearch search) throws Exception {
		PaginatedList<TemplateEmailDTO> ret = templateEmailDao.search(search);
		return ret;
	}

	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public TemplateEmailDestinatariDto info(int idOrganizzazione, String codice) throws Exception {
		TemplateEmailDTO pk = new TemplateEmailDTO();
		pk.setIdOrganizzazione(idOrganizzazione);
		pk.setCodice(codice);
		//TemplateEmailDTO ret = templateEmailDao.find(pk);
		TemplateEmailDTO ret=findAncheSuDefault(idOrganizzazione, codice);
		// prelevo anche i destinatari...
		TemplateDestinatarioSearch searchDest = new TemplateDestinatarioSearch();
		searchDest.setCodiceTemplate(ret.getCodice());
		searchDest.setIdOrganizzazione(ret.getIdOrganizzazione() + "");
		PaginatedList<TemplateDestinatarioDTO> listaDest = templateDestinatarioDao.search(searchDest);
		TemplateEmailDestinatariDto infoOut = new TemplateEmailDestinatariDto(ret, listaDest.getList());
		setInfoPlaceholder(infoOut);
		return infoOut;
	}
	
	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public TemplateEmailDTO findAncheSuDefault(int idOrganizzazione,String codice) throws Exception
	{
		TemplateEmailDTO pk = new TemplateEmailDTO();
		pk.setIdOrganizzazione(idOrganizzazione);
		pk.setCodice(codice);
		try {
			pk=templateEmailDao.find(pk);
		}catch(EmptyResultDataAccessException e) {
			pk.setIdOrganizzazione(0);//vado sui template default
			pk=templateEmailDao.find(pk);
		}
		return  pk;
	}
	
	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public List<TemplateDestinatarioDTO> infoList(int idOrganizzazione,int idTipoProce) throws Exception {
		
		TemplateDestinatarioSearch searchTemp = new TemplateDestinatarioSearch();
		searchTemp.setIdOrganizzazione(idOrganizzazione+"");
		PaginatedList<TemplateDestinatarioDTO> paginResp = templateDestinatarioDao.search(searchTemp);
		return paginResp.getList();
	}

	private void setInfoPlaceholder(TemplateEmailDestinatariDto infoOut) {
		// risolvo i placeholder
		infoOut.setPlaceholders(new ArrayList<>());
		String[] phs = infoOut.getTemplate().getPlaceholders().split(",");
		Arrays.asList(phs).forEach(el -> {
			infoOut.getPlaceholders().add(new PlainStringValueLabel(el, PlaceHolder.valueOf(el.trim()).getLegenda()));
		});
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
	@Transactional(transactionManager = DatabaseConfiguration.TX_WRITE, readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public int sincronizza(TemplateEmailSearch search) {
		long inserted = 0;
		List<TemplateEmailDTO> templateToCreate = templateEmailDao.getTemplateMancanti(search.getIdOrganizzazione(),
				search.getVisibilita());
		for (TemplateEmailDTO templ : templateToCreate) {
			int idOrganizzazione = search.getIdOrganizzazione();
			templ.setIdOrganizzazione(idOrganizzazione);
			inserted += templateEmailDao.insert(templ);
			// copio eventuali destinatari
			templateDestinatarioDao.copiaDestinatariDefault(idOrganizzazione, templ.getCodice());
		}
		return (int) inserted;
	}

	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public TemplateEmailDTO find(TemplateEmailDTO pk) throws Exception {
		return templateEmailDao.find(pk);
	}

	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_WRITE, readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int delete(TemplateEmailDTO pk) throws Exception {
		return templateEmailDao.delete(pk);
	}

	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_WRITE, readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int saveOrInsert(TemplateEmailDestinatariDto templateDto, String gruppo) {
		int ret = 0;
		// seek su template....
		// se esiste copro solo alcuni campi....
		TemplateEmailDTO template = templateDto.getTemplate();
		if (template.getCodice() != null) {
			// tentativo di update
			try {
				TemplateEmailDTO oldTemplate = templateEmailDao.find(template);
				if (StringUtil.isEmpty(oldTemplate.getVisibilita()) || oldTemplate.getVisibilita().contains(gruppo)) {
					oldTemplate.setNome(template.getNome());
					oldTemplate.setOggetto(template.getOggetto());
					oldTemplate.setTesto(template.getTesto());
					oldTemplate.setDescrizione(template.getDescrizione());
					oldTemplate.setProtocollazione(template.getProtocollazione());
					ret = templateEmailDao.update(oldTemplate);
					// allineo i destinatari....
					TemplateDestinatarioSearch searchDest = new TemplateDestinatarioSearch();
					searchDest.setIdOrganizzazione(template.getIdOrganizzazione() + "");
					searchDest.setCodiceTemplate(template.getCodice());
					PaginatedList<TemplateDestinatarioDTO> oldDests = templateDestinatarioDao.search(searchDest);
					oldDests.getList().stream().forEach(dest -> {
						// se nel nuovo esiste... faccio update...
						if (templateDto.getDestinatari() != null) {
							Optional<TemplateDestinatarioDTO> optDest = templateDto.getDestinatari().stream()
									.filter(destNew -> destNew.getId() == dest.getId()).findAny();
							if (optDest.isPresent()) {
								templateDestinatarioDao.update(optDest.get());
							} else {
								templateDestinatarioDao.delete(dest);
							}
						}else {
							// nessun elemento nel nuovo...
							templateDestinatarioDao.delete(dest);
						}
					});
					if (templateDto.getDestinatari() != null) {
						addDestinatari(templateDto, template);
					} 
				} else {
					throw new CustomOperationException("La visibilità del template non è ammessa");
				}
			} catch (Exception e) {
				log.error("Errore nell'update del templateEmail ", e);
				ret = 0;
			}
		} else {
			// nuovo template
			template.setCancellabile(true);
			template.setCodice(templateEmailDao.getNewCodice());
			template.setReadonlyOggetto("N");
			template.setReadonlyTesto("N");
			template.setVisibilita(","+gruppo+",");
			template.setSezione(""); //sezione blank indica per comunicazioni....
			ret = (int) templateEmailDao.insert(template);
			if (templateDto.getDestinatari() != null) {
				//tutti quelli senza id sono da inserire...
				addDestinatari(templateDto, template);
			}
		}
		return ret;
	}

	private void addDestinatari(TemplateEmailDestinatariDto templateDto, TemplateEmailDTO template) {
		//tutti quelli senza id sono da inserire...
		templateDto.getDestinatari().stream()
		.filter(destNew -> destNew.getId() == 0).forEach(dest->{
			dest.setCodiceTemplate(template.getCodice());
			dest.setIdOrganizzazione(template.getIdOrganizzazione());
			templateDestinatarioDao.insert(dest);
			});
	}
	
	@Override
	public TemplateEmailDestinatariDto getNew(int idOrganizzazione,String gruppo) {
		TemplateEmailDTO template=new TemplateEmailDTO(); 
		template.setIdOrganizzazione(idOrganizzazione);
		template.setVisibilita(gruppo);
		TemplateEmailDestinatariDto ret=new TemplateEmailDestinatariDto(template,new ArrayList<>());
		setInfoPlaceholder(ret);
		return ret;
	}

}
