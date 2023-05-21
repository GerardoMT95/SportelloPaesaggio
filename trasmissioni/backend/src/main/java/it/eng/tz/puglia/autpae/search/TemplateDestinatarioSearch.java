package it.eng.tz.puglia.autpae.search;

import it.eng.tz.puglia.autpae.dbMapping.TemplateDestinatario;
import it.eng.tz.puglia.autpae.enumeratori.TipoTemplate;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Search for table public.template_destinatario
 */
public class TemplateDestinatarioSearch extends WhereClauseGenerator<TemplateDestinatario> {

	private static final long serialVersionUID = 6693642738L;
	
	private Long id;
	private TipoTemplate codiceTemplate;
	private String emailOpec;
//	private String email;						// inutile
//	private String pec;							// inutile
//	private String denominazione;				// inutile
//	private TipoDestinatario tipo;				// inutile
	
	public TemplateDestinatarioSearch() { }

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoTemplate getCodiceTemplate() {
		return codiceTemplate;
	}

	public void setCodiceTemplate(TipoTemplate codiceTemplate) {
		this.codiceTemplate = codiceTemplate;
	}

	public String getEmailOpec() {
		return emailOpec;
	}

	public void setEmailOpec(String emailOpec) {
		this.emailOpec = emailOpec;
	}
	
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public String getPec() {
//		return pec;
//	}
//
//	public void setPec(String pec) {
//		this.pec = pec;
//	}
//
//	public String getDenominazione() {
//		return denominazione;
//	}
//
//	public void setDenominazione(String denominazione) {
//		this.denominazione = denominazione;
//	}
//
//	public TipoDestinatario getTipo() {
//		return tipo;
//	}
//
//	public void setTipo(TipoDestinatario tipo) {
//		this.tipo = tipo;
//	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((codiceTemplate == null) ? 0 : codiceTemplate.hashCode());
		result = prime * result + ((emailOpec == null) ? 0 : emailOpec.hashCode());
//		result = prime * result + ((denominazione == null) ? 0 : denominazione.hashCode());
//		result = prime * result + ((email == null) ? 0 : email.hashCode());
//		result = prime * result + ((pec == null) ? 0 : pec.hashCode());
//		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		TemplateDestinatarioSearch other = (TemplateDestinatarioSearch) obj;
		if (codiceTemplate != other.codiceTemplate)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (emailOpec == null) {
			if (other.emailOpec != null)
				return false;
		} else if (!emailOpec.equals(other.emailOpec))
			return false;
//		if (denominazione == null) {
//			if (other.denominazione != null)
//				return false;
//		} else if (!denominazione.equals(other.denominazione))
//			return false;
//		if (email == null) {
//			if (other.email != null)
//				return false;
//		} else if (!email.equals(other.email))
//			return false;
//		if (pec == null) {
//			if (other.pec != null)
//				return false;
//		} else if (!pec.equals(other.pec))
//			return false;
//		if (tipo != other.tipo)
//			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TemplateDestinatarioSearch [id=" + id + ", codiceTemplate=" + codiceTemplate + ", emailOpec=" + emailOpec + "]";
	}

	@Override
	protected void generateWhereCriteria() {

		String separator = " where ";
		if(id!=null)
		{
			sql.append(separator)
			   .append(TemplateDestinatario.id.columnName())
			   .append(" = :")
			   .append(TemplateDestinatario.id.columnName());
			parameters.put(TemplateDestinatario.id.columnName(), id);
			separator = " and ";
		}
		if(codiceTemplate!=null)
		{
			sql.append(separator)
			   .append(TemplateDestinatario.codice_template.columnName())
			   .append(" = :")
			   .append(TemplateDestinatario.codice_template.columnName());
			parameters.put(TemplateDestinatario.codice_template.columnName(), codiceTemplate.name());
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(emailOpec))
		{
			sql.append(separator)
			   .append(" ( ")
			   .append(TemplateDestinatario.email.columnName())
			   .append(" = :")
			   .append(TemplateDestinatario.email.columnName())
			   .append(" OR ")
			   .append(TemplateDestinatario.pec.columnName())
			   .append(" = :")
			   .append(TemplateDestinatario.email.columnName())
			   .append(" ) ");
			parameters.put(TemplateDestinatario.email.columnName(), emailOpec);
			separator = " and ";
		}
	}
	
}
