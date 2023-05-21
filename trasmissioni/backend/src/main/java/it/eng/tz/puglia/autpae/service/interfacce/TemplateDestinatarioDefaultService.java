package it.eng.tz.puglia.autpae.service.interfacce;

import it.eng.tz.puglia.autpae.dto.DestinatarioTemplateDTO;
import it.eng.tz.puglia.autpae.search.TemplateDestinatarioSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface TemplateDestinatarioDefaultService {

	//BaseRepository
//	public List<TemplateDestinatarioDTO> select() throws Exception;
//	public long count(TemplateDestinatarioSearch filter) throws Exception;
//	public TemplateDestinatarioDTO find(Long pk) throws Exception;
//	public Long insert(TemplateDestinatarioDTO entity) throws Exception;
//	public int update(TemplateDestinatarioDTO entity) throws Exception;
//	public int delete(TemplateDestinatarioSearch search) throws Exception;
	public PaginatedList<DestinatarioTemplateDTO> search(TemplateDestinatarioSearch filter) throws Exception;
		
}