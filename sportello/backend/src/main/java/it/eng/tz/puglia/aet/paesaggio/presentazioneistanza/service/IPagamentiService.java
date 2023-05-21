package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service;

import javax.servlet.http.HttpServletResponse;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.AvviaPagamentoRequestBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PagamentoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaPagamentiDTO;


/**
 * Metodo per avviare pagamenti
 * @author Antonio La Gatta
 * @date 8 lug 2021
 */
public interface IPagamentiService {

	/**
	 * effettua check di competenza
	 * Avvia un pagamento
	 * @author Antonio La Gatta
	 * @date 8 lug 2021
	 * @param idPratica
	 * @param requestBean
	 * @return il pagamento
	 */
	PagamentoDto avviaPagamento(String idPratica, AvviaPagamentoRequestBean requestBean) throws Exception;
	/**
	 * Recupera il pagamento
	 * @author Antonio La Gatta
	 * @date 8 lug 2021
	 * @param idPratica
	 * @return il pagamento
	 */
	PagamentoDto getPagamento(String idPratica) throws Exception;
	/**
	 * Verifica se il pagamento è stato effettuato
	 * @author Antonio La Gatta
	 * @date 8 lug 2021
	 * @param idPratica
	 * @return true se pagamento effettuato, false altrimenti
	 * @throws Exception
	 */
	boolean verificaPagamento(String idPratica) throws Exception;
	/**
	 * Annulla pagamento
	 * @author Antonio La Gatta
	 * @date 8 lug 2021
	 * @param idPratica
	 * @throws Exception
	 */
	void annullaPagamento(String idPratica) throws Exception;
	/**
	 * Chiama mypay per generazione url e la salva a db
	 * @author Antonio La Gatta
	 * @date 8 lug 2021
	 * @param idPratica
	 * @return url mypay
	 * @throws Exception
	 */
	String generaUrlMyPay(String idPratica) throws Exception;
	/**
	 * download ricevuta xml
	 * @author Antonio La Gatta
	 * @date 9 lug 2021
	 * @param idPratica
	 * @param response
	 * @throws Exception
	 */
	void downloadRicevuta(String idPratica, HttpServletResponse response) throws Exception;
	
	PagamentoDto getPagamentoByCodPraticaAppptr(String idPratica) throws Exception;
	
	/**
	 * retrieve del pagamento pratica senza alcun check di competenza o stato
	 * @author acolaianni
	 *
	 * @param pratica
	 * @return
	 */
	PraticaPagamentiDTO getPagamentoPraticaWithoutCheck(PraticaDTO pratica);
	
	/**
	 * calcolo oneri
	 * @author acolaianni
	 *
	 * @param idEnteDelegato
	 * @param tipoProcedimento
	 * @param importoProgetto
	 * @return
	 */
	double calcolaOneri(Integer idEnteDelegato, Integer tipoProcedimento, double importoProgetto);
	/**
	 * aggiornamento flag pagato su pagamento
	 * @author acolaianni
	 *
	 * @param idPagamento
	 * @param ricevuta
	 */
	void aggiornaPagato(Long idPagamento, String ricevuta);
}
