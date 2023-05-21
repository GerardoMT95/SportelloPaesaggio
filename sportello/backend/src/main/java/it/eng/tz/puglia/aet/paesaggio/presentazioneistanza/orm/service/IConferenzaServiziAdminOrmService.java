package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service;

import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.SelectParentItemDto;


public interface IConferenzaServiziAdminOrmService {

	/**
	 * @author Alessio Bottalico
	 * @date 25 lug 2022
	 * @param idProcedimento
	 * @return
	 */
	List<SelectParentItemDto> attivita(String idProcedimento);
	/**
	 * @author Alessio Bottalico
	 * @date 25 lug 2022
	 * @param idProcedimento
	 * @return
	 */
	List<SelectParentItemDto> tipo(String idProcedimento);
	/**
	 * @author Alessio Bottalico
	 * @date 25 lug 2022
	 * @param idProcedimento
	 * @return
	 */
	List<SelectParentItemDto> azioni(String idProcedimento);
	/**
	 * @author Alessio Bottalico
	 * @date 25 lug 2022
	 * @param idProcedimento
	 * @return
	 */
	List<SelectParentItemDto> attivitaDisponibili();
	/**
	 * @author Alessio Bottalico
	 * @date 25 lug 2022
	 * @param idProcedimento
	 * @return
	 */
	List<SelectParentItemDto> tipoDisponibili();
	/**
	 * @author Alessio Bottalico
	 * @date 25 lug 2022
	 * @param idProcedimento
	 * @return
	 */
	List<SelectParentItemDto> azioniDisponibili();
	/**
	 * @author Alessio Bottalico
	 * @date 25 lug 2022
	 * @param idProcedimento
	 * @return
	 */
	void saveAttivita(String idProcedimento, List<SelectParentItemDto> attivita);
	/**
	 * @author Alessio Bottalico
	 * @date 25 lug 2022
	 * @param idProcedimento
	 * @return
	 */
	void saveTipo(String idProcedimento, List<SelectParentItemDto> tipi);
	/**
	 * @author Alessio Bottalico
	 * @date 25 lug 2022
	 * @param idProcedimento
	 * @return
	 */
	void saveAzioni(String idProcedimento, List<SelectParentItemDto> azioni);
}
