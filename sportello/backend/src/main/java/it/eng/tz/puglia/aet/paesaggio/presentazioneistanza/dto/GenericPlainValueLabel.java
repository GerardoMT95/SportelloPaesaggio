package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public abstract class GenericPlainValueLabel<T> implements Serializable
{
	private static final long serialVersionUID = -7331357558804558706L;

	@NotNull
	private T value;
	private String description;
	private String linked;

	public GenericPlainValueLabel()
	{
		super();
	}
	public GenericPlainValueLabel(T value, String description) 
	{
	  this(value, description, null);
	}
	public GenericPlainValueLabel(T value, String description, String linked)
	{
		this.value = value;
		this.description = description;
		this.linked = linked;
	}

	public String getLinked()
	{
		return linked;
	}
	public void setLinked(String linked)
	{
		this.linked = linked;
	}

	public T getValue()
	{
		return value;
	}
	public void setValue(T value)
	{
		this.value = value;
	}

	public String getDescription()
	{
		return description;
	}
	public void setDescription(String label)
	{
		this.description = label;
	}
}
