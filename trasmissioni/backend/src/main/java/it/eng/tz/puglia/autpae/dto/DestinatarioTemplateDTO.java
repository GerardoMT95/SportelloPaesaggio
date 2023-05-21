package it.eng.tz.puglia.autpae.dto;

import it.eng.tz.puglia.autpae.enumeratori.TipoTemplate;

public class DestinatarioTemplateDTO extends TipologicaDestinatarioDTO
{
	private static final long serialVersionUID = 3052387865741405967L;
	private Long id;
	private TipoTemplate codiceTemplate;
	
	public DestinatarioTemplateDTO()
	{
		super();
	}
	public DestinatarioTemplateDTO(TipologicaDestinatarioDTO other)
	{
		super();
		id = null;
		codiceTemplate = null;
		setNome(other.getNome());
		setEmail(other.getEmail());
		setPec(other.isPec());
		setTipo(other.getTipo());
	}
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public TipoTemplate getCodiceTemplate()
	{
		return codiceTemplate;
	}
	public void setCodiceTemplate(TipoTemplate codiceTemplate)
	{
		this.codiceTemplate = codiceTemplate;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((codiceTemplate == null) ? 0 : codiceTemplate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DestinatarioTemplateDTO other = (DestinatarioTemplateDTO) obj;
		if (codiceTemplate == null)
		{
			if (other.codiceTemplate != null)
				return false;
		} else if (!codiceTemplate.equals(other.codiceTemplate))
			return false;
		if (id == null)
		{
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		return "DestinatarioTemplateDTO [id=" + id + ", codiceTemplate=" + codiceTemplate + "]";
	}
}
