package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDelegatoDTO;

public interface PraticaDelegatoService
{
    PraticaDelegatoDTO addDelegato(PraticaDelegatoDTO delegato, MultipartFile delega, PraticaDTO pratica) throws Exception;
    PraticaDelegatoDTO getLastDelegato() throws Exception;
    List<PraticaDelegatoDTO> findDelegati(UUID idPratica) throws Exception;
    void validaDelegato(PraticaDelegatoDTO delegato, MultipartFile delega) throws CustomOperationToAdviceException;
}
