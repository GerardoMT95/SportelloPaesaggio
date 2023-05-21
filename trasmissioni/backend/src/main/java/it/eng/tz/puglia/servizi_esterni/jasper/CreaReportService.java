package it.eng.tz.puglia.servizi_esterni.jasper;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import it.eng.tz.puglia.autpae.dto.InformazioniDTO;
import it.eng.tz.puglia.autpae.dto.TipologicaDTO;
import it.eng.tz.puglia.autpae.dto.TipologicaDestinatarioDTO;
import it.eng.tz.puglia.autpae.entity.CampionamentoDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloPublicDto;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.error.exception.CustomOperationException;
import it.eng.tz.puglia.error.exception.CustomValidationException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

public interface CreaReportService {

	/**
	 * 
	 * @param idFascicolo
	 * @param protocollo
	 * @param ulterioriDestinatari
	 * @param info                 su questo oggetto vengono convertiti alcuni campi
	 *                             con enum
	 * @param isBatchUser
	 * @return
	 */
	public JasperPrint creaPdf_RicevutaDiTrasmissione(Long idFascicolo, String protocollo,
			List<TipologicaDestinatarioDTO> ulterioriDestinatari, InformazioniDTO info, boolean isBatchUser);

	public JasperPrint creaPdfLetteraDiTrasmissioneEsitoProvvedimento(Long idFascicolo, String protocollo,
			List<TipologicaDestinatarioDTO> ulterioriDestinatari);

	public JasperPrint crea_LetteraEsitoVerifica(Long idFascicolo, String protocollo);

	public ResponseEntity<InputStreamResource> exportFascicoliCSV(PaginatedList<? extends FascicoloPublicDto> lista)
			throws IOException, JRException, CustomOperationException, CustomValidationException;

	public ResponseEntity<InputStreamResource> exportFascicoliPDF(PaginatedList<? extends FascicoloPublicDto> lista)
			throws IOException, JRException, CustomOperationException, CustomValidationException;

	public JasperPrint creaPdf_DemoTemplate(String nomeTemplate);

	public JasperPrint creaPdf_EsitoCampionamento(String protocollo, CampionamentoDTO campionamento,
			List<TipologicaDTO> destinatariTO, List<TipologicaDTO> destinatariCC, List<FascicoloDTO> fascicoliAll,
			List<FascicoloDTO> fascicoliSelected, List<FascicoloDTO> fascicoliNotSelected);

}
