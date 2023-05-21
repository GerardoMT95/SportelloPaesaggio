package it.eng.tz.puglia.autpae.service.interfacce;

import it.eng.tz.puglia.autpae.dto.BE_to_FE.EnteBean;
import it.eng.tz.puglia.autpae.dto.BE_to_FE.PaesaggioEmailBean;
import it.eng.tz.puglia.autpae.dto.BE_to_FE.organizzazioni.PaesaggioOrganizzazioneBean;
import it.eng.tz.puglia.autpae.dto.BE_to_FE.organizzazioni.PaesaggioOrganizzazioniSearchBean;
import it.eng.tz.puglia.autpae.generated.orm.dto.PaesaggioOrganizzazioneAttributiDTO;
import it.eng.tz.puglia.autpae.generated.orm.dto.PaesaggioOrganizzazioneDTO;
import it.eng.tz.puglia.autpae.search.PesaggioEmailSearchBean;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.remote.dto.PaesaggioEmailDTO;

public interface PaesaggioOrganizzazioneService
{
	PaginatedList<PaesaggioOrganizzazioneBean> searchPaesaggioOrganizzazione(PaesaggioOrganizzazioniSearchBean search) throws Exception;
	java.util.List<PaesaggioOrganizzazioneDTO> searchPaesaggioOrganizzazioneNoPag(PaesaggioOrganizzazioniSearchBean search) throws Exception;
	/**
	 * cerca solo sui tipi ETI ED SBAP
	 * @autor Adriano Colaianni
	 * @date 28 lug 2021
	 * @param idOrganizzazione
	 * @return
	 * @throws Exception
	 */
	PaesaggioOrganizzazioneBean findPaesaggioOrganizzazione(Long idOrganizzazione) throws Exception;
	/**
	 * cerca su tutti i tipi
	 * @autor Adriano Colaianni
	 * @date 28 lug 2021
	 * @param idOrganizzazione
	 * @return
	 * @throws Exception
	 */
	PaesaggioOrganizzazioneBean findPaesaggioOrganizzazioneAllTipi(Long idOrganizzazione) throws Exception;
	PaesaggioOrganizzazioneBean insert(PaesaggioOrganizzazioneBean bean) throws Exception;
	PaesaggioOrganizzazioneBean update(PaesaggioOrganizzazioneBean bean) throws Exception;
	void attivaOrganizzazione(PaesaggioOrganizzazioneAttributiDTO bean) throws Exception;
	void aggiornaTermineAbilitazione(PaesaggioOrganizzazioneAttributiDTO bean) throws Exception;
	void disattivaOrganizzazione(Long idOrganizzazione) throws Exception;
	java.util.List<EnteBean> listEntiOrganizzazione(Long idOrganizzazione) throws Exception;
	java.util.List<EnteBean> listEntiOrganizzazione(Long idOrganizzazione, boolean isSbap) throws Exception;
	PaginatedList<PaesaggioEmailBean> listPaesaggioEmail(PesaggioEmailSearchBean search) throws Exception;
	PaesaggioEmailBean savePaesaggioEmail(PaesaggioEmailDTO bean) throws Exception;
	PaesaggioEmailBean updatePaesaggioEmail(PaesaggioEmailDTO bean) throws Exception;
	void deletePaesaggioEmail(Long idPaesaggioEmail) throws Exception;
	java.util.List<EnteBean> findCompetenze(long idOrganizzazione) throws Exception;	
}
