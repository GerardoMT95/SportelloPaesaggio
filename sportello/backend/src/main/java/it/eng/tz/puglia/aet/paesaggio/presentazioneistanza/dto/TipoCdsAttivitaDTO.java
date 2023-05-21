package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class TipoCdsAttivitaDTO implements Serializable{

	/**
	 * @author Alessio Bottalico
	 * @date 28 lug 2022
	 */
	private static final long serialVersionUID = 7680313003867237566L;
	private String id;
	private int idTipoProcedimento;
	private int idOrganizzazione;
	private String attivita;
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
	public String getAttivita() {
		return attivita;
	}
	public void setAttivita(String attivita) {
		this.attivita = attivita;
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
	
	@Override
	public int hashCode() {
		return Objects.hash(attivita, endDate, id, idOrganizzazione, idTipoProcedimento, startDate, userCreate);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoCdsAttivitaDTO other = (TipoCdsAttivitaDTO) obj;
		return Objects.equals(attivita, other.attivita) && Objects.equals(endDate, other.endDate)
				&& Objects.equals(id, other.id) && idOrganizzazione == other.idOrganizzazione
				&& idTipoProcedimento == other.idTipoProcedimento && Objects.equals(startDate, other.startDate)
				&& Objects.equals(userCreate, other.userCreate);
	}
	
}
