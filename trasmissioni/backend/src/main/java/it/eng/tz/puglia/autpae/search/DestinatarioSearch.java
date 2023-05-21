package it.eng.tz.puglia.autpae.search;

import java.util.Date;

import it.eng.tz.puglia.autpae.dbMapping.Destinatario;
import it.eng.tz.puglia.autpae.enumeratori.StatoCorrispondenza;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;
import it.eng.tz.puglia.autpae.utility.DateUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Search for table public.destinatario
 */
public class DestinatarioSearch extends WhereClauseGenerator<Destinatario> {

	private static final long serialVersionUID = 6693642738L;
	
	private Long id;
	private String type;
	private String email;
	private StatoCorrispondenza stato;
	private Long idCorrispondenza;
	private String denominazione;
	private Boolean pec;
	private Date dataRicezioneDa;
	private Date dataRicezioneA;
	
	public DestinatarioSearch() { }


	public Date getDataRicezioneDa() {
		return dataRicezioneDa;
	}
	public void setDataRicezioneDa(Date dataRicezioneDa) {
		this.dataRicezioneDa = dataRicezioneDa;
	}
	public Date getDataRicezioneA() {
		return dataRicezioneA;
	}
	public void setDataRicezioneA(Date dataRicezioneA) {
		this.dataRicezioneA = dataRicezioneA;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getIdCorrispondenza() {
		return idCorrispondenza;
	}
	public void setIdCorrispondenza(Long idCorrispondenza) {
		this.idCorrispondenza = idCorrispondenza;
	}
	public StatoCorrispondenza getStato() {
		return stato;
	}
	public void setStato(StatoCorrispondenza stato) {
		this.stato = stato;
	}
	public Boolean getPec() {
		return pec;
	}
	public void setPec(Boolean pec) {
		this.pec = pec;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dataRicezioneA == null) ? 0 : dataRicezioneA.hashCode());
		result = prime * result + ((dataRicezioneDa == null) ? 0 : dataRicezioneDa.hashCode());
		result = prime * result + ((denominazione == null) ? 0 : denominazione.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idCorrispondenza == null) ? 0 : idCorrispondenza.hashCode());
		result = prime * result + ((pec == null) ? 0 : pec.hashCode());
		result = prime * result + ((stato == null) ? 0 : stato.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DestinatarioSearch other = (DestinatarioSearch) obj;
		if (dataRicezioneA == null) {
			if (other.dataRicezioneA != null)
				return false;
		} else if (!dataRicezioneA.equals(other.dataRicezioneA))
			return false;
		if (dataRicezioneDa == null) {
			if (other.dataRicezioneDa != null)
				return false;
		} else if (!dataRicezioneDa.equals(other.dataRicezioneDa))
			return false;
		if (denominazione == null) {
			if (other.denominazione != null)
				return false;
		} else if (!denominazione.equals(other.denominazione))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idCorrispondenza == null) {
			if (other.idCorrispondenza != null)
				return false;
		} else if (!idCorrispondenza.equals(other.idCorrispondenza))
			return false;
		if (pec == null) {
			if (other.pec != null)
				return false;
		} else if (!pec.equals(other.pec))
			return false;
		if (stato != other.stato)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DestinatarioSearch [id=" + id + ", type=" + type + ", email=" + email + ", stato=" + stato
				+ ", idCorrispondenza=" + idCorrispondenza + ", denominazione=" + denominazione + ", pec=" + pec
				+ ", dataRicezioneDa=" + dataRicezioneDa + ", dataRicezioneA=" + dataRicezioneA + "]";
	}

	@Override
	protected void generateWhereCriteria() {

		String separator = " where ";
		if(id != null)
		{
			sql.append(separator)
			   .append(Destinatario.id.getCompleteName())
			   .append(" = :")
			   .append(Destinatario.id.columnName());
			parameters.put(Destinatario.id.columnName(), id);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(type))
		{
			sql.append(separator)
			   .append(Destinatario.type.getCompleteName())
			   .append(" = :")
			   .append(Destinatario.type.columnName());
			parameters.put(Destinatario.type.columnName(), type);
			separator = " and ";
		}
		if(pec != null)
		{
			sql.append(separator)
			   .append(Destinatario.pec.getCompleteName())
			   .append(" = :")
			   .append(Destinatario.pec.columnName());
			parameters.put(Destinatario.pec.columnName(), pec);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(email))
		{
			sql.append(separator)
			   .append(Destinatario.email.getCompleteName())
			   .append(" = :")
			   .append(Destinatario.email.columnName());
			parameters.put(Destinatario.email.columnName(), email);
			separator = " and ";
		}
		if(stato != null)
		{
			sql.append(separator)
			   .append(Destinatario.stato.getCompleteName())
			   .append(" = :")
			   .append(Destinatario.stato.columnName());
			parameters.put(Destinatario.stato.columnName(), stato.name());
			separator = " and ";
		}
		if(idCorrispondenza != null)
		{
			sql.append(separator)
			   .append(Destinatario.id_corrispondenza.getCompleteName())
			   .append(" = :")
			   .append(Destinatario.id_corrispondenza.columnName());
			parameters.put(Destinatario.id_corrispondenza.columnName(), idCorrispondenza);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(denominazione))
		{
			sql.append(separator)
			   .append(Destinatario.denominazione.getCompleteName())
			   .append(" = :")
			   .append(Destinatario.denominazione.columnName());
			parameters.put(Destinatario.denominazione.columnName(), denominazione);
			separator = " and ";
		}
		if(dataRicezioneDa != null && dataRicezioneA != null)
		{
			sql.append(separator)
			   .append(Destinatario.data_ricezione.getCompleteName())
			   .append(" between :dataRicezioneDa and :dataRicezioneA");
			parameters.put("dataRicezioneDa", dataRicezioneDa);
			parameters.put("dataRicezioneA", DateUtil.endOfDay(dataRicezioneA));
			separator = " and ";
		}
		else if(dataRicezioneDa != null)
		{
			sql.append(separator)
			   .append(Destinatario.data_ricezione.getCompleteName())
			   .append(" >= :")
			   .append(Destinatario.data_ricezione.columnName());
			parameters.put(Destinatario.data_ricezione.columnName(), dataRicezioneDa);
			separator = " and ";
		}
		else if(dataRicezioneA != null)
		{
			sql.append(separator)
			   .append(Destinatario.data_ricezione.getCompleteName())
			   .append(" <= :")
			   .append(Destinatario.data_ricezione.columnName());
			parameters.put(Destinatario.data_ricezione.columnName(), DateUtil.endOfDay(dataRicezioneA));
			separator = " and ";
		}
	}
	
}
