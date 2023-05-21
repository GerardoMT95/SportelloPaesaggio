package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoDestinatario;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinatarioDTO;

public interface DestinatarioService
{
	List<DestinatarioDTO> findDestinatariForEnteDelegato(int idOrganizzazione, Collection<Integer> idEnti, TipoDestinatario tipoDest) throws Exception;
	List<DestinatarioDTO> findDestinatariForRegione(Collection<Integer> idEnti, TipoDestinatario tipoDest) throws Exception;
	List<DestinatarioDTO> findDestinatariForSoprintendenze(Collection<Integer> idEnti, TipoDestinatario tipoDest) throws Exception;
	List<DestinatarioDTO> findDestinatariForProvince(Collection<Integer> idEnti, TipoDestinatario tipoDest) throws Exception;
	List<DestinatarioDTO> findDestinatariForComuni(Collection<Integer> idEnti, TipoDestinatario tipoDest) throws Exception;
	List<DestinatarioDTO> findDestinatarioCommissioneLocale(Long idEnteDelegato, Date dataValidita) throws Exception;
	List<DestinatarioDTO> findDestinatariNotifica(UUID idPratica, Integer idOrganizzazione, Gruppi gruppo, boolean prendiReferenti, boolean prendiAdmin) throws Exception;
	List<DestinatarioDTO> findDestinatariNotifica(UUID idPratica, Integer idOrganizzazione, Gruppi gruppo) throws Exception;
}
