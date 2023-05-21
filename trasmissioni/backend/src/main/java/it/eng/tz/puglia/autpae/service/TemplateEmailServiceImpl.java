package it.eng.tz.puglia.autpae.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dto.DestinatarioTemplateDTO;
import it.eng.tz.puglia.autpae.dto.TemplateEmailDestinatariDTO;
import it.eng.tz.puglia.autpae.entity.PlaceholderDTO;
import it.eng.tz.puglia.autpae.entity.TemplateEmailDTO;
import it.eng.tz.puglia.autpae.enumeratori.PlaceHolder;
import it.eng.tz.puglia.autpae.enumeratori.TipoDestinatario;
import it.eng.tz.puglia.autpae.enumeratori.TipoTemplate;
import it.eng.tz.puglia.autpae.repository.base.TemplateEmailBaseRepository;
import it.eng.tz.puglia.autpae.search.TemplateDestinatarioSearch;
import it.eng.tz.puglia.autpae.search.TemplateEmailSearch;
import it.eng.tz.puglia.autpae.service.interfacce.TemplateDestinatarioDefaultService;
import it.eng.tz.puglia.autpae.service.interfacce.TemplateDestinatarioService;
import it.eng.tz.puglia.autpae.service.interfacce.TemplateEmailDefaultService;
import it.eng.tz.puglia.autpae.service.interfacce.TemplateEmailService;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class TemplateEmailServiceImpl implements TemplateEmailService {

	private static final Logger log = LoggerFactory.getLogger(TemplateEmailServiceImpl.class);
	
	@Autowired TemplateEmailBaseRepository repository;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository
	
//	@Override @Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 		  List<TemplateEmailDTO> select() 					  		throws Exception { return repository.select(); 	   	 }
	@Override @Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public long 						 	 count (TemplateEmailSearch filter) throws Exception { return repository.count (filter); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 			   TemplateEmailDTO  find  (TipoTemplate pk) 			throws Exception { return repository.find  (pk); 	 }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=false) public TipoTemplate 					 insert(TemplateEmailDTO entity) 	throws Exception { return repository.insert(entity); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 	 update(TemplateEmailDTO entity)	throws Exception { return repository.update(entity); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 	 delete(TemplateEmailSearch search) throws Exception { return repository.delete(search); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public PaginatedList<TemplateEmailDTO> search(TemplateEmailSearch filter) throws Exception { return repository.search(filter); }

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Altri Metodi
	
	@Autowired TemplateEmailDefaultService        templateEmailDefaultService;
	@Autowired TemplateDestinatarioService 		  templateDestinatarioService;
	@Autowired TemplateDestinatarioDefaultService templateDestinatarioDefaultService;
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public TemplateEmailDestinatariDTO info(TipoTemplate codice) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		TemplateEmailDestinatariDTO dto = new TemplateEmailDestinatariDTO(repository.find(codice));
		caricaPlaceholder(codice, dto);
		caricaDestinatariAutomatici(codice, dto);
		
		TemplateDestinatarioSearch searchD = new TemplateDestinatarioSearch();
		searchD.setCodiceTemplate(codice);
		dto.setDestinatari(templateDestinatarioService.search(searchD).getList());
		
		return dto;
	}
	
	private void caricaPlaceholder(TipoTemplate codice, TemplateEmailDestinatariDTO dto) {
		for(PlaceHolder plH : codice.getPlaceholdersAmmessi()) {
			dto.getPlaceholders().add(new PlaceholderDTO(plH.name(), plH.getLegenda()));
		}
	}

	private void caricaDestinatariAutomatici(TipoTemplate codice, TemplateEmailDestinatariDTO dto) {
		for(String dest : codice.getDestinatariAutomatici()) {
			dto.getDestinatariAutomatici().add(dest);
		}
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public boolean salva(TemplateEmailDestinatariDTO body) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		if  (   body.getCodice()==null
			 || StringUtil.isEmpty(body.getOggetto())
			 || StringUtil.isEmpty(body.getTesto())
			 || StringUtil.isEmpty(body.getDescrizione())
//			 || StringUtil.isEmpty(body.getNome()) 
		    ) {
			log.error("Dati incompleti!");
			throw new Exception("Dati incompleti!");
		}
		
		if (body.getDestinatari()!=null) {
			Set<String> email = new HashSet<String>();
			int numeroEmail = 0;
			for (int i=0; i<body.getDestinatari().size(); i++) {
				
				if (     StringUtil.isEmpty(body.getDestinatari().get(i).getNome())
				    || ( StringUtil.isEmpty(body.getDestinatari().get(i).getEmail())  &&  body.getDestinatari().get(i).isPec()) )
				{
				  log.error("Dati incompleti!");
				  throw new CustomOperationToAdviceException("Dati incompleti!");
				}
				
				if (body.getDestinatari().get(i).getTipo()==null)
					body.getDestinatari().get(i).setTipo(TipoDestinatario.TO);
				
				body.getDestinatari().get(i).setCodiceTemplate(body.getCodice());
				email.add(body.getDestinatari().get(i).getEmail().toLowerCase());
				numeroEmail++;
//				if (!StringUtil.isEmpty(body.getDestinatari().get(i).getEmail())) {
//					email.add(body.getDestinatari().get(i).getEmail().toLowerCase());
//					numeroEmail++;
//				}
//				if (!StringUtil.isEmpty(body.getDestinatari().get(i).getPec())) {
//					email.add(body.getDestinatari().get(i).getPec().toLowerCase());
//					numeroEmail++;
//				}
			}
			
			if (email.size()!=numeroEmail) {
				  log.error("Errore. Email ripetute!");
				  throw new CustomOperationToAdviceException("Errore. indirizzi email ripetuti!");
			   // return false;
			}
		}
		
		if (repository.update(body)!=1)
			return false;
		
		templateDestinatarioService.deleteAll(body.getCodice());
		if (body.getDestinatari()!=null) {
			for (DestinatarioTemplateDTO destinatario : body.getDestinatari()) {
				try {
					if (templateDestinatarioService.insert(destinatario)!=1)
						throw new CustomOperationToAdviceException("Inserimento fallito");
				} catch (Exception e) {
					log.error("Errore nell'inserimento di un destinatario di default",e);
					throw e;	//e.printStackTrace();
				}
			}
		}
		
		return true;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)	// TODO: se disabilito l'autoSave posso usare "readOnly=true"
	public TemplateEmailDestinatariDTO resetEmail(TipoTemplate codice, boolean autoSave) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		TemplateEmailDestinatariDTO emailDefault = new TemplateEmailDestinatariDTO(templateEmailDefaultService.find(codice));
		
		// se 'autoSave' è TRUE, aggiorno anche il DB, altrimenti passo al FE soltanto il DTO con i dati ORIGINARI
		if (autoSave) {
			if (repository.update(emailDefault)!=1) {
				log.error("Reset del template email originale fallito!");
				throw new CustomOperationToAdviceException("Reset del template email originale fallito!");
			}
		}
		// potrei anche restituire al FE i destinatari (ATTUALI presenti sul DB)
	//	TemplateDestinatarioSearch searchD = new TemplateDestinatarioSearch();
	//	searchD.setCodiceTemplate(codice);
	//	emailDefault.setDestinatari(templateDestinatarioService.search(searchD).getList());
		
		return emailDefault;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)	// TODO: se disabilito l'autoSave posso usare "readOnly=true"
	public TemplateEmailDestinatariDTO resetDestinatari(TipoTemplate codice, boolean autoSave) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		// potrei anche restituire al FE il template Email (ATTUALE presente sul DB)
		TemplateEmailDestinatariDTO dto = new TemplateEmailDestinatariDTO(/*templateEmailService.find(codice)*/);
		
		TemplateDestinatarioSearch searchD = new TemplateDestinatarioSearch();
		searchD.setCodiceTemplate(codice);
		dto.setDestinatari(templateDestinatarioDefaultService.search(searchD).getList());
		
		// se 'autoSave' è TRUE, aggiorno anche il DB, altrimenti passo al FE soltanto il DTO con i dati ORIGINARI
		if (autoSave) {
			templateDestinatarioService.deleteAll(codice);
			if (dto.getDestinatari()!=null) {
				dto.getDestinatari().forEach(destinatario-> {
					try {
						if (templateDestinatarioService.insert(destinatario)!=1)
							throw new Exception("Inserimento fallito");
					} catch (Exception e) {
						log.error("Errore nell'inserimento di un destinatario di default",e);
					}
				});
			}
		}
		
		return dto;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public List<TemplateEmailDestinatariDTO> getAll() throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		List<TemplateEmailDestinatariDTO> lista = new ArrayList<>();
		for(TipoTemplate tipo:TipoTemplate.values()) {
			lista.add(this.info(tipo));	
		}
//	    lista.add(this.info(TipoTemplate.INVIO_COMUNICAZIONI));
//		lista.add(this.info(TipoTemplate.INVIO_TRASMISSIONE));
//		lista.add(this.info(TipoTemplate.INVIO_RITRASMISSIONE));
//		lista.add(this.info(TipoTemplate.INVIO_ULTERIORE_DOCUMENTAZIONE));
//		lista.add(this.info(TipoTemplate.ESITO_CAMPIONAMENTO));
//		lista.add(this.info(TipoTemplate.ESITO_VERIFICA));
//		lista.add(this.info(TipoTemplate.MODIFICA_POST_TRASMISSIONE));
//		lista.add(this.info(TipoTemplate.REVOCA_MODIFICA_POST_TRASMISSIONE));
//		lista.add(this.info(TipoTemplate.ELIMINAZIONE_POST_TRASMISSIONE));
//		lista.add(this.info(TipoTemplate.NOTIFICA_PRE_CAMPIONAMENTO));
//		lista.add(this.info(TipoTemplate.NOTIFICA_PRE_VERIFICA));
//		lista.add(this.info(TipoTemplate.ASSEGNAMENTO_FASCICOLO));
//		lista.add(this.info(TipoTemplate.DISASSEGNAMENTO_FASCICOLO));
		return lista;
	}
	
}