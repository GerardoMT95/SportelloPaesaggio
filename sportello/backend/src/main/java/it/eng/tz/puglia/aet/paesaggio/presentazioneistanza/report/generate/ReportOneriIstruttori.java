package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.generate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.ReportOneriIstruttoriDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.ReportParameterBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.service.IReportService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.utils.IReportTypeConstant;

@Component(IReportTypeConstant.REPORT_ONERI_ISTRUTTORI)
public class ReportOneriIstruttori extends GenerateReport<ReportOneriIstruttoriDto>{

	@Autowired
	private IReportService service;
	
	/**
	 * @author Antonio La Gatta
	 * @date 2 mag 2022
	 */
	@Override
	protected List<ReportOneriIstruttoriDto> getList(final ReportParameterBean parameters, final String username) throws Exception {
		return this.service.listaOneriIstruttori(parameters, username);
	}
//
//	/**
//	 * @author Antonio La Gatta
//	 * @date 2 mag 2022
//	 * @see it.eng.tz.aet.report.generate.pdf.ReportPdf#getReportFileName()
//	 */
//	@Override
//	protected String getReportFileName() {
//		return "Oneri Istruttori";
//	}

	/**
	 * @author Antonio La Gatta
	 * @date 2 mag 2022
	 * @see it.eng.tz.aet.report.generate.pdf.ReportPdf#getJasperFileName()
	 */
	@Override
	protected String getJasperFileName() {
		return "report_oneri_istruttori";
	}

	/**
	 * @author Antonio La Gatta
	 * @date 3 mag 2022
	 * @see it.eng.tz.aet.report.generate.pdf.ReportPdf#getTitolo()
	 */
	@Override
	protected String getTitolo() {
		return "Oneri Istruttori";
	}


}
