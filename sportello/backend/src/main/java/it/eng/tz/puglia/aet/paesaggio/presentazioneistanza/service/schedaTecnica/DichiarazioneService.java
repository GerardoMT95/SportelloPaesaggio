package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica;

import java.util.Date;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.DichiarazioneDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DichiarazioneDTO;

public interface DichiarazioneService extends SchedaTecnicaChild<DichiarazioneDto>
{

	/**
	 * 
	 * @author acolaianni
	 *
	 * @param id
	 * @param date
	 * @return
	 */
	public DichiarazioneDTO findByTipoProcedimentoAndData(Integer id, Date date);

	/**
	 * aggiornamento valore con marcatura storico
	 * @author acolaianni
	 *
	 * @param id id procedimento
	 * @param testo
	 * @param labelCb
	 * @return updated row
	 */
	int upsertDichiarazione(int id, String testo, String labelCb);

	/**
	 * validazione per aggiornamento/inserimento dichiarazione da funzionalità di admin
	 * @author acolaianni
	 *
	 * @param id
	 * @param testo
	 * @param labelCb
	 * @throws CustomOperationToAdviceException
	 */
	public void valida(int id, String testo, String labelCb) throws CustomOperationToAdviceException;
}
