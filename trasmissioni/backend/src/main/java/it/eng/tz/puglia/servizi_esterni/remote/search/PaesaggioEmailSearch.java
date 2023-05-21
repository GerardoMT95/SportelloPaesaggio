package it.eng.tz.puglia.servizi_esterni.remote.search;

import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;
import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Common_Paesaggio_Email;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Search for table common.paesaggio_email
 */
public class PaesaggioEmailSearch extends WhereClauseGenerator<Common_Paesaggio_Email> {

	private static final long serialVersionUID = 6693642738L;
	
	private Long id;
	private String codiceApplicazione;
	private String email;
	private String denominazione;
	private Boolean pec;
	private Integer organizzazioneId;
	private Integer enteId;

	
	public PaesaggioEmailSearch() { }

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCodiceApplicazione() {
		return codiceApplicazione;
	}
	public void setCodiceApplicazione(String codiceApplicazione) {
		this.codiceApplicazione = codiceApplicazione;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public Boolean getPec() {
		return pec;
	}
	public void setPec(Boolean pec) {
		this.pec = pec;
	}
	public Integer getOrganizzazioneId() {
		return organizzazioneId;
	}
	public void setOrganizzazioneId(Integer organizzazioneId) {
		this.organizzazioneId = organizzazioneId;
	}
	public Integer getEnteId() {
		return enteId;
	}
	public void setEnteId(Integer enteId) {
		this.enteId = enteId;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((codiceApplicazione == null) ? 0 : codiceApplicazione.hashCode());
		result = prime * result + ((denominazione == null) ? 0 : denominazione.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((organizzazioneId == null) ? 0 : organizzazioneId.hashCode());
		result = prime * result + ((enteId == null) ? 0 : enteId.hashCode());
		result = prime * result + ((pec == null) ? 0 : pec.hashCode());
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
		PaesaggioEmailSearch other = (PaesaggioEmailSearch) obj;
		if (codiceApplicazione == null) {
			if (other.codiceApplicazione != null)
				return false;
		} else if (!codiceApplicazione.equals(other.codiceApplicazione))
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
		if (organizzazioneId == null) {
			if (other.organizzazioneId != null)
				return false;
		} else if (!organizzazioneId.equals(other.organizzazioneId))
			return false;
		if (enteId == null) {
			if (other.enteId != null)
				return false;
		} else if (!enteId.equals(other.enteId))
			return false;
		if (pec == null) {
			if (other.pec != null)
				return false;
		} else if (!pec.equals(other.pec))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "PaesaggioEmailSearch [id=" + id + ", codiceApplicazione=" + codiceApplicazione + ", email=" + email
				+ ", denominazione=" + denominazione + ", pec=" + pec + ", organizzazioneId=" + organizzazioneId + ", enteId=" + enteId + "]";
	}


	@Override
	protected void generateWhereCriteria() {

		String separator = " where ";
		if(id!=null)
		{
			sql.append(separator)
			   .append(Common_Paesaggio_Email.id.getCompleteName())
			   .append(" = :")
			   .append(Common_Paesaggio_Email.id.columnName());
			parameters.put(Common_Paesaggio_Email.id.columnName(), id);
			separator = " and ";
		}
		if(!StringUtil.isEmpty(codiceApplicazione))
		{
			sql.append(separator)
			   .append(Common_Paesaggio_Email.codice_applicazione.getCompleteName())
			   .append(" = :")
			   .append(Common_Paesaggio_Email.codice_applicazione.columnName());
			parameters.put(Common_Paesaggio_Email.codice_applicazione.columnName(), codiceApplicazione.toUpperCase());
			separator = " and ";
		}
		if(!StringUtil.isEmpty(email))
		{
			sql.append(separator)
			   .append(Common_Paesaggio_Email.email.getCompleteName())
			   .append(" ILIKE :")
			   .append(Common_Paesaggio_Email.email.columnName());
			parameters.put(Common_Paesaggio_Email.email.columnName(), StringUtil.convertLike(email));
			separator = " and ";
		}
		if(!StringUtil.isEmpty(denominazione))
		{
			sql.append(separator)
			   .append(Common_Paesaggio_Email.denominazione.getCompleteName())
			   .append(" ILIKE :")
			   .append(Common_Paesaggio_Email.denominazione.columnName());
			parameters.put(Common_Paesaggio_Email.denominazione.columnName(), StringUtil.convertLike(denominazione));
			separator = " and ";
		}
		if(pec != null)
		{
			sql.append(separator)
			   .append(Common_Paesaggio_Email.pec.getCompleteName())
			   .append(" = :")
			   .append(Common_Paesaggio_Email.pec.columnName());
			parameters.put(Common_Paesaggio_Email.pec.columnName(), pec);
			separator = " and ";
		}
		if(organizzazioneId!=null && enteId==null)
		{
			sql.append(separator)
			   .append(Common_Paesaggio_Email.organizzazione_id.getCompleteName())
			   .append(" = :")
			   .append(Common_Paesaggio_Email.organizzazione_id.columnName())
			   .append(" and ")
			   .append(Common_Paesaggio_Email.ente_id.getCompleteName())
			   .append(" IS NULL");
			parameters.put(Common_Paesaggio_Email.organizzazione_id.columnName(), organizzazioneId);
			separator = " and ";
		}
		if(enteId!=null && organizzazioneId==null)
		{
			sql.append(separator)
			   .append(Common_Paesaggio_Email.ente_id.getCompleteName())
			   .append(" = :")
			   .append(Common_Paesaggio_Email.ente_id.columnName())
			   .append(" and ")
			   .append(Common_Paesaggio_Email.organizzazione_id.getCompleteName())
			   .append(" IS NULL");
			parameters.put(Common_Paesaggio_Email.ente_id.columnName(), enteId);
			separator = " and ";
		}
		if(organizzazioneId!=null && enteId!=null)
		{
			sql.append(separator)
			   .append(Common_Paesaggio_Email.organizzazione_id.getCompleteName())
			   .append(" = :")
			   .append(Common_Paesaggio_Email.organizzazione_id.columnName())
			   .append(" and ")
			   .append(Common_Paesaggio_Email.ente_id.getCompleteName())
			   .append(" = :")
			   .append(Common_Paesaggio_Email.ente_id.columnName());
			parameters.put(Common_Paesaggio_Email.organizzazione_id.columnName(), organizzazioneId);
			parameters.put(Common_Paesaggio_Email.ente_id.columnName(), enteId);
			separator = " and ";
		}
	}

}