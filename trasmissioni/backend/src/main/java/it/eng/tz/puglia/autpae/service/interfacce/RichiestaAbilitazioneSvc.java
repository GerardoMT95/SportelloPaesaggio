package it.eng.tz.puglia.autpae.service.interfacce;

import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.aet.abilitazione.bean.DettagliRichiestaDto;
import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dto.RichiestaAbilitazioneDto;


public interface RichiestaAbilitazioneSvc {

	void sendRichiesta(RichiestaAbilitazioneDto richData, MultipartFile file) throws Exception;
	void validaRichiesta(RichiestaAbilitazioneDto richData, MultipartFile file) throws CustomOperationToAdviceException;
	/**
	 * @autor Adriano Colaianni
	 * @date 15 apr 2021
	 * @return
	 */
	DettagliRichiestaDto getLastAbilitazione();

}
