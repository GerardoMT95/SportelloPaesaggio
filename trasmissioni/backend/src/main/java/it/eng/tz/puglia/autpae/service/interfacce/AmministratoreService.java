package it.eng.tz.puglia.autpae.service.interfacce;

import java.util.List;

import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dto.TipologicaDTO;
import it.eng.tz.puglia.autpae.dto.BE_to_FE.PannelloAmministratoreDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.enumeratori.EsitoVerifica;
import it.eng.tz.puglia.autpae.enumeratori.StatoFascicolo;

public interface AmministratoreService {

//	public PermessiCampionamentoDTO getPermessiCampionamento() throws Exception;

//	public boolean salvaPermessiCampionamento(PermessiCampionamentoDTO body) throws Exception;

	public FascicoloDTO getFascicoloDaAnnullare (String codice) throws Exception;

	public FascicoloDTO getFascicoloDaModificare(String codice) throws Exception;
	
	public boolean annullaFascicolo (long id) throws Exception;

	public boolean modificaFascicolo(long id, int giorni) throws Exception;

	public PannelloAmministratoreDTO infoPannello(String jwt) throws Exception;

	public boolean savePannello(PannelloAmministratoreDTO pannello) throws Exception;

	public List<TipologicaDTO> listaGruppi(String jwt) throws Exception;

	public boolean revocaModifica (long idFascicolo) throws Exception;

	/**
	 * check se lo stato fascicolo e lo stato del campionamento ammettono la modifica
	 * @autor Adriano Colaianni
	 * @date 30 ago 2021
	 * @param stato
	 * @param esito
	 * @throws CustomOperationToAdviceException
	 */
	public void isModificabile(StatoFascicolo stato,EsitoVerifica esito) throws CustomOperationToAdviceException;

	/**
	 * check se lo stato fascicolo e lo stato del campionamento ammettono la cancellazione post trasmissione
	 * @autor Adriano Colaianni
	 * @date 30 ago 2021
	 * @param stato
	 * @param esitoVerifica
	 * @throws CustomOperationToAdviceException
	 */
	void isAnnullabile(StatoFascicolo stato, EsitoVerifica esitoVerifica) throws CustomOperationToAdviceException;
	
}