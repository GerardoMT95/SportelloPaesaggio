package it.eng.tz.puglia.servizi_esterni.remote.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.servizi_esterni.remote.utility.TipoEnte;
import it.eng.tz.puglia.servizi_esterni.remote.utility.TipoEnteSerializer;

public class EnteDTO implements Serializable
{
	private static final long serialVersionUID = 1950139697991161830L;
	private Integer id;
	@JsonProperty(access=Access.WRITE_ONLY)
	private Integer parentId;
	private String nome;
	private String descrizione;
	private String codice;
	@JsonProperty(access=Access.WRITE_ONLY)
	private String pec;
	@JsonSerialize(using=TipoEnteSerializer.class)
	private TipoEnte tipo;
	private String codiceFiscale;
	
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	
	public Integer getParentId()
	{
		return parentId;
	}
	public void setParentId(Integer parentId)
	{
		this.parentId = parentId;
	}
	
	public String getNome()
	{
		return nome;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	
	public String getDescrizione()
	{
		return descrizione;
	}
	public void setDescrizione(String descrizione)
	{
		this.descrizione = descrizione;
	}
	
	public String getCodice()
	{
		return codice;
	}
	public void setCodice(String codice)
	{
		this.codice = codice;
	}
	
	public String getPec()
	{
		return pec;
	}
	public void setPec(String pec)
	{
		this.pec = pec;
	}
	
	public TipoEnte getTipo()
	{
		return tipo;
	}
	public void setTipo(TipoEnte tipo)
	{
		this.tipo = tipo;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	@Override
	public String toString() {
		return "EnteDTO [id=" + id + ", parentId=" + parentId + ", nome=" + nome + ", descrizione=" + descrizione
				+ ", codice=" + codice + ", pec=" + pec + ", tipo=" + tipo + ", codiceFiscale=" + codiceFiscale + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codice == null) ? 0 : codice.hashCode());
		result = prime * result + ((codiceFiscale == null) ? 0 : codiceFiscale.hashCode());
		result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
		result = prime * result + ((pec == null) ? 0 : pec.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		EnteDTO other = (EnteDTO) obj;
		if (codice == null) {
			if (other.codice != null)
				return false;
		} else if (!codice.equals(other.codice))
			return false;
		if (codiceFiscale == null) {
			if (other.codiceFiscale != null)
				return false;
		} else if (!codiceFiscale.equals(other.codiceFiscale))
			return false;
		if (descrizione == null) {
			if (other.descrizione != null)
				return false;
		} else if (!descrizione.equals(other.descrizione))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		if (pec == null) {
			if (other.pec != null)
				return false;
		} else if (!pec.equals(other.pec))
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}
	
	
}
