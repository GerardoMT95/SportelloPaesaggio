package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service;

import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.RichiestaAbilitazioneDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.RichiestaAbilitazioneRichiedenteDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;

public interface RichiestaAbilitazioneSvc {

	/**
	 * abilitazione richiedente in presentazione istanza
	 * @param richData
	 * @param file
	 * @throws Exception
	 */
	void sendRichiesta(RichiestaAbilitazioneRichiedenteDto richData, MultipartFile file) throws Exception;
	
	/**
	 * abilitazione user in istruttoria
	 * @param richData
	 * @param file
	 * @throws Exception
	 */
	void sendRichiesta(RichiestaAbilitazioneDto richData, MultipartFile file) throws Exception;
	void validaRichiesta(RichiestaAbilitazioneDto richData, MultipartFile file) throws CustomOperationToAdviceException;

}
