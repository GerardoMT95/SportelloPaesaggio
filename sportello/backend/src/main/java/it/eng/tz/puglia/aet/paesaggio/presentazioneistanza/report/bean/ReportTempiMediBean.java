package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Bean per report tempi medi
 * @author Antonio La Gatta
 * @date 3 mag 2022
 */
public class ReportTempiMediBean implements Serializable{

	
	/**
	 * @author Antonio La Gatta
	 * @date 3 mag 2022
	 */
	private static final long serialVersionUID = -7214050478699122957L;

	private List<ReportTempiMediDto> tempiList;
	
	private List<ReportTempiMediProcedimentoDto> tempiProcedimentoList;

	/**
	 * @return the tempiList
	 */
	public List<ReportTempiMediDto> getTempiList() {
		return this.tempiList;
	}

	/**
	 * @param tempiList the tempiList to set
	 */
	public void setTempiList(final List<ReportTempiMediDto> tempiList) {
		this.tempiList = tempiList;
	}

	/**
	 * @return the tempiProcedimentoList
	 */
	public List<ReportTempiMediProcedimentoDto> getTempiProcedimentoList() {
		return this.tempiProcedimentoList;
	}

	/**
	 * @param tempiProcedimentoList the tempiProcedimentoList to set
	 */
	public void setTempiProcedimentoList(final List<ReportTempiMediProcedimentoDto> tempiProcedimentoList) {
		this.tempiProcedimentoList = tempiProcedimentoList;
	}
	
}
