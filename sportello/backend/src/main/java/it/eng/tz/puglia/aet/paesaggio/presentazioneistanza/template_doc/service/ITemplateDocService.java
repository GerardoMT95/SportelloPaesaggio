package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.service;

import java.util.List;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.dto.TemplateDocDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.dto.TemplateDocSezioniDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.search.TemplateDocSearch;
import it.eng.tz.puglia.bean.PaginatedList;

/**
 * 
 * @author acolaianni
 *
 */
public interface ITemplateDocService {

	public long count(TemplateDocSearch filter) throws Exception;
	public TemplateDocDTO find(TemplateDocDTO pk) throws Exception;
	
	/**
	 * salva solo le sezioni con tipoSezione!= IMAGE
	 * @param body
	 * @return
	 */
	public Boolean salva(TemplateDocDTO body);
	/**
	 * copia il campo valore dalla tabella delle sezioni default, in caso di IMAGE effettua anche un check se su cms sono stati 
	 * caricati i loghi default per le sezioni prelevandoli dal classpath /jasper/image_template_default
	 * @param nome 
	 * @return
	 * @throws Exception
	 */
	public TemplateDocDTO reset(TemplateDocDTO pk) throws Exception;
	public AllegatiDTO caricaImage(TemplateDocSezioniDTO sezione, MultipartFile file) throws Exception;
	
	/**
	 * elimino il riferimento dalla sezione e se è presente una sezione default con tale riferimento, lo lascio....
	 * @param idAllegato
	 * @throws Exception
	 */
	public void deleteRifAllegato(int idOrganizzazione, UUID idAllegato) throws Exception;
	public PaginatedList<TemplateDocDTO> getAllPaginated(TemplateDocSearch br) throws Exception;
	List<TemplateDocDTO> getAll(TemplateDocSearch filter) throws Exception;
	TemplateDocDTO info(int idOrganizzazione, String nome,boolean ancheSuDefault) throws Exception;
	int sincronizza(TemplateDocSearch search);
	Resource getImageDefault(TemplateDocSezioniDTO sezione) throws CustomOperationToAdviceException;
	TemplateDocDTO findAncheSuDefault(TemplateDocDTO pk) throws Exception;
	
	
	
}