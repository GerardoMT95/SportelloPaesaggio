package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service;

import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDelegatoDTO;

public interface DelegatoService
{
    PraticaDelegatoDTO addDelegato(PraticaDelegatoDTO delegato, MultipartFile delega) throws Exception;
}
