package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.webmail.service;

import java.util.Optional;

import org.springframework.core.io.Resource;

import it.eng.tz.regione.puglia.webmail.be.dto.ConfigurazioneCasellaPostaleDto;
import it.eng.tz.regione.puglia.webmail.be.dto.ConfigurazioneCasellaPostaleResponseDto;
import it.eng.tz.regione.puglia.webmail.be.dto.InvioMailDto;
import it.eng.tz.regione.puglia.webmail.be.dto.InvioMailResultDto;

public interface WebmailService {
	/**
	 * 
	 * @param datiMail
	 * @param allegati
	 * @param idCorrispondenza se a null indica che non deve fare alcun update su DB per registrare 
	 * @return
	 * @throws Exception
	 */
	public InvioMailResultDto inviaMail(InvioMailDto datiMail, Resource[] allegati, Long idCorrispondenza) throws Exception;
	public Optional<ConfigurazioneCasellaPostaleResponseDto> configuraEAttivaCasellaPostale(ConfigurazioneCasellaPostaleDto ccpd) throws Exception;
	//public Optional<ConfigurazioneCasellaPostaleResponseDto> disattivaCasellaPostale(ConfigurazioneCasellaPostaleDto ccpd)	throws Exception;
}
