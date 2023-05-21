package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.service;


import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TemplateDestinatarioDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TemplateEmailDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.TemplateEmailSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.dto.TemplateEmailDestinatariDto;
import it.eng.tz.puglia.bean.PaginatedList;

public interface ITemplateMailService {
	
	/**
	 * effettuare una sincronizzazione con @link{sincronizza} prima di effettuare la search
	 * 
	 * effettua una search a partire dai template email con cancellabile a false accessibili al tipo ente in questione
	 * @author acolaianni
	 *
	 * @param idOrganizzazione
	 * @return
	 * @throws Exception
	 */
	public PaginatedList<TemplateEmailDTO> search(TemplateEmailSearch search) throws Exception;
	/**
	 * 
	 * @author acolaianni
	 *
	 * @param idOrganizzazione
	 * @param codice  del template_email 
	 * @return
	 * @throws Exception
	 */
	public TemplateEmailDestinatariDto info(int idOrganizzazione,String codice) throws Exception;
	
	public TemplateEmailDTO find(TemplateEmailDTO pk) throws Exception;
	/**
	 * cerca per idOrganizzazione e se nullo passa a cercare per idOrganizzazione=0 (template default)
	 * @author acolaianni
	 *
	 * @param idOrganizzazione
	 * @param codice
	 * @return
	 * @throws Exception
	 */
	TemplateEmailDTO findAncheSuDefault(int idOrganizzazione,String codice) throws Exception;
	public int delete(TemplateEmailDTO pk) throws Exception;
	public int saveOrInsert(TemplateEmailDestinatariDto templateDto, String gruppo);
	public TemplateEmailDestinatariDto getNew(int idOrganizzazione, String gruppo);
	int sincronizza(TemplateEmailSearch search);
	public List<TemplateDestinatarioDTO> infoList(int idOrganizzazione,int idTipoProcedimento) throws Exception;

}
