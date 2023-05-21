package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service;

import java.util.List;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.IstrPraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ComuniCompetenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PrivacyDTO;

public interface IPraticaDataAwareService {
	
	public void checkDiMiaCompetenza(PraticaDTO pratica) throws CustomOperationToAdviceException;

	void checkStatoForUpdate(PraticaDTO pratica) throws CustomOperationToAdviceException;

	void checkComuniCompetenza(List<ComuniCompetenzaDTO> entiCompetenza);

	void checkEsistenzaPrivacy(List<PrivacyDTO> privacyCurrent, PraticaDTO dto) throws CustomOperationToAdviceException;

	PraticaDTO checkDiMiaCompetenza(UUID idPratica) throws CustomOperationToAdviceException;

	PraticaDTO checkDiMiaCompetenzaByCodicePraticaAppptr(String codicePraticaAppptr) throws CustomOperationToAdviceException;

	/**
	 * verifica se l'utente loggato è rup per la pratica sia per diretta assegnazione oppure perchè è REG_
	 * @author acolaianni
	 *
	 * @param rup
	 * @return
	 */
	boolean isRupForPratica(String rup);

}