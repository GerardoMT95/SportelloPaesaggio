package it.eng.tz.puglia.autpae.service.interfacce;

import java.util.List;

import it.eng.tz.puglia.autpae.dto.TemplateEmailDestinatariDTO;
import it.eng.tz.puglia.autpae.entity.TemplateEmailDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoTemplate;
import it.eng.tz.puglia.autpae.search.TemplateEmailSearch;

public interface TemplateEmailService {

	//BaseRepository
//	public List<TemplateEmailDTO> select() throws Exception;
	public long count(TemplateEmailSearch filter) throws Exception;
	public TemplateEmailDTO find(TipoTemplate pk) throws Exception;
//	public TipoTemplate insert(TemplateEmailDTO entity) throws Exception;
	public int update(TemplateEmailDTO entity) throws Exception;
//	public int delete(TemplateEmailSearch entity) throws Exception;
//	public PaginatedList<TemplateEmailDTO> search(TemplateEmailSearch bean) throws Exception;
	
	
	
	public TemplateEmailDestinatariDTO info(TipoTemplate codice) throws Exception;

	public boolean salva(TemplateEmailDestinatariDTO body) throws Exception;

	public TemplateEmailDestinatariDTO resetEmail(TipoTemplate codice, boolean autoSave) throws Exception;

	public TemplateEmailDestinatariDTO resetDestinatari(TipoTemplate codice, boolean autoSave) throws Exception;
	
	public List<TemplateEmailDestinatariDTO> getAll() throws Exception;
	
}