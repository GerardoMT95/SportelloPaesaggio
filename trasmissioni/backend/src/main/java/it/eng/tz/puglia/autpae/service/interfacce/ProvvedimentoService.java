package it.eng.tz.puglia.autpae.service.interfacce;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.autpae.dto.AllegatoCustomDTO;
import it.eng.tz.puglia.autpae.dto.ProvvedimentoTabDTO;
import it.eng.tz.puglia.autpae.dto.SelectOptionDto;
import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.enumeratori.TipoProcedimento;

public interface ProvvedimentoService {

	public ProvvedimentoTabDTO datiProvvedimento(Long idFascicolo) throws Exception;
	
	public AllegatoCustomDTO inserisciAllegato(Long idFascicolo, TipoAllegato tipoAllegato, MultipartFile file) throws IOException, Exception;

	List<SelectOptionDto> validazione(FascicoloDTO fascicolo,Long idFascicolo, TipoProcedimento tipoProcedimento) throws Exception;
	
	public Boolean cambiaTipo(long idFascicolo, long idAllegato, TipoAllegato nuovoTipo) throws Exception;



}