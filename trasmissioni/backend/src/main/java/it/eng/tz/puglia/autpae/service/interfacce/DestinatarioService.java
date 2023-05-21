package it.eng.tz.puglia.autpae.service.interfacce;

import java.util.Collection;
import java.util.List;

import it.eng.tz.puglia.autpae.dto.TipologicaDestinatarioDTO;
import it.eng.tz.puglia.autpae.entity.DestinatarioDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoDestinatario;
import it.eng.tz.puglia.autpae.search.DestinatarioSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface DestinatarioService {

	// BaseRepository
//	public List<DestinatarioDTO> select() throws Exception;
	public long count(DestinatarioSearch filter) throws Exception;

	public DestinatarioDTO find(Long pk) throws Exception;

	public Long insert(DestinatarioDTO entity) throws Exception;

	public int update(DestinatarioDTO entity) throws Exception;

	public int delete(DestinatarioSearch search) throws Exception;

	public PaginatedList<DestinatarioDTO> search(DestinatarioSearch filter) throws Exception;

	// FullRepository
	public List<DestinatarioDTO> searchByIdCorrispondenzaAndEmails(Long idCorrispondenza, List<String> emails)
			throws Exception;

	public int updateFieldPec(List<Long> listaId, Boolean pec) throws Exception;

	public List<TipologicaDestinatarioDTO> findDestinatariForEnteDelegato(int idOrganizzazione,
			Collection<Integer> idEnti, TipoDestinatario tipoDest) throws Exception;

	public List<TipologicaDestinatarioDTO> findDestinatariForRegione(Collection<Integer> idEnti,
			TipoDestinatario tipoDest) throws Exception;

	public List<TipologicaDestinatarioDTO> findDestinatariForSoprintendenze(Collection<Integer> idEnti,
			TipoDestinatario tipoDest) throws Exception;

	public List<TipologicaDestinatarioDTO> findDestinatariForProvince(Collection<Integer> idEnti,
			TipoDestinatario tipoDest) throws Exception;

	public List<TipologicaDestinatarioDTO> findDestinatariForComuni(Collection<Integer> idEnti,
			TipoDestinatario tipoDest) throws Exception;

}