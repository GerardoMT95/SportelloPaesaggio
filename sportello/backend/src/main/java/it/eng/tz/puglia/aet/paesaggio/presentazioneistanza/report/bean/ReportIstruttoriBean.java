package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean;

import java.io.Serializable;
import java.util.List;


/**
 * Bean per report istruttori
 * @author Antonio La Gatta
 * @date 3 mag 2022
 */
public class ReportIstruttoriBean implements Serializable{

	
	/**
	 * @author Antonio La Gatta
	 * @date 3 mag 2022
	 */
	private static final long serialVersionUID = -7214050478699122957L;

	private List<ReportIstruttoriDto> istruttoreList;
	
	private List<ReportIstruttoriProcedimentoDto> istruttoreProcedimentoList;

	/**
	 * @return the istruttoreList
	 */
	public List<ReportIstruttoriDto> getIstruttoreList() {
		return this.istruttoreList;
	}

	/**
	 * @param istruttoreList the istruttoreList to set
	 */
	public void setIstruttoreList(final List<ReportIstruttoriDto> istruttoreList) {
		this.istruttoreList = istruttoreList;
	}

	/**
	 * @return the istruttoreProcedimentoList
	 */
	public List<ReportIstruttoriProcedimentoDto> getIstruttoreProcedimentoList() {
		return this.istruttoreProcedimentoList;
	}

	/**
	 * @param istruttoreProcedimentoList the istruttoreProcedimentoList to set
	 */
	public void setIstruttoreProcedimentoList(final List<ReportIstruttoriProcedimentoDto> istruttoreProcedimentoList) {
		this.istruttoreProcedimentoList = istruttoreProcedimentoList;
	}
	
}
