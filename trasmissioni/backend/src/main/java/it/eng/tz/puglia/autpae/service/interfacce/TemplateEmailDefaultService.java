package it.eng.tz.puglia.autpae.service.interfacce;

import it.eng.tz.puglia.autpae.entity.TemplateEmailDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoTemplate;

public interface TemplateEmailDefaultService {

	//BaseRepository
//	public List<TemplateEmailDTO> select() throws Exception;
//	public long count(TemplateEmailSearch filter) throws Exception;
	public TemplateEmailDTO find(TipoTemplate pk) throws Exception;
//	public TipoTemplate insert(TemplateEmailDTO entity) throws Exception;
//	public int update(TemplateEmailDTO entity) throws Exception;
//	public int delete(TemplateEmailSearch entity) throws Exception;
//	public PaginatedList<TemplateEmailDTO> search(TemplateEmailSearch bean) throws Exception;

}