package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto;

import java.util.function.Consumer;

import org.codehaus.jackson.annotate.JsonIgnore;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DisclaimerDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.EnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.PaesaggioOrganizzazioneDTO;

public class PlainNumberValueLabel extends GenericPlainValueLabel<Long>
{
	private static final long serialVersionUID = 5681458088773084011L;
	
	public PlainNumberValueLabel()
	{
		super();
	}
	public PlainNumberValueLabel(Long value, String description) 
	{
	  super(value, description);
	}
	public PlainNumberValueLabel(Long value, String description, String linked)
	{
		super(value, description, linked);
	}
	public PlainNumberValueLabel(DisclaimerDTO dto)
	{
		super((long) dto.getId(), dto.getTesto(), Integer.toString(dto.getTipoProcedimento()));
	}
	public PlainNumberValueLabel(EnteDTO other)
	{
		this.setValue((long) other.getId());
		this.setDescription(other.getNome());
		this.setLinked(null);
	}
	public PlainNumberValueLabel(PaesaggioOrganizzazioneDTO other)
	{
		this.setValue((long) other.getId());
		this.setDescription(other.getDenominazione());
		this.setLinked(null);
	}

	@JsonIgnore
	public static void toSetter(PlainNumberValueLabel val, Consumer<Integer> setVal, Consumer<String> setName)
	{
		if (val != null && val.getValue() != null)
		{
			setVal.accept((int) ((long) val.getValue()));
		} else
		{
			setVal.accept(null);
		}
		if (val != null && val.getDescription() != null)
		{
			setName.accept(val.getDescription());
		} else
		{
			setName.accept(null);
		}
	}

	@JsonIgnore
	public static PlainNumberValueLabel toGetter(Integer val, String label)
	{
		PlainNumberValueLabel ret = null;
		if (val != null)
		{
			ret = new PlainNumberValueLabel(Long.valueOf(val), label);
		}
		return ret;
	}

}
