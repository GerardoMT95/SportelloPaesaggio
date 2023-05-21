package it.eng.tz.puglia.autpae.service.interfacce;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.autpae.dto.AllegatoCustomDTO;
import it.eng.tz.puglia.autpae.dto.InformazioniDTO;
import it.eng.tz.puglia.autpae.dto.TipologicaDestinatarioDTO;

public interface EsitoService {
	
	public List<AllegatoCustomDTO> datiEsito(long idFascicolo) throws Exception;

	public boolean inviaEsito(long idFascicolo) throws Exception;

	public List<AllegatoCustomDTO> proseguiEsito(InformazioniDTO body, MultipartFile file) throws Exception;

	public long caricaOresettaLettera(long idFascicolo, MultipartFile file) throws Exception;
	
	List<TipologicaDestinatarioDTO> destinatariEsito(Long idFascicolo) throws Exception;

}