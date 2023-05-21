package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class TipoCdsAzioneDTO implements Serializable{
	
	/**
	 * @author Alessio Bottalico
	 * @date 28 lug 2022
	 */
	private static final long serialVersionUID = 3863825647301692231L;
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
	
	@Override
	public int hashCode() {
		return Objects.hash(azione, endDate, id, idOrganizzazione, idTipoProcedimento, startDate, userCreate);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoCdsAzioneDTO other = (TipoCdsAzioneDTO) obj;
		return Objects.equals(azione, other.azione) && Objects.equals(endDate, other.endDate)
				&& Objects.equals(id, other.id) && idOrganizzazione == other.idOrganizzazione
				&& idTipoProcedimento == other.idTipoProcedimento && Objects.equals(startDate, other.startDate)
				&& Objects.equals(userCreate, other.userCreate);
	}

}
