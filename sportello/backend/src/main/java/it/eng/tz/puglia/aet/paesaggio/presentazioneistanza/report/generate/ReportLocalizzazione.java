package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.generate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.ReportLocalizzazioneBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.ReportParameterBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.service.IReportService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.utils.IReportTypeConstant;

@Component(IReportTypeConstant.REPORT_LOCALIZZAZIONE)
public class ReportLocalizzazione extends GenerateReport<ReportLocalizzazioneBean>{

	@Autowired
	private IReportService service;
	
	/**
	 * @author Antonio La Gatta
	 * @date 3 mag 2022
	 * @see it.eng.tz.aet.report.generate.pdf.ReportPdf#getList(it.eng.tz.aet.bean.ReportParameterBean, java.lang.String)
	 */
	@Override
	protected List<ReportLocalizzazioneBean> getList(final ReportParameterBean parameters, final String username) throws Exception {
		final List<ReportLocalizzazioneBean> result = new ArrayList<>();
		result.add(this.service.listaLocalizzazione(parameters, username));
		return result;
	}
//
//	/**
//	 * @author Antonio La Gatta
//	 * @date 2 mag 2022
//	 * @see it.eng.tz.aet.report.generate.pdf.ReportPdf#getReportFileName()
//	 */
//	@Override
//	protected String getReportFileName() {
//		return "Localizzazione";
//	}

	/**
	 * @author Antonio La Gatta
	 * @date 2 mag 2022
	 * @see it.eng.tz.aet.report.generate.pdf.ReportPdf#getJasperFileName()
	 */
	@Override
	protected String getJasperFileName() {
		return "report_localizzazione";
	}

	/**
	 * @author Antonio La Gatta
	 * @date 3 mag 2022
	 * @see it.eng.tz.aet.report.generate.pdf.ReportPdf#getTitolo()
	 */
	@Override
	protected String getTitolo() {
		return "Localizzazione";
	}


}
