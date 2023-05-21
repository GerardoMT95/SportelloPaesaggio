package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.generate;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.ReportOutputBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.ReportParameterBean;

/**
 * Interface to generate report
 * @author Antonio La Gatta
 * @date 2 mag 2022
 */
public interface IReportGenerateService {
	
	/**
	 * @author Antonio La Gatta
	 * @date 2 mag 2022
	 * @param parameters
	 * @param username
	 * @return Generate report and in output get path file
	 * @throws Exception
	 */
	ReportOutputBean generateReport(ReportParameterBean parameters, String username) throws Exception;

}
