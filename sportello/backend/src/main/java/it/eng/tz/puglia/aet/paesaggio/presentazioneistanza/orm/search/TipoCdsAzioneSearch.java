package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import java.sql.Timestamp;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

public class TipoCdsAzioneSearch extends BaseSearchRequestBean{
	

	/**
	 * @author Alessio Bottalico
	 * @date 28 lug 2022
	 */
	private static final long serialVersionUID = -6829390940997997655L;
	private String id;
	private int idTipoProcedimento;
	private int idOrganizzazione;
	private String azione;
	private Timestamp startDate;
	private Timestamp endDate;
	private String userCreate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getIdTipoProcedimento() {
		return idTipoProcedimento;
	}
	public void setIdTipoProcedimento(int idTipoProcedimento) {
		this.idTipoProcedimento = idTipoProcedimento;
	}
	public int getIdOrganizzazione() {
		return idOrganizzazione;
	}
	public void setIdOrganizzazione(int idOrganizzazione) {
		this.idOrganizzazione = idOrganizzazione;
	}
	public String getAzione() {
		return azione;
	}
	public void setAzione(String azione) {
		this.azione = azione;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public String getUserCreate() {
		return userCreate;
	}
	public void setUserCreate(String userCreate) {
		this.userCreate = userCreate;
	}
	
}
