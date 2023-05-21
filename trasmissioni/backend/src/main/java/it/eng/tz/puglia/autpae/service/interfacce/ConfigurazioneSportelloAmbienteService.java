package it.eng.tz.puglia.autpae.service.interfacce;

import java.util.List;
import java.util.UUID;

import it.eng.tz.puglia.autpae.generated.orm.dto.VPaesaggioTipoCaricaDocumentoDTO;

/**
 * 
 * @author Adriano Colaianni
 * @date 23 lug 2022
 */
public interface ConfigurazioneSportelloAmbienteService {

	/**
	 * elenco per popolare dropdown a selezione multipla
	 * @autor Adriano Colaianni
	 * @date 23 lug 2022
	 * @return
	 */
	public List<VPaesaggioTipoCaricaDocumentoDTO> getElencoTipoDocumentoSportello();
	
	/**
	 * prelievo valori attuali impostati in configurazione
	 * @autor Adriano Colaianni
	 * @date 23 lug 2022
	 * @return
	 * @throws Exception 
	 */
	public List<UUID> getSelected() throws Exception;
	
	

	/**
	 * salvataggio valori selezionati
	 * @autor Adriano Colaianni
	 * @date 23 lug 2022
	 * @param selezionati
	 * @param currentUser
	 * @return
	 * @throws Exception
	 */
	int setSelected(List<UUID> selezionati, String currentUser) throws Exception;
	
}