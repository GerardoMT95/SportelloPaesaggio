package it.eng.tz.puglia.autpae.search;

import java.util.Date;

import it.eng.tz.puglia.autpae.dbMapping.Responsabile;
import it.eng.tz.puglia.autpae.dbMapping.Richiedente;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Search for table public.responsabile
 */
public class ResponsabileSearch extends WhereClauseGenerator<Richiedente> {

private static final long serialVersionUID = 7162211932348119352L;
	
	private Long   id;
	private String nome;
	private String cognome;
	private String inQualitaDi;
	private String servizioSettoreUfficio;
	private String pec;
	private String mail;
	private String telefono;
	private String riconoscimentoTipo;
	private String riconoscimentoNumero;
	private Date   riconoscimentoDataRilascio;
	private Date   riconoscimentoDataScadenza;
	private String riconoscimentoRilasciatoDa;
	private Long   idFascicolo;
	
	public ResponsabileSearch() { }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getInQualitaDi() {
		return inQualitaDi;
	}

	public void setInQualitaDi(String inQualitaDi) {
		this.inQualitaDi = inQualitaDi;
	}

	public String getServizioSettoreUfficio() {
		return servizioSettoreUfficio;
	}

	public void setServizioSettoreUfficio(String servizioSettoreUfficio) {
		this.servizioSettoreUfficio = servizioSettoreUfficio;
	}

	public String getPec() {
		return pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getRiconoscimentoTipo() {
		return riconoscimentoTipo;
	}

	public void setRiconoscimentoTipo(String riconoscimentoTipo) {
		this.riconoscimentoTipo = riconoscimentoTipo;
	}

	public String getRiconoscimentoNumero() {
		return riconoscimentoNumero;
	}

	public void setRiconoscimentoNumero(String riconoscimentoNumero) {
		this.riconoscimentoNumero = riconoscimentoNumero;
	}

	public Date getRiconoscimentoDataRilascio() {
		return riconoscimentoDataRilascio;
	}

	public void setRiconoscimentoDataRilascio(Date riconoscimentoDataRilascio) {
		this.riconoscimentoDataRilascio = riconoscimentoDataRilascio;
	}

	public Date getRiconoscimentoDataScadenza() {
		return riconoscimentoDataScadenza;
	}

	public void setRiconoscimentoDataScadenza(Date riconoscimentoDataScadenza) {
		this.riconoscimentoDataScadenza = riconoscimentoDataScadenza;
	}

	public String getRiconoscimentoRilasciatoDa() {
		return riconoscimentoRilasciatoDa;
	}

	public void setRiconoscimentoRilasciatoDa(String riconoscimentoRilasciatoDa) {
		this.riconoscimentoRilasciatoDa = riconoscimentoRilasciatoDa;
	}

	public Long getIdFascicolo() {
		return idFascicolo;
	}

	public void setIdFascicolo(Long idFascicolo) {
		this.idFascicolo = idFascicolo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cognome == null) ? 0 : cognome.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idFascicolo == null) ? 0 : idFascicolo.hashCode());
		result = prime * result + ((inQualitaDi == null) ? 0 : inQualitaDi.hashCode());
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((pec == null) ? 0 : pec.hashCode());
		result = prime * result + ((riconoscimentoDataRilascio == null) ? 0 : riconoscimentoDataRilascio.hashCode());
		result = prime * result + ((riconoscimentoDataScadenza == null) ? 0 : riconoscimentoDataScadenza.hashCode());
		result = prime * result + ((riconoscimentoNumero == null) ? 0 : riconoscimentoNumero.hashCode());
		result = prime * result + ((riconoscimentoRilasciatoDa == null) ? 0 : riconoscimentoRilasciatoDa.hashCode());
		result = prime * result + ((riconoscimentoTipo == null) ? 0 : riconoscimentoTipo.hashCode());
		result = prime * result + ((servizioSettoreUfficio == null) ? 0 : servizioSettoreUfficio.hashCode());
		result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResponsabileSearch other = (ResponsabileSearch) obj;
		if (cognome == null) {
			if (other.cognome != null)
				return false;
		} else if (!cognome.equals(other.cognome))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idFascicolo == null) {
			if (other.idFascicolo != null)
				return false;
		} else if (!idFascicolo.equals(other.idFascicolo))
			return false;
		if (inQualitaDi == null) {
			if (other.inQualitaDi != null)
				return false;
		} else if (!inQualitaDi.equals(other.inQualitaDi))
			return false;
		if (mail == null) {
			if (other.mail != null)
				return false;
		} else if (!mail.equals(other.mail))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (pec == null) {
			if (other.pec != null)
				return false;
		} else if (!pec.equals(other.pec))
			return false;
		if (riconoscimentoDataRilascio == null) {
			if (other.riconoscimentoDataRilascio != null)
				return false;
		} else if (!riconoscimentoDataRilascio.equals(other.riconoscimentoDataRilascio))
			return false;
		if (riconoscimentoDataScadenza == null) {
			if (other.riconoscimentoDataScadenza != null)
				return false;
		} else if (!riconoscimentoDataScadenza.equals(other.riconoscimentoDataScadenza))
			return false;
		if (riconoscimentoNumero == null) {
			if (other.riconoscimentoNumero != null)
				return false;
		} else if (!riconoscimentoNumero.equals(other.riconoscimentoNumero))
			return false;
		if (riconoscimentoRilasciatoDa == null) {
			if (other.riconoscimentoRilasciatoDa != null)
				return false;
		} else if (!riconoscimentoRilasciatoDa.equals(other.riconoscimentoRilasciatoDa))
			return false;
		if (riconoscimentoTipo == null) {
			if (other.riconoscimentoTipo != null)
				return false;
		} else if (!riconoscimentoTipo.equals(other.riconoscimentoTipo))
			return false;
		if (servizioSettoreUfficio == null) {
			if (other.servizioSettoreUfficio != null)
				return false;
		} else if (!servizioSettoreUfficio.equals(other.servizioSettoreUfficio))
			return false;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ResponsabileDTO [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", inQualitaDi=" + inQualitaDi
				+ ", servizioSettoreUfficio=" + servizioSettoreUfficio + ", pec=" + pec + ", mail=" + mail
				+ ", telefono=" + telefono + ", riconoscimentoTipo=" + riconoscimentoTipo + ", riconoscimentoNumero="
				+ riconoscimentoNumero + ", riconoscimentoDataRilascio=" + riconoscimentoDataRilascio
				+ ", riconoscimentoDataScadenza=" + riconoscimentoDataScadenza + ", riconoscimentoRilasciatoDa="
				+ riconoscimentoRilasciatoDa + ", idFascicolo=" + idFascicolo + "]";
	}

	@Override
	protected void generateWhereCriteria() {
		String separator = " where ";
		if(id != null)
		{
			sql.append(separator)
			   .append(Responsabile.id.getCompleteName())
			   .append(" = :")
			   .append(Responsabile.id.columnName());
			parameters.put(Responsabile.id.columnName(), id);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(nome))
		{
			sql.append(separator)
			   .append(Responsabile.nome.getCompleteName())
			   .append(" = :")
			   .append(Responsabile.nome.columnName());
			parameters.put(Responsabile.nome.columnName(), nome);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(cognome))
		{
			sql.append(separator)
			   .append(Responsabile.cognome.getCompleteName())
			   .append(" = :")
			   .append(Responsabile.cognome.columnName());
			parameters.put(Responsabile.cognome.columnName(), cognome);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(inQualitaDi))
		{
			sql.append(separator)
			   .append(Responsabile.in_qualita_di.getCompleteName())
			   .append(" = :")
			   .append(Responsabile.in_qualita_di.columnName());
			parameters.put(Responsabile.in_qualita_di.columnName(), inQualitaDi);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(servizioSettoreUfficio))
		{
			sql.append(separator)
			   .append(Responsabile.servizio_settore_ufficio.getCompleteName())
			   .append(" = :")
			   .append(Responsabile.servizio_settore_ufficio.columnName());
			parameters.put(Responsabile.servizio_settore_ufficio.columnName(), servizioSettoreUfficio);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(pec))
		{
			sql.append(separator)
			   .append(Responsabile.pec.getCompleteName())
			   .append(" = :")
			   .append(Responsabile.pec.columnName());
			parameters.put(Responsabile.pec.columnName(), pec);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(mail))
		{
			sql.append(separator)
			   .append(Responsabile.mail.getCompleteName())
			   .append(" = :")
			   .append(Responsabile.mail.columnName());
			parameters.put(Responsabile.mail.columnName(), mail);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(telefono))
		{
			sql.append(separator)
			   .append(Responsabile.telefono.getCompleteName())
			   .append(" = :")
			   .append(Responsabile.telefono.columnName());
			parameters.put(Responsabile.telefono.columnName(), telefono);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(riconoscimentoTipo))
		{
			sql.append(separator)
			   .append(Responsabile.riconoscimento_tipo.getCompleteName())
			   .append(" = :")
			   .append(Responsabile.riconoscimento_tipo.columnName());
			parameters.put(Responsabile.riconoscimento_tipo.columnName(), riconoscimentoTipo);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(riconoscimentoNumero))
		{
			sql.append(separator)
			   .append(Responsabile.riconoscimento_numero.getCompleteName())
			   .append(" = :")
			   .append(Responsabile.riconoscimento_numero.columnName());
			parameters.put(Responsabile.riconoscimento_numero.columnName(), riconoscimentoNumero);
			separator = " and ";
		}
		if(riconoscimentoDataRilascio != null)
		{
			sql.append(separator)
			   .append(Responsabile.riconoscimento_data_rilascio.getCompleteName())
			   .append(" = :")
			   .append(Responsabile.riconoscimento_data_rilascio.columnName());
			parameters.put(Responsabile.riconoscimento_data_rilascio.columnName(), riconoscimentoDataRilascio);
			separator = " and ";
		}
		if(riconoscimentoDataScadenza != null)
		{
			sql.append(separator)
			   .append(Responsabile.riconoscimento_data_scadenza.getCompleteName())
			   .append(" = :")
			   .append(Responsabile.riconoscimento_data_scadenza.columnName());
			parameters.put(Responsabile.riconoscimento_data_scadenza.columnName(), riconoscimentoDataScadenza);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(riconoscimentoRilasciatoDa))
		{
			sql.append(separator)
			   .append(Responsabile.riconoscimento_rilasciato_da.getCompleteName())
			   .append(" = :")
			   .append(Responsabile.riconoscimento_rilasciato_da.columnName());
			parameters.put(Responsabile.riconoscimento_rilasciato_da.columnName(), riconoscimentoRilasciatoDa);
			separator = " and ";
		}
		if(idFascicolo != null)
		{
			sql.append(separator)
			   .append(Responsabile.id_fascicolo.getCompleteName())
			   .append(" = :")
			   .append(Responsabile.id_fascicolo.columnName());
			parameters.put(Responsabile.id_fascicolo.columnName(), idFascicolo);
			separator = " and ";
		}
	}

}
