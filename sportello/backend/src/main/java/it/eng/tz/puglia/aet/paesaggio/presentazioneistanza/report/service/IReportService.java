package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.service;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ReportDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.ReportSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.InsertReportBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.ReportLocalizzazioneBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.ReportOneriIstruttoriDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.ReportOutputBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.ReportParameterBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.SelectReportTypeDto;
import it.eng.tz.puglia.bean.PaginatedList;

/**
 * Service for report
 * @author Antonio La Gatta
 * @date 2 mag 2022
 */
public interface IReportService {
	
	static String KEY_URL_DOWNLOAD_PREFIX="URL_DOWNLOAD_PREFIX";
	static String BASE_PUBLIC_DOWNLOAD = "downloadDocumentoDaMail";

	/**
	 * @author Antonio La Gatta
	 * @date 2 mag 2022
	 * @return id report terminati in ordine decrescente di creazione
	 */
	List<String> idTerminati();
	/**
	 * Elimina fisicamente report
	 * @author Antonio La Gatta
	 * @date 2 mag 2022
	 * @param id
	 * @throws Exception
	 */
	void eliminaReport(String id) throws Exception;
	/**
	 * @author Antonio La Gatta
	 * @date 2 mag 2022
	 * @param id
	 * @return Data creazione report
	 */
	Timestamp dataCreazione(String id);
	/**
	 * @author Antonio La Gatta
	 * @date 12 mag 2022
	 * @param id
	 * @return Data richiesta
	 */
	Timestamp dataRichiesta(String id);
	/**
	 * @author Antonio La Gatta
	 * @date 2 mag 2022
	 * @return massimo numero di ore prima di eliminare report
	 */
	int getMaxOre() throws Exception;
	/**
	 * @author Antonio La Gatta
	 * @date 2 mag 2022
	 * @return next id to execute
	 */
	String getNextId();
	/**
	 * Set Concluso report
	 * @author Antonio La Gatta
	 * @date 2 mag 2022
	 * @throws Exception
	 */
	void concludiReport(String id, ReportOutputBean bean)  throws Exception;
	/**
	 * Set errore stato report
	 * @author Antonio La Gatta
	 * @date 2 mag 2022
	 * @param id
	 */
	void erroreReport(String id);
	/**
	 * @author Antonio La Gatta
	 * @date 2 mag 2022
	 * @param id
	 * @return {@link ReportDTO}
	 */
	ReportDTO detail(String id);
	/**
	 * @author Antonio La Gatta
	 * @date 2 mag 2022
	 * @param id
	 * @return account mail
	 */
	String getMail(String id);
	
    public PaginatedList<ReportDTO> search(final ReportSearch search) throws Exception;
    
    public void insert(InsertReportBean bean) throws Exception;
	
	public List<ReportOneriIstruttoriDto> listaOneriIstruttori(final ReportParameterBean parameters, final String username) throws Exception;
	
	public ReportLocalizzazioneBean listaLocalizzazione(final ReportParameterBean parameters, final String username) throws Exception;
	
	public List<SelectReportTypeDto> selectTipologie();
	
	public void downloadReport(final String idDownload, final String username, final HttpServletResponse response) throws Exception;
}
