package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Bean per report localizzazione
 * @author Antonio La Gatta
 * @date 3 mag 2022
 */
public class ReportLocalizzazioneBean implements Serializable{

	
	/**
	 * @author Antonio La Gatta
	 * @date 3 mag 2022
	 */
	private static final long serialVersionUID = -7214050478699122957L;

	private List<ReportProvinciaDto> provinciaList;
	
	private List<ReportProvinciaProcedimentoDto> provinciaProcedimentoList;

	private List<ReportComuneDto> comuneList;
	
	private List<ReportComuneProcedimentoDto> comuneProcedimentoList;

	/**
	 * @return the provinciaList
	 */
	public List<ReportProvinciaDto> getProvinciaList() {
		return this.provinciaList;
	}

	/**
	 * @param provinciaList the provinciaList to set
	 */
	public void setProvinciaList(final List<ReportProvinciaDto> provinciaList) {
		this.provinciaList = provinciaList;
	}

	/**
	 * @return the provinciaProcedimentoList
	 */
	public List<ReportProvinciaProcedimentoDto> getProvinciaProcedimentoList() {
		return this.provinciaProcedimentoList;
	}

	/**
	 * @param provinciaProcedimentoList the provinciaProcedimentoList to set
	 */
	public void setProvinciaProcedimentoList(final List<ReportProvinciaProcedimentoDto> provinciaProcedimentoList) {
		this.provinciaProcedimentoList = provinciaProcedimentoList;
	}

	/**
	 * @return the comuneList
	 */
	public List<ReportComuneDto> getComuneList() {
		return this.comuneList;
	}

	/**
	 * @param comuneList the comuneList to set
	 */
	public void setComuneList(final List<ReportComuneDto> comuneList) {
		this.comuneList = comuneList;
	}

	/**
	 * @return the comuneProcedimentoList
	 */
	public List<ReportComuneProcedimentoDto> getComuneProcedimentoList() {
		return this.comuneProcedimentoList;
	}

	/**
	 * @param comuneProcedimentoList the comuneProcedimentoList to set
	 */
	public void setComuneProcedimentoList(final List<ReportComuneProcedimentoDto> comuneProcedimentoList) {
		this.comuneProcedimentoList = comuneProcedimentoList;
	}
	
}
