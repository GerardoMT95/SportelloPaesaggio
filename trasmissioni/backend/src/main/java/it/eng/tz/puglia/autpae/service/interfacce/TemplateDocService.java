package it.eng.tz.puglia.autpae.service.interfacce;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.entity.AllegatoDTO;
import it.eng.tz.puglia.autpae.entity.TemplateDocDTO;
import it.eng.tz.puglia.autpae.entity.TemplateDocSezioneDTO;
import it.eng.tz.puglia.autpae.search.TemplateDocSearch;
import it.eng.tz.puglia.bean.PaginatedList;

/**
 * 
 * @author acolaianni
 *
 */
public interface TemplateDocService {

	public long count(TemplateDocSearch filter) throws Exception;
	public TemplateDocDTO find(String pk) throws Exception;
	public List<TemplateDocDTO> getAll() throws Exception;
	public TemplateDocDTO info(String nome) throws Exception;
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
	public TemplateDocDTO reset(String nome) throws Exception;
	public AllegatoDTO caricaImage(TemplateDocSezioneDTO sezione, MultipartFile file) throws Exception;
	
	/**
	 * elimino il riferimento dalla sezione e se è presente una sezione default con tale riferimento, lo lascio....
	 * @param idAllegato
	 * @throws Exception
	 */
	public void deleteRifAllegato(long idAllegato) throws Exception;
	public PaginatedList<TemplateDocDTO> getAllPaginated(TemplateDocSearch br) throws Exception;
	
	Resource getImageDefault(TemplateDocSezioneDTO sezione) throws CustomOperationToAdviceException;
	
}