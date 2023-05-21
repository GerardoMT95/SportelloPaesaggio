package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.validation.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.GetFieldsUtils;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.validation.Contains;
import it.eng.tz.puglia.util.string.StringUtil;

public class ContainsValidation implements ConstraintValidator<Contains, Collection<?>>
{
	private String field;
	private List<String> values;
	public void initialize(Contains c)
	{
		field = c.field();
		values = Arrays.asList(c.values());
	}
	
	@Override
	public boolean isValid(Collection<?> collection, ConstraintValidatorContext context)
	{
		boolean valid = false;
		if(collection != null && ! collection.isEmpty())
		{
			List<String> collectionValues;
			if(StringUtil.isNotEmpty(field))
				collectionValues = collection.stream().map(m -> GetFieldsUtils.getField(m, field) != null ? GetFieldsUtils.getField(m, field).toString() : null).collect(Collectors.toList());
			else
				collectionValues = collection.stream().map(m -> m != null ? m.toString() : null).collect(Collectors.toList());
			valid = true;
			for(String v: values)
			{
				valid = valid && collectionValues.contains(v);
			}
		}
		return valid;
	}
	
}
