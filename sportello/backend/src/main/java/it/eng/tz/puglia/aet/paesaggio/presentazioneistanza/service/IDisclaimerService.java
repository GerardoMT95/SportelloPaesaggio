package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service;

import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DisclaimerDTO;

public interface IDisclaimerService {

	/**
	 * effettua check di validità,
	 * chiude di valitità gli attuali a current_timestamp e crea nuovi record con data_fine a null e start_date=current_timestamp+1
	 * @author acolaianni
	 *
	 * @param tipoProcedimento
	 * @param disclaimer
	 * @return lista dei disclaimer aggiornati
	 * @throws CustomOperationToAdviceException
	 */
	public List<DisclaimerDTO> updateDisclaimers(int tipoProcedimento, List<DisclaimerDTO> disclaimer) throws CustomOperationToAdviceException;

	/**
	 * 
	 * @author acolaianni
	 *
	 * @param disclaimer
	 * @return empty or messaggio di errore
	 */
	public String validate(DisclaimerDTO disclaimer);

	/**
	 * 
	 * @author acolaianni
	 *
	 * @param disclaimerList
	 * @return empty o messaggio di errore
	 */
	String validate(List<DisclaimerDTO> disclaimerList);
	
	/**
	 * elenco attuali disclaimers
	 * @author acolaianni
	 *
	 * @param tipoProcedimento
	 * @return
	 */
	public List<DisclaimerDTO> currentDisclaimers(int tipoProcedimento);

}
